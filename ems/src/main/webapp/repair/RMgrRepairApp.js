Ext.require("Ems.repair.Repair");
Ext.require("Ems.repair.RMgrRepairGrid");
//Ext.require("Ems.repair.RepairTree");
//Ext.require("Ems.repair.RepairForm");
Ext.onReady(function(){
	var grid=Ext.create('Ems.repair.RMgrRepairGrid',{
		//region:'west',
		//split: true,
		//collapsible: true,
		////title:'XXX表格'
	});

//	var tree=Ext.create('Ems.repair.RepairTree',{
//		title:'树',
//		width:400,
//		region:'west'
//	});
//
//	var form=Ext.create('Ems.repair.RepairForm',{
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