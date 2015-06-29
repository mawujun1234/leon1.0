Ext.require("Ems.task.TaskRepairReport");
Ext.onReady(function(){
	var workunit_combox=Ext.create('Ext.form.field.ComboBox',{
	        fieldLabel: '作业单位',
	        labelAlign:'right',
            labelWidth:55,
            //width:250,
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
	  	format:'Y-m-d'
        //name: 'str_out_date_start',
        //value:  Ext.Date.add(new Date(), Ext.Date.DAY, -7)
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
	var exportPoles = new Ext.Action({
		    text: '导出',
		    //itemId:'reload',
		    icon:'../icons/page_excel.png',
		    handler: function(){
		    	var me=this;
		    	var params={
		    		workunit_id:workunit_combox.getValue(),
					date_start:date_start.getRawValue(),
					date_end:date_end.getRawValue()
		    	}
				var pp=Ext.Object.toQueryString(params);
				window.open(Ext.ContextPath+"/task/exportRepairTaskesReport.do?"+pp, "_blank");
		    }
		});
	var toolbar=Ext.create('Ext.toolbar.Toolbar',{
		items:[workunit_combox,date_start,date_end,query_button,exportPoles]
	});
	var store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			model: 'Ems.task.TaskRepairReport',
			autoLoad:false,
			proxy:{
				type:'ajax',
				actionMethods: {
			        create : 'POST',
			        read   : 'POST',
			        update : 'POST',
			        destroy: 'POST'
			    },
				url:Ext.ContextPath+'/task/queryRepairTaskesReport.do',
				reader:{
					type:'json',
					root:'root'
				}
			}
	});
	store.on("beforeload",function(store){
		if(!workunit_combox.getValue()){
			alert("请选择作业单位!");
			return;
		}
		if(!date_start.getValue()){
			alert("请选择开始时间!");
			return;
		}
		store.getProxy().extraParams={
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
	        {dataIndex:'id',text:'任务编号',width:100},
	        {dataIndex:'customer_name',text:'派出所'},
	        {dataIndex:'pole_code',text:'点位编号',width:55},
	        {dataIndex:'pole_name',text:'点位名称'},
	        {dataIndex:'workunit_name',text:'作业单位'},
	        {dataIndex:'hitchReason',text:'故障现象'},
	        {dataIndex:'createDate',text:'任务下派时间',width:120},
			{dataIndex:'startHandDate',text:'维修到达时间',width:120},
			{dataIndex:'submitDate',text:'提交时间',width:120},
			{dataIndex:'finishTime',text:'总耗时'},
			//{dataIndex:'',text:'结果'},
			{dataIndex:'repairTime',text:'修复耗时'},
			{dataIndex:'isOverTime',text:'是否超时',renderer:function(v){
				
				if(v=="true"){
					return "超时";
				} else {
					return "否";
				}
				//return (v==true)?"超时":"否";				
			}},
			{dataIndex:'hitchType',text:'故障类型'}
			//{dataIndex:'',text:'故障处理方法'},
			//{dataIndex:'',text:'备注'}
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