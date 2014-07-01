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
            title: 'Months',
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
                renderer: function(storeItem, item) {
                    this.setTitle(storeItem.get('name'));
                    this.update(storeItem.get('data'));
                }
            },
            style: {
                fill: '#38B8BF'
            }
        }]
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
		items:[westPanel,chart]
	});

});