//Ext.require("Ems.store.Barcode");
//Ext.require("Ems.install.WorkUnitEquipmentWindow");
//Ext.require("Ems.install.StoreEquipmentWindow");
Ext.require("Ems.install.BorrowList");
Ext.onReady(function(){

     var store_combox=Ext.create('Ext.form.field.ComboBox',{
	        fieldLabel: '<b>入库仓库</b>',
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
			    	extraParams:{type:[1,3],edit:true},
			    	url:Ext.ContextPath+"/store/queryCombo.do",
			    	reader:{
			    		type:'json',
			    		root:'root'
			    	}
			    }
		   })
	    });
//
//	var workUnit_combox=Ext.create('Ext.form.field.ComboBox',{
//	        fieldLabel: '<b>作业单位</b>',
//	        labelAlign:'right',
//            labelWidth:60,
//	        //xtype:'combobox',
//	        //afterLabelTextTpl: Ext.required,
//	        name: 'workUnit_id',
//		    displayField: 'name',
//		    valueField: 'id',
//		    //queryParam: 'name',
//    		//queryMode: 'remote',
//    		//triggerAction: 'query',
//    		//minChars:-1,
//		    //trigger1Cls: Ext.baseCSSPrefix + 'form-clear-trigger',
//		    //trigger2Cls: Ext.baseCSSPrefix + 'form-arrow-trigger',//'form-search-trigger',
//			//onTrigger1Click : function(){
//			//    var me = this;
//			//    me.setValue('');
//			//},
//	        allowBlank: false,
//	        store:Ext.create('Ext.data.Store', {
//		    	fields: ['id', 'name'],
//			    proxy:{
//			    	type:'ajax',
//			    	url:Ext.ContextPath+"/workUnit/queryCombo.do",
//			    	reader:{
//			    		type:'json',
//			    		root:'root'
//			    	}
//			    }
//		   })
//	    });

	
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
//				if(!workUnit_combox.getValue()){
//					Ext.Msg.alert("消息","请先选择作业单位!");
//					return;
//				}
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
//	var memo_textfield=Ext.create('Ext.form.field.Text',{
//		labelAlign:'right',
//		fieldLabel: '备注',
//		labelWidth:55,
//		name:'memo',
//		flex:1,
//		allowBlank:true
//	});
	
	//var store_id_temp=null;//用来判断仓库的id有没有变
	//var workUnit_id_temp=null;
	function equipScan(field,newValue,oldValue,e){
		
		var form= step1.down('form').getForm();
		if(newValue.length>=Ext.ecode_length){
		   if(field.isValid()){
			  // form.load({
		   	Ext.Ajax.request({
					params : {ecode:newValue,store_id:store_combox.getValue()},//传递参数   
					url : Ext.ContextPath+'/borrow/getBorrowListVOByEcode.do',//请求的url地址   
					method : 'GET',//请求方式   
					success : function(response) {//加载成功的处理函数   
						var ret=Ext.decode(response.responseText);
						if(ret.success){
						
							
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
								equipStore.insert(0, scanrecord);				
							}
							//workUnit_combox.disable();
							store_combox.disable();
						}
					}
				});
		   }
		}else{
			//form.reset();
		}
	}
	
	//==========================================================================================
	
	
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
	              {header: '借用单编号', dataIndex: 'borrow_id',width:150},
    			  {header: '条码', dataIndex: 'ecode',width:150},
    	          {header: '设备类型', dataIndex: 'subtype_name',width:120},
    	          {header: '品名', dataIndex: 'prod_name'},
    	          {header: '品牌', dataIndex: 'brand_name',width:120},
    	          {header: '供应商', dataIndex: 'supplier_name'},
    	          {header: '设备型号', dataIndex: 'style',width:120},
    	          {header:'规格',dataIndex:'prod_spec',flex:1,renderer:function(value,metadata,record){
						metadata.tdAttr = "data-qtip='" + value+ "'";
					    return value;
						}
				  }
    	          ],
        tbar:['<pan id="toolbar-title-text">当前入库记录</span>','->',
              {text:'清空所有的设备',
        	   iconCls:'icon-clearall',
        	   handler:function(){
        		   Ext.MessageBox.confirm('确认', '您确认要清除所有记录吗?', function(btn){
						if(btn=='yes'){
							equipStore.removeAll();
							//workUnit_combox.enable();
							store_combox.enable();
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
        items:[{xtype:'form',items:[{xtype:'fieldcontainer',layout: 'hbox',items:[store_combox,ecode_textfield,clear_button]},
                                    {xtype:'fieldcontainer',layout: 'hbox',items:[storeman_textfield,inDate_textfield]}
		            		        //{xtype:'columnbox',columnSize:4,items:[{xtype:'listcombox',url:Ext.ContextPath+'/dataExtra/stockList.do',itemId:'stock_field',fieldLabel:'库房',name:'stid',allowBlank:false,emptyText:'未选择库房',labelAlign:'right'},{xtype:'textfield',name:'stmemo',fieldLabel:'库房描述',columnWidth:3/4,labelAlign:'right'}]}
		            		        ]},
        {layout:{type:'hbox',algin:'stretch'},items:[{flex:1,border:false,html:'<HR style="FILTER: alpha(opacity=100,finishopacity=0,style=3)" width="100%" color=#987cb9 SIZE=3>'}
        //,{xtype:'button',text:'添加',handler:addEquip,width:70,iconCls:'icon-add',margin:'0 5px 0 5px'}
        ]},
        equip_grid,
        {html:'<HR style="FILTER: alpha(opacity=100,finishopacity=0,style=3)" width="100%" color=#987cb9 SIZE=3>'},
        //{html:'<img src="../images/error.gif" style="vertical-align:middle">&nbsp;库房人员应当根据采购单，对设备分类后，一次对同类设备批量“添加”入库，直到所有采购单设备根据设备类型都已经“添加”到入库清单后，可以选择“下一步”，进入到二维码生成步骤'}],
        {html:'<img src="../images/error.gif" style="vertical-align:middle">&nbsp;可以多张借用单据同时归还,不用管谁借的。'}],
        buttons:[{text:'借用返还',handler:function(btn){
        	var form= step1.down('form').getForm();
        	if(!form.isValid()){
        		alert("请在出现红框的地方选择值!");
        		return;
        	}
            if (equipStore.getCount()> 0) { 
            	Ext.Msg.confirm("消息",'确认要入库吗?',function(btn){
            	if(btn=='yes'){
            		
            	Ext.getBody().mask("正在入库....");
            	var equipments = new Array();
            	equipStore.each(function(record){
            		equipments.push(record.data);
            	});
            	
				Ext.Ajax.request({
					url:Ext.ContextPath+'/borrow/borrowReturn.do',
					method:'POST',
					timeout:600000000,
					headers:{ 'Content-Type':'application/json;charset=UTF-8'},
					params:{store_id:store_combox.getValue()},
					jsonData:equipments,
					//params:{jsonStr:Ext.encode(equiplist)},
					success:function(response){
						//store_id_temp=null;//用来判断仓库的id有没有变
						//workUnit_id_temp=null;
						var obj=Ext.decode(response.responseText);
						//注意备注是加在单个设备上的，还是先取消掉备注
						Ext.Msg.alert("消息","设备返库完成!");
						equipStore.removeAll();
						Ext.getBody().unmask();
						
						//workUnit_combox.enable();
						store_combox.enable();
					},
					failure:function(){
						Ext.getBody().unmask();
					}
				});
				
            	}//if(btn=='yes'){
				});//Ext.Msg.confirm("消息","",function(){
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