<#assign simpleClassNameFirstLower = simpleClassName?uncap_first> 
<#-- //所在模块-->
<#assign module = basepackage?substring(basepackage?last_index_of(".")+1)>
Ext.define('Leon.${module}.${simpleClassName}GridQuery',{
	extend:'Ext.grid.Panel',
	requires: [
	     'Leon.${module}.${simpleClassName}'
	],
	columnLines :true,
	stripeRows:true,
	viewConfig:{
		stripeRows:true,
		listeners:{
			refresh:function(){
				this.select(0);
			}
		}
	},
	initComponent: function () {
      var me = this;
     <#-----------------------------------------生成列--------------------------------- ----->
      me.columns=[
      <#list propertyColumns as propertyColumn>	
      <#if propertyColumn.isBaseType==true ||  propertyColumn.isIdProperty==true>
		<#if propertyColumn.jsType=='date'>
		{dataIndex:'${propertyColumn.property}',text:'${propertyColumn.label!propertyColumn.property}',xtype: 'datecolumn',   format:'Y-m-d'}<#if propertyColumn_has_next>,</#if>
		<#elseif propertyColumn.jsType=='int' || propertyColumn.jsType=='float'>
		{dataIndex:'${propertyColumn.property}',text:'${propertyColumn.label!propertyColumn.property}',xtype: 'numbercolumn', format:'0.00'}<#if propertyColumn_has_next>,</#if>
		<#else>
		{dataIndex:'${propertyColumn.property}',text:'${propertyColumn.label!propertyColumn.property}'}<#if propertyColumn_has_next>,</#if>
		</#if>
	  <#elseif propertyColumn.isConstantType==true>
	    {dataIndex:'${propertyColumn.property}',text:'${propertyColumn.label!propertyColumn.property}'}<#if propertyColumn_has_next>,</#if>
	  <#else>
		{dataIndex:'${propertyColumn.property}',text:'${propertyColumn.label!propertyColumn.property}'}<#if propertyColumn_has_next>,</#if>
	  </#if>
	  </#list>
      ];
      
      <#-----------------------------------------生成store--------------------------------- ----->
      <#if extenConfig.userModel==true>
	  me.store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			model: 'Leon.${module}.${simpleClassName}',
			autoLoad:true
	  });
	  <#else>
	  me.store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			autoLoad:true,
			proxy:{
				type: 'ajax',
			    url : Ext.ContextPath+'/${simpleClassNameFirstLower}/query',
			    headers:{ 'Accept':'application/json;'},
			    actionMethods: { read: 'POST' },
			    extraParams:{limit:50},
			    reader:{
					type:'json',
					root:'root',
					successProperty:'success',
					totalProperty:'total'		
				}
			}
	  });
	  </#if>
	  
	  <#-----------------------------------------是否启用page--------------------------------- ----->
      me.dockedItems= [{
	        xtype: 'pagingtoolbar',
	        store: me.store,  
	        dock: 'bottom',
	        displayInfo: true
	  }];
       
      me.callParent();
	}
});
