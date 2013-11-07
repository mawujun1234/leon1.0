Ext.require('Leon.desktop.monitor.SystemInfoPanel');
Ext.require('Leon.desktop.monitor.CpuMemInfoPanel')
Ext.onReady(function(){
	

	Ext.Ajax.request({
				method:'POST',
				url:Ext.ContextPath+"/monitorSystem/querySystemInfo",
				success:function(response){
					var obj=Ext.decode(response.responseText);
					initPage(obj.root.fileSystemInfo.devNames);
				}
	});
	
function initPage(devNames){
	var systemInfoPanel=Ext.create('Leon.desktop.monitor.SystemInfoPanel',{
		devNames:devNames,
		title:'系统信息'
	});
	var cpuInfoPanel=Ext.create('Leon.desktop.monitor.CpuMemInfoPanel',{
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

	
	var task={
		run:function(){
			//console.log(11);
			Ext.Ajax.request({
				method:'POST',
				url:Ext.ContextPath+"/monitorSystem/querySystemInfo",
				success:function(response){
					var obj=Ext.decode(response.responseText);
					systemInfoPanel.getForm().setValues(obj.root.systemInfo);
					systemInfoPanel.getForm().setValues(obj.root.cpuInfo);
					systemInfoPanel.getForm().setValues(obj.root.memoryInfo);
					systemInfoPanel.getForm().setValues(obj.root.fileSystemInfo);
					systemInfoPanel.getForm().setValues(obj.root.netInfo);
				}
			});
		},
		interval:10000//10s
		//duration:10000
	}
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'fit',
		items:[tabPanel],
		listeners:{
			afterrender:function(){
				Ext.TaskManager.start(task);
			}
		}
	});
}	

});