Ext.require('Ems.mgr.UIElementGrid');
Ext.onReady(function() {

	window.selectedNode;
	var treeStore = Ext.create('Ext.data.TreeStore', {
		fields : [ 'id', 'text', 'link', 'leaf', 'parentId', 'memo' ],
		proxy : {
			type : 'ajax',
			url : Ext.ContextPath + '/nav/list.do',
			reader : {
				type : 'json',
				rootProperty : 'children'
			}
		},
		root : {
			text : '菜单管理',
			id : 'root',
			expanded : true
		}
	});

	var tree = Ext.create('Ext.tree.Panel', {
		//title : 'TreeGrid',
		width : 300,
		region : 'west',
		// rootVisible: false,

		store : treeStore,
		tbar : [ {
			iconCls : 'form-reload-button',
			text : '刷新',
			handler : function() {
				treeStore.reload({
					node : window.selectedNode ? window.electedNode : tree.getRootNode()
				});
			}
		}, {
			iconCls : 'form-add-button',
			text : '添加',
			handler : addNav
		}, {
			iconCls : 'form-delete-button',
			text : '删除',
			handler : deleteNav
		}, {
			iconCls : 'form-update-button',
			text : '编辑',
			handler : modifNav
		} ],
		//						tools : [{
		//									type : 'refresh',
		//									tooltip : '刷新',
		//									handler : function() {
		//										treeStore.reload({node:selectedNode?selectedNode:tree.getRootNode()});
		//									}
		//								}, {
		//									type : 'plus',
		//									tooltip : '添加',
		//									handler : addNav
		//								}, {
		//									type : 'minus',
		//									tooltip : '删除',
		//									handler : deleteNav
		//								}, {
		//									type : 'gear',
		//									tooltip : '编辑',
		//									handler : modifNav
		//								}],
		listeners : {
			itemclick : function(tree, record, item, index, e) {
				window.selectedNode = record;
				nav_form.loadRecord(record);
				save_btn.setDisabled(true);
				edit_btn.setDisabled(false);
				
				uielementgrid.getStore().getProxy().extraParams={
					navigation_id:record.get("id")
				}
				uielementgrid.getStore().reload();
			}
		}
	});

	function addNav() {
		if (!window.selectedNode) {
			Ext.Msg.alert("消息", "请先选择父节点.");
			return;
		}
		nav_form.update = false;
		nav_form.getForm().setValues({
			text : '',
			link : '',
			memo : '',
			id : '',
			parentId : window.selectedNode.get("id")
		});
		save_btn.setDisabled(false);
		edit_btn.setDisabled(true);
	}
	function deleteNav() {
		if (!window.selectedNode) {
			Ext.Msg.alert("消息", "请先选择父节点.");
			return;
		}
		Ext.Msg.confirm("消息", "确定要删除吗?", function(btn) {
			if (btn == 'yes') {
				Ext.Ajax.request({
					url : Ext.ContextPath + "/nav/delete.do",
					params : {
						id : window.selectedNode.get("id")
					},
					success : function(response) {
						//treeStore.reload({node:selectedNode});
						//treeStore.remove(selectedNode);
						window.selectedNode.remove();
						window.selectedNode = null;
						nav_form.getForm().setValues({
							text : '',
							memo : '',
							link : '',
							id : '',
							parentId : ''
						});
					}

				});
			}
		});

	}
	function modifNav() {
		if (!window.selectedNode) {
			Ext.Msg.alert("消息", "请先选择父节点.");
			return;
		}
		save_btn.setDisabled(false);
		edit_btn.setDisabled(true);
		nav_form.update = true;
	}
	//========================================================================================

	var save_btn = Ext.create('Ext.button.Button', {
		text : '保存',
		iconCls : 'icon-save',
		disabled : true,
		handler : savenav,
		listeners : {
			disable : function(b, e) {
				var fields = nav_form.getForm().getFields();
				fields.each(function(items) {
					items.setReadOnly(true);
				});
			},
			enable : function() {
				var fields = nav_form.getForm().getFields();
				fields.each(function(items) {
					if (items.getName() == 'leaf' && nav_form.update == true) {
						//alert(items.getName()=='leaf' +"=="+ nav_form.update);
						items.setReadOnly(true);
					} else {
						items.setReadOnly(false);
					}
				});
			}
		}
	});
	var edit_btn = Ext.create('Ext.button.Button', {
		text : '编辑',
		iconCls : 'icon-edit',
		disabled : true,
		handler : modifNav
	});

	var nav_form = Ext.create('Ext.form.Panel', {
		title:'菜单',
		split : true,
		collapsible : true,
		collapsed : false,
		collapseMode : 'mini',
		hideCollapseTool : true,
		defaultType : 'textfield',
		trackResetOnLoad : true,
		fieldDefaults : {
			labelWidth : 70,
			anchor : '95%'
		},
		bodyPadding : '5px',
		items : [ {
			xtype : 'hidden',
			name : 'id'
		}, {
			xtype : 'hidden',
			name : 'parentId'
		}, {
			xtype : 'combo',
			fieldLabel : '类型',
			name : 'leaf',
			displayField : 'text',
			valueField : 'value',
			store : Ext.create('Ext.data.ArrayStore', {
				fields : [ 'value', 'text' ],
				data : [ [ false, '文件夹' ], [ true, '菜单' ] ]
			}),
			editable : false,
			readOnly : true,
			value : false
		}, {
			fieldLabel : '名称',
			name : 'text',
			readOnly : true
		}, {
			fieldLabel : '地址',
			name : 'link',
			readOnly : true
		}, {
			fieldLabel : '描述',
			name : 'memo',
			readOnly : true
		} ],
		bbar : [ '->', save_btn, edit_btn ]
	});
	nav_form.update = false;

	function savenav() {
		var values = nav_form.getForm().getValues();
		//values.parentId=selectedNode.get("id");
		if (nav_form.update) {
			url = Ext.ContextPath + "/nav/update.do";
		} else {
			url = Ext.ContextPath + "/nav/save.do";
		}
		Ext.Ajax.request({
			url : url,
			params : values,
			success : function(response) {

				if (nav_form.update) {
					//url=Ext.ContextPath+"/nav/updatenav.do";
					nav_form.updateRecord(window.selectedNode);
				} else {
					treeStore.reload({
						node : window.selectedNode
					});
				}
				save_btn.setDisabled(false);
				edit_btn.setDisabled(true);
			}

		});
	}
	//			function modifynav(){
	//				save_btn.setDisabled(false);
	//				edit_btn.setDisabled(true);
	//				nav_form.update=true;
	//			}
	var uielementgrid=Ext.create('Ems.mgr.UIElementGrid',{
		title:'界面按钮'
	});
	var tabpanel=Ext.create('Ext.tab.Panel',{
		region : 'center',
		items:[nav_form,uielementgrid]
	});
	Ext.create('Ext.container.Viewport', {
		layout : 'border',
		//padding : '5px',
		//renderTo : 'view-port',
		style : 'background-color:#FFFFFF',
		items : [ tree, tabpanel ]
	});
});