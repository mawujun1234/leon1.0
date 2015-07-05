Ext.define('Ems.adjust.AdjustGrid',{
	extend:'Ext.grid.Panel',
	requires: [
	     'Ems.adjust.Adjust'
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
		{dataIndex:'id',text:'单号'},
		{dataIndex:'status_name',text:'状态',width:60},
		{dataIndex:'adjustType_name',text:'调拨类型'},
		{dataIndex: 'str_out_name',text: '出库仓库'},
    	{dataIndex: 'str_in_name',text: '入库仓库'},
    	{dataIndex:'str_out_date',text:'出库时间',xtype: 'datecolumn',   format:'Y-m-d',width:80},
    	{dataIndex:'memo',text:'备注',flex:1}
      ];
      
	  me.store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			model: 'Ems.adjust.Adjust',
			autoLoad:false
//			proxy:{
//				type:'ajax',
//				actionMethods:{read:'POST'},
//				url:Ext.ContextPath+'/adjust/query4InStr.do',
//				reader:{
//					type:'json',
//					root:'root'
//				}
//			}
	  });
	  
      me.dockedItems= [{
	        xtype: 'pagingtoolbar',
	        store: me.store,  
	        dock: 'bottom',
	        displayInfo: true
	  }];
	  
	  var out_store_combox=Ext.create('Ext.form.field.ComboBox',{
	        fieldLabel: '<b>发货仓库</b>',
	        labelAlign:'right',
            labelWidth:60,
	        //xtype:'combobox',
	        //afterLabelTextTpl: Ext.required,
	        name: 'store_id',
		    displayField: 'name',
		    valueField: 'id',
	        //allowBlank: false,
	        store:Ext.create('Ext.data.Store', {
		    	fields: ['id', 'name'],
			    proxy:{
			    	type:'ajax',
			    	extraParams:{type:[1,3],look:true},
			    	url:Ext.ContextPath+"/store/queryCombo.do",
			    	reader:{
			    		type:'json',
			    		root:'root'
			    	}
			    }
		   })
	  }); 
	  
	  var in_store_combox=Ext.create('Ext.form.field.ComboBox',{
	        fieldLabel: '<b>入库仓库</b>',
	        labelAlign:'right',
            labelWidth:60,
	        //xtype:'combobox',
	        //afterLabelTextTpl: Ext.required,
	        name: 'rpa_id',
		    displayField: 'name',
		    valueField: 'id',
	        //allowBlank: false,
	        store:Ext.create('Ext.data.Store', {
		    	fields: ['id', 'name'],
			    proxy:{
			    	type:'ajax',
			    	extraParams:{type:[1,3],look:true},
			    	url:Ext.ContextPath+"/store/queryCombo.do",
			    	reader:{
			    		type:'json',
			    		root:'root'
			    	}
			    }
		   })
	  }); 
	  
	  var str_out_date_start=Ext.create('Ext.form.field.Date',{
	  	fieldLabel: '出仓时间',
	  	labelWidth:50,
	  	width:150,
	  	format:'Y-m-d',
        //name: 'str_out_date_start',
        value:  Ext.Date.add(new Date(), Ext.Date.DAY, -7)
	  });
	  var str_out_date_end=Ext.create('Ext.form.field.Date',{
	  	fieldLabel: '到',
	  	format:'Y-m-d',
	  	labelWidth:15,
	  	labelWidth:15,
        //name: 'str_out_date_end',
        value: new Date()
	  });
	  
	 
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
			    data:[{id:"",name:"所有"},{id:"carry",name:"在途"},{id:"noallin",name:"未全入"},{id:"noreturn",name:"未归还"},{id:"partreturn",name:"部分归还"},{id:"over",name:"完成"}]//,{id:"edit",name:"编辑中"}
		   })
	  }); 
	  
	 me.store.on("beforeload",function(store){
		store.getProxy().extraParams={
					str_out_id:out_store_combox.getValue(),
					str_in_id:in_store_combox.getValue(),
					str_out_date_start: str_out_date_start.getRawValue(),
					str_out_date_end: str_out_date_end.getRawValue(),
					status:status_combo.getValue()
				  };
	});
	
	//将调拨单的类型从借用单转换成领用单
	//已经入过库的不能转换，不是出库仓库的不能转
	var chang2installout=Ext.create('Ext.button.Button',{
		text:'借转领',
		handler:function(){
			var record=me.getSelectionModel().getLastSelected();
			if(!record){
				return;
			}
			
			Ext.Msg.confirm("消息","确定要把该借用类型调拨单，转换为领用类型吗?",function(btn){
				if(btn=='yes'){
				Ext.getBody().mask("正在执行....");
				Ext.Ajax.request({
					url:Ext.ContextPath+'/adjust/change2installout.do',
					method:'POST',
					params:{adjust_id:record.get("id")},
					success:function(response){
						me.store.reload();
						Ext.getBody().unmask();
					},
					failure:function(){
						Ext.getBody().unmask();
					}
				
				});
				}
			});
			
		}
	});
	  me.tbar={
		xtype: 'container',
		layout: 'anchor',
		defaults: {anchor: '0'},
		defaultType: 'toolbar',
		items: [{
			items: [out_store_combox,in_store_combox] // toolbar 1
		}, {
			items: [str_out_date_start,str_out_date_end] // toolbar 2
		}, {
			items: [status_combo,{
			text: '查询',
			iconCls:'form-search-button',
			handler: function(btn){
				//me.store.reload();
				me.store.loadPage(1);
			}
		  },chang2installout] 
		}]
	   };
	   me.store.load();

       
      me.callParent();
	}
});
