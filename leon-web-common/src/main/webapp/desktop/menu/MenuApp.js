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
		//注意这样是否会报错，如果不会报错，就完善文档
		//var MenuItem= Ext.ModelManager.getModel('Leon.desktop.menu.MenuItem')
		Leon.desktop.menu.MenuItem.load(record.get('rootId'),{
			action:'get',
			success:function(record){
				//console.dir(record.raw);
				var obj=record.raw;
				obj.expanded=true;
				obj.menu_id=record.raw.menu.id;
				//obj.fun_id=record.raw.fun.id;
				//obj.fun_text=record.raw.fun.text;
				var root=tree.setRootNode(obj);
				root.setFun({id:record.raw.fun.id,text:record.raw.fun.text});
				//这个又问题
				//tree.setRootNode(record);
				//root.set('menu_id',record.raw.menu.id);
				
				//tree.getStore().reload({node:tree.getRootNode( )});
				//root.expand( );
			}
		});
		
		
		tree.setMenuId(record.get("id"));
		//tree.getStore().reload({node:tree.getRootNode( )});
		
		if(tree.getMenuId()=="default"){
			form.disableItem4DefauleMenu();
		} else {
			form.enableItem4DefauleMenu();
		}
	});

	var tree=Ext.create('Leon.desktop.menu.MenuItemTree',{
		title:'菜单树',
		region:'center'
	});
	tree.on('itemclick',function(view,record,item,index){
		//alert(1);
		var basicFoem=form.getForm();
		basicFoem.loadRecord(record);
		
		//if(record.getId()!=tree.getRootNode().getId()){
		console.dir(record.raw);
		alert(record.get("fun_id"));前面的实例没有建好的原因
		alert(record.getFun());这里报错
			basicFoem.setValues({"fun_text":record.getFun().get("text")}) ;
			var parent=tree.getStore().getNodeById(record.get("parent_id"));
			if(parent){
				basicFoem.setValues({"parent_text":parent.get("text")}) ;
			} else {
				basicFoem.setValues({"parent_text":null}) ;
			}
		//} 
		

	});
	var form=Ext.create('Leon.desktop.menu.MenuItemForm',{
		region:'east',
		split: true,
		collapsible: true,
		title:'表单',
		width:520
	});
	
	
	
	
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'border',
		items:[grid,tree,form]
	});
	
	form.on("created",function(record){
		var fun=tree.getSelectionModel().getLastSelected( ) ;
		tree.getStore().load({node:fun});
	});
	
});