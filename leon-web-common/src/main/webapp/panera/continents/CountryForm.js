Ext.define('Leon.panera.continents.CountryForm',{
	extend:'Ext.form.Panel',
	requires: [
	     'Leon.panera.continents.Country'
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
	        //readOnly:true,
	        xtype:'hidden',
	        allowBlank: false
	    },
		{
	        fieldLabel: '洲',
	        //afterLabelTextTpl: Ext.required,
	        name: 'continent',
	        //readOnly:true,
	        xtype:'combo',
	        afterLabelTextTpl: Ext.required,
	        allowBlank: false,
	        queryMode: 'local',
		    displayField: 'name',
		    valueField: 'id',
		    value:'Europe',
	        store:Ext.create('Ext.data.Store',{
	        	fields:['id','name'],
	        	data : [
			         {id: 'Europe',    name: '欧洲'},
			         {id: 'Asia', name: '亚洲'},
			         {id: 'Oceania', name: '大洋洲'},
			         {id: 'Africa', name: '非洲'},
			         {id: 'NorthAmerica', name: '北美洲'},
			         {id: 'SouthAmerica', name: '南美洲'}
			    ]
			    
	        })
	    },
		{
	        fieldLabel: '名称',
	        //afterLabelTextTpl: Ext.required,
	        name: 'name',
	        //readOnly:true,
	        xtype:'textfield',
	        afterLabelTextTpl: Ext.required,
	        allowBlank: false
	    },
		{
	        fieldLabel: '名称_英文',
	        //afterLabelTextTpl: Ext.required,
	        name: 'name_en',
	        //readOnly:true,
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
//            listeners:{
//				hide:function(b,e){
//					
//					var fields=b.up('form').getForm().getFields( );
//					fields.each(function(items){
//						items.setReadOnly(true);
//					});
//				},
//				show:function(b){
//					var form=b.up('form');
//					var fields=form.getForm().getFields( );
//					fields.each(function(items){
//						if(items.getName()=='id'  && !form.createAction){
//							items.setReadOnly(true);
//						}else{
//							items.setReadOnly(false);
//						}
//					});
//				}
//			},
            handler: function(btn) {
            	var form=this.up('form');
                if(!form.getForm().isValid()) {
                	return;
                }
                form.getForm().updateRecord();
				form.getRecord().save({
					success: function(record, operation) {
						form.win.close();
						if(form.grid){
							form.grid.getStore().reload();
						}
						
						record.commit();
					}
				});
            }
      });
      
     
      
	  me.tbar=	[saveButton];

      me.callParent();
	}
	/**
	 * 重载父类的方法
	 */
//	loadRecord:function(){
//		var form=this;
//		form.down("button#update").enable();
//		form.down("button#destroy").enable();
//		
//		form.callParent(arguments);
//	}
});
