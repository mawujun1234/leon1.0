Ext.require("Ems.baseinfo.Pole");
Ext.onReady(function(){
	//区
     	var customer_2=Ext.create('Ext.form.field.ComboBox',{
	        fieldLabel: '区',
	        labelAlign:'right',
            labelWidth:20,
            width:120,
	        //xtype:'combobox',
	        //afterLabelTextTpl: Ext.required,
	        name: 'customer_2',
		    displayField: 'name',
		    valueField: 'id',
		    queryParam: 'name',
    		queryMode: 'remote',
//	        allowBlank: false,
	        store:Ext.create('Ext.data.Store', {
		    	fields: ['id', 'name'],
			    proxy:{
			    	type:'ajax',
			    	//extraParams:{type:1,edit:true},
			    	url:Ext.ContextPath+"/customer/query4combo.do",
			    	reader:{
			    		type:'json',
			    		root:'root'
			    	}
			    }
		   }),
		   listeners:{
		   	 change:function(field,newValue, oldValue){
				customer_0or1.clearValue( );
				if(newValue){
					customer_0or1.getStore().getProxy().extraParams={parent_id:newValue};
					//customer_0or1.getStore().reload();
				}
				
			 }
		   }
		});
		
		var customer_0or1=Ext.create('Ext.form.field.ComboBox',{
	        fieldLabel: '客户',
	        labelAlign:'right',
            labelWidth:40,
            width:250,
	        //xtype:'combobox',
	        //afterLabelTextTpl: Ext.required,
	        name: 'customer_0or1',
		    displayField: 'name',
		    valueField: 'id',
		    queryParam: 'name',
    		queryMode: 'remote',
    		triggerAction: 'query',
    		minChars:-1,
		    trigger1Cls: Ext.baseCSSPrefix + 'form-clear-trigger',
		    trigger2Cls: Ext.baseCSSPrefix + 'form-arrow-trigger',//'form-search-trigger',
			onTrigger1Click : function(){
			    var me = this;
			    me.setValue('');
			},
	        allowBlank: false,
	        store:Ext.create('Ext.data.Store', {
		    	fields: ['id', 'name'],
			    proxy:{
			    	type:'ajax',
			    	//extraParams:{type:1,edit:true},
			    	method:'POST',
			    	url:Ext.ContextPath+"/customer/query4combo.do",
			    	reader:{
			    		type:'json',
			    		root:'root'
			    	}
			    },
			    listeners:{
			    	beforeload:function(store){
				    	if(!customer_2.getValue()){
				    		delete customer_0or1.lastQuery;
				    		alert("请先选择'区'");
				    		return false;
				    	}
				    }
			    
			    }
			    
		   })
		});
		var workUnit_combox=Ext.create('Ext.form.field.ComboBox',{
	        fieldLabel: '作业单位',
	        labelAlign:'right',
            labelWidth:60,
	        //xtype:'combobox',
	        //afterLabelTextTpl: Ext.required,
	        name: 'workUnit_id',
		    displayField: 'name',
		    valueField: 'id',
	        allowBlank: false,
	        store:Ext.create('Ext.data.Store', {
		    	fields: ['id', 'name'],
			    proxy:{
			    	type:'ajax',
			    	url:Ext.ContextPath+"/workUnit/queryCombo.do",
			    	reader:{
			    		type:'json',
			    		root:'root'
			    	}
			    }
		   })
	    });
	var reload = new Ext.Action({
		    text: '查询',
		    itemId:'reload',
		    handler: function(){
//		    	if(customer_2.getValue() && !customer_0or1.getValue()){
//		    		alert("请选择一个客户");
//		    		return;
//		    	}
		    	if(!customer_2.getValue() && !workUnit_combox.getValue()){
		    		alert("请选择一个区或者作业单位!");
		    		return;
		    	}
		    	poleStore.getProxy().extraParams=Ext.apply(poleStore.getProxy().extraParams,{
		    		customer_2_id:customer_2.getValue(),
		    		customer_0or1_id:customer_0or1.getValue(),
		    		workunit_id:workUnit_combox.getValue()
		    	});
		    	poleStore.loadPage(1);
		    },
		    iconCls: 'form-reload-button'
		});	
		
	var initAllPoleNoLngLat = new Ext.Action({
		    text: '初始化',
		    
		    handler: function(){
				Ext.Msg.confirm("提醒","这里将初始化所有没有设置经纬度的点位!不受查询条件影响!",function(btn){
					if(btn=='yes'){
						Ext.getBody().mask("正在初始化....");
						Ext.Ajax.request({
							url:Ext.ContextPath+'/map/initAllPoleNoLngLat.do',
							success:function(response){
								Ext.getBody().unmask();
								var obj=Ext.decode(response.responseText);
								if(obj.root){
									alert(obj.root+"以上点位由于地址问题获取失败，请手工进行调整!");
								} else {
									alert("成功");
								}
								
							},
							failure:function(){
								Ext.getBody().unmask();
							}
						});
					}
				});
		    },
		    icon: '../icons/database_refresh.png'
		});	
	var poleStore=Ext.create('Ext.data.Store',{
				autoSync:false,
				pageSize:50,
				model: 'Ems.baseinfo.Pole',
				proxy: {
				        type: 'ajax',
				        url: Ext.ContextPath+'/map/queryPoles.do',
				        reader: {
				        	type:'json',
				            root: 'root'
				        }
				},
				autoLoad:false
		  });
    var polePanel=Ext.create('Ext.grid.Panel',{
    	region:'west',
    	width:400,
    	split: true,
		collapsible: true,
    	tbar:{
			xtype: 'container',
			layout: 'anchor',
			defaults: {anchor: '0'},
			defaultType: 'toolbar',
			items: [{
				items: [customer_2,customer_0or1] // toolbar 1
			},{
				items: [workUnit_combox,reload,initAllPoleNoLngLat] // toolbar 1
			}]
		},
    	columns:[
	      	{dataIndex:'status',text:'状态',width:40,menuDisabled:true,renderer : function(value,metadata, record, rowIndex, columnIndex, store) {
	      	   metadata.tdAttr = "data-qtip='" + record.get("status_name")+ "'";
			   if (value == 'uninstall') {
			    return "<img src='../icons/help_circle_blue.png' />";
			   } else if (value == 'installing'){
			    return "<img src='../icons/circle_blue.png' />";
			   }else if (value == 'using'){
			    return "<img src='../icons/circle_green.png' />";
			   }else if (value == 'hitch'){
			    return "<img src='../icons/circle_yellow.png' />";
			   }else if (value == 'cancel'){
			    return "<img src='../icons/circle_red.png' />";
			   }
			   return record.get("status_name");
			 }
			},
			{dataIndex:'code',text:'编号',width:60},
			{dataIndex:'name',text:'点位名称',width:160},
	      	{dataIndex:'province',text:'地址',flex:1,renderer:function(value,metaData ,record){
	      		return value+record.get("city")+record.get("area")+record.get("address")
	      	}}
	      ],
	      store:poleStore,
		  dockedItems: [{
		        xtype: 'pagingtoolbar',
		        store:poleStore,  
		        dock: 'bottom',
		        displayInfo: true
		  }]
    });   
	
	
	
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'border',
		items:[polePanel,{html:'地图',region:'center',
		split: true,
		collapsible: true}]
	});

});