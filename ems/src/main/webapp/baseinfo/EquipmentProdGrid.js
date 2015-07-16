Ext.define('Ems.baseinfo.EquipmentProdGrid',{
	extend:'Ext.tree.Panel',
	requires: [
	     'Ems.baseinfo.EquipmentProd',
	     'Ems.baseinfo.EquipmentProdForm'
	],
	columnLines :true,
	stripeRows:true,
//	viewConfig:{
//		stripeRows:true,
//		listeners:{
//			refresh:function(){
//				//this.select(0);
//			}
//		}
//	},
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
     	
       var create = new Ext.Action({
		    text: '新建',
		    itemId:'create',
		    disabled:me.disabledAction,
		    handler: function(b){
		    	me.onCreate();
		    },
		    iconCls: 'form-add-button'
		});
		actions.push(create);
		
		
		var createTJ = new Ext.Action({
		    text: '增加套件组成',
		    hidden:true,
		    itemId:'createTJ',
		    handler: function(){
		    	me.createTJ();
		    },
		    icon:'../icons/package_add.png'
		    //iconCls: 'form-reload-button'
		});
		actions.push(createTJ);
		
		actions.push('-');
		actions.push('-');
		//me.addAction(create);
		
		var update = new Ext.Action({
		    text: '更新',
		    itemId:'update',
		    disabled:me.disabledAction,
		    handler: function(b){
		    	me.onUpdate(null,b);
		    },
		    iconCls: 'form-update-button'
		});
		//me.addAction(create);
		actions.push(update);
		
		var destroy = new Ext.Action({
		    text: '删除',
		    itemId:'destroy',
		    disabled:me.disabledAction,
		    handler: function(){
		    	me.onDelete();    
		    },
		    iconCls: 'form-delete-button'
		});
		//me.addAction(destroy);
		actions.push(destroy)
		
		

		var checkbox=Ext.create('Ext.form.field.Checkbox',{
			boxLabel  : '只有在用',
            name      : 'status',
            //value:false,
            checked:true,
            listeners:{
            	change:function(checkbox,newValue, oldValue){
            		me.store.getProxy().extraParams.status =newValue;
            		me.store.reload();
            	}
            }
		});
		actions.push(checkbox);
		
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
    onCreate:function(){
    	var me=this;

    	var values={};

    	var subtype=me.tree.getSelectionModel( ).getLastSelected( )||me.tree.getRootNode( );    

    	var subtype_id=subtype.get("id");
//    	if(subtype_id!="root"){
//    		subtype_id=subtype_id;
//    	}
		var initValue={
		    'subtype_id':subtype_id,
		    status:1,
		    text:''
		};
		//if(parent.get("levl")){
		//	initValue.levl=parent.get("levl")+1;
		//}
		//	if(initValue.levl==2){
		//		
		//	}
		//initValue.levl=3;

    	values=Ext.applyIf(values,initValue);

		var child=Ext.createModel("Ems.baseinfo.EquipmentProd",values);
		var form=Ext.create('Ems.baseinfo.EquipmentProdForm',{
			//isprod:true,
			//parent_id:parent_id,
			isUpdate:false,
			url:Ext.ContextPath+"/equipmentType/createProd.do",
			//isType:initValue.levl==1?true:false,
			listeners:{
				saved:function(){
					win.close();
					me.getStore().reload();
					//me.tree.getStore().reload({node:parent});
				}
			}
		});
		form.getForm().loadRecord(child);
		var win=new Ext.window.Window({
			items:[form],
			layout:'fit',
			closeAction:'destroy',
			width:300,
			height:400,
			modal:true
		});
		//form.win=win
		win.show();

    },
    createTJ:function(){
    	var me=this;
    	var parent=me.getSelectionModel( ).getLastSelected( );
		if(!parent){
			alert("请先选择一个套件品名!");
		    return;
		} 
		//alert(parent.get("parent_id"));
		if(parent.get("parent_id")){
			alert("不能再进行拆分了");
			return;
		} 
		var unit=parent.get("unit");
		if(unit!="套" && unit!="对"){
			alert("你选的品名单位不是'套'或'对',请确认有没有选错.");
		}
		
		
		
		var child=Ext.createModel('Ems.baseinfo.EquipmentProd',{
			parent_id:parent.get("id"),
			brand_id:parent.get("brand_id"),
			status:true,
			subtype_id:parent.get("subtype_id")
		});
		//套件的form
		var form=Ext.create('Ems.baseinfo.EquipmentProdForm',{
			//isprod:true,
			//parent_id:parent.get("id"),
			isUpdate:false,
			url:Ext.ContextPath+"/equipmentType/createProdTJ.do",
			//isType:initValue.levl==1?true:false,
			listeners:{
				saved:function(){
					win.close();
					parent.set("leaf",false);
					me.getStore().reload({node:parent});
					//me.tree.getStore().reload({node:parent});
				}
			}
		});
		form.getForm().loadRecord(child);
		
		var subtype_id=form.getForm().findField("subtype_id");
		subtype_id.hide();
		var parent_id=form.getForm().findField("parent_id");
		parent_id.show();
		var brand_id=form.getForm().findField("brand_id");
		//brand_id.setValue(parent.get("brand_id"));
		//brand_id.getStore().load();
		var project_model= brand_id.getStore().createModel({id:parent.get("brand_id"),name:parent.get("brand_name")});
		brand_id.setValue(project_model);

		
		var win=new Ext.window.Window({
			items:[form],
			layout:'fit',
			title:'增加套件',
			closeAction:'destroy',
			width:300,
			height:400,
			modal:true
		});
		//form.win=win
		win.show();
		
    },
    onUpdate:function(){
    	var me=this;
    	var record=me.getSelectionModel().getLastSelected( );
		if(!record){
			Ext.Msg.alert("消息","请先选择品名!");	
			return;
		}
		
		var form=Ext.create('Ems.baseinfo.EquipmentProdForm',{
			isUpdate:true,
			url:Ext.ContextPath+"/equipmentType/updateProd.do",
			listeners:{
				saved:function(){
					//form.updateRecord();
					win.close();
					me.getStore().reload();
				}
			}
		});
		form.getForm().loadRecord(record);
		var subtype_id=form.getForm().findField("subtype_id");
		subtype_id.hide();
		var parent_id=form.getForm().findField("parent_id");
		parent_id.hide();
		var id=form.getForm().findField("id");
		id.setReadOnly(true);
		//end_id.setValue(record.get("id").substr(record.get("parent_id").length));//replace(record.get("parent_id","")));
		//alert(record.get("brand_name"));
		var brand_model=form.getForm().findField("brand_id").getStore().createModel({id:record.get("brand_id"),name:record.get("brand_name")});
		form.getForm().findField("brand_id").setValue(brand_model);
		//form.getForm().findField("brand_id").getStore().reload();//.setValue(brand_model);

		
		
		var win=new Ext.window.Window({
			items:[form],
			layout:'fit',
			closeAction:'destroy',
			width:300,
			height:400,
			modal:true
		});
		//form.win=win
		win.show();	
		    	
		
    },
    onDelete:function(){
    	var me=this;
    	var record=me.getSelectionModel( ).getLastSelected( );

    	//console.dir(record);
		if(!record){
		    Ext.Msg.alert("消息","请先选择一条品名");	
			return;
		}
		//return;

		Ext.Msg.confirm("删除",'确定要删除吗?', function(btn, text){
				if (btn == 'yes'){
					Ext.Ajax.request({
						url:Ext.ContextPath+'/equipmentType/destroyProd.do',
//						params:{
//							id:record.get("id"),
//							levl:record.get("levl")
//						},
						params:record.raw,
						method:'POST',
						success:function(){
							//var parent=me.tree.getSelectionModel( ).getLastSelected( )||me.tree.getRootNode( );  
							me.getStore().reload({node:record.parentNode});
							//record.destroy();
							//me.getStore().remove(record);
							//me.select(0);
						
						}
					});
			}
		});
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
