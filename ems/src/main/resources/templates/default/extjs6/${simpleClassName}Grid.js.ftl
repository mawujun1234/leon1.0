<#assign simpleClassNameFirstLower = simpleClassName?uncap_first> 
<#-- //所在模块-->
<#assign module = basepackage?substring(basepackage?last_index_of(".")+1)>
Ext.define('${extenConfig.extjs_packagePrefix}.${module}.${simpleClassName}Grid',{
	extend:'Ext.grid.Panel',
	requires: [
	     '${extenConfig.extjs_packagePrefix}.${module}.${simpleClassName}'
	],
	columnLines :true,
	stripeRows:true,
	viewConfig:{
		enableTextSelection:true
	},
	initComponent: function () {
      var me = this;
     <#-----------------------------------------生成列--------------------------------- ----->
     <#-----------------------------------------在使用单元格编辑的时候，如果具有cobobox的话，就得先在这里生成store，然后再在列中进行应用--------------------------------- ----->
     <#if extenConfig.extjs_grid_enable_cellEditing==true>
     <#list propertyColumns as propertyColumn>	
     	<#if propertyColumn.showType=='combobox'>
     	<#if propertyColumn.isEnum=='true'>
     var store_${propertyColumn.property}=Ext.create('Ext.data.Store',{
     	storeId:'store_${propertyColumn.property}',
		fields: ['key', 'name'],
		data : [
		<#assign  keys=propertyColumn.showType_values?keys/>
		<#list keys as key>
			{"key":"${key}", "name":"${propertyColumn.showType_values["${key}"]}"}<#if key_has_next>,</#if>
		</#list>
		]
	});
		<#else>
	var store_${propertyColumn.property} =Ext.create('Ext.data.Store',{
		fields: ['key', 'name'],
		proxy: {
			autoLoad:true,
			type: 'ajax',
			url: Ext.ContextPath+'/${propertyColumn.property}/queryPager.do',
			reader: {
				type: 'json',
				rootProperty: '${propertyColumn.property}'
			}
		}
	})
		</#if>
     	</#if>
     </#list>
     </#if><#---------    <#if extenConfig.extjs_grid_enable_cellEditing==true>       ------->
      me.columns=[
      	{xtype: 'rownumberer'},
      <#list propertyColumns as propertyColumn>	
      	<#if propertyColumn.hidden==false>
		<#if propertyColumn.jsType=='date'>
		{dataIndex:'${propertyColumn.property}',header:'${propertyColumn.property_label!propertyColumn.property}',xtype: 'datecolumn', format:'Y-m-d H:i:s',width:150
			<#if extenConfig.extjs_grid_enable_cellEditing==true>
			,editor: {
                xtype: 'datefield',
                <#if propertyColumn.nullable=='false'>
                allowBlank: false,
                </#if>
                format : 'Y-m-d H:i:s',
                editable : false
            }
            </#if>
		}<#if propertyColumn_has_next>,</#if>
		<#elseif propertyColumn.jsType=='bool'>
		{dataIndex:'${propertyColumn.property}',header:'${propertyColumn.property_label!propertyColumn.property}',xtype: 'checkcolumn'	
            <#if extenConfig.extjs_grid_enable_cellEditing==true>
            ,listeners:{
				checkchange:function( checkcolumn, rowIndex, checked, eOpts ){
					var grid=checkcolumn.up("grid");
					var record=grid.getStore().getAt(rowIndex);
					record.set('${propertyColumn.property}',checked);
					record.save();
				}
			}
            <#else>
            ,stopSelection :false,
			processEvent : function(type) {  
            	if (type == 'click')  
                   return false;  
            }
            </#if>
		}<#if propertyColumn_has_next>,</#if>
		<#elseif propertyColumn.jsType=='int' >
		{dataIndex:'${propertyColumn.property}',header:'${propertyColumn.property_label!propertyColumn.property}',xtype: 'numbercolumn', format:'0',align : 'right'
			<#if extenConfig.extjs_grid_enable_cellEditing==true>
			,editor: {
                xtype: 'numberfield',
                <#if propertyColumn.nullable=='false'>
                allowBlank: false,
                </#if>
                allowDecimals:false,
                selectOnFocus:true 
            }
            </#if>
		}<#if propertyColumn_has_next>,</#if>
		<#elseif propertyColumn.jsType=='float'>
		{dataIndex:'${propertyColumn.property}',header:'${propertyColumn.property_label!propertyColumn.property}',xtype: 'numbercolumn', format:'0.00',align : 'right'
			<#if extenConfig.extjs_grid_enable_cellEditing==true>
			,editor: {
                xtype: 'numberfield',
                <#if propertyColumn.nullable=='false'>
                allowBlank: false,
                </#if>
                selectOnFocus:true 
            }
            </#if>
		}<#if propertyColumn_has_next>,</#if>
		<#else>
		{dataIndex:'${propertyColumn.property}',header:'${propertyColumn.property_label!propertyColumn.property}'
			<#if extenConfig.extjs_grid_enable_cellEditing==true>
			<#if propertyColumn.showType=='combobox'>
			<#if propertyColumn.isEnum=='true'>
			,editor: {
				queryMode: 'local',
				editable:false,
				forceSelection:true,
			    displayField: 'name',
			    valueField: 'key',
			    store: store_${propertyColumn.property},
				<#if propertyColumn.nullable=='false'>
                allowBlank: false,
                </#if>
                xtype:'combobox'
			},renderer: function(val,metaData,record ,rowIndex ,colIndex ,store,view ){
				var combobox_store=Ext.data.StoreManager.lookup('store_${propertyColumn.property}');
	            var record = combobox_store.findRecord('key',val); 
	            if (record != null){
	                return record.get("name"); 
	            } else {
	                return val;
	            }
	        }
			<#else><#------------------ 如果是从后台获取数据的combobox----->
			,editor: {
				queryMode: 'remote',
				editable:false,
				forceSelection:true,
			    displayField: 'name',
			    valueField: 'key',
			    store: store_${propertyColumn.property},
				<#if propertyColumn.nullable=='false'>
                allowBlank: false,
                </#if>
                xtype:'combobox'
			},renderer: function(val,metaData,record ,rowIndex ,colIndex ,store,view ){
				var combobox_store=Ext.data.StoreManager.lookup('store_${propertyColumn.property}');
	            var record = combobox_store.findRecord('key',val); 
	            if (record != null){
	                return record.get("name"); 
	            } else {
	                return val;
	            }
	        }
			</#if><#-------------------<#if propertyColumn.isEnum=='true'>-->
            <#else>
            ,editor: {
                xtype: 'textfield',
                <#if propertyColumn.nullable=='false'>
                allowBlank: false,
                </#if>
                selectOnFocus:true 
            }
            </#if>
            </#if><#-------        <#if extenConfig.extjs_grid_enable_cellEditing==true>   --->
        }<#if propertyColumn_has_next>,</#if>
		</#if>
		</#if><#-- <#if propertyColumn.hidden==false> -->
	  </#list>
      ];
      
      <#-----------------------------------------生成store--------------------------------- ----->

	  me.store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			autoLoad:true,
			model: '${extenConfig.extjs_packagePrefix}.${module}.${simpleClassName}',
			proxy:{
				type: 'ajax',
			    url : Ext.ContextPath+'/${simpleClassNameFirstLower}/queryPager.do',
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

	  me.dockedItems=[];
	  <#-----------------------------------------是否启用page--------------------------------- ----->
      me.dockedItems.push({
	        xtype: 'pagingtoolbar',
	        store: me.store,  
	        dock: 'bottom',
	        displayInfo: true
	  });
	  <#-------------------------生成查询条件得体toolbar ---------------------------------->
	  <#if (queryProperties?size>0) >
	  me.dockedItems.push({
	  	xtype: 'toolbar',
	  	dock:'top',
	  	//enableOverflow:true,
		items:[
	    <#list queryProperties as propertyColumn>
	    <#if propertyColumn.jsType=='date'>
	    	{
                xtype: 'datefield',
                itemId:'${propertyColumn.property}_start',
                fieldLabel: '开始时间',//${propertyColumn.property_label!propertyColumn.property}
	  			labelWidth:60,
	  			width:170,
                format : 'Y-m-d',
                editable : false
            },{
                xtype: 'datefield',
                itemId:'${propertyColumn.property}_end',
                fieldLabel: '结束时间',//${propertyColumn.property_label!propertyColumn.property}
	  			labelWidth:60,
	  			width:170,
                format : 'Y-m-d',
                editable : false
            },
		<#elseif propertyColumn.jsType=='bool'>
			{
                xtype: 'checkbox',
                itemId:'${propertyColumn.property}',
                fieldLabel: '${propertyColumn.property_label!propertyColumn.property}',
                labelWidth:60,
                width:100,
                cls: 'x-grid-checkheader-editor'
            },
		<#elseif propertyColumn.jsType=='int' >
		{
	        fieldLabel: '${propertyColumn.property_label!propertyColumn.property}',
	        itemId: '${propertyColumn.property}',
            allowDecimals:false,
            labelWidth:60,
            //width:100,
	        xtype:'numberfield'   
	    }<#if propertyColumn_has_next>,</#if>
		<#elseif propertyColumn.jsType=='float'>
		{
	        fieldLabel: '${propertyColumn.property_label!propertyColumn.property}',
	        itemId: '${propertyColumn.property}',
            labelWidth:60,
            //width:100,
	        xtype:'numberfield'   
	    }<#if propertyColumn_has_next>,</#if>
	    <#elseif propertyColumn.showType=='combobox'>
	    <#if propertyColumn.isEnum=='true'>
		{
			fieldLabel: '${propertyColumn.property_label!propertyColumn.property}',
			itemId: '${propertyColumn.property}',
			queryMode: 'local',
			editable:false,
			forceSelection:true,
		    displayField: 'name',
		    valueField: 'key',
		    labelWidth:60,
            width:150,
		    store: {
			    fields: ['key', 'name'],
			    data : [
			    <#assign  keys=propertyColumn.showType_values?keys/>
			    <#list keys as key>
			    	{"key":"${key}", "name":"${propertyColumn.showType_values["${key}"]}"}<#if key_has_next>,</#if>
				</#list>
			    ]
			},
			xtype:'combobox'
		}<#if propertyColumn_has_next>,</#if>
		<#else>
		{
			fieldLabel: '${propertyColumn.property_label!propertyColumn.property}',
			itemId: '${propertyColumn.property}',
			queryMode: 'remote',
			editable:false,
			forceSelection:true,
		    displayField: 'name',
		    valueField: 'key',
		    labelWidth:60,
            width:150,
		    store: {
			    fields: ['key', 'name'],
			    proxy: {
			    	autoLoad:true,
			        type: 'ajax',
			        url: Ext.ContextPath+'/${propertyColumn.property}/query.do',
			        reader: {
			            type: 'json',
			            rootProperty: '${propertyColumn.property}'
			        }
			    }
			},
			xtype:'combobox'
		}<#if propertyColumn_has_next>,</#if>
		</#if><#-- <#if propertyColumn.isEnum=='true'> -->
		<#elseif propertyColumn.showType=='radio'>
		<#if propertyColumn.isEnum=='true'>
		{
            xtype      : 'fieldcontainer',
            fieldLabel : '${propertyColumn.property_label!propertyColumn.property}',
            defaultType: 'radiofield',
            itemId:'${propertyColumn.property}',
            defaults: {
                flex: 1
            },
            layout: 'hbox',
            items: [
            <#assign  keys=propertyColumn.showType_values?keys/>
			<#list keys as key>
				{
                    boxLabel  : '${propertyColumn.showType_values["${key}"]}',
                    name: '${propertyColumn.property}',
                    inputValue: '${key}'
                }<#if key_has_next>,</#if>
			</#list>
            ]
        }<#if propertyColumn_has_next>,</#if>
		<#else>
			这个radio自动生成还没有做，请不要设置为radio
		</#if>
		<#else>
			{
                xtype: 'textfield',
				itemId:'${propertyColumn.property}',
                fieldLabel: '${propertyColumn.property_label!propertyColumn.property}',
                labelWidth:60,
                width:150,
                selectOnFocus:true 
            },
		</#if>
	    </#list>
	    	{
            	text:'查询',
            	iconCls:'icon-search',
            	handler:function(btn){
            		var grid=btn.up("grid");
	            	grid.getStore().getProxy().extraParams=Ext.apply(grid.getStore().getProxy().extraParams,{
	            		<#list queryProperties as propertyColumn>
	            		<#if propertyColumn.jsType=='date'>
	            		"params['${propertyColumn.property}_start']": Ext.Date.format(grid.down("#${propertyColumn.property}_start").getValue(),'Y-m-d H:i:s'),
	            		"params['${propertyColumn.property}_end']": Ext.Date.format(grid.down("#${propertyColumn.property}_end").getValue(),'Y-m-d H:i:s')<#if propertyColumn_has_next>,</#if>
						<#elseif propertyColumn.jsType=='bool'>
						"params['${propertyColumn.property}']":grid.down("#${propertyColumn.property}").getValue()<#if propertyColumn_has_next>,</#if>
						<#elseif propertyColumn.jsType=='int' >
						"params['${propertyColumn.property}']":grid.down("#${propertyColumn.property}").getValue()<#if propertyColumn_has_next>,</#if>
						<#elseif propertyColumn.jsType=='float'>
						"params['${propertyColumn.property}']":grid.down("#${propertyColumn.property}").getValue()<#if propertyColumn_has_next>,</#if>
						<#else>
						"params['${propertyColumn.property}']":grid.down("#${propertyColumn.property}").getValue()<#if propertyColumn_has_next>,</#if>
						</#if>
		                </#list>
	                });
            		grid.getStore().reload();
            	}
            }
	  	]
	  });
	  </#if>
	  
	   <#-----------------------------------------生成工具栏--------------------------------- ----->
	  <#if extenConfig.extjs_grid_createDelUpd_button=true>
	  me.dockedItems.push({
	  		xtype: 'toolbar',
	  		dock:'top',
		  	items:[{
				text: '新增',
				itemId:'create',
				handler: function(btn){
					me.onCreate();
				},
				iconCls: 'icon-plus'
			},{
			    text: '更新',
			    itemId:'update',
			    handler: function(){
			    	me.onUpdate();
					
			    },
			    iconCls: 'icon-edit'
			},{
			    text: '删除',
			    itemId:'destroy',
			    handler: function(){
			    	me.onDelete();    
			    },
			    iconCls: 'icon-trash'
			},{
				text: '刷新',
				itemId:'reload',
				disabled:me.disabledAction,
				handler: function(btn){
					var grid=btn.up("grid");
					grid.getStore().reload();
				},
				iconCls: 'icon-refresh'
			}]
		});
	  </#if>

	  <#-------------------------生成可编辑的grid ---------------------------------->
	  <#if extenConfig.extjs_grid_enable_cellEditing==true>
	  this.cellEditing = new Ext.grid.plugin.CellEditing({  
            clicksToEdit : 1  
      });  
	  this.plugins = [this.cellEditing];
	  //this.selType = 'cellmodel';//'rowmodel','checkboxmodel';
	  this.on('edit', function(editor, e) {
		e.record.save({
	  		success:function(){
	  			e.record.commit();
	  		}
	  	});
	  });
	  </#if>
       
      me.callParent();
	},
	<#if extenConfig.extjs_grid_createDelUpd_button=true>
	onCreate:function(){
    	var me=this;
		var child=Ext.create('${extenConfig.extjs_packagePrefix}.${module}.${simpleClassName}',{

		});
		child.set("id",null);
		
		var formpanel=Ext.create('${extenConfig.extjs_packagePrefix}.${module}.${simpleClassName}Form',{});
		formpanel.loadRecord(child);
		
    	var win=Ext.create('Ext.window.Window',{
    		layout:'fit',
    		title:'新增',
    		modal:true,
    		width:400,
    		height:300,
    		closeAction:'hide',
    		items:[formpanel],
    		listeners:{
    			close:function(){
    				me.getStore().reload();
    			}
    		}
    	});
    	win.show();
    },
    
     onUpdate:function(){
    	var me=this;

    	var node=me.getSelectionModel( ).getLastSelected();
    	if(node==null){
    		Ext.Msg.alert("提醒","请选择一行数据!");
    		return;
    	}

		var formpanel=Ext.create('${extenConfig.extjs_packagePrefix}.${module}.${simpleClassName}Form',{});
		formpanel.loadRecord(node);
		
    	var win=Ext.create('Ext.window.Window',{
    		layout:'fit',
    		title:'更新',
    		modal:true,
    		width:400,
    		height:300,
    		closeAction:'hide',
    		items:[formpanel]
    	});
    	win.show();
    },
    
    onDelete:function(){
    	var me=this;
    	var node=me.getSelectionModel( ).getLastSelected( );

		if(!node){
		    Ext.Msg.alert("消息","请先选择一行数据");	
			return;
		}
		var parent=node.parentNode;
		Ext.Msg.confirm("删除",'确定要删除吗?', function(btn, text){
				if (btn == 'yes'){
					node.erase({
					    failure: function(record, operation) {
			            	me.getStore().reload();
					    },
					    success:function(){
					    	me.getStore().reload();
					    }
				});
			}
		});
    }
    </#if>
});
