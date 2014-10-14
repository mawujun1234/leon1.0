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
			    	extraParams:{type:1,look:true},
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
	
	
	var subtype_combox=Ext.create('Ems.baseinfo.SubtypeCombo',{
		labelAlign:'right',
		allowBlank: false,
		labelWidth:50,
		minChars:-1,
		listeners:{
			change:function(field,newValue, oldValue){
				prod_combox.clearValue( );
				prod_combox.getStore().getProxy().extraParams={equipmentSubtype_id:newValue};
				prod_combox.getStore().reload();
			}
		}
	});
	var prod_combox=Ext.create('Ems.baseinfo.ProdCombo',{
		labelAlign:'right',
		allowBlank: false,
		minChars:-1	
	});
	var brand_combox=Ext.create('Ems.baseinfo.BrandCombo',{
		labelAlign:'right',
		allowBlank: false,
		minChars:-1
	});
	var supplier_combox=Ext.create('Ems.baseinfo.SupplierCombo',{
		labelAlign:'right',
		minChars:-1,
		allowBlank: false
//		queryParam: 'name',
//    	queryMode: 'remote',
//    	triggerAction: 'query',
//    	minChars:-1,
//		trigger1Cls: Ext.baseCSSPrefix + 'form-clear-trigger',
//		trigger2Cls: Ext.baseCSSPrefix + 'form-arrow-trigger',//'form-search-trigger',
//		onTrigger1Click : function(){
//			var me = this;
//			me.setValue('');
//		}
	});
	var totalprice_display=Ext.create('Ext.form.field.Display',{
		xtype:'displayfield',fieldLabel:'总价(元)',name:'totalprice',labelWidth:100,submitValue : true,labelAlign:'right',hidden:true
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
    	          {header: '设备类型', dataIndex: 'subtype_name',width:120},
    	          {header: '品名', dataIndex: 'prod_name'},
    	          {header: '品牌', dataIndex: 'brand_name',width:120},
    	          {header: '供应商', dataIndex: 'supplier_name'},
    	          {header: '设备型号', dataIndex: 'style',width:120},
    	          {header: '数量', dataIndex: 'orderNum',width:70},
    	          {header: '单价(元)', dataIndex: 'unitPrice',width:70},
    	          {header: '总价(元)', dataIndex: 'totalprice',width:70},
    	          

    	          { header:'操作',
	                xtype: 'actioncolumn',
	                width: 70,
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
	            }],
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
		var equipform=step1.down('form');
        var form=equipform.getForm();
		if(form.isValid()){
			var obj=form.getValues();
		    var record=new Ext.create('Ems.store.Order',{
		    	orderNo:order_no.getValue(),
	            subtype_id:obj.subtype_id,
	            subtype_name:subtype_combox.getRawValue(),
	            prod_id:obj.prod_id,
	            prod_name:prod_combox.getRawValue(),
	            brand_id:obj.brand_id,
	            brand_name:brand_combox.getRawValue(),
	            supplier_id:obj.supplier_id,
	            supplier_name:supplier_combox.getRawValue(),
	            style:obj.style,
	            store_id:store_combox.getValue(),
	            store_name:store_combox.getRawValue(),
	            orderNum:obj.orderNum,
	            unitPrice:obj.unitPrice,
	            totalprice:obj.totalprice,
	            orderDate:orderDate.getValue(),
	            operater:loginUserId
		    })
			equipStore.add(record);
			//订单号和仓库变味不可编辑
			order_no.disable();
			store_combox.disable();
		}
	}
	
	var step1=Ext.create('Ext.panel.Panel',{
        layout: {
            type:'vbox',
            padding:'5',
            align:'stretch'
        },
        defaults:{margins:'0 0 5 0',border:false},
        items:[{xtype:'form',items:[
        							{xtype:'fieldcontainer',layout: 'hbox',items:[order_no,store_combox,orderDate,operater]},
        							{xtype:'fieldcontainer',layout: 'hbox',items:[subtype_combox,prod_combox,brand_combox,supplier_combox]},
                                    {xtype:'fieldcontainer',layout: 'hbox',items:[
                                    	{xtype:'textfield',itemId:'style_field',fieldLabel:'型号',name:'style',labelWidth:50,allowBlank:false,labelAlign:'right'},
                                    	{xtype:'numberfield',itemId:'orderNum_field',fieldLabel:'数目',name:'orderNum',minValue:1,labelWidth:100,allowBlank:false,labelAlign:'right',value:1},
                                    	{xtype:'numberfield',itemId:'unitprice_field',fieldLabel:'单价(元)',name:'unitPrice',minValue:0,labelWidth:100,listeners:{change:countTotal},allowBlank:true,labelAlign:'right'},
										totalprice_display
									  ]
									}
		            		       
		            		        ]},
        {layout:{type:'hbox',algin:'stretch'},items:[{flex:1,border:false,html:'<HR style="FILTER: alpha(opacity=100,finishopacity=0,style=3)" width="100%" color=#987cb9 SIZE=3>'},{xtype:'button',text:'添加',handler:addEquip,width:70,iconCls:'icon-add',margin:'0 5px 0 5px'}]},
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