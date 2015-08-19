Ext.defineModel("Ems.report.ScrapReport",{
	extend:"Ext.data.Model",
	idProperty:'id',
	fields:[
		{name:'customer_name',type:'string'},
		{name:'pole_id',type:'string'},
		{name:'pole_code',type:'string'},
		{name:'pole_name',type:'string'},
		{name:'workunit_name',type:'string'},
		{name:'memo',type:'string'},
		{name:'hitchDate',type:'string'},
		{name:'createDate',type:'string'},
		{name:'startHandDate',type:'string'},
		{name:'submitDate',type:'string'},
		{name:'completeDate',type:'string'},
		{name:'usedTime',type:'string'},
		{name:'repairUsedTime',type:'string'},
		{name:'result',type:'string'},
		{name:'overtime',type:'string'},
		{name:'hitchType',type:'string'},
		{name:'hitchReason',type:'string'},
		{name:'handleMethod_name',type:'string'},
		{name:'handle_contact',type:'string'},
		{name:'',type:'string'},
		{name:'',type:'string'},
		{name:'',type:'string'},
		{name:'',type:'string'},
		{name:'',type:'string'},

		
		
	],
	associations:[
	]
});