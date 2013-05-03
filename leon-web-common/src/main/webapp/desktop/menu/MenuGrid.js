Ext.define('Leon.desktop.menu.MenuGrid',{
	extend:'Ext.grid.Panel',
	requires: [
	     'Leon.desktop.menu.Menu'
	],
	columnLines :true,
	stripeRows:true,
	viewConfig:{
		stripeRows:true
	},
	//selType: 'cellmodel',
    plugins: [
        Ext.create('Ext.grid.plugin.CellEditing', {
            clicksToEdit: 1
        })
    ],
	initComponent: function () {
       var me = this;
       me.columns=[
	        //{ text: 'id',  dataIndex: 'id' },
	        { text: '名称', dataIndex: 'text', flex: 1 ,
	        	editor: {
	                xtype: 'textfield',
	                allowBlank: false
	            }
	        }
       ];
       me.store=Ext.create('Ext.data.Store',{
       		autoSync:true,
       		model: 'Leon.desktop.menu.Menu',
       		autoLoad:true
       });
       
       var tbar=Ext.create('Ext.toolbar.Toolbar', {
       		items:[{
       			text:'新增',
       			iconCls:'form-add-button ',
       			handler:function(){
       				var menu=Ext.create('Leon.desktop.menu.Menu',{text:'新菜单'});
       				me.store.add(menu);
       			}	
       		},{
       			text:'删除',
       			iconCls:'form-delete-button ',
       			handler:function(){
       				var model=me.getSelectionModel( ).getLastSelected( ) ;
       				if(model){
       					model.destroy();
       				}
       			}	
       		}]
       });
       me.tbar=tbar;
       me.callParent();
	}
});