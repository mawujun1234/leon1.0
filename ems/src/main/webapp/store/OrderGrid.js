Ext.define('Ems.store.OrderGrid',{
	extend:'Ext.grid.Panel',
	requires: [
	     'Ems.store.Order',
	     'Ems.store.OrderForm'
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
	initComponent: function () {
      var me = this;
      me.columns=[
      	{xtype: 'rownumberer'},
		{dataIndex:'orderNo',text:'订单号',width:150},
		{dataIndex:'store_name',text:'入库仓库',flex:1},
		{dataIndex: 'status_name',text: '状态',width:70},
		{dataIndex: 'project_name',text: '项目',width:70},
		{header: '供应商', dataIndex: 'supplier_name'},
		{dataIndex:'orderDate',text:'订购日期',xtype: 'datecolumn',   format:'Y-m-d',width:80}
    	//{dataIndex: 'operater',text: '经办人'}
      ];
      
	  me.store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			model: 'Ems.store.Order',
			autoLoad:false,
			proxy:{
				type:'ajax',
				actionMethods:{read:'POST'},
				url:Ext.ContextPath+'/order/queryMain.do',
				reader:{
					type:'json',
					root:'root'
				}
			}
	  });
	  
      me.dockedItems= [{
	        xtype: 'pagingtoolbar',
	        store: me.store,  
	        dock: 'bottom',
	        displayInfo: true
	  }];
	  
	  var store_combox=Ext.create('Ext.form.field.ComboBox',{
	        fieldLabel: '入库仓库',
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
			    	extraParams:{type:[1,3],look:true},
			    	url:Ext.ContextPath+"/store/queryCombo.do",
			    	reader:{
			    		type:'json',
			    		root:'root'
			    	}
			    }
		   })
	  }); 
	  
