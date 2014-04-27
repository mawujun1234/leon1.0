Ext.define('Leon.desktop.org.OrgTypeGrid',{
	extend:'Ext.grid.Panel',
	requires: [
	     'Leon.desktop.org.OrgType'
	],
	columnLines :true,
	stripeRows:true,
	viewConfig:{
		stripeRows:true,
		listeners:{
			refresh:function(){
				this.select(0);
			}
		}
	},
	initComponent: function () {
      var me = this;
      me.columns=[
		{dataIndex:'id',text:'id'},
		{dataIndex:'iconCls',text:'iconCls'},
		{dataIndex:'name',text:'name'}
      ];
      
	  me.store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			model: 'Leon.desktop.org.OrgType',
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
			itemId:'create',
			handler: function(btn){
				var grid=btn.up("grid");
				var modelName=grid.model||grid.getStore().getProxy( ).getModel().getName( );
				var model=Ext.createModel(modelName,{      	//id:''
				});
				model.phantom =true;
				grid.form.getForm().loadRecord(model);//form是在app中定义的grid.form=form;
				
				grid.form.createAction=true;
				grid.form.down("button#save").enable();
				
			},
			iconCls: 'form-add-button'
		},{
			text: '更新',
			itemId:'update',
			handler: function(btn){
				var grid=btn.up("grid");
				grid.form.down("button#save").enable();
			},
			iconCls: 'form-update-button'
		},{
			text: '删除',
			itemId:'destroy',
			handler: function(btn){
				var grid=btn.up("grid");
		    	Ext.Msg.confirm("删除",'确定要删除吗?', function(btn, text){
					if (btn == 'yes'){
						var records=me.getSelectionModel( ).getSelection( );//.getLastSelected( );
						grid.getStore().remove( records );
						grid.getStore().sync({
							failure:function(){
								grid.getStore().reload();
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
