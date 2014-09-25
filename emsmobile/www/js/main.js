// JavaScript Document
//$.ServerPath="http://localhost:8084";
//$.ServerPath="http://172.16.3.4:8084";
$.ServerPath="";
$.ServerPort="8080";
$.ecodeLength=17;
$.ajaxSetup({
	//jsonp: "jsonpCallback",//使用浏览器进行测试的时候用的，如果安装到手机，就注释掉
	//dataType:'jsonp',//使用浏览器进行测试的时候用的，如果安装到手机就注释掉
	// contentType: "application/json", 表示请求的数据是数据流
	//dataType: 'json',  要求后台返回json，和设置了headers是同个道理
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
		var contentType=jqXHR.getResponseHeader("Content-Type");
		if(contentType.indexOf("application/json")!=-1){
			//统一处理后台返回的信息
			var data=$.parseJSON(jqXHR.responseText);
			if(!data.success && data.reasons){
				if(data.reasons.code=="noLogin"){
					location.href="login.html";
					return;
				}
			} else if(!data.success) {
				alert(data.msg); 
			}
		} else {
			alert('网络异常或服务停止了');  
		}
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
	
	var ServerIP=localStorage.getItem("ServerIP");
	var ServerPath="http://"+ServerIP+":"+$.ServerPort;
	$.ServerPath=ServerPath;
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

$(function(){
	document.addEventListener("deviceready", function(){
		document.addEventListener("backbutton", function(){
			var activePage=$(':mobile-pagecontainer').pagecontainer( "getActivePage" );
			//所有独立的html的第一个页面都取名为page_homepage，这样就可以统一处理了
			if(activePage.is('#page_homepage')){
                //navigator.app.exitApp();
				navigator.notification.confirm(
					'你确定要退出?',
					function (buttonIndex) {
					  if (buttonIndex === 1) {
						$.ajax({   
							url : $.ServerPath+"/mobile/logout.do",   
							success : function(data){
							}
						});	
						navigator.app.exitApp();
						//navigator.geolocation.clearWatch(watchID);
					  }
					},
					'退出',
					['确定','取消']
				);
            }else {
				navigator.app.backHistory();
			}
			
		}, false);//backbutton
		
		//进入后5秒后开始发送地理信息
		if(sessionStorage.getItem("user") && !sessionStorage.getItem("watchID")){
			//setTimeout(uploadGeolocation,2000);
			//setInterval(uploadGeolocation1,65000);
		}
		
		
	}, false); //deviceready
	
	function uploadGeolocation1() {
		var user=$.parseJSON(sessionStorage.getItem("user"));
		var uuid=device.uuid;
		//获取设备的地理位置
		navigator.geolocation.getCurrentPosition(
			function(position){
				//alert(position.coords.longitude);
				var params={};
				params.longitude=position.coords.longitude;
				params.latitude=position.coords.latitude;
				params.loginName=user.username;
				params.uuid=uuid;
				
				$.ajax({   
					url : $.ServerPath+"/geolocation/mobile/upload.do",
					data:params,   
					success : function(data){
					}
				});	
			}, 
			function(error){
				//PositionError.TIMEOUT
				 alert('code: '    + error.code    + '\n' +'message: ' + error.message + '\n');
			},
			{ maximumAge: 3000, timeout: 60000, enableHighAccuracy: false  }
		);	
	}
	function uploadGeolocation(){
		//sessionStorage.setItem("user",JSON.stringify(data.root));
		var user=$.parseJSON(sessionStorage.getItem("user"));
		var uuid=device.uuid;
		//获取设备的地理位置
		var watchID = navigator.geolocation.watchPosition(
			function(position){
				alert(position.coords.longitude);
				var params={};
				params.longitude=position.coords.longitude;
				params.latitude=position.coords.latitude;
				params.loginName=user.username;
				params.uuid=uuid;
				
				$.ajax({   
					url : $.ServerPath+"/geolocation/mobile/upload.do",
					data:params,   
					success : function(data){
					}
				});	
			}, 
			function(error){
				//PositionError.TIMEOUT
				 alert('code: '    + error.code    + '\n' +'message: ' + error.message + '\n');
			}, 
			{ maximumAge: 3000, timeout: 5000, enableHighAccuracy: false  }
		);	
		//用来控制应用只发送一个请求
		sessionStorage.setItem("watchID",watchID);
	}
	
});

$.tasks={
	getHitchType:function(){
		return JSON.parse(localStorage.getItem("HitchType"));
	},
	getHitchType_version:function(){
		return localStorage.getItem("HitchType_version")?localStorage.getItem("HitchType_version"):0;
	},
	updateHitchType:function(){
		$.ajax({   
			url : $.ServerPath+"/hitchType/mobile/query.do",  
			success : function(data){
				localStorage.setItem("HitchType_version",data.version);
				localStorage.setItem("HitchType",JSON.stringify(data.root));
			}
		});
	},
	getHitchReasonTpl:function(){
		
		return JSON.parse(localStorage.getItem("HitchReasonTpl"));
	},
	getHitchReasonTpl_version:function(){
		return localStorage.getItem("HitchReasonTpl_version")?localStorage.getItem("HitchReasonTpl_version"):0;
	},
	updateHitchReasonTpl:function(){
		$.ajax({   
			url : $.ServerPath+"/hitchReasonTpl/mobile/query.do",
			//data:params,   
			success : function(data){
				localStorage.setItem("HitchReasonTpl_version",data.version);
				localStorage.setItem("HitchReasonTpl",JSON.stringify(data.root));
				//alert(data.root);
				//alert(localStorage.getItem("HitchReasonTpl"));
			}
		});
	},
	updateAll:function(hitchType_version,hitchReasonTpl_version,callBack){
		$.showLoader("正在更新....");
		$.ajax({   
			url : $.ServerPath+"/hitchReasonTpl/mobile/queryAll.do",
			data:{
				hitchType_version:hitchType_version,
				hitchReasonTpl_version:hitchReasonTpl_version
			},   
			success : function(data){
				if(data.root.hitchTypes){
					localStorage.setItem("HitchType_version",data.root.hitchType_version);
					localStorage.setItem("HitchType",JSON.stringify(data.root.hitchTypes));
				}
				//alert(JSON.stringify(data.root.hitchReasonTpls));
				if(data.root.hitchReasonTpls){
					localStorage.setItem("HitchReasonTpl_version",data.root.hitchReasonTpl_version);
					localStorage.setItem("HitchReasonTpl",JSON.stringify(data.root.hitchReasonTpls));
				}
				
				if(callBack){
					callBack();
				}
				$.hideLoader();
			}
		});	
	},
	clearAll:function(){
		localStorage.removeItem("HitchType_version");
		localStorage.removeItem("HitchType");
		localStorage.removeItem("HitchReasonTpl_version");
		localStorage.removeItem("HitchReasonTpl");
	}
}

function Page(start,limit){
	this.start=start?start:0;
	this.limit=limit?limit:10;
	this.limit_reset=this.limit;
	this.getStart=function(){
		return this.start;	
	}
	this.getLimit=function(){
		return this.limit;	
	}
	this.addStart=function(add){
		this.start=this.start+add;	
	}
	this.subtractStart=function(subtract){
		this.start=this.start-subtract;
		this.start=this.start<0?0:this.start
	}	
	this.reset=function(){
		this.start=0;
		this.limit=this.limit_reset;
	}
}