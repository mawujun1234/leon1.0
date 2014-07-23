Ext.define('Ext.D.column.Container', {
		extend:'Ext.container.Container',  
	    alias:'widget.columnbox',
	    layout:'column',
	    columnSize:2,
	    frame:false,
	    border:false,
	    initComponent: function(){
	    	var defaultsItem={columnWidth:1/this.columnSize,border:false};
	    	if(this.defaults){
	    		Ext.apply(defaultsItem,this.defaults)
	    	}
	    	Ext.apply(this, {
	    		defaults:defaultsItem,
	    		listeners:{
	    			add:function(container,c,i,e){
	    				if(i>0){
	    					Ext.apply(c,{
	    						margin:'0 0 0 5px'
	    					})
	    				}
	    			}
	    		}
	    	});
	    	Ext.D.column.Container.superclass.initComponent.call(this);
	    }
});

/*
 * url 数据接口链接
 * cascadeId 级联对象
 * autoSelected 自动选中
 */
Ext.define('Ext.D.list.ComboBox', {
	extend:'Ext.form.field.ComboBox',  
    alias:'widget.listcombox',
    url:null,
    loadAuto:false,
    listConfig: {loadMask:false},
    initComponent: function(){
    	if(this.url){
	    	Ext.apply(this, {
	    		store:Ext.create('Ext.data.Store',{
			    	    fields: [
			  	    	       {name: 'text', type: 'string'},
			  	    	       {name: 'value', type: 'int'}
			  	    	    ],
			    	    proxy: {
			    	        type: 'ajax',
			    	        url : this.url
			    	    },
			    	    autoLoad:this.loadAuto,
			    	    reference:this
					}),
			    displayField: 'text',
			    valueField: 'value'
	    	});
	    	
	    	if(!!this.baseParams){
	    	   var proxy=this.getStore().getProxy( );
	    	   proxy.extraParams=this.baseParams;
	    	}
	    	
	    	if(!!this.value){
	    		this.defaultValue=this.value;
	    		this.getStore().on('load',function(s,r,success,o,e){
	    			var combox=this.reference;
	    			if(success){
	    				combox.clearValue( );
	    				combox.setValue(combox.defaultValue);
	    			}
	    		});
//	    		this.store.listeners={load:function(s,r,success,o,e){
//	    			var combox=this.reference;
//	    			if(success){
//	    				combox.clearValue( );
//	    				combox.setValue(combox.defaultValue);
//	    			}
//	    		}}
	    	}else if(!!this.autoSelected||typeof(this.autoSelected)=='number'){
	    		this.getStore().on('load',function(s,r,success,o,e){
	    			var combox=this.reference;
	    			if(success){
	    				if(r.length>0){
			    			if(typeof(combox.autoSelected)=='boolean'){
			    				if(combox.autoSelected){
			    					combox.clearValue( );
			    					combox.setValue(r[0].get('value'));
			    				}
			    			}else if(typeof(combox.autoSelected)=='number'){
			    				combox.clearValue( );
			    				combox.setValue(r[combox.autoSelected].get('value'));
			    			}else{
			    				combox.clearValue( );
			    				combox.setValue(combox.autoSelected);
			    			}
	    				}
	    			}
	    		});
//	    		this.store.listeners={load:function(s,r,success,o,e){
//	    			var combox=this.reference;
//	    			if(success){
//	    				if(r.length>0){
//	    					alert(typeof(combox.autoSelected));
//			    			if(typeof(combox.autoSelected)=='boolean'){
//			    				if(combox.autoSelected){
//			    					combox.clearValue( );
//			    					combox.setValue(r[0].get('value'));
//			    				}
//			    			}else if(typeof(combox.autoSelected)=='number'){
//			    				combox.clearValue( );
//			    				combox.setValue(r[combox.autoSelected].get('value'));
//			    			}else{
//			    				combox.clearValue( );
//			    				combox.setValue(combox.autoSelected);
//			    			}
//	    				}
//	    			}
//	    		}}
	    	}
    	}
    	if(this.cascadeId){
    		this.addListener("change",function(f,n,o,e){
				var cascade=Ext.getCmp(this.cascadeId);
				if(cascade){
					cascade.reset();
					var baseParams = {selectkey:null};
					if(n&&n!=='')baseParams.selectkey=n;
					var cascadeStore=cascade.store;
			        Ext.apply(cascadeStore.proxy.extraParams, baseParams);
			        cascadeStore.load();
				}else{
					Ext.Msg.alert('提示','级联对象不存在');
				}
    			if(this.selectChange){
    				this.selectChange(f,n,o,e);
    			}
    		});
    	}
    	Ext.D.list.ComboBox.superclass.initComponent.call(this);
    }
});

