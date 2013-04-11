/**
 * 菜单初始化时，更新菜单显示的例子，修改了菜单的内容，同时生产了子菜单
 */
Ext.define('Leon.desktop.example.MenuPluginUpdateText', {
   extend:'Leon.desktop.MenuExten',

   init: function(){
   		var me=this;
   		
   		//修改名称
    	me.menuItem.setText(me.menuItem.getText( ) +"(111)");
    	
    	//子菜单
    	var child=Ext.create('Ext.menu.Menu', {
		    //width: 100,
		   // height: 100,
		   // floating: false,  // usually you want this set to True (default)
		    //renderTo: Ext.getBody(),  // usually rendered by it's containing component
		    items: [{
		        text: 'icon item',
		        iconCls: 'add16'
		    },{
		        text: 'text item'
		    },{
		        text: 'plain item'
		    }]
		});
		me.menuItem.menu=child;
		//me.menuItem.doComponentLayout();
   }
});