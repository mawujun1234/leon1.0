Ext.onReady(function(){
		//var equipstatus={USING:0,NEW_STOCK:1,BACK_OUTSOURCE:2,BACK_REPAIR:3,TOREPAIRING:4,BACK_OTHER:5,OUT_SOURCING:12,REPAIRING:13,DISCARD:14,OTHER:255},estatus=0;
	
	var style_field=Ext.create('Ext.form.field.Display',{
		fieldLabel:'设备型号',name:'style',labelAlign:'right',hidden:true,submitValue:true
	});
	
	//step1
	var esubtype_combox=Ext.create('Ext.D.list.ComboBox',{
		id:'esubtype_combox',url:Ext.ContextPath+'/dataExtra/esubtypeList.do',fieldLabel:'设备子类型',name:'esubtype',labelAlign:'right',autoSelected:true,loadAuto:true,editable:false
	});
	var etype_combox=Ext.create('Ext.D.list.ComboBox',{
		url:Ext.ContextPath+'/dataExtra/etypeList.do',fieldLabel:'设备类型',name:'etype',cascade:true,cascadeId:'esubtype_combox',labelAlign:'right',autoSelected:true,loadAuto:true,editable:false
	});
	
	var totalprice_display=Ext.create('Ext.form.field.Display',{
		xtype:'displayfield',fieldLabel:'总价(元)',name:'totalprice',labelWidth:100,submitValue : true,labelAlign:'right',hidden:true
	});
	
	esubtype_combox.on('change',function(field,newValue,oldValue,exception){
		if(!!newValue&&newValue!=""){
			Ext.Ajax.request({
			    url: Ext.ContextPath+'/dataExtra/equipStyle.do',
			    params: {
			        etype:etype_combox.getValue(),
			        esubtype:newValue
			    },
			    success: function(response, opts){
			    	var ret=Ext.decode(response.responseText);
			    	if(ret.success){
			    		if(!style_field.isVisible()){
			    			style_field.setVisible(true);
			    		}
			    		style_field.setValue(ret.style);
			    	}else{
			    		Ext.Msg.alert('提示',ret.msg);
			    	}    
			    },
			    failure: function(response, opts){
			    	Ext.Msg.alert('提示',response.responseText);
			    }
			});
		}
	});
	

    var equipStore = Ext.create('Ext.data.Store', {
        autoDestroy: true,
        model: 'equipment',
        proxy: {
            type: 'memory'
        }
    });
	var equip_grid=Ext.create('Ext.grid.Panel',{
		flex:1,
		store:equipStore,
    	columns: [Ext.create('Ext.grid.RowNumberer'),
    	          {header: 'etype', dataIndex: 'etype',hideable:false,hidden:true},
    	          {header: '设备类型', dataIndex: 'typename',width:120},
    	          {header: 'esubtype', dataIndex: 'esubtype',hideable:false,hidden:true},
    	          {header: '设备子类型', dataIndex: 'subtypename',width:120},
    	          {header: 'sid', dataIndex: 'sid',hideable:false,hidden:true},
    	          {header: '设备型号', dataIndex: 'style',width:120},
    	          {header: '设备商', dataIndex: 'sname',width:120},
    	          {header: '数量', dataIndex: 'nums',width:70},
    	          {header: '单价(元)', dataIndex: 'price',width:70},
    	         // {header: '总价(元)', dataIndex: 'totalprice',width:70},
    	          //{header: 'stid', dataIndex: 'stid',hideable:false,hidden:true},
    	         // {header: '库房', dataIndex: 'stock',width:120},
    	          //{header: '库房描述', dataIndex: 'stmemo',flex:1},
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
              {text:'清空设备',
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
        items:[{xtype:'form',items:[{xtype:'columnbox',columnSize:4,items:[etype_combox,esubtype_combox,style_field,
		            		                                               {xtype:'listcombox',itemId:'supplier_field',url:Ext.ContextPath+'/dataExtra/supplierList.do',fieldLabel:'厂商',name:'sid',editable:false,allowBlank:false,emptyText:'未选择厂商',labelAlign:'right'}]},
                                    {xtype:'columnbox',columnSize:4,items:[{xtype:'numberfield',itemId:'nums_field',fieldLabel:'入库数目',name:'nums',minValue:1,labelWidth:100,allowBlank:false,labelAlign:'right',value:1},
                                    {xtype:'numberfield',itemId:'unitprice_field',fieldLabel:'单价(元)',name:'price',minValue:0,labelWidth:100,listeners:{change:countTotal},allowBlank:false,labelAlign:'right'},
									totalprice_display]}
		            		        //{xtype:'columnbox',columnSize:4,items:[{xtype:'listcombox',url:Ext.ContextPath+'/dataExtra/stockList.do',itemId:'stock_field',fieldLabel:'库房',name:'stid',allowBlank:false,emptyText:'未选择库房',labelAlign:'right'},{xtype:'textfield',name:'stmemo',fieldLabel:'库房描述',columnWidth:3/4,labelAlign:'right'}]}
		            		        ]},
        {layout:{type:'hbox',algin:'stretch'},items:[{flex:1,border:false,html:'<HR style="FILTER: alpha(opacity=100,finishopacity=0,style=3)" width="100%" color=#987cb9 SIZE=3>'},{xtype:'button',text:'添加',handler:addEquip,width:70,iconCls:'icon-add',margin:'0 5px 0 5px'}]},
        equip_grid,
        {html:'<HR style="FILTER: alpha(opacity=100,finishopacity=0,style=3)" width="100%" color=#987cb9 SIZE=3>'},
        //{html:'<img src="../images/error.gif" style="vertical-align:middle">&nbsp;库房人员应当根据采购单，对设备分类后，一次对同类设备批量“添加”入库，直到所有采购单设备根据设备类型都已经“添加”到入库清单后，可以选择“下一步”，进入到二维码生成步骤'}],
        {html:'<img src="../images/error.gif" style="vertical-align:middle">&nbsp;二维码的生成规则是 大类(2)+小类(2)+供应商(3)+日期(6)+流水号(5)，可以选择“下一步”，进入到二维码生成步骤'}],
        buttons:[{text:'下一步',handler:function(btn){
            if (equipStore.getCount()> 0) { 
            	var equiplist = new Array();
            	equipStore.each(function(record){
            		equiplist.push(record.data);
            	});
            	printStore.load({
            		params:{jsonStr:Ext.encode(equiplist)},
            		callback: function(records, operation, success) {
				        if(success){
							var cardview=step1.up("#equip_new");
							var layout=cardview.getLayout();
							layout['next']();
				        }
				    }
				});
            }else{
            	Ext.Msg.alert('提示','请先添加一个设备');
            }
		}}]
	});
	
	function countTotal(f,n,o,e){
		var form=f.up('form');
		var nums=form.down('#nums_field').getValue();
		var unitprice=form.down('#unitprice_field').getValue();
		if(!!nums&&!!unitprice&&nums!=""&&unitprice!=""){
			var total=nums*unitprice;
			if(!totalprice_display.isVisible()){
				totalprice_display.setVisible(true);
			}
			totalprice_display.setValue(total.toFixed(2));
		}
	}
	
	function addEquip(){
		var equipform=step1.down('form');
        var form=equipform.getForm();
		if(form.isValid()){
			var obj=form.getValues();
		    var record=new Ext.create('equipment',{
	            etype:obj.etype,
	            typename:etype_combox.getRawValue(),
	            esubtype:obj.esubtype,
	            subtypename:esubtype_combox.getRawValue(),
	            sid:obj.sid,
	            sname:equipform.down('#supplier_field').getRawValue(),
	            nums:obj.nums,
	            price:obj.price,
	            totalprice:obj.totalprice,
	            //stid:obj.stid,
	            //stock:equipform.down('#stock_field').getRawValue(),
	            style:obj.style,
	            //stmemo:obj.stmemo,
	            printed:0
		    })
			equipStore.add(record);
		}
	}
	
    var printStore = Ext.create('Ext.data.Store', {
        model: 'equipment',
        proxy: {
            type: 'ajax',
            actionMethods: {
		        create : 'POST',
		        read   : 'POST',
		        update : 'POST',
		        destroy: 'POST'
		    },
            url: Ext.ContextPath+'/qrcode/getBarCodeList.do',
            reader: {
                type: 'json',
                root: 'root'
            }
        }
    });
    
	//step2
	var printPanel=Ext.create('Ext.grid.Panel',{
		flex:1,
		title:'导出二维码',
		store:printStore,
		multiSelect: true,
		//selModel: Ext.create('Ext.selection.CheckboxModel',{mode:'MULTI'}),
    	columns: [Ext.create('Ext.grid.RowNumberer'),
    	          {header: '条形码', dataIndex: 'ecode',width:150},
    	          {header: 'etype', dataIndex: 'etype',hideable:false,hidden:true},
    	          {header: '设备类型', dataIndex: 'typename',width:120},
    	          {header: 'esubtype', dataIndex: 'esubtype',hideable:false,hidden:true},
    	          {header: '设备子类型', dataIndex: 'subtypename',width:120},
    	          {header: 'sid', dataIndex: 'sid',hideable:false,hidden:true},
    	          {header: '设备型号', dataIndex: 'style',width:120},
    	          {header: '设备商', dataIndex: 'sname',width:120},
    	          {header: '单价(元)', dataIndex: 'price',width:70}
    	          //{header: 'stid', dataIndex: 'stid',hideable:false,hidden:true},
    	          //{header: '库房', dataIndex: 'stock',width:120},
    	          //{header: '库房描述', dataIndex: 'stmemo',flex:1},
    	          //{header: '状态', dataIndex: 'printed',renderer:flagRender}
    	          ]
	});
	
	function flagRender(v){
        if (v == 1) {
        	return '<font color="red">已导出</font>';
        }
        return '未导出';
    }
	
	var step2=Ext.create('Ext.panel.Panel',{
        layout: {
            type:'vbox',
            padding:'5',
            align:'stretch'
        },
        //defaults:{margins:'0 0 5px 0',border:false},
        items:printPanel,
		buttons:[{text:'上一步',handler:function(btn){
			var cardview=btn.up("#equip_new");	
			var layout=cardview.getLayout();
			layout['prev']();
		}},{text:'导出',
			//handler:doPrint
			handler:function(){
				//var records=printPanel.getSelectionModel().getSelection();
				var records=printPanel.getStore().getRange();
				var len=records.length;
				if(len<1){
					Ext.Msg.alert('提示:','请选择一种设备');
					return;
				}
				var barcodes=[];
				for(var j=0;j<len;j++){
					barcodes.push(records[j].get('ecode'));
					records[j].set('printed',1);
				}
				Ext.getBody().mask("正在导出....");
				Ext.Ajax.request({
					url:Ext.ContextPath+'/qrcode/export.do',
					method:'POST',
					timeout:600000000,
					params:{barcodes:barcodes},
					success:function(response){
						var obj=Ext.decode(response.responseText);
						//var fileServicePath="http://"+location.hostname+":"+location.port+Ext.ContextPath+obj.root;
						//window.location.href = Ext.ContextPath+obj.root;
						var test =window.open(Ext.ContextPath+"/qrcode/download.do?fileName="+obj.root, "_blank");//这个方法就直接把这个TXT以浏览器的方式打开了 
						//test.document.execCommand("SaveAs");   
						//test.close(); 
						Ext.getBody().unmask();
					}
				});
				
			}
		}]
	});
	
	Ext.create('Ext.container.Viewport',{
        layout:'fit',
        itemId:'equip_new',
        renderTo:'view-port',
	    items:[step1,step2]
//       	items:{xtype:'tabpanel',
//	    	activeTab:0,
//	    	margin:'2px',
//	    	plain: true,
//	    	border:false,
//	    	//defaults:{layout:'card',border:false},
//	    	items:[{layout:'card',border:false,itemId:'equip_new',title:'新设备二维码',items:[step1,step2]},
//	    	       qrcodeRe]
//	    }
	});

//	var qrcodeRe=Ext.create('Ext.pages.qrcodeRe',{
//		title:'二维码重导'
//	});
//	Ext.create('Ext.container.Viewport',{
//        layout:'fit',
//        //itemId:'equip_new',
//        renderTo:'view-port',
//	    //items:[step1,step2]
//       	items:{xtype:'tabpanel',
//	    	activeTab:0,
//	    	margin:'2px',
//	    	plain: true,
//	    	border:false,
//	    	//defaults:{layout:'card',border:false},
//	    	items:[{layout:'card',border:false,itemId:'equip_new',title:'新设备二维码',items:[step1,step2]},
//	    	       qrcodeRe]
//	    }
//	});
	
});