/**
 * 
 */
 function UpdateApp(){
 
 }
 /**
  * UpdateApp.checkOrUpdateApp(function(){},function(){},{
  * 	，
  * });
  * @param {} successCallback
  * @param {} errorCallback
  * @param {} params：{},文件下载地址，版本检查地址
  */
 UpdateApp.prototype.checkOrUpdateApp=function(successCallback,errorCallback,params){
 	cordova.exec(successCallback, errorCallback, "UpdateApp", "checkOrUpdateApp", [params]);
 }
 
 var updateApp=new UpdateApp();
 module.exports = updateApp;
