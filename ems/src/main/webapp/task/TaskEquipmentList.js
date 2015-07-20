Ext.defineModel("Ems.task.TaskEquipmentList",{
	extend:"Ext.data.Model",
	idProperty:'id',
	fields:[
		{name:'id',type:'string'},
		{name:'ecode',type:'string'},
		{name:'task_id',type:'string'},
		{name:'type',type:'string'},
		
		{name:'type_name',type:'string'},
		{name:'subtype_name',type:'string'},
		{name:'prod_name',type:'string'},
		{name:'brand_name',type:'string'},
		{name:'supplier_name',type:'string'},
		{name:'style',type:'string'}

	],
	associations:[
	]
});