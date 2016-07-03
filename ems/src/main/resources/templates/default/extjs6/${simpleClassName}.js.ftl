<#assign simpleClassNameFirstLower = simpleClassName?uncap_first> 
<#-- //所在模块-->
<#assign module = basepackage?substring(basepackage?last_index_of(".")+1)> 
Ext.define("${extenConfig.extjs_packagePrefix}.${module}.${simpleClassName}",{
	extend:"Ext.data.Model",
	fields:[
	<#list propertyColumns as propertyColumn>
		<#if propertyColumn.jsType=='date'>
		{name:'${propertyColumn.property}',type:'${propertyColumn.jsType!"auto"}', dateFormat: 'Y-m-d H:i:s'}<#if propertyColumn_has_next>,</#if>
		<#else>
		{name:'${propertyColumn.property}',type:'${propertyColumn.jsType!"auto"}'}<#if propertyColumn_has_next>,</#if>
		</#if>
	</#list>      
	],
	proxy:{
		type:'ajax',
		actionMethods: { read: 'POST' },
		timeout :600000,
		headers:{ 'Accept':'application/json;'},
		writer:{
			type:'json',
			writeRecordId:true,
			writeAllFields:true
		},
		reader:{
			type:'json'
			///rootProperty:'root',
			//successProperty:'success',
			//totalProperty:'total'		
		},
		api:{
			read:Ext.ContextPath+'/${simpleClassNameFirstLower}/load.do',
			//load : Ext.ContextPath+'/${simpleClassNameFirstLower}/load.do',
			create:Ext.ContextPath+'/${simpleClassNameFirstLower}/create.do',
			update:Ext.ContextPath+'/${simpleClassNameFirstLower}/update.do',
			destroy:Ext.ContextPath+'/${simpleClassNameFirstLower}/destroy.do'
		}
	}
});