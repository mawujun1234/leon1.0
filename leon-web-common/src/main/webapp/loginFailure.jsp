<%@ page language="java" import="java.util.*,org.springframework.security.web.WebAttributes" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>登陆</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	
	<%@include file="common/init.jsp" %>
  </head>
  
  <body>
    登录失败,<a href="login.jsp">返回</a><br/>
    <%
    Exception exception=(Exception)request.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    out.println(exception.getMessage());
    //exception.printStackTrace();
    %>
  </body>
</html>
