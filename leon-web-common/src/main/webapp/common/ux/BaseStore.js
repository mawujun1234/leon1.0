Ext.define('Leon.common.ux.BaseStore',{
	extend: 'Ext.data.Store',
	alias: 'store.bstore',
	constructor: function(config) {
		var me=this;
		alert(1);
        config = Ext.apply({
            proxy: {
                type:'ajax',
				url:me.url,
				reader:{
					type:'json',
					root:'root',
					successProperty:'success',
					totalProperty:'total'	
				}
				,writer:{
					type:'json'
				}
            }
        }, config);
        this.callParent([config]);
    }
	
});