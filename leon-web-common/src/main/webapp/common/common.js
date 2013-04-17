Ext.Ajax.timeout=60000000;
Ext.Ajax.on({
	requestexception:function(conn, response, options, eOpts ){
		alert('连接后台失败，请检查网络和后台服务器是否正常运行!');
	}
//	,requestcomplete:function(conn, response, options, eOpts ){
//		alert('后台业务发生错误,错误信息为:'+11);
//		return false;
//	}
});

/**
 * 添加了，createModel，专门用来创建具有关联关系的model实例
 */
Ext.apply(Ext,{
	 createModel:function(className,data){
	 	if (typeof className !== 'string') {
	                Ext.Error.raise({
	                    sourceClass: "Ext",
	                    sourceMethod: "define",
	                    msg: "Invalid class name '" + className + "' specified, must be a non-empty string"
	                });
	     }
	            
		var record=Ext.create(className,data);
		//if (implicitIncludes ) {
			var reader=record.getProxy().getReader();
			reader.readAssociated(record, data);
		//}
		return record;

	 }
});
/**
 * 为了后台和前台的模型差异，做的补充
 */
Ext.override(Ext.data.BelongsToAssociation, { 
    read: function(record, reader, associationData){
    	//this.callOverridden(record, reader, associationData);
    	record[this.instanceName] = reader.read([associationData]).records[0];;
    	record.set(this.foreignKey,record[this.instanceName].getId());//主键必须是id
    }
});
Ext.override(Ext.data.HasOneAssociation, { 
    read: function(record, reader, associationData){
    	var inverAssoc=null;
        var inverse = this.associatedModel.prototype.associations.findBy(function(assoc){
            //return assoc.type === 'belongsTo' && assoc.associatedName === record.$className;
        	var bool=assoc.type === 'belongsTo' && assoc.associatedName === record.$className;
        	if(bool){
        		inverAssoc=assoc;
        	}
        	return bool;
        }), newRecord = reader.read([associationData]).records[0];
        
        record[this.instanceName] = newRecord;
        record.set(this.foreignKey,newRecord.getId());
    
        //if the inverse association was found, set it now on each record we've just created
        if (inverse) {
            newRecord[inverse.instanceName] = record;
            newRecord.set(inverAssoc.foreignKey,record.getId());//设置外键的值
        }
    }
});

Ext.override(Ext.data.Model,{
	/**
	 * 只会获取hasOne或belongsTo的id的数据
	 */
	getAssociatedDataOnlyId:function(){
		var me               = this,
            associations     = me.associations.items,
            associationCount = associations.length,
            associationData  = {},

              associatedRecord,    
              association, type, associationKey;
            
		for (i = 0; i < associationCount; i++) {
            association = associations[i];
            
            type = association.type;
            associationKey = association.associationKey;
            if (type == 'hasMany') {//hasMany就不做处理
               
            } else if (type == 'belongsTo' || type == 'hasOne') {
                associatedRecord = me[association.instanceName];
                // If we have a record, put it onto our list
                if (associatedRecord !== undefined) {
                    associationData[associationKey] = associatedRecord.getData();
                } else {
                	associationData[associationKey]={
                		id:me.get(association.foreignKey)
                	}
                }
            }
		}
		return associationData;
	},
	/**
	 * 修改了两处地方，associationKey和id
	 * @param {} seenKeys
	 * @param {} depth
	 * @return {}
	 */
	prepareAssociatedDataCustomer: function(seenKeys, depth) {

        var me               = this,
            associations     = me.associations.items,
            associationCount = associations.length,
            associationData  = {},
            toRead           = [],
            toReadKey        = [],
            toReadIndex      = [],
            associatedStore, associatedRecords, associatedRecord, o, index, result, seenDepth,
            associationId, associatedRecordCount, association, i, j, type, name;

        for (i = 0; i < associationCount; i++) {
            association = associations[i];
            associationId = association.associationId;
            
            seenDepth = seenKeys[associationId];
            if (seenDepth && seenDepth !== depth) {
                continue;
            }
            seenKeys[associationId] = depth;

            type = association.type;
            //name = association.name;
            name = association.associationKey;//修改为associationKey
            if (type == 'hasMany') {
                associatedStore = me[association.storeName];

                associationData[name] = [];
                if (associatedStore && associatedStore.getCount() > 0) {
                    associatedRecords = associatedStore.data.items;
                    associatedRecordCount = associatedRecords.length;

                    for (j = 0; j < associatedRecordCount; j++) {
                        associatedRecord = associatedRecords[j];
                        associationData[name][j] = associatedRecord.getData();
                        toRead.push(associatedRecord);
                        toReadKey.push(name);
                        toReadIndex.push(j);
                    }
                }
            } else if (type == 'belongsTo' || type == 'hasOne') {
                associatedRecord = me[association.instanceName];
                if (associatedRecord !== undefined) {
                    associationData[name] = associatedRecord.getData();
                    toRead.push(associatedRecord);
                    toReadKey.push(name);
                    toReadIndex.push(-1);
                } else {//这个else是添加的
                	associationData[name]={
                		id:me.get(association.foreignKey)
                	}
                }
            }
        }
        
        for (i = 0, associatedRecordCount = toRead.length; i < associatedRecordCount; ++i) {
            associatedRecord = toRead[i];
            o = associationData[toReadKey[i]];
            index = toReadIndex[i];
            result = associatedRecord.prepareAssociatedData(seenKeys, depth + 1);
            if (index === -1) {
                Ext.apply(o, result);
            } else {
                Ext.apply(o[index], result);
            }
        }

        return associationData;
    }
});

Ext.override(Ext.data.writer.Writer, { 
    getRecordData: function(record,operation) { 
    	var me=this;
        var data=this.callOverridden(arguments);
        if(operation.includeAssociated){
        	//获取所有的关联数据，这里还是有只设置id的时候的问题
        	//var aa=record.getAssociatedData( );
        	var aa=record.prepareAssociatedDataCustomer({},1);
    		Ext.apply(data,aa)
        } else {
        	//默认情况下，只获取hasOne和BelongsTo的关联对象的id
        	var aa=record.getAssociatedDataOnlyId( );
    		Ext.apply(data,aa)
        }
        
        return data;
    } 
});