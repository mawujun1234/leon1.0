Ext.require("Ems.task.Task");
Ext.require("Ems.task.TaskQueryGrid");
//Ext.require("Ems.task.TaskTree");
//Ext.require("Ems.task.TaskForm");
Ext.onReady(function(){
	var grid=Ext.create('Ems.task.TaskQueryGrid',{
		region:'west',
		split: true,
		//collapsible: true,
		//title:'XXX表格',
		width:400
	});


	
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'fit',
		items:[grid]
	});

});