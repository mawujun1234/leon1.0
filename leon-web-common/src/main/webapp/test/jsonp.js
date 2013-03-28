Ext.onReady(function(){
	Ext.get('test').load({
	    url: 'aa.js',
	    scripts: true,
	    loadMask:true,
	    params: {
	        id: 1
	    }
	});
});