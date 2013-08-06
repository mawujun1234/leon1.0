Ext.require('Leon.desktop.constant.Constant');
Ext.require('Leon.desktop.constant.ConstantType');
Ext.require('Leon.desktop.constant.ConstantItem');
Ext.require('Leon.common.ux.BaseGrid');
Ext.require('Leon.common.ux.BaseTree');
Ext.require('Leon.common.ux.BaseAjax');
Ext.onReady(function(){

	var tree=Ext.create('Leon.common.ux.BaseTree',{
		title:'常数管理',
		defaultRootText:'常数管理',
		//useArrows: true,
		rootVisible: false,
		fields:['id','text','discriminator'],
		url:'/constantType/queryNode',
		//region:'west',
		split:true,
		flex: 0.8,
		listeners:{		
			"beforeitemclick":function(view,record,item,index){
				tree.getStore().getProxy().extraParams={discriminator:record.get("discriminator")}
			}
		}
	});
	tree.on("itemclick",function(view,record){
		constantItemGrid.getEl().unmask();
		if(record.get("discriminator")=="ConstantType") {
			constantTypeGrid.hide();
			constantGrid.show();
			constantItemGrid.hide();
			constantGrid.getStore().getProxy().extraParams={constanType_id:record.getId()};
			constantGrid.getStore().reload();
		} else if(record.get("discriminator")=="Constant"){
			constantTypeGrid.hide();
			constantGrid.hide();
			constantItemGrid.show();
			constantItemGrid.getStore().getProxy().extraParams={constan_id:record.getId()};
			constantItemGrid.getStore().reload();
		}  else if(record.get("discriminator")=="ConstantItem"){
//			constantTypeGrid.hide();
//			constantGrid.hide();
//			constantItemGrid.show();
//			constantItemGrid.getStore().getProxy().extraParams={constan_id:record.getId()};
//			constantItemGrid.getStore().reload();
			constantItemGrid.getEl().mask();
		}else {
			constantTypeGrid.show();
			constantGrid.hide();
			constantItemGrid.hide();
			constantTypeGrid.getStore().reload();
		}
	});
	var reload = new Ext.Action({
		    text: '刷新',
		    handler: function(){
		    	tree.onReload();
		    },
		    iconCls: 'form-reload-button'
	});
	tree.addAction(reload);
	var destroy = new Ext.Action({
		    text: '删除',
		    handler: function(){
		    	var me=tree;
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
				       Ext.Ajax.request({
				       	url:'/constantType/deleteNode',
				       	params:{id:node.get('id'),discriminator:node.get('discriminator')},
				       	success:function(){
				       	
				       	},
				       	failure: function(record, operation) {
				        			if(parent){
				        				//var index=parent.indexOf(node);
		            					me.getStore().reload({node:parent});
		            					//parent.insertChild(index,record);
				        			}
				        			Ext.Msg.alert("消息","删除失败!");
		            				return;
				        }
				       })

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
		    iconCls: 'form-delete-button'
	});
	tree.addAction(destroy);
	
//	 var createConstantType = new Ext.Action({
//		text : '新增常数分类',
//		handler : function() {
//			var parent = tree.getSelectionModel().getLastSelected()|| tree.getRootNode();
//			var values = {
//				'text' : '新常数分类'
//			};
//			var child = Ext.createModel('Leon.desktop.constant.ConstantType', values);
//
//			child.save({
//				success : function(record, operation) {
//									// child=record;
//					tree.getStore().reload({
//						node : parent
//					});
//					parent.expand();
//				}
//			});
//
//		},
//		iconCls : 'form-addChild-button'
//	});
//	tree.addAction(createConstantType);
//	
//	var createConstant = new Ext.Action({
//		text : '新增常数',
//		handler : function() {
//			var parent = tree.getSelectionModel().getLastSelected()|| tree.getRootNode();
//			var values = {
//				'text' : '新常数'
//			};
//			var child = Ext.createModel('Leon.desktop.constant.Constant', values);
//			child.setConstantType(parent);
//			child.save({
//				success : function(record, operation) {
//									// child=record;
//					tree.getStore().reload({
//						node : parent
//					});
//					parent.expand();
//				}
//			});
//
//		},
//		iconCls : 'form-addChild-button'
//	});
//	tree.addAction(createConstant);
//	var createConstantItem = new Ext.Action({
//		text : '新增常数项',
//		handler : function() {
//			var parent = tree.getSelectionModel().getLastSelected()|| tree.getRootNode();
//			var values = {
//				'text' : '新常数项'
//			};
//			var child = Ext.createModel('Leon.desktop.constant.ConstantItem', values);
//			child.setConstant(parent);
//			child.save({
//				success : function(record, operation) {
//									// child=record;
//					tree.getStore().reload({
//						node : parent
//					});
//					parent.expand();
//				}
//			});
//
//		},
//		iconCls : 'form-addChild-button'
//	});
//	tree.addAction(createConstantItem);
//	tree.onAfterContextMenuShow=function(tree,record,item,index,e){
//		if(record.get("discriminator")=="ConstantType") {
//			createConstantType.disable();
//			createConstant.enable();
//			createConstantItem.disable();
//		} else if(record.get("discriminator")=="Constant"){
//			createConstantType.disable();
//			createConstant.disable();
//			createConstantItem.enable();
//		} else if(record.get("discriminator")=="ConstantItem"){
//			createConstantType.disable();
//			createConstant.disable();
//			createConstantItem.enable();
//		} else {
//			createConstantType.enable();
//			createConstant.disable();
//			createConstantItem.disable();
//		}
//	};

	var constantTypeGrid=Ext.create('Leon.common.ux.BaseGrid',{
		model:'Leon.desktop.constant.ConstantType',
		autoSync:false,
		autoLoadData:false,
		//autoInitAction:false,
		flex: 1,
		title:'常数分类',
		columns:[{dataIndex:'text',text:'名称',editor: {
	                xtype: 'textfield',
	                allowBlank: false,
	                selectOnFocus:true
	            }},{dataIndex:'remark',text:'备注',flex:1,editor: {
	                xtype: 'textfield',
	                allowBlank: true,
	                selectOnFocus:true
	            }}
	    ]
		
	});
	var constantGrid=Ext.create('Leon.common.ux.BaseGrid',{
		model:'Leon.desktop.constant.Constant',
		autoSync:false,
		autoLoadData:false,
		//autoInitAction:false,
		flex: 1,
		//selModel:Ext.create('Ext.selection.CellModel',{}),
		hidden:true,
		title:'常数',
		columns:[{dataIndex:'code',text:'编码',editor: {
		                xtype: 'textfield',
		                allowBlank: true,
		                selectOnFocus:true
		            }},{dataIndex:'text',text:'名称',editor: {
		                xtype: 'textfield',
		                allowBlank: false,
		                selectOnFocus:true
		            }},{dataIndex:'remark',text:'备注',flex:1,editor: {
		                xtype: 'textfield',
		                allowBlank: true,
		                selectOnFocus:true
		            }}
		    	]
		
	});
	constantGrid.getStore().on('add',function(store,records,index){
		//这里不行，报错
		//records[0].set("constantType_id",tree.getLastSelected().getId());//	这种方案还要测试，还不行
		records[0].setConstantType(tree.getLastSelected().getId());
		//records[0].setConstantType(tree.getLastSelected());
	});
	
	var constantItemGrid=Ext.create('Leon.common.ux.BaseGrid',{
		model:'Leon.desktop.constant.ConstantItem',
		autoSync:false,
		autoLoadData:false,
		//autoInitAction:false,
		flex: 1,
		hidden:true,
		title:'常数项',
		columns:[{dataIndex:'code',text:'编码',editor: {
		                xtype: 'textfield',
		                allowBlank: true,
		                selectOnFocus:true
		            }},{dataIndex:'text',text:'名称',editor: {
		                xtype: 'textfield',
		                allowBlank: false,
		                selectOnFocus:true
		            }},{dataIndex:'ordering',text:'排序',editor: {
		                xtype: 'numberfield',
		                allowBlank: true,
		                selectOnFocus:true
		            }},{dataIndex:'remark',text:'备注',flex:1,editor: {
		                xtype: 'textfield',
		                allowBlank: true,
		                selectOnFocus:true
		            }}
		    	]
		
	});
	constantItemGrid.getStore().on('add',function(store,records,index){
		//这里不行，报错
		//records[0].set("constantType_id",tree.getLastSelected().getId());//	这种方案还要测试，还不行
		records[0].setConstant(tree.getLastSelected().getId());
		//records[0].setConstantType(tree.getLastSelected());
	});

	
	
	var viewPort=Ext.create('Ext.container.Viewport',{
		//layout:'border',
		layout: {
             type: 'hbox',
             padding:'5',
             align:'stretch'
        },
        defaults:{margin:'0 5 0 0'},
		items:[tree,constantTypeGrid,constantGrid,constantItemGrid]
	});
	
	
});