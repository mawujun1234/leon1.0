Ext.defineModel("Leon.desktop.constant.ConstantType",{
	extend:"Ext.data.Model",
	fields:[
		{name:'id',type:'auto'},
		{name:'text',type:'string'},
		{name:'remark',type:'string'},
		{name:'discriminator',type:'string'}
	],
	associations:[
		{type:'hasMany',model:'Leon.desktop.constant.Constant',name : 'constants'}
		//{type:'belongsTo',model:'Leon.desktop.fun.Fun',associatedName:'Parent'}
	]
//	,proxy:{
//		type:'bajax',
//		api:{
//			read:'/constantType/queryAll',
//			get : '/constantType/get',
//			create:'/constantType/create',
//			update:'/constantType/update',
//			destroy:'/constantType/destroy'
//		}
//	}
});