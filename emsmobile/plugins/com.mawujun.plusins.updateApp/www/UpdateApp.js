/**
 * 
 */
 function UpdateApp(){
 
 }
 /**
  * UpdateApp.checkOrUpdateApp(function(){},function(){},{
  * 	��
  * });
  * @param {} successCallback
  * @param {} errorCallback
  * @param {} params��{},�ļ����ص�ַ���汾����ַ
  */
 UpdateApp.prototype.checkOrUpdateApp=function(successCallback,errorCallback,params){
 	cordova.exec(successCallback, errorCallback, "UpdateApp", "checkOrUpdateApp", [params]);
 }
 
 var updateApp=new UpdateApp();
 module.exports = updateApp;
