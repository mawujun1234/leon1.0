Ext.define('Leon.desktop.menu.MenuItemTree',{
	extend:'Leon.common.ux.BaseTree',
	requires: [
	     'Leon.desktop.menu.MenuItem'
	],
	rootVisible: false,
	config:{
		menuId:'default'//默认的菜单id
	},
	disabledAction:false,
	autoInitSimpleAction:true,
	model:'Leon.desktop.menu.MenuItem',
	autoLoadData:false,
	defaultRootText:'默认菜单',
	initComponent: function () {
       var me = this;

       
//       me.on('beforeCreate',function(parentNode,values){
//       	values.menu_id=me.getMenuId();
//       	return true;
//       });
       me.on('beforeDelete',function(node){
       		if(me.getMenuId()=='default'){
       			Ext.Msg.alert('消息','这是自动创建的菜单项，不能删除！');
       			return;
       		}
       });
       
       me.callParent();
	}
});