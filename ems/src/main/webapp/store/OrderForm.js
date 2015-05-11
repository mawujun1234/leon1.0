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
       
      // alert(me.orderNo);
     var order_no=Ext.create('Ext.form.field.Hidden',{
		fieldLabel:'订单号',
		name:'orderNo',
		labelWidth:50,
		allowBlank:false,
		labelAlign:'right',
		value:me.orderNo
	});
	me.order_no=order_no;
	
	var order_id=Ext.create('Ext.form.field.Hidden',{
		fieldLabel:'id',
		name:'id',
		labelWidth:50,
		allowBlank:false,
		labelAlign:'right'
	});
	me.order_id=order_id;
	
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
	me.type_combox=type_combox;
	
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
	me.subtype_combox=subtype_combox;
	
	var prod_id=Ext.create('Ext.form.field.Hidden',{
		fieldLabel: '品名',
		labelWidth:40,
		labelAlign:'right',
		allowBlank: false,
		readOnly:true,
		name:'prod_id'
	});
	me.prod_id=prod_id;
	var prod_name=Ext.create('Ext.form.field.Text',{
		fieldLabel: '品名',
		labelWidth:40,
		labelAlign:'right',
		allowBlank: false,
		readOnly:true,
		name:'prod_name'
	});
	me.prod_name=prod_name;
	var prod_unit=Ext.create('Ext.form.field.Text',{
		fieldLabel: '单位',
		labelWidth:40,
		width:80,
		labelAlign:'right',
		allowBlank: false,
		readOnly:true,
		name:'prod_unit'
	});
	me.prod_unit=prod_unit;
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
						prod_unit.setValue(record.get("unit"));
						
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
	me.brand_id=brand_id;
	var brand_name=Ext.create('Ext.form.field.Text',{
	    fieldLabel: '品牌',
	    labelWidth:40,
		labelAlign:'right',
		allowBlank: false,
		readOnly:true,
		name:'brand_name'	
	});
	me.brand_name=brand_name;
	var style=Ext.create('Ext.form.field.Text',{
		flex:1,
		readOnly:true,
		xtype:'textfield',itemId:'style_field',fieldLabel:'型号',name:'style',labelWidth:50,allowBlank:false,labelAlign:'right'});
	me.prod_style=style;
	var prod_spec=Ext.create('Ext.form.field.Text',{
		flex:1,
		readOnly:true,
		xtype:'textfield',itemId:'style_field',fieldLabel:'规格',name:'prod_spec',labelWidth:50,allowBlank:false,labelAlign:'right'});
	me.prod_spec=prod_spec;
	var supplier_combox=Ext.create('Ems.baseinfo.SupplierCombo',{
		labelAlign:'right',
		labelWidth:40,
		flex:1,
		minWidth:150,
		allowBlank: true
	});
	me.supplier_combox=supplier_combox;
	var orderNum_field=Ext.create('Ext.form.field.Number',{
		xtype:'numberfield',itemId:'orderNum_field',fieldLabel:'数目',name:'orderNum',minValue:1,labelWidth:40,listeners:{change:me.countTotal},allowBlank:false,labelAlign:'right',value:1
	});
	me.orderNum_field=orderNum_field;
	
	var unitprice_field=Ext.create('Ext.form.field.Number',{
		xtype:'numberfield',itemId:'unitprice_field',fieldLabel:'单价(元)',name:'unitPrice',minValue:0,labelWidth:80,listeners:{change:me.countTotal},allowBlank:true,labelAlign:'right'
	});
	me.unitprice_field=unitprice_field;
	var totalprice_display=Ext.create('Ext.form.field.Display',{
		xtype:'displayfield',fieldLabel:'总价(元)',name:'totalprice',labelWidth:60,submitValue : true,labelAlign:'right',width:180
	});
	me.totalprice_display=totalprice_display;
	
	me.items=[
			{xtype:'fieldcontainer',layout: 'hbox',items:[order_no,order_id,brand_id,prod_id]},
        							{xtype:'fieldcontainer',layout: 'hbox',items:[type_combox,subtype_combox,prod_name,queryProd_button,brand_name,style]},
        							{xtype:'fieldcontainer',layout: 'hbox',items:[prod_spec,prod_unit]},
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
               
				form.submit({
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
		var me=this;
		
		var form=f.up('form');
		var nums=form.down('#orderNum_field').getValue();
		var unitprice=form.down('#unitprice_field').getValue();
		if(!!nums&&!!unitprice&&nums!=""&&unitprice!=""){
			var total=nums*unitprice;
			if(!form.totalprice_display.isVisible()){
				form.totalprice_display.setVisible(true);
			}
			form.totalprice_display.setValue(total.toFixed(2));
		}
	},
	//更新字段信息
	updateFieldValue : function(order) {
		var me=this;
		this.order_id.setValue(order.get("id"));
		//this.type_combox.setValue(order.get("type_id"));
		var fun0 = function() {
			me.type_combox.setValue(order.get("type_id"));
			me.type_combox.getStore().un("load", fun);
		}
		this.type_combox.getStore().on("load", fun0);
		this.type_combox.getStore().load();
		
		var fun = function() {
			me.subtype_combox.setValue(order.get("subtype_id"));
			me.subtype_combox.getStore().un("load", fun);
		}
		this.subtype_combox.getStore().on("load", fun);

		this.prod_id.setValue(order.get("prod_id"));
		this.prod_name.setValue(order.get("prod_name"));
		this.prod_unit.setValue(order.get("prod_unit"));
		this.brand_id.setValue(order.get("brand_id"));
		this.brand_name.setValue(order.get("brand_name"));
		this.prod_style.setValue(order.get("style"));
		this.prod_spec.setValue(order.get("prod_spec"));

		var supplier_model = this.supplier_combox.getStore().createModel({
					id : order.get("supplier_id"),
					name : order.get("supplier_name")
				});
		this.supplier_combox.setValue(supplier_model);

		this.orderNum_field.setValue(order.get("orderNum"));
		this.unitprice_field.setValue(order.get("unitPrice"));
		this.totalprice_display.setValue(order.get("totalprice"));

		this.type_combox.disable();
		this.subtype_combox.disable();
	}
});
