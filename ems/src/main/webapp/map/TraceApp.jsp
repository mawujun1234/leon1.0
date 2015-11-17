<%@ page language="java" import="java.util.*,com.mawujun.shiro.*" pageEncoding="UTF-8"%>
<html> 
<head> 
	<meta charset="utf-8" /> 
	<title>轨迹</title> 
	<style type="text/css">
		body, html{width: 100%;height: 100%;margin:0;font-family:"微软雅黑";}
		#map_canvas{width:100%;height:100%;}
		#result {width:100%}
		
		/**隐藏百度地图的标签**/
	    .anchorBL{
	    	display:none;
	    }
	    
	    #entryTracePanel{
			overflow-x:hidden;
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
	
	<script type="text/javascript" >
		var Ext={};
		Ext.ContextPath="<%=request.getContextPath()%>";
	</script>
	<script src="http://api.map.baidu.com/api?v=2.0&ak=ED0fe5c7c869da5ee4260b4006e811b8"></script>
	<script type="text/javascript" src="http://api.map.baidu.com/library/LuShu/1.2/src/LuShu_min.js"></script>
	<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
	<script src="/jquery/jquery-1.11.3.min.js"></script>
	
	<!-- 新 Bootstrap 核心 CSS 文件 -->
	<link rel="stylesheet" href="/bootstrap/3.3.5/css/bootstrap.min.css">
	
	<!-- 可选的Bootstrap主题文件（一般不用引入） -->
	<link rel="stylesheet" href="/bootstrap/3.3.5/css/bootstrap-theme.min.css">
	
	
	
	<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
	<script src="/bootstrap/3.3.5/js/bootstrap.min.js"></script>
	
	<link rel="stylesheet" type="text/css" href="/jquery/datetimepicker/jquery.datetimepicker.css"/ >
	<script src="/jquery/datetimepicker/jquery.datetimepicker.full.min.js"></script>

</head> 
<body> 
	<div id="map_canvas"></div> 
	<!--
	<div id="result"></div>
	<button id="run">开始</button> 
	<button id="stop">停止</button> 
	<button id="pause">暂停</button> 
	<button id="hide">隐藏信息窗口</button> 
	<button id="show">展示信息窗口</button> 
	 -->
	
	
	<div id="entryTracePanel" class="panel panel-info">
	  <div id="entryTracePanel_mask"></div>
	  <!-- Default panel contents   glyphicon glyphicon-chevron-down-->
	  <div class="panel-heading" style="cursor:move;">  
	  	<span id="entryTracePanel_button">
        	<i class="glyphicon glyphicon-chevron-up glyphicon-chevron-down"></i>
        </span>
                           作业单位行车轨迹
	  </div>
	  <div id="entryTracePanel_content">
	  	<ul class="nav nav-tabs nav-justified">
		  <li role="presentation" class="active"><a href="#realtime_tab" role="tab" data-toggle="tab">实时监控</a></li>
		  <li role="presentation"><a href="#history_tab" role="tab" data-toggle="tab">历史轨迹</a></li>
		</ul>
		<div class="tab-content">
		    <div role="tabpanel" class="tab-pane active" id="realtime_tab">
		    	<table class="table table-hover">
				  <tbody>
			        <tr >
			          <th scope="row" width="20"><input data-uuid="11" data-loginName="loginName" type="checkbox"></th>
			          <td>作业单位1</td>
			          <td>最后更新时间</td>
			        </tr>
			        <tr>
			          <th scope="row"><input type="checkbox"></th>
			          <td>作业单位2</td>
			          <td>最后更新时间</td>
			        </tr>
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
		    	<table class="table table-hover">
				  <tbody>
			        <tr data-uuid="11" data-loginName="loginName">
			          <th scope="row">1</th>
			          <td>作业单位1</td>
			        </tr>
			        <tr data-uuid="11" data-loginName="loginName">
			          <th scope="row">2</th>
			          <td>作业单位2</td>
			        </tr>
			      </tbody>
				</table>
		    </div>
		 </div>
	  <div>
	</div>
	<script type="text/javascript" src="TraceApp.js"> 
</script> 
</body> 
</html> 