Ext.define('Leon.desktop.QuickStartPanel', {
    extend: 'Ext.panel.Panel',
    shortcutTpl: [
        '<tpl for=".">',
            '<div class="ux-desktop-shortcut" id="{menuItemId}-shortcut">',
                '<div class="ux-desktop-shortcut-icon {iconCls32}">',
                    '<img src="',Ext.BLANK_IMAGE_URL,'" title="{text}">',
                '</div>',
                '<span class="ux-desktop-shortcut-text">{text}</span>',
            '</div>',
        '</tpl>',
        '<div class="x-clear"></div>'
    ],
    shortcutItemSelector: 'div.ux-desktop-shortcut',
    btn:null,
    width:390,
    height:260,
    frame:true,
    initComponent: function () {
        var me = this;
        
        me.createShortcutsStore();
        delete me.quickstarts
        var item=me.createDataView();
		me.items=[item];

		
		
		
//		me.on('render',function(panel){
//			panel.getEl().on('mouseleave',function(){
//				//me.btn.hideMenu();
//			});
//		});
        me.callParent();
    },
    createShortcutsStore:function(){
    	var me=this;
    	var data=this.quickstarts;
    	//.break 这里进行数据匹配，病显示在桌面上，注意要使用32位的图标
    	var myStore = Ext.create('Ext.data.Store', {
		    fields: [
		       { name: 'text' },
		       { name: 'iconCls' },
		       { name: 'iconCls32' },
		       { name: 'url' },
		       { name: 'menuItemId' },
		       { name: 'funId' }
		    ],
		    data:me.quickstarts
		});
    	this.shortcuts=myStore;
    	
    	this.bbar=Ext.create('Ext.toolbar.Paging',{
			store: myStore,
			border:false,
			displayInfo: true
		});
    	return myStore;
    },
    createDataView: function () {
        var me = this;
        return {
            xtype: 'dataview',
            overItemCls: 'x-item-over',
            trackOver: true,
            itemSelector: me.shortcutItemSelector,
            store: me.shortcuts,
            style: {
                position: 'absolute'
            },
            x: 0, y: 0,
            tpl: new Ext.XTemplate(me.shortcutTpl),
            listeners:{
            	'itemclick':function(view,record,item){
            		//console.dir(record.data);
            		var params=record.data;
            		params.title=params.text;
					me.desktop.createWindow(record.data);
//					me.createWindow({
//		        			title:btn.text,
//		        			menuItemId:btn.menuItemId,
//		        			funId:btn.funId,
//		        			url:btn.link_url,
//		        			iconCls:btn.iconCls
//		        		});
				}
            }
        };
    }
});