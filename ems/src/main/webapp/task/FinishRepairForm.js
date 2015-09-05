/**
 * 主要用于手工结束维修任务的时候，需要填写维修故障原因等等的内容
 */

Ext.define('Ems.task.FinishRepairForm',{
	extend:'Ext.form.Panel',
	requires: [
	     'Ems.task.Task'
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
       
       var hitchType_combox=Ext.create('Ext.form.field.ComboBox',{
		        fieldLabel: '故障类型',
		        labelAlign:'right',
	            labelWidth:60,
		        //xtype:'combobox',
		        afterLabelTextTpl: Ext.required,
		        name: 'hitchType_id',
			    displayField: 'name',
			    valueField: 'id',
		        allowBlank: false,
		        store:Ext.create('Ext.data.Store', {
			    	fields: ['id', 'name'],
				    proxy:{
				    	type:'ajax',
				    	url:Ext.ContextPath+"/hitchType/mobile/query.do",
				    	reader:{
				    		type:'json',
				    		root:'root'
				    	}
				    }
			   }),
			   listeners:{
			   	change:function(field,newValue, oldValue){
			   		hitchReason_combox.clearValue();
					hitchReason_combox.getStore().getProxy().extraParams={
						hitchType_id:newValue
					};
					hitchReason_combox.getStore().reload();
				}
			   }
		});	
		var hitchReason_combox=Ext.create('Ext.form.field.ComboBox',{
		        fieldLabel: '故障原因',
		        labelAlign:'right',
	            labelWidth:60,
		        //xtype:'combobox',
		        afterLabelTextTpl: Ext.required,
		        name: 'hitchReasonTpl_id',
			    displayField: 'name',
			    valueField: 'id',
		        allowBlank: false,
		        store:Ext.create('Ext.data.Store', {
			    	fields: ['id', 'name'],
			    	autoLoad:false,
				    proxy:{
				    	type:'ajax',
				    	url:Ext.ContextPath+"/hitchReasonTpl/query.do",
				    	reader:{
				    		type:'json',
				    		root:'root'
				    	}
				    }
			   })
		});	

		var handleMethod_combox=Ext.create('Ext.form.field.ComboBox',{
		        fieldLabel: '故障处理方法',
		        labelAlign:'right',
	            labelWidth:60,
		        //xtype:'combobox',
		        afterLabelTextTpl: Ext.required,
		        name: 'handleMethod_id',
			    displayField: 'name',
			    valueField: 'id',
		        allowBlank: false,
		        store:Ext.create('Ext.data.Store', {
			    	fields: ['id', 'name'],
				    proxy:{
				    	type:'ajax',
				    	url:Ext.ContextPath+"/handleMethod/query.do",
				    	reader:{
				    		type:'json',
				    		root:'root'
				    	}
				    }
			   })
		});	
       me.items= [
	    hitchType_combox,hitchReason_combox,handleMethod_combox
	  ];   
	  
	  
	  var send_Button=Ext.create('Ext.button.Button',{
            text: '结束任务',
            iconCls:'form-save-button',

            handler: function(btn) {
            	var form=this.up('form');
                if(!form.getForm().isValid()) {
                	return;
                }
				Ext.getBody().mask("正在处理....");
                var values=form.getForm().getValues();
                values.hitchType=hitchType_combox.getRawValue();
                values.hitchReason=hitchReason_combox.getRawValue();
                values.task_id=me.task_id;
                //values.handleMethod=hitchType_combox.getRawValue();
                Ext.Ajax.request({
                	method:'POST',
                	url:Ext.ContextPath+"/task/finishRepairTask.do",
                	params:values,
                	headers:{ 
                		'Accept':'application/json;'
                		//'Content-Type':'application/json;charset=UTF-8'
                	},
                	success:function(){
						alert("发送成功!");
						me.fireEvent("sended",form);
						Ext.getBody().unmask();
                	},
                	failure:function(){
						Ext.getBody().unmask();
					}
                })
            }
      });
      
	  me.buttons=	[send_Button];

	
      me.addEvents("sended");
      me.callParent();
	}
});
