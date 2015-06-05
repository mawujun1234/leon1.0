//Ext.require("Ems.store.Barcode");
Ext.require("Ems.baseinfo.Equipment");
//Ext.require("Ems.store.BarcodeTree");
//Ext.require("Ems.store.BarcodeForm");
Ext.onReady(function(){
//	var type_radio=Ext.create('Ext.form.RadioGroup',{
//            //xtype      : 'fieldcontainer',
//            fieldLabel : '<b>入库类型</b>',
//            labelAlign:'right',
//            labelWidth:60,
//            allowBlank:false,
//            //width:200,
//            defaultType: 'radiofield',
//            defaults: {flex: 1},
//            layout: 'hbox',
//            items: [
//                {
//                    boxLabel  : '新设备入库',
//                    name      : 'type',
//                    inputValue: '1'
//                }, {
//                    boxLabel  : '设备返库',
//                    name      : 'type',
//                    inputValue: '2'
//                }, {
//                    boxLabel  : '维修入库',
//                    name      : 'type',
//                    inputValue: '3'
//                }
//            ]
//     });
	
    var checkDate=(new Date()).getTime();
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
			    	extraParams:{type:[1,3],edit:true},
			    	url:Ext.ContextPath+"/store/queryCombo.do",
			    	reader:{
			    		type:'json',
			    		root:'root'
			    	}
			    }
		   })
	    });

	var ecode_textfield=Ext.create('Ext.form.field.Text',{
		labelAlign:'right',
		name:'encode',
		fieldLabel: '输入设备条码',
		minLength:Ext.ecode_length,
		maxLength:Ext.ecode_length,
		//length:Ext.ecode_length,
		selectOnFocus:true,
		labelWidth:80,
		width:250,
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
//				if(type_radio.getValue().type!=1){
//					alert("设备返库和维修入库还没有做!");
//					return;
//				}
//				if(!type_radio.getValue().type){
//					Ext.Msg.alert("消息","请先选择入库类型!");
//					return;
//				}
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
		icon:'../icons/delRole.png',
		handler:function(){
			ecode_textfield.setValue('');
		}
	});
	var storeman_textfield=Ext.create('Ext.form.field.Text',{
		labelAlign:'right',
		labelWidth:55,
		fieldLabel: '经办人',
		name:'operater',
		readOnly:true,
		allowBlank:false,
		value:loginName
	});
	
	var inDate_textfield=Ext.create('Ext.form.field.Text',{
		labelAlign:'right',
		fieldLabel: '时间',
		labelWidth:55,
		name:'operateDate',
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
	
	var store_id_temp=null;//用来判断仓库的id有没有变
	function equipScan(field,newValue,oldValue,e){
//		if(!stock_field.getValue()){
//			Ext.Msg.alert("消息","请先选择仓库!");
//			ecode_textfield.setValue("");
//			ecode_textfield.clearInvalid( );
//			return;
//		}
		//if(!store_id_temp){
			store_id_temp=store_combox.getValue();
		//} else if(store_id_temp!=store_combox.getValue()){
		//	Ext.Msg.alert("消息","对不起，一次入库只能选择一个仓库.");
		//	ecode_textfield.setValue("");
		//	ecode_textfield.clearInvalid( );
		//	return;
		//}
		
		var form= step1.down('form').getForm();
		if(newValue.length>=Ext.ecode_length){
		   if(field.isValid()){
			  // form.load({
		   	Ext.Ajax.request({
					params : {ecode:newValue,store_id:store_combox.getValue(),checkDate:checkDate},//传递参数   
					url : Ext.ContextPath+'/inStore/getEquipFromBarcode.do',//请求的url地址   
					method : 'GET',//请求方式   
					success : function(response) {//加载成功的处理函数   
						var ret=Ext.decode(response.responseText);
						if(ret.success){
							
							//为新增的equipment添加仓库等其他信息
							ret.root.store_id=store_combox.getValue();
							ret.root.store_name=store_combox.getRawValue();
							
							var scanrecord = Ext.create('Ems.baseinfo.Equipment', ret.root);

							ecode_textfield.setValue("");
							ecode_textfield.clearInvalid( );

//							var exist=false;
//							equipStore.each(function(record){
//								if(newValue==record.get('ecode')){
//								    exist=true;
//								    return !exist;
//								}
//							});
//							if(exist){
//								Ext.Msg.alert('提示','该设备已经存在');
//							}else{
								equipStore.insert(0, scanrecord);	
								//alert(1);
								equip_grid.getView().refresh();
//							}		
							store_combox.disable();
							
							if(equipStore.getCount()>=pageSize+20){
								//永远获取第一页
								equipStore.getProxy().extraParams={store_id:store_combox.getValue(),checkDate:checkDate};
								//equipStore.load({params:{start:0,limit:pageSize}})
								equip_grid.getDockedItems('toolbar[dock="bottom"]')[0].moveFirst( );
							}
						} else {
							Ext.Msg.alert("消息",ret.msg);
							ecode_textfield.setValue("");
							ecode_textfield.clearInvalid( );
						}
					}
				});
		   }
		}else{
			//form.reset();
		}
	}
	
	//==========================================================================================
	
	var pageSize=50;
	var equipStore = Ext.create('Ext.data.Store', {
        autoDestroy: true,
        pageSize:pageSize,
        model: 'Ems.baseinfo.Equipment',
        autoLoad:false,
        proxy: {
	        type: 'ajax',
	        url: '/inStore/queryEquipFromCache.do',  // url that will load data with respect to start and limit params
	        reader: {
	            type: 'json',
	            root: 'root',
	            totalProperty: 'total'
	        }
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
	                        var record = equipStore.getAt(rowIndex);
	                        Ext.MessageBox.confirm('确认', '您确认要删除该记录吗?', function(btn){
	                        	if(btn=='yes'){
	                        		
	                        		Ext.Ajax.request({
										params : {ecode:record.get("ecode"),store_id:store_combox.getValue(),checkDate:checkDate},//传递参数   
										url : Ext.ContextPath+'/inStore/removeEquipFromCache.do',//请求的url地址   
										method : 'GET',//请求方式   
										success : function(response) {//加载成功的处理函数   
											var ret=Ext.decode(response.responseText);
											if(ret.success){
												equipStore.remove(record);
											}
										}
									});
	                        	}
	                        });
	                    }
	                }]
	            },
    			  {header: '条码', dataIndex: 'ecode',width:150},
    	          {header: '设备类型', dataIndex: 'subtype_name',width:120},
    	          {header: '品名', dataIndex: 'prod_name'},
    	          {header: '品牌', dataIndex: 'brand_name',width:120},
    	          {header: '供应商', dataIndex: 'supplier_name'},
    	          {header: '设备型号', dataIndex: 'style',width:120},
    	          {header: '规格', dataIndex: 'prod_spec',width:120,renderer:function(value,metadata,record){
						metadata.tdAttr = "data-qtip='" + value+ "'";
					    return value;
					}
				  },
    	          {header: '仓库', dataIndex: 'store_name'},
    	          //{header: '数量', dataIndex: 'serialNum',width:70},
  
    	          {header: '状态', dataIndex: 'status_name',width:100}
    	          ],
        tbar:['<pan id="toolbar-title-text">当前入库记录</span>','->',
              {text:'清空所选记录',
        	   iconCls:'icon-clearall',
        	   handler:function(){
        		   Ext.MessageBox.confirm('确认', '您确认要清除所有记录吗?', function(btn){
					   if(btn=='yes'){
							
							Ext.Ajax.request({
								params : {store_id:store_combox.getValue(),checkDate:checkDate},//传递参数   
								url : Ext.ContextPath+'/inStore/clearEquipFromCache.do',//请求的url地址   
								method : 'GET',//请求方式   
								success : function(response) {//加载成功的处理函数   
									var ret=Ext.decode(response.responseText);
									if(ret.success){
										equipStore.removeAll();
										store_combox.enable();
										equip_grid.getDockedItems('toolbar[dock="bottom"]')[0].moveFirst( );
									}
								}
							});
						}
					});
        	   }
        	}
        ],
        bbar:{
	        xtype: 'pagingtoolbar',
	        store: equipStore,  
	        dock: 'bottom',
	        displayInfo: true
	  }
	});
	
	
