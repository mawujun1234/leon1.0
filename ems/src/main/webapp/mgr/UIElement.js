Ext.define("Ems.mgr.UIElement",{
	extend:"Ext.data.Model",
	fields:[
		{name:'text',type:'string'},
		{name:'code',type:'string'},
		{name:'navigation_id',type:'string'},
		{name:'memo',type:'string'},
		{name:'id',type:'string'}
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
			read:Ext.ContextPath+'/uIElement/load.do',
			//load : Ext.ContextPath+'/uIElement/load.do',
			create:Ext.ContextPath+'/uIElement/create.do',
			update:Ext.ContextPath+'/uIElement/update.do',
			destroy:Ext.ContextPath+'/uIElement/destroy.do'
		}
	}
});