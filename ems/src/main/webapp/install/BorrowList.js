Ext.defineModel("Ems.install.BorrowList",{
	extend:"Ext.data.Model",
	idProperty:'id',
	fields:[
		{name:'id',type:'string'},
		{name:'borrow_id',type:'string'},
		{name:'ecode',type:'string'},
		{name:'isReturn',type:'bool'},
		{name:'memo',type:'string'},
		{name:'returnDate',type:'date', dateFormat: 'Y-m-d'},
		{name:'borrowListType',type:'string'},
		
		{name:'borrowListType_name',type:'string'},
		{name:'subtype_name',type:'string'},
		{name:'prod_name',type:'string'},
		{name:'prod_spec',type:'string'},
		{name:'brand_name',type:'string'},
		{name:'supplier_name',type:'string'},
		{name:'style',type:'string'}
	],
	associations:[
	]
});