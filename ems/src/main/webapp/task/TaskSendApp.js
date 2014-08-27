Ext.require("Ems.task.Task");
Ext.require("Ems.task.TaskSendGrid");
//Ext.require("Ems.task.TaskTree");
//Ext.require("Ems.task.TaskForm");
Ext.onReady(function(){
	var grid=Ext.create('Ems.task.TaskSendGrid',{
		region:'west',
		split: true,
		//collapsible: true,
		//title:'XXX表格',
		width:400
	});

//	var tree=Ext.create('Ems.task.TaskTree',{
//		title:'树',
//		width:400,
//		region:'west'
//	});
//
//	var form=Ext.create('Ems.task.TaskForm',{
//		region:'center',
//		split: true,
//		//collapsible: true,
//		title:'表单',
//		listeners:{
//			saved:function(){
//				grid.getStore().reload();
//			}
//		}
//	});
//	grid.form=form;
//	form.grid=grid;
//	grid.on('itemclick',function(view,record,item,index){
//		//var basicForm=form.getForm();
//		form.loadRecord(record);
//	});
	
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'fit',
		items:[grid]
	});

});