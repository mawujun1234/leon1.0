Ext.define('Ems.store.InStoreListGridQuery',{
	extend:'Ext.grid.Panel',
	requires: [
	     'Ems.store.InStoreList'
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
//	features: [{
//            id: 'group',
//            ftype: 'groupingsummary',
//            groupHeaderTpl: '{name}',
//            hideGroupedHeader: true,
//            enableGroupingMenu: false,
//            id:'restaurantGrouping'
//     }],
	initComponent: function () {
      var me = this;
      me.columns=[
      	Ext.create('Ext.grid.RowNumberer'),
		{dataIndex:'ecode',text:'条码',width:120},
		{header: '新/旧', dataIndex: 'isnew',width:100,renderer:function(value,metadata,record){
						if(value){
							return "新品";
						} else {
							return "<span style='color:red;'>旧品</span>";
						}
				  }},
		{dataIndex:'subtype_name',text:'小类'},
		{dataIndex:'prod_name',text:'品名'},
		{dataIndex:'brand_name',text:'品牌'},
		{dataIndex:'supplier_name',text:'供应商'},
		{dataIndex:'style',text:'型号'},
		{dataIndex:'prod_spec',text:'规格',flex:1,renderer:function(value,metadata,record){
			metadata.tdAttr = "data-qtip='" + value+ "'";
		    return value;
			}
		}
		//{dataIndex:'num',text:'数量',summaryType: 'sum'}
      ];
      
      
      
	  me.store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			model: 'Ems.store.InStoreList',
			autoLoad:false,
			groupField: 'subtype_name',
			proxy:{
				type:'ajax',
				actionMethods:{read:'POST'},
				url:Ext.ContextPath+'/inStore/queryList.do',
				reader:{
					type:'json',
					root:'root'
				}
			}
	  });

      me.initAction();
      me.callParent();
      this.groupingFeature = this.view.getFeature('restaurantGrouping');
	},
	initAction:function(){
		var me=this;
//		me.tbar=[{
//			text: '取消分组',
//			
//			handler: function(btn){
//				var grid=btn.up("grid");
//				grid.groupingFeature.disable();
//				//grid.groupBy_brand_name();
//			}
//		},{
//			text: '按小类分组',
//			
//			handler: function(btn){
//				var grid=btn.up("grid");
//				grid.groupingFeature.enable();
//				grid.groupBy_subtype_name();
//			}
//		},{
//			text: '按品名分组',
//			
//			handler: function(btn){
//				var grid=btn.up("grid");
//				grid.groupingFeature.enable();
//				grid.groupBy_prod_name();
//			}
//		},{
//			text: '按供应商分组',
//			
//			handler: function(btn){
//				var grid=btn.up("grid");
//				grid.groupingFeature.enable();
//				grid.groupBy_supplier_name();
//			}
//		},{
//			text: '按型号分组',
//			
//			handler: function(btn){
//				var grid=btn.up("grid");
//				grid.groupingFeature.enable();
//				grid.groupBy_style();
//			}
//		},{
//			text: '按品牌分组',
//			
//			handler: function(btn){
//				var grid=btn.up("grid");
//				grid.groupingFeature.enable();
//				grid.groupBy_brand_name();
//			}
//		}]
	},
	groupBy_brand_name:function(){
		var params=this.getStore().getProxy().extraParams;
		var store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			model: 'Ems.store.InStoreList',
			autoLoad:false,
			groupField: 'brand_name',
			proxy:{
				type:'ajax',
				actionMethods:{read:'POST'},
				url:Ext.ContextPath+'/inStore/queryList.do',
				reader:{
					type:'json',
					root:'root'
				}
			}
	  });
	  store.getProxy().extraParams=params;
	  store.reload();
	  
	  this.reconfigure(store);
	  this.updateLayout();
	},
	groupBy_subtype_name:function(){
		var params=this.getStore().getProxy().extraParams;
		var store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			model: 'Ems.store.InStoreList',
			autoLoad:false,
			groupField: 'subtype_name',
			proxy:{
				type:'ajax',
				actionMethods:{read:'POST'},
				url:Ext.ContextPath+'/inStore/queryList.do',
				reader:{
					type:'json',
					root:'root'
				}
			}
	  });
	  store.getProxy().extraParams=params;
	  store.reload();
	  
	  this.reconfigure(store);
	  this.updateLayout();
	},
	groupBy_prod_name:function(){
		var params=this.getStore().getProxy().extraParams;
		var store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			model: 'Ems.store.InStoreList',
			autoLoad:false,
			groupField: 'prod_name',
			proxy:{
				type:'ajax',
				actionMethods:{read:'POST'},
				url:Ext.ContextPath+'/inStore/queryList.do',
				reader:{
					type:'json',
					root:'root'
				}
			}
	  });
	  store.getProxy().extraParams=params;
	  store.reload();
	  
	  this.reconfigure(store);
	  this.updateLayout();
	},
	groupBy_supplier_name:function(){
		var params=this.getStore().getProxy().extraParams;
		var store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			model: 'Ems.store.InStoreList',
			autoLoad:false,
			groupField: 'supplier_name',
			proxy:{
				type:'ajax',
				actionMethods:{read:'POST'},
				url:Ext.ContextPath+'/inStore/queryList.do',
				reader:{
					type:'json',
					root:'root'
				}
			}
	  });
	  store.getProxy().extraParams=params;
	  store.reload();
	  
	  this.reconfigure(store);
	  this.updateLayout();
	},
	groupBy_style:function(){
		var params=this.getStore().getProxy().extraParams;
		var store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			model: 'Ems.store.InStoreList',
			autoLoad:false,
			groupField: 'style',
			proxy:{
				type:'ajax',
				actionMethods:{read:'POST'},
				url:Ext.ContextPath+'/inStore/queryList.do',
				reader:{
					type:'json',
					root:'root'
				}
			}
	  });
	  store.getProxy().extraParams=params;
	  store.reload();
	  
	  this.reconfigure(store);
	  this.updateLayout();
	}
});
