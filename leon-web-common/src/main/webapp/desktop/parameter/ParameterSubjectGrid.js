Ext.define('Leon.desktop.parameter.ParameterSubjectGrid',{
	extend:'Ext.grid.Panel',
//	requires: [
//	     'Leon.desktop.parameter.Parameter'
//	],
	columnLines :true,
	stripeRows:true,
	viewConfig:{
		stripeRows:true,
		listeners:{
			refresh:function(){
				this.select(0);
			}
		}
	},
////	//selType: 'cellmodel',
//    plugins: [
//        Ext.create('Ext.grid.plugin.CellEditing', {
//        	//pluginId: 'cellEditingPlugin',
//            clicksToEdit: 1
//        })
//    ],
	initComponent: function () {
       var me = this;
       function formatQtip(data,metadata){   
		    metadata.tdAttr = "data-qtip='" + data + "'";
		    return data;    
	  }  
      me.columns=[
      		{ text: '主体id',  dataIndex: 'subjectId' },
	        { text: '主体名称',  dataIndex: 'subjectName' },
	        { text: '主体类型', dataIndex: 'subjectType', width: 100
	        },
	        { text: '参数id', dataIndex: 'parameterId', width: 150 
	        },
	        { text: '参数值', dataIndex: 'parameterValue', flex:1 }
       ];
        me.store=Ext.create('Ext.data.Store',{
       		autoSync:false,
       		pageSize:50,
       		fields:['subjectId','subjectName','subjectType','parameterId','parameterValue'],
       		//model: 'Leon.desktop.parameter.Parameter',
       		autoLoad:false,
       		proxy:{
		    	type: 'ajax',
        		url : '/parametersubject/querySubject',
        		headers:{ 'Accept':'application/json;'},
        		actionMethods: { read: 'POST' },
        		extraParams:{limit:50},
        		reader:{
					type:'json',
					root:'root',
					successProperty:'success',
					totalProperty:'total'		
				}
		    }
       });
      // me.store=store;
       
       var subjectTypeCombo=Ext.create('Ext.form.ComboBox',{
		    queryMode: 'remote',
		    displayField: 'name',
		    valueField: 'key',
		    store:Ext.create('Ext.data.Store', {
		    	fields: ['key', 'name'], 
			    proxy:{
			    	type:'ajax',
			    	url:'/parametersubject/querySubjectType',
			    	reader:{
			    		type:'json',
			    		totalProperty:'total',
			    		root:'root'
			    	}
			    }
			})
       });
       var tbar=Ext.create('Ext.toolbar.Toolbar', {
       		items:['主体:',subjectTypeCombo,{
       			text:'查询',
       			iconCls:'icons_search ',
       			handler:function(){
//       				if(!subjectTypeCombo.getValue()){
//       					return;
//       				}
					 me.getStore().getProxy( ).extraParams={subjectType:subjectTypeCombo.getValue(),parameterId:me.parameterId};
					 me.getStore().reload();
       			}	
       		}]
       });
       me.tbar=tbar;
       
       me.dockedItems= [{
	        xtype: 'pagingtoolbar',
	        store: me.store,  
	        dock: 'bottom',
	        displayInfo: true
	   }];
       
       me.callParent();
	}
});