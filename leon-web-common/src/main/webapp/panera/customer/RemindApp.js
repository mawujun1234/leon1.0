

Ext.require("Leon.panera.customer.Remind");

Ext.onReady(function(){

	
	
	
	var    createGrid= function() {
        var me = this;
        var store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			model: 'Leon.panera.customer.Followup',
			autoLoad:true,
			proxy:{
				type:'ajax',
				url: Ext.ContextPath+'/followup/remind',
				reader:{
					type:'json',
					root:'root'
				}
			}
	  	});
		var grid=Ext.create('Ext.grid.Panel',{
			
			columnLines :true,
			stripeRows:true,
			loadMask: true,
		        viewConfig: {
		            //id: 'gv',
		            //trackOver: false,
		            //stripeRows: false,
		            plugins: [{
		                ptype: 'preview',
		                bodyField: 'nextContent',
		                expanded: true,
		                pluginId: 'preview'
		            }]
		    },
		    columns:[
				//{dataIndex:'id',text:'id'},
		        //{dataIndex:'createDate',text:'创建时间',xtype: 'datecolumn',   format:'Y-m-d'},
		        //{dataIndex:'method',text:'跟进方式'},
				//{dataIndex:'content',text:'内容'},
		        //{dataIndex:'feedbackDate',text:'反馈时间',xtype: 'datecolumn',   format:'Y-m-d'},
				//{dataIndex:'feedbackContent',text:'feedback'},
				//
		    	{dataIndex:'customer_name',text:'客户名称',flex:1,renderer:function(value){
		    		return "<a href='javascript:void(0);'>"+value+"</a>"
		    	}},
				{dataIndex:'nextDate',text:'下次跟进时间',xtype: 'datecolumn',   format:'Y-m-d',flex:1}
//				{dataIndex:'nextContent',text:'下次跟进内容',flex:1,renderer:function formatQtip(value,metadata){   
//				    metadata.tdAttr = "data-qtip='" + value + "'";
//				    return value;    
//					}
//				}
		    ],
		    store:store    
		});
		
		grid.on('cellclick',function(view,  td, cellIndex, record, tr, rowIndex){
			if(cellIndex!=0){
				return;
			}
			
//				
//		    	var grid=Ext.create('Leon.panera.customer.FollowupGrid',{
//					customer_id:record.get("customer_id")
//				});
//				var win=Ext.create('Ext.Window',{
//					title:'往来跟进记录',
//					modal:true,
//					//autoScroll :true,
//					width:600,
//					height:500,
//					layout:'fit',
//					items:[grid]
//				});
//				win.show();
				top.desktop.showWindowByJspPath("/panera/customer/CustomerApp.jsp",{
					methodName:"queryCustomer",
					params:{customer_id:record.get("customer_id")}
				});
				
			
		});
		me.store=store;
		me.tools=[{
		    type:'refresh',
		    tooltip: '刷新',
		    //store:store,
		    scope:me,
		    // hidden:true,
		    handler: function(event, toolEl, panelHeader){  	
		    	this.store.reload();
		    }
		}]
       
		if(me.execuMethod){
			me.execuIframeMethod();
		}
        return grid;
    }

    var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'fit',
		items:[createGrid()]
	});
});

