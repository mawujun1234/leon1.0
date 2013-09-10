Ext.define('Leon.desktop.Menubar', {
    extend: 'Ext.toolbar.Toolbar', // TODO - make this a basic hbox panel...

    requires: [
        'Ext.button.Button',
        'Ext.resizer.Splitter',
        'Ext.menu.Menu'
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

//        me.startMenu = new Ext.ux.desktop.StartMenu(me.startConfig);
//
//        me.quickStart = new Ext.toolbar.Toolbar(me.getQuickStart());
//
//        me.windowBar = new Ext.toolbar.Toolbar(me.getWindowBarConfig());
//
//        me.tray = new Ext.toolbar.Toolbar(me.getTrayConfig());
//
//        me.items = [
//            {
//                xtype: 'button',
//                cls: 'ux-start-button',
//                iconCls: 'ux-start-button-icon',
//                menu: me.startMenu,
//                menuAlign: 'bl-tl',
//                text: me.startBtnText
//            },
//            me.quickStart,
//            {
//                xtype: 'splitter', html: '&#160;',
//                height: 14, width: 2, // TODO - there should be a CSS way here
//                cls: 'x-toolbar-separator x-toolbar-separator-horizontal'
//            },
//            //'-',
//            me.windowBar,
//            '-',
//            me.tray
//        ];

        me.callParent();
    }
});