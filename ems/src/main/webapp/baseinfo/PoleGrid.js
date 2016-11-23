Ext.define('Ems.baseinfo.PoleGrid',{
	extend:'Ext.grid.Panel',
	requires: [
	     'Ems.baseinfo.Pole',
	     'Ems.baseinfo.PoleForm'
	     //'Ems.baseinfo.PoleEquipmentGrid'
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
	initComponent: function () {
      var me = this;
      me.columns=[
		//{dataIndex:'id',text:'id'},
        {xtype: 'rownumberer'},
       
        {dataIndex:'status',text:'状态',width:40,menuDisabled:true,renderer : function(value,metadata, record, rowIndex, columnIndex, store) {
      	   metadata.tdAttr = "data-qtip='" + record.get("status_name")+ "'";
		   if (value == 'uninstall') {
		    return "<img src='../icons/help_circle_blue.png' />";
		   } else if (value == 'installing'){
		    return "<img src='../icons/circle_blue.png' />";
		   }else if (value == 'using'){
		    return "<img src='../icons/circle_green.png' />";
		   }else if (value == 'hitch'){
		    return "<img src='../icons/circle_yellow.png' />";
		   }else if (value == 'cancel'){
		    return "<img src='../icons/circle_red.png' />";
		   }
		   return record.get("status_name");
		 }
		},
		{dataIndex:'code',text:'编号',width:60},
      	{dataIndex:'name',text:'点位名称',width:160},
      	{dataIndex:'poleType_name',text:'点位类型',width:160},
      	{dataIndex:'project_name',text:'所属项目',width:160},
      	{dataIndex:'province',text:'地址',flex:1,renderer:function(value,metadata ,record){
      		var aaa=value+record.get("city")+record.get("area")+record.get("address");
      		metadata.tdAttr = "data-qtip='" + aaa+ "'";
      		return aaa;
      	}},
   		
//      	{dataIndex:'city',text:'city'},
//      	{dataIndex:'area',text:'area'},
//		{dataIndex:'address',text:'address'},
		//{dataIndex:'customer_id',text:'customer_id'},
		{dataIndex:'latitude',text:'经度',hidden:true},
		{dataIndex:'longitude',text:'纬度',hidden:true}
		//
      ];
      
	  me.store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			model: 'Ems.baseinfo.Pole',
			autoLoad:false,
					proxy:{
				type: 'ajax',
			    url : Ext.ContextPath+'/pole/query.do',
			    headers:{ 'Accept':'application/json;'},
			    actionMethods: { read: 'POST' },
			    extraParams:{limit:50},
			    reader:{
					type:'json',//如果没有分页，那么可以把后面三行去掉，而且后台只需要返回一个数组就行了
					rootProperty:'root',
					//root:'root',
					successProperty:'success',
					totalProperty:'total'		
				}
			}
	  });
	  
      me.dockedItems= [{
	        xtype: 'pagingtoolbar',
	        store: me.store,  
	        dock: 'bottom',
	        displayInfo: true
	  }];

      me.initAction(); 
      me.callParent();
	},
	initAction:function(){
     	var me = this;
     	var actions=[];
     	
       var create = new Ext.Action({
		    text: '新建',
		    itemId:'create',
		    hidden:!Permision.canShow('customer_pole_create'),
		    disabled:me.disabledAction,
		    handler: function(b){
		    	me.onCreate(null,b);
		    },
		    iconCls: 'form-add-button'
		});
		//me.addAction(create);
		actions.push(create);
		var update = new Ext.Action({
		    text: '更新',
		    itemId:'update',
		    hidden:!Permision.canShow('customer_pole_update'),
		    disabled:me.disabledAction,
		    handler: function(b){
		    	me.onUpdate(null,b);
		    },
		    iconCls: 'form-update-button'
		});
		//me.addAction(create);
		actions.push(update);
		
		var destroy = new Ext.Action({
		    text: '删除',
		    itemId:'destroy',
		    hidden:!Permision.canShow('customer_pole_delete'),
		    disabled:me.disabledAction,
		    handler: function(){
		    	me.onDelete();    
		    },
		    iconCls: 'form-delete-button'
		});
		//me.addAction(destroy);
		actions.push(destroy)
		
		var nameORcode=Ext.create('Ext.form.field.Text',{
			emptyText:'点位名称或编号',
			itemId:'nameORcode',
			width:120
		});
		actions.push(nameORcode);
		var reload = new Ext.Action({
		    text: '查询',
		    itemId:'reload',
		    handler: function(){
		    	me.onReload();
		    },
		    iconCls: 'form-reload-button'
		});
		//me.addAction(reload);
		actions.push(reload);

//		var showEquipment = new Ext.Action({
//		    text: '拥有的设备',
//		    //itemId:'reload',
//		    handler: function(){
//		    	me.onShowEquipment();
//		    },
//		    icon: '../icons/1.png'
//		});
//		actions.push(showEquipment);
		
		var exportPoles = new Ext.Action({
		    text: '导出点位设备信息',
		    //itemId:'reload',
		    icon:'../icons/page_excel.png',
		    handler: function(){
		    	me.onExportPoles();
		    }
		});
		actions.push(exportPoles);
		
		me.tbar={
			itemId:'action_toolbar',
			layout: {
	               overflowHandler: 'Menu'
	        },
			items:actions
			//,autoScroll:true		
		};

    },
    onCreate:function(){
    	var me=this;
    	if(!me.customer_id){
    		Ext.Msg.alert("消息","请先选择一个客户!");
    		return;
    	}

    	

		var form=new Ems.baseinfo.PoleForm({
			listeners:{
				saved:function(){
					win.close();
					me.getStore().reload();
					//me.tree.getStore().reload({node:parent});
				}
			}
		});
		
		var data=form.getValues();
		data.customer_id=me.customer_id;
		data.status="uninstall";
		var record=new Ems.baseinfo.Pole(data);    
		form.getForm().loadRecord(record);
		
		
		
		var win=new Ext.window.Window({
			items:[form],
			layout:'fit',
			closeAction:'destroy',
			width:350,
			height:400,
			modal:true
		});
		//form.win=win
		win.show();

    },
    onUpdate:function(){
    	var me=this;
    	if(!me.customer_id){
    		Ext.Msg.alert("消息","请先选择一个客户!");
    		return;
    	}
    	
    	var record=me.getSelectionModel( ).getLastSelected( );
		if(!record){
			Ext.Msg.alert("消息","请先选择一条联系方式!");	
			return;
		}
		
		var form=new Ems.baseinfo.PoleForm({
			listeners:{
				saved:function(){
					//form.updateRecord();
					win.close();
					me.getStore().reload();

				}
			}
		});
		form.getForm().loadRecord(record);
		//var ids=record.get("id").split("_");
		//form.getForm().findField("id").setValue(ids[0]);
		form.getForm().findField("id").setReadOnly(true);
		
		var project_combox=form.getForm().findField("project_id");
		var project_model= project_combox.getStore().createModel({id:record.get("project_id"),name:record.get("project_name")});
		project_combox.setValue(project_model);
		
		var win=new Ext.window.Window({
			items:[form],
			layout:'fit',
			closeAction:'destroy',
			width:300,
			height:400,
			modal:true
		});
		//form.win=win
		win.show();	
		    	
		
    },
    onDelete:function(){
    	var me=this;
    	var record=me.getSelectionModel( ).getLastSelected( );

		if(!record){
		    Ext.Msg.alert("消息","请先选择类型");	
			return;
		}

		Ext.Msg.confirm("删除",'确定要删除吗?', function(btn, text){
				if (btn == 'yes'){
					Ext.Ajax.request({
						url:Ext.ContextPath+'/pole/destroy.do',
						params:{
							id:record.get("id")
						},
						method:'POST',
						success:function(){
							//var parent=me.tree.getSelectionModel( ).getLastSelected( )||me.tree.getRootNode( );  
							//me.tree.getStore().reload({node:parent});
							
							me.getStore().remove(record);
						}
					});
			}
		});
    },
    onReload:function(){
    	var me=this;
    	me.getStore().getProxy().extraParams={
    		nameORcode:me.down("#nameORcode").getValue(),
    		customer_id:me.customer_id
    	}
    	me.getStore().reload();	      
    },
//    onShowEquipment:function(){
//    	var me=this;
//    	var record=me.getSelectionModel( ).getLastSelected( );
//    	var grid=Ext.create('Ems.baseinfo.EquipmentGrid',{});
//    	grid.getStore().load({params:{id:record.get("id")}});
//    	var win=new Ext.window.Window({
//			items:[grid],
//			title:record.get("name")+'拥有的设备',
//			layout:'fit',
//			closeAction:'destroy',
//			width:680,
//			height:400,
//			modal:true
//		});
//		//form.win=win
//		win.show();	
//    }
    onExportPoles:function(){
    	var me=this;
    	var params={
    		customer_id:me.customer_id
    	}
		var pp=Ext.Object.toQueryString(params);
		window.open(Ext.ContextPath+"/pole/exportPoles.do?"+pp, "_blank");
    }
});
