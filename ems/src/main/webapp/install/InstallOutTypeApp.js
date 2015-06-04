Ext.require("Ems.install.InstallOutType");
Ext.require("Ems.install.InstallOutTypeGrid");
//Ext.require("Ems.install.InstallOutTypeTree");
Ext.require("Ems.install.InstallOutTypeForm");
Ext.onReady(function(){
	var grid=Ext.create('Ems.install.InstallOutTypeGrid',{
		region:'west',
		split: true,
		collapsible: true,
		title:'XXX表格',
		width:400
	});

//	var tree=Ext.create('Ems.install.InstallOutTypeTree',{
//		title:'树',
//		width:400,
//		region:'west'
//	});

	var form=Ext.create('Ems.install.InstallOutTypeForm',{
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