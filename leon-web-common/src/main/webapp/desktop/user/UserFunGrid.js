Ext.define('Leon.desktop.user.UserFunGrid',{
	extend:'Ext.grid.Panel',
	requires: [
	     //'Leon.desktop.fun.Fun'
	],
	columnLines :true,
	stripeRows:true,
	viewConfig:{
		stripeRows:true,
		listeners:{
			refresh:function(){
				this.select(0);
			}
		}
	},
	initComponent: function () {
      var me = this;
      me.columns=[
//      	{ xtype : 'checkcolumn', text : 'Active', dataIndex : 'active',
//      		listeners:{
//      			checkchange:function(checkcolumn, rowIndex, checked){
//      				//
//      				me.fireEvent("checkchange",checkcolumn,rowIndex,checked);
//      			}
//      		}
//      	},
		{dataIndex:'text',text:'名称'},
		{dataIndex:'url',text:'地址',flex:1}
      ];
      
	  me.store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			//model: 'Leon.desktop.fun.Fun',
			fields : ['id', 'text', 'url','bussinessType', 'active'],
			proxy  : {
		        type   : 'ajax',
		        url:Ext.ContextPath+'/user/queryFun',
		        reader : {
		            type : 'json',
		            root : 'root'
		        }
		    },
			autoLoad:false
	  });
	  
      me.dockedItems= [{
	        xtype: 'pagingtoolbar',
	        store: me.store,  
	        dock: 'bottom',
	        displayInfo: true
	  }];
       
	  me.addEvents("checkchange");
      me.callParent();
	}
});