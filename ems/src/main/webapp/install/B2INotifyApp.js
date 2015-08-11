Ext.require("Ems.install.B2INotify");
Ext.require("Ems.install.B2INotifyGrid");
Ext.onReady(function(){
	var grid=Ext.create('Ems.install.B2INotifyGrid',{
		region:'west',
		split: true,
		//collapsible: true,
		//title:'XXX表格',
		width:400
	});

//	var form=Ext.create('Ems.install.B2INotifyForm',{
//		region:'center',
//		split: true,
//		//collapsible: true,
//		title:'表单',
//		listeners:{
//			saved:function(){
//				grid.getStore().reload();
//			}
//		}
//	});
//	grid.form=form;
//	form.grid=grid;
	
	
	
	grid.on('itemclick',function(view,record,item,index){
		//var basicForm=form.getForm();
		//form.loadRecord(record);
	});
	
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'fit',
		items:[grid]
	});

});