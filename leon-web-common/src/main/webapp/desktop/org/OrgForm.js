Ext.define('Leon.desktop.org.OrgForm',{
	extend:'Ext.form.Panel',
	requires: [
	     'Leon.desktop.org.Org'
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
	        xtype:'textfield',
	        allowBlank: false
	    },
		{
	        fieldLabel: 'address',
	        //afterLabelTextTpl: Ext.required,
	        name: 'address',
	        readOnly:true,
	        xtype:'textfield',
	        allowBlank: false
	    },
		{
	        fieldLabel: 'code',
	        //afterLabelTextTpl: Ext.required,
	        name: 'code',
	        readOnly:true,
	        xtype:'textfield',
	        allowBlank: false
	    },
		{
	        fieldLabel: 'corporation',
	        //afterLabelTextTpl: Ext.required,
	        name: 'corporation',
	        readOnly:true,
	        xtype:'textfield',
	        allowBlank: false
	    },
		{
	        fieldLabel: 'email',
	        //afterLabelTextTpl: Ext.required,
	        name: 'email',
	        readOnly:true,
	        xtype:'textfield',
	        allowBlank: false
	    },
		{
	        fieldLabel: 'fax',
	        //afterLabelTextTpl: Ext.required,
	        name: 'fax',
	        readOnly:true,
	        xtype:'textfield',
	        allowBlank: false
	    },
		{
	        fieldLabel: 'introduction',
	        //afterLabelTextTpl: Ext.required,
	        name: 'introduction',
	        readOnly:true,
	        xtype:'textfield',
	        allowBlank: false
	    },
		{
	        fieldLabel: 'name',
	        //afterLabelTextTpl: Ext.required,
	        name: 'name',
	        readOnly:true,
	        xtype:'textfield',
	        allowBlank: false
	    },
		{
	        fieldLabel: 'phonenumber',
	        //afterLabelTextTpl: Ext.required,
	        name: 'phonenumber',
	        readOnly:true,
	        xtype:'textfield',
	        allowBlank: false
	    },
		{
	        fieldLabel: 'postalcode',
	        //afterLabelTextTpl: Ext.required,
	        name: 'postalcode',
	        readOnly:true,
	        xtype:'textfield',
	        allowBlank: false
	    },
		{
	        fieldLabel: 'reportCode',
	        //afterLabelTextTpl: Ext.required,
	        name: 'reportCode',
	        readOnly:true,
	        xtype:'textfield',
	        allowBlank: false
	    },
		{
	        fieldLabel: 'web',
	        //afterLabelTextTpl: Ext.required,
	        name: 'web',
	        readOnly:true,
	        xtype:'textfield',
	        allowBlank: false
	    },
	   {
	  		fieldLabel: 'orgType_id',
		    name: 'orgType_id',
		    readOnly:true,
		    xtype:'combobox',
            typeAhead: true,
            triggerAction: 'all',
            queryMode: 'remote',
		    displayField: 'name',
		    valueField: 'id',
            store: Ext.create('Ext.data.Store', {
			    fields: ['id', 'name'], 
				proxy:{
				    type:'ajax',
				    url:Ext.ContextPath+'orgType/query',
				    reader:{
				    	type:'json',
				    	totalProperty:'total',
				    	root:'root'
				    }
				}
			})
        }
	  ];   

	var saveButton=Ext.create('Ext.button.Button',{
            text: '保存',
            iconCls:'form-save-button',
            //formBind: true,当设置这个值得时候，当表单里面有内容后，就自动会变成可执行
            disabled :true,
            itemId:'save',
            listeners:{
				disable:function(b,e){
					
					var fields=b.up('form').getForm().getFields( );
					fields.each(function(items){
						items.setReadOnly(true);
					});
				},
				enable:function(b){
					var form=b.up('form');
					var fields=form.getForm().getFields( );
					fields.each(function(items){
						if(items.getName()=='id'  && !form.createAction){
							items.setReadOnly(true);
						}else{
							items.setReadOnly(false);
						}
					});
				}
			},
            handler: function(btn) {
            	var form=this.up('form');
                if(!form.getForm().isValid()) {
                	return;
                }
                form.getForm().updateRecord();
				form.getRecord().save({
					success: function(record, operation) {
						btn.disable();
						me.fireEvent("saved");
					}
				});
            }
      });
	  me.buttons= [saveButton];    
      me.saveButton=saveButton;
      me.addEvents("saved");
      me.callParent();
	}
});
