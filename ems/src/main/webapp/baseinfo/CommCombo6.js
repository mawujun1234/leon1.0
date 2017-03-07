//用于这些公用的combobox
Ext.define("Ems.baseinfo.IdName",{
	extend:"Ext.data.Model",
	fields:[
		{name:'id',type:'string'},
		{name:'key',type:'string'},//值等于id，为了显示"所有"
		{name:'name',type:'string'},
		{name:'text',type:'string'}
	]
});
Ext.define('Ems.baseinfo.TypeCombo',{
	extend:'Ext.form.field.ComboBox',
	xtype:'typecombo',
	requires: [
	     //'Ems.baseinfo.Brand'
	],
	fieldLabel: '大类',
    displayField: 'name',
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
		    fields: ['id', 'name','text'],
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
		    		rootProperty:'rootProperty'
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
	xtype:'subtypecombo',
	requires: [
	     //'Ems.baseinfo.Brand'
	],
	fieldLabel: '类型',
    displayField: 'name',
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
		    fields: ['id', 'name','text'],
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
		    		rootProperty:'root'
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
	xtype:'prodcombo',
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
		    		rootProperty:'root'
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
	xtype:'brandcombo',
	requires: [
	     //'Ems.baseinfo.Brand'
	],
	fieldLabel: '品牌',
    displayField: 'name',
    valueField: 'id',
   minChars:-1,
    forceSelection:true,
    queryParam: 'name',
    queryMode: 'remote',
    name:'brand_id',
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
		    	url:Ext.ContextPath+"/brand/queryBrandCombo.do",
		    	reader:{
		    		type:'json',
		    		rootProperty:'root'
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
	xtype:'suppliercombo',
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
		    		rootProperty:'root'
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

Ext.define('Ems.baseinfo.ProjectCombo',{
	extend:'Ext.form.field.ComboBox',
	xtype:'projectcombo',
	fieldLabel: '项目',
	        labelAlign:'right',
            labelWidth:40,
            //flex:1,
	        //xtype:'combobox',
	        //afterLabelTextTpl: Ext.required,
	        name: 'project_id',
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
			initComponent: function () {
       			var me = this;
       			me.store=Ext.create('Ext.data.Store', {
			    	fields: ['id', 'name'],
				    proxy:{
				    	type:'ajax',
				    	actionMethods: {
					        create : 'POST',
					        read   : 'POST',
					        update : 'POST',
					        destroy: 'POST'
					    },
				    	//extraParams:{type:[1,3],look:true},
				    	url:Ext.ContextPath+"/project/queryCombo.do",
				    	reader:{
				    		type:'json',
				    		rootProperty:'root'
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
				    }
			   });
       			me.callParent();
			}
});

//客户的分区
Ext.define('Ems.baseinfo.CustomerAreaCombo', {
			extend : 'Ext.form.field.ComboBox',
			xtype : 'customerareacombo',
			fieldLabel : '区',
			labelAlign : 'right',
			labelWidth : 60,
			// xtype:'combobox',
			// afterLabelTextTpl: Ext.required,
			name : 'parent_id',
			displayField : 'name',
			valueField : 'key',
			allowBlank : true,
			editable:false,
			look : true,//获取只读的仓库
			edit : false,//获取可以编辑操作的仓库，就是可以有权限入库的
			
			selFirst:false,
			showBlank:false,//是否显示“无”的数据
			autoLoad:true,
			
			initComponent : function() {
				var me = this;
				var store = Ext.create('Ext.data.Store', {
							//fields : ['id', 'name'],
					model:'Ems.baseinfo.IdName',
					autoLoad:me.autoLoad,
							proxy : {
								type : 'ajax',
								extraParams : {
									// type : [1, 3],
									showBlank:me.showBlank,
									look : me.look,
									edit : me.edit
								},
								url : Ext.ContextPath + "/customer/queryAreaCombo.do",
								reader : {
									type : 'json',
									rootProperty : 'root'
								}
							}
						})
				me.store = store;
				if(!me.value && me.selFirst){
					me.store.on("load",function(myStore){
						if(myStore.getCount( ) >0){
							var r=null;
							if(me.showBlank==true){
								r=myStore.getAt(1);//第一行是无
							} else {
								r=myStore.getAt(0);//第一行是,正确的数据
							}
					 		me.select( r );
						 	me.fireEvent("select", me, r);
					 	}
					})
				}
				me.callParent();
			}
		});
Ext.define('Ems.baseinfo.CustomerCombo', {
			extend : 'Ext.form.field.ComboBox',
			xtype : 'customercombo',
			requires : [
			// 'Ems.baseinfo.Brand'
			],
			fieldLabel: '客户',
	        labelAlign:'right',
            labelWidth:60,
            //width:250,
	        //xtype:'combobox',
	        //afterLabelTextTpl: Ext.required,
	        name: 'customer_id',
		    displayField: 'name',
		    valueField: 'key',
		    queryParam: 'name',
    		queryMode: 'remote',
    		triggerAction: 'query',
    		minChars:-1,
			triggers : {
				foo : {
					cls : Ext.baseCSSPrefix + 'form-clear-trigger',
					weight : -1,
					handler : function() {
						var me = this;
						me.setValue('');
					}
				}
				// bar: {
				// cls: Ext.baseCSSPrefix + 'form-arrow-trigger'
				// // weight: -1,
				// // handler: function() {
				// // console.log('bar trigger clicked');
				// // }
				// }
			},
			
			selFirst:false,
			showBlank:false,//是否显示“无”的数据
			autoLoad:false,
			
			initComponent : function() {
				var me = this;
				var store = Ext.create('Ext.data.Store', {
							//fields : ['id', 'name'],
					autoLoad:me.autoLoad,
					model:'Ems.baseinfo.IdName',
					//fields: ['id', 'name'],
							proxy : {
								type : 'ajax',
								actionMethods : {
									create : 'POST',
									read : 'POST',
									update : 'POST',
									destroy : 'POST'
								},
								extraParams:{
							    	showBlank:me.showBlank
							    },
								url : Ext.ContextPath+ "/customer/queryCombo.do",
								reader : {
									type : 'json',
									rootProperty : 'root'
								}
							},
							listeners : {
							}
						});
				me.store = store;
				if(!me.value && me.selFirst){
					me.store.on("load",function(myStore){
						if(myStore.getCount( ) >0){
							var r=null;
							if(me.showBlank==true){
								r=myStore.getAt(1);//第一行是无
							} else {
								r=myStore.getAt(0);//第一行是,正确的数据
							}
					 		me.select( r );
						 	me.fireEvent("select", me, r);
					 	}
					})
				}
				me.callParent();
			}
		});