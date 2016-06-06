Ext.define('Ems.install.BorrowListGrid',{
	extend:'Ext.grid.Panel',
	requires: [
	     'Ems.install.BorrowList'
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
      	Ext.create('Ext.grid.RowNumberer'),
		//{dataIndex:'id',text:'id'},
		{dataIndex:'ecode',text:'条码',width:130},
		{dataIndex:'borrowListType_name',text:'借/领',
			renderer:function(value,metadata,record){
				if("领用"==value){
					return "<span style='color:green;'>"+value+"</span>";
				}
				return value;
			}
		},
		//{dataIndex:'installIn_id',text:'installIn_id'},
		//{dataIndex:'isBad',text:'isBad'}
		{dataIndex:'returnDate',text:'归还/安装日期',xtype: 'datecolumn',   format:'Y-m-d'},
		{dataIndex:'subtype_name',text:'小类'},
		{dataIndex:'prod_name',text:'品名'},
		{dataIndex:'brand_name',text:'品牌'},
		{dataIndex:'supplier_name',text:'供应商'},
		{dataIndex:'style',text:'型号'},
		{header:'规格',dataIndex:'prod_spec',flex:1,renderer:function(value,metadata,record){
				metadata.tdAttr = "data-qtip='" + value+ "'";
				return value;
			}
		}
		//{dataIndex:'num',text:'数量',summaryType: 'sum'}
      ];
      
	  me.store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			model: 'Ems.install.BorrowList',
			autoLoad:false,
			proxy:{
				type:'ajax',
				actionMethods:{read:'POST'},
				url:Ext.ContextPath+'/borrow/queryList.do',
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
