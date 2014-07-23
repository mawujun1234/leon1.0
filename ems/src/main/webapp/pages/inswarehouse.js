Ext.onReady(function(){
	Ext.QuickTips.init(); 
	
	
//	
//	var style_field=Ext.create('Ext.form.field.Display',{
//		fieldLabel:'设备型号',name:'style',labelAlign:'right',hidden:true,submitValue:true
//	});
//	
//	//step1
//	var esubtype_combox=Ext.create('Ext.D.list.ComboBox',{
//		id:'esubtype_combox',url:Ext.ContextPath+'/dataExtra/esubtypeList.do',fieldLabel:'设备子类型',name:'esubtype',labelAlign:'right',autoSelected:true,loadAuto:true,editable:false
//	});
//	var etype_combox=Ext.create('Ext.D.list.ComboBox',{
//		url:Ext.ContextPath+'/dataExtra/etypeList.do',fieldLabel:'设备类型',name:'etype',cascade:true,cascadeId:'esubtype_combox',labelAlign:'right',autoSelected:true,loadAuto:true,editable:false
//	});
//	
//	var totalprice_display=Ext.create('Ext.form.field.Display',{
//		xtype:'displayfield',fieldLabel:'总价(元)',name:'totalprice',labelWidth:100,submitValue : true,labelAlign:'right',hidden:true
//	});
//	
//	esubtype_combox.on('change',function(field,newValue,oldValue,exception){
//		if(!!newValue&&newValue!=""){
//			Ext.Ajax.request({
//			    url: Ext.ContextPath+'/dataExtra/equipStyle.do',
//			    params: {
//			        etype:etype_combox.getValue(),
//			        esubtype:newValue
//			    },
//			    success: function(response, opts){
//			    	var ret=Ext.decode(response.responseText);
//			    	if(ret.success){
//			    		if(!style_field.isVisible()){
//			    			style_field.setVisible(true);
//			    		}
//			    		style_field.setValue(ret.style);
//			    	}else{
//			    		Ext.Msg.alert('提示',ret.msg);
//			    	}    
//			    },
//			    failure: function(response, opts){
//			    	Ext.Msg.alert('提示',response.responseText);
//			    }
//			});
//		}
//	});
//	
//    Ext.define('equipment',{
//        extend: 'Ext.data.Model',
//        fields: [
//            {name: 'ecode', mapping: 'ecode'},
//            {name: 'etype', mapping: 'etype'},
//            {name: 'typename', mapping: 'typename'},
//            {name: 'esubtype', mapping: 'esubtype'},
//            {name: 'subtypename', mapping: 'subtypename'},
//            {name: 'sid', mapping: 'sid'},
//            {name: 'sname', mapping: 'sname'},
//            {name: 'nums', mapping: 'nums'},
//            {name: 'price', mapping: 'price'},
//            {name: 'totalprice', mapping: 'totalprice'},
//            {name: 'stid', mapping: 'stid'},
//            {name: 'stock', mapping: 'stock'},
//            {name: 'style', mapping: 'style'},
//            {name: 'stmemo', mapping: 'stmemo'},
//            {name: 'flag', mapping: 'flag'}
//        ]
//    });
//    var equipStore = Ext.create('Ext.data.Store', {
//        autoDestroy: true,
//        model: 'equipment',
//        proxy: {
//            type: 'memory'
//        }
//    });
//	var equip_grid=Ext.create('Ext.grid.Panel',{
//		flex:1,
//		store:equipStore,
//    	columns: [Ext.create('Ext.grid.RowNumberer'),
//    	          {header: 'etype', dataIndex: 'etype',hideable:false,hidden:true},
//    	          {header: '设备类型', dataIndex: 'typename',width:120},
//    	          {header: 'esubtype', dataIndex: 'esubtype',hideable:false,hidden:true},
//    	          {header: '设备子类型', dataIndex: 'subtypename',width:120},
//    	          {header: 'sid', dataIndex: 'sid',hideable:false,hidden:true},
//    	          {header: '设备型号', dataIndex: 'style',width:120},
//    	          {header: '设备商', dataIndex: 'sname',width:120},
//    	          {header: '数量', dataIndex: 'nums',width:70},
//    	          {header: '单价(元)', dataIndex: 'price',width:70},
//    	          {header: '总价(元)', dataIndex: 'totalprice',width:70},
//    	          {header: 'stid', dataIndex: 'stid',hideable:false,hidden:true},
//    	          {header: '库房', dataIndex: 'stock',width:120},
//    	          {header: '库房描述', dataIndex: 'stmemo',flex:1},
//    	          { header:'操作',
//	                xtype: 'actioncolumn',
//	                width: 70,
//	                items: [{
//	                    icon   : '../images/delete.gif',  // Use a URL in the icon config
//	                    tooltip: '删除',
//	                    handler: function(grid, rowIndex, colIndex) {
//	                        var rec = equipStore.getAt(rowIndex);
//	                        Ext.MessageBox.confirm('确认', '您确认要删除该记录吗?', function(btn){
//	                        	if(btn=='yes'){
//	                        		equipStore.remove(rec);
//	                        	}
//	                        });
//	                    }
//	                }]
//	            }],
//        tbar:['<pan id="toolbar-title-text">当前入库记录</span>','->',
//              {text:'清空设备',
//        	   iconCls:'icon-clearall',
//        	   handler:function(){
//        		   Ext.MessageBox.confirm('确认', '您确认要清除所有记录吗?', function(btn){
//								            	if(btn=='yes'){
//								            		equipStore.removeAll();
//								            	}
//								            });
//        		   }
//        }]
//	});
//	
//	var step1=Ext.create('Ext.panel.Panel',{
//        layout: {
//            type:'vbox',
//            padding:'5',
//            align:'stretch'
//        },
//        defaults:{margins:'0 0 5 0',border:false},
//        items:[{xtype:'form',items:[{xtype:'columnbox',columnSize:4,items:[etype_combox,esubtype_combox,style_field,
//		            		                                               {xtype:'listcombox',itemId:'supplier_field',url:Ext.ContextPath+'/dataExtra/supplierList.do',fieldLabel:'厂商',name:'sid',editable:false,allowBlank:false,emptyText:'未选择厂商',labelAlign:'right'}]},
//                                    {xtype:'columnbox',columnSize:4,items:[{xtype:'numberfield',itemId:'nums_field',fieldLabel:'入库数目',name:'nums',minValue:1,labelWidth:100,listeners:{change:countTotal},allowBlank:false,labelAlign:'right',value:1},
//                                                                          {xtype:'numberfield',itemId:'unitprice_field',fieldLabel:'单价(元)',name:'price',minValue:0,labelWidth:100,listeners:{change:countTotal},allowBlank:false,labelAlign:'right'},
//                                                                          totalprice_display]},
//		            		        {xtype:'columnbox',columnSize:4,items:[{xtype:'listcombox',url:Ext.ContextPath+'/dataExtra/stockList.do',itemId:'stock_field',fieldLabel:'库房',name:'stid',allowBlank:false,emptyText:'未选择库房',labelAlign:'right'},{xtype:'textfield',name:'stmemo',fieldLabel:'库房描述',columnWidth:3/4,labelAlign:'right'}]}]},
//        {layout:{type:'hbox',algin:'stretch'},items:[{flex:1,border:false,html:'<HR style="FILTER: alpha(opacity=100,finishopacity=0,style=3)" width="100%" color=#987cb9 SIZE=3>'},{xtype:'button',text:'添加',handler:addEquip,width:70,iconCls:'icon-add',margin:'0 5px 0 5px'}]},
//        equip_grid,
//        {html:'<HR style="FILTER: alpha(opacity=100,finishopacity=0,style=3)" width="100%" color=#987cb9 SIZE=3>'},
//        {html:'<img src="../images/error.gif" style="vertical-align:middle">&nbsp;库房人员应当根据采购单，对设备分类后，一次对同类设备批量“添加”入库，直到所有采购单设备根据设备类型都已经“添加”到入库清单后，可以选择“下一步”，进入到条形码生成步骤'}],
//        buttons:[{text:'下一步',handler:function(btn){
//            if (equipStore.getCount()> 0) { 
//            	var equiplist = new Array();
//            	equipStore.each(function(record){
//            		equiplist.push(record.data);
//            	});
//            	printStore.load({
//            		params:{jsonStr:Ext.encode(equiplist)},
//            		callback: function(records, operation, success) {
//				        if(success){
//							var cardview=step1.up("#equip_new");
//							var layout=cardview.getLayout();
//							layout['next']();
//				        }
//				    }
//				});
//            }else{
//            	Ext.Msg.alert('提示','请先添加一个设备');
//            }
//		}}]
//	});
//	
//	function countTotal(f,n,o,e){
//		var form=f.up('form');
//		var nums=form.down('#nums_field').getValue();
//		var unitprice=form.down('#unitprice_field').getValue();
//		if(!!nums&&!!unitprice&&nums!=""&&unitprice!=""){
//			var total=nums*unitprice;
//			if(!totalprice_display.isVisible()){
//				totalprice_display.setVisible(true);
//			}
//			totalprice_display.setValue(total.toFixed(2));
//		}
//	}
//	
//	function addEquip(){
//		var equipform=step1.down('form');
//        var form=equipform.getForm();
//		if(form.isValid()){
//			var obj=form.getValues();
//		    var record=new Ext.create('equipment',{
//	            etype:obj.etype,
//	            typename:etype_combox.getRawValue(),
//	            esubtype:obj.esubtype,
//	            subtypename:esubtype_combox.getRawValue(),
//	            sid:obj.sid,
//	            sname:equipform.down('#supplier_field').getRawValue(),
//	            nums:obj.nums,
//	            price:obj.price,
//	            totalprice:obj.totalprice,
//	            stid:obj.stid,
//	            stock:equipform.down('#stock_field').getRawValue(),
//	            style:obj.style,
//	            stmemo:obj.stmemo,
//	            printed:0
//		    })
//			equipStore.add(record);
//		}
//	}
//	
//    var printStore = Ext.create('Ext.data.Store', {
//        model: 'equipment',
//        proxy: {
//            type: 'ajax',
//            url: Ext.ContextPath+'/inWarehouse/getBarCodeList.do',
//            reader: {
//                type: 'json',
//                root: 'root'
//            }
//        }
//    });
//    
//	//step2
//	var printPanel=Ext.create('Ext.grid.Panel',{
//		flex:1,
//		title:'打印条形码',
//		store:printStore,
//		multiSelect: true,
//		selModel: Ext.create('Ext.selection.CheckboxModel',{mode:'MULTI'}),
//    	columns: [Ext.create('Ext.grid.RowNumberer'),
//    	          {header: '条形码', dataIndex: 'ecode'},
//    	          {header: 'etype', dataIndex: 'etype',hideable:false,hidden:true},
//    	          {header: '设备类型', dataIndex: 'typename',width:120},
//    	          {header: 'esubtype', dataIndex: 'esubtype',hideable:false,hidden:true},
//    	          {header: '设备子类型', dataIndex: 'subtypename',width:120},
//    	          {header: 'sid', dataIndex: 'sid',hideable:false,hidden:true},
//    	          {header: '设备型号', dataIndex: 'style',width:120},
//    	          {header: '设备商', dataIndex: 'sname',width:120},
//    	          {header: '单价(元)', dataIndex: 'price',width:70},
//    	          {header: 'stid', dataIndex: 'stid',hideable:false,hidden:true},
//    	          {header: '库房', dataIndex: 'stock',width:120},
//    	          {header: '库房描述', dataIndex: 'stmemo',flex:1},
//    	          {header: '状态', dataIndex: 'printed',renderer:flagRender}]
//	});
//	
//	function flagRender(v){
//        if (v == 1) {
//        	return '<font color="red">已打印</font>';
//        }
//        return '未打印';
//    }
//	
//	var step2=Ext.create('Ext.panel.Panel',{
//        layout: {
//            type:'vbox',
//            padding:'5',
//            align:'stretch'
//        },
//        //defaults:{margins:'0 0 5px 0',border:false},
//        items:printPanel,
//		buttons:[{text:'上一步',handler:function(btn){
//			var cardview=btn.up("#equip_new");	
//			var layout=cardview.getLayout();
//			layout['prev']();
//		}},{text:'生成/打印',
//			handler:doPrint
//		}]
//	});
//	var barcodewin;
//	function doPrint(){
//		var records=printPanel.getSelectionModel().getSelection();
//		var len=records.length;
//		if(len<1){
//			Ext.Msg.alert('提示:','请选择一种设备');
//		}else{
//			if(!barcodewin){
//			   barcodewin=Ext.create('Ext.window.Window',{
//				   title:'条形码',
//				   height:350,
//				   width:120,
//				   closeAction:'hide',
//			       autoScroll:true,
//			       bodyPadding:'5px',
//			       items:[],
//			       buttons:[
////			       	{text:'关闭',handler:function(){
////			    	   this.up('window').close();
////			       }},
//			       	{text:'打印',iconCls:'icon-print',handler:function(){
//			    	   for(var i=0;i<len;i++){
//			    		   printPage(records[i].get('ecode'));
//			    	   }
//			       }}]
//			   });
//			}else{
//				barcodewin.removeAll();
//			}
//			for(var j=0;j<len;j++){
//				//console.log(record.get('ecode'));
//				var record=records[j];
//				barcodewin.add({
//					height:85,
//					width:85,
//					layout:{
//						type:'vbox',
//						align:'center',
//						pack:'center'
//					},
//					items:{
//						height:80,
//						width:80,
//						xtype:'imgbox',
//						imgSrc:Ext.ContextPath+'/qrcode/qrcode.do?ecode='+record.get('ecode')+'&height=80&width=80'
//						//imgSrc:Ext.ContextPath+'/barcode.action?msg='+record.get('ecode')+'&height=8'
//						//imgSrc:Ext.ContextPath+'/barcode/barcode.do?barcode='+record.get('ecode')+'&height=8'
//					}
//				});
//				record.set('printed',1);
//			}
//			barcodewin.show();
//		}
//	}
	//step3
    Ext.define('barcodeequip',{
        extend: 'Ext.data.Model',
        fields: [
            {name: 'batchid', mapping: 'batchid'},
            {name: 'cpid', mapping: 'cpid'},
            {name: 'datebuying', mapping: 'datebuying'},
            {name: 'did', mapping: 'did'},
            {name: 'ecode', mapping: 'ecode'},
            {name: 'ename', mapping: 'ename'},
            {name: 'estatus', mapping: 'estatus'},
            {name: 'esubtype', mapping: 'esubtype'},
            {name: 'etype', mapping: 'etype'},
            {name: 'fstJid', mapping: 'fstJid'},
            {name: 'iid', mapping: 'iid'},
            {name: 'lstJid', mapping: 'lstJid'},
            {name: 'lstMjid', mapping: 'lstMjid'},
            {name: 'lstOid', mapping: 'lstOid'},
            {name: 'lstRid', mapping: 'lstRid'},
            {name: 'memo', mapping: 'memo'},
            {name: 'nums', mapping: 'nums'},
            {name: 'price', mapping: 'price'},
            {name: 'sid', mapping: 'sid'},
            {name: 'sname', mapping: 'sname'},
            {name: 'stid', mapping: 'stid'},
            {name: 'stmemo', mapping: 'stmemo'},
            {name: 'stock', mapping: 'stock'},
            {name: 'style', mapping: 'style'},
            {name: 'subtypename', mapping: 'subtypename'},
            {name: 'timesMaintained', mapping: 'timesMaintained'},
            {name: 'timesOutsourcing', mapping: 'timesOutsourcing'},
            {name: 'timesRepairing', mapping: 'timesRepairing'},
            {name: 'typename', mapping: 'typename'},
            {name: 'uname', mapping: 'uname'},
            {name: 'unitid', mapping: 'unitid'},
            {name:'rucid',mapping:'rucid'},
            {name:'rucname',mapping:'rucname'}
        ]
    });
    
    var scanStore = Ext.create('Ext.data.Store', {
        // destroy the store if the grid is destroyed
        autoDestroy: true,
        model: 'barcodeequip',
        proxy: {
            type: 'memory'
        }
    });
    
	var equipScanGrid=Ext.create('Ext.grid.Panel',{
		region:'center',
		store:scanStore,
    	columns: [{header: '条码',dataIndex: 'ecode',width:150},
    	          {header: '设备类型', dataIndex: 'typename'},
    	          {header: '设备子类型', dataIndex: 'subtypename'},
    	          {header: '型号', dataIndex: 'style'},
    	          {header: '批次', dataIndex: 'batchid'},
    	          {header: '设备状态', dataIndex: 'estatus',renderer:statusInfo},
    	          {header: '仓库', dataIndex: 'stmemo',flex:1},
		          { xtype: 'actioncolumn',
    	        	header:'操作',
	                width: 50,
	                items: [{
	                    icon   : '../images/delete.gif',  // Use a URL in the icon config
	                    tooltip: 'Sell stock',
	                    handler: function(grid, rowIndex, colIndex) {
	                        var rec = scanStore.getAt(rowIndex);
	                        Ext.MessageBox.confirm('确认', '您确认要删除该记录吗?', function(btn){
	                        	if(btn=='yes'){
	                        		scanStore.remove(rec);
	                        	}
	                        });
	                    }
	                }]} 
    	          ],
            listeners: {
                selectionchange: function(model, records) {
                    if (records[0]) {
                    	step1.down('#equipinfo_form').getForm().loadRecord(records[0]);
                    }
                }
            },
            tbar:['<pan id="toolbar-title-text">扫描设备列表</span>','->',
                  {text:'清空设备',
            	   iconCls:'icon-clearall',
            	   handler:function(){
            		   Ext.MessageBox.confirm('确认', '您确认要清除所有记录吗?', function(btn){
									            	if(btn=='yes'){
									            		scanStore.removeAll();
									            	}
									            });
            		   }
            }]
	});
	
	var ecode_length=18;
	var ecode_field=Ext.create('Ext.form.field.Text',{
		name:'ecode',
		fieldLabel:'输入设备条形码',
		allowBlank:false,
		vtype:'alphanum',
		minLength:ecode_length,
		maxLength:ecode_length,
		msgTarget:'side',
		validateOnBlur:false,
		listeners:{
			blur:function(f,e){
				if(!f.getValue()||f.getValue()==''){
					f.clearInvalid();
				}
			},
			change:equipScan
		}
	});
	
	var estatus_fieild=Ext.create('Ext.D.list.ComboBox',{labelWidth:75,width:220,xtype:'listcombox',url:Ext.ContextPath+'/dataExtra/estatusList.do',fieldLabel:'设备状态',loadAuto:true,name:'estatus',readOnly:true});
	var stock_field=Ext.create('Ext.D.list.ComboBox',{labelWidth:50,url:Ext.ContextPath+'/dataExtra/stockList.do',itemId:'stock_field',fieldLabel:'库房',name:'stid',allowBlank:false,emptyText:'未选择库房',labelAlign:'right'});
	var step3=Ext.create('Ext.panel.Panel',{
        layout: {
            type:'vbox',
            padding:'5',
            align:'stretch'
        },
        defaults:{border:false},
        items:[{xtype:'fieldcontainer',margin:'5px',layout:'hbox',items:[stock_field,ecode_field,{margins:'0 0 0 5px',xtype:'button',text:'清除',handler:function(btn){ecode_field.reset();ecode_field.focus();}}]},
               {html:'<HR style="FILTER: alpha(opacity=100,finishopacity=0,style=3)" width="100%" color=#987cb9 SIZE=3>'},
               {flex:1,layout:'border',bodyStyle:'background-color:#FFFFFF',//设置面板体的背景色
              		defaults:{border:false},
              		items:[{
              			xtype:'form',
              			region:'west',
              			layout:'fit',
              			split:true,
              			width:255,
              			items:[{xtype:'fieldset',title:'设备基本信息',defaults:{xtype:'textfield',readOnly:true,labelWidth:75,width:220},
              				    margin:'0px',
      							items:[{fieldLabel:'条形码',name:'ecode'},
      							       estatus_fieild,
      	  					           {fieldLabel:'设备类型',name:'typename'},
      	  					     	   {fieldLabel:'型号',name:'style'},
      	  					           {fieldLabel:'厂家',name:'sname'},
      						           {fieldLabel:'批次',name:'batchid'},
      						     	   {fieldLabel:'使用单位',name:'uname'}]}]
      	        		},equipScanGrid]
              	   }],
		buttons:[{text:'下一步',handler:function(btn){
			if(scanStore.getCount()>0){
				step3.down('form').getForm().reset();
				var cardview=btn.up("#equip_scan_imp");	
				var layout=cardview.getLayout();
				layout['next']();
			}else{
				Ext.MessageBoxEx('提示','当前入库设备为 0 !');
			}
		}}],
    	listeners:{
    		activate:function(p,e){
    			ecode_field.reset();
    			ecode_field.focus();
    		}
    	}
	});
	
	function statusInfo(v){
		var stateStore= estatus_fieild.getStore();
		var diplayTxt="";
		stateStore.each(function(record){
			var value=record.get('value');
			if(v===value){
				diplayTxt=record.get('text');
				return false;
			}
		});
		return diplayTxt;
	}
	//f:form,n:,o:,e:
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
	
	var step4=Ext.create('Ext.form.Panel',{
        layout: {
            type:'vbox',
            padding:'50',
            align:'stretch'
        },
        defaults:{margin:'5px',border:false},
        items:[{padding:'5px',height:75,items:[{xtype:'columnbox',columnSize:4,items:[{name:'rucid',xtype:'listcombox',fieldLabel:'申请人',url:Ext.ContextPath+'/dataExtra/clerkList.do',allowBlank:false,loadAuto:true,autoSelected:true}]},
                                                  {xtype:'columnbox',columnSize:4,items:[{name:'datebuying',xtype:'datefield',fieldLabel:'采购日期',allowBlank:false,format: 'Y-m-d',maxValue:new Date()},{name:'orders',xtype:'textfield',fieldLabel:'采购单',allowBlank:false}]}]},
               {html:'<HR style="FILTER: alpha(opacity=100,finishopacity=0,style=3)" width="100%" color=#987cb9 SIZE=3>'},
               {flex:1,xtype:'textarea',fieldLabel:'备注信息',labelAlign:'top',name:'memo'}],
		buttons:[{text:'上一步',handler:function(btn){
			var cardview=btn.up("#equip_scan_imp");	
			var layout=cardview.getLayout();
			layout['prev']();
		}},{text:'下一步',handler:function(btn){
			if(step4.getForm().isValid( )){
				ecode_field.setValue("");
				ecode_field.clearInvalid( );
							
				var cardview=btn.up("#equip_scan_imp");	
				var layout=cardview.getLayout();
				layout['next']();
			}
		}}]
	});
	
	var equiplist_grid=Ext.create('Ext.grid.Panel',{
		title:'扫描设备列表',
		region:'center',
		store:scanStore,
    	columns: [{header: 'etype',dataIndex: 'etype', hidden:true,hideable:false},
    			  {header: 'esubtype',dataIndex: 'esubtype', hidden:true,hideable:false},
    	          {header: '条码',dataIndex: 'ecode',width:150},
    	          {header: '设备类型', dataIndex: 'typename'},
    	          {header: '设备子类型', dataIndex: 'subtypename'},
    	          {header: '型号', dataIndex: 'style'},
    	          {header: '批次', dataIndex: 'batchid'},
    	          {header: '状态', dataIndex: 'estatus',renderer:statusInfo},
    	          {header: '仓库', dataIndex: 'stmemo',flex:1}]
	});
	
	var step5=Ext.create('Ext.form.Panel',{
    	layout: {
            type:'vbox',
            padding:'5',
            align:'stretch'
        },
        defaults:{border:false},
        items:[{
        	flex:1,
        	layout:'border',
        	bodyStyle:'background-color:#FFFFFF',
        	items:[{
      			region:'west',
      			split:true,
      			width:255,
      			border:false,
      			layout:'fit',
      			items:[{xtype:'fieldset',title:'入库单信息',defaults:{xtype:'textfield',readOnly:true,anchor:'100%',labelWdith:75},
      					margin:'0px',padding:'10px',
						items:[{xtype:'hidden',name:'rucid'},
						       {name:'rucname',fieldLabel:'入库申请人'},
						       {name:'orders',fieldLabel:'采购单'},
						       {name:'btime',xtype:'datefield',fieldLabel:'采购日期',format: 'Y-m-d'},
						       {xtype:'textarea',fieldLabel:'备注信息',labelAlign:'top',name:'memo',readOnly:false}]}]
	        	},equiplist_grid]
        },
        {margin:'5px 0 0 0',xtype:'columnbox',columnSize:4,items:[{columnWidth:2/4,xtype:'displayfield'},
                                                            {name:'ucid',xtype:'listcombox',fieldLabel:'经办人',url:Ext.ContextPath+'/dataExtra/clerkList.do',allowBlank:false,loadAuto:true,autoSelected:true},
                                                            {name:'itime',xtype:'datefield',fieldLabel:'经办日期',format: 'Y-m-d',value:new Date()}]}],
		buttons:[{text:'返回',itemId:'step5_first_btn',hidden:true,handler:function(btn){
			scanStore.removeAll();
			var cardview=btn.up("#equip_scan_imp");	
			var layout=cardview.getLayout();
			layout.setActiveItem(0);
		 }},{text:'上一步',itemId:'step5_prev_btn',handler:function(btn){
			var cardview=btn.up("#equip_scan_imp");	
			var layout=cardview.getLayout();
			layout['prev']();
		}},{text:'设备入库',itemId:'step5_complete_btn',handler:function(btn){
			//var ecodes=new Array();
			//var stids=[];
			var equipments=[];
			scanStore.each(function(record){
				//ecodes.push(record.get("ecode"));
				//stids.push(record.get("stid"));
				equipments.push({
					ecode:record.get("ecode"),
					stid:record.get("stid"),
					etype:record.get("etype"),
					esubtype:record.get("esubtype"),
					sid:record.get("sid"),
					price:record.get("price")
				});
			});
			var form=step5.getForm();
			var params=form.getValues();
			params.equipmentsStr=Ext.encode(equipments);
			if(equipments.length>0){
				//console.log(params);
				Ext.Ajax.request({
//		        	params:{
//		        		//ecodes:ecodes,
//		        		//stids:stids
//		        		equipments:Ext.encode(equipments)
//		        	},
					params :params,
					method:"POST",
		        	//headers:{ 'Content-Type':'application/json;charset=UTF-8','Accept':'application/json;'},
		        	//timeout:600000000,
		        	//standardSubmit:false,
		        	//jsonSubmit:true,
				   // clientValidation: true,
				    url: Ext.ContextPath+'/inWarehouse/impStockNew.do',
				    success: function(response) {
			        	Ext.MessageBox.confirm('设备入库成功', '返回继续入库设备？', function(btn){
			        		if(btn=='yes'){
			        			scanStore.removeAll();
								var cardview=step5.up("#equip_scan_imp");	
								var layout=cardview.getLayout();
								layout.setActiveItem(0);
			        		}else{
			        			var firstBtn=step5.down('#step5_first_btn');
			        			var prevBtn=step5.down('#step5_prev_btn');
			        			var completeBtn=step5.down('#step5_complete_btn');
			        			if(!firstBtn.isVisible()){
			        				firstBtn.setVisible(true);
			        			}
			        			if(prevBtn.isVisible()){
			        				prevBtn.setVisible(false);
			        			}
			        			if(completeBtn.isVisible()){
			        				completeBtn.setVisible(false);
			        			}
			        		}
			        	});
				    }
				});
				
//		        form.submit({
//		        	params:{
//		        		//ecodes:ecodes,
//		        		//stids:stids
//		        		equipments:Ext.encode(equipments)
//		        	},
//		        	headers:{ 'Content-Type':'application/json;charset=UTF-8','Accept':'application/json;'},
//		        	timeout:600000000,
//		        	standardSubmit:false,
//		        	jsonSubmit:true,
//				    clientValidation: true,
//				    url: Ext.ContextPath+'/inWarehouse/impStockNew.do',
//				    success: function(form, action) {
//			        	Ext.MessageBox.confirm('设备入库成功', '返回继续入库设备？', function(btn){
//			        		if(btn=='yes'){
//			        			scanStore.removeAll();
//								var cardview=step5.up("#equip_scan_imp");	
//								var layout=cardview.getLayout();
//								layout.setActiveItem(0);
//			        		}else{
//			        			var firstBtn=step5.down('#step5_first_btn');
//			        			var prevBtn=step5.down('#step5_prev_btn');
//			        			var completeBtn=step5.down('#step5_complete_btn');
//			        			if(!firstBtn.isVisible()){
//			        				firstBtn.setVisible(true);
//			        			}
//			        			if(prevBtn.isVisible()){
//			        				prevBtn.setVisible(false);
//			        			}
//			        			if(completeBtn.isVisible()){
//			        				completeBtn.setVisible(false);
//			        			}
//			        		}
//			        	});
//				    },
//				    failure: function(form, action) {
//				        switch (action.failureType) {
//				            case Ext.form.Action.CLIENT_INVALID:
//							    //客户端数据验证失败的情况下，例如客户端验证邮件格式不正确的情况下提交表单  
//							    Ext.MessageBoxEx('提示','数据错误，非法提交');  
//				                break;
//				            case Ext.form.Action.CONNECT_FAILURE:
//							    //服务器指定的路径链接不上时  
//							    Ext.MessageBoxEx('连接错误','指定路径连接错误!'); 
//				                break;
//				            case Ext.form.Action.SERVER_INVALID:
//				            	//服务器端你自己返回success为false时  
//							     Ext.MessageBoxEx('友情提示', "数据更新异常");
//							     break;
//							default:
//								 //其它类型的错误  
//		                         Ext.MessageBoxEx('警告', '服务器数据传输失败：'+action.response.responseText); 
//							     break;
//				       }
//				    }
//				});
			}
		}}],
		listeners:{
			activate:function(p,e){
				var step4From=step4.getForm();
				var obj=step4From.getValues();
				var displayform=p.getForm();
				displayform.setValues({memo:obj.memo,orders:obj.orders,btime:obj.datebuying,rucid:obj.rucid,rucname:step4.down('listcombox').getRawValue()});
			},
    		beforeactivate:function(p,e){
    			var firstBtn=p.down('#step5_first_btn');
    			var prevBtn=p.down('#step5_prev_btn');
    			var completeBtn=p.down('#step5_complete_btn');
    			if(firstBtn.isVisible()){
    				firstBtn.setVisible(false);
    			}
    			if(!prevBtn.isVisible()){
    				prevBtn.setVisible(true);
    			}
    			if(!completeBtn.isVisible()){
    				completeBtn.setVisible(true);
    			}
    		}
		}
	});
	
	Ext.create('Ext.container.Viewport',{
        layout:'card',
        renderTo:'view-port',
        itemId:'equip_scan_imp',
	    items:[step3,step4,step5]
	});
	
//	Ext.create('Ext.container.Viewport',{
//        layout:'fit',
//        renderTo:'view-port',
//	    items:{xtype:'tabpanel',
//	    	activeTab:1,
//	    	margin:'2px',
//	    	plain: true,
//	    	border:false,
//	    	defaults:{layout:'card',border:false},
//	    	items:[{itemId:'equip_new',title:'设备新增',items:[step1,step2]},
//	    	       {itemId:'equip_scan_imp',title:'设备入库',items:[step3,step4,step5]}]
//	    }
//	});
});