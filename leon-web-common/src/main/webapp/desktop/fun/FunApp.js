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
		    	var parent=tree.getSelectionModel( ).getLastSelected( )||tree.getRootNode( );    
				if(parent.get("funEnum")=="fun"){
					Ext.Msg.alert("消息","功能不能增加下级节点!");
					return;
				}
		    	tree.onCreate({text:'新模块','funEnum':'module'});
		    },
		    iconCls: 'fun-module-iconCls'
	});
    tree.addAction(createModule,0);
    var createFun = new Ext.Action({
		text: '新增功能',
		handler: function(){
			var parent=tree.getSelectionModel( ).getLastSelected( )||tree.getRootNode( );    
			if(parent.get("funEnum")=="fun"){
				Ext.Msg.alert("消息","功能不能增加下级节点!");
				return;
			}
		    tree.onCreate({text:'新功能','funEnum':'fun'});
		},
		iconCls: 'fun-fun-iconCls'
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