Ext.define('Ems.task.HitchReasonTplForm',{
	extend:'Ext.form.Panel',
	requires: [
	     'Ems.task.HitchReasonTpl'
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
	        fieldLabel: '原因简称',
	        //afterLabelTextTpl: Ext.required,
	        name: 'name',
	        readOnly:false,
	        xtype:'textfield',
	        allowBlank: false
	    },
		{
	        fieldLabel: '原因模板',
	        //afterLabelTextTpl: Ext.required,
	        name: 'tpl',
	        readOnly:false,
	        //grow:true,
	        height:260,
	        xtype:'textareafield',
	        allowBlank: false
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
					}
				});
            }
      });
      me.buttons=[saveButton];
      
      me.addEvents("saved");
      me.callParent();
	}
});
