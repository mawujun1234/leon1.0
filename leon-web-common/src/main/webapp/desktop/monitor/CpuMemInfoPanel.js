/**
 * CPU和内存的实时图
 */
Ext.define('Leon.desktop.monitor.CpuMemInfoPanel',{
	extend:'Ext.panel.Panel',
	title:'概要',
	border:false, 
	//layout:'border',
	layout: {
        type: 'vbox',
        align: 'center'
    },
   
	frame:true,
	initComponent:function(){
		var me=this;
			
		var cpuChart=me.getCpuChart();
		var memChart=me.getMemChart();
		
		me.items=[cpuChart,memChart];

		me.callParent();
	},
	getStore:function(){
		var me=this;
		if(me.store){
			return me.store;
		}
		me.store= Ext.create('Ext.data.Store', {
			 	xtype:'store',
			 	fields:['time','cpu_combined','cpu_userTime','cpu_sysTime'],
			 	data: [
			    ]
			 });
	return me.store
	},
	addData:function(model){
		var me=this;
		
		if(me.getStore().getCount( )>=60){
			me.store.removeAt(0);
		}
		me.store.add(model);
		console.log(model);
		console.log(me.getStore().getCount( ));
	},
	getCpuChart:function(){
		var me=this;
		var chart=Ext.create('Ext.chart.Chart',{
			 region:'center',
			 flex: 1,
			 animate: true,
			 width:900,
			 store:me.getStore(),
			 axes: [
		        {
		            title: 'CPU百分比',grid: true,
		            type: 'Numeric',
		            position: 'left',
		            fields: ['cpu_combined','cpu_userTime','cpu_sysTime'],
		            minimum: 0,
		            maximum: 100
		        },
		        {
		            title: '时间',grid: true,
		            type: 'Time',
		            position: 'bottom',
		            fields: ['time'],
		            dateFormat: 'H:i:s'
		            //constrain: true,
    				//fromDate: new Date('1/1/11'),
    				//toDate: new Date('1/7/11')
		        }
		    ],
		    series: [
		        {
		        	axis: 'left',
		        	smooth: true,
		            type: 'line',
		            xField: 'time',
		            yField: 'cpu_combined'
		        }
		    ]
		});
		return chart;
	},
	getMemChart:function(){
		var chart=Ext.create('Ext.chart.Chart',{
			 region:'center',
			 flex: 1,
			  width:900,
			 store:{
			 	xtype:'bstore',
			 	fields:['time','temperature'],
			 	url:Ext.ContextPath+"/monitorSystem/querySystemInfo"
			 },
			 axes: [
		        {
		            title: 'Temperature',
		            type: 'Numeric',
		            position: 'left',
		            fields: ['temperature'],
		            minimum: 0,
		            maximum: 100
		        },
		        {
		            title: 'Time',
		            type: 'Time',
		            position: 'bottom',
		            fields: ['time'],
		            minimum: 0,
		            maximum: 60
		        }
		    ],
		    series: [
		        {
		            type: 'line',
		            xField: 'time',
		            yField: 'temperature'
		        }
		    ]
		});
		return chart;
	}
});