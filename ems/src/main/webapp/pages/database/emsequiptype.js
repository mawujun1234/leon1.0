Ext.onReady(function(){

	Ext.tip.QuickTipManager.init();
    
	var etype=null,typename='';
	var etype_win=null;
	var esubType_win=null;
	
	Ext.define('etype', {
	    extend: 'Ext.data.Model',
	    fields: [{name:'etype',mapping:'etype'},
	             {name:'typename',mapping:'typename'}]
	});
	
	var etypeStore = new Ext.data.JsonStore({
		model: 'etype',
	    proxy: {
	        type: 'ajax',
	        url: Ext.ContextPath+'/dataManagr/getEtypeList.do',
	        reader: {
	            root: 'list',
	            idProperty: 'etype'
	        }
	    },
	    autoLoad:true,
	    listeners:{
		    load:function(s,records,success,o,e){
		    	if(success&&records.length>0){
		    		etype_grid.getSelectionModel( ).select(0);
		    	}
		    }
	    }
	});
	    
	var etype_grid=Ext.create('Ext.grid.Panel', {
		region:'west',
    	width: 250,
    	split:true,
    	title:'设备类型',
	    store: etypeStore,
	    columns: [
	        { header: 'etype',  dataIndex: 'etype',hidden:true,hideable:false},
	        Ext.create('Ext.grid.RowNumberer'),
	        { header: '设备类型', dataIndex: 'typename',flex:1}
	    ],
	    listeners:{
	    	select:function(row,r,i,e){
	    		if(r){
		    		etype=r.get('etype');
		    		typename=r.get('typename');
		    		if(etype&&etype!=''){
		    			esubTypeStore.load({params:{etype:etype}});
		    		}else{
		    			esubTypeStore.removeAll();
		    		}
	    		}
	    	}
	    },
	    tools:[{type:'refresh',tooltip:'刷新',handler:function(){etypeStore.load()}},
	           {type:'plus',tooltip:'添加' ,handler:addetype},
	           {type:'minus',tooltip:'删除',handler:deleteetype},
	           {type:'gear',tooltip:'编辑',handler:modifyetype}]
	});
	
	function addetype(){
		Ext.MessageBox.prompt('设备类型', '清输入新的设备类型名称:', function(btn,text){
			if(btn=='ok'){
				if(etypeStore.find('typename',text,0,false,false,true)==-1){
					var newEtype=Ext.create('etype',{
						etype:null,
						typename:text
					});
					etypeStore.insert(0,newEtype);
					etype_grid.getSelectionModel().select(0);
//					//etypeStore.sync();
//					Ext.Ajax.request({
//						        url : Ext.ContextPath+'/dataManagr/etypesave.do',
//						        method : 'post',
//						        params:{etype:etype,typename:text},
//						        success : function(response, options) {
//						        	var obj=Ext.decode(response.responseText);
//						        	var newEtype=Ext.create('etype',{
//										etype:obj.etype,
//										typename:text
//									});
//									etypeStore.insert(0,newEtype);
//									etype_grid.getSelectionModel().select(0);
//									esubTypeStore.load({params:{etype:obj.etype}});
//						        },
//						        failure : function() {
//						        	 Ext.Msg.alert('提示','新增失败');
//						        }
//					  });
				}else{
					Ext.Msg.alert('提示','该设备类型已经存在');
				}
			}
		});
	}
	function deleteetype(){
		var records=etype_grid.getSelectionModel( ).getSelection();
		if(records.length>0){
			if(esubTypeStore.getCount()>0){
				Ext.MessageBoxEx('提示','该设备类型下存在子类型,请先删除子类型！');
			}else{
				etypeStore.remove(etype_grid.getSelectionModel( ).getLastSelected( )); 
			}
		}else{
			Ext.MessageBoxEx('提示','请选择一条记录');
		}
		
	}
	function modifyetype(){
		Ext.MessageBox.prompt('设备类型', '清输入新的设备类型名称:', function(btn,text){
			if(btn=='ok'){
				if(etypeStore.find('typename',text,0,false,false,true)==-1){
					if(etype&&text&&text!=''){
			   		   	 Ext.Ajax.request({
						        url : Ext.ContextPath+'/dataManagr/typenameupdate.do',
						        method : 'post',
						        params:{etype:etype,typename:text},
						        success : function(response, options) {
						        	Ext.MessageBoxEx('提示','更新成功');
						        	var record =etype_grid.getSelectionModel( ).getLastSelected();
						        	record.set('typename',text);
						        	esubTypeStore.load({params:{etype:etype}});
						        },
						        failure : function() {
						        	 Ext.Msg.alert('提示','数据加载失败');
						        }
					  	 });
					}
				}else{
					Ext.Msg.alert('提示','该设备类型已经存在');
				}
			}
	    },this,false,typename);
	}
	
	Ext.define('esubType', {
	    extend: 'Ext.data.Model',
	    fields: [{name:'etype',mapping:'etype'},
	             {name:'typename',mapping:'typename'},
	             {name:'esubtype',mapping:'esubtype'},
	             {name:'subtypename',mapping:'subtypename'},
	             {name:'style',mapping:'style'},
	             {name:'memo',mapping:'memo'}]
	});
	
	var esubTypeStore = new Ext.data.JsonStore({
		model: 'esubType',
	    proxy: {
	        type: 'ajax',
	        url: Ext.ContextPath+'/dataManagr/getEtypeList.do',
	        reader: {
	            root: 'list'
	        }
	    }
	});
	    
	var esubType_grid=Ext.create('Ext.grid.Panel', {
    	title:'设备子类型',
    	region:'center',
	    store: esubTypeStore,
	    columns: [
	        { header: 'etype',  dataIndex: 'etype',hidden:true,hideable:false},
	        { header: 'esubtype',  dataIndex: 'esubtype',hidden:true,hideable:false},
	        { header: '设备类型', dataIndex: 'typename',flex:1},
	        { header: '设备子类型', dataIndex: 'subtypename',flex:1},
	        { header: '设备型号', dataIndex: 'style',flex:1},
	        { header: '描述', dataIndex: 'memo',flex:1}
	    ],
	    tools:[{type:'refresh',tooltip:'刷新',handler:function(){esubTypeStore.load({params:{etype:etype}})}},
	           {type:'plus',tooltip:'添加' ,handler:addesubType},
	           {type:'minus',tooltip:'删除',handler:deleteesubType},
	           {type:'gear',tooltip:'编辑',handler:modifyesubType}]
	});
	
	function addesubType(){
			esubType_win=getEsubtypeForm(esubType_win);
			esubType_win.setTitle('('+typename+')联系方式');
			var esubType=esubType_win.down('form').getForm();
			esubType.setValues({etype:etype,esubtype:'',style:'',memo:'',typename:typename,subtypename:''});
			esubType.clearInvalid( );
			esubType_win.show();
	}
	function deleteesubType(){
		var records=esubType_grid.getSelectionModel( ).getSelection();
		if(records.length>0){
			Ext.Msg.confirm('消息','确定要删除吗?',function(btn){
				if (btn == 'yes'){
					 Ext.Ajax.request({
				        url : Ext.ContextPath+'/dataManagr/etypedelete.do',
				        method : 'post',
				        params:{etype:etype,esubtype:records[0].get('esubtype')},
				        success : function(response, options) {
				        	Ext.MessageBoxEx('提示','删除成功');
				        	esubTypeStore.remove(esubType_grid.getSelectionModel( ).getLastSelected( ));
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
	function modifyesubType(){
		var records=esubType_grid.getSelectionModel().getSelection();
		if(records.length>0){
			esubType_win=getEsubtypeForm(esubType_win);
			esubType_win.setTitle('('+typename+')联系方式');
			var esubType=esubType_win.down('form').getForm();
			esubType.loadRecord(records[0]);
			esubType_win.show();
		}else{
			Ext.MessageBoxEx('提示','请选择一个已存在的联系人');
		}
	}
	
	function getEsubtypeForm(win){
		if(!win){
			win=Ext.create('Ext.window.Window',{
				height:180,
				width:250,
				layout:'fit',
				closeAction:'hide',
				modal:true,
				items:{
					xtype:'form',
					border:false,
					defaultType:'textfield',
					padding:'5px',
					style:'background-color:#FFFFFF',
					defaults:{labelWidth:70},
					trackResetOnLoad:true,
					items:[{xtype:'hidden',name:'etype'},{xtype:'hidden',name:'esubtype'},
					       {fieldLabel:'设备类型',name:'typename',anchor:'98%',readOnly:true},
					       {fieldLabel:'设备子类型',name:'subtypename',anchor:'98%'},
					       {fieldLabel:'设备型号',name:'style',anchor:'98%'},
					       {fieldLabel:'描述',name:'memo',anchor:'98%'}]
				},
				buttons:[{text:'关闭',handler:function(){
					this.up('window').close();
				}},{text:'保存',handler:esubTypesave}]
			});
		}
		return win;
	}
	function esubTypesave(){
		var form=esubType_win.down('form').getForm();
		if(form.isDirty()){
			form.submit({
				    clientValidation: true,
				    url: Ext.ContextPath+'/dataManagr/etypesave.do',
				    success: function(form, action) {
				    	var ret=Ext.decode(action.response.responseText);
				    	if(ret.save&&ret.etype){
				        	var record =etype_grid.getSelectionModel( ).getLastSelected();
				        	etype=ret.etype;
				        	record.set('etype',etype);
				        	esubTypeStore.load({params:{etype:etype}});
				    	}else if(ret.update){
				    		form.updateRecord(form.getRecord());
				    	}
				    	Ext.MessageBoxEx('提示', "数据更新成功");
				    	esubType_win.close();
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
	
	Ext.create('Ext.container.Viewport',{
        layout:'border',
        padding:'5px',
        renderTo:'view-port',
        style:'background-color:#FFFFFF',
        items:[etype_grid,esubType_grid]
	});

});