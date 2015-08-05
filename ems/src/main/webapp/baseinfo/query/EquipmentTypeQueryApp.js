Ext.require("Ems.baseinfo.EquipmentType");
Ext.require("Ems.baseinfo.query.EquipmentProdQueryGrid");
Ext.require("Ems.baseinfo.query.EquipmentTypeQueryTree");
Ext.onReady(function(){
	var grid=Ext.create('Ems.baseinfo.query.EquipmentProdQueryGrid',{
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

	var tree=Ext.create('Ems.baseinfo.query.EquipmentTypeQueryTree',{
		title:'大类小类',
		width:400,
		split:true,
		collapsible: true,
		region:'west'
	});
	grid.tree=tree;
	
	tree.on("itemclick",function(panel,record, item, index, e){

		
		if(record.get("parent_id")){
			grid.getEl().unmask();

			grid.getStore().getProxy().extraParams={
				subtype_id:record.get("id"),
				status:true
			};
			grid.clearStyleAll_prod();
			grid.getStore().load();
			grid.subtype_name=record.get("text");
		} else {
			grid.getEl().mask();
			//grid.getStore().removeAll();
		}
		
	});

	
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'border',
		items:[grid,tree]
	});

});