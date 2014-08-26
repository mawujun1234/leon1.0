Ext.require("Ems.baseinfo.Customer");
Ext.require("Ems.baseinfo.CustomerGrid");
Ext.require("Ems.baseinfo.PoleGrid");
Ext.require("Ems.baseinfo.CustomerContactGrid");
Ext.require("Ems.baseinfo.CustomerForm");
Ext.onReady(function(){
	var grid=Ext.create('Ems.baseinfo.CustomerGrid',{
		region:'west',
		split: true,
		collapsible: true,
		title:'客户列表',
		width:400
	});
	grid.on('itemclick',function(view,record,item,index){
		
		customercontactgrid.customer_id=record.get("id");
		customercontactgrid.getStore().load({params:{customer_id:record.get("id")}});
		
		poleGrid.customer_id=record.get("id");
		poleGrid.getStore().load({params:{customer_id:record.get("id")}});
	});
	var customercontactgrid=Ext.create('Ems.baseinfo.CustomerContactGrid',{
		region:'north',
		split: true,
		height:200,
		collapsible: true,
		title:'联系方式'
	});
	var poleGrid=Ext.create('Ems.baseinfo.PoleGrid',{
		region:'center',
		split: true,
		height:200,
		collapsible: true,
		title:'杆位管理'
	});


//	var form=Ext.create('Ems.baseinfo.CustomerForm',{
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
		items:[grid,{region:'center',layout:'border',
			items:[customercontactgrid,poleGrid]
		}]
	});

});