Ext.define('Ems.baseinfo.EquipmentCycleGrid',{
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
		{dataIndex:'ecode',text:'ecode',width:140},
		{dataIndex:'operateType_name',text:'类型',width:80},
		{dataIndex:'type_id',text:'单据号'},
		{dataIndex:'target_name',text:'设备去向',flex:1},
		{dataIndex:'operater_name',text:'操作者'}
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
		},{
			text: '导出重新打印',
			icon:'../icons/page_excel.png',
			handler: function(btn){
				var grid=btn.up("grid");
				window.open(Ext.ContextPath+"/equipment/exportEcode.do?ecode="+grid.ecode, "_blank");
			}
		}]
       
      me.callParent();
	}
});