//	  var project_id=Ext.create('Ext.form.field.Hidden',{
//		labelAlign:'right',
//		labelWidth:40,
//		fieldLabel: '项目',
//		name:'project_id',
//		readOnly:true,
//		//emptyText:"不可编辑",
//		allowBlank:false
//	});
//	var project_name=Ext.create('Ext.form.field.Text',{
//		labelAlign:'right',
//		labelWidth:40,
//		fieldLabel: '项目',
//		name:'project_name',
//		readOnly:true,
//		emptyText:"不可编辑",
//		allowBlank:false
//	});
//	var project_button=Ext.create('Ext.button.Button',{
//		text:'选择项目',
//		margin:'0 0 0 5',
//		handler:function(){
//			var projectGrid=Ext.create('Ems.baseinfo.ProjectQueryGrid',{
//				listeners:{
//					itemdblclick:function(view,record,item){
//						project_id.setValue(record.get("id"));
//						project_name.setValue(record.get("name"));
//						win.close();
//					}
//				}
//			});
//			var win=Ext.create('Ext.window.Window',{
//				title:'双击选择项目',
//				items:[projectGrid],
//				layout:'fit',
//				modal:true,
//				width:700,
//				height:300
//			});
//			win.show();
//		}
//	});
	
	var project_combox=Ext.create('Ext.form.field.ComboBox',{
	        fieldLabel: '项目',
	        labelAlign:'right',
            labelWidth:40,
            flex:1,
	        //xtype:'combobox',
	        //afterLabelTextTpl: Ext.required,
	        name: 'project_id',
		    displayField: 'name',
		    valueField: 'id',
		    queryParam: 'name',
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
		    	fields: ['id', 'name'],
			    proxy:{
			    	type:'ajax',
			    	//extraParams:{type:[1,3],look:true},
			    	actionMethods: {
				        create : 'POST',
				        read   : 'POST',
				        update : 'POST',
				        destroy: 'POST'
				    },
			    	url:Ext.ContextPath+"/project/query.do",
			    	reader:{
			    		type:'json',
			    		root:'root'
			    	}
			    }
		   })
	});	
	
	  var supplier_combox=Ext.create('Ems.baseinfo.SupplierCombo',{
		labelAlign:'right',
		labelWidth:40,
		flex:1,
		allowBlank: true
	});
	  
	  var date_start=Ext.create('Ext.form.field.Date',{
	  	fieldLabel: '订购日期',
	  	labelWidth:60,
	  	width:160,
	  	format:'Y-m-d'
        //name: 'date_start',
        //value:  Ext.Date.add(new Date(), Ext.Date.DAY, -7)
	  });
	  var date_end=Ext.create('Ext.form.field.Date',{
	  	fieldLabel: '到',
	  	format:'Y-m-d',
	  	labelWidth:15,
	  	width:115,
        //name: 'date_end',
        value: new Date()
	  });
	  
	 var order_no=Ext.create('Ext.form.field.Text',{
		fieldLabel:'订单号',
		name:'orderNo',
		labelWidth:40,
		allowBlank:true,
		labelAlign:'right'
	});
	  
	me.store.on("beforeload",function(store){
		store.getProxy().extraParams={
					store_id:store_combox.getValue(),
					date_start: date_start.getRawValue(),
					date_end: date_end.getRawValue(),
					orderNo:order_no.getValue(),
					project_id:project_combox.getValue(),
					supplier_id:supplier_combox.getValue()
				  };
	});
	  me.tbar={
		xtype: 'container',
		layout: 'anchor',
		defaults: {anchor: '0'},
		defaultType: 'toolbar',
		items: [{
			items: [store_combox,date_start,date_end] // toolbar 1
		},{
			items: [] // toolbar 1
		},{
			items: [supplier_combox] // toolbar 1
		}, {
			items: [project_combox,order_no,{
			text: '查询',
			iconCls:'form-search-button',
			handler: function(btn){
				me.store.reload();
			}
		  }] 
		},{
			items:[{
			text: '确认',
			icon:'../icons/cog.png',
			//iconCls:'form-search-button',
			handler: function(btn){
				Ext.Msg.confirm("提醒","确认后,该订单将不可再修改!",function(btn){
					if(btn=='yes'){
						var record=me.getSelectionModel().getLastSelected();
						if(!record){
						alert("请先选择一个订单!");
						return;
						}
						Ext.Ajax.request({
							url:Ext.ContextPath+'/order/editover.do',
							method:'POST',
							params:{id:record.get("id")},
							success:function(resposne){
								me.store.reload();
							}
						});
					
					}
				});
			}
		  },{
			text: '修改',
			iconCls: 'form-update-button',
			icon:'../icons/action_delete.gif',
			handler: function(btn){
				var record=me.getSelectionModel().getLastSelected();
				if(record.get("status")=='editover'){
					alert("状态是'已确认',不能修改！");
					return;
				}
				me.updateOrder(record);
				
			}
		  },{
			text: '删除',
			iconCls: 'form-delete-button',
			handler: function(btn){
				Ext.Msg.confirm("提醒","确认要删除吗?",function(btn){
					if(btn=='yes'){
						var record=me.getSelectionModel().getLastSelected();
						if(record.get("status")=='editover'){
							alert("状态是'已确认',不能删除！");
							return;
						}
						Ext.Ajax.request({
							url:Ext.ContextPath+'/order/delete.do',
							method:'POST',
							params:{id:record.get("id")},
							success:function(resposne){
								me.store.reload();
							}
						});
					
					}
				});
			}
		  }]
		}]
	   };
	   me.store.load();


       
      me.callParent();
	},
	updateOrder:function(order){
		var me=this;
		if(order.get("status")=="editover"){
			alert("订单已确认，不能修改!");
       		return;
		}
		//var documentWidth=Ext.getBody().getWidth();
		
		//console.log(record.get("unitPrice"));
		var form=Ext.create('Ems.store.OrderForm',{
			//order_id:order_id,
			//order_id:order_id,
			url:Ext.ContextPath+'/order/update.do',
			listeners:{
				saved:function(){
					win.close();
					me.getStore().reload();
					me.getSelectionModel().deselect(order);
				}
			}	
		});
		form.loadRecord(order);
		

		form.getForm().findField("store_id").getStore().load();
		var project_combox=form.getForm().findField("project_id");
		var project_model= project_combox.getStore().createModel({id:order.get("project_id"),name:order.get("project_name")});
		project_combox.setValue(project_model);
		
		var supplier_combox=form.getForm().findField("supplier_id");
		var supplier_model = supplier_combox.getStore().createModel({
			id : order.get("supplier_id"),
			name : order.get("supplier_name")
		});
		supplier_combox.setValue(supplier_model);
		
		
		var win=Ext.create('Ext.window.Window',{
			modal:true,
			//width:documentWidth-100,
			width:350,
			title:"更新",
			closeAction:"destroy",
			items:[form]
		});
		win.show();
	}
});
