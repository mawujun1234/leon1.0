
var isSupportCanvas = false;
try {
    document.createElement('canvas').getContext('2d');
    isSupportCanvas = true;
} catch (e) {
    isSupportCanvas = false;
}
if (!isSupportCanvas) {
    alert("您的浏览器版本过低：推荐使用、chrome、firefox、safari、IE10");
}


$(function(){
	showMap()
	
	
	//收缩、展开作业单位
	$("#entryTracePanel_button i").click(function(){
		$(this).toggleClass("glyphicon-chevron-down");
		$("#entryTracePanel_content").toggle();
	});
	//展开或显示历史轨迹明细
	$("#entryTracePanel_list_button i").click(function(){
		$("#entryTracePanel_list").toggle();
	});
	
//========================================================================================	
	//获取当前正在线上的作业单位
	
	$('#realtime_tab').on('click', 'tr', function(){
		$('#realtime_tab tr').removeClass("danger");
		$(this).addClass("danger");
		
		tracksControl.centerTimerCar($(this).attr("data-sessionId"));
	}); 
	
	//当点击复选框的时候，在界面上刻画路线，并且定位到响应的地方
	$("#realtime_tab tbody").on('click','input',function(e){
		//alert(1);
		//return false;
		var value=$(this).prop("checked");
		var sessionId=$(this).parent().parent().attr("data-sessionId");

		if(value){
			//alert('划线!');
			tracksControl.drawTimerPolylineOvelay(sessionId);
		} else {
			tracksControl.removeTimerPolylineOvelay(sessionId);
		}
		
		//tracksControl.centerTimerCar(sessionId);
		//e.preventDefault();
		//e.stopPropagation();
	});

//========================================================================================		
	//更新现在
	$('#history_tab').on('click', 'tr', function(){
		$("#history_tab tr ").removeClass("danger");
		$(this).addClass("danger");
		
		//获取某个作业单位某天的轨迹列表
		refreshEntryTracePanel_list($(this));
	});
	//当点击轨迹列表中某条具体的轨迹的时候
	$("#entryTracePanel_list tbody").on('click','tr',function(){
		$("#entryTracePanel_list").hide();
		$("#tracks-history-play").show();
		//然后去获取这条轨迹的路径数据
		queryHistoryTraceList($(this).attr("data-sessionId"));
		
		//把任务的信息填写在播放器上
		var html="";
		$(this).children().each(function(index,element){
			if(index==1){
				html+="&nbsp;&nbsp;&nbsp;&nbsp;时间范围:";
			} else if(index==2){
				html+="&nbsp;&nbsp;&nbsp;&nbsp;时长:";
			} else if(index==3){
				html+="&nbsp;&nbsp;&nbsp;&nbsp;里程:";
			}
			html+=$(this).text()+"     "
		});
		
		$("#track_detail_info").html(html);
		
	});
	
	
//========================================================================================
	//当切换tab页的时候，隐藏历史轨迹明细数据
	$('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
	  //e.target // newly activated tab
	  //e.relatedTarget // previous active tab
		$("#entryTracePanel_list").hide();
		$("#tracks-history-play").hide();//隐藏播放器
		//.当点击历史轨迹的时候，就清空界面元素和停止这个定时调用，翻过来一样
		map.clearOverlays();
		showWorkunitCar_setTimeout=!showWorkunitCar_setTimeout;//来回切换定时更新定时数据
		if(showWorkunitCar_setTimeout){//去获取实时监控轨迹中的轨迹数据
			showWorkunitCar();
		}

	})
	
	//$('[data-toggle="popover"]').popover();

	

	


	
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
	 	window.loc_time=$input.val();
	 	showHistoryWorkunit(window.loc_time);
	 	$("#entryTracePanel_list").hide();
	  }
	});
	$('#datetimepicker').next().click(function(){
		$('#datetimepicker').datetimepicker('show');
	});
	
	initDrag();
	//initDrag("tracks-history-play");
	
	$("#h-slider").slider({
        range: "min",
        min: 0,
        max: 2000,
        value: 0
    });
    


});

