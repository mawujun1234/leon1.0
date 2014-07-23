


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

Ext.define('Ext.D.duty.ComboBox', {
	extend:'Ext.form.field.ComboBox',  
    alias:'widget.dutybox',
    initComponent: function(){
    	Ext.apply(this, {
	        xtype: 'combo',
	        name: 'duty',
	        displayField:'text',
	        valueField:'value',
	        store:Ext.create('Ext.data.ArrayStore',{
	        	fields:['value','text'],
	        	data:[[0,'普通'],[1,'管理']]
	        }),
	        editable:false,
	        allowBlank:false,
	        value:0
    	});
    	Ext.D.duty.ComboBox.superclass.initComponent.call(this);
    }
});

Ext.onReady(function(){

	Ext.tip.QuickTipManager.init();
    Ext.grid.RowEditor.prototype.saveBtnText = "保存";
    Ext.grid.RowEditor.prototype.cancelBtnText = '取消';
    
	var unitid=null,uname='';
	var contact_win=null;
	
	Ext.define('unit', {
	    extend: 'Ext.data.Model',
	    fields: [{name:'memo',mapping:'memo'},
	             {name:'status',mapping:'status'},
	             {name:'uname',mapping:'uname'},
	             {name:'unitid',mapping:'unitid'},
	             {name:'utype',mapping:'utype',type: 'int'}]
	});
	
	var unitStore = new Ext.data.JsonStore({
		model: 'unit',
	    proxy: {
	        type: 'ajax',
	        url: Ext.ContextPath+'/dataManagr/getUnitList.do',
	        reader: {
	            root: 'root',
	            idProperty: 'unitid'
	        }
	    },
	    autoLoad:true,
	    listeners:{
		    load:function(s,records,success,o,e){
		    	if(success&&records.length>0){
		    		unit_grid.getSelectionModel( ).select(0);
		    	}
		    }
	    }
	});
	    
	var unit_grid=Ext.create('Ext.grid.Panel', {
    	region:'center',
    	title:'业务单位',
	    store: unitStore,
	    columns: [
	        { header: 'unitid',  dataIndex: 'unitid',hidden:true,hideable:false},
	        { header: '类型', dataIndex: 'utype',width:50,renderer:utypeRender},
	        { header: '单位名称', dataIndex: 'uname',flex:1,minWidth:100},
	        { header: '状态', dataIndex: 'status', width:50, renderer:statusRender},
	        { header: '描述', dataIndex: 'memo',hidden:true }
	    ],
	    listeners:{
	    	select:function(row,r,i,e){
	    		if(r){
		    		if(!unit_form.isHidden()){
		    			unit_form.loadRecord(r);
		    			save_btn.setDisabled(true);
		    			edit_btn.setDisabled(false);
		    		}
		    		unitid=r.get('unitid');
		    		uname=r.get('uname');
		    		contactStore.load({params:{unitid:unitid}});
		    		clerkStore.load({params:{unitid:unitid}});
	    		}
	    	}
	    },
	    tools:[{type:'refresh',tooltip:'刷新',handler:function(){unitStore.load()}},
	           {type:'plus',tooltip:'添加' ,handler:addunit},
	           {type:'minus',tooltip:'删除',handler:deleteunit},
	           {type:'gear',tooltip:'编辑',handler:modifyunit}]
	});
	
	function addunit(){
		//if(unit_form.isHidden()){
		//	unit_form.toggleCollapse();
		//}
		unit_form.expand();
		unit_grid.getSelectionModel( ).deselectAll();
		unit_form.getForm().setValues({unitid:'',memo:'',uname:'',utype:0,status:0});
		save_btn.setDisabled(false);
		edit_btn.setDisabled(true);
		
	}
	function deleteunit(){
		var records=unit_grid.getSelectionModel( ).getSelection();
		if(records.length>0){
			if(contactStore.getCount()>0||clerkStore.getCount()>0){
				Ext.MessageBoxEx('提示','该单位下存在联系人活员工信息,清先删除所述信息');
			}else{
				Ext.Msg.confirm('消息','确定要删除吗?',function(btn){
				if (btn == 'yes'){
					Ext.Ajax.request({
				        url : Ext.ContextPath+'/dataManagr/unitdelete.do',
				        method : 'post',
				        params:{unitid:unitid},
				        success : function(response, options) {
				        	Ext.MessageBoxEx('提示','删除成功');
				        	unitStore.remove(unit_grid.getSelectionModel( ).getLastSelected( ));
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
	function modifyunit(){
		var records=unit_grid.getSelectionModel( ).getSelection( );
		if(records.length>0){
			//if(unit_form.isHidden()){
				unit_form.expand();
				unit_form.loadRecord(records[0]);
			//}
			save_btn.setDisabled(false);
			edit_btn.setDisabled(true);
		}else{
			Ext.MessageBoxEx('提示','请选择一条记录');
		}
	}
	
	function unitsave(){
		var unitform= unit_form.getForm();
		var unit=unitform.getValues();
		var unit_id=unit.unitid;
		if(!unit_id||unit_id==''){
			if(unitStore.find('uname',unit.uname)>0){
				Ext.MessageBoxEx('提示', "该单位名称已经存在,请换一个名称");
				return false;
			}
		}
		if(unitform.isDirty()){
			unitform.submit({
				    clientValidation: true,
				    url: Ext.ContextPath+'/dataManagr/unitsave.do',
				    success: function(form, action) {
				    	var unit=form.getValues();
				    	if(!unit.unitid){
					    	unitStore.load({
					    		callback: function(records, operation, success) {
					               if(success&&records.length>0){
					            	   var obj=form.getValues();
					            	   var index= unitStore.find('uname',obj.uname);
					            	   if(index!=-1){
					            		   unit_grid.getSelectionModel( ).select(index); 
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
	var save_btn=Ext.create('Ext.button.Button',{text:'保存',iconCls:'icon-save',disabled:true,handler:unitsave,
		listeners:{
			disable:function(b,e){
				var fields=unit_form.getForm().getFields( );
				fields.each(function(items){
					items.setReadOnly(true);
				});
			},
			enable:function(){
				var fields=unit_form.getForm().getFields( );
				fields.each(function(items){
					if(items.getName()=='uname'){
						var id=unit_form.getValues().unitid;
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
	var edit_btn=Ext.create('Ext.button.Button',{text:'编辑',iconCls:'icon-edit',disabled:true,handler:modifyunit});
	
	var unit_form = Ext.create('Ext.form.Panel', {
	    region: 'south',
	    height: 180,
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
	    items: [{xtype: 'hidden',name: 'unitid'},
			    {
			        xtype: 'combo',
			        fieldLabel: '类型',
			        name: 'utype',
			        displayField:'text',
			        valueField:'value',
			        store:Ext.create('Ext.data.ArrayStore',{
			        	fields:['value','text'],
			        	data:[[0,'维修'],[1,'库房']]
			        }),
			        editable:false,
			        readOnly:true,
			        value:0
			    },
			    {fieldLabel: '单位名称',name: 'uname'},
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
	             {name:'unitid',mapping:'unitid'}]
	});
	
	var contactStore = new Ext.data.JsonStore({
		model: 'contact',
	    proxy: {
	        type: 'ajax',
	        url: Ext.ContextPath+'/dataManagr/getUnitContactList.do',
	        reader: {
	            root: 'root'
	        }
	    }
	});
	    
	var contact_grid=Ext.create('Ext.grid.Panel', {
    	title:'单位联系方式',
    	height:150,
	    store: contactStore,
	    columns: [
	        { header: 'unitid',  dataIndex: 'unitid',hidden:true,hideable:false},
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
	    tools:[{type:'refresh',tooltip:'刷新',handler:function(){contactStore.load({params:{unitid:unitid}})}},
	           {type:'plus',tooltip:'添加' ,handler:addcontact},
	           {type:'minus',tooltip:'删除',handler:deletecontact},
	           {type:'gear',tooltip:'编辑',handler:modifycontact}]
	});
	
	function addcontact(){
			contact_win=getContactForm(contact_win);
			contact_win.setTitle('('+uname+')联系方式');
			var contact=contact_win.down('form').getForm();
			contact.setValues({unitid:unitid,serial:'',contact:'',phone:'',cellphone:'',title:'',email:'',fax:'',address:'',postcode:'',status:'',memo:''});
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
				        url : Ext.ContextPath+'/dataManagr/unitContactdelete.do',
				        method : 'post',
				        params:{unitid:unitid,serial:records[0].get('serial')},
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
			contact_win.setTitle('('+uname+')联系方式');
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
					items:[{xtype:'hidden',name:'serial'},{xtype:'hidden',name:'unitid'},
					       {xtype:'columnbox',defaults:{defaultType:'textfield',defaults:{labelWidth:55}},items:[{items:[{fieldLabel:'联系人',name:'contact',allowBlank:false},{fieldLabel:'手机',name:'cellphone',allowBlank:false},{fieldLabel:'电子邮件',name:'email',vtype:'email'}]},
					                                 {items:[{fieldLabel:'职务',name:'title',allowBlank:false},{fieldLabel:'电话',name:'phone'},{fieldLabel:'传真',name:'fax'}]}]},
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
				    url: Ext.ContextPath+'/dataManagr/unitContactsave.do',
				    success: function(form, action) {
				    	var contact=form.getValues();
				    	if(!contact.serial){
					    	contactStore.load({params:{unitid:unitid}});
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
	    
	Ext.define('clerk', {
	    extend: 'Ext.data.Model',
	    fields: [{name:'address',mapping:'address'},
	             {name:'duty',mapping:'duty'},
	             {name:'email',mapping:'email'},
	             {name:'memo',mapping:'memo'},
	             {name:'phone',mapping:'phone'},
	             {name:'postcode',mapping:'postcode'},
	             {name:'status',mapping:'status'},
	             {name:'ucid',mapping:'ucid'},
	             {name:'ucname',mapping:'ucname'},
	             {name:'unitid',mapping:'unitid'},
	             {name:'upwd',mapping:'upwd'}]
	});
	
	var clerkStore = new Ext.data.JsonStore({
		model: 'clerk',
	    proxy: {
	        type: 'ajax',
	        url: Ext.ContextPath+'/dataManagr/getClerkList.do',
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
        		params.unitid=unitid;
        		Ext.Ajax.request({
				        url : Ext.ContextPath+'/dataManagr/clerksave.do',
				        method : 'post',
				        params:params,
				        headers:{ 'Accept':'application/json;'},
				        success : function(response, options) {
				        	//Ext.MessageBoxEx('提示','删除成功');
				        	//contactStore.remove(contact_grid.getSelectionModel( ).getLastSelected( ));
				        	var obj=Ext.decode(response.responseText);
				        	record.set("ucid",obj.ucid);
				        	rowEditing.completeEdit( );
				        	record.commit();
				        },
				        failure : function(response) {
				        	var obj=Ext.decode(response.responseText);
				        	 Ext.Msg.alert('提示',obj.msg);
				        	 rowEditing.cancelEdit(); 
				        }
			  	});
        		
//        		var colum=o.column;
//                var form =colum.getEditor().up('form').getForm();//alert(unitid);
//                //form.setValues({unitid:unitid});
//        		if(form.isDirty()){
//        			form.submit({
//        				    //clientValidation: true,
//        				    url: Ext.ContextPath+'/dataManagr/clerksave.do',
//        				    success: function(form, action) {
//        				    	var ret=Ext.decode(action.response.responseText);
//        				    	if(ret.ucid){
//            				    	Ext.MessageBoxEx('提示','更新成功');
//            				    	form.setValues({ucid:ret.ucid});
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
        		if(!record.get('ucid')){
        			clerkStore.remove(record);
        		}
        	}
        }
    });
	    
	var clerk_grid=Ext.create('Ext.grid.Panel', {
		title:'员工信息',
	    flex:1,
	    margin:'2px 0 0 0',
	    store: clerkStore,
	    columns: [
	            { header: 'ucid',  dataIndex: 'ucid',hidden:true,hideable:false,editor:{xtype:'hidden'}},
	  	        { header: 'unitid',  dataIndex: 'unitid',hidden:true,hideable:false,editor:{xtype:'hidden'}},
		        { header: '名称', dataIndex: 'ucname',width:70,editor:{allowBlank:false}},
		        { header: '岗位类型', dataIndex: 'duty',width:70,renderer:dutyRender,editor:{allowBlank:false,xtype:'dutybox'}},
		        { header: '电话', dataIndex: 'phone',width:100,editor:{allowBlank:false}},
		        { header: '邮箱', dataIndex: 'email',width:100,editor: 'textfield'},
		        { header: '联系地址', dataIndex: 'address',flex:1,editor: 'textfield'},
		        { header: '邮政编码', dataIndex: 'postcode',width:55,editor:{maxLength:6,minLength:6,xtype:'numberfield',hideTrigger:true}},
		        { header: '状态', dataIndex: 'status', width:50, renderer:statusRender,editor:{allowBlank:false,xtype:'statusbox'}},
		        { header: '描述', dataIndex: 'memo',flex:1,editor: 'textfield'},
		        { header: 'upwd', dataIndex: 'upwd', hidden:true }
	    ],
	    plugins: [rowEditing],
	    tools:[{type:'refresh',tooltip:'刷新',handler:function(){clerkStore.load({params:{unitid:unitid}})}},
	           {type:'plus',tooltip:'添加' ,handler:addclerk},
	           {type:'minus',tooltip:'删除',handler:deleteclerk},
	           {type:'gear',tooltip:'编辑',handler:modifyclerk}],
	    listeners:{
	    	deselect:function(row,record,i,o){
	    		if(!record.get('ucid')){
	    			clerkStore.remove(record);
	    		}
	    	}
	    }
	});
	function addclerk(){
        rowEditing.cancelEdit();
        // Create a model instance
        var r = Ext.create('clerk', {
    	    address:'',
    	    duty:'',
    	    email:'',
    	    memo:'',
    	    phone:'',
    	    postcode:'',
    	    status:0,
    	    ucid:'',
    	    ucname:'',
    	    unitid:unitid,
    	    upwd:''
        });

        clerkStore.insert(0, r);
        rowEditing.startEdit(0, 0);
		
	}
	function deleteclerk(){
		var records=clerk_grid.getSelectionModel().getSelection();
		if(records.length>0){
			Ext.Msg.confirm('消息','确定要删除吗?',function(btn){
				if (btn == 'yes'){
					 Ext.Ajax.request({
				        url : Ext.ContextPath+'/dataManagr/clerkdelete.do',
				        
				        method : 'post',
				        params:{ucid:records[0].get('ucid')},
				        success : function(response, options) {
				        	Ext.MessageBoxEx('提示','删除成功');
				        	clerkStore.remove(clerk_grid.getSelectionModel( ).getLastSelected( ));
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
	function modifyclerk(){
		var records=clerk_grid.getSelectionModel().getSelection();
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
        	items:[unit_grid,unit_form]
        },{
        	region:'center',
        	border:false,
            layout:{
            	type: 'vbox',
                align: 'stretch'
            },
            items:[contact_grid,clerk_grid]
        }]
	});
	
	function utypeRender(v){switch(v){case 0:return '库房';case 1:return '维修';default:return v}};
	function statusRender(v){switch(v){case 0:return '有效';default:return '其他'}};
	function dutyRender(v){switch(v){case 1:return '管理';default:return '普通  '}};
});