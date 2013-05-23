Ext.require('Leon.desktop.constant.Constant');
Ext.onReady(function(){
//	var grid=Ext.create('Leon.desktop.menu.MenuGrid',{
//		region:'west',
//		split: true,
//		collapsible: true,
//		title:'菜单',
//		width:200
//	});
//	grid.on('itemclick',function(view,record,item,index){
//		//注意这样是否会报错，如果不会报错，就完善文档
//		//var MenuItem= Ext.ModelManager.getModel('Leon.desktop.menu.MenuItem')
//		Ext.Ajax.request({
//			url:Ext.ContextPath+'/menuItem/get',
//			method:'POST',
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
//		
//		tree.setMenuId(record.get("id"));
//		//tree.getStore().reload({node:tree.getRootNode( )});
//		form.getForm().reset(false);
//		if(tree.getMenuId()=="default"){
//			form.setReadonlyItem4DefauleMenu(true);
//			tree.setDisableAction(true);
//		} else {
//			form.setReadonlyItem4DefauleMenu(false);
//			tree.setDisableAction(false);
//		}
//	});

	
	var tree=Ext.create('Leon.common.ux.BaseTree',{
		title:'菜单树',
		model:"Leon.desktop.constant.Constant",
		region:'center'
	});
	tree.on('itemclick',function(view,record,item,index){

	});
	var grid=Ext.create('Leon.common.ux.BaseGrid',{
		region:'east',
		//plugins:[{ptype:'treeexten'}],
		model:'Leon.desktop.constant.Constant',
		autoSync:false,
		//store:store,
		collapsible: true,
		selType :'cellmodel',
		autoNextCellColIdx:0,
		title:'菜单',
		columns:[{dataIndex:'id',text:'222',editor: {
	                xtype: 'textfield',
	                allowBlank: false,
	                selectOnFocus:true
	            }},{dataIndex:'text',text:'111',editor: {
	                xtype: 'textfield',
	                allowBlank: false,
	                selectOnFocus:true
	            }}
	            ],
		width:400
	});
	
	
	
	
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'border',
		items:[tree,grid]
	});
	
	
});