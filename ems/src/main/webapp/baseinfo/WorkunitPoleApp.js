
Ext.require("Ems.baseinfo.WorkUnitGrid");
Ext.require("Ems.baseinfo.WorkunitPoleGrid");
Ext.require("Ems.baseinfo.PoleEquipmentGrid");
Ext.require('Ems.baseinfo.EquipmentCycleGrid');
Ext.onReady(function(){
	var grid=Ext.create('Ems.baseinfo.WorkUnitGrid',{
		region:'west',
		split: true,
		collapsible: true,
		title:'作业单位',
		width:300
	});


	grid.on('itemclick',function(view,record,item,index){
		areaPoleGrid.getEl().unmask();
		
		areaPoleGrid.workunit_id=record.get("id");
		
		areaPoleGrid.getStore().getProxy().extraParams={workunit_id:record.get("id")};
		areaPoleGrid.getStore().reload();
		
		equipment_grid.getStore().removeAll();
	});
	
	var areaPoleGrid=Ext.create('Ems.baseinfo.WorkunitPoleGrid',{
		region:'center',
		split: true,
		collapsible: true,
		title:'点位信息',
		listeners:{
			render:function(grid){
				grid.getEl().mask();
			}
		}
	});
	
	var equipment_grid=Ext.create('Ems.baseinfo.PoleEquipmentGrid',{
    	title:'拥有的设备',
    	height:200,
    	split: true,
    	collapsible: true,
    	//collapsed:true,
    	region:'south'
    });
    areaPoleGrid.on('itemclick',function(view,record,item,index){
    	equipment_grid.getStore().load({params:{id:record.get("id")}});
    });
    
    var panel=Ext.create('Ext.panel.Panel',{
		//title:'点位管理',
		region:'center',
		layout:'border',
		items:[areaPoleGrid,equipment_grid]
//		listeners:{
//			render:function(panel){
//				panel.getEl().mask();
//			}
//		}
	});
	
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'border',
		items:[grid,panel]
	});

});