/**
 * 查询某个会话的所有轨迹数据，并显示在地图上
 */
function queryHistoryTraceList(sessionId){
	//alert(sessionId);return;
	$.ajax({
			type: 'POST',
		    url: Ext.ContextPath+"/trace/queryHistoryTraceList.do" ,
		    data: {
		    	sessionId:sessionId
		    } ,
		    dataType: "json",
		    success: function(data){
		    	////window.tracksControl.setTraceListpois(data.root);
		    	window.tracksControl.drawPolylineOvelay(data.root);
		    	//window.tracksControl.drawPolylineOvelay(arrPois);
		    }
	});
}

/**
 * 更新轨迹列表，获取某个作业单位某天的轨迹列表
 */
function refreshEntryTracePanel_list(tr){
		var loginName=tr.attr("data-loginName");//放这里的原因就是当没有数据的时候，在这里就会报错了
		if(!loginName){
			return;
		}
		
		showMask();
		$("#entryTracePanel_list").hide();
		$.ajax({
			type: 'POST',
		    url: Ext.ContextPath+"/trace/queryHistoryTrace.do" ,
		    data: {
		    	loc_time:window.loc_time,
		    	loginName:loginName
		    } ,
		    dataType: "json",
		    success: function(data){
		    	hideMask();
		    	var html='';
		    	for(var i=0;i<data.root.length;i++){
		    		html+='<tr scope="row" data-sessionId="'+data.root[i].sessionId+'">';
		    		html+='<th>'+(i+1)+'</th>';
		    		html+='<td>'+data.root[i].startDate+'--'+data.root[i].endDate+'</td>';
		    		html+='<td>'+data.root[i].duration+'</td>';
		    		html+='<td>'+data.root[i].distance_km+'</td>';
		    		html+='</tr>';
		    	}
		    	
		    	//把弹出的列表并列显示到主panel
		    	var $entryTracePanel_list=$("#entryTracePanel_list");
		    	var $entryTracePanel=$("#entryTracePanel");

		    	$entryTracePanel_list.css({
		    		top:$("#entryTracePanel").css("top"),
		    		left:parseFloat($entryTracePanel.css("left"))+parseFloat($entryTracePanel.outerWidth())+"px"
		    	});
		    	$entryTracePanel_list.show();
				$("#entryTracePanel_list tbody").html(html);
		    }
		});
		
}
//获取某一天中，有登录过的作业单位
function showHistoryWorkunit(loc_time){
		$.ajax({
			url : Ext.ContextPath + '/trace/queryHistoryWorkunit.do',
			data:{loc_time:loc_time},
			dataType:"json",
			type: "POST",
			success : function(data) {		
				var html="";
				var workunits=data.root;
				if(!workunits || workunits.length==0){
					html+='<tr scope="row">'+
					   '<td>没有数据</td>'+
					'</tr>';
				} else {
					for(var i=0;i<workunits.length;i++){
						html+='<tr scope="row" data-loginName="'+workunits[i].loginName+'">'+
						   '<th width="10">'+(i+1)+'</th>'+
						   '<td>'+workunits[i].name+'</td>'+
						'</tr>';
					}
				}
				
				
				$("#history_tab tbody").html(html);
			},
			failure : function() {
					//Ext.getBody().unmask();
			}
		});
}

