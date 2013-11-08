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
		me.currentDate=new Date();//这个是
		me.store= Ext.create('Ext.data.Store', {
			 	xtype:'store',
			 	fields:['time','cpu_combined','mem_used'],
			 	data: [
			 		{time:Ext.Date.format(Ext.Date.add(me.currentDate,Ext.Date.SECOND,-10),"H:i:s"),cpu_combined:0,mem_used:0},//
			 		{time:Ext.Date.format(Ext.Date.add(me.currentDate,Ext.Date.SECOND,-5),"H:i:s"),cpu_combined:0,mem_used:0}		 		
			    ]
			 });
		return me.store
	},
	addData:function(model){
		var me=this;
		me.currentDate=Ext.Date.add(me.currentDate,Ext.Date.SECOND,5);
		
		model.time=Ext.Date.format(me.currentDate,"H:i:s");
		if(me.getStore().getCount( )>=12){
			me.store.removeAt(0);
		}
		me.store.loadData([model],true);
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
		            fields: ['cpu_combined'],
		            minimum: 0,
		            maximum: 100
		        },
		        {
		            title: '时间',grid: true,
		            type: 'Category',
		            position: 'bottom',
		            fields: ['time']
		            //dateFormat: 'i'
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
		            highlight: {
		                size: 7,
		                radius: 7
		            },
		            markerConfig: {
		                type: 'circle',
		                size: 4,
		                radius: 4,
		                'stroke-width': 0
		            },
		            xField: 'time',
		            yField: 'cpu_combined'
		        }
		    ]
		});
		return chart;
	},
	getMemChart:function(){
		var me=this;
		var chart=Ext.create('Ext.chart.Chart',{
			 region:'center',
			 flex: 1,
			 animate: true,
			 width:900,
			 store:me.getStore(),
			 axes: [
		        {
		            title: 'Memory百分比',grid: true,
		            type: 'Numeric',
		            position: 'left',
		            fields: ['mem_used'],
		            minimum: 0,
		            maximum: 100
		        },
		        {
		            title: '时间',grid: true,
		            type: 'Category',
		            position: 'bottom',
		            fields: ['time']
		            //dateFormat: 'i'
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
		            highlight: {
		                size: 7,
		                radius: 7
		            },
		            markerConfig: {
		                type: 'circle',
		                size: 4,
		                radius: 4,
		                'stroke-width': 0
		            },
		            xField: 'time',
		            yField: 'mem_used'
		        }
		    ]
		});
		return chart;
	}
});