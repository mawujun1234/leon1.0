Ext.define('Leon.desktop.parameter.JavaForm', {
	extend:'Ext.form.Panel',
	frame:true,
	
	defaults: { 
		labelAlign:'right',
		labelWidth:60
	},
	
	defaultType: 'textfield',
    items: [{
    		xtype: 'label',
    		text:'java类写全限定包名，并且一定要继承接口：com.mawujun.parameter.JavaBeanDataSource,可以参考JavaBeanDataSourceSample的写法'
    	},{
            fieldLabel: 'java类',
            name: 'content',
            hidden:false,
            allowBlank:false,
            tooltip: '输入表达式，java类名'
    }],
	initComponent:function(){
		this.callParent();
	},
	setValueEnum:function(valueEnum,vaidation){
		
	}
});