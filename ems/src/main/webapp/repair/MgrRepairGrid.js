Ext.define('Ems.repair.MgrRepairGrid',{
	extend:'Ext.grid.Panel',
	requires: [
	     'Ems.repair.Repair'
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
	pageSize:50,
	selModel:new Ext.selection.CheckboxModel({
		checkOnly:true,
		showHeaderCheckbox:false,//防止点全选，去选择
		renderer: function(value, metaData, record, rowIndex, colIndex, store, view) {
			if(record.get("status")==3){
				var baseCSSPrefix = Ext.baseCSSPrefix;
		        metaData.tdCls = baseCSSPrefix + 'grid-cell-special ' + baseCSSPrefix + 'grid-cell-row-checker';
		        return '<div class="' + baseCSSPrefix + 'grid-row-checker">&#160;</div>';
			} else {
				return "";
			}
	        
	    }
	}),
	initComponent: function () {
      var me = this;
      me.columns=[
		{dataIndex:'id',text:'维修单号',width:120},
		{dataIndex:'ecode',text:'条码',width:140},
		{dataIndex:'str_out_name',text:'仓库'},
		{dataIndex:'rpa_type_name',text:'维修类型',width:60},
		{dataIndex:'status_name',text:'状态'},
		{dataIndex:'str_out_date',text:'出仓时间',xtype: 'datecolumn',   format:'Y-m-d'},
		{dataIndex:'rpa_name',text:'维修中心'},
		{dataIndex:'rpa_in_date',text:'入维时间',xtype: 'datecolumn',   format:'Y-m-d'},
		//{dataIndex:'rpa_in_oper_id',text:'rpa_in_oper_id'},
		{dataIndex:'rpa_out_date',text:'出维时间',xtype: 'datecolumn',   format:'Y-m-d'},
		//{dataIndex:'rpa_out_oper_id',text:'rpa_out_oper_id'},
		{dataIndex:'str_in_date',text:'入仓时间',xtype: 'datecolumn',   format:'Y-m-d'}
		//{dataIndex:'str_in_id',text:'str_in_id'},
		//{dataIndex:'str_in_oper_id',text:'str_in_oper_id'},
		//{dataIndex:'str_out_oper_id',text:'str_out_oper_id'}
      ];
      
	  me.store=Ext.create('Ext.data.Store',{
	  		
			autoSync:false,
			pageSize:me.pageSize,
			model: 'Ems.repair.Repair',
			autoLoad:false,
			proxy:{
				actionMethods:{read:'POST'},
				url:Ext.ContextPath+'/repair/storeQuery.do',
				type:'ajax',
				reader:{
					type:'json',
					root:'root'
				}
			}
	  });
	  //me.store.load({params:{start:0,limit:me.pageSize}});
	  
      me.dockedItems= [{
	        xtype: 'pagingtoolbar',
	        store: me.store,  
	        dock: 'bottom',
	        displayInfo: true
	  }];
	  
	  
	  var store_combox=Ext.create('Ext.form.field.ComboBox',{
	        fieldLabel: '<b>仓库</b>',
	        labelAlign:'right',
            labelWidth:40,
	        //xtype:'combobox',
	        //afterLabelTextTpl: Ext.required,
	        name: 'store_id',
		    displayField: 'name',
		    valueField: 'id',
	        allowBlank: false,
	        store:Ext.create('Ext.data.Store', {
		    	fields: ['id', 'name'],
			    proxy:{
			    	type:'ajax',
			    	extraParams:{type:1},
			    	url:Ext.ContextPath+"/store/queryCombo.do",
			    	reader:{
			    		type:'json',
			    		root:'root'
			    	}
			    }
		   })
	  }); 
	  
	  var repair_combox=Ext.create('Ext.form.field.ComboBox',{
	        fieldLabel: '<b>维修中心</b>',
	        labelAlign:'right',
            labelWidth:60,
	        //xtype:'combobox',
	        //afterLabelTextTpl: Ext.required,
	        name: 'rpa_id',
		    displayField: 'name',
		    valueField: 'id',
	        allowBlank: false,
	        store:Ext.create('Ext.data.Store', {
		    	fields: ['id', 'name'],
			    proxy:{
			    	type:'ajax',
			    	extraParams:{type:2},
			    	url:Ext.ContextPath+"/store/queryCombo.do",
			    	reader:{
			    		type:'json',
			    		root:'root'
			    	}
			    }
		   })
	  }); 
	  
	  var str_out_date_start=Ext.create('Ext.form.field.Date',{
	  	fieldLabel: '入库时间范围',
	  	labelWidth:70,
	  	format:'Y-m-d',
        //name: 'str_out_date_start',
        value:  Ext.Date.add(new Date(), Ext.Date.DAY, -7)
	  });
	  var str_out_date_end=Ext.create('Ext.form.field.Date',{
	  	fieldLabel: '到',
	  	format:'Y-m-d',
	  	labelWidth:15,
        //name: 'str_out_date_end',
        value: new Date()
	  });
	  
//	  var container_repair_over=Ext.create('Ext.form.field.Checkbox',{
//	  	labelWidth:60,
//	  	fieldLabel: '包含完成',
//	  	checked:false
//	  });
	  var status_combo=Ext.create('Ext.form.field.ComboBox',{
	        fieldLabel: '状态',
	        labelAlign:'right',
            labelWidth:30,
	        //xtype:'combobox',
	        //afterLabelTextTpl: Ext.required,
	        name: 'status',
		    displayField: 'name',
		    valueField: 'id',
	        allowBlank: false,
	        store:Ext.create('Ext.data.Store', {
		    	fields: ['id', 'name'],
			    data:[{id:"1",name:"发往维修中心"},{id:"2",name:"维修中"},{id:"3",name:"返库途中"},{id:"4",name:"完成"}]
		   })
	  }); 
	  
	  var query_button=Ext.create("Ext.button.Button",{
			text:'查询',
			margin:'0 0 0 5',
			iconCls:'form-search-button',
			handler:function(){
				me.store.load({params:{
					str_out_id:store_combox.getValue(),
					rpa_id:repair_combox.getValue(),
					str_out_date_start: Ext.Date.format(new Date(str_out_date_start.getValue()),'Y-m-d'),
					str_out_date_end: Ext.Date.format(new Date(str_out_date_end.getValue()),'Y-m-d'),
					status:status_combo.getValue()
				}
			  });
			}
	  });
	  me.store.load({params:{
					str_out_id:store_combox.getValue(),
					rpa_id:repair_combox.getValue(),
					str_out_date_start: Ext.Date.format(new Date(str_out_date_start.getValue()),'Y-m-d'),
					str_out_date_end: Ext.Date.format(new Date(str_out_date_end.getValue()),'Y-m-d'),
					status:status_combo.getValue()
				}
	 });
	  
	  //入库按钮
	  var str_in_button=Ext.create("Ext.button.Button",{
			text:'入库',
			margin:'0 0 0 5',
			icon:Ext.ContextPath+"/icons/database_copy.png",
			handler:function(){
				
			}
	  });
	  me.tbar={
		xtype: 'container',
		layout: 'anchor',
		defaults: {anchor: '0'},
		defaultType: 'toolbar',
		items: [{
			items: [store_combox,repair_combox,str_out_date_start,str_out_date_end,status_combo,query_button] // toolbar 1
		}, {
			items: [str_in_button] // toolbar 2
		}]
	}	
	  
//	  [{
//			text: '查询',
//			itemId:'reload',
//			disabled:me.disabledAction,
//			handler: function(btn){
//				var grid=btn.up("grid");
//				grid.getStore().reload();
//			},
//			iconCls: 'form-reload-button'
//		}]
       
      me.callParent();
	}
});
