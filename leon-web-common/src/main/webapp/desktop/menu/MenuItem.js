Ext.defineModel("Leon.desktop.menu.MenuItem",{
	extend:"Ext.data.Model",
	requires: [
	     'Leon.desktop.fun.Fun'
	],
	fields:[
		{name:'id',type:'auto'},
		{name:'code',type:'string'},
		{name:'text',type:'string'},
		{name:'javaClass',type:'string'},
		{name:'scripts',type:'string'},
		{name:'iconCls',type:'string',defaultValue:'menu-category-default-16'},
		{name:'iconCls32',type:'string',defaultValue:'menu-category-default-32'},
		{name:'reportCode',type:'string'},
		{name:'isAutoCreate',type:'boolean'},
		{name:'autostart',type:'boolean'},
		{name:'url',type:'string'}//是从功能里面来的
		,{name:'fun_id',type:'auto'}
		,{name:'menu_id',type:'auto'}
		,{name:'parent_id',type:'auto'}
	],
	associations:[
		//{type:'hasMany',model:'Leon.desktop.fun.Fun',name : 'children'},不用，因为树形结构一般都是通过store来获取子节点的
		{type:'belongsTo',model:'Leon.desktop.fun.Fun',associatedName:'Fun'},
		{type:'belongsTo',model:'Leon.desktop.menu.Menu',associatedName:'Menu'},
		{type:'belongsTo',model:'Leon.desktop.menu.MenuItem',associatedName:'Parent'}//,foreignKey:'parent_id',associationKey:'parent'
	]

});
