Ext.require('Leon.desktop.generator.GeneratorForm');
Ext.onReady(function(){
	var form=Ext.create('Leon.desktop.generator.GeneratorForm',{
		region:'north',
		height:70
	});
	
	var controllerPanel=Ext.create('Ext.panel.Panel',{
        	title:"Controller配置",

        	layout:'fit',
        	tbar:[{
	            xtype: 'checkboxgroup',
	            labelWidth:50,
	            fieldLabel: '生成选项',
	            //defaultType: 'checkboxfield',
	            items: [
	                {
	                    boxLabel  : '分页',
	                    name      : 'showPager',
	                    inputValue: '1'
	                }, {
	                    boxLabel  : '树形',
	                    name      : 'showTree',
	                    inputValue: '2',
	                    checked   : true
	                }
	            ]
	        },{
	        	text:'预览',
	        	margin:'0 0 0 120',
	        	handler:function(btn){
	        		Ext.Ajax.request({
	        			url:'/generator/generatorStr',
	        			params:{
	        				className:"com.mawujun.fun.Fun",
	        				type:'Controller'
	        			},
	        			success:function(response){
	        				//var obj=Ext.decode(response.responseText);
	        				controllerPanel.items.getAt(0).setValue(response.responseText);
	        			}
	        		
	        		});
	        	
	        	}
	        }],
        	items:[{
        		xtype:'textarea'
        	}]
        });
        
        var servicePanel=Ext.create('Ext.panel.Panel',{
        	title:"Service配置",

        	layout:'fit',
        	tbar:[{
	            xtype: 'checkboxgroup',
	            labelWidth:50,
	            fieldLabel: '重载方法',
	            //defaultType: 'checkboxfield',
	            items: [
	                {
	                    boxLabel  : '增',
	                    name      : 'topping',
	                    inputValue: '1'
	                }, {
	                    boxLabel  : '删',
	                    name      : 'topping',
	                    inputValue: '2'
	                }, {
	                    boxLabel  : '改',
	                    name      : 'topping',
	                    inputValue: '2'
	                }, {
	                    boxLabel  : '查',
	                    name      : 'topping',
	                    inputValue: '2'
	                }
	            ]
	        },{
	        	text:'预览',
	        	margin:'0 0 0 120',
	        	handler:function(btn){
	        		Ext.Ajax.request({
	        			url:'/generator/generatorStr',
	        			params:{
	        				className:"com.mawujun.fun.Fun",
	        				type:'Service'
	        			},
	        			success:function(response){
	        				//var obj=Ext.decode(response.responseText);
	        				servicePanel.items.getAt(0).setValue(response.responseText);
	        			}
	        		
	        		});
	        	
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
            
            items:[controllerPanel,servicePanel,{
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
        
	
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'border',
		items:[form,tabpanel]
	});
	

});