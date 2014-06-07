Ext.require("Leon.panera.continents.Country");
Ext.require("Leon.panera.continents.CountryGrid");
//Ext.require("Leon.continents.CountryTree");
//Ext.require("Leon.continents.CountryForm");
Ext.onReady(function(){
	var grid=Ext.create('Leon.panera.continents.CountryGrid',{
		//region:'west',
		split: true,
		collapsible: true,
		title:'XXX表格'
	});

//
//	var form=Ext.create('Leon.panera.continents.CountryForm',{
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
	grid.on('itemclick',function(view,record,item,index){
		//var basicForm=form.getForm();
		//form.loadRecord(record);
	});
	
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'fit',
		items:[grid]
	});

});