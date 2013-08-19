Ext.define("Leon.LoginWin",{
	extend:'Ext.window.Window',
	frame:true,
	layout:'fit',
	title:'请登录',
	standardSubmit:true,
	initComponent: function () {
       var me = this;
       var form=new Ext.form.Panel({
			url: Ext.ContextPath+'/j_spring_security_check',
			frame:true,
			margin:'0 0 0 0',
			standardSubmit:me.standardSubmit,
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
		                       me.success(form, action);
		                    },
		                    failure: function(form, action) {
		                        me.failure(form, action);
		                    }
		                });
		            }
		    	}
		    }]
		});
		
	   me.items=[form];
       me.callParent();
	}
});