Ext.define("Ems.check.Check",{
	extend:"Ext.data.Model",
	fields:[
		{name:'id',type:'string'},
		{name:'status',type:'string'},
		{name:'status_name',type:'string'},
		{name:'creater',type:'string'},
		{name:'createDate',type:'string'},
		{name:'completer',type:'string'},
		{name:'completeDate',type:'string'},
		{name:'task_id',type:'string'},
		
		{name:'pole_id',type:'string'},
		{name:'pole_name',type:'string'}
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
			///rootProperty:'root',
			//successProperty:'success',
			//totalProperty:'total'		
		},
		api:{
			read:Ext.ContextPath+'/check/load.do',
			//load : Ext.ContextPath+'/check/load.do',
			create:Ext.ContextPath+'/check/create.do',
			update:Ext.ContextPath+'/check/update.do',
			destroy:Ext.ContextPath+'/check/destroy.do'
		}
	}
});