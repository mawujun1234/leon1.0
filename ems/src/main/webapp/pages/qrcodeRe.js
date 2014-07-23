//二维码重打
Ext.define('Ext.pages.qrcodeRe',{
	extend:'Ext.panel.Panel',
	
	layout: {
            type:'vbox',
            padding:'5',
            align:'stretch'
     },
     itemsPerPage:50,
	initComponent: function () {
      var me = this;
      me.items=[me.getFormPanel(),me.getGridPanel()];
      
      me.callParent();
	},
	getFormPanel : function() {
		var me=this;
		var esubtype_combox=Ext.create('Ext.D.list.ComboBox',{
			id:'esubtype_combox1',url:Ext.ContextPath+'/dataExtra/esubtypeList.do',fieldLabel:'设备子类型',name:'esubtype',labelAlign:'right',autoSelected:true,loadAuto:true,editable:false
		});
		var etype_combox=Ext.create('Ext.D.list.ComboBox',{
			url:Ext.ContextPath+'/dataExtra/etypeList.do',fieldLabel:'设备类型',name:'etype',cascade:true,cascadeId:'esubtype_combox1',labelAlign:'right',autoSelected:true,loadAuto:true,editable:false
		});
		
		
//		var style_field=Ext.create('Ext.form.field.Text',{
//			fieldLabel:'设备型号',name:'style',labelAlign:'right',hidden:false,submitValue:true
//		});
		var isInStock_field=Ext.create('Ext.form.field.Checkbox',{
			fieldLabel:'已入库',name:'isInStock',labelAlign:'right',hidden:false,submitValue:true
		});
	
				var formPanel= Ext.create('Ext.form.Panel',{
					xtype : 'form',
					items : [{
						xtype : 'columnbox',
						columnSize : 4,
						items : [etype_combox, esubtype_combox//, style_field
						, {
							xtype : 'listcombox',
							itemId : 'supplier_field',
							url : Ext.ContextPath+ '/dataExtra/supplierList.do',
							fieldLabel : '厂商',
							name : 'sid',
							editable : false,
							allowBlank : false,
							emptyText : '未选择厂商',
							labelAlign : 'right'
						}]
					}, {
						xtype : 'columnbox',
						columnSize : 4,
						items : [{
									xtype : 'listcombox',
									url : Ext.ContextPath
											+ '/dataExtra/stockList.do',
									itemId : 'stock_field',
									fieldLabel : '库房',
									name : 'stid',
									allowBlank : false,
									emptyText : '未选择库房',
									labelAlign : 'right'
								},isInStock_field]
					}, {layout:{type:'hbox',algin:'stretch'},items:[{flex:1,border:false,html:'<HR style="FILTER: alpha(opacity=100,finishopacity=0,style=3)" width="100%" color=#987cb9 SIZE=3>'},{xtype:'button',text:'查询',handler:queryEquip,width:70,iconCls:'icon-add',margin:'0 5px 0 5px'}]}]
				});
				
				function queryEquip(btn){
					var supplier_field=formPanel.getForm().findField("sid");
					var stock_field=formPanel.getForm().findField("stid");
					var params={
						isInStock:isInStock_field.getValue(),
						etype:etype_combox.getValue(),
						esubtype:esubtype_combox.getValue(),
						sid:supplier_field.getValue(),
						//unitid:.getValue(),
						stid:stock_field.getValue(),
						//style:style_field.getValue(),
						start:0,
						limit:me.itemsPerPage
					};
					
					me.equip_grid.getStore().load({params:params});
				}
				this.formPanel=formPanel;
				return formPanel;
				
		},
		getGridPanel:function(){
			var me=this;

		    var equipStore = Ext.create('Ext.data.Store', {
		        autoDestroy: true,
		        model: 'equipment',
		        pageSize: me.itemsPerPage,
		        proxy: {
		            type: 'ajax',
		            url:Ext.ContextPath+'/qrcode/queryEquip.do',
		            reader:{
		            	type:'json',
		            	root:'data',
		            	totalProperty:"totalCount"
		            	
		            }
		        }
		    });
			var equip_grid=Ext.create('Ext.grid.Panel',{
				flex:1,
				store:equipStore,
				selModel: Ext.create('Ext.selection.CheckboxModel',{mode:'MULTI'}),
		    	columns: [Ext.create('Ext.grid.RowNumberer'),
		    			  {header: '条码', dataIndex: 'ecode',hideable:false,hidden:false,flex:1},
		    	          //{header: 'etype', dataIndex: 'etype',hideable:false,hidden:true},
		    	          {header: '设备类型', dataIndex: 'typename',width:120},
		    	          //{header: 'esubtype', dataIndex: 'esubtype',hideable:false,hidden:true},
		    	          {header: '设备子类型', dataIndex: 'subtypename',width:120},
		    	          //{header: 'sid', dataIndex: 'sid',hideable:false,hidden:true},
		    	          {header: '设备型号', dataIndex: 'style',width:120},
		    	          {header: '设备商', dataIndex: 'sname',width:120},
		    	          {header: '数量', dataIndex: 'nums',width:70},
		    	          {header: '单价(元)', dataIndex: 'price',width:70},
		    	          {header: '总价(元)', dataIndex: 'totalprice',width:70},
		    	          {header: 'stid', dataIndex: 'stid',hideable:false,hidden:true},
		    	          {header: '库房', dataIndex: 'stock',width:120},
		    	          //{header: '库房描述', dataIndex: 'stmemo',flex:1},
		    	          { header:'操作',
			                xtype: 'actioncolumn',
			                width: 70,
			                items: [{
			                    icon   : '../images/delete.gif',  // Use a URL in the icon config
			                    tooltip: '删除',
			                    handler: function(grid, rowIndex, colIndex) {
			                    	if(me.formPanel.getForm().findField("isInStock")==true){
			                    		Ext.Msg.alert("消息","已入库设备部能删除。");
			                    		return;
			                    	}
			                       
			                        Ext.MessageBox.confirm('确认', '您确认要删除该记录吗?', function(btn){
			                        	if(btn=='yes'){
			                        		var rec = equipStore.getAt(rowIndex);
			                        		Ext.Ajax.request({
			                        			url:Ext.ContextPath+'/qrcode/deleteEquip.do',
			                        			method:'POST',
			                        			params:{ecode:rec.get("ecode")},
			                        			success:function(){
			                        				equipStore.remove(rec);
			                        			}
			                        		});
			                        		
			                        	}
			                        });
			                    }
			                }]
			            }],
		        tbar:['<pan id="toolbar-title-text">当前入库记录</span>','->',
		            {text:'导出',
		        	 //iconCls:'icon-clearall',
		            icon:'../icons/database_go.png',
		        	 handler:function(){
		        		  var records=equip_grid.getSelectionModel().getSelection();
							var len=records.length;
							if(len<1){
								Ext.Msg.alert('提示:','请选择一种设备');
								return;
							}
							var barcodes=[];
							for(var j=0;j<len;j++){
								barcodes.push(records[j].get('ecode'));
								//record.set('printed',1);
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
		        }],
		        dockedItems: [{
			        xtype: 'pagingtoolbar',
			        store: equipStore,   // same store GridPanel is using
			        dock: 'bottom',
			        displayInfo: true
			    }]
			});
			this.equip_grid=equip_grid;
			return equip_grid;
		}

});