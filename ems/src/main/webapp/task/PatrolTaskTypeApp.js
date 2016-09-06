Ext.require("Ems.task.PatrolTaskType");
Ext.require("Ems.task.PatrolTaskTypeForm");
Ext.onReady(function(){
	var grid=Ext.create('Ems.task.PatrolTaskTypeGrid',{
		//title:'树',
		//width:400,
		//split:true,
		//collapsible : true,
		region:'center'
	});
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'border',
		items:[grid]
	});



});