Ext.define('Leon.desktop.Desktop', {
	//extend: 'Ext.container.Viewport',
	extend: 'Ext.panel.Panel',
	requires: [
	     'Leon.desktop.Menubar',
	     'Leon.desktop.Taskbar',
	     'Leon.desktop.Window'
	],
	layout:'fit',
	border:false,
	
	windows:null,
	lastActiveWindow:null,
    initComponent: function () {
        var me = this;
        me.windows = new Ext.util.MixedCollection();

        var menubar=Ext.create('Leon.desktop.Menubar',{
        	items:[{iconCls: 'menubar-index-button',xtype:'button',text:'桌面'}
        	,{text:'测试',handler:function(){
        		me.createWindow({
        			//id:1111,
        			title:'测试',
        			url:'http://www.baidu.com'
        		});
        		
        	}}]
        });
        var taskbar=Ext.create('Leon.desktop.Taskbar',{
        	
        });
        me.tbar=menubar;
        me.bbar=taskbar;
        
        //因为到时候可能做到 任务栏可以设置到左边或右边
        me.menubar=menubar;
        me.taskbar=taskbar;

        me.callParent();
    },
    createWindow:function(config){
    	//如果已经存在这个窗口，就显示已经存在的窗口
    	if(this.windows.containsKey(config.url)){
    		this.restoreWindow(this.windows.get(config.url));
    		return;
    	}

		var me = this,  cfg = Ext.applyIf(config || {}, {
            stateful: false,
            isWindow: true,
            constrainHeader: true,
            minimizable: true,
            maximizable: true
            
            ,height:me.getViewHeight()*0.9
            ,width:me.getViewWidth()*0.9
            //maximized:false,
        });

		var win=Ext.create('Leon.desktop.Window',cfg);
		me.add(win);
	    me.windows.add(config.url,win);
	
	    win.taskButton = me.taskbar.addTaskButton(win);
	    win.animateTarget = win.taskButton.el;
	
	    win.on({
	        activate: me.updateActiveWindow,
	        beforeshow: me.updateActiveWindow,
	        deactivate: me.updateActiveWindow,
	        minimize: me.minimizeWindow,
	        destroy: me.onWindowClose
	        ,scope: me
	    });
	
//	    win.on({
//	        afterrender: function () {
//	            win.dd.xTickSize = me.xTickSize;
//	            win.dd.yTickSize = me.yTickSize;
//	
//	            if (win.resizer) {
//	                win.resizer.widthIncrement = me.xTickSize;
//	                win.resizer.heightIncrement = me.yTickSize;
//	            }
//	        },
//	        single: true
//	    });
	
	    // replace normal window close w/fadeOut animation:
	    win.doClose = function ()  {
	        win.doClose = Ext.emptyFn; // dblclick can call again...
	        win.el.disableShadow();
	        win.el.fadeOut({
	            listeners: {
	                afteranimate: function () {
	                    win.destroy();
	                }
	            }
	        });
	    };
		
		win.show();
		return win;
    },
    updateActiveWindow: function () {
        var me = this, activeWindow = me.getActiveWindow(), last = me.lastActiveWindow;
        if (activeWindow === last) {
            return;
        }

        if (last) {
            if (last.el.dom) {
                last.addCls(me.inactiveWindowCls);
                last.removeCls(me.activeWindowCls);
            }
            last.active = false;
        }

        me.lastActiveWindow = activeWindow;

        if (activeWindow) {
            activeWindow.addCls(me.activeWindowCls);
            activeWindow.removeCls(me.inactiveWindowCls);
            activeWindow.minimized = false;
            activeWindow.active = true;
        }

        me.taskbar.setActiveButton(activeWindow && activeWindow.taskButton);
    },
    minimizeWindow: function(win) {
        win.minimized = true;
        win.hide();
    },
    restoreWindow: function (win) {
        if (win.isVisible()) {
            win.restore();
            win.toFront();
        } else {
            win.show();
        }
        return win;
    },
    getDesktopZIndexManager: function () {
        var windows = this.windows;
        // TODO - there has to be a better way to get this...
        return (windows.getCount() && windows.getAt(0).zIndexManager) || null;
    },

    getWindow: function(id) {
        return this.windows.get(id);
    },
    getActiveWindow: function () {
        var win = null,
            zmgr = this.getDesktopZIndexManager();

        if (zmgr) {
            // We cannot rely on activate/deactive because that fires against non-Window
            // components in the stack.

            zmgr.eachTopDown(function (comp) {
                if (comp.isWindow && !comp.hidden) {
                    win = comp;
                    return false;
                }
                return true;
            });
        }

        return win;
    },
    onWindowClose: function(win) {
        var me = this;
        me.windows.remove(win);
        me.taskbar.removeTaskButton(win.taskButton);
        me.updateActiveWindow();
    },
    
    
    /**
     * 还要减掉 菜单和任务栏的高度
     * @return {}
     */
    getViewHeight : function(){
      //return (Ext.lib.Dom.getViewHeight() - this.gettaskbarHeight());
    	var me=this;
    	return (Ext.getBody( ).getHeight() - me.menubar.getHeight( ) - me.taskbar.getHeight( ));
    },
    getViewWidth : function(){
       return Ext.getBody( ).getWidth();
    },

    getWinWidth : function(){
        var width = this.getViewWidth();
        return width < 800 ? 800 : width;
    },

    getWinHeight : function(){
        var height = this.getViewHeight();
        return height < 480 ? 480 : height;
    }
    
});