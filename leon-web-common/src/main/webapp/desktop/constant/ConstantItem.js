Ext.defineModel("Leon.desktop.constant.ConstantItem",{
	extend:"Ext.data.Model",
	fields:[
		{name:'id',type:'string'},
		{name:'code',type:'string'},
		{name:'text',type:'string'},
		{name:'ordering',type:'int'},
		{name:'remark',type:'string'},
		{name:'discriminator',type:'string'},

		{name:'constant_id',type:'string'}
	],
	associations:[
		{type:'belongsTo',model:'Leon.desktop.constant.Constant',associatedName:'Constant',associationKey:'constant',foreignKey:"constant_id"}
	]
});