//true：的时候定时去后台取数据
var showWorkunitCar_setTimeout=true;
function showWorkunitCar(){
		if(!showWorkunitCar_setTimeout){
			return;	
		}
		$.ajax({
			url : Ext.ContextPath + '/trace/queryWorkingWorkunit.do',
			dataType:"json",
			type: "POST",
			success : function(data) {		

				//map.clearOverlays();.当点击历史轨迹的时候，就清空界面元素和停止这个定时调用，翻过来一样
				//当前轨迹的时候，把所有作业单位的轨迹都画出来
				//....
				var html="";
				var workunits=data.root;
				//window.workunits=workunits;
				
				if(!workunits || workunits.length==0){
					html+='<tr scope="row">'+
					   '<td>没有数据</td>'+
					'</tr>';
					//清空界面上关于实时的数据
					tracksControl.clearTimerOvelay();
				} else {
					for(var i=0;i<workunits.length;i++){
						html+='<tr scope="row" data-sessionId="'+workunits[i].sessionId+'">'+
						   '<th width="10"><input type="checkbox" ></th>'+
						   //'<th width="10">'+(i+1)+'</th>'+
						   '<td>'+workunits[i].name+'</td>'+
						   '<td>'+workunits[i].lastedUploadTime+'</td>'+
						'</tr>';
						
						//addCar2Map(workunits[i]);
						tracksControl.setTimerTraceListpois(workunits);
						//同时绘制路线图
						//map.addOverlay(new BMap.Polyline(workunits[i].traceListes, {strokeColor: '#111'}));
	     				//map.setViewport(workunits[i].traceListes);	
					}
					
				}
				
				
				$("#realtime_tab tbody").html(html);
			},
			failure : function() {
					//Ext.getBody().unmask();
			}
		});
		setTimeout("showWorkunitCar()",60*1000);//每一分钟发送一次
}
//window.workunit_markeres={};
//function addCar2Map(workunit){
//		var point = new BMap.Point(workunit.lasted_longitude, workunit.lasted_latitude);
//		var car_marker = new BMap.Marker(point, {
//			icon : carIcon
//		});	
//		window.workunit_markeres[workunit.sessionId]=car_marker;
//		map.addOverlay(car_marker); // 将标注添加到地图中
//	
//		addMouseoverHandler("账号:"+workunit.loginName+"<br/>作业单位:"+workunit.name+"<br/>电话:"+workunit.phone+"<br/>登录时间:"+workunit.loginTime ,car_marker);
//	
//}
function addMouseoverHandler(content,marker){
		marker.addEventListener("mouseover",function(e){		
			openMarkerInfo(content,e);
		});
		marker.addEventListener("mouseout",function(e){		
			map.closeInfoWindow();
		});
}
function openMarkerInfo(content,e){
		var marker = e.target;
		var point = new BMap.Point(marker.getPosition().lng, marker.getPosition().lat);
		var infoWindow = new BMap.InfoWindow(content,{
			width : 250,     // 信息窗口宽度
			height: 80,     // 信息窗口高度
			title : "" , // 信息窗口标题
			enableMessage:true//设置允许信息窗发送短息
		});  // 创建信息窗口对象 
		map.openInfoWindow(infoWindow,point); //开启信息窗口
}
function showMap() {
	window.carIcon = new BMap.Icon("./images/car.png", new BMap.Size(48,48));
	window.map = new BMap.Map('map_canvas');
	
//	var centerPoint= new BMap.Point(48,48)
//	map.centerAndZoom("宁波", 14); 
	map.setMapStyle({style:'light'});
	
	map.enableScrollWheelZoom();
	map.centerAndZoom(new BMap.Point(121.558321,29.814518), 14);

	
	$("#h-slider").slider({
        range: "min",
        min: 0,
        max: 20,
        value: 0
    });
    
    window.tracksControl=new TracksControl();
    tracksControl.map=map;
    
    tracksControl.timeControl=new TimeControl();
    map.addControl(tracksControl.timeControl);
    tracksControl.timeControl.hide();

    var fullScreenCtr=new FullScreenControl();
    map.addControl(fullScreenCtr);
    
    var newScreenControl=new NewScreenControl();
    map.addControl(newScreenControl);
    
    //添加地图类型控件
    map.addControl(new BMap.MapTypeControl({
    	type:'BMAP_MAPTYPE_CONTROL_HORIZONTAL',
    	mapTypes:[BMAP_NORMAL_MAP,BMAP_SATELLITE_MAP,BMAP_HYBRID_MAP]
    }));
    
    // 实例化一个驾车导航用来生成路线
	//DrivingRoute是自动生成的导航，要换成从后台获取的
	var drv = new BMap.DrivingRoute('宁波', {
	        onSearchComplete: function(res) {
	            if (drv.getStatus() == BMAP_STATUS_SUCCESS) {
	                var plan = res.getPlan(0);
	                window.arrPois =[];
	                for(var j=0;j<plan.getNumRoutes();j++){
	                    var route = plan.getRoute(j);
	                    //aaa.loc_time="2015-11-18 18:15:15";
	                    //arrPois= arrPois.concat(route.getPath());
	                    
	                    var aa=route.getPath(); 
	                    for(var x=0;x<aa.length;x++){
	                    	 arrPois.push({
		                    	longitude:aa[x].lng,
		                    	latitude:aa[x].lat,
		                    	loc_time:'2015-11-25 12:12:12'
		                    });
	                    }
	                    
	                   
	                }
	                //map.addOverlay(new BMap.Polyline(arrPois, {strokeColor: '#111'}));
	                //map.setViewport(arrPois);
	                
	                //tracksControl.setTraceListpois(arrPois);
	                
	                
	            }
	        }
	 });
	drv.search('雅戈尔国际展销中心', '都市森林');
	
		
	// 绑定事件
	$("#btn-play").click(function(){
		if ($(this).children().hasClass('glyphicon-play')) {
			//lushu.start();
			tracksControl.trackPlay();
			//$(this).children().removeClass('glyphicon-play').addClass('glyphicon-pause');
		} else if ($(this).children().hasClass('glyphicon-pause')) {
			 //$(this).children().removeClass('glyphicon-pause').addClass('glyphicon-play');
			//lushu.pause();
			tracksControl.trackPause();
		} else {
			return;
		}
	});
	$("#btn-stop").click(function(){
		//lushu.stop();
		//map.removeOverlay(lushu._marker);
		//$("#btn-play").trigger("click");
		//$("#btn-play").children().removeClass().addClass('glyphicon glyphicon-play');
		tracksControl.trackStop();
	});
	$("#btn-backward").click(function(){
		tracksControl.trackBackward();
	});
	$("#btn-forward").click(function(){
		tracksControl.trackForward();
	});

	$(".tracks-history .close").click(function(){
		 $('.tracks-history').hide();
        tracksControl.trackStop();
	});
	
	//获取实时监控 数据
	showWorkunitCar();
	window.loc_time=(new Date).format("yyyy-MM-dd");
	showHistoryWorkunit(window.loc_time);
}


