Ext.require('Leon.desktop.role.Role');
Ext.require('Leon.common.ux.BaseGrid');
//Ext.require('Leon.desktop.user.UserForm');
Ext.onReady(function(){
//	var grid=Ext.create('Leon.common.ux.BaseGrid',{
//		model:'Leon.desktop.role.Role',
//		region:'west',
//		split:true,
//		autoSync:false,
//		//autoLoad:true,
//		//autoInitAction:false,
//		flex: 1,
//		//title:'用户管理',
//		columns:[{dataIndex:'name',text:'登陆名'},
//			{dataIndex:'password',text:'密码'},
//	         {dataIndex:'name',text:'姓名'},
//	         {dataIndex:'deleted',text:'是否删除'},
//	         {dataIndex:'deletedDate',text:'删除日期'},
//	         {dataIndex:'enable',text:'是否可用'},
//	         {dataIndex:'locked',text:'是否锁定'},
//	         {dataIndex:'createDate',text:'创建日期',xtype: 'datecolumn',   format:'Y-m-d'},
//	         {dataIndex:'expireDate',text:'过期日期'},
//	         {dataIndex:'lastLoginDate',text:'最后登陆时间'}
//	    ]
//		
//	});
//	var form=Ext.create('Leon.desktop.user.UserForm',{});
//	var win=Ext.create('Ext.Window',{
//			layout:'fit',
//			modal:true,
//			items:[form],
//			listeners:{
//				close:function(panel){
//					grid.getStore().reload();
//				}
//			}
//	});
//	
//	//重载新增的功能，不从表格里面新增
//	grid.onCreate=function(){
//		//alert('建立一个form进行新增。');
//		var modal=Ext.createModel('Leon.desktop.user.User',{enable:true});
//		
//		form.getForm().loadRecord(modal);
//		
//		win.show();
//	}
//	grid.onUpdate=function(){
//		var modal=grid.getLastSelected();
//		form.getForm().loadRecord(modal);
//		win.show();
//	}
	
	
	var tree=Ext.create('Leon.common.ux.BaseTree',{
		//title:'常数管理',
		defaultRootText:'角色管理',
		//useArrows: true,
		displayField :'name',
		model:'Leon.desktop.role.Role',
		region:'west',
		split:true,
		flex: 0.8
//		listeners:{		
//			"beforeitemclick":function(view,record,item,index){
//				tree.getStore().getProxy().extraParams={discriminator:record.get("discriminator")}
//			}
//		}
	});
	
	tree.removeAction("create");
	var createModule = new Ext.Action({
		    text: '新增目录',
		    handler: function(){
		    	var parent=tree.getSelectionModel( ).getLastSelected( )||tree.getRootNode( );    
				if(parent.get("roleEnum")=="role"){
					Ext.Msg.alert("消息","角色不能增加下级节点!");
					return;
				}
		    	tree.onCreate({name:'新目录','roleEnum':'roleCategory'});
		    },
		    iconCls: 'role-category-add'
	});
    tree.addAction(createModule,0);
    var createFun = new Ext.Action({
		text: '新增角色',
		handler: function(){
			var parent=tree.getSelectionModel( ).getLastSelected( )||tree.getRootNode( );    
			if(parent.get("funEnum")=="fun"){
				Ext.Msg.alert("消息","功能不能增加下级节点!");
				return;
			}
		    tree.onCreate({name:'新角色','roleEnum':'role'});
		},
		iconCls: 'role-add-iconCls'
	});
    tree.addAction(createFun,1);
	
	var tabPanel=Ext.create('Ext.tab.Panel', {
		region:'center',
		split:true,
	    activeTab: 0,
	    items: [
	     	{
	            title: '权限',
	            html : '功能树，如果是从角色上继承过来的，就灰色显示不能再进行修改了，否则就可以修改，并且添加一个不显示角色权限的按钮，只要不勾，哪就只显示直接授权在用户上的功能'
	        },
	        {
	            title: '继承角色',
	            html : '注意有公有，私有权限'
	        },
	         {
	            title: '互斥角色',
	            html : '功能树，如果是从角色上继承过来的，就灰色显示不能再进行修改了，否则就可以修改，并且添加一个不显示角色权限的按钮，只要不勾，哪就只显示直接授权在用户上的功能'
	        }
	       
	    ]
	});

	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'border',
		items:[tree,tabPanel]
	});
	

	
});