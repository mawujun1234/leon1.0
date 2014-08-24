Ext.defineModel("Ems.baseinfo.EquipmentType",{
	extend:"Ext.data.Model",
	idProperty:'id',
	fields:[
		{name:'id',type:'string'},
		{name:'levl',type:'int'},
		{name:'status',type:'bool'},
		{name:'text',type:'string'},
		{name:'unit',type:'string'},
		{name:'parent_id',type:'string'},
		
		{name:'status_name',type:'String'}
	],
	associations:[
	]
});