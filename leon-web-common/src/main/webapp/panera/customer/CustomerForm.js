Ext.define('Leon.panera.customer.CustomerForm', {
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
        var me=this;
        Ext.apply(this, {
            //width: 550,
            fieldDefaults: {
                labelAlign: 'right',
                labelWidth: 90,
                msgTarget: 'qtip'
            },

            items: [{
                xtype: 'fieldset',
                title: '基本信息',
                defaultType: 'textfield',
                layout: 'anchor',
                defaults: {
                    anchor: '100%'
                },
                items: [{
                    xtype: 'fieldcontainer',
                    //fieldLabel: 'Name',
                    layout: 'hbox',
                    combineErrors: true,
                    defaultType: 'textfield',
                    defaults: {
                        //hideLabel: 'true'
                    },
                    items: [{
                        name: 'id',
                        xtype:'hidden',
                        fieldLabel: '客户id',
                        flex: 2,
                        //emptyText: 'First',
                        allowBlank: true
                    },{
                        name: 'code',
                        fieldLabel: '客户编号',
                        //flex: 1,
                        //emptyText: 'First',
                        //afterLabelTextTpl: Ext.required,
                        allowBlank: true
                    },{
                        name: 'name',
                        fieldLabel: '客户名称',
                        //flex: 1,
                        //emptyText: 'First',
                        afterLabelTextTpl: Ext.required,
                        allowBlank: false
                    }]
                }, {
                    xtype: 'container',
                    layout: 'hbox',
                    defaultType: 'textfield',
                    margin: '0 0 5 0',
                    items: [{
                        name: 'customerSource_id',
                        fieldLabel: '客户来源',
                        xtype: 'combobox',
                        store: new Ext.data.Store({
				            fields:['id','name'],
				            proxy: {
				            	url:Ext.ContextPath+'/customerSource/query',
				                type: 'ajax',
				                reader: {
				                    type: 'json',
				                    root:'root'
				                }
				            }
				        }),
                        valueField: 'id',
                        displayField: 'name',
                        //typeAhead: true,
                        queryMode: 'remote',
                        editable:false,
                        //disabled: true,
                        allowBlank: false,
                        afterLabelTextTpl: Ext.required,
                        forceSelection: true
                    },{
                        xtype: 'button',
                        text:'刷新',
                        //scope:this,
                        handler:function(btn) {
                        	btn.previousSibling().getStore().reload();
                        }   
                    },{
                        fieldLabel: '业务阶段',
                        name: 'businessPhase_id',
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
				            data: [{id:'none',name:'未回复'},{id:'replay',name:'客户回复'},{id:'discuss',name:'讨论价格'},{id:'send',name:'送样'},{id:'deal',name:'成交'}]
				        }),
				        value:'none',
				        valueField: 'id',
                        displayField: 'name',
                        //typeAhead: true,
                        queryMode: 'local',
                        editable:false,
                        //disabled: true,
                        allowBlank: false,
                        afterLabelTextTpl: Ext.required,
                        forceSelection: true
                    }]
                }, {
                    xtype: 'container',
                    layout: 'hbox',
                    defaultType: 'textfield',
                    margin: '0 0 5 0',
                    items: [{
                        fieldLabel: '客户性质',
                        name: 'customerProperty_id',
                        xtype: 'combobox',
                        store: new Ext.data.Store({
				            fields:['id','name'],
				            proxy: {
				            	url:Ext.ContextPath+'/customerProperty/query',
				                type: 'ajax',
				                reader: {
				                    type: 'json',
				                    root:'root'
				                }
				            }
				        }),
                        valueField: 'id',
                        displayField: 'name',
                        //typeAhead: true,
                        queryMode: 'remote',
                        editable:false,
                        //disabled: true,
                        allowBlank: true,
                        forceSelection: true
                    },{
                        xtype: 'button',
                        text:'刷新',
                        //scope:this,
                        handler:function(btn) {
                        	btn.previousSibling().getStore().reload();
                        }   
                    },{
                        fieldLabel: '跟进次数',
                        labelWidth: 100,
                        //disabled: true,
                        emptyText: '自动统计(只读)',
                        readOnly:true,
                        name: 'followupNum'
                    }]
                }, {
                    xtype: 'container',
                    layout: 'hbox',
                    defaultType: 'textfield',
                    margin: '0 0 5 0',
                    items: [{
                    	xtype:'datefield',
                        fieldLabel: '初次询盘时间',
                        name: 'inquiryDate',
                        format:'Y-m-d',
                        editable:false,
                        //flex: 1,
                         afterLabelTextTpl: Ext.required,
                        allowBlank: false
                    }]
                }, {
                    xtype: 'container',
                    layout: 'hbox',
                    defaultType: 'textfield',
                    margin: '0 0 5 0',
                    items: [{
                    	xtype     : 'textareafield',
        				grow      : true,
                        fieldLabel: '初次询盘内容',
                        name: 'inquiryContent',
                        flex: 1,
                         afterLabelTextTpl: Ext.required,
                        allowBlank: false
                    }]
                }, {
                    xtype: 'container',
                    layout: 'hbox',
                    defaultType: 'textfield',
                    margin: '0 0 5 0',
                    items: [{
                        fieldLabel: '网址',
                        name: 'website',
                        flex: 1,
                        allowBlank: true
                    }]
                },{
                    xtype: 'fieldcontainer',
                    //fieldLabel: 'Name',
                    layout: 'hbox',
                    combineErrors: true,
                    defaultType: 'textfield',
                    defaults: {
                        //hideLabel: 'true'
                    },
                    items: [{
                        name: 'continent_id',
                        fieldLabel: '洲',
                        xtype: 'combobox',
                        store: new Ext.data.Store({
				            fields:['id','name'],
				            proxy: {
				            	url:Ext.ContextPath+'/country/queryContinent',
				                type: 'ajax',
				                reader: {
				                    type: 'json',
				                    root:'root'
				                }
				            }
				        }),
                        valueField: 'id',
                        displayField: 'name',
                        //typeAhead: true,
                        queryMode: 'remote',
                        editable:false,
                        //disabled: true,
                         afterLabelTextTpl: Ext.required,
                        allowBlank: false,
                        forceSelection: true,
                        listeners:{
                        	change:function(combo, newValue, oldValue){
                        		var country=combo.nextSibling();
                        		country.getStore().load({params:{continent:newValue}});
                        	}
                        }
                    }, {
                        name: 'country_id',
                        fieldLabel: '国家',
                        xtype: 'combobox',
                        store: new Ext.data.Store({
				            fields:['id','name'],
				            proxy: {
				            	autoLoad:false,
				            	url:Ext.ContextPath+'/country/query',
				                type: 'ajax',
				                //extraParams:{continent:'none'},
				                reader: {
				                    type: 'json',
				                    root:'root'
				                }
				            }
				        }),
                        valueField: 'id',
                        displayField: 'name',
                        //typeAhead: true,
                        queryMode: 'remote',
                        editable:false,
                        //disabled: true,
                        allowBlank: false,
                        afterLabelTextTpl: Ext.required,
                        forceSelection: true,
                        listeners:{
                        	beforequery:function(queryPlan){
                        		var continent=queryPlan.combo.previousSibling();
                        		if(!continent.getValue()){
                        			return false;
                        		}
                        		queryPlan.combo.getStore().getProxy().extraParams={continent:continent.getValue()};
                        	}
                        }
                    },{
                        xtype: 'button',
                        text:'刷新',
                        //scope:this,
                        handler:function(btn) {
                        	btn.previousSibling().getStore().reload();
                        }   
                    }]
                },{
                    xtype: 'fieldcontainer',
                    //fieldLabel: 'Name',
                    layout: 'hbox',
                    combineErrors: true,
                    defaultType: 'textfield',
                    defaults: {
                        //hideLabel: 'true'
                    },
                    items: [{
                        name: 'address',
                        fieldLabel: '地址',
                        flex: 2,
                        allowBlank: true
                    }]
                }]
            },  {
                xtype: 'fieldset',
                title: '默认联系人',
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
                        name: 'contact_id',
                        flex: 1,
                        allowBlank: false
                    },{
                        xtype: 'hidden',
                        fieldLabel: '是否是默认联系人',
                        name: 'contact_isDefault',
                        value:true,
                        flex: 1,
                        allowBlank: false
                    },{
                        //labelWidth: 110,
                        xtype: 'textfield',
                        fieldLabel: '联系人',
                        name: 'contact_name',
                       // style: (!Ext.isIE6) ? 'opacity:.3' : '',
                        flex: 1,
                        afterLabelTextTpl: Ext.required,
                        allowBlank: false
                    }, {
                        //labelWidth: 110,
                        xtype: 'textfield',
                        fieldLabel: '职位',
                        name: 'contact_position',
                       // style: (!Ext.isIE6) ? 'opacity:.3' : '',
                        flex: 1,
                        allowBlank: true
                    }]
                },{
                    xtype: 'container',
                    layout: 'hbox',
                    margin: '0 0 5 0',
                    items: [{
                        //labelWidth: 110,
                        xtype: 'textfield',
                        fieldLabel: '电话',
                        //afterLabelTextTpl: Ext.required,
                        name: 'contact_phone',
                       // style: (!Ext.isIE6) ? 'opacity:.3' : '',
                        flex: 1,
                        allowBlank: true
                    }, {
                        //labelWidth: 110,
                        xtype: 'textfield',
                        fieldLabel: '手机',
                        name: 'contact_mobile',
                       // style: (!Ext.isIE6) ? 'opacity:.3' : '',
                        flex: 1,
                        allowBlank: true
                    }]
                }, {
                    xtype: 'container',
                    layout: 'hbox',
                    margin: '0 0 5 0',
                    items: [{
                        //labelWidth: 110,
                        xtype: 'textfield',
                        fieldLabel: '聊天账号',
                        name: 'contact_chatNum',
                       // style: (!Ext.isIE6) ? 'opacity:.3' : '',
                        flex: 1,
                        allowBlank: true
                    },{
                        //labelWidth: 110,
                        xtype: 'textfield',
                        fieldLabel: '传真',
                        name: 'contact_fax',
                       // style: (!Ext.isIE6) ? 'opacity:.3' : '',
                        flex: 1,
                        allowBlank: true
                    }]
                }, {
                    xtype: 'container',
                    layout: 'hbox',
                    margin: '0 0 5 0',
                    items: [{
                        //labelWidth: 110,
                        xtype: 'textfield',
                        fieldLabel: 'email',
                        name: 'contact_email',
                        vtype:'email',
                       // style: (!Ext.isIE6) ? 'opacity:.3' : '',
                        flex: 1,
                         afterLabelTextTpl: Ext.required,
                        allowBlank: false
                    }]
                }]
            },{
                xtype: 'fieldset',
                title: '客户星级',
                defaultType: 'textfield',
                layout: 'anchor',
                defaults: {
                    anchor: '100%'
                },
                items: [{
                    xtype: 'container',
                    layout: 'hbox',
                    margin: '0 0 5 0',
                    items: [{
                        xtype: 'hidden',
                        fieldLabel: '星级',
                        name: 'star',
                        flex: 1,
                        allowBlank: true
                    },{
                        fieldLabel: '光带几年经验',
                        name: 'expYear',
                        flex: 1,
                        xtype: 'combobox',
                        store:new Ext.data.Store({
				            fields:[{name:'id',type:'int'},{name:'name',type:'string'}],
				            proxy: {
				                type: 'memory',
				                reader: {
				                    //type: 'array'
				                }
				            },
				            data: [{id:1,name:'0年'},{id:2,name:'1年'},{id:3,name:'2年'},{id:4,name:'3年'},{id:5,name:'4年及以上'}]
				        }),
				        valueField: 'id',
                        displayField: 'name',
                        //typeAhead: true,
                        queryMode: 'local',
                        editable:false,
                        //disabled: true,
                        allowBlank: true,
                        forceSelection: true
                    },{
                        fieldLabel: '光带占比',
                        name: 'proportion',
                        flex: 1,
                        xtype: 'combobox',
                        store:new Ext.data.Store({
				            fields:['id','name'],
				            proxy: {
				                type: 'memory',
				                reader: {
				                    //type: 'array'
				                }
				            },
				            data: [{id:1,name:'<10%'},{id:2,name:'10%'},{id:3,name:'15%'},{id:4,name:'20%'},{id:5,name:'30%'}]
				        }),
				        valueField: 'id',
                        displayField: 'name',
                        //typeAhead: true,
                        queryMode: 'local',
                        editable:false,
                        //disabled: true,
                        allowBlank: true,
                        forceSelection: true
                    },{
                        fieldLabel: '客户类型',
                        name: 'customerType',
                        flex: 1,
                        xtype: 'combobox',
                        store:new Ext.data.Store({
				            fields:['id','name'],
				            proxy: {
				                type: 'memory',
				                reader: {
				                    //type: 'array'
				                }
				            },
				            data: [{id:1,name:'DIY'},{id:2,name:'工程'}]
				        }),
				        valueField: 'id',
                        displayField: 'name',
                        //typeAhead: true,
                        queryMode: 'local',
                        editable:false,
                        //disabled: true,
                        allowBlank: true,
                        forceSelection: true
                    }]
                },{
                    xtype: 'container',
                    layout: 'hbox',
                    margin: '0 0 5 0',
                    items: [{
                        fieldLabel: '员工人数',
                        name: 'empNum',
                        flex: 1,
                        xtype: 'combobox',
                        store:new Ext.data.Store({
				            fields:['id','name'],
				            proxy: {
				                type: 'memory',
				                reader: {
				                    //type: 'array'
				                }
				            },
				            data: [{id:1,name:'1+'},{id:2,name:'10+'},{id:3,name:'15+'},{id:4,name:'20+'},{id:5,name:'30+'}]
				        }),
				        valueField: 'id',
                        displayField: 'name',
                        //typeAhead: true,
                        queryMode: 'local',
                        editable:false,
                        //disabled: true,
                        allowBlank: true,
                        forceSelection: true
                    },{
                        fieldLabel: '光带年采购额',
                        name: 'buyMoney',
                        flex: 1,
                        xtype: 'combobox',
                        store:new Ext.data.Store({
				            fields:['id','name'],
				            proxy: {
				                type: 'memory',
				                reader: {
				                    //type: 'array'
				                }
				            },
				            data: [{id:1,name:'10+'},{id:2,name:'20+'},{id:3,name:'30+'},{id:4,name:'40+'},{id:5,name:'50+'}]
				        }),
				        valueField: 'id',
                        displayField: 'name',
                        //typeAhead: true,
                        queryMode: 'local',
                        editable:false,
                        //disabled: true,
                        allowBlank: true,
                        forceSelection: true
                    },{
                        fieldLabel: '质量档次',
                        name: 'quality',
                        flex: 1,
                        xtype: 'combobox',
                        store:new Ext.data.Store({
				            fields:['id','name'],
				            proxy: {
				                type: 'memory',
				                reader: {
				                    //type: 'array'
				                }
				            },
				            data: [{id:2,name:'C'},{id:3,name:'B.C'},{id:4,name:'A.B.H'},{id:5,name:'A.H'}]
				        }),
				        valueField: 'id',
                        displayField: 'name',
                        //typeAhead: true,
                        queryMode: 'local',
                        editable:false,
                        //disabled: true,
                        allowBlank: true,
                        forceSelection: true
                    }]
                },{
                    xtype: 'container',
                    layout: 'hbox',
                    margin: '0 0 5 0',
                    items: [{
                        fieldLabel: '价格档次',
                        name: 'price',
                        flex: 1,
                        xtype: 'combobox',
                        store:new Ext.data.Store({
				            fields:['id','name'],
				            proxy: {
				                type: 'memory',
				                reader: {
				                    //type: 'array'
				                }
				            },
				            data: [{id:2,name:'C'},{id:3,name:'B.C'},{id:4,name:'A.B'},{id:5,name:'A.H'}]
				        }),
				        valueField: 'id',
                        displayField: 'name',
                        //typeAhead: true,
                        queryMode: 'local',
                        editable:false,
                        //disabled: true,
                        allowBlank: true,
                        forceSelection: true
                    },{
                        fieldLabel: '每单每款MOQ',
                        name: 'moq',
                        flex: 1,
                        xtype: 'combobox',
                        store:new Ext.data.Store({
				            fields:['id','name'],
				            proxy: {
				                type: 'memory',
				                reader: {
				                    //type: 'array'
				                }
				            },
				            data: [{id:1,name:'<500M'},{id:3,name:'>=500M'},{id:5,name:'>=1000M'}]
				        }),
				        valueField: 'id',
                        displayField: 'name',
                        //typeAhead: true,
                        queryMode: 'local',
                        editable:false,
                        //disabled: true,
                        allowBlank: true,
                        forceSelection: true
                    }]
                },{
                    xtype: 'container',
                    layout: 'hbox',
                    margin: '0 0 5 0',
                    items: [{
                        fieldLabel: '付款条款',
                        name: 'paymentTerms',
                        flex: 1,
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
					            {id:0,name:'后T/T出货后OA **天内'},
					            {id:1,name:'无订金 B/L提单复印件7天'},
					            {id:2,name:'无订金 100%出货前'},
					            {id:3,name:'L/C信用证45天'},
					            {id:400,name:'L/C信用证即期'},
					            {id:4,name:'前T/T 30%订金,70%出货前（空运海运都适用）'},
					            {id:5,name:'前T/T 100%订金'}
				            ]
				        }),
				        valueField: 'id',
                        displayField: 'name',
                        //typeAhead: true,
                        queryMode: 'local',
                        editable:false,
                        //disabled: true,
                        allowBlank: true,
                        forceSelection: true
                    }]
                }
              ]
            }
        ],

        buttons: [{
            text: '保存并管理其他联系人',
            scope: this,
            hidden:me.update,
            handler: this.saveAndManagerOtherContact
        }, {
            text: '其他联系人管理',
            scope: this,
            iconCls:'role-role-iconCls',
            hidden:!me.update,
            handler: this.managerOtherContact
        },{
            text: '保存',
            //width: 150,
            scope: this,
            handler: this.onSave
        }]    
        });
        this.callParent();
    },
    
    saveAndManagerOtherContact: function(){
    	//首先添加用户，然后再弹出框来管理联系人的添加
    	var me=this;
    	function callback(){
	        //this.getForm().reset();
	    	var grid=Ext.create('Leon.panera.customer.ContactGrid',{
					customer_id:me.getRecord().getId()	
			});
			var win=Ext.create('Ext.Window',{
				title:'联系人管理',
				modal:true,
				autoScroll :true,
				width:600,
				height:500,
				layout:'fit',
				items:[grid]
			});
			win.show();
    	}
    	
    	this.onSave(callback);
    },
    managerOtherContact: function(){
        //this.getForm().reset();
    	var me=this;
    	var grid=Ext.create('Leon.panera.customer.ContactGrid',{
			customer_id:me.getRecord().getId()
		});
		var win=Ext.create('Ext.Window',{
			title:'联系人管理',
			modal:true,
			autoScroll :true,
			width:600,
			height:500,
			layout:'fit',
			items:[grid]
		});
		win.show();
    },
    
    onSave: function(callBack){
    	var me=this;
        var form = this.getForm();
        if (form.isValid()) {
            //Ext.MessageBox.alert('Submitted Values', form.getValues(true));
            form.submit({
            	//standardSubmit :false,
            	clientValidation: true,
    			url: Ext.ContextPath+(me.update?'/customer/update':'/customer/create'),
    			success: function(form, action) {
    				Ext.Msg.alert('消息', "保存成功");
    			 	if(callBack instanceof Function){
    			 		callBack(action.result.root.id);
    			 	}
    			 	form.updateRecord();
    			 	form.getRecord().set("star",action.result.root.star);
    			 	
    			 	if(!me.update){
    			 		me.grid.getStore().reload();
    			 	} else {
    			 		form.getRecord().set("country_name",form.findField("country_id").getRawValue());
    			 		form.getRecord().set("customerProperty_name",form.findField("customerProperty_id").getRawValue());
    			 		//form.getRecord().set("businessPhase_id",action.result.root.star);
    			 	}
    			 	me.win.close();
    			 	
    			 },
    			 failure:function(form, action){
    			 	 //Ext.Msg.alert('Failure', "保存客户失败");
    			 }
            });
        }
        
        
    },
    
    onMailingAddrFieldChange: function(field){
        var copyToBilling = this.down('[name=billingSameAsMailing]').getValue(),
            copyField = this.down('[name=' + field.billingFieldName + ']');

        if (copyToBilling) {
            copyField.setValue(field.getValue());
        } else {
            copyField.clearInvalid();
        }
    },
    
    /**
     * Enables or disables the billing address fields according to whether the checkbox is checked.
     * In addition to disabling the fields, they are animated to a low opacity so they don't take
     * up visual attention.
     */
    onSameAddressChange: function(box, checked){
        var fieldset = box.ownerCt;
        Ext.Array.forEach(fieldset.previousSibling().query('textfield'), this.onMailingAddrFieldChange, this);
        Ext.Array.forEach(fieldset.query('textfield'), function(field) {
            field.setDisabled(checked);
            // Animate the opacity on each field. Would be more efficient to wrap them in a container
            // and animate the opacity on just the single container element, but IE has a bug where
            // the alpha filter does not get applied on position:relative children.
            // This must only be applied when it is not IE6, as it has issues with opacity when cleartype
            // is enabled
            if (!Ext.isIE6) {
                field.el.animate({opacity: checked ? 0.3 : 1});
            }
        });
    }
});