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
	    	//Ext.D.status.ComboBox.superclass.initComponent.call(this);
	    	this.callParent();
	    }
});

Ext.onReady(function(){
	
	var stid=null;
	
	Ext.define('stock', {
	    extend: 'Ext.data.Model',
	    fields: [{name:'stid',mapping:'stid'},
	             {name:'stock',mapping:'stock'},
	             {name:'unitid',mapping:'unitid'},
	             {name:'uname',mapping:'uname'},
	             {name:'status',mapping:'status'},
	             {name:'memo',mapping:'memo'}]
	});
	
	var stockStore = new Ext.data.JsonStore({
		model: 'stock',
	    proxy: {
	        type: 'ajax',
	        url: Ext.ContextPath+'/dataExtra/getStockList.do',
	        reader: {
	            root: 'root',
	            idProperty: 'stid'
	        }
	    },
	    autoLoad:true,
	    listeners:{
		    load:function(s,records,success,o,e){
		    	if(success&&records.length>0){
		    		stock_grid.getSelectionModel( ).select(0);
		    	}
		    }
	    }
	});
	    
	var stock_grid=Ext.create('Ext.grid.Panel', {
    	region:'center',
    	title:'库房信息',
    	flex:1,
	    store: stockStore,
	    columns: [
	        { header: 'stid',  dataIndex: 'stid',hidden:true,hideable:false},
	        { header: 'unitid',  dataIndex: 'unitid',hidden:true,hideable:false},
	        { header: '仓库名', dataIndex: 'stock',flex:1},
	        { header: '用户单位', dataIndex: 'uname'},
	        { header: '状态', dataIndex: 'status',renderer:statusRender},
	        { header: '描述', dataIndex: 'memo',flex:1}
	    ],
	    listeners:{
	    	select:function(row,r,i,e){
	    		if(r){
		    		if(!stock_form.isHidden()){
		    			stock_form.loadRecord(r);
		    			save_btn.setDisabled(true);
		    			edit_btn.setDisabled(false);
		    		}
		    		stid=r.get('stid');
	    		}
	    	}
	    },
	    tools:[{type:'refresh',tooltip:'刷新',handler:function(){stockStore.load()}},
	           {type:'plus',tooltip:'添加' ,handler:addstock},
	           {type:'minus',tooltip:'删除',handler:deletestock},
	           {type:'gear',tooltip:'编辑',handler:modifystock}]
	});
	
	function addstock(){
		if(stock_form.isHidden()){
			stock_form.toggleCollapse();
		}
		stock_grid.getSelectionModel( ).deselectAll();
		stock_form.getForm().setValues({stid:'',memo:'',unitid:'',stock:'',status:0});
		save_btn.setDisabled(false);
		edit_btn.setDisabled(true);
		
	}
	function deletestock(){
		var records=stock_grid.getSelectionModel( ).getSelection();
		if(records.length>0){
			Ext.Msg.confirm('消息','确定要删除吗?',function(btn){
				if (btn == 'yes'){
					 Ext.Ajax.request({
					        url : Ext.ContextPath+'/dataManagr/stockdelete.do',
					        method : 'post',
					        params:{stid:stid},
					        success : function(response, options) {
					        	Ext.MessageBoxEx('提示','删除成功');
					        	stockStore.remove(stock_grid.getSelectionModel( ).getLastSelected( ));
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
	function modifystock(){
		var records=stock_grid.getSelectionModel( ).getSelection( );
		if(records.length>0){
			if(stock_form.isHidden()){
				stock_form.toggleCollapse();
				stock_form.loadRecord(records[0]);
			}
			save_btn.setDisabled(false);
			edit_btn.setDisabled(true);
		}else{
			Ext.MessageBoxEx('提示','请选择一条记录');
		}
	}
	
	function stocksave(){
		var stockform= stock_form.getForm();
		var stock=stockform.getValues();
		var stock_id=stock.stid;
		if(!stock_id||stock_id==''){
			if(stockStore.find('stock',stock.stock)>0){
				Ext.MessageBoxEx('提示', "该库房名称已经存在,请换一个名称");
				return false;
			}
		}
		if(stockform.isDirty()){
			stockform.submit({
				    clientValidation: true,
				    url: Ext.ContextPath+'/dataManagr/stocksave.do',
				    success: function(form, action) {
				    	var stock=form.getValues();
				    	if(!stock.stid){
					    	stockStore.load({
					    		callback: function(records, operation, success) {
					               if(success&&records.length>0){
					            	   var obj=form.getValues();
					            	   var index= stockStore.find('cname',obj.cname);
					            	   if(index!=-1){
					            		   stock_grid.getSelectionModel( ).select(index); 
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
	
	var save_btn=Ext.create('Ext.button.Button',{text:'保存',iconCls:'icon-save',disabled:true,handler:stocksave,
		listeners:{
			disable:function(b,e){
				var fields=stock_form.getForm().getFields( );
				fields.each(function(items){
					items.setReadOnly(true);
				});
			},
			enable:function(){
				var fields=stock_form.getForm().getFields( );
				fields.each(function(items){
					if(items.getName()=='cname'){
						var id=stock_form.getValues().stid;
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
	var edit_btn=Ext.create('Ext.button.Button',{text:'编辑',iconCls:'icon-edit',disabled:true,handler:modifystock});
	
	var stock_form = Ext.create('Ext.form.Panel', {
	    region: 'south',
	    width: 250,
	    defaultType: 'textfield',
	    trackResetOnLoad:true,
	    fieldDefaults: {
	        labelWidth: 70,
	        anchor: '95%'
	    },
	    margin:'0 0 0 2px',
	    bodyPadding: '5px',
	    items: [{xtype: 'hidden',name: 'stid'},
	            {fieldLabel: '仓库名',name: 'stock'},
			    {fieldLabel: '作业单位',name: 'unitid',xtype:'listcombox',url:Ext.ContextPath+'/dataExtra/unitList.do',loadAuto:true,readOnly:true},
			    {xtype:'statusbox',fieldLabel:'状态',readOnly:true},
			    {fieldLabel: '描述',name: 'memo',readOnly:true}],
		bbar:['->',save_btn,edit_btn]
	});
	
	Ext.create('Ext.container.Viewport',{
        layout:{
        	type: 'hbox',
        	padding:'5px',
            align: 'stretch'
        },
        renderTo:'view-port',
        items:[stock_grid,stock_form]
	});
	
	function statusRender(v){switch(v){case 0:return '有效';default:return '待定'}};
});