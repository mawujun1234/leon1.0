Ext.require("Ems.baseinfo.Project");
Ext.require("Ems.baseinfo.ProjectGrid");
Ext.require("Ems.baseinfo.ProjectForm");
Ext.onReady(function(){
	var grid=Ext.create('Ems.baseinfo.ProjectGrid',{
		region:'west',
		split: true,
		collapsible: true,
		title:'项目',
		width:400
	});

	var form=Ext.create('Ems.baseinfo.ProjectForm',{
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
	});
	
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'border',
		items:[grid,form]
	});

});