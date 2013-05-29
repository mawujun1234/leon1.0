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
    initComponent: function () {
		var me = this;
		
		
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
			me.initAction();
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
			me.initAction();
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
    initAction:function(){
     	var me = this;
       var create = new Ext.Action({
		    text: '新增子节点',
		    disabled:me.disabledAction,
		    handler: function(){
		    	var parent=me.getSelectionModel( ).getLastSelected( )||me.getRootNode( );    	
		    	var values={
		    		'parent_id':parent.get("id")
		    	};
		    	values[me.displayField]='新节点';
		        var child=Ext.createModel(parent.self.getName(),values);
		        //alert(Ext.encode(child.raw));
		       //return;
		        child.save({
					success: function(record, operation) {
						//child=record;
						me.getStore().reload({node:parent});
						parent.expand();
					}
				});

		    },
		    iconCls: 'form-addChild-button'
		});
		me.addContextMenu(create);
		var createSibling = new Ext.Action({
		    text: '新增兄弟节点',
		    disabled:me.disabledAction,
		    handler: function(){
		    	var node=me.getSelectionModel( ).getLastSelected( );   
		    	if(node.isRoot()){
				       	Ext.Msg.alert("消息","不能在根节点上新增兄弟节点!");	
				       	return;
				}
		    	var parent=node.parentNode;
		    	var values={
		    		'parent_id':parent.get("id")
		    	};
		    	values[me.displayField]='新节点';
		        var child=Ext.createModel(node.self.getName(),values);
		        //alert(Ext.encode(child.raw));
		       //return;
		        child.save({
					success: function(record, operation) {
						//child=record;
						me.getStore().reload({node:parent});
						parent.expand();
					}
				});

		    },
		    iconCls: 'form-addChild-button'
		});
		me.addContextMenu(createSibling);
		var destroy = new Ext.Action({
		    text: '删除',
		    disabled:me.disabledAction,
		    handler: function(){
		    	var node=me.getSelectionModel( ).getLastSelected( );
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
//				        }
				    }
				});
		        
		    },
		    iconCls: 'form-delete-button'
		});
		me.addContextMenu(destroy);
		var update = new Ext.Action({
		    text: '修改',
		    disabled:me.disabledAction,
		    handler: function(){
		    	var node=me.getSelectionModel( ).getLastSelected( );
				if(node.isRoot( )){
				    Ext.Msg.alert("消息","根节点不能修改!");	
				    return;
				}
		    	
		        Ext.Msg.prompt('修改', '请输入名称:', function(btn, text){
				    if (btn == 'ok'){
				    	//var node=me.getSelectionModel( ).getLastSelected( );
				    	if(node.get(me.displayField )==text){
				    		return;
				    	}
				        node.set(me.displayField,text);
				        node.save({
							success: function(record, operation) {
								//child=record;
								me.getStore().reload({node:parent});
								parent.expand();
							}
						});
				    }
				});
		    },
		    iconCls: 'form-update-button'
		});
		me.addContextMenu(update);
		var copy = new Ext.Action({
		    text: '复制',
		    disabled:me.disabledAction,
		    handler: function(){
		        var node=me.getSelectionModel( ).getLastSelected( );
		        if(node.isRoot()){
				    Ext.Msg.alert("消息","不能复制根节点!");	
				    return;
				}
		        var newnode=node.copy();
		        newnode.set("id",null);
		        me.copyNode=newnode;
		        
		    },
		    iconCls: 'form-copy-button'
		});
		me.addContextMenu(copy);
		var paste = new Ext.Action({
		    text: '粘贴',
		    disabled:me.disabledAction,
		    handler: function(){
		    	var parent=me.getSelectionModel( ).getLastSelected( );
		    	if(!parent){
				    Ext.Msg.alert("消息","请选择要粘贴到的父节点!");	
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
		    iconCls: 'form-paste-button'
		});
		me.addContextMenu(paste);
		var reload = new Ext.Action({
		    text: '刷新',
		    handler: function(){
		    	var parent=me.getSelectionModel( ).getLastSelected( );
		    	if(parent){
		    		me.getStore().reload({node:parent});
		    	} else {
		    		me.getStore().reload();	
		    	}      
		    },
		    iconCls: 'form-reload-button'
		});
		me.addContextMenu(reload);
		
//		me.actions=[create,createSibling,destroy,update,copy,paste,reload];
//		//me.tbar=me.actions;
//		//me.actions.push(reload);
//		var menu=Ext.create('Ext.menu.Menu', {
//		    items: me.actions
//		});
//		me.on('itemcontextmenu',function(tree,record,item,index,e){
//			menu.showAt(e.getXY());
//			e.stopEvent();
//		});
//		
//		me.on('containercontextmenu',function(view,e){
//			menu.showAt(e.getXY());
//			e.stopEvent();
//		});
    },
    afterContextMenuShow:function(tree,record,item,index,e){

    },
    addContextMenu:function(action){
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
				me.afterContextMenuShow(tree,record,item,index,e);
			});
			
			me.on('containercontextmenu',function(view,e){
				//menu.showAt(e.getXY());
				e.stopEvent();
			});
    	} else if(!me.hasListener("itemcontextmenu")|| me.hasListener("containercontextmenu")){
    		me.on('itemcontextmenu',function(tree,record,item,index,e){
				menu.showAt(e.getXY());
				e.stopEvent();
			});
			
			me.on('containercontextmenu',function(view,e){
				menu.showAt(e.getXY());
				e.stopEvent();
			});
    	}
    	me.contextMenu.add(action);
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