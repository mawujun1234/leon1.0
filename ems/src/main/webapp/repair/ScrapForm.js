Ext.define('Ems.repair.ScrapForm',{
	extend:'Ext.form.Panel',
	requires: [
	     'Ems.repair.Scrap'
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
	        xtype:'hidden',
	        allowBlank: false
	    },
	    {
	        fieldLabel: '报废单号',
	        //afterLabelTextTpl: Ext.required,
	        name: 'repair_id',
	        readOnly:true,
	        xtype:'textfield',
	        allowBlank: false
	    },
		{
	        fieldLabel: '条码',
	        //afterLabelTextTpl: Ext.required,
	        name: 'ecode',
	        readOnly:true,
	        xtype:'textfield',
	        allowBlank: false
	    },
		{
            fieldLabel: '报废申请时间',
            name: 'operateDate',
            readOnly:true,
            xtype: 'hidden',
            format: 'Y-m-d'
        },
        
		{
	        fieldLabel: '报废申请人',
	        //afterLabelTextTpl: Ext.required,
	        name: 'operater',
	        readOnly:me.storeer?true:true,
	        xtype:'textfield',
	        value:loginUsername,
	        allowBlank: false
	    },
		{
	        fieldLabel: '报废原因',
	        //afterLabelTextTpl: Ext.required,
	        name: 'reason',
	        readOnly:me.storeer?true:false,
	        grow :true,
	        xtype:'textarea',
	        allowBlank: false
	    },
		
		{
	        fieldLabel: '残余值',
	        //afterLabelTextTpl: Ext.required,
	        name: 'residual',
	        readOnly:false,
	        grow :true,
	        xtype:'textarea',
	        allowBlank: false
	    }
	  ];   
	  
	  

     if(me.storeer){//当仓库人员进入的时候
     	 var makeSureScrapButton=Ext.create('Ext.button.Button',{
	            text: '确    认',
	            iconCls:'form-save-button',
	            //formBind: true,当设置这个值得时候，当表单里面有内容后，就自动会变成可执行
	            //hidden :true,
	            handler: function(btn) {
	            	var form=this.up('form');
	                if(!form.getForm().isValid()) {
	                	return;
	                }
	               	Ext.Ajax.request({
						url:Ext.ContextPath+'/scrap/makeSureScrap.do',
						method:'POST',
						params:form.getValues(),
						success:function(response){
							var obj=Ext.decode(response.responseText);
							if(obj.success){
								Ext.Msg.alert("消息","报废确认成功");
								me.fireEvent("makeSureScrap");
								return;
							} else {
								Ext.Msg.alert("消息","报废确认失败");
							}
						}
					});
	                
	                
	            }
	      });
	      me.addEvents("makeSureScrap");
	      me.makeSureScrapButton=makeSureScrapButton;
	       me.buttons=[makeSureScrapButton];
     } else {
     	var saveButton=Ext.create('Ext.button.Button',{
	            text: '保存',
	            iconCls:'form-save-button',
	            //formBind: true,当设置这个值得时候，当表单里面有内容后，就自动会变成可执行
	            //hidden :true,
	            handler: function(btn) {
	            	var form=this.up('form');
	                if(!form.getForm().isValid()) {
	                	return;
	                }
	                form.getForm().updateRecord();
					form.getRecord().save({
						success: function(record, operation) {
							//me.win.close();
							me.fireEvent("saved");
						}
					});
	            }
	      });
	      me.saveButton=saveButton;
	       var scrapButton=Ext.create('Ext.button.Button',{
	            text: '报废',
	            iconCls:'form-save-button',
	            //formBind: true,当设置这个值得时候，当表单里面有内容后，就自动会变成可执行
	            //hidden :true,
	            handler: function(btn) {
	            	Ext.MessageBox.confirm('确认', '您确认要报废该设备吗?', function(btn){
		              if(btn=='yes'){
		              	  var form=this.up('form');
				          if(!form.getForm().isValid()) {
				             return;
				           }
				          var values=form.getValues();
		              	  //form.getForm().updateRecord();
		                  Ext.Ajax.request({
		                  	method:'POST',
		                  	url:Ext.ContextPath+'/scrap/scrap.do',
		                  	params:values,
		                  	success:function(response){
		                  		var obj=Ext.decode(response.responseText);
		                  		me.fireEvent("scraped");
				               // me.win.close();
		                  	}
		                  });		
		              }
		           },btn);
	            }
	      });
	     me.scrapButton=scrapButton;
	     me.buttons=[scrapButton,saveButton];
     }
      
      me.addEvents("saved");
      me.addEvents("scraped");

      me.callParent();
	}
});
