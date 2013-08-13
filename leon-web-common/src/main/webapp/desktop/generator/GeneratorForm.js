Ext.define('Leon.desktop.generator.GeneratorForm',{
	extend:'Ext.form.Panel',
	requires:['Leon.desktop.generator.DirSelectPanel'],
	required:'<span style="color:red;font-weight:bold" data-qtip="Required">*</span>',
    frame: true,
    defaults: {
        anchor: '100%',
        labelAlign: 'right'
    },
	initComponent: function () {
		var me=this;
		
		me.items=[{
                    xtype: 'container',
                    layout: 'hbox',
                    defaultType: 'textfield',
                    defaults: {
				        labelAlign: 'right'
				    },
                    margin: '0 0 5 0',
                    items: [{
                        fieldLabel: '主体类型',
                        name: 'subjectType',
                        allowBlank: false,
                        xtype:"combobox",
                        displayField:'name',
                        valueField:'key',
                        value:'DDD',
                        store:Ext.create('Ext.data.Store',{
                        	fields:["key","name"],
                        	data:[{key:'DDD',name:'领域类'},{key:'SQL',name:'sql语句'},{key:'TABLE',name:'数据库表'}]
                        }),
                        listeners:{
                        	change:function(combobox,newValue,oldValue){
                        		var comp=me.getComponent("javaAndjsDir");
                        		if(newValue=='DDD'){
                        			comp.disable();
                        		} else{
                        			comp.enable();
                        		}
                        	}
                        }
                    }, {
                        fieldLabel: '主体名称',
                        labelWidth: 100,
                        width:400,
                        name: 'subjectName',
                        allowBlank: false
                    },{
                    	xtype:'button',
                    	text:'选择主体',
                    	handler:function(btn){
                    		var combobox=btn.previousSibling("[xtype=combobox][name=subjectType]")
                    		var panel=null;

                    		if('DDD'==combobox.getValue()){
                    			panel=Ext.create('Leon.desktop.generator.DDDSelectGrid',{
                    				
                    			});
                    		}
                    		panel.on('itemdblclick',function(grid, record, item, index){
                    			btn.previousSibling().setValue(record.get("className"));
                    			me.fireEvent("selected",record);
                    		});
                    		var win=Ext.create('Ext.Window',{
                    			layout:'fit',
                    			title:'选择主体',
                    			modal:true,
                    			width:370,
                    			height:400,
                    			items:[panel]
                    		});
                    		win.show();
                    	}
                    }]
                },{
                    xtype: 'container',
                    layout: 'hbox',
                    itemId:'javaAndjsDir',
                    disabled:true,
                    defaultType: 'textfield',
                    defaults: {
				        labelAlign: 'right'
				    },
                    margin: '0 0 5 0',
                    items: [{
                        fieldLabel: 'js保存目录',
                        name: 'jsDir',
                        width:300,
                        allowBlank: false
                    },{
                    	xtype:'button',
                    	text:'选择目录',
                    	handler:function(btn){
                    		var dirSelect=Ext.create('Leon.desktop.generator.DirSelectPanel',{
                    			url:'/generator/listJsDir'
                    		});
                    		var win=Ext.create('Ext.Window',{
                    			layout:'fit',
                    			title:'选择js目录',
                    			modal:true,
                    			width:250,
                    			height:400,
                    			items:[dirSelect]
                    		});
                    		dirSelect.on("itemdblclick",function(tree, record, item, index){
                    			if(record.get("isFile")){
                    				Ext.Msg.alert("消息","请选择文件夹");
                    				return;
                    			}
                    			btn.previousSibling().setValue(record.getId());
                    		});
                    		win.show();
                    	}
                    }, {
                        fieldLabel: 'java包名',
                        labelWidth: 100,
                        width:300,
                        name: 'javaDir'
                    },{
                    	xtype:'button',
                    	text:'选择目录',
                    	handler:function(){
                    		var dirSelect=Ext.create('Leon.desktop.generator.DirSelectPanel',{
                    			url:'/generator/listJavaDir'
                    		});
                    		var win=Ext.create('Ext.Window',{
                    			layout:'fit',
                    			title:'选择java目录',
                    			modal:true,
                    			width:250,
                    			height:400,
                    			items:[dirSelect]
                    		});
                    		dirSelect.on("itemdblclick",function(tree, record, item, index){
                    			if(record.get("isFile")){
                    				Ext.Msg.alert("消息","请选择包名");
                    				return;
                    			}
                    			btn.previousSibling().setValue(record.getId());
                    		});
                    		win.show();
                    	}
                    }]
                }];
        
        
        me.addEvents("selected");
        me.callParent();
        
	},
	getSubjectName:function(){
		var field=this.getForm().findField("subjectName");
		return field.getValue();
	}
});