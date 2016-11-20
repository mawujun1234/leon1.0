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
		},
		getRowClass: function(record, rowIndex, rowParams, store){
		    	if(record.get('diff')){
                	return "yellowColor";
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
    	{header: '安装时间', dataIndex: 'last_install_date',renderer:Ext.util.Format.dateRenderer('Y-m-d')},
    	{
            xtype:'actioncolumn',
            width:50,
            header: '交换',
            items: [{
            	iconCls: 'icon-cogs',
            	tooltip: '交换',
                handler: function(grid, rowIndex, colIndex) {
                    var record = grid.getStore().getAt(rowIndex);
                    if(!record.get("diff")){
                    	Ext.Msg.alert("消息","该设备没有问题，不准操作！");
                    	return;
                    }
                    var diffgrid=Ext.create('Ems.check.DiffEquipmentGrid',{
                    	
                    });
                    diffgrid.getStore().getProxy().extraParams={
						check_id:window.selRecord.get("id"),
						pole_id:window.selRecord.get("pole_id"),
					}
					diffgrid.getStore().reload();
                    var win=Ext.create('Ext.window.Window',{
                    	layout:'fit',
                    	title:'把当前设备和当前点位上的某个设备(双击选择的设备)进行交换',
                    	modal:true,
                    	height:300,
                    	width:500,
                    	items:[diffgrid]
                    });
                    win.show();
                    
                    diffgrid.on("itemdblclick",function(view , record_new , item , index , e , eOpts){
                    	Ext.Msg.confirm("交换",'确定把设备"'+record.get("ecode")+'"转移到"'+record_new.get("orginal_name")+'"上?\n '+
                    		'把设备"'+record_new.get("ecode")+'"转移到"'+record.get("orginal_name")+'"上，让它们交换下位置。', function(btn, text){
	            			if (btn == 'yes'){
	            				Ext.Ajax.request({
	            					url:Ext.ContextPath+"/check/exchange.do",
	            					method:'POST',
	            					jsonData:{
	            						scan_eqip:{
	            							check_id:window.selRecord.get("id"),
		            						ecode:record.get("ecode"),
		            						orginal_id:record.get("orginal_id"),
		            						orginal_type:record.get("orginal_type"),
		            						target_id:record_new.get("orginal_id"),
		            						target_type:record_new.get("orginal_type")
	            						},
	            						pole_eqip:{
	            							check_id:window.selRecord.get("id"),
		            						ecode:record_new.get("ecode"),
		            						orginal_id:record_new.get("orginal_id"),
		            						orginal_type:record_new.get("orginal_type"),
		            						target_id:record.get("orginal_id"),
		            						target_type:record.get("orginal_type")
	            						}
	            						
	            					},
	            					success:function(response) {
	            						var obj=Ext.decode(response.responseText);
	            						Ext.Msg.alert("消息","成功!");
	            						window.reloadDiff();
	            						win.close();
	            					}
	            					
	            				});
	            			}
	                	});
                    });
                    
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
                    var record = grid.getStore().getAt(rowIndex);
                    if(!record.get("diff")){
                    	Ext.Msg.alert("消息","该设备没有问题，不准操作！");
                    	return;
                    }
                    Ext.Msg.confirm("转移",'确定把该设备从"'+record.get("orginal_name")+'"转移到"'+window.selRecord.get("pole_name")+'"点位上?', function(btn, text){
            			if (btn == 'yes'){
            				Ext.Ajax.request({
            					url:Ext.ContextPath+"/check/transfer.do",
            					method:'POST',
            					params:{
            						check_id:window.selRecord.get("id"),
            						//pole_id:window.selRecord.get("pole_id"),
            						ecode:record.get("ecode"),
            						orginal_id:record.get("orginal_id"),
            						orginal_type:record.get("orginal_type"),
            						target_id:window.selRecord.get("pole_id"),
            						target_type:'pole'
            						
            					},
            					success:function(response) {
            						var obj=Ext.decode(response.responseText);
            						Ext.Msg.alert("消息","成功!");
            						window.reloadDiff();
            					}
            					
            				});
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
