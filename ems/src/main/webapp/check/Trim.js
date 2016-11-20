Ext.define("Ems.check.Trim",{
	extend:"Ext.data.Model",
	fields:[
		{name:'id',type:'string'},
		{name:'ecode',type:'string'},
		{name:'orginal_id',type:'string'},
		{name:'orginal_type',type:'string'},
		{name:'target_id',type:'string'},
		{name:'target_type',type:'string'},
		{name:'check_id',type:'string'},
		{name:'creater',type:'string'},
		{name:'createDate',type:'string'},
		{name:'trimType',type:'string'}
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
			read:Ext.ContextPath+'/trim/load.do',
			//load : Ext.ContextPath+'/trim/load.do',
			create:Ext.ContextPath+'/trim/create.do',
			update:Ext.ContextPath+'/trim/update.do',
			destroy:Ext.ContextPath+'/trim/destroy.do'
		}
	}
});