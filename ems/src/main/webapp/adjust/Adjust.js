Ext.defineModel("Ems.adjust.Adjust",{
	extend:"Ext.data.Model",
	idProperty:'id',
	fields:[
		{name:'id',type:'string'},
		{name:'memo',type:'string'},
		{name:'status',type:'string'},
		{name:'str_in_date',type:'date', dateFormat: 'Y-m-d'},
		{name:'str_in_id',type:'string'},
		{name:'str_in_oper_id',type:'string'},
		{name:'str_out_date',type:'date', dateFormat: 'Y-m-d'},
		{name:'str_out_id',type:'string'},
		{name:'str_out_oper_id',type:'string'},
		
		{name:'str_in_name',type:'string'},
		{name:'str_out_name',type:'string'},
		{name:'status_name',type:'string'},
		{name:'ecode',type:'string'},
		
		{name:'brand_name',type:'string'},
		{name:'prod_name',type:'string'},
		{name:'subtype_name',type:'string'},
		{name:'supplier_name',type:'string'},
		{name:'equipment_style',type:'string'},
		{name:'equipment_status',type:'int'}
		
	],
	associations:[
	]
});