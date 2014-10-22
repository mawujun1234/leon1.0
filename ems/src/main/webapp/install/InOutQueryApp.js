Ext.require("Ems.install.InOutVO");
Ext.require("Ems.install.InOutListVO");
Ext.require("Ems.install.InOutVOGridQuery");
Ext.require("Ems.install.InOutListVOGridQuery");

Ext.onReady(function(){
	var grid=Ext.create('Ems.install.InOutVOGridQuery',{
		region:'west',
		split: true,
		collapsible: true,
		title:'领用,返库单',
		width:400
	});

	var gridList=Ext.create('Ems.install.InOutListVOGridQuery',{
		region:'center',
		//split: true,
		//collapsible: true,
		title:'明细'
	});
	
	grid.on('itemclick',function(view,record,item,index){
		gridList.getStore().getProxy().extraParams={inOut_id:record.get("id"),type:grid.getStore().getProxy().extraParams.type};
		gridList.getStore().load();
	});
	
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'border',
		items:[grid,gridList]
	});

});