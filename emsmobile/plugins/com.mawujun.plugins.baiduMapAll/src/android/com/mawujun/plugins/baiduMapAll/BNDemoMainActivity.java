package com.mawujun.plugins.baiduMapAll;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.navisdk.adapter.BNOuterTTSPlayerCallback;
import com.baidu.navisdk.adapter.BNRoutePlanNode;
import com.baidu.navisdk.adapter.BNRoutePlanNode.CoordinateType;
import com.baidu.navisdk.adapter.BaiduNaviManager;
import com.baidu.navisdk.adapter.BaiduNaviManager.NaviInitListener;
import com.baidu.navisdk.adapter.BaiduNaviManager.RoutePlanListener;
import com.mawujun.plugins.baiduMapAll.LocationApplication.MyBinder;

public class BNDemoMainActivity extends Activity {

	public static final String TAG = "NaviSDkDemo";
	private static final String APP_FOLDER_NAME = "BNSDKDemo";

	
	private RadioGroup routePlanPreferenceRadio;
	private RadioButton ROUTE_PLAN_MOD_AVOID_TAFFICJAM;
	private RadioButton ROUTE_PLAN_MOD_MIN_DIST;
	private RadioButton ROUTE_PLAN_MOD_MIN_TIME;
	private RadioButton ROUTE_PLAN_MOD_MIN_TOLL;
	private RadioButton ROUTE_PLAN_MOD_RECOMMEND;
	
	
	private ToggleButton isGPSNavButton;
	private Button mBd09llNaviBtn = null;
	
	private String mSDCardPath = null;
	
	public static final String ROUTE_PLAN_NODE = "routePlanNode";
	
	private Context context = this;
	
	String longitude=null;
	String latitude=null;
	
	private Boolean isGPSNav=true;//是导航 还是模拟导航 true是直接导航
	private int routePlanPreference=BaiduNaviManager.RoutePlanPreference.ROUTE_PLAN_MOD_RECOMMEND;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);  
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		bindService();
		
