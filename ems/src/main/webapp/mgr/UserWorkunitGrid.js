Ext.define('Ems.mgr.UserWorkunitGrid',{
	extend:'Ext.grid.Panel',
	requires: [
	     //'Ems.baseinfo.Area'
	],
	columnLines :true,
	stripeRows:true,
	viewConfig:{
		enableTextSelection:true
	},
	selModel: {
        selType: 'checkboxmodel',
        checkOnly :true
    },
	initComponent: function () {
      var me = this;
      me.columns=[
      	{xtype: 'rownumberer'},
      	{dataIndex:'name',text:'名称'},
		//{dataIndex:'workunit_name',text:'作业单位'},
		{dataIndex:'memo',text:'描述',flex:1}
      ];
      

	  me.store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			autoLoad:false,
			//model: 'Ems.baseinfo.Area',
			fields:[
					{name:'id',type:'string'},
					{name:'memo',type:'string'},
					{name:'name',type:'string'}
				],
			proxy:{
				type: 'ajax',
			    url : Ext.ContextPath+'/workunit/queryByUser.do',
			    headers:{ 'Accept':'application/json;'},
			    actionMethods: { read: 'POST' },
			    extraParams:{limit:50},
			    reader:{
					type:'json',//如果没有分页，那么可以把后面三行去掉，而且后台只需要返回一个数组就行了
					rootProperty:'root',
					successProperty:'success',
					totalProperty:'total'		
				}
			}
	  });

	 
       
      me.callParent();
	}
});
