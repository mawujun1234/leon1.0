//Ext.require("Ems.store.Barcode");
//Ext.require("Ems.install.WorkUnitEquipmentWindow");
//Ext.require("Ems.install.StoreEquipmentWindow");
//Ext.require("Ems.store.BarcodeForm");
Ext.onReady(function(){

     var store_combox=Ext.create('Ext.form.field.ComboBox',{
	        fieldLabel: '<b>入库仓库</b>',
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
			    	url:Ext.ContextPath+"/store/queryCombo.do",
			    	reader:{
			    		type:'json',
			    		root:'root'
			    	}
			    }
		   })
	    });
//	var queryStoEquip_button=Ext.create('Ext.button.Button',{
//		text:'库存',
//		margin:'0 0 0 5',
//		handler:function(){
//			var store_id=store_combox.getValue();
//			if(!store_id){
//				Ext.Msg.alert("消息","请先选择一个仓库");
//				return;
//			}
//			showStoreEquipment(store_id,store_combox.getRawValue());
//		}
//	});
//	//显示某个作业单位的手持设备情况
//	function showStoreEquipment(store_id,store_name) {
//		var win=Ext.create('Ems.install.StoreEquipmentWindow',{
//			title:'库存设备情况',
//			width:800,
//			height:400,
//			closeAction:'hide',
//			constrainHeader:true,
//			modal:true,
//			store_id:store_id,
//			store_name:store_name
//		});
//		win.show();	
//	}
        
	var workUnit_combox=Ext.create('Ext.form.field.ComboBox',{
	        fieldLabel: '<b>作业单位</b>',
	        labelAlign:'right',
            labelWidth:60,
	        //xtype:'combobox',
	        //afterLabelTextTpl: Ext.required,
	        name: 'workUnit_id',
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
			    	url:Ext.ContextPath+"/workUnit/queryCombo.do",
			    	reader:{
			    		type:'json',
			    		root:'root'
			    	}
			    }
		   })
	    });
