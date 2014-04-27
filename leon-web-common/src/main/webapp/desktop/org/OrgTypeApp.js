Ext.require("Leon.desktop.org.OrgType");
Ext.require("Leon.desktop.org.OrgTypeGrid");
Ext.require("Leon.desktop.org.OrgTypeForm");
Ext.onReady(function(){
	var grid=Ext.create('Leon.desktop.org.OrgTypeGrid',{
		region:'west',
		split: true,
		collapsible: true,
		title:'组织类型',
		width:400
	});
	


	var form=Ext.create('Leon.desktop.org.OrgTypeForm',{
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