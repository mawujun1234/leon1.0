Ext.define('Leon.desktop.menu.MenuItemTree',{
	extend:'Ext.tree.Panel',
	requires: [
	     'Leon.desktop.menu.MenuItem'
	],
	rootVisible: true,
	initComponent: function () {
       var me = this;
       var store = Ext.create('Ext.data.TreeStore', {
       		autoLoad:false,
       		model:'Leon.desktop.menu.MenuItem',
		    root: {
		        expanded: true,
		        text:'功能管理'
		    },
		    listeners:{
		    	load:function(store,node,records){//alert(1);
		    		//node.expand();
		    	}
		    }
		});
       //store.load();
       me.store=store;
       
       
       me.callParent();
	}
});