//	var queryWorkUnitEquip_button=Ext.create('Ext.button.Button',{
//		text:'持有',
//		margin:'0 0 0 5',
//		handler:function(){
//			var workUnit_id=workUnit_combox.getValue();
//			if(!workUnit_id){
//				Ext.Msg.alert("消息","请先选择一个作业单位");
//				return;
//			}
//			showWorkUnitEquipment(workUnit_id);
//		}
//	});
//	//显示某个作业单位的手持设备情况
//	function showWorkUnitEquipment(workUnit_id) {
//		var win=Ext.create('Ems.install.WorkUnitEquipmentWindow',{
//			title:'作业单位手持设备情况',
//			width:800,
//			height:400,
//			constrainHeader:true,
//			closeAction:'hide',
//			modal:true,
//			workUnit_id:workUnit_id
//		});
//		win.show();	
//	}
//	
//	var requestnum_textfield=Ext.create('Ext.form.field.Number',{
//		labelAlign:'right',
//		labelWidth:55,
//		fieldLabel: '<b>请领数</b>',
//		name:'requestnum',
//		width:110,
//		readOnly:false,
//		allowBlank:false
//	});
	
	
	var ecode_length=19;
	var ecode_textfield=Ext.create('Ext.form.field.Text',{
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
				//alert(type_radio.getValue().type);
//				if(type_radio.getValue().type==2){
//					alert("设备维修出库还没有做!");
//					return;
//				}
//				if(!type_radio.getValue().type){
//					Ext.Msg.alert("消息","请先选择出库类型!");
//					return;
//				}
				if(!workUnit_combox.getValue()){
					Ext.Msg.alert("消息","请先选择作业单位!");
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
		value:loginUsername
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
	var workUnit_id_temp=null;
	function equipScan(field,newValue,oldValue,e){
//		if(!stock_field.getValue()){
//			Ext.Msg.alert("消息","请先选择仓库!");
//			ecode_textfield.setValue("");
//			ecode_textfield.clearInvalid( );
//			return;
//		}
		if(!store_id_temp){
			store_id_temp=store_combox.getValue();
		} else if(store_id_temp!=store_combox.getValue()){
			Ext.Msg.alert("消息","对不起，一次入库只能选择一个仓库.");
			ecode_textfield.setValue("");workUnit_combox
			ecode_textfield.clearInvalid( );
			return;
		}
		if(!workUnit_id_temp){
			workUnit_id_temp=workUnit_combox.getValue();
		} else if(workUnit_id_temp!=workUnit_combox.getValue()){
			Ext.Msg.alert("消息","对不起，一次出库库只能选择一个作业单位.");
			ecode_textfield.setValue("");
			ecode_textfield.clearInvalid( );
			return;
		}
		
		var form= step1.down('form').getForm();
		if(newValue.length>=ecode_length){
		   if(field.isValid()){
			  // form.load({
		   	Ext.Ajax.request({
					params : {ecode:newValue},//传递参数   
					url : Ext.ContextPath+'/installOut/getEquipmentByEcode.do',//请求的url地址   
					method : 'GET',//请求方式   
					success : function(response) {//加载成功的处理函数   
						var ret=Ext.decode(response.responseText);
						if(ret.success){
							if(ret.root.status!=3 && ret.root.status!=2 && ret.root.status!=4){//这是新设备入库的情况
								Ext.Msg.alert("消息","该设备为非手持状态,不能添加到设备返库列表.");
								return;
							}
							if( ret.root.status==4){
								alert("该设备已损坏,请注意!");
							}
							//为新增的equipment添加仓库等其他信息
							ret.root.workUnit_id=workUnit_combox.getValue();
							ret.root.workUnit_name=workUnit_combox.getRawValue();
							ret.root.store_id=store_combox.getValue();
							ret.root.store_name=store_combox.getRawValue();
							//ret.root.memo=memo_textfield.getValue();
							//ret.root.outStore_type=type_radio.getValue().type;
							var scanrecord = Ext.create('Ems.baseinfo.Equipment', ret.root);

							ecode_textfield.setValue("");
							ecode_textfield.clearInvalid( );

							var exist=false;
							equipStore.each(function(record){
								if(newValue==record.get('ecode')){
								    exist=true;
								    return !exist;
								}
							});
							if(exist){
								Ext.MessageBoxEx('提示','该设备已经存在');
							}else{
								equipStore.insert(0, scanrecord);				
							}						
						}
					}
//					failure : function(response) {//加载失败的处理函数   
//						Ext.Msg.alert('提示', '设备加载失败：'+ response.responseText);
//						ecode_textfield.setValue("");
//						ecode_textfield.clearInvalid( );
//					}
				});
		   }
		}else{
			//form.reset();
		}
	}
	
	//==========================================================================================
	
	
	var equipStore = Ext.create('Ext.data.Store', {
        autoDestroy: true,
        model: 'Ems.baseinfo.Equipment',
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
    	          {header: '仓库', dataIndex: 'store_name'},
    	          //{header: '作业单位', dataIndex: 'workUnit_name'},
    	          //{header: '数量', dataIndex: 'serialNum',width:70},
    	          {header: '单价(元)', dataIndex: 'unitPrice',width:70},
    	          
    	          
    	          //{header: 'stid', dataIndex: 'stid',hideable:false,hidden:true},
    	         // {header: '库房', dataIndex: 'stock',width:120},
    	          {header: '状态', dataIndex: 'status',width:60,renderer:function(value){
    	          	  if(value==4 || value==5){
	    	          		return '<font color="red">'+equipmentStatus[value]+'</font>';
	    	          } else {
	    	          		return equipmentStatus[value];
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
        items:[{xtype:'form',items:[{xtype:'fieldcontainer',layout: 'hbox',items:[store_combox,workUnit_combox,ecode_textfield,clear_button]},
                                    {xtype:'fieldcontainer',layout: 'hbox',items:[storeman_textfield,inDate_textfield,memo_textfield]}
		            		        //{xtype:'columnbox',columnSize:4,items:[{xtype:'listcombox',url:Ext.ContextPath+'/dataExtra/stockList.do',itemId:'stock_field',fieldLabel:'库房',name:'stid',allowBlank:false,emptyText:'未选择库房',labelAlign:'right'},{xtype:'textfield',name:'stmemo',fieldLabel:'库房描述',columnWidth:3/4,labelAlign:'right'}]}
		            		        ]},
        {layout:{type:'hbox',algin:'stretch'},items:[{flex:1,border:false,html:'<HR style="FILTER: alpha(opacity=100,finishopacity=0,style=3)" width="100%" color=#987cb9 SIZE=3>'}
        //,{xtype:'button',text:'添加',handler:addEquip,width:70,iconCls:'icon-add',margin:'0 5px 0 5px'}
        ]},
        equip_grid,
        {html:'<HR style="FILTER: alpha(opacity=100,finishopacity=0,style=3)" width="100%" color=#987cb9 SIZE=3>'},
        //{html:'<img src="../images/error.gif" style="vertical-align:middle">&nbsp;库房人员应当根据采购单，对设备分类后，一次对同类设备批量“添加”入库，直到所有采购单设备根据设备类型都已经“添加”到入库清单后，可以选择“下一步”，进入到二维码生成步骤'}],
        {html:'<img src="../images/error.gif" style="vertical-align:middle">&nbsp;'}],
        buttons:[{text:'设备返库',handler:function(btn){
            if (equipStore.getCount()> 0) { 
            	Ext.getBody().mask("正在入库....");
            	var equipments = new Array();
            	equipStore.each(function(record){
            		equipments.push(record.data);
            	});
            	
				Ext.Ajax.request({
					url:Ext.ContextPath+'/installIn/equipmentInStore.do',
					method:'POST',
					timeout:600000000,
					headers:{ 'Content-Type':'application/json;charset=UTF-8'},
					params:{memo:memo_textfield.getValue(),store_id:store_combox.getValue(),workUnit_id:workUnit_combox.getValue()},
					jsonData:equipments,
					//params:{jsonStr:Ext.encode(equiplist)},
					success:function(response){
						store_id_temp=null;//用来判断仓库的id有没有变
						workUnit_id_temp=null;
						var obj=Ext.decode(response.responseText);
						
						Ext.Msg.alert("消息","设备返库完成!");
						equipStore.removeAll();
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