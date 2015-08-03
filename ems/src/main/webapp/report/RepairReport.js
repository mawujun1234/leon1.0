Ext.defineModel("Ems.report.RepairReport",{
	extend:"Ext.data.Model",
	fields:[
		{name:'ecode',type:'string'},
		{name:'str_out_name',type:'string'},
		{name:'brand_name',type:'string'},
		
		{name:'prod_style',type:'string'},
		{name:'subtype_name',type:'string'},
		{name:'str_out_date',type:'string'},
		{name:'repair_take_time',type:'string'},
		
		{name:'broken_reson',type:'string'},
		{name:'handler_method',type:'string'},
		{name:'status',type:'string'},
		{name:'send_date',type:'string'},
		{name:'receive_date',type:'string'},
		{name:'str_in_date',type:'string'}
	]
});