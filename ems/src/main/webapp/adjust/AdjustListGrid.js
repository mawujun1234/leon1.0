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
	selModel:new Ext.selection.CheckboxModel({
		//checkOnly:true,
		showHeaderCheckbox:true,//防止点全选，去选择
		renderer: function(value, metaData, record, rowIndex, colIndex, store, view) {
			if(record.get("status")==false ){
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
		//{dataIndex:'id',text:'id'},
		//{dataIndex:'adjust_id',text:'adjust_id'},
		{dataIndex:'ecode',text:'条码',width:140},
		//{dataIndex:'in_num',text:'in_num',xtype: 'numbercolumn', format:'0.00'},
		//{dataIndex:'out_num',text:'out_num',xtype: 'numbercolumn', format:'0.00'},
		{header: '设备类型', dataIndex: 'subtype_name',width:120},
    	{header: '品名', dataIndex: 'prod_name'},
    	{header: '品牌', dataIndex: 'brand_name',width:120},
    	{header: '供应商', dataIndex: 'supplier_name'},
    	{header: '设备型号', dataIndex: 'equipment_style',width:120}
      ];
      
	  me.store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			model: 'Ems.adjust.AdjustList',
			autoLoad:false,
			proxy:{
				type:'ajax',
				actionMethods:{read:'POST'},
				url:Ext.ContextPath+'/adjust/query4InStrList.do',
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
	  
	  var ecode_textfield=Ext.create('Ext.form.field.Text',{
		labelAlign:'right',
		name:'encode',
		fieldLabel: '扫码选择',
		minLength:Ext.ecode_length,
		maxLength:Ext.ecode_length,
		length:Ext.ecode_length,
		selectOnFocus:true,
		labelWidth:80,
		width:250,
		allowBlank:false,
		listeners:{
			change:function(textfield, newValue, oldValue){
				if(newValue.length<Ext.ecode_length){
					return;
				}
				//var flag=true;
				me.store.each(function(record){
					//flag=true;
					if(record.get("ecode")==newValue){
						//flag=false;
						me.getSelectionModel( ).select(record,true);
						return;
					} 
				});
				
//				if(flag){
//					Ext.Msg.alert("消息","该设备状态不是'发往维修中心'和'维修中'或者该设备在下一页,请注意");	
//				}
				ecode_textfield.setValue("");
				ecode_textfield.clearInvalid( );
				ecode_textfield.focus();
			}
		}
	});
	var clear_button=Ext.create('Ext.button.Button',{
		text:'清除',
		margin:'0 0 0 5',
		handler:function(){
			ecode_textfield.setValue('');
		}
	});
	
	var part_inStore_button=Ext.create('Ext.button.Button',{
		text:'部分先入库',
		margin:'0 0 0 5',
		handler:function(){
			
			var records=me.getSelectionModel( ).getSelection( );
			var adjustes=[];
			for(var i=0;i<records.length;i++){
				adjustes.push(records[i].getData());
			}
			//不会修改调拨单状态
			Ext.getBody().mask("正在执行,请稍候.....");
			Ext.Ajax.request({
				url:Ext.ContextPath+'/adjust/partInStr.do',
				method:'POST',
				timeout:600000000,
				//headers:{ 'Content-Type':'application/json;charset=UTF-8'},
				params:{str_in_id:me.str_in_id},
				jsonData:adjustes,
				//params:{jsonStr:Ext.encode(equiplist)},
				success:function(response){
					var obj=Ext.decode(response.responseText);		
							//Ext.Msg.alert("消息","维修中心入库完成!");
					Ext.getBody().unmask();
					me.getStore().reload();
				},
				failure:function(){
					Ext.getBody().unmask();
				}
			});
		}
	});
	var all_inStore_button=Ext.create('Ext.button.Button',{
		text:'全部入库',
		margin:'0 0 0 5',
		handler:function(){
			//如果发现要入库的设备和实际的设备数量不一致的时候，给出提醒，把未入库的当做是设别丢失了，修改设备状态为丢失，损耗
		}
	});
	me.tbar=	[{
			text: '刷新',
			itemId:'reload',
			disabled:me.disabledAction,
			handler: function(btn){
				var grid=btn.up("grid");
				grid.getStore().reload();
			},
			iconCls: 'form-reload-button'
		},ecode_textfield,clear_button,part_inStore_button,all_inStore_button]
       
      me.callParent();
	}
});
