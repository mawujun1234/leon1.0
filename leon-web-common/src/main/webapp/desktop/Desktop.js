Ext.define('Leon.desktop.Desktop', {
	extend: 'Ext.container.Viewport',
	requires: [
	     'Leon.desktop.MenuBar',
	     'Leon.desktop.TaskBar'
	],
	layout:'fit',
    initComponent: function () {
        var me = this;
        //me.html="测试内容";
        //me.tbar={items:[{text:'测试'}]};
        var menuBar=Ext.create('Leon.desktop.MenuBar',{
        	items:[{iconCls: 'menuBar-index-button',xtype:'button',text:'桌面'}]
        });
        var taskBar=Ext.create('Leon.desktop.TaskBar',{
        	items:[{iconCls: 'taskBar-index-button',text:'任务栏：',xtype:''}]
        });
        me.items=[{
        	xtype:'panel',
        	tbar:menuBar,
        	bbar:taskBar
        }];

        me.callParent();
    }
});