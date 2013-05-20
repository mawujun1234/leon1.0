Ext.require('Leon.desktop.fun.FunTree');
Ext.require('Leon.desktop.fun.FunForm');
Ext.require('Leon.common.ux.BaseGrid');
Ext.require('Leon.desktop.menu.Menu');
Ext.onReady(function(){
	
	var grid=Ext.create('Leon.common.ux.BaseGrid',{
		region:'center',
		//plugins:[{ptype:'treeexten'}],
		model:'Leon.desktop.menu.Menu',
		split: true,
		//store:store,
		collapsible: true,
		title:'菜单',
		columns:[{dataIndex:'id',text:'222'},{dataIndex:'text',text:'111',editor: {
	                xtype: 'textfield',
	                allowBlank: false,
	                selectOnFocus:true
	            }}],
		width:400
	});
	
	
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'fit',
		items:[grid]
	});
	
});