
$(function(){
	showMap()
	
	$("#entryTracePanel_button i").click(function(){
		$(this).toggleClass("glyphicon-chevron-down");
		$("#entryTracePanel_content").toggle();
	});
	
	$("#entryTracePanel_list_button i").click(function(){
		$("#entryTracePanel_list").toggle();
	});
	
	$("#realtime_tab tr th input").click(function(){
		//var value=$(this).prop("checked");
		var uuid=$(this).attr("data-uuid");//手机码
		var loginName=$(this).attr("data-loginName");//手机码
		//alert(uuid);
		//alert(loginName);
	});
//	$("#realtime_tab table tbody tr").click(function(){
//		alert(1);
//		$(this).children("input:checkbox").prop("checked",true);
//	});
	
	
	
	
	//$('[data-toggle="popover"]').popover();
	var history_tab_tr=$("#history_tab tr ");
	history_tab_tr.click(function(e){
		history_tab_tr.removeClass("danger");
		$(this).addClass("danger");
		
		refreshEntryTracePanel_list($(this));
		//$("#tracks-history-play").hide();
		
//		var uuid=$(this).attr("data-uuid");//手机码
//		var loginName=$(this).attr("data-loginName");//手机码
//		alert(uuid);
//		alert(loginName);
	});
	
	$("#entryTracePanel_list tbody tr").click(function(){
		$("#entryTracePanel_list").hide();
		$("#tracks-history-play").show();
		//然后去获取这条轨迹的路径数据
		
	});
	
	/**
	 * 更新明细数据
	 */
	function refreshEntryTracePanel_list(tr){
		showMask();
		$("#entryTracePanel_list").hide();
		$.ajax({
			type: 'POST',
		    url: Ext.ContextPath+"/trace/queryHistoryTrace.do" ,
		    data: {
		    
		    } ,
		    dataType: "json",
		    success: function(data){
		    	hideMask();
		    	var html='<table class="table table-hover table-striped" ><thead><tr>'+
		          '<th>#</th>'+
		          '<th>起始时间--终止时间</th>'+
		          '<th>时长</th>'+
		          '<th>里程<small>(公里)</small></th>'+
		          '<th>操作</th>'+
		        '</tr>'+
		      '</thead><tbody>';
		    	for(var i=0;i<data.root.length;i++){
		    		html+='<tr scope="row">';
		    		html+='<th>'+(i+1)+'</th>';
		    		html+='<td>10:21:10--12:22:22</td>';
		    		html+='<td>1111</td>';
		    		html+='<td>222</td>';
		    		html+='<td><span class="glyphicon glyphicon-pause"></span></td>';
		    		html+='</tr>';
		    	}
		    	html+='</tbody></table>';
		    	
		    	var $entryTracePanel_list=$("#entryTracePanel_list");
		    	var $entryTracePanel=$("#entryTracePanel");

		    	$entryTracePanel_list.css({
		    		top:$("#entryTracePanel").css("top"),
		    		left:parseFloat($entryTracePanel.css("left"))+parseFloat($entryTracePanel.outerWidth())+"px"
		    	});
				$("#entryTracePanel_list").show();
		    }
		});
		
	}

	
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
	
	initDrag();
	//initDrag("tracks-history-play");
	
	$("#h-slider").slider({
        range: "min",
        min: 0,
        max: 2000,
        value: 0
    });
    


});



function showMap() {
	window.map = new BMap.Map('map_canvas');
	map.enableScrollWheelZoom();
	map.centerAndZoom(new BMap.Point(116.404, 39.915), 13);

	
	$("#h-slider").slider({
        range: "min",
        min: 0,
        max: 20,
        value: 0
    });
    
    var tracksControl=new TracksControl();
    tracksControl.map=map;
    tracksControl.timeControl=new TimeControl();
    map.addControl(tracksControl.timeControl);
    tracksControl.timeControl.hide();

    
    // 实例化一个驾车导航用来生成路线
	//DrivingRoute是自动生成的导航，要换成从后台获取的
	var drv = new BMap.DrivingRoute('北京', {
	        onSearchComplete: function(res) {
	            if (drv.getStatus() == BMAP_STATUS_SUCCESS) {
	                var plan = res.getPlan(0);
	                var arrPois =[];
	                for(var j=0;j<plan.getNumRoutes();j++){
	                    var route = plan.getRoute(j);
	                    var aaa=route.getPath();
	                   
	                    //aaa.loc_time="2015-11-18 18:15:15";
	                    arrPois= arrPois.concat(aaa);
	                    // console.dir(aaa);
	                }
	                map.addOverlay(new BMap.Polyline(arrPois, {strokeColor: '#111'}));
	                map.setViewport(arrPois);
	                
	                tracksControl.setLngLatpois(arrPois);
	                
	            }
	        }
	 });
	drv.search('天安门', '百度大厦');
	
		
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

	$(".tracks-history .close").click(function(){
		 $('.tracks-history').hide();
        tracksControl.trackStop();
	});
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

//    var $move = $('.slide-up').mousedown(function(e) {
//        var pos = {
//            x: e.pageX,
//            y: e.pageY
//        };
//        $.extend(document, {
//            move: true,
//            call_down: function(e) {
//                var h = e.pageY - posiy.y + posiy.h;
//                h = (h < 50) ? 50 : h;
//                h = (h > 250) ? 250 : h;
//                $('.monitor-panel').css({
//                    'height': h
//                });
//                $('#mapContainer').css({
//                    'height':$('.row').height() - h
//                })
//            }
//        });
//        return false;
//    });
}
function showMask(){  
        $("#entryTracePanel_mask").css("height",$("#entryTracePanel").height());  
        $("#entryTracePanel_mask").css("width",$("#entryTracePanel").width());  
        $("#entryTracePanel_mask").show();  
}  

function hideMask(){ 
	$("#entryTracePanel_mask").hide();
}
