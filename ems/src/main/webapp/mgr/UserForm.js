Ext.define('Ems.mgr.UserForm',{
	extend:'Ext.form.Panel',
	requires: [
	     
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
            labelWidth: 80,
            anchor: '100%'
        },

//    layout: {
//            type: 'vbox',
//            align: 'stretch'  // Child items are stretched to full width
//    },
	initComponent: function () {
       var me = this;
       me.items= [{xtype: 'hidden',name: 'id'},
					    {fieldLabel: '用户名',name: 'username',readOnly:false,allowBlank:false,afterLabelTextTpl: me.required},
					    {fieldLabel: '姓名',name: 'name',readOnly:false},
					    {fieldLabel: '电话',name: 'phone',readOnly:false}];
	   me.buttons=[{
	   	text:'保存',
	   	handler:function(){
	   		var values=me.getForm().getValues();
				//values.parentId=selectedNode.get("id");
			if(me.update){
					url=Ext.ContextPath+"/user/update.do";
			} else {
					url=Ext.ContextPath+"/user/save.do";
			}
			Ext.Ajax.request({
				url:url,
				params:values,
				success:function(response){
						
					if(me.update){
						//url=Ext.ContextPath+"/user/updatenav.do";
						me.updateRecord(me.getForm().getRecord());
					} else {
							me.grid.getStore().reload();
					}
					me.win.close();
				}
					
			});
	   	}
	   }];
       
       me.callParent();
	}
});