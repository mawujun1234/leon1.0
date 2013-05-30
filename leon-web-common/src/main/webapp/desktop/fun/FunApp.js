Ext.require('Leon.desktop.fun.FunTree');
Ext.require('Leon.desktop.fun.FunForm');
//Ext.require('Leon.common.ux.BaseAjax');//這個必須要加的
//Ext.require('Leon.common.ux.BaseTree');//這個必須要加的
Ext.onReady(function(){
	var tree=Ext.create('Leon.desktop.fun.FunTree',{
		region:'west',
		split: true,
		collapsible: true,
		title:'功能树',
		width:400
	});
	tree.removeAction(0);
	var createModule = new Ext.Action({
		    text: '新增模块',
		    handler: function(){	
		    	me.onCreate({text:'新模块','funEunm':'module'});
		    },
		    iconCls: 'form-save-button'
	});
    tree.addAction(createModule,0);
    var createFun = new Ext.Action({
		text: '新增功能',
		handler: function(){
		    me.onCreate({text:'新功能','funEunm':'fun'});
		},
		iconCls: 'form-save-button'
	});
    tree.addAction(createFun,1);

    
	var form=Ext.create('Leon.desktop.fun.FunForm',{
		region:'center'
	});
	tree.on('itemclick',function(view,record,item,index){
		//alert(1);
		if(record.get("funEnum")=='module'){
			form.getForm().findField("url").hide();
		} else {
			form.getForm().findField("url").show();
		}
		form.getForm().loadRecord(record);
	});
	
	
	
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'border',
		items:[tree,form]
	});

});