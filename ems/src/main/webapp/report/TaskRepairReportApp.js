Ext.require("Ems.report.TaskRepairReport");
Ext.onReady(function(){

	var date_start=Ext.create('Ext.form.field.Date',{
	  	fieldLabel: '开始时间',
	  	labelWidth:60,
	  	hidden:false,
	  	//editable:false,
	  	format:'Y-m-d',
        //name: 'str_out_date_start',
	  	editable:false,
        value:  Ext.Date.add(new Date(), Ext.Date.DAY, -7)
	});
	var date_end=Ext.create('Ext.form.field.Date',{
	  	fieldLabel: '到',
	  	hidden:false,
	  	format:'Y-m-d',
	  	//editable:false,
	  	labelWidth:15,
	  	editable:false,
        //name: 'str_out_date_end',
        value: new Date()
	});
	var pole_code=Ext.create('Ext.form.field.Text',{
		fieldLabel: '点位号',
		labelWidth:60,
		name: 'pole_code'
	})
	
	var hitchType_id=Ext.create('Ext.form.field.ComboBox',{
		fieldLabel: '故障类型',
		labelWidth:60,
	    queryMode: 'remote',
	    displayField: 'name',
	    valueField: 'id',
	    store:Ext.create('Ext.data.Store',{
	    	fields: ['id', 'name'],
	    	proxy:{
	    		url:Ext.ContextPath+'/hitchType/query.do',
	    		type:'ajax',
	    		reader:{
		    		type:'json',
		    		root:'root'
		    	}
	    	}
	    })
	});
	
	var query_button=Ext.create('Ext.button.Button',{
			text:'查询',
			margin:'0 0 0 5',
			iconCls:'form-search-button',
			handler:function(){
				//store.load();
				store.loadPage(1);
			}
	});
	var exportPoles = new Ext.Action({
		    text: '导出',
		    //itemId:'reload',
		    icon:'../icons/page_excel.png',
		    handler: function(){
		    	var me=this;
		    	var params=getParams();
				var pp=Ext.Object.toQueryString(params);
				window.open(Ext.ContextPath+"/report/taskrepair/exportRepairReport.do?"+pp, "_blank");
		    }
		});
	var toolbar=Ext.create('Ext.toolbar.Toolbar',{
		items:[date_start,date_end,pole_code,hitchType_id,query_button,exportPoles]
	});
	
	function getParams(){
		var params={
		    date_start:date_start.getRawValue(),
		    date_end:date_end.getRawValue(),
		    pole_code:pole_code.getValue(),
		    hitchType_id:hitchType_id.getValue()
		}
		return params;
	}
	var store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			model: 'Ems.report.TaskRepairReport',
			autoLoad:false,
			proxy:{
				type:'ajax',
				actionMethods: {
			        create : 'POST',
			        read   : 'POST',
			        update : 'POST',
			        destroy: 'POST'
			    },
				url:Ext.ContextPath+'/report/taskrepair/queryRepairReport.do',
				reader:{
					type:'json',
					root:'root'
				}
			}
	});
	store.on("beforeload",function(store){
		store.getProxy().extraParams=getParams();
	});
			
	var grid=Ext.create('Ext.grid.Panel',{
		columnLines :true,
		stripeRows:true,
		columns:[
			{xtype: 'rownumberer'},
			{dataIndex:'customer_name',text:'客户名称'},
			{dataIndex:'pole_code',text:'点位编号',width:60},
			{dataIndex:'pole_name',text:'点位名称'},
			{dataIndex:'workunit_name',text:''},
			{dataIndex:'memo',text:'故障现象'},
			{dataIndex:'hitchDate',text:'故障时间',width:130},
			{dataIndex:'createDate',text:'任务下发时间',width:130},
			{dataIndex:'startHandDate',text:'开始处理时间',width:130},
			{dataIndex:'submitDate',text:'提交时间',width:130},
			{dataIndex:'completeDate',text:'完成时间',width:130},
			{dataIndex:'usedTime',text:'总耗时'},
			{dataIndex:'repairUsedTime',text:'维修耗时'},
			{dataIndex:'result',text:'维修结果'},
			{dataIndex:'overtime',text:'超时',width:60},
			{dataIndex:'hitchType',text:'故障类型'},
			{dataIndex:'hitchReason',text:'故障原因'},
			{dataIndex:'handleMethod_name',text:'处理方法'},
			{dataIndex:'handle_contact',text:'备注'}
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