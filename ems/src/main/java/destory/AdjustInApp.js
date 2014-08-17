Ext.require("Ems.adjust.Adjust");
Ext.require("Ems.adjust.AdjustGrid");
Ext.require("Ems.adjust.AdjustListGrid");
//Ext.require("Ems.adjust.AdjustForm");
Ext.onReady(function(){
	var grid=Ext.create('Ems.adjust.AdjustGrid',{
		region:'west',
		split: true,
		collapsible: true,
		title:'调拨单',
		width:400
	});

	var gridList=Ext.create('Ems.adjust.AdjustListGrid',{
		region:'center',
		//split: true,
		//collapsible: true,
		title:'调拨单明细'
	});
	
	grid.on('itemclick',function(view,record,item,index){
		gridList.getStore().load({params:{adjust_id:record.get("id")}});
	});
	
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'border',
		items:[grid,gridList]
	});

});