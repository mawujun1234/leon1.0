Ext.define('Ems.task.HitchReasonTplGrid',{
	extend:'Ext.grid.Panel',
	requires: [
	     'Ems.task.HitchReasonTpl',
	     'Ems.task.HitchReasonTplForm'
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
		{dataIndex:'id',text:'编号',xtype: 'numbercolumn', format:'0',width:60},
		{dataIndex:'name',text:'名称'},
		{dataIndex:'tpl',text:'模板内容',flex:1}
      ];
      
	  me.store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			model: 'Ems.task.HitchReasonTpl',
			autoLoad:false
	  });
	  
//      me.dockedItems= [{
//	        xtype: 'pagingtoolbar',
//	        store: me.store,  
//	        dock: 'bottom',
//	        
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
		    hidden:true,
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
if(!me.hitchType_id){
	alert("请先选择一个故障类型");
	return;
}
		var form=new Ems.task.HitchReasonTplForm({
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
		data.hitchType_id=me.hitchType_id;
		var record=new Ems.task.HitchReasonTpl(data);    
		form.getForm().loadRecord(record);
		var win=new Ext.window.Window({
			items:[form],
			layout:'fit',
			title:'原因模板',
			closeAction:'destroy',
			width:300,
			height:400,
			modal:true
		});
		//form.win=win
		win.show();

    },
    onUpdate:function(){
    	var me=this;
    	
    	var record=me.getSelectionModel( ).getLastSelected( );
		if(!record){
			Ext.Msg.alert("消息","请先选择一条记录!");	
			return;
		}
		
		var form=new Ems.task.HitchReasonTplForm({
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
		var workunit=Ext.create('Ems.task.HitchReasonTpl',{

		});
		
		var win=new Ext.window.Window({
			items:[form],
			layout:'fit',
			title:'原因模板',
			closeAction:'destroy',
			width:300,
			height:400,
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
						url:Ext.ContextPath+'/hitchReasonTpl/deleteById.do',
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
