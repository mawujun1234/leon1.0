Ext.require("Ems.store.Order");
Ext.require("Ems.store.OrderList");
Ext.require("Ems.store.OrderGrid");
Ext.require("Ems.store.OrderListGrid");
Ext.require('Ems.store.OrderForm');

Ext.onReady(function(){
	var grid=Ext.create('Ems.store.OrderGrid',{
		region:'west',
		split: true,
		collapsible: true,
		//title:'订单',
		onlyRead:true,
		width:500
	});

	var gridList=Ext.create('Ems.store.OrderListGrid',{
		region:'center',
		//split: true,
		//collapsible: true,
		onlyRead:true,
		title:'订单明细'
	});
	
	grid.on('itemclick',function(view,record,item,index){
		//gridList.getStore().load({params:{orderNo:record.get("orderNo")}});
		gridList.getStore().getProxy().extraParams={order_id:record.get("id")};
		gridList.getStore().load();
		gridList.order_status=record.get("status")
		gridList.order_orderType=record.get("orderType");
		
		//如果是新品订单，隐藏后面的年，月，日3列
		if(record.get("orderType")=='new_equipment'){
			gridList.down('#depreci_year').hide();
			gridList.down('#depreci_month').hide();
			gridList.down('#depreci_day').hide();
		} else {
			gridList.down('#depreci_year').show();
			gridList.down('#depreci_month').show();
			gridList.down('#depreci_day').show();
		}
	});
	
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'border',
		items:[grid,gridList]
	});

});