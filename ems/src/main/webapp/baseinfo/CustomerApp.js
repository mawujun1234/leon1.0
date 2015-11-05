Ext.require("Ems.baseinfo.Customer");
Ext.require("Ems.baseinfo.CustomerTreeGrid");
Ext.require("Ems.baseinfo.PoleGrid");
Ext.require("Ems.baseinfo.PoleEquipmentGrid");
Ext.require("Ems.baseinfo.EquipmentCycleGrid");
Ext.require("Ems.baseinfo.CustomerContactGrid");
Ext.require("Ems.baseinfo.CustomerForm");
Ext.onReady(function(){
	var grid=Ext.create('Ems.baseinfo.CustomerTreeGrid',{
		region:'west',
		split: true,
		collapsible: true,
		title:'客户列表',
		width:400
	});
	grid.on('itemclick',function(view,record,item,index){
		//if(record.get("type")==2){
			//return;
		//}

		customercontactgrid.customer_id=record.get("id");
		customercontactgrid.getStore().getProxy().extraParams={customer_id:record.get("id")};
		customercontactgrid.getStore().load();
		
		poleGrid.customer_id=record.get("id");
		poleGrid.getStore().getProxy().extraParams={customer_id:record.get("id")};
		poleGrid.getStore().loadPage(1);
		
		equipment_grid.getStore().removeAll();
		tabpanel.getEl().unmask();
	});
	var customercontactgrid=Ext.create('Ems.baseinfo.CustomerContactGrid',{
		region:'north',
		//split: true,
		height:200,
		//collapsible: true,
		title:'联系方式'
	});
	var poleGrid=Ext.create('Ems.baseinfo.PoleGrid',{
		region:'center',
		//split: true,
		height:200
		//collapsible: true,
		//title:'点位信息'
	});
	
	//var record=me.getSelectionModel( ).getLastSelected( );
    var equipment_grid=Ext.create('Ems.baseinfo.PoleEquipmentGrid',{
    	title:'拥有的设备',
    	height:300,
    	split: true,
    	collapsible: true,
    	region:'south'
    });
    poleGrid.on('itemclick',function(view,record,item,index){
    	equipment_grid.getStore().load({params:{id:record.get("id")}});
    });
    
	var panel=Ext.create('Ext.panel.Panel',{
		title:'点位管理',
		region:'center',
		layout:'border',
		items:[poleGrid,equipment_grid]
	});

	var tabpanel=Ext.create('Ext.tab.Panel',{
		region:'center',
		layout:'border',
		items:[panel,customercontactgrid],
		listeners:{
			render:function(tabpanel){
				tabpanel.getEl().mask();
			}
		}
	});

	
	
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'border',
		items:[grid,tabpanel]
	});

});