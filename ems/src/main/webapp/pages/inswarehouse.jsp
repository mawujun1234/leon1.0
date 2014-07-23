<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<html>
  <head>
    <title>设备入库</title>
    <%@ include file="../utils/meta.inc"%>
    <!--  <script type="text/javascript" src="../print/LodopFuncs.js"></script>-->
    <script type="text/javascript" src="widgetutils.js"></script>
     <script type="text/javascript" src="obj/equipment.js"></script>
    <script type="text/javascript" src="inswarehouse.js"></script>
    <!-- 
    <object id="LODOP_OB" classid="clsid:2105C259-1E0C-4534-8141-A753534CB4CA" width=0 height=0>
			<embed id="LODOP_EM" type="application/x-print-lodop" width=0 height=0 pluginspage="../print/install_lodop32.exe"></embed>
		</object>
		 -->
	<style type="text/css">
		.icon-clearall{
		    background-image: url(../images/all_clear.png) !important;
		}
		.icon-add{
		    background-image: url(../images/add.png) !important;
		}
		.icon-print{
		    background-image: url(../images/print.png) !important;
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
