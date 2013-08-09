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
                        })
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
                    		//alert(combobox.getValue());
                    		if('DDD'==combobox.getValue()){
                    			panel=Ext.create('Leon.desktop.generator.DDDSelectGrid',{
                    				
                    			});
                    		}
                    		panel.on('itemdblclick',function(grid, record, item, index){
                    			btn.previousSibling().setValue(record.get("className"));
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
        
        var tabpanel={
            xtype:'tabpanel',
            plain:true,
            activeTab: 0,
            defaults:{
                bodyPadding: 10
            },
            items:[{
                title:'Controller配置',
                defaults: {
                    width: 230
                },
                defaultType: 'textfield',

                items: [{
                    fieldLabel: '模板地址',
                    name: 'controllerLocal',
                    value: ''
                }]
            },{
                title:'Service配置',
                defaults: {
                    width: 230
                },
                defaultType: 'textfield',

                items: [{
                    fieldLabel: '模板地址',
                    name: 'serviceLocal',
                    value: ''
                }]
            },{
                title:'DDD配置',
                defaults: {
                    width: 230
                },
                defaultType: 'textfield',

                items: [{
                    fieldLabel: '模板地址',
                    name: 'dddLocal',
                    value: ''
                }]
            },{
                title:'Form配置',
                defaults: {
                    width: 230
                },
                defaultType: 'textfield',

                items: [{
                    fieldLabel: '模板地址',
                    name: 'formLocal',
                    value: ''
                }]
            },{
                title:'grid配置',
                defaults: {
                    width: 230
                },
                defaultType: 'textfield',

                items: [{
                    fieldLabel: '模板地址',
                    name: 'gridLocal',
                    value: ''
                }]
            },{
                title:'combobox配置',
                defaults: {
                    width: 230
                },
                defaultType: 'textfield',

                items: [{
                    fieldLabel: '模板地址',
                    name: 'comboboxLocal',
                    value: ''
                }]
            }]
        }
        me.items.push(tabpanel);
        
        me.callParent();
        
	}
});