Ext.define("Leon.desktop.constant.ConstantItem",{
	extend:"Ext.data.Model",
	fields:[
		{name:'id',type:'string'},
		{name:'code',type:'string'},
		{name:'text',type:'string'},
		{name:'remark',type:'string'},
		{name:'discriminator',type:'string'},

		{name:'constant_id',type:'string'}
	],
	associations:[
		{type:'belongsTo',model:'Leon.desktop.fun.Constant',associatedName:'Constant'}
	]
	,proxy:{
		actionMethods: { read: 'POST' },
		timeout :600000,
		headers:{ 'Accept':'application/json;'},
		type:'ajax',
		api:{
			read:'/constantItem/query',
			get : '/constantItem/get',
			create:'/constantItem/create',
			update:'/constantItem/update',
			destroy:'/constantItem/destroy'
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