Ext.define('Ext.D.form.field.CheckboxGroup', {
	extend:'Ext.form.FieldContainer',  
    alias:'widget.checkgroupboxfield',
    valuefield:'value',
    diplayfield:'text',
    columns:2,
    allValue:null,
    initComponent:function (){
    	if(this.store){
        	var checkbox_list=Ext.create('Ext.form.CheckboxGroup',{
            	hideLabel : 'true',
                columns: this.columns,
                vertical: true,
                autoScroll:true,
                reference:this,
                listeners:{
                	change:function(f,n,o,e){
                		var obj=new Array();
            			for(var i in n){
            				obj=n[i];
            				break;
            			}
            			if(obj.length!=0){
            				if(checkbox_all.getValue()){
            					checkbox_all.setValue(false);
            				}
            			}
            			f.reference.onChange(f,n,o,e);
                	}
                }
            });
    		this.store.on('load',function(s,r,success,o,e){
    			if(success){
	    			s.each(function(record){
	    				var item={boxLabel:record.get(this.diplayfield),inputValue:record.get(this.valuefield)};
	    				var submitName=this.name;
	    				if(submitName){
	    					Ext.apply(item,{name:submitName})
	    				}
	    				Ext.apply(item,{checked:!!record.get('checked')});
	    				this.down('checkboxgroup').add(item);
	    			},this);
	    			var label_dom=this.labelEl.dom;
	    			if(label_dom.innerHTML.indexOf(this.labelSeparator)==-1){
	    				label_dom.innerHTML=this.getFieldLabel( )+this.labelSeparator;
	    			}
    			}
    			this.onLoad(s,r,success,o,e);
    		},this)
    	}
    	
    	var allConfig={
    		boxLabel:'全部',
            inputValue: true,
            checked:true
    	};
    	if(this.name){
    		allConfig.name=this.name;
    	}
    	var checkbox_all=Ext.create('Ext.form.field.Checkbox',allConfig);
    	checkbox_all.on('change',function(f,n,o,e){
    		if(n){
    		  var obj=checkbox_list.getValue();
    		  for(var i in obj){
    			  checkbox_list.setValue({});
    			  break;
    		  }
    		}
    	})
    	
    	Ext.apply(this,{
    		layout:{type:'vbox',align:'stretch'},
    		items:[checkbox_all,{flex:1,layout:'fit',bodyStyle:{padding:'5px'},items:[checkbox_list]}],
    		getValue:function(){
    		  if(checkbox_all.getValue()){
    			  if(this.allValue){
    				  return this.allValue
    			  }else{
	    			  var objects=new Array();
	    			  this.store.each(function(record){
	    				  objects.push(record.get('value'));
	    			  });
	    			  return objects.join(',');
    			  }
    		  }else{
	      		  var obj=checkbox_list.getValue();
	    		  for(var i in obj){
	    			  return String(obj[i]);
	    		  }
    		  }
    		  return null;
    		},    
    		onChange:function(f,n,o,e){
    			
    	    },
    		onLoad:function(s,r,success,o,e){
    			
    		}
    	});
    	
    	Ext.D.form.field.CheckboxGroup.superclass.initComponent.call(this);
    }
});

Ext.define('Ext.D.img.Box', {
	extend:'Ext.Component',  
    alias:'widget.imgbox',
    initComponent:function (){
    	var imgUrl=this.imgSrc;
    	if(imgUrl==null||imgUrl==""){
    		imgUrl=Ext.BLANK_IMAGE_URL;
    	}
    	Ext.apply(this,{
    	    autoEl : {  
    	        tag : 'img',   
    	        src : imgUrl, 
    	        style : 'filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale);',   
    	        complete:'off' 
    	        } 
    	});
    	Ext.D.img.Box.superclass.initComponent.call(this);
    }
});

Ext.define('Ext.dx.form.QueryField', {
    extend: 'Ext.form.field.Trigger',
    
    alias: 'widget.queryfield',
    
    trigger1Cls: Ext.baseCSSPrefix + 'form-clear-trigger',
    
    trigger2Cls: Ext.baseCSSPrefix + 'form-search-trigger',
    
    hasSearch : false,
    
    initComponent: function(){
        this.callParent(arguments);
        this.on('specialkey', function(f, e){
            if(e.getKey() == e.ENTER){
                this.onTrigger2Click();
            }
        }, this);
    },
    
    afterRender: function(){
        this.callParent();
        this.triggerEl.item(0).setDisplayed('none');  
    },
    
    onTrigger1Click : function(){
        var me = this;
        
        if (me.hasSearch) {
            me.setValue('');
            me.hasSearch = false;
            me.triggerEl.item(0).setDisplayed('none');
            me.doComponentLayout();
        }
    },

    onTrigger2Click : function(){
        var me = this,
        	value = me.getValue();
        me.onTriggerClick();
        if (value.length < 1) {
            me.onTrigger1Click();
            return;
        }
        me.hasSearch = true;
        me.triggerEl.item(0).setDisplayed('block');
        me.doComponentLayout();
    }
});