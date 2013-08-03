Ext.require('Leon.desktop.parameter.ParameterUtils');
Ext.onReady(function(){
	var utils=new Leon.desktop.parameter.ParameterUtils();
	utils.getForm('SYSTEM',function(form){
		var viewPort=Ext.create('Ext.container.Viewport',{
			layout:'fit',
			items:[form]
		});
		utils.setSubjectId("SYSTEM");
	});
	
	
});