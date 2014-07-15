Ext.require('Leon.desktop.help.HelpWindow');
Ext.define('Leon.desktop.Window', {
    extend: 'Ext.window.Window',
	//type : 'IframeWindow',
	url : '',//iframe链接的地址
	
	shrinkWrap :true,
	resizable:true,
	isWindow: true,
	plain : true,
	loadMask:true,
	layout:'fit',
	constrainHeader:true,
	//constrain:true,
	maximizable :true,
	minimizable:true,
	//maximized:false,
	closeAction:'close',
	//title:'测试1',
	initComponent:function(){
		var me=this;
		var iframe_id=me.menuItemId+"_iframe_";
		
//		me.tools=[{
//		    type:'refresh',
//		    tooltip: '刷新',
//		    // hidden:true,
//		    handler: function(event, toolEl, toolPanel){
//		        // refresh logic
//		    	//console.dir(panel.getEl().dom);
//		    	//panel.getEl().query('#111',true).document.location.reload();
//		    	me.el.mask("正在加载...");
//		    	var innerDocument=null;
//		    	if (Ext.isIE ){//IE本来的判断是 docuemnt.all
//		                innerDocument = document.getElementById(iframe_id).document;
//		        }else{//Firefox    
//		                innerDocument = document.getElementById(iframe_id).contentDocument;
//		        }
//		    	innerDocument.location.reload();;
//		    	
//		    }
//		},
//		{
//		    type:'help',
//		    tooltip: '获取帮助',
//		    handler: function(event, toolEl, panel) {
//		        // show help here
//		    	alert('还没有做!');
//		    }
//		}];
//		this.html='<iframe id="'+iframe_id+'" onload="javascript:window.desktop.removeWindowMask();" src="'+me.url+'" frameborder=0 width="100%" height="100%"></iframe>';
//		
//		me.on("render",function(win){
//			win.el.mask("正在加载...");	
//		});
		
//		me.on("render",function(win){
//			iframe.el.mask("正在加载...");
//		});

		
		me.tools=[{
		    type:'refresh',
		    tooltip: '刷新',
		    // hidden:true,
		    handler: function(event, toolEl, toolPanel){  	
		    	iframe.load(me.url);
		    }
		},{
			type:'plus',
			 tooltip: '发送到快速启动',
		    // hidden:true,
		    handler: function(event, toolEl, toolPanel){  	
		    	Ext.Ajax.request({
		    		url:Ext.ContextPath+'/desktop/createQuickstart',
		    		method:'POST',
		    		params:{menuItemId:me.menuItemId},
		    		success:function(response){
		    			//刷新快捷方式中的数据
		    			window.desktop.reloadQuickStart();
		    		}
		    	});
		    }
		},
		{
		    type:'help',
		    tooltip: '获取帮助',
		    handler: function(event, toolEl, panel) {
		    	if(!me.funId){
		    		Ext.Msg.alert('消息','该菜单没有和功能进行挂钩，所以没有帮助文档!');
		    		return;
		    	}

		    	var win=Ext.WindowManager.get("helpContent_jsp");
		    	if(win){
		    		win.reload(me.funId);
		    		win.show();
		    		return;
		    	}
				var win=Ext.create('Leon.desktop.help.HelpWindow',{funId:me.funId});
				win.show();
		    }
		}];
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
		
		//进行两个窗口之间的交互
		if(me.execuMethod){
			iframe.on('load',function(){
            	me.execuIframeMethod(me.execuMethod);
            	delete me.execuMethod;
           });
		}
		
		
		this.callParent();
		me.on("afterrender",function(win){
			var url=me.url;
			if(me.url.lastIndexOf('?')!=-1){
				url+="&funId="+me.funId
			} else {
				url+="?funId="+me.funId
			}
			me.url=url;
			iframe.load(me.url);
		});
		

	},
	/**
	 * 执行iframe里的方法
	 */
	execuIframeMethod:function(execuMethod){
		//alert(me.execuMethod.method);
		var iframe=this.items.getAt(0);
		var windoww=iframe.getWin(); //iframe里的window对象
		//var  func=eval(me.execuMethod.method);
		//alert(execuMethod.methodName);
		//alert(windoww.queryCustomer);
		windoww[execuMethod.methodName](execuMethod.params);
	}

});










