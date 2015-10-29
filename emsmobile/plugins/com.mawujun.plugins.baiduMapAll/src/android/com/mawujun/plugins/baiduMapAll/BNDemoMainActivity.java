package com.mawujun.plugins.baiduMapAll;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.navisdk.adapter.BNOuterTTSPlayerCallback;
import com.baidu.navisdk.adapter.BNRoutePlanNode;
import com.baidu.navisdk.adapter.BNRoutePlanNode.CoordinateType;
import com.baidu.navisdk.adapter.BaiduNaviManager;
import com.baidu.navisdk.adapter.BaiduNaviManager.NaviInitListener;
import com.baidu.navisdk.adapter.BaiduNaviManager.RoutePlanListener;

public class BNDemoMainActivity extends Activity {

	public static final String TAG = "NaviSDkDemo";
	private static final String APP_FOLDER_NAME = "BNSDKDemo";
	private Button mWgsNaviBtn = null;
	private Button mGcjNaviBtn = null;
	private Button mBdmcNaviBtn = null;
	private String mSDCardPath = null;
	
	public static final String ROUTE_PLAN_NODE = "routePlanNode";
	
	private Context context = this;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
//		setContentView(R.layout.activity_main);
//		
//		mWgsNaviBtn = (Button) findViewById(R.id.wgsNaviBtn);
//		mGcjNaviBtn = (Button) findViewById(R.id.gcjNaviBtn);
//		mBdmcNaviBtn = (Button) findViewById(R.id.bdmcNaviBtn);
		
		setContentView(context.getResources().getIdentifier("bndemomainactivity","layout", context.getPackageName()));
		
		mWgsNaviBtn = (Button) findViewById(context.getResources().getIdentifier("wgsNaviBtn","id", context.getPackageName()));
		mGcjNaviBtn = (Button) findViewById(context.getResources().getIdentifier("gcjNaviBtn","id", context.getPackageName()));
		mBdmcNaviBtn = (Button) findViewById(context.getResources().getIdentifier("bdmcNaviBtn","id", context.getPackageName()));
		
//		initListener();
		if ( initDirs() ) {
			initNavi();
		}
	}
	
	@Override
	protected void onResume() {
		
		super.onResume();
	}
	
//	private void initListener() {
////		if ( mWgsNaviBtn != null ) {
////			mWgsNaviBtn.setOnClickListener(new OnClickListener() {
////				
////				@Override
////				public void onClick(View arg0) {
////					if ( BaiduNaviManager.isNaviInited() ) {
////						routeplanToNavi(CoordinateType.WGS84);
////					}
////				}
////			});
////		}
////		if ( mGcjNaviBtn != null ) {
////			mGcjNaviBtn.setOnClickListener(new OnClickListener() {
////				
////				@Override
////				public void onClick(View arg0) {
////					if ( BaiduNaviManager.isNaviInited() ) {
////						routeplanToNavi(CoordinateType.GCJ02);
////					}
////				}
////			});
////		}
////		if ( mBdmcNaviBtn != null ) {
////			mBdmcNaviBtn.setOnClickListener(new OnClickListener() {
////				
////				@Override
////				public void onClick(View arg0) {
////					if ( BaiduNaviManager.isNaviInited() ) {
////						routeplanToNavi(CoordinateType.BD09_MC);
////					}
////				}
////			});
////		}
//	}
	
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
	    BaiduNaviManager.getInstance().setNativeLibraryPath(mSDCardPath + "/BaiduNaviSDK_SO");
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
                        Toast.makeText(BNDemoMainActivity.this, authinfo, Toast.LENGTH_LONG).show();
                    }
                });

            }
				public void initSuccess() {
					Toast.makeText(BNDemoMainActivity.this, "百度导航引擎初始化成功", Toast.LENGTH_SHORT).show();
					routeplanToNavi(CoordinateType.GCJ02);
				}
				
				public void initStart() {
					Toast.makeText(BNDemoMainActivity.this, "百度导航引擎初始化开始", Toast.LENGTH_SHORT).show();
				}
				
				public void initFailed() {
					Toast.makeText(BNDemoMainActivity.this, "百度导航引擎初始化失败", Toast.LENGTH_SHORT).show();
				}
		}, null /*mTTSCallback*/);
	}
	
	
	private String getSdcardDir() {
		if (Environment.getExternalStorageState().equalsIgnoreCase(
				Environment.MEDIA_MOUNTED)) {
			return Environment.getExternalStorageDirectory().toString();
		}
		return null;
	}
	
	private BDLocation getBDLocation(){
		LocationClient mLocationClient = new LocationClient(context);
		return mLocationClient.getLastKnownLocation();
		
	}
	
	private void routeplanToNavi(CoordinateType coType) {
		//BDLocation bDLocation= BaiduMapAll.locationApplication.mLocationClient.getLastKnownLocation();
		
		//BNRoutePlanNode sNode = new BNRoutePlanNode(bDLocation.getLongitude(), bDLocation.getLatitude(),bDLocation.getBuildingName(), null, coType);
		BNRoutePlanNode sNode = new BNRoutePlanNode( BaiduMapAll.locationApplication.currentLongitude,  BaiduMapAll.locationApplication.currentLatitude,"起点", null, coType);
		//Log.i(BaiduMapAll.LOG_TAG, bDLocation.getLongitude()+","+bDLocation.getLatitude()+","+bDLocation.getBuildingName());
	    BNRoutePlanNode eNode = new BNRoutePlanNode(116.560377, 40.100691, "北京天安门", null, coType);
	    if (sNode != null && eNode != null) {
	        List<BNRoutePlanNode> list = new ArrayList<BNRoutePlanNode>();
	        list.add(sNode);
	        list.add(eNode);
	        BaiduNaviManager.getInstance().launchNavigator(this, list, 1, true, new DemoRoutePlanListener(sNode));
		}
	    
	}
	
//	private void routeplanToNavi(CoordinateType coType) {
//		////wgs84:国际经纬度坐标  "gcj02":国家测绘局标准,"bd09ll":百度经纬度标准,"bd09":百度墨卡托标准
//	    BNRoutePlanNode sNode = null;
//	    BNRoutePlanNode eNode = null;
//		switch(coType) {
//			case GCJ02: {
//				sNode = new BNRoutePlanNode(116.293616, 39.862738, 
//			    		"百度大厦", null, coType);
//				eNode = new BNRoutePlanNode(116.560377, 40.100691, 
//			    		"北京天安门", null, coType);
//				break;
//			}
//			case WGS84: {
//				sNode = new BNRoutePlanNode(116.300821,40.050969,
//			    		"百度大厦", null, coType);
//				eNode = new BNRoutePlanNode(116.397491,39.908749, 
//			    		"北京天安门", null, coType);
//				break;
//			}
//			case BD09_MC: {
//				sNode = new BNRoutePlanNode(12947471,4846474,  
//			    		"百度大厦", null, coType);
//				eNode = new BNRoutePlanNode(12958160,4825947,  
//			    		"北京天安门", null, coType);
//				break;
//			}
//			default : ;
//		}
//		if (sNode != null && eNode != null) {
//	        List<BNRoutePlanNode> list = new ArrayList<BNRoutePlanNode>();
//	        list.add(sNode);
//	        list.add(eNode);
//	        BaiduNaviManager.getInstance().launchNavigator(this, list, 1, true, new DemoRoutePlanListener(sNode));
//		}
//	}
	
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
