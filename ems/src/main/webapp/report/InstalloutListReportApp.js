
Ext.onReady(function(){

	
	var date_start=Ext.create('Ext.form.field.Date',{
	  	fieldLabel: '开始时间',
	  	labelWidth:50,
	  	width:170,
	  	format:'Y-m-d',
	  	minValue:Ext.Date.parse('2015-07-10','Y-m-d')//从7.10号开始有数据
        //value:  Ext.Date.add(new Date(), Ext.Date.DAY, -7)
	  });
	var date_end=Ext.create('Ext.form.field.Date',{
	  	fieldLabel: '到',
	  	format:'Y-m-d',
	  	labelWidth:15,
	  	labelWidth:15,
	  	maxValue:new Date(),
        value: new Date()
	  });
	
	var store_combox=Ext.create('Ext.form.field.ComboBox',{
	        fieldLabel: '备品备件仓库',
	        labelAlign:'right',
            labelWidth:80,
	        //xtype:'combobox',
	        //afterLabelTextTpl: Ext.required,
	        name: 'store_id',
		    displayField: 'name',
		    valueField: 'id',
	        allowBlank: false,
	        store:Ext.create('Ext.data.Store', {
		    	fields: ['id', 'name'],
			    proxy:{
			    	type:'ajax',
			    	extraParams:{type:[1,3],edit:true},
			    	url:Ext.ContextPath+"/store/queryCombo.do",
			    	reader:{
			    		type:'json',
			    		root:'root'
			    	}
			    }
		   })
	});	
	
	var tbar1=Ext.create('Ext.toolbar.Toolbar',{
		items:[date_start,date_end,store_combox
		]
	})
	var project_combox=Ext.create('Ems.baseinfo.ProjectCombo',{
		width:500,
		allowBlank: true
	});
	var tbar2=Ext.create('Ext.toolbar.Toolbar',{
		items:[project_combox,'-','-'
		,{
			text:'导出',
			icon:'../icons/page_excel.png',
			handler:function(){
				var params=getParams();
				if(!params){
					return false;
				}
				var pp=Ext.Object.toQueryString(params);
				window.open(Ext.ContextPath+"/report/storereport/queryInstalloutListReport.do?"+pp, "_blank");
			}
		}
		]
	})
	
	function getParams(){
		var params={
			date_start:date_start.getRawValue(),
			date_end:date_end.getRawValue(),
			store_id:store_combox.getValue(),
			project_id:project_combox.getValue()
		}
		if(!params.date_start){
			Ext.Msg.alert("提醒","请先选择开始日期!");
			return false;
		}
		if(!params.date_end){
			Ext.Msg.alert("提醒","请先选择结束日期!");
			return false;
		}
//		if(!params.store_id){
//			Ext.Msg.alert("提醒","请先选择仓库!");
//			return false;
//		}
		return params;
	}
	
	var panel=Ext.create('Ext.panel.Panel',{
		tbar:{
		  xtype: 'container',
		  layout: 'anchor',
		  defaults: {anchor: '0'},
		  defaultType: 'toolbar',
		  items: [tbar1,tbar2]
		}
	});
	

	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'fit',
		items:[panel]
	});

});