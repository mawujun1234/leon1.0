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
	isprod:false,//判断现在是不是品名的form
	parent_id:'',//大类，小类的值
 //   layout: {
 //       type: 'vbox',
 //       align: 'stretch'  // Child items are stretched to full width
 //   },
	initComponent: function () {
       var me = this;
       var maxLength=2;//me.parent_id.length+2;//me.isType?2:3;
       
	
       me.items= [
       {
							xtype : 'fieldcontainer',
							fieldLabel : '编码',
							afterLabelTextTpl: Ext.required,
							//labelStyle : 'font-weight:bold;padding:0;',
							layout : 'hbox',
							defaultType : 'textfield',
							fieldDefaults : {
				// labelAlign: 'top'
							},

							items : [{
								width : 35,
								//fieldLabel : '父编码',
								// afterLabelTextTpl: Ext.required,
								margin:'0 5 0 0',
								name : 'parent_id',
								readOnly:true
									// xtype:'hidden',
									// allowBlank: false
								}, {
									flex:1,
	        //fieldLabel: '编码',
	        name: 'id',
	        minLength:maxLength,
	        maxLength:maxLength,
	        length:maxLength,
	        xtype:'textfield',
	        allowBlank: false,
	        listeners:{
	        	change:function(field,newValue,oldValue){
		        	if(maxLength<newValue.length){
		        		field.setValue(oldValue);
		        	}
		        }
	        }
	    }]
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
	        fieldLabel: '描述',
	        //afterLabelTextTpl: Ext.required,
	        name: 'memo',
	        xtype:'textfield',
	        maxLength:50,
	        value:"",
	        allowBlank: true
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
            	//alert(form.getForm().isValid());
                if(!form.getForm().isValid()) {
                	return;
                }
               // form.getForm().findField("id").setValue(form.getForm().findField("parent_id").getValue()+form.getForm().findField("end_id").getValue());
                Ext.Msg.confirm("消息","确定要保存吗?",function(btn){
                	if(btn=='yes'){
                		form.getForm().updateRecord();
                		//form.getForm().getRecord().set("brand_name",form.getForm().findField("brand_id").getRawValue());
						form.getRecord().save({
							url:form.url,
							success: function(record, operation) {
													
								me.fireEvent("saved");
							}
						});
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
