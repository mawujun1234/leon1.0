package com.mawujun.plugins.baiduMapAll;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;


public class BaiduMapAll  extends CordovaPlugin {
	private static final String STOP_ACTION = "stopGetPosition";
	private static final String GET_ACTION = "getCurrentPosition";
	private static final String NAVI_ACTION = "navi";
	public static final String LOG_TAG = "BaiduMapAll";
	
	//public static final String loc_action = "com.mawujun.plugins.baiduMapAll.LocationApplication";
	//public static LocationApplication locationApplication;
	//private LocationClient mLocationClient;
	
	
//    public static Double  currentLongitude;
//    public static Double  currentLatitude;
//    public static Long  current_loc_time;//最近一次的提交时间
//
//    public static String uploadUrl;
//    public static int gps_interval=0;
//    public static JSONObject params;//需要传递到后台的数据
	


	@Override
	public boolean execute(String action, final JSONArray args,
			final CallbackContext callbackContext) {

		
		if (GET_ACTION.equals(action)) {
			
//			try {
//				params= args.getJSONObject(0);
//				BaiduMapAll.uploadUrl=params.getString("uploadUrl");
//				BaiduMapAll.gps_interval=params.getInt("gps_interval");
//			} catch (JSONException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//			
			
			cordova.getActivity().runOnUiThread(new Runnable() {
				@Override
				public void run() {
					//配置信息
					Log.i(LOG_TAG, "开始获取gps地址!");

					
					acquireWakeLock();
					try {
					JSONObject params= args.getJSONObject(0);
					Intent intent=new Intent(cordova.getActivity(), LocationApplication.class);
					intent.putExtra("uploadUrl", params.getString("uploadUrl"));
					intent.putExtra("gps_interval", params.getInt("gps_interval"));
					intent.putExtra("params", params.toString());
					MyLog.i(BaiduMapAll.LOG_TAG,  params.getInt("gps_interval")+"=111111111");
					
					
					//initGPS();
					cordova.getActivity().startService(intent);
					
						
//					MyLog.i(LOG_TAG, "start alarm");
//
//					
//					
//					AlarmManager am = (AlarmManager)cordova.getActivity().getSystemService(Context.ALARM_SERVICE);
//
//					Intent intent=new Intent(cordova.getActivity(), LocationApplication.class);
//					intent.setAction(loc_action);
//					//intent.putExtra("uploadUrl", params.getString("uploadUrl"));
//					//intent.putExtra("gps_interval", params.getInt("gps_interval"));
//					//intent.putExtra("params", params.toString());
//					
//					//String params_str=intent.getStringExtra("params");
//					
//					
//					PendingIntent pendingIntent = PendingIntent.getService(cordova.getActivity(), 0, intent,PendingIntent.FLAG_UPDATE_CURRENT);
//					
//					
//					//am.cancel(collectSender);
//					am.setRepeating(AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime(),params.getInt("gps_interval"), pendingIntent);
					
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

	




	@Override
	public void onDestroy() {
		//locationApplication.stop();
		releaseWakeLock();
		cordova.getActivity().stopService(new Intent(cordova.getActivity(), LocationApplication.class));
		//stopPollingService(cordova.getActivity());
		super.onDestroy();
	}
	
//	public static void stopPollingService(Context context) {
//		MyLog.i(BaiduMapAll.LOG_TAG, "stopPollingService()方法中停止了定时启动!");
//		Log.i("ServiceUtil-AlarmManager", "cancleAlarmManager to start ");  
//	    Intent intent = new Intent(context,LocationApplication.class);  
//	    intent.setAction(loc_action);  
//	    PendingIntent pendingIntent=PendingIntent.getService(context, 0, intent,PendingIntent.FLAG_UPDATE_CURRENT);  
//	    AlarmManager alarm=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);  
//	    alarm.cancel(pendingIntent);  
//	}
	
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
