Ext.defineModel("Leon.genTest.MenuItem",{
	extend:"Ext.data.Model",
	idProperty:'id',
	fields:[
		{name:'id',type:'string'},
		{name:'code',type:'string'},
		{name:'iconCls',type:'string'},
		{name:'iconCls32',type:'string'},
		{name:'javaClass',type:'string'},
		{name:'leaf',type:'bool'},
		{name:'reportCode',type:'string'},
		{name:'scripts',type:'string'},
		{name:'text',type:'string'},
		{name:'fun_id',type:'auto'},
		{name:'menu_id',type:'auto'},
		{name:'parent_id',type:'auto'}
	],
	associations:[
			{type:'belongsTo',model: 'Leon.fun.Fun',associatedName:'Fun'},
			{type:'belongsTo',model: 'Leon.menu.Menu',associatedName:'Menu'},
			{type:'belongsTo',model: 'Leon.menu.MenuItem',associatedName:'Parent'}
	]
});