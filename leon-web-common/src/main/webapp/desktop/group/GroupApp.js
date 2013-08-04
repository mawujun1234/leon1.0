Ext.require('Leon.desktop.group.GroupTree')
Ext.onReady(function(){
	var tree=Ext.create('Leon.desktop.group.GroupTree',{
		region:'west',
		width:300
	});
	tree.on('itemclick',function(view,record,item,index){
    	tabPanel.unmask();
    	
//    	roleId=record.get('id');
//    	Ext.Ajax.request({
//    		url:'/roleFun/query',
//    		method:'POST',
//    		params:{roleId:roleId},
//    		success:function(response){
//    			//console.log(response.responseText);
//    			var obj=Ext.decode(response.responseText);
//    			//alert(obj.root);
//    			roleFunTree.checkingFunes(obj.root);
//    			roleFunTree.roleId=roleId;
//    			tabPanel.unmask();
//    		}   		
//    	});
//    	//roleInheritGrid.currentRole=record;
//    	//roleInheritGrid.getStore().load({params:{childId:record.getId(),roleRoleEnum:'inherit'}});
//    	roleMutexGrid.currentRole=record;
//    	roleMutexGrid.getStore().load({params:{ownId:record.getId(),roleRoleEnum:'mutex'}});
//    	
//    	utils.setSubjectId(record.getId());
    });
	
	var tabPanel=Ext.create('Ext.tab.Panel', {
		region:'center',
		split:true,
		mask:true,
	    activeTab: 0,
	    items: [
	        {
	           title: '用户',
	           html:"11111"
	        },
	         {
	           title: '角色',
	           html:"11111"
	        }, {
	           title: '参数设置',
	           html:"11111"
	        }
	    ],
	    listeners:{
	    	render:function(tabPanel){
	    		tabPanel.getEl().mask();
	    	}
	    }
	});
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'border',
		items:[tree,tabPanel]
	});
});