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
	grid.on("itemclick",function(grid, record, item, index, e, eOpts ){
		tabPanel.getEl().unmask();
		selectedRoleTree.getStore().load({params:{userId:record.getId()}});
		funTree.getStore().load({params:{userId:record.getId()}});
		
	});
	
	var selectedRoleTree=Ext.create('Ext.tree.Panel',{		
		fields:['id','name'],
		rootVisible: false,
		flex:1,
		store:{
        		root: {
			        expanded: true,
			        name:'根节点' 
			    },
			    nodeParam :'id',
        		autoLoad:false,
				proxy:{
					type:'ajax',
					url:'/user/queryRole'
					,reader:{//因为树会自己生成model，这个时候就有这个问题，不加就解析不了，可以通过   动态生成 模型，而不是通过树默认实现，哪应该就没有问题
							type:'json',
							root:'children',
							successProperty:'success',
							totalProperty:'total'	
					}
					,writer:{
						type:'json'
					}
				}
				
		},
		displayField:'name'
		,dockedItems: [{
	        xtype: 'toolbar',
	        dock: 'right',
	        items: [{xtype:'tbspacer',flex:1},{//选择角色
	        	icon:'/icons/arrow_180.png',
	            text: '',
	            handler:function(){
	            	var selectRoleNode=roleTree.getLastSelected();
	            	if(selectRoleNode && selectRoleNode.isLeaf()){
	            		var user=grid.getLastSelected();
	            		var params={
	            			userId:user.getId(),
	            			roleId:selectRoleNode.getId()
	            		};
	            		Ext.Ajax.request({
	            			url:'/user/addRole',
	            			method:'POST',
	            			params:params,
	            			success:function(){
	            				selectedRoleTree.getStore().load({params:{userId:user.getId()}});
	            			}
	            		});
	            	} else {
	            		Ext.Msg.alert('消息',"目录不能移动，请选择角色!");
	            	}
	            }
	        },{
	        	icon:'/icons/arrow.png',
	            text: '',
	            handler:function(){//去掉角色
	            	var selectRoleNode=selectedRoleTree.getSelectionModel( ).getLastSelected();
	            	if(selectRoleNode && selectRoleNode.isLeaf()){
	            		var user=grid.getLastSelected();
	            		var params={
	            			userId:user.getId(),
	            			roleId:selectRoleNode.getId()
	            		};
	            		Ext.Ajax.request({
	            			url:'/user/removeRole',
	            			method:'POST',
	            			params:params,
	            			success:function(){
	            				selectRoleNode.remove(true);
	            			}
	            		});
	            	} else {
	            		Ext.Msg.alert('消息',"目录不能移动，请选择角色!");
	            	}
	            }
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
	var funTree=Ext.create('Leon.common.ux.BaseTree',{
		url:'/user/queryFun',
		fields:['id','text'],
		defaultRootName:'children',
		rootVisible: false,
		//flex:1,
		title:'拥有的权限',
		displayField:'text'
	});
	
	//var form=Ext.create('Leon.desktop.user.UserForm',{title:'用户表单'});
	var tabPanel=Ext.create('Ext.tab.Panel', {
		region:'center',
		split:true,
		mask:true,
	    activeTab: 0,
	    items: [
	        {
	           title: '选择角色',
	           layout:{type:'hbox',align: 'stretch'},
	           items:[selectedRoleTree,roleTree]
	        },
	        funTree
	    ],
	    listeners:{
	    	render:function(tabPanel){
	    		tabPanel.getEl().mask();
	    	}
	    }
	});
	
	

	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'border',
		items:[grid,tabPanel]
	});
	

	
});