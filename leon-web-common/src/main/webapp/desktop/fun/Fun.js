Ext.define("Leon.desktop.fun.Fun",{
	extend:"Ext.data.Model",
	fields:[
		{name:'id',type:'auto'},
		{name:'code',type:'string'},
		{name:'text',type:'string'},
		{name:'url',type:'string'},
		{name:'reportCode',type:'string'}
	],
	associations:[
		{type:'hasMany',model:'Leon.desktop.fun.Fun',name : 'children'},
		{type:'belongsTo',model:'Leon.desktop.fun.Fun'}
	]
	,proxy:{
		type:'ajax',
		api:{
			read:'/fun/list',
			load : '/fun/load',
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
