Ext.defineModel("Leon.desktop.role.Role",{
	extend:"Ext.data.Model",
	fields:[
		{name:'id',type:'string'},
		{name:'name',type:'string'},
		{name:'roleEnum',type:'string'},
		{name:'description',type:'string'},
		{name:'parent_id',type:'string'}
	],
	associations:[
		{type:'belongsTo',model:'Leon.desktop.role.Role',associatedName:'Parent'}
	]
});