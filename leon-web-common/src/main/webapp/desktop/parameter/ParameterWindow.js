Ext.define('Leon.desktop.parameter.ParameterWindow', {
    extend: 'Ext.window.Window',
    requires:[
    	'Leon.desktop.parameter.ParameterForm',
    	'Leon.desktop.parameter.SimpleForm'
    ],
	//type : 'IframeWindow',
	url : '',//iframe链接的地址
	
	shrinkWrap :true,
	resizable:true,
	isWindow: true,
	plain : true,
	loadMask:true,
	modal:true,
	constrainHeader:true,
	//constrain:true,
	maximizable :false,
	minimizable:false,
	//maximized:false,
	closeAction:'close',
	//title:'测试1',
	
	width: 300,
    height: 200,
    layout: 'card',
	initComponent:function(){
		var me=this;
		me.bbar=['上一步','->',{
	        text: '下一步',
	        handler: function(){
	            var layout = me.getLayout();
	            根据不同的值类型，显示不同的panel，并且根据不同的值把值设置到record里面。
	            parameterForm.updateRecord();
	            var record=parameterForm.getRecord();
	            if(record.get("valueEnum")=='STRING'){
	            	layout.setActiveItem(1);
	            }
	            
	            //active = main.items.indexOf(layout.getActiveItem());
	        }
	    }];
		
	    var parameterForm=Ext.create('Leon.desktop.parameter.ParameterForm');
	    parameterForm.loadRecord(Ext.create('Leon.desktop.parameter.Parameter'));
	    this.items=[parameterForm,Ext.create('Leon.desktop.parameter.SimpleForm')];
		this.callParent();
		
	}

});











