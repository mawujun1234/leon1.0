Ext.define('Ems.baseinfo.ProdQueryGrid',{
	extend:'Ext.grid.Panel',
	requires: [
	     'Ems.baseinfo.EquipmentType'
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
		{dataIndex:'id',text:'编码',width:150
//		,renderer:function(value){
//			var values=value.split("_");
//			return values[0];
//		}
		},
		//{dataIndex:'level',text:'level',xtype: 'numbercolumn', format:'0.00'},
		//{dataIndex:'status',text:'status',xtype: 'numbercolumn', format:'0.00'},
//		{dataIndex:'subtype_name',text:'型号',renderer:function(){
//			return me.subtype_name;
//		}},
		{dataIndex:'name',text:'名称',flex:1},
		{dataIndex:'unit',text:'单位',flex:1},
		{dataIndex:'brand_name',text:'品牌',flex:1}
      ];
      
	  me.store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			model: 'Ems.baseinfo.EquipmentType',
			autoLoad:false,
			proxy:{
				type:'ajax',
				url:Ext.ContextPath+'/equipmentType/queryProdGrid.do',
				reader:{
			    	type:'json',
			    	root:'root'
			    }
			}
	  });
	  //me.store.getProxy().extraParams ={isGrid:true,status:true}
       
	  me.initAction();
      me.callParent();
	},
	initAction:function(){
//     	var me = this;
//     	var actions=[];
//     	
//       var create = new Ext.Action({
//		    text: '新建',
//		    itemId:'create',
//		    disabled:me.disabledAction,
//		    handler: function(b){
//		    	me.onCreate(null,b);
//		    },
//		    iconCls: 'form-add-button'
//		});
//		//me.addAction(create);
//		actions.push(create);
		
    },
    onReload:function(subtype_id){
    	this.store.getProxy().extraParams ={subtype_id:subtype_id};
    	this.store.reload();
    }
});
