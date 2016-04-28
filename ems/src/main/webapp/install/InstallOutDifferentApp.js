Ext.require('Ems.install.InstallOutDifferent');
Ext.onReady(function(){
	//查询这个时间范围内所有的领出去的设备，当点击的时候，显示这个设备所属的单据，包括借用单和领用单
	var store_combox=Ext.create('Ext.form.field.ComboBox',{
	        fieldLabel: '仓库',
	        labelAlign:'right',
            labelWidth:30,
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
	  var workUnit_combox=Ext.create('Ext.form.field.ComboBox',{
	        fieldLabel: '作业单位',
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
	  
	   var returnDate_start=Ext.create('Ext.form.field.Date',{
	  	fieldLabel: '领用时间',
	  	labelWidth:60,
	  	width:155,
	  	format:'Y-m-d',
        //name: 'date_start',
        value:  Ext.Date.add(new Date(), Ext.Date.DAY, -7)
	  });
	  var returnDate_end=Ext.create('Ext.form.field.Date',{
	  	fieldLabel: '到',
	  	format:'Y-m-d',
	  	labelWidth:15,
	  	labelWidth:15,
        //name: 'date_end',
        value: new Date()
	  });
	  
	   var operateDate_start=Ext.create('Ext.form.field.Date',{
		  	fieldLabel: '出库时间',
		  	labelWidth:60,
		  	width:155,
		  	format:'Y-m-d'
	        //name: 'date_start',
	        //value:  Ext.Date.add(new Date(), Ext.Date.DAY, -7)
		  });
		  var operateDate_end=Ext.create('Ext.form.field.Date',{
		  	fieldLabel: '到',
		  	format:'Y-m-d',
		  	labelWidth:15,
		  	labelWidth:15
	        //name: 'date_end',
	        //value: new Date()
		  });
	  
//	  var project_combox=Ext.create('Ems.baseinfo.ProjectCombo',{
//		flex:1,
//		allowBlank: false
//	  });
	var type_combox=Ext.create('Ems.baseinfo.TypeCombo',{
		labelAlign:'right',
		allowBlank: false,
		labelWidth:50,
		//minChars:-1
		listeners:{
			change:function(field,newValue, oldValue){
				subtype_combox.clearValue( );
				if(newValue){
					subtype_combox.getStore().getProxy().extraParams={equipmentType_id:newValue};
					subtype_combox.getStore().reload();
				}
				
			}
		}
	});
	
	var subtype_combox=Ext.create('Ems.baseinfo.SubtypeCombo',{
		labelAlign:'right',
		allowBlank: false,
		labelWidth:50,
		//minChars:-1，
		listeners:{
			beforeload:function(store){
				if(type_combox.getValue()){
					return true;
				} 
				Ext.Msg.alert("消息","请先选择大类!");
				return false;
			},
			select:function( combo, records, eOpts){
				//当小类变化后，品名要清空
				prod_id.setValue(""); 
				prod_name.setValue(""); 
				brand_id.setValue(""); 
				brand_name.setValue(""); 
				style.setValue(""); 
				prod_spec.setValue(""); 
				prod_unit.setValue("");
			}
		}
	});
	  
	  
	  
	  
	var store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			model: 'Ems.install.InstallOutDifferent',
			autoLoad:false,
			proxy:{
				type:'ajax',
				actionMethods:{read:'POST'},
				url:Ext.ContextPath+'/installOut/queryDifference.do',
				reader:{
					type:'json',
					root:'root'
				}
			}
	    });
	var grid=Ext.create('Ext.grid.Panel',{
		columnLines :true,
		stripeRows:true,
		columns:[
			//{dataIndex:'id',text:'id'},
	      	{xtype:'rownumberer'},
			//{dataIndex:'installIn_id',text:'installIn_id'},
			//{dataIndex:'isBad',text:'isBad'},
			{dataIndex:'ecode',text:'条码',width:140},
			{dataIndex:'installOut_id',text:'单号'},
			{dataIndex:'type',text:'借/领用单'},
			{header: '领用类型', dataIndex: 'installOutType_name'},
			{header: '领用类型二级', dataIndex: 'installOutType_content'},
			{dataIndex:'operateDate',text:'出库时间'},
			{dataIndex:'returnDate',text:'领用时间'},
			//{dataIndex:'installIn_id',text:'installIn_id'},
			//{dataIndex:'isBad',text:'isBad'}
			{dataIndex:'subtype_name',text:'小类'},
			{dataIndex:'prod_name',text:'品名'},
			{dataIndex:'brand_name',text:'品牌'},
			//{dataIndex:'supplier_name',text:'供应商'},
			{dataIndex:'prod_style',text:'型号'},
			{header:'规格',dataIndex:'prod_spec',flex:1,renderer:function(value,metadata,record){
							metadata.tdAttr = "data-qtip='" + value+ "'";
						    return value;
							}
			}
	      ],
	    store:store,
	    dockedItems: [{
	        xtype: 'pagingtoolbar',
	        store: store,  
	        dock: 'bottom',
	        displayInfo: true
	    },{
	    	xtype: 'toolbar',
	    	 dock: 'top',
	    	 items:[store_combox,workUnit_combox,returnDate_start,returnDate_end]
	      },{
	      	xtype: 'toolbar',
	    	 dock: 'top',
	    	 items:[operateDate_start,operateDate_end,type_combox,subtype_combox,{
		    	text: '查询',
				iconCls:'form-search-button',
				handler: function(btn){
					//me.store.reload();
					store.loadPage(1);
				}
		    }]
	    	}
	    ]
	});
	
	store.on("beforeload",function(store){
		store.getProxy().extraParams={
					store_id:store_combox.getValue(),
					workUnit_id:workUnit_combox.getValue(),
					//project_id:project_combox.getValue( ),
					type_id:type_combox.getValue(),
					subtype_id:subtype_combox.getValue(),
					//installOutType_id:installOutType_combox.getValue(),
					returnDate_start: returnDate_start.getRawValue(),
					returnDate_end: returnDate_end.getRawValue(),
					operateDate_start: operateDate_start.getRawValue(),
					operateDate_end: operateDate_end.getRawValue()
				  };
	 });
	
	
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'fit',
		items:[grid]
	});

});