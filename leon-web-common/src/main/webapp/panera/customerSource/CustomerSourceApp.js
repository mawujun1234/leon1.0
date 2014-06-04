Ext.require("Leon.panera.customerSource.CustomerSource");
Ext.require("Leon.panera.customerSource.CustomerSourceGrid");
Ext.require("Leon.panera.customerSource.CustomerSourceForm");
Ext.onReady(function(){
	var grid=Ext.create('Leon.panera.customerSource.CustomerSourceGrid',{
		region:'west',
		split: true,
		collapsible: true,
		title:'菜单',
		width:400
	});


	var form=Ext.create('Leon.panera.customerSource.CustomerSourceForm',{
		region:'center',
		split: true,
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