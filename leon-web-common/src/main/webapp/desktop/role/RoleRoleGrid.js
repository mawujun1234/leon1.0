Ext.define('Leon.desktop.role.RoleRoleGrid',{
	extend:'Ext.grid.Panel',
	requires: [
	     'Leon.desktop.role.RoleRole'
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
	        { text: '名称', dataIndex: 'text', flex: 1 }
       ];
       me.store=Ext.create('Ext.data.Store',{
       		autoSync:true,
       		model: 'Leon.desktop.role.RoleRole',
       		autoLoad:true
       });
       
       var tbar=Ext.create('Ext.toolbar.Toolbar', {
       		items:[{
       			text:'新增',
       			iconCls:'form-add-button ',
       			handler:function(){
       				var menu=Ext.create('Leon.desktop.menu.Menu',{text:'新菜单'});
       				me.store.add(menu);
       				var edit=me.getPlugin("cellEditingPlugin");
       				edit.cancelEdit();
       				edit.startEditByPosition({column:0,row:me.store.getCount( ) -1});
       				//menu.beginEdit( );
       				
       			}	
       		},{
       			text:'删除',
       			iconCls:'form-delete-button ',
       			handler:function(){
       				var model=me.getSelectionModel( ).getLastSelected( ) ;
       				if(model){
       					if(model.get("id")=="default"){
       						Ext.Msg.alert("消息","默认菜单不能删除");
       						return;
       					}
       					model.destroy();
       				}
       			}	
       		}]
       });
       me.tbar=tbar;
       me.callParent();
	}
});