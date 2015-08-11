Ext.defineModel("Ems.install.B2INotify",{
	extend:"Ext.data.Model",
	idProperty:'id',
	fields:[
		{name:'id',type:'string'},
		{name:'borrow_id',type:'string'},
		{name:'createDate',type:'date', dateFormat: 'Y-m-d'},
		{name:'ecode',type:'string'},
		{name:'pole_id',type:'string'},
		{name:'store_id',type:'string'},
		{name:'task_id',type:'string'},
		{name:'workunit_id',type:'string'},
		
		{name:'subtype_name',type:'string'},
		{name:'prod_name',type:'string'},
		{name:'prod_spec',type:'string'},
		{name:'brand_name',type:'string'},
		{name:'supplier_name',type:'string'},
		{name:'prod_style',type:'string'},
		{name:'pole_code',type:'string'},
		{name:'pole_name',type:'string'},
		{name:'store_name',type:'string'},
		{name:'workunit_name',type:'string'}
		
	],
	associations:[
	]
});