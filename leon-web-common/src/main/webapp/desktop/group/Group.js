Ext.defineModel("Leon.desktop.group.Group",{
	extend:"Ext.data.Model",
	fields:[
		{name:'id',type:'auto'},
		{name:'name',type:'string'},
		{name:'parent_id',type:'string'}	
	],
	associations:[
		//{type:'hasMany',model:'Leon.desktop.fun.Fun',name : 'children'},不用，因为树形结构一般都是通过store来获取子节点的
		{type:'belongsTo',model:'Leon.desktop.group.Group',associatedName:'Parent'}
	]
});
