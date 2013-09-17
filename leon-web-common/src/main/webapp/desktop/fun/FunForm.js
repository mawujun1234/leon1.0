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
			    editable:false,
			    value:true,
			    name:'isEnable'
			},{
	            fieldLabel: '助记码',
	            //afterLabelTextTpl: me.required,
	            name: 'code',
	            afterLabelTextTpl:'<span class="icons_help" data-qtip="主要用于界面元素的显示控制，例如界面元素按钮的id=testId,这里也设置成testid，把这个功能授予某个用户的时候，这个用户就能看到这个按钮了">&nbsp;&nbsp;&nbsp;&nbsp;</span>'
	        },{
	            fieldLabel: 'url',
	            name: 'url',
	            tooltip: "请输入url"
	        }
	        ,{
	        	xtype:'constantcombo',
                fieldLabel  : '业务类型',
                name: 'bussinessType',
				code:'Fun_BussinessType'
            }
	    ];
	    me.buttons= [{
            text: '更新',
            iconCls:'form-save-button',
            handler: function() {
            	var form=this.up('form');
                if(!form.getForm().isValid()) {
                	return;
                }
                form.getForm().updateRecord();
				form.getRecord().save({
					success: function(record, operation) {

					}
				});
            }
        },{
            text: '重置',
            iconCls:'form-reset-button',
            handler: function() {
            	//var copyRcd=this.up('form').getRecord( );
                this.up('form').getForm().reset(true);
            }
        }];
       
        me.addEvents("created");
       me.callParent();
	}
});