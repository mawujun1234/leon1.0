<#import "/macro.include" as my/>  
<#assign simpleClassNameFirstLower = simpleClassName?uncap_first> 
<#-- //所在模块-->
<#assign module = basepackage?substring(basepackage?last_index_of(".")+1)>
Ext.define('Leon.${module}.${simpleClassName}Grid',{
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
	<#if  extenConfig.editable=="true">
	<#if  extenConfig.rowediting=="true">
    plugins:[
    	Ext.create('Ext.grid.plugin.RowEditing', {
	        clicksToMoveEditor: 1,
	        autoCancel: false
	    })
    ],
    <#else>
    plugins:[
        Ext.create('Ext.grid.plugin.CellEditing', {
            clicksToEdit: 1
        })
    ],
    </#if>
	</#if>
	initComponent: function () {
      var me = this;
     
      me.columns=[
       <@my.generateGridColumns editable=extenConfig.editable/>  
      ];
      
      me.store=<@my.generateGridStore userModel=extenConfig.userModel  extjsClassName='Leon.'+module+'.'+simpleClassName/>;
	  
	  <#if extenConfig.pageable="true">
      me.dockedItems= [{
	        xtype: 'pagingtoolbar',
	        store: me.store,  
	        dock: 'bottom',
	        displayInfo: true
	  }];
	  </#if>
	  <#if extenConfig.createDelUpd="true">
	  me.tbar=<@my.generateGridCreateDelUpd/>;
	  </#if>
       
      me.callParent();
	}
});
