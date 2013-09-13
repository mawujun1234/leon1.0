Ext.define('Leon.desktop.QuickStartPanel', {
    extend: 'Ext.panel.Panel',
    shortcutTpl: [
        '<tpl for=".">',
            '<div class="ux-desktop-shortcut" id="{name}-shortcut">',
                '<div class="ux-desktop-shortcut-icon {iconCls}">',
                    '<img src="',Ext.BLANK_IMAGE_URL,'" title="{name}">',
                '</div>',
                '<span class="ux-desktop-shortcut-text">{name}</span>',
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
    	var myStore = Ext.create('Ext.data.Store', {
		    fields: [
		       { name: 'name' },
		       { name: 'iconCls' },
		       { name: 'module' }
		    ],
		    data:[{name:'快捷方式1',iconCls:'pngs_32_20110213231934121',module:'1'},
		    	{name:'快捷方式2',iconCls:'pngs_32_20110213231934121',module:'1'},
		    	{name:'快捷方式3',iconCls:'pngs_32_20110213231934121',module:'1'},
		    	{name:'快捷方式4',iconCls:'pngs_32_20110213231934121',module:'1'},
		    	{name:'快捷方式5',iconCls:'pngs_32_20110213231934121',module:'1'},
		    	{name:'快捷方式6',iconCls:'pngs_32_20110213231934121',module:'1'},
		    	{name:'快捷方式7',iconCls:'pngs_32_20110213231934121',module:'1'},
		    	{name:'快捷方式8',iconCls:'pngs_32_20110213231934121',module:'1'},
		    	{name:'快捷方式9',iconCls:'pngs_32_20110213231934121',module:'1'},
		    	{name:'快捷方式10',iconCls:'pngs_32_20110213231934121',module:'1'},
		    	{name:'快捷方式11',iconCls:'pngs_32_20110213231934121',module:'1'},
		    	{name:'快捷方式12',iconCls:'pngs_32_20110213231934121',module:'1'}]
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
            	'itemclick':function(){
					alert('弹出桌面');
				}
            }
        };
    }
});