//		setContentView(R.layout.activity_main);
//		
//		mWgsNaviBtn = (Button) findViewById(R.id.wgsNaviBtn);
//		mGcjNaviBtn = (Button) findViewById(R.id.gcjNaviBtn);
//		mBdmcNaviBtn = (Button) findViewById(R.id.bdmcNaviBtn);
		
		setContentView(context.getResources().getIdentifier("bndemomainactivity","layout", context.getPackageName()));
		
		routePlanPreferenceRadio = (RadioGroup) findViewById(context.getResources().getIdentifier("routePlanPreferenceRadio","id", context.getPackageName()));
		ROUTE_PLAN_MOD_AVOID_TAFFICJAM= (RadioButton) findViewById(context.getResources().getIdentifier("ROUTE_PLAN_MOD_AVOID_TAFFICJAM","id", context.getPackageName()));
		ROUTE_PLAN_MOD_MIN_DIST = (RadioButton) findViewById(context.getResources().getIdentifier("ROUTE_PLAN_MOD_MIN_DIST","id", context.getPackageName()));
		ROUTE_PLAN_MOD_MIN_TIME = (RadioButton) findViewById(context.getResources().getIdentifier("ROUTE_PLAN_MOD_MIN_TIME","id", context.getPackageName()));
		ROUTE_PLAN_MOD_MIN_TOLL = (RadioButton) findViewById(context.getResources().getIdentifier("ROUTE_PLAN_MOD_MIN_TOLL","id", context.getPackageName()));
		ROUTE_PLAN_MOD_RECOMMEND = (RadioButton) findViewById(context.getResources().getIdentifier("ROUTE_PLAN_MOD_RECOMMEND","id", context.getPackageName()));
		
		
		isGPSNavButton = (ToggleButton) findViewById(context.getResources().getIdentifier("isGPSNavButton","id", context.getPackageName()));
		mBd09llNaviBtn = (Button) findViewById(context.getResources().getIdentifier("mBd09llNaviBtn","id", context.getPackageName()));
		mBd09llNaviBtn.setClickable(false);
		
		
		
		initListener();
		if ( initDirs() ) {
			initNavi();
		}
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		Intent intent=getIntent();
		longitude=intent.getStringExtra("com.mawujun.plugins.baiduMapAll.longitude");
		latitude=intent.getStringExtra("com.mawujun.plugins.baiduMapAll.latitude");
	}
	
	@Override
	protected void onResume() {
		
		super.onResume();
	}
	
	private void initListener() {
		if ( routePlanPreferenceRadio != null ) {
			routePlanPreferenceRadio.setOnCheckedChangeListener(new OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(RadioGroup group, int checkedId) {
					// TODO Auto-generated method stub
					if(ROUTE_PLAN_MOD_AVOID_TAFFICJAM.getId()==checkedId){
						routePlanPreference=BaiduNaviManager.RoutePlanPreference.ROUTE_PLAN_MOD_AVOID_TAFFICJAM;
			        } else if(ROUTE_PLAN_MOD_MIN_DIST.getId()==checkedId){
			        	routePlanPreference=BaiduNaviManager.RoutePlanPreference.ROUTE_PLAN_MOD_MIN_DIST;
			        } else if(ROUTE_PLAN_MOD_MIN_TIME.getId()==checkedId){
			        	routePlanPreference=BaiduNaviManager.RoutePlanPreference.ROUTE_PLAN_MOD_MIN_TIME;
			        } else if(ROUTE_PLAN_MOD_MIN_TOLL.getId()==checkedId){
			        	routePlanPreference=BaiduNaviManager.RoutePlanPreference.ROUTE_PLAN_MOD_MIN_TOLL;
			        } else{
			        	routePlanPreference=BaiduNaviManager.RoutePlanPreference.ROUTE_PLAN_MOD_RECOMMEND;
			        }

				}

			});
		}
		if ( isGPSNavButton != null ) {
			isGPSNavButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
				@Override
				 public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					isGPSNav=!isChecked;
				}
			});
		}
		if ( mBd09llNaviBtn != null ) {
			mBd09llNaviBtn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					//Toast.makeText(BNDemoMainActivity.this, "按钮点击", Toast.LENGTH_SHORT).show();
					if ( BaiduNaviManager.isNaviInited() ) {
						routeplanToNavi(CoordinateType.GCJ02);
					}  else {
						Toast.makeText(BNDemoMainActivity.this, "导航正在初始化，请稍候!", Toast.LENGTH_SHORT).show();
					}
				}
			});
		}
	}
	
	private boolean initDirs() {
		mSDCardPath = getSdcardDir();
		if ( mSDCardPath == null ) {
			return false;
		}
		File f = new File(mSDCardPath, APP_FOLDER_NAME);
		if ( !f.exists() ) {
			try {
				f.mkdir();
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}
	
	String authinfo = null;
	
	private void initNavi() {
		BaiduNaviManager.getInstance().setNativeLibraryPath(
				mSDCardPath + "/BaiduNaviSDK_SO");
		BaiduNaviManager.getInstance().init(this, mSDCardPath, APP_FOLDER_NAME,
				new NaviInitListener() {
					@Override
					public void onAuthResult(int status, String msg) {
						if (0 == status) {
							authinfo = "key校验成功!";
						} else {
							authinfo = "key校验失败, " + msg;
						}
						BNDemoMainActivity.this.runOnUiThread(new Runnable() {

							@Override
							public void run() {
								Toast.makeText(BNDemoMainActivity.this,
										authinfo, Toast.LENGTH_LONG).show();
							}
						});

					}

					public void initSuccess() {
						Toast.makeText(BNDemoMainActivity.this, "导航引擎初始化成功",
								Toast.LENGTH_SHORT).show();
						mBd09llNaviBtn.setClickable(true);
						mBd09llNaviBtn.setText("开始导航");
					}

					public void initStart() {
						Toast.makeText(BNDemoMainActivity.this, "导航引擎初始化开始",
								Toast.LENGTH_SHORT).show();
					}

					public void initFailed() {
						Toast.makeText(BNDemoMainActivity.this, "导航引擎初始化失败",
								Toast.LENGTH_SHORT).show();
					}
				}, null /* mTTSCallback */);
	}
	
	
	private String getSdcardDir() {
 
		
		
		if (Environment.getExternalStorageState().equalsIgnoreCase(
				Environment.MEDIA_MOUNTED)) {
			return Environment.getExternalStorageDirectory().toString();
		}
		return null;
	}
	
	private BDLocation bd09llTOgcj02(Double db09ll_longitude,Double db09ll_latitude){

		BDLocation bd=new BDLocation();
		bd.setLatitude(db09ll_latitude);
		bd.setLongitude(db09ll_longitude);
		
		return LocationClient.getBDLocationInCoorType(bd, BDLocation.BDLOCATION_BD09LL_TO_GCJ02);
		
	}

	
	private void routeplanToNavi(CoordinateType coType) {
		//BDLocation bDLocation= BaiduMapAll.locationApplication.mLocationClient.getLastKnownLocation();
		
		//BNRoutePlanNode sNode = new BNRoutePlanNode(bDLocation.getLongitude(), bDLocation.getLatitude(),bDLocation.getBuildingName(), null, coType);
		//同步定位，返回最近一次定位结果
		//BDLocation start_dest=conn.getBindService().mLocationClient.getLastKnownLocation();
		BDLocation start_dest=bd09llTOgcj02(conn.getBindService().currentLongitude,conn.getBindService().currentLatitude);
//		BDLocation start_dest=new BDLocation();
//		start_dest.setLatitude(116.30142);
//		start_dest.setLongitude(40.05087);
		
		BNRoutePlanNode sNode = new BNRoutePlanNode( start_dest.getLongitude(),start_dest.getLatitude(),"起点", null, coType);
		//Log.i(BaiduMapAll.LOG_TAG, bDLocation.getLongitude()+","+bDLocation.getLatitude()+","+bDLocation.getBuildingName());
		BDLocation end_dest=bd09llTOgcj02(Double.parseDouble(longitude),Double.parseDouble(latitude));
	    BNRoutePlanNode eNode = new BNRoutePlanNode(end_dest.getLongitude(),end_dest.getLatitude() , "终点", null, coType);
	    
	    if (sNode != null && eNode != null) {
	        List<BNRoutePlanNode> list = new ArrayList<BNRoutePlanNode>();
	        list.add(sNode);
	        list.add(eNode);
	        BaiduNaviManager.getInstance().launchNavigator(this, list, routePlanPreference, isGPSNav, new DemoRoutePlanListener(sNode));
		}
	    
	}
	@Override
	public void onDestroy(){
		unBind();
		super.onDestroy();
	}
	
	
	public class DemoRoutePlanListener implements RoutePlanListener {
		
		private BNRoutePlanNode mBNRoutePlanNode = null;
		public DemoRoutePlanListener(BNRoutePlanNode node){
			mBNRoutePlanNode = node;
		}
		
		@Override
		public void onJumpToNavigator() {
			 Intent intent = new Intent(BNDemoMainActivity.this, BNDemoGuideActivity.class);
			 Bundle bundle = new Bundle();
			 bundle.putSerializable(ROUTE_PLAN_NODE, (BNRoutePlanNode) mBNRoutePlanNode);
			 intent.putExtras(bundle);
             startActivity(intent);
		}
		@Override
		public void onRoutePlanFailed() {
			// TODO Auto-generated method stub
			
		}
	}
	
	//http://blog.163.com/allegro_tyc/blog/static/337437682013629348791/
	private boolean flag = false;

	private void bindService() {
		Intent intent = new Intent(BNDemoMainActivity.this,LocationApplication.class);
		bindService(intent, conn, Context.BIND_AUTO_CREATE);
	}

	private void unBind() {
		if (flag == true) {
			// Log.i(TAG, "BindService-->unBind()");
			unbindService(conn);
			flag = false;
		}
	}

	public class MyServiceConnection implements ServiceConnection {
		LocationApplication bindService;

		public LocationApplication getBindService() {
			return bindService;
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO Auto-generated method stub
			MyBinder binder = (MyBinder) service;
			bindService = binder.getService();
			//bindService.mLocationClient.requestLocation();
			// bindService.
			flag = true;
		}

	}

	private MyServiceConnection conn = new MyServiceConnection();
//	private ServiceConnection conn = new ServiceConnection() {    
//		LocationApplication bindService;
//        public LocationApplication getBindService() {
//			return bindService;
//		}
//
//		@Override
//        public void onServiceDisconnected(ComponentName name) {
//            // TODO Auto-generated method stub
//            
//        }
//        
//        @Override
//        public void onServiceConnected(ComponentName name, IBinder service) {
//            // TODO Auto-generated method stub
//            MyBinder binder = (MyBinder)service;
//            bindService = binder.getService();
//            bindService.mLocationClient.requestLocation();
//            //bindService.
//            flag = true;
//        }
//    };
	
	private BNOuterTTSPlayerCallback mTTSCallback = new BNOuterTTSPlayerCallback() {
		
		@Override
		public void stopTTS() {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void resumeTTS() {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void releaseTTSPlayer() {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public int playTTSText(String speech, int bPreempt) {
			// TODO Auto-generated method stub
			return 0;
		}
		
		@Override
		public void phoneHangUp() {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void phoneCalling() {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void pauseTTS() {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void initTTSPlayer() {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public int getTTSState() {
			// TODO Auto-generated method stub
			return 0;
		}
	};
}
