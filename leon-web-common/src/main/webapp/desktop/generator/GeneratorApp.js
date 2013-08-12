Ext.require('Leon.desktop.generator.GeneratorForm');
Ext.onReady(function(){
	var form=Ext.create('Leon.desktop.generator.GeneratorForm',{
		region:'north',
		height:70
	});
	
	var controllerPanel=Ext.create('Ext.panel.Panel',{
        	title:"Controller生成",
        	layout:'fit',
        	tbar:[{
	                    boxLabel  : '普通',
	                    xtype:'radio',
	                    name      : 'showModel',
	                    inputValue: 'normal',
	                    checked   : true
	                },{
	                    boxLabel  : '分页',
	                    name      : 'showModel',
	                    margin:'0 0 0 10',
	                    xtype:'radio'
	                   ,inputValue: 'page'
	                }, {
	                    boxLabel  : '树形',
	                    xtype:'radio',
	                    name      : 'showModel',
	                    inputValue: 'tree'
	                },
	        {
	        	text:'生成',
	        	iconCls:'icons_search_button_green',
	        	margin:'0 0 0 20',
	        	handler:function(btn){
	        		var showModel=btn.previousSibling("[name=showModel],[checked=true]"); 
	        		Ext.Ajax.request({
	        			url:'/generator/generatorStr',
	        			params:{
	        				className:form.getSubjectName(),
	        				type:'Controller',
	        				showModel:showModel.getGroupValue( )
	        			},
	        			success:function(response){
	        				controllerPanel.items.getAt(0).setValue(response.responseText);
	        			}
	        		
	        		});
	        	
	        	}
	        },{
	        	text:'导出',
	        	iconCls:'icons_table_export',
	        	margin:'0 0 0 20',
	        	handler:function(btn){
	        		var showModel=btn.previousSibling("[name=showModel],[checked=true]"); 
	        		var params={
	        				className:form.getSubjectName(),
	        				type:'Controller',
	        				showModel:showModel.getGroupValue( )
	        		}
	        		window.location.href=Ext.ContextPath+"/generator/saveFile?"+Ext.urlEncode(params);
	        	}
	        }],
        	items:[{
        		xtype:'textarea'
        	}]
        });
        
        var servicePanel=Ext.create('Ext.panel.Panel',{
        	title:"Service生成",
        	layout:'fit',
        	tbar:['重载方法:',
        		{
	                    boxLabel  : '增',
	                    xtype:'checkbox',
	                    name      : 'create',
	                    inputValue: '1'
	                }, {
	                    boxLabel  : '删',
	                    xtype:'checkbox',
	                    name      : 'destroy',
	                    inputValue: '2'
	                }, {
	                    boxLabel  : '改',
	                    xtype:'checkbox',
	                    name      : 'update',
	                    inputValue: '2'
	                }//, {
	                 //   boxLabel  : '查',
	                 //   xtype:'checkbox',
	               //     name      : 'query',
	                //    inputValue: '2'
	               // }
	        ,{
	        	text:'生成',
	        	iconCls:'icons_search_button_green',
	        	margin:'0 0 0 20',
	        	handler:function(btn){
	        		var create=btn.previousSibling("[name=create]"); 
	        		var destroy=btn.previousSibling("[name=destroy]"); 
	        		var update=btn.previousSibling("[name=update]"); 
	        		//var query=btn.previousSibling("[name=query]"); 
	        		Ext.Ajax.request({
	        			url:'/generator/generatorStr',
	        			params:{
	        				className:form.getSubjectName(),
	        				type:'Service',
	        				create:create.getValue(),
	        				destroy:destroy.getValue(),
	        				update:update.getValue()
	        				//query:query.getValue()
	        			},
	        			success:function(response){
	        				//var obj=Ext.decode(response.responseText);
	        				servicePanel.items.getAt(0).setValue(response.responseText);
	        			}
	        		
	        		});
	        	
	        	}
	        },{
	        	text:'导出',
	        	iconCls:'icons_table_export',
	        	margin:'0 0 0 20',
	        	handler:function(btn){
	        		var create=btn.previousSibling("[name=create]"); 
	        		var destroy=btn.previousSibling("[name=destroy]"); 
	        		var update=btn.previousSibling("[name=update]"); 
	        		var params={
	        				className:form.getSubjectName(),
	        				type:'Service',
	        				create:create.getValue(),
	        				destroy:destroy.getValue(),
	        				update:update.getValue()
	        				//query:query.getValue()
	        			}
	        		window.location.href=Ext.ContextPath+"/generator/saveFile?"+Ext.urlEncode(params);
	        	}
	        }],
        	items:[{
        		xtype:'textarea'
        	}]
        });
        //xml文件模板
         var mapperXMLPanel=Ext.create('Ext.panel.Panel',{
        	title:"xml文件模板生成",
        	layout:'fit',
        	tbar:['重载方法:',
        		{
	                boxLabel  : '增',
	                xtype:'checkbox',
	                name      : 'create',
	                inputValue: '1'
	            }, {
	                boxLabel  : '删',
	                xtype:'checkbox',
	                name      : 'destroy',
	                inputValue: '2'
	            }, {
	                boxLabel  : '改',
	                xtype:'checkbox',
	                name      : 'update',
	                inputValue: '2'
	            }, {
	                boxLabel  : '查',
	                xtype:'checkbox',
	                name      : 'query',
	                inputValue: '2'
	            }
	        ,{
	        	text:'生成',
	        	iconCls:'icons_search_button_green',
	        	margin:'0 0 0 20',
	        	handler:function(btn){
	        		var create=btn.previousSibling("[name=create]"); 
	        		var destroy=btn.previousSibling("[name=destroy]"); 
	        		var update=btn.previousSibling("[name=update]"); 
	        		var query=btn.previousSibling("[name=query]"); 
	        		Ext.Ajax.request({
	        			url:'/generator/generatorStr',
	        			params:{
	        				className:form.getSubjectName(),
	        				type:'MapperXML',
	        				create:create.getValue(),
	        				destroy:destroy.getValue(),
	        				update:update.getValue(),
	        				query:query.getValue()
	        			},
	        			success:function(response){
	        				//var obj=Ext.decode(response.responseText);
	        				mapperXMLPanel.items.getAt(0).setValue(response.responseText);
	        			}
	        		
	        		});
	        	
	        	}
	        },{
	        	text:'导出',
	        	iconCls:'icons_table_export',
	        	margin:'0 0 0 20',
	        	handler:function(btn){
	        		var create=btn.previousSibling("[name=create]"); 
	        		var destroy=btn.previousSibling("[name=destroy]"); 
	        		var update=btn.previousSibling("[name=update]"); 
	        		var query=btn.previousSibling("[name=query]"); 
	        		var params={
	        				className:form.getSubjectName(),
	        				type:'MapperXML',
	        				create:create.getValue(),
	        				destroy:destroy.getValue(),
	        				update:update.getValue(),
	        				query:query.getValue()
	        			}
	        		window.location.href=Ext.ContextPath+"/generator/saveFile?"+Ext.urlEncode(params);
	        	}
	        }],
        	items:[{
        		xtype:'textarea'
        	}]
        });
        
        var Extjs_ModelPanel=Ext.create('Ext.panel.Panel',{
        	title:"Extjs的model生成",
        	layout:'fit',
        	tbar:[
	        {
	        	text:'生成',
	        	iconCls:'icons_search_button_green',
	        	margin:'0 0 0 20',
	        	handler:function(btn){
	        		Ext.Ajax.request({
	        			url:'/generator/generatorStr',
	        			params:{
	        				className:form.getSubjectName(),
	        				type:'Extjs_Model'
	        			},
	        			success:function(response){
	        				//var obj=Ext.decode(response.responseText);
	        				Extjs_ModelPanel.items.getAt(0).setValue(response.responseText);
	        			}
	        		
	        		});
	        	
	        	}
	        },{
	        	text:'导出',
	        	iconCls:'icons_table_export',
	        	margin:'0 0 0 20',
	        	handler:function(btn){
	        		
	        		var params={
	        				className:form.getSubjectName(),
	        				type:'Extjs_Model'
	        			}
	        		window.location.href=Ext.ContextPath+"/generator/saveFile?"+Ext.urlEncode(params);
	        	}
	        }],
        	items:[{
        		xtype:'textarea'
        	}]
        });
        
        var Extjs_FormPanel=Ext.create('Ext.panel.Panel',{
        	title:'Form生成',
        	layout:'fit',
        	tbar:['生成form方式:',{
	                    boxLabel  : '创建',
	                    xtype:'radio',
	                    name      : 'createFormModel',//是使用Ext.create创建一个Form，还是使用Ext.define定义一个model
	                    inputValue: 'create'
	                },{
	                    boxLabel  : '定义',
	                    name      : 'createFormModel',
	                    margin:'0 0 0 10',
	                    xtype:'radio'
	                   ,inputValue: 'define'
	                   ,checked   : true
	                },'-',
	            {
	            
	            },
	        {
	        	text:'生成',
	        	iconCls:'icons_search_button_green',
	        	margin:'0 0 0 20',
	        	handler:function(btn){
	        		var createFormModel=btn.previousSibling("[name=createFormModel]"); 
	        		Ext.Ajax.request({
	        			url:'/generator/generatorStr',
	        			params:{
	        				className:form.getSubjectName(),
	        				type:'Extjs_Form',
	        				createFormModel:createFormModel.getGroupValue()
	        			},
	        			success:function(response){
	        				//var obj=Ext.decode(response.responseText);
	        				Extjs_FormPanel.items.getAt(0).setValue(response.responseText);
	        			}
	        		
	        		});
	        	
	        	}
	        },{
	        	text:'导出',
	        	iconCls:'icons_table_export',
	        	margin:'0 0 0 20',
	        	handler:function(btn){
	        		var createFormModel=btn.previousSibling("[name=createFormModel]"); 
	        		var params={
	        				className:form.getSubjectName(),
	        				type:'Extjs_Form',
	        				createFormModel:createFormModel.getGroupValue()
	        			}
	        		window.location.href=Ext.ContextPath+"/generator/saveFile?"+Ext.urlEncode(params);
	        	}
	        }],
        	items:[{
        		xtype:'textarea'
        	}]
        });
        
        var tabpanel={
            xtype:'tabpanel',
            region:'center',
            plain:true,
            activeTab: 0,
            defaults:{
                bodyPadding: 10
            },
            
            items:[controllerPanel,servicePanel,mapperXMLPanel,Extjs_ModelPanel,Extjs_FormPanel,{
                title:'Tree生成',//生成普通树，还是继承基础树
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
                title:'grid生成',
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
                title:'combobox生成',
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
        
	
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'border',
		items:[form,tabpanel]
	});
	

});