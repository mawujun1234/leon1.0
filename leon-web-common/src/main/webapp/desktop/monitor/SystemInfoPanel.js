Ext.define('Leon.desktop.monitor.SystemInfoPanel',{
	extend:'Ext.grid.Panel',
	title:'概要',
	columnLine:true,
	requires: [
        'Ext.grid.feature.Grouping'
    ],
//    features: [{
//        ftype: 'grouping',
//        groupHeaderTpl: '{columnName}: {name} ({rows.length} Item{[values.rows.length > 1 ? "s" : ""]})',
//        hideGroupedHeader: true,
//        startCollapsed: true
//       // id: 'restaurantGrouping'
//    }],
	features: [{
		ftype:'grouping',
		groupHeaderTpl: '{name} ({rows.length} Item{[values.rows.length > 1 ? "s" : ""]})'
	}],
	initComponent:function(){
		var me=this;
		me.columns= [
	        { text: '名称',  dataIndex: 'name' },
	        { text: '值', dataIndex: 'value',flex:1}
	    ];
	    me.store=Ext.create('Ext.data.Store', {
	    	autoLoad:true,
	    	groupField: 'group',
		    fields:['name', 'value', 'group'],
		    proxy: {
		        type: 'ajax',
		        url:Ext.ContextPath+'/monitorSystem/querySystemInfo',
		        reader: {
		            type: 'json',
		            root: 'root'
		        }
		    }
		});
		
		me.callParent();
	}
});