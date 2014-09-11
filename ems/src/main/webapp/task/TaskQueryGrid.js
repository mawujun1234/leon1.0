
Ext.define('Ems.task.TaskQueryGrid',{
	extend:'Ext.grid.Panel',
	requires: [
	     'Ems.task.Task'
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
//	selModel:new Ext.selection.CheckboxModel({
//		checkOnly:true,
//		showHeaderCheckbox:true//防止点全选，去选择
////		renderer: function(value, metaData, record, rowIndex, colIndex, store, view) {
////			alert(record.get("status")+"测试在没有复选框的时候，getSelect会不会选择");
////			if(record.get("status")!='installing' && !record.get("status")=='cancel' ){
////				var baseCSSPrefix = Ext.baseCSSPrefix;
////		        metaData.tdCls = baseCSSPrefix + 'grid-cell-special ' + baseCSSPrefix + 'grid-cell-row-checker';
////		        return '<div class="' + baseCSSPrefix + 'grid-row-checker">&#160;</div>';
////			} else {
////				return "";
////			}
////	        
////	    }
//	}),
	initComponent: function () {
      var me = this;
      me.columns=[
		{dataIndex:'status_name',text:'状态',width:50,renderer:function(value,meta,record){
			if(record.get("status")=='submited'){
				return '<span style="color:red;">'+value+'</span>';
			}
			return value;
		}},
		{dataIndex:'type_name',text:'任务类型',width:50},
		{dataIndex:'pole_name',text:'杆位名称'},
		{dataIndex:'pole_address',text:'地址',flex:1},
		//{dataIndex:'area_name',text:'所属片区'},
		{dataIndex:'workunit_name',text:'作业单位'},
		{dataIndex:'customer_name',text:'所属客户'},
		{dataIndex:'memo',text:'任务描述'}
      ];
      
	  me.store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:me.pageSize,
			model: 'Ems.task.Task',
			autoLoad:true,
			proxy:{
				type:'ajax',
				url:Ext.ContextPath+'/task/query.do',
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
      
//	  me.on('cellclick',function(view, td, cellIndex, record, tr, rowIndex, e, eOpts){
//	  	//alert(cellIndex);
//	  	if(cellIndex==2){
//	  		me.showTaskForm(record,record.get("task_type"))
//	  	}
//	  });
	  me.initToolbar();
      me.callParent();
	},
	//初始化工具栏
	initToolbar:function(){
		var me=this;
		var customer_combox=Ext.create('Ext.form.field.ComboBox',{
	        fieldLabel: '<b>客户名称</b>',
	        labelAlign:'right',
            labelWidth:55,
            //width:250,
	        //xtype:'combobox',
	        //afterLabelTextTpl: Ext.required,
	        name: 'customer_id',
		    displayField: 'name',
		    valueField: 'id',
		    queryParam: 'name',
    		queryMode: 'remote',
    		triggerAction: 'query',
    		minChars:-1,
		    trigger1Cls: Ext.baseCSSPrefix + 'form-clear-trigger',
		    trigger2Cls: Ext.baseCSSPrefix + 'form-arrow-trigger',//'form-search-trigger',
			onTrigger1Click : function(){
			    var me = this;
			    me.setValue('');
			},
	        //allowBlank: false,
	        store:Ext.create('Ext.data.Store', {
		    	fields: ['id', 'name'],
			    proxy:{
			    	type:'ajax',
			    	//extraParams:{type:1,edit:true},
			    	url:Ext.ContextPath+"/customer/queryCombo.do",
			    	reader:{
			    		type:'json',
			    		root:'root'
			    	}
			    }
		   })
	    });
	    
//	    var area_combox=Ext.create('Ext.form.field.ComboBox',{
//	        fieldLabel: '<b>片区</b>',
//	        labelAlign:'right',
//            labelWidth:55,
//            //width:250,
//	        //xtype:'combobox',
//	        //afterLabelTextTpl: Ext.required,
//	        name: 'area_id',
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
//	        //allowBlank: false,
//	        store:Ext.create('Ext.data.Store', {
//		    	fields: ['id', 'name'],
//			    proxy:{
//			    	type:'ajax',
//			    	//extraParams:{type:1,edit:true},
//			    	url:Ext.ContextPath+"/area/queryCombo.do",
//			    	reader:{
//			    		type:'json',
//			    		root:'root'
//			    	}
//			    }
//		   })
//	    });
	    
	    var workunit_combox=Ext.create('Ext.form.field.ComboBox',{
	        fieldLabel: '<b>作业单位</b>',
	        labelAlign:'right',
            labelWidth:55,
            //width:250,
	        //xtype:'combobox',
	        //afterLabelTextTpl: Ext.required,
	        name: 'workunit_id',
		    displayField: 'name',
		    valueField: 'id',
		    queryParam: 'name',
    		queryMode: 'remote',
    		triggerAction: 'query',
    		minChars:-1,
		    trigger1Cls: Ext.baseCSSPrefix + 'form-clear-trigger',
		    trigger2Cls: Ext.baseCSSPrefix + 'form-arrow-trigger',//'form-search-trigger',
			onTrigger1Click : function(){
			    var me = this;
			    me.setValue('');
			},
	        //allowBlank: false,
	        store:Ext.create('Ext.data.Store', {
		    	fields: ['id', 'name'],
			    proxy:{
			    	type:'ajax',
			    	//extraParams:{type:1,edit:true},
			    	url:Ext.ContextPath+"/workUnit/queryCombo.do",
			    	reader:{
			    		type:'json',
			    		root:'root'
			    	}
			    }
		   })
	    });
	    
	    var pole_textfield=Ext.create('Ext.form.field.Text',{
			labelAlign:'right',
			name:'pole_name',
			fieldLabel: '杆位名称',
			selectOnFocus:true,
			labelWidth:80,
			width:250,
			allowBlank:true
		});
		
		var status_combox=Ext.create('Ext.form.field.ComboBox',{
	        fieldLabel: '状态',
	        labelAlign:'right',
            labelWidth:55,
            width:'155',
	        //xtype:'combobox',
	        //afterLabelTextTpl: Ext.required,
	        name: 'status',
		    displayField: 'name',
		    valueField: 'id',
	        //allowBlank: false,
	        store:Ext.create('Ext.data.Store', {
		    	fields: ['id', 'name'],
			    data:[{id:'newTask',name:'新任务'},{id:'read',name:'已阅'},{id:'handling',name:'处理中'},{id:'submited',name:'已提交'},{id:'complete',name:'完成'}]
		   })
	  }); 
		
		var query_button=Ext.create('Ext.button.Button',{
			text:'查询',
			margin:'0 0 0 5',
			iconCls:'form-search-button',
			handler:function(){
				me.getStore().load({params:{
					customer_id:customer_combox.getValue(),
					pole_name:pole_textfield.getValue(),
					//area_id:area_combox.getValue(),
					status:status_combox.getValue(),
					workunit_id:workunit_combox.getValue()
				}});
			}
		});
		
		var install_button=Ext.create('Ext.button.Button',{
			text:'确认',
			margin:'0 0 0 5',
			icon:'../icons/gem_okay.png',
			handler:function(){
				var records=me.getSelectionModel().getSelection();
				if(!records || records.length==0){
					alert("请先选择任务");
					return;
				}
				
				if(records.length==1){
					if(records[0].get("status")!="submited"){
						alert("只有'已提交'状态的状态，才能确认!");
						return;
					}	
					Ext.Ajax.request({
						url:Ext.ContextPath+'/task/confirm.do',
						method:'POST',
						params:{id:records[0].get("id")},
						success:function(response){
							records[0].set("status","complete");
							records[0].set("status_name","完成");
						}
					});
				} else {
					Ext.Msg.confirm("提醒","只会为对'只有'已提交'状态的状态，才能确认",function(btn){
						if(btn=='yes'){
						
						}
					});
				}
				
			}
		});
		
		
		me.tbar={
			xtype: 'container',
			layout: 'anchor',
			defaults: {anchor: '0'},
			defaultType: 'toolbar',
			items: [{
				items: [customer_combox,workunit_combox,pole_textfield,status_combox,query_button] // toolbar 1
			}, {
				items: [install_button] // toolbar 2
			}]
		  }	
		
	}
//	showTaskForm:function(pole,task_type){
//		var pole_values=pole.getData();
//	
//					var values={};
//					values.pole_id=pole_values.id
//					values.pole_name=pole_values.name;
//					values.pole_address=pole_values.province+pole_values.city+pole_values.area+pole_values.address;
//					
//					values.customer_id=pole_values.customer_id;
//					values.customer_name=pole_values.customer_name;
//					values.workunit_id=pole_values.workunit_id;
//					values.workunit_name=pole_values.workunit_name;
//					values.type=task_type;
//					var title="发送任务";
//					//如果存在，表明该杆位已经存在任务关联了，所以直接设置这几个值
//					var showSendButton=true;
//					if(pole.get('task_status')){
//						values.status=pole.get('task_status');
//						values.status_name=pole.get('task_status_name');
//						values.memo=pole.get('task_memo');
//						showSendButton=false;//标识我现在只是查看这个任务的状态
//						title="查看任务详情";
//					}
//					var record=Ext.create('Ems.task.Task',values);
//					var form=Ext.create('Ems.task.TaskForm',{
//						url:Ext.ContextPath+'/task/create.do',
//						showSendButton:showSendButton,
//						listeners:{
//							sended:function(){
//								win.close();
//							}
//						}
//					});
//					//var record=me.showTaskForm(records[0]);
//					//record.set("type","newInstall");
//					form.loadRecord(record);
//					
//					var win=Ext.create('Ext.Window',{
//		        		layout:'fit',
//		        		width:450,
//		        		title:title,
//		        		height:420,
//		        		items:[form],
//		        		modal:true
//		        	});
//		        	win.show();
//		return record;
//	}
});