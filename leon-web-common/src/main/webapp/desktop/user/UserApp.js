Ext.require('Leon.desktop.menu.MenuItemTree');
Ext.require('Leon.desktop.menu.MenuItemForm');
Ext.require('Leon.desktop.menu.MenuGrid');
Ext.onReady(function(){
	var grid=Ext.create('Leon.common.ux.BaseGrid',{
		model:'Leon.desktop.user.User',
		region:'west',
		autoSync:false,
		autoLoad:false,
		//autoInitAction:false,
		flex: 1,
		title:'用户管理',
		columns:[{dataIndex:'loginName',text:'登陆名称'},
			{dataIndex:'password',text:'密码',flex:1},
	         {dataIndex:'name',text:'姓名',flex:1},
	         {dataIndex:'isDeleted',text:'是否删除',flex:1},
	         {dataIndex:'deletedDate',text:'删除日期',flex:1},
	         {dataIndex:'isEnable',text:'是否可用',flex:1},
	         {dataIndex:'isEnable',text:'是否锁定',flex:1},
	         {dataIndex:'createDate',text:'创建日期',flex:1},
	         {dataIndex:'expireDate',text:'过期日期',flex:1},
	         {dataIndex:'lastLoginDate',text:'最后登陆时间',flex:1}
	    ]
		
	});
	//重载新增的功能，不从表格里面新增
	grid.onCreate()=function(){
		alert('建立一个form进行新增。');
	}
	
	
	var tabPanel=Ext.create('Ext.tab.Panel', {
		region:'center',
	    activeTab: 0,
	    items: [
	        {
	            title: '基本信息',
	            bodyPadding: 10,
	            html : 'A simple tab'
	        },
	        {
	            title: '角色',
	            html : '分两块，左边是未选择的角色，右边是选择了的角色'
	        },
	        {
	            title: '权限',
	            html : '功能树，如果是从角色上继承过来的，就灰色显示不能再进行修改了，否则就可以修改，并且添加一个不显示角色权限的按钮，只要不勾，哪就只显示直接授权在用户上的功能'
	        }
	    ],
	    renderTo : Ext.getBody()
	});

	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'border',
		items:[grid,tabPanel]
	});
	

	
});