Ext.require('Leon.desktop.constant.Constant');
Ext.require('Leon.desktop.constant.ConstantType');
Ext.require('Leon.common.ux.BaseGrid');

Ext.onReady(function(){

	var tree=Ext.create('Leon.common.ux.BaseTree',{
		title:'菜单树',
		//fields:['id','text','discriminator'],
		url:'/constantType/queryChildren',
		//model:'Leon.desktop.constant.ConstantType',
		region:'west',
		split:true,
		width:500
	});
	tree.on('itemclick',function(view,record,item,index){
		if(record.get("id")=="root"){
			grid.getStore().reload();
		}
	});
	var grid=Ext.create('Leon.common.ux.BaseGrid',{
		region:'center',
		//plugins:[{ptype:'treeexten'}],
		model:'Leon.desktop.constant.ConstantType',
		autoSync:true,
		autoLoad:false,
		//store:store,
		
		//collapsible: true,
		//selType :'cellmodel',
		autoNextCellColIdx:0,
		title:'常数分类',
		columns:[{dataIndex:'text',text:'名称',editor: {
	                xtype: 'textfield',
	                allowBlank: false,
	                selectOnFocus:true
	            }},{dataIndex:'remark',text:'备注',flex:1,editor: {
	                xtype: 'textfield',
	                allowBlank: true,
	                selectOnFocus:true
	            }}
	    ]
		
	});
	grid.on('datachanged',function(){
		tree.getStore().reload({node:tree.getLastSelected()});
	});
	
	
	
	
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'border',
		items:[tree,grid]
	});
	
	
});