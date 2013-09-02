Ext.define('Leon.desktop.help.HelpWindow',{
	extend: 'Ext.window.Window',
	//config:{
		funId:null,
	//},
	id:'helpContent_jsp',
	isWindow: true,
	constrainHeader: false,
	minimizable: false,
	maximizable: true
	,closeAction:'close',
	title:'帮助文档',
	iconCls:'icons_help',
	width:400,
	height:500,
	layout:'fit',
	//url:null,
	initComponent: function () {
       var me = this;
       var iframe_id=me.id+"_iframe_";
       
       var url=me.getUrl(me.funId);
       //alert(url);
	   var iframe=Ext.create('Ext.ux.IFrame',{
			id:iframe_id,
			closable: true,
            layout: 'fit',
            loadMask:'正在加载，请稍候...',
            
            border: false

			//src:me.url
			//src:'http://www.baidu.com'
		});

		me.items=[iframe];

		this.callParent();
		me.on("afterrender",function(win){
			iframe.load(url);
		});
		desktop.configWindow(this,{url:'/help/helpLookContent'});
	},
	getUrl:function(funId){
		return '/help/helpLookContent?funId='+funId;
	},
	reload:function(funId){
		this.items.getAt(0).load(this.getUrl(funId));
	}
});