Ext.require("Ems.install.InstallIn");
Ext.require("Ems.install.InstallInList");
Ext.require("Ems.install.InstallInGrid");
Ext.require("Ems.install.InstallInListGrid");

Ext.onReady(function(){
	var grid=Ext.create('Ems.install.InstallInGrid',{
		region:'west',
		split: true,
		collapsible: true,
		title:'返库单',
		width:400
	});

	var gridList=Ext.create('Ems.install.InstallInListGrid',{
		region:'center',
		//split: true,
		//collapsible: true,
		title:'明细'
	});
	
	grid.on('itemclick',function(view,record,item,index){
		gridList.getStore().getProxy().extraParams={installIn_id:record.get("id")};
		gridList.getStore().load();
	});
	
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'border',
		items:[grid,gridList]
	});

});