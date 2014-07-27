/**
 * 功能的扩展，添加自定义的怎，删，改
 * 添加右键菜单，增，删，改，并且增加工具栏，增，删，改。
 */
Ext.define('Leon.common.plugin.TreeExten', {
    extend: 'Ext.AbstractPlugin',
    alias: 'plugin.treeexten',
    


    init : function(view) {
        //view.on('render', this.onViewRender, this, {single: true});
    	var create = new Ext.Action({
		    text: '新增',
		    handler: function(){
		        Ext.Msg.alert('Click', 'You did something.');
		    },
		    iconCls: 'form-addChild-button'
		});
		var destroy = new Ext.Action({
		    text: '删除',
		    handler: function(){
		        Ext.Msg.alert('Click', 'You did something.');
		    },
		    iconCls: 'form-delete-button'
		});
		var update = new Ext.Action({
		    text: '修改',
		    handler: function(){
		        Ext.Msg.alert('Click', 'You did something.');
		    },
		    iconCls: 'form-update-button'
		});
		var copy = new Ext.Action({
		    text: '复制',
		    handler: function(){
		        Ext.Msg.alert('Click', 'You did something.');
		    },
		    iconCls: 'form-copy-button'
		});
		var paste = new Ext.Action({
		    text: '粘贴',
		    handler: function(){
		        Ext.Msg.alert('Click', 'You did something.');
		    },
		    iconCls: 'form-paste-button'
		});
		view.tbar=[create,destroy,update,copypaste];
    }

    
});