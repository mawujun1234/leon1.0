Ext.require("Ems.repair.Scrap");
Ext.require("Ems.repair.ScrapGrid");
Ext.onReady(function(){
	var grid=Ext.create('Ems.repair.ScrapGrid',{
		//region:'west',
		//split: true,
		//collapsible: true,
		//title:'XXX表格',
		//width:400
	});

//	var tree=Ext.create('Ems.repair.ScrapTree',{
//		title:'树',
//		width:400,
//		region:'west'
//	});
//
//	var form=Ext.create('Ems.repair.ScrapForm',{
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