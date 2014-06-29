Ext.defineModel("Leon.panera.customer.Followup",{
	extend:"Ext.data.Model",
	idProperty:'id',
	fields:[
		{name:'id',type:'string'},
		{name:'content',type:'string'},
		{name:'createDate',type:'date', dateFormat: 'Y-m-d'},
		{name:'customer_id',type:'string'},
		{name:'feedbackContetnt',type:'string'},
		{name:'feedbackDate',type:'date', dateFormat: 'Y-m-d'},
		{name:'method',type:'string'},
		{name:'nextContent',type:'string'},
		{name:'nextDate',type:'date', dateFormat: 'Y-m-d'}
	],
	associations:[
	]
});