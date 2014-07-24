Ext.define('Ext.ms.view.CheckCode',{ 
    extend: 'Ext.form.field.Text',  
    alias: 'widget.checkcode', 
    inputTyle:'codefield', 
    codeUrl:Ext.BLANK_IMAGE_URL, 
    isLoader:true, 
    onRender:function(ct,position){ 
        this.callParent(arguments); 
        this.codeEl = ct.createChild({tag: 'img', src: Ext.BLANK_IMAGE_URL}); 
        //this.codeEl = ct.createChild([{tag:'span',html:'    '},{tag: 'img', src:Ext.BLANK_IMAGE_URL,align:'absbottom'}]);
        
        this.codeEl.addCls('x-form-code'); 
        this.codeEl.on('click', this.loadCodeImg, this); 
         
        if (this.isLoader) this.loadCodeImg(); 
    }, 
    alignErrorIcon: function() { 
        this.errorIcon.alignTo(this.codeEl, 'tl-tr', [2, 0]); 
    }, 

    loadCodeImg: function() { 
        this.codeEl.set({ src: this.codeUrl + '?id=' + Math.random() }); 
    } 

}) 

/*
* 用户带验证码登录页面
* sheak 
* validateCode.jsp 生成验证码 
* 1202增加键盘回车提交功能（粗体部分）
*/

Ext.define('Ext.ux.ms.loginDialog',{
   extend:'Ext.window.Window', 
   alias:'widget.loginBox',
   requires: ['Ext.form.*','Ext.ms.view.CheckCode'], 
   initComponent:function(){ 
   	var me=this;
//        var checkcode = Ext.create('Ext.ms.view.CheckCode',{ 
//            fieldCls : 'rand',
//            fieldLabel : '验证码', 
//            name : 'validateCode', 
//            id : 'CheckCode', 
//            allowBlank : false, 
//            isLoader:true, 
//            blankText : '验证码不能为空', 
//            codeUrl: 'validateCode.jsp', 
//            width : 160 
//        }) 
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
                fieldLabel: '用户名',
                name:'username',
                fieldCls:'user',
                blankText : '用户名不能为空', 
                allowBlank: false, 
                width:240 
            },{ 
                xtype: 'textfield', 
                fieldLabel: '密&nbsp;&nbsp;&nbsp;码', 
                name:'password',
                fieldCls:'key',
                allowBlank: false, 
                blankText : '密码不能为空', 
                enableKeyEvents:true,
                width:240, 
                inputType : 'password' ,
                listeners:{
                	keypress:function(field,e){
                		if(e.getKey( )==e.ENTER ){
                			me.login();
                		}
                	}
                }
            }//,checkcode
            ], 
            buttons:[{ 
                text:'登录', 
                scope:this,
                handler:me.login
//                handler:function(){ 
//                   var loginform=form.getForm();
//                   if(loginform.isValid()){
//                	  loginform.submit({ 
//	            		  waitMsg : '正在登录......', 
//	            		  url : Ext.ContextPath+'/login.do', 
//	            		  success : function(form, action) {
//	            			  window.location.href = 'index.jsp';
//	            		  }, 
//	            		  failure : function(form, action) {
//		            		  form.reset();
//						        switch (action.failureType) {
//						            case Ext.form.Action.CLIENT_INVALID:
//									    //客户端数据验证失败的情况下，例如客户端验证邮件格式不正确的情况下提交表单  
//									    ShowMessage('提示','数据错误，非法提交');  
//						                break;
//						            case Ext.form.Action.CONNECT_FAILURE:
//									    //服务器指定的路径链接不上时  
//									    ShowMessage('连接错误','指定路径连接错误!'); 
//						                break;
//						            case Ext.form.Action.SERVER_INVALID:
//						            	//服务器端你自己返回success为false时  
//									     ShowMessage('友情提示', action.result.root);	
//									     break;
//									default:
//										 //其它类型的错误  
//			                             ShowMessage('警告', '服务器数据传输失败：'+action.response.responseText); 
//									     break;
//						       }
//	            		  }
//	            		  
//	            		});
//                   }
//                } 
            },{ 
                text:'取消', 
                handler:function(){ 
                   form.findLayoutController().hide();
                } 
            }] 
        });
        
       
        Ext.apply(this,{ 
            height: 160, 
            width: 280, 
            title: '用户登陆', 
            closeAction: 'hide', 
            closable : true,
            layout: 'fit', 
            modal : true,  
            
            plain : true, 
            resizable: false,
            items:form 
        });
      	this.form=form;
        this.callParent(arguments); 
    } ,
   login:function(){
   	var form=this.form;
        	 var loginform=form.getForm();
                   if(loginform.isValid()){
                	  loginform.submit({ 
	            		  waitMsg : '正在登录......', 
	            		  url : Ext.ContextPath+'/login.do', 
	            		  success : function(form, action) {
	            		  	if(action.result.success){
	            		  		window.location.href = 'index.jsp';
	            		  	}
	            			  //
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
});
function ShowMessage(title,msg){
	Ext.Msg.alert(title,msg);
}
var lwin;
function login(){
	requires:['Ext.ux.ms.loginDialog'] 
	if(!lwin){
		lwin=Ext.create('Ext.ux.ms.loginDialog',{iconCls: 'icon-login'});
	}
	lwin.show();
}