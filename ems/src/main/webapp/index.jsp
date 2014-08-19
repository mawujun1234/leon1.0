<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>奥维生产调度平台</title>
<%-- include file="utils/meta.inc" --%>
<%@include file="common/init.jsp" %>
<!-- CSS 文件配置 -->
<link rel="stylesheet" type="text/css"href="css/index.css" />
<link rel="stylesheet" type="text/css"href="css/about.css" />
<!-- 界面引入 -->
<script type="text/javascript" src="scripts/about.js" charset="UTF-8"></script>
<script type="text/javascript" src="scripts/login.js" charset="UTF-8"></script>
<script type="text/javascript" src="scripts/changePwd.js" charset="UTF-8"></script>
<script type="text/javascript" src="scripts/SelLeftNav.js" charset="UTF-8"></script>
<script type="text/javascript" src="scripts/frame.js" charset="UTF-8"></script>
<script type="text/javascript" src="scripts/index.js" charset="UTF-8"></script>
<!-- -->
<script type="text/javascript" src="ext-4.2.1/examples/ux/IFrame.js" charset="UTF-8"></script>

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
	/*登陆窗口css --start--*/
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
	/*登陆窗口css --end--*/
	</style>
</head>
<body scroll="no" id="docs">
	<!-- loading元素 -->
	<div id="loading-mask" style="width:100%;height:100%;background:#c3daf9;position:absolute;z-index:20000;left:0;top:0;">&#160;</div>
	<div id="loading">
		<div class="loading-indicator">
			<img src="images/loading.gif" style="width:16px;height:16px;" align="absmiddle">&#160;正在加载...
		</div>
	</div> 
	<!-- -->
   <div id='div-header'>
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			background="images/bg_main-2.gif" height="80">
			<tr>
				<td>
					<div style="float:right; margin-top:30px;margin-right:10px;color: #CCC;">
				        <a href="javascript:login();void(0);" target="_self" style="padding:5px;color: black;">切换用户</a> |
				        <a href="javascript:changePwd();void(0);" target="_self" style="padding:5px;color: black;">修改密码</a> |
				        <a href="javascript:showSselLeftNavWin();void(0);" target="_self" style="padding:5px;color: black;">选择常用功能</a> |
				        <a href="logout.do" style="padding:5px;color: black;">退出</a>
				    </div>
					<table width="513" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td width="513" height="80" align="right"
								background="images/top-001.jpg">
								<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"
									codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,29,0"
									width="400" height="80">
									<param name="movie" value="images/2.swf">
									<param name="quality" value="high">
									<param name="wmode" value="transparent">
									<!--这里代码可使Flash背景透明 -->
									<embed src="images/2.swf" width="400" height="80"
										quality="high"
										pluginspage="http://www.macromedia.com/go/getflashplayer"
										type="application/x-shockwave-flash" wmode="transparent">
									</embed>
									
								</object>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
</div>

<div id='div-north'></div>
<div id='div-left'></div>
<div id='div-center'></div>
<div id='div-south'></div>
</body>
</html>