/**
 * 功能的扩展，添加自定义的怎，删，改
 * 添加右键菜单，增，删，改，并且增加工具栏，增，删，改。
 * 后台的类最好继承TreeNode类，这样就可以少写很多代码
 */
Ext.define('Ems.baseinfo.query.EquipmentTypeQueryTree', {
    extend: 'Ext.tree.Panel',
    requires:['Ems.baseinfo.EquipmentType'],
    viewConfig: {
	    getRowClass: function(record, rowIndex, rowParams, store){
	        return record.get("status")==true ? "" : "status_disable";
	    }
    },
    initComponent: function () {
		var me = this;

        me.store = Ext.create('Ext.data.TreeStore', {
	       	autoLoad:true,
	       	nodeParam :'id',//传递到后台的数据，默认是node
	       	model:'Ems.baseinfo.EquipmentType',
			root: {
			    expanded: true,
			   // levl:0,
			    status:true,
			    text:"类型管理" 
			},
			listeners:{
				beforeload:function(store,operation){
					var node=operation.node;//me.getSelectionModel( ).getLastSelected( );
					//console.dir(operation);
					//operation.params.isGrid=false;
					//operation.params.status=true;
					//operation.params.levl=node?node.get("levl"):null;
					store.getProxy().extraParams=Ext.apply(store.getProxy().extraParams,{
						//isGrid:false,
						//levl:node?node.get("levl"):null
					});
				}
			}
		});
		//me.initAction();
       
		me.callParent(arguments);
    }
});
