Ext.define('Leon.desktop.group.GroupTree',{
	extend:'Leon.common.ux.BaseTree',
	requires: [
	     'Leon.desktop.group.Group'
	],
	rootVisible: false,
	config:{
		menuId:'default'//默认的菜单id
	},
	disabledAction:false,
	autoInitSimpleAction:true,
	displayField:'name',
	model:'Leon.desktop.group.Group',
	autoLoadData:false,
	defaultRootText:'群组管理',
	initComponent: function () {
       var me = this;

    
       
       me.callParent();
	}
});