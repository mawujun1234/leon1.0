Ext.defineModel("Leon.panera.customer.Customer",{
	extend:"Ext.data.Model",
	idProperty:'id',
	fields:[
		{name:'id',type:'string'},
		{name:'address',type:'string'},
		{name:'businessPhase',type:'string'},
		{name:'buyMoney',type:'int'},
		{name:'country',type:'auto'},
		{name:'customerType',type:'int'},
		{name:'empNum',type:'int'},
		{name:'expYear',type:'int'},
		{name:'followNum',type:'string'},
		{name:'inquiryContent',type:'string'},
		{name:'inquiryDate',type:'date', dateFormat: 'Y-m-d'},
		{name:'moq',type:'int'},
		{name:'name',type:'string'},
		{name:'paymentTerms',type:'int'},
		{name:'price',type:'int'},
		{name:'proportion',type:'int'},
		{name:'quality',type:'int'},
		{name:'star',type:'int'},
		{name:'website',type:'string'},
		{name:'customerProperty_id',type:'auto'},
		{name:'customerSource_id',type:'auto'}
	],
	associations:[
			{type:'belongsTo',model: 'Leon.panera.customerProperty.CustomerProperty',associatedName:'Customerproperty'},
			{type:'belongsTo',model: 'Leon.panera.customerSource.CustomerSource',associatedName:'Customersource'}
	]
});