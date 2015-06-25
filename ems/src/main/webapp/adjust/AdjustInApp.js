Ext.require("Ems.adjust.Adjust");
Ext.require("Ems.adjust.AdjustInGrid");
Ext.require("Ems.adjust.AdjustListInGrid");
//Ext.require("Ems.adjust.AdjustForm");
Ext.onReady(function(){
	var grid=Ext.create('Ems.adjust.AdjustInGrid',{
		region:'west',
		split: true,
		collapsible: true,
		title:'调拨单',
		width:410
	});

	var gridList=Ext.create('Ems.adjust.AdjustListInGrid',{
		region:'center',
		//split: true,
		//collapsible: true,
		title:'调拨单明细'
	});
	
	grid.on('itemclick',function(view,record,item,index){
		gridList.getStore().load({params:{adjust_id:record.get("id")}});
		gridList.adjust_id=record.get("id");
	});
	
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'border',
		items:[grid,gridList]
	});

});