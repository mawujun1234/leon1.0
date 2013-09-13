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
    width:300,
    height:200,
//    shadow:false,
//    bodyBorder :false,
//    border:false,
//    bodyStyle:{
//    	//background:'transparent',
//    	//background: 'red',
//    	"opacity": 10,
//    	"filter": "alpha(opacity=10)"
//    },
    frame:true,
    initComponent: function () {
        var me = this;
        
        me.createShortcutsStore();
        var item=me.createDataView();
		me.items=[item];
        me.callParent();
    },
    createShortcutsStore:function(){
    	var myStore = Ext.create('Ext.data.Store', {
		    fields: [
		       { name: 'name' },
		       { name: 'iconCls' },
		       { name: 'module' }
		    ],
		    data:[{name:'快捷方式',iconCls:'pngs_32_20110213231934121',module:'1'},{name:'快捷方式',iconCls:'pngs_32_20110213231934121',module:'1'}]
		});
    	this.shortcuts=myStore;
    },
    createDataView: function () {
        var me = this;
        return {
            xtype: 'dataview',
            overItemCls: 'x-view-over',
            trackOver: true,
            itemSelector: me.shortcutItemSelector,
            store: me.shortcuts,
            style: {
                position: 'absolute'
            },
            x: 0, y: 0,
            tpl: new Ext.XTemplate(me.shortcutTpl)
        };
    }
});