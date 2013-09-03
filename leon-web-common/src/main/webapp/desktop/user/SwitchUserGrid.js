/**
 * 进行切换用户的管理的，进行增，删，改查的
 */
Ext.define('Leon.desktop.user.SwitchUserGrid',{
	extend:'Ext.grid.Panel',
	requires: [
	     'Leon.desktop.user.User'
	],
	columnLines :true,
	stripeRows:true,
	viewConfig:{
		stripeRows:true,
		listeners:{
			refresh:function(){
				this.select(0);
			}
		}
	},
	config:{
		masterId:null
	},
	
	initComponent: function () {
       var me = this;
//       function formatQtip(data,metadata){   
//		    metadata.tdAttr = "data-qtip='" + data + "'";
//		    return data;    
//	  }  
      me.columns=[
	        {dataIndex:'loginName',text:'登陆名'},
			{dataIndex:'password',text:'密码'},
	        {dataIndex:'name',text:'姓名'},
	        {dataIndex:'deleted',text:'是否删除'},
	        {dataIndex:'deletedDate',text:'删除日期',xtype: 'datecolumn',   format:'Y-m-d'},
	        {dataIndex:'enable',text:'是否可用'},
	        {dataIndex:'locked',text:'是否锁定'},
	        {dataIndex:'createDate',text:'创建日期',xtype: 'datecolumn',   format:'Y-m-d'},
	        {dataIndex:'expireDate',text:'过期日期',xtype: 'datecolumn',   format:'Y-m-d'},
	        {dataIndex:'lastLoginDate',text:'最后登陆时间',xtype: 'datecolumn',   format:'Y-m-d'}
       ];
        me.store=e=Ext.create('Ext.data.Store',{
       		autoSync:false,
       		pageSize:50,
       		//fields:['userId','userName'],
       		model: 'Leon.desktop.user.User',
       		autoLoad:false,
       		proxy:{
		    	type: 'ajax',
        		url : '/app/switchUser/query',
        		headers:{ 'Accept':'application/json;'},
        		actionMethods: { read: 'POST' },
        		extraParams:{limit:50},
        		reader:{
					type:'json',
					root:'root',
					successProperty:'success',
					totalProperty:'total'		
				}
		    }
       });

       var nameField=Ext.create('Ext.form.field.Text',{
       	 name:'userName'
       });
       var tbar=Ext.create('Ext.toolbar.Toolbar', {
       		items:[{
       			text:'添加',
       			iconCls: 'form-add-button',
       			handler:function(){
       				var userGrid=Ext.create('Leon.desktop.user.UserQueryGrid',{
						listeners:{
							itemdblclick:function(grid,record){
								Ext.Ajax.request({
									url:'/app/switchUser/create',
									method:'POST',
									params:{masterId:me.getMasterId(),switchUserId:record.get("id")},
									success:function(){
										//me.getStore().reload();
									}
								});
							}
						}
					});
       				var win=Ext.create('Ext.window.Window',{
       					layout:'fit',
       					height:400,
       					modal:true,
       					width:300,
       					items:[userGrid],
       					listeners:{
       						close:function(){
       							me.getStore().reload();
       						}
       					}
       				});
       				win.show();
       			}
       		},{
       			text:'删除',
       			iconCls: 'form-delete-button',
       			handler:function(btn){
			    	Ext.Msg.confirm("删除",'确定要删除吗?', function(btn, text){
						if (btn == 'yes'){
							var record=me.getSelectionModel( ).getLastSelected( );//.getLastSelected( );
							//
							Ext.Ajax.request({
								url:'/app/switchUser/destroy',
								method:'POST',
								params:{masterId:me.getMasterId(),switchUserId:record.get("id")},
								success:function(){
									me.getStore().remove( record );
								}
							});
						}
					});	
       			}
       		},nameField,{
       			text:'查询',
       			iconCls:'icons_search ',
       			handler:function(){
					 me.getStore().getProxy( ).extraParams={userName:nameField.getValue(),masterId:me.getMasterId()};
					 me.getStore().reload();
       			}	
       		}]
       });
       me.tbar=tbar;
       
       me.dockedItems= [{
	        xtype: 'pagingtoolbar',
	        store: me.store,  
	        dock: 'bottom',
	        displayInfo: true
	   }];
       
       me.callParent();
	},
	reload:function(masterId){
		var me=this;
		 me.getStore().getProxy( ).extraParams={masterId:me.getMasterId()};
		 me.getStore().reload();
	}
});