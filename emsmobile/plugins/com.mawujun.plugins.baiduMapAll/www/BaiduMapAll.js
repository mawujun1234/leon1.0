//var cordova = require('cordova');

function BaiduMapAll() {

}
BaiduMapAll.prototype.execute=function(action, successCallback, errorCallback) {
		cordova.exec(     
			function(pos) {
				var errorcode = pos.errorcode;
				if (errorcode == 61 || errorcode == 65 || errorcode == 161) {
					successCallback(pos);
				} else {
					if (typeof(errorCallback) != "undefined") {
						if (errorcode >= 162) {
							errorcode = 162;
						}
						errorCallback(pos)
					};
				}
			}, 
			function(err){},
			"BaiduMapAll",
			action,
			[]
		)
};

BaiduMapAll.prototype.getCurrentPosition=function(successCallback, errorCallback) {
		this.execute("getCurrentPosition", successCallback, errorCallback);
};
BaiduMapAll.prototype.stopGetPosition= function(action, successCallback, errorCallback) {
		this.execute("stopGetPosition", successCallback, errorCallback);
}
var baiduMapAll=new BaiduMapAll();
module.exports = baiduMapAll;