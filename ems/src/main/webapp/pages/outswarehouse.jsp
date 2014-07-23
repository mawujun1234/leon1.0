<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<html>
  <head>
    <title>设备出库</title>
    <%@ include file="../utils/meta.inc"%>
    <link rel="stylesheet" type="text/css" href="../ext-4.2.1/examples/ux/css/ItemSelector.css" />
    <script type="text/javascript" src="widgetutils.js"></script>
    <script type="text/javascript" src="obj/equipment.js"></script>
    <script type="text/javascript" src="outswarehouse.js"></script>
	<style type="text/css">
		.icon-clearall{
		    background-image: url(../images/all_clear.png) !important;
		}
		.icon-add{
		    background-image: url(../images/add.png) !important;
		}
		#toolbar-title-text{
			color:#04408c;
			line-height:17px;
			font-family:tahoma,arial,verdana,sans-serif;
			font-size:11px;
			font-weight:bold;
		}
	</style>
  </head>
  
  <body>
   	 <div id='view-port'></div>
  </body>
</html>
