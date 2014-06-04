Ext.define('Leon.desktop.fun.FunForm',{
	extend:'Ext.form.Panel',
	requires: [
	     'Leon.desktop.fun.Fun'
	],
	fieldDefaults: {
            msgTarget: 'side',
            labelWidth: 75,
            labelAlign:'right'
    },
    required:'<span style="color:red;font-weight:bold" data-qtip="Required">*</span>',
    defaultType: 'textfield',
    frame: true,
    bodyPadding: '5 5 0',
    //trackResetOnLoad:true,
    
    fieldDefaults: {
            labelWidth: 75,
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
	            readOnly:true,
	            name: 'id'
	            //allowBlank: false,
	            //tooltip: 'Enter your first name'
	        },
	        {
	            fieldLabel: '名称',
	            afterLabelTextTpl: me.required,
	            name: 'text',
	            readOnly:true,
	            allowBlank: false,
	            tooltip: '请输入名称'
	        },{
	        	xtype:'combobox',
			    fieldLabel: '是否可用',
			    afterLabelTextTpl:'<span class="icons_help" data-qtip="不可用：表示所有关联到这个功能的菜单和界面元素都看不见了，可用于临时维护。">&nbsp;&nbsp;&nbsp;&nbsp;</span>',
			    store:  Ext.create('Ext.data.Store', {
				    fields: ['id', 'name'],
				    data : [
				        {"id":true, "name":"可用"},
				        {"id":false, "name":"不可用"}
				    ]
				}),
			    queryMode: 'local',
			    displayField: 'name',
			    valueField: 'id',
			    readOnly:true,
			    editable:false,
			    value:true,
			    name:'isEnable'
			},{
	            fieldLabel: 'url',
	            name: 'url',
	            readOnly:true,
	            tooltip: "请输入url"
	        }
	        ,{
	        	xtype:'constantcombo',
                fieldLabel  : '业务类型',
                readOnly:true,
                name: 'bussinessType',
				code:'Fun_BussinessType'
            }
	    ];
//	    me.buttons= [{
//            text: '保存',
//            itemId:'save',
//            disabled :true,
//            iconCls:'form-save-button',
//            listeners:{
//				disable:function(b,e){
//					var fields=b.up('form').getForm().getFields( );
//					fields.each(function(items){
//						items.setReadOnly(true);
//					});
//				},
//				enable:function(b){
//					var form=b.up('form');
//					var fields=form.getForm().getFields( );
//					fields.each(function(items){
//						if(items.getName()=='id' && !form.createAction){
//							items.setReadOnly(true);
//						}else{
//							items.setReadOnly(false);
//						}
//					});
//				}
//			},
//            handler: function(btn) {
//            	var form=this.up('form');
//                if(!form.getForm().isValid()) {
//                	return;
//                }
//                form.getForm().updateRecord();
//				form.getRecord().save({
//					success: function(record, operation) {
//						btn.disable();
//						
//						form.grid.getStore().reload();
//					}
//				});
//				
//            }
//        }
////        ,{
////            text: '重置',
////            iconCls:'form-reset-button',
////            handler: function() {
////            	//var copyRcd=this.up('form').getRecord( );
////                this.up('form').getForm().reset(true);
////            }
////        }
//        ];
//       
//       //me.addEvents("created");
	    
	    var saveButton=Ext.create('Ext.button.Button',{
            text: '保存',
            iconCls:'form-save-button',
            //formBind: true,当设置这个值得时候，当表单里面有内容后，就自动会变成可执行
            hidden :true,
            itemId:'save',
            listeners:{
				hide:function(b,e){
					
					var fields=b.up('form').getForm().getFields( );
					fields.each(function(items){
						items.setReadOnly(true);
					});
				},
				show:function(b){
					var form=b.up('form');
					var fields=form.getForm().getFields( );
					fields.each(function(items){
						if(items.getName()=='id'  && !form.createAction){
							items.setReadOnly(true);
						}else{
							items.setReadOnly(false);
						}
					});
				}
			},
            handler: function(btn) {
            	var form=this.up('form');
                if(!form.getForm().isValid()) {
                	return;
                }
                form.getForm().updateRecord();
				form.getRecord().save({
					success: function(record, operation) {
						btn.hide();
						form.down("button#cancel").hide();
						var buttons=form.query("button[cls~=fla_form_action]");
						for(var i=0;i<buttons.length;i++){
							buttons[i].show();
						}
						//如果是新建的话,新建完成，就清空form
						if(form.createAction){
							form.down("button#update").disable();
							form.down("button#destroy").disable();
							form.getForm().reset();
						}
						
						me.fireEvent("saved");
					}
				});
            }
      });
      
      var cancelButton=Ext.create('Ext.button.Button',{
      	text: '取消',
      	iconCls:'form-cancel-button',
      	hidden :true,
      	itemId:'cancel',
      	handler: function(btn) {
            var form=btn.up('form');
            form.down("button#save").hide();
            btn.hide();
			var buttons=form.query("button[cls~=fla_form_action]");
			for(var i=0;i<buttons.length;i++){
				buttons[i].show();
			}
        }
      });
      
	  me.tbar=	[saveButton,cancelButton,{
		  	text: '新增',
			itemId:'create',
			cls:'fla_form_action',
			handler: function(btn){
				var form=btn.up("form");
				var grid=form.grid;//是在CustomerSourceApp.js中把引用授予的
				var modelName=grid.model||grid.getStore().getProxy( ).getModel().getName( );
				var model=Ext.createModel(modelName,{      	//id:''
				});
				
				form.getForm().loadRecord(model);//form是在app中定义的grid.form=form;
				model.phantom =true;
								
				form.createAction=true;
				form.down("button#save").show();
				form.down("button#cancel").show();
				var buttons=form.query("button[cls~=fla_form_action]");
				for(var i=0;i<buttons.length;i++){
					buttons[i].hide();
				}
				
			},
			iconCls: 'form-add-button'
		},{
			text: '更新',
			itemId:'update',
			disabled:true,
			cls:'fla_form_action',
			handler: function(btn){
				//var grid=btn.up("grid");
				var form=btn.up("form");
				var grid=form.grid;
				
				form.down("button#save").show();
				form.down("button#cancel").show();
				var buttons=form.query("button[cls~=fla_form_action]");
				for(var i=0;i<buttons.length;i++){
					buttons[i].hide();
				}
			},
			iconCls: 'form-update-button'
		},{
			text: '删除',
			itemId:'destroy',
			disabled:true,
			cls:'fla_form_action',
			handler: function(btn){
				//var grid=btn.up("grid");
				var form=btn.up("form");
				var grid=form.grid;
		    	Ext.Msg.confirm("删除",'确定要删除吗?', function(btn, text){
					if (btn == 'yes'){
						var records=grid.getSelectionModel( ).getSelection( );//.getLastSelected( );
						grid.getStore().remove( records );
						form.down("button#update").disable();
						form.down("button#destroy").disable();
						form.getForm().reset();
						grid.getStore().sync({
							failure:function(){
								grid.getStore().reload();
							}
						});
					}
				});
			},
			iconCls: 'form-delete-button'
		}];
       me.callParent();
	},
	loadRecord:function(){
		var form=this;
		form.down("button#update").enable();
		form.down("button#destroy").enable();
		
		form.callParent(arguments);
	}
});