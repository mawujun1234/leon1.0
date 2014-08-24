// JavaScript Document
$.ContextPath="http://localhost:8084";
$.ajaxSetup({
	headers:{
		Accept:'application/json;'
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