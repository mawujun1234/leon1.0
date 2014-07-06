//Ext.require("Leon.panera.customer.Customer");
//Ext.require("Leon.panera.customer.CustomerGrid");
//Ext.require("Leon.panera.customer.CustomerTree");
//Ext.require("Leon.panera.customer.CustomerForm");
Ext.onReady(function(){
	//http://docs.sencha.com/extjs/4.2.2/extjs-build/examples/charts/ReloadChart.html
	var chart_store=Ext.create('Ext.data.Store', {
        	autoLoad:false,
		    fields:["name","data"],
		    proxy:{
		    	url:Ext.ContextPath+"/report/getData",
		    	type:'ajax',
		    	reader:{
		    		type:"json",
		    		root:'root'
		    	}
		    }
		});
	chart_store.load({params:{dimession:"continent",meausre:"customer_num"}});
	function getChart(type){
		type=type?type:'column';
		if(type=='column'){
			var chart = Ext.create('Ext.chart.Chart', {
				region:'center',
		        animate: true,
		        shadow: true,
		        store: chart_store,
		        axes: [{
		            type: 'Numeric',
		            position: 'left',
		            fields: ['data'],
		            title: '客户数',
		            grid: true
		           // minimum: 0,
		           // maximum: 100
		        }, {
		            type: 'Category',
		            position: 'bottom',
		            fields: ['name'],
		            title: '维度',
		            label: {
		                rotate: {
		                    degrees: 270
		                }
		            }
		        }],
		        series: [{
		            type: 'column',
		            axis: 'left',
		            gutter: 80,
		            xField: 'name',
		            yField: ['data'],
		            tips: {
		                trackMouse: true,
		                width:100,
		                renderer: function(storeItem, item) {
		                    this.setTitle(storeItem.get('name'));
		                    this.update(storeItem.get('data'));
		                }
		            },
		            style: {
		                fill: '#38B8BF'
		            },
		            listeners: {
		                'itemclick': function() {
		                       // alert(1);
		                }
		        	}
		        }]
		    });
		     return chart;
		} else {
			    var chart = Ext.create('Ext.chart.Chart', {
			        xtype: 'chart',
			        animate: true,
			        store: chart_store,
			        shadow: true,
			        legend: {
			            position: 'right'
			        },
			        insetPadding: 60,
			        theme: 'Base:gradients',
			        series: [{
			            type: 'pie',
			            field: 'data',
			            showInLegend: true,
			            //donut: donut,
			            tips: {
			                trackMouse: true,
			                width:100,
			                renderer: function(storeItem, item) {
			                    //calculate percentage.
			                    var total = 0;
			                    chart_store.each(function(rec) {
			                        total += rec.get('data');
			                    });
			                    //this.setTitle(storeItem.get('name') + '</br>占比: ' + Math.round(storeItem.get('data') / total * 100) + '%'+ '</br>客户数: ' + Math.round(storeItem.get('data') ));
			                    this.setTitle('</br>占比: ' + Math.round(storeItem.get('data') / total * 100) + '%'+ '</br>客户数: ' + Math.round(storeItem.get('data') ));

			                }
			            },
			            highlight: {
			                segment: {
			                    margin: 20
			                }
			            },
			            label: {
			                field: 'name',
			                display: 'rotate',
			                contrast: true,
			                font: '18px Arial'
			            }
			        }]
			    });
			    return chart;
		}
	}
    var toolbar=Ext.create('Ext.toolbar.Toolbar',{
    	items:[{
	        xtype: 'radiogroup',
	        fieldLabel: '显示样式',
	        // Arrange radio buttons into two columns, distributed vertically
	        //columns: 2,
	        labelWidth:60,
	        vertical: true,
	        width:200,
	        items: [
	            { boxLabel: '柱状图', name: 'showType', inputValue: 'column', checked: true },
	            { boxLabel: '饼图', name: 'showType', inputValue: 'pie'}
	        ],
	        listeners:{
	        	change:function( radiogroup, newValue, oldValue){
	        		//console.dir(newValue);
	        		//alert(newValue);
	        		var newChart=getChart(newValue.showType);
	        		var oldChart=centerPanel.items.get(0);
	        		centerPanel.remove(oldChart);
	        		centerPanel.add(newChart);
	        		centerPanel.updateLayout();
	        	}
	        }
	    }]
    });
    var centerPanel=Ext.create('Ext.panel.Panel',{
		//layout:'border',
		tbar:toolbar,
		split:true,
		region:'center',
		layout:'fit',
		items:[getChart()]
	});
	
//==============================================================================================================	
	var dimession=Ext.create('Ext.grid.Panel',{
		title:'维度',
		region:'center',
		columns: [
	        { text: '名称',  dataIndex: 'name',flex:1 }
	    ],
	    store:Ext.create('Ext.data.Store', {
		    fields:['name', 'id'],
		    data:{'items':[
		        { 'name': '洲',  "id":"continent"  },
		        { 'name': '国家',  "id":"country"},
		        { 'name': '阶段', "id":"businessPhase"},
		        { 'name': '客户来源', "id":"customerSource"},
		        { 'name': '客户属性', "id":"customerProperty"},
		        { 'name': '客户星级', "id":"star"}
		    ]},
		    proxy: {
		        type: 'memory',
		        reader: {
		            type: 'json',
		            root: 'items'
		        }
		    }
		}),
		viewConfig:{
			stripeRows:true,
			listeners:{
				refresh:function(view){
					view.select(0);
				}
			}
		}
	});
	dimession.on('itemclick',function(view, record, item, index, e){
		chart_store.load({params:{dimession:record.get("id"),meausre:"customer_num"}});
	});
	var meausre=Ext.create('Ext.grid.Panel',{
		title:'度量',
		region:'south',
		height:300,
		columns: [
	        { text: '名称',  dataIndex: 'name',flex:1 }
	    ],
	    store:Ext.create('Ext.data.Store', {
		    fields:['name', 'id'],
		    data:{'items':[
		        { 'name': '客户数',  "id":"customer_num"  }
		    ]},
		    proxy: {
		        type: 'memory',
		        reader: {
		            type: 'json',
		            root: 'items'
		        }
		    }
		}),
		viewConfig:{
			stripeRows:true,
			listeners:{
				refresh:function(view){
					view.select(0);
				}
			}
		}
	});
	var westPanel=Ext.create('Ext.panel.Panel',{
		//layout:'border',
		width:200,
		split:true,
		region:'west',
		layout:'border',
		items:[dimession,meausre]
	});
	
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'border',
		items:[westPanel,centerPanel]
	});

});