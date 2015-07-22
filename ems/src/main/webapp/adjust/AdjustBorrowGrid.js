/**
 * 用来查询未归还的调拨单的
 */
Ext.define('Ems.adjust.AdjustBorrowGrid',{
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
      	Ext.create('Ext.grid.RowNumberer'),
		{dataIndex:'id',text:'单号'},
		{dataIndex:'status_name',text:'状态',width:60},
		{dataIndex:'adjustType_name',text:'调拨单类型'},
		{dataIndex:'returnStatus_name',text:'归还状态',width:60},
		{dataIndex: 'str_out_name',text: '出库仓库'},
    	{dataIndex: 'str_in_name',text: '入库仓库'},
    	{dataIndex:'str_out_date',text:'出库时间',xtype: 'datecolumn',   format:'Y-m-d',width:80},
    	{dataIndex:'memo',text:'备注',flex:1}
      ];
      
	  me.store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			model: 'Ems.adjust.Adjust',
			autoLoad:true,
			proxy:{
				type:'ajax',
				actionMethods:{read:'POST'},
				url:Ext.ContextPath+'/adjust/queryBorrowAdjuest.do',
				reader:{
					type:'json',
					root:'root'
				}
			}
	  });
	  
//      me.dockedItems= [{
//	        xtype: 'pagingtoolbar',
//	        store: me.store,  
//	        dock: 'bottom',
//	        displayInfo: true
//	  }];
	  

       
      me.callParent();
	}
});
