Ext.define('Leon.desktop.fun.FunForm',{
	extend:'Ext.form.Panel',
	requires: [
	     'Leon.desktop.fun.Fun'
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
	initComponent: function () {
       var me = this;
      
       me.items= [{
	            fieldLabel: 'parent_id',
	            //afterLabelTextTpl: me.required,
	            name: 'parent_id',
	            readOnly:true
	            //allowBlank: false,
	            //tooltip: 'Enter your first name'
	        },{
	            fieldLabel: '上级名称',
	            //afterLabelTextTpl: me.required,
	            name: 'parent_text',
	            readOnly:true
	            //allowBlank: false,
	            //tooltip: 'Enter your first name'
	        },{
	            fieldLabel: 'id',
	            //afterLabelTextTpl: me.required,
	            readOnly:true,
	            name: 'id'
	            //allowBlank: false,
	            //tooltip: 'Enter your first name'
	        },{
	            fieldLabel: '名称',
	            afterLabelTextTpl: me.required,
	            name: 'text',
	            allowBlank: false,
	            tooltip: '请输入名称'
	        },{
	            fieldLabel: '助记码',
	            //afterLabelTextTpl: me.required,
	            name: 'code'
	            //allowBlank: false,
	            //tooltip: '请输入名称'
	        },{
	            fieldLabel: 'url',
	            name: 'url',
	            tooltip: "请输入url"
	        },{
	        	xtype:'checkboxfield',
                fieldLabel  : '创建菜单',
                name      : 'isCreateMenu',
                inputValue: '2',
                checked   : true
            }
	    ];
	    me.buttons= [{
            text: '重置',
            iconCls:'form-reset-button',
            handler: function() {
                this.up('form').getForm().reset();
            }
        },{
            text: '复制',
            iconCls:'form-copy-button',
            handler: function() {
                //this.up('form').getForm().isValid();
            	var copyRcd=this.up('form').getRecord( ).copy();
            	copyRcd.set("id",null);
            	this.up('form').copyRcd=copyRcd;
            	this.nextSibling().show();
            }
        },{
            text: '粘贴',
            hidden:true,
            iconCls:'form-paste-button',
            handler: function() {
            	this.up('form').getForm().loadRecord(this.up('form').copyRcd);
                this.hide();
            }
        }
//        ,{
//	    	action:'save',
//            text: '保存',
//            iconCls:'form-save-button',
//            handler: function() {
//                var values=this.up('form').getForm().getValues( );
//				var newRecord=Ext.createModel("Leon.desktop.fun.Fun",values);
//				newRecord.save();
//            }
//        }
        ,{
            text: '保存',
            iconCls:'form-save-button',
            handler: function() {
            	var form=this.up('form');
                form.getForm().isValid();
                form.getForm().updateRecord();
				form.getRecord().save({
					success: function(record, operation) {
						if(form.action=="create"){
							me.fireEvent("created",record);
							form.action=="update"
						} else {
							
						}
					}
				});
            }
        },{
            text: '新建子功能',
            iconCls:'form-addChild-button',
            action:'createChild',
            handler: function() {
            	var parent=this.up('form').getRecord( );
            	this.up('form').getForm().reset();
            	var newRecord=Ext.createModel("Leon.desktop.fun.Fun",{'parent_id':parent.get("id"),'parent_text':parent.get("text")});
            	this.up('form').getForm().setValues({'parent_id':parent.get("id"),'parent_text':parent.get("text")});
            	this.up('form').getForm().loadRecord(newRecord);
            	this.up('form').action="create";
            }
        },{
            text: '删除',
            iconCls:'form-delete-button',
            handler: function() {

            	var record= this.up('form').getRecord();
            	var parent=record.parentNode;
            	var index=parent.indexOf(record);
            	if(record.hasChildNodes( )){
            		Ext.Msg.alert("消息","请先删除子节点!");
            		return;
            	}
                record.destroy({
                	failure: function(record, operation) {
                		parent.insertChild(index,record);
                	}
                });
            }
        }];
       
        me.addEvents("created");
       me.callParent();
	}
});