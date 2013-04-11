<%@ page language="java" pageEncoding="UTF-8"%>

<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/ext-4/resources/css/ext-all.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/common/icons.css" />

<script type="text/javascript" src="<%=request.getContextPath()%>/ext-4/bootstrap.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/ext-4/locale/ext-lang-zh_CN.js"></script>
    
<style>

</style>

<script type="text/javascript">

Ext.ContextPath="<%=request.getContextPath()%>";//应用程序上下文
//Ext.Loader.setConfig({enabled:true});
//Ext.Loader.setPath({
//	//加载路径配置对象
//});
Ext.ns('Leon');
Ext.Loader.setConfig({
	enabled: true,
	paths:{
		'Leon':'.',
		'Ext.ux':'../ext-4/examples/ux',
		//'MyDesktop':'.'
	}
});
//Ext.setGlyphFontFamily("Pictos");
</script>

