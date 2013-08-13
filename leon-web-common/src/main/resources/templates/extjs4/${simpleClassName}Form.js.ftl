<#assign simpleClassNameFirstLower = simpleClassName?uncap_first> 
<#-- //所在模块-->
<#assign module = basepackage?substring(basepackage?last_index_of(".")+1)> 
<#if extenConfig.createFormModel=="define">
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
    required:'<span style="color:red;font-weight:bold" data-qtip="Required">*</span>',
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
            fieldLabel: '${propertyColumn.property}',
            name: '${propertyColumn.property}',
            xtype: 'datefield',
            format: 'Y-m-d'
        }<#if propertyColumn_has_next>,</#if>
		<#elseif propertyColumn.jsType=='int' || propertyColumn.jsType=='float'>
		{
	        fieldLabel: '${propertyColumn.property}',
	        //afterLabelTextTpl: me.required,
	        name: '${propertyColumn.property}',
	        xtype:'numberfield',
	        allowBlank: false
	    }<#if propertyColumn_has_next>,</#if>
		<#else>
		{
	        fieldLabel: '${propertyColumn.property}',
	        //afterLabelTextTpl: me.required,
	        name: '${propertyColumn.property}',
	        xtype:'textfield',
	        allowBlank: false
	    }<#if propertyColumn_has_next>,</#if>
		</#if>
	  <#elseif propertyColumn.isConstantType==true>
		{
	        xtype:'constantcombo',
            fieldLabel:'${propertyColumn.property}',
            name: '${propertyColumn.property}',
			code:'请补充完整'
        }<#if propertyColumn_has_next>,</#if>
	  <#elseif propertyColumn.isAssociationType==true>
	  <#if (propertyColumn.showModel!"")=='combobox'>
	  {
	  		fieldLabel: '${propertyColumn.property}',
		    name: '${propertyColumn.property}',
			,store: new Ext.form.field.ComboBox({
                typeAhead: true,
                triggerAction: 'all',
                queryMode: 'remote',
		    	displayField: 'name',
		    	valueField: 'key',
                store: Ext.create('Ext.data.Store', {
			    	fields: ['key', 'name'], 
				    proxy:{
				    	type:'ajax',
				    	url:'......',
				    	reader:{
				    		type:'json',
				    		totalProperty:'total',
				    		root:'root'
				    	}
				    }
				})
            })
         }<#if propertyColumn_has_next>,</#if>
		<#else>
		{
	        fieldLabel: '${propertyColumn.property}',
	        //afterLabelTextTpl: me.required,
	        name: '${propertyColumn.property}',
	        xtype:'textfield',
	        allowBlank: false
	   	}<#if propertyColumn_has_next>,</#if>
		</#if>
	  </#if>
	  </#list>   
	  ];   

	    me.buttons= [{
            text: '保存',
            iconCls:'form-save-button',
            formBind: true,
            handler: function() {
            	var form=this.up('form');
                if(!form.getForm().isValid()) {
                	return;
                }
                form.getForm().updateRecord();
				form.getRecord().save({
					success: function(record, operation) {

					}
				});
            }
        },{
            text: '重置',
            iconCls:'form-reset-button',
            handler: function() {
            	//var copyRcd=this.up('form').getRecord( );
                this.up('form').getForm().reset(true);
            }
        }];    
       //me.addEvents("created");
       me.callParent();
	}
});
</#if>
<#------------------创建form------->
<#if extenConfig.createFormModel=="create">
Ext.create('Ext.form.Panel', {
    title: '${simpleClassName} Form',
    bodyPadding: 5,
    url: '/${simpleClassNameFirstLower}/create',

    layout: 'anchor',
    defaults: {
        anchor: '100%'
    },

    items: [
    	 <#list propertyColumns as propertyColumn>
      <#if propertyColumn.isBaseType==true ||  propertyColumn.isIdProperty==true>
		<#if propertyColumn.jsType=='date'>
		{
            fieldLabel: '${propertyColumn.property}',
            name: '${propertyColumn.property}',
            xtype: 'datefield',
            format: 'Y-m-d'
        }<#if propertyColumn_has_next>,</#if>
		<#elseif propertyColumn.jsType=='int' || propertyColumn.jsType=='float'>
		{
	        fieldLabel: '${propertyColumn.property}',
	        //afterLabelTextTpl: me.required,
	        name: '${propertyColumn.property}',
	        xtype:'numberfield',
	        allowBlank: false
	    }<#if propertyColumn_has_next>,</#if>
		<#else>
		{
	        fieldLabel: '${propertyColumn.property}',
	        //afterLabelTextTpl: me.required,
	        name: '${propertyColumn.property}',
	        xtype:'textfield',
	        allowBlank: false
	    }<#if propertyColumn_has_next>,</#if>
		</#if>
	  <#elseif propertyColumn.isConstantType==true>
		{
	        xtype:'constantcombo',
            fieldLabel:'${propertyColumn.property}',
            name: '${propertyColumn.property}',
			code:'请补充完整'
        }<#if propertyColumn_has_next>,</#if>
	  <#elseif propertyColumn.isAssociationType==true>
		{
		    fieldLabel: '${propertyColumn.property}',
		    name: '${propertyColumn.property}',
		    displayField: 'name',
		    valueField: 'abbr',
		    queryMode: 'local',
		    store:Ext.create('Ext.data.Store', {
			    fields: ['abbr', 'name'],
			    data : [
			        {"abbr":"AL", "name":"Alabama"},
			        {"abbr":"AK", "name":"Alaska"},
			        {"abbr":"AZ", "name":"Arizona"}
			        //...
			    ]
			})
		    
		}<#if propertyColumn_has_next>,</#if>
	  </#if>
	  </#list>   
    ],

    // Reset and Submit buttons
    buttons: [{
        text: '重置',
        iconCls:'form-reset-button',
        handler: function() {
            this.up('form').getForm().reset();
        }
    }, {
        text: '提交',
        iconCls:'form-save-button',
        formBind: true,
        handler: function() {
            var form = this.up('form').getForm();
            if (form.isValid()) {
                form.submit({
                    success: function(form, action) {
                       Ext.Msg.alert('Success', action.result.msg);
                    },
                    failure: function(form, action) {
                        Ext.Msg.alert('Failed', action.result.msg);
                    }
                });
            }
        }
    }]
});
</#if>
