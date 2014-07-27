/**
 * 当双击选的的时候，会触发事件itemdblclick，表示选择
 */
Ext.define('Leon.common.IconWindow', {
    extend: 'Ext.window.Window',
    pagesize:66,
    width:500,
    height:380,
    title:'选择图标',
    modal:true,
    initComponent: function () {
        var me = this;
        var store=Ext.create('Ext.data.Store', {
        	autoLoad:true,
        	pageSize:me.pagesize,
		    fields: ['iconCls','iconCls32','src','src16','name'],
		    proxy:{
		    	type: 'ajax',
        		url : Ext.ContextPath+'/png/query',
        		headers:{ 'Accept':'application/json;'},
        		actionMethods: { read: 'POST' },
        		extraParams:{limit:me.pagesize},
        		reader:{
					type:'json',
					root:'root',
					successProperty:'success',
					totalProperty:'total'		
				}
		    }
		});
		

		me.id= 'images-view';
		var view=Ext.create('Ext.view.View', {
		    store: store,
		    //tpl: imageTpl,
		    tpl: [
                '<tpl for=".">',
                    '<div class="thumb-wrap" >',
                        '<div class="thumb"><img src="{src}" title="{name:htmlEncode}"></div>',
                        '<span class="x-editable"></span>',
                    '</div>',
                '</tpl>',
                '<div class="x-clear"></div>'
            ],
		    trackOver: true,
            overItemCls: 'x-item-over',
		    itemSelector: 'div.thumb-wrap',
		    emptyText: 'No images available',
		    listeners: {
                itemdblclick: function(dv, record ){
                    //alert(record.get('src'));
                    me.fireEvent('itemdblclick',me,record);
                }
            }
		});
		this.items=[view];
		me.dockedItems= [{
	        xtype: 'pagingtoolbar',
	        store: store,   // same store GridPanel is using
	        dock: 'bottom',
	        displayInfo: true
	    }];
	    
	    me.tbar= [
	    	{xtype:'textfield'},
			{ xtype: 'button', text: '查询' ,
				handler:function(btn){
					var name=btn.previousSibling('textfield').getValue();
					store.getProxy( ).extraParams={name:name};
					store.load();
				}
			}
		]
	    me.addEvents('itemdblclick');
		 me.callParent();
    }
    
})