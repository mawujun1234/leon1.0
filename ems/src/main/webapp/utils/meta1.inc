
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<%@ page isELIgnored="false" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<!-- Ext Libs -->
	<link rel="stylesheet" type="text/css" href="icons.css" />
   <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/ext-4.2.1/resources/css/ext-all.css" />
   <script type="text/javascript" src="${pageContext.request.contextPath}/ext-4.2.1/bootstrap.js"></script>
   <script type="text/javascript" src="${pageContext.request.contextPath}/ext-4.2.1/locale/ext-lang-zh_CN.js"></script>
   <script type="text/javascript" src="${pageContext.request.contextPath}/utils/fixed.js"></script>
<script type="text/javascript">
	Ext.apply(Ext,{
		top:window.top.Ext,
		ContextPath:'${pageContext.request.contextPath}',
		MessageBoxEx:function(title,msg){
	 	   Ext.Msg.alert(title, msg);
	 	   setTimeout(function(){Ext.Msg.hide();},3000);
	    }
	});
  //document.oncontextmenu=function(){return false;}
  	new Ext.KeyMap(document, [{  
        key: Ext.EventObject.BACKSPACE,  
        fn: function (key, e) {  
            var t = e.target.tagName;
            if (t == "INPUT" || t == "TEXTAREA") { 
               if(e.target.readOnly){
                	e.stopEvent();  
                }
            }  
        }  
    }]);
    
    


    Ext.Loader.setConfig({enabled: true});

    Ext.Loader.setPath('Ext.ux', Ext.ContextPath+'/ext-4.2.1/examples/ux');
</script >