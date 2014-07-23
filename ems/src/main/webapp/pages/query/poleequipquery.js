Ext.onReady(function(){

	Ext.tip.QuickTipManager.init();
	
	var cid;
	
	var pole_field=Ext.create('Ext.dx.form.QueryField',{
		name:'pname',
		width:300,
		labelWidth:58,
		fieldLabel:'杆位信息',
		emptyText:'请输入杆位信息',
	    onTriggerClick :customerQuery
	});
	
	var customer_field=Ext.create('Ext.dx.form.QueryField',{
		name:'cname',
		labelWidth:58,
		fieldLabel:'客户信息',
		emptyText:'请输入客户信息',
	    onTriggerClick :customerQuery
	});
	
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
	        url: 'getCustomerList',
	        actionMethods: {  
	        	 read: 'POST'  
	        },  
	        reader: {
	            root: 'list',
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
    	title:'客户列表',
	    store: customerStore,
	    columns: [
	        { header: 'cid',  dataIndex: 'cid',hidden:true,hideable:false},
	        { header: 'unitid',  dataIndex: 'unitid',hidden:true,hideable:false},
	        { header: '类型', dataIndex: 'ctype',width:50,renderer:ctypeRender},
	        { header: '客户名称', dataIndex: 'cname',flex:1,minWidth:100},
	        { header: '作业单位', dataIndex: 'uname',hidden:true},
	        { header: '状态', dataIndex: 'status', width:50, renderer:statusRender},
	        { header: '描述', dataIndex: 'memo',hidden:true }
	    ],
	    listeners:{
	    	select:function(row,r,i,e){
	    		if(r){
	    			cid=r.get('cid');
	    			poleQuery();
	    		}
	    	}
	    },
	    tools:[{type:'refresh',tooltip:'刷新',handler:customerQuery}],
	    tbar:[customer_field]
	});
	
	function customerQuery(){
		var obj={cname:null,pname:null};
		var pname=pole_field.getValue();
		var cname=customer_field.getValue();
		if(!Ext.isEmpty(pname)){
			obj.pname=pname;
		}
		if(!Ext.isEmpty(cname)){
			obj.cname=cname;
		}
		customerStore.load({params:obj});
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
	        url: 'getPoleList',
	        actionMethods: {  
	           read: 'POST'  
	        },  
	        reader: {
	            root: 'list'
	        }
	    }
	});
	 
	var pole_grid=Ext.create('Ext.grid.Panel', {
		title:'客户杆位',
	    store: poleStore,
	    columns: [
	            { header: 'cpid',  dataIndex: 'cpid',hidden:true,hideable:false},
	  	        { header: 'cid',  dataIndex: 'cid',hidden:true,hideable:false},
		        { header: '杆位名称', dataIndex: 'pname',width:150},
		        { header: '类型', dataIndex: 'ptype',width:70,renderer:dutyRender},
		        { header: '位置', dataIndex: 'position',width:200},
		        { header: '位置描述', dataIndex: 'posidesc',width:200},
		        { header: '状态', dataIndex: 'status', width:50, renderer:statusRender},
		        { header: '描述', dataIndex: 'memo',flex:1}
	    ],
	    tools:[{type:'refresh',tooltip:'刷新',handler:poleQuery}],
	});
	
	function poleQuery(){
		var obj={cid:cid,pname:null};
		var pname=pole_field.getValue();
		if(!Ext.isEmpty(pname)){
			obj.pname=pname;
		}
		poleStore.load({params:obj});
	}
	
	var equipStore = Ext.create('Ext.data.ArrayStore', {
		pageSize: 10,
	    proxy: {
	        type: 'ajax',
	        url: 'poleequiplist',
	        actionMethods: {  
	           read: 'POST'  
	        },  
	        reader: {
	            root: 'list'
	        }
	    },
		fields : [{name : 'ecode',mapping : 'ecode'}, 
		          {name : 'estatus',mapping : 'estatus'}, 
		          {name : 'typename',mapping : 'typename'},
		          {name : 'etype',mapping : 'etype'},
		          {name : 'esubtype',mapping : 'esubtype'},
		          {name : 'subtypename',mapping : 'subtypename'}]
	});
	
	var equip_list=Ext.create('Ext.grid.Panel', {
	    store: equipStore,
	    border: false,
	    sortableColumns: false,
	    columns: [Ext.create('Ext.grid.RowNumberer', {text: '序号',width: 35}),
	              {header: '条形码',dataIndex: 'ecode'},
	              {header: '设备类型',dataIndex: 'typename'},
	              {header: '设备子类型',dataIndex: 'subtypename'},
	              {header: '状态',dataIndex: 'estatus',flex:1,renderer:statusRender}],
      	viewConfig: {
	 	    	  loadMask : {msg : '正在加载数据，请稍等...' },
	 	    	  stripeRows: true  
	 	},
	    dockedItems: [{
	        xtype: 'pagingtoolbar',
	        store: equipStore,
	        // same store extract_grid is using
	        dock: 'bottom',
	        //分页 位置
	        emptyMsg: '没有数据',
	        displayInfo: true,
	        displayMsg: '当前显示{0}-{1}条记录 / 共{2}条记录 ',
	        beforePageText: '第',
	        afterPageText: '页/共{0}页'
	    }]
	});
	
	var list_win;
   	var contextMenu = new Ext.menu.Menu({
		id : 'equip-list',
		setActionRecord:function(record){
			this.actionRecord=record;
		},
		items : [{
					text : '查看杆位设备',
					iconCls : 'icon-list',
					handler : function(btn) {
						if(!list_win){
							list_win=Ext.create('Ext.window.Window',{
								title:'设备列表',
								layout:'fit',
								height:300,
								width:400,
								closeAction:'hide',
								border:false,
								mode:true,
								items:equip_list,
								buttons:[{text:'关闭',handler:function(){
									this.up('window').hide();
								}}]
							});
						}
						var record=contextMenu.actionRecord;
						if(!!record){
							var cpid=record.get('cpid');
							equipStore.load({params:{cpid:cpid}});
							equipStore.on('beforeload',function() {
							    Ext.apply(equipStore.proxy.extraParams, {
							    	cpid:cpid
							    });
							});
							list_win.show();
						}
					}
				}]
	})
   	
   	pole_grid.on("itemcontextmenu", function(v,record,item,i,e,o) {
			e.preventDefault();
			contextMenu.setActionRecord(record);
			contextMenu.showAt(e.getXY());
	})
	
	Ext.create('Ext.container.Viewport',{
        layout: {
            type:'vbox',
            padding:'5',
            align:'stretch'
        },
        defaults:{border:false},
        items:[{margin:'2px 0 0 0',items:[pole_field]},
               {html:'<HR style="FILTER: alpha(opacity=100,finishopacity=0,style=3)" width="100%" color=#987cb9 SIZE=3>'},
               {flex:1,layout:'border',bodyStyle:'background-color:#FFFFFF',//设置面板体的背景色
           		defaults:{border:false,layout:'fit'},
                items:[{
                	region:'west',
                	width: 300,
                	split:true,
                	items:[customer_grid]
	                },{
	                	region:'center',
	                    items:[pole_grid]
	                }]
           	   }],
    	listeners:{
    		activate:function(p,e){
    			ecode="";
    			ecode_field.reset();
    			ecode_field.focus();
    		}
    	}
	});
	
	function ctypeRender(v){switch(v){case 0:return '机关';case 1:return '企业';default:return v}};
	function statusRender(v){switch(v){case 0:return '有效';default:return '待定'}};
	function dutyRender(v){switch(v){case 1:return '低杆';case 2:return '高杆';default:return '普通  '}};
});