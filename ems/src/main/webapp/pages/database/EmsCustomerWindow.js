Ext.define('Ext.D.EmsCustomerWindow', {
		extend:'Ext.window.Window',  
		layout:'border',
		modal:true,
	    initComponent: function(){
	    	var me=this;
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
				    	if(success&&records.length>0 && customer_grid){
				    		customer_grid.getSelectionModel( ).select(0);
				    	}
				    }
			    }
			});
			var customer_grid=Ext.create('Ext.grid.Panel', {
		    	region:'west',
		    	width:230,
		    	title:'客户信息',
			    store: customerStore,
			    columns: [
			        { header: 'cid',  dataIndex: 'cid',hidden:true,hideable:false},
			        { header: 'unitid',  dataIndex: 'unitid',hidden:true,hideable:false},
			        { header: '类型', dataIndex: 'ctype',width:50,renderer:ctypeRender},
			        { header: '客户名称', dataIndex: 'cname',flex:1,minWidth:100}
			        //{ header: '作业单位', dataIndex: 'uname',hidden:true},
			        //{ header: '状态', dataIndex: 'status', width:50, renderer:statusRender},
			        //{ header: '描述', dataIndex: 'memo',hidden:true }
			    ],
			    listeners:{
			    	select:function(row,r,i,e){
			    		if(r){
				    		cid=r.get('cid');
				    		cname=r.get('cname');
				    		//contactStore.load({params:{cid:cid}});
				    		poleStore.load({params:{cid:cid}});
			    		}
			    	}
			    }
			});
	    	
			var poleStore = new Ext.data.JsonStore({
				model: 'pole',
			    proxy: {
			        type: 'ajax',
			        url: Ext.ContextPath+'/dataManagr/getSelectCustPoleList.do',
			        reader: {
			            root: 'root'
			        }
			    }
			});
			var pole_grid=Ext.create('Ext.grid.Panel', {
				title:'客户杆位',
			    //flex:1,
				split:true,
			    region:'center',
			    margin:'2px 0 0 0',
			    store: poleStore,
			    selModel:Ext.create('Ext.selection.CheckboxModel',{
			    	checkOnly:false
			    }),
			    columns: [
			    		{xtype: 'rownumberer'},
			           // { header: 'cpid',  dataIndex: 'cpid',hidden:true,hideable:false,editor:{xtype:'hidden'}},
			  	        //{ header: 'cid',  dataIndex: 'cid',hidden:true,hideable:false,editor:{xtype:'hidden'}},
				        { header: '杆位名称', dataIndex: 'pname',width:150,editor:{allowBlank:false}},
				        { header: '类型', dataIndex: 'ptype',width:70,renderer:dutyRender,editor:{allowBlank:false,xtype:'ptypebox'}},
				        { header: '位置', dataIndex: 'position',width:200,editor:{allowBlank:false}},
				        { header: '位置描述', dataIndex: 'posidesc',width:200,editor:{allowBlank:false}},
				        { header: '状态', dataIndex: 'status', width:50, renderer:statusRender,editor:{allowBlank:false,xtype:'statusbox'}},
				        { header: '描述', dataIndex: 'memo',flex:1,editor: 'textfield'}
			    ],
//			    tools:[{type:'refresh',tooltip:'刷新',handler:function(){poleStore.load({params:{cid:cid}})}},
//			           {type:'plus',tooltip:'添加' ,handler:addpole},
//			           {type:'minus',tooltip:'删除',handler:deletepole},
//			           {type:'gear',tooltip:'编辑',handler:modifypole}],
			    listeners:{
			    	deselect:function(row,record,i,o){
			    		if(!record.get('cpid')){
			    			poleStore.remove(record);
			    		}
			    	}
			    }
			});
			me.pole_grid=pole_grid;
			
			this.items=[customer_grid,pole_grid];
	    	this.callParent();
	    }
});