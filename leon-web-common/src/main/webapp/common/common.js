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
    	record[this.instanceName] = reader.read([associationData]).records[0];;
    	record.set(this.foreignKey,record[this.instanceName].getId());//主键必须是id
    	//this.callParent(record, reader, associationData);   
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