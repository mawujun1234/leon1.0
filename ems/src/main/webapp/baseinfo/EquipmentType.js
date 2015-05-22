Ext.defineModel("Ems.baseinfo.EquipmentType",{
	extend:"Ext.data.Model",
	idProperty:'id',
	fields:[
		{name:'id',type:'string'},
		//{name:'levl',type:'int'},
		{name:'status',type:'bool'},
		{name:'name',type:'string'},
		{name:'text',type:'string'},
		//{name:'unit',type:'string'},
		//{name:'spec',type:'string'},
		//{name:'style',type:'string'},
		//{name:'brand_id',type:'string'},
		{name:'parent_id',type:'string'},
		{name:'memo',type:'string'},
		//{name:'type',type:'string'},
		//{name:'type_parent_id',type:'string'},
		
		//{name:'type_name',type:'string'},
		
		{name:'status_name',type:'String'}
		//{name:'brand_name',type:'string'}
	],
	associations:[
	]
});