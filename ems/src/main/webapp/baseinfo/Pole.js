//Ext.defineModel("Ems.baseinfo.Pole",{
Ext.define("Ems.baseinfo.Pole",{
	extend:"Ext.data.Model",
	//idProperty:'id',
	fields:[
		{name:'id',type:'string'},
		{name:'code',type:'string'},
		{name:'address',type:'string'},
		{name:'area',type:'string'},
		{name:'city',type:'string'},
		{name:'customer_id',type:'string'},
		{name:'latitude',type:'string'},
		{name:'longitude',type:'string'},
		{name:'latitude_orgin',type:'string'},
		{name:'longitude_orgin',type:'string'},
		{name:'name',type:'string'},
		{name:'province',type:'string'},
		{name:'status',type:'string'},
		{name:'poleType',type:'string'},
		{name:'area_id',type:'string'},
		
		{name:'workunit_id',type:'string'},
		{name:'project_id',type:'string'},
		
		{name:'poleType_name',type:'string'},
		{name:'status_name',type:'string'},
		{name:'area_name',type:'string'},
		{name:'workunit_name',type:'string'},
		{name:'customer_name',type:'string'},
		{name:'project_name',type:'string'},
		
		{name:'task_num',type:'int'}
//		{name:'task_id',type:'string'},
//		{name:'task_type',type:'string'},
//		{name:'task_status',type:'string'},
//		{name:'task_status_name',type:'string'},
//		{name:'task_memo',type:'string'}
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
			read:Ext.ContextPath+'/pole/load.do',
			create:Ext.ContextPath+'/pole/create.do',
			update:Ext.ContextPath+'/pole/update.do',
			destroy:Ext.ContextPath+'/pole/destroy.do'
		}
	}
});