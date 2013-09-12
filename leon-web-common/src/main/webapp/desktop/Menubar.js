Ext.define('Leon.desktop.Menubar', {
    extend: 'Ext.toolbar.Toolbar', // TODO - make this a basic hbox panel...

    requires: [
        'Ext.button.Button',
        'Ext.resizer.Splitter',
        'Ext.menu.Menu',
        'Leon.desktop.QuickStartPanel'
    ],
    itemId:"desktop_menubar",
    layout: {
         overflowHandler: 'Menu'
    },
    style:{
       'z-index': 88888
    },
    initComponent: function () {
        var me = this;
        
		var newItems=[];
		newItems.push({text:'',iconCls:'icons_arrow_inout',tooltip:'快速启动',
//			handler:function(){
//				//alert("还没有做，这里弹出的是所有菜单的快捷方式，和360的一样");
//				var panel=Ext.create('Leon.desktop.QuickStartPanel',{
//					title:'快速启动'
//				});
//			}
			listeners:{
				mouseover:function(btn){
					btn.maybeShowMenu();
				}
			},
			menu:{
				xtype: 'menu',
	            plain: true,
	            items: Ext.create('Leon.desktop.QuickStartPanel',{
					
				})
			}
		});
		
		me.items=newItems.concat(me.items);
		//me.items=newItems;


        me.callParent();
    }
});