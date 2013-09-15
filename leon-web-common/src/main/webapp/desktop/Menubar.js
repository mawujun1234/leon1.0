Ext.define('Leon.desktop.Menubar', {
    extend: 'Ext.toolbar.Toolbar', // TODO - make this a basic hbox panel...

    requires: [
        'Ext.button.Button',
        'Ext.resizer.Splitter',
        'Ext.menu.Menu',
        'Leon.desktop.QuickStartPanel'
    ],
    itemId:"desktop_menubar",
    layout: {
         overflowHandler: 'Menu'
    },
    style:{
       'z-index': 88888
    },
    initComponent: function () {
        var me = this;
        
		var newItems=[];
		var quickStartPanel=Ext.create('Leon.desktop.QuickStartPanel',{
			quickstarts:me.quickstarts,
			desktop:me.desktop
		});
		delete me.quickstarts;
		
		newItems.push({text:'',iconCls:'icons_arrow_inout',tooltip:'快速启动',
			listeners:{
				mouseover:function(btn){
					//quickStartPanel.btn=btn;
					btn.maybeShowMenu();
				}
			},
			menu:{
				xtype: 'menu',
	            plain: true,
	            items: quickStartPanel
			}
		});
		
		me.items=newItems.concat(me.items);
		//me.items=newItems;


        me.callParent();
    }
});