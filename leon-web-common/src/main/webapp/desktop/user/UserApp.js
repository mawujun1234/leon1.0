Ext.require('Leon.desktop.user.User');
Ext.require('Leon.common.ux.BaseGrid');
Ext.require('Leon.desktop.user.UserForm');
Ext.onReady(function(){
	var grid=Ext.create('Leon.common.ux.BaseGrid',{
		model:'Leon.desktop.user.User',
		region:'west',
		split:true,
		autoSync:false,
		//autoLoad:true,
		//autoInitAction:false,
		flex: 1,
		//title:'用户管理',
		columns:[{dataIndex:'loginName',text:'登陆名'},
			{dataIndex:'password',text:'密码'},
	         {dataIndex:'name',text:'姓名'},
	         {dataIndex:'deleted',text:'是否删除'},
	         {dataIndex:'deletedDate',text:'删除日期'},
	         {dataIndex:'enable',text:'是否可用'},
	         {dataIndex:'locked',text:'是否锁定'},
	         {dataIndex:'createDate',text:'创建日期',xtype: 'datecolumn',   format:'Y-m-d'},
	         {dataIndex:'expireDate',text:'过期日期'},
	         {dataIndex:'lastLoginDate',text:'最后登陆时间'}
	    ]
		
	});
	var form=Ext.create('Leon.desktop.user.UserForm',{});
	var win=Ext.create('Ext.Window',{
			layout:'fit',
			modal:true,
			items:[form],
			listeners:{
				close:function(panel){
					grid.getStore().reload();
				}
			}
	});
	
	//重载新增的功能，不从表格里面新增
	grid.onCreate=function(){
		//alert('建立一个form进行新增。');
		var modal=Ext.createModel('Leon.desktop.user.User',{enable:true});
		
		form.getForm().loadRecord(modal);
		
		win.show();
	}
	grid.onUpdate=function(){
		var modal=grid.getLastSelected();
		form.getForm().loadRecord(modal);
		win.show();
	}
	
	var selectedRoleTree=Ext.create('Leon.common.ux.BaseTree',{
		url:'/role/query',
		fields:['id','name'],
		rootVisible: false,
		flex:1,
		displayField:'name'
		,dockedItems: [{
	        xtype: 'toolbar',
	        dock: 'right',
	        items: [{xtype:'tbspacer',flex:1},{
	            text: '添加'
	        },{xtype:'tbspacer',flex:1}]
	    }]
	});
	var roleTree=Ext.create('Leon.common.ux.BaseTree',{
		url:'/role/query',
		fields:['id','name'],
		rootVisible: false,
		flex:1,
		displayField:'name'
	});
	
	//var form=Ext.create('Leon.desktop.user.UserForm',{title:'用户表单'});
	var tabPanel=Ext.create('Ext.tab.Panel', {
		region:'center',
		split:true,
	    activeTab: 0,
	    items: [
	        {
	           title: '选择角色',
	           layout:{type:'hbox',align: 'stretch'},
	           items:[selectedRoleTree,roleTree]
	        },
	        {
	            title: '权限',
	            html : '功能树，如果是从角色上继承过来的，就灰色显示不能再进行修改了，否则就可以修改，并且添加一个不显示角色权限的按钮，只要不勾，哪就只显示直接授权在用户上的功能'
	        }
	    ]
	});
	
	

	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'border',
		items:[grid,tabPanel]
	});
	

	
});