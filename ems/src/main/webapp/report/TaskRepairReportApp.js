Ext.require("Ems.report.RepairReport");
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
	  	labelWidth:15
	  	editable:false,
        //name: 'str_out_date_end',
        ,value: new Date()
	});
	var pole_code=Ext.create('Ext.form.field.Text',{
		fieldLabel: '点位号',
		name: 'pole_code'
	})
	
	var hitchType_id=Ext.create('Ext.form.field.Combobox',{
		fieldLabel: '故障类型',
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
				window.open(Ext.ContextPath+"/repare/taskrepair/exportRepairReport.do?"+pp, "_blank");
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
			model: 'Ems.report.RepairReport',
			autoLoad:false,
			proxy:{
				type:'ajax',
				actionMethods: {
			        create : 'POST',
			        read   : 'POST',
			        update : 'POST',
			        destroy: 'POST'
			    },
				url:Ext.ContextPath+'/repare/taskrepair/queryRepairReport.do',
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
			{dataIndex:'str_out_name',text:'出库仓库',width:130},
			{dataIndex:'brand_name',text:'品牌',width:120},
			{dataIndex:'prod_style',text:'型号'},
			{dataIndex:'subtype_name',text:'类型',width:150},
			{dataIndex:'str_out_date',text:'送修时间',width:140},
			{dataIndex:'repair_take_time',text:'维修时间',width:140},
			{dataIndex:'broken_reson',text:'故障原因',width:140},
			{dataIndex:'handler_method',text:'解决方案'},
			{dataIndex:'status_name',text:'维修结果'},
			{dataIndex:'send_date',text:'返厂时间'},
			{dataIndex:'receive_date',text:'返回时间',width:120},
			{dataIndex:'str_in_date',text:'入库时间',width:120}
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