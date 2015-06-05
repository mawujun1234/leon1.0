Ext.define('Ems.baseinfo.EquipmentProdForm',{
	extend:'Ext.form.Panel',
	requires: [
	     'Ems.baseinfo.EquipmentType'
	],
	fieldDefaults: {
        msgTarget: 'side',
        labelWidth: 75,
        labelAlign:'right',
        anchor: '90%'
    },
    frame: true,
    bodyPadding: '5 5 0',
	//isprod:false,//判断现在是不是品名的form
//	parent_id:'',//大类，小类的值
 //   layout: {
 //       type: 'vbox',
 //       align: 'stretch'  // Child items are stretched to full width
 //   },
	initComponent: function () {
       var me = this;
       var maxLength=2;//me.parent_id.length+2;//me.isType?2:3;
       
	
       me.items = [{
			xtype : 'fieldcontainer',
			fieldLabel : '编码',
			afterLabelTextTpl : Ext.required,
			// labelStyle : 'font-weight:bold;padding:0;',
			layout : 'hbox',
			defaultType : 'textfield',
			//fieldDefaults : {},
			items : [{
				width : 55,
				margin : '0 5 0 0',
				name : 'subtype_id',
				xtype:'textfield',
				readOnly : true
				
			},  {
	        	//fieldLabel: 'parent_id',
	        	//afterLabelTextTpl: Ext.required,
				width : 55,
				margin : '0 5 0 0',
	        	name: 'parent_id',
	        	xtype:'textfield',
	        	hidden:true,
	        	readOnly : true
	    	},{
				flex : 1,
						// fieldLabel: '编码',
				name : 'id',
				minLength : me.isUpdate?6:maxLength,
				maxLength : me.isUpdate?12:maxLength,
				length : maxLength,
				xtype : 'textfield',
				allowBlank : me.isUpdate?true:false
//				listeners : {
//					change : function(field, newValue, oldValue) {
//						if (maxLength < newValue.length) {
//							//field.setValue(oldValue);
//						}
//					}
//				}
			}]
		}, {
			fieldLabel : 'type',
			// afterLabelTextTpl: Ext.required,
			name : 'type',
			xtype : 'hidden',
			value:'DJ'
		},	
	    {
	        fieldLabel: '名称',
	        afterLabelTextTpl: Ext.required,
	        name: 'name',
	        //readOnly:true,
	        xtype:'textfield',
	        allowBlank: false
	    },
	    {
	    	xtype:'brandcombo'
	    },
//	    {
//	    	fieldLabel: '品牌',
//		    displayField: 'name',
//		    valueField: 'id',
//		    afterLabelTextTpl: Ext.required,
//		    //hidden:!me.isprod,
//		     //minChars:1,
//		     xtype:'combobox',
//		    forceSelection:true,
//		    editable:false,
//		   // allowBlank: !me.isprod,
//		    //queryParam: 'name',
//		    //queryMode: 'remote',
//		    name:'brand_id',
//	    	store:Ext.create('Ext.data.Store', {
//			    fields: ['id', 'name'],
//			    proxy:{
//			    	type:'ajax',
//			    	actionMethods: {
//				        create : 'POST',
//				        read   : 'POST',
//				        update : 'POST',
//				        destroy: 'POST'
//				    },
//			    	url:Ext.ContextPath+"/brand/queryBrandCombo.do",
//			    	reader:{
//			    		type:'json',
//			    		root:'root'
//			    	}
//			    },
//			    listeners:{
//				    	beforeload:function(store){
//				    		//包含所有的选项
//				    		if(me.containAll){
//				    			store.getProxy().extraParams=Ext.apply(store.getProxy().extraParams,{
//				    				containAll:true
//				    			})
//				    		}
//				    	}
//				}
//		   })
//		 },
	     {
	        fieldLabel: '型号',
	        //afterLabelTextTpl: Ext.required,
	        name: 'style',
	        //hidden:!me.isprod,
	        xtype:'textfield',
	        maxLength:50,
	        value:"",
	        allowBlank: true
	    },
	    {
	        fieldLabel: '质保(月)',
	        //afterLabelTextTpl: Ext.required,
	        name: 'quality_month',
	       // hidden:!me.isprod,
	        xtype:'numberfield'
	    },
	    {
	        fieldLabel: '描述',
	        //afterLabelTextTpl: Ext.required,
	        name: 'memo',
	        //hidden:!me.isprod,
	        xtype:'textfield',
	        maxLength:50,
	        value:"",
	        allowBlank: true
	    },
	    {
	        fieldLabel: '单位',
	        afterLabelTextTpl: Ext.required,
	        name: 'unit',
	       // hidden:!me.isprod,
	        xtype:'textfield'
	    },
	    {
	        fieldLabel: '规格',
	        //afterLabelTextTpl: Ext.required,
	        name: 'spec',
	        //hidden:!me.isprod,
	        xtype:'textareafield',
	        maxLength:500,
	        height:160,
	        grow      : false
	        //allowBlank: !me.isprod
	    },
		{
	        fieldLabel: 'status',
	        //afterLabelTextTpl: Ext.required,
	        name: 'status',
	        xtype:'hidden',
	        value:1
	    }
	  ];   
	  
	  
	  var saveButton=Ext.create('Ext.button.Button',{
            text: '保存',
            iconCls:'form-save-button',
            handler: function(btn) {
            	var form=this.up('form');
            	//alert(form.getForm().isValid());
            	//var id_field=form.getForm().findField("id");
            	var id_field=form.getForm().findField("id");
            	if(!me.isUpdate && (!id_field.getValue() || (id_field.getValue()+"").length!=2)){
               		alert("请输入2位长度的编码!");
               		return;
                }
                if(!form.getForm().isValid()) {
                	return;
                }
                
               
               
                Ext.Msg.confirm("消息","确定要保存吗?",function(btn){
                	if(btn=='yes'){
                		form.getForm().updateRecord();
                		form.getForm().getRecord().set("brand_name",form.getForm().findField("brand_id").getRawValue());
						form.getRecord().save({
							url:form.url,
							success: function(record, operation) {
													
								me.fireEvent("saved");
							}
						});
                	}
                });
                
            }
      });
      
      
	  me.buttons=[saveButton];

	
      me.addEvents("saved");
      me.callParent();
	},
	/**
	 * 重载父类的方法
	 */
	loadRecord:function(){
		var form=this;
		form.down("button#update").enable();
		form.down("button#destroy").enable();
		
		form.callParent(arguments);
	}
});
