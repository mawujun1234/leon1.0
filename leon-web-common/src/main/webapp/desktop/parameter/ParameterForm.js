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
            afterLabelTextTpl: required,
            name: 'id',
            allowBlank: false,
            tooltip: '输入唯一码'
        },{
            fieldLabel: '名称',
            afterLabelTextTpl: required,
            name: 'name',
            allowBlank: false,
            tooltip: '输入名称'
        }, {
            fieldLabel: '值类型',
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
            fieldLabel: '描述',
            name: 'desc'
        }],
	initComponent:function(){
		this.callParent();
	}
});