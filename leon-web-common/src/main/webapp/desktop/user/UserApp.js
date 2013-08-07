Ext.require('Leon.desktop.user.User');
Ext.require('Leon.common.ux.BaseGrid');
Ext.require('Leon.desktop.user.UserForm');
Ext.require('Leon.desktop.parameter.ParameterUtils');
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
		columns:[
			 {dataIndex:'loginName',text:'登陆名'},
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
		//selectedRoleTree.getStore().load({params:{userId:record.getId()}});
		roleSelectedTree.reloadSelected({userId:record.getId()});
		funTree.getStore().load({params:{userId:record.getId()}});
		//获取该用户的参数
		utils.setSubjectId(record.getId());
	});
	var roleSelectedTree=Ext.create('Leon.desktop.role.RoleSelectPanel',{
    	url:'/user/queryRole',
    	listeners:{
    		addRole:function(selectedRoleTree,selectRoleNode){
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
    		},
    		removeRole:function(selectedRoleTree,selectRoleNode){
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
    		}
    	}
    });

	var funTree=Ext.create('Leon.common.ux.BaseTree',{
		url:'/user/queryFun',
		fields:['id','text',"roleNames"],
		defaultRootName:'children',
		rootVisible: false,
		//flex:1,
		title:'拥有的权限',
		displayField:'text'
		,columns:[{
			xtype:'treecolumn',dataIndex:'text',text:'名称',flex:8
			
		},{
			dataIndex:'roleNames',text:'权限来源',flex:2
		}]
	});
	
	
	
	
	//var form=Ext.create('Leon.desktop.user.UserForm',{title:'用户表单'});
	var tabPanel=Ext.create('Ext.tab.Panel', {
		region:'center',
		split:true,
		mask:true,
	    activeTab: 0,
	    items: [
	    	roleSelectedTree,
	        funTree
	    ],
	    listeners:{
	    	render:function(tabPanel){
	    		tabPanel.getEl().mask();
	    	}
	    }
	});
	
	//参数设置
	var utils=new Leon.desktop.parameter.ParameterUtils();
	utils.getForm('USER',function(paramform){
		paramform.setTitle("参数设置");
		tabPanel.add(paramform);
	});
	

	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'border',
		items:[grid,tabPanel]
	});
	

	
});