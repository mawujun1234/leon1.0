Ext.define('Leon.desktop.fun.FunTree',{
	extend:'Leon.common.ux.BaseTree',
	requires: [
		'Leon.common.ux.BaseTree',
	    'Leon.desktop.fun.Fun'
	],
	rootVisible: true,
	initComponent: function () {
       var me = this;
       
        me.model="Leon.desktop.fun.Fun";
//       var store = Ext.create('Ext.data.TreeStore', {
//       		autoLoad:false,
//       		model:'Leon.desktop.fun.Fun',
//		    root: {
//		        expanded: true,
//		        text:'功能管理'
//		    }
//		});
//       //store.load();
//       me.store=store;
       
        me.callParent();
      
	}
});