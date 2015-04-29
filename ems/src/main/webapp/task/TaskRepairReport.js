Ext.defineModel("Ems.task.TaskRepairReport",{
	extend:"Ext.data.Model",
	idProperty:'id',
	fields:[
		{name:'id',type:'string'},
		{name:'approveDate',type:'string'},
		{name:'completeDate',type:'string'},
		{name:'createDate',type:'string'},
		{name:'customer_id',type:'string'},
		{name:'hitchReason',type:'string'},
		{name:'hitchType',type:'string'},
		{name:'memo',type:'string'},
		{name:'pole_id',type:'string'},
		{name:'startHandDate',type:'string'},
		{name:'status',type:'string'},
		{name:'submitDate',type:'string'},
		{name:'type',type:'auto'},
		{name:'workunit_id',type:'string'},
		
		{name:'pole_address',type:'string'},
		{name:'customer_name',type:'string'},
		{name:'pole_code',type:'string'},
		{name:'pole_name',type:'string'},
		{name:'workunit_name',type:'string'},
		{name:'status_name',type:'string'},
		{name:'type_name',type:'string'},
		{name:'equipList',type:'string'},
		
		{name:'isOverTime',type:'string'},
		{name:'finishTime',type:'string'},
		{name:'repairTime',type:'string'}
	],
	associations:[
	]
});