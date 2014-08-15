Ext.define('Ems.repair.RMgrRepairGrid',{
	extend:'Ext.grid.Panel',
	requires: [
	     'Ems.repair.Repair'
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
		//checkOnly:true,
		showHeaderCheckbox:false,//防止点全选，去选择
		renderer: function(value, metaData, record, rowIndex, colIndex, store, view) {
			if(record.get("status")==1 ||record.get("status")==2 ){
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
		{dataIndex:'id',text:'维修单号',width:130},
		{dataIndex:'ecode',text:'条码',width:140},
		{dataIndex:'str_out_name',text:'发货仓库'},
		{dataIndex:'rpa_type_name',text:'维修类型',width:60},
		{dataIndex:'status_name',text:'状态'},
		{dataIndex:'str_out_date',text:'出仓时间',xtype: 'datecolumn',   format:'Y-m-d'},
		{dataIndex:'rpa_name',text:'维修中心'},
		{dataIndex:'rpa_in_date',text:'入维时间',xtype: 'datecolumn',   format:'Y-m-d'},
		{dataIndex:'rpa_user_id',text:'维修人'},
		//{dataIndex:'rpa_in_oper_id',text:'rpa_in_oper_id'},
		{dataIndex:'rpa_out_date',text:'出维时间',xtype: 'datecolumn',   format:'Y-m-d'},
		//{dataIndex:'rpa_out_oper_id',text:'rpa_out_oper_id'},
		{dataIndex:'str_in_date',text:'入仓时间',xtype: 'datecolumn',   format:'Y-m-d'},
		{ header:'操作',
	        xtype: 'actioncolumn',
	        width: 70,
	        items: [{
	             icon   : '../icons/brick_edit.png',  // Use a URL in the icon config
	             tooltip: '编辑      |  ',
	             handler: function(grid, rowIndex, colIndex) {
	                  var record = grid.getStore().getAt(rowIndex);
	                  if(record.get("status")!=2){
	                  	Ext.Msg.alert('消息',"不是\"维修中\"的设备不能编辑!");
	                  	return;
	                  }
					  me.recordDbclick(grid.getView(),record);
	             }
	        },{
	             icon   : '../icons/action_delete.gif',  // Use a URL in the icon config
	             tooltip: '报废',
	             handler: function(grid, rowIndex, colIndex) {
	                  var record = grid.getStore().getAt(rowIndex);
	                  if(record.get("status")!=2){
	                  	Ext.Msg.alert('消息',"不是\"维修中\"的设备不能报废!");
	                  	return;
	                  }
	                  Ext.MessageBox.confirm('确认', '您确认要报废该设备吗?', function(btn){
	                      if(btn=='yes'){
	                      	me.scrapEquipment(record);
							
	                      }
	                 });
	             }
	        }]
	    }
      ];
      
	  me.store=Ext.create('Ext.data.Store',{
	  		
			autoSync:false,
			pageSize:me.pageSize,
			model: 'Ems.repair.Repair',
			autoLoad:false,
			proxy:{
				actionMethods:{read:'POST'},
				url:Ext.ContextPath+'/repair/storeQuery.do',
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
	        fieldLabel: '仓库',
	        labelAlign:'right',
            labelWidth:40,
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
			    	extraParams:{type:1},
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
	        allowBlank: false,
	        store:Ext.create('Ext.data.Store', {
		    	fields: ['id', 'name'],
			    proxy:{
			    	type:'ajax',
			    	extraParams:{type:2},
			    	url:Ext.ContextPath+"/store/queryCombo.do",
			    	reader:{
			    		type:'json',
			    		root:'root'
			    	}
			    }
		   })
	  }); 
	  
	  var str_out_date_start=Ext.create('Ext.form.field.Date',{
	  	fieldLabel: '入维时间',
	  	labelWidth:70,
	  	format:'Y-m-d'
        //name: 'str_out_date_start',
       // value:  Ext.Date.add(new Date(), Ext.Date.DAY, -7)
	  });
	  var str_out_date_end=Ext.create('Ext.form.field.Date',{
	  	fieldLabel: '到',
	  	format:'Y-m-d',
	  	labelWidth:15
        //name: 'str_out_date_end',
        //value: new Date()
	  });
	  
//	  var container_repair_over=Ext.create('Ext.form.field.Checkbox',{
//	  	labelWidth:60,
//	  	fieldLabel: '包含完成',
//	  	checked:false
//	  });
	  var status_combo=Ext.create('Ext.form.field.ComboBox',{
	        fieldLabel: '状态',
	        labelAlign:'right',
            labelWidth:30,
	        //xtype:'combobox',
	        //afterLabelTextTpl: Ext.required,
	        name: 'status',
		    displayField: 'name',
		    valueField: 'id',
		    value:"1",
	        allowBlank: false,
	        store:Ext.create('Ext.data.Store', {
		    	fields: ['id', 'name'],
			    data:[{id:"1",name:"发往维修中心"},{id:"2",name:"维修中"},{id:"3",name:"返库途中"},{id:"4",name:"完成"}]
		   }),
		   listeners:{
//		   	  change:function(combo,newValue, oldValue){
//				
//			  }
		   }
	  }); 
	  
	  var query_button=Ext.create("Ext.button.Button",{
			text:'查询',
			margin:'0 0 0 5',
			iconCls:'form-search-button',
			handler:function(){
				var rpa_id=repair_combox.getValue();
				if(!rpa_id){
					Ext.Msg.alert("消息","请先选择入库的维修中心!");
					return;
				}
				ecode_textfield.enable();
				var status=status_combo.getValue();
				if(status==1){
					str_in_button.enable();
				} else {
					str_in_button.disable();
				}
				if(status==2){
					str_out_button.enable();
				} else {
					str_out_button.disable();
				}
				
				me.store.load({params:{
					str_out_id:store_combox.getValue(),
					rpa_id:repair_combox.getValue(),
					str_out_date_start: str_out_date_start.getValue()?Ext.Date.format(new Date(str_out_date_start.getValue()),'Y-m-d'):null,
					str_out_date_end: str_out_date_start.getValue()?Ext.Date.format(new Date(str_out_date_end.getValue()),'Y-m-d'):null,
					status:status_combo.getValue()
				}
			  });
			}
	  });
//	  me.store.load({params:{
//					str_out_id:store_combox.getValue(),
//					rpa_id:repair_combox.getValue(),
//					str_out_date_start: Ext.Date.format(new Date(str_out_date_start.getValue()),'Y-m-d'),
//					str_out_date_end: Ext.Date.format(new Date(str_out_date_end.getValue()),'Y-m-d'),
//					status:status_combo.getValue()
//				}
//	 });
	 
	 
	 var ecode_textfield=Ext.create('Ext.form.field.Text',{
		labelAlign:'right',
		name:'encode',
		labelWidth:60,
		width:230,
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
	  //入库按钮
	  var str_in_button=Ext.create("Ext.button.Button",{
			text:'入库',
			margin:'0 0 0 5',
			disabled:true,
			icon:Ext.ContextPath+"/icons/database_copy.png",
			handler:function(btn){

				var rpa_id=repair_combox.getValue();
				var records=me.getSelectionModel( ).getSelection( );
				if(records && records.length>0){
					//var ids=[];
					//var ecodes=[];
					var repairs=[];
					for(var i=0;i<records.length;i++){
						repairs.push({
							ecode:records[i].get("ecode"),
							id:records[i].get("id"),
							rpa_id:rpa_id,
							str_out_id:records[i].get("str_out_id")
						});
						//ids.push(records[i].get("id"));
						//ecodes.push(records[i].get("ecode"));
					}
					Ext.getBody().mask("正在执行,请稍候.....");
					Ext.Ajax.request({
						url:Ext.ContextPath+'/repair/repairInStore.do',
						method:'POST',
						timeout:600000000,
						//headers:{ 'Content-Type':'application/json;charset=UTF-8'},
						//params:{ids:ids,ecodes:ecodes,rpa_id:rpa_id},
						jsonData:repairs,
						//params:{jsonStr:Ext.encode(equiplist)},
						success:function(response){
							var obj=Ext.decode(response.responseText);		
							//Ext.Msg.alert("消息","维修中心入库完成!");
							Ext.getBody().unmask();
							me.getStore().reload();
						},
						failure:function(){
							Ext.getBody().unmask();
						}
					});
				}
			}
			
	  });
	  
	  //出库，维修好后出库
	  var str_out_button=Ext.create("Ext.button.Button",{
			text:'出库',
			margin:'0 0 0 5',
			disabled:true,
			icon:Ext.ContextPath+"/icons/database_go.png",
			handler:function(btn){
				var rpa_id=repair_combox.getValue();
				var records=me.getSelectionModel( ).getSelection( );
				if(records && records.length>0){
					//var ids=[];
					//var ecodes=[];
					var repairs=[];
					for(var i=0;i<records.length;i++){
						repairs.push({
							ecode:records[i].get("ecode"),
							id:records[i].get("id"),
							rpa_id:rpa_id,
							str_out_id:records[i].get("str_out_id")
						});
						//ids.push(records[i].get("id"));
						//ecodes.push(records[i].get("ecode"));
					}
					Ext.getBody().mask("正在执行,请稍候.....");
					Ext.Ajax.request({
						url:Ext.ContextPath+'/repair/repairOutStore.do',
						method:'POST',
						timeout:600000000,
						//headers:{ 'Content-Type':'application/json;charset=UTF-8'},
						//params:{ids:ids,ecodes:ecodes,rpa_id:rpa_id},
						jsonData:repairs,
						//params:{jsonStr:Ext.encode(equiplist)},
						success:function(response){
							var obj=Ext.decode(response.responseText);		
							//Ext.Msg.alert("消息","维修中心入库完成!");
							Ext.getBody().unmask();
							me.getStore().reload();
						},
						failure:function(){
							Ext.getBody().unmask();
						}
					});
				}
			}
	  });
	  me.tbar={
		xtype: 'container',
		layout: 'anchor',
		defaults: {anchor: '0'},
		defaultType: 'toolbar',
		items: [{
			items: [store_combox,repair_combox,str_out_date_start,str_out_date_end,status_combo,query_button] // toolbar 1
		}, {
			items: [ecode_textfield,str_in_button,str_out_button] // toolbar 2
		}]
	}	

       
	
	  me.on('itemdblclick',me.recordDbclick);
      me.callParent();
	},
	recordDbclick:function(view, record){
		
		var form=Ext.create('Ems.repair.RepairForm',{
			listeners:{
				saved:function(){
					win.close();				
				}
			}
		});
		
		//record.set('rpa_user_id',loginUsername);
		form.loadRecord(record);
		var win=Ext.create('Ext.window.Window',{
			title:'编辑维修单',
			layout:'fit',
			width:500,
			items:[form]
		});
		win.show();
		
	},
	scrapEquipment:function(repair){//报废设备
		var form=Ext.create('Ems.repair.ScrapForm',{
								
		});
		var scrap=Ext.create('Ext.repair,Scrap',{
			repair_id:repair.get("id"),
			ecode:repair.get("ecode")
		});
		form.loadRecord(scrap);
		var win=Ext.create('Ext.window.Window',{
			title:'生成报废单',
			layout:'fit',
			width:500,
			items:[form]
		});
		win.show();					
	}
});
