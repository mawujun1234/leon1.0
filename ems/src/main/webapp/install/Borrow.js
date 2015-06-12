Ext.defineModel("Ems.install.Borrow",{
	extend:"Ext.data.Model",
	idProperty:'id',
	fields:[
		{name:'id',type:'string'},
		{name:'memo',type:'string'},
		{name:'operateDate',type:'date', dateFormat: 'Y-m-d'},
		{name:'operater',type:'string'},
		{name:'project_id',type:'string'},
		{name:'store_id',type:'string'},
		{name:'workUnit_id',type:'string'},
		{name:'isAllReturn',type:'bool'},
		
		{name:'project_name',type:'string'},
		{name:'store_name',type:'string'},
		{name:'workUnit_name',type:'string'}
	],
	associations:[
	]
});