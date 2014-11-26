Ext.define('Ems.task.TaskForm',{
	extend:'Ext.form.Panel',
	requires: [
	     'Ems.task.Task'
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
            fieldLabel: 'approveDate',
            name: 'approveDate',
            readOnly:true,
            xtype: 'hidden',
            format: 'Y-m-d'
        },
		{
            fieldLabel: 'completeDate',
            name: 'completeDate',
            readOnly:true,
            xtype: 'hidden',
            format: 'Y-m-d'
        },
		{
            fieldLabel: 'createDate',
            name: 'createDate',
            readOnly:true,
            xtype: 'hidden',
            format: 'Y-m-d'
        },
        {
            fieldLabel: 'submitDate',
            name: 'submitDate',
            readOnly:true,
            xtype: 'hidden',
            format: 'Y-m-d'
        },
        {
            fieldLabel: 'startHandDate',
            name: 'startHandDate',
            readOnly:true,
            xtype: 'hidden',
            format: 'Y-m-d'
        },
		{
	        fieldLabel: 'hitchReason',
	        //afterLabelTextTpl: Ext.required,
	        name: 'hitchReason',
	        readOnly:true,
	        xtype:'hidden',
	        allowBlank: false
	    },
		{
	        fieldLabel: 'hitchType',
	        //afterLabelTextTpl: Ext.required,
	        name: 'hitchType',
	        readOnly:true,
	        xtype:'hidden',
	        allowBlank: false
	    },
	    {
	        fieldLabel: 'status',
	        //afterLabelTextTpl: Ext.required,
	        name: 'status',
	        readOnly:true,
	        xtype:'hidden',
	        allowBlank: false
	    },
	    {
	        fieldLabel: '状态',
	        //afterLabelTextTpl: Ext.required,
	        name: 'status_name',
	        readOnly:true,
	        xtype:me.showSendButton?'hidden':'textfield',
	        allowBlank: true
	    },
		{
	        fieldLabel: '任务描述',
	        afterLabelTextTpl: Ext.required,
	        name: 'memo',
	        readOnly:false,
	        xtype:'textareafield',
	        grow:true,
	        allowBlank: false
	    },
	    {
	        fieldLabel: '任务类型',
	        //afterLabelTextTpl: Ext.required,
	        name: 'type',
	        readOnly:false,
	        xtype:'hidden',
	        allowBlank: false
	    },
	    {
	        fieldLabel: 'customer_id',
	        //afterLabelTextTpl: Ext.required,
	        name: 'customer_id',
	        readOnly:true,
	        xtype:'hidden',
	        allowBlank: false
	    },
	     {
	        fieldLabel: '客户名称',
	        //afterLabelTextTpl: Ext.required,
	        name: 'customer_name',
	        readOnly:true,
	        xtype:'textfield',
	        allowBlank: false
	    },
		{
	        fieldLabel: 'pole_id',
	        //afterLabelTextTpl: Ext.required,
	        name: 'pole_id',
	        readOnly:true,
	        xtype:'hidden',
	        allowBlank: false
	    },
	    {
	        fieldLabel: '点位',
	        //afterLabelTextTpl: Ext.required,
	        name: 'pole_name',
	        readOnly:true,
	        xtype:'textfield',
	        allowBlank: false
	    },
		{
	        fieldLabel: '地址',
	        //afterLabelTextTpl: Ext.required,
	        name: 'pole_address',
	        readOnly:true,
	        xtype:'textfield',
	        allowBlank: false
	    },
		
	    {
	        fieldLabel: 'workunit_id',
	        //afterLabelTextTpl: Ext.required,
	        name: 'workunit_id',
	        readOnly:true,
	        xtype:'hidden',
	        allowBlank: false
	    },
	    {
	        fieldLabel: '作业单位',
	        //afterLabelTextTpl: Ext.required,
	        name: 'workunit_name',
	        readOnly:true,
	        xtype:'textfield',
	        allowBlank: false
	    }
	  ];   
	  
	  
	  var send_Button=Ext.create('Ext.button.Button',{
            text: '发送',
            iconCls:'form-save-button',
            //formBind: true,当设置这个值得时候，当表单里面有内容后，就自动会变成可执行
            hidden :!me.showSendButton,

            handler: function(btn) {
            	var form=this.up('form');
                if(!form.getForm().isValid()) {
                	return;
                }
                form.getEl().mask("正在执行...");
                form.getForm().updateRecord();
				form.getRecord().save({
					success: function(record, operation) {

						form.getEl().unmask();
						alert("发送成功!");
						me.fireEvent("sended",form);
						
					},
					failure:function(){
						form.getEl().unmask();
					}
				});
            }
      });
      
	  me.buttons=	[send_Button];

	
      me.addEvents("sended");
      me.callParent();
	}
});
