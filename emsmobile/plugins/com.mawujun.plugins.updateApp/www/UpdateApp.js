/**
 * 
 */
 function UpdateApp(){
 
 }
 /**检查并提示更新，如果不用更新，也会给出提示，主要用于手工工薪的时候
  * UpdateApp.manuallyUpdateApp(function(){},function(){},{
		serverVerUrl:'http://localhost:8080/apkVersion.js'
  * });
  * apkVersion.js的内容如下：{verCode:2,verName:"0.0.2",downloadFile:"http://172.16.3.10:8080/emsmobile-debug-unaligned.apk"} 这里的值是和config.xml中的version属性对应的。请看文档
  * @param {} successCallback
  * @param {} errorCallback
  * @param {} params：{},文件下载地址，版本检查地址
  */
 UpdateApp.prototype.manuallyUpdateApp=function(successCallback,errorCallback,params){
 	if(params){
 		params=[params];
 	} else {
 		params=[];
 	}
 	cordova.exec(successCallback, errorCallback, "UpdateApp", "manuallyUpdateApp", params);
 }
 /**
  * 更新程序，如果不用更新了的话，就不会给出提示，如果需要更新才会给出提示
  * @param {} successCallback
  * @param {} errorCallback
  * @param {} params
  */
 UpdateApp.prototype.autoUpdateApp=function(successCallback,errorCallback,params){
 	if(params){
 		params=[params];
 	} else {
 		params=[];
 	}
 	cordova.exec(successCallback, errorCallback, "UpdateApp", "autoUpdateApp", params);
 }
 
 var updateApp=new UpdateApp();
 module.exports = updateApp;
