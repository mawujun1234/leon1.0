<#assign simpleClassNameFirstLower = simpleClassName?uncap_first> 
<#-- //所在模块-->
<#assign module = basepackage?substring(basepackage?last_index_of(".")+1)> 
Ext.require("Leon.${module}.${simpleClassName}");
Ext.require("Leon.${module}.${simpleClassName}Grid");
Ext.require("Leon.${module}.${simpleClassName}Tree");
Ext.require("Leon.${module}.${simpleClassName}Form");
Ext.onReady(function(){
	var grid=Ext.create('Leon.${module}.${simpleClassName}Grid',{
		region:'west',
		split: true,
		collapsible: true,
		title:'菜单',
		width:200
	});

	var tree=Ext.create('Leon.${module}.${simpleClassName}Tree',{
		title:'菜单树',
		region:'center'
	});

	var form=Ext.create('Leon.${module}.${simpleClassName}Form',{
		region:'center',
		split: true,
		collapsible: true,
		title:'表单',
		width:460
	});
	grid.form=form;
	form.grid=grid;
	grid.on('itemclick',function(view,record,item,index){
		var basicForm=form.getForm();
		basicForm.loadRecord(record);
	});
	
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'border',
		items:[grid,tree,form]
	});

});