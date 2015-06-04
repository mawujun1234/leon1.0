Ext.define('Ems.install.InstallInListGrid',{
	extend:'Ext.grid.Panel',
	requires: [
	     'Ems.install.InstallInList'
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
		//{dataIndex:'id',text:'id'},
      	{xtype:'rownumberer'},
		//{dataIndex:'installIn_id',text:'installIn_id'},
		//{dataIndex:'isBad',text:'isBad'},
		{dataIndex:'ecode',text:'条码'},
		//{dataIndex:'installIn_id',text:'installIn_id'},
		//{dataIndex:'isBad',text:'isBad'}
		{dataIndex:'subtype_name',text:'小类'},
		{dataIndex:'prod_name',text:'品名'},
		{dataIndex:'brand_name',text:'品牌'},
		{dataIndex:'supplier_name',text:'供应商'},
		{dataIndex:'style',text:'型号'},
		{header:'规格',dataIndex:'prod_spec',flex:1,renderer:function(value,metadata,record){
						metadata.tdAttr = "data-qtip='" + value+ "'";
					    return value;
						}
		}
      ];
      
	  me.store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			model: 'Ems.install.InstallInList',
			autoLoad:false,
			proxy:{
				type:'ajax',
				actionMethods:{read:'POST'},
				url:Ext.ContextPath+'/installIn/queryList.do',
				reader:{
					type:'json',
					root:'root'
				}
			}
	  });
	  
      me.dockedItems= [{
	        xtype: 'pagingtoolbar',
	        store: me.store,  
	        dock: 'bottom',
	        displayInfo: true
	  }];
	  
	  me.tbar=	[{
			text: '刷新',
			itemId:'reload',
			disabled:me.disabledAction,
			handler: function(btn){
				var grid=btn.up("grid");
				grid.getStore().reload();
			},
			iconCls: 'form-reload-button'
		}]
       
      me.callParent();
	}
});
