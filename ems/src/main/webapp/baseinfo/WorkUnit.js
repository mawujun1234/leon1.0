//Ext.defineModel("Ems.baseinfo.WorkUnit",{
Ext.define("Ems.baseinfo.WorkUnit",{
	extend:"Ext.data.Model",
	idProperty:'id',
	fields:[
		{name:'id',type:'string'},
		{name:'memo',type:'string'},
		{name:'name',type:'string'},
		{name:'status',type:'bool'},
		{name:'loginName',type:'string'},
		{name:'password',type:'string'}
	],
	proxy:{
		type:'ajax',
		actionMethods: { read: 'POST' },
		timeout :600000,
		headers:{ 'Accept':'application/json;'},
		writer:{
			type:'json',
			writeRecordId:true,
			writeAllFields:true
		},
		reader:{
			type:'json'
//			,rootProperty:'root',
//			successProperty:'success',
//			totalProperty:'total'		
		},
		api:{
			read:Ext.ContextPath+'/workUnit/load.do',
			create:Ext.ContextPath+'/workUnit/create.do',
			update:Ext.ContextPath+'/workUnit/update.do',
			destroy:Ext.ContextPath+'/workUnit/destroy.do'
		}
	}
});