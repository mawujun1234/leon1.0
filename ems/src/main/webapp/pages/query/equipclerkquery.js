Ext.onReady(function(){
	Ext.tip.QuickTipManager.init();
	var startdt,enddt;
	var rucid;
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
	
	Ext.define('clerk',{
		extend:'Ext.data.Model',
		fields:[
			{name:'value',mapping:'value'},
			{name:'text',mapping:'text'}]
		});
	
	var clerkStore = Ext.create('Ext.data.JsonStore', {
        model:'clerk',
        proxy: {
            type: 'ajax',
            url: 'clerkList.do'
        },
        listeners:{
        	load:function(s,records,success,o,e){
        		if(success&&records.length>0){
        			clerks_grid.getSelectionModel( ).select(0);
        		}
        	}
        }
    });
	
	var clerks_grid=Ext.create('Ext.grid.Panel',{
	    title:'操作员工',
	    region:'west',
	    width:180,
	    split:true,
	    store:clerkStore,
	    columns: [
	         {xtype: 'rownumberer'},
	   		 {header:'value',dataIndex:'value',hidden:true,hideable:false},
	   		 {header:'姓名',dataIndex:'text',flex:1}
		],
		listeners:{
			select:function(row,r,i,e){
				rucid=r.get('value');
			}
		}
	});
	
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
    
	Ext.define('expinfo', {
		extend : 'Ext.data.Model',
		fields : [{name : 'RUcid',mapping : 'RUcid'}, 
		          {name : 'eid',mapping : 'eid'}, 
		          {name : 'esubtype',mapping : 'esubtype'}, 
		          {name : 'etime',mapping : 'etime.time',type:'date',dateFormat : 'time'},
		          {name : 'etype',mapping : 'etype'}, 
		          {name : 'memo',mapping : 'memo'},
		          {name : 'nums',mapping : 'nums'}, 
		          {name : 'numsNew',mapping : 'numsNew'},
		          {name : 'numsStock',mapping : 'numsStock'},
		          {name : 'rucname',mapping : 'rucname'},
		          {name : 'stid',mapping : 'stid'},
		          {name : 'stock',mapping : 'stock'},
		          {name : 'style',mapping : 'style'},
		          {name : 'subtypename',mapping : 'subtypename'},
		          {name : 'typename',mapping : 'typename'},
		          {name : 'ucid',mapping : 'ucid'},
		          {name : 'ucname',mapping : 'ucname'},
		          {name : 'userlist',mapping : 'userlist'},
		          {name : 'expstockLists',mapping : 'expstockLists'}]
	});
	
	var expStore = Ext.create('Ext.data.Store', {
	    //分页大小
	    pageSize: 12,
	    model: 'expinfo',
	    proxy: {
	        type: 'ajax',
	        url: 'getEquipTypeClerkStat.do',
	        reader: {
	            root: 'list',
	            totalProperty: 'totalcount'
	        }
	    },
	    remoteSort: true,
	    sorters: [{
	        property: 'ETIME',//排序字段
	        direction: 'DESC' // 默认ASC
	    }]
	})
	
	expStore.on('beforeload',function() {
	    Ext.apply(expStore.proxy.extraParams, {
	    	stids:stock_box.getValue(),etypes:etype_box.getValue(),
	    	startdt:startdt,enddt:enddt,
	    	rucid:rucid
	    });
	});
	
	var exp_grid = Ext.create('Ext.grid.Panel', {
	    store: expStore,
	    border: false,
	    sortableColumns: false,
	    columns: [Ext.create('Ext.grid.RowNumberer', {text: '序号',width: 35}), 
	              {header: '出库日期',dataIndex: 'etime',renderer : Ext.util.Format.dateRenderer("Y-m-d"),sortable: true},
	              {header: '申请人',dataIndex: 'rucname'},
	              {header: '设备类型',dataIndex: 'typename'},
	              {header: '设备子类型',dataIndex: 'subtypename'},
	              {header: '摘要',dataIndex: 'memo',flex: 1},
	              {header: '数量',dataIndex: 'nums'},
	              {header: '库存量',dataIndex: 'numsStock'},
	              {header: '库房名称',dataIndex: 'stock'},
	              {header: '操作远',dataIndex: 'ucname'}],
	    viewConfig: {
	 	    	  loadMask : {msg : '正在加载数据，请稍等...' },
	 	    	  stripeRows: true  
	 	},
	    dockedItems: [{
	        xtype: 'pagingtoolbar',
	        store: expStore,
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
		layout:'border',
        padding:'5px',
        style:'background-color:#FFFFFF',
		items:[clerks_grid,{
			region:'center',
	        layout: {
	            type:'vbox',
	            padding:'5',
	            align:'stretch'
	        },
	        defaults:{border:false},
	        items:[{xtype:'form',layout:'column',margin:'2px 5px 0 5px',defaults:{columnWidth:1/3,border:false},items:[stock_box,etype_box,{layout:{type:'hbox',pack:'center'},items:{xtype:'button',text:'查询',width:100,handler:doquery}}]},
	               {html:'<HR style="FILTER: alpha(opacity=100,finishopacity=0,style=3)" width="100%" color=#987cb9 SIZE=3>'},
	               {flex:1,tbar:['<pan id="toolbar-title-text">设备出库列表：</span>','->',startdt_field,enddt_field],layout:'fit',items:exp_grid}]
		}]
	});
	
	var equipStore = Ext.create('Ext.data.ArrayStore', {
	    autoDestroy: true,
		fields : [{name : 'ecode',mapping : 'ecode'}, 
		          {name : 'estatus',mapping : 'estatus'}, 
		          {name : 'typename',mapping : 'typename'},
		          {name : 'etype',mapping : 'etype'},
		          {name : 'esubtype',mapping : 'esubtype'},
		          {name : 'subtypename',mapping : 'subtypename'}]
	});
	
	var equip_list=Ext.create('Ext.grid.Panel', {
	    store: equipStore,
	    border: false,
	    sortableColumns: false,
	    columns: [Ext.create('Ext.grid.RowNumberer', {text: '序号',width: 35}),
	              {header: '条形码',dataIndex: 'ecode'},
	              {header: '设备类型',dataIndex: 'typename'},
	              {header: '设备子类型',dataIndex: 'subtypename'},
	              {header: '状态',dataIndex: 'estatus',flex:1,renderer:statusRender}]
	});
	
	var list_win;
   	var contextMenu = new Ext.menu.Menu({
		id : 'equip-list',
		setActionRecord:function(record){
			this.actionRecord=record;
		},
		items : [{
					text : '查看设备详情',
					iconCls : 'icon-list',
					handler : function(btn) {
						if(!list_win){
							list_win=Ext.create('Ext.window.Window',{
								title:'设备列表',
								layout:'fit',
								height:300,
								width:400,
								closeAction:'hide',
								border:false,
								mode:true,
								items:equip_list,
								buttons:[{text:'关闭',handler:function(){
									this.up('window').hide();
								}}]
							});
						}
						var record=contextMenu.actionRecord;
						if(!!record){
							var typename=record.get('typename');
							var subtypename=record.get('subtypename');
							var equipList=record.get('expstockLists');
							var objArray=new Array();
							if(!!equipList){
								for(var i in equipList){
									equipList[i].typename=typename;
									equipList[i].subtypename=subtypename;
									objArray.push(equipList[i]);
								}
								equipStore.loadData(equipList);
								list_win.show();
							}else{
								Ext.Msg.alert('提示','无相关设备信息');
							}
						}
					}
				}]
	})
   	
   	exp_grid.on("itemcontextmenu", function(v,record,item,i,e,o) {
			e.preventDefault();
			contextMenu.setActionRecord(record);
			contextMenu.showAt(e.getXY());
	})
	
	function doquery(){
		startdt=Ext.Date.format(startdt_field.getValue(),'Y-m-d');
		enddt=Ext.Date.format(enddt_field.getValue(),'Y-m-d');
		if(!Ext.isEmpty(startdt)&&!Ext.isEmpty(enddt)){
			if(startdt_field.isValid()&&enddt_field.isValid()){
				if(startdt_field.getValue()<enddt_field.getValue()){
					expStore.load();
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
	
	clerkStore.load();
});