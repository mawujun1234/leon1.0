Ext.define('Ems.check.CheckGrid',{
	extend:'Ext.grid.Panel',
	requires: [
	     'Ems.check.Check'
	],
	columnLines :true,
	stripeRows:true,
	viewConfig:{
		enableTextSelection:true
	},
	initComponent: function () {
      var me = this;
      me.columns=[
      	{xtype: 'rownumberer'},
		{dataIndex:'id',header:'编号'
        },
        {dataIndex:'task_id',header:'任务编号'
        },
		{dataIndex:'status_name',header:'状态'
        },
//        {dataIndex:'creater',header:'创建人'
//        },
		{dataIndex:'createDate',header:'创建时间'
		},
//		{dataIndex:'completer',header:'完成人'
//        },
		{dataIndex:'completeDate',header:'完成时间'
		}
		
      ];
      

	  me.store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			autoLoad:true,
			model: 'Ems.check.Check',
			proxy:{
				type: 'ajax',
			    url : Ext.ContextPath+'/check/queryPager.do',
			    headers:{ 'Accept':'application/json;'},
			    actionMethods: { read: 'POST' },
			    extraParams:{limit:50},
			    reader:{
					type:'json',//如果没有分页，那么可以把后面三行去掉，而且后台只需要返回一个数组就行了
					rootProperty:'root',
					successProperty:'success',
					totalProperty:'total'		
				}
			}
	  });

	  me.dockedItems=[];
      me.dockedItems.push({
	        xtype: 'pagingtoolbar',
	        store: me.store,  
	        dock: 'bottom',
	        displayInfo: true
	  });
	  
	  me.dockedItems.push({
	  		xtype: 'toolbar',
	  		dock:'top',
		  	items:[{
		  		itemId:'status',
				xtype:'combobox',
				labelWidth:65,
				width:100,
				displayField:'value',
				valueField:'key',
				store:{
					fields:['key','value'],
					data:[{key:'handling',value:'处理中'},{key:'complete',value:'完成'}]
				},
				listeners:{
					select:function( combo, record, eOpts ) {

					}
				}
			},{
				text: '查询',
				itemId:'reload',
				disabled:me.disabledAction,
				handler: function(btn){
					var grid=btn.up("grid");
    				grid.getStore().getProxy().extraParams=grid.getParams();
					grid.getStore().reload();
				},
				iconCls: 'icon-refresh'
			}]
		});

       
      me.callParent();
	},
	getParams:function(){
		var me=this;
		var toolbars=me.getDockedItems('toolbar[dock="top"]');
		var params={
			'status':toolbars[0].down("#status").getValue()
		};
		return params;

	},
	onCreate:function(){
    	var me=this;
		var child=Ext.create('Ems.check.Check',{

		});
		child.set("id",null);
		
		var formpanel=Ext.create('Ems.check.CheckForm',{});
		formpanel.loadRecord(child);
		
    	var win=Ext.create('Ext.window.Window',{
    		layout:'fit',
    		title:'新增',
    		modal:true,
    		width:400,
    		height:300,
    		closeAction:'hide',
    		items:[formpanel],
    		listeners:{
    			close:function(){
    				me.getStore().reload();
    			}
    		}
    	});
    	win.show();
    },
    
     onUpdate:function(){
    	var me=this;

    	var node=me.getSelectionModel( ).getLastSelected();
    	if(node==null){
    		Ext.Msg.alert("提醒","请选择一行数据!");
    		return;
    	}

		var formpanel=Ext.create('Ems.check.CheckForm',{});
		formpanel.loadRecord(node);
		
    	var win=Ext.create('Ext.window.Window',{
    		layout:'fit',
    		title:'更新',
    		modal:true,
    		width:400,
    		height:300,
    		closeAction:'hide',
    		items:[formpanel]
    	});
    	win.show();
    },
    
    onDelete:function(){
    	var me=this;
    	var node=me.getSelectionModel( ).getLastSelected( );

		if(!node){
		    Ext.Msg.alert("消息","请先选择一行数据");	
			return;
		}
		var parent=node.parentNode;
		Ext.Msg.confirm("删除",'确定要删除吗?', function(btn, text){
				if (btn == 'yes'){
					node.erase({
					    failure: function(record, operation) {
			            	me.getStore().reload();
					    },
					    success:function(){
					    	me.getStore().reload();
					    }
				});
			}
		});
    }
});
