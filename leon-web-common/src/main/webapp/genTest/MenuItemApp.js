//Ext.require("Leon.genTest.MenuItem");
//Ext.require("Leon.menu.MenuItemGrid");
Ext.require("Leon.genTest.MenuItemTree");
Ext.require("Leon.genTest.MenuItemForm");
Ext.onReady(function(){
	var grid=Ext.create('Leon.genTest.MenuItemGrid',{
		region:'west',
		split: true,
		collapsible: true,
		title:'菜单',
		width:200
	});

	var tree=Ext.create('Leon.genTest.MenuItemTreeQuery',{
		title:'菜单树',
		region:'center'
	});

	var form=Ext.create('Leon.genTest.MenuItemForm',{
		region:'east',
		split: true,
		collapsible: true,
		title:'表单',
		width:460
	});
	
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'border',
		items:[grid,tree,form]
	});

});