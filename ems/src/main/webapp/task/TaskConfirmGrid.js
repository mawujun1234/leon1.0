
Ext.define('Ems.task.TaskConfirmGrid',{
	extend:'Ext.grid.Panel',
	requires: [
	     'Ems.task.Task'
	],
	columnLines :true,
	stripeRows:true,
	viewConfig:{
		stripeRows:true,
		listeners:{

		}
	},
	pageSize:50,

	initComponent: function () {
      var me = this;
      me.columns=[
        {dataIndex:'id',text:'任务编号',width:100},
        {dataIndex:'submitDate',text:'提交时间'},
		//{dataIndex:'status_name',text:'状态',width:50},
		{dataIndex:'type_name',text:'任务类型',width:60},
		{dataIndex:'pole_code',text:'点位编号',width:55},
		{dataIndex:'pole_name',text:'点位名称'},
		{dataIndex:'pole_address',text:'地址',flex:1},
		{dataIndex:'workunit_name',text:'作业单位'},
		//{dataIndex:'customer_name',text:'所属客户'},
		{dataIndex:'memo',text:'任务描述',flex:1,renderer:function(value,metadata,record){
			metadata.tdAttr = "data-qtip='" + value+ "'";
		    return value;
		}}
      ];

	  me.store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:me.pageSize,
			model: 'Ems.task.Task',
			
			proxy:{
				type:'ajax',
				actionMethods:{read:'POST'},
				url:Ext.ContextPath+'/task/querySubmited.do',
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
      
      
	  me.initToolbar();
      me.callParent();
	},
	//初始化工具栏
	initToolbar:function(){
		var me=this;
		var customer_combox=Ext.create('Ext.form.field.ComboBox',{
	        fieldLabel: '客户名称',
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
	    
	    
	    var workunit_combox=Ext.create('Ext.form.field.ComboBox',{
	        fieldLabel: '作业单位',
	        labelAlign:'right',
            labelWidth:55,
            //width:250,
	        //xtype:'combobox',
	        //afterLabelTextTpl: Ext.required,
	        name: 'workunit_id',
		    displayField: 'name',
		    valueField: 'id',

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
	    
	  var task_type_combox=Ext.create('Ext.form.field.ComboBox',{
	        fieldLabel: '类型',
	        labelAlign:'right',
            labelWidth:40,
            width:120,
	        //xtype:'combobox',
	        //afterLabelTextTpl: Ext.required,
	        name: 'status',
		    displayField: 'name',
		    valueField: 'id',
	        //allowBlank: false,
	        store:Ext.create('Ext.data.Store', {
		    	fields: ['id', 'name'],
			    data:[{id:'',name:'所有'},{id:'newInstall',name:'新安装'},{id:'repair',name:'维修维护'},{id:'patrol',name:'巡检'},{id:'cancel',name:'取消'}]
		   })
	  }); 
		
	  var pole_textfield=Ext.create('Ext.form.field.Text',{
			labelAlign:'right',
			name:'pole_name',
			//fieldLabel: '杆位名称',
			emptyText:'请输入点位编号或名称',
			selectOnFocus:true,
			labelWidth:80,
			width:120,
			allowBlank:true
		});
		
		me.getStore().on("beforeload",function(store){
			store.getProxy().extraParams={
				customer_id:customer_combox.getValue(),
				pole_name:pole_textfield.getValue(),
				type:task_type_combox.getValue(),
				workunit_id:workunit_combox.getValue()
			};
		});
		var query_button=Ext.create('Ext.button.Button',{
			text:'查询',
			margin:'0 0 0 5',
			iconCls:'form-search-button',
			handler:function(){
				me.store.loadPage(1);
			}
		});
		
		var confirm_button=Ext.create('Ext.button.Button',{
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
						alert("只有'已提交'状态下，才能确认!");
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
		
		var sendback_button=Ext.create('Ext.button.Button',{
			text:'退回',
			margin:'0 0 0 5',
			icon:'../icons/arrow_undo.png',
			handler:function(){
				Ext.Msg.confirm("消息","确定要退回吗?",function(btn){
					if(btn=='no'){
						return;
					}
					var records=me.getSelectionModel().getSelection();
					if(!records || records.length==0){
						alert("请先选择任务");
						return;
					}
					
					if(records.length==1){
						if(records[0].get("status")!="submited"){
							alert("只有'已提交'状态下，才能退回!");
							return;
						}	
						Ext.Ajax.request({
							url:Ext.ContextPath+'/task/back.do',
							method:'POST',
							params:{id:records[0].get("id")},
							success:function(response){
								records[0].set("status","handling");
								records[0].set("status_name","处理中");
							}
						});
					} else {
						Ext.Msg.confirm("提醒","只会为对'只有'已提交'状态的状态，才能确认",function(btn){
							if(btn=='yes'){
							
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
				items: [customer_combox,workunit_combox,task_type_combox,pole_textfield] // toolbar 1
			}, {
				items: [query_button,confirm_button,sendback_button] // toolbar 2
			}]
		  }	
		
	}

});
