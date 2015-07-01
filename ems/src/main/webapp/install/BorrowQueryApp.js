Ext.require("Ems.install.Borrow");
Ext.require("Ems.install.BorrowList");
Ext.require("Ems.install.BorrowGrid");
Ext.require("Ems.install.BorrowListGrid");

Ext.onReady(function(){
	var grid=Ext.create('Ems.install.BorrowGrid',{
		region:'west',
		split: true,
		collapsible: true,
		title:'借用查询',
		width:400
	});

	var gridList=Ext.create('Ems.install.BorrowListGrid',{
		region:'center',
		//split: true,
		//collapsible: true,
		title:'明细'
	});
	
	grid.on('itemclick',function(view,record,item,index){
		gridList.getStore().getProxy().extraParams={borrow_id:record.get("id")};
		gridList.getStore().load();
	});
	
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'border',
		items:[grid,gridList]
	});

});