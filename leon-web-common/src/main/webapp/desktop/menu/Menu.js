Ext.defineModel("Leon.desktop.menu.Menu",{
	extend:"Ext.data.Model",
	fields:[
		{name:'id',type:'auto'},
		{name:'text',type:'string'},
		{name:'rootId',type:'string'}
	]
//	proxy:{
//		type:'bajax',
//		api:{
//			read:'/menu/queryAll',
//			load : '/menu/get',
//			create:'/menu/create',
//			update:'/menu/update',
//			destroy:'/menu/destroy'
//		}
//	}
	
});
