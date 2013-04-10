Ext.define('Leon.desktop.Taskbar', {
    extend: 'Ext.toolbar.Toolbar', // TODO - make this a basic hbox panel...

    requires: [
        'Ext.button.Button',
        'Ext.resizer.Splitter',
        'Ext.menu.Menu'
    ],
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

        me.items=[{iconCls: 'taskBar-index-button',text:'任务栏：'},'-','-'];
        me.callParent();
    },
    onWindowBtnClick: function (btn) {
        var win = btn.win;

        if (win.minimized || win.hidden) {
            win.show();
        } else if (win.active) {
            win.minimize();
        } else {
            win.toFront();
        }
    },

    addTaskButton: function(win) {
        var config = {
            iconCls: win.iconCls,
            icon:win.icon,
            enableToggle: true,
            toggleGroup: 'all',
            width: 140,
            text: Ext.util.Format.ellipsis(win.title, 20),
            listeners: {
                click: this.onWindowBtnClick,
                scope: this
            },
            win: win
        };

        var cmp = this.add(config);
        cmp.toggle(true);
        return cmp;
    },

    removeTaskButton: function (btn) {
        var found, me = this;
        me.items.each(function (item) {
            if (item === btn) {
                found = item;
            }
            return !found;
        });
        if (found) {
            me.remove(found);
        }
        return found;
    },

    setActiveButton: function(btn) {
        if (btn) {
            btn.toggle(true);
        } else {
            this.items.each(function (item) {
                if (item.isButton) {
                    item.toggle(false);
                }
            });
        }
    }
});