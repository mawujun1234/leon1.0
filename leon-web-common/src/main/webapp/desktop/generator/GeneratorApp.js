Ext.require('Leon.desktop.generator.GeneratorForm');
Ext.onReady(function(){
	var form=Ext.create('Leon.desktop.generator.GeneratorForm',{
	});
	
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'fit',
		items:[form]
	});

});