//Ext.require('Ext.ux.IFrame');
//Ext.require('Leon.desktop.example.MenuUpdateText');
Ext.onReady(function(){
	//alert(1);
	Ext.Ajax.request({
		url:'/menuItem/query4Desktop?menuId=default',
		success:function(response){
			var obj=Ext.decode(response.responseText);
			var desktop=Ext.create('Leon.desktop.Desktop',{
				initMenus:obj.root
			});
			Ext.create('Ext.container.Viewport',{
				layout:'fit',
				border:false,
				items:[desktop]
			});
			
		}
	});
//	var desktop=Ext.create('Leon.desktop.Desktop');
//	Ext.create('Ext.container.Viewport',{
//		layout:'fit',
//		border:false,
//		items:[desktop]
//	});
});