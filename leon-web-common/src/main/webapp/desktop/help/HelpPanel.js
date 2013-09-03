Ext.define('Leon.desktop.help.HelpPanel',{
	extend:'Ext.panel.Panel',
	config:{
		funId:null
	},
	listeners:{
			afterrender:function(panel){
				//实例化编辑器，看ueditor.config.js文件
				var URL="/desktop/help/";
				//alert(location.hostname);
				//alert(location.port);
			    var options = {
			    	
			    	imagePath: "/" ,
					imageUrl:URL+"jsp/imageUp.jsp",
					
					imageManagerUrl:"/desktop/help/jsp/imageManager.jsp",
			    	imageManagerPath:"/doc/" ,
			    	
			    	catcherUrl:URL +"jsp/getRemoteImage.jsp" ,
			    	catcherPath:"/",

			    	fileUrl:URL+"jsp/fileUp.jsp",
			    	filePath:"/" ,
			    	
			    	scrawlUrl:URL+"jsp/scrawlUp.jsp" ,
			    	scrawlPath:"/" ,
			    	
			    	snapscreenHost: location.hostname ,
			    	snapscreenServerUrl: URL +"jsp/imageUp.jsp",
			    	snapscreenPath: "/",
			    	snapscreenServerPort: location.port ,
			    	
			    	wordImageUrl:URL + "jsp/imageUp.jsp" ,
			    	wordImagePath:"/" ,
			        focus:true,
			        autoHeightEnabled:false
			        //,initialFrameHeight:panel.getHeight( ) 
			        //,minFrameHeight:panel.getHeight( ) 
			    };

				panel.ueEditor = UE.getEditor('ueEditor',options);


			},
			activate:function(panel){
				panel.isActivate=true;
				panel.updateContent();
			},
			deactivate:function(panel){
				panel.isActivate=false;
			},
			resize:function(panel,width,height,oldWidth,oldHeight){
				panel.ueEditor.container.style.width=width+"px";
				panel.ueEditor.iframe.style.width=width+"px";
				panel.ueEditor.setHeight(height-150);
            	//panel.ueEditor.body.style.height = height + "px";
            	panel.ueEditor.body.style.overflow = "auto";
				//alert(panel.ueEditor.container.style.height);
				
			}
	},
	 //html:'<h3>UEditor - 完整示例</h3><p class="note">注：线上演示版上传图片功能一次只能上传一张，涂鸦功能不能将背景和图片合成，而下载版没有限制</p>' +
	//			'<div><script id="ueEditor" type="text/plain"></script></div>',
	html:'<iframe name="hideFrame" style="display:none"></iframe>' +
			'<form id="helpContent_hideFrame"  target="hideFrame" action="/help/helpCreateOrupdate" method="post" >' +
			'<input type="hidden" name="funId" id="funId"/>' +
			//'<input type="submit" name="submit" value="保    存"/>' +
			'<script id="ueEditor" type="text/plain"></script></form>',
	initComponent: function () {
       var me = this;
       me.tbar=[{
       	text:'保存',
       	iconCls:'form-save-button',
       	handler:function(){
       		if(me.ueEditor.hasContents()){ //此处以非空为例
       			var form=Ext.getDom("helpContent_hideFrame");//document.getElementById("helpContent_hideFrame");//;//("form[target=hideFrame]");
			    me.ueEditor.sync();   //同步内容
			    form.submit();   //提交Form
			}
       	}
       }];
	   me.callParent();
	},
	setFunId:function(funId){
		this.funId=funId;
		var hidd=document.getElementById("funId");
		if(hidd){
			hidd.value=funId;
			
		}
		this.updateContent();
		Ext.util.Cookies.set('help_funId_folder',funId);
		
		
//		还需要修改后台的代码，解决在FF下session不能共享的问题
//		help_funId_folder加上去，是为了解决Uploader上传附件的时候的路径问题
//		IpFilter中通过JSESSIONIDForSwfUser参数过滤了swfupload的请求
//		在attachment.html文件中加上下面两行
//		use_query_string:true,//这一行是必须要有的
//		post_params:{JSESSIONIDForSwfUser:editor.JSESSIONIDForSwfUser,"help_funId_folder":editor.help_funId_folder},
		this.ueEditor.help_funId_folder=funId;
		this.ueEditor.JSESSIONIDForSwfUser=sessionId;
	},
	updateContent : function() {
		var me=this;
		if(!me.getFunId() || !me.isActivate){
			//Ext.Msg.alert('消息',"功能id不能为空");
			return;
		}

		me.ueEditor.disable();
		ueEditor.focus(true);
		Ext.Ajax.request({
			url : '/app/help/helpGetContent?_dc='+new Date(),
			params : {
				funId : me.getFunId()
			},
			success : function(response) {
				var conetent = Ext.decode(response.responseText).root;
				me.ueEditor.setContent(conetent);
				me.ueEditor.enable();
			}
		});
	}
});