Ext.require("Ems.store.OrderList");

Ext.onReady(function(){
//	var order_no=Ext.create('Ext.form.field.Text',{
//		fieldLabel:'订单号',
//		name:'orderNo',
//		labelWidth:50,
//		allowBlank:false,
//		labelAlign:'right'
//	});
	
	var project_combox=Ext.create('Ems.baseinfo.ProjectCombo',{
		width:400,
		allowBlank: true,
		listeners:{
			select:function( combo, records, eOpts ) {
				//alert(1);
				order_no.getStore().removeAll();
				 
				order_no.clearValue( );
				//order_no.setValue('');
				//alert(order_no.lastQuery);
				delete order_no.lastQuery;
			}
		}
	});
	var order_no=Ext.create('Ext.form.field.ComboBox',{
	        fieldLabel: '订单号',
	        labelAlign:'right',
            labelWidth:60,
            width:280,
	        //xtype:'combobox',
	        //afterLabelTextTpl: Ext.required,
	        name: 'orderNo',
		    displayField: 'orderNo',
		    valueField: 'id',
		    queryParam: 'orderNo',
    		queryMode: 'remote',
    		triggerAction: 'query',
    		minChars:-1,
		    trigger1Cls: Ext.baseCSSPrefix + 'form-clear-trigger',
		    trigger2Cls: Ext.baseCSSPrefix + 'form-arrow-trigger',//'form-search-trigger',
			onTrigger1Click : function(){
			    var me = this;
			    me.setValue('');
			},
	        allowBlank: false,
	        store:Ext.create('Ext.data.Store', {
		    	fields: ['id', 'orderNo'],
			    proxy:{
			    	type:'ajax',
			    	//extraParams:{type:1,edit:true},
			    	url:Ext.ContextPath+"/order/queryUncomplete.do",
			    	reader:{
			    		type:'json',
			    		root:'root'
			    	}
			    },
			    listeners:{
			    	beforeload:function(store){
			    		
			    		store.getProxy().extraParams={
			    			project_id:project_combox.getValue()
			    		}
			    	}
			    }
		   })
	});
	
	var query_button=Ext.create('Ext.Button',{xtype:'button',text:'查询',handler:queryEquip,width:70,iconCls:'form-search-button',margin:'0 5px 0 5px'});
	
//	var equipStore = Ext.create('Ext.data.Store', {
//        autoDestroy: true,
//        autoLoad:false,
//        model: 'Ems.store.OrderList',
//        proxy: {
//        	url:Ext.ContextPath+'/order/queryList4Barcode.do',
//            type: 'ajax',
//            reader:{
//            	type:'json',
//            	root:'root'
//            }
//        }
//    });
	var equipStore = Ext.create('Ext.data.TreeStore', {
        autoDestroy: true,
        autoLoad:false,
        model: 'Ems.store.OrderList',
        //nodeParam :'parent_id',
        proxy: {
        	url:Ext.ContextPath+'/order/queryList4Barcode.do',
            type: 'ajax',
            reader:{
            	type:'json',
            	root:'root'
            },
            root: {
				expanded: true,
				text: "根节点"
			}
        },
        listeners:{
        	beforeload:function(store, operation, eOpts){
        		if(expendItem!=null){	
        			store.getProxy().extraParams.parent_id=expendItem.get("prod_id");
        		}
        	}
        }
    });
    var expendItem=null;
	var equip_grid=Ext.create('Ext.tree.Panel',{
		flex:1,
		columnLines:true,
		store:equipStore,
		useArrows: true,
    	rootVisible: false,
		plugins: [
	        Ext.create('Ext.grid.plugin.CellEditing', {
	            clicksToEdit: 1
//	            listeners:{
//	            	beforestartedit:function(editor, e) {alert(1);
//					    // commit the changes right after editing finished
//						var record=e.record;
//						if(record.get("noedit")){
//							editor.cancelEdit();
//						}
//						
//					}
//	            }
	        })
	    ],
	    listeners:{
	    	beforeitemexpand:function(node){
	    		expendItem=node;
	    	}
	    },
    	columns: [//Ext.create('Ext.grid.RowNumberer'),
    			{xtype:'treecolumn',dataIndex:'prod_id',text:'编码',width:120},
    			{dataIndex:'prod_name',text:'品名',width:80},
    	          {header: '设备类型', dataIndex: 'subtype_name',width:120},
    	          //{header: '品名', dataIndex: 'prod_name'},
    	          {header: '品牌', dataIndex: 'brand_name',width:120},
    	          //{header: '供应商', dataIndex: 'supplier_name'},
    	          {header: '设备型号', dataIndex: 'style',width:120},
    	          {header: '单位', dataIndex: 'prod_unit',width:40},
    	          {header: '规格', dataIndex: 'prod_spec',flex:1,renderer:function(value,metadata,record){
								metadata.tdAttr = "data-qtip='" + value+ "'";
							    return value;
							}
				  },
    	          {header: '订单数量', dataIndex: 'orderNum',width:70,renderer:function(value,metadata,record){
    	          	if(!record.get("noedit")){
    	          		return value;
    	          	} 
    	          }},
    	          {header: '累计入库数量', dataIndex: 'totalNum',width:80,renderer:function(value,metadata,record){
    	          	if(!record.get("noedit")){
    	          		return value;
    	          	} 
    	          }},
    	          {header: '本次入库数量', dataIndex: 'printNum',width:80,editor: {
	                xtype: 'numberfield',
	                selectOnFocus:true,
	                minValue:0,
	                allowBlank: false
	              },renderer:function(value,metadata,record){
    	          	if(!record.get("noedit")){
    	          		return value;
    	          	} 
    	          }},
    	          {header: '状态', dataIndex: 'exportStatus',width:60,renderer:function(value,metadata,record){
    	          	if(value){
    	          		return '<font color="red">已导出</font>';
    	          	} else {
    	          		if(!record.get("noedit")){
	    	          		return "未导出";
	    	          	} 
    	          		
    	          	}
    	          }},

    	          { header:'导出',
	                xtype: 'actioncolumn',
	                width: 70,   
	                items: [{
	                    icon   : '../icons/cog_start.png',  // Use a URL in the icon config
	                    tooltip: '导出',
	                    isDisabled :function(view  ,rowIndex  ,colIndex ,item ,record  ){
		                	//alert(record.get("noedit"));
		                	return record.get("noedit");
		                },
	                    handler: function(grid, rowIndex, colIndex,item,e,record ,row) {
	                    	//var record=equipStore.getAt(rowIndex);
	                    	//console.log(record.getData());
	                    	if(record.get("printNum")==0){
	                    		alert("请先输入 本次入库数量!");
	                    		return;
	                    	}
	                    	Ext.getBody().mask("正在导出......");
	                        Ext.Ajax.request({
								url:Ext.ContextPath+'/order/exportBarcode.do',
								method:'POST',
								timeout:600000000,
								headers:{ 'Content-Type':'application/json;charset=UTF-8'},
								jsonData:record.getData(),
								params:{orderno:order_no.getRawValue()},
								success:function(response){
									var obj=Ext.decode(response.responseText);
									
									
									var test =window.open(Ext.ContextPath+"/order/downloadBarcode.do?orderno="+order_no.getRawValue(), "_blank");//这个方法就直接把这个TXT以浏览器的方式打开了 

					            	record.set("exportStatus",true);
									Ext.getBody().unmask();
								},
								failure:function(){
									Ext.getBody().unmask();
								}
							});
	                    }
	                }]
	            }],
        tbar:['<pan id="toolbar-title-text">当前订单记录</span>','->']
	});

	equip_grid.on('beforeedit', function(editor, e) {
		
	    // commit the changes right after editing finished
		var record=e.record;
		//alert(record.get("noedit"));
		if(record.get("noedit")){
			editor.cancelEdit();
			return false;
		}
		
	});
	equip_grid.on('edit', function(editor, e) {
	    // commit the changes right after editing finished
		var record=e.record;
		var printNum=e.value;
		var orderNum=record.get("orderNum");
		var totalNum=record.get("totalNum");
		if((orderNum-totalNum)>0){
			if((orderNum-totalNum)<printNum){
				Ext.Msg.alert("消息","输入的数量超过了限制,将自动设为合适的值!");
				record.set('printNum',orderNum-totalNum);
			}
		} else {
			//Ext.Msg.alert("消息","该类型设备已经全部入库");
			//record.set('printNum',0);
		}
	});
	function queryEquip(){
		if(!order_no.getValue()){
			alert("请先输入订单号!");
			return;
		}
		//equipStore.getProxy().extraParams={};
		expendItem=null;
		equipStore.getProxy().extraParams={order_id:order_no.getValue()};
		equipStore.load();
	}
	
	var step1=Ext.create('Ext.panel.Panel',{
        layout: {
            type:'vbox',
            padding:'5',
            align:'stretch'
        },
        defaults:{margins:'0 0 5 0',border:false},
        items:[{xtype:'form',items:[
        							{xtype:'fieldcontainer',layout: 'hbox',items:[project_combox,order_no,query_button]}
        							//{xtype:'fieldcontainer',layout: 'hbox',items:[subtype_combox,prod_combox,brand_combox,supplier_combox]},
//                                    {xtype:'fieldcontainer',layout: 'hbox',items:[
//                                    	{xtype:'textfield',itemId:'style_field',fieldLabel:'型号',name:'style',labelWidth:50,allowBlank:false,labelAlign:'right'},
//                                    	{xtype:'numberfield',itemId:'orderNum_field',fieldLabel:'数目',name:'orderNum',minValue:1,labelWidth:100,allowBlank:false,labelAlign:'right',value:1},
//                                    	{xtype:'numberfield',itemId:'unitprice_field',fieldLabel:'单价(元)',name:'unitPrice',minValue:0,labelWidth:100,listeners:{change:countTotal},allowBlank:true,labelAlign:'right'},
//										totalprice_display
//									  ]
//									}
		            		       
		            		        ]},
        {layout:{type:'hbox',algin:'stretch'},items:[{flex:1,border:false,html:'<HR style="FILTER: alpha(opacity=100,finishopacity=0,style=3)" width="100%" color=#987cb9 SIZE=3>'}]},
        equip_grid,
        {html:'<HR style="FILTER: alpha(opacity=100,finishopacity=0,style=3)" width="100%" color=#987cb9 SIZE=3>'},
        //{html:'<img src="../images/error.gif" style="vertical-align:middle">&nbsp;条码的生成规则是: 小类(3)+品名(2)+-+品牌(3)+供应商(3)+-+日期(6)+流水号(4)，按“导出”，导出条码'}],
        {html:'<img src="../images/error.gif" style="vertical-align:middle">&nbsp;条码的生成规则是: 品名编码(6)+-+日期(6)+流水号(4)，按“导出”，导出条码'}],
        
        buttons:[{text:'导出',hidden:true,handler:function(btn){
            if (equipStore.getCount()> 0) { 
            	
            	var orderVOs = new Array();
            	equipStore.each(function(record){
            		if(record.get("printNum")>0){
            			orderVOs.push(record.getData());
            		}      		
            	});
            	if(orderVOs.length==0){
            		alert("请先输入要打印的条码数!");
            		return;
            	}
            	Ext.getBody().mask("正在保存....");
            	
				Ext.Ajax.request({
								url:Ext.ContextPath+'/order/exportBarcode.do',
								method:'POST',
								timeout:600000000,
								headers:{ 'Content-Type':'application/json;charset=UTF-8'},
								jsonData:orderVOs,
								//params:{jsonStr:Ext.encode(equiplist)},
								success:function(response){
									var obj=Ext.decode(response.responseText);
									
									
									
									var test =window.open(Ext.ContextPath+"/order/downloadBarcode.do?fileName="+obj.root, "_blank");//这个方法就直接把这个TXT以浏览器的方式打开了 
									//for(var i=0;i<orderVOs.length;i++){
									//	orderVOs[i].set("exportStatus",true);
									//}
									equipStore.each(function(record){
					            		record.set("exportStatus",true);     		
					            	});
					            	
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