Ext.require("Ems.report.RepeatRepairReport");
Ext.onReady(function(){

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
	
	var repeatnum=Ext.create('Ext.form.field.Number',{
	  	fieldLabel: '重复维修次数',
        //minValue:2,
        value:2
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
		    	var params={
		    		date_start:date_start.getRawValue(),
		    		date_end:date_end.getRawValue(),
		    		repeatnum:repeatnum.getValue()
		    	}
				var pp=Ext.Object.toQueryString(params);
				window.open(Ext.ContextPath+"/report/repair/exportRepeatRepairReport.do?"+pp, "_blank");
		    }
		});
	var toolbar=Ext.create('Ext.toolbar.Toolbar',{
		items:[date_start,date_end,repeatnum,query_button,exportPoles]
	});
	var store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			model: 'Ems.report.RepeatRepairReport',
			autoLoad:false,
			proxy:{
				type:'ajax',
				actionMethods: {
			        create : 'POST',
			        read   : 'POST',
			        update : 'POST',
			        destroy: 'POST'
			    },
				url:Ext.ContextPath+'/report/repair/queryRepeatRepairReport.do',
				reader:{
					type:'json',
					root:'root'
				}
			}
	});
	store.on("beforeload",function(store){
		store.getProxy().extraParams={
				date_start:date_start.getRawValue(),
		    	date_end:date_end.getRawValue(),
		    	repeatnum:repeatnum.getValue()
		};
	});
	var grid=Ext.create('Ext.grid.Panel',{
		columnLines :true,
		stripeRows:true,
		columns:[
			{xtype: 'rownumberer'},
			{dataIndex:'ecode',text:'设备编码',width:140},
			{dataIndex:'repeate_count',text:'重复次数'},
			{dataIndex:'prod_name',text:'设备名称'},
			{dataIndex:'str_out_name',text:'出库仓库'},
			{dataIndex:'rpa_in_date',text:'维修入库',width:140},
			{dataIndex:'rpa_user_name',text:'维修人员',width:150},	
			{dataIndex:'rpa_out_date',text:'维修出库',width:140},
			{dataIndex:'str_in_date',text:'入库日期',width:140}
	    ],
      	store:store,
      	tbar:toolbar
	  	 
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