Ext.define('Ems.baseinfo.EquipmentProdQueryGrid',{
	extend:'Ext.grid.Panel',
	requires: [
	     'Ems.baseinfo.EquipmentProd'
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
		{dataIndex:'id',text:'编码',width:60},
		{dataIndex:'name',text:'名称',width:80},
		{dataIndex:'unit',text:'单位',width:50},
		{dataIndex:'brand_name',text:'品牌',width:60},
		{dataIndex:'style',text:'型号',flex:1},
		{dataIndex:'quality_month',text:'质保(月)',width:50},
		{dataIndex:'memo',text:'描述'},
		{dataIndex:'spec',text:'规格',flex:1,renderer:function(value,metadata,record){
			metadata.tdAttr = "data-qtip='" + value+ "'";
		    return value;
			}
		}
		
      ];
      
	  me.store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			model: 'Ems.baseinfo.EquipmentProd',
			autoLoad:false,
			proxy:{
				type:'ajax',
				//url:Ext.ContextPath+'/equipmentType/queryProdGrid.do',
				url:Ext.ContextPath+'/equipmentType/queryProds.do',
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
