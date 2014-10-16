Ext.define('Ems.baseinfo.AreaSelPoleWindow', {
		extend:'Ext.window.Window',  
		layout:'border',
		requires:['Ems.baseinfo.Customer'],
		modal:true,
	    initComponent: function(){
	    	var me=this;
	    	var customerStore = new Ext.data.Store({
				model: 'Ems.baseinfo.Customer',
			    proxy: {
			        type: 'ajax',
			        url: Ext.ContextPath+'/customer/query.do',
			        reader: {
			        	type:'json',
			            root: 'root'
			        }
			    },
			    autoLoad:true
//			    listeners:{
//				    load:function(s,records,success,o,e){
//				    	if(success&&records.length>0 && customer_grid){
//				    		customer_grid.getSelectionModel( ).select(0);
//				    	}
//				    }
//			    }
			});
			var customer_grid=Ext.create('Ext.grid.Panel', {
		    	region:'west',
		    	width:230,
		    	title:'客户信息',
			    store: customerStore,
			    columns: [
			        {dataIndex:'name',text:'名称',flex:1},
			        {dataIndex:'type',text:'type',xtype: 'numbercolumn', renderer:function(value){
						if(value==0){
							return "机关";
						} else {
							return "企业";
						}
					}}
			    ],
			    listeners:{
			    	select:function(row,r,i,e){
			    		if(r){
				    		customer_id=r.get('id');
				    		customer_name=r.get('name');
				    		//contactStore.load({params:{cid:cid}});
				    		poleStore.getProxy().extraParams={customer_id:customer_id,filterContainArea:true};
				    		poleStore.reload();
				    		//poleStore.load({params:{customer_id:customer_id,filterContainArea:true}});
			    		}
			    	}
			    }
			});
	    	
			var pole_pageSize=50;
			var poleStore = new Ext.data.JsonStore({
				model: 'Ems.baseinfo.Pole',
				pageSize:pole_pageSize,
			    proxy: {
			        type: 'ajax',
			        url: Ext.ContextPath+'/pole/query.do',
			        reader: {
			        	type:'json',
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
			    dockedItems: [{
				        xtype: 'pagingtoolbar',
				        store: poleStore,  
				        dock: 'bottom',
				        displayInfo: true
				}],
			    columns: [
			    		{xtype: 'rownumberer'},
			            {dataIndex:'name',text:'杆位名称',width:160},
				        {dataIndex:'province',text:'地址',flex:1,renderer:function(value,metaData ,record){
				      		return value+record.get("city")+record.get("area")+record.get("address")
				        }},
				//      	{dataIndex:'city',text:'city'},
				//      	{dataIndex:'area',text:'area'},
				//		{dataIndex:'address',text:'address'},
						//{dataIndex:'customer_id',text:'customer_id'},
						{dataIndex:'latitude',text:'经度'},
						{dataIndex:'longitude',text:'纬度'}
			    ],
			    listeners:{
			    	deselect:function(row,record,i,o){
//			    		if(!record.get('cpid')){
//			    			poleStore.remove(record);
//			    		}
			    	}
			    }
			});
			me.pole_grid=pole_grid;
			
			this.items=[customer_grid,pole_grid];
	    	this.callParent();
	    }
});