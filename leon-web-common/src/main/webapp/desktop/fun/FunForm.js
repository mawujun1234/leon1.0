Ext.define('Leon.desktop.fun.FunForm',{
	extend:'Ext.form.Panel',
	requires: [
	     'Leon.desktop.fun.Fun'
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
	initComponent: function () {
       var me = this;
      
       me.items= [{
	            fieldLabel: 'id',
	            //afterLabelTextTpl: me.required,
	            name: 'id'
	            //allowBlank: false,
	            //tooltip: 'Enter your first name'
	        },{
	            fieldLabel: '名称',
	            afterLabelTextTpl: me.required,
	            name: 'text',
	            allowBlank: false,
	            tooltip: '请输入名称'
	        },{
	            fieldLabel: '助记码',
	            //afterLabelTextTpl: me.required,
	            name: 'code'
	            //allowBlank: false,
	            //tooltip: '请输入名称'
	        },{
	            fieldLabel: 'url',
	            name: 'url',
	            tooltip: "请输入url"
	        },{
	        	xtype:'checkboxfield',
                fieldLabel  : '创建菜单',
                name      : 'isCreateMenu',
                inputValue: '2',
                checked   : true
            }
	    ];
	    me.buttons= [{
            text: '保存',
            iconCls:'form-save-button',
            handler: function() {
                this.up('form').getForm().isValid();
            }
        },{
            text: '更新',
            iconCls:'form-update-button',
            handler: function() {
                this.up('form').getForm().isValid();
            }
        },{
            text: '新建子菜单',
            iconCls:'form-addChild-button',
            handler: function() {
                this.up('form').getForm().reset();
            }
        },{
            text: '删除',
            iconCls:'form-delete-button',
            handler: function() {
                this.up('form').getForm().reset();
            }
        }]
       
       me.callParent();
	}
});