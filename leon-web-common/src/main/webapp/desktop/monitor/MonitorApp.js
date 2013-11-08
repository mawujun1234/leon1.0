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
					
					//++
					//console.log(obj.root.cpuInfo.cpu_combined);
					console.log(obj.root.cpuInfo.cpu_combined.replace("%",""));
					var cpu_combineds=obj.root.cpuInfo.cpu_combined.split('.');
					cpuInfoPanel.addData({
						mem_used:(obj.root.memoryInfo.mem_used/obj.root.memoryInfo.mem_total)*100,
						cpu_combined:parseInt(cpu_combineds[0])//obj.root.cpuInfo.cpu_combined.replace("%",""),
						//cpu_userTime:obj.root.cpuInfo.cpu_userTime,
						//cpu_sysTime:obj.root.cpuInfo.cpu_sysTime
					});
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