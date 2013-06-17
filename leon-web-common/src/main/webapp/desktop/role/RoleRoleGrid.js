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
	initComponent: function () {
       var me = this;
       me.columns=[
	        //{ text: 'id',  dataIndex: 'id' },
	        { text: '名称', dataIndex: 'name', flex: 1 },
	        { text: '描述', dataIndex: 'description', flex: 1 }
       ];
       
       var url="";
	   if(me.roleRoleEnum=="inherit"){
			url="/role/queryParent";
		} else {
			url="/role/queryMutex";
	   }
       me.store=Ext.create('Ext.data.Store',{
       		autoSync:false,
       		model: 'Leon.desktop.role.Role',
       		autoLoad:false,
       		proxy:{
       			type:'bajax',
       			url:url
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
						,listeners:{
							itemdblclick:function( tree, record, item, index, e, eOpts ){
								if(record.get('roleEnum')=='roleCategory'){
									return;
								}
								var params={};
								var url="";
								if(me.roleRoleEnum=="inherit"){
									url="/role/addParent";
									params={parentId:record.getId(),childId:me.currentRole.getId()};
								} else {
									url="/role/addMutex";
									params={ownId:me.currentRole.getId(),mutexId:record.getId()};
								}
								tree.mask("正在新增....");
								Ext.Ajax.request({
		       						url:url,
		       						params:params,
		       						//jsonData:{id:{currentId:me.currentRole.getId(),otherId:model.getId()}},
		       						method:'POST',
		       						success:function(response){
		       							me.getStore().reload();
		       						},
		       						callback :function(){
										tree.unmask();
									}
		       					});
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
       				var record=me.getSelectionModel( ).getLastSelected( ) ;
       				if(record){
       					var url="";
						if(me.roleRoleEnum=="inherit"){
							url="/role/removeParent";
							params={parentId:record.getId(),childId:me.currentRole.getId()};
						} else {
							url="/role/removeMutex";
							params={ownId:me.currentRole.getId(),mutexId:record.getId()};
						}
       					Ext.Ajax.request({
       						url:url,
       						params:params,
       						method:'POST',
       						success:function(response){
       							me.getStore().reload();
       						}
       					});
       				}
       			}	
       		}]
       });
       me.tbar=tbar;
       

       me.callParent();
	}
});