/**
 * 封装grid
 */
Ext.define('Leon.common.ux.BaseGrid', {
    extend: 'Ext.tree.Panel',
    alias: 'widget.bgridpanel',
    columnLines:true,
    viewConfig: {
        stripeRows: false
    },
    initComponent: function () {
       var me = this;
       var create = new Ext.Action({
		    text: '新增',
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
		    text: '保存',
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
		
		me.actions=[create,destroy,update];
		me.tbar=me.actions;
		me.tbar.push(reload);
		
		var menu=Ext.create('Ext.menu.Menu', {
		    //width: 100,
		    //plain: true,
		    //floating: false,  // usually you want this set to True (default)
		    //renderTo: Ext.getBody(),  // usually rendered by it's containing component
		    items: [create,destroy,update,copy,paste,reload]
		});
		me.on('itemcontextmenu',function(tree,record,item,index,e){
			menu.showAt(e.getXY());
			e.stopEvent();
		});
        me.callParent();
    }
});