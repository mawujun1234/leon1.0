Ext.define('Leon.genTest.MenuItemGrid',{
	extend:'Ext.grid.Panel',
	requires: [
	     'Leon.genTest.MenuItem'
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
		{dataIndex:'code',text:'code'},
		{dataIndex:'iconCls',text:'iconCls'},
		{dataIndex:'iconCls32',text:'iconCls32'},
		{dataIndex:'javaClass',text:'javaClass'},
		{dataIndex:'leaf',text:'leaf'},
		{dataIndex:'reportCode',text:'reportCode'},
		{dataIndex:'scripts',text:'scripts'},
		{dataIndex:'text',text:'text'},
		{dataIndex:'fun',text:'fun'},
		{dataIndex:'menu',text:'menu'},
		{dataIndex:'parent',text:'parent'}
      ];
      
	  me.store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			model: 'Leon.genTest.MenuItem',
			autoLoad:false
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
		
				var model=Ext.createModel(modelName,{
				        	//id:''
				});
				model.phantom =true;
				grid.getStore().insert(0, model);

			},
			iconCls: 'form-add-button'
		},{
			text: '更新',
			itemId:'update',
			handler: function(btn){
	
			},
			iconCls: 'form-update-button'
		},{
			text: '删除',
			itemId:'destroy',
			handler: function(){
		    	Ext.Msg.confirm("删除",'确定要删除吗?', function(btn, text){
					if (btn == 'yes'){
						var records=me.getSelectionModel( ).getSelection( );//.getLastSelected( );
						me.getStore().remove( records );
						me.getStore().sync({
							failure:function(){
								me.getStore().reload();
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
			handler: function(){
				grid.getStore().reload();
			},
			iconCls: 'form-reload-button'
		}]
       
      me.callParent();
	}
});
