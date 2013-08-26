Ext.define('Leon.desktop.user.UserForm',{
	extend:'Ext.form.Panel',
	requires: [
	     'Leon.desktop.user.User'
	],
	fieldDefaults: {
            msgTarget: 'side',
            labelWidth: 75,
            labelAlign:'right'
    },
    required:'<span style="color:red;font-weight:bold" data-qtip="Required">*</span>',
    defaultType: 'textfield',
    frame: true,
    bodyPadding: '5 5 0',
    //trackResetOnLoad:true,
    
    fieldDefaults: {
            labelWidth: 55,
            anchor: '100%'
        },

    layout: {
            type: 'vbox',
            align: 'stretch'  // Child items are stretched to full width
    },
	initComponent: function () {
       var me = this;
      
       me.items= [
	        {
	            fieldLabel: '登陆名',
	            afterLabelTextTpl: me.required,
	            name: 'loginName',
	            allowBlank: false,
	            tooltip: '请输入名称'
	        },{
	            fieldLabel: '密码',
	            afterLabelTextTpl: me.required,
	            name: 'password',
	            allowBlank: false
	        },{
	            fieldLabel: '姓名',
	            name: 'name'
	            //tooltip: "请输入url"
	        },{
	        	xtype:'checkboxfield',
                fieldLabel  : '是否锁定',
                name      : 'locked',
                inputValue: 'false',
                checked   : false
            },{
	        	xtype:'checkboxfield',
                fieldLabel  : '是否可用',
                name      : 'enable',
                inputValue: 'true',
                checked   : true
            }
	    ];
	    me.buttons= [{
            text: '保存',
            iconCls:'form-save-button',
            handler: function() {
            	var form=this.up('form');
                if(!form.getForm().isValid()) {
                	return;
                }
                form.getForm().updateRecord();
				form.getRecord().save({
					success: function(record, operation) {
						record.commit();
						me.up("window").close();
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
       
       me.addEvents("created");
       me.callParent();
	},
	hidePassordField:function(){
		this.getForm().findField("password").hide();
	}
});