Ext.require('Leon.desktop.group.GroupTree');
Ext.require('Leon.desktop.user.UserSelectGrid');
Ext.require('Leon.desktop.role.RoleSelectPanel');
Ext.require('Leon.desktop.parameter.ParameterUtils');
Ext.onReady(function(){
	var tree=Ext.create('Leon.desktop.group.GroupTree',{
		region:'west',
		split:true,
		width:300
	});
	tree.on('itemclick',function(view,record,item,index){
    	tabPanel.unmask();
    	userGroupGrid.reloadSelected({groupId:record.getId()});
    	roleSelectedTree.reloadSelected({groupId:record.getId()});
    	//获取该用户的参数
		utils.setSubjectId(record.getId());
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
	
    var userGroupGrid=Ext.create('Leon.desktop.user.UserSelectGrid',{
    	url:'/group/queryUser',
    	listeners:{
    		addUser:function(grid,user){
    			var group=tree.getLastSelected();
    			Ext.Ajax.request({
					url:'/group/addUser',
					params:{userId:user.get("id"),groupId:group.getId()},
					method:'POST',
					success:function(){
						grid.getStore().reload();
					}
				});
    		},
    		removeUser:function(grid,user){
    			var group=tree.getLastSelected();
    			Ext.Ajax.request({
					url:'/group/removeUser',
					params:{userId:user.get("id"),groupId:group.getId()},
					method:'POST',
					success:function(){
						grid.getStore().reload();
					}
				});
    		}
    	}
    });
    var roleSelectedTree=Ext.create('Leon.desktop.role.RoleSelectPanel',{
    	url:'/group/queryRole',
    	listeners:{
    		addRole:function(selectedRoleTree,selectRoleNode){
    			var group=tree.getLastSelected();
		        var params={
		            groupId:group.getId(),
		            roleId:selectRoleNode.getId()
		        };
		        Ext.Ajax.request({
		            url:'/group/addRole',
		            method:'POST',
		            params:params,
		            success:function(){
		            	selectedRoleTree.getStore().load({params:{groupId:group.getId()}});
		            }
		        });
    		},
    		removeRole:function(selectedRoleTree,selectRoleNode){
    			var group=tree.getLastSelected();
		        var params={
		            groupId:group.getId(),
		            roleId:selectRoleNode.getId()
		        };
    			Ext.Ajax.request({
		            url:'/group/removeRole',
		            method:'POST',
		            params:params,
		            success:function(){
		            	selectRoleNode.remove(true);
		            }
		        });
    		}
    	}
    });
	var tabPanel=Ext.create('Ext.tab.Panel', {
		region:'center',
		split:true,
		mask:true,
	    activeTab: 0,
	    items: [
	       userGroupGrid,
	       roleSelectedTree
	    ],
	    listeners:{
	    	render:function(tabPanel){
	    		tabPanel.getEl().mask();
	    	}
	    }
	});
	//参数设置
	var utils=new Leon.desktop.parameter.ParameterUtils();
	utils.getForm('GROUP',function(paramform){
		paramform.setTitle("参数设置");
		tabPanel.add(paramform);
	});
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'border',
		items:[tree,tabPanel]
	});
});