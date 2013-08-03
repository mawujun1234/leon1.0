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
        }
//        ,{
//            fieldLabel: '默认值',
//            name: 'defaultValue',
//            allowBlank: true,
//            tooltip: '输入默认值'
//    }
    	,{
            xtype: 'fieldset',
            flex: 1,
            title: '数字框约束',
            disabled :true,
            itemId :'fieldset_numberfield',
            //defaultType: 'checkbox', // each item will be a checkbox
            layout: 'anchor',
            defaults: {
                anchor: '100%',
                hideEmptyLabel: false
            },
            items: [{
                xtype: 'numberfield',
                name: 'minValue',
                fieldLabel: '最小值'
            }, {
                xtype: 'numberfield',
                name: 'maxValue',
                fieldLabel: '最大值'
            },{
         			fieldLabel: '允许小数',
         			name: 'allowDecimals',
         			xtype:'combobox',
         			displayField: 'name',
    				valueField: 'key',
    				value:'true',
         			store: Ext.create('Ext.data.Store', {
					    fields: ['key', 'name'],
					    data :[{key:'true',name:'允许'},{key:'false',name:'不允许'}]
					})
         	} ]
        },{
            xtype: 'fieldset',
            flex: 1,
            title: '字符串约束',
            disabled :true,
            itemId :'fieldset_textfield',
            //defaultType: 'checkbox', // each item will be a checkbox
            layout: 'anchor',
            defaults: {
                anchor: '100%',
                hideEmptyLabel: false
            },
            items: [{
                xtype: 'numberfield',
                name: 'minLength',
                allowDecimals:false,
                fieldLabel: '最小长度'
            }, {
                xtype: 'numberfield',
                name: 'maxLength',
                allowDecimals:false,
                fieldLabel: '最大长度'
            }, {
                xtype: 'textfield',
                name: 'regex',
                fieldLabel: '正则表达式'
            }]
        }],
	initComponent:function(){
		this.callParent();
	},
	setValueEnum:function(valueEnum,vaidation){
		var me=this;
		if(valueEnum=='NUMBER'){
			me.hideContent();
			this.getComponent('fieldset_numberfield').enable();
			this.getComponent('fieldset_textfield').disable();
			console.log(vaidation);
			if(vaidation){
				this.getForm().setValues(Ext.decode(vaidation));
			}
		} else if(valueEnum=='STRING'){
			me.hideContent();
			this.getComponent('fieldset_numberfield').disable();
			this.getComponent('fieldset_textfield').enable();
			if(vaidation){
				this.getForm().setValues(Ext.decode(vaidation));
			}
		} else {
			me.hideContent();
			this.getComponent('fieldset_numberfield').disable();
			this.getComponent('fieldset_textfield').disable();
		}
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