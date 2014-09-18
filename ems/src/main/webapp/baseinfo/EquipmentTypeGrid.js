Ext.define('Ems.baseinfo.EquipmentTypeGrid',{
	extend:'Ext.grid.Panel',
	requires: [
	     'Ems.baseinfo.EquipmentType'
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
		{dataIndex:'id',text:'编码',width:150
//		,renderer:function(value){
//			var values=value.split("_");
//			return values[0];
//		}
		},
		//{dataIndex:'level',text:'level',xtype: 'numbercolumn', format:'0.00'},
		//{dataIndex:'status',text:'status',xtype: 'numbercolumn', format:'0.00'},
		{dataIndex:'name',text:'名称',flex:1},
		{dataIndex:'unit',text:'单位',flex:1},
		{dataIndex:'status',text:'状态',renderer:function(value){
			if(value){
				return "有效";
			} else {
				return "<span style='color:red'>无效</>";
			}
		}}
      ];
      
	  me.store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			model: 'Ems.baseinfo.EquipmentType',
			autoLoad:false
	  });
	  me.store.getProxy().extraParams ={isGrid:true}
	  
//	  me.tbar=	[{
//			text: '刷新',
//			itemId:'reload',
//			disabled:me.disabledAction,
//			handler: function(btn){
//				var grid=btn.up("grid");
//				grid.getStore().reload();
//			},
//			iconCls: 'form-reload-button'
//		}]
       
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
    onCreate:function(values,b){
    	var me=this;

    	values=values||{};

    	var parent=me.tree.getSelectionModel( ).getLastSelected( )||me.tree.getRootNode( );    

    	var parent_id=parent.get("id");
    	if(parent_id!="root"){
    		parent_id=parent_id;//.split("_")[0];
    	}
		var initValue={
		    'parent_id':parent_id,
		    status:1,
		    text:''
		};
		//if(parent.get("levl")){
			initValue.levl=parent.get("levl")+1;
		//}
			if(initValue.levl==2){
				
			}

    	values=Ext.applyIf(values,initValue);

		var child=values.isModel?values:Ext.createModel(parent.self.getName(),values);
		var form=new Ems.baseinfo.EquipmentTypeForm({
			url:Ext.ContextPath+"/equipmentType/create.do",
			isType:initValue.levl==1?true:false,
			listeners:{
				saved:function(){
					win.close();
					me.getStore().reload();
					//me.tree.getStore().reload({node:parent});
				}
			}
		});
		form.getForm().loadRecord(child);
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
    	var record=me.getSelectionModel().getLastSelected( );
		if(!record){
			Ext.Msg.alert("消息","请先选择类型!");	
			return;
		}
		
		var form=new Ems.baseinfo.EquipmentTypeForm({
			url:Ext.ContextPath+"/equipmentType/update.do",
			isType:record.get("levl")==1?true:false,
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
			height:200,
			modal:true
		});
		//form.win=win
		win.show();	
		    	
		
    },
    onDelete:function(){
    	var me=this;
    	var record=me.getSelectionModel( ).getLastSelected( );

    	//console.dir(record);
		if(!record){
		    Ext.Msg.alert("消息","请先选择一条记录");	
			return;
		}
		//return;

		Ext.Msg.confirm("删除",'确定要删除吗?', function(btn, text){
				if (btn == 'yes'){
					Ext.Ajax.request({
						url:Ext.ContextPath+'/equipmentType/destroy.do',
						params:{
							id:record.get("id"),
							levl:record.get("levl")
						},
						method:'POST',
						success:function(){
							//var parent=me.tree.getSelectionModel( ).getLastSelected( )||me.tree.getRootNode( );  
							//me.tree.getStore().reload({node:parent});
							
							me.getStore().remove(record);
							//me.select(0);
							
							//record.set("status_name","无效");
							//record.set("status",false);
						}
					});
			}
		});
    },
    onReload:function(node){
    	var me=this;
    	var parent=node||me.getSelectionModel( ).getLastSelected( );
		if(parent){
		    me.getStore().reload({node:parent});
		} else {
		    me.getStore().reload();	
		}      
    }
});
