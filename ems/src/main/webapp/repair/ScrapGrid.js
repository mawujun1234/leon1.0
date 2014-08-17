Ext.define('Ems.repair.ScrapGrid',{
	extend:'Ext.grid.Panel',
	requires: [
	     'Ems.repair.Scrap'
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
		{dataIndex:'id',text:'报废单号'},
		{dataIndex:'ecode',text:'条码'},
		{dataIndex:'operateDate',text:'报废时间',xtype: 'datecolumn',   format:'Y-m-d'}
		//{dataIndex:'operater',text:'operater'},
		//{dataIndex:'reason',text:'reason'},
		//{dataIndex:'repair_id',text:'repair_id'},
		//{dataIndex:'residual',text:'residual'}
      ];
      
	  me.store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			model: 'Ems.repair.Scrap',
			autoLoad:true
	  });
	  
      me.dockedItems= [{
	        xtype: 'pagingtoolbar',
	        store: me.store,  
	        dock: 'bottom',
	        displayInfo: true
	  }];
	  
	  me.tbar=	[{
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
