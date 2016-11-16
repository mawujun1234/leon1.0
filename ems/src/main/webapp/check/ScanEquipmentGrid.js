/**
 *扫描过的点位上有哪些设备
 */
Ext.define('Ems.check.ScanEquipmentGrid',{
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
    	{header: '安装时间', dataIndex: 'last_install_date',renderer:Ext.util.Format.dateRenderer('Y-m-d')},
    	{
            xtype:'actioncolumn',
            width:50,
            header: '替换',
            items: [{
            	iconCls: 'icon-cogs',
            	tooltip: '替换',
                handler: function(grid, rowIndex, colIndex) {
                    var rec = grid.getStore().getAt(rowIndex);
                    
                    var diffgrid=Ext.create('Ems.check.DiffEquipmentGrid',{});
                    var win=Ext.create('Ext.window.Window',{
                    	layout:'fit',
                    	title:'替换掉点位上的设备(双击选择)',
                    	modal:true,
                    	height:300,
                    	width:500,
                    	items:[diffgrid]
                    });
                    win.show();
                    
                }
            }]
        },
        {
            xtype:'actioncolumn',
            width:50,
            header: '转移',
            items: [{
            	iconCls: 'icon-wrench',
            	tooltip: '转移',
                handler: function(grid, rowIndex, colIndex) {
                    var rec = grid.getStore().getAt(rowIndex);
                    Ext.Msg.confirm("转移",'确定把该设备转移到当前点位上?', function(btn, text){
            			if (btn == 'yes'){
            				
            			}
                	});
                }
            }]
        }
      ];
      
	  me.store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			model: 'Ems.baseinfo.Equipment',
	        autoLoad:false,
	        data :[]
//	        proxy: {
//		        type: 'ajax',
//		        url: Ext.ContextPath+'/check/queryScanEquipment.do',  // url that will load data with respect to start and limit params
//		        reader: {
//		            type: 'json',
//		            rootProperty: 'root',
//		            totalProperty: 'total'
//		        }
//		    }
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
