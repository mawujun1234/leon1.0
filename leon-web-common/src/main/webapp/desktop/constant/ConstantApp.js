Ext.require('Leon.desktop.constant.Constant');
Ext.require('Leon.desktop.constant.ConstantType');
Ext.require('Leon.common.ux.BaseGrid');

Ext.onReady(function(){

	var tree=Ext.create('Leon.common.ux.BaseTree',{
		title:'常数管理',
		defaultRootText:'常数管理',
		//useArrows: true,
		fields:['id','text','discriminator'],
		url:'/constantType/query',
		region:'west',
		split:true,
		width:500
	});

	tree.on('itemclick',function(view,record,item,index){
		//alert(record.get("discriminator"));
		if(record.get("id")=="root"){
			grid.reconfigure('Leon.desktop.constant.ConstantType',[{dataIndex:'text',text:'名称',editor: {
		                xtype: 'textfield',
		                allowBlank: false,
		                selectOnFocus:true
		            }},{dataIndex:'remark',text:'备注',flex:1,editor: {
		                xtype: 'textfield',
		                allowBlank: true,
		                selectOnFocus:true
		            }}
		    ]);
			grid.discriminator="ConstantType";
			//grid.getStore().reload();
		} else if(record.get("discriminator")=="ConstantType"){			
			if(grid.discriminator!="Constant"){
				grid.reconfigure('Leon.desktop.constant.Constant',[{dataIndex:'id',text:'编码',editor: {
		                xtype: 'textfield',
		                allowBlank: true,
		                selectOnFocus:true
		            }},{dataIndex:'text',text:'名称',editor: {
		                xtype: 'textfield',
		                allowBlank: false,
		                selectOnFocus:true
		            }},{dataIndex:'remark',text:'备注',flex:1,editor: {
		                xtype: 'textfield',
		                allowBlank: true,
		                selectOnFocus:true
		            }}
		    	]);
			} 
			grid.discriminator="Constant";
		} else if(record.get("discriminator")=="Constant"){			
			if(grid.discriminator!="Constant"){
				grid.reconfigure('Leon.desktop.constant.ConstantItem',[{dataIndex:'id',text:'编码',editor: {
		                xtype: 'textfield',
		                allowBlank: true,
		                selectOnFocus:true
		            }},{dataIndex:'text',text:'名称',editor: {
		                xtype: 'textfield',
		                allowBlank: false,
		                selectOnFocus:true
		            }},{dataIndex:'ordering',text:'排序',editor: {
		                xtype: 'numberfield',
		                allowBlank: true,
		                selectOnFocus:true
		            }},{dataIndex:'remark',text:'备注',flex:1,editor: {
		                xtype: 'textfield',
		                allowBlank: true,
		                selectOnFocus:true
		            }}
		    	]);
			} 
			grid.discriminator="ConstantItem";
		}
		grid.getStore().reload();
	});
	var grid=Ext.create('Leon.common.ux.BaseGrid',{
		region:'center',
		//plugins:[{ptype:'treeexten'}],
		model:'Leon.desktop.constant.ConstantType',
		autoSync:false,
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
//	grid.on('datachanged',function(){
//		tree.getStore().reload({node:tree.getLastSelected()});
//	});
	
	grid.on('add',function(store,records,index){
		//alert(11);
		//console.dir(record);
		测试是先insert，还是先add事件，看看在什么地方把这个关联关系加进去
		还有就是查看文档，设置关联关系有哪几种方式
		record.set("constantType_id",tree.getLastSelected().getId());
		
	});
	
	
	
	
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'border',
		items:[tree,grid]
	});
	
	
});