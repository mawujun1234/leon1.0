<%@ page language="java" import="java.util.*,com.mawujun.shiro.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>地图</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=ED0fe5c7c869da5ee4260b4006e811b8"></script>
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

	<style  type="text/css">
		 /**隐藏百度地图的标签**/
    .anchorBL{
    	display:none;
    }
	</style>
	<%@include file="../../common/init.jsp" %>
	<script type="text/javascript" src="MapApp.js"></script>
	<script type="text/javascript">

	</script>
  </head>
  
  <body>
   
  </body>
</html>
