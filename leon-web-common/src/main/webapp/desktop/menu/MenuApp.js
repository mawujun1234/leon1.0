Ext.require('Leon.desktop.menu.MenuItemTree');
Ext.require('Leon.desktop.menu.MenuItemForm');
Ext.require('Leon.desktop.menu.MenuGrid');
Ext.onReady(function(){
	var grid=Ext.create('Leon.desktop.menu.MenuGrid',{
		region:'west',
		split: true,
		collapsible: true,
		title:'菜单',
		width:200
	});
	grid.on('itemclick',function(view,record,item,index){
		//alert(tree.getMenuId());
		tree.setMenuId(record.get("id"));
		tree.getStore().reload();
		tree.getRootNode( ).set("text",record.get("text"))
	});

	var tree=Ext.create('Leon.desktop.menu.MenuItemTree',{
		title:'菜单树',
		region:'center'
	});
	tree.on('itemclick',function(view,record,item,index){
		//alert(1);
		form.getForm().loadRecord(record);
		var parent=tree.getStore().getNodeById(record.get("parent_id"));
		if(parent){
			form.getForm().setValues({"parent_text":parent.get("text")}) ;
		} else {
			form.getForm().setValues({"parent_text":null}) ;
		}
		
	});
	var form=Ext.create('Leon.desktop.menu.MenuItemForm',{
		region:'east',
		split: true,
		collapsible: true,
		title:'表单',
		width:520
	});
	
	
	
	
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'border',
		items:[grid,tree,form]
	});
	
	form.on("created",function(record){
		var fun=tree.getSelectionModel().getLastSelected( ) ;
		tree.getStore().load({node:fun});
	});
	
});