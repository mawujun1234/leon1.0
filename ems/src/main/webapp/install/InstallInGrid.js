Ext.define('Ems.install.InstallInGrid',{
	extend:'Ext.grid.Panel',
	requires: [
	     'Ems.install.InstallIn'
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
		//{dataIndex:'id',text:'id'},
		//
      	{xtype:'rownumberer'},
		{dataIndex:'store_name',text:'仓库名称'},
		{dataIndex:'type_name',text:'入库类型'},
		{dataIndex:'workUnit_name',text:'作业单位'},
		//{dataIndex:'operater',text:'操作者'},
		{dataIndex:'operateDate',text:'返库时间',xtype: 'datecolumn',   format:'Y-m-d'},
		{dataIndex:'memo',text:'备注'}
      ];
      
	  me.store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			model: 'Ems.install.InstallIn',
			autoLoad:false,
			proxy:{
				type:'ajax',
				actionMethods:{read:'POST'},
				url:Ext.ContextPath+'/installIn/queryMain.do',
				reader:{
					type:'json',
					root:'root'
				}
			}
	  });
	  
	   me.store.on("beforeload",function(store){
		store.getProxy().extraParams={
					store_id:store_combox.getValue(),
					workUnit_id:workUnit_combox.getValue(),
					//type:types.getValue( ).type,
					type:type_combox.getValue(),
					operateDate_start: operateDate_start.getRawValue(),
					operateDate_end: operateDate_end.getRawValue()
				  };
	 });
	  
      me.dockedItems= [{
	        xtype: 'pagingtoolbar',
	        store: me.store,  
	        dock: 'bottom',
	        displayInfo: true
	  }];
	  
	 var store_combox=Ext.create('Ext.form.field.ComboBox',{
	        fieldLabel: '仓库',
	        labelAlign:'right',
            labelWidth:30,
	        //xtype:'combobox',
	        //afterLabelTextTpl: Ext.required,
	        name: 'store_id',
		    displayField: 'name',
		    valueField: 'id',
	        //allowBlank: false,
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
	  
	  var workUnit_combox=Ext.create('Ext.form.field.ComboBox',{
	        fieldLabel: '作业单位',
	        labelAlign:'right',
            labelWidth:60,
	        //xtype:'combobox',
	        //afterLabelTextTpl: Ext.required,
	        name: 'workUnit_id',
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
			    	url:Ext.ContextPath+"/workUnit/queryCombo.do",
			    	reader:{
			    		type:'json',
			    		root:'root'
			    	}
			    }
		   })
	    });
	  
	  
	  var operateDate_start=Ext.create('Ext.form.field.Date',{
	  	fieldLabel: '领用时间',
	  	labelWidth:60,
	  	width:155,
	  	format:'Y-m-d',
        //name: 'date_start',
        value:  Ext.Date.add(new Date(), Ext.Date.DAY, -7)
	  });
	  var operateDate_end=Ext.create('Ext.form.field.Date',{
	  	fieldLabel: '到',
	  	format:'Y-m-d',
	  	labelWidth:15,
	  	labelWidth:15,
        //name: 'date_end',
        value: new Date()
	  });
	  
//	var types=Ext.create('Ext.form.RadioGroup',{
//        //xtype: 'radiogroup',
//        fieldLabel: '类型',
//        labelWidth:30,
//        width:150,
//        // Arrange radio buttons into two columns, distributed vertically
//        //columns: 2,
//        vertical: true,
//        items: [
//            { boxLabel: '领用', name: 'type', inputValue: 'out', checked: true },
//            { boxLabel: '返库', name: 'type', inputValue: 'in'}
//        ]
//    });
	 var type_combox=Ext.create('Ext.form.field.ComboBox',{
	        fieldLabel: '入库类型',
	        labelAlign:'right',
            labelWidth:60,
            width:150,
	        //xtype:'combobox',
	        //afterLabelTextTpl: Ext.required,
	        name: 'type',
		    displayField: 'name',
		    valueField: 'id',
		    //value:'bad',
	        //allowBlank: false,
	        store:Ext.create('Ext.data.ArrayStore', {
		    	fields: ['id', 'name'],
			    data:[['good','好件入库'],['bad','坏件入库']]
		   })
	    });
	 me.tbar={
		xtype: 'container',
		layout: 'anchor',
		defaults: {anchor: '0'},
		defaultType: 'toolbar',
		items: [{
			items: [operateDate_start,operateDate_end] // toolbar 2
		}, {
			items: [store_combox,workUnit_combox] // toolbar 1
		},{
			items: [type_combox,{
			text: '查询',
			iconCls:'form-search-button',
			handler: function(btn){
				//me.store.reload();
				me.store.loadPage(1);
			}
		  }]
		}]
	   };
	  me.store.load();
       
      me.callParent();
	}
});
