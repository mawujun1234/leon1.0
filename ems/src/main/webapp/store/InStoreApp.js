Ext.require("Ems.store.Barcode");
//Ext.require("Ems.store.BarcodeGrid");
//Ext.require("Ems.store.BarcodeTree");
//Ext.require("Ems.store.BarcodeForm");
Ext.onReady(function(){
	var type_radio=Ext.create('Ext.form.RadioGroup',{
            //xtype      : 'fieldcontainer',
            fieldLabel : '<b>入库类型</b>',
            labelAlign:'right',
            labelWidth:60,
            allowBlank:false,
            //width:200,
            defaultType: 'radiofield',
            defaults: {flex: 1},
            layout: 'hbox',
            items: [
                {
                    boxLabel  : '新设备入库',
                    name      : 'type',
                    inputValue: '1'
                }, {
                    boxLabel  : '设备返库',
                    name      : 'type',
                    inputValue: '2'
                }, {
                    boxLabel  : '维修入库',
                    name      : 'type',
                    inputValue: '3'
                }
            ]
     });
        
	var store_combox=Ext.create('Ext.form.field.ComboBox',{
	        fieldLabel: '<b>仓库</b>',
	        labelAlign:'right',
            labelWidth:55,
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
			    	url:Ext.ContextPath+"/store/queryCombo.do",
			    	reader:{
			    		type:'json',
			    		root:'root'
			    	}
			    }
		   })
	    });
	var ecode_length=19;
	var encode_textfield=Ext.create('Ext.form.field.Text',{
		labelAlign:'right',
		name:'encode',
		fieldLabel: '输入设备条码',
		minLength:ecode_length,
		maxLength:ecode_length,
		length:ecode_length,
		//width:200,
		allowBlank:false,
		listeners:{
			blur:function(f,e){
				if(!f.getValue()||f.getValue()==''){
					f.clearInvalid();
				}
			},
			focus:function(){
				//alert(type_radio.getValue());
				//console.dir(type_radio.getValue());
				if(!type_radio.getValue().type){
					Ext.Msg.alert("消息","请先选择入库类型!");
					return;
				}
				if(!store_combox.getValue()){
					Ext.Msg.alert("消息","请先选择仓库!");
					return;
				}
			},
			change:equipScan
		}
	});
	
	var clear_button=Ext.create('Ext.button.Button',{
		text:'清除',
		margin:'0 0 0 5',
		handler:function(){
			encode_textfield.setValue('');
		}
	});
	var storeman_textfield=Ext.create('Ext.form.field.Text',{
		labelAlign:'right',
		labelWidth:55,
		fieldLabel: '经办人',
		name:'storeman_id',
		readOnly:true,
		allowBlank:false,
		value:loginUsername
	});
	
	var inDate_textfield=Ext.create('Ext.form.field.Text',{
		labelAlign:'right',
		fieldLabel: '时间',
		labelWidth:55,
		name:'inDate',
		readOnly:true,
		allowBlank:false,
		value:Ext.Date.format(new Date(),'Y-m-d')
	});
	var memo_textfield=Ext.create('Ext.form.field.Text',{
		labelAlign:'right',
		fieldLabel: '备注',
		labelWidth:55,
		name:'memo',
		flex:1,
		allowBlank:true
	});
	
	function equipScan(f,n,o,e){
		if(!stock_field.getValue()){
			Ext.Msg.alert("消息","请先选择仓库!");
			ecode_field.setValue("");
			ecode_field.clearInvalid( );
			return;
		}
		
		var form= step3.down('form').getForm();
		if(n.length>=ecode_length){
		   if(f.isValid()){
			  // form.load({
		   	Ext.Ajax.request({
					params : {ecode:n},//传递参数   
					url : Ext.ContextPath+'/qrcode/getEquipInfo.do',//请求的url地址   
					method : 'GET',//请求方式   
					success : function(response) {//加载成功的处理函数   
						var ret=Ext.decode(response.responseText);
						if(ret.success){
							ret.root.stid=stock_field.getValue();
							ret.root.stmemo=stock_field.getRawValue();
							var scanrecord = Ext.create('equipment', ret.root);

							form.loadRecord(scanrecord);
							ecode_field.setValue("");
							ecode_field.clearInvalid( );
							var estatus=scanrecord.get('estatus');

							if(estatus==1){//这里是条码的表的状态
								var exist=false;
								scanStore.each(function(record){
								    if(n==record.get('ecode')){
								    	exist=true;
								    	return !exist;
								    }
								});
								if(exist){
									Ext.MessageBoxEx('提示','该设备已经存在');
								}else{
									//scanrecord.set("stid",stock_field.getValue());
									scanStore.insert(0, scanrecord);
									
								}
							}else{
								Ext.MessageBoxEx('提示','该设备为非新增设备,不能添加到入库列表');
							}
							
						}
					},
					failure : function(response) {//加载失败的处理函数   
						Ext.Msg.alert('提示', '设备加载失败：'+ response.responseText);
						ecode_field.setValue("");
						ecode_field.clearInvalid( );
					}
				});
		   }
		}else{
			//form.reset();
		}
	}
	
	//==========================================================================================
	
	
	var equipStore = Ext.create('Ext.data.Store', {
        autoDestroy: true,
        model: 'Ems.store.Barcode',
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
	
	
	function addEquip(){
		var equipform=step1.down('form');
        var form=equipform.getForm();
		if(form.isValid()){
			var obj=form.getValues();
		    var record=new Ext.create('Ems.store.Barcode',{
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
        items:[{xtype:'form',items:[{xtype:'fieldcontainer',layout: 'hbox',items:[type_radio,store_combox,encode_textfield,clear_button]},
                                    {xtype:'fieldcontainer',layout: 'hbox',items:[storeman_textfield,inDate_textfield,memo_textfield]}
		            		        //{xtype:'columnbox',columnSize:4,items:[{xtype:'listcombox',url:Ext.ContextPath+'/dataExtra/stockList.do',itemId:'stock_field',fieldLabel:'库房',name:'stid',allowBlank:false,emptyText:'未选择库房',labelAlign:'right'},{xtype:'textfield',name:'stmemo',fieldLabel:'库房描述',columnWidth:3/4,labelAlign:'right'}]}
		            		        ]},
        {layout:{type:'hbox',algin:'stretch'},items:[{flex:1,border:false,html:'<HR style="FILTER: alpha(opacity=100,finishopacity=0,style=3)" width="100%" color=#987cb9 SIZE=3>'},{xtype:'button',text:'添加',handler:addEquip,width:70,iconCls:'icon-add',margin:'0 5px 0 5px'}]},
        equip_grid,
        {html:'<HR style="FILTER: alpha(opacity=100,finishopacity=0,style=3)" width="100%" color=#987cb9 SIZE=3>'},
        //{html:'<img src="../images/error.gif" style="vertical-align:middle">&nbsp;库房人员应当根据采购单，对设备分类后，一次对同类设备批量“添加”入库，直到所有采购单设备根据设备类型都已经“添加”到入库清单后，可以选择“下一步”，进入到二维码生成步骤'}],
        {html:'<img src="../images/error.gif" style="vertical-align:middle">&nbsp;一次入库只能入一个仓库'}],
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