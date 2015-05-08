Ext.require("Ems.store.Order");
//Ext.require("Ems.store.OrderGrid");
//Ext.require("Ems.store.OrderTree");
//Ext.require("Ems.store.OrderForm");
Ext.onReady(function(){
	var order_no=Ext.create('Ext.form.field.Text',{
		fieldLabel:'订单号',
		name:'orderNo',
		labelWidth:50,
		allowBlank:false,
		labelAlign:'right'
	});
	var store_combox=Ext.create('Ext.form.field.ComboBox',{
	        fieldLabel: '入库仓库',
	        labelAlign:'right',
            labelWidth:60,
	        //xtype:'combobox',
	        //afterLabelTextTpl: Ext.required,
	        name: 'store_id',
		    displayField: 'name',
		    valueField: 'id',
		    //queryParam: 'name',
    		//queryMode: 'remote',
    		//triggerAction: 'query',
    		//minChars:-1,
		    //trigger1Cls: Ext.baseCSSPrefix + 'form-clear-trigger',
		    //trigger2Cls: Ext.baseCSSPrefix + 'form-arrow-trigger',//'form-search-trigger',
			//onTrigger1Click : function(){
			//    var me = this;
			//    me.setValue('');
			//},
	        allowBlank: false,
	        store:Ext.create('Ext.data.Store', {
		    	fields: ['id', 'name'],
			    proxy:{
			    	type:'ajax',
			    	extraParams:{type:[1,3],look:true},
			    	url:Ext.ContextPath+"/store/queryCombo.do",
			    	reader:{
			    		type:'json',
			    		root:'root'
			    	}
			    }
		   })
	});	
	
	var orderDate=Ext.create('Ext.form.field.Date',{
		fieldLabel: '订购日期',
		labelWidth:60,
        name: 'orderDate',
        format:'Y-m-d',
        value: new Date() 
	});
	var operater=Ext.create('Ext.form.field.Text',{
		labelAlign:'right',
		labelWidth:55,
		fieldLabel: '经办人',
		name:'operater',
		readOnly:true,
		allowBlank:false,
		value:loginName
	});
	
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
		xtype:'numberfield',itemId:'orderNum_field',fieldLabel:'数目',name:'orderNum',minValue:1,labelWidth:40,listeners:{change:countTotal},allowBlank:false,labelAlign:'right',value:1
	});
	var unitprice_field=Ext.create('Ext.form.field.Number',{
		xtype:'numberfield',itemId:'unitprice_field',fieldLabel:'单价(元)',name:'unitPrice',minValue:0,labelWidth:80,listeners:{change:countTotal},allowBlank:true,labelAlign:'right'
	});
	var totalprice_display=Ext.create('Ext.form.field.Display',{
		xtype:'displayfield',fieldLabel:'总价(元)',name:'totalprice',labelWidth:60,submitValue : true,labelAlign:'right',width:180
	});
	var equipStore = Ext.create('Ext.data.Store', {
        autoDestroy: true,
        model: 'Ems.store.Order',
        proxy: {
            type: 'memory'
        }
    });
	var equip_grid=Ext.create('Ext.grid.Panel',{
		flex:1,
		store:equipStore,
    	columns: [Ext.create('Ext.grid.RowNumberer'),
    		{ header:'操作',
	                xtype: 'actioncolumn',
	                width: 60,
	                items: [{
	                    icon   : '../images/delete.gif',  // Use a URL in the icon config
	                    tooltip: '删除',
	                    handler: function(grid, rowIndex, colIndex) {
	                        var rec = equipStore.getAt(rowIndex);
	                        Ext.MessageBox.confirm('确认', '您确认要删除该记录吗?', function(btn){
	                        	if(btn=='yes'){
	                        		equipStore.remove(rec);
	                        	}
	                        });
	                    }
	                }]
	            },
    	          {header: '设备类型', dataIndex: 'subtype_name',width:120},
    	          {header: '品名', dataIndex: 'prod_name'},
    	          {header: '品牌', dataIndex: 'brand_name',width:120},
    	          {header: '供应商', dataIndex: 'supplier_name'},
    	          {header: '设备型号', dataIndex: 'style',width:120},
    	          {header: '规格', dataIndex: 'prod_spec',flex:1,renderer:function(value,metadata,record){
								metadata.tdAttr = "data-qtip='" + value+ "'";
							    return value;
							}
				  },
    	          {header: '数量', dataIndex: 'orderNum',width:70},
    	          {header: '单价(元)', dataIndex: 'unitPrice',width:70},
    	          {header: '总价(元)', dataIndex: 'totalprice',width:70}
    	          

    	          ],
        tbar:['<pan id="toolbar-title-text">当前订单记录</span>','->'
//              {text:'清空设备',
//        	   iconCls:'icon-clearall',
//        	   handler:function(){
//        		   Ext.MessageBox.confirm('确认', '您确认要清除所有记录吗?', function(btn){
//								            	if(btn=='yes'){
//								            		equipStore.removeAll();
//								            	}
//					});
//        	   }
//       		}
        ]
	});
	function countTotal(f,n,o,e){
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
	
	function addEquip(){
		type_combox.enable();
		subtype_combox.enable();
		if(equip_grid.click_record){
			equipStore.remove(equip_grid.click_record);
		}
		equip_grid.click_record=null;
		
		var equipform=step1.down('form');
        var form=equipform.getForm();
		if(form.isValid()){
			//var obj=form.getValues();
			if(!supplier_combox.getValue()){
				alert("请选择供应商!");
				return;
			}
			//console.log("新增或修改:"+type_combox.getValue());
			//console.log("新增或修改:"+order_no.getValue());
			//console.log(obj.subtype_id);
		    var record=new Ext.create('Ems.store.Order',{
		    	orderNo:order_no.getValue(),
		    	type_id:type_combox.getValue(),
		    	type_name:type_combox.getRawValue(),
	            subtype_id:subtype_combox.getValue(),
	            subtype_name:subtype_combox.getRawValue(),
	            prod_id:prod_id.getValue(),
	            prod_name:prod_name.getValue(),
	            prod_spec:prod_spec.getValue(),
	            brand_id:brand_id.getValue(),
	            brand_name:brand_name.getValue(),
	            supplier_id:supplier_combox.getValue(),
	            supplier_name:supplier_combox.getRawValue(),
	            style:style.getValue(),
	            store_id:store_combox.getValue(),
	            store_name:store_combox.getRawValue(),
	            orderNum:orderNum_field.getValue(),
	            unitPrice:unitprice_field.getValue(),
	            totalprice:totalprice_display.getValue(),
	            orderDate:orderDate.getValue(),
	            operater:loginUserId
		    })
			equipStore.add(record);
			//订单号和仓库变味不可编辑
			order_no.disable();
			store_combox.disable();
			
			//清空信息
			type_combox.clearValue();
			subtype_combox.clearValue();
			prod_id.setValue(""); 
			prod_name.setValue(""); 
			brand_id.setValue(""); 
			brand_name.setValue(""); 
			style.setValue(""); 
			prod_spec.setValue(""); 
			supplier_combox.clearValue();
			orderNum_field.setValue(1); 
			unitprice_field.setValue(0);
			totalprice_display.setValue(0);
		}
		
		
	}
	
	equip_grid.on('itemclick',function(view, record, item, index, e, eOpts){
		//console.log(record.get("type_id"));
		//console.log(record.get("type_name"));
			//var type_model= type_combox.getStore().createModel({id:record.get("type_id"),text:record.get("type_name")});
			//type_combox.setValue(type_model);
			//alert(type_combox.getValue());
			
			//var subtype_model= subtype_combox.getStore().createModel({id:record.get("subtype_id"),text:record.get("subtype_name")});
			//subtype_combox.setValue(subtype_model);
		
			type_combox.setValue(record.get("type_id"));
			var fun=function(){
				subtype_combox.setValue(record.get("subtype_id"));
				subtype_combox.getStore().un("load",fun);
			}
			subtype_combox.getStore().on("load",fun);
			
			
			prod_id.setValue(record.get("prod_id")); 
			prod_name.setValue(record.get("prod_name")); 
			brand_id.setValue(record.get("brand_id")); 
			brand_name.setValue(record.get("brand_name")); 
			style.setValue(record.get("style")); 
			prod_spec.setValue(record.get("prod_spec")); 
			
			var supplier_model= supplier_combox.getStore().createModel({id:record.get("supplier_id"),name:record.get("supplier_name")});
			supplier_combox.setValue(supplier_model);
			//supplier_combox.setValue(record.get("supplier_id"));
			//supplier_combox.setRawValue(record.get("supplier_name"));
			
			orderNum_field.setValue(record.get("orderNum")); 
			unitprice_field.setValue(record.get("unitPrice"));
			totalprice_display.setValue(record.get("totalprice"));
			
			type_combox.disable();
			subtype_combox.disable();
			
			equip_grid.click_record=record;
	});
	
	var step1=Ext.create('Ext.panel.Panel',{
        layout: {
            type:'vbox',
            padding:'5',
            align:'stretch'
        },
        defaults:{margins:'0 0 5 0',border:false},
        items:[{xtype:'form',items:[
        							{xtype:'fieldcontainer',layout: 'hbox',items:[order_no,store_combox,orderDate,operater]},
        							{xtype:'fieldcontainer',layout: 'hbox',items:[type_combox,subtype_combox,prod_name,queryProd_button,brand_name,style]},
        							{xtype:'fieldcontainer',layout: 'hbox',items:[prod_spec]},
                                    {xtype:'fieldcontainer',layout: 'hbox',items:[
                                   		supplier_combox,
                                    	orderNum_field,
                                    	unitprice_field,
										totalprice_display
									  ]
									}
		            		       
		            		        ]},
        {layout:{type:'hbox',algin:'stretch'},items:[{flex:1,border:false,html:'<HR style="FILTER: alpha(opacity=100,finishopacity=0,style=3)" width="100%" color=#987cb9 SIZE=3>'},
        	{xtype:'button',text:'添加或修改',handler:addEquip,width:70,margin:'0 5px 0 5px'}]},
        equip_grid,
        {html:'<HR style="FILTER: alpha(opacity=100,finishopacity=0,style=3)" width="100%" color=#987cb9 SIZE=3>'},
        {html:'<img src="../images/error.gif" style="vertical-align:middle">&nbsp;一次只能输入一个订单'}],
        buttons:[{text:'保存',handler:function(btn){
            if (equipStore.getCount()> 0) { 
            	Ext.getBody().mask("正在保存....");
            	var barcodes = new Array();
            	equipStore.each(function(record){
            		barcodes.push(record.data);
            	});
            	
				Ext.Ajax.request({
					url:Ext.ContextPath+'/order/create.do',
					method:'POST',
					timeout:600000000,
					headers:{ 'Content-Type':'application/json;charset=UTF-8'},
					jsonData:barcodes,
					//params:{jsonStr:Ext.encode(equiplist)},
					success:function(response){
						var obj=Ext.decode(response.responseText);
						if(obj.success){
							Ext.Msg.alert("消息","成功");
							equipStore.removeAll();
							
							var record=new Ext.create('Ems.store.Order',{
								operater:loginUserId,
								orderDate:new Date()
						    });
						    order_no.enable();
							store_combox.enable();
							var equipform=step1.down('form');
							equipform.getForm().loadRecord(record);
						}
						Ext.getBody().unmask();
						
					},
					failure:function(){
						Ext.getBody().unmask();
					}
				});
            }else{
            	Ext.Msg.alert('提示','请先添加一个设备');
            }
		}}]
	});
	
	
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'fit',
		items:[step1]
	});

});