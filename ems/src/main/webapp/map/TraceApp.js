
$(function(){
	showMap()
	
	$("#entryTracePanel_button i").click(function(){
		$(this).toggleClass("glyphicon-chevron-down");
		$("#entryTracePanel_content").toggle();
	});
	
	$("#realtime_tab tr th input").click(function(){
		//var value=$(this).prop("checked");
		var uuid=$(this).attr("data-uuid");//手机码
		var loginName=$(this).attr("data-loginName");//手机码
		//alert(uuid);
		//alert(loginName);
	});
	
	
	var history_tab_tr=$("#history_tab tr ");
	history_tab_tr.click(function(e){
		history_tab_tr.removeClass("danger");
		$(this).addClass("danger");

		history_tab_tr.popover('hide');
		$(this).popover('show');
//		var uuid=$(this).attr("data-uuid");//手机码
//		var loginName=$(this).attr("data-loginName");//手机码
//		alert(uuid);
//		alert(loginName);
	});
////http://v3.bootcss.com/javascript/#tooltips
//	$("#history_tab tr td").click(function(e){
//		history_tab_tr.popover('hide');
////		//aa
////		$(this).parent().popover({
////			trigger:"click",
////			container:'body',
////			html:true,
////			placement:'right',
////			content:"正在加载......"
////		});
//		$(this).parent().popover('show');
//	});
	
	
	//$('#datetimepicker4').datetimepicker('show');
	//$('#datetimepicker4').datetimepicker('hide');
	$.datetimepicker.setLocale('zh');
	$('#datetimepicker').datetimepicker({
	 timepicker:false,
	 format:'Y-m-d',
	 value:new Date(),
	 onSelectDate:function(dp,$input){
	    //alert($input.val());
	    //alert(dp.dateFormat('d/m/Y'))
	  }
	});
	$('#datetimepicker').next().click(function(){
		$('#datetimepicker').datetimepicker('show');
	});
	
	initDrag("entryTracePanel");
});

function showMap() {
	var map = new BMap.Map('map_canvas');
	map.enableScrollWheelZoom();
	map.centerAndZoom(new BMap.Point(116.404, 39.915), 13);
	var lushu;
	// 实例化一个驾车导航用来生成路线
    var drv = new BMap.DrivingRoute('北京', {
        onSearchComplete: function(res) {
            if (drv.getStatus() == BMAP_STATUS_SUCCESS) {
                var plan = res.getPlan(0);
                var arrPois =[];
                for(var j=0;j<plan.getNumRoutes();j++){
                    var route = plan.getRoute(j);
                    arrPois= arrPois.concat(route.getPath());
                }
                map.addOverlay(new BMap.Polyline(arrPois, {strokeColor: '#111'}));
                map.setViewport(arrPois);
                
                lushu = new BMapLib.LuShu(map,arrPois,{
                defaultContent:"",//"从天安门到百度大厦"
                autoView:true,//是否开启自动视野调整，如果开启那么路书在运动过程中会根据视野自动调整
                icon  : new BMap.Icon('http://developer.baidu.com/map/jsdemo/img/car.png', new BMap.Size(52,26),{anchor : new BMap.Size(27, 13)}),
                speed: 4500,
                enableRotation:true,//是否设置marker随着道路的走向进行旋转
                landmarkPois: [
                   {lng:116.314782,lat:39.913508,html:'加油站',pauseTime:2},
                   {lng:116.315391,lat:39.964429,html:'高速公路收费<div><img src="http://map.baidu.com/img/logo-map.gif"/></div>',pauseTime:3},
                   {lng:116.381476,lat:39.974073,html:'肯德基早餐<div><img src="http://ishouji.baidu.com/resource/images/map/show_pic04.gif"/></div>',pauseTime:2}
                ]});          
            }
        }
    });
	drv.search('天安门', '百度大厦');
	//绑定事件
	$("run").onclick = function(){
		lushu.start();
	}
	$("stop").onclick = function(){
		lushu.stop();
	}
	$("pause").onclick = function(){
		lushu.pause();
	}
	$("hide").onclick = function(){
		lushu.hideInfoWindow();
	}
	$("show").onclick = function(){
		lushu.showInfoWindow();
	}
}


function initDrag(id){
            /*--------------拖曳效果----------------
            *原理：标记拖曳状态dragging ,坐标位置iX, iY
            *         mousedown:fn(){dragging = true, 记录起始坐标位置，设置鼠标捕获}
            *         mouseover:fn(){判断如果dragging = true, 则当前坐标位置 - 记录起始坐标位置，绝对定位的元素获得差值}
            *         mouseup:fn(){dragging = false, 释放鼠标捕获，防止冒泡}
            */
            var dragging = false;
            var iX, iY;
            var drag_object=null;
            $("#"+id).mousedown(function(e) {
                dragging = true;
                iX = e.clientX - this.offsetLeft;
                iY = e.clientY - this.offsetTop;
                this.setCapture && this.setCapture();
                drag_object=$(this);
                return false;
            });
            document.onmousemove = function(e) {
                if (dragging) {
                var e = e || window.event;
                var oX = e.clientX - iX;
                var oY = e.clientY - iY;
                drag_object.css({"left":oX + "px", "top":oY + "px"});
                return false;
                }
            };
            $(document).mouseup(function(e) {
                dragging = false;
                drag_object.releaseCapture &&　drag_object.releaseCapture();
                e.cancelBubble = true;
            })
}