//Ext.require('Ext.ux.IFrame');
//Ext.require('Leon.desktop.example.MenuUpdateText');
Ext.onReady(function(){
	//alert(1);
	Ext.Ajax.request({
		url:'/app/desktop/query',
		headers:{ 'Accept':'application/json;'},
		success:function(response){
			var obj=Ext.decode(response.responseText);
			window.desktop=Ext.create('Leon.desktop.Desktop',{
				initMenus:obj.root.menuItems
				,switchUsers:obj.root.switchUsers?obj.root.switchUsers:null,
				authMsg:obj.root.authMsg
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