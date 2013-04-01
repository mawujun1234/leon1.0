Ext.define('Address', {
    extend: 'Ext.data.Model',
    fields: [
        { name: 'id',          type: 'int' },
        { name: 'number', type: 'string' },
        { name: 'street', type: 'string' },
        { name: 'city', type: 'string' },
        { name: 'zip', type: 'string' },
    ]
	,proxy:{
		type:'ajax',
		api:{
			read:'address.js',
			create:'',
			update:'',
			destory:''
		},
		reader:{
			type:'json',
			root:'root'
		}
	
	}
});

Ext.define('Person', {
    extend: 'Ext.data.Model',
    fields: [
        { name: 'id',   type: 'int' },
        { name: 'name', type: 'string' },
        { name: 'address_id', type: 'int'}
    ],
    // we can use the hasOne shortcut on the model to create a hasOne association
    associations: [{ type: 'hasOne', model: 'Address' }]
	,proxy:{
		type:'ajax',
		api:{
			read:'person.js',
			create:'',
			update:'',
			destory:''
		},
		reader:{
			type:'json',
			root:'root'
		}
	
	}
});


Ext.onReady(function(){
	var person = new Person({
	    id: 100,
	    address_id: 20,
	    name: 'John Smith'
	});
	
	person.getAddress(function(address, operation) {
	    // do something with the address object
	    alert(address.get('id')); // alerts 20
	}, this);
	
	Person.load(1,{
		success:function(record){
			alert(record.getAddress().get("street"));
		}
	});
});