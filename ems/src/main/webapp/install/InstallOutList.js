Ext.defineModel("Ems.install.InstallOutList",{
	extend:"Ext.data.Model",
	idProperty:'id',
	fields:[
		{name:'id',type:'string'},
		{name:'ecode',type:'string'},
		{name:'installOut_id',type:'string'},
		{name:'installOutType_id',type:'string'},
		{name:'installOutType_content',type:'string'},//领用类型的二级
		{name:'installOutListType',type:'string'},

		{name:'isnew',type:'bool'},
		
		{name:'installOutListType_name',type:'string'},
		{name:'subtype_name',type:'string'},
		{name:'prod_name',type:'string'},
		{name:'prod_spec',type:'string'},
		{name:'brand_name',type:'string'},
		{name:'supplier_name',type:'string'},
		{name:'style',type:'string'},
		{name:'installOutType_name',type:'string'},
		{name:'num',type:'int'},
		{name:'memo',type:'string'}
	],
	associations:[
	]
});