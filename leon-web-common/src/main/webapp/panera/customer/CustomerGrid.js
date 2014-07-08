Ext.define('Leon.panera.customer.CustomerGrid',{
	extend:'Ext.grid.Panel',
	requires: [
	     //'Leon.panera.customer.Customer'
		'Leon.panera.customer.CustomerForm',
		'Leon.panera.customer.Followup',
		'Leon.panera.customer.ContactGrid',
		'Leon.panera.customer.FollowupGrid'
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
		//{dataIndex:'id',text:'id'},
      	{xtype: 'rownumberer'},
      	{dataIndex:'name',text:'客户编号',flex:1,renderer:function(value,metaData ,record){
      		if(record.get('deleted')){
      			return '<span style="color:red;">'+value+'(已删除)</span>';
      		} else {
      			return value;
      		}
      	}},
		{dataIndex:'name',text:'客户名称',flex:1},
		{dataIndex:'contact_name',text:'联系人'},
		{dataIndex:'contact_email',text:'邮箱'},
		//{dataIndex:'continent_id',text:'洲'},
		{dataIndex:'country_name',text:'国家'},
		{dataIndex:'customerProperty_name',text:'客户性质'},
		{dataIndex:'inquiryDate',text:'初次询盘时间',xtype: 'datecolumn',   format:'Y-m-d'},
		{dataIndex:'followupNum',text:'跟进次数',width:55},
		{dataIndex:'businessPhase_id',text:'业务阶段',renderer:function(value,metaData ,record){
      			if("none"==value){
					return "未回复";
				} else if("replay"==value){
					return "客户回复";
				}else if("discuss"==value){
					return "讨论价格";
				}else if("send"==value){
					return "送样";
				}else if("deal"==value){
					return "成交";
				}
      	}},
		{dataIndex:'star',text:'客户星级',width:55,renderer:function(value,metaData ,record){
      		if(value){
      			return value+'星';
      		} else {
      			return value;
      		}
      	}}
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
	  
	  var actionBUttons=	[{
			text: '新增',
			//itemId:'reload',
			//disabled:me.disabledAction,
			handler: function(btn){
				var grid=btn.up("grid");
				var form=Ext.create('Leon.panera.customer.CustomerForm',{
					update:false,
					grid:grid
				});
				var record=new Leon.panera.customer.Customer();
				form.loadRecord(record);
				var win=Ext.create('Ext.Window',{
					title:'新增客户',
					modal:true,
					constrainHeader:true,
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
//				var grid=btn.up("grid");
//				var record=grid.getSelectionModel( ).getLastSelected( );
//				if(!record){
//					Ext.Msg.alert("消息","请先选择一个顾客.");
//					return;
//				}
				me.showUpdateForm();
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
					constrainHeader:true,
					autoScroll :true,
					width:600,
					height:500,
					layout:'fit',
					items:[grid]
				});
				win.show();
            }
        }, {
            text: '往来跟进记录',
            iconCls:'followup_icons',
            handler: function(btn){
            	var grid=btn.up("grid");
				var record=grid.getSelectionModel( ).getLastSelected( );
				if(!record){
					Ext.Msg.alert("消息","请先选择一个顾客.");
					return;
				}
				
		    	var grid=Ext.create('Leon.panera.customer.FollowupGrid',{
					customer_id:record.getId()
				});
				var win=Ext.create('Ext.Window',{
					title:'往来跟进记录',
					modal:true,
					constrainHeader:true,
					//autoScroll :true,
					width:600,
					height:500,
					layout:'fit',
					items:[grid]
				});
				win.show();
            }
        }];
		
		var queryItems=[];
		var nameField=Ext.create('Ext.form.field.Text',{
			emptyText:'请输入公司名称'
		});
		queryItems.push(nameField);
		
		var contact_nameField=Ext.create('Ext.form.field.Text',{
			emptyText:'请输入联系人'
		});
		queryItems.push(contact_nameField);
		
		var contact_emailField=Ext.create('Ext.form.field.Text',{
			emptyText:'请输入email'
		});
		queryItems.push(contact_emailField);
		
		var delectedField=Ext.create('Ext.form.field.Checkbox',{
			
		});
		queryItems.push('已删除',delectedField);
		
		queryItems.push({
			text: '查询',
			itemId:'reload',
			//disabled:me.disabledAction,
			handler: function(btn){
				var grid=btn.up("grid");

				var params={
					deleted:delectedField.getValue(),
					name:nameField.getValue(),
					contact_name:contact_nameField.getValue(),
					contact_email:contact_emailField.getValue()
				}
				grid.getStore().load({params:params});
			},
			iconCls: 'form-reload-button'
		});
		me.tbar={
			 xtype: 'container',
 			defaults: {anchor: '0'},
  			defaultType: 'toolbar',
 			layout: 'anchor',
			items:[{xtyle:'toolbar',items:actionBUttons},{xtype:'toolbar',items:queryItems}]
		}

       me.on("itemdblclick",function(view,record, item, index){
			me.showUpdateForm(record);
		});
      me.callParent();
	},
	showUpdateForm:function(record){
		var grid=this;
		if(!record){
			var record=grid.getSelectionModel( ).getLastSelected( );
			if(!record){
				Ext.Msg.alert("消息","请先选择一个联系方式.");
				return;
			}
		}
		var form=Ext.create('Leon.panera.customer.CustomerForm',{
					update:true,
					grid:grid
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
					constrainHeader:true,
					autoScroll :true,
					width:600,
					height:500,
					layout:'fit',
					items:[form]
				});
				form.win=win;
				win.show();
				
	}
});
