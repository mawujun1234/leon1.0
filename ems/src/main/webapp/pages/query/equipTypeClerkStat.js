Ext.onReady(function(){
	Ext.QuickTips.init(); 
	
	Ext.define('clerk',{
		extend:'Ext.data.Model',
		fields:[
			{name:'value',mapping:'value'},
			{name:'text',mapping:'text'}]
		});
	
	var clerkStore = Ext.create('Ext.data.JsonStore', {
        model:'clerk',
        proxy: {
            type: 'ajax',
            url: 'clerkList.do'
        },
        autoLoad:true
    });
	
    var selModel = Ext.create('Ext.selection.CheckboxModel', {});
	
	var clerks_grid=Ext.create('Ext.grid.Panel',{
	    title:'操作员工',
	    region:'west',
	    width:180,
	    split:true,
	    store:clerkStore,
	    selModel:selModel,
	    columns: [
	   		 {header:'value',dataIndex:'value',hidden:true,hideable:false},
	   		 {header:'姓名',dataIndex:'text',flex:1}
		]
	});
	
    var etypeStore = Ext.create('Ext.data.ArrayStore', {
	    fields: [{name: 'text', type: 'string'},
	    	     {name: 'value', type: 'int'}],
        proxy: {
            type: 'ajax',
            url: 'etypeList',
            reader: {
                type: 'json'
            }
        },
    	autoLoad:true,
    	listeners:{
    		load:function(s,r,success,o,e){
    			var etypes=new Array();
    			s.each(function(record){
    				etypes.push(record.get('value'));
    			});
    			doStatistics(null,etypes.join(','),null);
    		}
    	}
    });
    
    var etype_box=Ext.create('Ext.D.form.field.CheckboxGroup',{
    	flex:1,
    	fieldLabel : '设备类型',
        columns: 2,
        labelWidth:70,
        name:'etypes',
        labelAlign:'top',
        store:etypeStore
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
    	autoLoad:true
    });
    
    var stocks_box=Ext.create('Ext.form.ComboBox',{
    	fieldLabel: '库房',
        store: stockStore,
        queryMode: 'local',
        displayField: 'text',
        valueField: 'value',
        labelWidth:55,
        multiSelect:true
    });


  // create the data store
	var statsStore = Ext.create('Ext.data.ArrayStore', {
		autoDestroy: true,
	    fields: [{name: 'ucname', mapping: 'ucname'},
	             {name: 'ucid', mapping: 'ucid'},
	             {name: 'amount', mapping: 'amount'},
	    	     {name: 'typename', type: 'typename'},
	    	     {name: 'totalPrice', type: 'totalPrice'},
	    	     {name: 'statusName', type: 'statusName'},
	    	     {name: 'eubtypename', type: 'eubtypename'},
	    	     {name: 'sname', type: 'sname'}],
	    groupField: 'ucname'
	});

	var groupingFeature = Ext.create('Ext.grid.feature.GroupingSummary',{
        groupHeaderTpl: '员工名称: {name} ({rows.length} Item{[values.rows.length > 1 ? "s" : ""]})'
	});
  // create the Grid
	var equip_statistics_grid = Ext.create('Ext.grid.Panel', {
		  region:'center',
	      store: statsStore,
	      columnLines: true,
	      features: [groupingFeature],
	      columns: [{
	          text     : '员工',
	          flex:1,
	          sortable : false,
	          dataIndex: 'ucname',
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
	      tbar:['<pan id="toolbar-title-text"><员工设备类型统计></span>','->',stocks_box,
	            {iconCls:'icon-stats',text:'确认统计',handler:function(){
	            	var records= clerks_grid.getSelectionModel().getSelection( );
	            	var ucids=new Array();
	            	if(records.length>0){
	            		for(var i in records){
	            			ucids.push(records[i].get('value'));
	            		}
	            	}
	    			var etypes= etype_box.getValue();
	    			var stids=stocks_box.getValue();
	    			doStatistics(ucids,etypes,stids)
	            }}],
	      viewConfig: {
	          stripeRows: true
	      }
	});
    function doStatistics(ucids,etypes,stids){
    	if(etypes&&etypes.length>0){
			Ext.Ajax.request({     
				url:'equipTypeClerkStat',  
				params:{  
					ucids:ucids,
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
    		Ext.Msg.alert('提示','请至少选择一种设备状态');
    	}
    }
	Ext.create('Ext.container.Viewport',{
        layout:'border',
        renderTo:'view-port',
        padding:'5px',
        style:'background-color:#FFFFFF',
        defaults:{border:false},
	    items:[clerks_grid,equip_statistics_grid,{
	    	region:'east',
	    	width:250,
	    	split:true,
    		layout:{
    			type:'vbox',
    			align:'stretch'
    		},
    		items:[etype_box]
	    }]
	});
});