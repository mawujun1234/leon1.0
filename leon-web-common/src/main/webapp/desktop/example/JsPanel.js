/**
 * 这是个例子
 */
Ext.define('Leon.desktop.example.JsPanel', {
    extend: 'Ext.panel.Panel',
	//type : 'IframeWindow',
	url : '',//iframe链接的地址
	desktop:null,//desktop对象
	menuItem:null,//菜单按钮的链接
	isWindow:true,

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










