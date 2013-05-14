<#assign simpleClassNameFirstLower = simpleClassName?uncap_first> 
<#-- //所在模块-->
<#assign module = basepackage?substring(basepackage?last_index_of(".")+1)> 

//在创建具有关联数据的时候 使用Ext.createModel();
//或者在创建模型数据的时候就使用这个
Ext.define("Ext.${module}.${simpleClassName}",{
	extend:"Ext.app.SimpleModel",
	idProperty:'${idPropertyName}',
	fields:[
	<#list baseTypePropertyColumns as propertyColumn>
	<#if propertyColumn.isBaseType==true>
		<#if propertyColumn.jsType=='date'>
		{name:'${propertyColumn.property}',type:'${propertyColumn.jsType!"auto"}', dateFormat: 'Y-m-d'}<#if propertyColumn_has_next>,</#if>
		<#else>
		{name:'${propertyColumn.property}',type:'${propertyColumn.jsType!"auto"}'}<#if propertyColumn_has_next>,</#if>
		</#if>
	</#if>
	</#list>      
	],
	belongsTo:[
	<#list propertyColumns as propertyColumn>
	<#if propertyColumn.isAssociationType==true>
		{model: 'Ext.${propertyColumn.basepackage?substring(propertyColumn.basepackage?last_index_of(".")+1)}.${propertyColumn.javaTypeClassName}',associationKey: '${propertyColumn.javaTypeClassName?uncap_first}',associatedName:'${propertyColumn.javaTypeClassName?uncap_first}',getterName:'get${propertyColumn.javaTypeClassName}'}<#if propertyColumn_has_next>,</#if>
	<#elseif propertyColumn.isComponentType==true>
		{model: 'Ext.${propertyColumn.basepackage?substring(propertyColumn.basepackage?last_index_of(".")+1)}.${propertyColumn.javaTypeClassName}',associationKey: '${propertyColumn.javaTypeClassName?uncap_first}',associatedName:'${propertyColumn.javaTypeClassName?uncap_first}',getterName:'get${propertyColumn.javaTypeClassName}'}<#if propertyColumn_has_next>,</#if>
	</#if>
	</#list>
	],
	//belongsTo:  {model: 'ExampleType',associationKey: 'exampleType',associatedName:'exampleType',getterName:'getExampleType'},
	api: {
		create  : Ext.ContextPath+'/${simpleClassNameFirstLower}/insert.do',
		read    : Ext.ContextPath+'/${simpleClassNameFirstLower}/qeryPage.do',
		load    : Ext.ContextPath+'/${simpleClassNameFirstLower}/get.do',//在load的时候指定
		update  : Ext.ContextPath+'/${simpleClassNameFirstLower}/update.do',
		destroy : Ext.ContextPath+'/${simpleClassNameFirstLower}/delete.do'
	}
});