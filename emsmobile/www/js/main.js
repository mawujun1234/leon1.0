// JavaScript Document
//$.ServerPath="http://localhost:8084";
$.ServerPath="http://172.16.3.4:8084";
$.ecodeLength=16;
$.ajaxSetup({
	//jsonp: "jsonpCallback",//使用浏览器进行测试的时候用的，如果安装到手机，就注释掉
	//dataType:'jsonp',//使用浏览器进行测试的时候用的，如果安装到手机就注释掉
	type:'POST',
	headers:{
		Accept:"application/json;"
	},
	/****/
	complete: function(jqXHR,textStatus ){
		var contentType=jqXHR.getResponseHeader("Content-Type");
		if(contentType.indexOf("application/json")!=-1){
			//统一处理后台返回的信息
			var data=$.parseJSON(jqXHR.responseText);
			if(!data.success && data.reasons){
				if(data.reasons.code=="noLogin"){
					location.href="login.html";
					return;
				}
			}
		}
	},
	
	error:function(jqXHR){  
		alert('网络异常或服务停止了');  
		$.hideLoader(); 
	}  
	
});
$(function() {
	if(location.href.indexOf("login.html")!=-1){
		return;
	}
	if(!sessionStorage.getItem("user")){
		location.href="login.html";
		return;
	}
});
//将form的值序列化为json
(function($){
	$.fn.serializeJson=function(){
		var serializeObj={};
		var array=this.serializeArray();
		var str=this.serialize();
		$(array).each(function(){
			if(serializeObj[this.name]){
				if($.isArray(serializeObj[this.name])){
					serializeObj[this.name].push(this.value);
				}else{
					serializeObj[this.name]=[serializeObj[this.name],this.value];
				}
			}else{
				serializeObj[this.name]=this.value;	
			}
		});
		return serializeObj;
	};
})(jQuery);

//显示加载器
function showLoader(text) {
    //显示加载器.for jQuery Mobile 1.2.0
    $.mobile.loading('show', {
        text: text?text:'加载中...', //加载器中显示的文字
        textVisible: true, //是否显示文字
        theme: 'a',        //加载器主题样式a-e
        textonly: false,   //是否只显示文字
        html: ""           //要显示的html内容，如图片等
    });
}
$.showLoader=showLoader;

//隐藏加载器.for jQuery Mobile 1.2.0
function hideLoader()
{
    //隐藏加载器
    $.mobile.loading('hide');
}
$.hideLoader=hideLoader;


$.getUrlParam = function(string) {  
        var obj = new Object();  
        if (string.indexOf("?") != -1) {  
            var string = string.substr(string.indexOf("?") + 1);  
            var strs = string.split("&");  
            for (var i = 0; i < strs.length; i++) {  
                var tempArr = strs[i].split("=");  
                obj[tempArr[0]] = tempArr[1];  
            }  
        }  
        return obj;  
}  
