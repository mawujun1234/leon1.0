Ext.define('Ext.ms.navi.Panel',{
	extend: 'Ext.panel.Panel',
	alias : "widget.nvaipanel",
    initComponent: function(){
    	var config=[];
    	var me=this;
    	if(this.url){
//	    	Ext.define('service', {
//	    	    extend: 'Ext.data.Model',
//	    	    fields: [
//	    	        {name: 'id', type: 'string'},
//	    	        {name: 'text',  type: 'string'},
//	    	        {name: 'link',   type: 'string'},
//	    	        {name: 'loadType', type: 'string'},
//	    	        {name: 'flex', type: 'int'}
//	    	    ]
//	    	});
//	    	Ext.define('serviceGroup', {
//	    	    extend: 'Ext.data.Model',
//	    	    fields: [
//	    	        {name: 'id', type: 'string'},
//	    	        {name: 'text',  type: 'string'},
//	    	        {name: 'link',   type: 'string'},
//	    	        {name: 'loadType', type: 'string'},
//	    	        {name:'items', type:'service'}
//	    	    ]
//	    	});
//	    	
//	    	this.store = Ext.create('Ext.data.Store', {
//	    	    model: 'serviceGroup',
//	    	    proxy: {
//	    	        type: 'ajax',
//	    	        url : this.url
//	    	    },
//	    	    autoLoad: true
//	    	});
//	    	var config=[];
//	    	this.store.addListener('load',function(s,r,success,o,e){
//				this.remove(0);
//				if(r.length>0){
//					config=r[0].get('items');
//					this.add(
//				        Ext.apply({
//			                layout: {
//			                    type:'vbox',
//			                    padding:'5',
//			                    align:'stretch'
//			                },
//			                id:'button-box',
//			                defaults:{margins:'0 0 5 0', xtype:'naviButton',handler:this.clickAction},
//			                items:config
//				        })
//				    );
//				}
//	    	},this);
    		
    		Ext.Ajax.request({
				url:this.url,
				success:function(response){
					config=Ext.decode(response.responseText).root;
					me.add(
				        Ext.apply({
			                layout: {
			                    type:'vbox',
			                    padding:'5',
			                    align:'stretch'
			                },
			                id:'button-box',
			                defaults:{margins:'0 0 5 0', xtype:'naviButton',flex:1,maxHeight:80,handler:me.clickAction},
			                items:config
				        })
				    );
				}
							
			});
    	}
    	
    	this.getConfig=function(){
    		return config;
    	};
    	
    	this.selectClass=function(cls){
            if(!!cls){
            	var index=cls.indexOf('.');
            	if(index!=-1){
            		var id = cls.substring(0,index);
            		var obj=this.down("#"+id);
            		if(!!obj&&obj.getXType()=='naviButton'){
            			if(!obj.pressed){
            				obj.toggle();
            			}
            		}else{
            			var its =this.down('#button-box').items;
            			var index=its.length;
            			for(var i=0;i<index;i++){
            				var btn=its.get(i)
            				if(!!btn&&btn.pressed){
            					btn.toggle();
            				}
            			}
            		}
            	}
            }
        }
    	
        Ext.apply(this,{
            minWidth: 150,
            maxWidth: 300,
            collapsible: true,
            autoScroll : true,
            split:true,
            layout:'fit',
            tbar:['->',{// 展开搜索菜单
				text : "居中",
                iconCls: 'icon-center-align',
				tooltip: 'Align Center',
				handler : function() {
					this.remove(0);
					this.add(
				        Ext.apply({
			                layout: {
			                    type:'vbox',
			                    padding:'5',
			                    align:'center'
			                },
			                defaults:{margins:'0 0 5 0'},
			                items:this.getConfig()
				        })
				    );
				},
            	scope: this
			},								{
				text : "平铺",
                iconCls: 'icon-stretch-align',
                tooltip: 'Align Stretch',
				handler : function() {
					this.remove(0);
					this.add(
				        Ext.apply({
			                layout: {
			                    type:'vbox',
			                    padding:'5',
			                    align:'stretch'
			                },
			                defaults:{margins:'0 0 5 0'},
			                items:this.getConfig()
				        })
				    );
				},
            	scope: this
			}]
        })
        Ext.ms.navi.Panel.superclass.initComponent.call(this);
    }
});

Ext.define('Ext.ms.navi.Button',{
	extend: 'Ext.button.Button',
	alias : "widget.naviButton",
	link:"#",
	toggleGroup:"naviGroup",
    initComponent : function(){
    	Ext.apply(this,{
    		listeners:{
	    		click:function(b,e,o){
	    			e.preventDefault();
	    			if(!b.pressed){
	    				b.toggle();
	    			}
	    		}
	    	}
    	});
        Ext.ms.navi.Button.superclass.initComponent.call(this);
    }
});

