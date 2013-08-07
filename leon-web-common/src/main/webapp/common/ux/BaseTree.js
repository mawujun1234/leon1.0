/**
 * 	var tree=Ext.create('Leon.common.ux.BaseTree',{
		title:'菜单树',
		//model:'ddddddd',
		fields:['id','text','discriminator'],
		//url:'/constantType/queryChildren',
		api:{
			read:'/constantType/queryChildren',
			load : '/constantType/get',
			create:'/constantType/create',
			update:'/constantType/update',
			destroy:'/constantType/destroy'
		},
		region:'west',
		split:true,
		width:500
	});
 * 功能的扩展，添加自定义的怎，删，改
 * 添加右键菜单，增，删，改，并且增加工具栏，增，删，改。
 * 后台的类最好继承TreeNode类，这样就可以少写很多代码
 */
Ext.define('Leon.common.ux.BaseTree', {
    extend: 'Ext.tree.Panel',
    alias: 'widget.btreepanel',
    cascadeDelete:false,//在删除的时候是否级联删除节点
    displayField :'text',//传递到后台的时候，名称的默认树形
    //rootVisible:true,
    disabledAction:false,//讲动作都禁止掉，不可使用
    model:null,//用来构建store，如果没有这个值，就得自己构建model
    defaultRootText :'根节点',
    defaultRootName :'root',
    fields:null,//和api，url搭配的，也可以没有
    url:null,//url和fields是同时出现，但和model属性是不能同时出现的
    autoLoadData:true,//自动加载数据
    autoRootExpanded:true,
    api:null,
    autoShowSimpleActionToTbar:true,//是否把按钮添加到工具栏
    autoInitSimpleAction:true,//初始化增，删改等按钮
    initComponent: function () {
		var me = this;
		//me.callParent();
		if(me.model){
        	me.store = Ext.create('Ext.data.TreeStore', {
	       		autoLoad:me.autoLoadData,
	       		nodeParam :'id',
	       		model:me.model,
			    root: {
			    	//id:'root',
			        expanded: me.autoRootExpanded,
			        text:me.defaultRootText 
			    }
			});
			me.initSimpleAction();
        } else if(me.url){

        	var cofig={
        		root: {
			    	//id:'root',
			        expanded: me.autoRootExpanded,
			        text:me.defaultRootText 
			    },
			    nodeParam :'id',
        		autoLoad:me.autoLoadData,
				proxy:{
					type:'ajax',
					url:me.url
					,reader:{//因为树会自己生成model，这个时候就有这个问题，不加就解析不了，可以通过   动态生成 模型，而不是通过树默认实现，哪应该就没有问题
							type:'json',
							root:me.defaultRootName,
							successProperty:'success',
							totalProperty:'total'	
					}
					,writer:{
						type:'json'
					}
				}
				
			};
			if(me.fields){
				cofig.fields=me.fields;
				delete  me.fields;
			}
        	me.store=Ext.create('Ext.data.TreeStore',cofig);
        	 
			
        } else if(me.api){
        	var fields=me.fields;
        	if(!fields){
        		fields=['id','text'];
        	} else {
        		me.fields.length=0;
        	}
        	
        	var model=me.dynamicModel('Tree.TempleModel'+Ext.id(),fields,me.api);
        	me.store = Ext.create('Ext.data.TreeStore', {
	       		autoLoad:me.autoLoadData,
	       		nodeParam :'id',
	       		model:model,
			    root: {
			    	//id:'root',
			        expanded: me.autoRootExpanded,
			        text:me.defaultRootText 
			    }
			});
			me.initSimpleAction();
        }
        
        this.on('beforeitemremove',function(nodeinter,node){
        	if(node.isRoot( ) ){
        		Ext.Msg.alert("消息","根节点不能删除!");
        		return false;
        	}
        });
       
        me.addEvents(
        	/**
        	 * @param parentNode 父节点
        	 * @param values oncreate方法的参数
        	 */
        	'beforeCreate',
        	/**
        	 * @param node 当前节点
        	 * @param values oncreate方法的参数
        	 */
        	'beforeDelete',
        	/**
        	 * @param node 当前节点
        	 * @param values oncreate方法的参数
        	 */
        	'beforeRename',
        	/**
        	 * @param node 当前节点
        	 */
        	'beforeCopy',
        	/**
        	 * @param node 当前节点
        	 */
        	'beforeCut',
        	/**
        	 * @param parentNode 新的父节点
        	 * @param copyNode 被复制的节点
        	 */
        	'beforePaste'
        );
		me.callParent();
    },
    dynamicModel:function(name, fields,api) {
    	//http://www.sencha.com/forum/showthread.php?136362-Extjs-4-Dynamic-Model
	    return Ext.define(name, {
	        extend: 'Ext.data.Model',
	        fields: fields
	        ,proxy:{
				type:'bajax',
				api:api
			}
		});
	},
    initSimpleAction:function(){
    	if(!this.autoInitSimpleAction){
    		return ;
    	}
     	var me = this;
     	var actions=[];
     	
       var create = new Ext.Action({
		    text: '子节点',
		    itemId:'create',
		    disabled:me.disabledAction,
		    handler: function(){
		    	me.onCreate();
		    },
		    iconCls: 'form-add-button'
		});
		//me.addAction(create);
		actions.push(create);
		
//		var createSibling = new Ext.Action({
//		    text: '新增兄弟节点',
//		    disabled:me.disabledAction,
//		    handler: function(){
//		    	var node=me.getSelectionModel( ).getLastSelected( );   
//		    	if(node.isRoot()){
//				       	Ext.Msg.alert("消息","不能在根节点上新增兄弟节点!");	
//				       	return;
//				}
//		    	var parent=node.parentNode;
//		    	var values={
//		    		'parent_id':parent.get("id")
//		    	};
//		    	values[me.displayField]='新节点';
//		        var child=Ext.createModel(node.self.getName(),values);
//
//		        child.save({
//					success: function(record, operation) {s
//						me.getStore().reload({node:parent});
//						parent.expand();
//					}
//				});
//
//		    },
//		    iconCls: 'form-addChild-button'
//		});
//		me.addAction(createSibling);
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
		
		if(me.autoShowSimpleActionToTbar){
			me.tbar={
				itemId:'action_toolbar',
				items:actions
				,autoScroll:true
			};
		}
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
    onCreate:function(values){
    	var me=this;
    	
    	//alert(arguments.length);
    	//alert(arguments[0].menu_id);
    	//alert(values.menu_id);
    	values=values||{};
//    	alert(me.getSelectionModel( ).getLastSelected( ));
//    	return;
    	var parent=me.getSelectionModel( ).getLastSelected( )||me.getRootNode( );    
    	if(me.fireEvent('beforeCreate',parent,values)===false){
    		return;
    	}
		var initValue={
		    'parent_id':parent.get("id")
		};
    	initValue[me.displayField]='新节点';

    	values=Ext.applyIf(values,initValue);//alert(values['parent_id']);
		//values[me.displayField]='新节点';
		var child=values.isModel?values:Ext.createModel(parent.self.getName(),values);
		//alert(values.menu_id);
		child.save({
			success: function(record, operation) {
						// child=record;
				me.getStore().reload({node:parent});
				parent.expand();
			}
		});
    },
    onRename:function(values){
    	var me=this;
    	var node=me.getSelectionModel( ).getLastSelected( );
    	if(me.fireEvent('beforeRename',parent,values)===false){
    		return;
    	}
//		if(node.isRoot( )){
//			Ext.Msg.alert("消息","根节点不能修改!");	
//			return;
//		}
    	if(values){
    		node.set(values);
    		node.save({
//				success: function(record, operation) {
//									//child=record;
//							//me.getStore().reload({node:parent});
//							//parent.expand();
//				}
			});
    	} else {
    		Ext.Msg.prompt('修改', '请输入名称:', function(btn, text){
				if (btn == 'ok'){
					    	//var node=me.getSelectionModel( ).getLastSelected( );
					if(node.get(me.displayField )==text){
					    return;
					}
					node.set(me.displayField,text);
					node.save({
//						success: function(record, operation) {
//									//child=record;
//							//me.getStore().reload({node:parent});
//							//parent.expand();
//						}
					});
				}
			});
    	}
		    	
		
    },
    onDelete:function(node){
    	var me=this;
    	var node=node||me.getSelectionModel( ).getLastSelected( );
    	if(me.fireEvent('beforeDelete',node)===false){
    		return;
    	}
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
    	if(me.fireEvent('beforeCopy',node)===false){
    		return;
    	}
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
    	if(me.fireEvent('beforeCut',node)===false){
    		return;
    	}
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
    	if(me.fireEvent('beforePaste',parent,me.copyNode)===false){
    		return;
    	}
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
//    onAfterContextMenuShow:function(tree,record,item,index,e){
//
//    },
   // actions:new Ext.util.MixedCollection(),
    addAction:function(action,index){
//    	if(!this.rendered){
//    		return;
//    	}
    	if(!action){
    		return;
    	}
    	var me=this;
    	var menu=me.contextMenu;
    	if(!menu){
    		me.contextMenu=Ext.create('Ext.menu.Menu', {
			    items:[]
			});
			menu=me.contextMenu;
			me.on('itemcontextmenu',function(tree,record,item,index,e){
				menu.showAt(e.getXY());
				e.stopEvent();
				//me.onAfterContextMenuShow(tree,record,item,index,e);
			});
			
			me.on('containercontextmenu',function(view,e){
				//menu.showAt(e.getXY());
				e.stopEvent();
			});
    	} 

    	if(index==0 || index){//alert(index);
    		me.contextMenu.insert(index,action);
    		me.contextMenu.doLayout();
    		//actions.insert(index,action);
    	} else {
    		me.contextMenu.add(action);
    		//actions.add(action);
    	}
    	
    	if(me.autoShowSimpleActionToTbar){//alert(me.tbar);
    		var tbar=me.getActionTbar();
    		if(tbar){		
    			if(index==0 || index){
		    		tbar.insert(index,action);
		    	} else {//alert(me.tbar.items.getCount());
		    		tbar.add(action);
		    	}
		    	tbar.doComponentLayout();
    		} 
			
		}
    },
    removeActionAt:function(index){
    	this.contextMenu.items.removeAt(index);
    	var tbar=this.getActionTbar();
    	tbar[0].items.removeAt(index);
    },
    /**
     * 就是action的itemId，例如有：create,rename,destroy,copy,cut,paste,reload等等
     * @param {} itemIds
     */
    removeAction:function(itemIds){
    	if(!Ext.isIterable(itemIds)){
    		itemIds=[itemIds];
    	}
    	
    	var items=this.contextMenu.items;
    	items.each(function(item ,index,len ){
    		for(var j=0;j<itemIds.length;j++){
    			if(item.getItemId( )==itemIds[j]){
    				items.removeAt(index);
    				break;
    			}
    		}
    	});
    	var tbar=this.getActionTbar();
    	items=tbar.items;
    	items.each(function(item ,index,len ){
    		for(var j=0;j<itemIds.length;j++){
    			if(item.getItemId( )==itemIds[j]){
    				items.removeAt(index);
    				break;
    			}
    		}
    	});
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
    getContextMenuItems:function(){
    	return this.contextMenu.items;
    },
    getLastSelected:function(){
    	return this.getSelectionModel( ).getLastSelected( );
    }
    
});