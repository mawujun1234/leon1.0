Ext.define('Leon.desktop.monitor.CpuInfoPanel',{
	extend:'Ext.panel.Panel',
	title:'概要',
	border:false, 
	layout:'border',
	//frame:true,
	initComponent:function(){
		var me=this;
		
		var form=Ext.create('Ext.form.Panel',{
			region:'north',
			height:100
		});
		
		var chart=Ext.create('Ext.chart.Chart',{
			 region:'center',
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
		me.items=[form,chart];

		me.callParent();
	}
});