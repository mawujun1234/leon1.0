Ext.define('Leon.desktop.fun.FunController', {
    extend: 'Ext.app.Controller',

//    config: {
//        refs: {
//            nav: '#mainNav'
//        }
//    },
//
//    addLogoutButton: function() {
//        this.getNav().add({
//            text: 'Logout'
//        });
//    }
    models:['Fun'],
    views: ['FunTree', 'FunForm','FunPanel'],
    refs:[]
});