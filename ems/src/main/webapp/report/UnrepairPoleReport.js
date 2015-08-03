Ext.require("Ems.task.Task");
Ext.onReady(function(){
	var customer_combox=Ext.create('Ext.form.field.ComboBox',{
	        fieldLabel: '客户名称',
	        labelAlign:'right',
            labelWidth:55,
            //width:250,
	        //xtype:'combobox',
	        //afterLabelTextTpl: Ext.required,
	        name: 'customer_id',
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
	        //allowBlank: false,
	        store:Ext.create('Ext.data.Store', {
		    	fields: ['id', 'name'],
			    proxy:{
			    	type:'ajax',
			    	//extraParams:{type:1,edit:true},
			    	url:Ext.ContextPath+"/customer/queryCombo.do",
			    	reader:{
			    		type:'json',
			    		root:'root'
			    	}
			    }
		   })
	    });
	    
	var workunit_combox=Ext.create('Ext.form.field.ComboBox',{
	        fieldLabel: '作业单位',
	        labelAlign:'right',
            labelWidth:55,
            width:250,
	        //xtype:'combobox',
	        //afterLabelTextTpl: Ext.required,
	        name: 'workunit_id',
		    displayField: 'name',
		    valueField: 'id',
	        allowBlank: false,
	        store:Ext.create('Ext.data.Store', {
		    	fields: ['id', 'name'],
			    proxy:{
			    	type:'ajax',
			    	//extraParams:{type:1,edit:true},
			    	url:Ext.ContextPath+"/workUnit/queryCombo.do",
			    	reader:{
			    		type:'json',
			    		root:'root'
			    	}
			    }
		   })
	});
	var date_start=Ext.create('Ext.form.field.Date',{
	  	fieldLabel: '开始时间',
	  	labelWidth:60,
	  	hidden:false,
	  	//editable:false,
	  	format:'Y-m-d',
        //name: 'str_out_date_start',
        value:  Ext.Date.add(new Date(), Ext.Date.DAY, -7)
	});
	var date_end=Ext.create('Ext.form.field.Date',{
	  	fieldLabel: '到',
	  	hidden:false,
	  	format:'Y-m-d',
	  	//editable:false,
	  	labelWidth:15
        //name: 'str_out_date_end',
        ,value: new Date()
	});
	
	var query_button=Ext.create('Ext.button.Button',{
			text:'查询',
			margin:'0 0 0 5',
			iconCls:'form-search-button',
			handler:function(){
				store.loadPage(1);
			}
	});
	var exportPoles = Ext.create('Ext.button.Button',{
		    text: '导出',
		    //itemId:'reload',
		    icon:'../icons/page_excel.png',
		    handler: function(){
		    	var me=this;
		    	var params={
		    		customer_id:customer_combox.getValue(),
		    		workunit_id:workunit_combox.getValue(),
		    		date_start:date_start.getRawValue(),
		    		date_end:date_end.getRawValue()
		    	}
				var pp=Ext.Object.toQueryString(params);
				window.open(Ext.ContextPath+"/report/task/exportUnrepairPoleReport.do?"+pp, "_blank");
		    }
		});
	var toolbar=Ext.create('Ext.toolbar.Toolbar',{
		items:[workunit_combox,customer_combox,date_start,date_end,query_button,exportPoles]
	});
	
//	var grid=Ext.create('Ext.panel.Panel',{
//		tbar:toolbar
//	});
	var store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			model: 'Ems.task.Task',
			autoLoad:false,
			proxy:{
				type:'ajax',
				actionMethods: {
			        create : 'POST',
			        read   : 'POST',
			        update : 'POST',
			        destroy: 'POST'
			    },
				url:Ext.ContextPath+'/report/task/queryUnrepairPoleReport.do',
				reader:{
					type:'json',
					root:'root'
				}
			}
	});
	store.on("beforeload",function(store){
		store.getProxy().extraParams={
				customer_id:customer_combox.getValue(),
		    	workunit_id:workunit_combox.getValue(),
		    	date_start:date_start.getRawValue(),
		    	date_end:date_end.getRawValue()
		};
	});
	var grid=Ext.create('Ext.grid.Panel',{
		columnLines :true,
		stripeRows:true,
		columns:[
			{xtype: 'rownumberer'},
			
	        {dataIndex:'customer_name',text:'派出所'},
	        {dataIndex:'pole_code',text:'点位编号',width:55},
	        {dataIndex:'pole_name',text:'点位名称'},
	        {dataIndex:'workunit_name',text:'作业单位'},
	        {dataIndex:'id',text:'任务编号',width:100},
	        {dataIndex:'memo',text:'任务描述'},
	        {dataIndex:'createDate',text:'任务下派时间',width:120},
			{dataIndex:'startHandDate',text:'维修到达时间',width:120},
			{dataIndex:'hitchType',text:'故障类型'},
			{dataIndex:'hitchReason',text:'故障原因'}
			//{dataIndex:'',text:'维修未完成原因'},
			//{dataIndex:'',text:'故障后续处理方法'}
	    ],
      	store:store,
      	tbar:toolbar,
      	dockedItems: [{
	        xtype: 'pagingtoolbar',
	        store: store,  
	        dock: 'bottom',
	        displayInfo: true
	  	}]
	  	 
	});
	



	
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'fit',
		items:[grid],
		listeners:{
			render:function(){
				
			}
		}
	});

});