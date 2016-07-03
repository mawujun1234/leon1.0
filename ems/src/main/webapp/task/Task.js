Ext.define("Ems.task.Task",{
	extend:"Ext.data.Model",
	idProperty:'id',
	fields:[
		{name:'id',type:'string'},
		{name:'approveDate',type:'string'},
		{name:'hitchDate',type:'string'},//, dateFormat: 'Y-m-d H:i:s'
		{name:'createDate',type:'string'},
		{name:'customer_id',type:'string'},
		{name:'hitchReason',type:'string'},
		{name:'hitchType',type:'string'},
		{name:'memo',type:'string'},
		{name:'pole_id',type:'string'},
		{name:'startHandDate',type:'string'},
		{name:'status',type:'string'},
		{name:'submitDate',type:'string'},
		{name:'completeDate',type:'string'},
		{name:'type',type:'string'},
		{name:'workunit_id',type:'string'},
		
		{name:'installoutType_name',type:'string'},
		{name:'pole_address',type:'string'},
		{name:'customer_name',type:'string'},
		{name:'pole_code',type:'string'},
		{name:'pole_name',type:'string'},
		{name:'workunit_name',type:'string'},
		{name:'status_name',type:'string'},
		{name:'type_name',type:'string'},
		{name:'equipList',type:'string'}
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