Ext.require("Ems.store.Order");
Ext.require("Ems.store.OrderList");
Ext.require("Ems.store.OrderGrid");
Ext.require("Ems.store.OrderListGrid");

Ext.onReady(function(){
	var grid=Ext.create('Ems.store.OrderGrid',{
		region:'west',
		split: true,
		collapsible: true,
		title:'订单',
		width:400
	});

	var gridList=Ext.create('Ems.store.OrderListGrid',{
		region:'center',
		//split: true,
		//collapsible: true,
		title:'订单明细'
	});
	
	grid.on('itemclick',function(view,record,item,index){
		//gridList.getStore().load({params:{orderNo:record.get("orderNo")}});
		gridList.getStore().getProxy().extraParams={order_id:record.get("id")};
		gridList.getStore().load();
		gridList.order_status=record.get("status")
	});
	
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'border',
		items:[grid,gridList]
	});

});