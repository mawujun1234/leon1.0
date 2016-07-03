<#assign simpleClassNameFirstLower = simpleClassName?uncap_first> 
<#-- //所在模块-->
<#assign module = basepackage?substring(basepackage?last_index_of(".")+1)> 
Ext.require("y.${module}.${simpleClassName}");
<#if extenConfig.extjs_treeForm_model==false>
Ext.require("y.${module}.${simpleClassName}Grid");
</#if>
<#if extenConfig.extjs_treeForm_model==true>
Ext.require("y.${module}.${simpleClassName}Tree");
</#if>
Ext.require("y.${module}.${simpleClassName}Form");
Ext.onReady(function(){
<#if extenConfig.extjs_treeForm_model==false>
	var grid=Ext.create('y.${module}.${simpleClassName}Grid',{
		region:'center',
		title:'XXX表格'
	});
	
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'border',
		items:[grid]
	});
</#if>
<#if extenConfig.extjs_treeForm_model==true>
	var tree=Ext.create('y.${module}.${simpleClassName}Tree',{
		title:'树',
		width:400,
		split:true,
		collapsible : true,
		region:'west'
	});
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'border',
		items:[tree,{region:'center',html:"请填写对应的内容!"}]
	});
</#if>



});