//	function addEquip(){
//		var equipform=step1.down('form');
//        var form=equipform.getForm();
//		if(form.isValid()){
//			var obj=form.getValues();
//		    var record=new Ext.create('Ems.store.Equipment',{
//	            subtype_id:obj.subtype_id,
//	            subtype_name:subtype_combox.getRawValue(),
//	            prod_id:obj.prod_id,
//	            prod_name:prod_combox.getRawValue(),
//	            brand_id:obj.brand_id,
//	            brand_name:brand_combox.getRawValue(),
//	            supplier_id:obj.supplier_id,
//	            supplier_name:supplier_combox.getRawValue(),
//	            style:obj.style,
//	            serialNum:obj.serialNum,
//	            unitPrice:obj.unitPrice,
//	            totalprice:obj.totalprice
//		    })
//			equipStore.add(record);
//		}
//	}
	
	var step1=Ext.create('Ext.panel.Panel',{
        layout: {
            type:'vbox',
            padding:'5',
            align:'stretch'
        },
        defaults:{margins:'0 0 5 0',border:false},
        items:[{xtype:'form',items:[{xtype:'fieldcontainer',layout: 'hbox',items:[store_combox,ecode_textfield,clear_button]},
                                    {xtype:'fieldcontainer',layout: 'hbox',items:[storeman_textfield,inDate_textfield,memo_textfield]}
		            		        //{xtype:'columnbox',columnSize:4,items:[{xtype:'listcombox',url:Ext.ContextPath+'/dataExtra/stockList.do',itemId:'stock_field',fieldLabel:'库房',name:'stid',allowBlank:false,emptyText:'未选择库房',labelAlign:'right'},{xtype:'textfield',name:'stmemo',fieldLabel:'库房描述',columnWidth:3/4,labelAlign:'right'}]}
		            		        ]},
        {layout:{type:'hbox',algin:'stretch'},items:[{flex:1,border:false,html:'<HR style="FILTER: alpha(opacity=100,finishopacity=0,style=3)" width="100%" color=#987cb9 SIZE=3>'}
        //,{xtype:'button',text:'添加',handler:addEquip,width:70,iconCls:'icon-add',margin:'0 5px 0 5px'}
        ]},
        equip_grid,
        {html:'<HR style="FILTER: alpha(opacity=100,finishopacity=0,style=3)" width="100%" color=#987cb9 SIZE=3>'},
        //{html:'<img src="../images/error.gif" style="vertical-align:middle">&nbsp;库房人员应当根据采购单，对设备分类后，一次对同类设备批量“添加”入库，直到所有采购单设备根据设备类型都已经“添加”到入库清单后，可以选择“下一步”，进入到二维码生成步骤'}],
        {html:'<img src="../images/error.gif" style="vertical-align:middle">&nbsp;一次入库只能选择一个仓库'}],
        buttons:[{text:'入库',handler:function(btn){
            if (equipStore.getCount()> 0) { 
            	Ext.getBody().mask("正在入库....");
            	var equipments = new Array();
            	equipStore.each(function(record){
            		equipments.push(record.data);
            	});
            	//ret.root.memo=memo_textfield.getValue();
							//ret.root.inStore_type=type_radio.getValue().type;
				Ext.Ajax.request({
					url:Ext.ContextPath+'/inStore/newInStore.do',
					method:'POST',
					timeout:600000000,
					headers:{ 'Content-Type':'application/json;charset=UTF-8'},
					params:{memo:memo_textfield.getValue(),store_id:store_combox.getValue(),checkDate:checkDate},
					jsonData:equipments,
					//params:{jsonStr:Ext.encode(equiplist)},
					success:function(response){
						var obj=Ext.decode(response.responseText);
						store_id_temp=null;
						Ext.Msg.alert("消息","入库完成!");
						equipStore.removeAll();
						Ext.getBody().unmask();
						store_combox.enable();
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