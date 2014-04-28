Ext.require("Leon.desktop.org.Org");
Ext.require("Leon.desktop.org.OrgTree");
Ext.require("Leon.desktop.org.OrgForm");
Ext.onReady(function(){
	var tree=Ext.create('Leon.desktop.org.OrgTree',{
		title:'组织树',
		width:400,
		region:'west'
	});
	tree.on('itemclick',function(view,record,item,index){

		var basicForm=form.getForm();
		basicForm.loadRecord(record);
		
		var parent=tree.getStore().getNodeById(record.get("parent_id"));
		if(parent){
			basicForm.setValues({"parent_text":parent.get("text")}) ;
		} else {
			basicForm.setValues({"parent_text":null}) ;
		}

		if(record.get("fun_id")){
			basicForm.findField("fun_text").setValue(record.getFun().get("text"));//.setValues({"fun_text":record.getFun().get("text")}) ;
		} else {
			basicForm.findField("fun_text").setValue("") ;
		}

	});

	var form=Ext.create('Leon.desktop.org.OrgForm',{
		region:'center',
		split: true,
		collapsible: true,
		title:'表单',
		listeners:{
			saved:function(){
				grid.getStore().reload();
			}
		}
	});
//	grid.form=form;
//	form.grid=grid;
//	grid.on('itemclick',function(view,record,item,index){
//		var basicForm=form.getForm();
//		basicForm.loadRecord(record);
//	});
	
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'border',
		items:[tree,form]
	});

});