/**
 * 这是个例子
 */
Ext.define('Leon.desktop.JsPanel', {
    extend: 'Ext.panel.Panel',
	//type : 'IframeWindow',
	url : '',//iframe链接的地址
	desktop:null,//desktop对象

	loadMask:true,
	layout:'fit',

	height:400,
	width:200,

	initComponent:function(){
		var me=this;
		me.title='';
		
		me.html="js测试Panel";
		this.callParent();
	}

});










