/**
 * 功能的扩展，添加自定义的怎，删，改
 * 添加右键菜单，增，删，改，并且增加工具栏，增，删，改。
 * 后台的类最好继承TreeNode类，这样就可以少写很多代码
 */
Ext.define('Ems.baseinfo.EquipmentTypeTree', {
    extend: 'Ext.tree.Panel',
    requires:['Ems.baseinfo.EquipmentType','Ems.baseinfo.EquipmentTypeForm'],
    viewConfig: {
	    getRowClass: function(record, rowIndex, rowParams, store){
	        return record.get("status")==true ? "" : "status_disable";
	    }
    },
    initComponent: function () {
		var me = this;

        me.store = Ext.create('Ext.data.TreeStore', {
	       	autoLoad:true,
	       	nodeParam :'id',//传递到后台的数据，默认是node
	       	model:'Ems.baseinfo.EquipmentType',
			root: {
			    expanded: true,
			    levl:0,
			    status:true,
			    text:"类型管理" 
			},
			listeners:{
				beforeload:function(store,operation){
					var node=operation.node;//me.getSelectionModel( ).getLastSelected( );
					//console.dir(operation);
					//operation.params.isGrid=false;
					//operation.params.status=true;
					//operation.params.levl=node?node.get("levl"):null;
					store.getProxy().extraParams=Ext.apply(store.getProxy().extraParams,{
						isGrid:false,
						levl:node?node.get("levl"):null
					});
				}
			}
		});
		me.initAction();
       
		me.callParent(arguments);
    },

    
//   
//     /**
//     * 就是action的itemId，例如有：create,rename,destroy,copy,cut,paste,reload等等
//     * @param {} itemIds
//     */
//    disableAction:function(itemIds){
//    	if(!Ext.isIterable(itemIds)){
//    		itemIds=[itemIds];
//    	}
//    	
//    	var items=this.contextMenu.items;
//    	items.each(function(item ,index,len ){
//    		for(var j=0;j<itemIds.length;j++){
//    			if(item.getItemId( )==itemIds[j]){
//    				item.disable();
//    				break;
//    			}
//    		}
//    	});
//    	var tbar=this.getActionTbar();//this.getDockedItems('toolbar[itemId="action_toolbar"]')[0];
//    	if(!tbar){
//    		return;
//    	}
//    	items=tbar.items;
//    	items.each(function(item ,index,len ){
//    		for(var j=0;j<itemIds.length;j++){
//    			if(item.getItemId( )==itemIds[j]){
//    				item.disable();
//    				break;
//    			}
//    		}
//    	});
//    },
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

		var checkbox=Ext.create('Ext.form.field.Checkbox',{
			boxLabel  : '只有在用',
            name      : 'status',
             checked:true,
            listeners:{
            	change:function(checkbox,newValue, oldValue){
            		//alert(newValue);
            		//me.store.getProxy().extraParams ={isGrid:false,status:newValue};
            		me.store.getProxy().extraParams.status=newValue;
            		//var parent=node||me.getSelectionModel( ).getLastSelected( );
		    		//me.getStore().reload({node:parent});
            		me.getStore().reload();
            		
            	}
            }
		});
		actions.push(checkbox);
		
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

    	var parent=me.getSelectionModel( ).getLastSelected( )||me.tree.getRootNode( );    
    	if(parent.get("levl")==2){
    		alert("小类下面不能再添加了，品名请在左边的表格里面新建!");
    		return;
    	}

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
					//me.getStore().reload();
					me.getStore().reload({node:parent});
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
							var parent=record.parentNode;//me.getSelectionModel( ).getLastSelected( )||me.getRootNode( );  
							me.getStore().reload({node:parent});
							
							//me.getStore().remove(record);
							//me.select(0);
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
//    /**
//     * 就是action的itemId，例如有：create,rename,destroy,copy,cut,paste,reload等等
//     * @param {} itemIds
//     */
//    enableAction:function(itemIds){
//    	if(!Ext.isIterable(itemIds)){
//    		itemIds=[itemIds];
//    	}
//    	
//    	var items=this.contextMenu.items;
//    	items.each(function(item ,index,len ){
//    		for(var j=0;j<itemIds.length;j++){
//    			if(item.getItemId( )==itemIds[j]){
//    				item.disable();
//    				break;
//    			}
//    		}
//    	});
//    	var tbar=this.getActionTbar();
//    	items=tbar.items;
//    	items.each(function(item ,index,len ){
//    		for(var j=0;j<itemIds.length;j++){
//    			if(item.getItemId( )==itemIds[j]){
//    				item.disable();
//    				break;
//    			}
//    		}
//    	});
//    },
//    getContextMenu:function(){
//    	return this.contextMenu;
//    },
//    actionTbar:null,
//    getActionTbar:function(){
//    	if(!this.actionTbar){
//    		this.actionTbar=this.getDockedItems('toolbar[itemId="action_toolbar"]')[0];
//    	}
//    	
//    	return this.actionTbar
//    },
////    getContextMenuItems:function(){
////    	return this.contextMenu.items;
////    },
//    getLastSelected:function(){
//    	return this.getSelectionModel( ).getLastSelected( );
//    }
    
});
