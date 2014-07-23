<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <title>登录</title>
    <%@ include file="utils/meta.inc"%>
    <script type="text/javascript" src="scripts/login.js"></script>
	<style type="text/css">
	  .icon-docs{
	 	background-image:url(images/tabs.gif) !important;
	}
	.icon-help{
	 	background-image:url(images/help.png) !important;
	}
	.icon-about{
	 	background-image:url(images/about.png) !important;
	}
	.icon-login{
	 	background-image:url(images/login.png) !important;
	}
	.icon-center-align{
	 	background-image:url(images/center.png) !important;
	}
	.icon-stretch-align{
	 	background-image:url(images/stretch.png) !important;
	}
		#CheckCode{float:left;} 
  .x-form-code{width:73px;height:20px;vertical-align:middle;cursor:pointer; float:left; margin-left:7px;} 
    
	.user{ background:url(images/user.png) no-repeat 1px 2px; }  
		.key{  background:url(images/key.png) no-repeat 1px 2px;  }  
	.rand{ background:url(images/rand.png) no-repeat 1px 2px; }  
	.user,.key,.rand{  
		background-color:#FFFFFF;  
		padding-left:20px; 
		font-weight:bold;  
		color:#000033;  
	}
	</style>
  </head>
  <body>
   	 <!-- <div id='view-port'></div> -->
   	 <script type="text/javascript">
   	
   	Ext.onReady(function(){
   		login();
   	});
   	 </script>
  </body>
</html>
