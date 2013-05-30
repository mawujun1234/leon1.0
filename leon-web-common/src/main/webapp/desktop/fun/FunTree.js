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
       
        
//       // me.removeAction(0);
//        var createModule = new Ext.Action({
//		    text: '新增模块',
//		    handler: function(){	
//		    	me.onCreate({text:'新模块','funEunm':'module'});
//		    },
//		    iconCls: 'form-save-button'
//		});
//        me.addAction(createModule);
//        
//        var createFun = new Ext.Action({
//		    text: '新增功能',
//		    handler: function(){
//		    	me.onCreate({text:'新功能','funEunm':'fun'});
//		    },
//		    iconCls: 'form-save-button'
//		});
//        me.addAction(createFun,1);
		
       
         me.callParent();
      
	}
});