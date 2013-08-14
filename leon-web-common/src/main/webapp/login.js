Ext.onReady(function(){
	var form=new Ext.form.Panel({
		url: '/j_spring_security_check',
		frame:true,
		margin:'0 0 0 0',
		method:'POST',
		items: [{
			xtype:'textfield',
	        fieldLabel: '用户名',
	        name: 'j_username',
	        allowBlank: false
	    },{
	    	xtype:'textfield',
	        fieldLabel: '密码',
	        name: 'j_password',
	        inputType:'password',
	        allowBlank: false
	    },{
	    	text:'提交',
	    	xtype:'button',
	    	formBind: true,
	    	
	    	handler:function(){
	    		var form = this.up('form').getForm();
	            if (form.isValid()) {
	                form.submit({
	                	//method:'GET',
	                    success: function(form, action) {
	                       Ext.Msg.alert('Success', action.result.msg);
	                    },
	                    failure: function(form, action) {
	                        Ext.Msg.alert('Failed', action.result.msg);
	                    }
	                });
	            }
	    	}
	    }]
	});
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'fit',
		items:[form]
	});
});