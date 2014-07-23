//Ext.Loader.setConfig({
//    enabled: true
//});

//Ext.Loader.setPath('Ext.ux', '../ext-4.0.7/examples/ux/');

Ext.require([
  'Ext.ux.form.MultiSelect',
  'Ext.ux.form.ItemSelector'
]);

Ext.onReady(function(){
	
	//var equipstatus={USING:0,NEW_STOCK:1,BACK_OUTSOURCE:2,BACK_REPAIR:3,TOREPAIRING:4,BACK_OTHER:5,OUT_SOURCING:12,REPAIRING:13,DISCARD:14,OTHER:255},estatus=0;
	
    Ext.define('equipstock',{
        extend: 'Ext.data.Model',
        fields: [
             {name: 'SID', mapping: 'sid'},
             {name: 'SNAME', mapping: 'sname'},
             {name: 'NUMSNEW', mapping: 'numsNew'},
             {name: 'STID', mapping: 'stid'},
             {name: 'STOCK', mapping: 'stock'},
             {name: 'STMEMO', mapping: 'stmemo'}
        ]
    });
    var equipstockStore = Ext.create('Ext.data.Store', {
        model: 'equipstock',
        proxy: {
            type: 'ajax',
            url: Ext.ContextPath+'/outWarehouse/getEquipStockList.do',
            reader: {
                type: 'json',
                root: 'root'
            }
        }
    });
    Ext.define('impstock',{
        extend: 'Ext.data.Model',
        fields: [
	         {name: 'BATCHID', mapping: 'batchid'},
	         {name: 'NUMS', mapping: 'nums'},
             {name: 'UNITPRICE', mapping: 'unitPrice'},
             {name: 'ITIME', mapping: 'itime'},
             {name: 'BTIME', mapping: 'btime'},
             {name: 'MEMO', mapping: 'memo'}
        ]
    });
    var impstockStore = Ext.create('Ext.data.Store', {
        model: 'impstock',
        proxy: {
            type: 'ajax',
            url: Ext.ContextPath+'/outWarehouse/getImpStockList.do',
            reader: {
                type: 'json',
                root:'root'
            }
        }
    });
    var equipstock_count_item = Ext.create('Ext.toolbar.TextItem', {text: '合计: 0'});

	var equipstock_panel=Ext.create('Ext.panel.Panel',{
		id:'equip_stock_panel',
		title:'新设备',
        layout:'border',
        border:false,
        items:[{
        	region:'east',
        	title:'设备详细入库信息',
        	split:true,
        	width:380,
        	xtype:'grid',
        	store:impstockStore,
        	columns: [{header: '批次',dataIndex: 'BATCHID',width:55},
        	          {header: '数量', dataIndex: 'NUMS',width:55},
        	          {header: '单价(元)', dataIndex: 'UNITPRICE',width:65},
        	          {header: '采购时间', dataIndex: 'BTIME',width:75},
        	          {header: '入库时间', dataIndex: 'ITIME',width:75,hidden:true},
        	          {header: '备注', dataIndex: 'MEMO',flex:1}],
           viewConfig:{
        	  autoFill:true
           },
           autoRender:true
        },{
        	xtype:'grid',
        	region:'center',
        	itemId:'equip_stock_grid',
        	store:equipstockStore,
        	columns: [{header: 'SID',dataIndex: 'SID',hidden:true,hideable:false},
        	          {header: '设备供应商',dataIndex: 'SNAME'},
        	          {header: '库存量', dataIndex: 'NUMSNEW'},
        	          {header: 'STID',dataIndex: 'STID',hidden:true,hideable:false},
        	          {header: '库房', dataIndex: 'STOCK'},
        	          {header: '存放位置', dataIndex: 'STMEMO',flex:1}],
	        bbar: Ext.create('Ext.toolbar.Toolbar', {
	              items: [' ',equipstock_count_item]
	         }),
	         listeners:{
	        	 select:function(rm,r,i,e){
	        		 impstockStore.load({
	        			 params:{
	        				 etype:etype_combox.getValue(),
	        				 esubtype:esubtype_combox.getValue(),
	        				 stid:r.get('STID'),
	        				 sid:r.get('SID')
	        			 }
	        		 });
	        	 }
	         }
        }]
	});
	
    Ext.define('equip',{
        extend: 'Ext.data.Model',
        fields: [
	         {name: 'SID', mapping: 'sid'},
	         {name: 'SNAME', mapping: 'sname'},
             {name: 'ECODE', mapping: 'ecode'},
             {name: 'STID', mapping: 'stid'},
             {name: 'STOCK', mapping: 'stock'},
             {name: 'STMEMO', mapping: 'stmemo'}
        ]
    });
    var equipStore = Ext.create('Ext.data.Store', {
        model: 'equip',
        proxy: {
            type: 'ajax',
            url: Ext.ContextPath+'/outWarehouse/getEquipList.do',
            reader: {
                type: 'json',
                root:'root'
            }
        }
    });
    var stock_count_item = Ext.create('Ext.toolbar.TextItem', {text: '库存数量: 0'});
    var repair_count_item = Ext.create('Ext.toolbar.TextItem', {text: '其中返修: 0',hidden:true});
    var restock_count_item = Ext.create('Ext.toolbar.TextItem', {text: '新设备返库: 0',hidden:true});
    
	var equip_grid=Ext.create('Ext.grid.Panel',{
		title:'旧设备',
		store:equipStore,
    	columns: [{header: 'SID', dataIndex: 'SID',hidden:true,hideable:false},
    	          {header: '设备供应商',dataIndex: 'SNAME',width:150},
    	          {header: '条码', dataIndex: 'ECODE'},
    	          {header: 'STID', dataIndex: 'STID',hidden:true,hideable:false},
    	          {header: '库房', dataIndex: 'STOCK'},
    	          {header: '返修日期', dataIndex: '',hidden:true,hideable:false},
    	          {header: '返修原因', dataIndex: '',hidden:true,hideable:false},
    	          {header: '存放位置', dataIndex: 'STMEMO',flex:1}],
        bbar: Ext.create('Ext.toolbar.Toolbar', {
              items: [' ','合计',' ',stock_count_item,' ',repair_count_item,' ',restock_count_item]
        })
    	 
	});
	
	var esubtype_combox=Ext.create('Ext.D.list.ComboBox',{
		url:Ext.ContextPath+'/dataExtra/esubtypeList.do',fieldLabel:'设备子类型',name:'esubtype',id:'subtype_field',autoSelected:true,actionMode:'local',listeners:{change:queryEquipStockList},labelAlign:'right'
	});
	
	var etype_combox=Ext.create('Ext.D.list.ComboBox',{
		url:Ext.ContextPath+'/dataExtra/etypeList.do',fieldLabel:'设备类型',name:'etype',cascadeId:'subtype_field',actionMode:'local',loadAuto:true,labelAlign:'right'
	});
	
	var step1=Ext.create('Ext.panel.Panel',{
		title:'库存设备查询',
        layout: {
            type:'vbox',
            padding:'5',
            align:'stretch'
        },
        defaults:{border:false},
		items:[{xtype:'form',items:[{xtype:'columnbox',columnSize:4,items:[{xtype:'listcombox',url:Ext.ContextPath+'/dataExtra/stockList.do',fieldLabel:'库房',name:'stid',emptyText:'未选择库房',listeners:{change:queryEquipStockList},labelAlign:'right'},
		                                                                    etype_combox,esubtype_combox,
		            		                                               {xtype:'listcombox',url:Ext.ContextPath+'/dataExtra/supplierList.do',fieldLabel:'厂商',name:'sid',emptyText:'未选择厂商',listeners:{change:queryEquipStockList},labelAlign:'right'}]},
		            		        {xtype:'columnbox',columnSize:4,items:[{xtype:'listcombox',itemId:'unit_clerk_combox',url:Ext.ContextPath+'/dataExtra/clerkList.do',fieldLabel:'申请人',name:'ucid',emptyText:'未选择申请人',listeners:{change:function(f,n,o,e){getClerkEquipAmount(n)}},labelAlign:'right'},
		            		                                               {xtype:'displayfield',itemId:'total_displayfield',labelWidth:150,value:10,fieldLabel:'总共设备出库数量',hidden:true},
		            		                                               {columnWidth:2/4,xtype:'container',padding:'5px 0 0 0',itemId:'clerk_displaybox',hidden:false}]}]},
		       {html:'<HR style="FILTER: alpha(opacity=100,finishopacity=0,style=3)" width="100%" color=#987cb9 SIZE=3>'},
		       {xtype:'displayfield',fieldLabel:'合计库存量',value:'0/0',id:'count_statistics'},
		       {flex:1,xtype:'tabpanel',border:false,items:[equipstock_panel,equip_grid]}],
		 listeners:{
			 activate:function(p,e){
				 if(!etype_combox.getValue()){
					 var typeStore=etype_combox.getStore();
					 if(typeStore&&typeStore.getCount()>0){
						 etype_combox.select(typeStore.getAt(0));
					 }
				 }
			 }
		 }
	});
	
	function getClerkEquipAmount(ucid){
	   	 Ext.Ajax.request({
		        url : Ext.ContextPath+'/outWarehouse/getClerkEquipAmount.do',
		        method : 'post',
		        params:{
		        		etype:etype_combox.getValue(),
		        	    esubtype:esubtype_combox.getValue(),
		        	    ucid:ucid
		        	    },
		        success : function(response, options) {
					var display1=step1.down('#total_displayfield');
					var display2=step1.down('#clerk_displaybox');
					var ret = Ext.decode(response.responseText);
					display1.setValue(ret.total);
					var displayStr='其中	<font color=blue>'+etype_combox.getRawValue()+'</font>	.	<font color=blue>'+esubtype_combox.getRawValue()+'</font>	类型数量:'	;
					var label=display2.getEl().dom;
					label.style.width=displayStr.length*4+"px";
					label.innerHTML=displayStr+"&nbsp;&nbsp;&nbsp;&nbsp;"+ret.clerk;
					if(!display1.isVisible()){
						display1.setVisible(true);
					}
					if(!display2.isVisible()){
						display2.setVisible(true);
					}
		        },
		        failure : function() {
		        	 Ext.Msg.alert('提示','数据加载失败');
		        }
	  	 });
	}
	
    function queryEquipStockList(f,n,o,e){
		var form=f.up('form').getForm();
		var formObj=form.getValues();
		if(formObj.etype&&formObj.esubtype){
			if(formObj.ucid){
				getClerkEquipAmount(formObj.ucid);
			}
	    	var num1=0,num2=0;
	    	equipstockStore.load({
	    		params:formObj,
	    	    callback: function(records, operation, success) {
	    	    	if(success){
		    	    	var len=records.length;
		    	    	if(len>0){
		    	    		var tabPanel=step1.down('tabpanel');
		    	    		var active=tabPanel.getActiveTab();
		    	    		if(active.getId()=='equip_stock_panel'){
		    	    			var grid=active.down('#equip_stock_grid');
		    	    			grid.getSelectionModel( ).select(0);
		    	    		}
		    	    		for(var i=0;i<len;i++){
		    	    			var record=records[i];
		    	    			num1+=record.get('NUMSNEW');
		    	    		}
		    	    		equipstock_count_item.setText("总计："+num1);
		    	    		step1.getComponent('count_statistics').setValue((num1+num2)+"("+num1+"/"+num2+")");
		    	    	}
	    	    	}
	    	    }
	    	});
	    	equipStore.load({params:formObj,
	    	    callback: function(records, operation, success) {
	    	    	if(success){
		    	    	num2=records.length;
		    	    	if(num2>0){
		    	    		stock_count_item.setText("库存数量: "+num2);
		    	    		step1.getComponent('count_statistics').setValue((num1+num2)+"("+num1+"/"+num2+")");
		    	    	}
	    	    	}
	    	    }
	    	});
		}
    }


    
    var scanStore = Ext.create('Ext.data.Store', {
        // destroy the store if the grid is destroyed
        autoDestroy: true,
        model: 'equipment',
        proxy: {
            type: 'memory'
        }
    });
    
	var equipScanGrid=Ext.create('Ext.grid.Panel',{
		region:'center',
		store:scanStore,
    	columns: [{header: '条码',dataIndex: 'ecode',width:150},
    	          {header: '设备类型', dataIndex: 'typename'},
    	          {header: '设备子类型', dataIndex: 'subtypename'},
    	          {header: '型号', dataIndex: 'style'},
    	          {header: '批次', dataIndex: 'batchid'},
    	          {header: '设备状态', dataIndex: 'estatus',renderer:statusInfo},
    	          {header: '会安装位置', dataIndex: 'stmemo',flex:1},
		          { xtype: 'actioncolumn',
    	        	header:'操作',
	                width: 50,
	                items: [{
	                    icon   : '../images/delete.gif',  // Use a URL in the icon config
	                    tooltip: 'Sell stock',
	                    handler: function(grid, rowIndex, colIndex) {
	                        var rec = scanStore.getAt(rowIndex);
	                        Ext.MessageBox.confirm('确认', '您确认要删除该记录吗?', function(btn){
	                        	if(btn=='yes'){
	                        		scanStore.remove(rec);
	                        	}
	                        });
	                    }
	                }]} 
    	          ],
            listeners: {
                selectionchange: function(model, records) {
                    if (records[0]) {
                    	step1.down('#equipinfo_form').getForm().loadRecord(records[0]);
                    }
                }
            },
            tbar:['<pan id="toolbar-title-text">扫描设备列表</span>','->',
                  {text:'清空设备',
            	   iconCls:'icon-clearall',
            	   handler:function(){
            		   Ext.MessageBox.confirm('确认', '您确认要清除所有记录吗?', function(btn){
									            	if(btn=='yes'){
									            		scanStore.removeAll();
									            	}
									            });
            		   }
            }]
	});
	var ecode_length=18;
	var ecode_field=Ext.create('Ext.form.field.Text',{
		name:'ecode',
		fieldLabel:'输入设备条形码',
		allowBlank:false,
		vtype:'alphanum',
		minLength:ecode_length,
		maxLength:ecode_length,
		msgTarget:'side',
		validateOnBlur:false,
		listeners:{
			blur:function(f,e){
				if(!f.getValue()||f.getValue()==''){
					f.clearInvalid();
				}
			},
			change:equipScan
		}
	});
	
	var estatus_fieild=Ext.create('Ext.D.list.ComboBox',{labelWidth:75,width:220,xtype:'listcombox',url:Ext.ContextPath+'/dataExtra/estatusList.do',fieldLabel:'设备状态',loadAuto:true,name:'estatus',readOnly:true});
	
	
	//========================================
	var step2=Ext.create('Ext.panel.Panel',{
        layout: {
            type:'vbox',
            padding:'5',
            align:'stretch'
        },
        defaults:{margins:'0 0 5px 0',border:false},
        items:[{xtype:'fieldcontainer',margin:'5px',layout:'hbox',items:[ecode_field,{margins:'0 0 0 5px',xtype:'button',text:'清除',handler:function(btn){ecode_field.reset();ecode_field.focus();}}]},
               {html:'<HR style="FILTER: alpha(opacity=100,finishopacity=0,style=3)" width="100%" color=#987cb9 SIZE=3>'},
               {flex:1,layout:'border',bodyStyle:'background-color:#FFFFFF',//设置面板体的背景色
              		defaults:{border:false},
              		items:[{
              			xtype:'form',
              			region:'west',
              			split:true,
              			width:255,
              			layout:'fit',
              			items:[{xtype:'fieldset',title:'设备基本信息',defaults:{xtype:'textfield',readOnly:true,labelWidth:75,width:220},
              				    margin:'0px',
      							items:[{fieldLabel:'条形码',name:'ecode'},
      							       estatus_fieild,
      	  					           {fieldLabel:'设备类型',name:'typename'},
      	  					     	   {fieldLabel:'型号',name:'style'},
      	  					           {fieldLabel:'厂家',name:'sname'},
      						           {fieldLabel:'批次',name:'batchid'},
      						     	   {fieldLabel:'使用单位',name:'uname'}]}]
      	        		},equipScanGrid]
              	   }],
		buttons:[{text:'下一步',handler:function(btn){
			if(scanStore.getCount()>0){
				step2.down('form').getForm().reset();
				var cardview=btn.up("#equip_scan_card");	
				var layout=cardview.getLayout();
				
				layout['next']();
			}else{
				Ext.MessageBoxEx('提示','当前出库设备为 0 !');
			}
		}}],
    	listeners:{
    		activate:function(p,e){
    			ecode_field.reset();
    			ecode_field.focus();
    		}
    	}
	});
	
	function statusInfo(v){
		var stateStore= estatus_fieild.getStore();
		var diplayTxt="";
		stateStore.each(function(record){
			var value=record.get('value');
			if(v===value){
				diplayTxt=record.get('text');
				return false;
			}
		});
		return diplayTxt;
	}
	
	function equipScan(field,n,o,e){
		var form= step2.down('form').getForm();
		if(n.length>=ecode_length){
		   if(field.isValid()){
			   Ext.Ajax.request({
					params : {ecode:n},//传递参数   
					url : Ext.ContextPath+'/outWarehouse/getEquipInfo.do',//请求的url地址   
					method : 'GET',//请求方式   
					success : function(response) {//加载成功的处理函数   
						var ret=Ext.decode(response.responseText);
						ecode_field.setValue("");
						ecode_field.clearInvalid( );
						if(ret.success){
							var scanrecord = Ext.create('equipment', ret.root);
							form.loadRecord(scanrecord);
							
							
							var estatus=scanrecord.get('estatus');
							//if((estatus>0&&estatus<5)||estatus==equipstatus.BACK_OTHER){
							if(estatus==0){
								var exist=false;
								scanStore.each(function(record){
								    if(n==record.get('ecode')){
								    	exist=true;
								    	return !exist;
								    }
								});
								if(exist){
									Ext.MessageBoxEx('提示','该设备已经存在');
								}else{
									scanStore.insert(0, scanrecord);
								}
							}else{
								Ext.MessageBoxEx('提示','该设备为非库存设备,不能添加到出库列表');
							}
						} else {
							Ext.MessageBoxEx('提示','该设备为非库存设备,不能添加到出库列表');
						}
					},
					failure : function(response) {//加载失败的处理函数   
						Ext.Msg.alert('提示', '设备加载失败：'+ response.responseText);
					}
				});
		   }
		}else{
			//form.reset();
		}
	}
	
//	var selectStore=Ext.create('Ext.data.ArrayStore', {
//        data: [{value:1,text:1}],
//        fields: ['value','text'],
//        sortInfo: {
//           field: 'value',
//           direction: 'ASC'
//        }
//    });
	 var selectStore = Ext.create('Ext.data.JsonStore', {
        fields: [{name: 'text', type: 'string'},
	    	     {name: 'value', type: 'int'}],
        proxy: {
            type: 'ajax',
            url: Ext.ContextPath+'/dataExtra/unitList.do'
        },
        autoLoad:true
    });
	
	var ucid_field=Ext.create('Ext.D.list.ComboBox',{url:Ext.ContextPath+'/dataExtra/clerkList.do',fieldLabel:'申请人',name:'ucid',emptyText:'未选择申请人',allowBlank:false});
	var memo_field=Ext.create('Ext.form.field.TextArea',{flex:1,height:300,fieldLabel:'备注信息',labelAlign:'top'});
	var company_select=Ext.create('Ext.ux.form.MultiSelect',{
			flex:1,
			fieldLabel:'业务单位列表',
			labelAlign:'top',
			margin:'0 0 2px 2px',
	        store:selectStore,
	        displayField: 'text',
	        valueField: 'value',
	        ddReorder: true
	        //hideLabel:true,
	        //tbar:[{text:'111'}]
	        //tbar:['<pan id="toolbar-title-text"><用户单位列表></span>','->',{text:'添加',iconCls:'icon-add',handler:addcompany}]
        });
	
        //===========================================输入申请人，用户单位
	var step3=Ext.create('Ext.form.Panel',{
        layout: {
            type:'vbox',
            //padding:'50',
            align:'stretch'
        },
        defaults:{margin:'5px',border:false},
        items:[{padding:'5px',items:ucid_field},
               {html:'<HR style="FILTER: alpha(opacity=100,finishopacity=0,style=3)" width="100%" color=#987cb9 SIZE=3>'},
               {flex:1,layout:{type:'hbox',align:'stretch'},items:[memo_field,company_select]}],
		buttons:[{text:'上一步',handler:function(btn){
			var cardview=btn.up("#equip_scan_card");	
			var layout=cardview.getLayout();
			layout['prev']();
		}},{text:'下一步',handler:function(btn){
			if(ucid_field.isValid()){
				if(selectStore.getCount()<1){
					Ext.MessageBoxEx('提示','请添加用户单位');
				}else{
					var cardview=btn.up("#equip_scan_card");	
					var layout=cardview.getLayout();
					layout['next']();
				}	
			}else{
				Ext.MessageBoxEx('提示','请填写申请人');
			}
		}}]
	});
	
//    var unitStore = Ext.create('Ext.data.JsonStore', {
//        fields: [{name: 'text', type: 'string'},
//	    	     {name: 'value', type: 'int'}],
//        proxy: {
//            type: 'ajax',
//            url: Ext.ContextPath+'/dataExtra/unitList.do'
//        },
//        autoLoad:true
//    });
//	var select_form;
//	function addcompany(){
//		if(!select_form){
//			select_form=Ext.create('Ext.window.Window',{
//			        title: '用户选择',
//			        width: 500,
//			        height:300,
//			        bodyPadding: 5,
//			        layout:'fit',
//			        closeAction:'hide',
//			        items:[{
//			            xtype: 'itemselector',
//			            fieldLabel:'请选择用户单位',
//			            labelAlign:'top',
//			            store: unitStore,
//			            displayField: 'text',
//			            valueField: 'value',
//			            value: [],
//			            msgTarget: 'side'
//			        }],
//			        buttons:[{text:'确认',handler:function(){
//			        	var select=this.up('window').down('itemselector');
//			        	var units=select.getValue();
//			        	var unitArray = new Array();
//		        		 for(var i=0;i<units.length;i++){
//				        	unitStore.each(function(record) {
//				        		 var value=record.get('value');
//				        		 if(units[i]==value){
//				        			 unitArray.push(record);
//				        		 }
//			        		});
//				        }
//		        		selectStore.loadData(unitArray);
//		        		select_form.close();
//			        }}]
//				})
//		}
//		select_form.show();
//	}

    var outware_diplay_text = Ext.create('Ext.toolbar.TextItem', {text: '合计: 0,其中新设备数 10',hidden:true});
	var equiplist_grid=Ext.create('Ext.grid.Panel',{
		title:'扫描设备列表',
		flex:2,
		margin:'5px',
		store:scanStore,
    	columns: [{header: 'etype',dataIndex: 'etype', hidden:true,hideable:false},
    			  {header: 'esubtype',dataIndex: 'esubtype', hidden:true,hideable:false},
    	          {header: '条码',dataIndex: 'ecode',width:150},
    	          {header: '设备类型', dataIndex: 'typename'},
    	          {header: '设备子类型', dataIndex: 'subtypename'},
    	          {header: '型号', dataIndex: 'style'},
    	          {header: '批次', dataIndex: 'batchid'},
    	          {header: '状态', dataIndex: 'estatus',renderer:statusInfo},
    	          {header: '仓库', dataIndex: 'stmemo',flex:1}],
        bbar:['->','-',outware_diplay_text],
        listeners:{
        	select:function(m,r,i,e){
        		var ucid =step4.getForm().getValues().ucid;
        		if(ucid){
		       	   	 Ext.Ajax.request({
		 		        url : Ext.ContextPath+'/outWarehouse/getClerkEquipAmount.do',
		 		        method : 'post',
		 		        params:{
		 		        		etype:r.get('etype'),
		 		        	    esubtype:r.get('esubtype'),
		 		        	    ucid:ucid
		 		        	    },
		 		        success : function(response, options) {
		 					var ret = Ext.decode(response.responseText);
		 					var displayStr='总共设备领取：'+ret.total+'&nbsp;&nbsp;&nbsp;&nbsp;其中&nbsp;&nbsp;<font color=blue>'+r.get('typename')+'</font>	.	<font color=blue>'+r.get('subtypename')+'</font>&nbsp;&nbsp;类型数量：'	;
		 					displayStr=displayStr+ret.clerk;
		 					display_box.setDisplayStr(displayStr);
		 					if(!display_box.isVisible()){
		 						display_box.setVisible(true);
		 					}
		 		        },
		 		        failure : function() {
		 		        	if(display_box.isVisible()){
		 		        	 display_box.setVisible(false);
		 		        	}
		 		        	 Ext.Msg.alert('提示','数据加载失败');
		 		        }
		       	   	 });
        		}else{
 		        	if(display_box.isVisible()){
	 		        	 display_box.setVisible(false);
	 		        }
            	}
        	}
        }
	});
	
	var display_box =Ext.create('Ext.Component',{
			html:'总共设备领取：其中XXX型设备数为0',
			hidden:true,
			setDisplayStr:function(displayStr){
				this.getEl().dom.innerHTML=displayStr;
			}
		});
	
	var step4=Ext.create('Ext.form.Panel',{
        layout: {
            type:'vbox',
            padding:'5',
            align:'stretch'
        },
        defaults:{margins:'0 0 5 0',border:false},
        items:[{
        	flex:1,
        	layout:{type:'hbox',align:'stretch'},
        	items:[{
         	        border:false,
         	        flex:1,
		            layout: {
		                type:'vbox',
		                align:'stretch'
		            },
		            defaults:{margins:'0 0 5 0',border:false},
	 				items:[{xtype:'textarea',name:'userlist',fieldLabel:'预计用户列表',labelAlign:'top',flex:1,readOnly:true,allowBlank:false},
	 				       {xtype:'textarea',name:'memo',fieldLabel:'备注信息',labelAlign:'top',flex:1}]
		            },
 				    equiplist_grid]
        		},
        		{
        		height:50,
	        	items:[{xtype:'columnbox',columnSize:5,items:[{xtype:'displayfield',columnWidth:2/5},
	        	                                              {xtype:'panel',columnWidth:2/5,items:[
	        	                                              {xtype:'listcombox',url:Ext.ContextPath+'/dataExtra/clerkList.do',loadAuto:true,fieldLabel:'申请人',name:'ucid',emptyText:'未选择申请人',readOnly:true,allowBlank:true},
	        	                                              display_box
	        	                                              ],border:false},{xtype:'panel',items:[
	           	                                              {xtype:'listcombox',url:Ext.ContextPath+'/dataExtra/clerkList.do',name:'rucid',fieldLabel:'经办人',loadAuto:true,labelWidth:70,width:200,allowBlank:false,autoSelected:true},
	        	                                              {xtype:'datefield',name:'etime',fieldLabel:'经办日期',labelWidth:70,width:200,format:'Y-m-d',allowBlank:false,value:new Date()}
	        	                                              ],border:false}]
	        	}]
        }],
		buttons:[{text:'返回',itemId:'step4_first_btn',hidden:true,handler:function(btn){
			scanStore.removeAll();
			var cardview=btn.up("#equip_scan_card");	
			var layout=cardview.getLayout();
			layout.setActiveItem(0);
		 }},{text:'上一步',itemId:'step4_prev_btn',handler:function(btn){
			var cardview=btn.up("#equip_scan_card");	
			var layout=cardview.getLayout();
			layout['prev']();
		}},{text:'确认出库',itemId:'step4_complete_btn',handler:function(btn){
			var ecodes=new Array();
			scanStore.each(function(record){
				ecodes.push(record.get("ecode"));
			});
			var form =step4.getForm();
			if(ecodes.length>0){
		        form.submit({
		        	params:{
		        		ecodes:ecodes
		        	},
				    clientValidation: true,
				    url: Ext.ContextPath+'/outWarehouse/outwarehouse.do',
				    success: function(form, action) {
			        	Ext.MessageBox.confirm('设备出库成功', '返回继续出库设备？', function(btn){
			        		if(btn=='yes'){
			        			scanStore.removeAll();
			        			var cardview=step4.up("#equip_scan_card");	
			        			var layout=cardview.getLayout();
								layout.setActiveItem(0);
			        		}else{
			        			var firstBtn=step4.down('#step4_first_btn');
			        			var prevBtn=step4.down('#step4_prev_btn');
			        			var completeBtn=step4.down('#step4_complete_btn');
			        			if(!firstBtn.isVisible()){
			        				firstBtn.setVisible(true);
			        			}
			        			if(prevBtn.isVisible()){
			        				prevBtn.setVisible(false);
			        			}
			        			if(completeBtn.isVisible()){
			        				completeBtn.setVisible(false);
			        			}
			        		}
			        	});
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
			}
		
		}}],
    	listeners:{
    		activate:function(p,e){
    		    var countItem=0;
    			scanStore.each(function(record){
    				var isNew=record.get('isNew');
    				//if(estatus==equipstatus.NEW_STOCK){
    				if(isNew==0){
    					countItem++;
    				}
    			})
    			outware_diplay_text.setText('合计: '+scanStore.getCount()+'，其中新设备数 ：'+countItem);
    			if(!outware_diplay_text.isVisible()){
    				outware_diplay_text.setVisible(true);
    			}
    			var cArray=new Array();
    			var userlist=new Array();
//    			selectStore.each(function(record){
//    				cArray.push(record.get('value'));
//    				userlist.push(record.get('text'));
//    			});
				cArray=company_select.getValue( );
				selectStore.each(function(record){
					for(var i=0;i<cArray.length;i++){
						if(cArray[i]==record.get('value')){
							userlist.push(record.get('text'));
						}
					}
    			});
				
    			
    			//console.log(company_select.getValue( ));
    			step4.getForm().setValues({cIds:cArray.join(','),userlist:userlist.join(','),memo:memo_field.getValue(),ucid:ucid_field.getValue()});
    		},
    		beforeactivate:function(p,e){
    			var firstBtn=p.down('#step4_first_btn');
    			var prevBtn=p.down('#step4_prev_btn');
    			var completeBtn=p.down('#step4_complete_btn');
    			if(firstBtn.isVisible()){
    				firstBtn.setVisible(false);
    			}
    			if(!prevBtn.isVisible()){
    				prevBtn.setVisible(true);
    			}
    			if(!completeBtn.isVisible()){
    				completeBtn.setVisible(true);
    			}
    		}
    	}
	});
	
	Ext.create('Ext.container.Viewport',{
        layout:'fit',
        renderTo:'view-port',
	    items:{xtype:'tabpanel',
	    	activeTab:0,
	    	margin:'2px',
	    	plain: true,
	    	border:false,
	    	defaults:{border:false},
	    	items:[step1,{layout:'card',itemId:'equip_scan_card',title:'设备出库',items:[step2,step3,step4]}],
	    	listeners:{
	    		afterrender:function(t,e){
	    			t.setActiveTab(1);
	    		}
	    	}
	    }
	});
});