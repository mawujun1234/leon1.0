Ext.require("Ems.task.Task");
Ext.require("Ems.task.TaskQueryGrid");
//Ext.require("Ems.task.TaskTree");
//Ext.require("Ems.task.TaskForm");
Ext.onReady(function(){

	
	 grid=Ext.create('Ems.task.TaskQueryGrid',{
		region:'west',
		split: true,
		autoLoad1:autoLoad,
		//collapsible: true,
		//title:'XXX表格',
		width:400
	});
	



	
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'fit',
		items:[grid],
		listeners:{
			render:function(){
//				var search=location.search
//				 var obj = new Object();  
//		        if (search.indexOf("?") != -1) {  
//		            var search = search.substr(search.indexOf("?") + 1);  
//		            var strs = search.split("&");  
//		            for (var i = 0; i < strs.length; i++) {  
//		                var tempArr = strs[i].split("=");  
//		                obj[tempArr[0]] = tempArr[1];  
//		            }  
//		        }  
//		        if(obj.callBack){
//		        	window[obj.callBack](obj);
//		        } else {
//		        	grid.getStore().load();
//		        }
				
			}
		}
	});

});