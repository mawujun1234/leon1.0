Ext.define('Ems.baseinfo.SupplierContactGrid',{
	extend:'Ext.grid.Panel',
	requires: [
	     'Ems.baseinfo.SupplierContact',
	     'Ems.baseinfo.SupplierContactForm'
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
		//{dataIndex:'id',text:'id'},
		{dataIndex:'contact',text:'联系人'},
		{dataIndex:'position',text:'职位'},
		{dataIndex:'mobile',text:'手机'},
		{dataIndex:'phone',text:'电话'},
		{dataIndex:'email',text:'电子邮件'},
		{dataIndex:'fax',text:'传真'},	
		{dataIndex:'address',text:'地址'},
		{dataIndex:'postcode',text:'邮编'}
      ];
      
	  me.store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			model: 'Ems.baseinfo.SupplierContact',
			autoLoad:false
	  });
	  
//      me.dockedItems= [{
//	        xtype: 'pagingtoolbar',
//	        store: me.store,  
//	        dock: 'bottom',
//	        displayInfo: true
//	  }];
	  

       
	  me.initAction();
      me.callParent();
	},
	initAction:function(){
     	var me = this;
     	var actions=[];
     	
       var create = new Ext.Action({
		    text: '新建',
		    itemId:'create',
		    disabled:me.disabledAction,
		    handler: function(b){
		    	me.onCreate(null,b);
		    },
		    iconCls: 'form-add-button'
		});
		//me.addAction(create);
		actions.push(create);
		var update = new Ext.Action({
		    text: '更新',
		    itemId:'update',
		    disabled:me.disabledAction,
		    handler: function(b){
		    	me.onUpdate(null,b);
		    },
		    iconCls: 'form-update-button'
		});
		//me.addAction(create);
		actions.push(update);
		
		var destroy = new Ext.Action({
		    text: '删除',
		    itemId:'destroy',
		    disabled:me.disabledAction,
		    handler: function(){
		    	me.onDelete();    
		    },
		    iconCls: 'form-delete-button'
		});
		//me.addAction(destroy);
		actions.push(destroy)
		
		var reload = new Ext.Action({
		    text: '刷新',
		    itemId:'reload',
		    handler: function(){
		    	me.onReload();
		    },
		    iconCls: 'form-reload-button'
		});
		//me.addAction(reload);
		actions.push(reload);

		me.tbar={
			itemId:'action_toolbar',
			layout: {
	               overflowHandler: 'Menu'
	        },
			items:actions
			//,autoScroll:true		
		};

    },
    onCreate:function(){
    	var me=this;
    	if(!me.supplier_id){
    		Ext.Msg.alert("消息","请先选择供应商!");
    		return;
    	}

    	var record=new Ems.baseinfo.SupplierContact({
    		supplier_id:me.supplier_id
    	});    

		var form=new Ems.baseinfo.SupplierContactForm({
			listeners:{
				saved:function(){
					win.close();
					me.getStore().reload();
					//me.tree.getStore().reload({node:parent});
				}
			}
		});
		form.getForm().loadRecord(record);
		var win=new Ext.window.Window({
			items:[form],
			layout:'fit',
			closeAction:'destroy',
			width:300,
			height:300,
			modal:true
		});
		//form.win=win
		win.show();

    },
    onUpdate:function(){
    	var me=this;
    	if(!me.supplier_id){
    		Ext.Msg.alert("消息","请先选择供应商!");
    		return;
    	}
    	
    	var record=me.getSelectionModel( ).getLastSelected( );
		if(!record){
			Ext.Msg.alert("消息","请先选择一条联系方式!");	
			return;
		}
		
		var form=new Ems.baseinfo.SupplierContactForm({
			listeners:{
				saved:function(){
					//form.updateRecord();
					win.close();
					//alert(record.get("id")+"_"+record.get("levl"));
					//me.tree.getStore().getNodeById(record.get("id")+"_"+record.get("levl")).set("text",record.get("text")) 
				}
			}
		});
		form.getForm().loadRecord(record);
		//var ids=record.get("id").split("_");
		//form.getForm().findField("id").setValue(ids[0]);
		form.getForm().findField("id").setReadOnly(true);
		
		var win=new Ext.window.Window({
			items:[form],
			layout:'fit',
			closeAction:'destroy',
			width:300,
			height:300,
			modal:true
		});
		//form.win=win
		win.show();	
		    	
		
    },
    onDelete:function(){
    	var me=this;
    	var record=me.getSelectionModel( ).getLastSelected( );

		if(!record){
		    Ext.Msg.alert("消息","请先选择类型");	
			return;
		}

		Ext.Msg.confirm("删除",'确定要删除吗?', function(btn, text){
				if (btn == 'yes'){
					Ext.Ajax.request({
						url:Ext.ContextPath+'/supplierContact/destroy.do',
						params:{
							id:record.get("id")
						},
						method:'POST',
						success:function(){
							//var parent=me.tree.getSelectionModel( ).getLastSelected( )||me.tree.getRootNode( );  
							//me.tree.getStore().reload({node:parent});
							
							me.getStore().remove(record);
						}
					});
			}
		});
    },
    onReload:function(){
    	var me=this;
    	me.getStore().reload();	
//    	var parent=node||me.getSelectionModel( ).getLastSelected( );
//		if(parent){
//		    me.getStore().reload({node:parent});
//		} else {
//		    me.getStore().reload();	
//		}      
    }
});
