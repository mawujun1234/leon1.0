Ext.define('Leon.desktop.Window', {
    extend: 'Ext.Window',
	//type : 'IframeWindow',
	//url : '',
	shrinkWrap :true,
	resizable:true,
	plain : true,
	loadMask:true,
	layout:'fit',
	constrainHeader:true,
	//constrain:true,
	maximizable :true,
	minimizable:true,
	maximized:false,
	closeAction:'close',
	title:'测试1',
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
		var iframe=Ext.create('Ext.ux.IFrame',{
			  src:'http://www.baidu.com',
			  title:'google'
		});
		me.items=[iframe];
		this.callParent();
	}

});










