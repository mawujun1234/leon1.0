//var cordova = require('cordova');

function CallActivityPlugin() {

}
CallActivityPlugin.prototype.execute=function(action,className, successCallback, errorCallback) {
		cordova.exec(     
			successCallback, 
			function(err){alert("failed>>" + message);},
			"CallActivityPlugin",
			action,
			[className]
		)
};

CallActivityPlugin.prototype.callActivityPlugin=function(className,successCallback, errorCallback) {
		this.execute("callActivityPlugin",className, successCallback, errorCallback);
};
var CallActivityPlugin=new CallActivityPlugin();
module.exports = CallActivityPlugin;