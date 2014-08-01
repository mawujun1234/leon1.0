Ext.defineModel("Ems.baseinfo.Supplier",{
	extend:"Ext.data.Model",
	idProperty:'id',
	fields:[
		{name:'id',type:'string'},
		{name:'memo',type:'string'},
		{name:'name',type:'string'},
		{name:'sname',type:'string'},
		{name:'status',type:'bool'},
		{name:'website',type:'string'}
	],
	associations:[
	]
});