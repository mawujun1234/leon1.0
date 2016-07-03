Ext.define("Ems.user.UserStore",{
	extend:"Ext.data.Model",
	idProperty:'id',
	fields:[
		{name:'id',type:'string'},
		{name:'edit',type:'bool'},
		{name:'look',type:'bool'},
		{name:'store_id',type:'string'},
		{name:'user_id',type:'string'},
		
		{name:'store_name',type:'string'},
		{name:'store_status',type:'string'},
		{name:'store_type',type:'int'},
		{name:'store_typeName',type:'string'}
	],
	proxy:{
		//type:'bajax',
		type:'ajax',
		actionMethods: { read: 'POST' },
		timeout :600000,
		headers:{ 'Accept':'application/json;'},
		reader:{
				type:'json',
				rootProperty:'root',
				successProperty:'success',
				totalProperty:'total'
				
		}
		,writer:{
			type:'json'
		},
		api:{
			read:Ext.ContextPath+'/user/query.do'
//			load : Ext.ContextPath+'/user/load.do',
//			create:Ext.ContextPath+'/user/create.do',
//			update:Ext.ContextPath+'/user/update.do',
//			destroy:Ext.ContextPath+'/user/destroy.do'
		}
	}
});

/**
 * 选择可访问的仓库
 */
Ext.define('Ems.mgr.UserStoreGrid',{
	extend:'Ext.grid.Panel',
	requires: [
	     'Ems.user.UserStore'
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
//	selModel:new Ext.selection.CheckboxModel({
//		checkOnly:true
//	}),
	initComponent: function () {
      var me = this;
      me.columns=[
		//{dataIndex:'id',text:'id'},
		{dataIndex:'store_name',text:'名称',flex:1},
		{dataIndex:'store_status',text:'状态',renderer:function(value){
			if(value){
				return "有效";
			} else {
				return "无效";
			}
		}},
		{dataIndex:'store_typeName',text:'类型'},
		{ xtype : 'checkcolumn', text : '查看', dataIndex : 'look',
//			renderer:function(value,metaData ,record){
//					return value;
//			},
			listeners:{
			checkchange:function(checkcolumn, rowIndex, checked){
				var record=me.getStore().getAt(rowIndex);
				if(checked){
					me.fireEvent('storeSelect',record,'look');
				} else {
					if(record.get("edit")){
						Ext.Msg.alert("消息","不可取消,可编辑就肯定可以看!");
						record.set("look",true);
						return;
					}
					me.fireEvent('storeDeselect',record,'look');
				}
				
			}
		}},
		{ xtype : 'checkcolumn', text : '编辑/所属', dataIndex : 'edit',listeners:{
			checkchange:function(checkcolumn, rowIndex, checked){
				var record=me.getStore().getAt(rowIndex);
				if(checked){
					me.fireEvent('storeSelect',record,'edit');
					record.set("look",true);
				} else {
					me.fireEvent('storeDeselect',record,'edit');
				}
			}
		}}
      ];
      
	  me.store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			model: 'Ems.user.UserStore',
			autoLoad:false,
			proxy:{
				type:'ajax',
				url:Ext.ContextPath+'/user/selectAllCheckedStore.do',
				reader:{
					type:'json',
					rootProperty:'root'
				}
			}
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
      //me.addEvents("storeSelect");
      //me.addEvents("storeDeselect");
      me.callParent();
	}
});
