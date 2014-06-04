Ext.define('Leon.desktop.user.UserForm',{
	extend:'Ext.form.Panel',
	requires: [
	     'Leon.desktop.user.User','Leon.common.ux.MultiSelectField'
	],
	fieldDefaults: {
            msgTarget: 'side',
            labelWidth: 75,
            labelAlign:'right'
    },
    required:'<span style="color:red;font-weight:bold" data-qtip="Required">*</span>',
    defaultType: 'textfield',
    frame: true,
    bodyPadding: '5 5 0',
    //trackResetOnLoad:true,
    
    fieldDefaults: {
            labelWidth: 80,
            anchor: '100%'
        },

    layout: {
            type: 'vbox',
            align: 'stretch'  // Child items are stretched to full width
    },
	initComponent: function () {
       var me = this;
       defaultPassword="12345";
       me.items= [
	        {
	            fieldLabel: '登陆名',
	            afterLabelTextTpl: me.required,
	            name: 'loginName',
	            allowBlank: false,
	            readOnly:true,
	            tooltip: '请输入名称'
	        },{
	        	xtype:'hidden',
	            fieldLabel: '密码',
	            afterLabelTextTpl: me.required,
	            name: 'password',
	            //value:defaultPassword,
	            readOnly:true,
	            allowBlank: false
	        },{
	            fieldLabel: '姓名',
	            readOnly:true,
	            name: 'name'
	            //tooltip: "请输入url"
	        },{
	        	xtype:'checkboxfield',
                fieldLabel  : '是否已删除',
                name      : 'deleted',
                readOnly:true,
                inputValue: 'false',
                checked   : false
            },{
	        	xtype:'datefield',
	        	readOnly:true,
                fieldLabel  : '删除日期',
                name      : 'deletedDate',
                format:'Y-m-d'
            },{
	        	xtype:'checkboxfield',
                fieldLabel  : '是否锁定',
                name      : 'locked',
                readOnly:true,
                inputValue: 'false',
                checked   : false
            },{
	        	xtype:'checkboxfield',
                fieldLabel  : '是否可用',
                readOnly:true,
                name      : 'enable',
                inputValue: 'true',
                checked   : true
            },{
	        	xtype:'checkboxfield',
                fieldLabel  : '是否过期',
                readOnly:true,
                name      : 'accountExpired',
                readOnly:true,
                inputValue: 'true',
                checked   : false
            },{
	        	xtype:'datefield',
                fieldLabel  : '过期日期',
                readOnly:true,
                name      : 'expireDate',
                format:'Y-m-d'
            },{
	        	xtype:'datefield',
                fieldLabel  : '最后登录时间',
                readOnly:true,
                name      : 'lastLoginDate',
                format:'Y-m-d'
            },{
	        	xtype:'datefield',
                fieldLabel  : '创建日期',
                readOnly:true,
                name      : 'createDate',
                format:'Y-m-d'
            }
	    ];
       
       me.tbar=me.getTbarItems();
       
       me.addEvents("created");
       me.callParent();
	},
	hidePassordField:function(){
		this.getForm().findField("password").hide();
	},
	getTbarItems:function(){
		var me=this;
		var tools=[{
            text: '保存',
            itemId:'save',
            disabled :true,
            iconCls:'form-save-button',
            listeners:{
				disable:function(b,e){
					var fields=b.up('form').getForm().getFields( );
					fields.each(function(items){
						items.setReadOnly(true);
					});
				},
				enable:function(b){
					var form=b.up('form');
					var fields=form.getForm().getFields( );
					fields.each(function(items){
						if(items.getName()=='id' && !form.createAction){
							items.setReadOnly(true);
						}else{
							items.setReadOnly(false);
						}
					});
				}
			},
            handler: function(btn) {
            	var form=this.up('form');
                if(!form.getForm().isValid()) {
                	return;
                }
                form.getForm().updateRecord();
				form.getRecord().save({
					success: function(record, operation) {
						
						Ext.Msg.alert('消息','保存成功');
						if(me.iscreateNew){
							me.grid.getStore().reload();
							me.iscreateNew=false;
						} else {
							record.commit();
						}
						btn.disable();
						//me.up("window").close();
					}
				});
            }
        }];
		
		var create = new Ext.Action({
		    text: '新增',
		    itemId:'create',
		    //sdisabled:me.disabledAction,
		    handler: function(){
		    	me.onCreate();
		    	me.iscreateNew=true;
		    	
		    	me.down("button#save").enable();
		    },
		    iconCls: 'form-add-button'
		});
		tools.push(create);
		
	    var edit = new Ext.Action({
		    text: '编辑',
		    handler: function(){
		    	me.down("button#save").enable();
		    },
		    iconCls: 'form-update-button'
	    });
		tools.push(edit);
		
		var destroy = new Ext.Action({
		    text: '删除',
		    itemId:'destroy',
		    handler: function(){
		    	me.onDelete();
		        
		    },
		    iconCls: 'form-delete-button'
		});
		//me.addAction(destroy);
		tools.push(destroy);
		
		var physicsDel = new Ext.Action({
		    text: '物理删除',
		    iconCls: 'icons_list-remove',
		    handler: function(){
		    	
		    	Ext.Msg.confirm("删除",'确定要删除吗?物理删除后，将不可恢复!', function(btn, text){
					if (btn == 'yes'){
						var user=me.getForm().getRecord( );
						//var user=grid.getLastSelected( );//.getLastSelected( );
						user.destroy({
							params:{physicsDel:true},
							success:function(){
								//grid.getStore().reload();
								me.getForm().reset(true);
//								alert(1);
//								me.getForm().setValues({
//									enable:true
//								});
							}
						});
					}
				});
		    }
		});
		tools.push(physicsDel);
		
		var recover = new Ext.Action({
		    text: '恢复',
		    iconCls: 'icons_arrow-step-over',
		    handler: function(){
		    	Ext.Msg.confirm("删除",'恢复被逻辑删除的用户，确定要恢复吗?', function(btn, text){
					if (btn == 'yes'){
						//var user=grid.getLastSelected( );//.getLastSelected( );
						var user=me.getForm().getRecord( );
						Ext.Ajax.request({
							url:Ext.ContextPath+'/user/recover',
							params:{id:user.getId()},
							success:function(){
								//grid.getStore().reload();
								user.set("deleted",false);
								user.set("deletedDate",null);
								me.getForm().loadRecord(user);
							}
						});
						
					}
				});
		    }
		});
		tools.push(recover);
		
		var resetPwd = new Ext.Action({
		    text: '重置密码',
		    iconCls: 'icons_recycle',
		    handler: function(){
		    	var user=me.getForm().getRecord( );
		    	if(!user){
		    		return;
		    	}
		    	Ext.Msg.prompt("重置密码",'请输入新密码:', function(btn, text){
					if (btn == 'ok'){
						//.getLastSelected( );
						Ext.Ajax.request({
							url:Ext.ContextPath+'/user/resetPwd',
							params:{id:user.getId(),password:text},
							success:function(){
								//grid.getStore().reload();
								user.set("password",text);
							}
						});
						
					}
				},window,false,defaultPassword);
		    }
		});
		tools.push(resetPwd);
		
		return tools;
	},
	onCreate:function(){
		//alert('没有做');
		//sreturn;
		var modal=Ext.createModel('Leon.desktop.user.User',{enable:true,password:defaultPassword});
		this.getForm().loadRecord(modal);	
		//win.show();
	},
	onDelete:function(){
    	var me=this;
    	var record=this.getForm().getRecord( );
    	Ext.Msg.confirm("删除",'确定要删除吗?', function(btn, text){
			if (btn == 'yes'){
				Ext.Ajax.request({
					url:Ext.ContextPath+'/user/destroy',
					jsonData:record.data,
					method:'POST',
					success:function(){
						record.set("deleted",true);
						record.set("deletedDate",new Date());
						me.getForm().loadRecord(record);
					}
				});
			}
		});
    }
});