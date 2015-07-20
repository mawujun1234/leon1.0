Ext.require("Ems.task.Task");
Ext.require("Ems.task.TaskConfirmGrid");
Ext.require('Ems.task.TaskEquipmentListGrid')
//Ext.require("Ems.task.TaskTree");
//Ext.require("Ems.task.TaskForm");
Ext.onReady(function(){

	
	var grid=Ext.create('Ems.task.TaskConfirmGrid',{
		region:'west',
		split: true,
		//collapsible: true,
		//title:'XXX表格',
		width:400
	});
	

	var gridList=Ext.create('Ems.task.TaskEquipmentListGrid',{
		region:'center',
		split: true
	});

	grid.on('itemclick',function(view,record,item,index){
		gridList.getStore().getProxy().extraParams={task_id:record.get("id")};
		gridList.getStore().load();
	});
	
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'border',
		items:[grid,list],
		listeners:{
			render:function(){

				
			}
		}
	});

});