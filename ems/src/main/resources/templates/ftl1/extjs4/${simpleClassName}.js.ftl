<#assign simpleClassNameFirstLower = simpleClassName?uncap_first> 
<#-- //所在模块-->
<#assign module = basepackage?substring(basepackage?last_index_of(".")+1)> 
Ext.defineModel("Ems.${module}.${simpleClassName}",{
	extend:"Ext.data.Model",
	idProperty:'${idPropertyName}',
	fields:[
	<#list propertyColumns as propertyColumn>
	<#if propertyColumn.isBaseType==true ||  propertyColumn.isIdProperty==true>
		<#if propertyColumn.jsType=='date'>
		{name:'${propertyColumn.property}',type:'${propertyColumn.jsType!"auto"}', dateFormat: 'Y-m-d'}<#if propertyColumn_has_next>,</#if>
		<#else>
		{name:'${propertyColumn.property}',type:'${propertyColumn.jsType!"auto"}'}<#if propertyColumn_has_next>,</#if>
		</#if>
	<#elseif propertyColumn.isConstantType==true>
		{name:'${propertyColumn.column}',type:'constant'}<#if propertyColumn_has_next>,</#if>
	<#elseif propertyColumn.isAssociationType==true>
		{name:'${propertyColumn.column}',type:'${propertyColumn.jsType!"auto"}'}<#if propertyColumn_has_next>,</#if>
	</#if>
	</#list>      
	],
	associations:[
		<#list propertyColumns as propertyColumn>
		<#if propertyColumn.isAssociationType==true>
			{type:'belongsTo',model: 'Ems.${propertyColumn.basepackage?substring(propertyColumn.basepackage?last_index_of(".")+1)}.${propertyColumn.javaTypeClassName}',associatedName:'${propertyColumn.property?capitalize}'}<#if propertyColumn_has_next>,</#if>
		<#elseif propertyColumn.isCollectionType==true>
			{type:'hasMany',model: 'Ems.${propertyColumn.basepackage?substring(propertyColumn.basepackage?last_index_of(".")+1)}.${propertyColumn.javaTypeClassName}',,associatedName:'${propertyColumn.property}'}<#if propertyColumn_has_next>,</#if>
		</#if>
		</#list>
	]
});