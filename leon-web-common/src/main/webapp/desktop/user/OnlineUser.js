Ext.onReady(function(){
	var store=Ext.create('Ext.data.Store',{
       		autoSync:false,
       		//pageSize:50,
       		fields:[
       			{name: 'loginName', type: 'string'},
       			{name: 'name', type: 'string'},
       			{name: 'count', type: 'int'},
       			{name: 'lastRequest', type: 'string'},
       			{name: 'sessionId', type: 'string'},
       			{name: 'lastIp', type: 'string'},
       			{name: 'lastLoginDate', type: 'string'},
       			{name: 'onlineSeconds', type: 'int'}
       		],
       		autoLoad:true,
       		proxy:{
		    	type: 'ajax',
        		url : "/onlineUser/query",
        		headers:{ 'Accept':'application/json;'},
        		actionMethods: { read: 'POST' },
        		//extraParams:{limit:50},
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
       			text:'强制注销',
       			iconCls:'icons_101',
       			handler:function(){
       				var record=grid.getSelectionModel( ).getLastSelected( );
       				if(!record){
       					return;
       				}
       				Ext.Msg.confirm("删除",'确定要注销该用户吗?', function(btn, text){
						if (btn == 'yes'){
							//.getLastSelected( );
							//
							Ext.Ajax.request({
								url:'/onlineUser/forceExpired',
								method:'POST',
								params:{sessionId:record.get("sessionId")},
								success:function(){
									if(record.get("count")==1){
										grid.getStore().remove( record );
									} else{
										record.set("count",record.get("count")-1);
									}
									
								}
							});
						}
					});	
       			}
       		
       		},'-',nameField,{
       			text:'查询',
       			iconCls:'icons_search ',
       			handler:function(){
					 grid.getStore().getProxy( ).extraParams={loginName:nameField.getValue()};
					 grid.getStore().reload();
       			}	
       		}]
    });
	var grid=Ext.create('Ext.grid.Panel',{
		columnLines :true,
		//stripeRows:true,
		viewConfig:{
			stripeRows:true,
			listeners:{
				refresh:function(){
					this.select(0);
				}
			}
		},
		columns:[
	        {dataIndex:'loginName',text:'登陆名'},
	        {dataIndex:'name',text:'姓名'},
	        {dataIndex:'count',text:'在线个数'},
	        {dataIndex:'lastRequest',text:'最后请求时间',width:150},
	        {dataIndex:'lastIp',text:'ip',width:150},
	        {dataIndex:'lastLoginDate',text:'登录时间',width:150},
	        {dataIndex:'onlineSeconds',text:'在线时长'}
       ],
       store:store,
//       dockedItems: [{
//	        xtype: 'pagingtoolbar',
//	        store: store,  
//	        dock: 'bottom',
//	        displayInfo: true
//	   }],
	   tbar:tbar
	});
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'fit',
		items:[grid]
	});
});