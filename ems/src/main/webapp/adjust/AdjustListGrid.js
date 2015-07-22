Ext.define('Ems.adjust.AdjustListGrid',{
	extend:'Ext.grid.Panel',
	requires: [
	     'Ems.adjust.AdjustList'
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
      	Ext.create('Ext.grid.RowNumberer'),
		//{dataIndex:'id',text:'id'},
		//{dataIndex:'adjust_id',text:'adjust_id'},
		{dataIndex:'ecode',text:'条码',width:130},
		{dataIndex:'adjustListStatus_name',text:'状态',width:60},
		{itemId: 'isReturnColumn',dataIndex:'isReturn',hidden:true,text:'是否归还',width:60,renderer:function(value,metadata,record){
			if(value==true){
				return "已归还";
			} else {
				return "否";
			}
		}},
		//{dataIndex:'in_num',text:'in_num',xtype: 'numbercolumn', format:'0.00'},
		//{dataIndex:'out_num',text:'out_num',xtype: 'numbercolumn', format:'0.00'},
		{header: '设备类型', dataIndex: 'subtype_name',width:120},
    	{header: '品名', dataIndex: 'prod_name'},
    	{header: '品牌', dataIndex: 'brand_name',width:120},
    	{header: '供应商', dataIndex: 'supplier_name'},
    	{header: '设备型号', dataIndex: 'prod_style',width:120},
    	{header:'规格',dataIndex:'prod_spec',flex:1,minWidth:100,renderer:function(value,metadata,record){
						metadata.tdAttr = "data-qtip='" + value+ "'";
					    return value;
						}
				  }
    	
      ];
      
	  me.store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			model: 'Ems.adjust.AdjustList',
			autoLoad:false,
			proxy:{
				type:'ajax',
				actionMethods:{read:'POST'},
				url:Ext.ContextPath+'/adjust/queryList.do',
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
//	  
//	  var ecode_textfield=Ext.create('Ext.form.field.Text',{
//		labelAlign:'right',
//		name:'ecode',
//		fieldLabel: '扫码选择',
//		minLength:Ext.ecode_length,
//		maxLength:Ext.ecode_length,
//		length:Ext.ecode_length,
//		selectOnFocus:true,
//		labelWidth:80,
//		width:250,
//		allowBlank:false,
//		listeners:{
//			blur:function(f,e){
//				if(!f.getValue()||f.getValue()==''){
//					f.clearInvalid();
//				}
//			},
//			focus:function(){
//				if(!repair_combox.getValue()){
//					Ext.Msg.alert("消息","请先选择仓库!");
//					return;
//				}
//			},
//			change:equipScan
//		}
//	});
//	var clear_button=Ext.create('Ext.button.Button',{
//		text:'清除',
//		margin:'0 0 0 5',
//		handler:function(){
//			ecode_textfield.setValue('');
//		}
//	});
	me.tbar=	[{
			text: '刷新',
			itemId:'reload',
			disabled:me.disabledAction,
			handler: function(btn){
				if(!me.adjust_id){
					alert("请先选择挑拨单!");
					return;
				}
				var grid=btn.up("grid");
				grid.getStore().reload();
			},
			iconCls: 'form-reload-button'
		}]
       
      me.callParent();
	}
});
