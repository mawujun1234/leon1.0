Ext.define('Ems.repair.MgrRepairGrid',{
	extend:'Ext.grid.Panel',
	requires: [
	     'Ems.repair.Repair',
	     'Ems.repair.ScrapForm'
	],
	columnLines :true,
	stripeRows:true,
	viewConfig:{
		stripeRows:true,
		listeners:{
			refresh:function(){
				//this.select(0);
			}
		}
	},
	pageSize:50,
	selModel:new Ext.selection.CheckboxModel({
		checkOnly:true,
		showHeaderCheckbox:false,//防止点全选，去选择
		renderer: function(value, metaData, record, rowIndex, colIndex, store, view) {
			if(record.get("status")=='back_store'){
				var baseCSSPrefix = Ext.baseCSSPrefix;
		        metaData.tdCls = baseCSSPrefix + 'grid-cell-special ' + baseCSSPrefix + 'grid-cell-row-checker';
		        return '<div class="' + baseCSSPrefix + 'grid-row-checker">&#160;</div>';
			} else {
				return "";
			}
	        
	    }
	}),
	initComponent: function () {
      var me = this;
      me.columns=[
      	    { header:'报废',
	        xtype: 'actioncolumn',
	        width: 40,
	        dataIndex:'scrap_id',
	        items: [{ 
	             isDisabled:function(view,rowIndex ,colIndex ,item ,record ){
	             	var status=record.get("status");
	             	var scrap_id=record.get("scrap_id");
	             	if(status=='scrap_confirm' || scrap_id){
	             		return false;
	             	}
	             	return true;
	             },
	             getClass:function(v,metadata,record,rowIndex ,colIndex ,store ){
	             	if(record.get("status")=='scrap_confirm'){
	             		return "scrap_edit";
	             	}
	             	if(record.get("scrap_id")){
	             		return "scrap_look";
	             	}
	             	return "";
	             },
	             getTip:function(value,metadata ,record,rowIndex ,colIndex ,store ){
	             	if(value && record.get("status")=='scrap_confirm'){
	             		return "确认报废单";
	             	}
	             	return '查看报废单';
	             },
	             handler: function(grid, rowIndex, colIndex) {
	                  var record = grid.getStore().getAt(rowIndex);

	                  me.makeSureScrapEquipment(record);
	                  
	             }
	        }]
	    },
		{dataIndex:'id',text:'维修单号',width:120},
		{dataIndex:'ecode',text:'条码',width:140},
		{dataIndex:'prod_name',text:'品名',width:140},
		{dataIndex:'equipment_style',text:'型号',width:140},
		{dataIndex:'str_out_name',text:'发货仓库'},
		{dataIndex:'rpa_type_name',text:'维修类型',width:60},
		{dataIndex:'status_name',text:'状态'},
		{dataIndex:'str_out_date',text:'出仓时间',xtype: 'datecolumn',   format:'Y-m-d'},
		{dataIndex:'rpa_name',text:'维修中心'},
		{dataIndex:'rpa_in_date',text:'入维时间',xtype: 'datecolumn',   format:'Y-m-d'},
		//{dataIndex:'rpa_in_oper_id',text:'rpa_in_oper_id'},
		{dataIndex:'rpa_out_date',text:'出维时间',xtype: 'datecolumn',   format:'Y-m-d'},
		//{dataIndex:'rpa_out_oper_id',text:'rpa_out_oper_id'},
		{dataIndex:'str_in_date',text:'入仓时间',xtype: 'datecolumn',   format:'Y-m-d'}
		//{dataIndex:'str_in_id',text:'str_in_id'},
		//{dataIndex:'str_in_oper_id',text:'str_in_oper_id'},
		//{dataIndex:'str_out_oper_id',text:'str_out_oper_id'}
      ];
      
	  me.store=Ext.create('Ext.data.Store',{
	  		
			autoSync:false,
			pageSize:me.pageSize,
			model: 'Ems.repair.Repair',
			autoLoad:false,
			proxy:{
				actionMethods:{read:'POST'},
				url:Ext.ContextPath+'/repair/storeMgrQuery.do',
				type:'ajax',
				reader:{
					type:'json',
					root:'root'
				}
			}
	  });
	  //me.store.load({params:{start:0,limit:me.pageSize}});
	  
      me.dockedItems= [{
	        xtype: 'pagingtoolbar',
	        store: me.store,  
	        dock: 'bottom',
	        displayInfo: true
	  }];
	  
	  
	  var store_combox=Ext.create('Ext.form.field.ComboBox',{
	        fieldLabel: '<b>发货仓库</b>',
	        labelAlign:'right',
            labelWidth:60,
	        //xtype:'combobox',
	        //afterLabelTextTpl: Ext.required,
	        name: 'store_id',
		    displayField: 'name',
		    valueField: 'id',
	        //allowBlank: false,
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
	  
	  var repair_combox=Ext.create('Ext.form.field.ComboBox',{
	        fieldLabel: '<b>维修中心</b>',
	        labelAlign:'right',
            labelWidth:60,
	        //xtype:'combobox',
	        //afterLabelTextTpl: Ext.required,
	        name: 'rpa_id',
		    displayField: 'name',
		    valueField: 'id',
	        //allowBlank: false,
	        store:Ext.create('Ext.data.Store', {
		    	fields: ['id', 'name'],
			    proxy:{
			    	type:'ajax',
			    	extraParams:{type:2,look:true},
			    	url:Ext.ContextPath+"/store/queryCombo.do",
			    	reader:{
			    		type:'json',
			    		root:'root'
			    	}
			    }
		   })
	  }); 
	  
	  var str_out_date_start=Ext.create('Ext.form.field.Date',{
	  	fieldLabel: '出仓时间',
	  	labelWidth:50,
	  	width:150,
	  	format:'Y-m-d',
        //name: 'str_out_date_start',
        value:  Ext.Date.add(new Date(), Ext.Date.DAY, -7)
	  });
	  var str_out_date_end=Ext.create('Ext.form.field.Date',{
	  	fieldLabel: '到',
	  	format:'Y-m-d',
	  	labelWidth:15,
	  	labelWidth:15,
        //name: 'str_out_date_end',
        value: new Date()
	  });
	  
	 
	  var status_combo=Ext.create('Ext.form.field.ComboBox',{
	        fieldLabel: '状态',
	        labelAlign:'right',
            labelWidth:30,
	        //xtype:'combobox',
	        //afterLabelTextTpl: Ext.required,
	        name: 'status',
		    displayField: 'name',
		    valueField: 'id',
	        allowBlank: false,
	        store:Ext.create('Ext.data.Store', {
		    	fields: ['id', 'name'],
			    data:[{id:"",name:"所有"},{id:"to_repair",name:"发往维修中心"},{id:"repairing",name:"维修中"},{id:"back_store",name:"返库途中"}
			    ,{id:"over",name:"完成"},{id:"scrap_confirm",name:"报废确认中"}]
		   })
	  }); 
	  var only_have_scap_checkbox=Ext.create('Ext.form.field.Checkbox',{
	  	labelWidth:50,
	  	fieldLabel: '只含报废',
	  	checked:false
	  });
	  
	 me.store.on("beforeload",function(store){
		store.getProxy().extraParams={
					str_out_id:store_combox.getValue(),
					rpa_id:repair_combox.getValue(),
					str_out_date_start: str_out_date_start.getRawValue(),
					str_out_date_end: str_out_date_end.getRawValue(),
					status:status_combo.getValue(),
					only_have_scap:only_have_scap_checkbox.getValue()
		};
	 });
	  var query_button=Ext.create("Ext.button.Button",{
			text:'查询',
			margin:'0 0 0 5',
			iconCls:'form-search-button',
			handler:function(){
				me.store.loadPage(1);
//				me.store.load({params:{
//					str_out_id:store_combox.getValue(),
//					rpa_id:repair_combox.getValue(),
//					str_out_date_start: str_out_date_start.getRawValue(),
//					str_out_date_end: str_out_date_end.getRawValue(),
//					status:status_combo.getValue(),
//					only_have_scap:only_have_scap_checkbox.getValue()
//				  }
//			    });  
			}
	  });
	  me.store.load({params:{
					str_out_id:store_combox.getValue(),
					rpa_id:repair_combox.getValue(),
					str_out_date_start: Ext.Date.format(new Date(str_out_date_start.getValue()),'Y-m-d'),
					str_out_date_end: Ext.Date.format(new Date(str_out_date_end.getValue()),'Y-m-d'),
					status:status_combo.getValue()
				}
	 });
	 var ecode_textfield=Ext.create('Ext.form.field.Text',{
		labelAlign:'right',
		name:'encode',
		labelWidth:60,
		width:230,
		hidden:true,
		disabled:true,
		fieldLabel: '扫描选择',
		minLength:Ext.ecode_length,
		maxLength:Ext.ecode_length,
		length:Ext.ecode_length,
		selectOnFocus:true,
		//width:200,
		//allowBlank:false
		listeners:{
			change:function(textfield, newValue, oldValue){
				if(newValue.length<Ext.ecode_length){
					return;
				}
				var flag=true;
				me.store.each(function(record){
					//flag=true;
					if(record.get("ecode")==newValue){
						flag=false;
						me.getSelectionModel( ).select(record,true);
						return;
					} 
				});
				
				if(flag){
					Ext.Msg.alert("消息","该设备状态不是'发往维修中心'和'维修中'或者该设备在下一页,请注意");	
				}
				ecode_textfield.setValue("");
				ecode_textfield.clearInvalid( );
			}
		}
	  });
	   me.ecode_textfield=ecode_textfield;
	  //入库按钮
	  var str_in_button=Ext.create("Ext.button.Button",{
			text:'入库',
			disabled:true,
			margin:'0 0 0 5',
			icon:Ext.ContextPath+"/icons/database_copy.png",
			handler:function(){
				var records=me.getSelectionModel( ).getSelection( );
				if(!records || records.length==0){
					Ext.Msg.alert("消息","请先选择要入库的设备");
					return;
				}
				//先选择仓库，弹出框
				var from=Ext.create('Ext.form.Panel',{
					frame:true,
					items:[{
				        fieldLabel: '<b>选择仓库</b>',
				        labelAlign:'right',
			            labelWidth:70,
				        xtype:'combobox',
				        afterLabelTextTpl: Ext.required,
				        name: 'str_in_id',
				        itemId:'str_in_id',
					    displayField: 'name',
					    valueField: 'id',
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
				  }],
				  buttons:[{
				  	text:'确定',
				  	handler:function(){
				  		var str_in_id=from.down("combobox#str_in_id");
						
						if(records && records.length>0){
							var repairs=[];
							for(var i=0;i<records.length;i++){
								repairs.push({
									ecode:records[i].get("ecode"),
									id:records[i].get("id"),
									rpa_id:records[i].get("rpa_id"),
									str_in_id:records[i].get("str_out_id")//入库仓库和发货仓库要一致
								});
								//ids.push(records[i].get("id"));
								//ecodes.push(records[i].get("ecode"));
							}
							Ext.getBody().mask("正在执行,请稍候.....");
							Ext.Ajax.request({
								url:Ext.ContextPath+'/repair/storeInStore.do',
								method:'POST',
								timeout:600000000,
								//headers:{ 'Content-Type':'application/json;charset=UTF-8'},
								params:{str_in_id:str_in_id.getValue()},
								jsonData:repairs,
								//params:{jsonStr:Ext.encode(equiplist)},
								success:function(response){
									var obj=Ext.decode(response.responseText);		
									//Ext.Msg.alert("消息","维修中心入库完成!");
									Ext.getBody().unmask();
									me.getStore().reload();
									win.close();
								},
								failure:function(){
									Ext.getBody().unmask();
								}
							});
						}
				  	}
				  }]
				});
				var win=Ext.create('Ext.window.Window',{
					layout:'fit',
					title:'选择入库仓库',
					modal:true,
					width:260,
					items:[from]
				});
				win.show();
				
				
			}
	  });
	  me.str_in_button=str_in_button;
	   
	  me.tbar={
		xtype: 'container',
		layout: 'anchor',
		defaults: {anchor: '0'},
		defaultType: 'toolbar',
		items: [{
			items: [store_combox,repair_combox,str_out_date_start,str_out_date_end,status_combo,only_have_scap_checkbox,query_button] // toolbar 1
		}, {
			items: [ecode_textfield,str_in_button] // toolbar 2
		}]
	}	
	   me.on('selectionchange',function(model,selected){
       	if(selected && selected.length>0){
       		me.toggleEcodeField(true);
       	} else {
       		me.toggleEcodeField(false);	
       	}
       });

       
      me.callParent();
	},
	toggleEcodeField:function(bool){
		if(bool){
			this.ecode_textfield.enable();
			this.str_in_button.enable();
		} else {
			this.ecode_textfield.disable();	
			this.str_in_button.disable();	
		}
		
	},
	makeSureScrapEquipment:function(repair){//报废设备
		var form=Ext.create('Ems.repair.ScrapForm',{
			storeer:true,
			listeners:{
				makeSureScrap:function(){
					repair.set("status",'over');
					repair.set("status_name",'完成');
					win.close();
				}
			}					
		});
		
		
		var win=Ext.create('Ext.window.Window',{
			title:'确认报废单',
			layout:'fit',
			modal:true,
			width:500,
			items:[form]
		});
		//form.win=win;
		if(repair.get("status")!='scrap_confirm'){
			form.makeSureScrapButton.hide();
			win.setTitle("查看报废单(不能编辑)");
	    }
			
		Ext.Ajax.request({
			url:Ext.ContextPath+'/scrap/loadByRepair_id.do',
			method:'POST',
			params:{repair_id:repair.get("id")},
			success:function(response){
				var obj=Ext.decode(response.responseText);
				if(obj.success){
					var scrap=Ext.create('Ems.repair.Scrap',obj.root);
					form.loadRecord(scrap);
				} else {
					Ext.Msg.alert("消息","没有对应的报废单，或者后台出错了");
					return;
				}
				win.show();
			}
		});
	}
});
