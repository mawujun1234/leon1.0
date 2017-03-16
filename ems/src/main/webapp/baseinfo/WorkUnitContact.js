//Ext.defineModel("Ems.baseinfo.WorkUnitContact",{
Ext.define("Ems.baseinfo.WorkUnitContact",{
	extend:"Ext.data.Model",
	idProperty:'id',
	fields:[
		{name:'id',type:'string'},
		{name:'address',type:'string'},
		{name:'contact',type:'string'},
		{name:'email',type:'string'},
		{name:'fax',type:'string'},
		{name:'mobile',type:'string'},
		{name:'phone',type:'string'},
		{name:'position',type:'string'},
		{name:'postcode',type:'string'},
		{name:'workunit_id',type:'string'}
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
			read:Ext.ContextPath+'/workUnitContact/load.do',
			create:Ext.ContextPath+'/workUnitContact/create.do',
			update:Ext.ContextPath+'/workUnitContact/update.do',
			destroy:Ext.ContextPath+'/workUnitContact/destroy.do'
		}
	}
});