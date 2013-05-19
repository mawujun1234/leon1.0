<%@ page language="java" pageEncoding="UTF-8"%>

<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/ext-4/resources/css/ext-all.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/common/icons.css" />

<script type="text/javascript" src="<%=request.getContextPath()%>/ext-4/bootstrap.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/ext-4/locale/ext-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/common.js"></script>

<script type="text/javascript" src="<%=request.getContextPath()%>/common/ux/BaseAjax.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/ux/BaseTree.js"></script>


    
<style>

</style>
<%
String servletPath=request.getServletPath();
int routeLength=servletPath.split("/").length-2;
String route="";
if(routeLength>0){
	for(int i=0;i<routeLength;i++){
		route+="../";
	}
	route=route.substring(0, route.length()-1);
} else if (routeLength==0){
	route=".";
}
%>
<script type="text/javascript">
//var servletPath='<%=servletPath%>';
//int routeLength	=servletPath.split(regex).length-1;
//if
Ext.ContextPath="<%=request.getContextPath()%>";//应用程序上下文
Ext.QuickTips.init();


Ext.ns('Leon');
Ext.Loader.setConfig({
	enabled: true,
	paths:{
		'Leon':'<%=route%>',
		'Ext.ux':'<%=route%>'+'/ext-4/examples/ux',
		//'MyDesktop':'.'
	}
});

//Ext.setGlyphFontFamily("Pictos");
</script>

