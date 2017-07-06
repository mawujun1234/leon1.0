Ext.define('Ems.baseinfo.SupplierGrid',{
	extend:'Ext.grid.Panel',
	requires: [
	     'Ems.baseinfo.Supplier'
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
		{dataIndex:'id',text:'编码',width:60},
		{dataIndex:'name',text:'名称',flex:1},
		//{dataIndex:'sname',text:'公司缩写'},
		//{dataIndex:'memo',text:'描述'},
		{dataIndex:'website',text:'网址'},
		{dataIndex:'status',text:'状态',width:60,renderer:function(value){
			if(value){
				return "有效";
			} else {
				return "<span style='color:red'>无效</>";
			}
		}}
      ];
      
	  me.store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			model: 'Ems.baseinfo.Supplier',
			autoLoad:true
	  });
	  
//      me.dockedItems= [{
//	        xtype: 'pagingtoolbar',
//	        store: me.store,  
//	        dock: 'bottom',
//	        displayInfo: true
//	  }];
	  
	  me.tbar=	[{
		    emptyText : '名称',
		    itemId:'name',
		    xtype:'textfield'
		},{
			text: '刷新',
			itemId:'reload',
			disabled:me.disabledAction,
			handler: function(btn){
				var grid=btn.up("grid");
				var name=btn.previousSibling ("#name").getValue();
				grid.getStore().reload({params:{name:name}});
			},
			iconCls: 'form-reload-button'
		}]
       
      me.callParent();
	}
});
