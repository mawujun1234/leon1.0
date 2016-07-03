Ext.defineModel("Ems.baseinfo.Area",{
	extend:"Ext.data.Model",
	idProperty:'id',
	fields:[
		{name:'id',type:'string'},
		{name:'memo',type:'string'},
		{name:'name',type:'string'},
		{name:'workunit_id',type:'string'},
		{name:'workunit_name',type:'string'}
	]
//	proxy:{
//		//type:'bajax',
//		type:'ajax',
//		actionMethods: { read: 'POST' },
//		timeout :600000,
//		headers:{ 'Accept':'application/json;'},
//		reader:{
//				type:'json',
//				rootProperty:'root',
//				successProperty:'success',
//				totalProperty:'total'
//				
//		}
//		,writer:{
//			type:'json'
//		},
//		api:{
//			read:Ext.ContextPath+'/area/query.do',
//			load : Ext.ContextPath+'/area/load.do',
//			create:Ext.ContextPath+'/area/create.do',
//			update:Ext.ContextPath+'/area/update.do',
//			destroy:Ext.ContextPath+'/area/destroy.do'
//		}
//	}
});