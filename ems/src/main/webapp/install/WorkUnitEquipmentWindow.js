/**
 * 作业单位的手持设备情况
 */
Ext.define('Ems.install.WorkUnitEquipmentWindow',{
	extend:'Ext.window.Window',
	requires: [
	     'Ems.baseinfo.Equipment'
	],
	layout:'fit',
	workUnit_id:null,
	initComponent: function () {
		var me=this;
		var equip_store=Ext.create('Ext.data.Store',{
			//fields:[],
			autoLoad:false,
			model:'Ems.baseinfo.Equipment',
			proxy:{
				type:'ajax',
				url:Ext.ContextPath+'/workUnit/queryEquipments.do',
				reader:{
					type:'json',
					root:'root'
				}
			}
		});
		equip_store.load({params:{workUnit_id:me.workUnit_id}});
		
		var equip_grid=Ext.create('Ext.grid.Panel',{
			flex:1,
			store:equip_store,
	    	columns: [Ext.create('Ext.grid.RowNumberer'),
	    			{header: '条码', dataIndex: 'ecode',width:130},
	    	          {header: '设备类型', dataIndex: 'subtype_name',width:120},
	    	          {header: '品名', dataIndex: 'prod_name'},
	    	          {header: '供应商', dataIndex: 'supplier_name'},
	    	          {header: '品牌', dataIndex: 'brand_name',width:120},
	    	          
	    	          {header: '设备型号', dataIndex: 'style',width:120},
	    	          //{header: '仓库', dataIndex: 'store_name'},
	    	          //{header: '数量', dataIndex: 'serialNum',width:70},
	    	          {header: '单价(元)', dataIndex: 'unitPrice',width:70},
	    	          //{header: 'stid', dataIndex: 'stid',hideable:false,hidden:true},
	    	         // {header: '库房', dataIndex: 'stock',width:120},
	    	          {header: '状态', dataIndex: 'status',width:60,renderer:function(value){
	    	          	if(value==0){
	    	          		return '<font color="red">未入库</font>';
	    	          	} else if(value==1){
	    	          		return "已入库";
	    	          	} else if(value==2){
	    	          		return "正常出库(等待安装)";
	    	          	} else if(value==4){
	    	          		return "损坏";
	    	          	} else if(value==9){
	    	          		return "维修后出库";
	    	          	} else {
	    	          		return "";
	    	          	}
	    	          }}
	    	]
		});
		
		me.items=[equip_grid];
		me.callParent();	
	}
	
});