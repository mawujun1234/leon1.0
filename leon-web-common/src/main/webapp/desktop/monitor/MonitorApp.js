Ext.require('Leon.desktop.monitor.SystemInfoPanel');
Ext.onReady(function(){
	var systemInfoPanel=Ext.create('Leon.desktop.monitor.SystemInfoPanel',{});

	var tabPanel=Ext.create('Ext.tab.Panel', {
		region:'center',
		split:true,
	    activeTab: 0,
	    items: [systemInfoPanel],
	    listeners:{
	    	render:function(tabPanel){
	    		//tabPanel.mask();
	    	}
	    }
	});

	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'fit',
		items:[tabPanel]
	});
	

	
});