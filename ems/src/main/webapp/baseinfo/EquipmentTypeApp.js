Ext.require("Ems.baseinfo.EquipmentType");
Ext.require("Ems.baseinfo.EquipmentTypeGrid");
Ext.require("Ems.baseinfo.EquipmentTypeTree");
Ext.require("Ems.baseinfo.EquipmentTypeForm");
Ext.onReady(function(){
	var grid=Ext.create('Ems.baseinfo.EquipmentTypeGrid',{
		region:'center',
		split: true,
		collapsible: true,
		title:'大类',
		width:400
	});

	var tree=Ext.create('Ems.baseinfo.EquipmentTypeTree',{
		title:'大类小类',
		width:400,
		region:'west'
	});
	grid.tree=tree;
	
	tree.on("itemclick",function(panel,record, item, index, e){
		if(record.get("levl")==0){
			grid.setTitle("大类");
		} else if(record.get("levl")==1){
			grid.setTitle("小类");
		} else if(record.get("levl")==2){
			grid.setTitle("品名");
		}
		var aa=grid.getSelectionModel( ).getLastSelected( );
		console.dir(aa);
		grid.getStore().load({params:{
			id:record.get("id").split("_")[0],
			levl:record.get("levl")
		}});
		
	});
//
//	var form=Ext.create('Ems.baseinfo.EquipmentTypeForm',{
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
		items:[grid,tree]
	});

});