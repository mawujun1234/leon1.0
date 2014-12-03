Ext.defineModel("Ems.baseinfo.Pole",{
	extend:"Ext.data.Model",
	idProperty:'id',
	fields:[
		{name:'id',type:'string'},
		{name:'code',type:'string'},
		{name:'address',type:'string'},
		{name:'area',type:'string'},
		{name:'city',type:'string'},
		{name:'customer_id',type:'string'},
		{name:'latitude',type:'string'},
		{name:'longitude',type:'string'},
		{name:'name',type:'string'},
		{name:'province',type:'string'},
		{name:'status',type:'string'},
		{name:'area_id',type:'string'},
		
		{name:'workunit_id',type:'string'},
		{name:'status_name',type:'string'},
		{name:'area_name',type:'string'},
		{name:'workunit_name',type:'string'},
		{name:'customer_name',type:'string'},
		
		{name:'task_num',type:'int'}
//		{name:'task_id',type:'string'},
//		{name:'task_type',type:'string'},
//		{name:'task_status',type:'string'},
//		{name:'task_status_name',type:'string'},
//		{name:'task_memo',type:'string'}
	],
	associations:[
	]
});