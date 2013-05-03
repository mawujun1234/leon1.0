Ext.define('Leon.desktop.fun.FunTree',{
	extend:'Ext.tree.Panel',
	requires: [
	     'Leon.desktop.fun.Fun'
	],
	rootVisible: true,
	initComponent: function () {
       var me = this;
       var store = Ext.create('Ext.data.TreeStore', {
       		autoLoad:false,
       		model:'Leon.desktop.fun.Fun',
		    root: {
		        expanded: true,
		        text:'功能管理'
		    },
		    listeners:{
		    	load:function(store,node,records){//alert(1);
		    		node.expand();
		    	}
		    }
		});
       //store.load();
       me.store=store;
       
       
       me.callParent();
	}
});