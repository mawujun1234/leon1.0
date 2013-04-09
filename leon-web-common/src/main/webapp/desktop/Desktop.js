Ext.define('Leon.desktop.Desktop', {
	extend: 'Ext.container.Viewport',
	requires: [
	     'Leon.desktop.MenuBar',
	     'Leon.desktop.TaskBar',
	     'Leon.desktop.Window'
	],
	layout:'fit',
    initComponent: function () {
        var me = this;
        //me.html="测试内容";
        //me.tbar={items:[{text:'测试'}]};
        var menuBar=Ext.create('Leon.desktop.MenuBar',{
        	items:[{iconCls: 'menuBar-index-button',xtype:'button',text:'桌面'}
        	,{text:'测试',handler:function(){
        		var win=Ext.create('Leon.desktop.Window',{
        			
        		});
        		panel.add(win);//这行很重要，防止window覆盖了工具栏
        		win.show();
        		
        	}}]
        });
        var taskBar=Ext.create('Leon.desktop.TaskBar',{
        	items:[{iconCls: 'taskBar-index-button',text:'任务栏：'}]
        });
        var panel=Ext.create('Ext.panel.Panel',{
        	xtype:'panel',
        	id:'111',
        	tbar:menuBar,
        	bbar:taskBar
        });
        me.items=[panel];

        me.callParent();
    }
});