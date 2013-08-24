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
		var iframe_id=me.id+"_iframe_";
		
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
		},
		{
		    type:'help',
		    tooltip: '获取帮助',
		    handler: function(event, toolEl, panel) {
		        // show help here
		    	alert('还没有做!');
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
		if(me.execuMethod){
			iframe.on('load',function(){
            	me.execuIframeMethod(me.execuMethod);
            	delete me.execuMethod;
           });
		}
		
		
		this.callParent();
		me.on("afterrender",function(win){
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
		//alert(windoww[execuMethod.methodName]);
		windoww[execuMethod.methodName](execuMethod.params);
	}

});










