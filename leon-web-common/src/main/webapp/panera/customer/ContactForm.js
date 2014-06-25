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
                        //labelWidth: 110,
                        xtype: 'textfield',
                        fieldLabel: '联系人',
                        name: 'contact_name',
                       // style: (!Ext.isIE6) ? 'opacity:.3' : '',
                        flex: 1,
                        allowBlank: false
                    }, {
                        //labelWidth: 110,
                        xtype: 'textfield',
                        fieldLabel: '职位',
                        name: 'contact_position',
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
                        name: 'contact_phone',
                       // style: (!Ext.isIE6) ? 'opacity:.3' : '',
                        flex: 1,
                        allowBlank: false
                    }, {
                        //labelWidth: 110,
                        xtype: 'textfield',
                        fieldLabel: '手机',
                        name: 'contact_mobile',
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
                        name: 'contact_chatNum',
                       // style: (!Ext.isIE6) ? 'opacity:.3' : '',
                        flex: 1,
                        allowBlank: false
                    },{
                        //labelWidth: 110,
                        xtype: 'textfield',
                        fieldLabel: '传真',
                        name: 'contact_fax',
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
                        name: 'contact_email',
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