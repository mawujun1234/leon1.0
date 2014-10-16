Ext.defineModel("Ems.store.InStoreList",{
	extend:"Ext.data.Model",
	idProperty:'id',
	fields:[
		{name:'id',type:'string'},
		{name:'encode',type:'string'},
		{name:'inStore_id',type:'string'},
		
		{name:'subtype_name',type:'string'},
		{name:'prod_name',type:'string'},
		{name:'brand_name',type:'string'},
		{name:'supplier_name',type:'string'},
		{name:'style',type:'string'},
		{name:'num',type:'int'}
	],
	associations:[
	]
});