Ext.define('Ems.store.OrderListGrid',{
	extend:'Ext.grid.Panel',
	requires: [
	    'Ems.store.Order',
		'Ems.store.OrderForm'
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
		{header: '单位', dataIndex: 'prod_unit',width:70},
    	{header: '订购数量',dataIndex:'orderNum',xtype: 'numbercolumn', format:'0',width:60},
    	{header: '单价',dataIndex:'unitPrice',xtype: 'numbercolumn', format:'0',width:60},
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
			//disabled:me.disabledAction,
			handler: function(btn){
				var grid=btn.up("grid");
				grid.getStore().reload();
			},
			iconCls: 'form-reload-button'
		},{
			text: '新增',
			//itemId:'reload',
			//disabled:me.disabledAction,
			handler: function(btn){
				me.addList();
			},
			iconCls: 'form-add-button'
		},{
			text: '修改',
			//itemId:'reload',
			//disabled:me.disabledAction,
			handler: function(btn){
				me.updateList();
			},
			iconCls: 'form-update-button'
		},{
			text: '删除',
			//itemId:'reload',
			//disabled:me.disabledAction,
			handler: function(btn){
				me.deleteList();
			},
			iconCls: 'form-delete-button'
		}]
       
      me.callParent();
	},
	//添加订单明细，参数是订单信息
	addList:function(){
		var me=this;
		var documentWidth=Ext.getBody().getWidth();
		var orderNo=this.getStore().getProxy().extraParams.orderNo;
		if(!orderNo){
       		alert("请先在左边选择一个订单!");
       		return;
        }
		
		var form=Ext.create('Ems.store.OrderForm',{
			orderNo:orderNo,
			url:Ext.ContextPath+'/order/addList.do',
			listeners:{
				saved:function(){
					win.close();
					me.getStore().reload();
				}
			}	
		});
		var win=Ext.create('Ext.window.Window',{
			modal:true,
			width:documentWidth-100,
			title:"新增",
			closeAction:"destroy",
			items:[form]
		});
		win.show();
	},
	//更新订单明细，参数是订单信息
	updateList:function(){
		var me=this;
		var documentWidth=Ext.getBody().getWidth();
		var orderNo=this.getStore().getProxy().extraParams.orderNo;
		if(!orderNo){
       		alert("请先在左边选择一个订单!");
       		return;
        }
		var record=me.getSelectionModel().getLastSelected();
		//console.log(record.get("unitPrice"));
		var form=Ext.create('Ems.store.OrderForm',{
			//order_id:orderNo,
			orderNo:orderNo,
			url:Ext.ContextPath+'/order/updateList.do',
			listeners:{
				saved:function(){
					win.close();
					me.getStore().reload();
					me.getSelectionModel().deselect(record);
				}
			}	
		});
		form.updateFieldValue(record);
		
		
		var win=Ext.create('Ext.window.Window',{
			modal:true,
			width:documentWidth-100,
			title:"更新",
			closeAction:"destroy",
			items:[form]
		});
		win.show();
	},
	//添加订单明细，参数是订单信息
	deleteList:function(){
		var me=this;
		var record = me.getSelectionModel().getLastSelected();
		if (!record) {
			alert("请先选择一个订单明细!");
			return;
		}
		Ext.Msg.confirm("消息","确认删除吗?",function(btn){
			if(btn=='yes'){
				
				Ext.Ajax.request({
					url:Ext.ContextPath+'/order/deleteList.do',
					params:{id:record.get("id")},
					success:function(){
						me.getStore().reload();
					}
				});
			}
		})
	}
});
