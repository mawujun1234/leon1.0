Ext.require('Ext.ux.IFrame');
Ext.define('Leon.desktop.Desktop', {
	//extend: 'Ext.container.Viewport',
	extend: 'Ext.panel.Panel',
	requires: [
	     'Leon.desktop.Menubar',
	     'Leon.desktop.Taskbar',
	     'Leon.desktop.Window',
	     'Leon.desktop.Wallpaper'
	],
	layout:'fit',
	border:false,
	menuItems:'',//初始化时，传递过来的菜单，根据这菜单数据生成菜单
	
	xTickSize: 1,
    yTickSize: 1,
    
	windows:null,
	lastActiveWindow:null,
    initComponent: function () {
        var me = this;
        me.windows = new Ext.util.MixedCollection();
       

 		me.menubarDock=me.menubarDock||"top";
 		me.taskbarDock=me.taskbarDock||"bottom";
        //alert(me.menubarDock);
 		//me.menubarDock="bottom";
        var menuItems=['-','-'];
        //me.returnMenuItem(menuItems,me.menuItems);
        
        me.menuCache={};//用来根据url快速导航到按钮的
        me.initMenuItemEvent(me.menuItems);
        menuItems=menuItems.concat(me.menuItems);
        delete me.menuItems;
        
        var switchUsers=null;//me.switchUsers;
        if(me.switchUsers){
        	switchUsers={items:[]};
        	for(var i=0;i<me.switchUsers.length;i++){
        		var userName=me.switchUsers[i]
        		switchUsers.items.push({
        			text:userName,
        			iconCls:'icons_user_b',
        			handler:function(){
			        	location.href="/j_spring_security_switch_user?j_username="+userName;
			        }
        		});
        	}
        }
       me.switchUsers=switchUsers;
             
        var menubar=Ext.create('Leon.desktop.Menubar',{
        	dock:me.menubarDock,
        	
        	items:menuItems
        });

        var taskbar=Ext.create('Leon.desktop.Taskbar',{
        	dock: me.taskbarDock,
        	desktop:me
        	//authMsg:me.authMsg
           // items:[{text:'当前用户:'+me.authMsg,xtype:'label'},'-','-']
        });
        
        
        
        function addHideAction(toolbar,dock){
        	toolbar.on("afterrender",function(toolbar, eOpts) {            		
            		toolbar.getEl( ).on("mouseover",function(e,htmlElement){
            			if(dock=="left" || dock=="right"){
            				toolbar.setWidth(toolbar.prevWidth);
            			} else {
            				toolbar.setHeight(toolbar.prevWidth);
            			}
			        	
			        });
			        toolbar.getEl( ).on("mouseout",function(e,htmlElement){
			        	//toolbar.setWidth(3);
			        	if(dock=="left" || dock=="right"){
            				toolbar.setWidth(3);
            			} else {
            				toolbar.setHeight(3);
            			}
			        });
			        var task = new Ext.util.DelayedTask(function(){
						  //toolbar.setWidth(3);
			        	if(dock=="left" || dock=="right"){
            				toolbar.setWidth(3);
            			} else {
            				toolbar.setHeight(3);
            			}
					});
					task.delay(1000);
            });
            toolbar.on("resize",function( toolbar, width, height, oldWidth, oldHeight, eOpts) {
            		//toolbar.prevWidth=toolbar.getWidth();	
            	if(dock=="left" || dock=="right"){
            		if(width>15){
            			toolbar.prevWidth=toolbar.getWidth();	
            		}
            	} else {
            		if(height>15){
            			toolbar.prevWidth=toolbar.getHeight();	
            		}
            	}
            });
        }
        
        if(me.menubarAutoHide){
        	addHideAction(menubar,me.menubarDock);
        }
        if(me.taskbarAutoHide){
        	addHideAction(taskbar,me.taskbarDock);
        }
        
        delete me.authMsg;
        delete me.switchUsers;
        //me.tbar=menubar;
        //me.bbar=taskbar;
        this.dockedItems =[taskbar,menubar];
        
        //因为到时候可能做到 任务栏可以设置到左边或右边
        me.menubar=menubar;
        me.taskbar=taskbar;
        
        me.windowMenu = new Ext.menu.Menu(me.createWindowMenu());
        me.taskbar.windowMenu = me.windowMenu;
        
        me.items = [
            { xtype: 'wallpaper', id: me.id+'_wallpaper' }
        ];

        me.callParent();
        window.desktop=me;
        
        
        var wallpaper = me.wallpaper;
        me.wallpaper = me.items.getAt(0);
        if (wallpaper) {
            me.setWallpaper(wallpaper, me.wallpaperStretch);
        }
        
       
    },
    showSwitchUserWin:function(){
    	var win=Ext.create('Leon.SwitchUserWin',{
    	
    	});
    	win.show();
    },
    /**
     * 主要用于两个窗口Window之间进行协同的
     * execuMethod 是一个对象，例如
     * {
     * 	methodName:'queryByName',
     *  params:{a:1,b:2}
     * }
     * @param {} jspUrl
     * @param {} execuMethod
     */
	showWindowByJspPath:function(jspUrl,execuMethod){
		var me=this;
		//如果已经存在这个窗口，就显示已经存在的窗口
    	if(this.windows.containsKey(jspUrl)){
    		var win=this.windows.get(jspUrl);
    		win.execuIframeMethod(execuMethod);
    		return this.restoreWindow(win);
    	}

		//根据jspUrl获取菜单的Item
		Ext.Ajax.request({
			url:'/app/desktop/queryMenuItem',
			method:'POST',
			params:{jspUrl:jspUrl},
			success:function(response){
				var obj=Ext.decode(response.responseText);
				var btn=obj.root;
				win=me.createWindow({
		        	title:btn.text,
		        	url:btn.url,
		        	iconCls:btn.iconCls,
		        	execuMethod:execuMethod
		        });
		        //
		        //创建成功后，在里面建立接收参数。
			}
		});
	},
	initMenuItemEvent:function(menuItems){
		var me=this;
		for(var i=0;i<menuItems.length;i++){
        		var model=menuItems[i];
        		
        		model.itemId=model.id;
        		delete model.id;

        		model.link_url=model.url;
        		delete model.url;
        		
//        		model.listeners={
//        			mouseover:function(btn){
//        				btn.maybeShowMenu();
//        			}
////        			mouseout:function(btn){
////        				btn.hideMenu();
////        			}
//        		}
        		
        		if(model.menu && model.menu.items && model.menu.items.length>0){
        			me.initMenuItemEvent(model.menu.items);
        		} else {
        			model.plugins=[{ptype:'menuplugin',scripts:model.scripts}];
        			//model.xtype= 'splitbutton';
        			//alert(menu.url+"===");
	        		model.handler=function(btn){
		        		me.createWindow({
		        			title:btn.text,
		        			funId:btn.funId,
		        			url:btn.link_url,
		        			iconCls:btn.iconCls
		        		});
	        		}
        		}
        	}
	},

    createWindow:function(config){
    	if(!config.url){
    		return;
    	}
    	//如果已经存在这个窗口，就显示已经存在的窗口
    	if(this.windows.containsKey(config.url)){
    		return this.restoreWindow(this.windows.get(config.url));
    	}
    	
    	var me = this,win;
//    	//一弹出就醉大话
//    	config.maximized=true
    	
    	//alert(config.url.lastIndexOf('.js')+'=='+config.url.length);
    	//config.url='/aaa/aa.js';
    	if(config.url.lastIndexOf('.js')==config.url.length-3){
			//就使用js来作为url的话，就实例化这个url，
    		//接着判断这个对象是否是window对象，如果是，就直接show，否则就封装在window中
    		Ext.Loader.loadScript({
    			url:'..'+config.url,
    			onLoad:function(options){
    				//console.dir(options);
    				var className=config.url.substring(1,config.url.length-3);
    				className='Leon.'+className.split('/').join('.');
    				var cfg = Ext.applyIf(config || {}, {
			            //stateful: false,
			            //isWindow: true,
			            constrainHeader: true,
			            desktop:me
			            //minimizable: true,
			           // maximizable: true  
			            //,height:me.getViewHeight()*0.9
			            //,width:me.getViewWidth()*0.9
			        });
    				var obj=Ext.create(className,cfg);
    				//判断是否要使用window进行展示
    				if(!obj.isWindow){
    					obj.run();
    					return null;
    				}
    				if(obj instanceof Ext.window.Window){
    					obj.setTitle(config.title);
    					//还要添加进管理
    					me.configWindow(obj,cfg);
    					obj.show();
    					return obj;
    				} else {
    					
    					//还要使用window进行包裹
    					var cfg = Ext.applyIf(config || {}, {
				            stateful: false,
				            isWindow: true,
				            constrainHeader: true,
				            minimizable: true,
				            maximizable: true
				            ,layout:'fit'
				            ,closeAction:'close'
				            ,items:[obj]
				            //,height:me.getViewHeight()*0.9
				            //,width:me.getViewWidth()*0.9
				            
				        });
    					win=Ext.create('Ext.window.Window',cfg);
    					me.configWindow(win,cfg);

						win.show();
						return win;
    				}
    				//alert(className);
    			},
    			onError:function(){
    				alert('url文档加载失败!');
    				return;
    			}
    		});
    		return;
		} else {
			//alert(3);
			//使用jsp，html等的时候，使用iframe进行加载
			var cfg = Ext.applyIf(config || {}, {
	            stateful: false,
	            isWindow: true,
	            constrainHeader: true,
	            minimizable: true,
	            maximizable: true
	            ,closeAction:'close'
	            //,height:me.getViewHeight()*0.9
	            //,width:me.getViewWidth()*0.9
	            ,maximized:true
	        });
			win=Ext.create('Leon.desktop.Window',cfg);
			me.configWindow(win,cfg);
			win.show();
			return win;
		}

    },

    
        //对win进行配置，并和taskbar进行关联
    configWindow:function(win,config){
    	var me=this;
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
	
		win.on({
            boxready: function () {
                win.dd.xTickSize = me.xTickSize;
                win.dd.yTickSize = me.yTickSize;

                if (win.resizer) {
                    win.resizer.widthIncrement = me.xTickSize;
                    win.resizer.heightIncrement = me.yTickSize;
                }
            },
            single: true
        });

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
    },
    /**
     * 设置当改变大小的时候，变化的步长
     * @param {} xTickSize
     * @param {} yTickSize
     */
    setTickSize: function(xTickSize, yTickSize) {
        var me = this,
            xt = me.xTickSize = xTickSize,
            yt = me.yTickSize = (arguments.length > 1) ? yTickSize : xt;

        me.windows.each(function(win) {
            var dd = win.dd, resizer = win.resizer;
            dd.xTickSize = xt;
            dd.yTickSize = yt;
            resizer.widthIncrement = xt;
            resizer.heightIncrement = yt;
        });
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
    //一般用于弹出window的 点刷新按钮的时候
    removeWindowMask: function () {
       //.break
        var me = this, activeWindow = me.getActiveWindow();
        activeWindow.el.unmask();
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
    	var menubarHeight=0;
    	var taskbarHeight=0;
    	if(me.menubar.dock=="top" || me.menubar.dock=="bottom"){
    		menubarHeight=me.menubar.getHeight( ) ;
    	}
    	if(me.taskbar.dock=="top" || me.taskbar.dock=="bottom"){
    		taskbarHeight=me.taskbar.getHeight( ) ;
    	}

    	return (Ext.getBody( ).getHeight() - menubarHeight- taskbarHeight);
    },
    getViewWidth : function(){
       //return Ext.getBody( ).getWidth();
    	var me=this;
    	var menubarWidth=0;
    	var taskbarWidth=0;
    	if(me.menubar.dock=="left" || me.menubar.dock=="right"){
    		menubarWidth=me.menubar.getWidth( ) ;
    	}
    	if(me.taskbar.dock=="left" || me.taskbar.dock=="right"){
    		taskbarWidth=me.taskbar.getWidth( ) ;
    	}
    	return Ext.getBody( ).getWidth() - menubarWidth- taskbarWidth;
    },

    getWinWidth : function(){
        var width = this.getViewWidth();
        //alert(width);
        return width < 800 ? 800 : width;
    },

    getWinHeight : function(){
        var height = this.getViewHeight();
        return height < 480 ? 480 : height;
    },
    //------------------------------------------------------
    // Window context menu handlers
    createWindowMenu: function () {
        var me = this;
        return {
//        	style:{
//        		"z-index":99999
//        	},
            defaultAlign: 'br-tr',
            items: [
                { text: '恢复', handler: me.onWindowMenuRestore, scope: me },
                { text: '最小化', handler: me.onWindowMenuMinimize, scope: me },
                { text: '最大化', handler: me.onWindowMenuMaximize, scope: me },
                '-',
                { text: '关闭', handler: me.onWindowMenuClose, scope: me }
            ],
            listeners: {
                beforeshow: me.onWindowMenuBeforeShow,
                hide: me.onWindowMenuHide,
                scope: me
            }
        };
    },
   

    onWindowMenuBeforeShow: function (menu) {
        var items = menu.items.items, win = menu.theWin;
        items[0].setDisabled(win.maximized !== true && win.hidden !== true); // Restore
        items[1].setDisabled(win.minimized === true); // Minimize
        items[2].setDisabled(win.maximized === true || win.hidden === true); // Maximize
    },

    onWindowMenuClose: function () {
        var me = this, win = me.windowMenu.theWin;

        win.close();
    },

    onWindowMenuHide: function (menu) {
        Ext.defer(function() {
            menu.theWin = null;
        }, 1);
    },

    onWindowMenuMaximize: function () {
        var me = this, win = me.windowMenu.theWin;

        win.maximize();
        win.toFront();
    },

    onWindowMenuMinimize: function () {
        var me = this, win = me.windowMenu.theWin;

        win.minimize();
    },
    /**
     * 所有窗口最小化
     */
    onAllWindowMinimize:function(){
    	this.windows.each(function(item){
	        item.hide();
	    });
    },

    onWindowMenuRestore: function () {
        var me = this, win = me.windowMenu.theWin;

        me.restoreWindow(win);
    },

    //------------------------------------------------------
    getWallpaper: function () {
        return this.wallpaper.wallpaper;
    },
    setWallpaper: function (wallpaper, stretch) {
        this.wallpaper.setWallpaper(wallpaper, stretch);
        return this;
    },
    reload:function(){
    	Ext.getBody().mask("正在重新加载，请稍候...");
    	location.reload();
    },
    setBarDocked:function(menubarDock,taskbarDock){
    	//this.getDockedComponent("desktop_menubar").setDocked(menubardock);
    	//this.getDockedComponent("desktop_taskbar").setDocked(taskbardock);
    	
    	this.menubarDock=menubarDock;
    	this.taskbarDock=taskbarDock;
	    //this.reload();
    },
    setBarHide:function(menubarAutoHide,taskbarAutoHide){
    	this.menubarAutoHide=menubarAutoHide;
    	this.taskbarAutoHide=taskbarAutoHide;
	    //this.reload();
    },
    createOrupdateDesktopConfig:function(paramsAAA,callback){
    	var me=this;
    	var params={
        		wallpaper:me.getWallpaper(),
        		wallpaperStretch:me.wallpaper.stretch,
        		menubarDock:me.menubarDock,
        		taskbarDock:me.taskbarDock,
        		menubarAutoHide:me.menubarAutoHide,
        		taskbarAutoHide:me.taskbarAutoHide
        }
        params=Ext.applyIf(params,paramsAAA);
        //console.dir(params);
    	//发送到后台保存
        Ext.Ajax.request({
        	url:"/app/desktop/createOrUpdate",
        	method:"POST",
        	params:params,
        	success:function(){
        		if(callback instanceof Function){
        			callback(params);
        		}
        	}
        });
    }
});