package com.mawujun.plugins.baiduMapAll;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.provider.Settings;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.baidu.platform.comapi.location.CoordinateType;

/**
 * 主Application，所有百度定位SDK的接口说明请参考线上文档：http://developer.baidu.com/map/loc_refer/index.html
 *
 * 百度定位SDK官方网站：http://developer.baidu.com/map/index.php?title=android-locsdk
 */
//public class LocationApplication extends Application {
public class LocationApplication extends Service{
    public LocationClient mLocationClient;
    public MyLocationListener mMyLocationListener;
    //
    
   // public CallbackContext callbackContext;
    
    public Double  currentLongitude;
    public Double  currentLatitude;

    private String uploadUrl;
    private int gps_interval=0;
    private JSONObject params;//需要传递到后台的数据
    //public Vibrator mVibrator;

    public JSONObject getParams() {
		return params;
	}
	public void setParams(JSONObject params) {
		this.params = params;
	}
	public int getGps_interval() {
		return gps_interval;
	}
	public void setGps_interval(int gps_interval) {
		this.gps_interval = gps_interval;
	}
	public String getUploadUrl() {
		return uploadUrl;
	}
	public void setUploadUrl(String uploadUrl) {
		this.uploadUrl = uploadUrl;
	}


	private static final Map<Integer, String> ERROR_MESSAGE_MAP = new HashMap<Integer, String>();

	//private static final String DEFAULT_ERROR_MESSAGE = "服务端定位失败";
    //Context activityContex;
    private Handler handler;  
    
	
	static {
		ERROR_MESSAGE_MAP.put(61, "GPS定位结果，GPS定位成功");
		ERROR_MESSAGE_MAP.put(62, "无法获取有效定位依据，定位失败，请检查运营商网络或者wifi网络是否正常开启，尝试重新请求定位。");
		ERROR_MESSAGE_MAP.put(63, "网络异常，没有成功向服务器发起请求，请确认当前测试手机网络是否通畅，尝试重新请求定位。");
		ERROR_MESSAGE_MAP.put(65, "定位缓存的结果");
		ERROR_MESSAGE_MAP.put(66, "离线定位结果。通过requestOfflineLocaiton调用时对应的返回结果");
		ERROR_MESSAGE_MAP.put(67, "离线定位失败。通过requestOfflineLocaiton调用时对应的返回结果");
		ERROR_MESSAGE_MAP.put(68, "网络连接失败时，查找本地离线定位时对应的返回结果。");
		ERROR_MESSAGE_MAP.put(161, "表示网络定位结果，网络定位定位成功。");
		
		ERROR_MESSAGE_MAP.put(162, "请求串密文解析失败。");
		ERROR_MESSAGE_MAP.put(167, "服务端定位失败，请您检查是否禁用获取位置信息权限，尝试重新请求定位。");
		ERROR_MESSAGE_MAP.put(502, "key参数错误，请按照说明文档重新申请KEY。");
		ERROR_MESSAGE_MAP.put(505, "key不存在或者非法，请按照说明文档重新申请KEY。");
		ERROR_MESSAGE_MAP.put(601, "key服务被开发者自己禁用，请按照说明文档重新申请KEY。");
		ERROR_MESSAGE_MAP.put(602, " key mcode不匹配，您的ak配置过程中安全码设置有问题，请确保：sha1正确");
	};
	
//	public void start(){
//		mLocationClient.start();
//	}
//	public void stop(){
//		if (mLocationClient != null && mLocationClient.isStarted()) {
//			mLocationClient.stop();
//		}
//	}

//	/**
//	 * 初始化定位代码
//	 * @param context
//	 */
//    public void onCreate() {
//    	//if(mLocationClient==null){
//    		 mLocationClient = new LocationClient(this.getBaseContext());
//    	     mMyLocationListener = new MyLocationListener();
//    	     mLocationClient.registerLocationListener(mMyLocationListener);
//    	    //this.activityContex=context;  
//    	    //不初始化，不能计算距离
//    	    SDKInitializer.initialize(this.getBaseContext().getApplicationContext());
//    	    
//    	    //initLocation();
//    	//}
//        //mVibrator =(Vibrator)getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
//    }
	
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        mLocationClient = new LocationClient(this.getApplicationContext());
//        mMyLocationListener = new MyLocationListener();
//        mLocationClient.registerLocationListener(mMyLocationListener);
//        
//        initLocation();
//        //mVibrator =(Vibrator)getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
//    }
    
