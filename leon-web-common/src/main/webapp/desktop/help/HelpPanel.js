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
			    	
			    	fileUrl:URL+"jsp/fileUp.jsp" ,
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

				//// editor 是编辑器实例，container 是编辑器容器，修改它的宽度就可以了。
				//editor.container.style.width="500px"; // 设置编辑器宽度为 500px
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
	html:'<iframe name="hidFrame" style="display:none"></iframe>' +
			'<form target="hidFrame" action="/help/helpCreateOrupdate" method="post" >' +
			'<input type="hidden" name="funId" id="funId"/>' +
			'<input type="submit" name="submit" value="保    存"/>' +
			'<script id="ueEditor" type="text/plain"></script></form>',
	initComponent: function () {
       var me = this;
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
			url : '/help/helpGetContent?_dc='+new Date(),
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