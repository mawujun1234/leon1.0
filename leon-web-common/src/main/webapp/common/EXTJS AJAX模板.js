Ext.Ajax.request({
	waitTitle:"请稍候",
	method:'POST',
	url:'',
	success:function(response,options){
	        var obj= Ext.decode(response.responseText) ;             
			if(obj.success==false){
				Ext.example.msg("消息",obj.msg);
				return;
			} 
			//如果成功
			//开始
	}
});	