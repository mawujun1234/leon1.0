<%@ page  isELIgnored="false" language="java" pageEncoding="UTF-8" import="com.mawujun.util.web.WebUtils"%>
<%
String theme=request.getParameter("theme");
if(theme==null){
	Cookie cookie=WebUtils.getCookie(request, "theme");
	if(cookie==null || cookie.getValue()==null){
		theme="classic";
	} else {
		theme=cookie.getValue();
	}
	
} else {
	//theme="-"+theme;
	Cookie c = new Cookie("theme",theme) ;
	//设定有效时间  以s为单位
	c.setMaxAge(5*365*24*60*60) ;
	//设置Cookie路径和域名
	// c.setPath("/") ;
	// c.setDomain(".zl.org") ;  //域名要以“.”开头
	//发送Cookie文件
	 response.addCookie(c) ;
}
String jsTheme="";
if("classic".equals(theme)){
	jsTheme="";
} else {
	jsTheme="-"+theme;
}
String extjscontextPath=request.getContextPath();
//String ip=request.getRemoteAddr();
//if("localhost".equals(ip) ||"127.0.0.1".equals(ip) || ip.startsWith("192.168.")){
//	
//} else {
	//http://extj1234.duapp.com
	//主要用于发布到到百多的开发者平台的时候使用的，这个时候war包就不会把extjs的内容打包进去了
	//extjscontextPath="http://extj1234.duapp.com";
//}

%>

<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/common/icons.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/common/pngs.css">

<link id="theme" rel="stylesheet" type="text/css" href="<%=extjscontextPath%>/ext-4/resources/css/ext-all<%=jsTheme %>.css" />
<script type="text/javascript" src="<%=extjscontextPath%>/ext-4/bootstrap.js"></script>
<%if(theme!=null && !"".equals(theme)) { %>
<script type="text/javascript" src="<%=extjscontextPath %>/ext-4/ext-theme-<%=theme %>.js"></script>
<%} %>
<script type="text/javascript" src="<%=extjscontextPath%>/ext-4/locale/ext-lang-zh_CN.js"></script>

<script type="text/javascript" src="<%=request.getContextPath()%>/common/common.js"></script>

<script type="text/javascript" src="<%=request.getContextPath()%>/common/ux/BaseAjax.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/ux/BaseTree.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/ux/BaseStore.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/LoginWin.js"></script>

    
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
String springPrev="/app";
String uri=request.getRequestURI();

//如果是首页和登录页面，就不加载页面数据权限控制
if(uri.indexOf("login.jsp")==-1 && uri.indexOf("index.jsp")==-1){
%>
<script type="text/javascript" src="<%=request.getContextPath()+springPrev %>/fun/generatorPermissionJs"></script>
<%}%>

<script type="text/javascript">
defaultTheme = '<%=theme%>',

Ext.ContextPath="<%=request.getContextPath()+springPrev%>";//应用程序上下文
Ext.JspContextPath="<%=request.getContextPath()%>";
Ext.QuickTips.init();


Ext.ns('Leon');
Ext.Loader.setConfig({
	enabled: true,
	paths:{
		'Leon':'<%=route%>',
		'Ext.ux':'<%=route%>'+'/ext-4/examples/ux'
		//'MyDesktop':'.'
	}
});
</script>

