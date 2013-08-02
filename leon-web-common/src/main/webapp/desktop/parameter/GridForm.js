Ext.define('Leon.desktop.parameter.GridForm',{
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
	//selType: 'cellmodel',
    plugins: [
        Ext.create('Ext.grid.plugin.CellEditing', {
        	//pluginId: 'cellEditingPlugin',
            clicksToEdit: 1
        })
    ],
	initComponent: function () {
       var me = this;
       me.columns=[
	        { text: '唯一码',  dataIndex: 'key',editor:{xtype:'textfield',allowBlank:false} },
	        { text: '显示值', dataIndex: 'name',editor:{xtype:'textfield',allowBlank:false}
	        }

       ];
       me.store=Ext.create('Ext.data.Store',{
       		autoSync:false,
       		fields:['key','name'],
       		autoLoad:true
       });

       
       var tbar=Ext.create('Ext.toolbar.Toolbar', {
       		items:[{
       			text:'新增',
       			iconCls:'form-add-button ',
       			handler:function(){
			    	var modelName=me.model||me.getStore().getProxy( ).getModel().getName( );
			
					var model=Ext.createModel(modelName,{
					        	//id:''
					});
					model.phantom =true;
					me.getStore().insert(0, model);
//					var cellediting=me.getPlugin("cellEditingPlugin");
//					cellediting.startEditByPosition({
//					     row: 0, 
//					     column: 0
//					});	
       			}	
       		},{
       			text:'删除',
       			iconCls:'form-delete-button ',
       			handler:function(){
       				Ext.Msg.confirm("删除",'确定要删除吗?', function(btn, text){
						if (btn == 'yes'){
							var records=me.getSelectionModel( ).getSelection( );//.getLastSelected( );
							me.getStore().remove( records );
							me.getStore().sync({
								failure:function(){
									me.getStore().reload();
								}
							});
						}
					});
       			}	
       		}]
       });
       me.tbar=tbar;
       me.callParent();
	}
});