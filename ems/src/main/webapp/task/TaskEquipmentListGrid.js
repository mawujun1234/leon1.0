Ext.define('Ems.task.TaskEquipmentListGrid',{
	extend:'Ext.grid.Panel',
	requires: [
	     'Ems.task.TaskEquipmentList'
	],
	columnLines :true,
	stripeRows:true,
	viewConfig:{
		stripeRows:true,
		listeners:{
			refresh:function(){
				//this.select(0);
			}
		}
	},
	initComponent: function () {
      var me = this;
      me.columns=[
		{dataIndex:'id',text:'id'},
		{dataIndex:'ecode',text:'ecode'},
		{dataIndex:'equipment_status',text:'equipment_status'},
		{dataIndex:'task_id',text:'task_id'},
		{dataIndex:'type',text:'type'}
      ];
      
	  me.store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			model: 'Ems.task.TaskEquipmentList',
			autoLoad:false,
			proxy:{
			    	type:'ajax',
			    	//extraParams:{type:1,edit:true},
			    	url:Ext.ContextPath+"/task/queryTaskEquipmentList.do",
			    	reader:{
			    		type:'json',
			    		root:'root'
			    	}
			}
	  });
	  
      me.dockedItems= [{
	        xtype: 'pagingtoolbar',
	        store: me.store,  
	        dock: 'bottom',
	        displayInfo: true
	  }];
	  
//	  me.tbar=	[{
//			text: '刷新',
//			itemId:'reload',
//			disabled:me.disabledAction,
//			handler: function(btn){
//				var grid=btn.up("grid");
//				grid.getStore().reload();
//			},
//			iconCls: 'form-reload-button'
//		}]
       
      me.callParent();
	}
});
