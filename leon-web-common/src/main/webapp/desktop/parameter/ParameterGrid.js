Ext.define('Leon.desktop.parameter.ParameterGrid',{
	extend:'Ext.grid.Panel',
	requires: [
	     'Leon.desktop.parameter.Parameter',
	     'Leon.desktop.parameter.ParameterSubjectGrid'
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
    plugins: [
        Ext.create('Ext.grid.plugin.CellEditing', {
        	//pluginId: 'cellEditingPlugin',
            clicksToEdit: 1
        })
    ],
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
	        { text: '排序', dataIndex: 'sort', width: 60,editor:{
	        		xtype:'numberfield',allowDecimals:false,allowBlank:false,selectOnFocus:true
	        		
	        	}
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
       		},{
       			text:'强制删除',
       			iconCls:'form-delete-button ',
       			handler:function(){
       				Ext.Msg.confirm("消息","删除后就不能恢复了，并且所有主体引用的值也都会被删除，确定要删除吗?",function(btn){
       					if(btn=='yes'){
       						var model=me.getSelectionModel( ).getLastSelected( ) ;
		       				if(model){
		       					//me.getStore().remove(model);
		       					model.destroy({params:{forceDelete:true}});
		       				}
       					}
       				});
       			}	
       		},{
       			text:'相关主体',
       			iconCls:'icons_account_balances ',
       			handler:function(){
       				var record=me.getSelectionModel( ).getLastSelected( );
					var grid=Ext.create('Leon.desktop.parameter.ParameterSubjectGrid',{
						parameterId:record.get("id")
					});
					var win=Ext.create('Ext.Window',{
						layout:'fit',
						width:500,
						height:300,
						items:[grid]
					});
					win.show();
       				
       			}	
       		}]
       });
       
       me.on('edit', function(editor, e) {
		    // commit the changes right after editing finished
		    e.record.commit();
		    e.record.save();
	   });
	   
       me.tbar=tbar;
       me.callParent();
	}
});