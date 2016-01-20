<%@ page language="java" import="java.util.*,com.mawujun.shiro.*" pageEncoding="UTF-8"%>
<html> 
<head> 
	<meta charset="utf-8" /> 
	<title>轨迹</title> 
	<style type="text/css">
		body, html{width: 100%;height: 100%;margin:0;font-family:"微软雅黑";
			overflow:hidden;
		}
		#map_canvas{width:100%;height:100%;}
		#result {width:100%}
		
		/**隐藏百度地图的标签**/
	    .anchorBL{
	    	display:none;
	    }
	    
	    #entryTracePanel{
			width:300px;
			background-color: rgba(231,246,253,0.8);
			position:absolute;
			left:50;
			top:30;
			z-index:800;
		}
		#entryTracePanel_button {
		    float:right;
		    cursor: pointer;
		}
		
		#showPolePanel{
			width:300px;
			background-color: rgba(231,246,253,0.8);
			position:absolute;
			left:380;
			top:30;
			z-index:800;
		}
		#showPolePanel_content{
			display:none;
		}
		#showPolePanel_button {
		    float:right;
		    cursor: pointer;
		}
		
		#entryTracePanel_mask {    
            position: absolute; top: 0px;left: 0px; filter: alpha(opacity=60); background-color: #777;  
            z-index: 1002;   
            opacity:0.5; -moz-opacity:0.5;  
     
        }  

		
		
		#full_screen_ctr { -webkit-box-sizing: border-box; -moz-box-sizing: border-box; box-sizing: border-box; font-size: 12px; background: #fff; text-align: center; border: 1px solid #e5e0e4; color: #666; cursor: pointer; border-radius: 5px; padding-top: 4px; }
		#full_screen_ctr:hover { color: #fff; background-color: #258BF0; }
		.full-map { top: -52px; left: 0px; margin-left: 0px; width: 100%; }
		
		#new_screen_ctr { -webkit-box-sizing: border-box; -moz-box-sizing: border-box; box-sizing: border-box; font-size: 12px; background: #fff; text-align: center; border: 1px solid #e5e0e4; color: #666; cursor: pointer; border-radius: 5px; padding-top: 4px; }
		#new_screen_ctr:hover { color: #fff; background-color: #258BF0; }
	</style>
	
	<script type="text/javascript" >
		var Ext={};
		Ext.ContextPath="<%=request.getContextPath()%>";
	</script>
	<script src="http://api.map.baidu.com/api?v=2.0&ak=ED0fe5c7c869da5ee4260b4006e811b8"></script>
	
	<!--
	<script type="text/javascript" src="http://api.map.baidu.com/library/LuShu/1.2/src/LuShu_min.js"></script>
	-->
	
	<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
	<script src="/jquery/jquery-1.11.3.min.js"></script>
	
	<!-- 新 Bootstrap 核心 CSS 文件 
	<link rel="stylesheet" href="/bootstrap/3.3.5/css/bootstrap.min.css">-->
	
	<!-- 可选的Bootstrap主题文件（一般不用引入）
	<link rel="stylesheet" href="/bootstrap/3.3.5/css/bootstrap-theme.min.css">
		<!-- 最新的 Bootstrap 核心 JavaScript 文件 
	<script src="/bootstrap/3.3.5/js/bootstrap.min.js"></script>-->
	
	<!-- 新 Bootstrap 核心 CSS 文件 -->
<link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">

<!-- 可选的Bootstrap主题文件（一般不用引入） -->
<link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
	    <!--[if lt IE 9]>
      <script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
	
	

	
	<link rel="stylesheet" type="text/css" href="/jquery/datetimepicker/jquery.datetimepicker.css"/ >
	<script src="/jquery/datetimepicker/jquery.datetimepicker.full.min.js"></script>


	<link rel="stylesheet" type="text/css" href="/jquery/slider/jquery-ui-1.9.2.custom.css"/ >
	<script src="/jquery/slider/jquery.slider.js"></script>
	
	
	<link rel="stylesheet" type="text/css" href="../bootstrap/select/bootstrap-select.css"/ >
	<script src="../bootstrap/select/bootstrap-select.js"></script>
	
