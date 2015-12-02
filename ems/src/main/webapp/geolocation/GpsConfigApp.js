Ext.require("Ems.geolocation.GpsConfig");
Ext.onReady(function(){
	Ext.Ajax.request({
		url:Ext.ContextPath+"/gpsConfig/get.do",
		success:function(response){
			var obj=Ext.decode(response.responseText);
			var  interval_field=form.getForm().findField("interval" );
			interval_field.setValue(obj.root.interval);
		}
		
	});

	var saveButton=Ext.create('Ext.button.Button',{
            text: '保存',
            iconCls:'form-save-button',
            handler: function(btn) {
            	var  interval_field=form.getForm().findField("interval" );
					Ext.Ajax.request({
						url:Ext.ContextPath+"/gpsConfig/update.do",
						params:{
							interval:interval_field.getValue()
						},
						success:function(response){
							alert("保存成功!");
							//var obj=Ext.decode(response.responseText);
							//var  interval_field=form.getForm().findField("interval" );
							//interval_field.setValue(obj.interval);
						}
					});	
            }
	});

	var form=Ext.create('Ext.form.Panel',{
		region:'center',
		//collapsible: true,
		frame:true,
		//title:'表单',
		items: [
		{
	        fieldLabel: 'id',
	        //afterLabelTextTpl: Ext.required,
	        name: 'id',
	        xtype:'hidden',
	        allowBlank: false
	    },
		{
	        fieldLabel: '时间间隔(秒)',
	        //afterLabelTextTpl: Ext.required,
	        name: 'interval',
	        readOnly:false,
	        xtype:'numberfield',
	        //minValue:30,
	        allowBlank: false
	    }
	  ],
	  buttons:[saveButton]
	});
	
	
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'fit',
		items:[form]
	});

});