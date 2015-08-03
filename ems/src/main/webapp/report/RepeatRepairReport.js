Ext.defineModel("Ems.report.RepeatRepairReport",{
	extend:"Ext.data.Model",
	fields:[
		{name:'ecode',type:'string'},
		{name:'prod_name',type:'string'},
		{name:'str_out_id',type:'string'},
		
		{name:'str_out_name',type:'string'},
		{name:'rpa_user_id',type:'string'},
		{name:'rpa_user_name',type:'string'},
		{name:'rpa_in_date',type:'string'},
		
		{name:'rpa_out_date',type:'string'},
		{name:'str_in_date',type:'string'},
		{name:'repeate_count',type:'int'}
	]
});