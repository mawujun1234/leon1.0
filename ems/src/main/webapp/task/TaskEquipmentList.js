Ext.define("Ems.task.TaskEquipmentList",{
	extend:"Ext.data.Model",
	idProperty:'id',
	fields:[
		{name:'id',type:'string'},
		{name:'ecode',type:'string'},
		{name:'task_id',type:'string'},
		{name:'type',type:'string'},
		{name:'scanDate',type:'string'},
		
		{name:'type_name',type:'string'},
		{name:'subtype_name',type:'string'},
		{name:'prod_name',type:'string'},
		{name:'brand_name',type:'string'},
		{name:'supplier_name',type:'string'},
		{name:'prod_style',type:'string'}

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
			read:Ext.ContextPath+'/task/query.do',
			load : Ext.ContextPath+'/task/load.do',
			create:Ext.ContextPath+'/task/create.do',
			update:Ext.ContextPath+'/task/update.do',
			destroy:Ext.ContextPath+'/task/destroy.do'
		}
	}
});