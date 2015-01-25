Ext.defineModel("Ems.task.Task",{
	extend:"Ext.data.Model",
	idProperty:'id',
	fields:[
		{name:'id',type:'string'},
		{name:'approveDate',type:'date', dateFormat: 'Y-m-d'},
		{name:'completeDate',type:'date', dateFormat: 'Y-m-d'},
		{name:'createDate',type:'date', dateFormat: 'Y-m-d'},
		{name:'customer_id',type:'string'},
		{name:'hitchReason',type:'string'},
		{name:'hitchType',type:'string'},
		{name:'memo',type:'string'},
		{name:'pole_id',type:'string'},
		{name:'startHandDate',type:'date', dateFormat: 'Y-m-d'},
		{name:'status',type:'string'},
		{name:'submitDate',type:'date', dateFormat: 'Y-m-d'},
		{name:'type',type:'auto'},
		{name:'workunit_id',type:'string'},
		
		{name:'pole_address',type:'string'},
		{name:'customer_name',type:'string'},
		{name:'pole_code',type:'string'},
		{name:'pole_name',type:'string'},
		{name:'workunit_name',type:'string'},
		{name:'status_name',type:'string'},
		{name:'type_name',type:'string'},
		{name:'equipList',type:'string'}
	],
	associations:[
	]
});