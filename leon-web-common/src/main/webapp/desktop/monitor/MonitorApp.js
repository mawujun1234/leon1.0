Ext.require('Leon.desktop.monitor.SystemInfoPanel');
Ext.require('Leon.desktop.monitor.CpuInfoPanel')
Ext.onReady(function(){
	var systemInfoPanel=Ext.create('Leon.desktop.monitor.SystemInfoPanel',{
		title:'系统信息'
	});
	var cpuInfoPanel=Ext.create('Leon.desktop.monitor.CpuInfoPanel',{
		title:'CPU信息'
	});

	var tabPanel=Ext.create('Ext.tab.Panel', {
		region:'center',
		split:true,
	    activeTab: 0,
	    items: [systemInfoPanel,cpuInfoPanel],
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