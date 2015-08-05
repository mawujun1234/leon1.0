Ext.define('Ems.baseinfo.query.EquipmentProdQueryGrid',{
	extend:'Ext.tree.Panel',
	requires: [
	     'Ems.baseinfo.EquipmentProd',
	     'Ems.baseinfo.EquipmentProdForm'
	],
	columnLines :true,
	stripeRows:true,
	reserveScrollbar: true,
    
    //title: 'Core Team Projects',
    //height: 300,
    useArrows: true,
    rootVisible: false,
    //multiSelect: true,
    //singleExpand: true,
	initComponent: function () {
      var me = this;
      me.columns=[
      	//{xtype:'rownumberer',text:'序号'},
		{xtype:'treecolumn',dataIndex:'id',text:'编码',width:120},

//		{dataIndex:'subtype_name',text:'类型',renderer:function(){
//			return me.subtype_name;
//		}},
		{dataIndex:'name',text:'名称',flex:1},
		{dataIndex:'style',text:'型号',flex:1},
		{dataIndex:'quality_month',text:'质保(月)',width:50},
		{dataIndex:'memo',text:'描述'},
		{dataIndex:'unit',text:'单位',width:60},
		{dataIndex:'brand_name',text:'品牌',width:100},
		{dataIndex:'spec',text:'规格',flex:1,renderer:function(value,metadata,record){
								metadata.tdAttr = "data-qtip='" + value+ "'";
							    return value;
							}},
		{dataIndex:'status',text:'状态',width:60,renderer:function(value){
			if(value){
				return "有效";
			} else {
				return "<span style='color:red'>无效</>";
			}
		}}
      ];
      
//	  me.store=Ext.create('Ext.data.Store',{
//			autoSync:false,
//			pageSize:50,
//			model: 'Ems.baseinfo.EquipmentProd',
//			autoLoad:false
//	  });
      me.store= new Ext.data.TreeStore({
      	autoLoad:false,
      	nodeParam :'parent_id',
                model: 'Ems.baseinfo.EquipmentProd',
                proxy: {
                    type: 'ajax',
                    method:'POST',
                    reader:{
						type:'json',
						root:'root',
						successProperty:'success',
						totalProperty:'total'
						
					},
                    url: Ext.ContextPath+'/equipmentType/queryProds.do'
                },
                root: {
				    expanded: true,
				    text: "根节点"
				}
                //folderSort: true
      });
	  //me.store.getProxy().extraParams ={isGrid:true,status:true}
	  
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
     	
		
		actions.push('-');
		actions.push('-');
		var style = new Ext.form.field.Text({
		    emptyText : '型号关键字',
		    itemId:'style'
		});
		//me.addAction(reload);
		actions.push(style);
		me.style_field=style;
		//当选中的时候就表示查询所有品名
		var all_prod_checkbox=Ext.create('Ext.form.field.Checkbox',{
			boxLabel  : '所有品名',
            itemId      : 'all_prod_checkbox',
            //value:false,
            checked:false
		});
		actions.push(all_prod_checkbox);
		me.all_prod_checkbox=all_prod_checkbox;
		
		
		var reload = new Ext.Action({
		    text: '查询',
		    itemId:'reload',
		    handler: function(){
		    	if(all_prod_checkbox.getValue() && !style.getValue()){
		    		alert("请先输入型号关键字!");
		    		return;
		    	}
		    	
		    	me.onReload(null,{
						all_prod:all_prod_checkbox.getValue(),
						style:style.getValue()
				});
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
  
    onReload:function(node,params){
    	var me=this;
    	var parent=node||me.getSelectionModel( ).getLastSelected( );
		if(parent){
			//走展开子节点的
		    me.getStore().reload({node:parent});
		} else {
		    me.getStore().reload({params:params});	
		}      
    },
    /**
     * 当点击左边的树的时候，清空型号和全部型号的关键字
     */
    clearStyleAll_prod:function(){
    	this.style_field.setValue();
    	this.all_prod_checkbox.setValue(false);
    }
});
