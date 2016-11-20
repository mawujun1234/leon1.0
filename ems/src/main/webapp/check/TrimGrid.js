Ext.define('Ems.check.TrimGrid',{
	extend:'Ext.grid.Panel',
	requires: [
	     'Ems.check.Trim'
	],
	columnLines :true,
	stripeRows:true,
	viewConfig:{
		enableTextSelection:true
	},
	initComponent: function () {
      var me = this;
      me.columns=[
      	{xtype: 'rownumberer'},
//		{dataIndex:'id',header:'id'
//        },
		{dataIndex:'ecode',header:'条码',width:140
        },
		{dataIndex:'orginal_name',header:'原始位置'
        },
		{dataIndex:'orginal_type_name',header:'原始位置类型'
        },
		{dataIndex:'target_name',header:'目标'
        },
		{dataIndex:'target_type_name',header:'目标位置类型'
        },
//		{dataIndex:'check_id',header:'check_id'
//        },
		{dataIndex:'creater',header:'调整者'
        },
		{dataIndex:'createDate',header:'调整时间',width:150
		},
		{dataIndex:'trimType_name',header:'调整类型'
        }
      ];
      

	  me.store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			autoLoad:false,
			model: 'Ems.check.Trim',
			proxy:{
				type: 'ajax',
			    url : Ext.ContextPath+'/trim/queryByCheck.do',
			    headers:{ 'Accept':'application/json;'},
			    actionMethods: { read: 'POST' },
			    extraParams:{limit:50},
			    reader:{
					type:'json',//如果没有分页，那么可以把后面三行去掉，而且后台只需要返回一个数组就行了
					rootProperty:'root',
					successProperty:'success',
					totalProperty:'total'		
				}
			}
	  });

	  me.dockedItems=[];
      me.dockedItems.push({
	        xtype: 'pagingtoolbar',
	        store: me.store,  
	        dock: 'bottom',
	        displayInfo: true
	  });
	  
	  me.dockedItems.push({
	  		xtype: 'toolbar',
	  		dock:'top',
		  	items:[{
				text: '刷新',
				itemId:'reload',
				disabled:me.disabledAction,
				handler: function(btn){
					var grid=btn.up("grid");
					grid.getStore().reload();
				},
				iconCls: 'icon-refresh'
			}]
		});

       
      me.callParent();
	},
	onCreate:function(){
    	var me=this;
		var child=Ext.create('Ems.check.Trim',{

		});
		child.set("id",null);
		
		var formpanel=Ext.create('Ems.check.TrimForm',{});
		formpanel.loadRecord(child);
		
    	var win=Ext.create('Ext.window.Window',{
    		layout:'fit',
    		title:'新增',
    		modal:true,
    		width:400,
    		height:300,
    		closeAction:'hide',
    		items:[formpanel],
    		listeners:{
    			close:function(){
    				me.getStore().reload();
    			}
    		}
    	});
    	win.show();
    }
});
