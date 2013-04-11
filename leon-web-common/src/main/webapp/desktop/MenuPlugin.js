/**
 * 更新菜单内容的例子
 */
Ext.define('Leon.desktop.example.MenuPlugin', {
	extend:'Ext.AbstractPlugin',
    alias: 'plugin.menuplugin',
    url:null,//扩展菜单的js文件地址
    scripts:null,//javascript脚本
    
    init: function(btn) {
    	var me=this;
    	if(me.url){
    		var className=me.url.substring(1,me.url.length-3);
    		className='Leon.'+className.split('/').join('.');
    		var obj=Ext.create(className,{menuItem:btn});
    	}
     	//btn.setText(btn.getText()+'(111)'+this.url);
    }
});