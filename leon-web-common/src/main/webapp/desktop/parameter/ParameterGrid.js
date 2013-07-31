Ext.define('Leon.desktop.parameter.ParameterGrid',{
	extend:'Ext.grid.Panel',
	requires: [
	     'Leon.desktop.parameter.Parameter'
	],
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
        	pluginId: 'cellEditingPlugin',
            clicksToEdit: 2
        })
    ],
	initComponent: function () {
       var me = this;
      me.columns=[
	        { text: 'id',  dataIndex: 'id' },
	        { text: '名称', dataIndex: 'name', flex: 1
	        },
	        { text: '描述', dataIndex: 'desc', flex: 1 
	        },
//	        { text: '展现方式', dataIndex: 'showModel', flex: 1 
//	        },
	        { text: '展现方式', dataIndex: 'showModelName', flex: 1 
	        },
//	        { text: '值类型', dataIndex: 'valueEnum', flex: 1 
//	        },
	        { text: '值类型', dataIndex: 'valueEnumName', flex: 1 
	        },
	        { text: '默认值', dataIndex: 'defaultValue', flex: 1
	        },
	        { text: '内容数据', dataIndex: 'content', flex: 1
	        }
       ];
       me.store=Ext.create('Ext.data.Store',{
       		autoSync:true,
       		model: 'Leon.desktop.parameter.Parameter',
       		autoLoad:true
       });
       
       var tbar=Ext.create('Ext.toolbar.Toolbar', {
       		items:[{
       			text:'新增',
       			iconCls:'form-add-button ',
       			handler:function(){
					var win=Ext.create('Leon.desktop.parameter.ParameterWindow');
					win.show();
       				
       			}	
       		},{
       			text:'更新',
       			iconCls:'form-update-button ',
       			handler:function(){
       				var record=me.getSelectionModel( ).getLastSelected( );
       				if(!record){
       					return;
       				}
					var win=Ext.create('Leon.desktop.parameter.ParameterWindow',{
						record:record
					});
					win.show();
       				
       			}	
       		},{
       			text:'删除',
       			iconCls:'form-delete-button ',
       			handler:function(){
       				Ext.Msg.confirm("消息","确定要删除吗?",function(btn){alert(btn);
       					if(btn=='yes'){
       						var model=me.getSelectionModel( ).getLastSelected( ) ;
		       				if(model){
		       					model.destroy();
		       				}
       					}
       				});
//       				var model=me.getSelectionModel( ).getLastSelected( ) ;
//       				if(model){
//       					if(model.get("id")=="default"){
//       						Ext.Msg.alert("消息","默认菜单不能删除");
//       						return;
//       					}
//       					model.destroy();
//       				}
       			}	
       		}]
       });
       me.tbar=tbar;
       me.callParent();
	}
});