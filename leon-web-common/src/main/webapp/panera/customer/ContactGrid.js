Ext.define('Leon.panera.customer.ContactGrid',{
	extend:'Ext.grid.Panel',
	requires: [
	     'Leon.panera.customer.Contact'
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
		{dataIndex:'chatNum',text:'chatNum'},
		{dataIndex:'email',text:'email'},
		{dataIndex:'fax',text:'fax'},
		{dataIndex:'mobile',text:'mobile'},
		{dataIndex:'name',text:'name'},
		{dataIndex:'phone',text:'phone'},
		{dataIndex:'position',text:'position'}
      ];
      
	  me.store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			model: 'Leon.panera.customer.Contact',
			autoLoad:true
	  });
	  
      me.dockedItems= [{
	        xtype: 'pagingtoolbar',
	        store: me.store,  
	        dock: 'bottom',
	        displayInfo: true
	  }];
	  
	  me.tbar=	[{
			text: '新增',
			//itemId:'reload',
			handler: function(btn){
				var grid=btn.up("grid");
				grid.getStore().reload();
			},
			iconCls: 'form-add-button'
		},{
			text: '删除',
			handler: function(btn){
				var grid=btn.up("grid");
				grid.getStore().reload();
			},
			iconCls: 'form-delete-button'
		},{
			text: '刷新',
			itemId:'reload',
			disabled:me.disabledAction,
			handler: function(btn){
				var grid=btn.up("grid");
				grid.getStore().reload();
			},
			iconCls: 'form-reload-button'
		}]
       
      me.callParent();
	}
});
