Ext.require('Leon.LoginWin');
Ext.onReady(function(){
	var form=Ext.create('Leon.LoginWin',{});
	form.show();
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'fit',
		items:[{
			xtype:'panel',
			//html:1111,
			frame:true
		}]
	});
});