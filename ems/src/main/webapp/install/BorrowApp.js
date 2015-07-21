Ext.require("Ems.install.BorrowEditGrid");
Ext.require("Ems.install.WorkUnitEquipmentWindow");
Ext.require("Ems.install.StoreEquipmentWindow");
Ext.require("Ems.install.BorrowList");
Ext.onReady(function(){
     var store_combox=Ext.create('Ext.form.field.ComboBox',{
	        fieldLabel: '<b>仓库</b>',
	        labelAlign:'right',
            labelWidth:55,
	        //xtype:'combobox',
	        //afterLabelTextTpl: Ext.required,
	        name: 'store_id',
		    displayField: 'name',
		    valueField: 'id',
	        allowBlank: false,
	        store:Ext.create('Ext.data.Store', {
		    	fields: ['id', 'name'],
			    proxy:{
			    	type:'ajax',
			    	extraParams:{type:[1,3],edit:true},
			    	url:Ext.ContextPath+"/store/queryCombo.do",
			    	reader:{
			    		type:'json',
			    		root:'root'
			    	}
			    }
		   })
	    });
	var queryStoEquip_button=Ext.create('Ext.button.Button',{
		text:'库存',
		margin:'0 0 0 5',
		handler:function(){
			var store_id=store_combox.getValue();
			if(!store_id){
				Ext.Msg.alert("消息","请先选择一个仓库");
				return;
			}
			showStoreEquipment(store_id,store_combox.getRawValue());
		}
	});
	//显示某个作业单位的手持设备情况
	function showStoreEquipment(store_id,store_name) {
		var win=Ext.create('Ems.install.StoreEquipmentWindow',{
			title:'库存设备情况',
			width:800,
			height:400,
			closeAction:'hide',
			constrainHeader:true,
			modal:true,
			store_id:store_id,
			store_name:store_name
		});
		win.show();	
	}
        
	var workUnit_combox=Ext.create('Ext.form.field.ComboBox',{
	        fieldLabel: '<b>作业单位</b>',
	        labelAlign:'right',
            labelWidth:60,
	        //xtype:'combobox',
	        //afterLabelTextTpl: Ext.required,
	        name: 'workUnit_id',
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
			    	url:Ext.ContextPath+"/workUnit/queryCombo.do",
			    	reader:{
			    		type:'json',
			    		root:'root'
			    	}
			    }
		   })
	    });
	var queryWorkUnitEquip_button=Ext.create('Ext.button.Button',{
		text:'持有',
		margin:'0 0 0 5',
		handler:function(){
			var workUnit_id=workUnit_combox.getValue();
			if(!workUnit_id){
				Ext.Msg.alert("消息","请先选择一个作业单位");
				return;
			}
			showWorkUnitEquipment(workUnit_id);
		}
	});
	//显示某个作业单位的手持设备情况
	function showWorkUnitEquipment(workUnit_id) {
		var win=Ext.create('Ems.install.WorkUnitEquipmentWindow',{
			title:'作业单位手持设备情况',
			width:800,
			height:400,
			constrainHeader:true,
			closeAction:'hide',
			modal:true,
			workUnit_id:workUnit_id
		});
		win.show();	
	}
	
	var project_combox=Ext.create('Ems.baseinfo.ProjectCombo',{
		width:500,
		allowBlank: false
	});
	
	var ecode_textfield=Ext.create('Ext.form.field.Text',{
		labelAlign:'right',
		name:'ecode',
		fieldLabel: '输入设备条码',
		minLength:Ext.ecode_length,
		maxLength:Ext.ecode_length,
		length:Ext.ecode_length,
		selectOnFocus:true,
		labelWidth:80,
		width:250,
		allowBlank:true,
		listeners:{
			blur:function(f,e){
				if(!f.getValue()||f.getValue()==''){
					f.clearInvalid();
				}
			},
			focus:function(){
				if(!workUnit_combox.getValue()){
					Ext.Msg.alert("消息","请先选择作业单位!");
					return;
				}
			},
			change:equipScan
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
	var storeman_textfield=Ext.create('Ext.form.field.Text',{
		labelAlign:'right',
		labelWidth:55,
		fieldLabel: '经办人',
		name:'operater',
		readOnly:true,
		allowBlank:false,
		value:loginName
	});
	
	var inDate_textfield=Ext.create('Ext.form.field.Text',{
		labelAlign:'right',
		fieldLabel: '时间',
		labelWidth:55,
		name:'operateDate',
		readOnly:true,
		allowBlank:false,
		value:Ext.Date.format(new Date(),'Y-m-d')
	});
	var memo_textfield=Ext.create('Ext.form.field.Text',{
		labelAlign:'right',
		fieldLabel: '备注',
		labelWidth:55,
		name:'memo',
		flex:1,
		allowBlank:true
	});
	
	var select_edit_borrow=Ext.create('Ext.button.Button',{
		text:'选择编辑中的借用单',
		handler:function(){
			var borrowEdtiGrid=Ext.create('Ems.install.BorrowEditGrid',{
				listeners:{
					itemdblclick:function( view, record, item, index, e, eOpts ) {
						borrow_id=record.get("id");//全局变量保存当前的订单
						reloadBorrow_content(record);
						win.close();
					}
				}
			});
			var win=Ext.create('Ext.window.Window',{
				layout:'fit',
				title:'双击选择',
				items:[borrowEdtiGrid],
				modal:true,
				width:500,
				height:360
			});
			win.show();
		
		}
	});
	
	function reloadBorrow_content(borrow) {
//		store_combox
//		workUnit_combox
//		project_combox

		store_combox.getStore().load();
		workUnit_combox.getStore().load();
		//borrowType_combox.getStore().load();
		
		var form= step1.down('form').getForm();
		form.loadRecord(borrow);
		var project_model= project_combox.getStore().createModel({id:borrow.get("project_id"),name:borrow.get("project_name")});
		project_combox.setValue(project_model);
		
		//获取领用单的明细数据
		Ext.Ajax.request({
			url:Ext.ContextPath+"/borrow/queryList.do",
			method:'POST',
			params:{borrow_id:borrow.get("id")},
			success:function(response){
				var obj=Ext.decode(response.responseText);
				equipStore.removeAll();
				equipStore.loadData(obj.root);
			}
		})
	}
	
	//var store_id_temp=null;//用来判断仓库的id有没有变
	//var workUnit_id_temp=null;
	function equipScan(field,newValue,oldValue,e){
		
		var form= step1.down('form').getForm();
		if(newValue.length>=Ext.ecode_length){
		   if(field.isValid()){
			  // form.load({
		   	Ext.Ajax.request({
					params : {ecode:newValue,store_id:store_combox.getValue()},//传递参数   
					url : Ext.ContextPath+'/borrow/getEquipmentByEcode.do',//请求的url地址   
					method : 'GET',//请求方式   
					success : function(response) {//加载成功的处理函数   
						var ret=Ext.decode(response.responseText);
						if(ret.success){
							//alert(ret.root.status);
							if(ret.root.status!='in_storage'){//这是新设备入库的情况
								Ext.Msg.alert("消息","该设备为非可用库存设备,不能添加到出库列表.");
								return;
							}
							//为新增的equipment添加仓库等其他信息
							ret.root.workUnit_id=workUnit_combox.getValue();
							ret.root.workUnit_name=workUnit_combox.getRawValue();
							ret.root.store_id=store_combox.getValue();
							ret.root.store_name=store_combox.getRawValue();
							//ret.root.memo=memo_textfield.getValue();
							//ret.root.outStore_type=type_radio.getValue().type;
							var scanrecord = Ext.create('Ems.install.BorrowList', ret.root);

							ecode_textfield.setValue("");
							ecode_textfield.clearInvalid( );

							var exist=false;
							equipStore.each(function(record){
								if(newValue==record.get('ecode')){
								    exist=true;
								    return !exist;
								}
							});
							if(exist){
								Ext.Msg.alert('提示','该设备已经存在');
							}else{
								//equipStore.insert(0, scanrecord);	
								//equipStore.add(scanrecord);	
								equipStore.insert(0, scanrecord);	
							    equip_grid.getView().refresh();	
								toolbar_title_text_num.update(""+equipStore.getCount());
							}	
							workUnit_combox.disable();
							store_combox.disable();
						}
					}
//					failure : function(response) {//加载失败的处理函数   
//						Ext.Msg.alert('提示', '设备加载失败：'+ response.responseText);
//						ecode_textfield.setValue("");
//						ecode_textfield.clearInvalid( );
//					}
				});
		   }
		}else{
			//form.reset();
		}
	}
	
	//==========================================================================================
	
	 var toolbar_title_text_num=Ext.create('Ext.form.Label',{
    	html:"0"
    });
	var equipStore = Ext.create('Ext.data.Store', {
        autoDestroy: true,
        model: 'Ems.install.BorrowList',
        proxy: {
            type: 'memory'
        }
    });
	var equip_grid=Ext.create('Ext.grid.Panel',{
		flex:1,
		store:equipStore,
    	columns: [Ext.create('Ext.grid.RowNumberer'),
    				{ header:'操作',
	                xtype: 'actioncolumn',
	                width: 50,
	                items: [{
	                    icon   : '../images/delete.gif',  // Use a URL in the icon config
	                    tooltip: '删除',
	                    handler: function(grid, rowIndex, colIndex) {
	                        var rec = equipStore.getAt(rowIndex);
	                        Ext.MessageBox.confirm('确认', '您确认要删除该记录吗?', function(btn){
	                        	if(btn=='yes'){
	                        		equipStore.remove(rec);
	                        	}
	                        });
	                    }
	                }]
	            },
    			  {header: '条码', dataIndex: 'ecode',width:150},
    	          {header: '设备类型', dataIndex: 'subtype_name',width:120},
    	          {header: '品名', dataIndex: 'prod_name'},
    	          {header: '品牌', dataIndex: 'brand_name',width:120},
    	          {header: '供应商', dataIndex: 'supplier_name'},
    	          {header: '设备型号', dataIndex: 'style',width:120},
    	          {dataIndex:'prod_spec',header:'规格',flex:1,renderer:function(value,metadata,record){
						metadata.tdAttr = "data-qtip='" + value+ "'";
					    return value;
						}
				  }
    	          //{header: '仓库', dataIndex: 'store_name'},
    	         // {header: '状态', dataIndex: 'status_name',width:100}
    	          ],
        tbar:['<pan id="toolbar-title-text">当前借用记录:</span>',toolbar_title_text_num,'->',
              {text:'清空所有的设备',
        	   iconCls:'icon-clearall',
        	   handler:function(){
        		   Ext.MessageBox.confirm('确认', '您确认要清除所有记录吗?', function(btn){
						if(btn=='yes'){
							equipStore.removeAll();
							workUnit_combox.enable();
							store_combox.enable();
						}
					});
        	   }
        }]
	});
	
	
	var borrow_id=null;
	var step1=Ext.create('Ext.panel.Panel',{
        layout: {
            type:'vbox',
            padding:'5',
            align:'stretch'
        },
        defaults:{margins:'0 0 5 0',border:false},
        items:[{xtype:'form',items:[{xtype:'fieldcontainer',layout: 'hbox',items:[store_combox,queryStoEquip_button,workUnit_combox,queryWorkUnitEquip_button,ecode_textfield,clear_button]},
                                    {xtype:'fieldcontainer',layout: 'hbox',items:[project_combox]},
                                    {xtype:'fieldcontainer',layout: 'hbox',items:[storeman_textfield,inDate_textfield,memo_textfield]}
		            		        //{xtype:'columnbox',columnSize:4,items:[{xtype:'listcombox',url:Ext.ContextPath+'/dataExtra/stockList.do',itemId:'stock_field',fieldLabel:'库房',name:'stid',allowBlank:false,emptyText:'未选择库房',labelAlign:'right'},{xtype:'textfield',name:'stmemo',fieldLabel:'库房描述',columnWidth:3/4,labelAlign:'right'}]}
		            		        ]},
        {layout:{type:'hbox',algin:'stretch'},items:[select_edit_borrow,{flex:1,border:false,html:'<HR style="FILTER: alpha(opacity=100,finishopacity=0,style=3)" width="100%" color=#987cb9 SIZE=3>'}
        //,{xtype:'button',text:'添加',handler:addEquip,width:70,iconCls:'icon-add',margin:'0 5px 0 5px'}
        ]},
        equip_grid,
        {html:'<HR style="FILTER: alpha(opacity=100,finishopacity=0,style=3)" width="100%" color=#987cb9 SIZE=3>'},
        //{html:'<img src="../images/error.gif" style="vertical-align:middle">&nbsp;库房人员应当根据采购单，对设备分类后，一次对同类设备批量“添加”入库，直到所有采购单设备根据设备类型都已经“添加”到入库清单后，可以选择“下一步”，进入到二维码生成步骤'}],
        {html:'<img src="../images/error.gif" style="vertical-align:middle">&nbsp;'}],
        buttons:[{text:'保存及打印',handler:function(btn){
        	var form= step1.down('form').getForm();
        	if(!form.isValid()){
        		alert("请在出现红框的地方选择值!");
        		return;
        	}
            if (equipStore.getCount()> 0) { 
            	Ext.getBody().mask("正在执行....");
            	var equipments = new Array();
            	equipStore.each(function(record){
            		equipments.push(record.data);
            	});
            	
				Ext.Ajax.request({
					url:Ext.ContextPath+'/borrow/saveAndPrint.do',
					method:'POST',
					timeout:600000000,
					headers:{ 'Content-Type':'application/json;charset=UTF-8'},
					params:{memo:memo_textfield.getValue(),store_id:store_combox.getValue(),workUnit_id:workUnit_combox.getValue()
					,project_id:project_combox.getValue()
					,borrow_id:borrow_id
					
					},
					jsonData:equipments,
					//params:{jsonStr:Ext.encode(equiplist)},
					success:function(response){
						//store_id_temp=null;//用来判断仓库的id有没有变
						//workUnit_id_temp=null;
						var obj=Ext.decode(response.responseText);
						borrow_id=obj.root;
						//Ext.Msg.alert("消息","借用出库完成!");
						Ext.Msg.confirm("消息","借用出库完成,是否要打印该借用单?",function(btn){
							if(btn=='yes'){
								window.open("/borrow/equipmentOutStorePrint.do?borrow_id="+obj.root,"_blank");
							}
						});
						//equipStore.removeAll();
						Ext.getBody().unmask();
						//workUnit_combox.enable();
						//store_combox.enable();
					},
					failure:function(){
						Ext.getBody().unmask();
					}
				});
            }else{
            	Ext.Msg.alert('提示','请先添加一个设备');
            }
		}},{text:'借用出库',handler:function(btn){
        	var form= step1.down('form').getForm();
        	if(!form.isValid()){
        		alert("请在出现红框的地方选择值!");
        		return;
        	}
            if (equipStore.getCount()> 0) { 
            	Ext.getBody().mask("正在执行....");
            	var equipments = new Array();
            	equipStore.each(function(record){
            		equipments.push(record.data);
            	});
            	
				Ext.Ajax.request({
					url:Ext.ContextPath+'/borrow/equipmentOutStore.do',
					method:'POST',
					timeout:600000000,
					headers:{ 'Content-Type':'application/json;charset=UTF-8'},
					params:{memo:memo_textfield.getValue(),store_id:store_combox.getValue(),workUnit_id:workUnit_combox.getValue()
					,project_id:project_combox.getValue()
					,borrow_id:borrow_id
					},
					jsonData:equipments,
					//params:{jsonStr:Ext.encode(equiplist)},
					success:function(response){
						//store_id_temp=null;//用来判断仓库的id有没有变
						//workUnit_id_temp=null;
						var obj=Ext.decode(response.responseText);
						
						//Ext.Msg.alert("消息","借用出库完成!");
						Ext.Msg.confirm("消息","借用出库完成,是否要打印该借用单?",function(btn){
							if(btn=='yes'){
								window.open("/borrow/equipmentOutStorePrint.do?borrow_id="+obj.root,"_blank");
							}
						});
						equipStore.removeAll();
						Ext.getBody().unmask();
						workUnit_combox.enable();
						store_combox.enable();
						borrow_id=null;
					},
					failure:function(){
						Ext.getBody().unmask();
					}
				});
            }else{
            	Ext.Msg.alert('提示','请先添加一个设备');
            }
		}}]
	});
	
	
	
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'fit',
		items:[step1]
	});

});