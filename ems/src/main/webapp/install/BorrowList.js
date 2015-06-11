Ext.defineModel("Ems.install.BorrowList",{
	extend:"Ext.data.Model",
	idProperty:'id',
	fields:[
		{name:'id',type:'string'},
		{name:'borrow_id',type:'string'},
		{name:'ecode',type:'string'},
		{name:'isReturn',type:'bool'},
		{name:'memo',type:'string'},
		{name:'returnDate',type:'date', dateFormat: 'Y-m-d'}
	],
	associations:[
	]
});