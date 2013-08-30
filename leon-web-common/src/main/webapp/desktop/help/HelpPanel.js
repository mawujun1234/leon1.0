Ext.define('Leon.desktop.help.HelpPanel',{
	extend:'Ext.panel.Panel',
	config:{
		funId:null
	},
	listeners:{
			afterrender:function(panel){
				//实例化编辑器
			    var options = {
//			        imageUrl:UEDITOR_HOME_URL + "../yunserver/yunImageUp.php",
//			        imagePath:"http://",
//			
//			        scrawlUrl:UEDITOR_HOME_URL + "../yunserver/yunScrawlUp.php",
//			        scrawlPath:"http://",
//			
//			        fileUrl:UEDITOR_HOME_URL + "../yunserver/yunFileUp.php",
//			        filePath:"http://",
//			
//			        catcherUrl:UEDITOR_HOME_URL + "php/getRemoteImage.php",
//			        catcherPath:UEDITOR_HOME_URL + "php/",
//			
//			        imageManagerUrl:UEDITOR_HOME_URL + "../yunserver/yunImgManage.php",
//			        imageManagerPath:"http://",
//			
//			        snapscreenHost:'ueditor.baidu.com',
//			        snapscreenServerUrl:UEDITOR_HOME_URL + "../yunserver/yunSnapImgUp.php",
//			        snapscreenPath:"http://",
//			
//			        wordImageUrl:UEDITOR_HOME_URL + "../yunserver/yunImageUp.php",
//			        wordImagePath:"http://", //
//			
//			        getMovieUrl:UEDITOR_HOME_URL + "../yunserver/getMovie.php",
//			
//			        lang:/^zh/.test(navigator.language || navigator.browserLanguage || navigator.userLanguage) ? 'zh-cn' : 'en',
//			        langPath:UEDITOR_HOME_URL + "lang/",
//			
//			        webAppKey:"9HrmGf2ul4mlyK8ktO2Ziayd",
//			        initialFrameWidth:860,
//			        initialFrameHeight:420,
			    	//imagePath:"/ueditor/jsp",
			        focus:true
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
			}
	},
	 //html:'<h3>UEditor - 完整示例</h3><p class="note">注：线上演示版上传图片功能一次只能上传一张，涂鸦功能不能将背景和图片合成，而下载版没有限制</p>' +
	//			'<div><script id="ueEditor" type="text/plain"></script></div>',
	html:'<iframe name="hidFrame" style="display:none"></iframe>' +
			'<form target="hidFrame" action="/help/helpCreateOrupdate" method="post">' +
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