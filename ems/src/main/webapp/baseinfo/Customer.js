//Ext.defineModel("Ems.baseinfo.Customer",{
Ext.define("Ems.baseinfo.Customer",{
	extend:"Ext.data.Model",
	//idProperty:'id',
	fields:[
		{name:'id',type:'string'},
		{name:'memo',type:'string'},
		{name:'name',type:'string'},
		{name:'parent_id',type:'string'},
		{name:'status',type:'bool'},
		{name:'type',type:'int'}
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
			read:Ext.ContextPath+'/customer/load.do',
			create:Ext.ContextPath+'/customer/create.do',
			update:Ext.ContextPath+'/customer/update.do',
			destroy:Ext.ContextPath+'/customer/destroy.do'
		}
	}
});