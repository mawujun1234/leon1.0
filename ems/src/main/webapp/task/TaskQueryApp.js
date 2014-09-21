Ext.require("Ems.task.Task");
Ext.require("Ems.task.TaskQueryGrid");
//Ext.require("Ems.task.TaskTree");
//Ext.require("Ems.task.TaskForm");
Ext.onReady(function(){
	function query4Pole(params){
		//alert(params.pole_id);
//		grid.getStore().load({params:{
//			pole_id:params.pole_id
//		}});
		
		grid.getStore().load({params:{
			pole_id:params
		}});
	}
	window.query4Pole=query4Pole;
	
	var grid=Ext.create('Ems.task.TaskQueryGrid',{
		region:'west',
		split: true,
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