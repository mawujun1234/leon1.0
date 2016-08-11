Ext.define("Ems.task.Overtime",{
	extend:"Ext.data.Model",
	idProperty:'id',
	fields:[
		{name:'id',type:'string'},
		{name:'handling',type:'int'},
		{name:'read',type:'int'}
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
			read:Ext.ContextPath+'/overtime/load.do',
			//load : Ext.ContextPath+'/user/load.do',
			create:Ext.ContextPath+'/overtime/create.do',
			update:Ext.ContextPath+'/overtime/update.do',
			destroy:Ext.ContextPath+'/overtime/destroy.do'
		}
	}
});