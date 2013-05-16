<#assign simpleClassNameFirstLower = simpleClassName?uncap_first> 
<#-- //所在模块-->
<#assign module = basepackage?substring(basepackage?last_index_of(".")+1)> 

//在创建具有关联数据的时候 使用Ext.createModel();
//或者在创建模型数据的时候就使用这个
Ext.define("Leon.${module}.${simpleClassName}",{
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
	<#elseif propertyColumn.isAssociationType==true>
		{name:'${propertyColumn.column}',type:'${propertyColumn.jsType!"auto"}'}<#if propertyColumn_has_next>,</#if>
	</#if>
	</#list>      
	],
	associations:[
		<#list propertyColumns as propertyColumn>
		<#if propertyColumn.isAssociationType==true>
			{type:'belongsTo',model: 'Leon.${propertyColumn.basepackage?substring(propertyColumn.basepackage?last_index_of(".")+1)}.${propertyColumn.javaTypeClassName}',associatedName:'${propertyColumn.property?capitalize}'}<#if propertyColumn_has_next>,</#if>
		<#elseif propertyColumn.isComponentType==true>
			{type:'belongsTo',model: 'Leon.${propertyColumn.basepackage?substring(propertyColumn.basepackage?last_index_of(".")+1)}.${propertyColumn.javaTypeClassName}',,associatedName:'${propertyColumn.property?capitalize}'}<#if propertyColumn_has_next>,</#if>
		</#if>
		</#list>
	],
	,proxy:{
		actionMethods: { read: 'POST' },
		timeout :600000,
		headers:{ 'Accept':'application/json;'},
		type:'ajax',
		api: {
			create  : Ext.ContextPath+'/${simpleClassNameFirstLower}/create',
			read    : Ext.ContextPath+'/${simpleClassNameFirstLower}/qeryPage.do',
			get    : Ext.ContextPath+'/${simpleClassNameFirstLower}/get',//在load（）方法的时候指定 action="get"
			update  : Ext.ContextPath+'/${simpleClassNameFirstLower}/update',
			destroy : Ext.ContextPath+'/${simpleClassNameFirstLower}/destroy'
		},
		reader:{
			type:'json',
			root:'root'
		}
		,writer:{
			type:'json'
		}
	}
});