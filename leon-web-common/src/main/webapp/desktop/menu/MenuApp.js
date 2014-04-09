Ext.require('Leon.desktop.menu.MenuItemTree');
Ext.require('Leon.desktop.menu.MenuItemForm');
Ext.require('Leon.desktop.menu.MenuGrid');
Ext.onReady(function(){
	var grid=Ext.create('Leon.desktop.menu.MenuGrid',{
		region:'west',
		split: true,
		collapsible: true,
		title:'菜单',
		width:200
	});
	grid.on('itemclick',function(view,record,item,index){
		var rootNode=tree.getRootNode();
		rootNode.set('text',record.get("text"));
		rootNode.commit();
		
		tree.setMenuId(record.get("id"));
		//tree.getStore().reload({node:tree.getRootNode( )});
		form.getForm().reset(false);

		
		tree.getStore().getProxy().extraParams={menuId:record.get("id")};

		tree.getStore().load({node:tree.getRootNode( )});
		tree.getStore().getProxy().extraParams={menuId:record.get("id")};
	});

	var tree=Ext.create('Leon.desktop.menu.MenuItemTree',{
		title:'菜单树',
		region:'center'
	});
	tree.getStore().getProxy().extraParams={menuId:'default'};
	//tree.getStore().load({node:tree.getRootNode( )});
	tree.on('itemclick',function(view,record,item,index){

		var basicFoem=form.getForm();
		basicFoem.loadRecord(record);
		
		var parent=tree.getStore().getNodeById(record.get("parent_id"));
		if(parent){
			basicFoem.setValues({"parent_text":parent.get("text")}) ;
		} else {
			basicFoem.setValues({"parent_text":null}) ;
		}

		if(record.get("fun_id")){
			basicFoem.findField("fun_text").setValue(record.getFun().get("text"));//.setValues({"fun_text":record.getFun().get("text")}) ;
		} else {
			basicFoem.findField("fun_text").setValue("") ;
		}

	});
	
	var createModule = new Ext.Action({
		    text: '新增菜单',
		    handler: function(){
		    	var parent=tree.getLastSelected();
		    	
		    	showFunTree(parent,tree);
		    },
		    iconCls: 'fun-module-add'
	});
    tree.addAction(createModule,0);
    
	var form=Ext.create('Leon.desktop.menu.MenuItemForm',{
		region:'east',
		split: true,
		collapsible: true,
		title:'表单',
		width:460,
		listeners:{
			createOrupdate:function(form,record){
				tree.getStore().load({node:record});
			}
		}
	});
	
	
	
	
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'border',
		items:[grid,tree,form]
	});
	
	form.on("created",function(record){
		var fun=tree.getSelectionModel().getLastSelected( ) ;
		tree.getStore().load({node:fun});
	});
	
	function showFunTree(parent,menuTree){
		var me=this;
		var tree=Ext.create('Leon.desktop.fun.FunTree',{
			autoInitSimpleAction:false,
			width:400,
			height:500
		});
		tree.on("itemdblclick",function(view,record,index,e){
			//fun_id_txt.setValue(record.get("id"));
			//fun_text_txt.setValue(record.get("text"));
			if(record.get("funEnum")=="module"){
				Ext.Msg.alert("消息","请选择功能节点!");
				return;
			}
			Ext.Ajax.request({
				url:Ext.ContextPath+'/menuItem/createByFun',
				method:'POST',
				params:{funId:record.getId(),parentId:parent?parent.getId():null,menuId:menuTree.getMenuId()},
				success:function(response){
					menuTree.getStore().reload({node:parent});
				}
			});
			win.close();
		});
		var win=Ext.create('Ext.Window',{
			layout:'fit',
			modal:true,
			constrainHeader:true,
			title:'新增菜单',
			items:tree
		});
		win.show();	
	}
	
});