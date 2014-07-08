Ext.define('Leon.panera.customer.FollowupForm',{
	extend:'Ext.form.Panel',
	requires: [
	     'Leon.panera.customer.Followup'
	],
	fieldDefaults: {
        msgTarget: 'side',
        labelWidth: 85,
        labelAlign:'right',
        anchor: '90%'
    },
    frame: true,
    bodyPadding: '5 5 0',

 //   layout: {
 //       type: 'vbox',
 //       align: 'stretch'  // Child items are stretched to full width
 //   },
	initComponent: function () {
       var me = this;
       me.items= [{
            xtype: 'container',
            layout: 'hbox',
            margin: '0 0 5 0',
            items: [{
                xtype: 'hidden',
                fieldLabel: 'id',
                name: 'id',
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
                xtype: 'datefield',
            	format: 'Y-m-d',
                fieldLabel: '创建时间',
                name: 'createDate',
                       // style: (!Ext.isIE6) ? 'opacity:.3' : '',
                //flex: 1,
                value:new Date(),
                allowBlank: false
            },{
                fieldLabel: '跟进方式',
                name: 'method',
                //flex: 1,
                xtype: 'combobox',
                store:new Ext.data.Store({
				            fields:['id','name'],
				            proxy: {
				                type: 'memory',
				                reader: {
				                    //type: 'array'
				                }
				            },
				            data: [
					            {id:"email",name:'email'},
					            {id:"phone",name:'电话'},
					            {id:"chattool",name:'聊天工具'}
				            ]
				        }),
				 valueField: 'id',
                 displayField: 'name',
                 queryMode: 'local',
                 editable:false,
                 allowBlank: false,
                 forceSelection: true
           }]
      }, {
                xtype: 'container',
                layout: 'hbox',
                defaultType: 'textfield',
                margin: '0 0 5 0',
                items: [{
                    	xtype     : 'textareafield',
        				grow      : true,
                        fieldLabel: '跟进内容',
                        name: 'content',
                        flex: 1,
                        allowBlank: false
            }]
       },{
            xtype: 'container',
            layout: 'hbox',
            margin: '0 0 5 0',
            items: [{
                xtype: 'datefield',
            	format: 'Y-m-d',
                fieldLabel: '反馈时间',
                name: 'feedbackDate',
                       // style: (!Ext.isIE6) ? 'opacity:.3' : '',
                //flex: 1,
                allowBlank: true
            }]
      }, {
                xtype: 'container',
                layout: 'hbox',
                defaultType: 'textfield',
                margin: '0 0 5 0',
                items: [{
                    	xtype     : 'textareafield',
        				grow      : true,
                        fieldLabel: '反馈内容',
                        name: 'feedbackContetnt',
                        flex: 1,
                        allowBlank: true
            }]
       },{
            xtype: 'container',
            layout: 'hbox',
            margin: '0 0 5 0',
            items: [{
                xtype: 'datefield',
            	format: 'Y-m-d',
                fieldLabel: '下次跟进时间',
                name: 'nextDate',
                allowBlank: true
            },{
                xtype: 'checkbox',
                fieldLabel: '已处理',
                name: 'nextHandled'
            }]
      }, {
                xtype: 'container',
                layout: 'hbox',
                defaultType: 'textfield',
                margin: '0 0 5 0',
                items: [{
                    	xtype     : 'textareafield',
        				grow      : true,
                        fieldLabel: '下次跟进内容',
                        name: 'nextContent',
                        flex: 1,
                        allowBlank: true
            }]
       }];   
	  
	this.buttons= [{
            text: '保存',
            //width: 150,
            scope: this,
            handler: this.onSave
        }]    
      me.callParent();
	},
	onSave: function(){
    	var me=this;
        var form = this.getForm();
        if (form.isValid()) {
            //Ext.MessageBox.alert('Submitted Values', form.getValues(true));
        	console.log(me.update);
            form.submit({
            	//standardSubmit :false,
            	clientValidation: true,
    			url: Ext.ContextPath+(me.update?'/followup/update':'/followup/create'),
    			success: function(form, action) {
    				Ext.Msg.alert('消息', "保存成功");
    				me.getForm().findField("id").setValue(action.result.root.id);
    				//me.getForm().setValues(action.result.root);
    			 	form.updateRecord();
    			 	if(!me.update){
    			 		me.grid.getStore().reload();
    			 		me.update=true;
    			 	}
    			 	
    			 },
    			 failure:function(form, action){
    			 	 //Ext.Msg.alert('Failure', "保存客户失败");
    			 }
            });
        }
        
        
    }
//	/**
//	 * 重载父类的方法
//	 */
//	loadRecord:function(){
//		var form=this;
//		form.down("button#update").enable();
//		form.down("button#destroy").enable();
//		
//		form.callParent(arguments);
//	}
});
