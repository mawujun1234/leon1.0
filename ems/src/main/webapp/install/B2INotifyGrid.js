Ext.define('Ems.install.B2INotifyGrid',{
	extend:'Ext.grid.Panel',
	requires: [
	     'Ems.install.B2INotify'
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
		{dataIndex:'ecode',text:'条码',width:150},
		{dataIndex:'subtype_name',text:'小类'},
		{dataIndex:'prod_name',text:'品名'},
		{dataIndex:'brand_name',text:'品牌'},
		{dataIndex:'prod_style',text:'型号'},
		{dataIndex:'prod_spec',text:'描述',flex:1},
		//{dataIndex:'supplier_name',text:'供应商'},
		{dataIndex:'borrow_id',text:'借用单编码'},
		{dataIndex:'workunit_name',text:'作业单位'},
		{dataIndex:'task_id',text:'任务编码'},
		{dataIndex:'createDate',text:'借转领时间',xtype: 'datecolumn',   format:'Y-m-d'},
		//{dataIndex:'pole_id',text:'pole_code'},
		{dataIndex:'store_name',text:'仓库名称'}
      ];
      
	  me.store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			model: 'Ems.install.B2INotify',
			autoLoad:true
	  });
	  
//      me.dockedItems= [{
//	        xtype: 'pagingtoolbar',
//	        store: me.store,  
//	        dock: 'bottom',
//	        displayInfo: true
//	  }];
	  
	  me.tbar=	[{
			text: '刷新',
			itemId:'reload',
			disabled:me.disabledAction,
			handler: function(btn){
				var grid=btn.up("grid");
				grid.getStore().reload();
			},
			iconCls: 'form-reload-button'
		},{
			text: '增加领用类型',
			//itemId:'reload',
			icon:'../icons/plugin_add.png',
			handler: function(btn){
				var grid=btn.up("grid");
				var record=grid.getSelectionModel( ).getLastSelected( );
				if(!record){
					alert("请先选择一个设备!");
					return;
				}
				var form=grid.createForm();
				var borrow_id_field=form.getForm().findField("borrow_id");
				borrow_id_field.setValue(record.get("borrow_id"));
				var ecode_field=form.getForm().findField("ecode");
				ecode_field.setValue(record.get("ecode"));
				var b2INotify_id_field=form.getForm().findField("b2INotify_id");
				b2INotify_id_field.setValue(record.get("id"));
				
				var win=Ext.create('Ext.window.Window',{
					layout:'fit',
					items:[form],
					title:'增加领用类型',
					width:280,
					height:300,
					//closeAction:'destroy',
					modal:true,
					buttons:[{
						text:'取消',
						handler:function(btn){
							win.close();
						}
					},{
						text:'确定',
						handler:function(btn){
							if(!form.isValid()){
								alert("请填写出现红框框的内容");
								return;
							}
							form.submit({
								url:Ext.ContextPath+"/borrow/updateInstalloutListType.do",
								success:function(response){
									win.close();
									grid.getStore().reload();
								}
							});
							
						}
					}]
				});
				win.show();
				
			}
		}]
       
      me.callParent();
	},
	createForm:function(){
		var me=this;
		var installOutType_combox=Ext.create('Ext.form.field.ComboBox',{
	        fieldLabel: '领用类型',
	        labelAlign:'right',
            //labelWidth:55,
	        //xtype:'combobox',
	        //afterLabelTextTpl: Ext.required,
	        name: 'installOutType_id',
		    displayField: 'name',
		    editable:false,
		    valueField: 'id',
	        allowBlank: false,
	        store:Ext.create('Ext.data.Store', {
		    	fields: ['id', 'name'],
			    proxy:{
			    	type:'ajax',
			    	//extraParams:{type:[1,3],edit:true},
			    	url:Ext.ContextPath+"/installOutType/query.do",
			    	reader:{
			    		type:'json',
			    		root:'root'
			    	}
			    }
		   }),
		   listeners:{
		   	change:function(field,newValue, oldValue){
				installOutType_content_textfield.setValue(field.getRawValue());
			}
		   }
		});
		var installOutType_content_textfield=Ext.create('Ext.form.field.Text',{
			labelAlign:'right',
			//labelWidth:55,
			fieldLabel: '领用类型二级',
			name:'installOutType_content',
			readOnly:false,
			allowBlank:false,
			emptyText:'输入车牌号等内容'
		});
		var form=Ext.create('Ext.form.Panel',{
			frame:true,
			items:[{
				name:'borrow_id',
				fieldLabel: '借用单编码',
				labelAlign:'right',
				editable:false,
				readOnly:true,
				xtype:"textfield"
			},{
				name:'ecode',
				fieldLabel: '条码',
				labelAlign:'right',
				editable:false,
				readOnly:true,
				xtype:"textfield"
			},{
				name:'b2INotify_id',
				labelAlign:'right',
				editable:false,
				readOnly:true,
				xtype:"hidden"
			},installOutType_combox,installOutType_content_textfield]
		});
		//me.form=form;
		return form;
	}
});
