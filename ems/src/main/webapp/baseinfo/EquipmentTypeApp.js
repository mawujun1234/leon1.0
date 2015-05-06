Ext.require("Ems.baseinfo.EquipmentType");
Ext.require("Ems.baseinfo.EquipmentTypeGrid");
Ext.require("Ems.baseinfo.EquipmentTypeTree");
Ext.require("Ems.baseinfo.EquipmentTypeForm");
Ext.onReady(function(){
	var grid=Ext.create('Ems.baseinfo.EquipmentTypeGrid',{
		region:'center',
		//split: true,
		//collapsible: true,
		title:'品名',
		//width:400,
		listeners:{
			render:function(grid){
				grid.getEl().mask();
			}
		}
	});

	var tree=Ext.create('Ems.baseinfo.EquipmentTypeTree',{
		title:'大类小类',
		width:400,
		split:true,
		collapsible: true,
		region:'west'
	});
	grid.tree=tree;
	
	tree.on("itemclick",function(panel,record, item, index, e){
//		if(record.get("levl")==0){
//			grid.setTitle("大类管理");
//		} else if(record.get("levl")==1){
//			grid.setTitle("\""+record.get("text")+"\"下的所有小类");
//		} else if(record.get("levl")==2){
//			grid.setTitle("\""+record.get("text")+"\"下的所有品名");
//		}
//		var aa=grid.getSelectionModel( ).getLastSelected( );
//		console.dir(aa);
//		grid.getStore().load({params:{
//			id:record.get("id").split("_")[0],
//			levl:record.get("levl")
//		}});
		
		if(record.get("levl")==2){
			grid.getEl().unmask();
			//var aa=grid.getSelectionModel( ).getLastSelected( );
			grid.getStore().load({params:{
				id:record.get("id"),//.split("_")[0],
				levl:record.get("levl")
			}});
			grid.subtype_name=record.get("text");
		} else {
			grid.getEl().mask();
			grid.getStore().removeAll();
		}
		
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