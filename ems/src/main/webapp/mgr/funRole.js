Ext.require("Ems.mgr.UserForm");
Ext.require('Ems.mgr.UIElemenCheckGrid');
Ext.onReady(function() {

	var selectedNode;
	var treeStore = Ext.create('Ext.data.TreeStore', {
		fields : [ 'id', 'text', 'leaf', 'parentId', 'memo' ],
		proxy : {
			type : 'ajax',
			url : Ext.ContextPath + '/funRole/list.do',
			reader : {
				type : 'json',
				rootProperty : 'children'
			}
		},
		root : {
			text : '功能角色管理',
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
					node : tree.getRootNode()
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
		listeners : {
			itemclick : function(treeview, record, item, index, e) {
				selectedNode = record;
				nav_form.loadRecord(record);
				save_btn.setDisabled(true);
				edit_btn.setDisabled(false);

				refreshSelectedNav();

				tabPanel.getEl().unmask();
				uielemencheckgrid.mask();
				//		    			userStore.load({
				//						    params:{
				//						        start:0,
				//						        limit: itemsPerPage,
				//						        funrole_id:selectedNode.get("id")
				//						    }
				//						});

			}
		}
	});

	function addNav() {
		//				if(!selectedNode) {
		//					Ext.Msg.alert("消息","请先选择父节点.");
		//					return;
		//				}
		nav_form.update = false;
		nav_form.getForm().setValues({
			text : '',
			memo : '',
			id : '',
			parentId : tree.getRootNode().get("id")
		});
		save_btn.setDisabled(false);
		edit_btn.setDisabled(true);

		tabPanel.getEl().unmask();
	}
	function deleteNav() {
		if (!selectedNode) {
			Ext.Msg.alert("消息", "请先选择节点.");
			return;
		}
		Ext.Msg.confirm("消息", "确定要删除吗?", function(btn) {
			if (btn == 'yes') {
				Ext.Ajax.request({
					url : Ext.ContextPath + "/funRole/delete.do",
					params : {
						id : selectedNode.get("id")
					},
					success : function(response) {
						//treeStore.reload({node:selectedNode});
						//treeStore.remove(selectedNode);
						selectedNode.remove();
						selectedNode = null;
						nav_form.getForm().setValues({
							text : '',
							memo : '',
							id : '',
							parentId : ''
						});
					}

				});
			}
		});

	}
	function modifNav() {
		if (!selectedNode) {
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
		itemId : 'nav_form',
		title : '表单',
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
			fieldLabel : '名称',
			name : 'text',
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
			url = Ext.ContextPath + "/funRole/update.do";
		} else {
			url = Ext.ContextPath + "/funRole/save.do";
		}
		Ext.Ajax.request({
			url : url,
			params : values,
			success : function(response) {

				if (nav_form.update) {
					//url=Ext.ContextPath+"/funRole/updatenav.do";
					nav_form.updateRecord(selectedNode);
				} else {
					treeStore.reload({
						node : tree.getRootNode()
					});
				}
				save_btn.setDisabled(false);
				edit_btn.setDisabled(true);
			}

		});
	}

	//可访问的功能===========================================================			
	var navigationStore = Ext.create('Ext.data.TreeStore', {
		fields : [ 'id', 'text', 'link', 'leaf', 'parentId', 'memo' ],
		proxy : {
			type : 'ajax',
			url : Ext.ContextPath + '/funRole/listNav4Checked.do',
			reader : {
				type : 'json',
				rootProperty : 'children'
			}
		},
		root : {
			text : '请先选择功能角色',
			id : 'root',
			expanded : true
		},
		autoLoad : false,
		listeners : {
			beforeload : function(store) {
				if (!selectedNode) {
					//Ext.Msg.alert("消息","请先选择功能角色!");
					return false;
				}
				store.getProxy().extraParams = Ext.apply(
						store.getProxy().extraParams, {
							funRole_id : selectedNode.get("id")
						});
			}
		}
	});

	var navigationTree = Ext.create('Ext.tree.Panel', {
		title : '菜单',
		region : 'west',
		width : 350,
		rootVisible : true,
		store : navigationStore,
		listeners : {
			checkchange : function(node, checked) {
				var params = {
					funRole_id : selectedNode.get("id"),
					navigation_id : node.get("id"),
					checked : checked
				};
				Ext.Ajax.request({
					url : Ext.ContextPath + "/funRole/checkchange.do",
					params : params,
					method : 'POST',
					success : function(response) {

					}

				});
			},
			itemclick : function() {
				uielemencheckgrid.unmask();
			}

		}
	});
	function refreshSelectedNav() {
		Ext.Ajax.request({
			url : Ext.ContextPath + "/funRole/selectAllCheckedNav.do",
			params : {
				funRole_id : selectedNode.get("id")
			},
			method : 'POST',
			success : function(response) {
				var obj = Ext.decode(response.responseText).root;
				navigationStore.getRootNode().cascadeBy(function(node) {
					if (node.isLeaf()) {
						node.set({
							checked : false
						});
						for (var i = 0; i < obj.length; i++) {

							if (node.get("id") == obj[i]) {
								node.set({
									checked : true
								});
							}
						}
					}
				});
			}

		});
	}

	navigationTree.on("itemclick", function(tree, record, item, index, e) {
		uielemencheckgrid.getStore().getProxy().extraParams = {
			navigation_id : record.get("id"),
			funRole_id : selectedNode.get("id")
		}
		uielemencheckgrid.getStore().reload();
	});

	var uielemencheckgrid = Ext.create('Ems.mgr.UIElemenCheckGrid', {
		title : '界面按钮',
		region : 'center',
		listeners : {
			select : function(model, record, index, eOpts) {
				Ext.Ajax.request({
					url : Ext.ContextPath + "/uIElement/checkByFunRole.do",
					params : {
						uIElement_id : record.get("id"),
						funRole_id : selectedNode.get("id")
					},
					success : function(response) {

					}
				});
			},
			deselect : function(model, record, index, eOpts) {
				Ext.Ajax.request({
					url : Ext.ContextPath + "/uIElement/uncheckByFunRole.do",
					params : {
						uIElement_id : record.get("id"),
						funRole_id : selectedNode.get("id")
					},
					success : function(response) {

					}
				});
			}
		}
	});
	uielemencheckgrid.getStore().on(
			"load",
			function(store, records, successful, eOpt) {
				var seles = [];
				for (var i = 0; i < records.length; i++) {
					if (records[i].get("selected") == true) {
						seles.push(records[i]);
					}
				}
				uielemencheckgrid.getSelectionModel()
						.select(seles, false, true)
			});
	var navigationTree_panel = Ext.create('Ext.panel.Panel', {
		title : '选择可访问的功能',
		layout : 'border',
		items : [ navigationTree, uielemencheckgrid ]
	});

	var tabPanel = Ext.create('Ext.tab.Panel', {
		region : 'center',
		items : [ nav_form, navigationTree_panel ],
		listeners : {
			render : function(tabPanel) {
				tabPanel.getEl().mask();
			},
			tabchange : function(tabPanel, newCard, oldCard) {
				if (!selectedNode && newCard.getItemId() != "nav_form") {
					alert("请先选择一个角色!");
					tabPanel.setActiveTab(0);
					//return false;
				}
			}
		}
	});

	Ext.create('Ext.container.Viewport', {
		layout : 'border',
		//padding : '5px',
		renderTo : 'view-port',
		style : 'background-color:#FFFFFF',
		items : [ tree, tabPanel ]
	});
});