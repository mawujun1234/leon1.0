Ext.define('Leon.desktop.fun.FunGridQuery',{
	extend:'Ext.grid.Panel',
	requires: [
	     'Leon.desktop.fun.Fun'
	],
	columnLines :true,
	stripeRows:true,
	viewConfig:{
		stripeRows:true,
		listeners:{
			refresh:function(){
				this.select(0);
			}
		}
	},
	initComponent: function () {
      var me = this;
      me.columns=[
		{dataIndex:'id',text:'id'},
		{dataIndex:'text',text:'名称'},
		{dataIndex:'url',text:'地址',flex:1},
	    {dataIndex:'bussinessType',text:'业务类型'}
      ];
      
	  me.store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			model: 'Leon.desktop.fun.Fun',
			autoLoad:true
	  });
	  
      me.dockedItems= [{
	        xtype: 'pagingtoolbar',
	        store: me.store,  
	        dock: 'bottom',
	        displayInfo: true
	  }];
       
      me.callParent();
	}
});