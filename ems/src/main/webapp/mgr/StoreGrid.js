/**
 * 选择可访问的仓库
 */
Ext.define('Ems.mgr.StoreGrid',{
	extend:'Ext.grid.Panel',
	requires: [
	     'Ems.baseinfo.Store'
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
	selModel:new Ext.selection.CheckboxModel({
		checkOnly:true
	}),
	initComponent: function () {
      var me = this;
      me.columns=[
		//{dataIndex:'id',text:'id'},
		{dataIndex:'name',text:'名称',flex:1},
		{dataIndex:'status',text:'状态',renderer:function(value){
			if(value){
				return "有效";
			} else {
				return "无效";
			}
		}},
		{dataIndex:'type_name',text:'类型'},
		{dataIndex:'memo',text:'注释'}
      ];
      
	  me.store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			model: 'Ems.baseinfo.Store',
			autoLoad:true
	  });
	  
	  
	  me.tbar=	[{
			text: '刷新',
			itemId:'reload',
			disabled:me.disabledAction,
			handler: function(btn){
				var grid=btn.up("grid");
				grid.getStore().reload();
			},
			iconCls: 'form-reload-button'
		}]
       
      me.callParent();
	}
});
