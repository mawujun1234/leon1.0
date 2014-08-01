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
				this.select(0);
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
      	{dataIndex:'name',text:'杆位名称',width:160},
      	{dataIndex:'province',text:'地址',flex:1,renderer:function(value,metaData ,record){
      		return value+record.get("city")+record.get("area")+record.get("address")
      	}},
//      	{dataIndex:'city',text:'city'},
//      	{dataIndex:'area',text:'area'},
//		{dataIndex:'address',text:'address'},
		//{dataIndex:'customer_id',text:'customer_id'},
		{dataIndex:'latitude',text:'经度'},
		{dataIndex:'longitude',text:'纬度'}
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
			title:'添加杆位(已经被分配的将不会显示)',
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
    }
});