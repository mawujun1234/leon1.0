Ext.define('Ems.repair.ScrapForm',{
	extend:'Ext.form.Panel',
	requires: [
	     'Ems.repair.Scrap'
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
	        readOnly:true,
	        xtype:'hidden',
	        allowBlank: false
	    },
	    {
	        fieldLabel: 'repair_id',
	        //afterLabelTextTpl: Ext.required,
	        name: 'repair_id',
	        readOnly:true,
	        xtype:'hidden',
	        allowBlank: false
	    },
		{
	        fieldLabel: 'ecode',
	        //afterLabelTextTpl: Ext.required,
	        name: '条码',
	        readOnly:true,
	        xtype:'textfield',
	        allowBlank: false
	    },
		{
            fieldLabel: 'operateDate',
            name: 'operateDate',
            readOnly:true,
            xtype: 'hidden',
            format: 'Y-m-d'
        },
        
		{
	        fieldLabel: 'operater',
	        //afterLabelTextTpl: Ext.required,
	        name: 'operater',
	        readOnly:true,
	        xtype:'textfield',
	        value:loginUsername,
	        allowBlank: false
	    },
		{
	        fieldLabel: 'reason',
	        //afterLabelTextTpl: Ext.required,
	        name: '报废原因',
	        readOnly:false,
	        xtype:'textfield',
	        allowBlank: false
	    },
		
		{
	        fieldLabel: 'residual',
	        //afterLabelTextTpl: Ext.required,
	        name: '残余值',
	        readOnly:false,
	        xtype:'textfield',
	        allowBlank: false
	    }
	  ];   
	  
	  
	  var saveButton=Ext.create('Ext.button.Button',{
            text: '报废',
            iconCls:'form-save-button',
            //formBind: true,当设置这个值得时候，当表单里面有内容后，就自动会变成可执行
            //hidden :true,
            handler: function(btn) {
            	var form=this.up('form');
                if(!form.getForm().isValid()) {
                	return;
                }
                form.getForm().updateRecord();
				form.getRecord().save({
					success: function(record, operation) {
						
						me.fireEvent("saved");
					}
				});
            }
      });
      me.buttons=[saveButton];
      me.addEvents("saved");

      me.callParent();
	}
});
