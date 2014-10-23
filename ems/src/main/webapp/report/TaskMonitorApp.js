//Ext.require("Ems.store.Order");
//Ext.require("Ems.store.OrderGrid");
//Ext.require("Ems.store.OrderTree");
//Ext.require("Ems.store.OrderForm");
Ext.onReady(function(){
	var queryType=Ext.create('Ext.form.RadioGroup',{
		fieldLabel: '查询方式',
		labelWidth:60,
		width:180,
		items: [
            { boxLabel: '按月', name: 'queryType', inputValue: 'month', checked: true },
            { boxLabel: '自定义', name: 'queryType', inputValue: 'customer'}
        ],
        listeners:{
        	change:function(field, newValue, oldValue){
        		if(newValue.queryType=="month"){
        			month_combox.show();
        			date_start.hide();
        			date_end.hide();
        		} else{
        			month_combox.hide();
        			date_start.show();
        			date_end.show();
        		}
        	}
        }
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
	        store:Ext.create('Ext.data.Store', {
		    	fields: ['id', 'name'],
				data:[{id:"0",name:"1月"},{id:"1",name:"2月"},{id:"2",name:"3月"},{id:"3",name:"4月"},{id:"4",name:"5月"},{id:"5",name:"6月"},{id:"6",name:"7月"},{id:"7",name:"8月"}
				,{id:"8",name:"9月"},{id:"9",name:"10月"},{id:"10",name:"11月"},{id:"11",name:"12月"}]
		   })
	});	
	
	var date_start=Ext.create('Ext.form.field.Date',{
	  	fieldLabel: '开始时间',
	  	labelWidth:60,
	  	hidden:true,
	  	//editable:false,
	  	format:'Y-m-d'
        //name: 'str_out_date_start',
        //value:  Ext.Date.add(new Date(), Ext.Date.DAY, -7)
	});
	var date_end=Ext.create('Ext.form.field.Date',{
	  	fieldLabel: '到',
	  	hidden:true,
	  	format:'Y-m-d',
	  	//editable:false,
	  	labelWidth:15
        //name: 'str_out_date_end',
        //value: new Date()
	});
	
	
	
	var tbar=Ext.create('Ext.toolbar.Toolbar',{
		items:[queryType,month_combox,date_start,date_end,{
			text:'查询',
			handler:function(){
				
				store.reload();
			}
		}]
	})
	
	var store=Ext.create('Ext.data.Store',{
		autoLoad:false,
		fields: ['id', 'name','in_storage','out_storage','using','breakdown','wait_for_repair','to_repair','outside_repairing','inner_repairing','out_repair','in_transit','scrapped'],
		proxy:{
			type:'ajax',
			extraParams:{type:[1,3],look:true},
			url:Ext.ContextPath+"/propertystatus/query.do",
			reader:{
			    type:'json',
			    root:'root'
			}
		}
	});
	store.on("beforeload",function(store){
		store.getProxy().extraParams={store_id:month_combox.getValue()};
	});
	var grid=Ext.create('Ext.grid.Panel',{
		store:store,
		tbar:tbar,
		columns:[
			{ text: '小类',  dataIndex: 'name' },
			{ text: '已入库',  dataIndex: 'in_storage' },
			{ text: '入库待维修',  dataIndex: 'wait_for_repair' },
			{ text: '发往维修中心',  dataIndex: 'to_repair' },
			{ text: '外修中',  dataIndex: 'outside_repairing' },
			{ text: '维修中',  dataIndex: 'inner_repairing' },
			{ text: '维修后已出库',  dataIndex: 'out_repair' }
		]
	});
	
	
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'fit',
		items:[grid]
	});

});