Ext.require("Ems.baseinfo.Store");
Ext.require("Ems.baseinfo.StoreGrid");
//Ext.require("Ems.baseinfo.StoreTree");
Ext.require("Ems.baseinfo.StoreForm");
Ext.onReady(function(){
	var grid=Ext.create('Ems.baseinfo.StoreGrid',{
		region:'west',
		split: true,
		collapsible: true,
		title:'XXX表格',
		width:400
	});

//	var tree=Ext.create('Ems.baseinfo.StoreTree',{
//		title:'树',
//		width:400,
//		region:'west'
//	});

	var form=Ext.create('Ems.baseinfo.StoreForm',{
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