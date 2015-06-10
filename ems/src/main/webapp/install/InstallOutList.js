Ext.defineModel("Ems.install.InstallOutList",{
	extend:"Ext.data.Model",
	idProperty:'id',
	fields:[
		{name:'id',type:'string'},
		{name:'ecode',type:'string'},
		{name:'installOut_id',type:'string'},
		//{name:'isBad',type:'bool'},
		
		{name:'subtype_name',type:'string'},
		{name:'prod_name',type:'string'},
		{name:'prod_spec',type:'string'},
		{name:'brand_name',type:'string'},
		{name:'supplier_name',type:'string'},
		{name:'style',type:'string'},
		{name:'num',type:'int'}
	],
	associations:[
	]
});