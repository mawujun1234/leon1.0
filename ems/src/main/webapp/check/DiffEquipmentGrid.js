/**
 * 显示点位上有哪些设备的
 */
Ext.define('Ems.check.DiffEquipmentGrid',{
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
	listeners : {
		cellclick : function(view, td, cellIndex, record, tr, rowIndex,e, eOpts) {
			if (cellIndex == 1) {
				var lifecycle_panel = Ext.create('Ems.baseinfo.EquipmentCycleGrid', {
							
				});
				lifecycle_panel.getStore().getProxy().extraParams={ecode:record.get("ecode")};
				lifecycle_panel.ecode=record.get("ecode");
				lifecycle_panel.getStore().reload();

				var win = Ext.create('Ext.window.Window', {
					width : 780,
					height : 300,
					layout : 'fit',
					modal : true,
					title : '生命周期记录',
					items : [lifecycle_panel]
				});
				win.show();
			}
		}
	},
	initComponent: function () {
      var me = this;
      me.columns=[
		Ext.create('Ext.grid.RowNumberer'),
    	{header: '条码', dataIndex: 'ecode',width:140,renderer:function(value,metadata,record){
    		return "<a href='javascript:void(0);' >"+value+"</a>";
    	}},
    	{header: '所在位置', dataIndex: 'orginal_name',width:120},
    	{header: '设备类型', dataIndex: 'subtype_name',width:120},
    	{header: '品名', dataIndex: 'prod_name'},
    	{header: '设备型号', dataIndex: 'style',width:120},
    	{header: '规格', dataIndex: 'prod_spec',width:120,renderer:function(value,metadata,record){
						metadata.tdAttr = "data-qtip='" + value+ "'";
					    return value;
					}
		},
    	{header: '品牌', dataIndex: 'brand_name',width:60},
    	{header: '质保', dataIndex: 'prod_quality_month',width:60},
    	{header: '供应商', dataIndex: 'supplier_name'},
    	{header: '安装时间', dataIndex: 'last_install_date',renderer:Ext.util.Format.dateRenderer('Y-m-d')}
      ];
      
	  me.store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			model: 'Ems.baseinfo.Equipment',
	        autoLoad:false,
	        //data :[]
	        proxy: {
		        type: 'ajax',
		        url: Ext.ContextPath+'/check/queryDifferentPoleEquipment.do',  // url that will load data with respect to start and limit params
		        reader: {
		            type: 'json',
		            rootProperty: 'root',
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
