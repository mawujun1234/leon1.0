Ext.define('Leon.genTest.MenuItemForm',{
	extend:'Ext.form.Panel',
	requires: [
	     'Leon.genTest.MenuItem'
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
		{
	        fieldLabel: 'id',
	        //afterLabelTextTpl: Ext.required,
	        name: 'id',
	        xtype:'textfield',
	        allowBlank: false
	    },
		{
	        fieldLabel: 'code',
	        //afterLabelTextTpl: Ext.required,
	        name: 'code',
	        xtype:'textfield',
	        allowBlank: false
	    },
		{
	        fieldLabel: 'iconCls',
	        //afterLabelTextTpl: Ext.required,
	        name: 'iconCls',
	        xtype:'textfield',
	        allowBlank: false
	    },
		{
	        fieldLabel: 'iconCls32',
	        //afterLabelTextTpl: Ext.required,
	        name: 'iconCls32',
	        xtype:'textfield',
	        allowBlank: false
	    },
		{
	        fieldLabel: 'javaClass',
	        //afterLabelTextTpl: Ext.required,
	        name: 'javaClass',
	        xtype:'textfield',
	        allowBlank: false
	    },
		{
	        fieldLabel: 'leaf',
	        //afterLabelTextTpl: Ext.required,
	        name: 'leaf',
	        xtype:'textfield',
	        allowBlank: false
	    },
		{
	        fieldLabel: 'reportCode',
	        //afterLabelTextTpl: Ext.required,
	        name: 'reportCode',
	        xtype:'textfield',
	        allowBlank: false
	    },
		{
	        fieldLabel: 'scripts',
	        //afterLabelTextTpl: Ext.required,
	        name: 'scripts',
	        xtype:'textfield',
	        allowBlank: false
	    },
		{
	        fieldLabel: 'text',
	        //afterLabelTextTpl: Ext.required,
	        name: 'text',
	        xtype:'textfield',
	        allowBlank: false
	    },
		{
	        fieldLabel: 'fun',
	        //afterLabelTextTpl: Ext.required,
	        name: 'fun',
	        xtype:'textfield',
	        allowBlank: false
	   	},
		{
	        fieldLabel: 'menu',
	        //afterLabelTextTpl: Ext.required,
	        name: 'menu',
	        xtype:'textfield',
	        allowBlank: false
	   	},
		{
	        fieldLabel: 'parent',
	        //afterLabelTextTpl: Ext.required,
	        name: 'parent',
	        xtype:'textfield',
	        allowBlank: false
	   	}
	  ];   

	  me.buttons= [{
            text: '保存',
            iconCls:'form-save-button',
            formBind: true,
            //jsonSubmit:true
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
