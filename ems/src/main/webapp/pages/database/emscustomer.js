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
		        	data:[[0,'有效'],[1,'待定']]
		        }),
		        editable:false,
		        allowBlank:false,
		        value:0
	    	});
	    	Ext.D.status.ComboBox.superclass.initComponent.call(this);
	    }
});

Ext.define('Ext.D.ptype.ComboBox', {
	extend:'Ext.form.field.ComboBox',  
    alias:'widget.ptypebox',
    initComponent: function(){
    	Ext.apply(this, {
	        xtype: 'combo',
	        //name: 'duty',
	        name:'ptype',
	        displayField:'text',
	        valueField:'value',
	        store:Ext.create('Ext.data.ArrayStore',{
	        	fields:['value','text'],
	        	data:[[0,'普通'],[1,'低杆'],[2,'高杆']]
	        }),
	        editable:false,
	        allowBlank:false,
	        value:0
    	});
    	Ext.D.ptype.ComboBox.superclass.initComponent.call(this);
    }
});

Ext.onReady(function(){

	Ext.tip.QuickTipManager.init();
    Ext.grid.RowEditor.prototype.saveBtnText = "保存";
    Ext.grid.RowEditor.prototype.cancelBtnText = '取消';
    
	var cid=null,cname='';
	var contact_win=null;
	
	Ext.define('customer', {
	    extend: 'Ext.data.Model',
	    fields: [{name:'cid',mapping:'cid'},
	             {name:'cname',mapping:'cname'},
	             {name:'unitid',mapping:'unitid'},
	             {name:'cid',mapping:'cid'},
	             {name:'uname',mapping:'uname'},
	             {name:'status',mapping:'status'},
	             {name:'memo',mapping:'memo'},
	             {name:'ctype',mapping:'ctype',type: 'int'}]
	});
	
	var customerStore = new Ext.data.JsonStore({
		model: 'customer',
	    proxy: {
	        type: 'ajax',
	        url: Ext.ContextPath+'/dataManagr/getCustomerList.do',
	        reader: {
	            root: 'root',
	            idProperty: 'cid'
	        }
	    },
	    autoLoad:true,
	    listeners:{
		    load:function(s,records,success,o,e){
		    	if(success&&records.length>0){
		    		customer_grid.getSelectionModel( ).select(0);
		    	}
		    }
	    }
	});
	var customer_grid=Ext.create('Ext.grid.Panel', {
    	region:'center',
    	title:'客户信息',
	    store: customerStore,
	    columns: [
	        { header: 'cid',  dataIndex: 'cid',hidden:true,hideable:false},
	        { header: 'unitid',  dataIndex: 'unitid',hidden:true,hideable:false},
	        { header: '类型', dataIndex: 'ctype',width:50,renderer:ctypeRender},
	        { header: '客户名称', dataIndex: 'cname',flex:1,minWidth:100},
	        //{ header: '作业单位', dataIndex: 'uname',hidden:true},
	        { header: '状态', dataIndex: 'status', width:50, renderer:statusRender},
	        { header: '描述', dataIndex: 'memo',hidden:true }
	    ],
	    listeners:{
	    	select:function(row,r,i,e){
	    		if(r){
		    		if(!customer_form.isHidden()){
		    			customer_form.loadRecord(r);
		    			save_btn.setDisabled(true);
		    			edit_btn.setDisabled(false);
		    		}
		    		cid=r.get('cid');
		    		cname=r.get('cname');
		    		contactStore.load({params:{cid:cid}});
		    		poleStore.load({params:{cid:cid}});
	    		}
	    	}
	    },
	    tools:[{type:'refresh',tooltip:'刷新',handler:function(){customerStore.load()}},
	           {type:'plus',tooltip:'添加' ,handler:addcustomer},
	           {type:'minus',tooltip:'删除',handler:deletecustomer},
	           {type:'gear',tooltip:'编辑',handler:modifycustomer}]
	});
	
	function addcustomer(){
		//if(customer_form.isHidden()){
		//	customer_form.toggleCollapse();
		//}
		customer_form.expand();
		customer_grid.getSelectionModel( ).deselectAll();
		customer_form.getForm().setValues({cid:'',memo:'',cname:'',ctype:0,status:0});
		save_btn.setDisabled(false);
		edit_btn.setDisabled(true);
		
	}
	function deletecustomer(){
		var records=customer_grid.getSelectionModel( ).getSelection();
		if(records.length>0){
			if(contactStore.getCount()>0||poleStore.getCount()>0){
				Ext.MessageBoxEx('提示','该客户下存在联系人活员工信息,清先删除所述信息');
			}else{
				Ext.Msg.confirm('消息','确定要删除吗?',function(btn){
				if (btn == 'yes'){
					Ext.Ajax.request({
				        url : Ext.ContextPath+'/dataManagr/customerdelete.do',
				        method : 'post',
				        params:{cid:cid},
				        success : function(response, options) {
				        	Ext.MessageBoxEx('提示','删除成功');
				        	customerStore.remove(customer_grid.getSelectionModel( ).getLastSelected( ));
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
	function modifycustomer(){
		var records=customer_grid.getSelectionModel( ).getSelection( );
		if(records.length>0){
			//if(customer_form.isHidden()){
				customer_form.expand();
				customer_form.loadRecord(records[0]);
			//}
			save_btn.setDisabled(false);
			edit_btn.setDisabled(true);
		}else{
			Ext.MessageBoxEx('提示','请选择一条记录');
		}
	}
	
	function customersave(){
		var customerform= customer_form.getForm();
		var customer=customerform.getValues();
		var customer_id=customer.cid;
		if(!customer_id||customer_id==''){
			if(customerStore.find('cname',customer.cname)>0){
				Ext.MessageBoxEx('提示', "该客户名称已经存在,请换一个名称");
				return false;
			}
		}
		if(customerform.isDirty()){
			customerform.submit({
				    clientValidation: true,
				    url:  Ext.ContextPath+'/dataManagr/customersave.do',
				    success: function(form, action) {
				    	var customer=form.getValues();
				    	if(!customer.cid){
					    	customerStore.load({
					    		callback: function(records, operation, success) {
					               if(success&&records.length>0){
					            	   var obj=form.getValues();
					            	   var index= customerStore.find('cname',obj.cname);
					            	   if(index!=-1){
					            		   customer_grid.getSelectionModel( ).select(index); 
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
	var save_btn=Ext.create('Ext.button.Button',{text:'保存',iconCls:'icon-save',disabled:true,handler:customersave,
		listeners:{
			disable:function(b,e){
				var fields=customer_form.getForm().getFields( );
				fields.each(function(items){
					items.setReadOnly(true);
				});
			},
			enable:function(){
				var fields=customer_form.getForm().getFields( );
				fields.each(function(items){
					if(items.getName()=='cname'){
						var id=customer_form.getValues().cid;
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
	var edit_btn=Ext.create('Ext.button.Button',{text:'编辑',iconCls:'icon-edit',disabled:true,handler:modifycustomer});
	
	var customer_form = Ext.create('Ext.form.Panel', {
	    region: 'south',
	    height: 170,
	    split: true,
	    collapsible: true,
	    collapsed:true,
	    collapseMode: 'mini',
	    hideCollapseTool: true,
	    defaultType: 'textfield',
	    trackResetOnLoad:true,
	    fieldDefaults: {
	        labelWidth: 70,
	        anchor: '95%'
	    },
	    bodyPadding: '5px',
	    items: [{xtype: 'hidden',name: 'cid'},
			    {
			        xtype: 'combo',
			        fieldLabel: '类型',
			        name: 'ctype',
			        displayField:'text',
			        valueField:'value',
			        store:Ext.create('Ext.data.ArrayStore',{
			        	fields:['value','text'],
			        	data:[[0,'机关'],[1,'企业']]
			        }),
			        editable:false,
			        readOnly:true,
			        value:0
			    },
			    {fieldLabel: '客户名称',name: 'cname'},
			    //{fieldLabel: '作业单位',name: 'unitid',xtype:'listcombox',url:Ext.ContextPath+'/dataExtra/unitList.do',loadAuto:true},
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
	             {name:'cid',mapping:'cid'}]
	});
	
	var contactStore = new Ext.data.JsonStore({
		model: 'contact',
	    proxy: {
	        type: 'ajax',
	        url: Ext.ContextPath+'/dataManagr/getCustomerContactList.do',
	        reader: {
	            root: 'root'
	        }
	    }
	});
	    
	var contact_grid=Ext.create('Ext.grid.Panel', {
    	title:'客户联系方式',
    	height:150,
	    store: contactStore,
	    columns: [
	        { header: 'cid',  dataIndex: 'cid',hidden:true,hideable:false},
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
	    tools:[{type:'refresh',tooltip:'刷新',handler:function(){contactStore.load({params:{cid:cid}})}},
	           {type:'plus',tooltip:'添加' ,handler:addcontact},
	           {type:'minus',tooltip:'删除',handler:deletecontact},
	           {type:'gear',tooltip:'编辑',handler:modifycontact}]
	});
	
	function addcontact(){
			contact_win=getContactForm(contact_win);
			contact_win.setTitle('('+cname+')联系方式');
			var contact=contact_win.down('form').getForm();
			contact.setValues({cid:cid,serial:'',contact:'',phone:'',cellphone:'',title:'',email:'',fax:'',address:'',postcode:'',status:'',memo:''});
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
				        url : Ext.ContextPath+'/dataManagr/customerContactdelete.do',
				        method : 'post',
				        params:{cid:cid,serial:records[0].get('serial')},
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
			contact_win.setTitle('('+cname+')联系方式');
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
					items:[{xtype:'hidden',name:'serial'},{xtype:'hidden',name:'cid'},
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
				    url: Ext.ContextPath+'/dataManagr/customerContactsave.do',
				    success: function(form, action) {
				    	var contact=form.getValues();
				    	if(!contact.serial){
					    	contactStore.load({params:{cid:cid}});
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
	    
	Ext.define('pole', {
	    extend: 'Ext.data.Model',
	    fields: [{name:'pname',mapping:'pname'},
	             {name:'position',mapping:'position'},
	             {name:'posidesc',mapping:'posidesc'},
	             {name:'memo',mapping:'memo'},
	             {name:'status',mapping:'status'},
	             {name:'cpid',mapping:'cpid'},
	             {name:'ptype',mapping:'ptype'},
	             {name:'cid',mapping:'cid'}]
	});
	
	var poleStore = new Ext.data.JsonStore({
		model: 'pole',
	    proxy: {
	        type: 'ajax',
	        url: Ext.ContextPath+'/dataManagr/getPoleList.do',
	        reader: {
	            root: 'root'
	        }
	    }
	});
	
    var rowEditing = Ext.create('Ext.grid.plugin.RowEditing', {
    	cancelBtnText: '取消',
        saveBtnText: '保存',
    	clicksToMoveEditor:1,
        autoCancel: true,
        listeners:{
        	edit:function(editor,o,e){
        		var record=o.record;
        		var params=record.getData();
        		params.cid=cid;
        		Ext.Ajax.request({
				        url : Ext.ContextPath+'/dataManagr/polesave.do',
				        method : 'post',
				        params:params,
				        headers:{ 'Accept':'application/json;'},
				        success : function(response, options) {
				        	//Ext.MessageBoxEx('提示','删除成功');
				        	//contactStore.remove(contact_grid.getSelectionModel( ).getLastSelected( ));
				        	var obj=Ext.decode(response.responseText);
				        	record.set("cpid",obj.cpid);
				        	rowEditing.completeEdit( );
				        	record.commit();
				        },
				        failure : function(response) {
				        	var obj=Ext.decode(response.responseText);
				        	 Ext.Msg.alert('提示',obj.msg);
				        	 rowEditing.cancelEdit(); 
				        }
			  	});
			  	
//        		var colum=editor.column;
//                var form =colum.getEditor().up('form').getForm();
//        		if(form.isDirty()){
//        			form.submit({
//        				    clientValidation: true,
//        				    url: Ext.ContextPath+'/dataManagr/polesave.do',
//        				    success: function(form, action) {
//        				    	var ret=Ext.decode(action.response.responseText);
//        				    	if(ret.cpid){
//            				    	Ext.MessageBoxEx('提示','更新成功');
//            				    	form.setValues({cpid:ret.cpid});
//            				    	form.updateRecord(form.getRecord());
//            				    	rowEditing.completeEdit( )
//        				    	}else{
//        				    		Ext.MessageBoxEx('提示','数据更新异常'); 
//        				    		rowEditing.cancelEdit();
//        				    	}
//        				    },
//        				    failure: function(form, action) {
//        				        switch (action.failureType) {
//        				            case Ext.form.Action.CLIENT_INVALID:
//        							    //客户端数据验证失败的情况下，例如客户端验证邮件格式不正确的情况下提交表单  
//        							    Ext.MessageBoxEx('提示','数据错误，非法提交');  
//        				                break;
//        				            case Ext.form.Action.CONNECT_FAILURE:
//        							    //服务器指定的路径链接不上时  
//        							    Ext.MessageBoxEx('连接错误','指定路径连接错误!'); 
//        				                break;
//        				            case Ext.form.Action.SERVER_INVALID:
//        				            	//服务器端你自己返回success为false时  
//        							     Ext.MessageBoxEx('友情提示', "数据更新异常");
//        							     break;
//        							default:
//        								 //其它类型的错误  
//        	                             Ext.MessageBoxEx('警告', '服务器数据传输失败：'+action.response.responseText); 
//        							     break;
//        				       }
//        				       rowEditing.cancelEdit(); 
//        				    }
//        				});
//        		}else{
//        			rowEditing.cancelEdit();
//        		}
        	},
        	canceledit:function(grid,e){
        		var record=grid.record;
        		if(!record.get('cpid')){
        			poleStore.remove(record);
        		}
        	}
        }
    });
	    
	var pole_grid=Ext.create('Ext.grid.Panel', {
		title:'客户杆位',
	    flex:1,
	    margin:'2px 0 0 0',
	    store: poleStore,
	    columns: [
	    		{xtype: 'rownumberer'},
	            { header: 'cpid',  dataIndex: 'cpid',hidden:true,hideable:false,editor:{xtype:'hidden'}},
	  	        { header: 'cid',  dataIndex: 'cid',hidden:true,hideable:false,editor:{xtype:'hidden'}},
		        { header: '杆位名称', dataIndex: 'pname',width:150,editor:{allowBlank:false}},
		        { header: '类型', dataIndex: 'ptype',width:70,renderer:dutyRender,editor:{allowBlank:false,xtype:'ptypebox'}},
		        { header: '位置', dataIndex: 'position',width:200,editor:{allowBlank:false}},
		        { header: '位置描述', dataIndex: 'posidesc',width:200,editor:{allowBlank:false}},
		        { header: '状态', dataIndex: 'status', width:50, renderer:statusRender,editor:{allowBlank:false,xtype:'statusbox'}},
		        { header: '描述', dataIndex: 'memo',flex:1,editor: 'textfield'}
	    ],
	    plugins: [rowEditing],
	    tools:[{type:'refresh',tooltip:'刷新',handler:function(){poleStore.load({params:{cid:cid}})}},
	           {type:'plus',tooltip:'添加' ,handler:addpole},
	           {type:'minus',tooltip:'删除',handler:deletepole},
	           {type:'gear',tooltip:'编辑',handler:modifypole}],
	    listeners:{
	    	deselect:function(row,record,i,o){
	    		if(!record.get('cpid')){
	    			poleStore.remove(record);
	    		}
	    	}
	    }
	});
	function addpole(){
        rowEditing.cancelEdit();
        // Create a model instance
        var r = Ext.create('pole', {
        	pname:'',
        	ptype:'',
        	position:'111.00,111.00',
        	posidesc:'',
    	    memo:'',
    	    status:0,
    	    cpid:'',
    	    cid:cid
        });

        poleStore.insert(0, r);
        rowEditing.startEdit(0, 0);
		
	}
	function deletepole(){
		var records=pole_grid.getSelectionModel().getSelection();
		if(records.length>0){
  		   	 Ext.Ajax.request({
			        url : Ext.ContextPath+'/dataManagr/poledelete.do',
			        method : 'post',
			        params:{cpid:records[0].get('cpid')},
			        success : function(response, options) {
			        	Ext.MessageBoxEx('提示','删除成功');
			        	poleStore.remove(pole_grid.getSelectionModel( ).getLastSelected( ));
			        },
			        failure : function() {
			        	 Ext.Msg.alert('提示','数据加载失败');
			        }
		  	 });
		}else{
			Ext.MessageBoxEx('消息', "请选择一条记录");
		}
	}
	function modifypole(){
		var records=pole_grid.getSelectionModel().getSelection();
		if(records.length>0){
			 rowEditing.startEdit(records[0], 0);
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
        	items:[customer_grid,customer_form]
        },{
        	region:'center',
        	border:false,
            layout:{
            	type: 'vbox',
                align: 'stretch'
            },
            items:[contact_grid,pole_grid]
        }]
	});
	

});
	function ctypeRender(v){switch(v){case 0:return '机关';case 1:return '企业';default:return v}};
	function statusRender(v){switch(v){case 0:return '有效';default:return '待定'}};
	function dutyRender(v){switch(v){case 1:return '低杆';case 2:return '高杆';default:return '普通  '}};