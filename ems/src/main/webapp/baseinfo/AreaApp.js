Ext.require("Ems.baseinfo.Area");
Ext.require("Ems.baseinfo.AreaGrid");
Ext.require("Ems.baseinfo.AreaPoleGrid");
Ext.require("Ems.baseinfo.AreaForm");
Ext.onReady(function(){
	var grid=Ext.create('Ems.baseinfo.AreaGrid',{
		region:'west',
		split: true,
		collapsible: true,
		title:'片区管理',
		width:300
	});


	grid.on('itemclick',function(view,record,item,index){
		areaPoleGrid.area_id=record.get("id");
		
		areaPoleGrid.getStore().getProxy().extraParams={area_id:record.get("id")};
		areaPoleGrid.getStore().reload();
	});
	
	var areaPoleGrid=Ext.create('Ems.baseinfo.AreaPoleGrid',{
		region:'center',
		split: true,
		collapsible: true,
		title:'管理的杆位'
	});
	
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'border',
		items:[grid,areaPoleGrid]
	});

});