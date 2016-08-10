Ext.require("Ems.mgr.UserStoreGrid");
Ext.require('Ems.mgr.UserWorkunitGrid');
Ext.onReady(function() {

	var selectedNode;
	var itemsPerPage = 25;
	var userStore = Ext.create('Ext.data.Store', {
		fields : [ 'id', 'username', 'password', 'name', 'phone' ],
		pageSize : itemsPerPage,
		proxy : {
			type : 'ajax',
			url : Ext.ContextPath + '/user/list.do',
			reader : {
				type : 'json',
				rootProperty : 'root'
			}
		}
	});
	userStore.load({
		params : {
			start : 0,
			limit : itemsPerPage
		}
	});

	var namefield = Ext.create('Ext.form.field.Text', {
		emptyText : '请输入用户名或登录名'
	});
	var tbar = {
		xtype : 'container',
		layout : 'anchor',
		defaults : {
			anchor : '0',
			border : false
		},
		defaultType : 'toolbar',
		items : [ {
			items : [ {
				iconCls : 'form-reload-button',
				text : '刷新',
				handler : function() {
					userStore.reload();
				}
			}, {
				iconCls : 'form-add-button',
				text : '添加',
				handler : addUser
			}, {
				iconCls : 'form-delete-button',
				text : '删除',
				handler : deleteUser
			}, {
				iconCls : 'form-update-button',
				text : '编辑',
				handler : modifUser
			} ]
		// toolbar 1
		}, {
			items : [ namefield, {
				text : '查询',
				iconCls : 'form-search-button',
				handler : function() {
					userStore.load({
						params : {
							start : 0,
							limit : itemsPerPage,
							name : namefield.getValue()
						}
					});
				}
			} ]
		// toolbar 2
		} ]
	};
	var userGrid = Ext.create('Ext.grid.Panel', {
		// title : 'grid',
		width : 400,
		tbar : tbar,
		region : 'west',

		columns : [ {
			header : '用户名',
			dataIndex : 'username'
		}, {
			header : '姓名',
			dataIndex : 'name'
		}, {
			header : '电话',
			dataIndex : 'phone',
			flex : 1
		} ],
		store : userStore,
		listeners : {
			itemclick : function(gridview, record, item, index, e) {
				selectedNode = record;
				user_form.loadRecord(record);
				save_btn.setDisabled(true);
				edit_btn.setDisabled(false);

				refreshSelectedFunRole();
				refreshSelectedStore();

				userWorkunitGrid.getStore().getProxy().extraParams = {
					user_id : selectedNode.get("id")
				}
				userWorkunitGrid.getStore().reload();

				tabPanel.getEl().unmask();
			}
		},
		dockedItems : [ {
			xtype : 'pagingtoolbar',
			store : userStore, // same store GridPanel is using
			dock : 'bottom',
			displayInfo : true
		} ]
	});

	function addUser() {
		// if(!selectedNode) {
		// Ext.Msg.alert("消息","请先选择父节点.");
		// return;
		// }
		user_form.update = false;
		user_form.getForm().setValues({
			username : '',
			password : '0',
			name : '',
			phone : ''
		});
		save_btn.setDisabled(false);
		edit_btn.setDisabled(true);

		tabPanel.setActiveTab(0);

		tabPanel.getEl().unmask();
	}
	function deleteUser() {
		if (!selectedNode) {
			Ext.Msg.alert("消息", "请先选择节点.");
			return;
		}
		Ext.Msg.confirm("消息", "确定要删除吗?", function(btn) {
			if (btn == 'yes') {
				Ext.Ajax.request({
					url : Ext.ContextPath + "/user/delete.do",
					params : {
						id : selectedNode.get("id")
					},
					success : function(response) {
						// userStore.reload({node:selectedNode});
						// userStore.remove(selectedNode);
						// selectedNode.remove();
						userStore.remove(selectedNode);
						selectedNode = null;
						user_form.getForm().setValues({
							username : '',
							name : '',
							phone : ''
						});
					}

				});
			}
		});

	}
	function modifUser() {
		if (!selectedNode) {
			Ext.Msg.alert("消息", "请先选择父节点.");
			return;
		}
		save_btn.setDisabled(false);
		edit_btn.setDisabled(true);
		user_form.update = true;
		tabPanel.setActiveTab(0);
	}
	// ========================================================================================

	var save_btn = Ext.create('Ext.button.Button', {
		text : '保存',
		iconCls : 'icon-save',
		disabled : true,
		handler : savenav,
		listeners : {
			disable : function(b, e) {
				var fields = user_form.getForm().getFields();
				fields.each(function(items) {
					items.setReadOnly(true);
				});
			},
			enable : function() {
				var fields = user_form.getForm().getFields();
				fields.each(function(items) {
					if (items.getName() == 'username'
							&& user_form.update == true) {
						// alert(items.getName()=='leaf' +"=="+
						// user_form.update);
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
		handler : modifUser
	});

	var user_form = Ext.create('Ext.form.Panel', {
		itemId : 'user_form',
		title : '表单(密码默认:0)',
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
			fieldLabel : '用户名',
			name : 'username',
			readOnly : true
		}, {
			fieldLabel : '姓名',
			name : 'name',
			readOnly : true
		}, {
			fieldLabel : '密码',
			name : 'password',
			readOnly : true,
			inputType : 'password'
		}, {
			fieldLabel : '电话',
			name : 'phone',
			readOnly : true
		} ],
		bbar : [ '->', save_btn, edit_btn ]
	});
	user_form.update = false;

	function savenav() {
		var values = user_form.getForm().getValues();
		// values.parentId=selectedNode.get("id");
		if (user_form.update) {
			url = Ext.ContextPath + "/user/update.do";
		} else {
			url = Ext.ContextPath + "/user/save.do";
		}
		Ext.Ajax.request({
			url : url,
			params : values,
			success : function(response) {

				if (user_form.update) {
					// url=Ext.ContextPath+"/user/updatenav.do";
					user_form.updateRecord(selectedNode);
				} else {
					userStore.reload();
				}
				save_btn.setDisabled(false);
				edit_btn.setDisabled(true);
				alert("保存成功!");
			}

		});
	}

	// 功能角色=====================================================================
	var funRoleStore = Ext.create('Ext.data.TreeStore', {
		fields : [ 'id', 'text', 'leaf', 'parentId', 'memo' ],
		proxy : {
			type : 'ajax',
			url : Ext.ContextPath + '/user/listFunRole.do',
			reader : {
				type : 'json',
				rootProperty : 'children'
			}
		},
		root : {
			text : '功能角色',
			id : 'root',
			expanded : true
		},
		listeners : {
			beforeload : function(store) {
				if (!selectedNode) {
					// Ext.Msg.alert("消息","请先选择功能角色!");
					// alert("请先选择一个用户或保存用户!");
					return false;
				}
				store.getProxy().extraParams = Ext.apply(
						store.getProxy().extraParams, {
							user_id : selectedNode.get("id")
						});
			}
		}
	});

	var funRoleTree = Ext.create('Ext.tree.Panel', {
		title : '选择功能角色',
		store : funRoleStore,
		listeners : {
			checkchange : function(node, checked) {
				var params = {
					user_id : selectedNode.get("id"),
					funRole_id : node.get("id"),
					checked : checked
				};
				Ext.Ajax.request({
					url : Ext.ContextPath + "/user/checkchangeFunRole.do",
					params : params,
					method : 'POST',
					success : function(response) {

					}

				});
			}
		}
	});

	function refreshSelectedFunRole() {
		Ext.Ajax.request({
			url : Ext.ContextPath + "/user/selectAllCheckedFunRole.do",
			params : {
				user_id : selectedNode.get("id")
			},
			method : 'POST',
			success : function(response) {
				var obj = Ext.decode(response.responseText).root;
				funRoleStore.getRootNode().cascadeBy(function(node) {
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

	// //数据角色=====================================================================
	// var dataRoleStore = Ext.create('Ext.data.TreeStore', {
	// fields:['id','text','leaf','parentId','memo'],
	// proxy : {
	// type : 'ajax',
	// url : Ext.ContextPath + '/user/listDataRole.do',
	// reader : {
	// type : 'json',
	// root : 'root'
	// }
	// },
	// root : {
	// text : '数据角色',
	// id : 'root',
	// expanded : true
	// },
	// listeners:{
	// beforeload:function( store){
	// if(!selectedNode){
	// //Ext.Msg.alert("消息","请先选择功能角色!");
	// return false;
	// }
	// store.getProxy().extraParams=Ext.apply(store.getProxy().extraParams,{
	// user_id:selectedNode.get("id")
	// });
	// }
	// }
	// });
	//
	// var dataRoleTree = Ext.create('Ext.tree.Panel', {
	// title:'选择数据角色',
	// store : dataRoleStore,
	// listeners:{
	// checkchange:function(node, checked){
	// var params={
	// user_id:selectedNode.get("id"),
	// dataRole_id:node.get("id"),
	// checked:checked
	// };
	// Ext.Ajax.request({
	// url:Ext.ContextPath+"/user/checkchangeDataRole.do",
	// params:params,
	// method:'POST',
	// success:function(response){
	//								
	// }
	//							
	// });
	// }
	// }
	// });
	//			
	// function refreshSelectedDataRole() {
	// Ext.Ajax.request({
	// url:Ext.ContextPath+"/user/selectAllCheckedDataRole.do",
	// params:{user_id:selectedNode.get("id")},
	// method:'POST',
	// success:function(response){
	// var obj=Ext.decode(response.responseText).root;
	// dataRoleStore.getRootNode( ).cascadeBy(function(node){
	// if(node.isLeaf( )){
	// node.set({checked:false});
	// for(var i=0;i<obj.length;i++){
	//	
	// if(node.get("id")==obj[i]){
	// node.set({checked:true});
	// }
	// }
	// }
	// });
	// }
	//							
	// });
	// }

	// 选择访问的仓库
	var store_grid = Ext.create('Ems.mgr.UserStoreGrid', {
		title : '仓库权限',
		listeners : {
			storeSelect : function(record, type) {
				var params = {
					user_id : selectedNode.get("id"),
					store_id : record.get("store_id"),
					checked : true,
					type : type
				};
				Ext.Ajax.request({
					url : Ext.ContextPath + "/user/checkchangeStore.do",
					params : params,
					method : 'POST',
					success : function(response) {

					}

				});
			},
			storeDeselect : function(record, type) {
				var params = {
					user_id : selectedNode.get("id"),
					store_id : record.get("store_id"),
					checked : false,
					type : type
				};
				Ext.Ajax.request({
					url : Ext.ContextPath + "/user/checkchangeStore.do",
					params : params,
					method : 'POST',
					success : function(response) {

					}

				});
			}
		}
	});
	function refreshSelectedStore() {
		store_grid.getStore().load({
			params : {
				user_id : selectedNode.get("id")
			}
		});
		// Ext.Ajax.request({
		// url:Ext.ContextPath+"/user/selectAllCheckedStore.do",
		// params:{user_id:selectedNode.get("id")},
		// method:'POST',
		// success:function(response){
		// // var selectedIds=Ext.decode(response.responseText).root;
		// // var store=store_grid.getStore();//.getRange();
		// // var selected=[];
		// // for(var i=0;i<selectedIds.length;i++){
		// // selected.push(store.getById(selectedIds[i]));
		// // }
		// // store_grid.getSelectionModel().select(selected,false,true);
		// }
		//							
		// });
	}

	var userWorkunitGrid = Ext.create('Ems.mgr.UserWorkunitGrid', {
		title : "可访问作业单位",
		listeners : {
			select : function(model, record, index, eOpts) {
				Ext.Ajax.request({
					url : Ext.ContextPath + "/workunit/checkByUser.do",
					params : {
						workunit_id : record.get("id"),
						user_id : selectedNode.get("id")
					},
					success : function(response) {

					}
				});
			},
			deselect : function(model, record, index, eOpts) {
				Ext.Ajax.request({
					url : Ext.ContextPath + "/workunit/uncheckByUser.do",
					params : {
						workunit_id : record.get("id"),
						user_id : selectedNode.get("id")
					},
					success : function(response) {

					}
				});
			}
		}
	});
	userWorkunitGrid.getStore().on("load",
			function(store, records, successful, eOpt) {
				userWorkunitGrid.getSelectionModel().deselectAll(true);
				var seles = [];
				for (var i = 0; i < records.length; i++) {
					if (records[i].get("selected") == true) {
						seles.push(records[i]);
					}
				}
				
				userWorkunitGrid.getSelectionModel().select(seles, false, true)
			});
	var tabPanel = Ext.create('Ext.tab.Panel', {
		region : 'center',
		split : true,
		// collapsible: true,
		// collapsed:false,
		// collapseMode: 'mini',
		// hideCollapseTool: true,
		items : [ user_form, funRoleTree, store_grid, userWorkunitGrid ],
		listeners : {
			render : function(tabPanel) {
				tabPanel.getEl().mask();
			},
			tabchange : function(tabPanel, newCard, oldCard) {
				if (!selectedNode && newCard.getItemId() != "user_form") {
					alert("请先选择一个用户!");
					tabPanel.setActiveTab(0);
					// return false;
				}
			}
		}

	});

	Ext.create('Ext.container.Viewport', {
		layout : 'border',
		// padding : '5px',
		renderTo : 'view-port',
		style : 'background-color:#FFFFFF',
		items : [ userGrid, tabPanel ]
	});
});