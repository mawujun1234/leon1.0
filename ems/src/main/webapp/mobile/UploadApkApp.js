Ext.require("Ems.task.Overtime");
Ext.onReady(function(){
	var form=Ext.create('Ext.form.Panel',{
		frame:true,
		items:[
			{
		        fieldLabel: '版本',
		        //afterLabelTextTpl: Ext.required,
		        name: 'version',
		        readOnly:false,
		        labelWidth:120,
		        xtype:'numberfield',
		        //value:1440,
		        minValue:60,
		        allowBlank: false
		    },
			{
		        fieldLabel: '未查看超时时间(分)',
		        //afterLabelTextTpl: Ext.required,
		        name: 'read',
		        readOnly:false,
		        labelWidth:120,
		        xtype:'hidden',
		        //value:1440,
		        minValue:60,
		        allowBlank: false
		    }
		  ],
		  buttons:[{
		  	text: '保存',
            iconCls:'form-save-button',
            handler: function(btn) {
            	var form=btn.up('form');
                if(!form.getForm().isValid()) {
                	return;
                }
                form.submit({
                	clientValidation: true,
                	url:Ext.ContextPath+"/overtime/update.do",
                	success:function(form, action){
                		 Ext.Msg.alert('消息',"保存成功!");
                		 
                	}
                });
            }
		  }]
	});
	Ext.Ajax.request({
		url:Ext.ContextPath+"/overtime/load.do",
		params:{id:'overtime'},
		success:function(response){
			var obj=Ext.decode(response.responseText);
			var record=Ext.create('Ems.task.Overtime',obj.root);
			form.loadRecord(record);
		}
	});
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'fit',
		frame:true,
		items:[form]
	});

});