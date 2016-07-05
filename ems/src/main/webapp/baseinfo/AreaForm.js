Ext.define('Ems.baseinfo.AreaForm',{
	extend:'Ext.form.Panel',
	requires: [
	     'Ems.baseinfo.Area'
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
	        allowBlank: false
	    },
//		{
//	        fieldLabel: '作业单位',
//	        xtype:'combobox',
//	        //afterLabelTextTpl: Ext.required,
//	        name: 'workunit_id',
//		    displayField: 'name',
//		    valueField: 'id',
//		    queryParam: 'name',
//    		queryMode: 'remote',
//    		triggerAction: 'query',
//    		minChars:-1,
//		    trigger1Cls: Ext.baseCSSPrefix + 'form-clear-trigger',
//		    trigger2Cls: Ext.baseCSSPrefix + 'form-arrow-trigger',//'form-search-trigger',
//			onTrigger1Click : function(){
//			    var me = this;
//			    me.setValue('');
//			},
//	        allowBlank: false,
//	        store:Ext.create('Ext.data.Store', {
//		    	fields: ['id', 'name'],
//			    proxy:{
//			    	type:'ajax',
//			    	url:Ext.ContextPath+"/workUnit/queryCombo.do",
//			    	reader:{
//			    		type:'json',
//			    		root:'root'
//			    	}
//			    }
//		   })
//	    },
		{
	        fieldLabel: '片区名称',
	        //afterLabelTextTpl: Ext.required,
	        name: 'name',
	        readOnly:false,
	        xtype:'textfield',
	        allowBlank: false
	    },
	    {
	        fieldLabel: '描述',
	        //afterLabelTextTpl: Ext.required,
	        name: 'memo',
	        readOnly:false,
	        xtype:'textareafield',
	        grow      : true,
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
