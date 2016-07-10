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
      	Ext.create('Ext.grid.RowNumberer'),
		//{dataIndex:'id',text:'id'},
		{dataIndex:'ecode',text:'二维码',width:130},
		{dataIndex:'type_name',text:'动作'},
		{dataIndex:'prod_name',text:'品名'},
		{dataIndex:'prod_style',text:'型号'},
		{dataIndex:'subtype_name',text:'子类型'},
		
		{dataIndex:'brand_name',text:'品牌'}
		//{dataIndex:'supplier_name',text:'供应商'}
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
			    		rootProperty:'root'
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
