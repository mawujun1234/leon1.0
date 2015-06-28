<%@ page language="java" import="java.util.*,com.mawujun.shiro.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>Repair</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

	
	<%@include file="../../common/init.jsp" %>
	<script type="text/javascript" src="MgrRepairApp.js"></script>
	<script type="text/javascript">
		var loginUsername='<%=ShiroUtils.getUserName()%>';
	</script>
	<style>
	.scrap_edit{
			 background: url(../icons/book_edit.png) left top no-repeat !important;  
		}
		.scrap_look{
			 background: url(../icons/book_magnify.png) left top no-repeat !important;  
		}
	</style>
  </head>
  
  <body>
   
  </body>
</html>
