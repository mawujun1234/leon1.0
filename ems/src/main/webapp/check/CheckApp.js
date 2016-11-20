Ext.require("Ems.check.Check");
Ext.require("Ems.check.CheckGrid");
Ext.require("Ems.check.PoleEquipmentGrid");
Ext.require("Ems.check.ScanEquipmentGrid");
Ext.require("Ems.check.TrimGrid");
Ext.onReady(function(){
	var grid=Ext.create('Ems.check.CheckGrid',{
		title:'盘点单',
		width:400,
		split:true,
		collapsible : true,
		region:'west'
	});
	window.selRecord=null;
	
	window.reloadDiff=function(params){
		if(params){
			window.reload_params=params;
		} else {
			params=window.reload_params;
		}
		
		Ext.Ajax.request({
			url:Ext.ContextPath+"/check/queryDifferentEquipment.do",
			method:'POST',
			params:params,
			success:function(response) {
				var obj=Ext.decode(response.responseText);
				scanEquipmentGrid.getStore().loadData(obj.root.scan_records);
				poleEquipmentGrid.getStore().loadData(obj.root.pole_records);
			}
			
		});
	}
	grid.on("itemclick",function(view, record , item , index , e , eOpts){
//		scanEquipmentGrid.getStore().reload({params:{check_id:record.get("id")}});
//		poleEquipmentGrid.getStore().reload({params:{pole_id:record.get("pole_id")}});
//		onlyDifferent.setValue(true);
//		
//		pubCodeGrid.getStore().getProxy().extraParams={
//			check_id:record.get("id")
//		}
		onlyDifferent.setValue(true);
		selRecord=record;
		window.reloadDiff({
			check_id:selRecord.get("id"),
			pole_id:selRecord.get("pole_id"),
			onlyDifferent:true
		});
		
		trimGrid.getStore().getProxy().extraParams={
			check_id:record.get("id")
		}
		trimGrid.getStore().reload();
	});
	
	
	var scanEquipmentGrid=Ext.create('Ems.check.ScanEquipmentGrid',{
		title:'扫描的设备--点位上实际存在的设备记录',
		region:'center'
	});
	var poleEquipmentGrid=Ext.create('Ems.check.PoleEquipmentGrid',{
		title:'点位上记录的设备',
		split:true,
		width:400,
		collapsible : true,
		region:'east'
	});
	
	var onlyDifferent=Ext.create('Ext.form.field.Checkbox',{
		fieldLabel:'只显示差异',
		checked:true,
		labelWidth:80
	});
	onlyDifferent.on("change",function(field , newValue , oldValue , eOpts){
		window.reloadDiff({
			check_id:selRecord.get("id"),
			pole_id:selRecord.get("pole_id"),
			onlyDifferent:newValue
		});
//		Ext.Ajax.request({
//			url:Ext.ContextPath+"/check/queryDifferentEquipment.do",
//			method:'POST',
//			params:{
//				check_id:selRecord.get("id"),
//				pole_id:selRecord.get("pole_id"),
//				onlyDifferent:newValue
//			},
//			success:function(response) {
//				var obj=Ext.decode(response.responseText);
//				scanEquipmentGrid.getStore().loadData(obj.root.scan_records);
//				poleEquipmentGrid.getStore().loadData(obj.root.pole_records);
//			}
//			
//		});

		
	});
	
	var trimGrid=Ext.create('Ems.check.TrimGrid',{
		title:'调整记录'
	});
	
	var tabpanel=Ext.create('Ext.TabPanel', {
	    fullscreen: true,
	    region:'center',
	    tabBarPosition: 'top',
	    defaults: {
	        styleHtmlContent: true
	    },
	    
	    items: [
	        {
	            title: '差异比较',
	            layout:'border',
	            tbar:[onlyDifferent],
	            items:[scanEquipmentGrid,poleEquipmentGrid]
	        },
	        trimGrid
	    ]
	});
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'border',
		items:[grid,tabpanel]
	});



});