/**
 * 显示点位上有哪些设备的
 */
Ext.define('Ems.check.PoleEquipmentGrid',{
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
            header: '卸载',
            items: [{
            	iconCls: 'icon-remove',
            	tooltip: '卸载',
                handler: function(grid, rowIndex, colIndex) {
                    var record = grid.getStore().getAt(rowIndex);
                    if(!record.get("diff")){
                    	Ext.Msg.alert("消息","该设备没有问题，不准操作！");
                    	return;
                    }
                    Ext.Msg.confirm("卸载",'确定把该设备从点位上卸载?', function(btn, text){
            			if (btn == 'yes'){
            				var workunit_combox=Ext.create('Ext.form.field.ComboBox',{
						        fieldLabel: '作业单位',
						        labelAlign:'right',
					            labelWidth:55,
						        name: 'workunit_id',
							    displayField: 'name',
							    valueField: 'id',
						        store:Ext.create('Ext.data.Store', {
							    	fields: ['id', 'name'],
								    proxy:{
								    	type:'ajax',
								    	extraParams:{pole_id:record.get("orginal_id")},
								    	url:Ext.ContextPath+"/workunit/queryByPole.do",
								    	reader:{
								    		type:'json',
								    		rootProperty:'root'
								    	}
								    }
							   })
						    });
            				var win=Ext.create('Ext.window.Window',{
            					title:'选择作业单位',
            					layout:'form',
            					items:[workunit_combox],
            					modal:true,
            					width:260,
            					height:100,
            					buttons:[{
            						text:'取消',
            						handler:function(){
            							win.close();
            						}
            					},{
            						text:'确定',
            						handler:function(){
            							var workunit_id=workunit_combox.getValue();
            							if(!workunit_id){
            								Ext.Msg.alert("消息","请先选择一个作业单位！");
            								return;
            							}
            							//添加作业单位的选择
			            				Ext.Ajax.request({
			            					url:Ext.ContextPath+"/check/uninstall.do",
			            					method:'POST',
			            					params:{
			            						check_id:window.selRecord.get("id"),
			            						pole_id:window.selRecord.get("pole_id"),
			            						ecode:record.get("ecode"),
			            						orginal_id:record.get("orginal_id"),
			            						orginal_type:record.get("orginal_type"),
			            						target_id:workunit_id,
			            						target_type:'workunit'
			            					},
			            					success:function(response) {
			            						var obj=Ext.decode(response.responseText);
			            						Ext.Msg.alert("消息","成功!");
			            						win.close();
			            					}
			            					
			            				});
            						}
            					
            					}]
            				});
            				win.show();
            				
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
//		        url: Ext.ContextPath+'/check/queryPoleEquipment.do',  // url that will load data with respect to start and limit params
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
