Ext.onReady(function(){
	
	Ext.tip.QuickTipManager.init();
	var ecode=null,jid=null,did=null;
	var jobtype={REPAIR:1,OUTSOURCE:2};
	var jobstatus={APPLY:0,COMPLETE:1,REJECT:2},status=0;
	var equipstatus={USING:0,NEW_STOCK:1,BACK_OUTSOURCE:2,BACK_REPAIR:3,TOREPAIRING:4,BACK_OTHER:5,OUT_SOURCING:12,REPAIRING:13,DISCARD:14,OTHER:255},estatus=0;
	var now=new Date();
	
	var ecode_field=Ext.create('Ext.form.field.Text',{
		name:'ecode',
		fieldLabel:'输入设备条形码',
		allowBlank:false,
		vtype:'alphanum',
		minLength:18,
		maxLength:18,
		msgTarget:'side',
		validateOnBlur:false,
		listeners:{
			blur:function(f,e){
				if(!f.getValue()||f.getValue()==''){
					f.clearInvalid();
				}
			}
		}
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
	    		buttons:[{text:'下一步',handler:doStep1}]
        	}],
        	listeners:{
        		activate:function(p,e){
        			ecode_field.reset();
        			ecode_field.focus();
        		},
        		beforeactivate:function(p,e){
        			if(!step2.isDisabled())
                    step2.setDisabled(true);
        			if(!step3.isDisabled())
                    step3.setDisabled(true);
        			if(!step4.isDisabled())
                    step4.setDisabled(true);
        		}
        	}
	});
	
	function doStep1(){
		    if(step1.getForm().isValid( )){
			   	 Ext.Ajax.request({
				        url :Ext.ContextPath+'/outWarehouse/getEquipInfo.do',
				        method : 'post',
				        params:{ecode:ecode_field.getValue()},
				        success : function(response, options) {
					    	var ret=Ext.decode(response.responseText);
					    	if(ret.success){
					    		var obj=ret.data;
					    		ecode=obj.ecode;
					    		jid=obj.lstJid;
					    		did=obj.did;
					    		estatus=obj.estatus;
					    		//alert('ecode:'+ecode+',jid:'+jid+',did:'+did+',estatus:'+estatus);
						    	step2.down('form').getForm().setValues(obj);
					    		var tabpanel=step1.up("tabpanel");
				    			tabpanel.setActiveTab(step2);
					    	}else{
					    		Ext.Msg.alert('提示','数据加载失败');
					    	}
				        },
				        failure : function() {
				        	 Ext.Msg.alert('提示','数据加载失败');
				        }
			  	 });
		    }
	}
	
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
	            reader: { root: 'list' }
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
	            reader: { root: 'list' }
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
		labelWidth:75,width:220,url:Ext.ContextPath+'/dataExtra/estatusList.do',fieldLabel:'设备状态',name:'estatus',loadAuto:true,readOnly:true
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
						     	   estatus_field]}],
			       buttons:[{text:'刷新',handler:loadEquipInfo}]
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
	    		}},{text:'下一步',itemId:'step1_next',handler:doStep2}]
        	}],
        	listeners:{
        		activate:function(p,e){
        			repStore.load({params:{ecode:ecode,jobtype:jobtype.REPAIR}});
		    		outStore.load({params:{ecode:ecode,jobtype:jobtype.OUTSOURCE}});
        		},
        		beforeactivate:function(p,e){
        			if(!step1.isDisabled())
        			step1.setDisabled(true);
        			if(estatus==equipstatus.DISCARD||(estatus==equipstatus.OTHER&&did>0)){
        				if(step3.isDisabled())
	        			step3.setDisabled(false);
        				if(step4.isDisabled())
	        			step4.setDisabled(false);
        				var nextBtn= step2.down('#step1_next')
	        			if(estatus==equipstatus.DISCARD){
	        				if(!nextBtn.isDisabled())
	        					nextBtn.setDisabled(true);
	        			}else{
	        				if(nextBtn.isDisabled())
	        				nextBtn.setDisabled(false);
	        			}
        			}
        		}
        	}
	});
	
	function loadEquipInfo(){
	   	 Ext.Ajax.request({
		        url : Ext.ContextPath+'/outWarehouse/getEquipInfo.do',
		        method : 'post',
		        params:{ecode:ecode},
		        success : function(response, options) {
			    	var ret=Ext.decode(response.responseText);
			    	if(ret.success){
			    		var obj=ret.data;
			    		ecode=obj.ecode;
			    		jid=obj.lstJid;
			    		did=obj.did;
			    		estatus=obj.estatus;
			    		//alert('ecode:'+ecode+',jid:'+jid+',did:'+did+',estatus:'+estatus);
				    	step2.down('form').getForm().setValues(obj);
	        			repStore.load({params:{ecode:ecode,jobtype:jobtype.REPAIR}});
			    		outStore.load({params:{ecode:ecode,jobtype:jobtype.OUTSOURCE}});
			    	}else{
			    		Ext.Msg.alert('提示','数据加载失败');
			    	}
		        },
		        failure : function() {
		        	 Ext.Msg.alert('提示','数据加载失败');
		        }
	  	 });
	}
	
	function doStep2(btn){
		if(estatus==14){
			Ext.Msg.alert('提示','该设备已报废')
		}else if(estatus==4||estatus==12||estatus==13){
			Ext.Msg.alert('提示','该设备存在其他未完成的操作,请先结束其他操作');
	    }else if(estatus==1){
	    	Ext.Msg.alert('提示','该设备为新设备');
	    }else if(estatus==255&&did==0){
	    	Ext.Msg.alert('提示','该设备还未入库');
	    }else{
			var tabpanel=btn.up("tabpanel");
			tabpanel.setActiveTab(step3);
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
	            items:[{width:500,items:{xtype:'displayfield',fieldLabel:'条形码',name:'ecode',submitValue:true},border:false},
	                   {border:true,bodyPadding:'5px',width:500,
		            	tbar:['<pan id="toolbar-title-text"><设备施工单></span>','->',{iconCls:'icon-search',text:'刷新施工单',handler:loadJobDetail},{iconCls:'icon-save',itemId:'step3_save_btn',text:'创建施工单',handler:addJoblist}],
		            	items:[{xtype:'columnbox',
			            		items:[{fieldLabel:'施工时间',xtype:'datefield',name:'jtime',format:'Y-m-d',allowBlank:false,minValue : now},
			            		       {xtype:'listcombox',url:Ext.ContextPath+'/dataExtra/clerkList.do',loadAuto:true,name:'ucid',fieldLabel:'施工人员',allowBlank:false}]},
		                        {xtype:'listcombox',url:Ext.ContextPath+'/dataExtra/poleList.do',loadAuto:true,fieldLabel:'杆位地址',name:'cpid',width:350},
		                        {xtype:'textarea',fieldLabel:'备注',name:'memo',width:480,labelAlign:'top'}]}]
        	    },
               {html:'<HR style="FILTER: alpha(opacity=100,finishopacity=0,style=3)" width="100%" color=#987cb9 SIZE=2>',
        		buttonAlign:'center',
	    		buttons:[{text:'上一步',handler:function(btn){
	    			var tabpanel=btn.up("tabpanel");
	    			tabpanel.setActiveTab(1);
	    		}},{text:'下一步',itemId:'step3_next',handler:doStep3}]
        	}],
        	listeners:{
        		activate:function(p,e){
        			if(jid>0){
        				loadJobDetail();
        			}
        		},
        		beforeactivate:function(p,e){
                    p.getForm().setValues({ecode:ecode});
                    var nextBtn=step3.down('#step3_next');
                    var saveBtn=step3.down('#step3_save_btn');
        			if(estatus==equipstatus.DISCARD){
        				if(!nextBtn.isDisabled())
        					nextBtn.setDisabled(true);
        				if(!saveBtn.isDisabled())
        					saveBtn.setDisabled(true);
        				step3.getForm().getFields( ).each(function(item){
        					if(!item.readOnly){
								item.setReadOnly(true);	
							}
        				})
        			}else{
        				if(nextBtn.isDisabled())
        					nextBtn.setDisabled(false);
        				if(saveBtn.isDisabled())
        					saveBtn.setDisabled(false);
        				step3.getForm().getFields( ).each(function(item){
        					if(item.readOnly){
								item.setReadOnly(false);	
							}
        				})
        			}
        		}
        	}
	});
	
	function doStep3(btn){
			if(jid>0){
				if(step3.getForm().isValid()){
					var tabpanel=btn.up("tabpanel");
	    			tabpanel.setActiveTab(step4);
				}else{
					Ext.MessageBox.confirm('确认', '该施工单可能已过期,确认要关联该施工单?', function(check){
						if(check=='yes'){
							var tabpanel=btn.up("tabpanel");
			    			tabpanel.setActiveTab(step4);
						}
					});
				}
			}else{
				Ext.MessageBoxEx('友情提示', "请先创建施工单");
			}
	}
	
	function loadJobDetail(){
		var form=step3.getForm();
	   	 Ext.Ajax.request({
		        url : Ext.ContextPath+'/equipRepair/getJobListInfo.do',
		        method : 'post',
		        params:{jid:jid,ecode:ecode},
		        success : function(response, options) {
			    	var ret=Ext.decode(response.responseText);
			    	if(ret.success){
			    		var form=step3.getForm();
			    		var obj=ret.data;
			    		var jtime=obj.jtime;
			    		if(jtime>0){
			    			jtime=jtime*1000;
			    			var time = new Date(jtime); 
			    		}
			    		obj.jtime=time;
				    	form.setValues(obj);
				    	var details=obj.details;
				    	if(details.length>0){
				    	   form.setValues({cpid:details[0].cpid,ecode:details[0].ecode});
				    	}
				    	step3.getForm().clearInvalid( );
			    	}else{
			    		Ext.Msg.alert('提示','数据加载失败');
			    	}
		        },
		        failure : function() {
		        	 Ext.Msg.alert('提示','数据加载失败');
		        }
	  	 });
	}
	
	function addJoblist(){
		var step3Form=step3.getForm();
	       if(step3Form.isDirty()){
	    	   step3Form.submit({
				    clientValidation: true,
				    url: Ext.ContextPath+'/equipRepair/newJoblist.do',
				    params:{jtype:99},
				    success: function(form, action) {
				    	var ret=Ext.decode(action.response.responseText);
				    	if(ret.success){
				    		jid=ret.jid;
				    	}
				    	//alert('ecode:'+ecode+',jid:'+jid+',did:'+did+',estatus:'+estatus);
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
	
	var statusStore=Ext.create('Ext.data.Store',{
	    fields: [{name: 'text', type: 'string'},
	    	     {name: 'value', type: 'int'}],
  	    proxy: {
  	        type: 'ajax',
  	        url : Ext.ContextPath+'/dataExtra/discardstatusList.do'
  	    },
  	    autoLoad:true
	});

	var status_combo=Ext.create('Ext.form.field.ComboBox',{
			fieldLabel:'报废状态',
		    store: statusStore,
		    queryMode: 'local',
		    displayField: 'text',
		    valueField: 'value',
		    value:'0',
		    name:'status',
		    editable:false
	   });
	
	var step4=Ext.create('Ext.form.Panel',{
		title:'设备报废',
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
	            		autoScroll:true,
		            	items:[{xtype:'columnbox',border:false,
    			   		        items:[{xtype:'displayfield',fieldLabel:'条形码',name:'ecode',submitValue:true},
    			   		               status_combo]},
		            	       {xtype:'fieldset',title:'设备报废',anchor:'100%',
			    				items:[{layout:'column',border:false,
			    			   		    items:[{xtype:'listcombox',url:Ext.ContextPath+'/dataExtra/clerkList.do',loadAuto:true,name:'DUcid',fieldLabel:'申请人',columnWidth:1/2,allowBlank:false},
			    			   		           {xtype:'datefield',format:'Y-m-d',name:'dtime',fieldLabel:'申请时间',columnWidth:1/2,margin:'0 0 0 5px',allowBlank:false,value:now,minValue : now}]},
			    			   		   {xtype:'numberfield',fieldLabel:'残值',name:'rvalue'},
			    			   		   {xtype:'textfield',fieldLabel:'故障原因',anchor:'100%',name:'reason',allowBlank:false}]},
			    			   {xtype:'textarea',name:'memo',fieldLabel:'备注',width:500,labelAlign:'top'}
		    				  ],
		    				  buttons:[{text:'刷新 ',handler:function(){
		    	        			if(did>0){
		    	        				loadDiscardInfo();
		    	        			}
		    				  }},{text:'报废确认',itemId:'step4_discard_btn',handler:discardCheck}]}]
        	 },
               {html:'<HR style="FILTER: alpha(opacity=100,finishopacity=0,style=3)" width="100%" color=#987cb9 SIZE=2>',
        		buttonAlign:'center',
	    		buttons:[{text:'返回',handler:function(btn){
	    			var tabpanel=btn.up("tabpanel");
	    			tabpanel.setActiveTab(0);
	    		}},{text:'上一步',handler:function(btn){
	    			var tabpanel=btn.up("tabpanel");
	    			tabpanel.setActiveTab(2);
	    		}}]
        	}],
        	listeners:{
        		activate:function(p,e){
        			if(did>0){
        				loadDiscardInfo();
        			}
        		},
        		beforeactivate:function(p,e){
        			p.getForm().setValues({ecode:ecode});
        			if(estatus==equipstatus.DISCARD){
        				var discardBtn=step4.down('#step4_discard_btn')
        				if(!discardBtn.isDisabled()){
        					discardBtn.setDisabled(true);
        				}
    					step4.getForm().getFields().each(function(item){
    						if(!item.readOnly){
    							item.setReadOnly(true);
    						}
    			    	});
        			}else{
        				var discardBtn=step4.down('#step4_discard_btn')
        				if(discardBtn.isDisabled()){
        					discardBtn.setDisabled(false);
        				}
    					step4.getForm().getFields().each(function(item){
    						if(item.readOnly){
    							item.setReadOnly(false);
    						}
    			    	});
        			}
        		}
        	}
	});
	
	function loadDiscardInfo(){
		step4.getForm().load({
			url : Ext.ContextPath+'/equipDiscard/getDiscardInfo.do',//请求的url地址   
			method : 'GET',//请求方式   
			params : {did:did},//传递参数   
			success : function(form, action) {//加载成功的处理函数   
		    	var ret=Ext.decode(action.response.responseText);
		    	if(!ret.success){
		    	   Ext.msg.alert('报废信息加载失败');
		    	}else{
		    		status=ret.data.status;
					step4.getForm().getFields().each(function(item){
						var itemName=item.getName();
						if(status===jobstatus.APPLY){
							if(itemName!='memo'&&itemName!='status'){
								if(!item.readOnly){
									item.setReadOnly(true);	
								}
							}
						}else{
							if(!item.readOnly){
								item.setReadOnly(true);	
							}
						}
			    	});
		    	}
			},
			failure : function(form, action) {//加载失败的处理函数   
				Ext.Msg.alert('提示', '设备加载失败：'+ action.response.responseText);
			}
		});
	}
	var discardWin=null;
	function discardCheck(){
		if(status_combo.getValue()!=jobstatus.APPLY){
			if(status==jobstatus.APPLY){
				if(!discardWin){
					discardWin=Ext.create('Ext.window.Window',{
						layout:'fit',
						width:300,
						hegith:250,
						closeAction:'hide',
						bodyStyle:'background-color:#FFFFFF',
						items:[{xtype:'form',
							padding:'5px',
							border:false,
							items:[{xtype:'listcombox',url:Ext.ContextPath+'/dataExtra/clerkList.do',loadAuto:true,name:'AUcid',fieldLabel:'审核人',allowBlank:false},
							       {xtype:'datefield',format:'Y-m-d',name:'atime',fieldLabel:'审核时间',allowBlank:false,value:now,minValue : now}]
							}],
						buttons:[{text:'确认',handler:function(){
							var obj_form=discardWin.down('form').getForm();
							if(obj_form.isValid()){
								var obj=obj_form.getValues();
								discardEquip({
							    	did:did,
							    	jid:jid,
							    	AUcid:obj.AUcid,
							    	atime:obj.atime
							    });
								discardWin.close();
							}
						}},{text:'取消',handler:function(){
							discardWin.close();
						}}]
					});
				}
			   discardWin.show();
			}else{
				Ext.MessageBoxEx('提示','该设备报废操作已经结束');
			}
		}else{
			discardEquip({
		    	did:did,
		    	jid:jid
		    })
		}
	}
	
	function discardEquip(submit_params){
		var step5Form=step4.getForm();
		step5Form.submit({
		    clientValidation: true,
		    url: Ext.ContextPath+'/equipDiscard/equipdiscard.do',
		    params:submit_params,
		    success: function(form, action) {
				var ret=Ext.decode(action.response.responseText);
		    	if(ret.success){
					var obj=ret.data;
		    		did=obj.did;
		    		status=obj.status;
					step4.getForm().getFields().each(function(item){
						var itemName=item.getName();
						if(status===jobstatus.APPLY&&itemName!='memo'&&itemName!='status')
						if(!item.readOnly){
							item.setReadOnly(true);	
						}
			    	});
		    	}
		    	//alert('ecode:'+ecode+',jid:'+jid+',did:'+did+',estatus:'+estatus);
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
	
	Ext.create('Ext.container.Viewport',{
        layout:'fit',
        renderTo:'view-port',
	    items: [{xtype:'tabpanel',
	    	activeTab: 0,
	    	margin:'2px',
	    	plain: true,
	    	items:[step1,step2,step3,step4],
	    	listeners:{
	    		beforetabchange:function( p, n, o, e ){
	    			if(n.isDisabled( )){
	    				n.setDisabled(false);
	    			}
	    		}
	    	}
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