Ext.define("Ems.mgr.User",{
	extend:"Ext.data.Model",
	fields:[
		{name:'id',type:'auto'},
		{name:'loginName',type:'string'},
		{name:'password',type:'string'},
		{name:'name',type:'string'},
		{name:'deleted',type:'boolean'},
		{name:'deletedDate',type:'date'},
		{name:'enable',type:'boolean'},
		{name:'locked',type:'boolean'},
		{name:'accountExpired',type:'boolean'},
		{name:'createDate',type:'date'},
		{name:'expireDate',type:'date'},
		{name:'lastLoginDate',type:'date'}
	],
	proxy:{
		//type:'bajax',
		type:'ajax',
		actionMethods: { read: 'POST' },
		timeout :600000,
		headers:{ 'Accept':'application/json;'},
		reader:{
				type:'json',
				rootProperty:'root',
				successProperty:'success',
				totalProperty:'total'
				
		}
		,writer:{
			type:'json'
		},
		api:{
			read:Ext.ContextPath+'/user/query.do',
			load : Ext.ContextPath+'/user/load.do',
			create:Ext.ContextPath+'/user/create.do',
			update:Ext.ContextPath+'/user/update.do',
			destroy:Ext.ContextPath+'/user/destroy.do'
		}
	}
});