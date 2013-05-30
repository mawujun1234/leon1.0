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
    disabledAction:false,//讲动作都禁止掉，不可使用
    model:null,//用来构建store，如果没有这个值，就得自己构建model
    defaultRootText :'根节点',
    fields:null,//和api，url搭配的，也可以没有
    url:null,//url和fields是同时出现，但和model属性是不能同时出现的
    api:null,
    autoShowSimpleActionToTbar:true,
    initComponent: function () {
		var me = this;
		//me.callParent();
		
		if(me.model){
        	me.store = Ext.create('Ext.data.TreeStore', {
	       		autoLoad:true,
	       		nodeParam :'id',
	       		model:me.model,
			    root: {
			    	//id:'root',
			        expanded: true,
			        text:me.defaultRootText 
			    }
			});
			me.initSimpleAction();
        } else if(me.url){
//        	if(!me.fields){
//        		Ext.Msg.alert("消息","请配置fields属性");
//        	}
        	//alert(me.fields);
        	var cofig={
        		root: {
			    	//id:'root',
			        expanded: true,
			        text:me.defaultRootText 
			    },
			    nodeParam :'id',
        		autoLoad:true,
				proxy:{
					type:'ajax',
					url:me.url
					,reader:{//因为树会自己生成model，这个时候就有这个问题，不加就解析不了，可以通过   动态生成 模型，而不是通过树默认实现，哪应该就没有问题
							type:'json',
							root:'root',
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
        	
        	var model=me.dynamicModel('Tree.TempleModelaaa',fields,me.api);
        	me.store = Ext.create('Ext.data.TreeStore', {
	       		autoLoad:true,
	       		nodeParam :'id',
	       		model:model,
			    root: {
			    	//id:'root',
			        expanded: true,
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
     	var me = this;
     	var actions=[];
       var create = new Ext.Action({
		    text: '新增子节点',
		    disabled:me.disabledAction,
		    handler: function(){
		    	var values={
		    		'parent_id':parent.get("id")
				};
				values[me.displayField]='新节点';
		    	me.onCreate(values);

		    },
		    iconCls: 'form-addChild-button'
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
		    disabled:me.disabledAction,
		    handler: function(){
		    	me.onDestroy();    
		    },
		    iconCls: 'form-delete-button'
		});
		//me.addAction(destroy);
		actions.push(destroy)
		var update = new Ext.Action({
		    text: '修改',
		    disabled:me.disabledAction,
		    handler: function(){
		    	me.onUpdate();
				
		    },
		    iconCls: 'form-update-button'
		});
		//me.addAction(update);
		actions.push(update);
		var copy = new Ext.Action({
		    text: '复制',
		    disabled:me.disabledAction,
		    handler: function(){
		       me.onCopy();
		        
		    },
		    iconCls: 'form-copy-button'
		});
		//me.addAction(copy);
		actions.push(copy);
		var paste = new Ext.Action({
		    text: '粘贴',
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
		    handler: function(){
		    	me.onReload();
		    },
		    iconCls: 'form-reload-button'
		});
		//me.addAction(reload);
		actions.push(reload);
		
		if(me.autoShowSimpleActionToTbar){
			me.tbar=actions;
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
    	var parent=me.getSelectionModel( ).getLastSelected( )||me.getRootNode( );    	
//		var values={
//		    'parent_id':parent.get("id")
//		};
    	//values=values||{'parent_id':parent.get("id")};
    	values=Ext.applyIf(values,{'parent_id':parent.get("id")});
		//values[me.displayField]='新节点';
		var child=values.isModel?values:Ext.createModel(parent.self.getName(),values);
		child.save({
			success: function(record, operation) {
						// child=record;
				me.getStore().reload({node:parent});
				parent.expand();
			}
		});
    },
    onUpdate:function(values){
    	var me=this;
    	var node=me.getSelectionModel( ).getLastSelected( );
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
    onDestroy:function(node){
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
    onPaste:function(parentNode){
    	var parent=parent||me.getSelectionModel( ).getLastSelected( );
		if(!parent){
			Ext.Msg.alert("消息","请选择要粘贴到的父节点!");	
			return;
		}
		if(!me.copyNode){
			Ext.Msg.alert("消息","请先复制节点!");	
			return;
		}
		    	
		if(me.copyNode){
		    me.copyNode.set('parent_id',parent.get("id"));
		    me.copyNode.save({
			success: function(record, operation) {
				me.getStore().reload({node:parent});
				parent.expand();
				}
			});
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
    onAfterContextMenuShow:function(tree,record,item,index,e){

    },
    actions:new Ext.util.MixedCollection(),
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
				me.onAfterContextMenuShow(tree,record,item,index,e);
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
    		var tbar=me.getDockedItems('toolbar[dock="top"]')[0];
    		if(tbar){		
    			if(index==0 || index){
		    		tbar.insert(index,action);
		    	} else {//alert(me.tbar.items.getCount());
		    		tbar.add(action);
		    	}
		    	tbar.doComponentLayout();
    		} 
    		
//    		var tbar=me.getDockedItems('toolbar[dock="top"]');
//    		//alert(1);
//    		if(!tbar || tbar.length==0){
//    			//me.tbar=Ext.create('Ext.toolbar.Toolbar');
//    			me.addDocked({
//			        xtype: 'toolbar',
//			        dock: 'top',
//			        items: []
//			    });
//    			tbar=me.getDockedItems('toolbar[dock="top"]');
//    		}
//    		if(index){
//	    		tbar[0].insert(index,action);
//	    	} else {
//	    		tbar[0].add(action);
//	    	}
			
		}
    },
    removeAction:function(index){
    	this.contextMenu.items.removeAt(index);
    	var tbar=this.getDockedItems('toolbar[dock="top"]');
    	tbar[0].items.removeAt(index);
    },
    getContextMenu:function(){
    	return this.contextMenu;
    },
    getContextMenuItems:function(){
    	return this.contextMenu.items;
    },
    setDisableAction:function(boool){
    	//让所有的菜单都不能使用
    	var me=this,actions=me.actions;
    	console.dir(actions);
    	for(var i=0;i<actions.length;i++){
    		actions[i].setDisabled(boool);
    	} 	
    },
    getLastSelected:function(){
    	return this.getSelectionModel( ).getLastSelected( );
    }
    
});