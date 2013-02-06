/**
 * 
 */
 alert(1);
 Ext.onReady(function(){
 	Ext.Ajax.request({
		waitTitle:"请稍候",
		method:'POST',

		params:{a:1,b:2},

		url:"/bs/example/testDuoDataSource",
		success:function(response,options){
		        var obj= Ext.decode(response.responseText) ;             
				alert(response.responseText);
		}
	});	
 });