Ext.require("Ems.check.Check");
Ext.require("Ems.check.CheckGrid");
Ext.require("Ems.check.PoleEquipmentGrid");
Ext.require("Ems.check.ScanEquipmentGrid");
Ext.onReady(function(){
	var grid=Ext.create('Ems.check.CheckGrid',{
		title:'盘点单',
		width:400,
		split:true,
		collapsible : true,
		region:'west'
	});
	var selRecord=null;
	grid.on("itemclick",function(view, record , item , index , e , eOpts){
//		scanEquipmentGrid.getStore().reload({params:{check_id:record.get("id")}});
//		poleEquipmentGrid.getStore().reload({params:{pole_id:record.get("pole_id")}});
//		onlyDifferent.setValue(true);
//		
//		pubCodeGrid.getStore().getProxy().extraParams={
//			check_id:record.get("id")
//		}
		selRecord=record;
		Ext.Ajax.request({
			url:Ext.ContextPath+"/check/queryDifferentEquipment.do",
			method:'POST',
			params:{
				check_id:selRecord.get("id"),
				pole_id:selRecord.get("pole_id"),
				onlyDifferent:true
			},
			success:function(response) {
				var obj=Ext.decode(response.responseText);
				scanEquipmentGrid.getStore().loadData(obj.root.scan_records);
				poleEquipmentGrid.getStore().loadData(obj.root.pole_records);
			}
			
		});
	});
	
	
	var scanEquipmentGrid=Ext.create('Ems.check.ScanEquipmentGrid',{
		title:'扫描的设备',
		region:'center'
	});
	var poleEquipmentGrid=Ext.create('Ems.check.PoleEquipmentGrid',{
		title:'记录的设备',
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
		Ext.Ajax.request({
			url:Ext.ContextPath+"/check/queryDifferentEquipment.do",
			method:'POST',
			params:{
				check_id:selRecord.get("id"),
				pole_id:selRecord.get("pole_id"),
				onlyDifferent:newValue
			},
			success:function(response) {
				var obj=Ext.decode(response.responseText);
				scanEquipmentGrid.getStore().loadData(obj.root.scan_records);
				poleEquipmentGrid.getStore().loadData(obj.root.pole_records);
			}
			
		});

		
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
	        {
	            title: '调整记录',
	            html: 'Contact Screen'
	        }
	    ]
	});
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'border',
		items:[grid,tabpanel]
	});



});