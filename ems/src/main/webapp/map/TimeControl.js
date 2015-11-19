function TimeControl() {
	this.defaultAnchor = BMAP_ANCHOR_TOP_RIGHT;
	this.defaultOffset = new BMap.Size(150, 10);
}

TimeControl.prototype = new BMap.Control();

TimeControl.prototype.initialize = function(map) {
	// 创建一个DOM元素  
	var div = document.createElement("div");
	// 添加文字说明  
	div.setAttribute("title", "轨迹时间");
	div.setAttribute("id", "time_control");
	// 设置样式  
	div.style.cursor = "default";
	div.style.width = '150px';
	div.style.height = '20px';
	div.style.background = "rgba(0,0,0,0.5)";
	div.style.textAlign = "center";
	div.style.fontSize = "10px";
	div.style.color = "#ffffff";
	map.getContainer().appendChild(div);
	// 将DOM元素返回  
	return div;
}



function FullScreenControl() {
	this.defaultAnchor = BMAP_ANCHOR_TOP_RIGHT;
	this.defaultOffset = new BMap.Size(10, 8);
}

FullScreenControl.prototype = new BMap.Control();

FullScreenControl.prototype.initialize = function(map) {
	var params=getRequest();
	var display="block";
	if(top.window!=window){
		display="none";
	}
	// 创建一个DOM元素  
	var div = document.createElement("div");
	// 添加文字说明  
	div.setAttribute("title", "全屏");
	div.setAttribute("id", "full_screen_ctr");
	// 设置样式  
	div.style.cursor = "pointer";
	div.style.width = '68px';
	div.style.height = '28px';
	div.style.display = display;
	div.innerHTML = '全屏';
	//div.style.background = "url(static/images/show_all.png)";
	// 绑定事件，点击一次放大两级  
	$(div).on({
		mouseenter: function() {
			$(this).addClass('active');
		},
		mouseleave: function() {
			$(this).removeClass('active');
		}
	});

	//alert(window.location.href);
	if(params &&　params.fullscreen){
		$(div).toggler(function() {
			$(this).attr("title", "全屏").html('全屏');
			exitFullscreen();
		},function() {
			$(this).attr("title", "退出全屏").html('退出全屏');
			launchFullscreen();
		});
		
		$(document).ready(function(){
			//launchFullscreen();
			$("#full_screen_ctr").trigger();
    	});
	} else {
		$(div).toggler(function() {
			$(this).attr("title", "退出全屏").html('退出全屏');
			launchFullscreen();
		},
		function() {
			$(this).attr("title", "全屏").html('全屏');
			exitFullscreen();
		});
	}


	// 添加DOM元素到地图中  
	map.getContainer().appendChild(div);
	// 将DOM元素返回  
	return div;
}

function getRequest() {
  
  var url = location.search; //获取url中"?"符后的字串
   var theRequest = new Object();
   if (url.indexOf("?") != -1) {
      var str = url.substr(1);
      strs = str.split("&");
      for(var i = 0; i < strs.length; i ++) {
         theRequest[strs[i].split("=")[0]]=(strs[i].split("=")[1]);
      }
   }
   return theRequest;
}



//打开新窗口
function NewScreenControl() {
	this.defaultAnchor = BMAP_ANCHOR_TOP_RIGHT;
	this.defaultOffset = new BMap.Size(90, 8);
}

NewScreenControl.prototype = new BMap.Control();

NewScreenControl.prototype.initialize = function(map) {
	var display="none";
	if(top.window!=window){
		display="block"
	}
	// 创建一个DOM元素  
	var div = document.createElement("div");
	// 添加文字说明  
	div.setAttribute("title", "结合'全屏'，可以在大屏幕上进行展现!");
	div.setAttribute("id", "new_screen_ctr");
	// 设置样式  
	div.style.cursor = "pointer";
	div.style.width = '68px';
	div.style.height = '28px';
	div.style.display = display;
	div.innerHTML = '新窗口';
	//div.style.background = "url(static/images/show_all.png)";
	// 绑定事件，点击一次放大两级  
	$(div).on({
		mouseenter: function() {
			//$(this).css("background", "url(static/images/show_all_1.png)");
			$(this).addClass('active');
			//$(".triangel-right").css("border-color", "transparent transparent transparent #3193F5");
		},
		mouseleave: function() {
			$(this).removeClass('active');
			//$(this).css("background", "url(static/images/show_all.png)");
		}
	});

	$(div).on("click",function(){
		window.open(window.location.href+"?fullscreen=true"); 
	});
//	$(div).toggler(function() {
//			$(this).attr("title", "退出全屏").html('退出全屏');
//			//window.open(window.location.href); 
//			launchFullscreen();
//		},
//		function() {
//			$(this).attr("title", "全屏").html('全屏');
//			exitFullscreen();
//		});
	// 添加DOM元素到地图中  
	map.getContainer().appendChild(div);
	// 将DOM元素返回  
	return div;
}