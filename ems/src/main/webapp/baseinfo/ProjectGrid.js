Ext.define('Ems.baseinfo.ProjectGrid',{
	extend:'Ext.grid.Panel',
	requires: [
	     'Ems.baseinfo.Project'
	],
	columnLines :true,
	stripeRows:true,
	viewConfig:{
		stripeRows:true,
		listeners:{
			refresh:function(){
				//this.select(0);
			}
		}
	},
	initComponent: function () {
      var me = this;
      me.columns=[
		
		{dataIndex:'name',text:'项目名称',flex:1},
		{dataIndex:'status',text:'状态',width:40,renderer:function(value){
			if(value){
				return "有效";
			} else {
				return "<span style='color:red'>无效</>";
			}
		}},
		{dataIndex:'memo',text:'备注'}
      ];
      
	  me.store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			model: 'Ems.baseinfo.Project',
			autoLoad:true,
			listeners:{
				beforeload:function(store){
					var tbar=me.getDockedItems('toolbar[dock="top"]')[0];
					var name=tbar.getComponent( "query_name" );
					//alert(name);
					store.getProxy().extraParams={
						name:name.getValue()
					}
				}
			}
	  });
	  
      me.dockedItems= [{
	        xtype: 'pagingtoolbar',
	        store: me.store,  
	        dock: 'bottom',
	        displayInfo: true
	  }];
	  
	  me.tbar=	[{
	  		fieldLabel: '名称',
	        //afterLabelTextTpl: Ext.required,
	        name: 'name',
	        labelWidth:40,
	        itemId:"query_name",
	        xtype:'textfield'
	        //allowBlank: false
	  },{
			text: '刷新',
			itemId:'reload',
			disabled:me.disabledAction,
			handler: function(btn){
				var grid=btn.up("grid");
				//grid.getStore().reload();
				grid.getStore().loadPage(1);
			},
			iconCls: 'form-reload-button'
		}]
       
      me.callParent();
	}
});
