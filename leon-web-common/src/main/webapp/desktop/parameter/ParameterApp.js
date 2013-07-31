Ext.require('Leon.desktop.parameter.ParameterGrid');
Ext.require('Leon.desktop.parameter.ParameterWindow')
Ext.onReady(function(){
	var grid=Ext.create('Leon.desktop.parameter.ParameterGrid',{});
	
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'fit',
		items:[grid]
	});
});