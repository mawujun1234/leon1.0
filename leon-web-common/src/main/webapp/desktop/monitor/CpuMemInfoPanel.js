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
	getCpuChart:function(){
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