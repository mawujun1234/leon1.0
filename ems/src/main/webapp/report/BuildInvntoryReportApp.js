//Ext.require("Ems.store.Order");
//Ext.require("Ems.store.OrderGrid");
//Ext.require("Ems.store.OrderTree");
//Ext.require("Ems.store.OrderForm");
Ext.onReady(function(){
//
//	var year_combox=Ext.create('Ext.form.field.ComboBox',{
//	        fieldLabel: '年',
//	        labelAlign:'right',
//            labelWidth:40,
//            width:120,
//	        //xtype:'combobox',
//	        //afterLabelTextTpl: Ext.required,
//	        name: 'month',
//		    displayField: 'name',
//		    valueField: 'id',
//	        allowBlank: false,
//	        value:(new Date()).getFullYear(),
//	        store:Ext.create('Ext.data.Store', {
//		    	fields: ['id', 'name'],
//				data:[{id:"2014",name:"2014"},{id:"2015",name:"2015"},{id:"2016",name:"2016"},{id:"2017",name:"2017"},{id:"2018",name:"2018"},{id:"2019",name:"2019"},{id:"2020",name:"2020"},{id:"2021",name:"2021"}]
//		   })
//	});	
//	var month_combox=Ext.create('Ext.form.field.ComboBox',{
//	        fieldLabel: '月份',
//	        labelAlign:'right',
//            labelWidth:40,
//            width:120,
//	        //xtype:'combobox',
//	        //afterLabelTextTpl: Ext.required,
//	        name: 'month',
//		    displayField: 'name',
//		    valueField: 'id',
//	        allowBlank: false,
//	        value:((new Date()).getMonth()+1)+"",
//	        store:Ext.create('Ext.data.Store', {
//		    	fields: ['id', 'name'],
//				data:[{id:"01",name:"1月"},{id:"02",name:"2月"},{id:"03",name:"3月"},{id:"04",name:"4月"},{id:"05",name:"5月"},{id:"06",name:"6月"}
//				,{id:"07",name:"7月"},{id:"08",name:"8月"},{id:"09",name:"9月"},{id:"10",name:"10月"},{id:"11",name:"11月"},{id:"12",name:"12月"}]
//		   })
//	});	
	
	var date_start=Ext.create('Ext.form.field.Date',{
	  	fieldLabel: '开始时间',
	  	labelWidth:50,
	  	width:170,
	  	format:'Y-m-d',
	  	minValue:Ext.Date.parse('2015-07-10','Y-m-d')//从7.10号开始有数据
        //value:  Ext.Date.add(new Date(), Ext.Date.DAY, -7)
	  });
	var date_end=Ext.create('Ext.form.field.Date',{
	  	fieldLabel: '到',
	  	format:'Y-m-d',
	  	labelWidth:15,
	  	labelWidth:15,
	  	minValue:Ext.Date.parse('2015-07-10','Y-m-d'),
	  	maxValue:new Date(),
        value: new Date()
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
	        allowBlank: true,
	        store:Ext.create('Ext.data.Store', {
		    	fields: ['id', 'name'],
			    proxy:{
			    	type:'ajax',
			    	extraParams:{type:1,edit:true},
			    	url:Ext.ContextPath+"/store/queryCombo.do",
			    	reader:{
			    		type:'json',
			    		root:'root'
			    	}
			    }
		   })
	});	
	var tbar1=Ext.create('Ext.toolbar.Toolbar',{
		items:[date_start,date_end,store_combox,{
			text:'计算当天结余',
			icon:'../icons/atm.png',
			handler:function(){
				if(!store_combox.getValue()){
					alert("没有指定仓库，将会对所有在建仓库进行计算");
				}
				Ext.Msg.confirm("消息","将计算今天截至当前为止的库存情况!",function(btn){
					if(btn=='yes'){
						Ext.getBody().mask("正在执行...");
						Ext.Ajax.request({
							url:Ext.ContextPath+"/inventory/day/proc_report_day_sparepart.do",
							params:{store_id:store_combox.getValue(),store_type:1},
							success:function(response){
								alert("计算成功!");
								Ext.getBody().unmask();
							}
						});
					}
				})
				
			}
		},{
			text:'导出月报表',
			icon:'../icons/page_excel.png',
			handler:function(){
				var params=getParams();
				if(!params){
					return false;
				}
				params.store_type=1;
				var pp=Ext.Object.toQueryString(params);
				window.open(Ext.ContextPath+"/inventory/month/sparepart/excelExport.do?"+pp, "_blank");
			}
		},{
			text:'导出日报表',
			icon:'../icons/page_excel.png',
			handler:function(){
				var params=getParams();
				if(!params){
					return false;
				}
				params.store_type=1;
				var pp=Ext.Object.toQueryString(params);
				window.open(Ext.ContextPath+"/inventory/day/sparepart/excelExport.do?"+pp, "_blank");
			}
		}
		
		]
	})
	
	var tbar2=Ext.create('Ext.toolbar.Toolbar',{
		items:[
		{
			text:'导出月报表模板',
			icon:'../icons/page_excel.png',
			handler:function(){
				window.open(Ext.ContextPath+"/inventory/month/build/excelTpl.do", "_blank");
			}
		},
		{
			text:'导出日报表模板',
			icon:'../icons/page_excel.png',
			handler:function(){
				window.open(Ext.ContextPath+"/inventory/day/build/excelTpl.do", "_blank");
			}
		}
		]
	})
	
	var panel=Ext.create('Ext.panel.Panel',{
		tbar:{
		  xtype: 'container',
		  layout: 'anchor',
		  defaults: {anchor: '0'},
		  defaultType: 'toolbar',
		  items: [tbar1, tbar2]
		}
	});
	
	function getParams(){
		var params={
			date_start:date_start.getRawValue(),
			date_end:date_end.getRawValue(),
			store_id:store_combox.getValue()
		}
		if(!params.date_start){
			Ext.Msg.alert("提醒","请先选择开始日期!");
			return false;
		}
		if(!params.date_end){
			Ext.Msg.alert("提醒","请先选择结束日期!");
			return false;
		}
//		if(!params.store_id){
//			Ext.Msg.alert("提醒","请先选择仓库!");
//			return false;
//		}
		return params;
	}

	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'fit',
		items:[panel]
	});

});