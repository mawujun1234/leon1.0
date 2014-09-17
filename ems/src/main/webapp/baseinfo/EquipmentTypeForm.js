Ext.define('Ems.baseinfo.EquipmentTypeForm',{
	extend:'Ext.form.Panel',
	requires: [
	     'Ems.baseinfo.EquipmentType'
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
       var maxLength=me.isSubetype?3:2;
       me.items= [
		{
	        fieldLabel: '编码',
	        afterLabelTextTpl: Ext.required,
	        name: 'id',
	        minLength:maxLength,
	        maxLength:maxLength,
	        length:me.isSubetype?3:2,
	        xtype:'textfield',
	        allowBlank: false,
	        listeners:{
	        	change:function(field,newValue,oldValue){
		        	if(maxLength<newValue.length){
		        		field.setValue(oldValue);
		        	}
		        }
	        }
	    },
	    {
	        fieldLabel: '名称',
	        afterLabelTextTpl: Ext.required,
	        name: 'name',
	        //readOnly:true,
	        xtype:'textfield',
	        allowBlank: false
	    },
	    {
	        fieldLabel: '单位',
	        //afterLabelTextTpl: Ext.required,
	        name: 'unit',
	        //readOnly:true,
	        xtype:'textfield',
	        allowBlank: true
	    },
	    {
	        fieldLabel: '父编码',
	        //afterLabelTextTpl: Ext.required,
	        name: 'parent_id',
	       
	        xtype:'hidden',
	        allowBlank: false
	    },
		{
	        fieldLabel: 'level',
	        //afterLabelTextTpl: Ext.required,
	        name: 'level',
	       
	        xtype:'hidden'
	    },
		{
	        fieldLabel: 'status',
	        //afterLabelTextTpl: Ext.required,
	        name: 'status',
	        xtype:'hidden'
	    }
	  ];   
	  
	  
	  var saveButton=Ext.create('Ext.button.Button',{
            text: '保存',
            iconCls:'form-save-button',
            handler: function(btn) {
            	var form=this.up('form');
                if(!form.getForm().isValid()) {
                	return;
                }
                form.getForm().updateRecord();
				form.getRecord().save({
					url:form.url,
					success: function(record, operation) {
											
						me.fireEvent("saved");
					}
				});
            }
      });
      
      
	  me.buttons=[saveButton];

	
      me.addEvents("saved");
      me.callParent();
	},
	/**
	 * 重载父类的方法
	 */
	loadRecord:function(){
		var form=this;
		form.down("button#update").enable();
		form.down("button#destroy").enable();
		
		form.callParent(arguments);
	}
});
