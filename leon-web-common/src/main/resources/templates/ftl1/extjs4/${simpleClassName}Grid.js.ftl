<#assign simpleClassNameFirstLower = simpleClassName?uncap_first> 
<#-- //所在模块-->
<#assign module = basepackage?substring(basepackage?last_index_of(".")+1)>
Ext.define('Leon.${module}.${simpleClassName}Grid',{
	extend:'Ext.grid.Panel',
	requires: [
	     'Leon.${module}.${simpleClassName}'
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
     <#-----------------------------------------生成列--------------------------------- ----->
      me.columns=[
      <#list propertyColumns as propertyColumn>	
      <#if propertyColumn.isBaseType==true ||  propertyColumn.isIdProperty==true>
		<#if propertyColumn.jsType=='date'>
		{dataIndex:'${propertyColumn.property}',text:'${propertyColumn.label!propertyColumn.property}',xtype: 'datecolumn',   format:'Y-m-d'}<#if propertyColumn_has_next>,</#if>
		<#elseif propertyColumn.jsType=='int' || propertyColumn.jsType=='float'>
		{dataIndex:'${propertyColumn.property}',text:'${propertyColumn.label!propertyColumn.property}',xtype: 'numbercolumn', format:'0.00'}<#if propertyColumn_has_next>,</#if>
		<#else>
		{dataIndex:'${propertyColumn.property}',text:'${propertyColumn.label!propertyColumn.property}'}<#if propertyColumn_has_next>,</#if>
		</#if>
	  <#elseif propertyColumn.isConstantType==true>
	    {dataIndex:'${propertyColumn.property}',text:'${propertyColumn.label!propertyColumn.property}'}<#if propertyColumn_has_next>,</#if>
	  <#else>
		{dataIndex:'${propertyColumn.property}',text:'${propertyColumn.label!propertyColumn.property}'}<#if propertyColumn_has_next>,</#if>
	  </#if>
	  </#list>
      ];
      
      <#-----------------------------------------生成store--------------------------------- ----->
      <#if extenConfig.userModel==true>
	  me.store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			model: 'Leon.${module}.${simpleClassName}',
			autoLoad:true
	  });
	  <#else>
	  me.store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			autoLoad:true,
			proxy:{
				type: 'ajax',
			    url : Ext.ContextPath+'/${simpleClassNameFirstLower}/query',
			    headers:{ 'Accept':'application/json;'},
			    actionMethods: { read: 'POST' },
			    extraParams:{limit:50},
			    reader:{
					type:'json',
					root:'root',
					successProperty:'success',
					totalProperty:'total'		
				}
			}
	  });
	  </#if>
	  
	  <#-----------------------------------------是否启用page--------------------------------- ----->
      me.dockedItems= [{
	        xtype: 'pagingtoolbar',
	        store: me.store,  
	        dock: 'bottom',
	        displayInfo: true
	  }];
	  
	  <#-----------------------------------------生成工具栏--------------------------------- ----->
	  <#if extenConfig.createDelUpd=true>
	  me.tbar=	[{
		  	text: '新增',
			itemId:'create',
			handler: function(btn){
				var grid=btn.up("grid");
				var modelName=grid.model||grid.getStore().getProxy( ).getModel().getName( );
				var model=Ext.createModel(modelName,{      	//id:''
				});
				model.phantom =true;
				grid.form.getForm().loadRecord(model);//form是在app中定义的grid.form=form;
				
				grid.form.createAction=true;
				grid.form.down("button#save").enable();
				
			},
			iconCls: 'form-add-button'
		},{
			text: '更新',
			itemId:'update',
			handler: function(btn){
				var grid=btn.up("grid");
				
				grid.form.down("button#save").enable();
			},
			iconCls: 'form-update-button'
		},{
			text: '删除',
			itemId:'destroy',
			handler: function(btn){
				var grid=btn.up("grid");
		    	Ext.Msg.confirm("删除",'确定要删除吗?', function(btn, text){
					if (btn == 'yes'){
						var records=me.getSelectionModel( ).getSelection( );//.getLastSelected( );
						grid.getStore().remove( records );
						grid.getStore().sync({
							failure:function(){
								grid.getStore().reload();
							}
						});
					}
				});
			},
			iconCls: 'form-delete-button'
		},{
			text: '刷新',
			itemId:'reload',
			disabled:me.disabledAction,
			handler: function(btn){
				var grid=btn.up("grid");
				grid.getStore().reload();
			},
			iconCls: 'form-reload-button'
		}]
	  </#if>
       
      me.callParent();
	}
});
