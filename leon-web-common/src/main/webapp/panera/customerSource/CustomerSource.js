Ext.defineModel("Leon.panera.customerSource.CustomerSource",{
	extend:"Ext.data.Model",
	idProperty:'id',
	fields:[
		{name:'id',type:'string'},
		{name:'desc',type:'string'},
		{name:'name',type:'string'}
	],
	associations:[
	]
});