/**
 * 这是个例子
 */
Ext.define('Leon.desktop.JsWindow', {
    extend: 'Ext.window.Window',
	//type : 'IframeWindow',
	url : '',//iframe链接的地址
	desktop:null,//desktop对象
	
	shrinkWrap :true,
	resizable:true,
	plain : true,
	loadMask:true,
	layout:'fit',
	constrainHeader:true,
	//constrain:true,
	maximizable :true,
	minimizable:true,
	height:300,
	width:300,
	//maximized:false,
	closeAction:'close',
	//title:'测试1',
	initComponent:function(){
		var me=this;
//		this.items = [{        
//			xtype : "component",         
//			autoEl : {             
//				tag : "iframe",   
//				scrolling:"auto",
//				id:this.id+ "_iframe",
//				frameborder:0,
//				width:'100%',
//				height:'100%',
//				shim : false,
//				src:me.entryPoint					
//			}     
//		}];
		
		me.html="js测试";
		this.callParent();
	}

});










