Ext.define('Leon.desktop.parameter.SimpleForm', {
	extend:'Ext.form.Panel',
	frame:true,
	
	defaults: { 
		labelAlign:'right'
	},
	defaultType: 'textfield',
        items: [{
            fieldLabel: '默认值',
            name: 'defaultValue',
            allowBlank: true,
            tooltip: '输入默认值'
        }],
	initComponent:function(){
		this.callParent();
	}
});