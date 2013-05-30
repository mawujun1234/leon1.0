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
//       		{
//	            fieldLabel: 'parent_id',
//
//	            name: 'parent_id',
//	            readOnly:true
//	        },{
//	            fieldLabel: '上级名称',
//	            //afterLabelTextTpl: me.required,
//	            name: 'parent_text',
//	            readOnly:true
//	            //allowBlank: false,
//	            //tooltip: 'Enter your first name'
//	        },{
//	            fieldLabel: 'id',
//	            //afterLabelTextTpl: me.required,
//	            readOnly:true,
//	            name: 'id'
//	            //allowBlank: false,
//	            //tooltip: 'Enter your first name'
//	        },
	        {
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
	        }
	        ,{
	        	xtype:'checkboxfield',
                fieldLabel  : '创建菜单',
                name      : 'isCreateMenu',
                inputValue: '2',
                checked   : true
            }
	    ];
	    me.buttons= [{
            text: '更新',
            iconCls:'form-save-button',
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
       
        me.addEvents("created");
       me.callParent();
	}
});