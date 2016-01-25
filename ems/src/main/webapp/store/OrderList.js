Ext.defineModel("Ems.store.OrderList",{
	extend:"Ext.data.Model",
	idProperty:'id',
	fields:[
		{name:'id',type:'string'},
		//{name:'orderNo',type:'string'},
		{name:'brand_id',type:'string'},
		{name:'order_id',type:'string'},
		//{name:'operater',type:'string'},
		//{name:'orderDate',type:'date', dateFormat: 'Y-m-d'},
		{name:'orderNum',type:'int'},
		{name:'prod_id',type:'string'},
		//{name:'store_id',type:'string'},
		
		{name:'subtype_id',type:'string'},
		
		{name:'totalNum',type:'int'},
		{name:'type_id',type:'string'},
		{name:'unitPrice',type:'float'},
		{name:'totalprice',type:'int'},
		{name:'quality_month',type:'int'},
		//{name:'status',type:'string'},
		
		{name:'depreci_year',type:'string'},
		{name:'depreci_month',type:'string'},
		{name:'depreci_day',type:'string'},
		
		{name:'brand_name',type:'string'},
		{name:'prod_style',type:'string'},
		{name:'prod_id',type:'string'},
		{name:'prod_name',type:'string'},
		{name:'prod_spec',type:'string'},
		{name:'prod_unit',type:'string'},
		{name:'prod_memo',type:'string'},
		{name:'type_name',type:'string'},
		{name:'subtype_name',type:'string'},
		
		{name:'noedit',type:'bool'},
		//{name:'store_name',type:'string'},
		{name:'printNum',type:'int'}
		//{name:'exportStatus',type:'bool'},
		//{name:'status_name',type:'string'}
	],
	associations:[
	]
});