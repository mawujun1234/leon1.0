Ext.require('Ext.ux.IFrame');
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
	initMenus:'',//初始化时，传递过来的菜单，根据这菜单数据生成菜单
	
	xTickSize: 1,
    yTickSize: 1,
    
	windows:null,
	lastActiveWindow:null,
    initComponent: function () {
        var me = this;
        me.windows = new Ext.util.MixedCollection();

//        var menubar=Ext.create('Leon.desktop.Menubar',{
//        	items:[{iconCls: 'menubar-index-button',xtype:'button',text:'桌面'}
//        	,{text:'测试',handler:function(){
//        		me.createWindow({
//        			//id:1111,
//        			title:'测试',
//        			url:'http://www.baidu.com'
//        		});
//        		
//        	}},{text:'测试jsWindow',handler:function(){
//        		me.createWindow({
//        			//id:1111,
//        			title:'测试jsWindow',
//        			url:'/desktop/example/JsWindow.js'
//        		});
//        		
//        	}},{text:'测试jsPanel',handler:function(){
//        		me.createWindow({
//        			//id:1111,
//        			title:'测试jsPanel',
//        			url:'/desktop/example/JsPanel.js'
//        		});
//        		
//        	}},{text:'测试menuExten,插件，子菜单',
//        		plugins:[{ptype:'menuplugin',url:'/desktop/example/MenuPluginUpdateText.js'}],
//        		handler:function(){
//        		me.createWindow({
//        			//id:1111,
//        			title:'测试menuExten',
//        			url:'/desktop/example/MenuExten.js'
//        		});
//        		
//        	}},{text:'scripts',
//        		plugins:[{ptype:'menuplugin',scripts:'function aa(){menuItem.setText(menuItem.getText( ) +"(scripts)");}'}],
//        		handler:function(){
//        		me.createWindow({
//        			//id:1111,
//        			title:'测试menuExten',
//        			url:'/desktop/example/MenuExten.js'
//        		});
//        		
//        	}}]
//        });
        
        var menuItems=[{iconCls: 'menubar-index-button',xtype:'button',text:'桌面'},'-','-'];
        me.returnMenuItem(menuItems,me.initMenus);
       
        var menubar=Ext.create('Leon.desktop.Menubar',{
        	items:menuItems
        });
       
        var taskbar=Ext.create('Leon.desktop.Taskbar',{
        	style:{
               'z-index': 99999999
            }
        });
        me.tbar=menubar;
        me.bbar=taskbar;
        
        //因为到时候可能做到 任务栏可以设置到左边或右边
        me.menubar=menubar;
        me.taskbar=taskbar;

        me.callParent();
        window.desktop=me;
    },
    /**
     * 返回菜单的组件形式
     */
    returnMenuItem:function(menuItems,initMenus){
    	var me=this;
    	//var initMenus=model.children;
    	if(initMenus){
        	for(var i=0;i<initMenus.length;i++){
        		var model=initMenus[i];
        		//alert(model.url);
        		var button={
        			text:model.text,
        			link_url:model.url,
        			pluginUrl:model.pluginUrl,
        			scripts:model.scripts,
        			iconCls:model.iconCls
        			,arrowAlign:'right'
        		};
        		
        		if(model.children && model.children.length>0){
        			button.menu={items:[]};
        			me.returnMenuItem(button.menu.items, model.children);
        		} else {
        			button.plugins=[{ptype:'menuplugin',pluginUrl:button.pluginUrl,scripts:button.scripts}];
        			//alert(menu.url+"===");
	        		button.handler=function(btn){
		        		me.createWindow({
		        			title:btn.text,
		        			url:btn.link_url,
		        			iconCls:btn.iconCls
		        		});
	        		}
        		}
        		menuItems.push(button);
        	}
        }
        return menuItems;
    },

    createWindow:function(config){
    	if(!config.url){
    		return;
    	}
    	//如果已经存在这个窗口，就显示已经存在的窗口
    	if(this.windows.containsKey(config.url)){
    		this.restoreWindow(this.windows.get(config.url));
    		return;
    	}
    	
    	var me = this,win;
    	
    	//alert(config.url.lastIndexOf('.js')+'=='+config.url.length);
    	//config.url='/aaa/aa.js';
    	if(config.url.lastIndexOf('.js')==config.url.length-2){
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
				            //maximized:false,
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
	            ,height:me.getViewHeight()*0.9
	            ,width:me.getViewWidth()*0.9
	            //maximized:false,
	        });
			win=Ext.create('Leon.desktop.Window',cfg);
			me.configWindow(win,cfg);

			if(me.lastXy){
				win.showAt(me.lastXy[0]+20,me.lastXy[1]+20);
			} else {
				win.show();
			}
			win.on("show",function(win){
				me.lastXy=win.getPosition( );
				//console.dir(me.lastXy);
			});
			
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