</head> 
<body> 
<div id="entryTracePanel_mask"></div>
	<div id="map_canvas"></div> 

	<!-- 播放器进度条 -->
	<style type="text/css">
	.tracks-history { font-size: 12px; font-weight: normal; position: absolute; z-index: 1002; left: 250px; bottom: 24px; display: none; width: 900px; height: 60px; cursor: move; color: #333; border: 1px solid #ddd; border-radius: 30px; background: #f8f8f8; }
	#btn-play,
	#btn-stop,#btn-backward,#btn-forward { color: #2d88f3; }
	#btn-play span { left: 1px; }
	#btn-stop span { left: -1px; }
	.tracks-history .row { margin: 0; }
	#h-slider { top: 25px; left: 200px; width: 640px; cursor: pointer; }
	#h-slider a { padding: 5px 5px; border-radius: 7px; }
	.tracks-history .close { font-size: 15px; position: absolute; top: 15px; right: 21px; width: 25px; height: 25px; color: #adabab; text-align: center; border-radius: 15px; border: 1px solid #ccc; background-image: -webkit-linear-gradient(top, #fff 0, #e0e0e0 100%); background-image: -o-linear-gradient(top, #fff 0, #e0e0e0 100%); background-image: -webkit-gradient(linear, left top, left bottom, from(#fff), to(#e0e0e0)); background-image: linear-gradient(to bottom, #fff 0, #e0e0e0 100%); filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#ffffffff', endColorstr='#ffe0e0e0', GradientType=0); filter: progid:DXImageTransform.Microsoft.gradient(enabled=false); background-repeat: repeat-x; opacity: 0.8; }
	.tracks-history .close:hover { color: #666; }
	.tracks-history .close span { position: relative; top: 5px; }
	.tracks-history button { padding: 3px 12px; }
	.tracks-history label { font-weight: normal; float: left; margin-top: 5px; padding: 0 9px; color: #333; }
	.tracks-history .progress-label { position: absolute; top: 10px; left: 15px; }
	.tracks-history .input-group .form-control { font-size: 12px; height: 28px; padding: 0 3px; }
	.play-control { position: absolute; top: 12px; left: 5px; width: 210px; padding-left: 6px; }
	.play-control a { margin: 0 2px; padding: 8px 10px; border-radius: 20px; }
	
	#track_detail_info {
		position: absolute; top: 40px; left: 190px; width: 100%; padding-left: 6px;
	}
	</style>
	<div id="tracks-history-play" class="tracks-history">
                <div class="close">
                     <span class="glyphicon glyphicon-remove"></span>
                </div>   
                <div class="play-control">
                    <a class="btn btn-default " title="历史回放" href="javascript:void(0)" id="btn-play" role="button">
                        <span class="glyphicon  glyphicon-play"></span>
                    </a>
                    <a class="btn btn-default " title="停止回放" href="javascript:void(0)" id="btn-stop" role="button">
                        <span class="glyphicon glyphicon-stop"></span>
                    </a>
                    <a class="btn btn-default " title="减速" href="javascript:void(0)" id="btn-backward" role="button">
                        <span class="glyphicon glyphicon-backward"></span>
                    </a>
                    <a class="btn btn-default " title="加速" href="javascript:void(0)" id="btn-forward" role="button">
                        <span class="glyphicon glyphicon-forward"></span>
                    </a>
                </div>
                <div id="h-slider"></div>
                <div id="track_detail_info">
                     	轨迹信息
                </div>  
    </div>
	
	
	
	
	<style type="text/css">
	    #entryTracePanel_list{
			width:500px;
			background-color: rgba(231,246,253,0.8);
			position:absolute;
			left:350;
			top:30;
			z-index:800;
			display:none;
		}
		#entryTracePanel_list_button {
		    float:right;
		    cursor: pointer;
		}
	
	</style>
	<div id="entryTracePanel_list" class="panel panel-info">
	  <div class="panel-heading" style="cursor:move;">  
	  	<span id="entryTracePanel_list_button">
        	<i class="glyphicon glyphicon-remove"></i>
        </span>
                          轨迹列表
	  </div>
	  <div id="entryTracePanel_list_content" class="panel-body" style="padding-top:5px;padding-bottom:5px;">
	    <table class="table table-hover" style="font-size:14px;line-height:14px;margin-bottom:0px;">
	    	<thead>
	    		<tr>
	    			<th>#</th>
	    			<th>起始时间--终止时间</th>
	    			<th>时长</th>
	    			<th>里程<small>(km)</small></th>
	    		</tr>
	    	</thead>
			<tbody>
			   <!--<tr>
			      <th scope="row">1</th>
			      <td>10:21:10--12:22:22</td>
			      <td>1111</td>
			      <td>1111</td>
			   </tr>
			   <tr>
			      <th scope="row">2</th>
			      <td>10:21:10--12:22:22</td>
			      <td>1111</td>
			      <td>1111</td>
			   </tr>-->
			</tbody>
		</table>
	  </div>
	</div>
	
	
	
		<style type="text/css">
	    #entryTracePanel{
			width:300px;
			background-color: rgba(231,246,253,0.8);
			position:absolute;
			left:50;
			top:30;
			z-index:800;
		}
		#entryTracePanel_button {
		    float:right;
		    cursor: pointer;
		}
		
		#entryTracePanel_mask {    
            position: absolute; top: 0px; filter: alpha(opacity=60); background-color: #777;  
            z-index: 1002; left: 0px;  
            opacity:0.5; -moz-opacity:0.5;  
     
        }  

		
	</style>
	
	<div id="showPolePanel" class="panel panel-info" >
	  <div class="panel-heading" style="cursor:move;">  
	  	<span id="showPolePanel_button">
        	<i class="glyphicon glyphicon-chevron-up"></i>
        </span>
                           显示点位
	  </div>
	  <div id="showPolePanel_content">
		  <div class="form-group">
		  	 <label for="customer_area">片区:</label>
		  	 <select id="customer_area" class="selectpicker form-control"  title="请选择" >
		        
		      </select>
		  </div>
	      <div class="form-group">
	        <label for="customeres">派出所:</label>
		  	<select id="customeres" class="selectpicker form-control" multiple data-live-search="true" data-max-options="10" data-size="auto"  data-done-button="true" title="必选，最多10个">
			    
			</select>
		  </div>
		  <button id="showPolePanel_querybutton" type="button" class="btn btn-primary btn-block">查询</button>
	  </div>
	 </div>
	 
	 
	<div id="entryTracePanel" class="panel panel-info" >
	  
	  <!-- Default panel contents   glyphicon glyphicon-chevron-down-->
	  <div class="panel-heading" style="cursor:move;">  
	  	<span id="entryTracePanel_button">
        	<i class="glyphicon glyphicon-chevron-up glyphicon-chevron-down"></i>
        </span>
                           作业单位行车轨迹
	  </div>
	  <div id="entryTracePanel_content"  >
	  	<ul class="nav nav-tabs nav-justified">
		  <li role="presentation" class="active"><a href="#realtime_tab" role="tab" data-toggle="tab">实时监控</a></li>
		  <li role="presentation"><a href="#history_tab" role="tab" data-toggle="tab">历史轨迹</a></li>
		</ul>
		<div class="tab-content">
		    <div role="tabpanel" class="tab-pane active" id="realtime_tab">
		    	<table class="table table-hover" style="margin-bottom:0px;">
				  <tbody>
			        <!--<tr >
			          <th scope="row" width="20"><input data-uuid="11" data-loginName="loginName" type="checkbox" /></th>
			          <td>作业单位1</td>
			          <td>最后更新时间</td>
			        </tr>
			        <tr>
			          <th scope="row"><input type="checkbox"/></th>
			          <td>作业单位2</td>
			          <td>最后更新时间</td>
			        </tr>-->
			      </tbody>
				</table>
		    </div>
		    <div role="tabpanel" class="tab-pane" id="history_tab">
		    	<form class="form-inline" style="margin-top:5px;margin-bottom:5px;">
				  <div class="form-group" >
				    <label for="datetimepicker">日期查询:</label>
				    <div class="input-group" >
				      <input type="text" class="form-control" id="datetimepicker" placeholder="选择日期" style="cursor: pointer;">
				      <div class="input-group-addon" style="cursor: pointer;"><i class="glyphicon glyphicon-chevron-down"></i></div>
				    </div>
				  </div>
				</form>
		    	<table class="table table-hover" style="margin-bottom:0px;">
				  <tbody>
			       <!-- <tr data-uuid="11" data-loginName="loginName">
			          <th scope="row">1</th>
			          <td>作业单位1</td>
			        </tr>
			        <tr data-uuid="11" data-loginName="loginName">
			          <th scope="row">2</th>
			          <td>作业单位2</td>
			        </tr>-->
			      </tbody>
				</table>
		    </div>
		 </div>
	  <div>
	</div>


	

	<script type="text/javascript" src="TraceApp.js"> </script>
	<script type="text/javascript" src="tracksControl.js"> </script> 
	<script type="text/javascript" src="LuShu.js"></script>
	<script type="text/javascript" src="TimeControl.js"></script>
	

	
</body> 
</html> 