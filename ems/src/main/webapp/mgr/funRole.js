Ext.require("Ems.mgr.UserForm")
Ext.onReady(function() {
	
		var selectedNode;
			var treeStore = Ext.create('Ext.data.TreeStore', {
				fields:['id','text','leaf','parentId','memo'],
						proxy : {
							type : 'ajax',
							url : Ext.ContextPath + '/funRole/list.do',
							reader : {
								type : 'json',
								root : 'root'
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
						tbar: [{
							iconCls:'form-reload-button',
							text : '刷新',
							handler : function() {
								treeStore.reload({node:tree.getRootNode()});
							}
						}, {
							iconCls:'form-add-button',
							text : '添加',
							handler : addNav
						}, {
							iconCls:'form-delete-button',
							text : '删除',
							handler : deleteNav
						}, {
							iconCls:'form-update-button',
							text : '编辑',
							handler : modifNav
						}],
				listeners:{
					itemclick:function(treeview, record, item, index, e){
						selectedNode=record;
						nav_form.loadRecord(record);
		    			save_btn.setDisabled(true);
		    			edit_btn.setDisabled(false);
		    			
		    			refreshSelectedNav();
		    			
		    			tabPanel.getEl().unmask();
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
					
			function addNav(){
//				if(!selectedNode) {
//					Ext.Msg.alert("消息","请先选择父节点.");
//					return;
//				}
				nav_form.update=false;
				nav_form.getForm().setValues({text:'',memo:'',id:'',parentId:tree.getRootNode().get("id")});
				save_btn.setDisabled(false);
				edit_btn.setDisabled(true);
			}
			function deleteNav(){
				if(!selectedNode) {
					Ext.Msg.alert("消息","请先选择节点.");
					return;
				}
				Ext.Msg.confirm("消息","确定要删除吗?",function(btn){
					if(btn=='yes'){
						Ext.Ajax.request({
							url:Ext.ContextPath+"/funRole/delete.do",
							params:{id:selectedNode.get("id")},
							success:function(response){
								//treeStore.reload({node:selectedNode});
								//treeStore.remove(selectedNode);
								selectedNode.remove();
								selectedNode=null;
								nav_form.getForm().setValues({text:'',memo:'',id:'',parentId:''});
							}
							
						});
					}
				});
				
			}
			function modifNav(){
				if(!selectedNode) {
					Ext.Msg.alert("消息","请先选择父节点.");
					return;
				}
				save_btn.setDisabled(false);
				edit_btn.setDisabled(true);
				nav_form.update=true;
			}
//========================================================================================
			
			var save_btn=Ext.create('Ext.button.Button',{text:'保存',iconCls:'icon-save',disabled:true,handler:savenav,
				listeners:{
					disable:function(b,e){
						var fields=nav_form.getForm().getFields( );
						fields.each(function(items){
							items.setReadOnly(true);
						});
					},
					enable:function(){
						var fields=nav_form.getForm().getFields( );
						fields.each(function(items){
							if(items.getName()=='leaf' && nav_form.update==true){
								//alert(items.getName()=='leaf' +"=="+ nav_form.update);
								items.setReadOnly(true);
							}else{
								items.setReadOnly(false);
							}
						});
					}
				}
			});
			var edit_btn=Ext.create('Ext.button.Button',{text:'编辑',iconCls:'icon-edit',disabled:true,handler:modifNav});
			
			var nav_form = Ext.create('Ext.form.Panel', {
			    title:'表单',
			    split: true,
			    collapsible: true,
			    collapsed:false,
			    collapseMode: 'mini',
			    hideCollapseTool: true,
			    defaultType: 'textfield',
			    trackResetOnLoad:true,
			    fieldDefaults: {
			        labelWidth: 70,
			        anchor: '95%'
			    },
			    bodyPadding: '5px',
			    items: [{xtype: 'hidden',name: 'id'},{xtype: 'hidden',name: 'parentId'},
					    {fieldLabel: '名称',name: 'text',readOnly:true},
					    {fieldLabel: '描述',name: 'memo',readOnly:true}],
				bbar:['->',save_btn,edit_btn]
			});
			nav_form.update=false;
			
			function savenav(){
				var values=nav_form.getForm().getValues();
				//values.parentId=selectedNode.get("id");
				if(nav_form.update){
					url=Ext.ContextPath+"/funRole/update.do";
				} else {
					url=Ext.ContextPath+"/funRole/save.do";
				}
				Ext.Ajax.request({
					url:url,
					params:values,
					success:function(response){
						
						if(nav_form.update){
							//url=Ext.ContextPath+"/funRole/updatenav.do";
							nav_form.updateRecord(selectedNode);
						} else {
							treeStore.reload({node:tree.getRootNode()});
						}
						save_btn.setDisabled(false);
						edit_btn.setDisabled(true);
					}
					
				});
			}

//可访问的功能===========================================================			
			var navigationStore = Ext.create('Ext.data.TreeStore', {
				fields:['id','text','link','leaf','parentId','memo'],
				proxy : {
						type : 'ajax',
						url : Ext.ContextPath + '/funRole/listNav4Checked.do',
						reader : {
						type : 'json',
						root : 'root'
					}
				},
				root : {
					text : '请先选择功能角色',
					id : 'root',
					expanded : true
				},
				autoLoad:false,
				listeners:{
					beforeload:function( store){
						if(!selectedNode){
							//Ext.Msg.alert("消息","请先选择功能角色!");
							return false;
						}
						 store.getProxy().extraParams=Ext.apply(store.getProxy().extraParams,{
		    				funRole_id:selectedNode.get("id")
		    			});
					}
				}
			});

			var navigationTree = Ext.create('Ext.tree.Panel', {
				title : '选择可访问的功能',
				rootVisible: true,
				store : navigationStore,
				listeners:{
					checkchange:function(node, checked){
						var params={
							funRole_id:selectedNode.get("id"),
							navigation_id:node.get("id"),
							checked:checked
						};
						Ext.Ajax.request({
							url:Ext.ContextPath+"/funRole/checkchange.do",
							params:params,
							method:'POST',
							success:function(response){
								
							}
							
						});
					}
					
				}
			});
			function refreshSelectedNav() {
				Ext.Ajax.request({
					url:Ext.ContextPath+"/funRole/selectAllCheckedNav.do",
					params:{funRole_id:selectedNode.get("id")},
					method:'POST',
					success:function(response){
						var obj=Ext.decode(response.responseText).root;
						navigationStore.getRootNode( ).cascadeBy(function(node){	
							if(node.isLeaf( )){		
								node.set({checked:false});
								for(var i=0;i<obj.length;i++){
	
									if(node.get("id")==obj[i]){
										node.set({checked:true});
									}
								}
							}
						});
					}
							
				});
			}

			
			
			
			var tabPanel=Ext.create('Ext.tab.Panel',{
				region:'center',
				items:[nav_form,navigationTree],
				listeners:{
					render:function(tabPanel){
						tabPanel.getEl().mask();
					}
				}
			});
			
			Ext.create('Ext.container.Viewport', {
						layout : 'border',
						//padding : '5px',
						renderTo : 'view-port',
						style : 'background-color:#FFFFFF',
						items : [tree, tabPanel]
					});
		});