/**
 * 作业单位的手持设备情况
 */
Ext.define('Ems.install.WorkUnitEquipmentWindow',{
	extend:'Ext.window.Window',
	requires: [
	     'Ems.baseinfo.Equipment'
	],
	layout:'fit',
	workUnit_id:null,
	initComponent: function () {
		var me=this;
		var equip_store=Ext.create('Ext.data.Store',{
			//fields:[],
			autoLoad:false,
			model:'Ems.baseinfo.Equipment',
			proxy:{
				type:'ajax',
				url:Ext.ContextPath+'/workUnit/queryEquipments.do',
				reader:{
					type:'json',
					root:'root'
				}
			}
		});
		//equip_store.load({params:{workUnit_id:me.workUnit_id}});
	/**	
		var equip_grid=Ext.create('Ext.grid.Panel',{
			flex:1,
			store:equip_store,
	    	columns: [Ext.create('Ext.grid.RowNumberer'),
	    			{header: '条码', dataIndex: 'ecode',width:150},
	    	          {header: '设备类型', dataIndex: 'subtype_name',width:120},
	    	          {header: '品名', dataIndex: 'prod_name'},
	    	          {header: '供应商', dataIndex: 'supplier_name'},
	    	          {header: '品牌', dataIndex: 'brand_name',width:120},
	    	          
	    	          {header: '设备型号', dataIndex: 'style',width:120},
	    	          //{header: '仓库', dataIndex: 'store_name'},
	    	          //{header: '数量', dataIndex: 'serialNum',width:70},
	    	          //{header: 'stid', dataIndex: 'stid',hideable:false,hidden:true},
	    	         // {header: '库房', dataIndex: 'stock',width:120},
	    	          {header: '状态', dataIndex: 'status',width:60,renderer:function(value){
	    	          	  if(value==4 || value==5){
		    	          	return '<font color="red">'+equipmentStatus[value]+'</font>';
		    	          } else {
		    	          		return equipmentStatus[value];
		    	          } 
	    	          }}
	    	]
		});
		**/
		
		var brand_combox=Ext.create('Ems.baseinfo.BrandCombo',{
			labelAlign:'right',
			labelWidth:40,
			editable:false,
			containAll:true,
			minChars:-1
		});
		var supplier_combox=Ext.create('Ems.baseinfo.SupplierCombo',{
			labelAlign:'right',
			labelWidth:40,
			editable:true,
			width:260,
			//containAll:true,
			minChars:2
		});
		equip_store.on("beforeload",function(store){
			store.getProxy().extraParams={
				workUnit_id:me.workUnit_id,
				subtype_id:subtype_id,//subtype_combox.getValue(),
				prod_id:prod_id,//mbox.getValue(),
				style:style,
				brand_id:brand_combox.getValue(),
				supplier_id:supplier_combox.getValue(),
				level:level
			};
		});
		var level=1;
		var subtype_id=null;
		var prod_id=null;
		var style=null;
		var button=Ext.create("Ext.button.Button",{
			text:'查询',
			margin:'0 0 0 5',
			iconCls:'form-search-button',
			handler:function(){
				equip_store.load();
			}
		});
		equip_store.load();
		
		var equip_grid=Ext.create('Ext.grid.Panel',{
			//flex:1,
//			tbar:{
//			  xtype: 'container',
//			  layout: 'anchor',
//			  defaults: {anchor: '0'},
//			  defaultType: 'toolbar',
//			  items: [{
//			    items: [store_combox,subtype_combox,prod_combox] // toolbar 1
//			  }, {
//			    items: [brand_combox,supplier_combox,button] // toolbar 2
//			  }]
//			},
			tbar:{
			    items: [brand_combox,supplier_combox,button] // toolbar 1
			  },
			store:equip_store,
	    	columns: [Ext.create('Ext.grid.RowNumberer'),
	    	          {header: '设备类型', dataIndex: 'subtype_name',width:120,renderer:function(value,metaData,record,rowIndex){
	    	          	if(record.get("subtype_id")=="total"){
	    	          		return "<a href='javascript:void(0);'>"+value+"</a>";
	    	          	} else {
	    	          		return value;
	    	          	}
	    	          }},
	    	          {header: '品名', dataIndex: 'prod_name',flex:1},
	    	          {header: '设备型号', dataIndex: 'style',width:120,hidden:true},
	    	          {dataIndex:'prod_spec',text:'规格',flex:1,renderer:function(value,metadata,record){
						metadata.tdAttr = "data-qtip='" + value+ "'";
					    return value;
						}
					  },
	    	          {header: '条码', dataIndex: 'ecode',width:120,hidden:true},
	    	          {header: '品牌', dataIndex: 'brand_name',width:120,hidden:true},
	    	          {header: '供应商', dataIndex: 'supplier_name',hidden:true},     
	    	         // {header: '状态', dataIndex: 'status_name',width:100,hidden:true},
	    	          {header: '数量', dataIndex: 'num',width:70,renderer:function(value,metaData,record,rowIndex){
	    	          	if(level==3 || record.get("subtype_id")=="total"){
	    	          		return value;
	    	          	} else {
	    	          		return "<a href='javascript:void(0);'>"+value+"</a>";
	    	          	}
	    	          	
	    	          }}
	    	],
	    	bbar:[{
		        xtype: 'pagingtoolbar',
		        store: equip_store,  
		        //dock: 'bottom',
		        displayInfo: true
		  }]
		});
		equip_grid.on('cellclick',function(grid, td, cellIndex, record, tr, rowIndex, e){
			//alert(equip_grid.columns );
			var columns=equip_grid.columns;
			//alert(columns[cellIndex].dataIndex);
			//console.log(level);
			
			var dataIndex=equip_grid.headerCt.getHeaderAtIndex(cellIndex).dataIndex;
			//console.log(dataIndex);
			if(level==1&&dataIndex=="num"){
				level=2;
				subtype_id=record.get("subtype_id");
				//var subtype_model=Ext.createModel(subtype_combox.getStore().model.getName( ),{id:record.get("subtype_id"),text:record.get("subtype_name")});
	    		//subtype_combox.setValue(subtype_model);

				equip_store.load({
					callback:function(records, operation, success){			
						for(var i=0;i<columns.length;i++){
							//console.log(columns[i].dataIndex);
							if(columns[i].dataIndex=="num" || columns[i].dataIndex=="ecode"){
								//columns[i].hide();
							} else{
								columns[i].show();
							}
						}
					}
				});
			} else if(level==2&&dataIndex=="num"){
				level=3;
				prod_id=record.get("prod_id");
				style=record.get("style");
				equip_store.load({
				callback:function(records, operation, success){
					
					for(var i=0;i<columns.length;i++){
						//console.log(columns[i].dataIndex);
						if(columns[i].dataIndex=="num"){
							//columns[i].hide();
						} else{
							columns[i].show();
						}
					}
				}});
				
			}

			//----点击合计回退到前面的时候
			if(level==2&&equip_store.getAt(rowIndex).get("subtype_id")=="total"&&dataIndex=="subtype_name"){
				level=1;
				//subtype_combox.clearValue( );
				subtype_id=null;
				equip_store.load({
				callback:function(records, operation, success){
					
					for(var i=0;i<columns.length;i++){
						//console.log(columns[i].dataIndex);
						var dataIndex=columns[i].dataIndex;
						if(dataIndex=="num" ||dataIndex=="subtype_name" || dataIndex=="prod_name"){
							//columns[i].hide();
						} else{
							columns[i].hide();
						}
					}
				}});
			}
			if(level==3&&equip_store.getAt(rowIndex).get("subtype_id")=="total"&&dataIndex=="subtype_name"){
				level=2;
				//subtype_combox.clearValue( );
				prod_id=null;
				style=null;
				equip_store.load({
				callback:function(records, operation, success){
					
					for(var i=0;i<columns.length;i++){
						//console.log(columns[i].dataIndex);
						var dataIndex=columns[i].dataIndex;
						if(dataIndex=="num" ||dataIndex=="subtype_name" || dataIndex=="prod_name"){
							//columns[i].hide();
						} else{
							columns[i].hide();
						}
					}
				}});
			}


		});
		
		me.items=[equip_grid];
		me.callParent();	
	}
	
});