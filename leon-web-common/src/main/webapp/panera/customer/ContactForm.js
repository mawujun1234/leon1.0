Ext.define('Leon.panera.customer.ContactForm', {
    extend: 'Ext.form.Panel',
    requires: [
        'Ext.data.*',
        'Ext.form.*'
    ],
    xtype: 'form-checkout',
    
    
    frame: true,
    //title: 'Complete Check Out',
    bodyPadding: 5,
    autoScroll :true,
    initComponent: function(){
        
        Ext.apply(this, {
            //width: 550,
            fieldDefaults: {
                labelAlign: 'right',
                labelWidth: 90,
                msgTarget: 'qtip'
            },

            items: [{
                xtype: 'fieldset',
                title: '联系人信息',
                layout: 'anchor',
                defaults: {
                    anchor: '100%'
                    //labelWidth: 60
                },
                items: [{
                    xtype: 'container',
                    layout: 'hbox',
                    margin: '0 0 5 0',
                    items: [{
                        xtype: 'hidden',
                        fieldLabel: '联系人id',
                        name: 'id',
                        flex: 1,
                        allowBlank: false
                    },{
                        xtype: 'hidden',
                        fieldLabel: '是否是默认联系人',
                        name: 'isDefault',
                        value:true,
                        flex: 1,
                        allowBlank: false
                    },{
                        xtype: 'hidden',
                        fieldLabel: '客户id',
                        name: 'customer_id',
                        value:true,
                        flex: 1,
                        allowBlank: false
                    },{
                        //labelWidth: 110,
                        xtype: 'textfield',
                        fieldLabel: '联系人',
                        name: 'name',
                       // style: (!Ext.isIE6) ? 'opacity:.3' : '',
                        flex: 1,
                        allowBlank: false
                    }, {
                        //labelWidth: 110,
                        xtype: 'textfield',
                        fieldLabel: '职位',
                        name: 'position',
                       // style: (!Ext.isIE6) ? 'opacity:.3' : '',
                        flex: 1,
                        allowBlank: false
                    }]
                },{
                    xtype: 'container',
                    layout: 'hbox',
                    margin: '0 0 5 0',
                    items: [{
                        //labelWidth: 110,
                        xtype: 'textfield',
                        fieldLabel: '电话',
                        name: 'phone',
                       // style: (!Ext.isIE6) ? 'opacity:.3' : '',
                        flex: 1,
                        allowBlank: false
                    }, {
                        //labelWidth: 110,
                        xtype: 'textfield',
                        fieldLabel: '手机',
                        name: 'mobile',
                       // style: (!Ext.isIE6) ? 'opacity:.3' : '',
                        flex: 1,
                        allowBlank: false
                    }]
                }, {
                    xtype: 'container',
                    layout: 'hbox',
                    margin: '0 0 5 0',
                    items: [{
                        //labelWidth: 110,
                        xtype: 'textfield',
                        fieldLabel: '聊天账号',
                        name: 'chatNum',
                       // style: (!Ext.isIE6) ? 'opacity:.3' : '',
                        flex: 1,
                        allowBlank: false
                    },{
                        //labelWidth: 110,
                        xtype: 'textfield',
                        fieldLabel: '传真',
                        name: 'fax',
                       // style: (!Ext.isIE6) ? 'opacity:.3' : '',
                        flex: 1,
                        allowBlank: false
                    }]
                }, {
                    xtype: 'container',
                    layout: 'hbox',
                    margin: '0 0 5 0',
                    items: [{
                        //labelWidth: 110,
                        xtype: 'textfield',
                        fieldLabel: 'email',
                        name: 'email',
                       // style: (!Ext.isIE6) ? 'opacity:.3' : '',
                        flex: 1,
                        allowBlank: false
                    }]
                }]
            }
        ],

        buttons: [{
            text: '保存',
            width: 150,
            scope: this,
            handler: this.onSave
        }]    
        });
        this.callParent();
    },
    
    onSave: function(callBack){
    	var me=this;
        var form = this.getForm();
        if (form.isValid()) {
            //Ext.MessageBox.alert('Submitted Values', form.getValues(true));
            form.submit({
            	//standardSubmit :false,
            	clientValidation: true,
    			url: Ext.ContextPath+(me.update?'/contact/update':'/contact/create'),
    			success: function(form, action) {
    				Ext.Msg.alert('消息', "保存成功");
    			 	if(callBack instanceof Function){
    			 		callBack(action.result.root.id);
    			 	}
    			 	form.updateRecord();
    			 	//form.getRecord().set("star",action.result.root.star);
    			 	me.win.close();
    			 	if(!me.update){
    			 		me.grid.getStore().reload();
    			 	}
    			 },
    			 failure:function(form, action){
    			 	 //Ext.Msg.alert('Failure', "保存客户失败");
    			 }
            });
        }
        
        
    }
});