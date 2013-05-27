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
    autoNextCellEditing:true,//在使用编辑的时候，按回车键会自动触发下一个单元格
    autoNextCellColIdx:0,//自动触发下一个单元格的时候，col开始的起始数
    //autoNextCelRowIdx:0,//自动触发下一个单元格的时候，row开始的起始数
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
       
		
		me.initAction();
		me.initPlugins();


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
    initPlugins:function(){
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

	    	var colSize=grid.getHeader( ).items.getCount( );
	    	var rowSize=grid.getStore().getCount();
	    	//alert(colIdx+"-----"+position.column);
	    	//alert(rowIdx+"-----"+position.row);
	    	//如果通过点击到另外的单元格，久切换到另外的道远个，而不是自动跳到下一格
	    	var position=me.getSelectionModel( ).getCurrentPosition( );
	    	if(position.column!=colIdx || position.row!=rowIdx){
	    		return;
	    	}
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
		    	var modelName=me.model||me.getStore().getProxy( ).getModel().getName( );

		        var model=Ext.createModel(modelName,{
		        	//id:''
		        });
		        me.getStore().insert(0, model);
		        var cellediting=me.getPlugin("cellEditingPlugin");
		        cellediting.startEditByPosition({
		            row: 0, 
		            column: me.autoNextCellColIdx
		        });
		    },
		    iconCls: 'form-add-button'
		});
		var update = new Ext.Action({
		    text: '更新',
		    disabled:me.disabledAction,
		    handler: function(){
				var position=me.getSelectionModel( ).getCurrentPosition( );
				var cellediting=me.getPlugin("cellEditingPlugin");
				//alert(cellediting);
		        cellediting.startEditByPosition(position);
		    },
		    iconCls: 'form-update-button'
		});
		var destroy = new Ext.Action({
		    text: '删除',
		    disabled:me.disabledAction,
		    handler: function(){
		    	Ext.Msg.confirm("删除",'确定要删除吗?', function(btn, text){
				    if (btn == 'yes'){
				        var records=me.getSelectionModel( ).getSelection( );//.getLastSelected( );
						me.getStore().remove( records );
				    }
				});
		        
		    },
		    iconCls: 'form-delete-button'
		});
		
		
		me.actions=[create,update,destroy];
		if(!me.autoSync){
			var update = new Ext.Action({
			    text: '保存',
			    disabled:me.disabledAction,
			    handler: function(){
					me.getStore().sync();
			    },
			    iconCls: 'form-save-button'
			});
			me.actions.push(update);
		}
		//me.tbar=me.actions;
		//me.tbar.push(reload);
		
		var menu=Ext.create('Ext.menu.Menu', {
		    items: me.actions
		});
		me.on('itemcontextmenu',function(view,record,item,index,e){
			menu.showAt(e.getXY());
			e.stopEvent();
		});
		
		me.on('containercontextmenu',function(view,e){
			menu.showAt(e.getXY());
			e.stopEvent();
		});
    },
    getLastSelected:function(){
    	return this.getSelectionModel( ).getLastSelected( );
    }
});