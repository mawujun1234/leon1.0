Ext.require("Leon.desktop.org.OrgDimenssion");
Ext.require("Leon.desktop.org.OrgDimenssionGrid");
Ext.require("Leon.desktop.org.OrgDimenssionForm");
Ext.onReady(function(){
	var grid=Ext.create('Leon.desktop.org.OrgDimenssionGrid',{
		region:'west',
		split: true,
		collapsible: true,
		title:'菜单',
		width:400
	});

	var form=Ext.create('Leon.desktop.org.OrgDimenssionForm',{
		region:'center',
		split: true,
		collapsible: true,
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
		var basicForm=form.getForm();
		basicForm.loadRecord(record);
	});
	
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'border',
		items:[grid,form]
	});

});