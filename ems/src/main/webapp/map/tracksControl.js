function TracksControl(){
	this.map;
	this.lushu;
	this.isPlayed = false; //小车移动动画是否在播放
    this.isPaused = false; //小车移动动画是否暂停
    this.totalStep = 0; //小车走完轨迹所需的步数
    this.curIndex = 0; //小车移动的动画游标
	this.defaultSpeed=4000;//值越大越快，//默认速度 米/秒
	
	this._encrLngLatPois=[];//这次路书的节点
	
	var lushuMarker;//路书上的图标
	
	/**
	 * 获取某个轨迹的所有节点
	 */
	this.setTraceListpois=function(traceList){
		var self=this;
		
		for(var i=0;i<traceList.length;i++){
			 var pos = new BMap.Point(traceList[i].longitude, traceList[i].latitude);
             pos.loc_time = traceList[i].loc_time
             self._encrLngLatPois.push(pos);
		}
		//self._encrLngLatPois=pois;
		self.totalStep=self._encrLngLatPois.length;
	};
	/**
	 * 直接设置baidu的经纬度
	 */
	this.setLngLatpois=function(pois){
		var self=this;
		self._encrLngLatPois=pois;
		self.totalStep=self._encrLngLatPois.length;
	};
	/**
	 * 在界面上画出运动轨迹
	 */
	this.drawPolylineOvelay=function(){
		if(!this._encrLngLatPois){
			alert("请先设置轨迹的经纬度数据!");
		}
		 map.addOverlay(new BMap.Polyline(this._encrLngLatPois, {strokeColor: '#111'}));
	     map.setViewport(this._encrLngLatPois);
	};
	//添加lushu，开始小车沿轨迹移动动画
	this.trackStart = function() {
		 var self = this;
		 
		 
	     self.lushu = new BMapLib.LuShu(self,self.map,self._encrLngLatPois,{
		 	defaultContent:"",// "从天安门到百度大厦"
		    autoView:true,// 是否开启自动视野调整，如果开启那么路书在运动过程中会根据视野自动调整
		    icon  : new BMap.Icon('http://developer.baidu.com/map/jsdemo/img/car.png', new BMap.Size(52,26),{anchor : new BMap.Size(27, 13)}),
		    speed: self.defaultSpeed,
		    enableRotation:true,// 是否设置marker随着道路的走向进行旋转
		    landmarkPois: [
                   {lng:116.314782,lat:39.913508,html:'加油站',pauseTime:2},
                   {lng:116.315391,lat:39.964429,html:'高速公路收费<div><img src="http://map.baidu.com/img/logo-map.gif"/></div>',pauseTime:3},
                   {lng:116.381476,lat:39.974073,html:'肯德基早餐<div><img src="http://ishouji.baidu.com/resource/images/map/show_pic04.gif"/></div>',pauseTime:2}
            ]
	      });     
	      
	      if (self.lushuMarker) {
          	self.map.removeOverlay(self.lushuMarker);
          }
                
	      self.lushu.start();

	};
	
	
	// 小车沿轨迹移动的动画播放
	this.trackPlay = function() {
		var self = this;
		
		// 第一次开始播放
		if (!self.isPlayed) {
			self.sliderStart();
			self.trackStart();
			// self.onActiveTrack = id;
			self.isPlayed = true;
		} else if (self.isPaused) {//alert(1);
			self.isPaused = false;
			self.lushu.start();
		}
		
		$("#btn-play").children().removeClass().addClass('glyphicon glyphicon-pause');
	};
	
	/**
     * 小车沿轨迹移动的动画暂停
     */
    this.trackPause = function() {
        var self = this;
        if (self.lushu && self.isPlayed) {
            $("#btn-play").children().removeClass().addClass('glyphicon glyphicon-play');
            //$(".ply-" + self.currentPlayTrack).children().removeClass().addClass('glyphicon glyphicon-play');
            //$(".ply-" + self.currentPlayTrack).attr('title', '轨迹回放')
            self.isPaused = true;
            self.lushu.pause();
        }
    };
    /**
     * 小车沿轨迹移动的动画重播
     * @Author: xuguanlong
     * @return  {[type]}   [description]
     */
    this.trackReplay = function() {
        $("#h-slider").slider({
            value: 0
        });
        //this.map.removeOverlay(this.lushu._marker);
        this.curIndex = 0;
        this.trackPlay();
    }
    /**
     * 小车沿轨迹移动的动画停止
     * @Author: xuguanlong
     * @return  {[type]}   [description]
     */
    this.trackStop = function() {
        var self = this;
        self.isPaused = false;
        self.isPlayed = false;
        self.curIndex = 0;
        $("#btn-play").children().removeClass().addClass('glyphicon glyphicon-play');

        if (self.lushu) {
            //停止后移除小车Marker
            self.map.removeOverlay(self.lushu._marker);
            self.lushu.stop();
            self.lushu = null;
        }
        //播放进度条回0
        $("#h-slider").slider({
            value: 0
        });
        $('.ui-slider-label').html('');
    };
    
    /**
     * 快进
     * @param {} speed 每次快进的速度，默认是增加s是2000m/s
     */
    this.trackBackward = function(speed) {
    	var self=this;
		if(self.lushu){
			self.lushu._opts.speed=self.lushu._opts.speed/2;
		}
    	
    }
    this.trackForward = function(speed) {
    	var self=this;
    	if(self.lushu){
    		self.lushu._opts.speed=self.lushu._opts.speed*2;
    	}
    	//self.lushu._opts.speed=self.lushu._opts.speed+(speed?speed:2000);
    	
    }
    
    //播放那个进度条开始
	this.sliderStart = function() {
		var self = this;

		// 手动控制进度
		$(document).bind("mouseup", function() {
			if (self.sliding) {
				if (self.isPlayed) {
					self.trackPlay();
				} else {
					self.setSliderValue(0);
				}
				self.sliding = false;
			}
		});

		$("#h-slider").slider({
			value : 0,
			min : 0,
			max : self.totalStep,
			slide : function(event, ui) {
				if (self.lushu && self.isPlayed) {
					self.sliding = true;
					// self.lushu.pause();
					self.trackPause();
					self.curIndex = ui.value;
					self.moveIndex(self.curIndex);
				} else {
					//self.setSliderValue(0);
					//$('.ui-slider-label').hide();
					self.curIndex = ui.value;
					self.moveIndex(self.curIndex);
					self.isPlayed=true;
					self.sliding=false;
					self.isPaused=true;
				}
			}
		});
	};
	
	 /**
     * [setSliderValue 调整进度条的进度]
     * @param   {[type]}   v [进度条的值]
     */
    this.setSliderValue = function(v) {
        var self = this;
        $('#h-slider').slider({
            value: v
        });
        //self.setSliderTime(self.monitoringTrackOverlays[self.currentPlayTrack]._encrLngLatPois[v].loc_time);
    }
    this.moveIndex = function(index) {
    	var self = this;
        this.lushu.i = index;
        this.lushu._marker.setPosition(self._encrLngLatPois[index]);
        this.setSliderTime(self._encrLngLatPois[index].loc_time);
        //this.lushu._overlay.setPosition(this.lushu._marker.getPosition(), this.lushu._marker.getIcon().size);
    }
    /**
     * 在进度条中显示时间
     * @param {} str
     */
    this.setSliderTime = function(str) {
    	if(str){
	    	var self = this;
	        $('.ui-slider-label').html(str.substring(str.length - 8, str.length));
	        $('.ui-slider-label').show();
    	}
    }
    /**
     * 动画结束回调函数
     * @return  {[type]}   [description]
     */
    this.end = function() {
        var self = this;
        self.curIndex = 0;
        self.isPlayed = false;
        //self.isPaused = false;
        $("#btn-play").children().removeClass().addClass('glyphicon glyphicon-play');
       
        //$('.ui-slider-range').css('width', '100%');
       // $('.ui-slider-handle').css('left', '100%');
        
       
    }
    /**
     * 设置timeController中的值，在路书中设置
     * @param {} str
     */
    this.setTimeControl = function(str) {
        $('#time_control').html(str);
    }
}
