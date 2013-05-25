Ext.define("Leon.common.ux.BaseAjax",{
	extend:"Ext.data.proxy.Ajax",
	alias: 'proxy.bajax',
	actionMethods: { read: 'POST' },
	timeout :600000,
	headers:{ 'Accept':'application/json;'},
//	constructor: function(config) {
//		config.reader={
//			type:'json',
//			root:'root',
//			successProperty:'success',
//			totalProperty:'total'
//				
//		};
//		config.writer={
//			type:'json'
//		};
//		
//		this.callParent(config);	
//	}
	reader:{
			type:'json',
			root:'root',
			successProperty:'success',
			totalProperty:'total'
			
	}
	,writer:{
		type:'json'
	}
});