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
	        value:(new Date()).getMonth()+"",
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
        ,value: new Date()
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
		fields: ['id', 'name','pole_nums','currt_new','currt_complete','currt_avgcompletetime','currt_submited','currt_avgsubmitedtime','total_nums'
		,'total_complete','total_complete_rate','total_avgcompletetime','total_submited','total_avgsubmitedtime'],
		proxy:{
			type:'ajax',
			actions:{
				"read":'POST'
			},
			url:Ext.ContextPath+"/taskmonitor/repair.do",
			reader:{
			    type:'json',
			    root:'root'
			}
		}
	});
	store.on("beforeload",function(store){
		store.getProxy().extraParams={
			queryType:queryType.getValue(),
			month:month_combox.getValue(),
			date_start:date_start.getRawValue(),
			date_end:date_end.getRawValue()
		};
	});
	var grid=Ext.create('Ext.grid.Panel',{
		store:store,
		tbar:tbar,
		columns:[
			{ text: '作业单位',  dataIndex: 'name' },
			{ text: '点位数',  dataIndex: 'pole_nums' },
			{ text: '本期新建任务',  dataIndex: 'currt_new' },
			{ text: '本期完成任务',  dataIndex: 'currt_complete' },
			{ text: '本期完成平均时间',  dataIndex: 'currt_avgcompletetime' },
			{ text: '本期提交&完成任务数',  dataIndex: 'currt_submited' },
			{ text: '本期提交&完成平均时间',  dataIndex: 'currt_avgsubmitedtime' },
			{ text: '累计任务数',  dataIndex: 'total_nums' },
			{ text: '累计的完成数',  dataIndex: 'total_complete' },
			{ text: '累计完成率',  dataIndex: 'total_complete_rate' },
			{ text: '累计平均完成时间 ',  dataIndex: 'total_avgcompletetime' },
			{ text: '累计提交&完成任务数',  dataIndex: 'total_submited' },
			{ text: '累计平均提交时间',  dataIndex: 'total_avgsubmitedtime' }
		]
	});
	
	
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'fit',
		items:[grid]
	});

});