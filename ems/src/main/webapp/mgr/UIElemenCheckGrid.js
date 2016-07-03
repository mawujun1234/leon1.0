Ext.define('Ems.mgr.UIElemenCheckGrid',{
	extend:'Ext.grid.Panel',
	requires: [
	     'Ems.mgr.UIElement'
	],
	columnLines :true,
	stripeRows:true,
	viewConfig:{
		enableTextSelection:true
	},
	selModel: {
        selType: 'checkboxmodel',
        checkOnly :true
    },
	initComponent: function () {
      var me = this;
      me.columns=[
      	{xtype: 'rownumberer'},
		{dataIndex:'text',header:'名称'
        },
		{dataIndex:'code',header:'编码'
        },
		{dataIndex:'memo',header:'备注',flex:1
        }
      ];
      

	  me.store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			autoLoad:false,
			model: 'Ems.mgr.UIElement',
			proxy:{
				type: 'ajax',
			    url : Ext.ContextPath+'/uIElement/queryByFunRole.do',
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

	 
       
      me.callParent();
	}
});
