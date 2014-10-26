//Ext.require("Ems.store.Order");
//Ext.require("Ems.store.OrderGrid");
//Ext.require("Ems.store.OrderTree");
//Ext.require("Ems.store.OrderForm");
Ext.onReady(function(){

	var year_combox=Ext.create('Ext.form.field.ComboBox',{
	        fieldLabel: '年',
	        labelAlign:'right',
            labelWidth:40,
            width:120,
	        //xtype:'combobox',
	        //afterLabelTextTpl: Ext.required,
	        name: 'month',
		    displayField: 'name',
		    valueField: 'id',
	        allowBlank: false,
	        value:(new Date()).getFullYear(),
	        store:Ext.create('Ext.data.Store', {
		    	fields: ['id', 'name'],
				data:[{id:"2014",name:"2014"},{id:"2015",name:"2015"},{id:"2016",name:"2016"},{id:"2017",name:"2017"},{id:"2018",name:"2018"},{id:"2019",name:"2019"},{id:"2020",name:"2020"},{id:"2021",name:"2021"}]
		   })
	});	
	var month_combox=Ext.create('Ext.form.field.ComboBox',{
	        fieldLabel: '月份',
	        labelAlign:'right',
            labelWidth:40,
            width:120,
	        //xtype:'combobox',
	        //afterLabelTextTpl: Ext.required,
	        name: 'month',
		    displayField: 'name',
		    valueField: 'id',
	        allowBlank: false,
	        value:((new Date()).getMonth()+1)+"",
	        store:Ext.create('Ext.data.Store', {
		    	fields: ['id', 'name'],
				data:[{id:"01",name:"1月"},{id:"02",name:"2月"},{id:"03",name:"3月"},{id:"04",name:"4月"},{id:"05",name:"5月"},{id:"06",name:"6月"}
				,{id:"07",name:"7月"},{id:"08",name:"8月"},{id:"09",name:"9月"},{id:"10",name:"10月"},{id:"11",name:"11月"},{id:"12",name:"12月"}]
		   })
	});	
	
	var store_combox=Ext.create('Ext.form.field.ComboBox',{
	        fieldLabel: '在建仓库',
	        labelAlign:'right',
            labelWidth:60,
	        //xtype:'combobox',
	        //afterLabelTextTpl: Ext.required,
	        name: 'store_id',
		    displayField: 'name',
		    valueField: 'id',
	        allowBlank: false,
	        store:Ext.create('Ext.data.Store', {
		    	fields: ['id', 'name'],
			    proxy:{
			    	type:'ajax',
			    	extraParams:{type:3,look:true},
			    	url:Ext.ContextPath+"/store/queryCombo.do",
			    	reader:{
			    		type:'json',
			    		root:'root'
			    	}
			    }
		   })
	});	
	
	
	var tbar=Ext.create('Ext.toolbar.Toolbar',{
		items:[year_combox,month_combox,store_combox,{
			text:'查询',
			handler:function(){
				
				store.reload();
			}
		}]
	})
	
	var store=Ext.create('Ext.data.Store',{
		autoLoad:false,
		fields: ['month', 'subtype_id','subtype_name','prod_id','prod_name','brand_id','brand_name','style','store_id','store_name','unit'
			,'lastnum','nownum','storeinnum','installoutnum','memo'],
		proxy:{
			type:'ajax',
			actions:{
				"read":'POST'
			},
			url:Ext.ContextPath+"/builddayreport/query.do",
			reader:{
			    type:'json',
			    root:'root'
			}
		},
		groupField: 'subtype_name'
	});
	store.on("beforeload",function(store){
		var params={
			year:year_combox.getValue(),
			month:month_combox.getValue(),
			store_id:store_combox.getValue()
		}
		if(!params.year){
			Exg.Msg.alert("提醒","请先选择年份!");
			return;
		}
		if(!params.month){
			Exg.Msg.alert("提醒","请先选择月份!");
			return;
		}
		if(!params.store_id){
			Exg.Msg.alert("提醒","请先选择仓库!");
			return;
		}
		store.getProxy().extraParams=params;
	});
	var cellEditing = Ext.create('Ext.grid.plugin.CellEditing', {
        clicksToEdit: 1
    });
	var grid=Ext.create('Ext.grid.Panel',{
		store:store,
		tbar:tbar,
		plugins: [cellEditing],
		features: [{
            id: 'group',
            ftype: 'groupingsummary',
            groupHeaderTpl: '小类:{name}',
            hideGroupedHeader: true,
            enableGroupingMenu: false
        }],
		columns:[
			{
            header: '小类',
            width: 180,
            sortable: true,
            dataIndex: 'subtype_name'
        	},{
            header: '品牌',
            sortable: true,
            dataIndex: 'brand_name',
            summaryRenderer: function(value, summaryData, dataIndex) {
                return "&nbsp;&nbsp;&nbsp;<b>汇总:</b>";
            }
        	},{
            header: '型号',
            sortable: true,
            dataIndex: 'style'
        	},{
            header: '品名',
            sortable: true,
            dataIndex: 'prod_name'
        	},{
            header: '所属仓库',
            sortable: true,
            dataIndex: 'store_name'
        	},{
            header: '单位',
            sortable: true,
            dataIndex: 'unit'
        	},{
            header: '上月结余数',
            sortable: true,
            dataIndex: 'lastnum',
            summaryType: 'sum'
        	},{
            header: '本月新增',
            sortable: true,
            dataIndex: 'storeinnum',
            summaryType: 'sum'
        	},{
            header: '本月领用',
            sortable: true,
            dataIndex: 'installoutnum',
            summaryType: 'sum'
        	},{
            header: '本月结余',
            sortable: true,
            dataIndex: 'nownum',
            summaryType: 'sum'
            
        	},{
            header: '备注',
            sortable: true,
            flex:1,
            dataIndex: 'memo',
            field: {
                xtype: 'textfield'
            }
        	}
        	
		]
	});
	
	grid.on('edit',function(editor, e){
		var record=e.record;
		var params=record.getData();
		delete params.id;
		Ext.Ajax.request({
			url:Ext.ContextPath+'/builddayreport/updateMemo.do',
			method:'POST',
			params:params,
			success:function(response){
				e.record.commit();
			}
		});
		//
	});
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'fit',
		items:[grid]
	});

});