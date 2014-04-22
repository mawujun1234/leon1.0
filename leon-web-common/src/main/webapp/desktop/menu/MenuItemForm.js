Ext.require('Leon.common.IconWindow');
Ext.require('Leon.desktop.fun.FunGridQuery');
Ext.define('Leon.desktop.menu.MenuItemForm',{
	extend:'Ext.form.Panel',
	requires: [
	     'Leon.desktop.menu.MenuItem'
	],
	fieldDefaults: {
            msgTarget: 'side',
            labelWidth: 75,
            labelAlign:'right'
    },
    required:'<span style="color:red;font-weight:bold" data-qtip="Required">*</span>',
    defaultType: 'textfield',
    frame: true,
    bodyPadding: '5 5 0 0',
    //trackResetOnLoad:true,
    
    fieldDefaults: {
            labelWidth: 75,
            labelAlign:'right',
            anchor: '100%'
    },

    layout: {
            type: 'vbox',
            align: 'stretch'  // Child items are stretched to full width
    },
	initComponent: function () {
       var me = this;

       me.items= [
           {
	            fieldLabel: 'id',
	            //afterLabelTextTpl: me.required,
	            name: 'id',
	            readOnly:true
	            //allowBlank: false,
	            //tooltip: 'Enter your first name'
	        },
	        {
                xtype: 'fieldcontainer',
                fieldLabel: '所属功能',
                combineErrors: true,
                msgTarget : 'side',
                layout: 'hbox',
                defaults: {
                    flex: 1,
                    hideLabel: true
                },
                items: [{
                        xtype     : 'triggerfield',
                        triggerCls:'x-form-clear-trigger',
                        //readOnly:true,
                        editable:false,
                        name      : 'fun_text',
                        fieldLabel: '功能名称',
                        readOnly:true,
                        allowBlank: true,
                        onTriggerClick: function() {
                        	var trigger=this;
					        trigger.setValue("");
					        //alert(trigger.nextSibling().getValue());
					        trigger.nextSibling().setValue("");
					        
					        //alert(trigger.nextSibling().getValue());
					    }
                    },{
                        xtype     : 'hidden',
                        name      : 'fun_id',
                        flex:0,
                        fieldLabel: '功能id',
                        //margin: '0 5 0 0',
                        allowBlank: true
                    },{
                        xtype     : 'button',
                        flex:0,
                        width:80,
                        text      : '选择功能',
                        handler:function(btn){
                        	var fun_id_txt=btn.previousSibling();
                        	var fun_text_txt=fun_id_txt.previousSibling();
                        	me.showFunTree(btn,fun_id_txt,fun_text_txt);
                        	
                        }
                    }]
            },
            {
                xtype: 'fieldcontainer',
                fieldLabel: '图标',
                combineErrors: true,
                msgTarget : 'side',
                layout: 'hbox',
                defaults: {
                    flex: 1,
                    hideLabel: true
                },
                items: [{
                        xtype     : 'button',
                        flex:0,
                        width:80,
                        
                        text      : '选择图标',
                        handler:function(iconButton){

                        	var win=Ext.create('Leon.common.IconWindow',{
                        		listeners:{
                        			itemdblclick:function(win,record){
                        				Ext.util.CSS.createStyleSheet("."+record.get("iconCls")+"{background: url("+record.get("src16")+") left top no-repeat !important;}") ;
                        				Ext.util.CSS.createStyleSheet("."+record.get("iconCls32")+"{background: url("+record.get("src")+") left top no-repeat !important;}") ;
                        				
                        				iconButton.setIconCls(record.get("iconCls") );
                        				iconButton.nextSibling().setValue(record.get("iconCls"));
                        				iconButton.nextSibling().nextSibling().setValue(record.get("iconCls32"));
                        				win.close();
                        			}
                        		}
                        	});
                        	win.show();
                        }
                    },{
                        xtype     : 'hidden',
                        name      : 'iconCls',
                        flex:0,
                        fieldLabel: '图标Cls',
                        listeners:{
                        	change:function(field, newValue, oldValue, eOpts){
                        		var iconButton=field.previousSibling();
                        		iconButton.setIconCls(newValue );
                        	}
                        }
                    },{
                        xtype     : 'hidden',
                        name      : 'iconCls32',
                        flex:0,
                        fieldLabel: '图标Cls32',
                        listeners:{
                        	change:function(field, newValue, oldValue, eOpts){
                        		//var iconButton=field.previousSibling();
                        		//iconButton.setIconCls(newValue );
                        	}
                        }
                    }]
            },
//            	{
//	            fieldLabel: 'id',
//	            //afterLabelTextTpl: me.required,
//	            readOnly:true,
//	            name: 'id'
//	            //allowBlank: false,
//	            //tooltip: 'Enter your first name'
//	        },
	        {
	            fieldLabel: '名称',
	            afterLabelTextTpl: me.required,
	            name: 'text',
	            readOnly:true,
	            allowBlank: false,
	            tooltip: '请输入名称'
	        },{
	            fieldLabel: '助记码',
	            //afterLabelTextTpl: me.required,
	            readOnly:true,
	            name: 'code'
	            //allowBlank: false,
	            //tooltip: '请输入名称'
	        },{
	            fieldLabel: 'java扩展',
	            name: 'javaClass',
	            readOnly:true,
	            afterLabelTextTpl:'<span class="icons_help" data-qtip="后台扩展菜单，必须继承com.mawujun.menu.MenuVOExten，例子请看MenuVOExtenSample。">&nbsp;&nbsp;&nbsp;&nbsp;</span>',
	            tooltip: "请输入javaClass"
	        },{
	            fieldLabel: 'js扩展',
	            xtype:'textareafield',
	            readOnly:true,
	            grow      : true,
	            afterLabelTextTpl:'<span class="icons_help" data-qtip="写一个函数，这个函数可以访问menuItem，例如function run(){menuItem.setText(menuItem.getText( )}">&nbsp;&nbsp;&nbsp;&nbsp;</span>',
	            name: 'scripts',
	            tooltip: "请输入scripts"
	        }
	    ];
	    me.buttons= [{
            text: '保存',
            itemId:'save',
            disabled :true,
            iconCls:'form-save-button',
            listeners:{
				disable:function(b,e){
					var fields=b.up('form').getForm().getFields( );
					fields.each(function(items){
						items.setReadOnly(true);
					});
				},
				enable:function(b){
					var fields=b.up('form').getForm().getFields( );
					fields.each(function(items){
						if(items.getName()=='id'){
							items.setReadOnly(true);
						}else{
							items.setReadOnly(false);
						}
					});
				}
			},
            handler: function() {
            	var form=this.up('form');
                if(!form.getForm().isValid()) {
                	return;
                }
                
                
                //设置关联对象的值，否则传递到后台的时候只有fun_id,而没有{。。fun:{....}}
                if(form.selectFun){
                	form.getRecord().setFun(form.selectFun);
                	delete form.selectFun;
                }
               //这个时候只会更新forginKey，而不会更新associationName的数据
                form.getForm().updateRecord();
                //console.log(form.getRecord().get("fun_id"));
                //当清空关联的功能的时候
                if(!form.getRecord().get("fun_id")){
                	form.getRecord().setFun(null);
                }
                
				form.getRecord().save({
					success: function(record, operation) {
						me.fireEvent('createOrupdate',me,record);
						b.disable();
					}
				});
            }
        }
//        ,{
//            text: '重置',
//            iconCls:'form-reset-button',
//            handler: function() {
//            	//var copyRcd=this.up('form').getRecord( );
//                this.up('form').getForm().reset(true);
//            }
//        }
        ];
       
       me.addEvents("createOrupdate");
       me.callParent();
	},
	showFunTree:function(showTarget,fun_id_txt,fun_text_txt){
		var me=this;
		var tree=Ext.create('Leon.desktop.fun.FunGridQuery',{
			width:600,
			height:500
		});
		tree.on("itemdblclick",function(view,record,index,e){
			
			fun_id_txt.setValue(record.get("id"));
			fun_text_txt.setValue(record.get("text"));
			win.close();
			
			me.selectFun=record;
		});
		var win=Ext.create('Ext.Window',{
			layout:'fit',
			modal:true,
			constrainHeader:true,
			title:showTarget.getText(),
			items:tree
		});
		win.show(showTarget);	
	},
	
	//党是默认菜单的菜单项时，禁止一些元素的修改
	disableItems:[],
	initdisableItems:function(){
		var me=this;
		var fields=me.getForm().getFields( ) ;
		var disableFieldObj={
			code:true,
			javaClass:true,
			scripts:true
		}
		fields.each(function(item,index ,len){
			if(!disableFieldObj[item.getName( )]){
				//item.setReadOnly(true);
				me.disableItems.push(item);
			}
		});
		var buttons=me.query("button");
		for(var i=0;i<buttons.length;i++){
			var item=buttons[i];
			if(item.getItemId( )!="save"){
				//item.disable();
				me.disableItems.push(item);
			}
		}
		me.disableItems.readOnly=true;
	}
//	setReadonlyItem4DefauleMenu:function(bool){
//		var me=this;
//		if(me.disableItems.length==0){
//			me.initdisableItems();
//		}
//		if(!bool){
//			for(var i=0;i<me.disableItems.length;i++){
//				if(me.disableItems[i].isFormField){
//					me.disableItems[i].setReadOnly(false);//.enable();
//				} else {
//					me.disableItems[i].enable();
//				}
//				
//			}
//			return;
//		} else {
//			for(var i=0;i<me.disableItems.length;i++){
//				if(me.disableItems[i].isFormField){
//					me.disableItems[i].setReadOnly(true);//.enable();
//				} else {
//					me.disableItems[i].disable();
//				}
//				
//			}
//		}
//		
//	}
//	//恢复diable掉的组件
//	enableItem4DefauleMenu:function(){
//		var me=this;
//		for(var i=0;i<me.disableItems.length;i++){
//			me.disableItems[i].enable();
//		}
//		//me.disableItems.length=0;
//	}
});