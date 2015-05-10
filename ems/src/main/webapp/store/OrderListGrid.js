Ext.define('Ems.store.OrderListGrid',{
	extend:'Ext.grid.Panel',
	requires: [
	     'Ems.store.Order'
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

	initComponent: function () {
      var me = this;
      me.columns=[
		//{dataIndex:'ecode',text:'条码',width:150},
		{header: '设备类型', dataIndex: 'subtype_name',width:120},
    	{header: '品名', dataIndex: 'prod_name'},
    	{header: '品牌', dataIndex: 'brand_name',width:120},
    	{header: '供应商', dataIndex: 'supplier_name'},
    	{header: '设备型号', dataIndex: 'style',width:120},
    	{header: '规格', dataIndex: 'prod_spec',width:120,renderer:function(value,metadata,record){
			metadata.tdAttr = "data-qtip='" + value+ "'";
		    return value;
		}
		},
    	{header: '订购数量',dataIndex:'orderNum',xtype: 'numbercolumn', format:'0',width:60},
    	{header: '入库数量',dataIndex:'totalNum',xtype: 'numbercolumn', format:'0',width:60}
      ];
      
	  me.store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			model: 'Ems.store.Order',
			autoLoad:false,
			proxy:{
				type:'ajax',
				actionMethods:{read:'POST'},
				url:Ext.ContextPath+'/order/queryList.do',
				reader:{
					type:'json',
					root:'root'
				}
			}
	  });
	  
      me.dockedItems= [{
	        xtype: 'pagingtoolbar',
	        store: me.store,  
	        dock: 'bottom',
	        displayInfo: true
	  }];

	me.tbar=	[{
			text: '刷新',
			itemId:'reload',
			disabled:me.disabledAction,
			handler: function(btn){
				var grid=btn.up("grid");
				grid.getStore().reload();
			},
			iconCls: 'form-reload-button'
		},{
			text: '新增',
			//itemId:'reload',
			disabled:me.disabledAction,
			handler: function(btn){
				me.addList();
			},
			iconCls: 'form-add-button'
		},{
			text: '修改',
			//itemId:'reload',
			//disabled:me.disabledAction,
			handler: function(btn){
				var grid=btn.up("grid");
				grid.getStore().reload();
			},
			iconCls: 'form-update-button'
		},{
			text: '删除',
			//itemId:'reload',
			//disabled:me.disabledAction,
			handler: function(btn){
				var grid=btn.up("grid");
				grid.getStore().reload();
			},
			iconCls: 'form-delete-button'
		}]
       
      me.callParent();
	},
	//添加订单明细，参数是订单信息
	addList:function(orderNo){
		var form=Ext.create('Ems.store.OrderForm',{});
		var win=Ext.create('Ext.window.Window',{
			modal:true,
			title:"新增",
			items:[form]
		});
		win.show();
	},
	//添加订单明细，参数是订单信息
	updateList:function(order){
	
	},
	//添加订单明细，参数是订单信息
	deleteList:function(id){
	
	}
});
