Ext.require('Leon.desktop.menu.MenuItemTree');
Ext.require('Leon.desktop.menu.MenuItemForm');
Ext.require('Leon.desktop.menu.MenuGrid');
Ext.onReady(function(){
	var grid=Ext.create('Leon.common.ux.BaseGrid',{
		model:'Leon.desktop.user.User',
		autoSync:false,
		autoLoad:false,
		//autoInitAction:false,
		flex: 1,
		title:'用户管理',
		columns:[{dataIndex:'loginName',text:'登陆名称'},
			{dataIndex:'password',text:'密码',flex:1,editor: {
	                xtype: 'textfield',
	                allowBlank: true,
	                selectOnFocus:true
	         }},
	         {dataIndex:'name',text:'姓名',flex:1,editor: {
	                xtype: 'textfield',
	                allowBlank: true,
	                selectOnFocus:true
	         }},
	         {dataIndex:'isDeleted',text:'是否删除',flex:1,editor: {
	                xtype: 'textfield',
	                allowBlank: true,
	                selectOnFocus:true
	         }},
	         {dataIndex:'deletedDate',text:'删除日期',flex:1,editor: {
	                xtype: 'textfield',
	                allowBlank: true,
	                selectOnFocus:true
	         }},
	         {dataIndex:'isEnable',text:'是否可用',flex:1,editor: {
	                xtype: 'textfield',
	                allowBlank: true,
	                selectOnFocus:true
	         }},
	         {dataIndex:'isEnable',text:'是否锁定',flex:1,editor: {
	                xtype: 'textfield',
	                allowBlank: true,
	                selectOnFocus:true
	         }},
	         {dataIndex:'createDate',text:'创建日期',flex:1,editor: {
	                xtype: 'textfield',
	                allowBlank: true,
	                selectOnFocus:true
	         }},
	         {dataIndex:'expireDate',text:'过期日期',flex:1,editor: {
	                xtype: 'textfield',
	                allowBlank: true,
	                selectOnFocus:true
	         }},
	         {dataIndex:'lastLoginDate',text:'最后登陆时间',flex:1,editor: {
	                xtype: 'textfield',
	                allowBlank: true,
	                selectOnFocus:true
	         }}
	    ]
		
	});
	//重载新增的功能，不从表格里面新增
	grid.onCreate()=function(){
		alert('建立一个form进行新增。');
	}

	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'fit',
		items:[grid]
	});
	

	
});