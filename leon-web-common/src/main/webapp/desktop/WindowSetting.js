/**
 * 弹出窗的个性化设置
 */
Ext.define('Leon.desktop.WindowSettings', {
    extend: 'Ext.window.Window',

    uses: [
    ],

    layout: 'anchor',
    title: '自定义设置',
    modal: true,
    width: 640,
    height: 480,
    border: false,

    initComponent: function () {
        var me = this;
		var form=me.createForm();

        me.buttons = [
            { text: 'OK', handler: me.onOK, scope: me },
            { text: 'Cancel', handler: me.close, scope: me }
        ];

        me.items = [
           
        ];

        me.callParent();
    },

    createForm : function() {
        var me = this;

       
        return tree;
    },


    onOK: function () {
        var me = this;
        if (me.selected) {
        	//发送到后台保存
//        	Ext.Ajax.request({
//        		url:"/app//desktop/createOrUpdate",
//        		method:"POST",
//        		params:{
//        			wallpaper:me.selected,
//        			wallpaperStretch:me.stretch
//        		},
//        		success:function(){
//        			me.desktop.setWallpaper(me.selected, me.stretch);
//        		}
//        	});
        	me.desktop.setWallpaper(me.selected, me.stretch);
        	me.desktop.createOrupdateDesktopConfig();
            
        }
        me.destroy();
    },

    onSelect: function (tree, record) {
        var me = this;

        if (record.raw.img) {
            me.selected = 'desktop/wallpapers/' + record.raw.img;
        } else {
            me.selected = Ext.BLANK_IMAGE_URL;
        }

        me.preview.setWallpaper(me.selected);
    }
});
