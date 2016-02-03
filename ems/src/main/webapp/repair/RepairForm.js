Ext.define('Ems.repair.RepairForm',{
	extend:'Ext.form.Panel',
	requires: [
	     'Ems.repair.Repair'
	],
	fieldDefaults: {
        msgTarget: 'side',
        labelWidth: 65,
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
       
       
       var depreci_year=Ext.create('Ext.form.field.Number',{
		xtype:'numberfield',name:'depreci_year',minValue:0,value:0,labelWidth:80,allowBlank:true,labelAlign:'right'
	});
	var depreci_month=Ext.create('Ext.form.field.Number',{
		xtype:'numberfield',name:'depreci_month',minValue:0,value:0,labelWidth:80,allowBlank:true,labelAlign:'right'
	});
	var depreci_day=Ext.create('Ext.form.field.Number',{
		xtype:'depreci_day',name:'depreci_day',minValue:0,value:0,labelWidth:80,allowBlank:true,labelAlign:'right'
	});
	
       me.items= [{
                        name: 'workunit_id',
                        fieldLabel: '作业单位',
                        flex: 1,
                        readOnly:true,
                        allowBlank: true
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
                        name: 'repair_date',
                        xtype:'datefield',
                        fieldLabel: '报修时间',
                        format:'Y-m-d',
                        flex: 1,
                        //emptyText: 'First',
                        readOnly:true,
                        allowBlank: true
                    },{
                        name: 'workunit_name',
                        fieldLabel: '作业单位',
                        labelWidth: 65,
                        flex: 1,
                        readOnly:true,
                        allowBlank: true
                    },{
                        name: 'str_out_name',
                        fieldLabel: '来源仓库',
                         labelWidth: 65,
                        flex: 1,
                        //emptyText: 'First',
                        readOnly:true,
                        allowBlank: true
                    },{
                        name: 'str_out_date',
                        xtype:'datefield',
                        format:'Y-m-d',
                        
                        fieldLabel: '出仓时间',
                         labelWidth: 65,
                        flex: 1,
                        readOnly:true,
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
                        name: 'broken_memo',
                        fieldLabel: '故障描述',
                        flex: 1,
                        //height:45,
                        //xtype:'textarea',
                        readOnly:true,
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
                        name: 'rpa_in_date',
                        fieldLabel: '入维时间',
                        xtype:'datefield',
                        format:'Y-m-d',
                        flex: 1,
                        //emptyText: 'First',
                        readOnly:true,
                        allowBlank: true
                    },{
                        name: 'rpa_user_id',
                        fieldLabel: '维修人',
                        flex: 1,
                        editable:false,
                        afterLabelTextTpl: Ext.required,
                        xtype:'combobox',
                        displayField: 'name',
		    			valueField: 'id',
                        readOnly:false,
                        allowBlank: false,
                        store:Ext.create('Ext.data.Store', {
					    	fields: ['id', 'name'],
						    proxy:{
						    	type:'ajax',
						    	extraParams:{store_id:me.rpa_id,edit:true},
						    	url:Ext.ContextPath+'/store/queryRUsers.do',
						    	reader:{
						    		type:'json',
						    		root:'root'
						    	}
						    }
					   })
                    },{
				        fieldLabel: '维修类型',
				        labelAlign:'right',
			            //labelWidth:60,
				        xtype:'combobox',
				        //afterLabelTextTpl: Ext.required,
				        name: 'rpa_type',
					    displayField: 'name',
					    valueField: 'id',
					    //value:"1",
				        //allowBlank: false,
				        store:Ext.create('Ext.data.Store', {
					    	fields: ['id', 'name'],
						    data:[{id:"innerrpa",name:"维修"},{id:"outrpa",name:"外修"}]
					   }),
					   listeners:{
					   		change:function( field, newValue, oldValue, eOpts ) {
					   			var form=field.up("form");
					   			if(newValue=='innerrpa'){
					   				form.getComponent( "outrpa_need_fillin" ).hide();
					   			} else {
					   				form.getComponent( "outrpa_need_fillin" ).show();
					   			}
					   			
					   		}
					   }
				  }]
     },{
                    xtype: 'fieldcontainer',
                    //fieldLabel: 'Name',
                    itemId:'outrpa_need_fillin',
                    layout: 'hbox',
                    hidden:true,
                    combineErrors: true,
                    defaultType: 'textfield',
                    defaults: {
                        //hideLabel: 'true'
                    },
                    items: [{
                        name: 'send_date',
                        xtype:'datefield',
                        fieldLabel: '寄出时间',
                        editable:false,
                        afterLabelTextTpl: Ext.required,
                        format:'Y-m-d',
                        flex: 1,
                        readOnly:false,
                        allowBlank: true
                    },{
                        name: 'sendno',
                        fieldLabel: '运单号',
                        labelWidth: 55,
                        flex: 1,
                        afterLabelTextTpl: Ext.required,
                        readOnly:false,
                        allowBlank: true
                    },{
                        name: 'repairFactory',
                        fieldLabel: '维修厂方',
                        flex: 1,
                        afterLabelTextTpl: Ext.required,
                        readOnly:false,
                        allowBlank: true
                    },{
                        name: 'receive_date',
                        xtype:'datefield',
                        editable:false,
                        fieldLabel: '收到时间',
                        format:'Y-m-d',
                        flex: 1,
                        afterLabelTextTpl: Ext.required,
                        readOnly:false,
                        allowBlank: true
                    }
                    ]
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
                        name: 'broken_reson',
                        fieldLabel: '故障/外修原因',
                        afterLabelTextTpl: Ext.required,
                        flex: 1,
                        xtype:'textarea',
                        readOnly:false,
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
                    items: []
     },
     {itemId:'depreci_container',hidden:false,xtype:'fieldcontainer',layout: 'hbox',items:[{xtype:'displayfield',value:'还可使用年数:',labelWidth:120},depreci_year,{
		                               xtype: 'displayfield',
		                               value: '-年'
		                           },depreci_month,{
		                           		xtype: 'displayfield',
		                               value: '-月'
		                           },depreci_day,{
		                           		xtype: 'displayfield',
		                               value: '-天'
	}]},
	{
                    xtype: 'fieldcontainer',
                    //fieldLabel: 'Name',
                    layout: 'hbox',
                    combineErrors: true,
                    defaultType: 'textfield',
                    defaults: {
                        //hideLabel: 'true'
                    },
                    items: [{
                        name: 'handler_method',
                        fieldLabel: '处理方法',
                        flex: 1,
                        xtype:'textarea',
                        readOnly:false,
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
                        name: 'memo',
                        fieldLabel: '备注',
                        flex: 1,
                        //xtype:'textarea',
                        readOnly:false,
                        allowBlank: true
                    }]
     },
     {
	        fieldLabel: 'id',
	        //afterLabelTextTpl: Ext.required,
	        name: 'id',
	        readOnly:true,
	        xtype:'hidden',
	        allowBlank: false
	    },
		{
	        fieldLabel: 'ecode',
	        //afterLabelTextTpl: Ext.required,
	        name: 'ecode',
	        readOnly:true,
	        xtype:'hidden',
	        allowBlank: false
	    },
		{
	        fieldLabel: 'installIn_id',
	        //afterLabelTextTpl: Ext.required,
	        name: 'installIn_id',
	        readOnly:true,
	        xtype:'hidden',
	        allowBlank: false
	    },
		{
	        fieldLabel: 'rpa_id',
	        //afterLabelTextTpl: Ext.required,
	        name: 'rpa_id',
	        readOnly:true,
	        xtype:'hidden',
	        allowBlank: false
	    },
		{
	        fieldLabel: 'rpa_in_oper_id',
	        //afterLabelTextTpl: Ext.required,
	        name: 'rpa_in_oper_id',
	        readOnly:true,
	        xtype:'hidden',
	        allowBlank: false
	    },
		{
            fieldLabel: 'rpa_out_date',
            name: 'rpa_out_date',
            readOnly:true,
            xtype: 'hidden',
            format: 'Y-m-d'
        },
		{
	        fieldLabel: 'rpa_out_oper_id',
	        //afterLabelTextTpl: Ext.required,
	        name: 'rpa_out_oper_id',
	        readOnly:true,
	        xtype:'hidden',
	        allowBlank: false
	    },
		{
	        fieldLabel: 'status',
	        //afterLabelTextTpl: Ext.required,
	        name: 'status',
	        readOnly:true,
	        xtype:'hidden',
	        allowBlank: false
	    },
		{
            fieldLabel: 'str_in_date',
            name: 'str_in_date',
            readOnly:true,
            xtype: 'hidden',
            format: 'Y-m-d'
        },
		{
	        fieldLabel: 'str_in_id',
	        //afterLabelTextTpl: Ext.required,
	        name: 'str_in_id',
	        readOnly:true,
	        xtype:'hidden',
	        allowBlank: false
	    },
		{
	        fieldLabel: 'str_in_oper_id',
	        //afterLabelTextTpl: Ext.required,
	        name: 'str_in_oper_id',
	        readOnly:true,
	        xtype:'hidden',
	        allowBlank: false
	    },
		{
	        fieldLabel: 'str_out_id',
	        //afterLabelTextTpl: Ext.required,
	        name: 'str_out_id',
	        readOnly:true,
	        xtype:'hidden',
	        allowBlank: false
	    },
		{
	        fieldLabel: 'str_out_oper_id',
	        //afterLabelTextTpl: Ext.required,
	        name: 'str_out_oper_id',
	        readOnly:true,
	        xtype:'hidden',
	        allowBlank: false
	    }
     ];   
     
     
	  
	  
	  var saveButton=Ext.create('Ext.button.Button',{
            text: '保存',
            iconCls:'form-save-button',
            //formBind: true,当设置这个值得时候，当表单里面有内容后，就自动会变成可执行
            //hidden :true,
            handler: function(btn) {
            	if(!(depreci_year.getValue() || depreci_month.getValue() || depreci_month.getValue())){
					alert("请填写还可使用年数!");
					return;
				}
            	var form=this.up('form');
                if(!form.getForm().isValid()) {
                	return;
                }
                var rpa_type=form.getForm().findField("rpa_type") ;
               
				//如果是外修就得判断外修的结果字段是否输值了
				if(rpa_type.getValue()=='outrpa'){
					var send_date=form.getForm().findField("send_date") ;
					if(!send_date.getValue()){
						alert("请输入寄货时间");
						return;
					}
					var sendno=form.getForm().findField("sendno") ;
					if(!sendno.getValue()){
						alert("请输入快递单号");
						return;
					}
					
					var repairFactory=form.getForm().findField("repairFactory") ;
					if(!repairFactory.getValue()){
						alert("请输入维修厂方");
						return;
					}
//					var receive_date=form.getForm().findField("receive_date") ;
//					if(!receive_date.getValue()){
//						alert("请输入收货时间");
//						return;
//					}
				}
				
                form.getForm().updateRecord();
				form.getRecord().save({
					success: function(record, operation) {
						
						me.fireEvent("saved");
					}
				});
            }
      });
      me.saveButton=saveButton;
      me.buttons=[saveButton];
      me.addEvents("saved");
      
      me.callParent();
	},
	/**
	 * 重载父类的方法
	 */
	loadRecord:function(record){
		var form=this;

		form.callParent(arguments);
		var store_combox=form.getForm().findField("rpa_user_id");
		var brand_model= store_combox.getStore().createModel({id:record.get("rpa_user_id"),name:record.get("rpa_user_name")});
		store_combox.setValue(brand_model);
	}
});
