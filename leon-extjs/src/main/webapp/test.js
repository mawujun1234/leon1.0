
	    	Ext.define('serviceGroup', {
	    	    extend: 'Ext.data.Model',
	    	    fields: [
	    	        {name: 'id', type: 'string'},
	    	        {name: 'text',  type: 'string'},
	    	        {name: 'link',   type: 'string'},
	    	        {name: 'loadType', type: 'string'},
	    	        {name:'items'}
	    	    ]
	    	});
	    	
	    	store = Ext.create('Ext.data.Store', {
	    	    model: 'serviceGroup',
	    	    proxy: {
	    	        type: 'ajax',
	    	        url : "test.json"
	    	    },
	    	    autoLoad: true
	    	});
	    	var config=[];
	    	store.addListener('load',function(s,r,success,o,e){
				//remove(0);
				if(r.length>0){
					config=r[0].get('items');
					console.log(config.length);
					console.log(config[0]);
					console.log(config[0].id);
					config.push( {
						"id" : "button_sl_3_6",
						"text" : "设备报废1111",
						"link" : "pages/equipscrapped.jsp",
						"width" : 100,
						"flex" : 2
					});
					r[0].save({
						url:'aa.do'
					});
				}
	    	});