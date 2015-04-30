Ext.require("Ems.repair.RepairReport");
Ext.onReady(function(){

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
				store.load();
			}
	});
	var exportPoles = new Ext.Action({
		    text: '导出',
		    //itemId:'reload',
		    icon:'../icons/page_excel.png',
		    handler: function(){
		    	var me=this;
		    	var params={
		    		date_start:date_start.getRawValue(),
		    		date_end:date_end.getRawValue()
		    	}
				var pp=Ext.Object.toQueryString(params);
				window.open(Ext.ContextPath+"/repair/exportRepairReport.do?"+pp, "_blank");
		    }
		});
	var toolbar=Ext.create('Ext.toolbar.Toolbar',{
		items:[date_start,date_end,query_button,exportPoles]
	});
	var store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			model: 'Ems.repair.RepairReport',
			autoLoad:false,
			proxy:{
				type:'ajax',
				actionMethods: {
			        create : 'POST',
			        read   : 'POST',
			        update : 'POST',
			        destroy: 'POST'
			    },
				url:Ext.ContextPath+'/repair/queryRepairReport.do',
				reader:{
					type:'json',
					root:'root'
				}
			}
	});
	store.on("beforeload",function(store){
		store.getProxy().extraParams={
				date_start:date_start.getRawValue(),
		    	date_end:date_end.getRawValue()
		};
	});
	var grid=Ext.create('Ext.grid.Panel',{
		columnLines :true,
		stripeRows:true,
		columns:[
			{xtype: 'rownumberer'},
			{dataIndex:'id',text:'维修单号',width:130},
			{dataIndex:'rpa_in_date',text:'坏件领料时间',width:120},
			{dataIndex:'rpa_user_name',text:'维修人员'},
			{dataIndex:'ecode',text:'条码',width:150},
			{dataIndex:'prod_name',text:'品名',width:140},
			{dataIndex:'equipment_style',text:'型号',width:140},
			{dataIndex:'broken_memo',text:'故障现象'},
			{dataIndex:'broken_reson',text:'处理方法'},
			{dataIndex:'rpa_out_date',text:'修复时间',width:120},
			{dataIndex:'str_in_date',text:'好件还库时间',width:120}
			//{dataIndex:'',text:'信息反馈及备注'}
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