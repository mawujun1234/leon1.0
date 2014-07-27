/**
 * 只要覆盖onTrigger2Click方法就可以了，例子看注释掉的,
 * 使用方法如下：
 * ,{
	        	xtype:'multiselectfield',
                fieldLabel  : '测试',
                name      : 'multiselectfield'
            }
            还可能要覆盖onTrigger2Click方法 来弹出选择框
 */
Ext.define('Leon.common.ux.MultiSelectField', {
    extend: 'Ext.form.field.Trigger',
    requires:[
    	//'Leon.common.ux.MultiSelectPanel'
    ],
    
    alias: 'widget.multiselectfield',
    
    trigger1Cls: Ext.baseCSSPrefix + 'form-clear-trigger',
    
    trigger2Cls: Ext.baseCSSPrefix + 'form-search-trigger',
    
    value:null,

    initComponent: function(){
        this.callParent(arguments);
        this.editable =false;
        this.on('specialkey', function(f, e){
            if(e.getKey() == e.ENTER){
                this.onTrigger2Click();
            }
        }, this);
    },
    
    afterRender: function(){
        this.callParent();
        //this.triggerEl.item(0).setDisplayed('none');  
    },
    /**
     * 点删除按钮时清空数据
     */
    onTrigger1Click : function(){
        var me = this;
         
        me.setValue('');
        me.setRawValue('');//'' + Ext.value(value, '');

    },
	/**
	 * 这个是要覆盖的方法，在这里实现如何从弹出框里面获取值
	 * 点查询按钮的时候，弹出框进行查询
	 * 弹出panel返回的Models，要组装成，value是数组，rawValue是以逗号分隔的字符串，显示用的
	 */
    onTrigger2Click : function(){
        var me = this;
        var selectPanel=Ext.create('Leon.common.ux.MultiSelectPanel',{
        	listeners:{
        		selectedFinish:function(models){
        		    if(!models||models.length==0){
		        		return;
		        	}
		        	alert(models);
		        	var rawValue='';
		        	var value=[];
		        	for(var i=0;i<models.length;i++){
		        		value.push(models[i].get('id'))
		        		rawValue+=','+models[i].get('name');
		        	}
		        	
		        	me.setValue(value);
		            me.setRawValue(rawValue.substring(1));
		            selectPanel.close();
        		}
        	}
        });
        selectPanel.show(me);
        
  //这是例子，不要删除    
//        var selOrgWin=Ext.create('Ext.ux.utils.SelectOrgPanel',{});
//        selOrgWin.show();
//        selOrgWin.on("selectedOrg",function(models){
//        	//alert(models.length);
//        	if(!models||models.length==0){
//        		return;
//        	}
//        	var rawValue='';
//        	var value=[];
//        	for(var i=0;i<models.length;i++){
//        		//value+=','+models[i].get('id');
//        		value.push(models[i].get('id'))
//        		rawValue+=','+models[i].get('name');
//        	}
//        	
//        	me.setValue(value);
//          me.setRawValue(rawValue.substring(1));
//        });
        
    },
    setValue:function(value){
    	var me = this;
        me.setRawValue(me.valueToRaw(value));
    	me.value=value;
    },
    getValue: function() {
        var me = this,val = me.value?me.value:me.rawToValue(me.processRawValue(me.getRawValue()));
        return val;
    },
    getSubmitValue: function() {
        return '' + Ext.value(this.value, '');
    }


});
/**
 * 用来显示框的，默认添加一个按
 */
Ext.define('Leon.common.ux.MultiSelectPanel', {
	 extend: 'Ext.Window',
	 layout:{type:'hbox',align: 'stretch'},     
	 modal:true,
	 title:'可多选',
	 width:530,
	 height:400,
	 initComponent: function () {
	 	var me=this;
	 	var panel1=me.getOrginalDataPanel();
	 	var panel2=me.getSelectedDataPanel();
	 	 
	 	me.items=[panel1,panel2];
	 	me.buttons= [
		  { text: '确定',handler:function(){
		  	var models=me.selectedDataPanel.getStore().getRange();
		  	
		  	me.fireEvent("selectedFinish",models);
		  } },
		  { text: '取消',handler:function(){
		  	me.close();
		  }}
		];
		
		me.addEvents("selectedFinish");
		me.callParent();
	 },
	 /**
	  * 生成被选择的数据源
	  */
	 getOrginalDataPanel:function(){
	 	var me=this;
	 	var store=Ext.create('Ext.data.Store', {
		    fields:['id', 'name'],
		    data:{'items':[
		        { 'name': 'Lisa',  "id":"555-111-1224"  },
		        { 'name': 'Bart',  "id":"555-222-1234" },
		        { 'name': 'Homer',  "id":"555-222-1244"  },
		        { 'name': 'Marge', "id":"555-222-1254"  }
		    ]},
		    proxy: {
		        type: 'memory',
		        reader: {
		            type: 'json',
		            root: 'items'
		        }
		    }
		});
		
		var grid=Ext.create('Ext.grid.Panel', {
		    store: store,
		    columns: [
		        { text: 'Name',  dataIndex: 'name' },
		        { text: 'id', dataIndex: 'id', flex: 1 }
		    ],
		    flex:1.1,
		    selModel:{mode:'MULTI'},
		    dockedItems: [{
		        xtype: 'toolbar',
		        dock: 'right',
		        items: [{xtype:'tbspacer',flex:1},{//选择角色
		        	icon:'/icons/arrow.png',
		            text: '',
		            handler:function(){
		            	var models=grid.getSelectionModel( ).getSelection();
		            	if(models){
		            		me.selectedDataPanel.getStore().insert(0,models);
		            	} else {
		            		
		            	}
		            }
		        },{
		        	icon:'/icons/arrow_180.png',	
		            text: '',
		            handler:function(){//去掉角色
		            	var models=me.selectedDataPanel.getSelectionModel( ).getSelection();
		            	if(models){
		            		me.selectedDataPanel.getStore().remove(models);
		            	} else {
		            		
		            	}
		            }
		        },{xtype:'tbspacer',flex:1}]
		    }]
		});
		me.orginalDataPanel=grid;
		return grid;
	 },
	 getSelectedDataPanel:function(){
	 	var me=this;
	 	var store=Ext.create('Ext.data.Store', {
		    fields:['id', 'name'],
		    data:{'items':[
		      
		    ]},
		    proxy: {
		        type: 'memory',
		        reader: {
		            type: 'json',
		            root: 'items'
		        }
		    }
		});
		
		var grid=Ext.create('Ext.grid.Panel', {
		    store: store,
		    flex:0.9,
		    selModel:{mode:'MULTI'},
		    columns: [
		        { text: 'Name',  dataIndex: 'name' },
		        { text: 'id', dataIndex: 'id', flex: 1 }
		    ]
		});
		me.selectedDataPanel=grid;
		return grid;
	 }
});