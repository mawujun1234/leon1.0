Ext.defineModel("Ems.repair.Scrap",{
	extend:"Ext.data.Model",
	idProperty:'id',
	fields:[
		{name:'id',type:'string'},
		{name:'ecode',type:'string'},
		{name:'operateDate',type:'date', dateFormat: 'Y-m-d'},
		{name:'operater',type:'string'},
		{name:'reason',type:'string'},
		{name:'repair_id',type:'string'},
		{name:'residual',type:'string'}
	],
	associations:[
	]
});