Ext.define('Ems.store.OrderGrid',{
	extend:'Ext.grid.Panel',
	requires: [
	     'Ems.store.Order'
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
		{dataIndex:'orderNo',text:'订单号',width:150},
		{dataIndex:'store_name',text:'入库仓库',flex:1},
		{dataIndex: 'status_name',text: '状态',width:70},
		{dataIndex:'orderDate',text:'订购日期',xtype: 'datecolumn',   format:'Y-m-d',width:80}
    	//{dataIndex: 'operater',text: '经办人'}
      ];
      
	  me.store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			model: 'Ems.store.Order',
			autoLoad:false,
			proxy:{
				type:'ajax',
				actionMethods:{read:'POST'},
				url:Ext.ContextPath+'/order/queryMain.do',
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
	  
	  var store_combox=Ext.create('Ext.form.field.ComboBox',{
	        fieldLabel: '入库仓库',
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
	  
	  
	  var date_start=Ext.create('Ext.form.field.Date',{
	  	fieldLabel: '订购日期',
	  	labelWidth:60,
	  	width:160,
	  	format:'Y-m-d',
        //name: 'date_start',
        value:  Ext.Date.add(new Date(), Ext.Date.DAY, -7)
	  });
	  var date_end=Ext.create('Ext.form.field.Date',{
	  	fieldLabel: '到',
	  	format:'Y-m-d',
	  	labelWidth:15,
	  	labelWidth:15,
        //name: 'date_end',
        value: new Date()
	  });
	  
	 var order_no=Ext.create('Ext.form.field.Text',{
		fieldLabel:'订单号',
		name:'orderNo',
		labelWidth:50,
		allowBlank:false,
		labelAlign:'right'
	});
	  
	me.store.on("beforeload",function(store){
		store.getProxy().extraParams={
					store_id:store_combox.getValue(),
					date_start: date_start.getRawValue(),
					date_end: date_end.getRawValue(),
					orderNo:order_no.getValue()
				  };
	});
	  me.tbar={
		xtype: 'container',
		layout: 'anchor',
		defaults: {anchor: '0'},
		defaultType: 'toolbar',
		items: [{
			items: [store_combox] // toolbar 1
		}, {
			items: [date_start,date_end] // toolbar 2
		}, {
			items: [order_no,{
			text: '查询',
			iconCls:'form-search-button',
			handler: function(btn){
				me.store.reload();
			}
		  },{
			text: '确认',
			icon:'../icons/cog.png',
			//iconCls:'form-search-button',
			handler: function(btn){
				Ext.Msg.confirm("提醒","确认后,该订单将不可再修改!",function(btn){
					if(btn=='yes'){
						var record=me.getSelectionModel().getLastSelected();
						Ext.Ajax.request({
							url:Ext.ContextPath+'/order/editover.do',
							method:'POST',
							params:{id:record.get("id")},
							success:function(resposne){
								me.store.reload();
							}
						});
					
					}
				});
			}
		  },{
			text: '删除',
			//iconCls:'form-search-button',
			icon:'../icons/action_delete.gif',
			handler: function(btn){
				Ext.Msg.confirm("提醒","确认要删除吗?",function(btn){
					if(btn=='yes'){
						var record=me.getSelectionModel().getLastSelected();
						if(record.get("status")=='editover'){
							alert("状态是'已确认',不能删除！");
							return;
						}
						Ext.Ajax.request({
							url:Ext.ContextPath+'/order/delete.do',
							method:'POST',
							params:{id:record.get("id")},
							success:function(resposne){
								me.store.reload();
							}
						});
					
					}
				});
			}
		  }] 
		}]
	   };
	   me.store.load();


       
      me.callParent();
	}
});
