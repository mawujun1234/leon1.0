Ext.require('Leon.desktop.user.User');
Ext.require('Leon.common.ux.BaseGrid');
Ext.require('Leon.desktop.user.UserForm');
Ext.require('Leon.desktop.role.RoleSelectPanel');
Ext.require('Leon.desktop.parameter.ParameterUtils');
Ext.require('Leon.desktop.user.SwitchUserGrid')
Ext.onReady(function(){
	var grid=Ext.create('Leon.common.ux.BaseGrid',{
		model:'Leon.desktop.user.User',
		region:'west',
		split:true,
		editable:true,
		autoSync:true,
		//autoLoad:true,
		//autoInitAction:false,
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
	
	var physicsDel = new Ext.Action({
		    text: '物理删除',
		    iconCls: 'icons_list-remove',
		    handler: function(){
		    	Ext.Msg.confirm("删除",'确定要删除吗?物理删除后，将不可恢复!', function(btn, text){
					if (btn == 'yes'){
						var user=grid.getLastSelected( );//.getLastSelected( );
						user.destroy({
							params:{physicsDel:true},
							success:function(){
								grid.getStore().reload();
							}
						});
					}
				});
		    }
	});
	grid.addAction(3,physicsDel);
	
	var recover = new Ext.Action({
		    text: '恢复',
		    iconCls: 'icons_arrow-step-over',
		    handler: function(){
		    	Ext.Msg.confirm("删除",'恢复被逻辑删除的用户，确定要恢复吗?', function(btn, text){
					if (btn == 'yes'){
						var user=grid.getLastSelected( );//.getLastSelected( );
						Ext.Ajax.request({
							url:'/user/recover',
							params:{id:user.getId()},
							success:function(){
								grid.getStore().reload();
							}
						});
						
					}
				});
		    }
	});
	grid.addAction(3,recover);
	
	
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
		
		switchUserGrid.setMasterId(record.getId());
		switchUserGrid.reload(record.getId());
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
	
	
	var switchUserGrid=Ext.create('Leon.desktop.user.SwitchUserGrid',{
		title:'切换用户管理'
	});
	
	
	//var form=Ext.create('Leon.desktop.user.UserForm',{title:'用户表单'});
	var tabPanel=Ext.create('Ext.tab.Panel', {
		region:'center',
		split:true,
		mask:true,
	    activeTab: 0,
	    items: [
	    	roleSelectedTree,
	        funTree,
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