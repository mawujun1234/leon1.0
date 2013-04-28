Ext.define('Leon.desktop.Window', {
    extend: 'Ext.window.Window',
	//type : 'IframeWindow',
	url : '',//iframe链接的地址
	
	shrinkWrap :true,
	resizable:true,
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
		
		me.tools=[{
		    type:'refresh',
		    tooltip: '刷新',
		    // hidden:true,
		    handler: function(event, toolEl, toolPanel){
		        // refresh logic
		    	//console.dir(panel.getEl().dom);
		    	//panel.getEl().query('#111',true).document.location.reload();
		    	me.el.mask("正在加载...");
		    	var innerDocument=null;
		    	if (document.all){//IE
		                innerDocument = document.getElementById(iframe_id).document;
		        }else{//Firefox    
		                innerDocument = document.getElementById(iframe_id).contentDocument;
		        }
		    	innerDocument.location.reload();;
		    	
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
		this.html='<iframe id="'+iframe_id+'" onload="javascript:window.desktop.removeWindowMask();" src="'+me.url+'" frameborder=0 width="100%" height="100%"></iframe>';
		
		me.on("render",function(win){
			win.el.mask("正在加载...");
			
		});
//		this.items = [{        
//			xtype : "component",         
//			autoEl : {     
//				id:'111',
//				tag : "iframe",   
//				scrolling:"auto",
//				//id:this.id+ "_iframe",
//				frameborder:0,
//				width:'100%',
//				height:'100%',
//				shim : false,
//				src:me.url					
//			}     
//		}];
		
//		var iframe=Ext.create('Ext.ux.IFrame',{
//			  src:me.url
//			//src:'http://www.baidu.com'
//			  //title:'google'
//		});
//		me.items=[iframe];
		
		this.callParent();
	}

});










