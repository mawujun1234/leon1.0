Ext.Loader.setConfig({enabled: true});

Ext.Loader.setPath('Ext.ux', '../../ext-4.0.7/examples/ux/');

Ext.require([
    'Ext.form.Panel',
    'Ext.ux.form.MultiSelect'
]);

Ext.onReady(function(){
	Ext.QuickTips.init(); 

	Ext.define('stock',{
		extend:'Ext.data.Model',
		fields:[
			{name:'memo',mapping:'memo'},
			{name:'status',mapping:'status'},
			{name:'stid',mapping:'stid'},
			{name:'stock',mapping:'stock'},
			{name:'uname',mapping:'uname'},
			{name:'unitid',mapping:'unitid'}]
		});
	
	var stockStore = Ext.create('Ext.data.JsonStore', {
        model:'stock',
        proxy: {
            type: 'ajax',
            url: 'getStockList.do',
            reader: { root: 'list' }
        },
        autoLoad:true
    });
	
    var selModel = Ext.create('Ext.selection.CheckboxModel', {});
	
	var stock_grid=Ext.create('Ext.grid.Panel',{
	    title:'仓库列表',
	    region:'west',
	    width:280,
	    split:true,
	    store:stockStore,
	    selModel:selModel,
	    columns: [
	         {header:'stid',dataIndex:'stid',hidden:true,hideable:false},
	   		 {header:'unitid',dataIndex:'unitid',hidden:true,hideable:false},
	   		 {header:'名称',dataIndex:'stock'},
	   		 {header:'维护组',dataIndex:'uname',hidden:true},
	   		 {header:'状态',dataIndex:'status',renderer:statusRender,hidden:true},
	   		 {header:'备注',dataIndex:'memo',flex:1}
			]
	});
    
	Ext.create('Ext.container.Viewport',{
        layout:'border',
        renderTo:'view-port',
        padding:'5px',
        style:'background-color:#FFFFFF',
        defaults:{border:false},
	    items:[stock_grid,{region:'center'}]
	});
	
	function statusRender(v){
		if(v===0){
			return '正常'
		}else{
			return '无效'
		}
	}
});