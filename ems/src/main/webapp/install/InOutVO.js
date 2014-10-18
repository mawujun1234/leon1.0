Ext.defineModel("Ems.install.InOutVO",{
	extend:"Ext.data.Model",
	idProperty:'id',
	fields:[
		{name:'id',type:'string'},
		{name:'operateDate',type:'date', dateFormat: 'Y-m-d'},
		{name:'operater',type:'string'},
		{name:'store_id',type:'string'},
		{name:'workUnit_id',type:'string'},
		{name:'memo',type:'string'},
		{name:'type',type:'string'},
		
		{name:'store_name',type:'string'},
		{name:'workUnit_name',type:'string'},
		{name:'type_name',type:'string'}
	],
	associations:[
	]
});