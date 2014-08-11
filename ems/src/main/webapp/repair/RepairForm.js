Ext.define('Ems.repair.RepairForm',{
	extend:'Ext.form.Panel',
	requires: [
	     'Ems.repair.Repair'
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
       me.items= [{
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
                        fieldLabel: '报修入库时间',
                        flex: 1,
                        //emptyText: 'First',
                        readOnly:true,
                        allowBlank: true
                    },{
                        name: 'workunit_id',
                        fieldLabel: '作业单位',
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
                        name: 'str_out_name',
                        fieldLabel: '来源仓库',
                        flex: 1,
                        //emptyText: 'First',
                        readOnly:true,
                        allowBlank: true
                    },{
                        name: 'str_out_date',
                        fieldLabel: '出仓时间',
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
                        name: 'rpa_in_date',
                        fieldLabel: '维修入库时间',
                        flex: 1,
                        //emptyText: 'First',
                        readOnly:true,
                        allowBlank: true
                    },{
                        name: 'rpa_user_id',
                        fieldLabel: '维修人',
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
                        name: 'broken_reson',
                        fieldLabel: '故障原因',
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
                        name: 'memo',
                        fieldLabel: '备注',
                        flex: 1,
                        readOnly:true,
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
	        fieldLabel: 'memo',
	        //afterLabelTextTpl: Ext.required,
	        name: 'memo',
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
	        fieldLabel: 'rpa_type',
	        //afterLabelTextTpl: Ext.required,
	        name: 'rpa_type',
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
            hidden :true,
            itemId:'save',
            listeners:{
				hide:function(b,e){
					
					var fields=b.up('form').getForm().getFields( );
					fields.each(function(items){
						items.setReadOnly(true);
					});
				},
				show:function(b){
					var form=b.up('form');
					var fields=form.getForm().getFields( );
					fields.each(function(items){
						if(items.getName()=='id'  && !form.createAction){
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
						btn.hide();
						form.down("button#cancel").hide();
						var buttons=form.query("button[cls~=fla_form_action]");
						for(var i=0;i<buttons.length;i++){
							buttons[i].show();
						}
						//如果是新建的话,新建完成，就清空form
						if(form.createAction){
							form.down("button#update").disable();
							form.down("button#destroy").disable();
							form.getForm().reset();
						}
						
						me.fireEvent("saved");
					}
				});
            }
      });
      
      var cancelButton=Ext.create('Ext.button.Button',{
      	text: '取消',
      	iconCls:'form-cancel-button',
      	hidden :true,
      	itemId:'cancel',
      	handler: function(btn) {
            var form=btn.up('form');
            form.down("button#save").hide();
            btn.hide();
			var buttons=form.query("button[cls~=fla_form_action]");
			for(var i=0;i<buttons.length;i++){
				buttons[i].show();
			}
        }
      });
      
	  me.tbar=	[saveButton,cancelButton,{
		  	text: '新增',
			itemId:'create',
			cls:'fla_form_action',
			handler: function(btn){
				var form=btn.up("form");
				var grid=form.grid;//是在RepairApp.js中把引用授予的
				var modelName=grid.model||grid.getStore().getProxy( ).getModel().getName( );
				var model=Ext.createModel(modelName,{      	//id:''
				});
				model.phantom =true;
				form.getForm().loadRecord(model);//form是在app中定义的grid.form=form;
				
				form.createAction=true;
				form.down("button#save").show();
				form.down("button#cancel").show();
				var buttons=form.query("button[cls~=fla_form_action]");
				for(var i=0;i<buttons.length;i++){
					buttons[i].hide();
				}
				
			},
			iconCls: 'form-add-button'
		},{
			text: '更新',
			itemId:'update',
			disabled:true,
			cls:'fla_form_action',
			handler: function(btn){
				//var grid=btn.up("grid");
				var form=btn.up("form");
				var grid=form.grid;
				
				form.createAction=false;
				form.down("button#save").show();
				form.down("button#cancel").show();
				var buttons=form.query("button[cls~=fla_form_action]");
				for(var i=0;i<buttons.length;i++){
					buttons[i].hide();
				}
			},
			iconCls: 'form-update-button'
		},{
			text: '删除',
			itemId:'destroy',
			disabled:true,
			cls:'fla_form_action',
			handler: function(btn){
				//var grid=btn.up("grid");
				var form=btn.up("form");
				var grid=form.grid;
		    	Ext.Msg.confirm("删除",'确定要删除吗?', function(btn, text){
					if (btn == 'yes'){
						var records=grid.getSelectionModel( ).getSelection( );//.getLastSelected( );
						grid.getStore().remove( records );
						form.down("button#update").disable();
						form.down("button#destroy").disable();
						form.getForm().reset();
						grid.getStore().sync({
							failure:function(){
								grid.getStore().reload();
							}
						});
					}
				});
			},
			iconCls: 'form-delete-button'
		}];

	
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
