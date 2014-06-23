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
    
    initComponent: function(){
        var states = new Ext.data.Store({
            fields:['key','value'],
            proxy: {
                type: 'memory',
                reader: {
                    type: 'array'
                }
            },
            data: []
        }),
        billingStates = new Ext.data.Store({
            fields:['key','value'],
            proxy: {
                type: 'memory',
                reader: {
                    type: 'array'
                }
            },
            data: []
        });
        
        Ext.apply(this, {
            width: 550,
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
                        name: 'name',
                        fieldLabel: '客户名称',
                        flex: 2,
                        //emptyText: 'First',
                        allowBlank: false
                    }, {
                        name: 'customerSource_id',
                        fieldLabel: '客户来源',
                        xtype: 'combobox',
                        store: new Ext.data.Store({
                        	url:Ext.ContextPath+''
				            fields:['key','value'],
				            proxy: {
				                type: 'memory',
				                reader: {
				                    type: 'array'
				                }
				            },
				            data: []
				        }),
                        valueField: 'key',
                        displayField: 'value',
                        typeAhead: true,
                        queryMode: 'remote',
                        disabled: true,
                        allowBlank: false,
                        forceSelection: true
                    }]
                }, {
                    xtype: 'container',
                    layout: 'hbox',
                    defaultType: 'textfield',
                    margin: '0 0 5 0',
                    items: [{
                        fieldLabel: '客户性质',
                        labelWidth: 100,
                        name: 'customerProperty_id'
                    },{
                        fieldLabel: '业务阶段',
                        name: 'businessPhase_id',
                        flex: 1,
                        allowBlank: false
                    }]
                }, {
                    xtype: 'container',
                    layout: 'hbox',
                    defaultType: 'textfield',
                    margin: '0 0 5 0',
                    items: [{
                        fieldLabel: '主动跟进次数',
                        labelWidth: 100,
                        name: 'followNum'
                    },{
                        fieldLabel: '初次询盘时间',
                        name: 'inquiryDate',
                        flex: 1,
                        allowBlank: false
                    }]
                }, {
                    xtype: 'container',
                    layout: 'hbox',
                    defaultType: 'textfield',
                    margin: '0 0 5 0',
                    items: [{
                        fieldLabel: '初次询盘内容',
                        name: 'inquiryContent',
                        flex: 1,
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
                        flex: 2,
                        allowBlank: false
                    }, {
                        name: 'country_id',
                        fieldLabel: '国家',
                        flex: 3,
                        margins: '0 0 0 6',
                        allowBlank: false
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
            }, {
                xtype: 'fieldset',
                title: '客户星级',
                defaultType: 'textfield',
                layout: 'anchor',
                defaults: {
                    anchor: '100%'
                },
                items: [{
                    labelWidth: 110,
                    fieldLabel: 'Street Address',
                    name: 'mailingStreet',
                    listeners: {
                        scope: this,
                        change: this.onMailingAddrFieldChange
                    },
                    billingFieldName: 'billingStreet',
                    allowBlank: false
                }, {
                    xtype: 'container',
                    layout: 'hbox',
                    margin: '0 0 5 0',
                    items: [{
                        labelWidth: 110,
                        xtype: 'textfield',
                        fieldLabel: 'City',
                        name: 'mailingCity',
                        listeners: {
                            scope: this,
                            change: this.onMailingAddrFieldChange
                        },
                        billingFieldName: 'billingCity',
                        flex: 1,
                        allowBlank: false
                    }, {
                        xtype: 'combobox',
                        name: 'mailingState',
                        forceSelection: true,
                        maxLength: 2,
                        enforceMaxLength: true,
                        listeners: {
                            scope: this,
                            change: this.onMailingAddrFieldChange
                        },
                        billingFieldName: 'billingState',
                        fieldLabel: 'State',
                        labelWidth: 50,
                        width: 112,
                        listConfig: {
                            minWidth: null
                        },
                        store: states,
                        valueField: 'abbr',
                        displayField: 'abbr',
                        typeAhead: true,
                        queryMode: 'local',
                        allowBlank: false,
                        forceSelection: true
                    }, {
                        xtype: 'textfield',
                        fieldLabel: 'Postal Code',
                        labelWidth: 80,
                        name: 'mailingPostalCode',
                        listeners: {
                            scope: this,
                            change: this.onMailingAddrFieldChange
                        },
                        billingFieldName: 'billingPostalCode',
                        width: 160,
                        allowBlank: false,
                        maxLength: 10,
                        enforceMaxLength: true,
                        maskRe: /[\d\-]/,
                        regex: /^\d{5}(\-\d{4})?$/,
                        regexText: 'Must be in the format xxxxx or xxxxx-xxxx'
                    }]
                }]
            }, {
                xtype: 'fieldset',
                title: 'Billing Address',
                layout: 'anchor',
                defaults: {
                    anchor: '100%'
                },
                items: [{
                    xtype: 'checkbox',
                    name: 'billingSameAsMailing',
                    boxLabel: 'Same as Mailing Address?',
                    hideLabel: true,
                    checked: true,
                    margin: '0 0 10 0',
                    scope: this,
                    handler: this.onSameAddressChange
                }, {
                    labelWidth: 110,
                    xtype: 'textfield',
                    fieldLabel: 'Street Address',
                    name: 'billingStreet',
                    style: (!Ext.isIE6) ? 'opacity:.3' : '',
                    disabled: true,
                    allowBlank: false
                }, {
                    xtype: 'container',
                    layout: 'hbox',
                    margin: '0 0 5 0',
                    items: [{
                        labelWidth: 110,
                        xtype: 'textfield',
                        fieldLabel: 'City',
                        name: 'billingCity',
                        style: (!Ext.isIE6) ? 'opacity:.3' : '',
                        flex: 1,
                        disabled: true,
                        allowBlank: false
                    }, {
                        xtype: 'combobox',
                        name: 'billingState',
                        maxLength: 2,
                        enforceMaxLength: true,
                        style: (!Ext.isIE6) ? 'opacity:.3' : '',
                        fieldLabel: 'State',
                        labelWidth: 50,
                        listConfig: {
                            minWidth: null
                        },
                        width: 112,
                        store: billingStates,
                        valueField: 'abbr',
                        displayField: 'abbr',
                        typeAhead: true,
                        queryMode: 'local',
                        disabled: true,
                        allowBlank: false,
                        forceSelection: true
                    }, {
                        xtype: 'textfield',
                        fieldLabel: 'Postal Code',
                        labelWidth: 80,
                        name: 'billingPostalCode',
                        style: (!Ext.isIE6) ? 'opacity:.3' : '',
                        width: 160,
                        disabled: true,
                        allowBlank: false,
                        maxLength: 10,
                        enforceMaxLength: true,
                        maskRe: /[\d\-]/,
                        regex: /^\d{5}(\-\d{4})?$/,
                        regexText: 'Must be in the format xxxxx or xxxxx-xxxx'
                    }]
                }]
            }, {
                xtype: 'fieldset',
                title: 'Payment',
                layout: 'anchor',
                defaults: {
                    anchor: '100%'
                },
                items: [{
                    xtype: 'radiogroup',
                    anchor: 'none',
                    layout: {
                        autoFlex: false
                    },
                    defaults: {
                        name: 'ccType',
                        margin: '0 15 0 0'
                    },
                    items: [{
                        inputValue: 'visa',
                        boxLabel: 'VISA',
                        checked: true
                    }, {
                        inputValue: 'mastercard',
                        boxLabel: 'MasterCard'
                    }, {
                        inputValue: 'amex',
                        boxLabel: 'American Express'
                    }, {
                        inputValue: 'discover',
                        boxLabel: 'Discover'
                    }]
                }, {
                    xtype: 'textfield',
                    name: 'ccName',
                    fieldLabel: 'Name On Card',
                    allowBlank: false
                }, {
                    xtype: 'container',
                    layout: 'hbox',
                    margin: '0 0 5 0',
                    items: [{
                        xtype: 'textfield',
                        name: 'ccNumber',
                        fieldLabel: 'Card Number',
                        flex: 1,
                        allowBlank: false,
                        minLength: 15,
                        maxLength: 16,
                        enforceMaxLength: true,
                        maskRe: /\d/
                    }, {
                        xtype: 'fieldcontainer',
                        fieldLabel: 'Expiration',
                        labelWidth: 75,
                        layout: 'hbox',
                        items: [{
                            xtype: 'combobox',
                            name: 'ccExpireMonth',
                            displayField: 'name',
                            valueField: 'num',
                            queryMode: 'local',
                            emptyText: 'Month',
                            hideLabel: true,
                            margins: '0 6 0 0',
                            store: new Ext.data.Store({
                                fields: ['name', 'num'],
                                data: (function() {
                                    var data = [];
                                        Ext.Array.forEach(Ext.Date.monthNames, function(name, i) {
                                        data[i] = {name: name, num: i + 1};
                                    });
                                    return data;
                                })()
                            }),
                            width: 100,
                            allowBlank: false,
                            forceSelection: true
                        }, {
                            xtype: 'numberfield',
                            name: 'ccExpireYear',
                            hideLabel: true,
                            width: 70,
                            value: new Date().getFullYear(),
                            minValue: new Date().getFullYear(),
                            allowBlank: false
                        }]
                    }]
                }]
            }
        ],

        buttons: [{
            text: 'Reset',
            scope: this,
            handler: this.onResetClick
        }, {
            text: 'Complete Purchase',
            width: 150,
            scope: this,
            handler: this.onCompleteClick
        }]    
        });
        this.callParent();
    },
    
    onResetClick: function(){
        this.getForm().reset();
    },
    
    onCompleteClick: function(){
        var form = this.getForm();
        if (form.isValid()) {
            Ext.MessageBox.alert('Submitted Values', form.getValues(true));
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