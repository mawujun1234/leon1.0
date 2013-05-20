/**
 * 封装grid
 */
Ext.define('Leon.common.ux.BaseGrid', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.bgridpanel',
    columnLines:true,
    
    viewConfig: {
    	lockable:true,
        stripeRows: false
    },
    model:null,//用来构建store，如果没有这个值，就得自己构建model
    itemsPerPage:50,
    initComponent: function () {
        var me = this;
        if(me.model){
        	me.store = Ext.create('Ext.data.Store', {
	        	autoSync:true,
	        	pageSize: me.itemsPerPage,
		       	autoLoad:true,
		       	model:me.model
			});
        }
        me.bbar= {
	        xtype: 'pagingtoolbar',
	        store: me.store,   // same store GridPanel is using
	        displayInfo: true
	    };
       
		
		me.initAction();
		me.initPlugins();

        me.callParent();
    },
    initPlugins:function(){
    	var me=this;
    	me.plugins= [
	        Ext.create('Ext.grid.plugin.CellEditing', {
	        	//pluginId: 'cellEditingPlugin',
	            clicksToEdit: 2
	        })
	    ];
    },
    initAction:function(){
    	var me=this;
    	var create = new Ext.Action({
		    text: '新增',
		    disabled:me.disabledAction,
		    handler: function(){
		    	var parent=me.getSelectionModel( ).getLastSelected( );    	
		    	var values={
		    		'parent_id':parent.get("id")
		    	};
		    	values[me.textField]='新节点';
		        var child=Ext.createModel(parent.self.getName(),values);
		        //alert(Ext.encode(child.raw));
		       //return;
		        child.save({
					success: function(record, operation) {
						//child=record;
						me.getStore().reload({node:parent});
						parent.expand();
					}
				});

		    },
		    iconCls: 'form-addChild-button'
		});
		var destroy = new Ext.Action({
		    text: '删除',
		    disabled:me.disabledAction,
		    handler: function(){
		    	Ext.Msg.confirm("删除",'确定要删除吗?', function(btn, text){
				    if (btn == 'yes'){
				       var node=me.getSelectionModel( ).getLastSelected( );

				      
				    }
				});
		        
		    },
		    iconCls: 'form-delete-button'
		});
		var update = new Ext.Action({
		    text: '保存（用于批量更新的时候）',
		    disabled:me.disabledAction,
		    handler: function(){

		    },
		    iconCls: 'form-update-button'
		});
		
		me.actions=[create,destroy,update];
		me.tbar=me.actions;
		//me.tbar.push(reload);
		
		var menu=Ext.create('Ext.menu.Menu', {
		    //width: 100,
		    //plain: true,
		    //floating: false,  // usually you want this set to True (default)
		    //renderTo: Ext.getBody(),  // usually rendered by it's containing component
		    items: me.actions
		});
		me.on('itemcontextmenu',function(tree,record,item,index,e){
			menu.showAt(e.getXY());
			e.stopEvent();
		});
    }
});