Ext.defineModel("Leon.panera.customer.Contact",{
	extend:"Ext.data.Model",
	idProperty:'id',
	fields:[
		{name:'id',type:'string'},
		{name:'chatNum',type:'string'},
		{name:'email',type:'string'},
		{name:'fax',type:'string'},
		{name:'mobile',type:'string'},
		{name:'name',type:'string'},
		{name:'phone',type:'string'},
		{name:'position',type:'string'},
		{name:'customer_id',type:'string'},
		{name:'isDefault',type:'boolean'}
	],
	associations:[
	]
});