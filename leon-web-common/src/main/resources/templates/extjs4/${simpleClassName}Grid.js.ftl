<#assign simpleClassNameFirstLower = simpleClassName?uncap_first> 
<#-- //所在模块-->
<#assign module = basepackage?substring(basepackage?last_index_of(".")+1)> 
Ext.define('Leon.${module}.UserQueryGrid',{
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
      me.columns=[
       <#list propertyColumns as propertyColumn>
      <#if propertyColumn.isBaseType==true ||  propertyColumn.isIdProperty==true>
		<#if propertyColumn.jsType=='date'>
		{dataIndex:'${propertyColumn.property}',text:'${propertyColumn.property}',xtype: 'datecolumn',   format:'Y-m-d'}<#if propertyColumn_has_next>,</#if>
		<#elseif propertyColumn.jsType=='int' || propertyColumn.jsType=='float'>
		{dataIndex:'${propertyColumn.property}',text:'${propertyColumn.property}',xtype: 'numbercolumn', format:'0.00'}<#if propertyColumn_has_next>,</#if>
		<#else>
		{dataIndex:'${propertyColumn.property}',text:'${propertyColumn.property}'}<#if propertyColumn_has_next>,</#if>
		</#if>
	  <#elseif propertyColumn.isConstantType==true>
		{dataIndex:'${propertyColumn.property}',text:'${propertyColumn.property}'}<#if propertyColumn_has_next>,</#if>
	  <#elseif propertyColumn.isAssociationType==true>
		{dataIndex:'${propertyColumn.property}',text:'${propertyColumn.property}'}<#if propertyColumn_has_next>,</#if>
	  </#if>
	  </#list>   
      ];
     
        me.store=e=Ext.create('Ext.data.Store',{
       		autoSync:false,
       		pageSize:50,
       		//fields:['userId','userName'],
       		model: 'Leon.${module}.${simpleClassName}',
       		autoLoad:false
//       		proxy:{
//		    	type: 'ajax',
//        		url : '/group/queryUser',
//        		headers:{ 'Accept':'application/json;'},
//        		actionMethods: { read: 'POST' },
//        		extraParams:{limit:50},
//        		reader:{
//					type:'json',
//					root:'root',
//					successProperty:'success',
//					totalProperty:'total'		
//				}
//		    }
       });

       
       me.dockedItems= [{
	        xtype: 'pagingtoolbar',
	        store: me.store,  
	        dock: 'bottom',
	        displayInfo: true
	   }];
       
       me.callParent();
	}
});
