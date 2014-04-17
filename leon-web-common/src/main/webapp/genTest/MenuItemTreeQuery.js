/**
 * 功能的扩展，添加自定义的怎，删，改
 * 添加右键菜单，增，删，改，并且增加工具栏，增，删，改。
 * 后台的类最好继承TreeNode类，这样就可以少写很多代码
 */
Ext.define('Leon.genTest.MenuItemTreeQuery', {
    extend: 'Ext.tree.Panel',
    requires:['Leon.genTest.MenuItem'],
    model:'Leon.genTest.MenuItem',
    initComponent: function () {
		var me = this;

        me.store = Ext.create('Ext.data.TreeStore', {
	       	autoLoad:true,
	       	nodeParam :'id',//传递到后台的数据，默认是node
	       	model:me.model,
			root: {
			    expanded: true,
			    text:"根节点" 
			}
		});
       
		me.callParent(arguments);
    },

    getLastSelected:function(){
    	return this.getSelectionModel( ).getLastSelected( );
    }
    
});
