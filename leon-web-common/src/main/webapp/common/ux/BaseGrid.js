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
    editable:true,//是否启用自动编辑
    autoSync:true,//是否自动同步，修改一个单元格就提交
    autoNextCellEditing:true,//在使用编辑的时候，按回车键会自动触发下一个单元格
    autoNextCellColIdx:0,//自动触发下一个单元格的时候，col开始的起始数
    //autoNextCelRowIdx:0,//自动触发下一个单元格的时候，row开始的起始数
    initComponent: function () {
        var me = this;
        if(me.model){
        	me.store = Ext.create('Ext.data.Store', {
	        	autoSync:me.autoSync,
	        	remoteSort :true,
	        	pageSize: me.itemsPerPage,
		       	autoLoad:true,
		       	model:me.model
			});
			me.getStore().getProxy().getWriter( ).a
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
    	//如果配置了autoSync，表示自动添加编辑组件
    	if(!me.editable){
    		return;
    	}
    	var me=this;
    	var cellediting=Ext.create('Ext.grid.plugin.CellEditing', {
	        	pluginId: 'cellEditingPlugin',
	            clicksToEdit: 2
	    });
    	me.plugins= [cellediting];
    	
	    cellediting.on('edit',function(editor,e){
	    	var grid =e.grid ;
	    	//var record =e.record ;
	    	//var field =e.field ;
	    	//var value =e.value ;
	    	//var row=e.row ;
	    	//var column =e.column ;
	    	var rowIdx=e.rowIdx;
	    	var colIdx =e.colIdx ;
	    	//alert(1);
	    	
	    	var colSize=grid.getHeader( ).items.getCount( );
	    	var rowSize=grid.getStore().getCount();
	    	//alert(colIdx+"-----"+colSize);
	    	if(colIdx<colSize-1){
	    		colIdx++;
	    		editor.startEdit(rowIdx,colIdx);
	    	} else if(rowIdx<rowSize-1){
		    	rowIdx++;
		    	colIdx=me.autoNextCellColIdx;
		    	editor.startEdit(rowIdx,colIdx);
	    	} else {
	    		//alert(1);
	    		//editor.startEdit(rowIdx,colIdx);
	    		//editor.cancelEdit();
	    	}
	    	
	    });
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
		
		
		me.actions=[create,destroy];
		if(!me.autoSync){
			var update = new Ext.Action({
			    text: '保存',
			    disabled:me.disabledAction,
			    handler: function(){
					me.getStore().sync();
			    },
			    iconCls: 'form-update-button'
			});
			me.actions.push(update);
		}
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