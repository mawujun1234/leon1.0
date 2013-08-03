Ext.define('Leon.desktop.parameter.ParameterWindow', {
    extend: 'Ext.window.Window',
    requires:[
    	'Leon.desktop.parameter.ParameterForm',
    	'Leon.desktop.parameter.SimpleForm',
    	'Leon.desktop.parameter.GridForm'
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
	
	width: 330,
    height: 280,
    layout: 'card',
    record:null,
	initComponent:function(){
		var me=this;
		
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
		            var subjects_t=parameterForm.getForm().findField("checkboxgroup_subjects").getValue( ).checkbox_subjects;
		            if(!(subjects_t instanceof Array)){
		            	subjects_t=[subjects_t];
		            }
		            me.record.set("subjects",Ext.encode(subjects_t));

		            var valueEnum=me.record.get("valueEnum");
		            if(valueEnum=='STRING' || valueEnum=='NUMBER' || valueEnum=='BOOLEAN'  || valueEnum=='DATE'|| valueEnum=='TIME' ){
		            	layout.setActiveItem(1);
		            	var form=layout.getActiveItem();	
		            	form.loadRecord(me.record);
		            	form.hideContent();
		            	
//		            } else if(valueEnum=='SQL'|| valueEnum=='JAVA'){
//		            	layout.setActiveItem(1);
//		            	var form=layout.getActiveItem();	
//		            	form.showContent();
//		            	form.loadRecord(me.record);
		            } else if(valueEnum=='ARRAY'){
		            	layout.setActiveItem(2);
		            	var form=layout.getActiveItem();
		            	if(!me.creaetNew && me.record.get("content")){
		            		form.getStore().loadData(Ext.decode(me.record.get("content")));
		            	}
		            	
		            } else {
		            	alert("请他情况还没有做，请稍后!");
		            }
		        }
		    }]
	    });
	    parameterForm.on("subjectsItemsReady",function(form,checkboxgroup_subjects){
	    	if(me.record.get('subjects')){
	    		form.getForm().setValues({"checkbox_subjects":Ext.decode(me.record.get('subjects'))});
	    	}
	    	
	    });
	    var simpleForm=Ext.create('Leon.desktop.parameter.SimpleForm',{
	    	bbar:[{
	    		text:'上一步',
	    		handler:function(){
	    			var layout = me.getLayout();
		            var form=layout.getActiveItem();
		            form.updateRecord();
	    			me.getLayout().setActiveItem(0);
	    		}
	    	},'->',{
		        text: '完成',
		        handler: function(){
		            var layout = me.getLayout();
		            var form=layout.getActiveItem();

		            form.updateRecord();
		            //me.getLayout().getLayoutItems( )[0].updateRecord();;

		            if(me.createNew){
		            	me.record.phantom=true;
		            }
		            if(me.record.get('valueEnum')=='BOOLEAN'){
		            	me.record.set("content",'[{key:"Y",name:"是"},{key:"N",name:"否"}]');
		            }

		            me.record.save({
						success: function(record, operation) {
							me.close();
							if(me.createNew){
								me.parameterGrid.getStore().reload();
							}
						}
					});
		            
		        }
		    }]
	    });
	    
	    var gridForm=Ext.create('Leon.desktop.parameter.GridForm',{
	    	bbar:[{
	    		text:'上一步',
	    		handler:function(){
	    			var layout = me.getLayout();
		            var form=layout.getActiveItem();
		            form.updateRecord();
	    			me.getLayout().setActiveItem(0);
	    		}
	    	},'->',{
		        text: '完成',
		        handler: function(){
		            var layout = me.getLayout();
		            var form=layout.getActiveItem();
		            var records=form.getStore().getRange();
		            var content="[";
		            for(var i=0;i<records.length;i++){
		            	if(!records[i].get("key") || !records[i].get("name")){
		            		Ext.Msg.alert("消息","第"+i+"行情输入数据!");
		            		return;
		            	}
		            	content=content+Ext.encode(records[i].getData());
		            	if(i!=records.length-1){
		            		content+=","
		            	}
		            }
		            content=content+"]";
		            if(content){
		            	me.record.set('content',content);
		            }
		            //form.updateRecord();
		            if(me.createNew){
		            	me.record.phantom=true;
		            }
		            
		            //alert(me.record.phantom);
		            me.record.save({
						success: function(record, operation) {
							me.close();
							if(me.createNew){
								me.parameterGrid.getStore().reload();
							}
						}
					});
		            
		        }
		    }]
	    });
	    
	    
	    if(!me.record){
	    	me.createNew=true;
	    	me.record=Ext.create('Leon.desktop.parameter.Parameter');
	    	parameterForm.loadRecord(me.record);
	    } else {
	    	me.createNew=false;
	    	parameterForm.loadRecord(me.record);
	    	//alert(Ext.decode(me.record.get('subjects')));
	    	
	    	
	    }
	   
	    //me.record.phantom=true;
	   

	    this.items=[parameterForm,simpleForm,gridForm];
		this.callParent();
		
	},
	setRecord:function(record){
		delete me.record;
		me.record=record;
		me.items[0].loadRecord(me.record);
	}

});











