/**
 * 功能的扩展，添加自定义的怎，删，改
 * 添加右键菜单，增，删，改，并且增加工具栏，增，删，改。
 * 后台的类最好继承TreeNode类，这样就可以少写很多代码
 */
Ext.define('Ems.baseinfo.EquipmentTypeTree', {
    extend: 'Ext.tree.Panel',
    requires:['Ems.baseinfo.EquipmentType','Ems.baseinfo.EquipmentTypeForm'],
    initComponent: function () {
		var me = this;

        me.store = Ext.create('Ext.data.TreeStore', {
	       	autoLoad:true,
	       	nodeParam :'id',//传递到后台的数据，默认是node
	       	model:'Ems.baseinfo.EquipmentType',
			root: {
			    expanded: true,
			    levl:0,
			    text:"类型管理" 
			},
			listeners:{
				beforeload:function(store,operation){
					var node=operation.node;//me.getSelectionModel( ).getLastSelected( );
					//console.dir(operation);
					operation.params.isGrid=false;
					operation.params.levl=node?node.get("levl"):null;
				}
			}
		});
		//me.initAction();
       
		me.callParent(arguments);
    },

    
   
     /**
     * 就是action的itemId，例如有：create,rename,destroy,copy,cut,paste,reload等等
     * @param {} itemIds
     */
    disableAction:function(itemIds){
    	if(!Ext.isIterable(itemIds)){
    		itemIds=[itemIds];
    	}
    	
    	var items=this.contextMenu.items;
    	items.each(function(item ,index,len ){
    		for(var j=0;j<itemIds.length;j++){
    			if(item.getItemId( )==itemIds[j]){
    				item.disable();
    				break;
    			}
    		}
    	});
    	var tbar=this.getActionTbar();//this.getDockedItems('toolbar[itemId="action_toolbar"]')[0];
    	if(!tbar){
    		return;
    	}
    	items=tbar.items;
    	items.each(function(item ,index,len ){
    		for(var j=0;j<itemIds.length;j++){
    			if(item.getItemId( )==itemIds[j]){
    				item.disable();
    				break;
    			}
    		}
    	});
    },
    /**
     * 就是action的itemId，例如有：create,rename,destroy,copy,cut,paste,reload等等
     * @param {} itemIds
     */
    enableAction:function(itemIds){
    	if(!Ext.isIterable(itemIds)){
    		itemIds=[itemIds];
    	}
    	
    	var items=this.contextMenu.items;
    	items.each(function(item ,index,len ){
    		for(var j=0;j<itemIds.length;j++){
    			if(item.getItemId( )==itemIds[j]){
    				item.disable();
    				break;
    			}
    		}
    	});
    	var tbar=this.getActionTbar();
    	items=tbar.items;
    	items.each(function(item ,index,len ){
    		for(var j=0;j<itemIds.length;j++){
    			if(item.getItemId( )==itemIds[j]){
    				item.disable();
    				break;
    			}
    		}
    	});
    },
    getContextMenu:function(){
    	return this.contextMenu;
    },
    actionTbar:null,
    getActionTbar:function(){
    	if(!this.actionTbar){
    		this.actionTbar=this.getDockedItems('toolbar[itemId="action_toolbar"]')[0];
    	}
    	
    	return this.actionTbar
    },
//    getContextMenuItems:function(){
//    	return this.contextMenu.items;
//    },
    getLastSelected:function(){
    	return this.getSelectionModel( ).getLastSelected( );
    }
    
});
