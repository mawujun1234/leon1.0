<%@ page language="java" import="java.util.*,com.mawujun.shiro.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>Barcode</title>
    <base href="">
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

	
	<%@include file="../../common/init.jsp" %>
	<script type="text/javascript" src="../baseinfo/CommCombo.js"></script>
	<script type="text/javascript" src="InstallInApp.js"></script>
	<script type="text/javascript">
		var loginUsername='<%=ShiroUtils.getName()%>';
	</script>
  </head>
  
  <body>
   
  </body>
</html>
