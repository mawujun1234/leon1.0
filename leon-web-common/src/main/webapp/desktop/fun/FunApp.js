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
	
	form.down("button[action=createChild]").on('click',function(btn){
		//btn.up('form').getForm().reset();
		form.getForm().reset();
		var fun=tree.getSelectionModel( ).getLastSelected( ) ;
		form.getForm().setValues('parent_id',fun.get("id"));
	});
});