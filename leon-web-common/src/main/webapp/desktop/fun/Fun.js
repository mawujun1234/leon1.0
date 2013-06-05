

//Ext.define("Leon.desktop.fun.Fun",{
Ext.defineModel("Leon.desktop.fun.Fun",{
	extend:"Ext.data.Model",
	fields:[
		{name:'id',type:'auto'},
		{name:'code',type:'string'},
		{name:'text',type:'string'},
		{name:'url',type:'string'},
		{name:'reportCode',type:'string'},
		{name:'helpContent',type:'string'},
		{name:'funEnum',type:'string'},
		{name:'iconCls',type:'string'},
		{name:'bussinessType',type:'constant'},//Ext.data.Types.Constant
		{name:'parent_id',type:'auto'}
	],
	associations:[
		//{type:'hasMany',model:'Leon.desktop.fun.Fun',name : 'children'},不用，因为树形结构一般都是通过store来获取子节点的
		{type:'belongsTo',model:'Leon.desktop.fun.Fun',associatedName:'Parent'}
	]
//	,proxy:{
//		type:'bajax',
//		api:{
//			read:'/fun/queryChildren',
//			load : '/fun/get',
//			create:'/fun/create',
//			update:'/fun/update',
//			destroy:'/fun/destroy'
//		}
//	}
//	,proxy:{
//		actionMethods: { read: 'POST' },
//		timeout :600000,
//		headers:{ 'Accept':'application/json;'},
//		type:'ajax',
//		api:{
//			read:'/fun/queryChildren',
//			get : '/fun/get',
//			create:'/fun/create',
//			update:'/fun/update',
//			destroy:'/fun/destroy'
//		},
//		reader:{
//			type:'json',
//			root:'root'
//		}
//		,writer:{
//			type:'json'
//		}
//	}
});
