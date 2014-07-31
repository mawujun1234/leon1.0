Ext.define('Ems.baseinfo.AreaGrid',{
	extend:'Ext.grid.Panel',
	requires: [
	     'Ems.baseinfo.Area',
	     'Ems.baseinfo.AreaForm'
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
		
		{dataIndex:'name',text:'名称'},
		{dataIndex:'workunit_name',text:'作业单位'},
		{dataIndex:'memo',text:'描述',flex:1}
		
      ];
      
	  me.store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			model: 'Ems.baseinfo.Area',
			autoLoad:true
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
//    	if(!me.customer_id){
//    		Ext.Msg.alert("消息","请先选择一个客户!");
//    		return;
//    	}

    	

		var form=new Ems.baseinfo.AreaForm({
			listeners:{
				saved:function(){
					win.close();
					me.getStore().reload();
					//me.tree.getStore().reload({node:parent});
				}
			}
		});
		
		var data=form.getValues();
		data.customer_id=me.customer_id;
		data.status=true;
		var record=new Ems.baseinfo.Area(data);    
		form.getForm().loadRecord(record);
		var win=new Ext.window.Window({
			items:[form],
			layout:'fit',
			closeAction:'destroy',
			width:300,
			height:200,
			modal:true
		});
		//form.win=win
		win.show();

    },
    onUpdate:function(){
    	var me=this;
//    	if(!me.customer_id){
//    		Ext.Msg.alert("消息","请先选择一个客户!");
//    		return;
//    	}
    	
    	var record=me.getSelectionModel( ).getLastSelected( );
		if(!record){
			Ext.Msg.alert("消息","请先选择一个片区!");	
			return;
		}
		
		var form=new Ems.baseinfo.AreaForm({
			listeners:{
				saved:function(){
					//form.updateRecord();
					win.close();
				}
			}
		});
		form.getForm().loadRecord(record);
		//var ids=record.get("id").split("_");
		//form.getForm().findField("id").setValue(ids[0]);
		form.getForm().findField("workunit_id").setRawValue(record.get("workunit_name"));
		
		var win=new Ext.window.Window({
			items:[form],
			layout:'fit',
			closeAction:'destroy',
			width:300,
			height:200,
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
						url:Ext.ContextPath+'/area/destroy.do',
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
    }
});
