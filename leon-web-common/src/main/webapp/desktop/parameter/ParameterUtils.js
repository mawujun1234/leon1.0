Ext.define('Leon.desktop.parameter.ParameterUtils', {
	//statics: {  
		 subjectType:null,
		 subjectId:null,
		 form:null,
         getForm: function(subjectType,fun) { 
         	if(!subjectType){
         		Ext.Msg.alert("消息","subjectType参数不能为空");
         		return;
         	}
         	var me=this;
         	me.subjectType=subjectType;
            Ext.Ajax.request({
            	url:Ext.ContextPath+'/parameter/queryBysubjectType',
            	method:'POST',
            	params:{subjectType:me.subjectType},
            	success:function(response){
            		var obj=Ext.decode(response.responseText);
            		var form=me.createForm(obj.root);
            		//me.setFormValues();
            		
            		fun(form);
            		
            	}
            });
         } , 
         /**
          * 必须嗲用，设置主体的id
          * @param {} subjectId
          */
         setSubjectId:function(subjectId){
	         	//this.form.findField('subjectId').setValue(subjectId);
         	this.subjectId=subjectId;
         	this.setFormValues();
	     },
         setFormValues:function(){
         	var me=this;
         	if(!me.subjectId){
         		return;
         	}
         	
         	var form=me.form;

         	Ext.Ajax.request({
            	url:Ext.ContextPath+'/parametersubject/query',
            	method:'POST',
            	params:{subjectId:me.subjectId,subjectType:me.subjectType},
            	success:function(response){
            		var obj=Ext.decode(response.responseText);
            		var values=obj.root;
            		//values[55]=['33'];
            		form.getForm().reset();
            		form.getForm().setValues(values);
            		//form.getForm().findField("55").setValue()
            	}
            });
         },
         
         createForm:function(parames){
         	var me=this;
         	var items=[];
         	for(var i=0;i<parames.length;i++){
         		items.push(me.getField(parames[i]))
         	}
         	var form=Ext.create('Ext.form.Panel',{items:items,
         		frame:true,
         		defaults:{
         			labelAlign:'right'
         		},
         		buttons:[{
         			text:'保存',
         			handler:function(){
         				//getValues( [asString], [dirtyOnly], [includeEmptyText], [useDataValues] ) 
         				var values=form.getValues( false, true,false,false,true);
         				if(!me.subjectId){
         					Ext.Msg.alert("消息","请先调用setSubjectId方法设置subjectId的值。");
         					return;
         				}
         				var arry=[];
         				for(var key in values){
         					var obj={
         						id:{
         							subjectType:me.subjectType,
         							subjectId:me.subjectId,
         							parameterId:key
         						},
         						parameterValue:values[key]
         					}
         					arry.push(obj);
         				}
         				//alert(Ext.encode(values));
         				Ext.Ajax.request({
         					url:Ext.ContextPath+'/parametersubject/create',
         					method:'POST',
         					jsonData:arry,
         					success:function(response){
         						
         					}
         				});
         			}
         		}]
         	
         	});
         	
         	me.form=form;
         	return form;
         },
         getQuickTip:function(text){
         	if(text){
         		return '<span class="icons_help" data-qtip="'+text+'">&nbsp;&nbsp;&nbsp;&nbsp;</span>';
         	} else {
         		return "";
         	}
         	
         },
         getField:function(params){
         	var me=this;
         	var showModel=params.showModel.toLowerCase();
         	if(showModel=='textfield'){
         		//alert(params.validations);
         		var field={
         			fieldLabel:params.name,
         			name:params.id,
         			afterLabelTextTpl: me.getQuickTip(params.desc),
         			xtype:'textfield'
         		};
         		if(params.validations){
         			field=Ext.apply(field,Ext.decode(params.validations));
         		}
         		return field;
         	} else if(showModel=='combobox'){
         		//alert(typeof params.content);
         		params.content=params.content?Ext.decode(params.content):"";
         		var field={
         			fieldLabel:params.name,
         			name:params.id,
         			editable:false,
         			xtype:'combobox',
         			afterLabelTextTpl: me.getQuickTip(params.desc),
         			displayField: 'name',
    				valueField: 'key',
         			store: Ext.create('Ext.data.Store', {
					    fields: ['key', 'name'],
					    data :params.content
					})
         		};
         		return field;
         	}else if(showModel=='checkboxgroup'){
         		var items=[];
				params.content=Ext.decode(params.content);
				for(var i=0;i<params.content.length;i++){
					items.push( {
		                    boxLabel  : params.content[i].name,
		                    name      : params.id,
		                    inputValue: params.content[i].key
		            });
				}
         		var field= {
		            xtype      : 'checkboxgroup',
		            width:100*params.content.length,
		            afterLabelTextTpl: me.getQuickTip(params.desc),
		            fieldLabel : params.name,
		            items: items
		        }
         		return field;
         	}else if(showModel=='radiogroup'){
				var items=[];
				params.content=Ext.decode(params.content);
				for(var i=0;i<params.content.length;i++){
					items.push( {
		                    boxLabel  : params.content[i].name,
		                    name      : params.id,
		                    inputValue: params.content[i].key
		                    
		                    //id        : 'radio1'
		            });
				}
         		var field= {
		            xtype      : 'radiogroup',
		            width:100*params.content.length,
		            fieldLabel : params.name,
		            afterLabelTextTpl: me.getQuickTip(params.desc),
		            items: items
		        }
         		return field;
         	} else if(showModel=='numberfield'){
         		var field={
         			fieldLabel:params.name,
         			name:params.id,
         			afterLabelTextTpl: me.getQuickTip(params.desc),
         			xtype:'numberfield'
         		};
         		if(params.validations){
         			field=Ext.apply(field,Ext.decode(params.validations));
         		}
         		return field;
         	}else if(showModel=='datefield'){
         		var field={
         			fieldLabel:params.name,
         			name:params.id,
         			format:'Y-m-d',
         			afterLabelTextTpl: me.getQuickTip(params.desc),
         			xtype:'datefield'
         		};
         		return field;
         	}else if(showModel=='timefield'){
         		var field={
         			fieldLabel:params.name,
         			name:params.id,
         			format:'H:i',
         			afterLabelTextTpl: me.getQuickTip(params.desc),
         			xtype:'timefield'
         		};
         		return field;
         	}
         }
     //}
});