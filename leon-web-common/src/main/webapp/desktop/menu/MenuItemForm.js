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
      
       me.items= [{
	            fieldLabel: 'parent_id',
	            //afterLabelTextTpl: me.required,
	            name: 'parent_id',
	            readOnly:true
	            //allowBlank: false,
	            //tooltip: 'Enter your first name'
	        },{
	            fieldLabel: '上级名称',
	            //afterLabelTextTpl: me.required,
	            name: 'parent_text',
	            readOnly:true
	            //allowBlank: false,
	            //tooltip: 'Enter your first name'
	        },{
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
                        xtype     : 'textfield',
                        name      : 'fun_text',
                        fieldLabel: '功能名称',
                        //margin: '0 5 0 0',
                        allowBlank: false
                    },{
                        xtype     : 'hidden',
                        name      : 'fun_id',
                        flex:0,
                        fieldLabel: '功能id',
                        //margin: '0 5 0 0',
                        allowBlank: false
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
            },{
	            fieldLabel: 'id',
	            //afterLabelTextTpl: me.required,
	            readOnly:true,
	            name: 'id'
	            //allowBlank: false,
	            //tooltip: 'Enter your first name'
	        },{
	            fieldLabel: '名称',
	            afterLabelTextTpl: me.required,
	            name: 'text',
	            allowBlank: false,
	            tooltip: '请输入名称'
	        },{
	            fieldLabel: '助记码',
	            //afterLabelTextTpl: me.required,
	            name: 'code'
	            //allowBlank: false,
	            //tooltip: '请输入名称'
	        },{
	            fieldLabel: '菜单扩展js',
	            name: 'pluginUrl',
	            tooltip: "请输入pluginUrl"
	        },{
	            fieldLabel: 'scripts',
	            xtype:'textareafield',
	            grow      : true,
	            name: 'scripts',
	            tooltip: "请输入scripts"
	        }
	    ];
	    me.buttons= [{
            text: '保存',
            itemId:'save',
            iconCls:'form-save-button',
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
                
               // alert(form.getValues(true));
               // alert(form.getForm().getRecord().getFun().get("id"));
				form.getRecord().save({
					success: function(record, operation) {
						console.dir(form.getRecord().getFun());
						if(form.action=="create"){
							me.fireEvent("created",record);
							form.action="update"
							
							me.down("button[itemId=createChild]").show();
						} else {
							
						}
					}
				});
            }
        },{
            text: '重置',
            iconCls:'form-reset-button',
            handler: function() {
            	var copyRcd=this.up('form').getRecord( );
                this.up('form').getForm().reset(copyRcd);
            }
        },{
            text: '复制',
            iconCls:'form-copy-button',
            handler: function() {
                //this.up('form').getForm().isValid();
            	var copyRcd=this.up('form').getRecord( ).copy();
            	copyRcd.set("id",null);
            	this.up('form').copyRcd=copyRcd;
            	this.nextSibling().show();
            }
        },{
            text: '粘贴',
            hidden:true,
            iconCls:'form-paste-button',
            handler: function() {
            	this.up('form').getForm().loadRecord(this.up('form').copyRcd);
            	this.up('form').action="create";
                this.hide();
            }
        },{
            text: '新建子功能',
            iconCls:'form-addChild-button',
            itemId:'createChild',
            handler: function(bth) {
            	var parent=this.up('form').getRecord( );
            	this.up('form').getForm().reset();
            	alert(parent.get("menu_id"));
            	var newRecord=Ext.createModel("Leon.desktop.menu.MenuItem",{'parent_id':parent.get("id"),'parent_text':parent.get("text"),menu_id:parent.get("menu_id")});
            	this.up('form').getForm().setValues({'parent_id':parent.get("id"),'parent_text':parent.get("text")});
            	this.up('form').getForm().loadRecord(newRecord);
            	this.up('form').action="create";
            	
            	bth.hide();
            }
        },{
            text: '删除',
            iconCls:'form-delete-button',
            handler: function() {

            	var record= this.up('form').getRecord();
            	var parent=record.parentNode;
            	var index=parent.indexOf(record);
            	if(record.hasChildNodes( )){
            		Ext.Msg.alert("消息","请先删除子节点!");
            		return;
            	}
                record.destroy({
                	failure: function(record, operation) {
                		parent.insertChild(index,record);
                	}
                });
            }
        }];
       
       me.addEvents("created");
       me.callParent();
	},
	showFunTree:function(showTarget,fun_id_txt,fun_text_txt){
		var me=this;
		var tree=Ext.create('Leon.desktop.fun.FunTree',{
//			region:'west',
//			split: true,
//			collapsible: true,
//			title:'功能树',
			width:400,
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
	disableItem4DefauleMenu:function(){
		var me=this;
		if(me.disableItems.length>0){
			for(var i=0;i<me.disableItems.length;i++){
				me.disableItems[i].disable();
			}
			return;
		}
		var fields=me.getForm().getFields( ) ;
		var disableFieldObj={
			code:true,
			pluginUrl:true,
			scripts:true
		}
		fields.each(function(item,index ,len){
			if(!disableFieldObj[item.getName( )]){
				item.disable();
				me.disableItems.push(item);
			}
		});
		var buttons=me.query("button");
		for(var i=0;i<buttons.length;i++){
			var item=buttons[i];
			console.log(item.getItemId( ));
			if(item.getItemId( )!="save"){
				item.disable();
				me.disableItems.push(item);
			}
		}
	},
	//恢复diable掉的组件
	enableItem4DefauleMenu:function(){
		var me=this;
		for(var i=0;i<me.disableItems.length;i++){
			me.disableItems[i].enable();
		}
		//me.disableItems.length=0;
	}
});