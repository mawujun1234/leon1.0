<#assign simpleClassNameFirstLower = simpleClassName?uncap_first> 
<#-- //所在模块-->
<#assign module = basepackage?substring(basepackage?last_index_of(".")+1)> 
Ext.define('Leon.${module}.${simpleClassName}Form',{
	extend:'Ext.form.Panel',
	requires: [
	     'Leon.${module}.${simpleClassName}'
	],
	fieldDefaults: {
        msgTarget: 'side',
        labelWidth: 75,
        labelAlign:'right',
        anchor: '90%'
    },
    frame: true,
    bodyPadding: '5 5 0',

 //   layout: {
 //       type: 'vbox',
 //       align: 'stretch'  // Child items are stretched to full width
 //   },
	initComponent: function () {
       var me = this;
       me.items= [
      <#list propertyColumns as propertyColumn>
      <#if propertyColumn.isBaseType==true ||  propertyColumn.isIdProperty==true>
		<#if propertyColumn.jsType=='date'>
		{
            fieldLabel: '${propertyColumn.label!propertyColumn.property}',
            name: '${propertyColumn.property}',
            readOnly:true,
            xtype: 'datefield',
            format: 'Y-m-d'
        }<#if propertyColumn_has_next>,</#if>
		<#elseif propertyColumn.jsType=='int' || propertyColumn.jsType=='float'>
		{
	        fieldLabel: '${propertyColumn.label!propertyColumn.property}',
	        //afterLabelTextTpl: Ext.required,
	        name: '${propertyColumn.property}',
	        readOnly:true,
	        xtype:'numberfield',
	        allowBlank: false
	    }<#if propertyColumn_has_next>,</#if>
		<#else>
		{
	        fieldLabel: '${propertyColumn.label!propertyColumn.property}',
	        //afterLabelTextTpl: Ext.required,
	        name: '${propertyColumn.property}',
	        readOnly:true,
	        xtype:'textfield',
	        allowBlank: false
	    }<#if propertyColumn_has_next>,</#if>
		</#if>
	  <#elseif propertyColumn.isConstantType==true>
		{
	        xtype:'constantcombo',
            fieldLabel:'${propertyColumn.label!propertyColumn.property}',
            name: '${propertyColumn.property}',
            readOnly:true,
			code:'请补充完整'
        }<#if propertyColumn_has_next>,</#if>
	  <#elseif propertyColumn.isAssociationType==true>
	  <#--if (propertyColumn.showModel!"")=='combobox'-->
	   {
	  		fieldLabel: '${propertyColumn.label!propertyColumn.column}',
		    name: '${propertyColumn.column}',
		    readOnly:true,
		    xtype:'combobox',
            typeAhead: true,
            triggerAction: 'all',
            queryMode: 'remote',
		    displayField: 'name',
		    valueField: 'id',
            store: Ext.create('Ext.data.Store', {
			    fields: ['id', 'name'], 
				proxy:{
				    type:'ajax',
				    url:Ext.ContextPath+'${propertyColumn.property}/query',
				    reader:{
				    	type:'json',
				    	totalProperty:'total',
				    	root:'root'
				    }
				}
			})
        }<#if propertyColumn_has_next>,</#if>
		<#else>
		 {
	        fieldLabel: '${propertyColumn.label!propertyColumn.property}',
	        //afterLabelTextTpl: Ext.required,
	        name: '${propertyColumn.property}',
	        readOnly:true,
	        xtype:'textfield',
	        allowBlank: false
	   	 }<#if propertyColumn_has_next>,</#if>
		<#--if-->
	  </#if>
	  </#list>   
	  ];   
	  
	  
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
				var grid=form.grid;//是在${simpleClassName}App.js中把引用授予的
				var modelName=grid.model||grid.getStore().getProxy( ).getModel().getName( );
				var model=Ext.createModel(modelName,{      	//id:''
				});
				model.phantom =true;
				form.getForm().loadRecord(model);//form是在app中定义的grid.form=form;
				
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
				
				form.createAction=false;
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

	
      me.addEvents("saved");
      me.callParent();
	},
	/**
	 * 重载父类的方法
	 */
	loadRecord:function(){
		var form=this;
		form.down("button#update").enable();
		form.down("button#destroy").enable();
		
		form.callParent(arguments);
	}
});
