/**
 * 用来选择还在编辑中的单据
 */
Ext.define('Ems.install.InstallOutEditGrid',{
	extend:'Ext.grid.Panel',
	requires: [
	     'Ems.install.InstallOut'
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
        { header:'操作',
	                xtype: 'actioncolumn',
	                width: 50,
	                items: [{
	                    icon   : '../images/delete.gif',  // Use a URL in the icon config
	                    tooltip: '删除',
	                    handler: function(grid, rowIndex, colIndex) {
	                        var record = grid.getStore().getAt(rowIndex);
	                        Ext.MessageBox.confirm('确认', '您确认要删除该记录吗?', function(btn){
	                        	if(btn=='yes'){
	                        		Ext.Ajax.request({
										params : {id:record.get("id"),status:record.get("status")},//传递参数   
										url : Ext.ContextPath+'/installOut/deleteEditInstallOut.do',//请求的url地址   
										method : 'POST',//请求方式   
										success : function(response) {//加载成功的处理函数   
											var ret=Ext.decode(response.responseText);
											if(ret.success){
												grid.getStore().remove(record);
											}
										}
									});
	                        	}
	                        });
	                    }
	                }]
	            },
		{dataIndex:'id',text:'编码'},
		{dataIndex:'status_name',text:'状态'},
		{dataIndex:'store_name',text:'仓库'},
		{dataIndex:'workUnit_name',text:'作业单位'},
		{dataIndex:'project_name',text:'项目'},
		{dataIndex:'operateDate',text:'操作时间',xtype: 'datecolumn',   format:'Y-m-d'},
		//{dataIndex:'operater',text:'操作人'},
		{dataIndex:'memo',text:'备注'}
      ];
      
	  me.store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			model: 'Ems.install.InstallOut',
			autoLoad:false,
			proxy:{
				type:'ajax',
				actionMethods:{read:'POST'},
				url:Ext.ContextPath+'/installOut/queryEditInstallOut.do',
				reader:{
					type:'json',
					root:'root'
				}
			}
	  });
	  

	  me.store.load();
       
      me.callParent();
	}
});
