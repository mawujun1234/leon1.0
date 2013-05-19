Ext.require('Leon.desktop.fun.FunTree');
Ext.require('Leon.desktop.fun.FunForm');
Ext.require('Leon.common.ux.BaseGrid');
Ext.onReady(function(){
	var store = Ext.create('Ext.data.Store', {
       		autoLoad:false,
       		model:'Leon.desktop.fun.Fun',
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
	var tree=Ext.create('Leon.common.ux.BaseGrid',{
		region:'center',
		//plugins:[{ptype:'treeexten'}],
		split: true,
		store:store,
		collapsible: true,
		title:'功能树',
		width:400
	});
	
	
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'fit',
		items:[tree]
	});
	
});