//Ext.require("Ems.store.Barcode");
Ext.require("Ems.repair.Repair");
//Ext.require("Ems.store.BarcodeTree");
//Ext.require("Ems.store.BarcodeForm");
Ext.onReady(function(){
        
	var store_combox=Ext.create('Ext.form.field.ComboBox',{
	        fieldLabel: '<b>仓库</b>',
	        labelAlign:'right',
            labelWidth:55,
	        //xtype:'combobox',
	        //afterLabelTextTpl: Ext.required,
	        name: 'str_out_id',
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
			    	extraParams:{type:1,edit:true},
			    	url:Ext.ContextPath+"/store/queryCombo.do",
			    	reader:{
			    		type:'json',
			    		root:'root'
			    	}
			    }
		   })
	});
	var repair_combox=Ext.create('Ext.form.field.ComboBox',{
	        fieldLabel: '<b>维修中心</b>',
	        labelAlign:'right',
            labelWidth:60,
	        //xtype:'combobox',
	        //afterLabelTextTpl: Ext.required,
	        name: 'rpa_id',
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
			    	extraParams:{type:2,look:true},
			    	url:Ext.ContextPath+"/store/queryCombo.do",
			    	reader:{
			    		type:'json',
			    		root:'root'
			    	}
			    }
		   })
	});
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
				if(!repair_combox.getValue()){
					Ext.Msg.alert("消息","请先选择仓库!");
					return;
				}
			},
			change:equipScan
		}
	});
	
	var clear_button=Ext.create('Ext.button.Button',{
		text:'清除',
		margin:'0 0 0 5',
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
//	var memo_textfield=Ext.create('Ext.form.field.Text',{
//		labelAlign:'right',
//		fieldLabel: '备注',
//		labelWidth:55,
//		name:'memo',
//		flex:1,
//		allowBlank:true
//	});
	
	//var store_id_temp=null;//用来判断仓库的id有没有变
	function equipScan(field,newValue,oldValue,e){
//		if(!store_id_temp){
//			store_id_temp=store_combox.getValue();
//		} else if(store_id_temp!=store_combox.getValue()){
//			Ext.Msg.alert("消息","对不起，一次出库只能选择一个仓库.");
//			ecode_textfield.setValue("");
//			ecode_textfield.clearInvalid( );
//			return;
//		}
		
		var form= step1.down('form').getForm();
		if(newValue.length>=Ext.ecode_length){
		   if(field.isValid()){
			  // form.load({
		   	Ext.Ajax.request({
					params : {ecode:newValue,store_id:store_combox.getValue()},//传递参数   
					url : Ext.ContextPath+'/repair/getRepairVOByEcode.do',//请求的url地址   
					method : 'GET',//请求方式   
					success : function(response) {//加载成功的处理函数   
						var ret=Ext.decode(response.responseText);
						if(ret.success){
							if(ret.root.equipment_status!=5){//这是新设备入库的情况
								Ext.Msg.alert("消息","该设备为非\"入库待维修\"状态,不能添加到列表.");
								return;
							}
							//为新增的equipment添加仓库等其他信息
							ret.root.str_out_id=store_combox.getValue();
							ret.root.str_out_name=store_combox.getRawValue();
							ret.root.rpa_id=repair_combox.getValue();
							ret.root.rpa_name=repair_combox.getRawValue();
							
							var scanrecord = Ext.create('Ems.repair.Repair', ret.root);

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
								equipStore.insert(0, scanrecord);				
							}			
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
	
	
	var equipStore = Ext.create('Ext.data.Store', {
        autoDestroy: true,
        model: 'Ems.repair.Repair',
        proxy: {
            type: 'memory'
        }
    });
	var equip_grid=Ext.create('Ext.grid.Panel',{
		flex:1,
		store:equipStore,
    	columns: [Ext.create('Ext.grid.RowNumberer'),
    			  {header: '条码', dataIndex: 'ecode',width:150},
    	          {header: '设备类型', dataIndex: 'subtype_name',width:120},
    	          {header: '品名', dataIndex: 'prod_name'},
    	          {header: '品牌', dataIndex: 'brand_name',width:120},
    	          {header: '供应商', dataIndex: 'supplier_name'},
    	          {header: '设备型号', dataIndex: 'equipment_style',width:120},
    	          {header: '所在仓库', dataIndex: 'str_out_name'},
    	          {header: '维修中心', dataIndex: 'rpa_name'},
    	          //{header: '数量', dataIndex: 'serialNum',width:70},
    	          //{header: '单价(元)', dataIndex: 'unitPrice',width:70},
    	          
    	          
    	          //{header: 'stid', dataIndex: 'stid',hideable:false,hidden:true},
    	         // {header: '库房', dataIndex: 'stock',width:120},
    	          {header: '设备状态', dataIndex: 'equipment_status',width:100,renderer:function(value){
    	          	  if(value==4 || value==5){
	    	          	return '<font color="red">'+equipmentStatus[value]+'</font>';
	    	          } else {
	    	          		return equipmentStatus[value];
	    	          } 
    	          }},
    	          { header:'操作',
	                xtype: 'actioncolumn',
	                width: 70,
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
	            }],
        tbar:['<pan id="toolbar-title-text">当前入库记录</span>','->',
              {text:'清空列表中设备',
        	   iconCls:'icon-clearall',
        	   handler:function(){
        		   Ext.MessageBox.confirm('确认', '您确认要清除所有记录吗?', function(btn){
						if(btn=='yes'){
							equipStore.removeAll();
						}
					});
        	   }
        }]
	});
	
	

	
	var step1=Ext.create('Ext.panel.Panel',{
        layout: {
            type:'vbox',
            padding:'5',
            align:'stretch'
        },
        defaults:{margins:'0 0 5 0',border:false},
        items:[{xtype:'form',items:[{xtype:'fieldcontainer',layout: 'hbox',items:[store_combox,repair_combox,ecode_textfield,clear_button]},
                                    {xtype:'fieldcontainer',layout: 'hbox',items:[storeman_textfield,inDate_textfield]}
		            		        //{xtype:'columnbox',columnSize:4,items:[{xtype:'listcombox',url:Ext.ContextPath+'/dataExtra/stockList.do',itemId:'stock_field',fieldLabel:'库房',name:'stid',allowBlank:false,emptyText:'未选择库房',labelAlign:'right'},{xtype:'textfield',name:'stmemo',fieldLabel:'库房描述',columnWidth:3/4,labelAlign:'right'}]}
		            		        ]},
        {layout:{type:'hbox',algin:'stretch'},items:[{flex:1,border:false,html:'<HR style="FILTER: alpha(opacity=100,finishopacity=0,style=3)" width="100%" color=#987cb9 SIZE=3>'}
        //,{xtype:'button',text:'添加',handler:addEquip,width:70,iconCls:'icon-add',margin:'0 5px 0 5px'}
        ]},
        equip_grid,
        {html:'<HR style="FILTER: alpha(opacity=100,finishopacity=0,style=3)" width="100%" color=#987cb9 SIZE=3>'},
        //{html:'<img src="../images/error.gif" style="vertical-align:middle">&nbsp;库房人员应当根据采购单，对设备分类后，一次对同类设备批量“添加”入库，直到所有采购单设备根据设备类型都已经“添加”到入库清单后，可以选择“下一步”，进入到二维码生成步骤'}],
        {html:'<img src="../images/error.gif" style="vertical-align:middle">&nbsp;一次入库只能选择一个仓库'}],
        buttons:[{text:'生成维修单',handler:function(btn){
            if (equipStore.getCount()> 0) { 
            	Ext.getBody().mask("正在入库....");
            	var equipments = new Array();
            	equipStore.each(function(record){
            		equipments.push(record.data);
            	});

				Ext.Ajax.request({
					url:Ext.ContextPath+'/repair/newRepair.do',
					method:'POST',
					timeout:600000000,
					headers:{ 'Content-Type':'application/json;charset=UTF-8'},
					//params:{store_id:store_combox.getValue()},
					jsonData:equipments,
					success:function(response){
						var obj=Ext.decode(response.responseText);
						//store_id_temp=null;
						Ext.Msg.alert("消息","维修单创建成功!");
						equipStore.removeAll();
						Ext.getBody().unmask();
						store_combox.enable();
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