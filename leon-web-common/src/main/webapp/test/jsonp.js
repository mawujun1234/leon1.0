Ext.onReady(function(){
	Ext.get('test').load({
	    url: 'aa.js',
	    scripts: true,
	    params: {
	        id: 1
	    }
	});
});