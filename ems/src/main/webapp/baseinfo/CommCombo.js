Ext.define('Ems.baseinfo.TypeCombo',{
	extend:'Ext.form.field.ComboBox',
	requires: [
	     //'Ems.baseinfo.Brand'
	],
	fieldLabel: '大类',
    displayField: 'text',
    valueField: 'id',
   
    forceSelection:true,
    queryParam: 'name',
    queryMode: 'remote',
    name:'type_id',
//    triggerAction: 'query',
//    trigger1Cls: Ext.baseCSSPrefix + 'form-clear-trigger',
//    trigger2Cls: Ext.baseCSSPrefix + 'form-arrow-trigger',//'form-search-trigger',
//	onTrigger1Click : function(){
//	    var me = this;
//	    me.setValue('');
//	},
	initComponent: function () {
       var me = this;
       var store=Ext.create('Ext.data.Store', {
		    fields: ['id', 'text'],
		    proxy:{
		    	type:'ajax',
		    	actionMethods: {
			        create : 'POST',
			        read   : 'POST',
			        update : 'POST',
			        destroy: 'POST'
			    },
		    	url:Ext.ContextPath+"/equipmentType/queryTypeCombo.do",
		    	reader:{
		    		type:'json',
		    		root:'root'
		    	}
		    },
		    listeners:{
//			    	beforeload:function(store){
//			    		//包含所有的选项
//			    		if(me.containAll){
//			    			store.getProxy().extraParams=Ext.apply(store.getProxy().extraParams,{
//			    				containAll:false
//			    			})
//			    		}
//			    	}
			}
	   });
	   me.store=store;

       me.callParent();
	}
});

Ext.define('Ems.baseinfo.SubtypeCombo',{
	extend:'Ext.form.field.ComboBox',
	requires: [
	     //'Ems.baseinfo.Brand'
	],
	fieldLabel: '类型',
    displayField: 'text',
    valueField: 'id',
   
    forceSelection:true,
    queryParam: 'name',
    queryMode: 'remote',
    name:'subtype_id',
//    triggerAction: 'query',
//    trigger1Cls: Ext.baseCSSPrefix + 'form-clear-trigger',
//    trigger2Cls: Ext.baseCSSPrefix + 'form-arrow-trigger',//'form-search-trigger',
//	onTrigger1Click : function(){
//	    var me = this;
//	    me.setValue('');
//	},
	initComponent: function () {
       var me = this;
       var store=Ext.create('Ext.data.Store', {
		    fields: ['id', 'text'],
		    proxy:{
		    	type:'ajax',
		    	actionMethods: {
			        create : 'POST',
			        read   : 'POST',
			        update : 'POST',
			        destroy: 'POST'
			    },
		    	url:Ext.ContextPath+"/equipmentType/querySubtypeCombo.do",
		    	reader:{
		    		type:'json',
		    		root:'root'
		    	}
		    },
		    listeners:{
			    	beforeload:function(store){
			    		//包含所有的选项
			    		if(me.containAll){
			    			store.getProxy().extraParams=Ext.apply(store.getProxy().extraParams,{
			    				containAll:true
			    			})
			    		}
			    	}
			}
	   });
	   me.store=store;

       me.callParent();
	}
});

