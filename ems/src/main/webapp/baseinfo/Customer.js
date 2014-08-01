Ext.defineModel("Ems.baseinfo.Customer",{
	extend:"Ext.data.Model",
	idProperty:'id',
	fields:[
		{name:'id',type:'string'},
		{name:'memo',type:'string'},
		{name:'name',type:'string'},
		{name:'status',type:'bool'},
		{name:'type',type:'int'}
	],
	associations:[
	]
});