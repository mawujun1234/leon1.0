Ext.defineModel("Ems.geolocation.GpsConfig",{
	extend:"Ext.data.Model",
	idProperty:'id',
	fields:[
		{name:'id',type:'int'},
		{name:'interval',type:'int'}
	],
	associations:[
	]
});