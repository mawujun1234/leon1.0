Ext.defineModel("Leon.desktop.role.Role",{
	extend:"Ext.data.Model",
	fields:[
		{name:'id',type:'string'},
		{name:'name',type:'string'},
		{name:'roleEnum',type:'string',useNull:true},
		{name:'description',type:'string'},
		{name:'category_id',type:'string'}
	],
	associations:[
		{type:'belongsTo',model:'Leon.desktop.role.Role',associatedName:'Category'}
	]
});