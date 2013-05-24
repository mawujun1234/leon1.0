/**
 * 功能的扩展，添加自定义的怎，删，改
 * 添加右键菜单，增，删，改，并且增加工具栏，增，删，改。
 * 后台的类最好继承TreeNode类，这样就可以少写很多代码
 */
Ext.define('Leon.common.ux.BaseTree', {
    extend: 'Ext.tree.Panel',
    alias: 'widget.btreepanel',
    cascadeDelete:false,//在删除的时候是否级联删除节点
    textField:'text',//传递到后台的时候，名称的默认树形
    disabledAction:false,//讲动作都禁止掉，不可使用
    model:null,//用来构建store，如果没有这个值，就得自己构建model
    defaultRootText :'根节点',
    fields:null,
    url:null,//url和fields是同时出现，但和model属性是不能同时出现的
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
					type:'bajax',
					url:me.url
				}
				
			};
			if(me.fields){
				cofig.fields=me.fields;
				me.fields.length=0;
			}
        	me.store=Ext.create('Ext.data.TreeStore',cofig);
			
        }

		me.callParent();
    },
    initAction:function(){
     	var me = this;
       var create = new Ext.Action({
		    text: '新增子节点',
		    disabled:me.disabledAction,
		    handler: function(){
		    	var parent=me.getSelectionModel( ).getLastSelected( );    	
		    	var values={
		    		'parent_id':parent.get("id")
		    	};
		    	values[me.textField]='新节点';
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
		var createSibling = new Ext.Action({
		    text: '新增兄弟节点',
		    disabled:me.disabledAction,
		    handler: function(){
		    	var node=me.getSelectionModel( ).getLastSelected( );   
		    	if(node.getId()==me.getRootNode().getId()){
		    		return;
		    	}
		    	var parent=node.parentNode;
		    	var values={
		    		'parent_id':parent.get("id")
		    	};
		    	values[me.textField]='新节点';
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
		var destroy = new Ext.Action({
		    text: '删除',
		    disabled:me.disabledAction,
		    handler: function(){
		    	Ext.Msg.confirm("删除",'确定要删除吗?', function(btn, text){
				    if (btn == 'yes'){
				       var node=me.getSelectionModel( ).getLastSelected( );
				       if(node.getId()==me.getRootNode().getId()){
				       	Ext.Msg.alert("消息","根节点不能删除!");	
				       	return;
				       }
				       var parent=node.parentNode;
				        if(node&&node.hasChildNodes( ) &&　!me.cascadeDelete){
				        	Ext.Msg.alert("消息","请先删除子节点!");
		            		return;
				        }else if(node){
				        	node.destroy({
//				        		success:function(){
//				        			Ext.Msg.alert("消息","删除成功!");
//		            				return;
//				        		},
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
				    }
				});
		        
		    },
		    iconCls: 'form-delete-button'
		});
		var update = new Ext.Action({
		    text: '修改',
		    disabled:me.disabledAction,
		    handler: function(){
		    	var node=me.getSelectionModel( ).getLastSelected( );
				if(node.getId()==me.getRootNode().getId()){
				    Ext.Msg.alert("消息","根节点不能修改!");	
				    return;
				}
		    	
		        Ext.Msg.prompt('修改', '请输入名称:', function(btn, text){
				    if (btn == 'ok'){
				    	//var node=me.getSelectionModel( ).getLastSelected( );
				    	if(node.get(me.textField)==text){
				    		return;
				    	}
				        node.set(me.textField,text);
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
		var copy = new Ext.Action({
		    text: '复制',
		    disabled:me.disabledAction,
		    handler: function(){
		        var node=me.getSelectionModel( ).getLastSelected( );
		        var newnode=node.copy();
		        newnode.set("id",null);
		        me.copyNode=newnode;
		        
		    },
		    iconCls: 'form-copy-button'
		});
		var paste = new Ext.Action({
		    text: '粘贴',
		    disabled:me.disabledAction,
		    handler: function(){
		    	var parent=me.getSelectionModel( ).getLastSelected( );
		    	
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
		var reload = new Ext.Action({
		    text: '重新加载',
		    handler: function(){
		    	var parent=me.getSelectionModel( ).getLastSelected( );
		    	
		        me.getStore().reload({node:parent});
		    },
		    iconCls: 'form-reload-button'
		});
		me.actions=[create,createSibling,destroy,update,copy,paste,reload];
		//me.tbar=me.actions;
		//me.actions.push(reload);
		var menu=Ext.create('Ext.menu.Menu', {
		    items: me.actions
		});
		me.on('itemcontextmenu',function(tree,record,item,index,e){
			menu.showAt(e.getXY());
			e.stopEvent();
		});
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
    	this.getSelectionModel( ).getLastSelected( );
    }
    
});