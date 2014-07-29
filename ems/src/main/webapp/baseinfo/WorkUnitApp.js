Ext.require("Ems.baseinfo.WorkUnit");
Ext.require("Ems.baseinfo.WorkUnitGrid");
Ext.require("Ems.baseinfo.WorkUnitContactGrid");
Ext.require("Ems.baseinfo.WorkUnitForm");
Ext.onReady(function(){
	var grid=Ext.create('Ems.baseinfo.WorkUnitGrid',{
		region:'west',
		split: true,
		collapsible: true,
		title:'作业单位',
		width:400
	});

//	var tree=Ext.create('Ems.baseinfo.WorkUnitTree',{
//		title:'树',
//		width:400,
//		region:'west'
//	});

	var form=Ext.create('Ems.baseinfo.WorkUnitForm',{
		region:'center',
		split: true,
		//collapsible: true,
		title:'表单',
		listeners:{
			saved:function(){
				grid.getStore().reload();
			}
		}
	});
	grid.form=form;
	form.grid=grid;
	grid.on('itemclick',function(view,record,item,index){
		//var basicForm=form.getForm();
		form.loadRecord(record);
		
		workUnitContactGrid.workunit_id=record.get("id");
		workUnitContactGrid.getStore().load({params:{workunit_id:record.get("id")}})
	});
	
	var workUnitContactGrid=Ext.create('Ems.baseinfo.WorkUnitContactGrid',{
		region:'south',
		split: true,
		collapsible: true,
		title:'联系方式',
		height:200
	});
	
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'border',
		items:[grid,form,workUnitContactGrid]
	});

});