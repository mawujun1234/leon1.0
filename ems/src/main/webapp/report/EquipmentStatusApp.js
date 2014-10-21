//Ext.require("Ems.store.Order");
//Ext.require("Ems.store.OrderGrid");
//Ext.require("Ems.store.OrderTree");
//Ext.require("Ems.store.OrderForm");
Ext.onReady(function(){
	var ecode_textfield=Ext.create('Ext.form.field.Text',{
		labelAlign:'right',
		name:'encode',
		fieldLabel: '输入设备条码',
		minLength:Ext.ecode_length,
		maxLength:Ext.ecode_length,
		length:Ext.ecode_length,
		selectOnFocus:true,
		labelWidth:80,
		width:250,
		allowBlank:false,
		listeners:{
			blur:function(f,e){
				if(!f.getValue()||f.getValue()==''){
					f.clearInvalid();
				}
			},
			focus:function(){

			},
			change:function(field,newValue,oldValue,e){
				if(newValue.length==Ext.ecode_length){
					Ext.Ajax.request({
						url:Ext.ContextPath+'/equipmentstatus/query.do',
						method:'POST',
						params:{ecode:newValue},
						success:function(response){
							var obj=Ext.decode(response.responseText);
							//console.dir(obj.baseinfo);
							baseinfo_form.getForm().setValues(obj.root.baseinfo);
							firstinstore_form.getForm().setValues(obj.root.firstinstore);
							ecode_textfield.setValue('');
						}
					});
				
				}
			}
		}
	});
	
	var clear_button=Ext.create('Ext.button.Button',{
		text:'清除',
		margin:'0 0 0 5',
		icon:'../icons/delRole.png',
		handler:function(){
			ecode_textfield.setValue('');
		}
	});
	
	var query_form=Ext.create('Ext.form.Panel',{
		region:'north',
		layout: 'hbox',
		frame:true,
		margin :'5 0 0 0',
		height:50,
		items:[ecode_textfield,clear_button]
	});
	//基本信息
	var baseinfo_form=Ext.create('Ext.form.Panel',{
		title:'基本信息',
		defaultType: 'textfield',
		items:[{
	        fieldLabel: '条码',
	        name: 'ecode',
	        allowBlank: true
	    },{
	        fieldLabel: '小类',
	        name: 'subtype_name',
	        allowBlank: true
	    },{
	        fieldLabel: '品名',
	        name: 'prod_name',
	        allowBlank: true
	    },{
	        fieldLabel: '品牌',
	        name: 'brand_name',
	        allowBlank: true
	    },{
	        fieldLabel: '供应商',
	        name: 'supplier_name',
	        allowBlank: true
	    },{
	        fieldLabel: '型号',
	        name: 'style',
	        allowBlank: true
	    },{
	        fieldLabel: '状态',
	        name: 'status_name',
	        allowBlank: true
	    },
	    {
	        fieldLabel: '仓库',
	        name: 'store_name',
	        allowBlank: true
	    },
	    {
	        fieldLabel: '作业单位',
	        name: 'workUnit_name',
	        allowBlank: true
	    },
	    {
	        fieldLabel: '杆位地址',
	        name: 'pole_address',
	        allowBlank: true
	    }]
	});
	//首次入库信息
	var firstinstore_form=Ext.create('Ext.form.Panel',{
		title:'其他信息',
		items:[{
			xtype:'fieldset',
			defaultType: 'textfield',
			title:'首次入库信息',
			items:[{
		        fieldLabel: '订单号',
		        name: 'orderNo',
		        allowBlank: true
		    },{
		        fieldLabel: '采购日期',
		        name: 'orderDate',
		        allowBlank: true
		    },{
		        fieldLabel: '首次入库日期',
		        name: 'instore_date',
		        allowBlank: true
		    },{
		        fieldLabel: '首次入库仓库',
		        name: 'instore_name',
		        allowBlank: true
		    },{
		        fieldLabel: '首次入库经手人',
		        name: 'instore_operater',
		        allowBlank: true
	    	}]
		},{
			xtype:'fieldset',
			defaultType: 'textfield',
			title:'最新领用信息',
			items:[{
		        fieldLabel: '领用日期',
		        name: 'installout_date',
		        allowBlank: true
		    },{
		        fieldLabel: '领用经办人',
		        name: 'installout_operater',
		        allowBlank: true
		    },{
		        fieldLabel: '领用作业单位',
		        name: 'installout_workunit_name',
		        allowBlank: true
		    }]
		}]

	});
	var tabPanel=Ext.create("Ext.tab.Panel",{
		region:'center',
		items:[baseinfo_form,firstinstore_form]
	});
	
	
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'border',
		items:[query_form,tabPanel]
	});

});