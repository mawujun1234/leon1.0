Ext.defineModel("Ems.repair.RepairReport",{
	extend:"Ext.data.Model",
	idProperty:'id',
	fields:[
		{name:'id',type:'string'},
		{name:'ecode',type:'string'},
		{name:'prod_id',type:'string'},
		{name:'rpa_id',type:'string'},
		
		{name:'repair_date',type:'string'},
		{name:'workunit_id',type:'string'},
		{name:'broken_memo',type:'string'},
		
		{name:'rpa_in_date',type:'string'},
		{name:'rpa_in_oper_id',type:'string'},
		{name:'rpa_out_date',type:'string'},
		{name:'rpa_out_oper_id',type:'string'},
		{name:'rpa_user_id',type:'string'},
		{name:'rpa_type',type:'string'},
		{name:'status',type:'int'},
		{name:'str_in_date',type:'string'},
		{name:'str_in_id',type:'string'},
		
		{name:'str_in_oper_id',type:'string'},
		{name:'str_out_date',type:'string'},
		{name:'str_out_id',type:'string'},
		
		{name:'str_out_oper_id',type:'string'},
		{name:'broken_memo',type:'string'},
		{name:'broken_reson',type:'string'},
		{name:'memo',type:'string'},
		
		{name:'workunit_name',type:'string'},
		{name:'rpa_name',type:'string'},
		{name:'rpa_user_name',type:'string'},
		{name:'str_in_name',type:'string'},
		{name:'str_out_name',type:'string'},
		{name:'brand_name',type:'string'},
		{name:'prod_name',type:'string'},
		{name:'prod_spec',type:'string'},
		{name:'subtype_name',type:'string'},
		{name:'supplier_name',type:'string'},
		{name:'equipment_style',type:'string'},
		{name:'equipment_status',type:'int'},
		{name:'status_name',type:'string'},
		{name:'rpa_type_name',type:'string'},
		{name:'scrap_id',type:'string'},
		
		{name:'handler_method',type:'string'}
		
	],
	associations:[
	]
});