Ext.require("Leon.panera.customer.Customer");
Ext.require("Leon.panera.customer.CustomerGrid");
//Ext.require("Leon.panera.customer.CustomerTree");
//Ext.require("Leon.panera.customer.CustomerForm");

Ext.onReady(function(){
	var grid=Ext.create('Leon.panera.customer.CustomerGrid',{
		region:'center'
		//split: true,
		//collapsible: true
		//title:'XXX表格',
		//width:400
	});


//	var form=Ext.create('Leon.customer.CustomerForm',{
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
//	grid.on('itemclick',function(view,record,item,index){
//		//var basicForm=form.getForm();
//		form.loadRecord(record);
//	});
	
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'border',
		items:[grid]
	});

});

