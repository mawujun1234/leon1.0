Ext.onReady(function(){
	var panel=Ext.create('Ext.panel.Panel',{
		region:'center',
		split:true,
		renderTo:'funTree'
	});
	var contentEditor=Ext.create('Ext.panel.Panel',{
		width:700,
		split:true,
		region:'east',
		html:'<div><script id="editor" type="text/plain" style="height:300px"></script></div>',
		renderTo:'contentEditor',
		listeners:{
			afterrender:function(){
				var ueEditor = UE.getEditor('editor');
				contentEditor.canResize=true;
			}
//			resize:function(){alert(contentEditor.canResize);
//				if(contentEditor.canResize){
//					buildUe();
//				}
//				
//			}
		}
	});
	
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'border',
		items:[panel,contentEditor]
	});
	
	/**
	 * ueditor不会自动变化大小，监听这个就可以了
	 */
	function buildUe() {
		//var content=getContent();
		var tmpUe = util.$G("editor");//, isCheck = util.$G('J_switch').checked,;
		var  option = {};
		tmpUe.removeChild(tmpUe.firstChild);
//		if (configName == "sourceEditor") {
//			option[configName] = isCheck ? "codemirror" : "textarea";
//		} else if (configName == "contextMenu") {
//			if (!isCheck)
//				option[configName] = [];
//		} else {
//			option[configName] = isCheck;
//		}
		setTimeout(function() {
			new UE.ui.Editor(option).render('editor');
		}, 60);
		//setContent(content);
	} 
	
	function getContent111() {
        var arr = [];
        arr.push("使用editor.getContent()方法可以获得编辑器的内容");
        arr.push("内容为：");
        //arr.push(ueEditor.getContent());
        arr.push(UE.getEditor('editor').getContent());
        alert(arr.join("\n"));
    }
    function setContent111(isAppendTo) {
        var arr = [];
        arr.push("使用editor.setContent('欢迎使用ueditor')方法可以设置编辑器的内容");
        ueEditor.setContent('欢迎使用ueditor', isAppendTo);
        alert(arr.join("\n"));
    }
});