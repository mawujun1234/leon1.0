Ext.define("Ems.task.PatrolTaskType",{
	extend:"Ext.data.Model",
	fields:[
		{name:'id',type:'string'},
		{name:'name',type:'string'}
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
			read:Ext.ContextPath+'/patrolTaskType/load.do',
			//load : Ext.ContextPath+'/patrolTaskType/load.do',
			create:Ext.ContextPath+'/patrolTaskType/create.do',
			update:Ext.ContextPath+'/patrolTaskType/update.do',
			destroy:Ext.ContextPath+'/patrolTaskType/destroy.do'
		}
	}
});