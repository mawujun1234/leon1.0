Ext.define('Ext.D.status.ComboBox', {
		extend:'Ext.form.field.ComboBox',  
	    alias:'widget.statusbox',
	    initComponent: function(){
	    	Ext.apply(this, {
		        xtype: 'combo',
		        name: 'status',
		        displayField:'text',
		        valueField:'value',
		        store:Ext.create('Ext.data.ArrayStore',{
		        	fields:['value','text'],
		        	data:[[0,'可用'],[1,'待定']]
		        }),
		        editable:false,
		        allowBlank:false,
		        value:0
	    	});
	    	Ext.D.status.ComboBox.superclass.initComponent.call(this);
	    }
});

Ext.onReady(function(){

	Ext.tip.QuickTipManager.init();
    Ext.grid.RowEditor.prototype.saveBtnText = "保存";
    Ext.grid.RowEditor.prototype.cancelBtnText = '取消';
    
	var sid=null,sname='';
	var contact_win=null;
	
	Ext.define('supplier', {
	    extend: 'Ext.data.Model',
	    fields: [{name:'abbr',mapping:'abbr'},
	             {name:'memo',mapping:'memo'},
	             {name:'sid',mapping:'sid'},
	             {name:'sname',mapping:'sname'},
	             {name:'status',mapping:'status'},
	             {name:'web',mapping:'web'}]
	});
	
	var supplierStore = new Ext.data.JsonStore({
		model: 'supplier',
	    proxy: {
	        type: 'ajax',
	        url: Ext.ContextPath+'/dataManagr/getSupplierList.do',
	        reader: {
	            root: 'root',
	            idProperty: 'sid'
	        }
	    },
	    autoLoad:true,
	    listeners:{
		    load:function(s,records,success,o,e){
		    	if(success&&records.length>0){
		    		supplier_grid.getSelectionModel( ).select(0);
		    	}
		    }
	    }
	});
	    
	var supplier_grid=Ext.create('Ext.grid.Panel', {
    	region:'center',
    	title:'供应商',
	    store: supplierStore,
	    columns: [
	        { header: 'sid',  dataIndex: 'sid',hidden:true,hideable:false},
	        { header: '公司缩写', dataIndex: 'abbr',width:60},
	        { header: '供应商名称', dataIndex: 'sname',flex:1,minWidth:100},
	        { header: '状态', dataIndex: 'status', width:50, renderer:statusRender},
	        { header: '网址', dataIndex: 'web',hidden:true},
	        { header: '描述', dataIndex: 'memo',hidden:true }
	    ],
	    listeners:{
	    	select:function(row,r,i,e){
	    		if(r){
		    		if(!supplier_form.isHidden()){
		    			supplier_form.loadRecord(r);
		    			save_btn.setDisabled(true);
		    			edit_btn.setDisabled(false);
		    		}
		    		sid=r.get('sid');
		    		sname=r.get('sname');
		    		contactStore.load({params:{sid:sid}});
		    		productStore.load({params:{sid:sid}});
	    		}
	    	}
	    },
	    tools:[{type:'refresh',tooltip:'刷新',handler:function(){supplierStore.load()}},
	           {type:'plus',tooltip:'添加' ,handler:addsupplier},
	           {type:'minus',tooltip:'删除',handler:deletesupplier},
	           {type:'gear',tooltip:'编辑',handler:modifysupplier}]
	});
	
	function addsupplier(){
		//if(supplier_form.isHidden()){
		//	supplier_form.toggleCollapse();
		//}
		supplier_form.expand();
		supplier_grid.getSelectionModel( ).deselectAll();
		supplier_form.getForm().setValues({sid:'',abbr:'',sname:'',web:'',status:0,memo:''});
		save_btn.setDisabled(false);
		edit_btn.setDisabled(true);
		
	}
	function deletesupplier(){
		var records=supplier_grid.getSelectionModel( ).getSelection();
		if(records.length>0){
			if(contactStore.getCount()>0||productStore.getCount()>0){
				Ext.MessageBoxEx('提示','该供应商下存在联系人活员工信息,清先删除所述信息');
			}else{
				Ext.Msg.confirm('消息','确定要删除吗?',function(btn){
				if (btn == 'yes'){
					 Ext.Ajax.request({
				        url : Ext.ContextPath+'/dataManagr/supplierdelete.do',
				        method : 'post',
				        params:{sid:sid},
				        success : function(response, options) {
				        	Ext.MessageBoxEx('提示','删除成功');
				        	supplierStore.remove(supplier_grid.getSelectionModel( ).getLastSelected( ));
				        },
				        failure : function() {
				        	 Ext.Msg.alert('提示','数据加载失败');
				        }
			  		});
				}
				});
	   		   	
			}
		}else{
			Ext.MessageBoxEx('提示','请选择一条记录');
		}
		
	}
	function modifysupplier(){
		var records=supplier_grid.getSelectionModel( ).getSelection( );
		if(records.length>0){
			//if(supplier_form.isHidden()){
				supplier_form.expand();
				supplier_form.loadRecord(records[0]);
			//}
			save_btn.setDisabled(false);
			edit_btn.setDisabled(true);
		}else{
			Ext.MessageBoxEx('提示','请选择一条记录');
		}
	}
	
	function suppliersave(){
		var supplierform= supplier_form.getForm();
		var supplier=supplierform.getValues();
		var supplier_id=supplier.sid;
		if(!supplier_id||supplier_id==''){
			if(supplierStore.find('sname',supplier.sname)>0){
				Ext.MessageBoxEx('提示', "该供应商名称已经存在,请换一个名称");
				return false;
			}
		}
		if(supplierform.isDirty()){
			supplierform.submit({
				    clientValidation: true,
				    url: Ext.ContextPath+'/dataManagr/suppliersave.do',
				    success: function(form, action) {
				    	var supplier=form.getValues();
				    	if(!supplier.sid){
					    	supplierStore.load({
					    		callback: function(records, operation, success) {
					               if(success&&records.length>0){
					            	   var obj=form.getValues();
					            	   var index= supplierStore.find('sname',obj.sname);
					            	   if(index!=-1){
					            		   supplier_grid.getSelectionModel( ).select(index); 
					            	   }
					               }
						        }
							});
				    	}else{
				    		form.updateRecord(form.getRecord());
				    	}
				    	Ext.MessageBoxEx('提示', "数据保存成功");
				    },
				    failure: function(form, action) {
				        switch (action.failureType) {
				            case Ext.form.Action.CLIENT_INVALID:
							    //客户端数据验证失败的情况下，例如客户端验证邮件格式不正确的情况下提交表单  
							    Ext.MessageBoxEx('提示','数据错误，非法提交');  
				                break;
				            case Ext.form.Action.CONNECT_FAILURE:
							    //服务器指定的路径链接不上时  
							    Ext.MessageBoxEx('连接错误','指定路径连接错误!'); 
				                break;
				            case Ext.form.Action.SERVER_INVALID:
				            	//服务器端你自己返回success为false时  
							     Ext.MessageBoxEx('友情提示', "数据更新异常");
							     break;
							default:
								 //其它类型的错误  
	                             Ext.MessageBoxEx('警告', '服务器数据传输失败：'+action.response.responseText); 
							     break;
				       }
				    }
				});
		}
	}
	var save_btn=Ext.create('Ext.button.Button',{text:'保存',iconCls:'icon-save',disabled:true,handler:suppliersave,
		listeners:{
			disable:function(b,e){
				var fields=supplier_form.getForm().getFields( );
				fields.each(function(items){
					items.setReadOnly(true);
				});
			},
			enable:function(){
				var fields=supplier_form.getForm().getFields( );
				fields.each(function(items){
					if(items.getName()=='sname'){
						var id=supplier_form.getValues().sid;
						if(id&&id!=''){
							items.setReadOnly(true);
						}else{
							items.setReadOnly(false);
						}
					}else{
						items.setReadOnly(false);
					}
				});
			}
		}
	});
	var edit_btn=Ext.create('Ext.button.Button',{text:'编辑',iconCls:'icon-edit',disabled:true,handler:modifysupplier});
	
	var supplier_form = Ext.create('Ext.form.Panel', {
	    region: 'south',
	    height: 170,
	    split: true,
	    collapsible: true,
	    collapsed:true,
	    collapseMode: 'mini',
	    hideCollapseTool: true,
	    autoScroll:true,
	    defaultType: 'textfield',
	    trackResetOnLoad:true,
	    fieldDefaults: {
	        labelWidth: 70,
	        anchor: '95%'
	    },
	    bodyPadding: '5px',
	    items: [{xtype: 'hidden',name: 'sid'},
			    {fieldLabel: '供应商缩写',name: 'abbr'},
			    {fieldLabel: '供应商名称',name: 'sname'},
			    {fieldLabel: '网址',name: 'web'},
			    {xtype:'statusbox',fieldLabel:'状态',readOnly:true},
			    {fieldLabel: '描述',name: 'memo',readOnly:true}],
		bbar:['->',save_btn,edit_btn]
	});
	
	Ext.define('contact', {
	    extend: 'Ext.data.Model',
	    fields: [{name:'address',mapping:'address'},
	             {name:'cellphone',mapping:'cellphone'},
	             {name:'contact',mapping:'contact'},
	             {name:'email',mapping:'email'},
	             {name:'fax',mapping:'fax'},
	             {name:'memo',mapping:'memo'},
	             {name:'phone',mapping:'phone'},
	             {name:'postcode',mapping:'postcode'},
	             {name:'serial',mapping:'serial'},
	             {name:'status',mapping:'status'},
	             {name:'title',mapping:'title'},
	             {name:'sid',mapping:'sid'}]
	});
	
	var contactStore = new Ext.data.JsonStore({
		model: 'contact',
	    proxy: {
	        type: 'ajax',
	        url: Ext.ContextPath+'/dataManagr/getSupplierContactList.do',
	        reader: {
	            root: 'root'
	        }
	    }
	});
	    
	var contact_grid=Ext.create('Ext.grid.Panel', {
    	title:'供应商联系方式',
    	height:150,
	    store: contactStore,
	    columns: [
	        { header: 'sid',  dataIndex: 'sid',hidden:true,hideable:false},
	        { header: '序号', dataIndex: 'serial',width:45},
	        { header: '联系人', dataIndex: 'contact',width:70},
	        { header: '电话', dataIndex: 'phone',width:85},
	        { header: '移动电话', dataIndex: 'cellphone',width:85},
	        { header: '职务', dataIndex: 'title',width:70},
	        { header: '邮箱', dataIndex: 'email',width:85},
	        { header: '传真', dataIndex: 'fax',width:95},
	        { header: '联系地址', dataIndex: 'address',flex:1},
	        { header: '邮政编码', dataIndex: 'postcode',width:55},
	        { header: '状态', dataIndex: 'status', width:45, renderer:statusRender,width:50},
	        { header: '描述', dataIndex: 'memo'}
	    ],
	    tools:[{type:'refresh',tooltip:'刷新',handler:function(){contactStore.load({params:{sid:sid}})}},
	           {type:'plus',tooltip:'添加' ,handler:addcontact},
	           {type:'minus',tooltip:'删除',handler:deletecontact},
	           {type:'gear',tooltip:'编辑',handler:modifycontact}]
	});
	
	function addcontact(){
			contact_win=getContactForm(contact_win);
			contact_win.setTitle('('+sname+')联系方式');
			var contact=contact_win.down('form').getForm();
			contact.setValues({sid:sid,serial:'',contact:'',phone:'',cellphone:'',title:'',email:'',fax:'',address:'',postcode:'',status:'',memo:''});
//			var fields=contact.getFields( );
//			fields.each(function(items){
//				items.setReadOnly(false);
//			});
			contact.clearInvalid( );
			contact_win.show();
	}
	function deletecontact(){
		var records=contact_grid.getSelectionModel( ).getSelection();
		if(records.length>0){
			Ext.Msg.confirm('消息','确定要删除吗?',function(btn){
				if (btn == 'yes'){
					Ext.Ajax.request({
				        url : Ext.ContextPath+'/dataManagr/supplierContactdelete.do',
				        method : 'post',
				        params:{sid:sid,serial:records[0].get('serial')},
				        success : function(response, options) {
				        	Ext.MessageBoxEx('提示','删除成功');
				        	contactStore.remove(contact_grid.getSelectionModel( ).getLastSelected( ));
				        },
				        failure : function() {
				        	 Ext.Msg.alert('提示','数据加载失败');
				        }
			  	 });
				}
			});
   		   	 
		}else{
			Ext.MessageBoxEx('提示','请选择一条记录');
		}	
	}
	function modifycontact(){
		var records=contact_grid.getSelectionModel().getSelection();
		if(records.length>0){
			contact_win=getContactForm(contact_win);
			contact_win.setTitle('('+sname+')联系方式');
			var contact=contact_win.down('form').getForm();
			contact.loadRecord(records[0]);
//			var fields=contact.getFields( );
//			fields.each(function(items){
//				items.setReadOnly(false);
//			});
			contact_win.show();
		}else{
			Ext.MessageBoxEx('提示','请选择一个已存在的联系人');
		}
	}
	
	function getContactForm(win){
		if(!win){
			win=Ext.create('Ext.window.Window',{
				height:250,
				width:465,
				layout:'fit',
				closeAction:'hide',
				items:{
					xtype:'form',
					border:false,
					defaultType:'textfield',
					padding:'5px',
					style:'background-color:#FFFFFF',
					defaults:{labelWidth:55},
					trackResetOnLoad:true,
					items:[{xtype:'hidden',name:'serial'},{xtype:'hidden',name:'sid'},
					       {xtype:'columnbox',defaults:{defaultType:'textfield',defaults:{labelWidth:55}},items:[{items:[{fieldLabel:'联系人',name:'contact',allowBlank:false},{fieldLabel:'手机',name:'cellphone'},{fieldLabel:'电子邮件',name:'email',vtype:'email'}]},
					                                 {items:[{fieldLabel:'职务',name:'title',allowBlank:false},{fieldLabel:'电话',name:'phone',allowBlank:false},{fieldLabel:'传真',name:'fax'}]}]},
					       {fieldLabel:'联系地址',name:'address',anchor:'98%'},
					       {xtype:'columnbox',defaults:{defaultType:'textfield',defaults:{labelWidth:55}},items:[{items:{fieldLabel:'邮编',name:'postcode',maxLength:6,minLength:6,xtype:'numberfield'}},
					                                                                                             {items:{xtype:'statusbox',fieldLabel:'状态',readOnly:false}}]},
					       {fieldLabel:'描述',name:'memo',anchor:'98%'}]
				},
				buttons:[{text:'关闭',handler:function(){
					this.up('window').close();
				}},{text:'保存',handler:contactsave}]
			});
		}
		return win;
	}
	function contactsave(){
		var form=contact_win.down('form').getForm();
		if(form.isDirty()){
			form.submit({
				    clientValidation: true,
				    url: Ext.ContextPath+'/dataManagr/supplierContactsave.do',
				    success: function(form, action) {
				    	var contact=form.getValues();
				    	if(!contact.serial){
					    	contactStore.load({params:{sid:sid}});
				    	}else{
				    		form.updateRecord(form.getRecord());
				    	}
				    	Ext.MessageBoxEx('提示', "数据保存成功");
				    	contact_win.close();
				    },
				    failure: function(form, action) {
				        switch (action.failureType) {
				            case Ext.form.Action.CLIENT_INVALID:
							    //客户端数据验证失败的情况下，例如客户端验证邮件格式不正确的情况下提交表单  
							    Ext.MessageBoxEx('提示','数据错误，非法提交');  
				                break;
				            case Ext.form.Action.CONNECT_FAILURE:
							    //服务器指定的路径链接不上时  
							    Ext.MessageBoxEx('连接错误','指定路径连接错误!'); 
				                break;
				            case Ext.form.Action.SERVER_INVALID:
				            	//服务器端你自己返回success为false时  
							     Ext.MessageBoxEx('友情提示', "数据更新异常");
							     break;
							default:
								 //其它类型的错误  
	                             Ext.MessageBoxEx('警告', '服务器数据传输失败：'+action.response.responseText); 
							     break;
				       }
				    }
				});
		}else{
			Ext.MessageBoxEx('友情提示', "没有数据被改变");
		}
	}
	
	var add_btn=Ext.create('Ext.button.Button',{text:'保存',iconCls:'icon-save',disabled:true,handler:productsave,
		listeners:{
			disable:function(b,e){
				product_form.setFieldsReadOnly(true);
			},
			enable:function(){
				product_form.setFieldsReadOnly(false);
			}
		}
	});
	
	function productsave(){
        var form =product_form.getForm();
		if(form.isDirty()){
			form.submit({
				    clientValidation: true,
				    url: Ext.ContextPath+'/dataManagr/productsave.do',
				    success: function(form, action) {
				    	var ret=Ext.decode(action.response.responseText);
				    	if(ret.save){
				    		var formObj=form.getValues();
				    		var product=Ext.create('product',{
				    			sid:sid,
				    		    etype:formObj.etype,
				    		    esubtype:formObj.esubtype,
				    		    price:formObj.price,
				    		    typename:product_form.getComponent('etype-field').getRawValue(),
				    		    subtypename:product_form.getComponent('esubtype-field').getRawValue()
				    		})
				    		productStore.insert(0,product);
				    		product_grid.getSelectionModel().select(0);
				    	}else{
				    		form.updateRecord(form.getRecord());
				    		add_btn.setDisabled(true);
				    	}
				    	Ext.MessageBoxEx('提示','更新成功');
				    },
				    failure: function(form, action) {
				        switch (action.failureType) {
				            case Ext.form.Action.CLIENT_INVALID:
							    //客户端数据验证失败的情况下，例如客户端验证邮件格式不正确的情况下提交表单  
							    Ext.MessageBoxEx('提示','数据错误，非法提交');  
				                break;
				            case Ext.form.Action.CONNECT_FAILURE:
							    //服务器指定的路径链接不上时  
							    Ext.MessageBoxEx('连接错误','指定路径连接错误!'); 
				                break;
				            case Ext.form.Action.SERVER_INVALID:
				            	//服务器端你自己返回success为false时  
							     Ext.MessageBoxEx('友情提示', "数据更新异常");
							     break;
							default:
								 //其它类型的错误  
	                             Ext.MessageBoxEx('警告', '服务器数据传输失败：'+action.response.responseText); 
							     break;
				       } 
				    }
				});
		}else{
			Ext.MessageBoxEx('友情提示', "数据没有任何修改");
		}
	}
	var modify_btn=Ext.create('Ext.button.Button',{text:'编辑',iconCls:'icon-edit',disabled:true,handler:modifyproduct});
	
	var product_form=Ext.create('Ext.form.Panel',{
		width:350,
		border:false,
		padding:'20px',
		defaults:{anchor:'100%'},
		items:[{xtype:'hidden',name:'sid'},
		       {xtype:'listcombox',name:'etype',id:'etype-field',cascadeId:'esubtype-field',url:Ext.ContextPath+'/dataExtra/etypeList.do',fieldLabel:'设备类型',loadAuto:true,editable:false,readOnly:true,allowBlank:false},
		       {xtype:'listcombox',name:'esubtype',id:'esubtype-field',url:Ext.ContextPath+'/dataExtra/esubtypeList.do',fieldLabel:'设备子类型',editable:false,readOnly:true,allowBlank:false},
		       {xtype:'textfield',name:'price',fieldLabel:'价格',readOnly:true}],
		setFieldsReadOnly:function(readOnly){
			var fields= this.getForm().getFields();
			fields.each(function(items){
				if(items.getXType()!='hidden')
					items.setReadOnly(readOnly);
			});
		},
		buttons:[add_btn,modify_btn]
	});
	    
	Ext.define('product', {
	    extend: 'Ext.data.Model',
	    fields: [{name:'sid',mapping:'sid'},
	             {name:'etype',mapping:'etype'},
	             {name:'esubtype',mapping:'esubtype'},
	             {name:'price',mapping:'price'},
	             {name:'typename',mapping:'typename'},
	             {name:'subtypename',mapping:'subtypename'}]
	});
	
	var productStore = new Ext.data.JsonStore({
		model: 'product',
	    proxy: {
	        type: 'ajax',
	        url: Ext.ContextPath+'/dataManagr/getProductList.do',
	        reader: {
	            root: 'root'
	        }
	    }
	});
	    
	var product_grid=Ext.create('Ext.grid.Panel', {
	    flex:1,
	    autoHeight:true,
	    store: productStore,
	    columns: [
	            { header: 'sid',  dataIndex: 'sid',hidden:true,hideable:false},
	  	        { header: 'etype',  dataIndex: 'etype',hidden:true,hideable:false},
	  	        { header: 'esubtype',  dataIndex: 'esubtype',hidden:true,hideable:false},
		        { header: '设备类型', dataIndex: 'typename',flex:1},
		        { header: '子设备类型', dataIndex: 'subtypename',flex:1},
		        { header: '价格', dataIndex: 'price'}
	    ],
	    listeners:{
	    	select:function(row,r,i,e){
	    		if(r){
	    			product_form.getForm().loadRecord(r);
	    			modify_btn.setDisabled(false);
	    			add_btn.setDisabled(true);
	    		}
	    	},
	    	deselect:function(row,r,i,e){
	    		modify_btn.setDisabled(false);
	    	}
	    }
	});
	function addproduct(){
		product_grid.getSelectionModel().deselectAll();
		modify_btn.setDisabled(true);
		add_btn.setDisabled(false);
		product_form.getForm().reset();
		product_form.getForm().setValues({sid:sid});
	}
	function deleteproduct(){
		var records=product_grid.getSelectionModel().getSelection();
		if(records.length>0){
			Ext.Msg.confirm('消息','确定要删除吗?',function(btn){
				if (btn == 'yes'){
					Ext.Ajax.request({
				        url : Ext.ContextPath+'/dataManagr/productdelete.do',
				        method : 'post',
				        params:{sid:records[0].get('sid'),etype:records[0].get('etype'),esubtype:records[0].get('esubtype')},
				        success : function(response, options) {
				        	Ext.MessageBoxEx('提示','删除成功');
				        	productStore.remove(product_grid.getSelectionModel( ).getLastSelected( ));
				        	product_form.getForm().reset();
				        },
				        failure : function() {
				        	 Ext.Msg.alert('提示','数据加载失败');
				        }
			  	 });
				}
			});
  		   	
		}else{
			Ext.MessageBoxEx('消息', "请选择一条记录");
		}
	}
	function modifyproduct(){
		var records=product_grid.getSelectionModel().getSelection();
		if(records.length>0){
			add_btn.setDisabled(false);
		}else{
			Ext.MessageBoxEx('消息', "请选择一条记录");
		}
	}
	
	Ext.create('Ext.container.Viewport',{
        layout:'border',
        padding:'5px',
        renderTo:'view-port',
        style:'background-color:#FFFFFF',
        items:[{
        	layout:'border',
        	region:'west',
        	width: 250,
        	split:true,
        	border:false,
        	items:[supplier_grid,supplier_form]
        },{
        	region:'center',
        	border:false,
            layout:{
            	type: 'vbox',
                align: 'stretch'
            },
            items:[contact_grid,
                   {
            			title:'客户产品',
            			flex:1,
		            	border:false,
		            	margin:'2px 0 0 0',
		            	layout:{type:'hbox',align:'stretch'},
		            	items:[product_grid,product_form],
		        	    tools:[{type:'refresh',tooltip:'刷新',handler:function(){productStore.load({params:{sid:sid}});product_form.getForm().reset();product_form.getForm().setValues({sid:sid});add_btn.setDisabled(true)}},
		        	           {type:'plus',tooltip:'添加' ,handler:addproduct},
		        	           {type:'minus',tooltip:'删除',handler:deleteproduct},
		        	           {type:'gear',tooltip:'编辑',handler:modifyproduct}]
	            	}]
        }]
	});
	
	function statusRender(v){switch(v){case 0:return '有效';default:return '其他'}};

});