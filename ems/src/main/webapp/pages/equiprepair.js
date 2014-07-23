Ext.onReady(function(){
	
	Ext.tip.QuickTipManager.init();
	var ecode=null,estatus=0,jid=null,rid=null,oid=null;
	var jobtype={REPAIR:1,OUTSOURCE:2}, rtype=1;
	var jobstatus={APPLY:0,PROCESS:1,COMPLETE:2,OUTSOURCE:3,DISCARD:4},status=0;
	
	var ecode_field=Ext.create('Ext.form.field.Text',{
		name:'ecode',fieldLabel:'输入设备条形码',allowBlank:false,vtype:'alphanum',minLength:18,maxLength:18,msgTarget:'side'
	});
	
	var step1=Ext.create('Ext.form.Panel',{
		title:'输入条码',
        layout: {
            type:'vbox',
            align:'stretch'
        },
        defaults:{border:false},
        items:[{flex:1,
	            layout: {
	                type:'hbox',
	                pack:'center',
	                align:'middle'
	            },
	            items:{width:300,xtype:'fieldcontainer',layout:'hbox',items:[ecode_field,{margins:'0 0 0 5px',xtype:'button',text:'清除',handler:function(btn){ecode_field.reset();ecode_field.focus();}}]}
        	   },
               {html:'<HR style="FILTER: alpha(opacity=100,finishopacity=0,style=3)" width="100%" color=#987cb9 SIZE=2>',
        		buttonAlign:'center',
	    		buttons:[{text:'下一步',handler:function(){
	    			var step1_form=step1.getForm();
	    		    if(step1_form.isDirty()&&step1_form.isValid( )){
	    			   	 Ext.Ajax.request({
	    				        url : Ext.ContextPath+'/outWarehouse/getEquipInfo.do',
	    				        method : 'post',
	    				        params:{ecode:ecode_field.getValue()},
	    				        success : function(response, options) {
	    					    	var ret=Ext.decode(response.responseText);
	    					    	var equipinfo=ret.data;
	    					    	if(ret.success&&equipinfo){
	    					    		ecode=equipinfo.ecode;
	    					    		jid=equipinfo.lstJid;
	    					    		oid=equipinfo.lstOid;
	    					    		rid=equipinfo.lstRid;
	    						    	step2.down('form').getForm().setValues(equipinfo);
	    					    		var tabpanel=step1.up("tabpanel");
	    				    			tabpanel.setActiveTab(step2);
	    					    	}
	    				        },
	    				        failure : function() {
	    				        	 Ext.Msg.alert('提示','数据加载失败');
	    				        }
	    			  	 });
	    		    }
	    		}}]
        	}],
        	listeners:{
        		activate:function(p,e){
        			ecode_field.focus();
        			ecode_field.reset();
        			p.setDisabled(false);
                    step2.setDisabled(true);
                    step3.setDisabled(true);
                    step4.setDisabled(true);
                    step5.setDisabled(true);
        		}
        	}
	});
	
	Ext.define('repairinfo',{
		extend:'Ext.data.Model',
		fields:[
			{name:'ATime',mapping:'ATime'},
			{name:'AUcid',mapping:'AUcid'},
			{name:'DUcid',mapping:'DUcid'},
			{name:'ETime',mapping:'ETime'},
			{name:'FTime',mapping:'FTime'},
			{name:'RType',mapping:'RType'},
			{name:'STime',mapping:'STime'},
			{name:'aucname',mapping:'aucname'},
			{name:'descrip',mapping:'descrip'},
			{name:'ducname',mapping:'ducname'},
			{name:'ecode',mapping:'ecode'},
			{name:'eid',mapping:'eid'},
			{name:'iidO',mapping:'iidO'},
			{name:'jid',mapping:'jid'},
			{name:'jobList',mapping:'jobList'},
			{name:'lastjid',mapping:'lastjid'},
			{name:'memo',mapping:'memo'},
			{name:'outsource',mapping:'outsource'},
			{name:'price',mapping:'price'},
			{name:'reason',mapping:'reason'},
			{name:'rid',mapping:'rid'},
			{name:'status',mapping:'status'}]
		});
	
	var repStore = Ext.create('Ext.data.Store', {
	        model:'repairinfo',
	        proxy: {
	            type: 'ajax',
	            url: Ext.ContextPath+'/equipRepair/getRepairList.do',
	            reader: { root: 'root' }
	        },
	        listeners:{
	        	load:function(s,r,success,o,e){
	        		if(success){
	        			repairGrid.setTitle('设备历史维修信息('+r.length+')');
	        		}
	        	}
	        }
	    });
	var repairGrid=Ext.create('Ext.grid.Panel',{
        title:'设备历史维修信息',
        store:repStore,
        columns: [
	   		 {header:'任务流水号',dataIndex:'rid',width:150},
	   		 {header:'任务指派时间',dataIndex:'ATime'},
	   		 {header:'故障描述',dataIndex:'descrip'},
	   		 {header:'故障原因',dataIndex:'reason'},
	   		 {header:'费用',dataIndex:'price'},
	   		 {header:'状态',dataIndex:'status',renderer:statusRender},
	   		 {header:'备注',dataIndex:'memo',flex:1}
			]
	});

	var outStore = Ext.create('Ext.data.Store', {
	        model:'repairinfo',
	        proxy: {
	            type: 'ajax',
	            url: Ext.ContextPath+'/equipRepair/getRepairList.do',
	            reader: { root: 'root' }
	        },
	        listeners:{
	        	load:function(s,r,success,o,e){
	        		if(success){
	        			outSourceGrid.setTitle('设备历史外修信息('+r.length+')');
	        		}
	        	}
	        }
	    });
	var outSourceGrid=Ext.create('Ext.grid.Panel',{
        title:'设备历史外修信息',
        store:outStore,
        columns: [
     	   		 {header:'任务流水号',dataIndex:'rid',width:150},
    	   		 {header:'任务指派时间',dataIndex:'ATime'},
    	   		 {header:'故障描述',dataIndex:'descrip'},
    	   		 {header:'故障原因',dataIndex:'reason'},
    	   		 {header:'费用',dataIndex:'price'},
    	   		 {header:'状态',dataIndex:'status',renderer:statusRender},
    	   		 {header:'备注',dataIndex:'memo',flex:1}
			]
	});
	
	var estatus_field=Ext.create('Ext.D.list.ComboBox',{
		url:Ext.ContextPath+'/dataExtra/estatusList.do',fieldLabel:'设备状态',name:'estatus',loadAuto:true,readOnly:true,
		labelWidth:75,width:220,
		listeners:{
		    change:function(f,n,o,e){
				if(n){
					estatus=f.getValue();
				}
			}
		}
	});
	
	var step2=Ext.create('Ext.panel.Panel',{
		title:'设备详细信息',
        layout: {
            type:'vbox',
            align:'stretch'
        },
        defaults:{border:false},
        items:[{flex:1,layout:'border',bodyStyle:'background-color:#FFFFFF',//设置面板体的背景色
        	    padding:'5px',
        		defaults:{border:false},
        		items:[{
        			xtype:'form',
        			region:'west',
        			layout:'fit',
        			split:true,
        			width:255,
        			items:[{xtype:'fieldset',title:'设备基本信息',defaults:{xtype:'textfield',readOnly:true,labelWidth:75,width:220},
        				    margin:'0px',
							items:[{fieldLabel:'条形码',name:'ecode'},
	  					           {fieldLabel:'设备类型',name:'typename'},
	  					     	   {fieldLabel:'型号',name:'style'},
	  					           {fieldLabel:'厂家',name:'sname'},
						           {fieldLabel:'批次',name:'batchid'},
						     	   {fieldLabel:'使用单位',name:'uname'},
						     	   estatus_field]}]
	        		},{
	        		   region:'center',
	        		   xtype:'tabpanel',
	        		   activeTab:0,
	        		   items:[repairGrid,outSourceGrid]
	        		}]
        	   },
               {html:'<HR style="FILTER: alpha(opacity=100,finishopacity=0,style=3)" width="100%" color=#987cb9 SIZE=2>',
        		buttonAlign:'center',
	    		buttons:[{text:'上一步',handler:function(btn){
	    			var tabpanel=btn.up("tabpanel");
	    			tabpanel.setActiveTab(0);
	    		}},{text:'下一步',handler:function(btn){
	    			switch(estatus){
	    			case 14:
	    				Ext.Msg.alert('提示','该设备已报废');
	    				break;
	    			case 255:
	    				Ext.Msg.alert('提示','新设备未入库或正在进行其他操作');
	    				break;
	    			default:
		    			var tabpanel=btn.up("tabpanel");
		    			tabpanel.setActiveTab(step3);
	    			}
	    		}}]
        	}],
        	listeners:{
        		activate:function(p,e){
        			repStore.load({params:{ecode:ecode,jobtype:jobtype.REPAIR}});
		    		outStore.load({params:{ecode:ecode,jobtype:jobtype.OUTSOURCE}});
        			p.setDisabled(false);
        			step1.setDisabled(true);
        			if(estatus==4){
	        			step2.setDisabled(false);
	        			step3.setDisabled(false);
        			}else
        			if(estatus==12||estatus==13){
        				step2.setDisabled(false);
	        			step3.setDisabled(false);
	        			step4.setDisabled(false);
        			}
        		}
        	}
	});
	
	function loadJobDetail(){
		var jobForm=step3.getForm();
		if(jid&&jid!=''){
			jobForm.load({
				params : {jid:jid,ecode:ecode},//传递参数   
				url : Ext.ContextPath+'/equipRepair/getJobListInfo.do',//请求的url地址   
				method : 'GET',//请求方式   
				success : function(form, action) {//加载成功的处理函数   
					
				},
				failure : function(form, action) {//加载失败的处理函数   
					Ext.Msg.alert('提示', '设备加载失败：'+ action.response.responseText);
				}
			});
		}
	}
	
	var step3=Ext.create('Ext.form.Panel',{
		title:'施工信息',
        layout: {
            type:'vbox',
            align:'stretch'
        },
        defaults:{border:false},
        items:[{flex:1,
	            layout: {
	                type:'vbox',
	                pack:'center',
	                align:'center'
	            },
	            items:[{width:520,items:{xtype:'displayfield',fieldLabel:'条形码',name:'ecode',submitValue:true},border:false},
	                   {border:true,bodyPadding:'5px',
		            	tbar:['<pan id="toolbar-title-text"><设备施工单></span>','->',{iconCls:'icon-search',text:'刷新施工单',handler:loadJobDetail},{iconCls:'icon-save',text:'创建施工单',handler:newjob}],
		            	items:[
		            	       {xtype:'columnbox',
			            		items:[{fieldLabel:'施工时间',xtype:'datefield',name:'jtime',format:'Y-m-d',allowBlank:false},
			                           {fieldLabel:'施工类型',xtype:'combo',name:'jtype',
				            		    store: Ext.create('Ext.data.Store', {
						            		        fields: ['text', 'value'],
						            		        data : [
						            		            {"text":"维护", "value":2},
						            		            {"text":"检修", "value":3},
						            		            {"text":"其他", "value":99}]
						            		    }),
				            		    queryMode: 'local',
				            		    displayField: 'text',
				            		    valueField: 'value',
				            		    value:3,
				            		    editable:false,
				            		    allowBlank:false
			            			   }]},
		                        {xtype:'listcombox',url:Ext.ContextPath+'/dataExtra/poleList.do',loadAuto:true,fieldLabel:'杆位地址',name:'cpid',anchor:'100%'},
		                        {xtype:'listcombox',url:Ext.ContextPath+'/dataExtra/clerkList.do',loadAuto:true,name:'ucid',fieldLabel:'施工人员',allowBlank:false},
		                        {xtype:'textfield',fieldLabel:'备注',name:'memo',anchor:'100%'}]}]
        	    },
               {html:'<HR style="FILTER: alpha(opacity=100,finishopacity=0,style=3)" width="100%" color=#987cb9 SIZE=2>',
        		buttonAlign:'center',
	    		buttons:[{text:'上一步',handler:function(btn){
	    			var tabpanel=btn.up("tabpanel");
	    			tabpanel.setActiveTab(1);
	    		}},{text:'下一步',handler:function(btn){
	    			if(jid&&jid!=0){
		    			var tabpanel=btn.up("tabpanel");
		    			tabpanel.setActiveTab(step4);
	    			}else{
	    				Ext.MessageBoxEx('友情提示', "请先创建施工单");
	    			}
	    		}}]
        	}],
        	listeners:{
        		activate:function(p,e){
        			p.setDisabled(false);
        			p.getForm().setValues({ecode:ecode});
        			loadJobDetail();
        		}
        	}
	});
	function newjob(){
		var step3Form=step3.getForm();
	       if(step3Form.isDirty()){
	    	   step3Form.submit({
					    clientValidation: true,
					    url: Ext.ContextPath+'newjob',
					    success: function(form, action) {
					    	var ret=Ext.decode(action.response.responseText);
					    	if(ret.success){
					    		jid=ret.jid;
					    	}
					    	Ext.MessageBoxEx('友情提示', "数据更新成功");
					    },
					    failure: function(form, action) {
					        switch (action.failureType) {
					            case Ext.form.Action.CLIENT_INVALID:
								    //客户端数据验证失败的情况下，例如客户端验证邮件格式不正确的情况下提交表单  
								    Ext.MessageBoxEx('提示','数据错误，非法提交');  
					                break;
					            case Ext.form.Action.CONNECT_FAILURE:
								    //服务器指定的路径链接不上时  
								    Ext.MessageBoxEx('连接错误','指定路径连接错误!'); 
					                break;
					            case Ext.form.Action.SERVER_INVALID:
					            	//服务器端你自己返回success为false时  
								     Ext.MessageBoxEx('友情提示', "数据更新异常");
								     break;
								default:
									 //其它类型的错误  
		                             Ext.MessageBoxEx('警告', '服务器数据传输失败：'+action.response.responseText); 
								     break;
					       }
					    }
					});
		        }else{
		        	Ext.Msg.alert('消息','没有数据被修改');
		        }
		
	}
	var step4=Ext.create('Ext.form.Panel',{
		title:'设备报修',
        layout: {
            type:'vbox',
            align:'stretch'
        },
        defaults:{border:false},
        items:[{flex:1,
	            layout: {
	                type:'hbox',
	                pack:'center',
	                align:'middle'
	            },
	            items:[{border:false,width:500,
		            	items:[{fieldLabel:'施工类型',xtype:'combo',
		            		    store: Ext.create('Ext.data.Store', {
		            		        fields: ['text', 'value'],
		            		        data : [
		            		            {"text":"维修", "value":jobtype.REPAIR},
		            		            {"text":"外修", "value":jobtype.OUTSOURCE}]
		            		    }),
			        		    queryMode: 'local',
			        		    displayField: 'text',
			        		    valueField: 'value',
			        		    value:1,
			        		    name:'RType',
			        		    editable:false
			    			   },
		            	       {xtype:'fieldset',title:'报修',anchor:'100%',items:[{layout:'column',border:false,
		                    	   												   items:[{xtype:'datefield',fieldLabel:'保修入库时间',columnWidth:1/2,name:'STime',format:'Y-m-d',allowBlank:false},
		                    	   												          {xtype:'listcombox',url:Ext.ContextPath+'/dataExtra/clerkList.do',loadAuto:true,fieldLabel:'报修人',name:'AUcid',columnWidth:1/2,margin:'0 0 0 5px',allowBlank:false}]},
		                    	   												  {xtype:'textfield',fieldLabel:'故障描述',anchor:'100%',name:'descrip',allowBlank:false}]}
		                       ]}]
        	 },
               {html:'<HR style="FILTER: alpha(opacity=100,finishopacity=0,style=3)" width="100%" color=#987cb9 SIZE=2>',
        		buttonAlign:'center',
	    		buttons:[{text:'上一步',handler:function(btn){
	    			var tabpanel=btn.up("tabpanel");
	    			tabpanel.setActiveTab(2);
	    		}},{text:'完成报修',itemId:'checkRepair_btn',handler:function(btn){
	    			if(estatus==12||estatus==13){
	    				var tabpanel=btn.up("tabpanel");
		    			tabpanel.setActiveTab(4);
	    			}else{
		    			var step4Form=step4.getForm();
		    			if(step4Form.isValid()){
		    				step4Form.submit({
	    					    clientValidation: true,
	    					    url: Ext.ContextPath+'/equipRepair/equiprepair.do',
	    					    params:{ecode:ecode,jid:jid,status:jobstatus.APPLY},
	    					    success: function(form, action) {
	    					    	btn.setText("下一步");
	    					    	step4.getForm().getFields().each(function(item){
	    					    		item.setReadOnly(true);
	    					    	});
	    					    	var ret=Ext.decode(action.response.responseText);
	    					    	if(ret.success){
	    					    		rtype=ret.RType;
	    					    		status=ret.status;
	    					    		rid=ret.rid;
	    					    	}
	    					    	Ext.MessageBoxEx('友情提示', "报修成功");
	    					    },
	    					    failure: function(form, action) {
	    					        switch (action.failureType) {
	    					            case Ext.form.Action.CLIENT_INVALID:
	    								    //客户端数据验证失败的情况下，例如客户端验证邮件格式不正确的情况下提交表单  
	    								    Ext.MessageBoxEx('提示','数据错误，非法提交');  
	    					                break;
	    					            case Ext.form.Action.CONNECT_FAILURE:
	    								    //服务器指定的路径链接不上时  
	    								    Ext.MessageBoxEx('连接错误','指定路径连接错误!'); 
	    					                break;
	    					            case Ext.form.Action.SERVER_INVALID:
	    					            	//服务器端你自己返回success为false时  
	    								     Ext.MessageBoxEx('友情提示', "数据更新异常");
	    								     break;
	    								default:
	    									 //其它类型的错误  
	    		                             Ext.MessageBoxEx('警告', '服务器数据传输失败：'+action.response.responseText); 
	    								     break;
	    					       }
	    					    },
	    					    scope:this
	    					});
		    			}
	    			}
	    		}}]
        	}],
        	listeners:{
        		activate:function(p,e){
        			p.setDisabled(false);
        			if(estatus==13||estatus==12){
	        			if(rid&&rid!=''){
	        				loadRepairInfo();
	        				p.down('#checkRepair_btn').setText('下一步');
	        			}
        			}
        		}
        	}
	});
	
	function loadRepairInfo(){
		step4.getForm().load({
			url : Ext.ContextPath+'/equipRepair/getRepairInfo.do',//请求的url地址   
			method : 'GET',//请求方式   
			params : {rid:rid},//传递参数   
			success : function(form, action) {//加载成功的处理函数   
				step4.getForm().getFields().each(function(item){
		    		item.setReadOnly(true);
		    	});
		    	var ret=Ext.decode(action.response.responseText);
		    	var obj=ret.data;
		    	if(obj){
		    		rtype=obj.RType;
		    		status=obj.status;
		    	}
			},
			failure : function(form, action) {//加载失败的处理函数   
				Ext.Msg.alert('提示', '设备加载失败：'+ action.response.responseText);
			}
		});
	}
	
	var statusStore=Ext.create('Ext.data.Store',{
	    fields: [{name: 'text', type: 'string'},
	    	     {name: 'value', type: 'int'}],
  	    proxy: {
  	        type: 'ajax',
  	        url : Ext.ContextPath+'/dataExtra/jobstatusList.do'
  	    },
  	    autoLoad:true
	});
	
	var rtype_combo=Ext.create('Ext.form.field.ComboBox',{
		fieldLabel:'施工类型',
	    store: Ext.create('Ext.data.Store', {
	        fields: ['text', 'value'],
	        data : [
	            {"text":"维修", "value":jobtype.REPAIR},
	            {"text":"外修", "value":jobtype.OUTSOURCE}]
	    }),
	    queryMode: 'local',
	    displayField: 'text',
	    valueField: 'value',
	    value:1,
	    name:'RType',
	    editable:false,
  	    readOnly:true,
  	    listeners:{
  	    	change:function(f,n,o,e){
  	    		if(n===jobtype.OUTSOURCE){
  	    		   step5.down('#outsource_fieldset').expand();
  	    		}else if(n===jobtype.REPAIR){
  	    			step5.down('#outsource_fieldset').collapse();
  	    		}
  	    	}
  	    }
	   });
	
	var status_combo=Ext.create('Ext.form.field.ComboBox',{
		fieldLabel:'施工状态',
	    store: statusStore,
	    queryMode: 'local',
	    displayField: 'text',
	    valueField: 'value',
	    value:0,
	    name:'status',
	    editable:false,
  	    listeners:{
  	    	change:function(f,n,o,e){
  	    		if(n===jobstatus.APPLY){
  	    			rtype_combo.setReadOnly(false)
  	    		}else{
  	    			rtype_combo.setValue(rtype);
  	    			rtype_combo.setReadOnly(true)
  	    		}
  	    	},
  	    	select:function(f,records,e){
  	    		if(records.length>0){
  	    			var v=records[0].get('value');
  	    			switch(status){
  	    			case jobstatus.Apply:
  	    				break;
  	    			case jobstatus.PROCESS:
  	    				if(v==jobstatus.Apply){
  	    					f.setValue(status);
  	    					Ext.Msg.alert('提示','该设备正在维修中');
  	    				}
  	    				if(rtype==jobtype.OUTSOURCE&&v==jobstatus.OUTSOURCE){
  	    					f.setValue(status);
  	    					Ext.Msg.alert('提示','该设备正在外修中');
  	    				}
  	    				break;
  	    			case jobstatus.COMPLETE:
  	    				f.setValue(rtype);
  	    				f.setReadOnly(true);
	    				Ext.Msg.alert('提示','该设备已经完成入库');
	    				break;
  	    			default: 
  	    				return false;
  	    			}
  	    		}
  	    	}
  	    }
	   });
	
	var step5=Ext.create('Ext.form.Panel',{
		title:'设备维修/外修',
        layout: {
            type:'vbox',
            align:'stretch'
        },
        defaults:{border:false},
        items:[{flex:1,
	            layout: {
	                type:'hbox',
	                pack:'center',
	                align:'middle'
	            },
	            autoScroll:true,
	            items:[{border:false,width:500,
		            	items:[{xtype:'columnbox',border:false,
    			   		        items:[rtype_combo,
    			   		               status_combo]},
		            	       {xtype:'fieldset',title:'设备维修',anchor:'100%',
			    				items:[{xtype:'datefield',name:'ATime',format:'Y-m-d',fieldLabel:'指派时间'},
			    		 			   {layout:'column',border:false,
			    			   		    items:[{xtype:'listcombox',url:Ext.ContextPath+'/dataExtra/clerkList.do',loadAuto:true,name:'RUcid',fieldLabel:'维修人',columnWidth:1/2},
			    			   		           {xtype:'listcombox',url:Ext.ContextPath+'/dataExtra/clerkList.do',loadAuto:true,name:'DUcid',fieldLabel:'审核人',columnWidth:1/2,margin:'0 0 0 5px'}]},
			    					  {xtype:'textfield',fieldLabel:'故障原因',anchor:'100%',name:'reason'}
			    					  ]},
				               {xtype:'fieldset',title:'设备外修',anchor:'100%',collapsed:true,closable:true,itemId:'outsource_fieldset',
			    				items:[{xtype:'columnbox',items:[{xtype:'numberfield',name:'price',fieldLabel:'预计价格'},
			    				 								  {xtype:'textfield',name:'outsource',fieldLabel:'外修单位'}]},
			    				       {xtype:'datefield',name:'ETime',format:'Y-m-d',fieldLabel:'预计结束时间'}]},
	    				       {xtype:'columnbox',border:false,
	    			   		        items:[{xtype:'datefield',format:'Y-m-d',name:'FTime',fieldLabel:'维修返库时间'},
	    			   		            {xtype:'textfield',name:'memo',fieldLabel:'备注',anchor:'100%'}]}
		    				  ],
		    				  buttons:[{text:'刷新 ',handler:function(){
		    	        			step5.getForm().load({
		    	        				url : Ext.ContextPath+'/equipRepair/getRepairInfo.do',//请求的url地址   
		    	        				method : 'GET',//请求方式   
		    	        				params : {rid:rid},//传递参数   
		    	        				success : function(form, action) {//加载成功的处理函数   
		    	        					var ret=Ext.decode(action.response.responseText);
		    						    	if(ret.success){
		    						    		var obj=ret.data;
		    						    		rtype=obj.RType;
		    						    		status=obj.status;
		    						    		rid=obj.rid;
		    						    	}
		    	        				},
		    	        				failure : function(form, action) {//加载失败的处理函数   
		    	        					Ext.Msg.alert('提示', '设备加载失败：'+ action.response.responseText);
		    	        				}
		    	        			});
		    				  }},{text:'维修确认',handler:equipRepair}]}]
        	 },
               {html:'<HR style="FILTER: alpha(opacity=100,finishopacity=0,style=3)" width="100%" color=#987cb9 SIZE=2>',
        		buttonAlign:'center',
	    		buttons:[{text:'上一步',handler:function(btn){
	    			var tabpanel=btn.up("tabpanel");
	    			tabpanel.setActiveTab(3);
	    		}},{text:'返回',handler:function(btn){
	    			var tabpanel=btn.up("tabpanel");
	    			tabpanel.setActiveTab(0);
	    		}}]
        	}],
        	listeners:{
        		activate:function(p,e){
        			if(status===jobstatus.APPLY){
        				rtype_combo.setReadOnly(false);
        			}
        			step5.getForm().load({
        				url : Ext.ContextPath+'/equipRepair/getRepairInfo.do',//请求的url地址   
        				method : 'GET',//请求方式   
        				params : {rid:rid},//传递参数   
        				success : function(form, action) {//加载成功的处理函数   
        					var ret=Ext.decode(action.response.responseText);
					    	if(ret.success){
					    		var obj=ret.data;
					    		rtype=obj.RType;
					    		status=obj.status;
					    		rid=obj.rid;
					    	}
        				},
        				failure : function(form, action) {//加载失败的处理函数   
        					Ext.Msg.alert('提示', '设备加载失败：'+ action.response.responseText);
        				}
        			});
        			p.setDisabled(false);
        		}
        	}
	});
	
	function equipRepair(){
		var repairObj=step4.getForm().getValues();
		var step5Form=step5.getForm();
		var invalidArray=new Array();
		var obj= step5Form.getValues();
		if(status_combo.getValue()==jobstatus.PROCESS){
			if(rtype_combo.getValue()==jobtype.REPAIR){
					if(!obj.ATime){
						invalidArray.push({id:'ATime', msg:'This field is needed!'})
					}
					if(!obj.RUcid){
						invalidArray.push({id:'RUcid', msg:'This field is needed!'})
					}
					if(!obj.DUcid){
						invalidArray.push({id:'DUcid', msg:'This field is needed!'})
					}
			}
			if(rtype_combo.getValue()==jobtype.OUTSOURCE){
				if(!obj.ATime){
					invalidArray.push({id:'ATime', msg:'This field is needed!'})
				}
				if(!obj.RUcid){
					invalidArray.push({id:'RUcid', msg:'This field is needed!'})
				}
				if(!obj.DUcid){
					invalidArray.push({id:'DUcid', msg:'This field is needed!'})
				}
				if(!obj.outsource){
					invalidArray.push({id:'outsource', msg:'This field is needed!'})
				}
				if(!obj.ETime){
					invalidArray.push({id:'ETime', msg:'This field is needed!'})
				}
			}
		}
		if(status_combo.getValue()==jobstatus.COMPLETE){
			if(!obj.FTime){
				invalidArray.push({id:'FTime', msg:'This field is needed!'})
			}
		}
		step5Form.markInvalid(invalidArray);
		if(invalidArray.length==0){
			step5Form.submit({
				    clientValidation: true,
				    url: Ext.ContextPath+'/equipRepair/equiprepair.do',
				    params:{
				    	ecode:ecode,
				    	jid:jid,
				    	rid:rid,
				    	descrip:repairObj.descrip,
				    	STime:repairObj.STime,
				    	AUcid:repairObj.AUcid
				    },
				    success: function(form, action) {
    					var ret=Ext.decode(action.response.responseText);
				    	if(ret.success){
				    		rtype=ret.rtype;
				    		status=ret.status;
				    		rid=ret.rid;
				    	}
				    	Ext.MessageBoxEx('友情提示', "数据更新成功");
				    },
				    failure: function(form, action) {
				        switch (action.failureType) {
				            case Ext.form.Action.CLIENT_INVALID:
							    //客户端数据验证失败的情况下，例如客户端验证邮件格式不正确的情况下提交表单  
							    Ext.MessageBoxEx('提示','数据错误，非法提交');  
				                break;
				            case Ext.form.Action.CONNECT_FAILURE:
							    //服务器指定的路径链接不上时  
							    Ext.MessageBoxEx('连接错误','指定路径连接错误!'); 
				                break;
				            case Ext.form.Action.SERVER_INVALID:
				            	//服务器端你自己返回success为false时  
							     Ext.MessageBoxEx('友情提示', "数据更新异常");
							     break;
							default:
								 //其它类型的错误  
		                         Ext.MessageBoxEx('警告', '服务器数据传输失败：'+action.response.responseText); 
							     break;
				       }
				    },
				    scope:this
				});
		}
	}
	
	Ext.create('Ext.container.Viewport',{
        layout:'fit',
        renderTo:'view-port',
	    items: [{xtype:'tabpanel',
	    	activeTab: 0,
	    	margin:'2px',
	    	plain: true,
	    	items:[step1,step2,step3,step4,step5]
    	}]
	});

	function statusRender(v){
		var diplayTxt="";
		statusStore.each(function(record) {
	        var value = record.get('value');
	        if (v === value) {
	            diplayTxt = record.get('text');
	            return false;
	        }
	    });
		return diplayTxt;
	}
});