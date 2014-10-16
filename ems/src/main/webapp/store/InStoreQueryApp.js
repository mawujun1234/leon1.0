Ext.require("Ems.store.InStore");
Ext.require("Ems.store.InStoreList");
Ext.require("Ems.store.InStoreGridQuery");
Ext.require("Ems.store.InStoreListGridQuery");

Ext.onReady(function(){
	var grid=Ext.create('Ems.store.InStoreGridQuery',{
		region:'west',
		split: true,
		collapsible: true,
		title:'入库单',
		width:400
	});

	var gridList=Ext.create('Ems.store.InStoreListGridQuery',{
		region:'center',
		//split: true,
		//collapsible: true,
		title:'入库单明细'
	});
	
	grid.on('itemclick',function(view,record,item,index){
		gridList.getStore().getProxy().extraParams={inStore_id:record.get("id")};
		gridList.getStore().load();
	});
	
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'border',
		items:[grid,gridList]
	});

});