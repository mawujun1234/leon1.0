Ext.onReady(function(){
	var panel=Ext.create('Ext.panel.Panel',{
		region:'center',
		renderTo:'funTree'
	});
	var contentEditor=Ext.create('Ext.panel.Panel',{
		width:800,
		split:true,
		region:'east',
		html:'<h3>UEditor - 完整示例</h3><p class="note">注：线上演示版上传图片功能一次只能上传一张，涂鸦功能不能将背景和图片合成，而下载版没有限制</p>' +
				'<div><script id="ueEditor" type="text/plain"></script></div>',
		renderTo:'contentEditor',
		listeners:{
			afterrender:function(){
				var ueEditor = UE.getEditor('ueEditor');
				//window.isFirstResize=true;
				//console.log(2);
			}
		}
	});
	
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'border',
		items:[panel,contentEditor]
//		listeners:{
//			resize:function(){
//				console.log(11);
//				if(!window.isFirstResize){
//					//buildUe();
//				}	
//				window.isFirstResize=false;
//			}
//		}
	});
	
	/**
	 * ueditor不会自动变化大小，监听这个就可以了
	 */
	function buildUe() {
		var content=getContent();
		var tmpUe = util.$G("ueEditor");//, isCheck = util.$G('J_switch').checked,;
		var  option = {};
		tmpUe.removeChild(tmpUe.firstChild);

		setTimeout(function() {
			new UE.ui.Editor(option).render('ueEditor');
		}, 60);
		setContent(content);
	} 
	
	function getContent() {
       UE.getEditor('ueEditor').getContent()
    }
    function setContent(content,isAppendTo) {
        //var arr = [];
        //arr.push("使用editor.setContent('欢迎使用ueditor')方法可以设置编辑器的内容");
        UE.getEditor('ueEditor').setContent(content, isAppendTo);
       // alert(arr.join("\n"));
    }
});