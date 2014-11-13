package com.mawujun.plugins.updateApp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.StrictMode;
import android.util.Log;

public class UpdateManager {
	Activity activity;
	public static final int getServerVer=0;
	public static final int DOWNLOADING=1;
	
	
	int newVerCode = -1;// 新版本号，服务端的版本号
	String newVerName = "";//新版本名称，服务端的版本名称
	String serverVerUrl = "http://172.16.3.10:8080/apkVersion.js";// 检查服务器版本的url
	String downloadFile = null;//http://192.168.0.100:88/phoneGap_jqm.apk";
	
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
	public UpdateManager(Activity activity){
		this.activity=activity;
		
	}
	
	Handler mHandler =  new Handler(Looper.getMainLooper()) {
		
		@Override
		public void handleMessage(Message msg) {
			switch(msg.what) {
				case getServerVer:
					getServerVer();
					break;
				case DOWNLOADING:
					//aa("证在下载");
					break;
			}
		}
	};
	
	public void exec(int action){
		mHandler.sendEmptyMessage(action);  
	}
	/**
	 * 从服务器端获得版本号与版本名称
	 * 
	 * @return
	 */
	public boolean getServerVer() {
		
		initHttpStrictMode();
//        new Thread() {
//        	public void run(){
        		
        
		HttpURLConnection httpConnection =null;
		InputStreamReader reader =null;
		BufferedReader bReader =null;
		try {
			URL url = new URL(serverVerUrl);
			httpConnection = (HttpURLConnection) url.openConnection();
			//httpConnection.setDoInput(true);
			//httpConnection.setDoOutput(true);
			httpConnection.setRequestMethod("GET");
			//exceptionDialog("0");
			//httpConnection.connect();
			//sdfsd,放到logcat中试下，调试下看问题出在哪里
			//通过android的日志系统记录然后，再在logcat中看下
			//httpConnection.setConnectTimeout(6*1000);
			if (httpConnection.getResponseCode() != 200) {
				exceptionDialog("http连接失败!");
				return false;
			}
			//exceptionDialog("11");
			reader = new InputStreamReader(httpConnection.getInputStream());
			//exceptionDialog("2");
			bReader = new BufferedReader(reader);
			//exceptionDialog("3");
			//String json = bReader.readLine();
			StringBuffer strBuffer = new StringBuffer();
			String line = null;
			while ((line = bReader.readLine()) != null) {
				strBuffer.append(line);
			}
			String json =strBuffer.toString();
			exceptionDialog(json);
			//JSONArray array = new JSONArray(json);
			//JSONObject jsonObj = array.getJSONObject(0);
			JSONObject jsonObj = new JSONObject(json);
			newVerCode = Integer.parseInt(jsonObj.getString("verCode"));
			newVerName = jsonObj.getString("verName");
			if(downloadFile==null || "".equals(downloadFile.trim())){
				downloadFile= jsonObj.getString("downloadFile");
			}
			
		} catch (Exception e) {
			exceptionDialog(e.getMessage());
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
	

	
	public void exceptionDialog(String msg) {
		StringBuffer sb = new StringBuffer(msg);

		Dialog dialog = new AlertDialog.Builder(activity)
				.setTitle("错误信息").setMessage(sb.toString())
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
