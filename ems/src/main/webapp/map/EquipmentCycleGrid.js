Ext.define('Ems.map.EquipmentCycleGrid',{
	extend:'Ext.grid.Panel',
	requires: [
	     'Ems.baseinfo.EquipmentCycle'
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
      	{xtype: 'rownumberer'},
		//{dataIndex:'id',text:'id'},
      	{dataIndex:'operateDate',text:'时间',width:130},
		{dataIndex:'ecode',text:'二维码',width:140},
		{dataIndex:'operateType_name',text:'类型',width:100},
		{dataIndex:'type_id',text:'单据号'},
		{dataIndex:'target_name',text:'设备去向',flex:1},
		{dataIndex:'operater_name',text:'操作者'},
		{dataIndex:'memo',text:'备注'}
      ];
      
	  me.store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			model: 'Ems.baseinfo.EquipmentCycle',
			autoLoad:false
	  });
	   me.tbar=	[{
			text: '刷新',
			itemId:'reload',
			disabled:me.disabledAction,
			handler: function(btn){
				var grid=btn.up("grid");
				grid.getStore().reload();
			},
			iconCls: 'form-reload-button'
		}];
	  
//	  me.tbar=	[{
//			text: '刷新',
//			itemId:'reload',
//			disabled:me.disabledAction,
//			handler: function(btn){
//				var grid=btn.up("grid");
//				grid.getStore().reload();
//			},
//			iconCls: 'form-reload-button'
//		},{
//			text: '导出重新打印',
//			icon:'../icons/page_excel.png',
//			handler: function(btn){
//				var grid=btn.up("grid");
//				window.open(Ext.ContextPath+"/equipment/exportEcode.do?ecode="+grid.ecode, "_blank");
//			}
//		},{
//			text: '手工设置为损坏',
//			icon:'../icons/webcam_delete.png',
//			handler: function(btn){
//				//alert("还没有做好");
//				//return;
//				Ext.Msg.prompt("原因","请输入手工修改原因。",function(btn1,txt){
//					if(btn1=='ok'){
//						Ext.Ajax.request({
//							url:Ext.ContextPath+'/equipment/wait_for_repair.do',
//							params:{ecode:me.ecode,reason:txt},
//							method:'POST',
//							success:function(response){
//								var grid=btn.up("grid");
//								grid.getStore().reload();
//							}
//						});
//					}
//				});
//			}
//		},{
//			text: '手工设置为旧品',
//			icon:'../icons/webcam_error.png',
//			handler: function(btn){
//				//alert("还没有做好");
//				//return;
//				Ext.Msg.prompt("原因","请输入手工修改原因。",function(btn1,txt){
//					if(btn1=='ok'){
//						Ext.Ajax.request({
//							url:Ext.ContextPath+'/equipment/to_old.do',
//							params:{ecode:me.ecode,reason:txt},
//							method:'POST',
//							success:function(response){
//								var grid=btn.up("grid");
//								grid.getStore().reload();
//							}
//						});
//					}
//				});
//			}
//		}]
       
      me.callParent();
	}
});
