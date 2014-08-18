Ext.define('Ems.adjust.AdjustGrid',{
	extend:'Ext.grid.Panel',
	requires: [
	     'Ems.adjust.Adjust'
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
		{dataIndex:'id',text:'单号'},
		{dataIndex:'status_name',text:'状态',width:60},
		{dataIndex: 'str_out_name',text: '出库仓库'},
    	{dataIndex: 'str_in_name',text: '入库仓库'},
    	{dataIndex:'str_out_date',text:'出库时间',xtype: 'datecolumn',   format:'Y-m-d',width:80},
    	{dataIndex:'memo',text:'备注',flex:1}
		//{dataIndex:'str_out_id',text:'str_out_id'}
		//{dataIndex:'str_in_date',text:'str_in_date',xtype: 'datecolumn',   format:'Y-m-d'},
		//{dataIndex:'str_in_id',text:'str_in_id'},
		//{dataIndex:'str_in_oper_id',text:'str_in_oper_id'},
		
		//{dataIndex:'str_out_oper_id',text:'str_out_oper_id'}
      ];
      
	  me.store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			model: 'Ems.adjust.Adjust',
			autoLoad:true,
			proxy:{
				type:'ajax',
				actionMethods:{read:'POST'},
				url:Ext.ContextPath+'/adjust/query4InStr.do',
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
		}]
       
      me.callParent();
	}
});