Ext.define('Ems.baseinfo.ProdCombo',{
	extend:'Ext.form.field.ComboBox',
	requires: [
	     //'Ems.baseinfo.Brand'
	],
	fieldLabel: '品名',
    displayField: 'text',
    valueField: 'id',
    minChars:1,
    forceSelection:true,
    queryParam: 'name',
    queryMode: 'remote',
    name:'prod_id',
//    triggerAction: 'query',
//    trigger1Cls: Ext.baseCSSPrefix + 'form-clear-trigger',
//    trigger2Cls: Ext.baseCSSPrefix + 'form-arrow-trigger',//'form-search-trigger',
//	onTrigger1Click : function(){
//	    var me = this;
//	    me.setValue('');
//	},
	initComponent: function () {
       var me = this;
	   var store=Ext.create('Ext.data.Store', {
		    fields: ['id', 'text'],
		    autoLoad:false,
		    proxy:{
		    	type:'ajax',
		    	actionMethods: {
			        create : 'POST',
			        read   : 'POST',
			        update : 'POST',
			        destroy: 'POST'
			    },
		    	url:Ext.ContextPath+"/equipmentType/queryProdCombo.do",
		    	reader:{
		    		type:'json',
		    		root:'root'
		    	}
		    },
		    listeners:{
			    	beforeload:function(store){
			    		//包含所有的选项
			    		if(me.containAll){
			    			store.getProxy().extraParams=Ext.apply(store.getProxy().extraParams,{
			    				containAll:true
			    			})
			    		}
			    	}
			}
	   });
	   me.store=store;
       me.callParent();
	}
});

Ext.define('Ems.baseinfo.BrandCombo',{
	extend:'Ext.form.field.ComboBox',
	requires: [
	     //'Ems.baseinfo.Brand'
	],
	fieldLabel: '品牌',
    displayField: 'name',
    valueField: 'id',
   minChars:1,
    forceSelection:true,
    queryParam: 'name',
    queryMode: 'remote',
    name:'brand_id',
//    triggerAction: 'query',
//    trigger1Cls: Ext.baseCSSPrefix + 'form-clear-trigger',
//    trigger2Cls: Ext.baseCSSPrefix + 'form-arrow-trigger',//'form-search-trigger',
//	onTrigger1Click : function(){
//	    var me = this;
//	    me.setValue('');
//	},
	initComponent: function () {
       var me = this;
	   var store=Ext.create('Ext.data.Store', {
		    fields: ['id', 'name'],
		    proxy:{
		    	type:'ajax',
		    	actionMethods: {
			        create : 'POST',
			        read   : 'POST',
			        update : 'POST',
			        destroy: 'POST'
			    },
		    	url:Ext.ContextPath+"/brand/queryBrandCombo.do",
		    	reader:{
		    		type:'json',
		    		root:'root'
		    	}
		    },
		    listeners:{
			    	beforeload:function(store){
			    		//包含所有的选项
			    		if(me.containAll){
			    			store.getProxy().extraParams=Ext.apply(store.getProxy().extraParams,{
			    				containAll:true
			    			})
			    		}
			    	}
			}
	   });
	   me.store=store;
       me.callParent();
	}
});

Ext.define('Ems.baseinfo.SupplierCombo',{
	extend:'Ext.form.field.ComboBox',
	requires: [
	     //'Ems.baseinfo.Brand'
	],
	fieldLabel: '供应商',
    displayField: 'name',
    valueField: 'id',
   forceSelection:true,
    minChars:1,
    queryParam: 'name',
    queryMode: 'remote',
    name:'supplier_id',
    triggerAction: 'query',
    trigger1Cls: Ext.baseCSSPrefix + 'form-clear-trigger',
    //trigger2Cls: Ext.baseCSSPrefix + 'form-arrow-trigger',//'form-search-trigger',
	onTrigger1Click : function(){
	    var me = this;
	    me.setValue('');
	},
	initComponent: function () {
       var me = this;
	   var store=Ext.create('Ext.data.Store', {       		
		    fields: ['id', 'name'],
		    proxy:{
		    	type:'ajax',
		    	actionMethods: {
			        create : 'POST',
			        read   : 'POST',
			        update : 'POST',
			        destroy: 'POST'
			    },
		    	url:Ext.ContextPath+"/supplier/querySupplierCombo.do",
		    	reader:{
		    		type:'json',
		    		root:'root'
		    	}
		    },
		    listeners:{
			    	beforeload:function(store){
			    		//包含所有的选项
			    		if(me.containAll){
			    			store.getProxy().extraParams=Ext.apply(store.getProxy().extraParams,{
			    				containAll:true
			    			})
			    		}
			    	}
			}
	   });
	   me.store=store;
       me.callParent();
	}
});