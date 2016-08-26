
Ext.define('Ems.store.OrderForm',{
	extend:'Ext.form.Panel',
	requires: [
	     'Ems.store.Order',
	     'Ems.baseinfo.ProjectQueryGrid'
	],
	fieldDefaults: {
        msgTarget: 'side',
        labelWidth: 75,
        labelAlign:'right',
        anchor: '90%'
    },
    frame: true,
    bodyPadding: '5 5 0',

    layout: {
        type: 'vbox',
        align: 'stretch'  // Child items are stretched to full width
    },
	initComponent: function () {
       var me = this;
     var id_field=Ext.create('Ext.form.field.Hidden',{
		fieldLabel:'id',
		name:'id',
		labelWidth:50,
		allowBlank:false,
		labelAlign:'right'
	});
	var orderType_field=Ext.create('Ext.form.field.Hidden',{
		fieldLabel:'orderType',
		name:'orderType'
	});
	var status=Ext.create('Ext.form.field.Hidden',{
		fieldLabel:'status',
		name:'status',
		labelWidth:50,
		allowBlank:false,
		labelAlign:'right'
	});
//	var createDate=Ext.create('Ext.form.field.Hidden',{
//		fieldLabel:'createDate',
//		name:'createDate',
//		labelWidth:50,
//		allowBlank:false,
//		labelAlign:'right'
//	});
//	var orderDate=Ext.create('Ext.form.field.Hidden',{
//		fieldLabel:'orderDate',
//		name:'orderDate',
//		labelWidth:50,
//		allowBlank:false,
//		labelAlign:'right'
//	});
       var order_no=Ext.create('Ext.form.field.Text',{
			fieldLabel:'订单号',
			name:'orderNo',
			labelWidth:50,
			allowBlank:false,
			labelAlign:'right'
		});
		var store_combox=Ext.create('Ext.form.field.ComboBox',{
		        fieldLabel: '入库仓库',
		        labelAlign:'right',
	            labelWidth:60,
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
				    	extraParams:{type:[1,3],look:true},
				    	url:Ext.ContextPath+"/store/queryCombo.do",
				    	reader:{
				    		type:'json',
				    		root:'root'
				    	}
				    }
			   })
		});	
		
		var orderDate=Ext.create('Ext.form.field.Date',{
			fieldLabel: '订购日期',
			labelWidth:60,
	        name: 'orderDate',
	        format:'Y-m-d'
	       // value: new Date() 
		});
		var operater=Ext.create('Ext.form.field.Hidden',{
			labelAlign:'right',
			labelWidth:55,
			fieldLabel: '经办人',
			name:'operater',
			readOnly:true,
			allowBlank:false
		});
		var operater_name=Ext.create('Ext.form.field.Text',{
			labelAlign:'right',
			labelWidth:55,
			fieldLabel: '经办人',
			name:'operater_name',
			readOnly:true,
			allowBlank:false
		});
		
//	var project_id=Ext.create('Ext.form.field.Hidden',{
//		labelAlign:'right',
//		labelWidth:40,
//		fieldLabel: '项目',
//		name:'project_id',
//		readOnly:true,
//		emptyText:"不可编辑",
//		allowBlank:false
//	});
//	var project_name=Ext.create('Ext.form.field.Text',{
//		labelAlign:'right',
//		labelWidth:40,
//		fieldLabel: '项目',
//		name:'project_name',
//		readOnly:true,
//		emptyText:"不可编辑",
//		allowBlank:false
//	});
//	var project_button=Ext.create('Ext.button.Button',{
//		text:'选择项目',
//		margin:'0 0 0 5',
//		handler:function(){
//			var projectGrid=Ext.create('Ems.baseinfo.ProjectQueryGrid',{
//				listeners:{
//					itemdblclick:function(view,record,item){
//						project_id.setValue(record.get("id"));
//						project_name.setValue(record.get("name"));
//						win.close();
//					}
//				}
//			});
//			var win=Ext.create('Ext.window.Window',{
//				title:'双击选择项目',
//				items:[projectGrid],
//				layout:'fit',
//				modal:true,
//				width:700,
//				height:300
//			});
//			win.show();
//		}
//	});
//		var project_combox=Ext.create('Ext.form.field.ComboBox',{
//	        fieldLabel: '项目',
//	        labelAlign:'right',
//            labelWidth:40,
//            flex:1,
//	        //xtype:'combobox',
//	        //afterLabelTextTpl: Ext.required,
//	        name: 'project_id',
//		    displayField: 'name',
//		    valueField: 'id',
//		    queryParam: 'name',
//    		queryMode: 'remote',
//    		triggerAction: 'query',
//    		minChars:-1,
//		    trigger1Cls: Ext.baseCSSPrefix + 'form-clear-trigger',
//		    trigger2Cls: Ext.baseCSSPrefix + 'form-arrow-trigger',//'form-search-trigger',
//			onTrigger1Click : function(){
//			    var me = this;
//			    me.setValue('');
//			},
//	        allowBlank: false,
//	        store:Ext.create('Ext.data.Store', {
//		    	fields: ['id', 'name'],
//			    proxy:{
//			    	type:'ajax',
//			    	actionMethods: {
//				        create : 'POST',
//				        read   : 'POST',
//				        update : 'POST',
//				        destroy: 'POST'
//				    },
//			    	url:Ext.ContextPath+"/project/query.do",
//			    	reader:{
//			    		type:'json',
//			    		root:'root'
//			    	}
//			    }
//		   })
//	});	
	var project_combox=Ext.create('Ems.baseinfo.ProjectCombo',{
		flex:1,
		allowBlank: false
	});
	var supplier_combox=Ext.create('Ems.baseinfo.SupplierCombo',{
		labelAlign:'right',
		labelWidth:40,
		flex:1,
		minWidth:150,
		allowBlank: true
	});
	//me.supplier_combox=supplier_combox;
		
		me.items=[orderType_field,id_field,status,order_no,store_combox,orderDate,operater,operater_name,project_combox,supplier_combox];
		
		var saveButton=Ext.create('Ext.button.Button',{
            text: '保存',
            iconCls:'form-save-button',
            //formBind: true,当设置这个值得时候，当表单里面有内容后，就自动会变成可执行
            //hidden :true,
            itemId:'save',
            handler: function(btn) {
            	var form=this.up('form');
                if(!form.getForm().isValid()) {
                	return;
                }
               
				form.submit({
					success: function(record, operation) {
						me.fireEvent("saved");
					}
				});
            }
      });
      me.buttons=[saveButton];
      me.addEvents("saved");
		me.callParent();
	}
});
