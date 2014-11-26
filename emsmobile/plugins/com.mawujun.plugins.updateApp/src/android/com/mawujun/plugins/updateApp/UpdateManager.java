package com.mawujun.plugins.updateApp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.StrictMode;
import android.util.Log;

public class UpdateManager {
	Activity activity;
	public static final int getAppVersionCode=0;
	public static final int getAppVersionName=1;
	//public static final int DOWNLOADING=1;
	public static final int closeProgressDialog=2;
	public static final int exceptionDialog=3;
	public static final int autoUpdateApp=50;
	public static final int manuallyUpdateApp=60;
	
	
	int newVerCode = -1;// 新版本号，服务端的版本号
	String newVerName = "";//新版本名称，服务端的版本名称
	String UPDATE_SERVERAPK = "ApkUpdateAndroid.apk";
	String serverVerUrl = null;//"http://172.16.3.10:8080/apkVersion.js";// 检查服务器版本的url
	String downloadFile = null;//http://192.168.0.100:88/phoneGap_jqm.apk";
	String changelog=null;
	
	private String log_tag="UpdateManager";
	
	ProgressDialog pd = null;
	
	public void initHttpStrictMode(){
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()       
        .detectDiskReads()       
        .detectDiskWrites()       
        .detectNetwork()   // or .detectAll() for all detectable problems       
        .penaltyLog()       
        .build());       
		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()       
        .detectLeakedSqlLiteObjects()    
        .penaltyLog()       
        .penaltyDeath()       
        .build());
	}
	
	public void initUrl(JSONArray args) throws JSONException{
		
		JSONObject params=args.getJSONObject(0);
		
		if(params==null){
			exceptionDialog("请输入地址参数");
		}
		try{
			downloadFile=params.getString("downloadFile");
		}catch(Exception e) {//如果值不存在，会爆出异常
			downloadFile=null;
		}
//		exceptionDialog(downloadFile+"");
//		if(downloadFile==null){
//			exceptionDialog("请输入文件下载地址参数:downloadFile");
//		}
		
		serverVerUrl=params.getString("serverVerUrl");
		if(serverVerUrl==null || "null".equalsIgnoreCase(serverVerUrl)){
			exceptionDialog("请输入获取服务器版本地址:serverVerUrl");
		}
		
	}
	
	public UpdateManager(Activity activity){
		this.activity=activity;
		
	}
	
	Handler mHandler =  new Handler(Looper.getMainLooper()) {
		
		@Override
		public void handleMessage(Message msg) {
			switch(msg.what) {
				case closeProgressDialog:
					pd.cancel();
					if(msg.obj!=null){
						exceptionDialog(msg.obj.toString());
					}
					break;	
				case exceptionDialog:
					if(msg.obj!=null){
						exceptionDialog(msg.obj.toString());
					}
				case autoUpdateApp:
					autoUpdateApp();
					break;
				case manuallyUpdateApp:
					manuallyUpdateApp();
					break;
			}
		}
	};
	
	public void exec(int action){
		mHandler.sendEmptyMessage(action);  
	}
	/**
	 * 更新程序，如果不用更新了的话，就不会给出提示，如果需要更新才会给出提示
	 * @param args
	 */
	public void autoUpdateApp() {
		// activity;
		
		if (getServerVersion()) {
			int verCode = this.getAppVersionCode();
			if (newVerCode > verCode) {
				doNewVersionUpdate();// 更新版本
			} 
		}
	}
	/**
	 * 检查并提示更新，如果不用更新，也会给出提示,提示已经是最新的程序了，主要用于手工更新的时候
	 * @param args
	 */
	public void manuallyUpdateApp() {
		// this.cordova.getActivity();
		if (getServerVersion()) {
			int verCode = this.getAppVersionCode();
			if (newVerCode > verCode) {
				doNewVersionUpdate();// 更新版本
			} else {
				// alert("")
				notNewVersionUpdate();// 提示已是最新版本
			}
		}
	}
	
	/**
	 * 获得版本号
	 */
	public int getAppVersionCode() {
		int verCode = -1;
		try {
			// String packName = context.getPackageName();
			// verCode = context.getPackageManager().getPackageInfo(packName,
			// 0).versionCode;
			PackageManager packageManager = activity.getPackageManager();
			verCode=packageManager.getPackageInfo(activity.getPackageName(), 0).versionCode;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			Log.e(log_tag, e.getMessage());
		}
		return verCode;
	}
	/**
	 * 获得版本名称
	 */
	public String getVersionName() {
		String verName = "";
		try {
//			String packName = activity.getPackageName();
//			verName = activity.getPackageManager()
//					.getPackageInfo(packName, 0).versionName;
			PackageManager packageManager = activity.getPackageManager();
			verName=packageManager.getPackageInfo(activity.getPackageName(), 0).versionName;
		} catch (NameNotFoundException e) {
			Log.e(log_tag, e.getMessage());
		}
		return verName;
	}
	/**
	 * 不更新版本
	 */
	public void notNewVersionUpdate() {
		//int verCode = this.getVerCode();
		//String verName = this.getVerName();
		StringBuffer sb = new StringBuffer();
		//sb.append("当前版本：");
		//sb.append(verName);
		//sb.append(" Code:");
		//sb.append(verCode);
		sb.append("已是最新版本，无需更新");
		Dialog dialog = new AlertDialog.Builder(activity)
				.setTitle("软件更新").setMessage(sb.toString())
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						//activity.finish();
					}
				}).create();
		dialog.show();
	}

	/**
	 * 从服务器端获得版本号与版本名称
	 * 
	 * @return
	 */
	public boolean getServerVersion() {
		
		initHttpStrictMode();
//        new Thread() {
//        	public void run(){
        		
        
		HttpURLConnection httpConnection =null;
		InputStreamReader reader =null;
		BufferedReader bReader =null;
		try {
			Log.d(log_tag, "获取服务器版本开始");
			URL url = new URL(serverVerUrl);
			httpConnection = (HttpURLConnection) url.openConnection();
			//httpConnection.setDoInput(true);
			//httpConnection.setDoOutput(true);
			httpConnection.setRequestMethod("GET");
			//httpConnection.connect();
			//sdfsd,放到logcat中试下，调试下看问题出在哪里
			//通过android的日志系统记录然后，再在logcat中看下
			httpConnection.setConnectTimeout(60*1000);
//			if (httpConnection.getResponseCode() != 200) {
//				exceptionDialog("http连接失败!");
//				return false;
//			}
			reader = new InputStreamReader(httpConnection.getInputStream());
			bReader = new BufferedReader(reader);
			//String json = bReader.readLine();
			StringBuffer strBuffer = new StringBuffer();
			String line = null;
			while ((line = bReader.readLine()) != null) {
				strBuffer.append(line);
			}
			String json =strBuffer.toString();
			//exceptionDialog(json);
			//JSONArray array = new JSONArray(json);
			//JSONObject jsonObj = array.getJSONObject(0);
			JSONObject jsonObj = new JSONObject(json);
			newVerCode = Integer.parseInt(jsonObj.getString("verCode"));
			newVerName = jsonObj.getString("verName");
			try{
				Log.d(log_tag, "开始获取downloadFile");
				downloadFile=jsonObj.getString("downloadFile");
			}catch(Exception e) {//如果后台也设置了下载文件的地址，那就以这个的优先级为高
				
			}
			try{
				changelog=jsonObj.getString("changelog");
			}catch(Exception e) {//如果后台也设置了下载文件的地址，那就以这个的优先级为高
				
			}
			Log.d(log_tag, "获取服务器版本结束");
			
		} catch (Exception e) {
			Log.d(log_tag, "获取服务器版本错误，错误信息："+e.getMessage());
			exceptionDialog("获取服务器版本失败");
			// TODO Auto-generated catch block
			e.printStackTrace();

			return true;// 如果这里改为false 则不更新会退出程序
		}finally {
            if (httpConnection != null) {
            	httpConnection.disconnect();
            }
            if (reader != null) {
                try {
                	reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bReader != null) {
                try {
                	bReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
 
        }
		return true;
//        	}
//        }.start();
	}
	
	/**
	 * 更新版本
	 */
	public void doNewVersionUpdate() {
		//int verCode = this.getVerCode();
		//String verName = this.getVerName();
		StringBuffer sb = new StringBuffer();
		//sb.append("当前版本：");
		//sb.append(verName);
		//sb.append(" Code:");
		//sb.append(verCode);
		sb.append("发现新版本");
		sb.append(newVerName);
		//sb.append(" Code:");
		//sb.append(newVerCode);
		sb.append(",是否立即更新?\n");
		if(changelog!=null){
			sb.append(changelog);
		}
		
		Dialog dialog = new AlertDialog.Builder(activity)
				.setTitle("软件更新")
				.setMessage(sb.toString())
				.setPositiveButton("更新", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						pd = new ProgressDialog(activity);
						pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);// 设置水平进度条  
						//pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
						pd.setTitle("正在下载");
						pd.setMessage("请稍后。。。");
						pd.setCancelable(true);// 设置是否可以通过点击Back键取消  
						pd.setCanceledOnTouchOutside(false);// 设置在点击Dialog外是否取消Dialog进度条  
						
						pd.show();
						downFile(downloadFile);
					}
				})
				.setNegativeButton("暂不更新",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								// finish();
							}
						}).create();
		// 显示更新框
		dialog.show();
	}

	/**
	 * 下载apk
	 */
	public void downFile(final String url) {
		
		new Thread() {
			public void run() {
//		cordova.getThreadPool().execute(new Runnable() {
//            public void run() {
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))  
        {  
				HttpClient client = new DefaultHttpClient();
				//请求超时
				client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 60000); 
				HttpGet get = new HttpGet(url);
				HttpResponse response;
				try {
					
					//exceptionDialog("0");
					response = client.execute(get);
					//exceptionDialog("1");
					HttpEntity entity = response.getEntity();
					//exceptionDialog("2");
					long length = entity.getContentLength();
					//exceptionDialog(length+"");
					InputStream is = entity.getContent();
					FileOutputStream fileOutputStream = null;
					
					
					
					if (is != null) {
						File file = new File(
								//Environment.getDownloadCacheDirectory(),
								Environment.getExternalStorageDirectory(),
								UPDATE_SERVERAPK);
						//exceptionDialog(file.canWrite()+"");
						//应该是没有写入权限造成的
						//http://www.cnblogs.com/mengdd/p/3742623.html
						fileOutputStream = new FileOutputStream(file);
						byte[] b = new byte[1024];
						int charb = -1;
						int count = 0;
						int progress=1;
						while ((charb = is.read(b)) != -1) {
							fileOutputStream.write(b, 0, charb);
							count += charb;
							progress = (int) (((float) count / length) * 100);  
							//pd.incrementProgressBy(progress);
							pd.setProgress(progress);
						}
					}
					//exceptionDialog("-111");
					fileOutputStream.flush();
					if (fileOutputStream != null) {
						fileOutputStream.close();
					}
					//exceptionDialog("000");
					//down();
					update();
					mHandler.sendEmptyMessage(closeProgressDialog);
					//exceptionDialog("333");
				} catch(ConnectTimeoutException e){
					//exceptionDialog();
					//mHandler.sendEmptyMessage(closeProgressDialog);
					Message msg=new Message();
					msg.what=closeProgressDialog;
					msg.obj="下载超时!";
					mHandler.sendMessage(msg);
				}catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
//					exceptionDialog("下载发生错误!");
//					mHandler.sendEmptyMessage(closeProgressDialog);
					Message msg=new Message();
					msg.what=closeProgressDialog;
					msg.obj="下载发生错误!";
					mHandler.sendMessage(msg);
				}
        } else {
        	exceptionDialog("SD卡不具有读写权限");
        	pd.cancel();
        }
//            }
//        });
			}
		}.start();
	}
	
	
	public void update() {
		//exceptionDialog("2222");
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(new File(Environment
				.getExternalStorageDirectory(), UPDATE_SERVERAPK)),
				"application/vnd.android.package-archive");
		this.activity.startActivity(intent);
	}
	

	
	public void exceptionDialog(String msg) {
		StringBuffer sb = new StringBuffer(msg);

		Dialog dialog = new AlertDialog.Builder(activity)
				.setTitle("消息").setMessage(sb.toString())
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						//activity.finish();
					}
				}).create();
		dialog.show();

	}
}
