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
	initComponent: function () {
       var me = this;
      
       me.items= [{
	            fieldLabel: 'parent_id',
	            //afterLabelTextTpl: me.required,
	            name: 'parent_id'
	            //allowBlank: false,
	            //tooltip: 'Enter your first name'
	        },{
	            fieldLabel: 'id',
	            //afterLabelTextTpl: me.required,
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
	    	action:'save',
            text: '取消',
            iconCls:'form-save-button',
            handler: function() {
                this.up('form').getForm().isValid();
                me.onSave();
            }
        },{
	    	action:'save',
            text: '保存',
            iconCls:'form-save-button',
            handler: function() {
                //var basicForm=this.up('form').getForm().isValid();
                me.onSave();
            }
        },{
            text: '更新',
            iconCls:'form-update-button',
            handler: function() {
                this.up('form').getForm().isValid();
            }
        },{
            text: '新建子功能',
            iconCls:'form-addChild-button',
            action:'createChild',
            handler: function() {
                //this.up('form').getForm().reset();
                //var newRecord=Ext.create("Leon.desktop.fun.Fun",{})
                //this.up('form').getForm().loadRecord(newRecord);
            }
        },{
            text: '删除',
            iconCls:'form-delete-button',
            handler: function() {
                this.up('form').getForm().reset();
            }
        }];
        //var url=Ext.ModelManager.get("Leon.desktop.fun.Fun");//.getProxy( ).api.create;
        //me.url=url;
        //alert(url);
       
       me.callParent();
	},
	onSave:function(){
		//this.getForm().submit(); 
		var values=this.getForm().getValues( );
		var newRecord=Ext.createModel("Leon.desktop.fun.Fun",values);
		newRecord.save();
	}
});