Ext.require('Leon.desktop.role.Role');
Ext.require('Leon.common.ux.BaseGrid');
Ext.require('Leon.desktop.role.RoleFunTree');
Ext.require('Leon.desktop.role.RoleRoleGrid');
Ext.onReady(function(){
	
	var tree=Ext.create('Leon.common.ux.BaseTree',{
		//title:'常数管理',
		defaultRootText:'角色管理',
		//useArrows: true,
		displayField :'name',
		rootVisible:false,
		model:'Leon.desktop.role.Role',
		region:'west',
		split:true,
		flex: 0.8
//		listeners:{		
//			"beforeitemclick":function(view,record,item,index){
//				tree.getStore().getProxy().extraParams={discriminator:record.get("discriminator")}
//			}
//		}
	});
	
	tree.removeAction("create");
	var createModule = new Ext.Action({
		    text: '新增目录',
		    handler: function(){
		    	var parent=tree.getSelectionModel( ).getLastSelected( )||tree.getRootNode( );    
				if(parent.get("roleEnum")=="role"){
					Ext.Msg.alert("消息","角色不能增加下级节点!");
					return;
				}
		    	tree.onCreate({name:'新目录','roleEnum':'roleCategory'});
		    },
		    iconCls: 'role-category-add'
	});
    tree.addAction(createModule,0);
    var createFun = new Ext.Action({
		text: '新增角色',
		handler: function(){
			var parent=tree.getSelectionModel( ).getLastSelected( )||tree.getRootNode( );    
			if(parent.get("funEnum")=="fun"){
				Ext.Msg.alert("消息","功能不能增加下级节点!");
				return;
			}
		    tree.onCreate({name:'新角色','roleEnum':'role'});
		},
		iconCls: 'role-add-iconCls'
	});
    tree.addAction(createFun,1);
    
    var roleId="";
    tree.on('itemclick',function(view,record,item,index){
    	if(record.get("roleEnum")!='role'){
    		tabPanel.mask();
    		return;
    	}
    	
    	roleId=record.get('id');
    	Ext.Ajax.request({
    		url:'/roleFun/query',
    		method:'POST',
    		params:{roleId:roleId},
    		success:function(response){
    			//console.log(response.responseText);
    			var obj=Ext.decode(response.responseText);
    			//alert(obj.root);
    			roleFunTree.checkingFunes(obj.root);
    			tabPanel.unmask();
    		}   		
    	});
    	roleInheritGrid.currentRole=record;
    	roleInheritGrid.getStore().load({params:{otherId:record.getId(),roleRoleEnum:'inherit'}});
    	roleMutexGrid.currentRole=record;
    	roleMutexGrid.getStore().load({params:{otherId:record.getId(),roleRoleEnum:'mutex'}});
    });
   
    
	var roleFunTree=Ext.create('Leon.desktop.role.RoleFunTree',{title:'权限'});
	roleFunTree.on('checkchange',function(node,checked){
    	var url='/roleFun/create';
    	var params={};
    	var isDestroy=false;
    	if(!checked){
    		isDestroy=true;
    		url='/roleFun/destroy';
    		params={
    			id:node.roleAssociation.id
    		};
    		//alert('删除还没有做');
    	} else {
    		
    		params ={
	    		roleId:roleId,
	    		funId:node.getId(),
	    		permissionType:'publicP'
	    	}
    	}
     	Ext.Ajax.request({
    		url:url,
    		method:'POST',
    		params :params,
    		success:function(response){
    			var obj=Ext.decode(response.responseText);
    			node.set('permissionType',params.permissionType);
    			if(isDestroy){
    				node.roleAssociation=null;
    			} else {
    				node.roleAssociation=obj.root;
    			}
    			//alert(node.roleAssociation);
    		}   		
    	});
    });
    
    var roleInheritGrid=Ext.create('Leon.desktop.role.RoleRoleGrid',{
    	title:'继承角色',
    	roleRoleEnum:'inherit',
    	currentRole:null
    });
    var roleMutexGrid=Ext.create('Leon.desktop.role.RoleRoleGrid',{
    	title:'互斥角色',
    	roleRoleEnum:'mutex',
    	currentRole:null
    });

	var tabPanel=Ext.create('Ext.tab.Panel', {
		region:'center',
		split:true,
	    activeTab: 0,
	    items: [roleFunTree,roleInheritGrid,roleMutexGrid],
	    listeners:{
	    	render:function(tabPanel){
	    		tabPanel.mask();
	    	}
	    }
	});

	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'border',
		items:[tree,tabPanel]
	});
	

	
});