Ext.defineModel("Ems.task.HitchReasonTpl",{
	extend:"Ext.data.Model",
	idProperty:'id',
	fields:[
		{name:'id',type:'int'},
		{name:'name',type:'string'},
		{name:'tpl',type:'string'},
		
		{name:'hitchType_id',type:'string'}
	],
	associations:[
	]
});