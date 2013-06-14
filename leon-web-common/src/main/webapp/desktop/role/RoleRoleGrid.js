Ext.define('Leon.desktop.role.RoleRoleGrid',{
	extend:'Ext.grid.Panel',
	requires: [
	     //'Leon.desktop.role.RoleRole'
	],
	columnLines :true,
	stripeRows:true,
	viewConfig:{
		stripeRows:true,
		listeners:{
			refresh:function(){
				this.select(0);
			}
		}
	},
//	//selType: 'cellmodel',
//    plugins: [
//        Ext.create('Ext.grid.plugin.CellEditing', {
//        	pluginId: 'cellEditingPlugin',
//            clicksToEdit: 2
//        })
//    ],
	initComponent: function () {
       var me = this;
       me.columns=[
	        //{ text: 'id',  dataIndex: 'id' },
	        { text: '名称', dataIndex: 'name', flex: 1 },
	        { text: '描述', dataIndex: 'description', flex: 1 }
       ];
       me.store=Ext.create('Ext.data.Store',{
       		autoSync:false,
       		model: 'Leon.desktop.role.Role',
       		autoLoad:false,
       		proxy:{
       			type:'bajax',
       			url:'/role/queryByRole'
       		}
       });
       
       var tbar=Ext.create('Ext.toolbar.Toolbar', {
       		items:[{
       			text:'新增角色',
       			iconCls:'form-add-button ',
       			handler:function(){
       				var tree=Ext.create('Leon.common.ux.BaseTree',{
						defaultRootText:'角色选择',
						width:300,
						height:200,
						rootVisible:false,
						displayField :'name',
						model:'Leon.desktop.role.Role',
						autoInitSimpleAction:false
						
						//url:'/role/query'
						//region:'west'
						//split:true,
						//flex: 0.8
						,listeners:{
							itemdblclick:function( tree, record, item, index, e, eOpts ){
								if(record.get('roleEnum')=='roleCategory'){
									return;
								}
								tree.mask("正在新增....");
								Ext.Ajax.request({
		       						url:'/roleRole/create',
		       						params:{currentId:record.getId(),otherId:me.currentRole.getId(),roleRoleEnum:me.roleRoleEnum},
		       						//jsonData:{id:{currentId:me.currentRole.getId(),otherId:model.getId()}},
		       						method:'POST',
		       						success:function(response){
		       							me.getStore().reload();
		       						},
		       						callback :function(){
										tree.unmask();
									}
		       					});
//								var roleRole=Ext.createModel('Leon.desktop.role.RoleRole',{
//									roleRoleEnum:me.roleRoleEnum,
//									//current:{id:me.currentRole.getId()},
//									//other:{id:record.getId()}
//									current_id:me.currentRole.getId(),
//									other_id:record.getId()
//								});
//								roleRole.save({
//									success: function(record, operation) {
//										me.getStore().reload();
//										
//									},
//									callback :function(){
//										tree.unmask();
//									}
//								});
							}
						}
				   });
       				var win=Ext.create('Ext.Window',{
       					layout:'fit',
       					//closeAction:'hide',
       					modal:true,
       					title:'选择角色',
       					items:[tree]
       					
       				});
       				win.show();
       			}	
       		},{
       			text:'删除',
       			iconCls:'form-delete-button ',
       			handler:function(){
       				var model=me.getSelectionModel( ).getLastSelected( ) ;
       				if(model){
//       					if(model.get("id")=="default"){
//       						Ext.Msg.alert("消息","默认菜单不能删除");
//       						return;
//       					}
       					Ext.Ajax.request({
       						url:'/roleRole/destroy',
       						params:{otherId:me.currentRole.getId(),currentId:model.getId(),roleRoleEnum:me.roleRoleEnum},
       						//jsonData:{id:{currentId:me.currentRole.getId(),otherId:model.getId()}},
       						method:'POST',
       						success:function(response){
       							me.getStore().reload();
       						}
       					});
       					//model.destroy();
       				}
       			}	
       		}]
       });
       me.tbar=tbar;
       

       me.callParent();
	}
});