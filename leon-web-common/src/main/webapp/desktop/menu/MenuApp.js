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
//		//注意这样是否会报错，如果不会报错，就完善文档
//		//var MenuItem= Ext.ModelManager.getModel('Leon.desktop.menu.MenuItem')
//		Ext.Ajax.request({
//			url:Ext.ContextPath+'/menuItem/load',
//			params:{id:record.get('rootId')},
//			success:function(response){
//				var obj=Ext.decode(response.responseText);
//				if(obj.success){
//					obj.root.expanded=true;
//					var record=Ext.createModel('Leon.desktop.menu.MenuItem',obj.root);
//					tree.setRootNode(record);
//				}
//			}
//		});	
		var rootNode=tree.getRootNode();
		rootNode.set('text',record.get("text"));
		rootNode.commit();
		
		tree.setMenuId(record.get("id"));
		//tree.getStore().reload({node:tree.getRootNode( )});
		form.getForm().reset(false);
		if(tree.getMenuId()=="default"){
			form.setReadonlyItem4DefauleMenu(true);
			//tree.setDisableAction(true);
		} else {
			form.setReadonlyItem4DefauleMenu(false);
			//tree.setDisableAction(false);
		}
		
		tree.getStore().getProxy().extraParams={menuId:record.get("id")};
//		if(record.get("id")=='default'){
//			tree.disableAction('update','destroy','');
//		}
		tree.getStore().load({node:tree.getRootNode( )});
	});

	var tree=Ext.create('Leon.desktop.menu.MenuItemTree',{
		title:'菜单树',
		region:'center'
	});
	tree.getStore().getProxy().extraParams={menuId:'default'};
	tree.getStore().load({node:tree.getRootNode( )});
	tree.on('itemclick',function(view,record,item,index){
		//alert(1);
		var basicFoem=form.getForm();
		basicFoem.loadRecord(record);
		
		//if(record.getId()!=tree.getRootNode().getId()){
		//alert(record.get("fun_id"));前面的实例没有建好的原因
		//alert(record.getFun());这里报错
			basicFoem.setValues({"fun_text":record.getFun().get("text")}) ;
			var parent=tree.getStore().getNodeById(record.get("parent_id"));
			if(parent){
				basicFoem.setValues({"parent_text":parent.get("text")}) ;
			} else {
				basicFoem.setValues({"parent_text":null}) ;
			}
		//} 
		

	});
	
	var createModule = new Ext.Action({
		    text: '新增菜单',
		    handler: function(){
		    	var parent=tree.getLastSelected();
		    	
		    	showFunTree(parent);
		    },
		    iconCls: 'fun-module-add'
	});
    tree.addAction(createModule,0);
    
	var form=Ext.create('Leon.desktop.menu.MenuItemForm',{
		region:'east',
		split: true,
		collapsible: true,
		title:'表单',
		width:460
	});
	
	
	
	
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'border',
		items:[grid,tree,form]
	});
	
	form.on("created",function(record){
		var fun=tree.getSelectionModel().getLastSelected( ) ;
		tree.getStore().load({node:fun});
	});
	
	function showFunTree(parent){
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
				url:'/menuItem/createByFun',
				method:'POST',
				params:{funId:record.getId(),parentId:parent?parent.getId():null},
				success:function(response){
					
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