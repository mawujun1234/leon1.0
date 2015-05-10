Ext.define('Ems.store.OrderForm',{
	extend:'Ext.form.Panel',
	requires: [
	     'Ems.store.Order'
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
       
	
	var type_combox=Ext.create('Ems.baseinfo.TypeCombo',{
		labelAlign:'right',
		allowBlank: false,
		labelWidth:50,
		//minChars:-1
		listeners:{
			change:function(field,newValue, oldValue){
				subtype_combox.clearValue( );
				if(newValue){
					subtype_combox.getStore().getProxy().extraParams={equipmentType_id:newValue};
					subtype_combox.getStore().reload();
				}
				
			}
		}
	});
	
	var subtype_combox=Ext.create('Ems.baseinfo.SubtypeCombo',{
		labelAlign:'right',
		allowBlank: false,
		labelWidth:50,
		//minChars:-1，
		listeners:{
			beforeload:function(store){
				if(type_combox.getValue()){
					return true;
				} 
				Ext.Msg.alert("消息","请先选择大类!");
				return false;
			}
//			change:function(){
//				//当小类变化后，品名要清空
//				prod_id.setValue(""); 
//				prod_name.setValue(""); 
//				brand_id.setValue(""); 
//				brand_name.setValue(""); 
//				style.setValue(""); 
//				prod_spec.setValue(""); 
//			}
		}
	});
	var prod_id=Ext.create('Ext.form.field.Hidden',{
		fieldLabel: '品名',
		labelWidth:40,
		labelAlign:'right',
		allowBlank: false,
		readOnly:true,
		name:'prod_id'
	});
	var prod_name=Ext.create('Ext.form.field.Text',{
		fieldLabel: '品名',
		labelWidth:40,
		labelAlign:'right',
		allowBlank: false,
		readOnly:true,
		name:'prod_name'
	});
	var queryProd_button=Ext.create('Ext.button.Button',{
		text:'选择品名',
		margin:'0 0 0 5',
		handler:function(){
			var subtype_id=subtype_combox.getValue();
			if(!subtype_id){
				Ext.Msg.alert("消息","请先选择一个类型");
				return;
			}
			var prod_grid=Ext.create('Ems.baseinfo.ProdQueryGrid',{
				listeners:{
					itemdblclick:function(view,record,item){
						prod_id.setValue(record.get("id"));
						prod_name.setValue(record.get("name"));
						prod_spec.setValue(record.get("spec"));
						
						brand_id.setValue(record.get("brand_id"));
						brand_name.setValue(record.get("brand_name"));
						
						style.setValue(record.get("style"));
						
						win.close();
					}
				}
			});
			prod_grid.onReload(subtype_id);
			var win=Ext.create('Ext.window.Window',{
				title:'双击选择品名',
				items:[prod_grid],
				layout:'fit',
				modal:true,
				width:700,
				height:300
			});
			win.show();
		}
	});
	var brand_id=Ext.create('Ext.form.field.Hidden',{
	    fieldLabel: '品牌',
	    labelWidth:40,
		labelAlign:'right',
		allowBlank: false,
		readOnly:true,
		name:'brand_id'	
	});
	var brand_name=Ext.create('Ext.form.field.Text',{
	    fieldLabel: '品牌',
	    labelWidth:40,
		labelAlign:'right',
		allowBlank: false,
		readOnly:true,
		name:'brand_name'	
	});
	var style=Ext.create('Ext.form.field.Text',{
		flex:1,
		readOnly:true,
		xtype:'textfield',itemId:'style_field',fieldLabel:'型号',name:'style',labelWidth:50,allowBlank:false,labelAlign:'right'});
	var prod_spec=Ext.create('Ext.form.field.Text',{
		flex:1,
		readOnly:true,
		xtype:'textfield',itemId:'style_field',fieldLabel:'规格',name:'prod_spec',labelWidth:50,allowBlank:false,labelAlign:'right'});
	
	var supplier_combox=Ext.create('Ems.baseinfo.SupplierCombo',{
		labelAlign:'right',
		labelWidth:40,
		flex:1,
		allowBlank: true
	});
	var orderNum_field=Ext.create('Ext.form.field.Number',{
		xtype:'numberfield',itemId:'orderNum_field',fieldLabel:'数目',name:'orderNum',minValue:1,labelWidth:40,listeners:{change:me.countTotal},allowBlank:false,labelAlign:'right',value:1
	});
	var unitprice_field=Ext.create('Ext.form.field.Number',{
		xtype:'numberfield',itemId:'unitprice_field',fieldLabel:'单价(元)',name:'unitPrice',minValue:0,labelWidth:80,listeners:{change:me.countTotal},allowBlank:true,labelAlign:'right'
	});
	var totalprice_display=Ext.create('Ext.form.field.Display',{
		xtype:'displayfield',fieldLabel:'总价(元)',name:'totalprice',labelWidth:60,submitValue : true,labelAlign:'right',width:180
	});
	
	me.items=[
        							{xtype:'fieldcontainer',layout: 'hbox',items:[type_combox,subtype_combox,prod_name,queryProd_button,brand_name,style]},
        							{xtype:'fieldcontainer',layout: 'hbox',items:[prod_spec]},
                                    {xtype:'fieldcontainer',layout: 'hbox',items:[
                                   		supplier_combox,
                                    	orderNum_field,
                                    	unitprice_field,
										totalprice_display
									  ]
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
					}
				});
            }
      });
      me.buttons=[saveButton];
      me.addEvents("saved");
      me.callParent();
	},
	countTotal:function(f,n,o,e){
		var form=f.up('form');
		var nums=form.down('#orderNum_field').getValue();
		var unitprice=form.down('#unitprice_field').getValue();
		if(!!nums&&!!unitprice&&nums!=""&&unitprice!=""){
			var total=nums*unitprice;
			if(!totalprice_display.isVisible()){
				totalprice_display.setVisible(true);
			}
			totalprice_display.setValue(total.toFixed(2));
		}
	}
});