	public void initLocation() {
		//配置参数是可以每次定位的时候都不同的
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);//可选，默认false,设置是否使用gps
		//高精度定位模式：这种定位模式下，会同时使用网络定位和GPS定位，优先返回最高精度的定位结果；
		//低功耗定位模式：这种定位模式下，不会使用GPS，只会使用网络定位（Wi-Fi和基站定位）；
		//仅用设备定位模式：这种定位模式下，不需要连接网络，只使用GPS进行定位，这种模式下不支持室内环境的定位。
		option.setLocationMode(LocationMode.Hight_Accuracy);////可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setScanSpan(this.getGps_interval());//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(false);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(false);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认false，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要

		option.setCoorType(CoordinateType.BD09LL);// 返回的定位结果是百度经纬度，默认值gcj02,//wgs84:国际经纬度坐标  "gcj02":国家测绘局标准,"bd09ll":百度经纬度标准,"bd09":百度墨卡托标准
		option.setProdName("BaiduLoc");
		mLocationClient.setLocOption(option);
	}


    /**
     * 实现实时位置回调监听
     */
    public class MyLocationListener implements BDLocationListener {
    	
        @Override
        public void onReceiveLocation(BDLocation location) {
        	//JSONObject jsonObj = new JSONObject();
        	if (location == null) {
        		//Toast.makeText(activityContex, "没有获取到经纬度数据!", Toast.LENGTH_LONG).show();
        		toast("没有获取到经纬度数据!",Toast.LENGTH_LONG);
        		return;
        	}
        	
			
			if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
				 postCoords(location);
				 
				 currentLongitude=location.getLongitude();
					currentLatitude=location.getLatitude();

			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
				 postCoords(location);
				 
				 currentLongitude=location.getLongitude();
				 currentLatitude=location.getLatitude();
			} else {
				int locationType = location.getLocType();
				//Toast.makeText(activityContex, "获取地址失败:"+getErrorMessage(locationType), Toast.LENGTH_LONG).show();
				toast("获取地址失败:"+getErrorMessage(locationType),Toast.LENGTH_LONG);
			}

        }


    }
    
    SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public void postCoords(BDLocation location){
    	//如果没有上传路径就不进行上传
    	if(this.getUploadUrl()==null || "".equals(this.getUploadUrl()) ){
    		return;
    	}
    	
    	Double distance=0.0;
    	if(currentLatitude!=null){
    		//上次的经纬度
        	LatLng pt1 = new LatLng(currentLatitude, currentLongitude);
        	//本次经纬度
        	LatLng pt2 = new LatLng(location.getLatitude(), location.getLongitude());
        	//计算p1、p2两点之间的直线距离，单位：米  
        	distance=DistanceUtil. getDistance(pt1, pt2);
    	}
    
    	
    	
		
		
		
		
		
		HttpResponse httpResponse;
		try {
			HttpPost httpPost = new HttpPost(this.getUploadUrl());
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
//			params.add(new BasicNameValuePair("sessionId", this.getSessionId()));
//			params.add(new BasicNameValuePair("loginName", this.getLoginName()));
//			params.add(new BasicNameValuePair("uuid", this.getUuid()));
			
			Iterator<String> iterator=params.keys();
			while(iterator.hasNext()){
				String key=iterator.next();
				if("uploadUrl".equals(key) || "gps_interval".equals(key)){
					continue;
				}
				nameValuePairs.add(new BasicNameValuePair(key, params.getString(key)));
			}
			nameValuePairs.add(new BasicNameValuePair("longitude", location.getLongitude()+""));
			nameValuePairs.add(new BasicNameValuePair("latitude", location.getLatitude()+""));
			nameValuePairs.add(new BasicNameValuePair("radius", location.getRadius()+""));
			nameValuePairs.add(new BasicNameValuePair("direction", location.getDirection()+""));
			nameValuePairs.add(new BasicNameValuePair("speed", location.getDirection()+""));
			//nameValuePairs.add(new BasicNameValuePair("loc_time",location.getTime()));//不使用这个，因为百度会缓存金维度
			nameValuePairs.add(new BasicNameValuePair("loc_time",format.format(new Date())));//String，时间，ex:2010-01-01 14:01:01
			nameValuePairs.add(new BasicNameValuePair("distance", distance+""));
			
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8));
			
			
			HttpParams httpParameters = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParameters, 50000);
			// Set the default socket timeout (SO_TIMEOUT) // in milliseconds which is the timeout for waiting for data.  
		    HttpConnectionParams.setSoTimeout(httpParameters, 30000);  
		    HttpClient client = new DefaultHttpClient(httpParameters);
			httpResponse = client.execute(httpPost);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				
				//String result = EntityUtils.toString(httpResponse.getEntity());
				//Log.d(BaiduMapAll.LOG_TAG, result);
			} else {
				//这里响应代码不是200 页正茬插入了
				//Toast.makeText(activityContex, "发送经纬度到后台失败!"+httpResponse.getStatusLine().getStatusCode()+this.getUploadUrl(), Toast.LENGTH_LONG).show();
				toast("发送经纬度到后台失败!",Toast.LENGTH_LONG);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//Toast.makeText(activityContex, "发送经纬度到后台失败!请检查网络连接!", Toast.LENGTH_LONG).show();
			toast("发送经纬度到后台失败!请检查网络连接!",Toast.LENGTH_LONG);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//Toast.makeText(activityContex, "发送经纬度到后台失败!请检查参数格式!", Toast.LENGTH_LONG).show();
			toast("发送经纬度到后台失败!请检查参数格式!",Toast.LENGTH_LONG);
		}
         
    }

	public String getErrorMessage(int locationType) {
		String result = ERROR_MESSAGE_MAP.get(locationType);
		if (result == null) {
			if(locationType>=162 && locationType<=167){
				return "服务端定位失败!";
			} else if(locationType>=501 && locationType<=700){
				return "key验证失败";
			} else {
				return "服务端定位失败!";
			}
		}
		return result;
	}
	
	public void toast(final String content,final int duration){
		handler.post(new Runnable() {    
			             @Override    
			             public void run() {    
			                Toast.makeText(getApplicationContext(), content,duration).show();    
			             }    
			        }); 
	}
	
	
	
	
	
	
	
	public class MyBinder extends Binder {

		public LocationApplication getService() {
			return LocationApplication.this;
		}
	}

	private MyBinder myBinder = new MyBinder();

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return myBinder;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		//onCreate(getBaseContext());
		 mLocationClient = new LocationClient(this.getBaseContext());
	     mMyLocationListener = new MyLocationListener();
	     mLocationClient.registerLocationListener(mMyLocationListener);
	    //this.activityContex=context;  
	    //不初始化，不能计算距离
	    SDKInitializer.initialize(this.getBaseContext().getApplicationContext());
		handler = new Handler(Looper.getMainLooper());
	}

	@Override
	 public int onStartCommand(Intent intent, int flags, int startId) {
		
		this.setUploadUrl(intent.getStringExtra("uploadUrl"));
		this.setGps_interval(intent.getIntExtra("gps_interval",0));
		String params_str=intent.getStringExtra("params");
		try {
			if(params_str!=null){
				this.setParams(new JSONObject(params_str));
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//mLocationClient.getLocOption().setScanSpan(this.getGps_interval());
		initLocation();
		
		mLocationClient.start();
		mLocationClient.requestLocation();

		toast("开始获取gps信息了", Toast.LENGTH_LONG);
		//LOG.i(BaiduMapAll.LOG_TAG, "开始获取gps信息了================================================");
		return super.onStartCommand(intent, flags,startId);
	}

	@Override
	public void onDestroy() {
		if (mLocationClient != null && mLocationClient.isStarted()) {
			mLocationClient.stop();
		}
		super.onDestroy();
	}
    
	

}
