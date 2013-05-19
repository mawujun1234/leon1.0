Ext.define("Leon.common.ux.BaseAjax",{
	extend:"Ext.data.proxy.Ajax",
	alias: 'proxy.bajax',
	actionMethods: { read: 'POST' },
	timeout :600000,
	headers:{ 'Accept':'application/json;'},
	reader:{
			type:'json',
			root:'root'
	}
	,writer:{
		type:'json'
	}
});