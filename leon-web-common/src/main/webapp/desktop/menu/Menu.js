Ext.define("Leon.desktop.menu.Menu",{
	extend:"Ext.data.Model",
	fields:[
		{name:'id',type:'auto'},
		{name:'text',type:'string'}
	],
//	associations:[
//		//{type:'hasMany',model:'Leon.desktop.fun.Fun',name : 'children'},不用，因为树形结构一般都是通过store来获取子节点的
//		{type:'belongsTo',model:'Leon.desktop.fun.Fun',foreignKey:'fun_id',associationKey:'fun'},
//		{type:'belongsTo',model:'Leon.desktop.menu.Menu',foreignKey:'parent_id',associationKey:'parent'}
//	],
	proxy:{
		actionMethods: { read: 'POST' },
		timeout :600000,
		headers:{ 'Accept':'application/json;'},
		type:'ajax',
		api:{
			read:'/menu/queryAll',
			get : '/menu/get',
			create:'/menu/create',
			update:'/menu/update',
			destroy:'/menu/destroy'
		},
		reader:{
			type:'json',
			root:'root'
		}
		,writer:{
			type:'json'
		}
	}
	
});
