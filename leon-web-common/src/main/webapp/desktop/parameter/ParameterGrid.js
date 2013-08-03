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
//	//selType: 'cellmodel',
//    plugins: [
//        Ext.create('Ext.grid.plugin.CellEditing', {
//        	pluginId: 'cellEditingPlugin',
//            clicksToEdit: 2
//        })
//    ],
	initComponent: function () {
       var me = this;
       function formatQtip(data,metadata){   
		    metadata.tdAttr = "data-qtip='" + data + "'";
		    return data;    
	  }  
      me.columns=[
	        { text: 'id',  dataIndex: 'id' },
	        { text: '名称', dataIndex: 'name', width: 100
	        },
	        { text: '描述', dataIndex: 'desc', width: 150 
	        },
//	        { text: '展现方式', dataIndex: 'showModel', flex: 1 
//	        },
	        { text: '展现方式', dataIndex: 'showModelName', width: 80 
	        },
//	        { text: '值类型', dataIndex: 'valueEnum', flex: 1 
//	        },
	        { text: '值类型', dataIndex: 'valueEnumName', width: 80 
	        },
	        { text: '使用次数', dataIndex: 'useCount', width: 60
	        },
	        { text: '目标', dataIndex: 'subjectNames', width: 150,renderer:formatQtip
	        },
	        { text: '内容数据', dataIndex: 'content', flex: 1,renderer:formatQtip
	        }
       ];
       me.store=Ext.create('Ext.data.Store',{
       		autoSync:false,
       		model: 'Leon.desktop.parameter.Parameter',
       		autoLoad:true
       });
       
       var tbar=Ext.create('Ext.toolbar.Toolbar', {
       		items:[{
       			text:'新增',
       			iconCls:'form-add-button ',
       			handler:function(){
					var win=Ext.create('Leon.desktop.parameter.ParameterWindow');
					win.parameterGrid=me;
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
       				Ext.Msg.confirm("消息","确定要删除吗?",function(btn){
       					if(btn=='yes'){
       						var model=me.getSelectionModel( ).getLastSelected( ) ;
		       				if(model){
		       					//me.getStore().remove(model);
		       					model.destroy();
		       				}
       					}
       				});
       			}	
       		}]
       });
       me.tbar=tbar;
       me.callParent();
	}
});