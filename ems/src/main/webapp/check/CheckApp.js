Ext.require("Ems.check.Check");
Ext.require("Ems.check.CheckGrid");
Ext.onReady(function(){
	var grid=Ext.create('Ems.check.CheckGrid',{
		title:'盘点单',
		width:400,
		split:true,
		collapsible : true,
		region:'west'
	});
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'border',
		items:[grid,{region:'center',html:"请填写对应的内容!"}]
	});



});