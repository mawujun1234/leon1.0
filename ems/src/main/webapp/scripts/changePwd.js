
  Ext.apply(Ext.form.field.VTypes, {
        password: function(val, field) {
            if (field.initialPassField) {
                var pwd = field.up('form').down('#' + field.initialPassField);
                return (val == pwd.getValue());
            }
            return true;
        },

        passwordText: '新密码不一致!'
    });

Ext.define('Ext.ux.ms.changePwdwin',{
   extend:'Ext.window.Window', 
   alias:'widget.changePwdBox',
   requires: ['Ext.form.*','Ext.ms.view.CheckCode'], 
   initComponent:function(){ 

        var form = Ext.widget('form',{ 
            border: false, 
            frame:true,
            bodyPadding: 10,
            fieldDefaults: { 
                labelAlign: 'left', 
                labelWidth: 55, 
                labelStyle: 'font-weight:bold' 
            }, 
            defaults: { 
            	msgTarget:'side',
                margins: '0 0 10 0'
            }, 
            items:[{ 
                xtype: 'textfield', 
                fieldLabel: '源密码',
                name:'password_old',
               // fieldCls:'key',
                blankText : '密码不能为空', 
                allowBlank: false, 
                inputType : 'password',
                width:240 
            },{ 
                xtype: 'textfield', 
                fieldLabel: '新密码', 
                name:'password_new',
                itemId:'password_new',
                //fieldCls:'key',
                allowBlank: false, 
                //blankText : '密码不能为空', 
                width:240, 
                inputType : 'password',
                listeners: {
	                validitychange: function(field){
	                    field.next().validate();
	                },
	                blur: function(field){
	                    field.next().validate();
	                }
	            }
            },{ 
                xtype: 'textfield', 
                fieldLabel: '再一次', 
                name:'password_new_repeat',
                //fieldCls:'key',
                allowBlank: false, 
                //blankText : '密码不能为空', 
                width:240, 
                vtype: 'password',
                initialPassField: 'password_new',
                inputType : 'password'  
            }
            ], 
            buttons:[{ 
                text:'修改', 
                handler:function(){ 
                   var loginform=form.getForm();
                   if(loginform.isValid()){
                	  loginform.submit({ 
	            		  waitMsg : '正在修改......', 
	            		  url : Ext.ContextPath+'/user/changePwd.do', 
	            		  success : function(form, action) {
	            			  //window.location.href = 'index.jsp';
	            		  	ShowMessage('提示','修改成功!'); 
	            		  	changePwdwin.close();
	            		  }, 
	            		  failure : function(form, action) {
		            		  form.reset();
						        switch (action.failureType) {
						            case Ext.form.Action.CLIENT_INVALID:
									    //客户端数据验证失败的情况下，例如客户端验证邮件格式不正确的情况下提交表单  
									    ShowMessage('提示','数据错误，非法提交');  
						                break;
						            case Ext.form.Action.CONNECT_FAILURE:
									    //服务器指定的路径链接不上时  
									    ShowMessage('连接错误','指定路径连接错误!'); 
						                break;
						            case Ext.form.Action.SERVER_INVALID:
						            	//服务器端你自己返回success为false时  
									     ShowMessage('友情提示', action.result.root);	
									     break;
									default:
										 //其它类型的错误  
			                             ShowMessage('警告', '服务器数据传输失败：'+action.response.responseText); 
									     break;
						       }
	            		  }
	            		  
	            		});
                   }
                } 
            },{ 
                text:'取消', 
                handler:function(){ 
                   form.findLayoutController().hide();
                } 
            }] 
        }) 
        Ext.apply(this,{ 
            height: 160, 
            width: 280, 
            title: '修改密码', 
            closeAction: 'hide', 
            closable : true,
            layout: 'fit', 
            modal : true,  
            
            plain : true, 
            resizable: false,
            items:form 
        });
        this.getLForm=function(){
        	return form;
        }
        this.callParent(arguments); 
    } 
});
var changePwdwin;
function changePwd(){
	requires:['Ext.ux.ms.changePwdwin'] 
	if(!changePwdwin){
		changePwdwin=Ext.create('Ext.ux.ms.changePwdwin',{iconCls: 'icon-login'});
	}
	changePwdwin.show();
}