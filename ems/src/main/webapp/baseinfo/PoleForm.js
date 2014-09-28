Ext.define('Ems.baseinfo.PoleForm',{
	extend:'Ext.form.Panel',
	requires: [
	     'Ems.baseinfo.Pole'
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
	        xtype:'hidden',
	        allowBlank: false
	    },
	    {
	        fieldLabel: 'customer_id',
	        //afterLabelTextTpl: Ext.required,
	        name: 'customer_id',      
	        xtype:'hidden',
	        allowBlank: false
	    },
	    {
	        fieldLabel: 'customer_id',
	        //afterLabelTextTpl: Ext.required,
	        name: 'pole_id',      
	        xtype:'hidden',
	        allowBlank: false
	    },
	     {
	        fieldLabel: 'status',
	        //afterLabelTextTpl: Ext.required,
	        name: 'status',      
	        xtype:'hidden',
	        allowBlank: false
	    },
	    {
	        fieldLabel: '名称',
	        //afterLabelTextTpl: Ext.required,
	        name: 'name',        
	        xtype:'textfield',
	        allowBlank: false
	    },
	    {
	        	xtype:'combobox',
			    fieldLabel: '省',
			    store:  Ext.create('Ext.data.Store', {
				    fields: ['id', 'name'],
				    data : [
				        {"id":"浙江省", "name":"浙江省"}
				    ]
				}),
			    queryMode: 'local',
			    displayField: 'name',
			    valueField: 'id',  
			    editable:false,
			    allowBlank:false,
			    value:"浙江省",
			    name:'province'
		},
		{
	        	xtype:'combobox',
			    fieldLabel: '市',
			    store:  Ext.create('Ext.data.Store', {
				    fields: ['id', 'name'],
				    data : [
				        {"id":"宁波市", "name":"宁波市"}
				    ]
				}),
			    queryMode: 'local',
			    displayField: 'name',
			    valueField: 'id',
			    editable:false,
			    allowBlank:false,
			    value:"宁波市",
			    name:'city'
		},
		{
	        	xtype:'combobox',
			    fieldLabel: '区/县',
			    store:  Ext.create('Ext.data.Store', {
				    fields: ['id', 'name'],
				    data : [
				        {"id":"海曙区", "name":"海曙区"},
				        {"id":"江东区", "name":"江东区"},
				        {"id":"江北区", "name":"江北区"},
				        {"id":"北仑区", "name":"北仑区"},
				        {"id":"镇海区", "name":"镇海区"},
				        {"id":"鄞州区", "name":"鄞州区"},
				        {"id":"象山县", "name":"象山县"},
				        {"id":"宁海县", "name":"宁海县"},
				        {"id":"余姚市", "name":"余姚市"},
				        {"id":"慈溪市", "name":"慈溪市"},
				        {"id":"奉化市", "name":"奉化市"}
				    ]
				}),
			    queryMode: 'local',
			    displayField: 'name',
			    valueField: 'id',  
			    editable:false,
			    allowBlank:false,
			    name:'area'
		},
		{
	        fieldLabel: '地址',
	        //afterLabelTextTpl: Ext.required,
	        name: 'address',
	        
	        xtype:'textfield',
	        allowBlank: false
	    },
	    {
	        fieldLabel: '经度',
	        //afterLabelTextTpl: Ext.required,
	        name: 'longitude',   
	        xtype:'textfield',
	        allowBlank: true
	    },	
		{
	        fieldLabel: '纬度',
	        //afterLabelTextTpl: Ext.required,
	        name: 'latitude',      
	        xtype:'textfield',
	        allowBlank: true
	    }
	  ];   
	  
	  
 	var saveButton=Ext.create('Ext.button.Button',{
            text: '保存',
            iconCls:'form-save-button',
            //formBind: true,当设置这个值得时候，当表单里面有内容后，就自动会变成可执行
            //hidden :true,
            itemId:'save',
            handler: function(btn) {
            	var form=this.up('form');
                if(!form.getForm().isValid()) {
                	return;
                }
                form.getForm().updateRecord();
				form.getRecord().save({
					success: function(record, operation) {
						me.fireEvent("saved");
						alert("保存成功!");
					}
				});
            }
      });
      me.buttons=[saveButton];
	
      me.addEvents("saved");
      me.callParent();
	}
});
