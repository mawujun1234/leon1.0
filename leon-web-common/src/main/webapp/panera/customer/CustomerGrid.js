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
		{dataIndex:'contact_name',text:'联系人'},
		{dataIndex:'contact_email',text:'邮箱'},
		{dataIndex:'continent_id',text:'洲'},
		{dataIndex:'country_name',text:'国家'},
		{dataIndex:'customerProperty_name',text:'客户性质'},
		{dataIndex:'inquiryDate',text:'初次询盘时间',xtype: 'datecolumn',   format:'Y-m-d'},
		{dataIndex:'contactNum',text:'主动联系次数'},
		{dataIndex:'businessPhase_id',text:'业务阶段'},
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
					update:false
				});
				var record=new Leon.panera.customer.Customer();
				form.loadRecord(record);
				var win=Ext.create('Ext.Window',{
					title:'新增客户',
					modal:true,
					autoScroll :true,
					width:600,
					height:500,
					layout:'fit',
					items:[form]
				});
				form.win=win;
				win.show();
			},
			iconCls: 'form-add-button'
		},{
			text: '更新',
			//itemId:'reload',
			//disabled:me.disabledAction,
			handler: function(btn){
				var grid=btn.up("grid");
				var record=grid.getSelectionModel( ).getLastSelected( );
				if(!record){
					Ext.Msg.alert("消息","请先选择一个顾客.");
					return;
				}
				var form=Ext.create('Leon.panera.customer.CustomerForm',{
					update:true
				});
				form.loadRecord(record);
				
				//先加载需要从后台获取数据的combobox
				var customerSource_id=form.getForm().findField("customerSource_id");
				customerSource_id.getStore().load({
					callback: function(records, operation, success) {
						customerSource_id.setValue(record.get("customerSource_id"));
					}
				});
				
				var customerProperty_id=form.getForm().findField("customerProperty_id");
				customerProperty_id.getStore().load({
					callback: function(records, operation, success) {
						customerProperty_id.setValue(record.get("customerProperty_id"));
					}
				});
				
				var continent_id=form.getForm().findField("continent_id");
				continent_id.getStore().load({
					callback: function(records, operation, success) {
						continent_id.setValue(record.get("continent_id"));
					}
				});
				
				var country_id=form.getForm().findField("country_id");
				country_id.getStore().load({
					callback: function(records, operation, success) {
						country_id.setValue(record.get("country_id"));
					}
				});
				
				
				var win=Ext.create('Ext.Window',{
					title:'更新客户',
					modal:true,
					autoScroll :true,
					width:600,
					height:500,
					layout:'fit',
					items:[form]
				});
				form.win=win;
				win.show();
			},
			iconCls: 'form-update-button'
		},{
			text: '删除',
			//itemId:'reload',
			//disabled:me.disabledAction,
			handler: function(btn){
				var grid=btn.up("grid");
				var record=grid.getSelectionModel( ).getLastSelected( );
				if(!record){
					Ext.Msg.alert("消息","请先选择一个顾客.");
					return;
				}
				Ext.Msg.confirm("消息","确定要删除吗?",function(btn) {
					if(btn=='yes'){
						Ext.Ajax.request({
							url:Ext.ContextPath+"/customer/deleteById",
							params:{id:record.get("id")},
							success:function(response){
								grid.getStore().remove(record);
							}
						});
					}
				});
			},
			iconCls: 'form-delete-button'
		}, {
            text: '联系人管理',
            iconCls:'role-role-iconCls',
            handler: function(btn){
            	var grid=btn.up("grid");
				var record=grid.getSelectionModel( ).getLastSelected( );
				if(!record){
					Ext.Msg.alert("消息","请先选择一个顾客.");
					return;
				}
				
		    	var grid=Ext.create('Leon.panera.customer.ContactGrid',{
					customer_id:record.getId()
				});
				var win=Ext.create('Ext.Window',{
					title:'联系人管理',
					modal:true,
					autoScroll :true,
					width:600,
					height:500,
					layout:'fit',
					items:[grid]
				});
				win.show();
            }
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
