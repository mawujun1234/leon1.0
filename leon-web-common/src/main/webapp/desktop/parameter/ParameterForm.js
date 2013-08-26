var required = '<span style="color:red;font-weight:bold" data-qtip="Required">*</span>';
Ext.define('Leon.desktop.parameter.ParameterForm', {
	extend:'Ext.form.Panel',
	frame:true,
	
	defaults: { 
		labelAlign:'right'
	},
	defaultType: 'textfield',
        items: [{
            fieldLabel: 'id',
            afterLabelTextTpl: '<span style="color:red;font-weight:bold" data-qtip="不能使用数字">*</span>',
            name: 'id',
            allowBlank: false,
            vtype:'alpha',
            tooltip: '输入唯一码'
        },{
            fieldLabel: '名称',
            afterLabelTextTpl: required,
            name: 'name',
            allowBlank: false,
            tooltip: '输入名称'
        }, {
            fieldLabel: '数据源',
            name: 'valueEnum',
            afterLabelTextTpl: required,
            allowBlank: false,
            xtype: 'combobox',
            displayField:'name',
            valueField:'key',
            editable:false,
            store:Ext.create('Ext.data.Store',{
            	autoLoad:true,
            	fields:['key','name'],
            	proxy:{
            		type:'bajax',
            		url:'/parameter/queryParameterValueEnum'
            	}
            }),
            listeners:{
            	change:function(combo, newValue, oldValue){
            		if(!newValue){
            			return;
            		}
            		var showModel=combo.nextSibling();
            		showModel.clearValue( );
            		showModel.getStore().removeAll();
            		showModel.getStore().load({params:{valueEnum:newValue},callback:function(){
            			var record=combo.up('form').getRecord();
            			if(record.get("valueEnum")==newValue){
            				showModel.setValue(record.get("showModel"));
            			}
            		}});
            	}
            }
        },{
            fieldLabel: '展现方式',
            name: 'showModel',
            xtype: 'combobox',
            afterLabelTextTpl: required,
            allowBlank: false,
            displayField:'name',
            valueField:'key',
            editable:false,
            queryMode: 'local',
            store:Ext.create('Ext.data.Store',{
            	autoLoad:false,
            	fields:['key','name'],
            	proxy:{
            		type:'bajax',
            		url:'/parameter/queryShowModel'
            	}
            })
        },{
		    xtype      : 'checkboxgroup',
		    name:'checkboxgroup_subjects',
		    columns: 3,
		    //width:100*params.content.length,
		    afterLabelTextTpl: '<span class="icons_help" data-qtip="作用的目标">&nbsp;&nbsp;&nbsp;&nbsp;</span>',
		    fieldLabel :"目标",
		    items: []
		},{
            fieldLabel: '描述',
            name: 'desc'
        }],
	initComponent:function(){
		var me=this;
		me.addEvents("subjectsItemsReady");
		Ext.Ajax.request({
            	url:'/parametersubject/querySubjectType',
            	method:'POST',
            	//params:{subjectId:me.subjectId,subjectType:me.subjectType},
            	success:function(response){
            		var obj=Ext.decode(response.responseText);
            		var values=obj.root;
            		var items=[];
            		for(var i=0;i<values.length;i++){
						items.push( {
			                boxLabel  :values[i].name,
			                name      : 'checkbox_subjects',
			                checked:true,
			                inputValue:values[i].key
			            });
					}
					var checkboxgroup_subjects=me.getForm().findField("checkboxgroup_subjects");
            		checkboxgroup_subjects.add(items);
            		//form.getForm().findField("55").setValue()
            		me.fireEvent("subjectsItemsReady",me,checkboxgroup_subjects);
            	}
            });
		this.callParent();
	}
});