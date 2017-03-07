Ext.define('Ems.baseinfo.CustomerTreeGrid',{
	extend:'Ext.tree.Panel',
	requires: [
	     'Ems.baseinfo.Customer'
	],
	columnLines :true,
	stripeRows:true,
//	viewConfig:{
//		stripeRows:true,
//		listeners:{
//			refresh:function(){
//				//this.select(0);
//			}
//		}
//	},
	reserveScrollbar: true,
    
    //title: 'Core Team Projects',
    //height: 300,
    useArrows: true,
    rootVisible: false,
    //multiSelect: true,
    //singleExpand: true,
    selModel: {
    	selType: 'checkboxmodel',
    	checkOnly:false,
    	listeners:{
    		beforeselect:function(model,record ,index){
    			if(record.get("type")==2){
    				return false;
    			}
    			return true;
    		}
    	}
    },
	initComponent: function () {
      var me = this;
      me.columns=[
		//{dataIndex:'id',text:'id'},
		
		{xtype:'treecolumn',dataIndex:'name',text:'名称',flex:1},
		{dataIndex:'status',text:'状态',renderer:function(value){
			if(value){
				return "有效";
			} else {
				return "<span style='color:red'>无效</>";
			}
		}},
		{dataIndex:'type',text:'类型',xtype: 'numbercolumn', renderer:function(value){
			if(value==0){
				return "机关";
			} else if(value==1) {
				return "企业";
			} else if(value==2){
				return "区";
			}
		}}
		//{dataIndex:'memo',text:'memo'},
      ];
      
//	  me.store=Ext.create('Ext.data.Store',{
//			autoSync:false,
//			pageSize:50,
//			model: 'Ems.baseinfo.Customer',
//			autoLoad:true
//	  });
	  me.store= new Ext.data.TreeStore({
                model: 'Ems.baseinfo.Customer',
                proxy: {
                    type: 'ajax',
                    method:'POST',
                    url: Ext.ContextPath+'/customer/query.do'
                },
                root: {
				    expanded: true,
				    text: "根节点"
				}
                //folderSort: true
      });
     // me.on("",function(){
      
     // });
	  
//      me.dockedItems= [{
//	        xtype: 'pagingtoolbar',
//	        store: me.store,  
//	        dock: 'bottom',
//	        displayInfo: true
//	  }];
//	  
//	  me.tbar=	[{
//			text: '刷新',
//			itemId:'reload',
//			disabled:me.disabledAction,
//			handler: function(btn){
//				var grid=btn.up("grid");
//				grid.getStore().reload();
//			},
//			iconCls: 'form-reload-button'
//		}]
      me.initAction(); 
      me.callParent();
	},
	initAction:function(){
     	var me = this;
     	var actions=[];
     	
       var create = new Ext.Action({
		    text: '新建',
		    itemId:'create',
		    hidden:!Permision.canShow('customer_create'),
		    disabled:me.disabledAction,
		    handler: function(b){
		    	me.onCreate(null,b);
		    },
		    iconCls: 'form-add-button'
		});
		//me.addAction(create);
		actions.push(create);
		var update = new Ext.Action({
		    text: '更新',
		    itemId:'update',
		    hidden:!Permision.canShow('customer_update'),
		    disabled:me.disabledAction,
		    handler: function(b){
		    	me.onUpdate(null,b);
		    },
		    iconCls: 'form-update-button'
		});
		//me.addAction(create);
		actions.push(update);
		
		var destroy = new Ext.Action({
		    text: '删除',
		    itemId:'destroy',
		    hidden:!Permision.canShow('customer_delete'),
		    disabled:me.disabledAction,
		    handler: function(){
		    	me.onDelete();    
		    },
		    iconCls: 'form-delete-button'
		});
		//me.addAction(destroy);
		actions.push(destroy)
		
		var transform = new Ext.Action({
		    text: '转移',
		    itemId:'transform',
		    hidden:!Permision.canShow('customer_transform'),
		    handler: function(){
		    	me.onTransform();    
		    },
		    iconCls: 'icon-exchange'
		});
		//me.addAction(destroy);
		actions.push(transform)
		
		var reload = new Ext.Action({
		    text: '刷新',
		    itemId:'reload',
		    handler: function(){
		    	me.onReload();
		    },
		    iconCls: 'form-reload-button'
		});
		//me.addAction(reload);
		actions.push(reload);

		me.tbar={
			itemId:'action_toolbar',
			layout: {
	               overflowHandler: 'Menu'
	        },
			items:actions
			//,autoScroll:true		
		};

    },
    onCreate:function(){
    	var me=this;
//    	if(!me.workunit_id){
//    		Ext.Msg.alert("消息","请先选择供应商!");
//    		return;
//    	}
    	var seletedcnode=me.getSelectionModel( ).getLastSelected( );
		//form.getForm().findField("paent_id").setValue(true);
    	var record=new Ems.baseinfo.Customer({
    		status:true
    	});    

		var form=new Ems.baseinfo.CustomerForm({
			seletedcnode:seletedcnode,
			isupdate:false,
			listeners:{
				saved:function(newrecord){
					win.close();
					
					if(newrecord.get("type")==2){
						me.getStore().reload({node:me.getRootNode()});
					} else {
						me.getStore().reload({node:seletedcnode.parentNode});
					}
					
					//me.tree.getStore().reload({node:parent});
				}
			}
		});
		form.getForm().loadRecord(record);
		var win=new Ext.window.Window({
			items:[form],
			layout:'fit',
			closeAction:'destroy',
			width:500,
			height:300,
			modal:true
		});
		//form.win=win
		win.show();

    },
    onUpdate:function(){
    	var me=this;
    	
    	var record=me.getSelectionModel( ).getLastSelected( );
		if(!record){
			Ext.Msg.alert("消息","请先选择一条联系方式!");	
			return;
		}
		
		var form=new Ems.baseinfo.CustomerForm({
			seletedcnode:record,
			isupdate:true,
			listeners:{
				saved:function(newrecord){
					//form.updateRecord();
					win.close();
					//alert(record.get("id")+"_"+record.get("levl"));
					//me.tree.getStore().getNodeById(record.get("id")+"_"+record.get("levl")).set("text",record.get("text")) 
				}
			}
		});
		form.getForm().loadRecord(record);
		//var ids=record.get("id").split("_");
		//form.getForm().findField("id").setValue(ids[0]);
		//form.getForm().findField("id").setReadOnly(true);
		
		var win=new Ext.window.Window({
			items:[form],
			layout:'fit',
			closeAction:'destroy',
			width:500,
			height:300,
			modal:true
		});
		//form.win=win
		win.show();	
		    	
		
    },
    onDelete:function(){
    	var me=this;
    	var record=me.getSelectionModel( ).getLastSelected( );

		if(!record){
		    Ext.Msg.alert("消息","请先选择类型");	
			return;
		}

		Ext.Msg.confirm("删除",'确定要删除吗?', function(btn, text){
				if (btn == 'yes'){
					Ext.Ajax.request({
						url:Ext.ContextPath+'/customer/destroy.do',
						params:record.getData(),
						method:'POST',
						success:function(){
							//var parent=me.tree.getSelectionModel( ).getLastSelected( )||me.tree.getRootNode( );  
							//me.tree.getStore().reload({node:parent});
							
							//me.getStore().remove(record);
							me.getStore().reload();
						}
					});
			}
		});
    },
    onTransform:function(){
    	var me=this;
    	var records=me.getSelectionModel().getSelection();
		if(records==null || records.length==0){
			Ext.Msg.alert("消息","请先选择派出所!");
			return;
		}
		Ext.Msg.confirm("消息","确认转移吗?",function(aa){
			if(aa=='yes'){
				//alert(records.length);
				var customer_ids=[];
				for(var i=0;i<records.length;i++){
					//alert(records[i].get("compno"));
					customer_ids.push(records[i].get("id"));
				}
				//var extraParams=me.getStore().getProxy().extraParams;
				var customerarea=Ext.create('Ems.baseinfo.CustomerAreaCombo',{
				
				});
				var win=Ext.create('Ext.window.Window',{
					layout:'form',
					title:'转移派出所',
					width:280,
					height:150,
					modal:true,
					items:[customerarea],
					buttons:[{
						text:'确认',
						handler:function(){
							Ext.Ajax.request({
						  		url:Ext.ContextPath+'/customer/transform.do',
						  		params:{
						  			parent_id:customerarea.getValue(),
						  			customer_ids:customer_ids
						  		},
						  		success:function(response){
						  			me.getStore().reload();
						  			win.close();
						  		}
						  	});
						}
					}]
				});
				win.show();
				
	  		}//if(aa=='yes'){
		})
    },
    onReload:function(){
    	var me=this;
    	me.getStore().reload();	
//    	var parent=node||me.getSelectionModel( ).getLastSelected( );
//		if(parent){
//		    me.getStore().reload({node:parent});
//		} else {
//		    me.getStore().reload();	
//		}      
    }
});
