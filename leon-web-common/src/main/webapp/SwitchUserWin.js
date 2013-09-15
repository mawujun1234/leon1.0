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
			url: Ext.JspContextPath+'/j_spring_security_switch_user',
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
		            	var values=basicform.getValues();
		            	Ext.Ajax.request({
			    			url:Ext.ContextPath+'/switchUser/checkPermission',
			    			params:{j_username:values.j_username},
			    			success:function(response){
			    				var obj=Ext.decode(response.responseText);
			    				if(!obj.success){
			    					Ext.Msg.alert("消息","你没有权限切换到该用户,可切换的用户范围请点击下面的表格的插叙按钮进行查看。");
			    					return;
			    				}
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
			    		});
		            	
		            }
		    	}
		    }]
		});
		
	   var userGrid=Ext.create('Leon.desktop.user.UserQueryGrid',{
			//region:'center',
	   		url:Ext.ContextPath+'/switchUser/query',
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