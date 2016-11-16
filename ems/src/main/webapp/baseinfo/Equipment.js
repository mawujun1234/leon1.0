//Ext.defineModel("Ems.baseinfo.Equipment",{
Ext.define("Ems.baseinfo.Equipment",{//2016.11.15改的
	extend:"Ext.data.Model",
	idProperty:'ecode',
	fields:[
		{name:'ecode',type:'string'},
		{name:'brand_id',type:'string'},
		{name:'brand_name',type:'string'},
		{name:'datebuying',type:'date', dateFormat: 'Y-m-d'},
		{name:'fisData',type:'date', dateFormat: 'Y-m-d'},
		{name:'isnew',type:'bool'},
		{name:'memo',type:'string'},
		{name:'prod_id',type:'string'},
		{name:'prod_name',type:'string'},
		{name:'prod_spec',type:'string'},
		{name:'place',type:'string'},
		{name:'status',type:'string'},
		{name:'style',type:'string'},
		{name:'subtype_id',type:'string'},
		{name:'subtype_name',type:'string'},
		{name:'supplier_id',type:'string'},
		{name:'supplier_name',type:'string'},
		{name:'store_id',type:'string'},
		{name:'store_name',type:'string'},
		{name:'unitPrice',type:'float'},
		{name:'num',type:'int'},
		{name:'orderlist_id',type:'string'},
		{name:'last_install_date',type:'date', dateFormat: 'Y-m-d'},
		{name:'last_task_id',type:'string'},
		{name:'last_installIn_id',type:'string'},
		{name:'last_workunit_id',type:'string'},
		{name:'prod_quality_month',type:'int'},
		
		{name:'inStore_type',type:'string'},
		{name:'isInStore',type:'string'},
		//出库的时候用的
		{name:'workUnit_id',type:'string'},
		{name:'workUnit_name',type:'string'},
		{name:'status_name',type:'string'},
		{name:'outStore_type',type:'string'}
		
	],
	associations:[
	]
});