<#assign simpleClassNameFirstLower = simpleClassName?uncap_first> 
<#-- //所在模块-->
<#assign module = basepackage?substring(basepackage?last_index_of(".")+1)> 
/**
 * 功能的扩展，添加自定义的怎，删，改
 * 添加右键菜单，增，删，改，并且增加工具栏，增，删，改。
 * 后台的类最好继承TreeNode类，这样就可以少写很多代码
 */
Ext.define('Leon.${module}.${simpleClassName}Tree', {
    extend: 'Ext.tree.Panel',
    requires:['Leon.${module}.${simpleClassName}'],
    model:'Leon.${module}.${simpleClassName}',
    initComponent: function () {
		var me = this;

        me.store = Ext.create('Ext.data.TreeStore', {
	       	autoLoad:true,
	       	nodeParam :'id',//传递到后台的数据，默认是node
	       	model:me.model,
			root: {
			    expanded: true,
			    text:"根节点" 
			}
		});
		me.initAction();
       
		me.callParent(arguments);
    },

    initAction:function(){
     	var me = this;
     	var actions=[];
     	
       var create = new Ext.Action({
		    text: '子节点',
		    itemId:'create',
		    disabled:me.disabledAction,
		    handler: function(b){
		    	me.onCreate(null,b);
		    },
		    iconCls: 'form-add-button'
		});
		//me.addAction(create);
		actions.push(create);
		
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
		var rename = new Ext.Action({
		    text: '重命名',
		    itemId:'rename',
		    disabled:me.disabledAction,
		    handler: function(){
		    	me.onRename();
				
		    },
		    iconCls: 'form-update-button'
		});
		//me.addAction(rename);
		actions.push(rename);
		var copy = new Ext.Action({
		    text: '复制',
		    itemId:'copy',
		    disabled:me.disabledAction,
		    handler: function(){
		       me.onCopy();
		        
		    },
		    iconCls: 'form-copy-button'
		});
		actions.push(copy);
		var cut = new Ext.Action({
		    text: '剪切',
		    itemId:'cut',
		    disabled:me.disabledAction,
		    handler: function(){
		       me.onCut();
		        
		    },
		    iconCls: 'form-cut-button'
		});
		actions.push(cut);
		var paste = new Ext.Action({
		    text: '粘贴',
		    itemId:'paste',
		    disabled:me.disabledAction,
		    handler: function(){
		    	me.onPaste();
		    },
		    iconCls: 'form-paste-button'
		});
		//me.addAction(paste);
		actions.push(paste);
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
		var menu=Ext.create('Ext.menu.Menu', {
			items: actions
		});	
		me.on('itemcontextmenu',function(tree,record,item,index,e){
			menu.showAt(e.getXY());
			e.stopEvent();
		});
		
		me.on('containercontextmenu',function(view,e){
			menu.showAt(e.getXY());
			e.stopEvent();
		});
		me.contextMenu=menu;
		
    },
    onCreate:function(values,b){
    	var me=this;

    	values=values||{};

    	var parent=me.getSelectionModel( ).getLastSelected( )||me.getRootNode( );    

		var initValue={
		    'parent_id':parent.get("id"),
		    text:'新节点'
		};

    	values=Ext.applyIf(values,initValue);

		var child=values.isModel?values:Ext.createModel(parent.self.getName(),values);

		child.save({
			success: function(record, operation) {
				parent.set('leaf',false);
				parent.appendChild(child);
				//alert(1);
//				parent.leaf=false;
//				me.getStore().reload({node:parent});
//				parent.expand();
			}
		});
    },
    onRename:function(values){
    	var me=this;
    	var node=me.getSelectionModel( ).getLastSelected( );
		if(node.isRoot( )){
			Ext.Msg.alert("消息","根节点不能修改!");	
			return;
		}
    	if(values){
    		node.set(values);
    		node.save({
				success: function(record, operation) {
					alert('成功');
				}
			});
    	} else {
    		Ext.Msg.prompt('修改', '请输入名称:', function(btn, text){
				if (btn == 'ok'){
					if(node.get(me.displayField )==text){
					    return;
					}
					node.set(me.displayField,text);
					node.save({
						success: function(record, operation) {
							alert('成功');
						}
					});
				}
			});
    	}
		    	
		
    },
    onDelete:function(node){
    	var me=this;
    	var node=node||me.getSelectionModel( ).getLastSelected( );

		if(!node){
		    Ext.Msg.alert("消息","请先选择节点");	
			return;
		}
		if(node.isRoot()){
			Ext.Msg.alert("消息","根节点不能删除!");	
			return;
		}
		if(node&&node.hasChildNodes( ) &&　!me.cascadeDelete){
			Ext.Msg.alert("消息","请先删除子节点!");
		    return;
		}
		Ext.Msg.confirm("删除",'确定要删除吗?', function(btn, text){
				if (btn == 'yes'){
				   var parent=node.parentNode;
				   node.destroy({
				        failure: function(record, operation) {
				        if(parent){
				        	var index=parent.indexOf(node);
		            		me.getStore().reload({node:parent});
		            		//parent.insertChild(index,record);
				        }
				        Ext.Msg.alert("消息","删除失败!");
		            	return;
				    }
				});
			}
		});
    },
    onCopy:function(node){
    	var me=this;
    	var node=node||me.getSelectionModel( ).getLastSelected( );
		if(node.isRoot()){
			Ext.Msg.alert("消息","不能复制根节点!");	
			return;
		}
		var newnode=node.copy();
		newnode.set("id",null);
		me.copyNode=newnode;
    },
    onCut:function(){
    	var me=this;
    	var node=node||me.getSelectionModel( ).getLastSelected( );
		if(node.isRoot()){
			Ext.Msg.alert("消息","不能复制根节点!");	
			return;
		}
		//var newnode=node.copy();
		//newnode.set("id",null);
		//me.copyNode=newnode;
		me.copyNode=node;
		me.copyNode.cut=true;
    },
    onPaste:function(parentNode){
    	var me=this;
    	var parent=parentNode||me.getSelectionModel( ).getLastSelected( );

		if(!parent){
			Ext.Msg.alert("消息","请选择要粘贴到的父节点!");	
			return;
		}
		if(!me.copyNode){
			Ext.Msg.alert("消息","请先复制/剪切节点!");	
			return;
		}
		    	
		if(!me.copyNode.cut){
		    me.copyNode.set('parent_id',parent.get("id"));
		    me.copyNode.save({
				success: function(record, operation) {
					me.getStore().reload({node:parent});
					parent.expand();
				}
			});
		} else {
			//alert(1);
			var orginalParent=me.copyNode.parentNode;
			if(orginalParent==null){
				orginalParent=me.getRootNode();
				//me.getRootNode().removeChild(me.copyNode,false);
			} else {
				//me.copyNode.parentNode.removeChild(me.copyNode,false);
				//orginalParent=
			}
			
			me.copyNode.set('parent_id',parent.get("id"));
			me.copyNode.setParent(parent);
			
			if(me.copyNode.hasChildNodes( ) ){
				me.copyNode.removeAll();
				me.copyNode.collapse();
				me.copyNode.leaf=false;
			}
			orginalParent.removeChild(me.copyNode,false);
			parent.appendChild(me.copyNode);
			me.copyNode.save({
				params:{isUpdateParent:true,oldParent_id:orginalParent.get("id")},
				success: function(record, operation) {
					me.getStore().reload({node:parent,success:function(){
					//	me.getStore().reload({node:orginalParent});
					}});
				}
			});
			delete me.copyNode.cut;
		}
    },
    onReload:function(node){
    	var me=this;
    	var parent=node||me.getSelectionModel( ).getLastSelected( );
		if(parent){
		    me.getStore().reload({node:parent});
		} else {
		    me.getStore().reload();	
		}      
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
