<#assign simpleClassNameFirstLower = simpleClassName?uncap_first> 
<#-- //所在模块-->
<#assign module = basepackage?substring(basepackage?last_index_of(".")+1)> 
Ext.define('${extenConfig.extjs_packagePrefix}.${module}.${simpleClassName}Form',{
	extend:'Ext.form.Panel',
	requires: [
	     '${extenConfig.extjs_packagePrefix}.${module}.${simpleClassName}'
	],
	
    frame: true,
    autoScroll : true,
	buttonAlign : 'center',
    bodyPadding: '5 5 0',


 	<#if  extenConfig.extjs_form_layoutColumns==-1>
    defaults: {
        msgTarget: 'under',
        labelWidth: 75,
        labelAlign:'right',
        anchor: '90%'
    },
    <#else>
    layout: {
        type: 'table',
        columns: ${extenConfig.extjs_form_layoutColumns}
    },
    defaults: {
    	msgTarget: 'side',
        labelWidth: 75,
        labelAlign:'right',
        width: '90%'
    },
    </#if>
	initComponent: function () {
       var me = this;
       me.items= [
      <#list propertyColumns as propertyColumn>
      	<#if propertyColumn.showType=='none'>
		<#if propertyColumn.jsType=='date'>
		{
            fieldLabel: '${propertyColumn.property_label!propertyColumn.property}',
            name: '${propertyColumn.property}',
            <#if propertyColumn.hidden==true>
            hidden:${propertyColumn.hidden?string("true","false")},
            </#if>
            <#if propertyColumn.nullable=='false' && propertyColumn.hidden==false>
            allowBlank: ${propertyColumn.nullable},
            afterLabelTextTpl: Ext.required,
            blankText:"${propertyColumn.property_label}不允许为空",
            </#if>
            editable:false,
            xtype: 'datefield',
            format: 'Y-m-d'   
        }<#if propertyColumn_has_next>,</#if>
        <#elseif propertyColumn.jsType=='bool'>
        {
        	fieldLabel: '${propertyColumn.property_label!propertyColumn.property}',
            name:'${propertyColumn.property}',
            <#if propertyColumn.hidden==true>
            hidden:${propertyColumn.hidden?string("true","false")},
            </#if>
            xtype: 'checkbox',
            cls: 'x-grid-checkheader-editor'
        }<#if propertyColumn_has_next>,</#if>
		<#elseif propertyColumn.jsType=='int'>
		{
	        fieldLabel: '${propertyColumn.property_label!propertyColumn.property}',
	        name: '${propertyColumn.property}',
	        <#if propertyColumn.hidden==true>
            hidden:${propertyColumn.hidden?string("true","false")},
            </#if>
             <#if propertyColumn.nullable=='false' && propertyColumn.hidden==false>
            allowBlank: ${propertyColumn.nullable},
            afterLabelTextTpl: Ext.required,
            blankText:"${propertyColumn.property_label}不允许为空",
            </#if>
            allowDecimals:false,
            selectOnFocus:true,
	        xtype:'numberfield'   
	    }<#if propertyColumn_has_next>,</#if>
	    <#elseif propertyColumn.jsType=='float'>
		{
	        fieldLabel: '${propertyColumn.property_label!propertyColumn.property}',
	        name: '${propertyColumn.property}',
	        <#if propertyColumn.hidden==true>
            hidden:${propertyColumn.hidden?string("true","false")},
            </#if>
             <#if propertyColumn.nullable=='false' && propertyColumn.hidden==false>
            allowBlank: ${propertyColumn.nullable},
            afterLabelTextTpl: Ext.required,
            blankText:"${propertyColumn.property_label}不允许为空",
            </#if>
            selectOnFocus:true,
	        xtype:'numberfield'   
	    }<#if propertyColumn_has_next>,</#if>
		<#else>
		{
	        fieldLabel: '${propertyColumn.property_label!propertyColumn.property}',
	        name: '${propertyColumn.property}',
	       <#if propertyColumn.hidden==true>
            hidden:${propertyColumn.hidden?string("true","false")},
            </#if>
            <#if propertyColumn.nullable=='false' && propertyColumn.hidden==false>
            allowBlank: ${propertyColumn.nullable},
            afterLabelTextTpl: Ext.required,
            blankText:"${propertyColumn.property_label}不允许为空",
            </#if>
            selectOnFocus:true,
	        xtype:'textfield'
	    }<#if propertyColumn_has_next>,</#if>
		</#if>
		<#elseif propertyColumn.showType=='combobox'>
		<#if propertyColumn.isEnum=='true'>
		{
			fieldLabel: '${propertyColumn.property_label!propertyColumn.property}',
			name: '${propertyColumn.property}',
			queryMode: 'local',
			editable:false,
			forceSelection:true,
		    displayField: 'name',
		    valueField: 'key',
		    store: {
			    fields: ['key', 'name'],
			    data : [
			    <#assign  keys=propertyColumn.showType_values?keys/>
			    <#list keys as key>
			    	{"key":"${key}", "name":"${propertyColumn.showType_values["${key}"]}"}<#if key_has_next>,</#if>
				</#list>
			    ]
			},
			<#if propertyColumn.hidden==true>
            hidden:${propertyColumn.hidden?string("true","false")},
            </#if>
            <#if propertyColumn.nullable=='false' && propertyColumn.hidden==false>
            allowBlank: ${propertyColumn.nullable},
            afterLabelTextTpl: Ext.required,
            blankText:"${propertyColumn.property_label}不允许为空",
            </#if>
			xtype:'combobox'
		}<#if propertyColumn_has_next>,</#if>
		<#else>
		{
			fieldLabel: '${propertyColumn.property_label!propertyColumn.property}',
			name: '${propertyColumn.property}',
			queryMode: 'remote',
			editable:false,
			forceSelection:true,
		    displayField: 'name',
		    valueField: 'id',
		    store: {
			    fields: ['id', 'name'],
			    proxy: {
			    	autoLoad:true,
			        type: 'ajax',
			        url: Ext.ContextPath+'/${propertyColumn.property}/query.do',
			        reader: {
			            type: 'json'
			            //rootProperty: '${propertyColumn.property}'
			        }
			    }
			},
			<#if propertyColumn.hidden==true>
            hidden:${propertyColumn.hidden?string("true","false")},
            </#if>
            <#if propertyColumn.nullable=='false' && propertyColumn.hidden==false>
            allowBlank: ${propertyColumn.nullable},
            afterLabelTextTpl: Ext.required,
            blankText:"${propertyColumn.property_label}不允许为空",
            </#if>
			xtype:'combobox'
		}<#if propertyColumn_has_next>,</#if>
		</#if><#-- <#if propertyColumn.isEnum=='true'> -->
		<#elseif propertyColumn.showType=='radio'>
		<#if propertyColumn.isEnum=='true'>
		{
            xtype      : 'fieldcontainer',
            fieldLabel : '${propertyColumn.property_label!propertyColumn.property}',
            defaultType: 'radiofield',
            defaults: {
                flex: 1
            },
            layout: 'hbox',
            items: [
            <#assign  keys=propertyColumn.showType_values?keys/>
			<#list keys as key>
				{
                    boxLabel  : '${propertyColumn.showType_values["${key}"]}',
                    name: '${propertyColumn.property}',
                    inputValue: '${key}'
                }<#if key_has_next>,</#if>
			</#list>
            ]
        }<#if propertyColumn_has_next>,</#if>
        <#elseif propertyColumn.jsType=='bool'>
        {
            xtype      : 'fieldcontainer',
            fieldLabel : '${propertyColumn.property_label!propertyColumn.property}',
            defaultType: 'radiofield',
            defaults: {
                flex: 1
            },
            layout: 'hbox',
            items: [
           		{
                    boxLabel  : 'true',
                    name: '${propertyColumn.property}',
                    inputValue: 'true'
                },{
                    boxLabel  : 'false',
                    name: '${propertyColumn.property}',
                    inputValue: 'false'
                }
            ]
        }<#if propertyColumn_has_next>,</#if>
		<#else>
			这个radio自动生成还没有做，请不要设置为radio
		</#if>
		
		</#if><#--<#if propertyColumn.showType=='none'>-->
	  </#list>   
	  ];   
	  
	  
	  this.buttons = [];
		this.buttons.push({
			text : '保存',
			itemId : 'save',
			formBind: true, //only enabled once the form is valid
       		disabled: true,
			glyph : 0xf0c7,
			handler : function(button){
				var formpanel = button.up('form');
				formpanel.updateRecord();
				formpanel.getForm().getRecord().save({
					failure: function(record, operation) {
				    },
				    success: function(record, operation) {
						button.up('window').close();
				    }
				});			
				
				}
			},{
				text : '关闭',
				itemId : 'close',
				glyph : 0xf00d,
				handler : function(button){
					button.up('window').close();
				}
	    });
      me.callParent();
	}
});
