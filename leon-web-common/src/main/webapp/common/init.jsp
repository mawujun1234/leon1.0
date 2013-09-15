<%@ page language="java" pageEncoding="UTF-8" import="com.mawujun.util.web.WebUtils"%>
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

%>
<link id="theme" rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/ext-4/resources/css/ext-all<%=jsTheme %>.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/common/icons.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/common/pngs.css">

<script type="text/javascript" src="<%=request.getContextPath()%>/ext-4/bootstrap.js"></script>
<%if(theme!=null && !"".equals(theme)) { %>
<script type="text/javascript" src="<%=request.getContextPath()%>/ext-4/ext-theme<%=jsTheme %>.js"></script>
<%} %>

<script type="text/javascript" src="<%=request.getContextPath()%>/ext-4/locale/ext-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/common.js"></script>

<script type="text/javascript" src="<%=request.getContextPath()%>/common/ux/BaseAjax.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/ux/BaseTree.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/LoginWin.js"></script>

    
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
defaultTheme = '<%=theme%>',
//var servletPath='<%=servletPath%>';
//int routeLength	=servletPath.split(regex).length-1;
//if
Ext.ContextPath="<%=request.getContextPath()%>/app";//应用程序上下文
Ext.JspContextPath="<%=request.getContextPath()%>";
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

