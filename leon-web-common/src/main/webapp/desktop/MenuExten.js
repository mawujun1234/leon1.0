/**
 * 菜单的扩展类,用来扩展显示菜单的
 */
Ext.define('Leon.desktop.MenuExten', {
    mixins: {
        observable: 'Ext.util.Observable'
    },
	isWindow:false,
	menuItem:null,//菜单按钮的对象
	
    constructor: function (config) {
        this.mixins.observable.constructor.call(this, config);
        this.init();
    },

    init: Ext.emptyFn,
    run: Ext.emptyFn
});