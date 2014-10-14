
Ext.define('Ems.baseinfo.SubtypeCombo',{
	extend:'Ext.form.field.ComboBox',
	requires: [
	     //'Ems.baseinfo.Brand'
	],
	fieldLabel: '小类',
    displayField: 'text',
    valueField: 'id',
   
    
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
   
    
    queryParam: 'name',
    queryMode: 'remote',
    name:'supplier_id',
    triggerAction: 'query',
    trigger1Cls: Ext.baseCSSPrefix + 'form-clear-trigger',
    trigger2Cls: Ext.baseCSSPrefix + 'form-arrow-trigger',//'form-search-trigger',
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
		    }
	   });
	   me.store=store;
       me.callParent();
	}
});