// JavaScript Document
$.ContextPath="http://192.168.94.20:8084";
$.ajaxSetup({
	headers:{
		Accept:"application/json;"
	},
	complete: function(jqXHR,textStatus ){
		//alert(jqXHR.responseText);
		var data=$.parseJSON(jqXHR.responseText);
		if(!data.success){
			if(data.reasons.code=="noLogin"){
				/**$.mobile.pageContainer.pagecontainer('change', "login.html", {
					transition: 'flow',
					reload    : true
					//role: "dialog" 
				});
				**/
				location.href="login.html";
				return;
			}
		}
		
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