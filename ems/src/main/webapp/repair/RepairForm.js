Ext.define('Ems.repair.RepairForm',{
	extend:'Ext.form.Panel',
	requires: [
	     'Ems.repair.Repair'
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
       me.items= [{
                    xtype: 'fieldcontainer',
                    //fieldLabel: 'Name',
                    layout: 'hbox',
                    combineErrors: true,
                    defaultType: 'textfield',
                    defaults: {
                        //hideLabel: 'true'
                    },
                    items: [{
                        name: 'repair_date',
                        xtype:'datefield',
                        fieldLabel: '报修时间',
                        flex: 1,
                        //emptyText: 'First',
                        readOnly:true,
                        allowBlank: true
                    },{
                        name: 'workunit_id',
                        fieldLabel: '作业单位',
                        flex: 1,
                        readOnly:true,
                        allowBlank: true
                    }]
      },{
                    xtype: 'fieldcontainer',
                    //fieldLabel: 'Name',
                    layout: 'hbox',
                    combineErrors: true,
                    defaultType: 'textfield',
                    defaults: {
                        //hideLabel: 'true'
                    },
                    items: [{
                        name: 'broken_memo',
                        fieldLabel: '故障描述',
                        flex: 1,
                        readOnly:true,
                        allowBlank: true
                    }]
     },{
                    xtype: 'fieldcontainer',
                    //fieldLabel: 'Name',
                    layout: 'hbox',
                    combineErrors: true,
                    defaultType: 'textfield',
                    defaults: {
                        //hideLabel: 'true'
                    },
                    items: [{
                        name: 'str_out_name',
                        fieldLabel: '来源仓库',
                        flex: 1,
                        //emptyText: 'First',
                        readOnly:true,
                        allowBlank: true
                    },{
                        name: 'str_out_date',
                        xtype:'datefield',
                        format:'Y-m-d',
                        fieldLabel: '出仓时间',
                        flex: 1,
                        readOnly:true,
                        allowBlank: true
                    }]
     },{
                    xtype: 'fieldcontainer',
                    //fieldLabel: 'Name',
                    layout: 'hbox',
                    combineErrors: true,
                    defaultType: 'textfield',
                    defaults: {
                        //hideLabel: 'true'
                    },
                    items: [{
                        name: 'rpa_in_date',
                        fieldLabel: '入维时间',
                        flex: 1,
                        //emptyText: 'First',
                        readOnly:true,
                        allowBlank: true
                    },{
                        name: 'rpa_user_id',
                        fieldLabel: '维修人',
                        flex: 1,
                        afterLabelTextTpl: Ext.required,
                        readOnly:false,
                        allowBlank: false
                    }]
     },{
                    xtype: 'fieldcontainer',
                    //fieldLabel: 'Name',
                    layout: 'hbox',
                    combineErrors: true,
                    defaultType: 'textfield',
                    defaults: {
                        //hideLabel: 'true'
                    },
                    items: [{
                        name: 'broken_reson',
                        fieldLabel: '故障原因',
                        flex: 1,
                        readOnly:false,
                        allowBlank: true
                    }]
     },{
                    xtype: 'fieldcontainer',
                    //fieldLabel: 'Name',
                    layout: 'hbox',
                    combineErrors: true,
                    defaultType: 'textfield',
                    defaults: {
                        //hideLabel: 'true'
                    },
                    items: [{
                        name: 'memo',
                        fieldLabel: '备注',
                        flex: 1,
                        readOnly:false,
                        allowBlank: true
                    }]
     },
     {
	        fieldLabel: 'id',
	        //afterLabelTextTpl: Ext.required,
	        name: 'id',
	        readOnly:true,
	        xtype:'hidden',
	        allowBlank: false
	    },
		{
	        fieldLabel: 'ecode',
	        //afterLabelTextTpl: Ext.required,
	        name: 'ecode',
	        readOnly:true,
	        xtype:'hidden',
	        allowBlank: false
	    },
		{
	        fieldLabel: 'installIn_id',
	        //afterLabelTextTpl: Ext.required,
	        name: 'installIn_id',
	        readOnly:true,
	        xtype:'hidden',
	        allowBlank: false
	    },
		{
	        fieldLabel: 'rpa_id',
	        //afterLabelTextTpl: Ext.required,
	        name: 'rpa_id',
	        readOnly:true,
	        xtype:'hidden',
	        allowBlank: false
	    },
		{
	        fieldLabel: 'rpa_in_oper_id',
	        //afterLabelTextTpl: Ext.required,
	        name: 'rpa_in_oper_id',
	        readOnly:true,
	        xtype:'hidden',
	        allowBlank: false
	    },
		{
            fieldLabel: 'rpa_out_date',
            name: 'rpa_out_date',
            readOnly:true,
            xtype: 'hidden',
            format: 'Y-m-d'
        },
		{
	        fieldLabel: 'rpa_out_oper_id',
	        //afterLabelTextTpl: Ext.required,
	        name: 'rpa_out_oper_id',
	        readOnly:true,
	        xtype:'hidden',
	        allowBlank: false
	    },
		{
	        fieldLabel: 'rpa_type',
	        //afterLabelTextTpl: Ext.required,
	        name: 'rpa_type',
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
            fieldLabel: 'str_in_date',
            name: 'str_in_date',
            readOnly:true,
            xtype: 'hidden',
            format: 'Y-m-d'
        },
		{
	        fieldLabel: 'str_in_id',
	        //afterLabelTextTpl: Ext.required,
	        name: 'str_in_id',
	        readOnly:true,
	        xtype:'hidden',
	        allowBlank: false
	    },
		{
	        fieldLabel: 'str_in_oper_id',
	        //afterLabelTextTpl: Ext.required,
	        name: 'str_in_oper_id',
	        readOnly:true,
	        xtype:'hidden',
	        allowBlank: false
	    },
		{
	        fieldLabel: 'str_out_id',
	        //afterLabelTextTpl: Ext.required,
	        name: 'str_out_id',
	        readOnly:true,
	        xtype:'hidden',
	        allowBlank: false
	    },
		{
	        fieldLabel: 'str_out_oper_id',
	        //afterLabelTextTpl: Ext.required,
	        name: 'str_out_oper_id',
	        readOnly:true,
	        xtype:'hidden',
	        allowBlank: false
	    }
     ];   
     
     
	  
	  
	  var saveButton=Ext.create('Ext.button.Button',{
            text: '保存',
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
//	/**
//	 * 重载父类的方法
//	 */
//	loadRecord:function(){
//		var form=this;
//		form.down("button#update").enable();
//		form.down("button#destroy").enable();
//		
//		form.callParent(arguments);
//	}
});