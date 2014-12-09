Ext.define('Ems.baseinfo.AreaPoleGrid',{
	extend:'Ext.grid.Panel',
	requires: [
	     'Ems.baseinfo.Pole',
	     'Ems.baseinfo.AreaSelPoleWindow'
	],
	columnLines :true,
	stripeRows:true,
	viewConfig:{
		stripeRows:true,
		listeners:{
			refresh:function(){
				//this.select(0);
			}
		}
	},
	selModel:Ext.create('Ext.selection.CheckboxModel',{
		checkOnly:false
	}),
	initComponent: function () {
      var me = this;
      me.columns=[
		//{dataIndex:'id',text:'id'},
      	
      	
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
      	{dataIndex:'province',text:'地址',flex:1,renderer:function(value,metaData ,record){
      		return value+record.get("city")+record.get("area")+record.get("address")
      	}}
		//{dataIndex:'latitude',text:'经度'},
		//{dataIndex:'longitude',text:'纬度'}
		//{dataIndex:'status',text:'status'}
      ];
      
	  me.store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			model: 'Ems.baseinfo.Pole',
			proxy: {
			        type: 'ajax',
			        url: Ext.ContextPath+'/area/queryPoles.do',
			        reader: {
			        	type:'json',
			            root: 'root'
			        }
			},
			autoLoad:false
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
		    text: '添加',
		    itemId:'create',
		    disabled:me.disabledAction,
		    handler: function(b){
		    	me.onCreate(null,b);
		    },
		    iconCls: 'form-add-button'
		});
		//me.addAction(create);
		actions.push(create);
//		var update = new Ext.Action({
//		    text: '更新',
//		    itemId:'update',
//		    disabled:me.disabledAction,
//		    handler: function(b){
//		    	me.onUpdate(null,b);
//		    },
//		    iconCls: 'form-update-button'
//		});
//		//me.addAction(create);
//		actions.push(update);
		
		var destroy = new Ext.Action({
		    text: '删除',
		    itemId:'destroy',
		    disabled:me.disabledAction,
		    handler: function(){
		    	me.onDelete();    
		    },
		    iconCls: 'form-delete-button'
		});
		//me.addAction(destroy);
		actions.push(destroy)
		
		var reload = new Ext.Action({
		    text: '刷新',
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
    	if(!me.area_id){
    		Ext.Msg.alert("消息","请先选择一个片区!");
    		return;
    	}
		var win=Ext.create('Ems.baseinfo.AreaSelPoleWindow',{
			width:800,
			title:'添加点位(已经被分配的将不会显示)',
			height:500,
			buttons:[{
				text:'确认',
				handler:function(){
					var records=win.pole_grid.getSelectionModel().getSelection();
					//console.log(records);
					if(!records || records.length==0){
						//Ext.MessageBoxEx('提示','请');
						return;
					}
					win.getEl().mask("正在执行,请稍候....");
					var pole_ids=[];
					for(var i=0;i<records.length;i++){
						pole_ids.push(records[i].get("id"));
					}
					 Ext.Ajax.request({
					        url : Ext.ContextPath+'/area/savePoles.do',
					        method : 'post',
					        params:{pole_ids:pole_ids,area_id:me.area_id},
					        success : function(response, options) {
					        	//Ext.MessageBoxEx('提示','删除成功');
					        	win.getEl().unmask();
					        	me.getStore().reload();
					        	alert("添加成功!");
					        },
					        failure : function() {
					        	win.getEl().unmask();
					        	 Ext.Msg.alert('提示','数据加载失败');
					        }
				  	 });
				}
			}]
		});
		win.show();

    },
//    onUpdate:function(){
//
//		    	
//		
//    },
    onDelete:function(){
    	var me=this;
    	var record=me.getSelectionModel( ).getLastSelected( );

		if(!record){
		    Ext.Msg.alert("消息","请先选择类型");	
			return;
		}

		Ext.Msg.confirm("删除",'确定要删除吗?', function(btn, text){
				if (btn == 'yes'){
					var records=me.getSelectionModel().getSelection(); 
					if(!records || records.length==0){
						//Ext.MessageBoxEx('提示','请');
						return;
					}
					Ext.getBody().mask("正在执行,请稍候....");
					var pole_ids=[];
					for(var i=0;i<records.length;i++){
						pole_ids.push(records[i].get("id"));
					}
					Ext.Ajax.request({
						url:Ext.ContextPath+'/area/deletePoles.do',
						params:{
							pole_ids:pole_ids
						},
						method:'POST',
						success:function(){
							Ext.getBody().unmask();
					        me.getStore().reload();
							//me.getStore().remove(record);
						}
					});
			}
		});
    },
    onReload:function(){
    	var me=this;
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
//			width:600,
//			height:400,
//			modal:true
//		});
//		//form.win=win
//		win.show();	
//    },
    onExportPoles:function(){
    	var me=this;
    	var params={
    		area_id:me.area_id
    	}
		var pp=Ext.Object.toQueryString(params);
		window.open(Ext.ContextPath+"/area/exportPoles.do?"+pp, "_blank");
    }
});
