Ext.define('Leon.desktop.Taskbar', {
    extend: 'Ext.toolbar.Toolbar', // TODO - make this a basic hbox panel...

    requires: [
        'Ext.button.Button',
        'Ext.resizer.Splitter',
        'Ext.menu.Menu'
    ],
    itemId:"desktop_taskbar",
        	//ui: 'footer',
    layout: {
        overflowHandler: 'Menu'
    },
    style:{
        'z-index': 99999
    },
    initComponent: function () {
        var me = this;
        me.windowBar = new Ext.toolbar.Toolbar(me.getWindowBarConfig());
        me.items = [{
	        	text:'欢迎:'+me.desktop.authMsg,
	        	iconCls:'icons_user_go',
	        	xtype: 'splitbutton',
	        	handler:function(){
	        		//location.href="/j_spring_security_switch_user?j_username=11";
	        		me.desktop.showSwitchUserWin();
	        	},
	        	menu : {items:[
			        {
			        	text: '切换用户',
			        	iconCls:'icons_user_go',
			        	handler:function(){
			        		//location.href="/j_spring_security_switch_user?j_username=11";
			        		me.desktop.showSwitchUserWin();
			        	},
			        	menu:me.desktop.switchUsers
			        },
			        {
			        	text: '回到用户',
			        	iconCls:'icons_user_home',
			        	handler:function(){
			        		location.href="/j_spring_security_exit_user";
			        	}
			        },
			        {
			        	text: '登录',
			        	iconCls:'icons_door_in',
			        	handler:function(){
			        		
			        		var loginForm=Ext.create('Leon.LoginWin',{
			 					modal:true,
			 					standardSubmit:true,
			 					success:function(form, action){
			 						loginForm.close();
			 					},
			 					failure:function(form, action){
			 						Ext.MessageBox.alert("错误", "用户名或密码错误!" );
			 					}
			 				});
							loginForm.show();
			        	}
			        },
			        {
			        	text: '退出',
			        	iconCls:'icons_door_out',
			        	handler:function(){
			        		location.href="/j_spring_security_logout";
			        	}
			        }
			    ]}
	        },'-','-',
            //{text:'当前用户:'+me.desktop.authMsg,xtype:'label'},'-','-',
            me.windowBar,
            '-'
        ];
        //delete me.authMsg;

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

        //me.items=['-'];
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

        var cmp = this.windowBar.add(config);
        cmp.toggle(true);
        return cmp;
    },

    removeTaskButton: function (btn) {
        var found, me = this;
        me.windowBar.items.each(function (item) {
            if (item === btn) {
                found = item;
            }
            return !found;
        });
        if (found) {
            me.windowBar.remove(found);
        }
        return found;
    },

    setActiveButton: function(btn) {
       if (btn) {
            btn.toggle(true);
        } else {
            this.windowBar.items.each(function (item) {
                if (item.isButton) {
                    item.toggle(false);
                }
            });
        }
    },
    afterLayout: function () {
        var me = this;
        me.callParent();
        me.windowBar.el.on('contextmenu', me.onButtonContextMenu, me);
    },
    onButtonContextMenu: function (e) {
        var me = this, t = e.getTarget(), btn = me.getWindowBtnFromEl(t);
        if (btn) {
            e.stopEvent();
            me.windowMenu.theWin = btn.win;
            me.windowMenu.showBy(t);
        }
    },
    getWindowBarConfig: function () {
        return {
            flex: 1,
            border:false,
            //cls: 'ux-desktop-windowbar',
            items: [ '&#160;' ],
            layout: {
		        overflowHandler: 'Menu'
		    }
        };
    },
    getWindowBtnFromEl: function (el) {
        var c = this.windowBar.getChildByElement(el);
        return c || null;
    }
});