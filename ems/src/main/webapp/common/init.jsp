<%@ page  isELIgnored="false" language="java" pageEncoding="UTF-8" import="com.mawujun.util.web.WebUtils"%>
<%
String extjscontextPath=request.getContextPath();
//String ip=request.getRemoteAddr();
//if("localhost".equals(ip) ||"127.0.0.1".equals(ip) || ip.startsWith("192.168.")){
//	
//} else {
	//http://extj1234.duapp.com
	//主要用于发布到到百多的开发者平台的时候使用的，这个时候war包就不会把extjs的内容打包进去了
	//extjscontextPath="http://extj1234.duapp.com";
//}

String contextPath = request.getContextPath();
String requestURI=request.getRequestURI();
String aa=requestURI.replaceAll("/"+contextPath+"/", "/");
%>
<script src="<%=request.getContextPath()%>/menu/queryElementPermission.do?url=<%=aa %>&timestamp="+new Date().getTime()></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/common/icons.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/common/pngs.css">

<link id="theme" rel="stylesheet" type="text/css" href="<%=extjscontextPath%>/ext-4.2.1/resources/css/ext-all.css" />
<script type="text/javascript" src="<%=extjscontextPath%>/ext-4.2.1/bootstrap.js"></script>
<%-- <script type="text/javascript" src="<%=extjscontextPath%>/ext-4.2.1/ext-all.js"></script>--%>
<script type="text/javascript" src="<%=extjscontextPath%>/ext-4.2.1/locale/ext-lang-zh_CN.js"></script>

<script type="text/javascript" src="<%=request.getContextPath()%>/common/common.js"></script>


    
<style>

</style>
<%

String servletPath=request.getServletPath();
//String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+servletPath;
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
//String springPrev="/app";
//String uri=request.getRequestURI();


%>


<script type="text/javascript">

Ext.ContextPath="<%=request.getContextPath()%>";//应用程序上下文
Ext.JspContextPath="<%=request.getContextPath()%>";
Ext.QuickTips.init();


Ext.ns('Ems');
Ext.Loader.setConfig({
	enabled: true,
	paths:{
		'Ems':'<%=route%>',
		'Ext.ux':'<%=route%>'+'/ext-4.2.1/examples/ux'
		//'MyDesktop':'.'
	}
});

//Ext.ecode_length=17;
Ext.ecode_length=20;

</script>

