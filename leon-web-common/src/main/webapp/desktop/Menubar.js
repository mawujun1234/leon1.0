Ext.define('Leon.desktop.Menubar', {
			extend : 'Ext.toolbar.Toolbar', // TODO - make this a basic hbox
			// panel...

			requires : ['Ext.button.Button', 'Ext.resizer.Splitter',
					'Ext.menu.Menu', 'Leon.desktop.QuickStartPanel'],
			itemId : "desktop_menubar",
			layout : {
				overflowHandler : 'Menu'
			},
			style : {
				'z-index' : 88888
			},
			desktop : null,
			initComponent : function() {
				var me = this;

				var newItems = [];
				var quickStartPanel = Ext.create(
						'Leon.desktop.QuickStartPanel', {
							quickstarts : me.quickstarts,
							desktop : me.desktop
						});
				delete me.quickstarts;

				newItems.push({
							text : '',
							iconCls : 'icons_arrow_inout',
							tooltip : '快速启动',
							listeners : {
				// mouseover:function(btn){
							// //quickStartPanel.btn=btn;
							// btn.maybeShowMenu();
							// }
							},
							menu : {
								xtype : 'menu',
								plain : true,
								items : quickStartPanel
							}
						},'-','-');

				me.lazyInitMenuItem(me.items);
				me.items = newItems.concat(me.items);
				// me.items=newItems;

				me.callParent();
			},
			/**
			 * 延迟初始化菜单
			 * 
			 * @param {}
			 *            menuItems
			 */
			lazyInitMenuItem : function(menuItems) {
				var me = this;
				if (!menuItems) {
					return;
				}
				for (var i = 0; i < menuItems.length; i++) {
					var model = menuItems[i];

					model.menuItemId = model.id;
					delete model.id;

					model.link_url = model.url;
					delete model.url;
					// 如果有url，就表示可以点击
					if (model.link_url) {
						model.handler = function(btn) {
							me.desktop.createWindow({
										title : btn.text,
										menuItemId : btn.menuItemId,
										funId : btn.funId,
										url : btn.link_url,
										iconCls : btn.iconCls
									});
						}
					}
					if (model.scripts) {
						model.plugins = [{
									ptype : 'menuplugin',
									scripts : model.scripts
								}];
					}

					// 如果不是叶子节点，就开始异步加载下级节点
					if (!model.leaf) {
						// console.log(model.text);
						model.menu = [{text : '正在加载...'}];

						// model.listeners={
						// mouseover:me.requeChildrenMenu
						// // ,mouseout:function(button){
						// // console.log(button.mouseMoveOut);
						// // if(button.mouseMoveOut){
						// // button.hideMenu();button.blur();
						// // button.mouseMoveOut=true;
						// // }
						// // //button.hideMenu();
						// // //button.blur();
						// // }
						// // ,menutriggerover:function(button){
						// // console.log(1);
						// // button.mouseMoveOut=false;
						// // }
						// }
						model.listeners = {
							scope : me,
							mouseover : me.requeChildrenMenu
						};

						// 如果有下级菜单就显示为split不tton
						model.xtype = 'splitbutton';
					}
				}
			},
			/**
			 * 
			 * @param {} button
			 */
			requeChildrenMenu : function(button) {
				var me = this;
				if (button.menuLoaded) {
					button.showMenu();
					return;
				}
				var menu = button.menu;
				console.log(me.text);
				Ext.Ajax.request({
							url : Ext.ContextPath + "/menuItem/query",
							params : {
								id : button.menuItemId
							},
							method : 'post',
							success : function(response, options) {
								var resp = Ext.decode(response.responseText);
								menu.removeAll();

								me.lazyInitMenuItem(resp.root);
								menu.insert(2, resp.root);
								button.showMenu();
								button.menuLoaded = true;
							},
							failure : function(response, options) {
								Ext.Msg.alert("错误", "加载子菜单失败！")
							}
						});
			}
		});