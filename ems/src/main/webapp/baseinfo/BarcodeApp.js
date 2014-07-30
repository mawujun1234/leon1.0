Ext.require("Ems.baseinfo.Barcode");
//Ext.require("Ems.baseinfo.BarcodeGrid");
//Ext.require("Ems.baseinfo.BarcodeTree");
//Ext.require("Ems.baseinfo.BarcodeForm");
Ext.onReady(function(){
//	var grid=Ext.create('Ems.baseinfo.BarcodeGrid',{
//		region:'west',
//		split: true,
//		collapsible: true,
//		title:'XXX表格',
//		width:400
//	});
//
//	var tree=Ext.create('Ems.baseinfo.BarcodeTree',{
//		title:'树',
//		width:400,
//		region:'west'
//	});
//
//	var form=Ext.create('Ems.baseinfo.BarcodeForm',{
//		region:'center',
//		split: true,
//		//collapsible: true,
//		title:'表单',
//		listeners:{
//			saved:function(){
//				grid.getStore().reload();
//			}
//		}
//	});
//	grid.form=form;
//	form.grid=grid;
//	grid.on('itemclick',function(view,record,item,index){
//		//var basicForm=form.getForm();
//		form.loadRecord(record);
//	});
	
	var subtype_combox=Ext.create('Ems.baseinfo.SubtypeCombo',{
		labelAlign:'right',
		minChars:-1//表示默认点击的时候就查询出所有的数据
	});
	var prod_combox=Ext.create('Ems.baseinfo.ProdCombo',{
		labelAlign:'right'
		,minChars:-1
	});
	var brand_combox=Ext.create('Ems.baseinfo.BrandCombo',{
		labelAlign:'right'
		,minChars:-1
	});
	var supplier_combox=Ext.create('Ems.baseinfo.SupplierCombo',{
		labelAlign:'right'
		,minChars:-1
	});
	var totalprice_display=Ext.create('Ext.form.field.Display',{
		xtype:'displayfield',fieldLabel:'总价(元)',name:'totalprice',labelWidth:100,submitValue : true,labelAlign:'right',hidden:true
	});
	var equipStore = Ext.create('Ext.data.Store', {
        autoDestroy: true,
        model: 'Ems.baseinfo.Barcode',
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
    	          {header: '数量', dataIndex: 'serialNum',width:70},
    	          {header: '单价(元)', dataIndex: 'unitPrice',width:70},
    	          {header: '总价(元)', dataIndex: 'totalprice',width:70},
    	          
    	          //{header: 'stid', dataIndex: 'stid',hideable:false,hidden:true},
    	         // {header: '库房', dataIndex: 'stock',width:120},
    	          {header: '状态', dataIndex: 'status',width:60,renderer:function(value){
    	          	if(value){
    	          		return '<font color="red">已导出</font>';
    	          	} else {
    	          		return "未导出";
    	          	}
    	          }},
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
        tbar:['<pan id="toolbar-title-text">当前入库记录</span>','->',
              {text:'清空设备',
        	   iconCls:'icon-clearall',
        	   handler:function(){
        		   Ext.MessageBox.confirm('确认', '您确认要清除所有记录吗?', function(btn){
								            	if(btn=='yes'){
								            		equipStore.removeAll();
								            	}
					});
        	   }
        }]
	});
	function countTotal(f,n,o,e){
		var form=f.up('form');
		var nums=form.down('#serialNum_field').getValue();
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
		    var record=new Ext.create('Ems.baseinfo.Barcode',{
	            subtype_id:obj.subtype_id,
	            subtype_name:subtype_combox.getRawValue(),
	            prod_id:obj.prod_id,
	            prod_name:prod_combox.getRawValue(),
	            brand_id:obj.brand_id,
	            brand_name:brand_combox.getRawValue(),
	            supplier_id:obj.supplier_id,
	            supplier_name:supplier_combox.getRawValue(),
	            style:obj.style,
	            serialNum:obj.serialNum,
	            unitPrice:obj.unitPrice,
	            totalprice:obj.totalprice
		    })
			equipStore.add(record);
		}
	}
	
	var step1=Ext.create('Ext.panel.Panel',{
        layout: {
            type:'vbox',
            padding:'5',
            align:'stretch'
        },
        defaults:{margins:'0 0 5 0',border:false},
        items:[{xtype:'form',items:[{xtype:'columnbox',columnSize:4,items:[subtype_combox,prod_combox,brand_combox,supplier_combox]},
                                    {xtype:'columnbox',columnSize:4,items:[
                                    	{xtype:'textfield',itemId:'style_field',fieldLabel:'型号',name:'style',minValue:1,labelWidth:100,allowBlank:false,labelAlign:'right',value:1},
                                    	{xtype:'numberfield',itemId:'serialNum_field',fieldLabel:'入库数目',name:'serialNum',minValue:1,labelWidth:100,allowBlank:false,labelAlign:'right',value:1},
                                    	{xtype:'numberfield',itemId:'unitprice_field',fieldLabel:'单价(元)',name:'unitPrice',minValue:0,labelWidth:100,listeners:{change:countTotal},allowBlank:false,labelAlign:'right'},
										totalprice_display
									  ]
									}
		            		        //{xtype:'columnbox',columnSize:4,items:[{xtype:'listcombox',url:Ext.ContextPath+'/dataExtra/stockList.do',itemId:'stock_field',fieldLabel:'库房',name:'stid',allowBlank:false,emptyText:'未选择库房',labelAlign:'right'},{xtype:'textfield',name:'stmemo',fieldLabel:'库房描述',columnWidth:3/4,labelAlign:'right'}]}
		            		        ]},
        {layout:{type:'hbox',algin:'stretch'},items:[{flex:1,border:false,html:'<HR style="FILTER: alpha(opacity=100,finishopacity=0,style=3)" width="100%" color=#987cb9 SIZE=3>'},{xtype:'button',text:'添加',handler:addEquip,width:70,iconCls:'icon-add',margin:'0 5px 0 5px'}]},
        equip_grid,
        {html:'<HR style="FILTER: alpha(opacity=100,finishopacity=0,style=3)" width="100%" color=#987cb9 SIZE=3>'},
        //{html:'<img src="../images/error.gif" style="vertical-align:middle">&nbsp;库房人员应当根据采购单，对设备分类后，一次对同类设备批量“添加”入库，直到所有采购单设备根据设备类型都已经“添加”到入库清单后，可以选择“下一步”，进入到二维码生成步骤'}],
        {html:'<img src="../images/error.gif" style="vertical-align:middle">&nbsp;条码的生成规则是: 小类(2)+品名(2)+品牌(3)+供应商(3)+日期(6)+流水号(3)，按“导出”，导出条码'}],
        buttons:[{text:'导出',handler:function(btn){
            if (equipStore.getCount()> 0) { 
            	Ext.getBody().mask("正在导出....");
            	var barcodes = new Array();
            	equipStore.each(function(record){
            		barcodes.push(record.data);
            	});
            	
				Ext.Ajax.request({
					url:Ext.ContextPath+'/barcode/export.do',
					method:'POST',
					timeout:600000000,
					headers:{ 'Content-Type':'application/json;charset=UTF-8'},
					jsonData:barcodes,
					//params:{jsonStr:Ext.encode(equiplist)},
					success:function(response){
						var obj=Ext.decode(response.responseText);
						
						
						var test =window.open(Ext.ContextPath+"/barcode/download.do?fileName="+obj.root, "_blank");//这个方法就直接把这个TXT以浏览器的方式打开了 
						//test.document.execCommand("SaveAs");   
						//test.close(); 
						equipStore.each(function(record){
		            		record.set("status",true);
		            	});
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