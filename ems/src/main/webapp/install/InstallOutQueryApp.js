Ext.require("Ems.install.InstallOut");
Ext.require("Ems.install.InstallOutList");
Ext.require("Ems.install.InstallOutGrid");
Ext.require("Ems.install.InstallOutListGrid");

Ext.onReady(function(){
	var grid=Ext.create('Ems.install.InstallOutGrid',{
		region:'west',
		split: true,
		collapsible: true,
		title:'领用查询',
		width:400
	});

	var gridList=Ext.create('Ems.install.InstallOutListGrid',{
		region:'center',
		//split: true,
		//collapsible: true,
		title:'明细'
	});
	
	grid.on('itemclick',function(view,record,item,index){
		gridList.getStore().getProxy().extraParams={installOut_id:record.get("id")};
		gridList.getStore().load();
	});
	
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'border',
		items:[grid,gridList]
	});

});