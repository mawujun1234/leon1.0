package com.mawujun.plugins.baiduMapAll;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;


public class BaiduMapAll  extends CordovaPlugin {
	private static final String STOP_ACTION = "stopGetPosition";
	private static final String GET_ACTION = "getCurrentPosition";
	private static final String NAVI_ACTION = "navi";
	public static final String LOG_TAG = "BaiduMapAll";
	

	//public static LocationApplication locationApplication;
	//private LocationClient mLocationClient;
	


	@Override
	public boolean execute(String action, final JSONArray args,
			final CallbackContext callbackContext) {

//		//acquireWakeLock();
//		if(locationApplication==null){
//			locationApplication=new LocationApplication();	
//		}
//		try {
////			locationApplication.setSessionId(args.getString(0));
////			locationApplication.setLoginName(args.getString(1));
////			locationApplication.setUuid(args.getString(2));
//			JSONObject params= args.getJSONObject(0);
//			locationApplication.setUploadUrl(params.getString("uploadUrl"));
//			locationApplication.setGps_interval(params.getInt("gps_interval"));
//			locationApplication.setParams(params);
//			
//			
//			locationApplication.onCreate(cordova.getActivity());
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			callbackContext.error("传递的参数有问题!"+e.getMessage());
//			return false;
//		}
		//locationApplication.callbackContext=callbackContext;
		
		if (GET_ACTION.equals(action)) {
			cordova.getActivity().runOnUiThread(new Runnable() {
				@Override
				public void run() {
					//配置信息
					Log.i(LOG_TAG, "开始获取gps地址!");
					//
//					//如果需要自定义配置信息，只要重新实现下面这个方法就可以了，或者重载
//					//locationApplication.initLocation();
//					locationApplication.start();
//					locationApplication.mLocationClient.requestLocation();
//					callbackContext.success("success");
					
					acquireWakeLock();
					try {
					JSONObject params= args.getJSONObject(0);
					Intent intent=new Intent(cordova.getActivity(), LocationApplication.class);
					intent.putExtra("uploadUrl", params.getString("uploadUrl"));
					intent.putExtra("gps_interval", params.getInt("gps_interval"));
					intent.putExtra("params", params.toString());
					
					
					//initGPS();
					cordova.getActivity().startService(intent);
					
					
					callbackContext.success("success");
					
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						Toast.makeText(cordova.getActivity(), "启动定位失败，参数有问题!", Toast.LENGTH_LONG).show();
						callbackContext.error("传递的参数有问题!"+e.getMessage());
					}
				}

			});
			return true;
		} else if (STOP_ACTION.equals(action)) {
			//locationApplication.stop();
			cordova.getActivity().stopService(new Intent(cordova.getActivity(), LocationApplication.class));
			callbackContext.success(200);
			return true;
		} else if (NAVI_ACTION.equals(action)) {
			final CordovaPlugin cordovaPlugin=this;
			cordova.getThreadPool().execute(new Runnable() {//使用这个线程，一运行就报错，一运行就报错
				@Override
				public void run() {
					Log.i(LOG_TAG, "开始调用MainActivity");
					String longitude=null;
					String latitude=null;
					try {
						longitude=args.getString(0);
						latitude=args.getString(1);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						Log.e(LOG_TAG, e.getMessage());
						callbackContext.error(PluginResult.Status.ERROR.toString());
					}
					
					Intent intent = new Intent().setClass(cordova.getActivity(), BNDemoMainActivity.class);  
					intent.putExtra("com.mawujun.plugins.baiduMapAll.longitude", longitude);
					intent.putExtra("com.mawujun.plugins.baiduMapAll.latitude", latitude);
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
	
//	private void initGPS(){ 
//        LocationManager locationManager=(LocationManager) cordova.getActivity().getSystemService(Context.LOCATION_SERVICE); 
//
//        //判断GPS模块是否开启，如果没有则开启 
//        if(!locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)){ 
////         Toast.makeText( cordova.getActivity(), "GPS没有打开，请打开它!", Toast.LENGTH_SHORT).show(); 
////         //转到手机设置界面，用户设置GPS
////         Intent intent=new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS); 
////         cordova.getActivity().startActivityForResult(intent,0); //设置完成后返回到原来的界面
//         
//			AlertDialog.Builder dialog = new AlertDialog.Builder(cordova.getActivity());
//			dialog.setMessage("请打开GPS!!");
//			dialog.setPositiveButton("确定",
//					new android.content.DialogInterface.OnClickListener() {
//
//						@Override
//						public void onClick(DialogInterface arg0, int arg1) {
//
//							// 转到手机设置界面，用户设置GPS
//							Intent intent = new Intent(
//									Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//							cordova.getActivity().startActivityForResult(
//									intent, 0); // 设置完成后返回到原来的界面
//
//						}
//					});
//			dialog.setNeutralButton("取消",
//					new android.content.DialogInterface.OnClickListener() {
//
//						@Override
//						public void onClick(DialogInterface arg0, int arg1) {
//							arg0.dismiss();
//						}
//					});
//			dialog.show();
//         
//        } 
//        else { 
//        	
//        } 
//   } 
	




	@Override
	public void onDestroy() {
		//locationApplication.stop();
		releaseWakeLock();
		cordova.getActivity().stopService(new Intent(cordova.getActivity(), LocationApplication.class));
		super.onDestroy();
	}
	
	 WakeLock wakeLock; 
		private void acquireWakeLock() {
			if (null == wakeLock) {
				PowerManager pm = (PowerManager) cordova.getActivity().getSystemService(Context.POWER_SERVICE);
				wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK
						| PowerManager.ON_AFTER_RELEASE, getClass()
						.getCanonicalName());
				if (null != wakeLock) {
					// Log.i(TAG, "call acquireWakeLock");
					wakeLock.acquire();
				}
			}
		}

		// 释放设备电源锁
		private void releaseWakeLock() {
			if (null != wakeLock && wakeLock.isHeld()) {
				// Log.i(TAG, "call releaseWakeLock");
				wakeLock.release();
				wakeLock = null;
			}
		}
	

}
