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
    
    fieldDefaults: {
            labelWidth: 75,
            anchor: '100%'
        },

    layout: {
            type: 'vbox',
            align: 'stretch'  // Child items are stretched to full width
    },
	initComponent: function () {
       var me = this;
      
       me.items= [
//       		{
//	            fieldLabel: 'parent_id',
//
//	            name: 'parent_id',
//	            readOnly:true
//	        },{
//	            fieldLabel: '上级名称',
//	            //afterLabelTextTpl: me.required,
//	            name: 'parent_text',
//	            readOnly:true
//	            //allowBlank: false,
//	            //tooltip: 'Enter your first name'
//	        },
       		{
	            fieldLabel: 'id',
	            //afterLabelTextTpl: me.required,
	            readOnly:true,
	            name: 'id'
	            //allowBlank: false,
	            //tooltip: 'Enter your first name'
	        },
	        {
	            fieldLabel: '名称',
	            afterLabelTextTpl: me.required,
	            name: 'text',
	            readOnly:true,
	            allowBlank: false,
	            tooltip: '请输入名称'
	        },{
	        	xtype:'combobox',
			    fieldLabel: '是否可用',
			    afterLabelTextTpl:'<span class="icons_help" data-qtip="不可用：表示所有关联到这个功能的菜单和界面元素都看不见了，可用于临时维护。">&nbsp;&nbsp;&nbsp;&nbsp;</span>',
			    store:  Ext.create('Ext.data.Store', {
				    fields: ['id', 'name'],
				    data : [
				        {"id":true, "name":"可用"},
				        {"id":false, "name":"不可用"}
				    ]
				}),
			    queryMode: 'local',
			    displayField: 'name',
			    valueField: 'id',
			    readOnly:true,
			    editable:false,
			    value:true,
			    name:'isEnable'
			},{
	            fieldLabel: '界面元素id',
	            //afterLabelTextTpl: me.required,
	            name: 'elementId',
	            readOnly:true,
	            afterLabelTextTpl:'<span class="icons_help" data-qtip="主要用于界面元素的显示控制，例如界面元素按钮的id=testId,这里也设置成testid，把这个功能授予某个用户的时候，这个用户就能看到这个按钮了,注意要具有唯一性">&nbsp;&nbsp;&nbsp;&nbsp;</span>'
	        },{
	            fieldLabel: 'url',
	            name: 'url',
	            readOnly:true,
	            tooltip: "请输入url"
	        }
	        ,{
	        	xtype:'constantcombo',
                fieldLabel  : '业务类型',
                readOnly:true,
                name: 'bussinessType',
				code:'Fun_BussinessType'
            }
	    ];
	    me.buttons= [{
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
					var fields=b.up('form').getForm().getFields( );
					fields.each(function(items){
						if(items.getName()=='id'){
							items.setReadOnly(true);
						}else{
							items.setReadOnly(false);
						}
					});
				}
			},
            handler: function(b) {
            	var form=this.up('form');
                if(!form.getForm().isValid()) {
                	return;
                }
                form.getForm().updateRecord();
				form.getRecord().save({
					success: function(record, operation) {
						b.disable();
					}
				});
				
            }
        }
//        ,{
//            text: '重置',
//            iconCls:'form-reset-button',
//            handler: function() {
//            	//var copyRcd=this.up('form').getRecord( );
//                this.up('form').getForm().reset(true);
//            }
//        }
        ];
       
        me.addEvents("created");
       me.callParent();
	}
});