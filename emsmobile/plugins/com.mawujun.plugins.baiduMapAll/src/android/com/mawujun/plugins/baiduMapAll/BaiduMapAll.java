package com.mawujun.plugins.baiduMapAll;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;

import android.content.Intent;
import android.util.Log;


public class BaiduMapAll  extends CordovaPlugin {
	private static final String STOP_ACTION = "stopGetPosition";
	private static final String GET_ACTION = "getCurrentPosition";
	private static final String NAVI_ACTION = "navi";
	public static final String LOG_TAG = "BaiduMapAll";
	

	public static LocationApplication locationApplication;
	//private LocationClient mLocationClient;
	


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
		//mLocationClient=locationApplication.mLocationClient;
		
		if (GET_ACTION.equals(action)) {
			cordova.getActivity().runOnUiThread(new Runnable() {
				@Override
				public void run() {
					//配置信息
					Log.i(LOG_TAG, "开始获取gps地址!");
					//如果需要自定义配置信息，只要重新实现下面这个方法就可以了，或者重载
					//locationApplication.initLocation();
					locationApplication.start();
					locationApplication.mLocationClient.requestLocation();
				}

			});
			return true;
		} else if (STOP_ACTION.equals(action)) {
			locationApplication.stop();
			callbackContext.success(200);
			return true;
		} else if (NAVI_ACTION.equals(action)) {
			final CordovaPlugin cordovaPlugin=this;
			cordova.getThreadPool().execute(new Runnable() {//使用这个线程，一运行就报错，一运行就报错
				@Override
				public void run() {
					Log.i(LOG_TAG, "开始调用MainActivity");
					Intent intent = new Intent().setClass(cordova.getActivity(), BNDemoMainActivity.class);  
					cordova.startActivityForResult(cordovaPlugin, intent, 1); 
		            
					//下面三句为cordova插件回调页面的逻辑代码  
	                PluginResult mPlugin = new PluginResult(PluginResult.Status.NO_RESULT);  
	                mPlugin.setKeepCallback(true);  
	  
	                callbackContext.sendPluginResult(mPlugin);  
	                callbackContext.success(200);
				}
			});
			
			
			return true;
		} else {
			Log.i(LOG_TAG, "没找到到对应的action!");
			callbackContext.error(PluginResult.Status.INVALID_ACTION.toString());
		}

		return false;
	}
	




	@Override
	public void onDestroy() {
		locationApplication.stop();
		super.onDestroy();
	}

}
