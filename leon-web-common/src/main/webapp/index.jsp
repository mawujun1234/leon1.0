<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>首页</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	
	<%@include file="common/init.jsp" %>
	<script type="text/javascript" src="desktop/Desktop.js"></script>
	<script type="text/javascript" src="index.js"></script>
	<script type="text/javascript" src="desktop/MenuPlugin.js"></script>
	<script type="text/javascript" src="desktop/menu/Menu.js"></script>
	<script type="text/javascript" src="desktop/options-toolbar.js"></script>
	<script type="text/javascript" src="<%=path%>/SwitchUserWin.js"></script>

  </head>
  
  <body>
    
  </body>
</html>
