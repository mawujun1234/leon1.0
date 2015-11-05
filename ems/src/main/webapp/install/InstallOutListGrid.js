Ext.define('Ems.install.InstallOutListGrid',{
	extend:'Ext.grid.Panel',
	requires: [
	     'Ems.install.InstallOutList'
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
		//{dataIndex:'id',text:'id'},
        Ext.create('Ext.grid.RowNumberer'),
		{dataIndex:'ecode',text:'条码',width:130},
		{dataIndex:'installOutType_name',text:'领用类型'},
		{dataIndex:'installOutType_name',text:'领用类型二级'},
		{dataIndex:'installOutListType_name',text:'借/领'},
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
		//{dataIndex:'num',text:'数量',summaryType: 'sum'}
      ];
      
	  me.store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			model: 'Ems.install.InstallOutList',
			autoLoad:false,
			proxy:{
				type:'ajax',
				actionMethods:{read:'POST'},
				url:Ext.ContextPath+'/installOut/queryList.do',
				reader:{
					type:'json',
					root:'root'
				}
			}
	  });
	  
//      me.dockedItems= [{
//	        xtype: 'pagingtoolbar',
//	        store: me.store,  
//	        dock: 'bottom',
//	        displayInfo: true
//	  }];
       
      me.callParent();
	}
});
