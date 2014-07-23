Ext.onReady(function(){
	Ext.tip.QuickTipManager.init();
	var startdt,enddt;
//    Ext.apply(Ext.form.field.VTypes, {
//        daterange: function(val, field) {
//            var date = field.parseDate(val);
//
//            if (!date) {
//                return false;
//            }
//            if (field.startDateField && (!this.dateRangeMax || (date.getTime() != this.dateRangeMax.getTime()))) {
//                var start = field.up('toolbar').down('#' + field.startDateField);
//                start.setMaxValue(date);
//                start.validate();
//                this.dateRangeMax = date;
//            }
//            else if (field.endDateField && (!this.dateRangeMin || (date.getTime() != this.dateRangeMin.getTime()))) {
//                var end = field.up('toolbar').down('#' + field.endDateField);
//                end.setMinValue(date);
//                end.validate();
//                this.dateRangeMin = date;
//            }
//            /*
//             * Always return true since we're only using this vtype to set the
//             * min/max allowed values (these are tested for after the vtype test)
//             */
//            return true;
//        },
//
//        daterangeText: 'Start date must be less than end date',
//    });
	
    var stockStore = Ext.create('Ext.data.ArrayStore', {
	    fields: [{name: 'text', type: 'string'},
	    	     {name: 'value', type: 'int'}],
        proxy: {
            type: 'ajax',
            url: 'stockList',
            reader: {
                type: 'json'
            }
        },
    	autoLoad:true
    });
    
    var stock_box=Ext.create('Ext.form.ComboBox',{
    	fieldLabel: '库房',
        store: stockStore,
        queryMode: 'local',
        displayField: 'text',
        valueField: 'value',
        labelWidth:55,
        margin:'0px',
        multiSelect:true
    });
	
    
    var etypeStore = Ext.create('Ext.data.ArrayStore', {
	    fields: [{name: 'text', type: 'string'},
	    	     {name: 'value', type: 'int'}],
        proxy: {
            type: 'ajax',
            url: 'etypeList',
            reader: {
                type: 'json'
            }
        },
    	autoLoad:true
    });
    
    var etype_box=Ext.create('Ext.form.ComboBox',{
    	fieldLabel: '设备类型',
        store: etypeStore,
        queryMode: 'local',
        displayField: 'text',
        valueField: 'value',
        labelWidth:55,
        margin:'0 0 0 5px',
        multiSelect:true
    });
    
    var startdt_field=Ext.create('Ext.ux.form.MonthField',{
    	fieldLabel: 'Start Date',
        name: 'startdt',
        id: 'startdt',
        format: 'Y-m',
        value:new Date(),
        maxValue: new Date()
    });
    var enddt_field=Ext.create('Ext.ux.form.MonthField',{
        fieldLabel: 'End Date',
        name: 'enddt',
        id: 'enddt',
        format: 'Y-m',
        value: Ext.Date.add(new Date(), Ext.Date.DAY, 1),
        maxValue: Ext.Date.add(new Date(), Ext.Date.DAY, 1)
    });
    
	Ext.define('statinfo', {
		extend : 'Ext.data.Model',
		fields : [{name : 'desc',mapping : 'desc'}, 
		          {name : 'typename',mapping : 'typename'},
		          {name : 'subtypename',mapping : 'subtypename'},
		          {name : 'amount',mapping : 'amount'},
		          {name : 'totalPrice',mapping : 'totalPrice'}]
	});
	
	var statStore = Ext.create('Ext.data.ArrayStore', {
	    model: 'statinfo',
	    autoDestroy: true,
	    groupField: 'desc'
	});
	
	var groupingFeature = Ext.create('Ext.grid.feature.GroupingSummary',{
        groupHeaderTpl: '月份: {name} ({rows.length} Item{[values.rows.length > 1 ? "s" : ""]})'
	});
	
	var stat_grid = Ext.create('Ext.grid.Panel', {
		region:'west',
		width:320,
		split:true,
	    store: statStore,
	    border: false,
	    sortableColumns: false,
	    features: [groupingFeature],
	      columns: [{
	    	  width:90,
	          text     : '设备类型',
	          sortable : false,
	          dataIndex: 'typename',
	          summaryType: 'count',
	          summaryRenderer: function(value, summaryData, dataIndex) {
	              return ((value === 0 || value > 1) ? '(' + value + ' Items)' : '(1 Item)');
	          }
	      },{
	    	  flex:1,
	          text     : '设备子类型',
	          sortable : false,
	          dataIndex: 'subtypename'
	      }, {
	          text: '统计',
	          columns: [{
	              text     : '数量',
	              sortable : true,
	              dataIndex: 'amount',
	              summaryType: 'sum',
	              width:70,
	              summaryRenderer: function(value, summaryData, dataIndex) {
	            	  return value;
	              }
	          },{
	              text     : '总价',
	              sortable : true,
	              renderer : 'usMoney',
	              dataIndex: 'totalPrice',
	              width:70,
	              summaryType: 'sum',
	              summaryRenderer: function(value, summaryData, dataIndex) {
	            	  return '$'+Ext.util.Format.number( value,'0.00');
	              }
	          }]
	      }],
	    viewConfig: {
	 	    	  loadMask : {msg : '正在加载数据，请稍等...' },
	 	    	  stripeRows: true  
	 	}
	});
	
	var chartStore= Ext.create('Ext.data.JsonStore', {
        fields: ['month', '次数','价格'],
        data:[{desc:'12',Number:12,Price:33}]
    });
	
	var groupbarchart=Ext.create('Ext.chart.Chart',{
		region:'center',
        id: 'chartCmp',
        xtype: 'chart',
        style: 'background:#fff',
        animate: true,
        shadow: true,
        store: chartStore,
        legend: {
          position: 'right'  
        },
        axes: [{
            type: 'Numeric',
            position: 'bottom',
            fields: ['次数', '价格'],
            minimum: 0,
            label: {
                renderer: Ext.util.Format.numberRenderer('0,0')
            },
            grid: true,
            title: '数量'
        }, {
            type: 'Category',
            position: 'left',
            fields: ['desc'],
            title: '月份'
        }],
        series: [{
            type: 'bar',
            axis: 'bottom',
            xField: 'name',
            yField: ['次数','价格']
        }]
    });
	
	Ext.create('Ext.container.Viewport',{
        layout: {
            type:'vbox',
            padding:'5',
            align:'stretch'
        },
        defaults:{border:false},
        items:[{xtype:'form',layout:'column',margin:'2px 5px 0 5px',defaults:{columnWidth:1/4,border:false},items:[stock_box,etype_box,{layout:{type:'hbox',pack:'center'},items:{xtype:'button',text:'统计',width:100,handler:doquery}}]},
               {html:'<HR style="FILTER: alpha(opacity=100,finishopacity=0,style=3)" width="100%" color=#987cb9 SIZE=3>'},
               {flex:1,tbar:['<pan id="toolbar-title-text">设备维修月统计：</span>','->',startdt_field,enddt_field],
            	layout:'border',items:[stat_grid,groupbarchart]}]
	});
	
	function doquery(){
		startdt=Ext.Date.format(startdt_field.getValue(),'Y-m');
		enddt=Ext.Date.format(enddt_field.getValue(),'Y-m');
		if(!Ext.isEmpty(startdt)&&!Ext.isEmpty(enddt)){
			if(startdt_field.isValid()&&enddt_field.isValid()){
				if(startdt_field.getValue()<enddt_field.getValue()){
					Ext.Ajax.request({     
						url:'getRepairMonthStat',  
						params:{  
							stids:stock_box.getValue(),
							etypes:etype_box.getValue(),
					    	startdt:startdt,
					    	enddt:enddt
						},  
						success: function(resp,opts) {   
							var ret= Ext.decode(resp.responseText);
							if(ret.success){
								var list=ret.list;
								statStore.loadData(list);
								var chartData=new Array();
								for(var i in list){
									var data=list[i];
									var obj={month:'','次数':0,'价格':0}
									obj.month=data.desc;
									obj.次数=data.amount;
									obj.价格=data.totalPrice;
									chartData.push(obj);
								}
								chartStore.loadData(chartData);
							}
						},   
						failure: function(resp,opts) {   
							var respText = Ext.decode(resp.responseText);   
							Ext.Msg.alert('错误', respText.error); 
						}
					});
				}else{
					Ext.Msg.alert('提示','起始日期不得小于结束日期');
				}
			}
		}else{
			Ext.Msg.alert('提示','请输入时间区间');
		}
	}
   	
    var estatusStore = Ext.create('Ext.data.ArrayStore', {
	    fields: [{name: 'text', type: 'string'},
	    	     {name: 'value', type: 'int'}],
        proxy: {
            type: 'ajax',
            url: 'estatusList',
            reader: {
                type: 'json'
            }
        },
        autoDestroy: false,
    	autoLoad:true
    });
    
	function statusRender(v){
		var diplayTxt="";
		estatusStore.each(function(record) {
	        var value = record.get('value');
	        if (v === value) {
	            diplayTxt = record.get('text');
	            return false;
	        }
	    });
		return diplayTxt;
	}
});