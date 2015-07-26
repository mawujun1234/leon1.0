Ext.defineModel("Ems.baseinfo.EquipmentCycle",{
	extend:"Ext.data.Model",
	idProperty:'id',
	fields:[
		{name:'id',type:'string'},
		{name:'ecode',type:'string'},
		{name:'operateDate',type:'string'},
		{name:'operateType',type:'string'},
		{name:'operateType_name',type:'string'},
		{name:'operater_id',type:'string'},
		{name:'operater_name',type:'string'},
		{name:'target_id',type:'string'},
		{name:'target_name',type:'string'},
		{name:'type_id',type:'string'}
	],
	associations:[
	]
});