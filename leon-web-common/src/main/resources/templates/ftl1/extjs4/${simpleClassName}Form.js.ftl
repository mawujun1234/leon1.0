<#assign simpleClassNameFirstLower = simpleClassName?uncap_first> 
<#-- //所在模块-->
<#assign module = basepackage?substring(basepackage?last_index_of(".")+1)> 
Ext.define('Leon.${module}.${simpleClassName}Form',{
	extend:'Ext.form.Panel',
	requires: [
	     'Leon.${module}.${simpleClassName}'
	],
	fieldDefaults: {
        msgTarget: 'side',
        labelWidth: 75,
        labelAlign:'right',
        anchor: '90%'
    },
    frame: true,
    bodyPadding: '5 5 0',

 //   layout: {
 //       type: 'vbox',
 //       align: 'stretch'  // Child items are stretched to full width
 //   },
	initComponent: function () {
       var me = this;
       me.items= [
      <#list propertyColumns as propertyColumn>
      <#if propertyColumn.isBaseType==true ||  propertyColumn.isIdProperty==true>
		<#if propertyColumn.jsType=='date'>
		{
            fieldLabel: '${propertyColumn.label!propertyColumn.property}',
            name: '${propertyColumn.property}',
            readOnly:true,
            xtype: 'datefield',
            format: 'Y-m-d'
        }<#if propertyColumn_has_next>,</#if>
		<#elseif propertyColumn.jsType=='int' || propertyColumn.jsType=='float'>
		{
	        fieldLabel: '${propertyColumn.label!propertyColumn.property}',
	        //afterLabelTextTpl: Ext.required,
	        name: '${propertyColumn.property}',
	        readOnly:true,
	        xtype:'numberfield',
	        allowBlank: false
	    }<#if propertyColumn_has_next>,</#if>
		<#else>
		{
	        fieldLabel: '${propertyColumn.label!propertyColumn.property}',
	        //afterLabelTextTpl: Ext.required,
	        name: '${propertyColumn.property}',
	        readOnly:true,
	        xtype:'textfield',
	        allowBlank: false
	    }<#if propertyColumn_has_next>,</#if>
		</#if>
	  <#elseif propertyColumn.isConstantType==true>
		{
	        xtype:'constantcombo',
            fieldLabel:'${propertyColumn.label!propertyColumn.property}',
            name: '${propertyColumn.property}',
            readOnly:true,
			code:'请补充完整'
        }<#if propertyColumn_has_next>,</#if>
	  <#elseif propertyColumn.isAssociationType==true>
	  <#--if (propertyColumn.showModel!"")=='combobox'-->
	   {
	  		fieldLabel: '${propertyColumn.label!propertyColumn.column}',
		    name: '${propertyColumn.column}',
		    readOnly:true,
		    xtype:'combobox',
            typeAhead: true,
            triggerAction: 'all',
            queryMode: 'remote',
		    displayField: 'name',
		    valueField: 'id',
            store: Ext.create('Ext.data.Store', {
			    fields: ['id', 'name'], 
				proxy:{
				    type:'ajax',
				    url:Ext.ContextPath+'${propertyColumn.property}/query',
				    reader:{
				    	type:'json',
				    	totalProperty:'total',
				    	root:'root'
				    }
				}
			})
        }<#if propertyColumn_has_next>,</#if>
		<#else>
		 {
	        fieldLabel: '${propertyColumn.label!propertyColumn.property}',
	        //afterLabelTextTpl: Ext.required,
	        name: '${propertyColumn.property}',
	        readOnly:true,
	        xtype:'textfield',
	        allowBlank: false
	   	 }<#if propertyColumn_has_next>,</#if>
		<#--if-->
	  </#if>
	  </#list>   
	  ];   

	var saveButton=Ext.create('Ext.button.Button',{
            text: '保存',
            iconCls:'form-save-button',
            formBind: true,
            //jsonSubmit:true
            handler: function(btn) {
            	var form=this.up('form');
                if(!form.getForm().isValid()) {
                	return;
                }
                form.getForm().updateRecord();
				form.getRecord().save({
					success: function(record, operation) {
						btn.disable();
					}
				});
            }
      })
	  me.buttons= [saveButton];    
      me.saveButton=saveButton;
      me.callParent();
	}
});
