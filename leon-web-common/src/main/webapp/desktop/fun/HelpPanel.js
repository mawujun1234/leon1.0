Ext.define('Leon.desktop.fun.HelpPanel',{
	extend:'Ext.panel.Panel',
	config:{
		funId:null
	},
	listeners:{
			afterrender:function(panel){
				panel.ueEditor = UE.getEditor('ueEditor');
			},
			activate:function(panel){
				panel.updateContent();
				panel.isFocus=true;
			},
			deactivate:function(panel){
				panel.isFocus=false;
			}
	},
	 //html:'<h3>UEditor - 完整示例</h3><p class="note">注：线上演示版上传图片功能一次只能上传一张，涂鸦功能不能将背景和图片合成，而下载版没有限制</p>' +
	//			'<div><script id="ueEditor" type="text/plain"></script></div>',
	html:'<iframe name="hidFrame" style="display:none"></iframe>' +
			'<form target="hidFrame" action="/fun/helpCreateOrupdate" method="post">' +
			'<input type="hidden" name="funId" id="funId"/>' +
			'<input type="submit" name="submit" value="保    存"/>' +
			'<script id="ueEditor" type="text/plain">sdfsdfsdfdsf</script></form>',
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
		if(!me.getFunId() || !me.isFocus){
			//Ext.Msg.alert('消息',"功能id不能为空");
			return;
		}
		me.ueEditor.disable();
		ueEditor.focus(true);
		Ext.Ajax.request({
			url : '/fun/helpGetContent',
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