Ext.require('Leon.desktop.user.User');
Ext.require('Leon.common.ux.BaseGrid');
Ext.require('Leon.desktop.user.UserForm');
Ext.require('Leon.desktop.role.RoleSelectPanel');
Ext.require('Leon.desktop.parameter.ParameterUtils');
Ext.require('Leon.desktop.user.SwitchUserGrid');
Ext.require('Leon.desktop.user.UserFunGrid');

Ext.onReady(function(){
	window.queryUserByLoginName=function(params){
		nameField.setValue(params.loginName);
		grid.getStore().load({params:{userName:params.loginName}});
	};
	var grid=Ext.create('Leon.common.ux.BaseGrid',{
		model:'Leon.desktop.user.User',
		region:'west',
		split:true,
		editable:false,
		autoSync:false,
		//autoLoad:true,
		autoInitSimpleAction:false,
		flex: 1,
		//title:'用户管理',
		columns:[
			 {dataIndex:'loginName',text:'登陆名'},
			 {dataIndex:'password',text:'密码'},
	         {dataIndex:'name',text:'姓名'},
	         {dataIndex:'deleted',text:'是否已删除'},
	         {dataIndex:'deletedDate',text:'删除日期',xtype: 'datecolumn',   format:'Y-m-d'},
	         {dataIndex:'enable',text:'是否可用',xtype:'checkcolumn', editor: {
                xtype: 'checkbox',
                allowBlank: false
             }},
	         {dataIndex:'locked',text:'是否锁定',xtype:'checkcolumn', editor: {
                xtype: 'checkbox',
                allowBlank: false
             }},
	         {dataIndex:'accountExpired',text:'是否过期',renderer: function(value,metaData ,record ){
	         	var expireDate=record.get("expireDate");
	         	var now=new Date();
		        if (expireDate&&expireDate<now) {
		            return true;
		        }
		        return false;
		    }},
	         {dataIndex:'expireDate',text:'过期日期',xtype: 'datecolumn',   format:'Y-m-d', editor: {
                xtype: 'datefield',
                format:'Y-m-d',
                allowBlank: false
             }},
	         {dataIndex:'lastLoginDate',text:'最后登陆时间',xtype: 'datecolumn',   format:'Y-m-d'},
	         {dataIndex:'createDate',text:'创建日期',xtype: 'datecolumn',   format:'Y-m-d'}
	    ]
		
	});

	
	var nameField=Ext.create('Ext.form.field.Text',{
       	 name:'userName'
       });
    var tbar=Ext.create('Ext.toolbar.Toolbar', {
       		items:[nameField,{
       			text:'查询',
       			iconCls:'icons_search ',
       			handler:function(){
					 grid.getStore().getProxy( ).extraParams={userName:nameField.getValue()};
					 grid.getStore().reload();
       			}	
       		}]
    });
    grid.addTopBar(tbar);
	

    var selectedUserId="";
	grid.on("itemclick",function(grid, record, item, index, e, eOpts ){
		tabPanel.getEl().unmask();
		userForm.loadRecord(record);
		selectedUserId=record.getId();
		//selectedRoleTree.getStore().load({params:{userId:record.getId()}});
		roleSelectedTree.reloadSelected({userId:selectedUserId});
		
		//获取该用户的参数
		utils.setSubjectId(selectedUserId);
		
		switchUserGrid.setMasterId(selectedUserId);
		switchUserGrid.reload(selectedUserId);
	});
	var roleSelectedTree=Ext.create('Leon.desktop.role.RoleSelectPanel',{
    	url:Ext.ContextPath+'/user/queryRole',
    	tbar:[{ xtype: 'button', text: '查看用户可用功能',iconCls:'form-search-button',handler:function(btn){
    		var userFunGrid=Ext.create('Leon.desktop.user.UserFunGrid',{
				
			});
			userFunGrid.getStore().load({params:{userId:selectedUserId}});
			var win=Ext.create('Ext.Window',{
				layout:'fit',
				title:'用户可用功能',
				modal:true,
				height: 500,
    			width: 400,
				items:[userFunGrid]
			});
			win.show();
    	}}],
    	listeners:{
    		addRole:function(selectedRoleTree,selectRoleNode){
    			var user=grid.getLastSelected();
		        var params={
		            userId:user.getId(),
		            roleId:selectRoleNode.getId()
		        };
		        Ext.Ajax.request({
		            url:Ext.ContextPath+'/user/addRole',
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
		            url:Ext.ContextPath+'/user/removeRole',
		            method:'POST',
		            params:params,
		            success:function(){
		            	selectRoleNode.remove(true);
		            }
		        });
    		}
    	}
    });

//	var userFunGrid=Ext.create('Leon.common.ux.BaseTree',{
//		url:Ext.ContextPath+'/user/queryFun',
//		fields:['id','text',"roleNames"],
//		defaultRootName:'children',
//		rootVisible: false,
//		//flex:1,
//		title:'拥有的权限',
//		displayField:'text'
//		,columns:[{
//			xtype:'treecolumn',dataIndex:'text',text:'名称',flex:8
//			
//		},{
//			dataIndex:'roleNames',text:'权限来源',flex:2
//		}]
//	});
	
	
	
	var switchUserGrid=Ext.create('Leon.desktop.user.SwitchUserGrid',{
		title:'切换用户管理'
	});
	
	var userForm=Ext.create('Leon.desktop.user.UserForm',{
		title:'用户信息',
		grid:grid
	});
	//var form=Ext.create('Leon.desktop.user.UserForm',{title:'用户表单'});
	var tabPanel=Ext.create('Ext.tab.Panel', {
		region:'center',
		split:true,
		mask:true,
	    activeTab: 0,
	    items: [
	    	userForm,
	    	roleSelectedTree,
	        //userFunGrid,
	        switchUserGrid
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