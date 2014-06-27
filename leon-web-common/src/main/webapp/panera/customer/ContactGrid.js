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
		{dataIndex:'isDefault',text:'是否默认',renderer:function(value){
			if(value===true){
				return "默认";
			} else {
				return "";
			}
		}},
		{dataIndex:'name',text:'姓名'},
		{dataIndex:'position',text:'职位'},
		{dataIndex:'phone',text:'电话'},
		{dataIndex:'mobile',text:'手机'},
		{dataIndex:'chatNum',text:'聊天账号'},
		{dataIndex:'email',text:'电子 邮件'},
		{dataIndex:'fax',text:'传真'}
      ];
      
	  me.store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			model: 'Leon.panera.customer.Contact',
			autoLoad:true
	  });
	   me.store.getProxy().extraParams={customer_id:me.customer_id};
	  
//      me.dockedItems= [{
//	        xtype: 'pagingtoolbar',
//	        store: me.store,  
//	        dock: 'bottom',
//	        displayInfo: true
//	  }];
	  
	  me.tbar=	[{
			text: '新增',
			//itemId:'reload',
			handler: function(btn){
				var form=Ext.create('Leon.panera.customer.ContactForm',{
					update:false
				});
				var record=new Leon.panera.customer.Contact({
					customer_id:me.customer_id
				
				});
				form.loadRecord(record);
				
				var win=Ext.create('Ext.Window',{
					title:'新增联系方式',
					modal:true,
					autoScroll :true,
					width:600,
					height:300,
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
					Ext.Msg.alert("消息","请先选择一个联系方式.");
					return;
				}
				var form=Ext.create('Leon.panera.customer.ContactForm',{
					update:true,
					grid:grid
				});
				form.loadRecord(record);
				
				
				var win=Ext.create('Ext.Window',{
					title:'更新联系方式',
					modal:true,
					autoScroll :true,
					width:600,
					height:300,
					layout:'fit',
					items:[form]
				});
				form.win=win;
				
				win.show();
			},
			iconCls: 'form-update-button'
		},{
			text: '删除',
			handler: function(btn){
				var grid=btn.up("grid");
				var record=grid.getSelectionModel( ).getLastSelected( );
				if(!record){
					Ext.Msg.alert("消息","请先选择一个联系方式.");
					return;
				}
				Ext.Msg.confirm("消息","确定要删除吗?",function(btn) {
					if(btn=='yes'){
						Ext.Ajax.request({
							url:Ext.ContextPath+"/contact/deleteById",
							params:{id:record.get("id")},
							success:function(response){
								grid.getStore().remove(record);
							}
						});
					}
				});
			},
			iconCls: 'form-delete-button'
		},{
			text: '设为默认联系方式',
			handler: function(btn){
				var grid=btn.up("grid");
				var record=grid.getSelectionModel( ).getLastSelected( );
				if(!record){
					Ext.Msg.alert("消息","请先选择一个顾客.");
					return;
				}
				var data=record.raw;
				data.isDefault=true;
				Ext.Ajax.request({
							url:Ext.ContextPath+"/contact/setDefault",
							params:data,
							success:function(response){
								grid.getStore().reload();
							}
				});
				
			},
			iconCls: 'taskBar-index-button'
		},{
			text: '刷新',
			handler: function(btn){
				var grid=btn.up("grid");
				grid.getStore().reload();
			},
			iconCls: 'form-reload-button'
		}]
       
      me.callParent();
	}
});
