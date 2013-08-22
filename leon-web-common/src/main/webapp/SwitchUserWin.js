Ext.define("Leon.SwitchUserWin",{
	extend:'Ext.window.Window',
	requires:['Leon.desktop.user.UserQueryGrid'],
	//frame:true,
	//layout:'fit',
	layout: {
        type: 'vbox',
        align: 'center'
    },
	width:400,
	height:400,
	modal:true,
	title:'切换用户',
	initComponent: function () {
       var me = this;
       var form=new Ext.form.Panel({
			url: Ext.ContextPath+'/j_spring_security_switch_user',
			frame:true,
			width:400,
			flex: 0.3,
			margin:'0 0 0 0',
			standardSubmit:true,
			items: [{
				xtype:'textfield',
		        fieldLabel: '用户名',
		        name: 'j_username',
		        allowBlank: false
		    },{
		    	text:'切换',
		    	xtype:'button',
		    	formBind: true,
		    	handler:function(){
		    		var basicform = this.up('form').getForm();
		            if (basicform.isValid()) {
		            	me.getEl().mask("正在切换，请稍候...");
		                basicform.submit({
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
		
	   var userGrid=Ext.create('Leon.desktop.user.UserQueryGrid',{
			//region:'center',
	   		url:'/switchUser/query',
			width:400,
			flex: 0.8,
			listeners:{
				itemdblclick:function(grid,record){
					form.getForm().setValues({j_username:record.get("loginName")});
				}
			}
		});
		
	   me.items=[form,userGrid];
       me.callParent();
	}
});