<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>Task</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

	
	<%@include file="../../common/init.jsp" %>
	<%
	String autoLoad=request.getParameter("autoLoad");
	if(autoLoad==null || "".equals(autoLoad.trim())){
		autoLoad="true";
	}
	//String autoLoad="true";
	%>
	<script type="text/javascript">
		var autoLoad=<%=autoLoad%>;
		var grid=null;
		function query4Pole(params){
		
		window.reload=function(){
			if(!grid){
				return;
			}
			grid.getStore().load({params:{
					pole_id:params
				}
			});
			clearInterval(interval);
		}
			var interval=setInterval("reload()",500);
		}
		window.query4Pole=query4Pole;
	</script>
	<script type="text/javascript" src="TaskQueryApp.js"></script>
  </head>
  
  <body>
   
  </body>
</html>
