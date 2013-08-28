Ext.define('Leon.desktop.fun.HelpPanel',{
	extend:'Ext.panel.Panel',
	config:{
		funId:null
	},
	listeners:{
			afterrender:function(panel){
				var ueEditor = UE.getEditor('ueEditor');
				var hidd=document.getElementById("funId");
				hidd.value=panel.getFunId();
			}
	},
	 //html:'<h3>UEditor - 完整示例</h3><p class="note">注：线上演示版上传图片功能一次只能上传一张，涂鸦功能不能将背景和图片合成，而下载版没有限制</p>' +
	//			'<div><script id="ueEditor" type="text/plain"></script></div>',
	html:'<iframe name="hidFrame" style="display:none"></iframe>' +
			'<form target="hidFrame" action="/fun/helpCreate" method="post">' +
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
	}
});