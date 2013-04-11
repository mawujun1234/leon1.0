Ext.require('Ext.ux.IFrame');
//Ext.require('Leon.desktop.example.MenuUpdateText');
Ext.onReady(function(){
	//alert(1);
	var desktop=Ext.create('Leon.desktop.Desktop');
	Ext.create('Ext.container.Viewport',{
		layout:'fit',
		border:false,
		items:[desktop]
	});
});