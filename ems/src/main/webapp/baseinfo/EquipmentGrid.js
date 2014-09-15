/**
 * 显示杆位上有哪些设备的
 */
Ext.define('Ems.baseinfo.EquipmentGrid',{
	extend:'Ext.grid.Panel',
	requires: [
	     'Ems.baseinfo.Equipment'
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
    	{header: '条码', dataIndex: 'ecode',width:150},
    	{header: '设备类型', dataIndex: 'subtype_name',width:120},
    	{header: '品名', dataIndex: 'prod_name'},
    	{header: '品牌', dataIndex: 'brand_name',width:120},
    	{header: '供应商', dataIndex: 'supplier_name'},
    	{header: '设备型号', dataIndex: 'style',width:120}
      ];
      
	  me.store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			model: 'Ems.baseinfo.Equipment',
	        autoLoad:false,
	        proxy: {
		        type: 'ajax',
		        url: '/pole/queryEquipments.do',  // url that will load data with respect to start and limit params
		        reader: {
		            type: 'json',
		            root: 'root',
		            totalProperty: 'total'
		        }
		    }
	  });
	  //me.store.getProxy().extraParams ={isGrid:true}
	  
//	  me.tbar=	[{
//			text: '刷新',
//			itemId:'reload',
//			disabled:me.disabledAction,
//			handler: function(btn){
//				var grid=btn.up("grid");
//				grid.getStore().reload();
//			},
//			iconCls: 'form-reload-button'
//		}]
       
      me.callParent();
	}
});
