Ext.Ajax.timeout=60000000;
Ext.Ajax.on({
	requestexception:function(conn, response, options, eOpts ){
		alert('连接后台失败，请检查网络和后台服务器是否正常运行!');
	}
//	,requestcomplete:function(conn, response, options, eOpts ){
//		alert('后台业务发生错误,错误信息为:'+11);
//		return false;
//	}
});