function initDrag(id){
 	$(document).mousemove(function(e) {
        if(!!this.move) {
            var posix = !document.move_target ? {
                    x: 0,
                    y: 0
                } : document.move_target.posix,
                callback = document.call_down || function() {
                    $(this.move_target).css({
                        top: e.pageY - posix.y,
                        left: e.pageX - posix.x
                    });
                };

            callback.call(this, e, posix);
        }
    }).mouseup(function(e) {
        if(!!this.move) {
            var callback = document.call_up || function() {};
            callback.call(this, e);
            $.extend(this, {
                move: false,
                move_target: null,
                call_down: false,
                call_up: false
            });
        }
    });
    
    var $entryTracePanely = $('#entryTracePanel').mousedown(function(e) {
        var offset = $(this).offset();

        this.posix = {
            x: e.pageX - offset.left,
            y: e.pageY - offset.top
        };
        $.extend(document, {
            move: true,
            move_target: this
        });
        //console.log(1);
    }).on('mousedown', '#entryTracePanel_content', function(e) {
        $.extend(document, {
            move: true,
            call_down: function(e) {
                return false;
            }
        });
        return false;
    });
    
    
    var $entryTracePanely = $('#entryTracePanel_list').mousedown(function(e) {
        var offset = $(this).offset();

        this.posix = {
            x: e.pageX - offset.left,
            y: e.pageY - offset.top
        };
        $.extend(document, {
            move: true,
            move_target: this
        });
        //console.log(1);
    }).on('mousedown', '#entryTracePanel_list_content', function(e) {
        $.extend(document, {
            move: true,
            call_down: function(e) {
                return false;
            }
        });
        return false;
    });

    var $box = $('#tracks-history-play').mousedown(function(e) {
        var offset = $(this).offset();

        this.posix = {
            x: e.pageX - offset.left,
            y: e.pageY - offset.top
        };
        $.extend(document, {
            move: true,
            move_target: this
        });
        //console.log(2);
    }).on('mousedown', '#h-slider', function(e) {
        $.extend(document, {
            move: true,
            call_down: function(e) {
                return false;
            }
        });
        return false;
    });

}
function showMask(){  
        $("#entryTracePanel_mask").css("height",$(document).height());  
        $("#entryTracePanel_mask").css("width",$(document).width());  
        $("#entryTracePanel_mask").show();  
}  

