// JavaScript Document
$.ServerPath="http://192.168.1.100:8084";
$.ajaxSetup({
	jsonp: "jsonpCallback",//使用浏览器进行测试的时候用的，如果安装到手机，就注释掉
	dataType:'jsonp',//使用浏览器进行测试的时候用的，如果安装到手机就注释掉
	type:'POST',
	headers:{
		Accept:"application/json;"
	},
	/**
	complete: function(jqXHR,textStatus ){
		alert(jqXHR.responseText);
		var data=$.parseJSON(jqXHR.responseText);
		if(!data.success){
			if(data.reasons.code=="noLogin"){
				location.href="login.html";
				return;
			}
		}
		
	},
	**/
	error:function(jqxhq){  
		alert('网络异常或服务停止了');  
		$.hideLoader(); 
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
