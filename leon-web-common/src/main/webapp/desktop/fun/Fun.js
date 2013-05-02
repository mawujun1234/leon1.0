Ext.define("Leon.desktop.fun.Fun",{
	extend:"Ext.data.Model",
	fields:[
		{name:'id',type:'auto'},
		{name:'code',type:'string'},
		{name:'text',type:'string'},
		{name:'url',type:'string'},
		{name:'reportCode',type:'string'},
		{name:'helpContent',type:'string'},
		{name:'parent_id',type:'auto'}
	],
	associations:[
		//{type:'hasMany',model:'Leon.desktop.fun.Fun',name : 'children'},不用，因为树形结构一般都是通过store来获取子节点的
		{type:'belongsTo',model:'Leon.desktop.fun.Fun',foreignKey:'parent_id',associationKey:'parent'}
	]
	,proxy:{
		actionMethods: { read: 'POST' },
		timeout :600000,
		type:'ajax',
		api:{
			read:'/fun/queryChildren',
			get : '/fun/get',
			create:'/fun/create',
			update:'/fun/update',
			destroy:'/fun/destroy'
		},
		reader:{
			type:'json',
			root:'root'
		}
	}
});
