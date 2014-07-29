Ext.require("Ems.baseinfo.Brand");
Ext.require("Ems.baseinfo.BrandGrid");
//Ext.require("Ems.baseinfo.BrandTree");
Ext.require("Ems.baseinfo.BrandForm");
Ext.onReady(function(){
	var grid=Ext.create('Ems.baseinfo.BrandGrid',{
		region:'west',
		split: true,
		collapsible: true,
		title:'XXX表格',
		width:400
	});

//	var tree=Ext.create('Ems.baseinfo.BrandTree',{
//		title:'树',
//		width:400,
//		region:'west'
//	});

	var form=Ext.create('Ems.baseinfo.BrandForm',{
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