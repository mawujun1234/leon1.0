Ext.define('Leon.desktop.org.OrgTypeForm',{
	extend:'Ext.form.Panel',
	requires: [
	     'Leon.desktop.org.OrgType'
	],
	fieldDefaults: {
        msgTarget: 'side',
        labelWidth: 75,
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
       me.items= [
		{
	        fieldLabel: 'id',
	        //afterLabelTextTpl: Ext.required,
	        name: 'id',
	        readOnly:true,
	        xtype:'textfield',
	        allowBlank: false
	    },
	    {
                xtype: 'fieldcontainer',
                fieldLabel: '图标',
                combineErrors: true,
                msgTarget : 'side',
                layout: 'hbox',
                defaults: {
                    flex: 1,
                    hideLabel: true
                },
                items: [{
                        xtype     : 'button',
                        flex:0,
                        width:80,
                        
                        text      : '选择图标',
                        handler:function(iconButton){

                        	var win=Ext.create('Leon.common.IconWindow',{
                        		listeners:{
                        			itemdblclick:function(win,record){
                        				Ext.util.CSS.createStyleSheet("."+record.get("iconCls")+"{background: url("+record.get("src16")+") left top no-repeat !important;}") ;
                        				//Ext.util.CSS.createStyleSheet("."+record.get("iconCls32")+"{background: url("+record.get("src")+") left top no-repeat !important;}") ;
                        				
                        				iconButton.setIconCls(record.get("iconCls") );
                        				//iconButton.nextSibling().setValue(record.get("iconCls"));
                        				iconButton.nextSibling().setValue(record.get("iconCls"));
                        				win.close();
                        			}
                        		}
                        	});
                        	win.show();
                        }
                    },{
                        xtype     : 'hidden',
                        name      : 'iconCls',
                        flex:0,
                        fieldLabel: '图标Cls',
                        listeners:{
                        	change:function(field, newValue, oldValue, eOpts){
                        		var iconButton=field.previousSibling();
                        		iconButton.setIconCls(newValue );
                        	}
                        }
                    }]
          },
		{
	        fieldLabel: 'name',
	        //afterLabelTextTpl: Ext.required,
	        name: 'name',
	        readOnly:true,
	        xtype:'textfield',
	        allowBlank: false
	    }
	  ];   

	var saveButton=Ext.create('Ext.button.Button',{
            text: '保存',
            iconCls:'form-save-button',
            //formBind: true,当设置这个值得时候，当表单里面有内容后，就自动会变成可执行
            disabled :true,
            itemId:'save',
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
						btn.disable();
						me.fireEvent("saved");
					}
				});
            }
      })
	  me.buttons= [saveButton];    
      me.saveButton=saveButton;
      
      me.addEvents("saved");
      me.callParent();
	}
});
