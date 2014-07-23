Ext.onReady(function(){
	Ext.tip.QuickTipManager.init();
	var ecode="";//,scanrecord=null;
	
//    Ext.define('equipment',{
//        extend: 'Ext.data.Model',
//        fields: [
//            {name: 'batchid', mapping: 'batchid'},
//            {name: 'cpid', mapping: 'cpid'},
//            {name: 'datebuying', mapping: 'datebuying'},
//            {name: 'did', mapping: 'did'},
//            {name: 'ecode', mapping: 'ecode'},
//            {name: 'ename', mapping: 'ename'},
//            {name: 'estatus', mapping: 'estatus'},
//            {name: 'esubtype', mapping: 'esubtype'},
//            {name: 'etype', mapping: 'etype'},
//            {name: 'fstJid', mapping: 'fstJid'},
//            {name: 'iid', mapping: 'iid'},
//            {name: 'lstJid', mapping: 'lstJid'},
//            {name: 'lstMjid', mapping: 'lstMjid'},
//            {name: 'lstOid', mapping: 'lstOid'},
//            {name: 'lstRid', mapping: 'lstRid'},
//            {name: 'memo', mapping: 'memo'},
//            {name: 'nums', mapping: 'nums'},
//            {name: 'price', mapping: 'price'},
//            {name: 'sid', mapping: 'sid'},
//            {name: 'sname', mapping: 'sname'},
//            {name: 'stid', mapping: 'stid'},
//            {name: 'stmemo', mapping: 'stmemo'},
//            {name: 'stock', mapping: 'stock'},
//            {name: 'style', mapping: 'style'},
//            {name: 'subtypename', mapping: 'subtypename'},
//            {name: 'timesMaintained', mapping: 'timesMaintained'},
//            {name: 'timesOutsourcing', mapping: 'timesOutsourcing'},
//            {name: 'timesRepairing', mapping: 'timesRepairing'},
//            {name: 'typename', mapping: 'typename'},
//            {name: 'uname', mapping: 'uname'},
//            {name: 'unitid', mapping: 'unitid'},
//            {name:'rucid',mapping:'rucid'},
//            {name:'rucname',mapping:'rucname'}
//        ]
//    });
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
    	columns: [{header: '条码',dataIndex: 'ecode', field: 'textfield',width:150},
    	          {header: '设备类型', dataIndex: 'etype'},
    	          {header: '型号', dataIndex: 'style'},
    	          {header: '批次', dataIndex: 'batchid'},
    	          {header: '设备状态', dataIndex: 'estatus',renderer:statusInfo},
    	          {header: '仓库', dataIndex: 'stmemo',flex:1},
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
                    	step1.down('form').getForm().loadRecord(records[0]);
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
	var unit_field1=Ext.create('Ext.D.list.ComboBox',{fieldLabel:'申请人',url:Ext.ContextPath+'/dataExtra/clerkList.do',loadAuto:true,name:'rucid',readOnly:true,autoSelected:true});
	var ecode_field=Ext.create('Ext.form.field.Text',{name:'ecode',fieldLabel:'输入设备条形码',vtype:'alphanum',minLength:18,maxLength:18,msgTarget:'side',listeners:{change:equipScan}});
	var estatus_fieild=Ext.create('Ext.D.list.ComboBox',{labelWidth:75,width:220,xtype:'listcombox',url:Ext.ContextPath+'/dataExtra/estatusList.do',fieldLabel:'设备状态',loadAuto:true,name:'estatus',readOnly:true});
	var stock_field=Ext.create('Ext.D.list.ComboBox',{labelWidth:50,url:Ext.ContextPath+'/dataExtra/stockList.do',itemId:'stock_field',fieldLabel:'库房',name:'stid',allowBlank:false,emptyText:'未选择库房',labelAlign:'right'});
	
	var step1=Ext.create('Ext.panel.Panel',{
        layout: {
            type:'vbox',
            padding:'5',
            align:'stretch'
        },
        defaults:{border:false},
        items:[{xtype:'columnbox',columnSize:3,margin:'5 0 0 0',
        		items:[stock_field,{xtype:'fieldcontainer',layout:'hbox',items:[ecode_field,{margins:'0 0 0 5px',xtype:'button',text:'清除',handler:function(btn){ecode_field.reset();ecode_field.focus();}}]}
	                   //{items:[unit_field1]},
	                   //{layout:{type:'hbox',pack:'center',align:'middle'},items:[{xtype:'button',text:'确认添加',handler:equipAdd}]}
	               ]},
               {html:'<HR style="FILTER: alpha(opacity=100,finishopacity=0,style=3)" width="100%" color=#987cb9 SIZE=3>'},
               {flex:1,layout:'border',bodyStyle:'background-color:#FFFFFF',//设置面板体的背景色
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
				var cardview=btn.up("viewport");	
				var layout=cardview.getLayout();
				layout['next']();
			}else{
				Ext.MessageBoxEx('提示','未添加任何设备');
			}
		}}],
    	listeners:{
    		activate:function(p,e){
    			ecode="";
    			ecode_field.reset();
    			ecode_field.focus();
    			unit_field1.reset();
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
	
	function equipScan(f,n,o,e){
		var scanText=f.getValue();
		//scanrecord=null;
		var form= step1.down('form').getForm();
		if(scanText.length>17){
		   if(f.isValid()){
			   ecode=scanText;
			   Ext.Ajax.request({
					params : {ecode:scanText},//传递参数   
					url : Ext.ContextPath+'/outWarehouse/getEquipInfo.do',//请求的url地址   
					method : 'GET',//请求方式   
					success : function(response) {//加载成功的处理函数   
						var ret=Ext.decode(response.responseText);
						ecode_field.setValue("");
						ecode_field.clearInvalid( );
						if(ret.success){
							ret.root.stid=stock_field.getValue();
							ret.root.stmemo=stock_field.getRawValue();
							var scanrecord = Ext.create('equipment', ret.root);
							form.loadRecord(scanrecord);

							
							var estatus=scanrecord.get('estatus');

							if(estatus==1 || estatus==2){
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
									//scanrecord.set("stid",stock_field.getValue());
									scanStore.insert(0, scanrecord);
									
								}
							}else{
								Ext.MessageBoxEx('提示','该设备为非出库待安装设备,不能添加到入库列表');
							}
						} else {
							Ext.MessageBoxEx('提示','该设备为不可以知设备,不能添加到列表');
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
	
//	function equipAdd(){
//		if(scanrecord){
//			var estatus=scanrecord.get('estatus');
//			if(estatus===1){
//				var ucid=unit_field1.getValue();
//				if(ucid&&ucid!=''){
//					var exist=false;
//					scanStore.each(function(record){
//					    if(estatus==record.get('estatus')){
//					    	exist=true;
//					    	return !exist;
//					    }
//					});
//					if(exist){
//						Ext.MessageBoxEx('提示','该设备已经存在');
//					}else{
//						scanrecord.set('rucid',ucid);
//						scanrecord.set('rucname',unit_field1.getRawValue());
//						scanStore.insert(0, scanrecord);
//					}
//				}else{
//					Ext.MessageBoxEx('提示','申请人获取异常，清重新扫描设备');
//				}
//			}else{
//				Ext.MessageBoxEx('提示','该设备状态不能进行当前操作');
//			}
//		}else{
//			Ext.MessageBoxEx('提示','设备信息未准备好,清扫描相关设备');
//		}
//	}

	var equipRestoreGrid=Ext.create('Ext.grid.Panel',{
    	flex:1,
    	margin:'0 5px 0 0',
    	store:scanStore,
    	columns: [{header: '条码',dataIndex: 'ecode', field: 'textfield',width:150},
    	          {header: '设备类型', dataIndex: 'etype'},
    	          {header: '型号', dataIndex: 'style'},
    	          {header: '批次', dataIndex: 'batchid'},
    	          {header: '设备状态', dataIndex: 'estatus',renderer:statusInfo},
    	          {header: '仓库', dataIndex: 'stmemo',flex:1}],
    	listeners:{
    		select:function(m,r,i,e){
    			unit_field2.setValue(r.get('rucid'));
    		}
    	}
    });
	var unit_field2=Ext.create('Ext.D.list.ComboBox',{fieldLabel:'申请人',url:Ext.ContextPath+'/dataExtra/clerkList.do',loadAuto:true,name:'rucid',readOnly:false,labelWidth:70,listeners:{change:loadStatistics}});
	var totalexp_field=Ext.create('Ext.form.field.Display',{labelWidth:120,value:0,fieldLabel:'总共设备出库数量',hidden:true});
	var typeexp_field=Ext.create('Ext.toolbar.TextItem',{
    	text:'<span>，其中	<font color=blue>xxx</font>	.	<font color=blue>xxx</font>	类型数量:</span><span style="margin-left:30px;">0</span>',
    	hidden:true,
    	updateText:function(etype,esubtype,count){
    		var diplsyStr='<span>，其中	<font color=blue>'+etype+'</font>	.	<font color=blue>'+esubtype+'</font>	类型数量:</span>';
    		this.setText(diplsyStr+'<span style="margin-left:30px;">'+count+'</span>');
    	}});
	function loadStatistics(){
		 var record=equipRestoreGrid.getSelectionModel().getLastSelected( );
		 if(record){
		   	 Ext.Ajax.request({
			        url : Ext.ContextPath+'clerkEuipAmount',
			        method : 'post',
			        params:{
			        		etype:record.get('etype'),
			        	    esubtype:record.get('esubtype'),
			        	    ucid:record.get('rucid')
			        	    },
			        success : function(response, options) {
						var ret = Ext.decode(response.responseText);
						var record=equipRestoreGrid.getSelectionModel().getLastSelected( );
						if(record){
							typeexp_field.updateText(record.get('typename'),record.get('subtypename'),ret.clerkAmount);
							totalexp_field.setValue(ret.clerkTotalAmount);
							totalexp_field.setVisible(true);
							typeexp_field.setVisible(true);
						}
			        },
			        failure : function() {
			        	 Ext.Msg.alert('提示','数据加载失败');
			        }
		  	 });
		 }
	}
	var step2=Ext.create('Ext.panel.Panel',{
        layout: {
            type:'vbox',
            padding:'5',
            align:'stretch'
        },
        defaults:{margins:'0 0 5 0',border:false},
        buttonAlign:'left',
		items:[{layout:{type:'vbox',align:'stretch',pack:'center'},height:50,items:[{flex:1,xtype:'columnbox',columnSize:4,items:[{items:unit_field2},{items:totalexp_field},{columnWidth:2/4,padding:'5px',items:typeexp_field}]}]},
		       {html:'<HR style="FILTER: alpha(opacity=100,finishopacity=0,style=3)" width="100%" color=#987cb9 SIZE=3>'},
		       equipRestoreGrid],
		buttons:[{text:'返回继续添加',handler:function(btn){
			var cardview=btn.up("viewport");	
			var layout=cardview.getLayout();
			layout['prev']();
		}},'->',{text:'确认入库',handler:function(btn){
			  var ecodes=new Array();
			  var stids=[];
			  scanStore.each(function(record){
				  ecodes.push(record.get('ecode'));
				  stids.push(record.get('stid'));
			  });
			 if(ecodes.length>0){
			   	 Ext.Ajax.request({
				        url : Ext.ContextPath+'/equipRestore/equiprestore.do',
				        method : 'post',
				        //params:{ecodes:ecodes.join(',')},
				        params:{ecodes:ecodes,stids:stids,rUcid:unit_field2.getValue()},
				        success : function(response, options) {
				        	scanStore.removeAll();
				        	Ext.MessageBox.confirm('设备入库成功', '返回继续添加入库设备？', function(btn){
				        		if(btn=='yes'){
					        		var cardview=step2.up("viewport");	
									var layout=cardview.getLayout();
									layout.setActiveItem(0);
				        		}
				        	});
				        },
				        failure : function() {
				        	 Ext.Msg.alert('提示','数据加载失败');
				        }
			  	 });
			 }else{
		        	Ext.MessageBox.confirm('没有设备需要入库','返回继续添加入库设备？', function(btn){
		        		if(btn=='yes'){
			        		var cardview=step2.up("viewport");	
							var layout=cardview.getLayout();
							layout.setActiveItem(0);
		        		}
		        	}); 
			 }
		}}]
	});
	
	Ext.create('Ext.container.Viewport',{
        layout:'card',
        renderTo:'view-port',
        activeItem: 0,
	    items: [step1,step2]
	});
});