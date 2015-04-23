//Ext.require("Ems.task.Task");
//Ext.require("Ems.task.TaskQueryGrid");
Ext.onReady(function(){
	var store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:me.pageSize,
			model: 'Ems.task.Task',
			autoLoad:me.autoLoad1,
			proxy:{
				type:'ajax',
				url:Ext.ContextPath+'/task/query.do',
				reader:{
					type:'json',
					root:'root'
				}
			}
	});
	var grid=Ext.create('Ext.grid.Panel',{
		columnLines :true,
		stripeRows:true,
		columns:[
	        {dataIndex:'id',text:'任务编号',width:100},
			{dataIndex:'status_name',text:'状态',width:50,renderer:function(value,meta,record){
				if(record.get("status")=='submited'){
					return '<span style="color:red;">'+value+'</span>';
				}
				return value;
			}},
			{dataIndex:'type_name',text:'任务类型',width:60},
			{dataIndex:'pole_code',text:'点位编号',width:55},
			{dataIndex:'pole_name',text:'点位名称'},
			{dataIndex:'pole_address',text:'地址',flex:1},
			{dataIndex:'createDate',text:'创建时间', renderer: Ext.util.Format.dateRenderer('Y-m-d')},
			{dataIndex:'submitDate',text:'提交时间', renderer: Ext.util.Format.dateRenderer('Y-m-d')},
			{dataIndex:'completeDate',text:'完成时间', renderer: Ext.util.Format.dateRenderer('Y-m-d')},
			{dataIndex:'workunit_name',text:'作业单位'},
			{dataIndex:'customer_name',text:'所属客户'},
			{dataIndex:'memo',text:'任务描述',flex:1,renderer:function(value,metadata,record){
				metadata.tdAttr = "data-qtip='" + value+ "'";
			    return value;
			}}
	    ],
      	store:store,
      	dockedItems: [{
	        xtype: 'pagingtoolbar',
	        store: store,  
	        dock: 'bottom',
	        displayInfo: true
	  	}]
	  	 
	});
	



	
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'fit',
		items:[grid],
		listeners:{
			render:function(){
				
			}
		}
	});

});