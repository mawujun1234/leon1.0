Ext.define('Ems.baseinfo.CustomerContactForm',{
	extend:'Ext.form.Panel',
	requires: [
	     'Ems.baseinfo.CustomerContact'
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
	        readOnly:false,
	        xtype:'hidden',
	        allowBlank: true
	    },
		
		{
	        fieldLabel: '联系人',
	        afterLabelTextTpl: Ext.required,
	        name: 'contact',
	        readOnly:false,
	        xtype:'textfield',
	        allowBlank: false
	    },
	    {
	        fieldLabel: '职位',
	        //afterLabelTextTpl: Ext.required,
	        name: 'position',
	        readOnly:false,
	        xtype:'textfield',
	        allowBlank: true
	    },
	    {
	        fieldLabel: '电话',
	        //afterLabelTextTpl: Ext.required,
	        name: 'phone',
	        readOnly:false,
	        xtype:'textfield',
	        allowBlank: true
	    },
		{
	        fieldLabel: '电子邮件',
	        //afterLabelTextTpl: Ext.required,
	        name: 'email',
	        readOnly:false,
	        xtype:'textfield',
	        allowBlank: true
	    },
	    {
	        fieldLabel: '手机',
	        //afterLabelTextTpl: Ext.required,
	        name: 'mobile',
	        readOnly:false,
	        xtype:'textfield',
	        allowBlank: true
	    },
		{
	        fieldLabel: '传真',
	        //afterLabelTextTpl: Ext.required,
	        name: 'fax',
	        readOnly:false,
	        xtype:'textfield',
	        allowBlank: true
	    },
	    {
	        fieldLabel: '地址',
	        //afterLabelTextTpl: Ext.required,
	        name: 'address',
	        readOnly:false,
	        xtype:'textfield',
	        allowBlank: true
	    },
		{
	        fieldLabel: '邮编',
	        //afterLabelTextTpl: Ext.required,
	        name: 'postcode',
	        readOnly:false,
	        xtype:'textfield',
	        allowBlank: true
	    },
		{
	        fieldLabel: 'customer_id',
	        //afterLabelTextTpl: Ext.required,
	        name: 'customer_id',
	        readOnly:true,
	        xtype:'hidden',
	        allowBlank: true
	    }
	  ];   
	  
	  
	  var saveButton=Ext.create('Ext.button.Button',{
            text: '保存',
            iconCls:'form-save-button',
            //formBind: true,当设置这个值得时候，当表单里面有内容后，就自动会变成可执行
            //hidden :true,
            itemId:'save',
            handler: function(btn) {
            	var form=this.up('form');
                if(!form.getForm().isValid()) {
                	return;
                }
                form.getForm().updateRecord();
				form.getRecord().save({
					success: function(record, operation) {
						me.fireEvent("saved");
						alert("保存成功");
					}
				});
            }
      });
      me.buttons=[saveButton];
	
      me.addEvents("saved");
      me.callParent();
	}
});
