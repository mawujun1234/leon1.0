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
    	autoLoad:true
    });
    
    var estatus_box=Ext.create('Ext.form.ComboBox',{
    	fieldLabel: '设备状态',
        store: estatusStore,
        queryMode: 'local',
        displayField: 'text',
        valueField: 'value',
        labelWidth:55,
        margin:'0 0 0 5px',
        multiSelect:true
    });
    
    var startdt_field=Ext.create('Ext.form.field.Date',{
    	fieldLabel: 'Start Date',
        name: 'startdt',
        id: 'startdt',
        format: 'Y-m-d',
        value:new Date(),
        maxValue: new Date()
    });
    var enddt_field=Ext.create('Ext.form.field.Date',{
        fieldLabel: 'End Date',
        name: 'enddt',
        id: 'enddt',
        format: 'Y-m-d',
        value: Ext.Date.add(new Date(), Ext.Date.DAY, 1),
        maxValue: Ext.Date.add(new Date(), Ext.Date.DAY, 1)
    });
    
	Ext.define('impinfo', {
		extend : 'Ext.data.Model',
		fields : [{name : 'STime',mapping : 'STime.time',type:'date',dateFormat : 'time'}, 
		          {name : 'typename',mapping : 'typename'}, 
		          {name : 'memo',mapping : 'memo'},
		          {name : 'reason',mapping : 'reason'},
		          {name : 'price',mapping : 'price'},
		          {name : 'aucname',mapping : 'aucname'},
		          {name : 'rucname',mapping : 'rucname'},
		          {name : 'ducname',mapping : 'ducname'},
		          {name : 'status',mapping : 'status'}]
	});
	
	var impStore = Ext.create('Ext.data.Store', {
	    //分页大小
	    pageSize: 12,
	    model: 'impinfo',
	    proxy: {
	        type: 'ajax',
	        url: 'getRepairPage.do',
	        reader: {
	            root: 'list',
	            totalProperty: 'totalcount'
	        }
	    },
	    remoteSort: true,
	    sorters: [{
	        property: 'ITIME',//排序字段
	        direction: 'DESC' // 默认ASC
	    }]
	})
	
	impStore.on('beforeload',function() {
	    Ext.apply(impStore.proxy.extraParams, {
	    	stids:stock_box.getValue(),etypes:etype_box.getValue(),estatuses:estatus_box.getValue(),
	    	startdt:startdt,enddt:enddt
	    });
	});
	
	var imp_grid = Ext.create('Ext.grid.Panel', {
	    store: impStore,
	    border: false,
	    sortableColumns: false,
	    columns: [Ext.create('Ext.grid.RowNumberer', {text: '序号',width: 35}),
	              {header: '报修时间',dataIndex: 'STime',renderer : Ext.util.Format.dateRenderer("Y-m-d"),sortable: true},
	              {header: '设备类型',dataIndex: 'typename'},
	              {header: '鼓掌描述',dataIndex: 'memo',flex: 1},
	              {header: '鼓掌原因',dataIndex: 'reason'},
	              {header: '零部件价格(元)',dataIndex: 'price'},
	              {header: '送修人',dataIndex: 'aucname'},
	              {header: '维修人',dataIndex: 'rucname'},
	              {header: '状态',dataIndex: 'status',renderer:statusRender}],
	    viewConfig: {
	 	    	  loadMask : {msg : '正在加载数据，请稍等...' },
	 	    	  stripeRows: true  
	 	},
	    dockedItems: [{
	        xtype: 'pagingtoolbar',
	        store: impStore,
	        // same store extract_grid is using
	        dock: 'bottom',
	        //分页 位置
	        emptyMsg: '没有数据',
	        displayInfo: true,
	        displayMsg: '当前显示{0}-{1}条记录 / 共{2}条记录 ',
	        beforePageText: '第',
	        afterPageText: '页/共{0}页'
	    }]
	});
	
	Ext.create('Ext.container.Viewport',{
        layout: {
            type:'vbox',
            padding:'5',
            align:'stretch'
        },
        defaults:{border:false},
        items:[{xtype:'form',layout:'column',margin:'2px 5px 0 5px',defaults:{columnWidth:1/4,border:false},items:[stock_box,etype_box,estatus_box,{layout:{type:'hbox',pack:'center'},items:{xtype:'button',text:'查询',width:100,handler:doquery}}]},
               {html:'<HR style="FILTER: alpha(opacity=100,finishopacity=0,style=3)" width="100%" color=#987cb9 SIZE=3>'},
               {flex:1,tbar:['<pan id="toolbar-title-text">设备出库列表：</span>','->',startdt_field,enddt_field],layout:'fit',items:imp_grid}]
	});
	
	function doquery(){
		startdt=Ext.Date.format(startdt_field.getValue(),'Y-m-d');
		enddt=Ext.Date.format(enddt_field.getValue(),'Y-m-d');
		if(!Ext.isEmpty(startdt)&&!Ext.isEmpty(enddt)){
			if(startdt_field.isValid()&&enddt_field.isValid()){
				if(startdt_field.getValue()<enddt_field.getValue()){
					impStore.load();
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