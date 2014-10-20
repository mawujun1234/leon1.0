//Ext.require("Ems.store.Order");
//Ext.require("Ems.store.OrderGrid");
//Ext.require("Ems.store.OrderTree");
//Ext.require("Ems.store.OrderForm");
Ext.onReady(function(){
	var store_combox=Ext.create('Ext.form.field.ComboBox',{
	        fieldLabel: '入库仓库',
	        labelAlign:'right',
            labelWidth:60,
	        //xtype:'combobox',
	        //afterLabelTextTpl: Ext.required,
	        name: 'store_id',
		    displayField: 'name',
		    valueField: 'id',
		    //queryParam: 'name',
    		//queryMode: 'remote',
    		//triggerAction: 'query',
    		//minChars:-1,
		    //trigger1Cls: Ext.baseCSSPrefix + 'form-clear-trigger',
		    //trigger2Cls: Ext.baseCSSPrefix + 'form-arrow-trigger',//'form-search-trigger',
			//onTrigger1Click : function(){
			//    var me = this;
			//    me.setValue('');
			//},
	        allowBlank: false,
	        store:Ext.create('Ext.data.Store', {
		    	fields: ['id', 'name'],
			    proxy:{
			    	type:'ajax',
			    	extraParams:{type:[1,3],look:true},
			    	url:Ext.ContextPath+"/store/queryCombo.do",
			    	reader:{
			    		type:'json',
			    		root:'root'
			    	}
			    }
		   })
	});	
	
	var store=Ext.create('Ext.data.Store',{
		autoLoad:false,
		fields: ['id', 'name','in_storage','out_storage','using','breakdown','wait_for_repair','to_repair','outside_repairing','inner_repairing','out_repair','in_transit','scrapped'],
		proxy:{
			    	type:'ajax',
			    	extraParams:{type:[1,3],look:true},
			    	url:Ext.ContextPath+"/propertystatus/query.do",
			    	reader:{
			    		type:'json',
			    		root:'root'
			    	}
		}
	});
	store.on("beforeload",function(store){
		store.getProxy().extraParams={store_id:store_combox.getValue()};
	});
	var grid=Ext.create('Ext.panel.Grid',{
		store:store,
		
	});
	
	
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'fit',
		items:[step1]
	});

});