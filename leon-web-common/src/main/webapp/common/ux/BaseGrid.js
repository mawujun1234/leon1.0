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
    autoLoad:true,
    autoSync:true,//是否自动同步，修改一个单元格就提交,否则的话，需要右键--》保存，并且如果有多个修改的话，会以数组的形式提交到后台的
    autoNextCellEditing:false,//在使用编辑的时候，按回车键会自动触发下一个单元格
    autoNextCellColIdx:0,//自动触发下一个单元格的时候，col开始的起始数
    //autoNextCelRowIdx:0,//自动触发下一个单元格的时候，row开始的起始数
    autoInitSimpleAction:true,
    autoShowSimpleActionToTbar:true,
    initComponent: function () {
        var me = this;
        
        if(me.model){
        	me.store=me.createStore(me.model);
			//me.getStore().getProxy().getWriter( ).a
        }
        me.bbar= {
	        xtype: 'pagingtoolbar',
	        store: me.store,   // same store GridPanel is using
	        displayInfo: true
	    };
       
		if(me.autoInitSimpleAction){
			me.initSimpleAction();
		}
		
		me.initAutoNextCellEditing();


        me.callParent();
    },
    createStore:function(model){
    	var me=this;
    	var store = Ext.create('Ext.data.Store', {
	        	autoSync:me.autoSync,
	        	remoteSort :true,
	        	pageSize: me.itemsPerPage,
		       	autoLoad:me.autoLoad,
		       	model:model
		});
		return store;
    },
    /**
     * 两种方式grid.reconfigure('Leon.desktop.constant.Constant'，columns);
     * grid.reconfigure(store，columns);
     * 这个时候对原来的store进行监听的事件将会失效，所以这个时候要注意
     * 
     * @param {} storeOrModel
     * @param {} columns
     * @return 会返回新建立的store
     */
    reconfigure:function(storeOrModel,columns) {
    	var me=this;
    	//console.log(storeOrModel);
    	//console.dir(columns);
    	
    	var tmpStore;
    	if(storeOrModel.isStore){
    		//me.reconfigure(store,columns)
    		tmpStore=storeOrModel;
    	} else {
    		tmpStore=me.createStore(storeOrModel);
    	}
    	//重新绑定分页工具栏
		//this.pagingBar.bind(newStore);
    	//console.dir(tmpStore);
    	arguments[0]=tmpStore;
    	me.callParent(arguments);
    	//console.trace();
    	return tmpStore;
    	
    },
    initAutoNextCellEditing:function(){
    	var me=this;
    	//如果配置了autoSync，表示自动添加编辑组件
    	if(!me.editable){
    		return;
    	}

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

	    	var colSize=grid.columns.length;
	    	var rowSize=grid.getStore().getCount();
	    	//alert(colIdx+"-----"+position.column);
	    	//alert(rowIdx+"-----"+position.row);
	    	//如果通过点击到另外的单元格，久切换到另外的道远个，而不是自动跳到下一格
	    	var position=me.getSelectionModel( ).getCurrentPosition( );
	    	if(grid.getSelectionModel( ).self.getName( ) =='Ext.selection.CellModel' ) {
	    		
	    		if(position.column!=colIdx || position.row!=rowIdx){
		    		return;
		    	}
	    	} else if (grid.getSelectionModel( ).self.getName( ) =='Ext.selection.RowModel'){
	    		if(position.row!=rowIdx){
		    		return;
		    	}
	    	}
	    	
	    	//alert(colIdx+"-----"+colSize);
	    	if(colIdx<colSize){
	    		
	    		colIdx++;
	    		editor.startEdit(rowIdx,colIdx);
	    	} else if(rowIdx<rowSize){
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
    initSimpleAction:function(){
    	var me=this;
    	var create = new Ext.Action({
		    text: '新增',
		    disabled:me.disabledAction,
		    handler: function(){
		    	me.onCreate();
		    },
		    iconCls: 'form-add-button'
		});
		me.addAction(create);
		var update = new Ext.Action({
		    text: '更新',
		    disabled:me.disabledAction,
		    handler: function(){
				me.onUpdate();
		    },
		    iconCls: 'form-update-button'
		});
		me.addAction(update);
		var destroy = new Ext.Action({
		    text: '删除',
		    disabled:me.disabledAction,
		    handler: function(){
		    	me.onDestroy();
		        
		    },
		    iconCls: 'form-delete-button'
		});
		me.addAction(destroy);

		if(!me.autoSync){
			var sync = new Ext.Action({
			    text: '保存',
			    disabled:me.disabledAction,
			    handler: function(){
					me.onSync();
			    },
			    iconCls: 'form-save-button'
			});
			me.addAction(sync);
		}
    },
    onCreate:function(){
    	var me=this;
    	var modelName=me.model||me.getStore().getProxy( ).getModel().getName( );

		var model=Ext.createModel(modelName,{
		        	//id:''
		});
		model.phantom =true;
		me.getStore().insert(0, model);
		        //me.getStore().add(model);
		var cellediting=me.getPlugin("cellEditingPlugin");
		cellediting.startEditByPosition({
		     row: 0, 
		     column: me.autoNextCellColIdx
		});
    },
    onUpdate:function(){
    	var me=this;
    	var position=me.getSelectionModel( ).getCurrentPosition( );
		var cellediting=me.getPlugin("cellEditingPlugin");
		cellediting.startEditByPosition(position);
    },
    onDestroy:function(){
    	var me=this;
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
    },
    onSync:function(){
    	var me=this;
    	me.getStore().sync({
			failure:function(){
				me.getStore().reload();
			}
		});
    },
    onAfterContextMenuShow:function(view,record,item,index,e){

    },
    addAction:function(action,index){
    	if(!action){
    		return;
    	}
    	var me=this;
    	var menu=me.contextMenu;
    	if(!menu){
    		me.contextMenu=Ext.create('Ext.menu.Menu', {
			    items:[]
			});
			menu=me.contextMenu;
			me.on('itemcontextmenu',function(tree,record,item,index,e){
				menu.showAt(e.getXY());
				e.stopEvent();
				me.onAfterContextMenuShow(tree,record,item,index,e);
			});
			
			me.on('containercontextmenu',function(view,e){
				//menu.showAt(e.getXY());
				e.stopEvent();
			});
    	} else if(!me.hasListener("itemcontextmenu")|| me.hasListener("containercontextmenu")){
    		me.on('itemcontextmenu',function(tree,record,item,index,e){
				menu.showAt(e.getXY());
				e.stopEvent();
			});
			
			me.on('containercontextmenu',function(view,e){
				menu.showAt(e.getXY());
				e.stopEvent();
			});
    	}
    	if(index){
    		me.contextMenu.insert(index,action);
    	} else {
    		me.contextMenu.add(action);
    	}
    	
//    	if(me.autoShowSimpleActionToTbar){
//    		var tbar=me.getDockedItems('toolbar[dock="top"]');
//    		
//    		if(!tbar || tbar.length==0){
//    			//me.tbar=Ext.create('Ext.toolbar.Toolbar');
//    			me.addDocked({
//			        xtype: 'toolbar',
//			        dock: 'top',
//			        items: []
//			    });
//    			tbar=me.getDockedItems('toolbar[dock="top"]');
//    		}
//    		if(index){
//	    		tbar[0].insert(index,action);
//	    	} else {
//	    		tbar[0].add(action);
//	    	}
//			
//		}
    },
    getLastSelected:function(){
    	return this.getSelectionModel( ).getLastSelected( );
    }
});