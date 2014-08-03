/**
 * 作业单位的手持设备情况
 */
Ext.define('Ems.store.StoreEquipmentWindow',{
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
	    store_combox.setRawValue(me.store_name);
	    store_combox.setValue(me.store_id);
	    
		
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
					level:1
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
	    	          {header: '设备类型', dataIndex: 'subtype_name',width:120},
	    	          {header: '品名', dataIndex: 'prod_name'},
	    	          {header: '数量', dataIndex: 'num',width:70,renderer:function(value){
	    	          	return "<a href='javascript:void(0);'>"+value+"</a>"
	    	          }},
	    	          {header: '设备型号', dataIndex: 'style',width:120,hidden:true},
	    	          {header: '品牌', dataIndex: 'brand_name',width:120,hidden:true},
	    	          {header: '供应商', dataIndex: 'supplier_name',hidden:true},     
	    	          {header: '单价(元)', dataIndex: 'unitPrice',width:70,hidden:true},
	    	          {header: '状态', dataIndex: 'status',width:60,hidden:true,renderer:function(value){
	    	          	if(value==0){
	    	          		return '<font color="red">未入库</font>';
	    	          	} else if(value==1){
	    	          		return "已入库";
	    	          	} else if(value==2){
	    	          		return "正常出库(等待安装)";
	    	          	} else if(value==4){
	    	          		return "损坏";
	    	          	} else if(value==9){
	    	          		return "维修后出库";
	    	          	} else {
	    	          		return "";
	    	          	}
	    	          }}
	    	]
		});
		equip_grid.on('cellclick',function(grid, td, cellIndex, record, tr, rowIndex, e){
			alert(cellIndex);
			equip_store.load({params:{
					store_id:store_combox.getValue(),
					subtype_id:subtype_combox.getValue(),
					prod_id:prod_combox.getValue(),
					brand_id:brand_combox.getValue(),
					supplier_id:supplier_combox.getValue(),
					level:2
			}});
		});
		
		me.items=[equip_grid];
		me.callParent();	
	}
	
});