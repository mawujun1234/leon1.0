

Ext.Ajax.timeout=60000000;
Ext.Ajax.defaultHeaders={ 'Accept':'application/json;'},
Ext.Ajax.on({
	requestexception:function(conn, response, options, eOpts ){
		var status = response.status;
 		var text = response.responseText;
 		switch (status) {
			case 404 :
				top.Ext.MessageBox.alert("错误", "加载数据时发生错误:请求url不可用");
				break;
			case 200 :
				if (text.length > 0) {
					var data = Ext.decode(text);
					if (data && data.error) {
						top.Ext.MessageBox.alert("错误", "加载数据时发生错误:<br/>"
										+ data.error);
					} else {
						top.Ext.MessageBox
								.alert("错误", "加载数据时发生错误:<br/>" + text);
					}
				}
				break;
			case 0 :
				top.Ext.MessageBox.alert("错误", "加载数据时发生错误:<br/>" + "远程服务器无响应");
				break;
			default :
				var data = Ext.decode(text);
				if (data && data.success==false) {
					//top.Ext.MessageBox.alert("错误", "加载数据时发生错误<br/>错误码:"+ status + "<br/>错误信息:" + data.message);
					top.Ext.MessageBox.alert("错误", "错误信息:" + data.message);
				} else {
					top.Ext.MessageBox.alert("错误", "错误信息:" + text);
				}

				break;
		}
		//alert('连接后台失败，请检查网络和后台服务器是否正常运行!');
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
	defineModel:function(className, data){
		if(!data.proxy){
			var path=Ext.String.uncapitalize(className.split('.').pop());
			data.proxy={
				type:'bajax',
				api:{
					read:'/'+path+'/query',
					load : '/'+path+'/load',
					create:'/'+path+'/create',
					update:'/'+path+'/update',
					destroy:'/'+path+'/destroy'
				}
			}
		}
		return Ext.define(className, data);
	},
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
    	record[this.instanceName] = reader.read([associationData]).records[0];
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
            
		for (var i = 0; i < associationCount; i++) {
            association = associations[i];
            
            type = association.type;
            associationKey = association.associationKey;
            if (type == 'hasMany') {//hasMany就不做处理
               
            } else if (type == 'belongsTo' || type == 'hasOne') {
                associatedRecord = me[association.instanceName];
                // If we have a record, put it onto our list
                if (associatedRecord ) {
                	//alert(associatedRecord.getId());
                	if(me.get(association.foreignKey) && me.get(association.foreignKey)!=associatedRecord.getId()){
                		alert("数据不一致，请联系管理员!");
                		throw new Error("association.instanceName的id和association.foreignKey的值不一致,请先进行处理！或者设置"+association.instanceName+"==null，或者更改foreignKey:" 
                		+association.foreignKey+"，即重新加载关联对象的数据getter({reload:true})或者调用setter方法，更新关联对象");

                	} else if(!me.get(association.foreignKey)){
                		me.set(association.foreignKey,associatedRecord.getId());
                	}
                    associationData[associationKey] = associatedRecord.getData();
                } else {
                	//如果没有值就不返回
                	if(me.get(association.foreignKey)){
                		associationData[associationKey]={
	                		id:me.get(association.foreignKey)
	                	}
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
//    ,set:function(fieldName, newValue){
//    	var me=this,
//    	single = (typeof fieldName == 'string'),
//    	associations     = me.associations.items,association,i,type,
//        associationCount = associations.length;
//    	if (single) {
//            values = me._singleProp;
//            values[fieldName] = newValue;
//        } else {
//            values = fieldName;
//        }
//        //for (name in values) {
//        	
//        //}
//        var reader=me.getProxy().getReader();
//        for (i = 0; i < associationCount; i++) {	
//        	 association = associations[i];
//        	 type = association.type;
//        	 if (type == 'belongsTo' || type == 'hasOne') {
//        	 	if(values[association.foreignKey] && me[association.instanceName] && values[association.foreignKey]!=me[association.instanceName].getId()){
//        	 		console.log(values[association.foreignKey]);
//        	 		console.log(me[association.instanceName].getId());
//        	 		//console.log(me['get' + association.associatedName]);
//        	 		// read: function(record, reader, associationData){
//        	 		association.read(me,me.getProxy().getReader(),{
//        	 			id:values[association.foreignKey]
//        	 		});
//        	 		
////        			me[association.instanceName]={
////        	 			id:values[association.foreignKey]
////        	 		}
//        		}
//        	 }
//        }
//        
//    	return this.callOverridden(arguments);
//    }
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

///**
// * 覆盖原来的关联关系配置方式
// */
//Ext.override(Ext.data.BelongsToAssociation, { 
//	constructor: function(config) {
//		this.callOverridden(arguments);
//
//        var me             = this,
//            ownerProto     = me.ownerModel.prototype,
//            associatedName = me.associatedName,
//            associatedNameCapitalize=Ext.String.capitalize(associatedName),
//            associatedNameUnCapitalize=Ext.String.uncapitalize(associatedName),
//            getterName     = config.getterName || 'get' + associatedNameCapitalize,
//            setterName     = config.setterName || 'set' + associatedNameCapitalize;
//
//        Ext.apply(me, {
//            name        : associatedName,
//            foreignKey  : config.foreignKey||associatedNameUnCapitalize + "_id",
//            instanceName: associatedName + 'BelongsToInstance',
//            associationKey:  config.associationKey||associatedNameUnCapitalize
//        });
//
//        var orginalgetterName='get' + associatedName;
//        var orginalsetterName='set' + associatedName;
//        ownerProto[getterName] = ownerProto[orginalgetterName];
//        ownerProto[setterName] = ownerProto[orginalsetterName];
//        delete ownerProto[orginalgetterName];
//        delete ownerProto[orginalsetterName];
//	}
//});
//Ext.override(Ext.data.association.HasOne, { 
//	constructor: function(config) {
//		this.callOverridden(arguments);
//
//        var me             = this,
//            ownerProto     = me.ownerModel.prototype,
//            associatedName = me.associatedName,
//            associatedNameCapitalize=Ext.String.capitalize(associatedName),
//            associatedNameUnCapitalize=Ext.String.uncapitalize(associatedName),
//            getterName     = me.getterName || 'get' + associatedNameCapitalize,
//            setterName     = me.setterName || 'set' + associatedNameCapitalize;
//
//        Ext.apply(me, {
//            name        : associatedName,
//            foreignKey  : config.foreignKey||associatedNameUnCapitalize + "_id",
//            instanceName: associatedName + 'HasOneInstance',
//            associationKey:  config.associationKey||associatedNameUnCapitalize
//        });
//        //ownerProto[getterName] = me.createGetter();
//        //ownerProto[setterName] = me.createSetter();
//	}
//});