Ext.define('Ext.ms.doc.Panel',{
	extend: 'Ext.tab.Panel',
	alias : "widget.docpanel",
    initComponent: function(){
    	 Ext.apply(this,{
    		    resizeTabs: true,
    		    minTabWidth: 80,
    		    enableTabScroll: true,
    		    activeTab: 0,
    		    items: [{
    		        id:'welcome-panel',
    		        title: '首页',
    		        index:-1,
    		        html:'<iframe src="welcome.html" frameborder="0" width="100%" height="100%"></iframe>',
    		        cclass:'welcome.index',
    		        iconCls:'icon-docs',
    		        autoScroll: true
    		    }]
    	 });
    	Ext.ms.doc.Panel.superclass.initComponent.call(this);
    },
    loadPage : function(link, cls, title, loadType){
        var scls = cls.split('.');
        var id = 'docs-' + scls.join('-');
        var tab = this.getComponent(id);
        if(tab){
            this.setActiveTab(tab);
        }else{
        	if(loadType=='autoLoad'){
	            var autoLoad = {url:link,scripts:true};
	            var p = this.add({
	            	xtype:'docpage',
	                id: id,
	                cclass : cls+'.'+title,
	                autoLoad: autoLoad
	                //iconCls: Docs.icons[cls]
	            });
        	}else{
//        		var iframe=new Ext.ux.IFrame({  
//                    //xtype: 'uxiframe',  
//                    id: id,  
//                    title: title,  
//                    //iconCls: record.data.iconCls,  
//                    closable: true,  
//                    layout: 'fit',  
//                    loadMask: '页面加载中...',  
//                    border: false 
//                });
//                
//        		var p = this.add(iframe);
//        		p.on('afterrender',function(){
//        			iframe.load(Ext.ContextPath+link); 
//        		});
                
	            var p = this.add({
	            	xtype:'docpage',
	                id: id,
	                cclass : cls+'.'+title,
	                html: '<iframe src="'+Ext.ContextPath+link+'" frameborder="0" width="100%" height="100%"></iframe>'
	                //iconCls: Docs.icons[cls]
	            });
        	}
            this.setActiveTab(p);
        }
    }
});

Ext.define('Ext.ms.page.Panel',{
	extend: 'Ext.panel.Panel',
	alias : "widget.docpage",
    closable: true,
    autoScroll:true,
    initComponent : function(){
        var ps = this.cclass.split('.');
        this.title = ps[ps.length-1];
        this.winBox=new Array(),
        Ext.ms.page.Panel.superclass.initComponent.call(this);
    },
    pushWin:function(win){
    	this.winBox.push(win);
    },
    listeners:{
    	removed:function(t,o,e){
    		while(this.winBox.length>0){
    		  var ct=this.winBox.pop();
    		  Ext.destroy(ct);
    		}
    	}
    }
});

Ext.define('Ext.ms.header.Toolbar',{
	extend:'Ext.toolbar.Toolbar', 
	alias:'widget.headerbar',
	requires: ['Ext.menu.*','Ext.Ajax','Ext.ux.ms.aboutDialog'],
	naviUrl:'',
	naviAction:'',
	initComponent:function(){
		var me=this;
		var itemArray=new Array();
		var action=this.naviAction;
		
		itemArray.push({xtype: 'tbtext', text: 'WELCOME ! '});
		itemArray.push('-');
		if(!!this.xItems){
			for(var x in this.xItems){
				itemArray.push(this.xItems[x]);
			}
		}
		itemArray.push('->');
		itemArray.push('-');
		itemArray.push({text:'帮助',iconCls:'icon-help',menu:[{text:'关于RETP',iconCls:'icon-about',handler:showAbout}]});
 
		Ext.apply(this,{
			items: itemArray
		});
		
		var aboutD=null;
		function showAbout(){
			   if(!aboutD){
				   aboutD=Ext.create('Ext.ux.ms.aboutDialog',{  
				       picture           : 'images/aboutshow.jpg',
				       AppVersion        : .1 ,
				       AppText           : '"提供房产数据的接收，传输及加工，保证房产数据的有效联网"', 
				       AppLinkText       : '浙江奥维',
				       AppLinkHref       : 'http://www.zjaw.net/'  
				 })
			   }
			  aboutD.show();
		   }
    	this.showMemu=function(){
		   	 Ext.Ajax.request({    
		   	    url:this.naviUrl, 
		   	    method:'post',
		           success : function(response, options){
		               var resp = Ext.decode(response.responseText);
		               //updateItems(resp.navi);
		               me.items.each(function(item,index,length){
			    			if(index>1&&index<length-3)
			    			{
			    				items.removeAt(index);
			    			}
			    		});
		               updateItems(me,resp.root);
		           }, 
		          failure : function(response,options){
		               Ext.Msg.alert("错误","数据加载失败！")
		           }
			     });
    	}
    	//var tb=this;
    	function updateItems(tb,config){
    		
    		for(var m in config){
    			var item=config[m];
    			if(!item.leaf){
    				item.menu=[{text:'正在加载...'}];
    				item.listeners={
    					menushow:me.menuAjaxShow,
    					focus:me.menuAjaxShow
    				}
    			} else {
    				item.handler=me.naviAction;
    			}
    			
			}
    		tb.insert(2,config);
    	};

    	
    	this.menuAjaxShow=function(button){
    		//alert(button.text);
    		//alert(menu.items.getCount());
    		if( button.menuLoaded){
    			return;
    		}
    		var menu=button.menu;
    		//console.log(button.text);
    		 Ext.Ajax.request({    
		   	    url:me.naviUrl, 
		   	    params:{node:button.id},
		   	    method:'post',
		           success : function(response, options){
		               var resp = Ext.decode(response.responseText);
		               //updateItems(resp.navi);
		               menu.removeAll();
		               updateItems(menu,resp.root);
		               button.menuLoaded=true;
		           }, 
		          failure : function(response,options){
		               Ext.Msg.alert("错误","数据加载失败！")
		           }
			     });
    	}
		Ext.ms.header.Toolbar.superclass.initComponent.call(this);
	}
});

Ext.define('Ext.ms.footer.Container',{
	extend:'Ext.container.Container', 
	alias:'widget.footerInfo',
	height: 20,
    html: "<center><div style='font: 12px arial;'>数字化视频监控服务调度系统</div></center>"
});
