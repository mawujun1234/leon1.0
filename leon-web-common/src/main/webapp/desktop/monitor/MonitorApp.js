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
		items:[tabPanel,{
			title:'CPU信息'
			,html:'一个会动的图，然后一个form，里面有基本的信息，例如频率，厂商等，参考win8任务管理'
		},{
			title:'内存信息'
			,html:'一个会动的图，然后一个form，里面有基本的信息，例如总大小等，参考win8任务管理'
		},{
			title:'磁盘信息'
			,html:'一个会动的图，然后一个form，里面有基本的信息，例如有哪些盘，大小，可用，已用等等，参考win8任务管理'
		},{
			title:'网络信息'
			,html:'一个会动的图，然后一个form，里面有基本的信息，例如有哪些网络接口等，参考win8任务管理'
		}]
	});
	

	
});