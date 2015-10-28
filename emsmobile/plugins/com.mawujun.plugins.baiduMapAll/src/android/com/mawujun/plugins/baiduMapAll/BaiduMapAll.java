package com.mawujun.plugins.baiduMapAll;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;

import android.util.Log;

import com.baidu.location.LocationClient;


public class BaiduMapAll  extends CordovaPlugin {
	private static final String STOP_ACTION = "stopGetPosition";
	private static final String GET_ACTION = "getCurrentPosition";
	public static final String LOG_TAG = "BaiduMapAll";
	

	private static LocationApplication locationApplication;
	private LocationClient mLocationClient;
	


	@Override
	public boolean execute(String action, JSONArray args,
			final CallbackContext callbackContext) {
		//setCallbackContext(callbackContext);
		//final LocationApplication locationApplication=(LocationApplication)cordova.getActivity().getApplication();
		if(locationApplication==null){
			locationApplication=new LocationApplication();
			locationApplication.onCreate(cordova.getActivity());
		}
		locationApplication.callbackContext=callbackContext;
		mLocationClient=locationApplication.mLocationClient;
		
		if (GET_ACTION.equals(action)) {
			cordova.getActivity().runOnUiThread(new Runnable() {
				@Override
				public void run() {
					//配置信息
					Log.i(LOG_TAG, "开始获取gps地址!");
					//如果需要自定义配置信息，只要重新实现下面这个方法就可以了，或者重载
					//locationApplication.initLocation();
					mLocationClient.start();
					//mLocationClient.requestLocation();
				}

			});
			return true;
		} else if (STOP_ACTION.equals(action)) {
			mLocationClient.stop();
			callbackContext.success(200);
			return true;
		} else {
			Log.i(LOG_TAG, "没找到到对应的action!");
			callbackContext.error(PluginResult.Status.INVALID_ACTION.toString());
		}

		return false;
	}
	




	@Override
	public void onDestroy() {
		if (mLocationClient != null && mLocationClient.isStarted()) {
			mLocationClient.stop();
		}
		super.onDestroy();
	}

}
