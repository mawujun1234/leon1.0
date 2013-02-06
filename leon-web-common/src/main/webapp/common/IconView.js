/**
 * 监听itemdblclick事件，参数有record,item,index
 */
Ext.define('Ext.common.IconView', {
    extend: 'Ext.Window',
    layout: 'fit',
    autoScroll:true,
    modal:true,
    zIndex:99999,
    title: '请选择图标(双击选择)',
    initComponent: function(){ 
    	this.items=[this.createIconPanel()];
    	
    	this.callParent(); 
    	this.addEvents("itemdblclick");
    },
    //items: [this.createIconPanel()],
    createIconPanel:function(){
    	var me=this;
		var iconPageSize=20;

	    var store = Ext.create('Ext.data.Store', {
	        fields: ['name', 'contextPath','url','icon16','icon48'],
	        proxy: {
	            type: 'ajax',
	            url: Ext.ContextPath+'/icon/iconView.do?size=48',
	            reader: {
	                type: 'json',
	                root: 'root',
	                totalProperty:'totalProperty',
	                fields: ['name', 'contextPath','url','icon16','icon48']
	            }
	        }
	    });
	    store.load({params:{start:0,limit:iconPageSize}});
	
	    var tpl = new Ext.XTemplate(
			'<tpl for=".">',
	            '<div class="thumb-wrap" id="{name}">',
			    '<div class="thumb-48" ><img src="{url}" title="{name}"></div>',
			    '<span class="x-editable">{shortName}</span></div>',
	        '</tpl>',
	        '<div class="x-clear"></div>'
		);

		 //在底部上增加分页信息
	    var paging = Ext.create('Ext.toolbar.Paging', {
	    	store: store, 
	        pageSize: iconPageSize,
	        //plugins:new Ext.ux.PageSizePlugin(),
	        displayInfo: true,
	        displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
	        emptyMsg: "没有记录"
	         
	    });
	    var panel= Ext.create('Ext.Panel', {
	        id:'images-view',
	        frame:true,
	        width: 540,
	        height:300,
	        autoScroll:true,
	        collapsible:false,
	        layout:'fit',
			bbar:paging,
	        items: Ext.create('Ext.view.View', {
	            store: store,
	            tpl: tpl,
	            autoHeight:true,
	            //multiSelect: true,
	            singleSelect :true,
	            trackOver: true,
        		overItemCls: 'x-item-over',
        		itemSelector: 'div.thumb-wrap',
//        		overClass:'x-view-over',
//	            itemSelector:'div.thumb-wrap',
	            emptyText: '没有图标',
	            prepareData: function(data){
	                data.shortName = Ext.util.Format.ellipsis(data.name, 15);
	                //data.sizeString = Ext.util.Format.fileSize(data.size);
	                //data.dateString = data.lastmod.format("m/d/Y g:i a");
	                return data;
	            },
	            
	            listeners: {
	            	itemdblclick:function(dataView,record,item,index){
	            		me.fireEvent("itemdblclick", record,item,index);
	            	}
	            }
	        })
	    });
	    return panel;
    }
    
});