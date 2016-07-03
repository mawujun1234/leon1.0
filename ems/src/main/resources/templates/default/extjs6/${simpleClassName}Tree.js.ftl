<#assign simpleClassNameFirstLower = simpleClassName?uncap_first> 
<#-- //所在模块-->
<#assign module = basepackage?substring(basepackage?last_index_of(".")+1)> 
/**
 * 功能的扩展，添加自定义的怎，删，改
 * 添加右键菜单，增，删，改，并且增加工具栏，增，删，改。
 * 后台的类最好继承TreeNode类，这样就可以少写很多代码
 */
Ext.define('${extenConfig.extjs_packagePrefix}.${module}.${simpleClassName}Tree', {
    extend: 'Ext.tree.Panel',
    requires:['${extenConfig.extjs_packagePrefix}.${module}.${simpleClassName}'],
    initComponent: function () {
		var me = this;

        me.store = Ext.create('Ext.data.TreeStore', {
	       	autoLoad:true,
	       	nodeParam :'parent_id',//传递到后台的数据，默认是node
	       	model:'${extenConfig.extjs_packagePrefix}.${module}.${simpleClassName}',
	       	proxy:{
				type: 'ajax',
			    url : Ext.ContextPath+'/${simpleClassNameFirstLower}/query.do',
			    headers:{ 'Accept':'application/json;'},
			    actionMethods: { read: 'POST' },
			    reader:{
					type:'json'		
				}
			},
			root: {
			    expanded: true,
			    text:"根节点" 
			}
		});
		me.initAction();
       
		me.callParent(arguments);
    },

    initAction:function(){
     	var me = this;
     	var actions=[];
     	
       var create = new Ext.Action({
		    text: '新建',
		    itemId:'create',
		    handler: function(b){
		    	me.onCreate(null,b);
		    },
		    iconCls: 'icon-plus'
		});
		//me.addAction(create);
		actions.push(create);
		
		var update = new Ext.Action({
		    text: '更新',
		    itemId:'update',
		    handler: function(){
		    	me.onUpdate();
				
		    },
		    iconCls: 'icon-edit'
		});
		actions.push(update);
		
		var destroy = new Ext.Action({
		    text: '删除',
		    itemId:'destroy',
		    handler: function(){
		    	me.onDelete();    
		    },
		    iconCls: 'icon-trash'
		});
		//me.addAction(destroy);
		actions.push(destroy)
		
		
		var reload = new Ext.Action({
		    text: '刷新',
		    itemId:'reload',
		    handler: function(){
		    	me.onReload();
		    },
		    iconCls: 'icon-refresh'
		});
		//me.addAction(reload);
		actions.push(reload);

		var menu=Ext.create('Ext.menu.Menu', {
			items: actions
		});	
		me.on('itemcontextmenu',function(tree,record,item,index,e){
			menu.showAt(e.getXY());
			e.stopEvent();
		});
		me.contextMenu=menu;
		
    },
    onCreate:function(){
    	var me=this;

    	var parent=me.getSelectionModel( ).getLastSelected( )||me.getRootNode( );    
		
		var child=Ext.create(parent.self.getName(),{
		    'parent_id':parent.get("id"),
		    text:''
		});
		child.set("id",null);
		
		var formpanel=Ext.create('${extenConfig.extjs_packagePrefix}.${module}.${simpleClassName}Form',{});
		formpanel.loadRecord(child);
		
    	var win=Ext.create('Ext.window.Window',{
    		layout:'fit',
    		title:'新增',
    		modal:true,
    		width:400,
    		height:300,
    		closeAction:'hide',
    		items:[formpanel],
    		listeners:{
    			close:function(){
    				me.onReload(parent);
    			}
    		}
    	});
    	win.show();
    },
    
     onUpdate:function(){
    	var me=this;

    	var node=me.getSelectionModel( ).getLastSelected();
    	if(node==null || node.isRoot()){
    		Ext.Msg.alert("提醒","请选择一个不是根节点的节点!");
    		return;
    	}

		var formpanel=Ext.create('${extenConfig.extjs_packagePrefix}.${module}.${simpleClassName}Form',{});
		formpanel.loadRecord(node);
		
    	var win=Ext.create('Ext.window.Window',{
    		layout:'fit',
    		title:'更新',
    		modal:true,
    		width:400,
    		height:300,
    		closeAction:'hide',
    		items:[formpanel]
    	});
    	win.show();
    },
    
    onDelete:function(){
    	var me=this;
    	var node=me.getSelectionModel( ).getLastSelected( );

		if(!node){
		    Ext.Msg.alert("消息","请先选择节点");	
			return;
		}
		if(node.isRoot()){
			Ext.Msg.alert("消息","根节点不能删除!");	
			return;
		}
		var parent=node.parentNode;
		Ext.Msg.confirm("删除",'确定要删除吗?', function(btn, text){
				if (btn == 'yes'){
					node.erase({
					    failure: function(record, operation) {
			            	me.onReload(parent);
					    },
					    success:function(){
					    	me.onReload(parent);
					    }
				});
			}
		});
    },
    onReload:function(node){
    	var me=this;
    	var parent=node||me.getSelectionModel( ).getLastSelected( );
		if(parent){
		    me.getStore().reload({node:parent});
		} else {
		    me.getStore().reload();	
		}      
    },
   
    
    getContextMenu:function(){
    	return this.contextMenu;
    },
    getLastSelected:function(){
    	return this.getSelectionModel( ).getLastSelected( );
    }
    
});
