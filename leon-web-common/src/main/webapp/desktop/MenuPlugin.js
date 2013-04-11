/**
 * 更新菜单内容的例子，
 * 使用方法如下：
 * plugins:[{ptype:'menuplugin',url:'/desktop/example/MenuPluginUpdateText.js'}],
 * 或
 * plugins:[{ptype:'menuplugin',scripts:'function run(){menuItem.setText(menuItem.getText( ) +"(scripts)")}'}]
 * 
 * 在使用scripts的时候,函数名称可以是匿名，也可以是任何其他名称，函数中可以使用变量menuItem，即当前menuItem
 * 还可以使用me变量，me指向的时插件，原理是使用了闭包。
 */
Ext.define('Leon.desktop.example.MenuPlugin', {
	extend:'Ext.AbstractPlugin',
    alias: 'plugin.menuplugin',
    url:null,//扩展菜单的js文件地址
    scripts:null,//javascript脚本
    
    init: function(menuItem) {
    	var me=this;
    	if(me.url){
    		var className=me.url.substring(1,me.url.length-3);
    		className='Leon.'+className.split('/').join('.');
    		var obj=Ext.create(className,{menuItem:menuItem});
//    		alert(me.url);
//    		 Ext.Loader.loadScript({
//    			url:'..'+me.url,
//    			onLoad:function(options){
//    				//eval(me.scripts)
//    				run();
//    				alert(options);
//    			},
//    			onError:function(){
//    				alert('url文档加载失败!');
//    				return;
//    			}
//    		});
    	} else if(me.scripts){
    		//var scripts=eval('(function(menuItem){'+me.scripts+'})(btn)');
    		eval('('+me.scripts+')();');
    	}
    }
});