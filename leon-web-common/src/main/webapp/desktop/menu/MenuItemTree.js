Ext.define('Leon.desktop.menu.MenuItemTree',{
	extend:'Ext.tree.Panel',
	requires: [
	     'Leon.desktop.menu.MenuItem'
	],
	rootVisible: true,
	config:{
		menuId:'default'//默认的菜单id
	},
	initComponent: function () {
       var me = this;
       var store = Ext.create('Ext.data.TreeStore', {
       		autoLoad:false,
       		model:'Leon.desktop.menu.MenuItem',
		    root: {
		        expanded: true,
		        text:'默认菜单'
		    },
		    listeners:{
		    	beforeload:function(store){
		    		store.getProxy().extraParams={menuId:me.getMenuId()}
		    	}
		    }
		});
       //store.load();
       me.store=store;
       
       
       me.callParent();
	}
});