Ext.require("Ems.task.HitchReasonTpl");
Ext.require("Ems.task.HitchReasonTplGrid");
Ext.require("Ems.task.HitchTypeGrid");
Ext.onReady(function(){
	
	
	var hitchTypeGrid=Ext.create('Ems.task.HitchTypeGrid',{
		split: true,
		//collapsible: true,
		title:'故障类型',
		flex:1
	});
	
	var hitchReasonTplGrid=Ext.create('Ems.task.HitchReasonTplGrid',{
		split: true,
		//collapsible: true,
		title:'故障原因模板',
		flex:1
	});

	hitchTypeGrid.on('itemclick',function(view,record,item,index){
		//gridList.getStore().load({params:{adjust_id:record.get("id")}});
		hitchReasonTplGrid.getStore().getProxy().extraParams={hitchType_id:record.get("id")};
		hitchReasonTplGrid.getStore().load();
		
		hitchReasonTplGrid.hitchType_id=record.get("id");
	});
	
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout: {
           type: 'hbox',
           align: 'stretch'
        },
		items:[hitchTypeGrid,hitchReasonTplGrid]
	});

});