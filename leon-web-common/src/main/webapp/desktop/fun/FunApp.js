Ext.require('Leon.desktop.fun.FunTree');
Ext.require('Leon.desktop.fun.FunForm');
//Ext.require('Leon.common.ux.BaseAjax');//這個必須要加的
//Ext.require('Leon.common.ux.BaseTree');//這個必須要加的
Ext.onReady(function(){
	var tree=Ext.create('Leon.desktop.fun.FunTree',{
		region:'west',
		split: true,
		collapsible: true,
		title:'功能树',
		width:400
	});

	var form=Ext.create('Leon.desktop.fun.FunForm',{
		region:'center'
	});
	tree.on('itemclick',function(view,record,item,index){
		//alert(1);
		form.getForm().loadRecord(record);
	});
	
	
	
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'border',
		items:[tree,form]
	});

});