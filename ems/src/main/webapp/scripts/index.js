   function ShowMessage(title,msg){
	   Ext.Msg.alert(title, msg);
	   setTimeout(function(){Ext.Msg.hide();},2000);
   }

   Ext.define('Ext.winwow.TopWindow', {
		extend  : 'Ext.window.Window',  
	    alias:'widget.topwin',  
	    layout:'fit',
	    height: 309,
		width: 500,
	    modal:true,
	    constrain: true,
	    autoRender:true,
	    closeAction:'hide',
	    listeners:{
	    	render:function(w,o) {
	    		var doc=Ext.top.getCmp('doc-panel');
	    		var tab=doc.getActiveTab( ) ;
	    		tab.pushWin(w);
	    	}
	    }
	});



Ext.require([
   'Ext.tab.*',
   'Ext.ux.TabCloseMenu',
   'Ext.ux.TabScrollerMenu'
]);

/**
 * 管理后台界面 stchou 2011.6
 */
// 开始调用
Ext.onReady(function() {    
	Ext.tip.QuickTipManager.init();
	
	var naviPanel=Ext.create("Ext.ms.navi.Panel",{
		contentEl:'div-left',
	    title:'常用功能',
	    region:'west',
	    //url:Ext.ContextPath+"/getnavi.do",
	    url:Ext.ContextPath+"/nav/getleftnavi.do",
	    width: 200,
	    clickAction:showtab
	});
	
	var currentItem;
	var docPanel=Ext.create("Ext.ms.doc.Panel",{
		contentEl:'div-center',
		id:'doc-panel',
	    region:'center',
        plugins: [Ext.create('Ext.ux.TabCloseMenu', {
            extraItemsTail: [
                '-',
                {
                    text: 'Closable',
                    checked: true,
                    hideOnClick: true,
                    handler: function (item) {
                		item.setDisabled(false);
                    	currentItem.tab.setClosable(item.checked);
                    }
                }
            ],
            listeners: {
                aftermenu: function () {
                    currentItem = null;
                },
                beforemenu: function (menu, item) {
                    var menuitem = menu.child('*[text="Closable"]');
                    currentItem = item;
                    if(item.getId()!='welcome-panel'){
                    	if(menuitem.isDisabled()){
                    		menuitem.setDisabled(false);
                    	}
                    	menuitem.setChecked(item.closable);
                    }else{
                    	if(!menuitem.isDisabled()){
                    		menuitem.setDisabled(true);
                    	}
                    }
                }
            }
        }),{
            ptype: 'tabscrollermenu',
            maxText  : 15,
            pageSize : 7
        }]
	});
	
	var navigate = function(panel, direction){
	    // This routine could contain business logic required to manage the navigation steps.
	    // It would call setActiveItem as needed, manage navigation button state, handle any]
	    // branching logic that might be required, handle alternate actions like cancellation
	    // or finalization, etc.  A complete wizard implementation could get pretty
	    // sophisticated depending on the complexity required, and should probably be
	    // done as a subclass of CardLayout in a real-world implementation.
	    var layout = panel.getLayout();
	    layout[direction]();
	    Ext.getCmp('move-prev').setDisabled(!layout.getPrev());
	    Ext.getCmp('move-next').setDisabled(!layout.getNext());
	};

	//var toolbar=Ext.create('Ext.ms.header.Toolbar',{naviUrl:Ext.ContextPath+'/showmenu.do',naviAction:showtab});
	var toolbar=Ext.create('Ext.ms.header.Toolbar',{naviUrl:Ext.ContextPath+'/nav/loadNaviT.do',naviAction:showtab});
	
	Ext.create('Ext.container.Viewport', {
	    layout: {
	        type: 'vbox',
	        align: 'stretch'
	    },
	    items: [{
	        height:100,
	        contentEl: 'div-header',
	        autoRender:true,
	        //title: '登记薄联网平台-v1.0',
	        collapsible: false,
	        bbar:toolbar
	    },{
	    	flex:1,
			layout : 'border',
			items : [ docPanel, naviPanel, {
				xtype : 'footerInfo',
				region : "south"
			} ]
		} ]
	});
	
    docPanel.on('tabchange', function(panel,newTab,oldTab,options){
    	   var cls=newTab.cclass;
    	   naviPanel.selectClass(cls);
    });
    window.docPanel=docPanel;
    toolbar.showMemu();
    function showtab(){
        docPanel.loadPage(this.link,this.id,this.text,this.loadType);
    }
    
 	setTimeout(function(){
 	    Ext.get('loading').remove();
 	    Ext.get('loading-mask').fadeOut({remove:true});
 	}, 250);
  
});


