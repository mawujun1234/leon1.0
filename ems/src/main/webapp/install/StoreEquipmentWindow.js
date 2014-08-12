/**
 * 作业单位的手持设备情况
 */
Ext.define('Ems.install.StoreEquipmentWindow',{
	extend:'Ext.window.Window',
	requires: [
	     'Ems.baseinfo.Equipment'
	],
	layout:'fit',
	store_id:null,
	store_name:null,
	initComponent: function () {
		var me=this;
		var equip_store=Ext.create('Ext.data.Store',{
			//fields:[],
			autoLoad:false,
			model:'Ems.baseinfo.Equipment',
			proxy:{
				type:'ajax',
				url:Ext.ContextPath+'/store/queryEquipments.do',
				reader:{
					type:'json',
					root:'root'
				}
			}
		});
		
		
		var store_combox=Ext.create('Ext.form.field.ComboBox',{
	        fieldLabel: '<b>仓库</b>',
	        labelAlign:'right',
            labelWidth:40,
	        //xtype:'combobox',
	        //afterLabelTextTpl: Ext.required,
	        name: 'store_id',
		    displayField: 'name',
		    valueField: 'id',
		    //queryParam: 'name',
    		//queryMode: 'remote',
    		//triggerAction: 'query',
    		//minChars:-1,
		    //trigger1Cls: Ext.baseCSSPrefix + 'form-clear-trigger',
		    //trigger2Cls: Ext.baseCSSPrefix + 'form-arrow-trigger',//'form-search-trigger',
			//onTrigger1Click : function(){
			//    var me = this;
			//    me.setValue('');
			//},
	        allowBlank: false,
	        store:Ext.create('Ext.data.Store', {
		    	fields: ['id', 'name'],
			    proxy:{
			    	type:'ajax',
			    	url:Ext.ContextPath+"/store/queryCombo.do",
			    	reader:{
			    		type:'json',
			    		root:'root'
			    	}
			    }
		   })
	    }); 
	    var store_model=Ext.createModel(store_combox.getStore().model.getName( ),{id:me.store_id,name:me.store_name});
	    store_combox.setValue(store_model);
	    
		
	    var subtype_combox=Ext.create('Ems.baseinfo.SubtypeCombo',{
			labelAlign:'right',
			labelWidth:40,
			minChars:-1//表示默认点击的时候就查询出所有的数据
		});
		var prod_combox=Ext.create('Ems.baseinfo.ProdCombo',{
			labelAlign:'right',
			labelWidth:40,
			minChars:-1
		});
		var brand_combox=Ext.create('Ems.baseinfo.BrandCombo',{
			labelAlign:'right',
			labelWidth:40,
			minChars:-1
		});
		var supplier_combox=Ext.create('Ems.baseinfo.SupplierCombo',{
			labelAlign:'right',
			labelWidth:40,
			minChars:-1
		});
		var level=1;
		var button=Ext.create("Ext.button.Button",{
			text:'查询',
			margin:'0 0 0 5',
			iconCls:'form-search-button',
			handler:function(){
				equip_store.load({params:{
					store_id:store_combox.getValue(),
					subtype_id:subtype_combox.getValue(),
					prod_id:prod_combox.getValue(),
					brand_id:brand_combox.getValue(),
					supplier_id:supplier_combox.getValue(),
					level:level
				}
			  });
			}
		});
		
		var equip_grid=Ext.create('Ext.grid.Panel',{
			//flex:1,
			tbar:{
			  xtype: 'container',
			  layout: 'anchor',
			  defaults: {anchor: '0'},
			  defaultType: 'toolbar',
			  items: [{
			    items: [store_combox,subtype_combox,prod_combox] // toolbar 1
			  }, {
			    items: [brand_combox,supplier_combox,button] // toolbar 2
			  }]
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
	    	          {header: '品名', dataIndex: 'prod_name'},
	    	          
	    	          {header: '设备型号', dataIndex: 'style',width:120,hidden:true},
	    	          {header: '品牌', dataIndex: 'brand_name',width:120,hidden:true},
	    	          {header: '供应商', dataIndex: 'supplier_name',hidden:true},     
	    	          {header: '单价(元)', dataIndex: 'unitPrice',width:70,hidden:true,renderer:function(value,metaData,record,rowIndex){
	    	          	if(record.get("subtype_id")=="total"){
	    	          		return "";
	    	          	}
	    	          }},
	    	          {header: '状态', dataIndex: 'status',width:100,hidden:true,renderer:function(value,metaData,record,rowIndex){
	    	          	if(record.get("subtype_id")=="total"){
	    	          		return "";
	    	          	}

	    	          	if(value==4 || value==5){
	    	          		return '<font color="red">'+equipmentStatus[value]+'</font>';
	    	          	} else {
	    	          		return equipmentStatus[value];
	    	          	} 
	    	          }},
	    	          {header: '数量', dataIndex: 'num',width:70,renderer:function(value,metaData,record,rowIndex){
	    	          	if(level==2){
	    	          		return value;
	    	          	} else {
	    	          		return "<a href='javascript:void(0);'>"+value+"</a>";
	    	          	}
	    	          	
	    	          }}
	    	]
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
				equip_store.load({params:{
						store_id:store_combox.getValue(),
						subtype_id:subtype_combox.getValue(),
						prod_id:prod_combox.getValue()?prod_combox.getValue():record.get("prod_id"),
						brand_id:brand_combox.getValue(),
						supplier_id:supplier_combox.getValue(),
						level:level
				},
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

			if(level==2&&equip_store.getAt(rowIndex).get("subtype_id")=="total"&&dataIndex=="subtype_name"){
				level=1;
				equip_store.load({params:{
						store_id:store_combox.getValue(),
						subtype_id:subtype_combox.getValue(),
						prod_id:prod_combox.getValue(),
						brand_id:brand_combox.getValue(),
						supplier_id:supplier_combox.getValue(),
						level:level
				},
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