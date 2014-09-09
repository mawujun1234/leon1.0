Ext.require('Ems.task.TaskForm');
Ext.define('Ems.task.TaskSendGrid',{
	extend:'Ext.grid.Panel',
	requires: [
	     'Ems.baseinfo.Pole'
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
		showHeaderCheckbox:true//防止点全选，去选择
//		renderer: function(value, metaData, record, rowIndex, colIndex, store, view) {
//			alert(record.get("status")+"测试在没有复选框的时候，getSelect会不会选择");
//			if(record.get("status")!='installing' && !record.get("status")=='cancel' ){
//				var baseCSSPrefix = Ext.baseCSSPrefix;
//		        metaData.tdCls = baseCSSPrefix + 'grid-cell-special ' + baseCSSPrefix + 'grid-cell-row-checker';
//		        return '<div class="' + baseCSSPrefix + 'grid-row-checker">&#160;</div>';
//			} else {
//				return "";
//			}
//	        
//	    }
	}),
	initComponent: function () {
      var me = this;
      me.columns=[
		{dataIndex:'status',text:'状态',width:40,renderer : function(value,metadata, record, rowIndex, columnIndex, store) {
      	   metadata.tdAttr = "data-qtip='" + record.get("status_name")+ "'";
		   if (value == 'uninstall') {
		    return "<img src='../icons/help_circle_blue.png' />";
		   } else if (value == 'installing'){
		    return "<img src='../icons/circle_blue.png' />";
		   }else if (value == 'using'){
		    return "<img src='../icons/circle_green.png' />";
		   }else if (value == 'hitch'){
		    return "<img src='../icons/circle_yellow.png' />";
		   }else if (value == 'cancel'){
		    return "<img src='../icons/circle_red.png' />";
		   }
		   return record.get("status_name");
		 }},
		{dataIndex:'name',text:'杆位名称',renderer:function(value,metadata ,record){
			if(record.get("task_type")=="newInstall"){
				 return "<img src='../images/install.png' />&nbsp;&nbsp;&nbsp;&nbsp;<a href='javascript:void(0);'>"+value+"</a>";
			} else if(record.get("task_type")=="repair"){
				 return "<img src='../images/repair.png' />&nbsp;&nbsp;&nbsp;&nbsp;<a href='javascript:void(0);'>"+value+"</a>";
			} else if(record.get("task_type")=="patrol"){
				 return "<img src='../images/patrols.png' />&nbsp;&nbsp;&nbsp;&nbsp;<a href='javascript:void(0);'>"+value+"</a>";
			}
			return value;
		}},
		{dataIndex:'province',text:'地址',flex:1,renderer:function(value,metadata ,record){
      		var aaa=value+record.get("city")+record.get("area")+record.get("address");
      		metadata.tdAttr = "data-qtip='" + aaa+ "'";
      		return aaa;
      	}},
		{dataIndex:'area_name',text:'所属片区'},
		{dataIndex:'workunit_name',text:'作业单位'},
		{dataIndex:'customer_name',text:'所属客户'}
      ];
      
	  me.store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:me.pageSize,
			model: 'Ems.baseinfo.Pole',
			autoLoad:true,
			proxy:{
				type:'ajax',
				url:Ext.ContextPath+'/task/queryPoles.do',
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
      
	  me.on('cellclick',function(view, td, cellIndex, record, tr, rowIndex, e, eOpts){
	  	//alert(cellIndex);
	  	if(cellIndex==2){
	  		me.showTaskForm(record,record.get("task_type"))
	  	}
	  });
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
	    
	    var area_combox=Ext.create('Ext.form.field.ComboBox',{
	        fieldLabel: '<b>片区</b>',
	        labelAlign:'right',
            labelWidth:55,
            //width:250,
	        //xtype:'combobox',
	        //afterLabelTextTpl: Ext.required,
	        name: 'area_id',
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
			    	url:Ext.ContextPath+"/area/queryCombo.do",
			    	reader:{
			    		type:'json',
			    		root:'root'
			    	}
			    }
		   })
	    });
	    
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
		
		var query_button=Ext.create('Ext.button.Button',{
			text:'查询',
			margin:'0 0 0 5',
			iconCls:'form-search-button',
			handler:function(){
				me.getStore().load({params:{
					customer_id:customer_combox.getValue(),
					pole_name:pole_textfield.getValue(),
					area_id:area_combox.getValue(),
					workunit_id:workunit_combox.getValue()
				}});
			}
		});
		
		var install_button=Ext.create('Ext.button.Button',{
			text:'发送安装任务',
			margin:'0 0 0 5',
			icon:'../images/install.png',
			handler:function(){
				var records=me.getSelectionModel().getSelection();
				if(!records || records.length==0){
					alert("请先选择杆位");
					return;
				}
				
				if(records.length==1){
					if(records[0].get("status")!="uninstall"){
						alert("只有'未安装'状态的杆位，才能发送新安装任务!");
						return;
					}
					me.showTaskForm(records[0],"newInstall");
					
				} else {
					Ext.Msg.confirm("提醒","只会为对'未安装'的杆位发送安装任务,选'是'进行发送",function(btn){
						if(btn=='yes'){
						
						}
					});
				}
				
			}
		});
		
		var repair_button=Ext.create('Ext.button.Button',{
			text:'发送维修/维护任务',
			margin:'0 0 0 5',
			icon:'../images/repair.png',
			handler:function(){
				var records=me.getSelectionModel().getSelection();
				if(!records || records.length==0){
					alert("请先选择杆位");
					return;
				}
				if(records.length==1){
					var pole_status=records[0].get("status");
					if(pole_status!="using" && pole_status!="hitch"){
						alert("只有'使用中','有损坏'状态的杆位，才能发送维修/维护任务!");
						return;
					}
					me.showTaskForm(records[0],"repair");
				} else {
					Ext.Msg.confirm("提醒","只会为对'使用中','有损坏'的杆位发送维修/维护任务,选'是'进行发送",function(btn){
						if(btn=='yes'){
						
						}
					});
				}
				
			}
		});
		
		var patrol_button=Ext.create('Ext.button.Button',{
			text:'发送巡检任务',
			margin:'0 0 0 5',
			icon:'../images/patrols.png',
			handler:function(){
				var records=me.getSelectionModel().getSelection();
				if(!records || records.length==0){
					alert("请先选择杆位");
					return;
				}
				if(records.length==1){
					var pole_status=records[0].get("status");
					if(pole_status!="using" && pole_status!="hitch"){
						alert("只有'使用中','有损坏'状态的杆位，才能发送巡任务!");
						return;
					}
					me.showTaskForm(records[0],"patrol");
				} else {
					Ext.Msg.confirm("提醒","只会为对'使用中','有损坏'的杆位发送巡检任务,选'是'进行发送",function(btn){
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
				items: [customer_combox,area_combox,workunit_combox,pole_textfield,query_button] // toolbar 1
			}, {
				items: [install_button,repair_button,patrol_button] // toolbar 2
			}]
		  }	
		
	},
	showTaskForm:function(pole,task_type){
		var pole_values=pole.getData();
	
					var values={};
					values.pole_id=pole_values.id
					values.pole_name=pole_values.name;
					values.pole_address=pole_values.province+pole_values.city+pole_values.area+pole_values.address;
					
					values.customer_id=pole_values.customer_id;
					values.customer_name=pole_values.customer_name;
					values.workunit_id=pole_values.workunit_id;
					values.workunit_name=pole_values.workunit_name;
					values.type=task_type;
					var title="发送任务";
					//如果存在，表明该杆位已经存在任务关联了，所以直接设置这几个值
					var showSendButton=true;
					if(pole.get('task_status')){
						values.status=pole.get('task_status');
						values.status_name=pole.get('task_status_name');
						values.memo=pole.get('task_memo');
						showSendButton=false;//标识我现在只是查看这个任务的状态
						title="查看任务详情";
					}
					var record=Ext.create('Ems.task.Task',values);
					var form=Ext.create('Ems.task.TaskForm',{
						url:Ext.ContextPath+'/task/create.do',
						showSendButton:showSendButton,
						listeners:{
							sended:function(){
								win.close();
							}
						}
					});
					//var record=me.showTaskForm(records[0]);
					//record.set("type","newInstall");
					form.loadRecord(record);
					
					var win=Ext.create('Ext.Window',{
		        		layout:'fit',
		        		width:450,
		        		title:title,
		        		height:420,
		        		items:[form],
		        		modal:true
		        	});
		        	win.show();
		return record;
	}
});
