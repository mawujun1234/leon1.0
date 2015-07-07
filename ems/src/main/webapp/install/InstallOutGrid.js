Ext.define('Ems.install.InstallOutGrid',{
	extend:'Ext.grid.Panel',
	requires: [
	     'Ems.install.InstallOut'
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
		{dataIndex:'id',text:'编码'},
		{dataIndex:'status_name',text:'状态'},
		{dataIndex:'store_name',text:'仓库'},
		{dataIndex:'workUnit_name',text:'作业单位'},
		{dataIndex:'project_name',text:'项目'},
		{dataIndex:'operateDate',text:'操作时间',xtype: 'datecolumn',   format:'Y-m-d'},
		{dataIndex:'operater',text:'操作人'},
		{dataIndex:'memo',text:'备注'}
      ];
      
	  me.store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			model: 'Ems.install.InstallOut',
			autoLoad:false,
			proxy:{
				type:'ajax',
				actionMethods:{read:'POST'},
				url:Ext.ContextPath+'/installOut/queryMain.do',
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
					project_id:project_combox.getValue( ),
					//installOutType_id:installOutType_combox.getValue(),
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
	  var project_combox=Ext.create('Ems.baseinfo.ProjectCombo',{
		flex:1,
		allowBlank: false
	});
//	 var installOutType_combox=Ext.create('Ext.form.field.ComboBox',{
//	        fieldLabel: '领用类型',
//	        labelAlign:'right',
//            labelWidth:55,
//	        //xtype:'combobox',
//	        //afterLabelTextTpl: Ext.required,
//	        name: 'installOutType_id',
//		    displayField: 'name',
//		    valueField: 'id',
//	        allowBlank: false,
//	        store:Ext.create('Ext.data.Store', {
//		    	fields: ['id', 'name'],
//			    proxy:{
//			    	type:'ajax',
//			    	//extraParams:{type:[1,3],edit:true},
//			    	url:Ext.ContextPath+"/installOutType/query.do",
//			    	reader:{
//			    		type:'json',
//			    		root:'root'
//			    	}
//			    }
//		   })
//	});
	 me.tbar={
		xtype: 'container',
		layout: 'anchor',
		defaults: {anchor: '0'},
		defaultType: 'toolbar',
		items: [{
			items: [operateDate_start,operateDate_end] // toolbar 2
		}, {
			items: [store_combox,workUnit_combox] // toolbar 1
		}, {
			items: [project_combox] // toolbar 1
		},{
			items: [{
			text: '查询',
			iconCls:'form-search-button',
			handler: function(btn){
				//me.store.reload();
				me.store.loadPage(1);
			}
		  },{
			text:'打印',
			icon:'../icons/printer.png',
			handler:function(){
				var record=me.getSelectionModel().getLastSelected();
				if(!record){
					alert("请先选择一个领用单!");
					return;
				}
				window.open("/installOut/equipmentOutStorePrint.do?installOut_id="+record.get("id"),"_blank");
			}
		}]
		}]
	   };
	  me.store.load();
       
      me.callParent();
	}
});
