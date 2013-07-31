Ext.define('Leon.desktop.parameter.SimpleForm', {
	extend:'Ext.form.Panel',
	frame:true,
	
	defaults: { 
		labelAlign:'right'
	},
	defaultType: 'textfield',
    items: [{
            fieldLabel: '内容',
            name: 'content',
            hidden:true,
            allowBlank:false,
            tooltip: '输入表达式，java类名或者是SQL语句'
        },{
            fieldLabel: '默认值',
            name: 'defaultValue',
            allowBlank: true,
            tooltip: '输入默认值'
    }],
	initComponent:function(){
		this.callParent();
	},
	showContent:function(){
		this.getForm().findField("content").show();
	},
	hideContent:function(){
		var field=this.getForm().findField("content");
		field.setValue("");
		field.hide();
	}
});