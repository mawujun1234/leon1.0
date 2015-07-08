Ext.define('Ems.install.BorrowEditGrid',{
	extend:'Ext.grid.Panel',
	requires: [
	     'Ems.install.Borrow'
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
		{dataIndex:'id',text:'编码'},
		{dataIndex:'store_name',text:'仓库'},
		{dataIndex:'workUnit_name',text:'作业单位'},
		{dataIndex:'project_name',text:'项目'},
		{dataIndex:'operateDate',text:'操作时间',xtype: 'datecolumn',   format:'Y-m-d'},
		{dataIndex:'operater',text:'操作人'},
		{dataIndex:'memo',text:'备注'}
      ];
      
	  me.store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			model: 'Ems.install.Borrow',
			autoLoad:false,
			proxy:{
				type:'ajax',
				actionMethods:{read:'POST'},
				url:Ext.ContextPath+'/borrow/queryEditBorrow.do',
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
