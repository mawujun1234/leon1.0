Ext.define("Leon.desktop.constant.ConstantType",{
	extend:"Ext.data.Model",
	fields:[
		{name:'id',type:'auto'},
		{name:'name',type:'string'},
		{name:'remark',type:'string'},
		{name:'discriminator',type:'string'}
	],
	associations:[
		{type:'hasMany',model:'Leon.desktop.constant.Constant',name : 'constants'}
		//{type:'belongsTo',model:'Leon.desktop.fun.Fun',associatedName:'Parent'}
	]
	,proxy:{
		actionMethods: { read: 'POST' },
		timeout :600000,
		headers:{ 'Accept':'application/json;'},
		type:'ajax',
		api:{
			read:'/constantType/query',
			get : '/constantType/get',
			create:'/constantType/create',
			update:'/constantType/update',
			destroy:'/constantType/destroy'
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