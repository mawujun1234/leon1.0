Ext.require('Leon.desktop.constant.Constant');
Ext.require('Leon.desktop.constant.ConstantType');
Ext.require('Leon.common.ux.BaseGrid');
Ext.require('Leon.desktop.constant.ConstantItem');
Ext.onReady(function(){

	var tree=Ext.create('Leon.common.ux.BaseTree',{
		title:'常数管理',
		defaultRootText:'常数管理',
		//useArrows: true,
		fields:['id','text','discriminator'],
		url:'/constantType/queryAll',
		//region:'west',
		split:true,
		flex: 0.8,
		listeners:{		
			"beforeitemclick":function(view,record,item,index){
				tree.getStore().getProxy().extraParams={discriminator:record.get("discriminator")}
			}
		}
	});
	tree.on("itemclick",function(view,record){
		if(record.get("discriminator")=="ConstantType") {
			constantTypeGrid.hide();
			constantGrid.show();
			constantItemGrid.hide();
		} else if(record.get("discriminator")=="Constant"){
			constantTypeGrid.hide();
			constantGrid.hide();
			constantItemGrid.show();
		} else {
			constantTypeGrid.show();
			constantGrid.hide();
			constantItemGrid.hide();
		}
	});

	var constantTypeGrid=Ext.create('Leon.common.ux.BaseGrid',{
		model:'Leon.desktop.constant.ConstantType',
		autoSync:false,
		autoLoad:false,
		flex: 1,
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
	var constantGrid=Ext.create('Leon.common.ux.BaseGrid',{
		model:'Leon.desktop.constant.Constant',
		autoSync:false,
		autoLoad:false,
		flex: 1,
		hidden:true,
		title:'常数',
		columns:[{dataIndex:'id',text:'编码',editor: {
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
		    	]
		
	});
	
	var constantItemGrid=Ext.create('Leon.common.ux.BaseGrid',{
		model:'Leon.desktop.constant.ConstantItem',
		autoSync:false,
		autoLoad:false,
		flex: 1,
		hidden:true,
		title:'常数项',
		columns:[{dataIndex:'id',text:'编码',editor: {
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
		    	]
		
	});

	
	
	var viewPort=Ext.create('Ext.container.Viewport',{
		//layout:'border',
		layout: {
             type: 'hbox',
             padding:'5',
             align:'stretch'
        },
        defaults:{margin:'0 5 0 0'},
		items:[tree,constantTypeGrid,constantGrid,constantItemGrid]
	});
	
	
});