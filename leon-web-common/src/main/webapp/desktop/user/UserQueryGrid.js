Ext.define('Leon.desktop.user.UserQueryGrid',{
	extend:'Ext.grid.Panel',
	requires: [
	     'Leon.desktop.user.User'
	],
	columnLines :true,
	url:'/user/query',
	//stripeRows:true,
	viewConfig:{
		stripeRows:true,
		listeners:{
			refresh:function(){
				this.select(0);
			}
		}
	},
	config:{
		groupId:null
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
        me.store=Ext.create('Ext.data.Store',{
       		autoSync:false,
       		pageSize:50,
       		//fields:['userId','userName'],
       		model: 'Leon.desktop.user.User',
       		autoLoad:false,
       		proxy:{
		    	type: 'ajax',
        		url : me.url,
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
       		items:[nameField,{
       			text:'查询',
       			
       			iconCls:'icons_search ',
       			handler:function(){
					 me.getStore().getProxy( ).extraParams={userName:nameField.getValue(),groupId:me.getGroupId()};
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
	}
});