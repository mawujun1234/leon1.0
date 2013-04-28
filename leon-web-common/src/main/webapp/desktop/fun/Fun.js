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
		{type:'belongsTo',model:'Leon.desktop.fun.Fun'}
	]
	,proxy:{
		type:'ajax',
		api:{
			read:'/fun/queryChildren',
			get : '/fun/get',
			create:'/fun/craete',
			update:'/fun/update',
			destory:'/fun/destory'
		},
		reader:{
			type:'json',
			root:'root'
		}
	}
});
