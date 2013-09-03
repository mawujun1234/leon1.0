<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String sessionId=session.getId();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>功能管理</title>
    <base href="">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">  
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	
	<script type="text/javascript" src="util.js"></script>-->
	<script type="text/javascript" charset="utf-8" src="../../ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="../../ueditor/ueditor.all.min.js"></script>
    <script type="text/javascript" charset="utf-8" src="../../ueditor/lang/zh-cn/zh-cn.js"></script>
	<%@include file="../../common/init.jsp" %>
	<script type="text/javascript">
		var sessionId='<%=sessionId%>';
	</script>
	<script type="text/javascript" charset="utf-8" src="FunApp.js"></script>
  </head>
  
  <body>
   
  </body>
</html>
