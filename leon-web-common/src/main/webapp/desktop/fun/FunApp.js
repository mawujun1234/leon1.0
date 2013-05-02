Ext.require('Leon.desktop.fun.FunTree');
Ext.require('Leon.desktop.fun.FunForm');
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
		var parent=tree.getStore().getNodeById(record.get("parent_id"));
		if(parent){
			form.getForm().setValues({"parent_text":parent.get("text")}) ;
		} else {
			form.getForm().setValues({"parent_text":null}) ;
		}
		
	});
	
	
	
	
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'border',
		items:[tree,form]
	});
	
	form.on("created",function(record){
		var fun=tree.getSelectionModel( ).getLastSelected( ) ;
		tree.getStore().load({node:fun});
	});
	
//	form.down("button[action=createChild]").on('click',function(btn){
//		//btn.up('form').getForm().reset();
//		form.getForm().reset();
//		var fun=tree.getSelectionModel( ).getLastSelected( ) ;
//		form.getForm().setValues({'parent_id':fun.get("id"),'parent_text':fun.get("text")});
//	});
});