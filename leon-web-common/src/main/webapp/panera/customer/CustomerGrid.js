Ext.define('Leon.panera.customer.CustomerGrid',{
	extend:'Ext.grid.Panel',
	requires: [
	     //'Leon.panera.customer.Customer'
		'Leon.panera.customer.CustomerForm'
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
		{dataIndex:'name',text:'公司名'},
		{dataIndex:'contactName',text:'联系人'},
		{dataIndex:'email',text:'邮箱'},
		{dataIndex:'continent',text:'洲'},
		{dataIndex:'country',text:'国家'},
		{dataIndex:'customerProperty',text:'客户性质'},
		{dataIndex:'inquiryDate',text:'初次询盘时间',xtype: 'datecolumn',   format:'Y-m-d'},
		{dataIndex:'contactNum',text:'主动联系次数'},
		{dataIndex:'businessPhase',text:'业务阶段'},
		{dataIndex:'star',text:'客户星级'}
      ];
      
	  me.store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			model: 'Leon.panera.customer.Customer',
			//fields:[],
			autoLoad:true
//			proxy: {
//				url:Ext.ContextPath+'/customer/query',
//				type: 'ajax',
//				reader: {
//				     type: 'json',
//				     root:'root'
//				}
//			}
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
			//disabled:me.disabledAction,
			handler: function(btn){
				var form=Ext.create('Leon.panera.customer.CustomerForm',{
					
				});
				var win=Ext.create('Ext.Window',{
					title:'新增客户',
					modal:true,
					autoScroll :true,
					width:600,
					height:500,
					layout:'fit',
					items:[form]
				});
				win.show();
			},
			iconCls: 'form-add-button'
		},{
			text: '刷新',
			itemId:'reload',
			//disabled:me.disabledAction,
			handler: function(btn){
				var grid=btn.up("grid");
				grid.getStore().reload();
			},
			iconCls: 'form-reload-button'
		}]
       
      me.callParent();
	}
});
