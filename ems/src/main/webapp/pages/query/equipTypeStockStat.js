Ext.onReady(function(){
	Ext.QuickTips.init(); 
	
	Ext.define('etype',{
		extend:'Ext.data.Model',
		fields:[
			{name:'value',mapping:'value'},
			{name:'text',mapping:'text'}]
		});
	
	var etypeStore = Ext.create('Ext.data.JsonStore', {
        model:'etype',
        proxy: {
            type: 'ajax',
            url: 'etypeList.do'
        },
        autoLoad:true
    });
	
    var selModel = Ext.create('Ext.selection.CheckboxModel', {});
	
	var etype_grid=Ext.create('Ext.grid.Panel',{
	    title:'设备类型',
	    region:'east',
	    width:180,
	    split:true,
	    store:etypeStore,
	    selModel:selModel,
	    columns: [
	   		 {header:'value',dataIndex:'value',hidden:true,hideable:false},
	   		 {header:'类型名称',dataIndex:'text',flex:1}
		]
	});
	
    var stockStore = Ext.create('Ext.data.ArrayStore', {
	    fields: [{name: 'text', type: 'string'},
	    	     {name: 'value', type: 'int'}],
        proxy: {
            type: 'ajax',
            url: 'stockList',
            reader: {
                type: 'json'
            }
        },
    	autoLoad:true,
    	listeners:{
    		load:function(s,r,success,o,e){
    			var stids=new Array();
    			s.each(function(record){
    				stids.push(record.get('value'));
    			});
    			doStatistics(null,null,stids);
    		}
    	}
    });
    
    var stock_box=Ext.create('Ext.D.form.field.CheckboxGroup',{
    	flex:1,
    	fieldLabel : '库房',
        columns: 2,
        labelWidth:70,
        name:'estatuses',
        labelAlign:'top',
        store:stockStore
    });
    
    var estatusStore = Ext.create('Ext.data.ArrayStore', {
	    fields: [{name: 'text', type: 'string'},
	    	     {name: 'value', type: 'int'}],
        proxy: {
            type: 'ajax',
            url: 'estatusList',
            reader: {
                type: 'json'
            }
        },
    	autoLoad:true
    });
    
    var estatus_box=Ext.create('Ext.form.ComboBox',{
    	fieldLabel: '设备状态',
        store: estatusStore,
        queryMode: 'local',
        displayField: 'text',
        valueField: 'value',
        labelWidth:55,
        multiSelect:true
    });

  // create the data store
	var statsStore = Ext.create('Ext.data.ArrayStore', {
		autoDestroy: true,
	    fields: [{name: 'amount', mapping: 'amount'},
	    	     {name: 'totalPrice', type: 'totalPrice'},
	    	     {name: 'stname', type: 'stname'},
	    	     {name: 'statusName', type: 'statusName'},
	    	     {name: 'typename', type: 'typename'}],
	    groupField: 'stname'
	});

	var groupingFeature = Ext.create('Ext.grid.feature.GroupingSummary',{
        groupHeaderTpl: '库房名称: {name} ({rows.length} Item{[values.rows.length > 1 ? "s" : ""]})'
	});
  // create the Grid
	var equip_statistics_grid = Ext.create('Ext.grid.Panel', {
		  region:'center',
	      store: statsStore,
	      columnLines: true,
	      features: [groupingFeature],
	      columns: [{
	          text     : '设备库房',
	          flex:1,
	          sortable : false,
	          dataIndex: 'stname',
	          summaryType: 'count',
	          summaryRenderer: function(value, summaryData, dataIndex) {
	              return ((value === 0 || value > 1) ? '(' + value + ' Items)' : '(1 Item)');
	          }
	      },{
	          text     : '设备类型',
	          flex:1,
	          sortable : false,
	          dataIndex: 'typename'
	      }, {
	          text: '统计',
	          columns: [{
	              text     : '总价',
	              sortable : true,
	              renderer : 'usMoney',
	              dataIndex: 'totalPrice',
	              summaryType: 'sum',
	              summaryRenderer: function(value, summaryData, dataIndex) {
	            	  return '$'+Ext.util.Format.number( value,'0.00');
	              }
	          }, {
	              text     : '数量',
	              sortable : true,
	              dataIndex: 'amount',
	              summaryType: 'sum',
	              summaryRenderer: function(value, summaryData, dataIndex) {
	            	  return value;
	              }
	          }]
	      }],
	      tbar:['<pan id="toolbar-title-text"><库房设备类型统计></span>','->',estatus_box,
	            {iconCls:'icon-stats',text:'确认统计',handler:function(){
	            	var records= etype_grid.getSelectionModel().getSelection( );
	            	var etypes=new Array();
	            	if(records.length>0){
	            		for(var i in records){
	            			etypes.push(records[i].get('value'));
	            		}
	            	}
	    			var estatuses= estatus_box.getValue();
	    			var stids=stock_box.getValue();
	    			doStatistics(estatuses,etypes,stids)
	            }}],
	      viewConfig: {
	          stripeRows: true
	      }
	});
    function doStatistics(estatuses,etypes,stids){
    	if(stids&&stids.length>0){
			Ext.Ajax.request({     
				url:'equipTypeStockStat',  
				params:{  
					estatuses:estatuses,
					etypes:etypes,
					stids:stids
				},  
				success: function(resp,opts) {   
					var ret= Ext.decode(resp.responseText);
					if(ret.success){
						statsStore.loadData(ret.list);
					}
				},   
				failure: function(resp,opts) {   
					var respText = Ext.decode(resp.responseText);   
					Ext.Msg.alert('错误', respText.error); 
				}
			});
    	}else{
    		Ext.Msg.alert('提示','请至少选择一个仓库');
    	}
    }
	Ext.create('Ext.container.Viewport',{
        layout:'border',
        renderTo:'view-port',
        padding:'5px',
        style:'background-color:#FFFFFF',
        defaults:{border:false},
	    items:[etype_grid,equip_statistics_grid,{
	    	region:'west',
	    	width:250,
	    	split:true,
    		layout:{
    			type:'vbox',
    			align:'stretch'
    		},
    		items:[stock_box]
	    }]
	});
});