Ext.require("Ems.baseinfo.Supplier");
Ext.require("Ems.baseinfo.SupplierGrid");
Ext.require("Ems.baseinfo.SupplierContactGrid");
Ext.require("Ems.baseinfo.SupplierForm");
Ext.onReady(function(){
	var grid=Ext.create('Ems.baseinfo.SupplierGrid',{
		region:'west',
		split: true,
		collapsible: true,
		title:'供应商',
		width:500
	});

//	var tree=Ext.create('Ems.baseinfo.SupplierTree',{
//		title:'树',
//		width:400,
//		region:'west'
//	});

	var form=Ext.create('Ems.baseinfo.SupplierForm',{
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
		
		supplierContactGrid.supplier_id=record.get("id");
		supplierContactGrid.getStore().load({params:{supplier_id:record.get("id")}})
	});
	
	
	var supplierContactGrid=Ext.create('Ems.baseinfo.SupplierContactGrid',{
		region:'south',
		split: true,
		height:200,
		collapsible: true,
		title:'联系方式'
	});
	
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'border',
		items:[grid,form,supplierContactGrid]
	});

});