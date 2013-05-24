Ext.require('Leon.desktop.fun.FunTree');
Ext.require('Leon.desktop.fun.FunForm');
Ext.require('Leon.common.ux.BaseGrid');
Ext.require('Leon.desktop.menu.Menu');
Ext.onReady(function(){
	
//	var grid=Ext.create('Leon.common.ux.BaseGrid',{
//		region:'center',
//		//plugins:[{ptype:'treeexten'}],
//		model:'Leon.desktop.menu.Menu',
//		split: true,
//		autoSync:false,
//		//store:store,
//		collapsible: true,
//		selType :'cellmodel',
//		autoNextCellColIdx:0,
//		title:'菜单',
//		columns:[{dataIndex:'id',text:'222',editor: {
//	                xtype: 'textfield',
//	                allowBlank: false,
//	                selectOnFocus:true
//	            }},{dataIndex:'text',text:'111',editor: {
//	                xtype: 'textfield',
//	                allowBlank: false,
//	                selectOnFocus:true
//	            }}
//	            ],
//		width:400
//	});
	
	var store = Ext.create('Ext.data.TreeStore', {
       		autoLoad:false,
       		//model:'Leon.desktop.fun.Fun',
		    root: {
		        expanded: true,
		        text:'鍔熻兘绠＄悊'
		    },
		    proxy:{
				type:'bajax',
				url:'/fun/queryChildren'
			},
		    fields:['id','text','discriminator'],
		    listeners:{
		    	load:function(store,node,records){//alert(1);
		    		//node.expand();
		    	}
		    }
	});
	var grid=Ext.create('Leon.common.ux.BaseTree',{
		region:'center',
		//plugins:[{ptype:'treeexten'}],
		split: true,
		store:store,
		collapsible: true,
		title:'llll',
		width:400
	});
	
	
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'fit',
		items:[grid]
	});
	
});