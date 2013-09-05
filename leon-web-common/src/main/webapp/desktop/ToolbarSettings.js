Ext.define('Leon.desktop.ToolbarSettings', {
    extend: 'Ext.window.Window',

    uses: [
        'Ext.tree.Panel',
        'Ext.tree.View',
        'Ext.form.field.Checkbox',
        'Ext.layout.container.Anchor',
        'Ext.layout.container.Border',

        'Leon.desktop.Wallpaper',

        'Leon.desktop.WallpaperModel'
    ],

    layout: 'anchor',
    title: '工具栏设置',
    modal: true,
    width: 640,
    height: 480,
    border: false,

    initComponent: function () {
        var me = this;
        var desktop=me.desktop;
        //alert(desktop.menubarDock);
        me.menubarDock=desktop.menubarDock;
        me.taskbarDock=desktop.taskbarDock;
        me.menubarAutoHide=desktop.menubarAutoHide;
        me.taskbarAutoHide=desktop.taskbarAutoHide;
        
        var menubarDockRadio=Ext.create('Ext.form.RadioGroup',{
	        items: [
	            { boxLabel: '上', name: 'menubarDock', inputValue: 'top' , checked: true},
	            { boxLabel: '下', name: 'menubarDock', inputValue: 'bottom'},
	            { boxLabel: '左', name: 'menubarDock', inputValue: 'left' },
	            { boxLabel: '右', name: 'menubarDock', inputValue: 'right' }
	        ],
	        value:{menubarDock:me.menubarDock},
	        listeners:{
	        	change:function( radioGroup, newValue, oldValue, eOpts ) {
	        		me.preview.getDockedComponent("menubar").setDocked(newValue.menubarDock);
	        		me.preview.doLayout();
	        		me.menubarDock=newValue.menubarDock;
	        	}
	        }
        });
         var taskbarDockRadio=Ext.create('Ext.form.RadioGroup',{
	        items: [
	            { boxLabel: '上', name: 'taskbarDock', inputValue: 'top' },
	            { boxLabel: '下', name: 'taskbarDock', inputValue: 'bottom', checked: true},
	            { boxLabel: '左', name: 'taskbarDock', inputValue: 'left' },
	            { boxLabel: '右', name: 'taskbarDock', inputValue: 'right' }
	        ],
	        value:{taskbarDock:me.taskbarDock},
	        listeners:{
	        	change:function( radioGroup, newValue, oldValue, eOpts ) {
	        		me.preview.getDockedComponent("taskbar").setDocked(newValue.taskbarDock);
	        		me.preview.doLayout();
	        		me.taskbarDock=newValue.taskbarDock;
	        	}
	        }
        });

		me.tbar=['菜单栏:',menubarDockRadio,'-',{
                    boxLabel  : '自动隐藏',
                    xtype:'checkbox',
                    name      : 'menubarAutoHide',
                    checked:me.menubarAutoHide,
                    listeners:{
			        	change:function( checkbox, newValue, oldValue, eOpts ) {
			        		me.menubarAutoHide=newValue;
			        	}
			        }
                },
                '-','-','-','任务栏:',taskbarDockRadio,{
                    boxLabel  : '自动隐藏',
                    xtype:'checkbox',
                    name      : 'taskbarAutoHide',
                    checked:me.taskbarAutoHide,
                    listeners:{
			        	change:function( checkbox, newValue, oldValue, eOpts ) {
			        		me.taskbarAutoHide=newValue;
			        	}
			        }
                }];

        me.buttons = [
            { text: 'OK', handler: me.onOK, scope: me },
            { text: 'Cancel', handler: me.close, scope: me }
        ];
        
        me.preview=Ext.create('Ext.panel.Panel',{
        	dockedItems:[{
        		xtype: 'toolbar',
        		itemId:'menubar',
		        dock: me.menubarDock,
		        items: [{
		            text: '菜单栏'
		        }]
        	},{
        		xtype: 'toolbar',
        		itemId:'taskbar',
		        dock: me.taskbarDock,
		        items: [{
		            text: '任务栏'
		        }]
        	}],
        	items:[{
        		html:'这里是工作台'
        	}]
        });

        me.items = [
            {
                anchor: '0 -30',
                border: false,
                layout: 'border',
                items: [
                    me.tree,
                    {
                        xtype: 'panel',
                        title: '预览',
                        region: 'center',
                        layout: 'fit',
                        items: [ me.preview ]
                    }
                ]
            }
        ];

        me.callParent();
    },

    

    onOK: function () {
        var me = this;
        me.desktop.setBarDocked(me.menubarDock, me.taskbarDock);
        me.desktop.setBarHide(me.menubarAutoHide, me.taskbarAutoHide);
        me.desktop.createOrupdateDesktopConfig({},me.desktop.reload);
        me.destroy();
    }
});
