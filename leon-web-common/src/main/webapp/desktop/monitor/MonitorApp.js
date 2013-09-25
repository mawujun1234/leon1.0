Ext.require('Leon.desktop.monitor.SystemInfo');
Ext.onReady(function(){
	var systemInfo=Ext.create('Leon.desktop.monitor.SystemInfo',{});

	var tabPanel=Ext.create('Ext.tab.Panel', {
		region:'center',
		split:true,
	    activeTab: 0,
	    items: [systemInfo],
	    listeners:{
	    	render:function(tabPanel){
	    		tabPanel.mask();
	    	}
	    }
	});

	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'fit',
		items:[tabPanel]
	});
	

	
});