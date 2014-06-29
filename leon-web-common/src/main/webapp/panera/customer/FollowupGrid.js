Ext.define('Leon.panera.customer.FollowupGrid',{
	extend:'Ext.grid.Panel',
	requires: [
	     'Leon.panera.customer.Followup',
	     'Ext.ux.PreviewPlugin',
	     'Leon.panera.customer.FollowupForm'
	],
	columnLines :true,
	stripeRows:true,
	loadMask: true,
        viewConfig: {
            id: 'gv',
            //trackOver: false,
            //stripeRows: false,
            plugins: [{
                ptype: 'preview',
                bodyField: 'content',
                expanded: true,
                pluginId: 'preview'
            }]
    },
	initComponent: function () {
      var me = this;
      me.columns=[
		//{dataIndex:'id',text:'id'},
        {dataIndex:'createDate',text:'创建时间',xtype: 'datecolumn',   format:'Y-m-d'},
        {dataIndex:'method',text:'跟进方式'},
		//{dataIndex:'content',text:'内容'},
        {dataIndex:'feedbackDate',text:'反馈时间',xtype: 'datecolumn',   format:'Y-m-d'},
		//{dataIndex:'feedbackContent',text:'feedback'},
		//
		{dataIndex:'nextDate',text:'下次跟进时间',xtype: 'datecolumn',   format:'Y-m-d'},
		{dataIndex:'nextContent',text:'下次跟进内容',flex:1,renderer:function formatQtip(value,metadata){   
		    metadata.tdAttr = "data-qtip='" + value + "'";
		    return value;    
			}
		}
      ];
      
      
	  me.store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			model: 'Leon.panera.customer.Followup',
			autoLoad:true
	  });
	  me.store.getProxy().extraParams={customer_id:me.customer_id};
	  
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
				var form=Ext.create('Leon.panera.customer.FollowupForm',{
					update:false
				});
				var record=new Leon.panera.customer.Contact({
					customer_id:me.customer_id
				
				});
				form.loadRecord(record);
				
				var win=Ext.create('Ext.Window',{
					title:'新增往来跟进记录',
					modal:true,
					autoScroll :true,
					width:600,
					height:400,
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
				var form=Ext.create('Leon.panera.customer.FollowupForm',{
					update:true,
					grid:grid
				});
				form.loadRecord(record);
				
				
				var win=Ext.create('Ext.Window',{
					title:'更新往来跟进记录',
					modal:true,
					autoScroll :true,
					width:600,
					height:400,
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
							url:Ext.ContextPath+"/followup/deleteById",
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
