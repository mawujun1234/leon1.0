Ext.define("Leon.desktop.constant.Constant",{
	extend:"Ext.data.Model",
	fields:[
		{name:'id',type:'string'},
		{name:'text',type:'string'},
		{name:'remark',type:'string'},
		{name:'discriminator',type:'string'},


		{name:'constantType_id',type:'string'}
	],
	associations:[
		{type:'hasMany',model:'Leon.desktop.constant.ConstantItem',name : 'constantItemes'},
		{type:'belongsTo',model:'Leon.desktop.fun.ConstantType',associatedName:'ConstantType'}
	]
	,proxy:{
		actionMethods: { read: 'POST' },
		timeout :600000,
		headers:{ 'Accept':'application/json;'},
		type:'ajax',
		api:{
			read:'/constant/query',
			get : '/constant/get',
			create:'/constant/create',
			update:'/constant/update',
			destroy:'/constant/destroy'
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