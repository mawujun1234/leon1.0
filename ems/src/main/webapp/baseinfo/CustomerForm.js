Ext.define('Ems.baseinfo.CustomerForm',{
	extend:'Ext.form.Panel',
	requires: [
	     'Ems.baseinfo.Customer'
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
	        
	        xtype:'hidden',
	        allowBlank: false
	    },
		
		{
	        fieldLabel: '名称',
	        //afterLabelTextTpl: Ext.required,
	        name: 'name',
	        
	        xtype:'textfield',
	        allowBlank: false
	    },
		{
	        	xtype:'combobox',
			    fieldLabel: '类型',
			    store:  Ext.create('Ext.data.Store', {
				    fields: ['id', 'name'],
				    data : [
				        {"id":0, "name":"机关"},
				        {"id":1, "name":"企业"}
				    ]
				}),
			    queryMode: 'local',
			    displayField: 'name',
			    valueField: 'id',
			    value:0,
			    editable:false,
			    value:true,
			    name:'type'
		},
	    {
	        	xtype:'combobox',
			    fieldLabel: '状态',
			    store:  Ext.create('Ext.data.Store', {
				    fields: ['id', 'name'],
				    data : [
				        {"id":true, "name":"有效"},
				        {"id":false, "name":"无效"}
				    ]
				}),
			    queryMode: 'local',
			    displayField: 'name',
			    valueField: 'id',
			    
			    editable:false,
			    value:true,
			    name:'status'
		},
		{
	        fieldLabel: '描述',
	        //afterLabelTextTpl: Ext.required,
	        name: 'memo',
	        grow      : true,
	        autoScroll:true,
	        xtype:'textareafield',
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