function hideMask(){ 
	$("#entryTracePanel_mask").hide();
}


/**
 * 时间对象的格式化;
 */
Date.prototype.format = function(format) {
	/*
	 * eg:format="yyyy-MM-dd hh:mm:ss";
	 */
	var o = {
		"M+" : this.getMonth() + 1, // month
		"d+" : this.getDate(), // day
		"h+" : this.getHours(), // hour
		"m+" : this.getMinutes(), // minute
		"s+" : this.getSeconds(), // second
		"q+" : Math.floor((this.getMonth() + 3) / 3), // quarter
		"S" : this.getMilliseconds()
		// millisecond
	}

	if (/(y+)/.test(format)) {
		format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4
						- RegExp.$1.length));
	}

	for (var k in o) {
		if (new RegExp("(" + k + ")").test(format)) {
			format = format.replace(RegExp.$1, RegExp.$1.length == 1
							? o[k]
							: ("00" + o[k]).substr(("" + o[k]).length));
		}
	}
	return format;
}

function launchFullscreen(element) {
	//console.log(top.window.fullScreenElement);
	if(top.window.fullScreenElement==true){
		exitFullscreen();
		return;
	}
	//alert(typeof window.ActiveXObject);
	var element=element?element:top.document.documentElement; 
 	var requestMethod = element.requestFullScreen || element.webkitRequestFullScreen || element.mozRequestFullScreen || element.msRequestFullScreen;
    if (requestMethod) {  
        requestMethod.call(element);
        top.window.fullScreenElement=true;
    } else if (typeof window.ActiveXObject !== "undefined") {  
        var wscript = new ActiveXObject("WScript.Shell");
        if (wscript !== null) {
            wscript.SendKeys("{F11}");
            top.window.fullScreenElement=true;
        }else {
        	alert("请按F11进行全屏显示.");
        }
    } else {
    	alert("请按F11进行全屏显示..");
    }
}

// 判断浏览器种类
function exitFullscreen() {
  if(top.document.exitFullscreen) {
    top.window.exitFullscreen();
    top.document.fullScreenElement=false;
  } else if(top.document.mozCancelFullScreen) {
    top.document.mozCancelFullScreen();
    top.window.fullScreenElement=false;
  } else if(top.document.webkitExitFullscreen) {
    top.document.webkitExitFullscreen();
    top.window.fullScreenElement=false;
  } else if (typeof window.ActiveXObject !== "undefined") {  
        var wscript = new ActiveXObject("WScript.Shell");
        if (wscript !== null) {
            wscript.SendKeys("{F11}");
            top.window.fullScreenElement=false;
        }else {
        	alert("请按F11或ESC进行退出全屏.");
        }
   } else {
   		alert("请按F11或ESC进行退出全屏..");
   }
  
}
$.fn.toggler = function( fn, fn2 ) {
    var args = arguments,guid = fn.guid || $.guid++,i=0,
    toggler = function( event ) {
      var lastToggle = ( $._data( this, "lastToggle" + fn.guid ) || 0 ) % i;
      $._data( this, "lastToggle" + fn.guid, lastToggle + 1 );
      event.preventDefault();
      return args[ lastToggle ].apply( this, arguments ) || false;
    };
    toggler.guid = guid;
    while ( i < args.length ) {
      args[ i++ ].guid = guid;
    }
    return this.click( toggler );
  };