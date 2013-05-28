Ext.defineModel("Leon.desktop.constant.Constant",{
	requires:['Leon.common.ux.BaseBelongsTo'],
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
		{type:'bbelongsto',model:'Leon.desktop.constant.ConstantType',associatedName:'constantType'}
		//{type:'belongsTo',model:'Leon.desktop.constant.ConstantType',associatedName:'ConstantType',associationKey:'constantType',foreignKey:"constantType_id"}
	]
//	,proxy:{
//		type:'bajax',
//		api:{
//			read:'/constant/query',
//			get : '/constant/get',
//			create:'/constant/create',
//			update:'/constant/update',
//			destroy:'/constant/destroy'
//		}
//	}
});