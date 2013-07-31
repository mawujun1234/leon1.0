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
    record:null,
	initComponent:function(){
		var me=this;
//		me.bbar=['上一步','->',{
//	        text: '下一步',
//	        handler: function(){
//	            var layout = me.getLayout();
//	            //根据不同的值类型，显示不同的panel，并且根据不同的值把值设置到record里面。
//	            parameterForm.updateRecord();
//	            var record=parameterForm.getRecord();
//	            if(record.get("valueEnum")=='STRING'){
//	            	layout.setActiveItem(1);
//	            	var form=layout.getActiveItem();
//	            	
//	            }
//	            
//	            //active = main.items.indexOf(layout.getActiveItem());
//	        }
//	    }];
		
	    var parameterForm=Ext.create('Leon.desktop.parameter.ParameterForm',{
	    	bbar:['->',{
		        text: '下一步',
		        handler: function(){
		            var layout = me.getLayout();
		            if(!parameterForm.isValid( )){
		            	return;
		            }
		            //根据不同的值类型，显示不同的panel，并且根据不同的值把值设置到record里面。
		            parameterForm.updateRecord();
		            //me.record=parameterForm.getRecord();
		            var valueEnem=me.record.get("valueEnum");
		            if(valueEnem=='STRING' || valueEnem=='NUMBER'|| valueEnem=='REGULAR'|| valueEnem=='BOOLEAN'|| valueEnem=='SQL'|| valueEnem=='JAVA'){
		            	layout.setActiveItem(1);
		            	var form=layout.getActiveItem();	
		            	form.loadRecord(me.record);
		            	在使用REGULAR，sql和java的时候，需要填入一个值，这个值是必填的。使用boolean的时候要填入Y或N
		            }
		        }
		    }]
	    });
	    var simpleForm=Ext.create('Leon.desktop.parameter.SimpleForm',{
	    	bbar:[{
	    		text:'上一步',
	    		handler:function(){
	    			var layout = me.getLayout();
		            var form=layout.getActiveItem();
		            var defaultValue=form.getForm( ).findField("defaultValue").getValue();
		            if(defaultValue){
		            	me.record.set('defaultValue',defaultValue);
		            }
	    			me.getLayout().setActiveItem(0);
	    		}
	    	},'->',{
		        text: '完成',
		        handler: function(){
		            var layout = me.getLayout();
		            var form=layout.getActiveItem();
		            var defaultValue=form.getForm( ).findField("defaultValue").getValue();
		            if(defaultValue){
		            	me.record.set('defaultValue',defaultValue);
		            }
		            if(me.createNew){
		            	me.record.phantom=true;
		            }
		            
		            //alert(me.record.phantom);
		            me.record.save({
						success: function(record, operation) {
							me.close();
						}
					});
		            
		        }
		    }]
	    });
	    if(!me.record){
	    	me.createNew=true;
	    	me.record=Ext.create('Leon.desktop.parameter.Parameter');
	    } else {
	    	me.createNew=false;
	    }
	   
	    //me.record.phantom=true;
	    parameterForm.loadRecord(me.record);

	    this.items=[parameterForm,simpleForm];
		this.callParent();
		
	},
	setRecord:function(record){
		delete me.record;
		me.record=record;
		me.items[0].loadRecord(me.record);
	}

});











