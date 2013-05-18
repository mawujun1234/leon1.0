Ext.require('Leon.desktop.fun.FunTree');
Ext.require('Leon.desktop.fun.FunForm');
Ext.require('Leon.common.LeonTree');
Ext.onReady(function(){
	var store = Ext.create('Ext.data.TreeStore', {
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
	var tree=Ext.create('Leon.common.LeonTree',{
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