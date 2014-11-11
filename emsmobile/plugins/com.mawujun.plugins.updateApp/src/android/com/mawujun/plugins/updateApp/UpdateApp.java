package com.mawujun.plugins.updateApp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * 程序更新的app插件
 * version.js的内容如下{verCode:1,verName:'0.0.1'}
 * @author mawujun
 * 
 */
public class UpdateApp extends CordovaPlugin {
	public CallbackContext callbackContext;
	int newVerCode = -1;// 新版本号，服务端的版本号
	String newVerName = "";//新版本名称，服务端的版本名称
	
	ProgressDialog pd = null;
	String UPDATE_SERVERAPK = "ApkUpdateAndroid.apk";
	String downloadFile = null;//http://192.168.0.100:88/phoneGap_jqm.apk";
	String serverVerUrl = "http://172.16.3.10:8080/version.js";// 检查服务器版本的url
	Activity activity;

	@Override
	public boolean execute(String action, JSONArray args,
			final CallbackContext callbackContext) throws JSONException {
		initUrl(args);
		this.callbackContext = callbackContext;
		activity = this.cordova.getActivity();
		final UpdateApp aa=this;
		
		// 检查并更新程序app
		if ("manuallyUpdateApp".equals(action)) {
			
//			cordova.getActivity().runOnUiThread(new Runnable() {
//                public void run() {
//                	aa.manuallyUpdateApp();
//                	callbackContext.success(); // Thread-safe.
//                }
//            });
			this.manuallyUpdateApp();
			callbackContext.success();
			return true;
		} else if("autoUpdateApp".equals(action)){
			this.autoUpdateApp();
			callbackContext.success();
			return true;
		}
		return false;
	}

	public void initUrl(JSONArray args) throws JSONException{
		JSONObject params=args.optJSONObject(0);
		if(params==null){
			exceptionDialog("请输入地址参数");
		}
		downloadFile=params.getString("downloadFile");
		if(downloadFile==null){
			exceptionDialog("请输入文件下载地址参数:downloadFile");
		}
		
		serverVerUrl=params.getString("serverVerUrl");
		if(serverVerUrl==null){
			exceptionDialog("请输入获取服务器版本地址:serverVerUrl");
		}
		
	}
	public void exceptionDialog(String msg) {
		//int verCode = this.getVerCode();
		//String verName = this.getVerName();
		StringBuffer sb = new StringBuffer(msg);

		Dialog dialog = new AlertDialog.Builder(this.cordova.getActivity())
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
	
	/**
	 * 更新程序，如果不用更新了的话，就不会给出提示，如果需要更新才会给出提示
	 * @param args
	 */
	public void autoUpdateApp() {
		// this.cordova.getActivity();
		if (getServerVer()) {
			int verCode = this.getVerCode();
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
		if (getServerVer()) {
			int verCode = this.getVerCode();
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
	public int getVerCode() {
		int verCode = -1;
		try {
			// String packName = context.getPackageName();
			// verCode = context.getPackageManager().getPackageInfo(packName,
			// 0).versionCode;
			PackageManager packageManager = this.cordova.getActivity().getPackageManager();
			verCode=packageManager.getPackageInfo(this.cordova.getActivity().getPackageName(), 0).versionCode;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			Log.e("版本号获取异常", e.getMessage());
		}
		return verCode;
	}

	/**
	 * 获得版本名称
	 */
	public String getVerName() {
		String verName = "";
		try {
//			String packName = this.cordova.getActivity().getPackageName();
//			verName = this.cordova.getActivity().getPackageManager()
//					.getPackageInfo(packName, 0).versionName;
			PackageManager packageManager = this.cordova.getActivity().getPackageManager();
			verName=packageManager.getPackageInfo(this.cordova.getActivity().getPackageName(), 0).versionName;
		} catch (NameNotFoundException e) {
			Log.e("版本名称获取异常", e.getMessage());
		}
		return verName;
	}

	/**
	 * 从服务器端获得版本号与版本名称
	 * 
	 * @return
	 */
	public boolean getServerVer() {
		// 生成一个请求对象
        HttpGet httpGet = new HttpGet("http://www.baidu.com/");
        // 生成一个Http客户端对象
        HttpClient httpClient = new DefaultHttpClient();

        // 下面使用Http客户端发送请求，并获取响应内容

        InputStream inputStream = null;
        HttpResponse mHttpResponse = null;
        HttpEntity mHttpEntity = null;
        try
        {
        	exceptionDialog("0");
            // 发送请求并获得响应对象
            mHttpResponse = httpClient.execute(httpGet);
            // 获得响应的消息实体
            mHttpEntity = mHttpResponse.getEntity();
            exceptionDialog("1");
            // 获取一个输入流
            inputStream = mHttpEntity.getContent();
            exceptionDialog("2");
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(inputStream));

            String result = "";
            String line = "";
            exceptionDialog("3");
            while (null != (line = bufferedReader.readLine()))
            {
                result += line;
            }

            // 将结果打印出来，可以在LogCat查看
            System.out.println(result);
            exceptionDialog("4");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                inputStream.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return true;
        
//		HttpURLConnection httpConnection =null;
//		InputStreamReader reader =null;
//		BufferedReader bReader =null;
//		try {
//			URL url = new URL("http://www.baidu.com");
//			
//			httpConnection = (HttpURLConnection) url.openConnection();
//			//httpConnection.setDoInput(true);
//			//httpConnection.setDoOutput(true);
//			//httpConnection.setRequestMethod("GET");
//			//exceptionDialog("0");
//			//httpConnection.connect();
//			sdfsd
//			httpConnection.setConnectTimeout(6*1000);
//			if (httpConnection.getResponseCode() != 200) {
//				exceptionDialog("http连接失败!");
//			}
//			exceptionDialog("11");
//			reader = new InputStreamReader(httpConnection.getInputStream());
//			exceptionDialog("2");
//			bReader = new BufferedReader(reader);
//			exceptionDialog("3");
//			//String json = bReader.readLine();
//			StringBuffer strBuffer = new StringBuffer();
//			String line = null;
//			while ((line = bReader.readLine()) != null) {
//				strBuffer.append(line);
//			}
//			String json =strBuffer.toString();
//			exceptionDialog(json);
//			//JSONArray array = new JSONArray(json);
//			//JSONObject jsonObj = array.getJSONObject(0);
//			JSONObject jsonObj = new JSONObject(json);
//			newVerCode = Integer.parseInt(jsonObj.getString("verCode"));
//			newVerName = jsonObj.getString("verName");
//			exceptionDialog(newVerCode+"=="+newVerName);
//		} catch (Exception e) {
//			exceptionDialog(e.getMessage());
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return true;// 如果这里改为false 则不更新会退出程序
//		}finally {
//            if (httpConnection != null) {
//            	httpConnection.disconnect();
//            }
//            if (reader != null) {
//                try {
//                	reader.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            if (bReader != null) {
//                try {
//                	bReader.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
// 
//        }
//		return true;
	}

	/**
	 * 不更新版本
	 */
	public void notNewVersionUpdate() {
		int verCode = this.getVerCode();
		String verName = this.getVerName();
		StringBuffer sb = new StringBuffer();
		sb.append("当前版本：");
		sb.append(verName);
		sb.append(" Code:");
		sb.append(verCode);
		sb.append("\n已是最新版本，无需更新");
		Dialog dialog = new AlertDialog.Builder(this.cordova.getActivity())
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
	 * 更新版本
	 */
	public void doNewVersionUpdate() {
		int verCode = this.getVerCode();
		String verName = this.getVerName();
		StringBuffer sb = new StringBuffer();
		sb.append("当前版本：");
		sb.append(verName);
		sb.append(" Code:");
		sb.append(verCode);
		sb.append(",发现版本：");
		sb.append(newVerName);
		sb.append(" Code:");
		sb.append(newVerCode);
		sb.append(",是否更新");
		Dialog dialog = new AlertDialog.Builder(this.cordova.getActivity())
				.setTitle("软件更新")
				.setMessage(sb.toString())
				.setPositiveButton("更新", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						pd = new ProgressDialog(activity);
						pd.setTitle("正在下载");
						pd.setMessage("请稍后。。。");
						pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
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
		pd.show();
		new Thread() {
			public void run() {
				HttpClient client = new DefaultHttpClient();
				HttpGet get = new HttpGet(url);
				HttpResponse response;
				try {
					response = client.execute(get);
					HttpEntity entity = response.getEntity();
					long length = entity.getContentLength();
					InputStream is = entity.getContent();
					FileOutputStream fileOutputStream = null;
					if (is != null) {
						File file = new File(
								Environment.getExternalStorageDirectory(),
								UPDATE_SERVERAPK);
						fileOutputStream = new FileOutputStream(file);
						byte[] b = new byte[1024];
						int charb = -1;
						int count = 0;
						while ((charb = is.read(b)) != -1) {
							fileOutputStream.write(b, 0, charb);
							count += charb;
						}
					}
					fileOutputStream.flush();
					if (fileOutputStream != null) {
						fileOutputStream.close();
					}
					down();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}.start();
	}

	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {

			super.handleMessage(msg);
			pd.cancel();
			update();
		}
	};

	/**
	 * 下载完成，通过handler将下载对话框取消
	 */
	public void down() {
		new Thread() {
			public void run() {
				Message message = handler.obtainMessage();
				handler.sendMessage(message);
			}
		}.start();
	}

	/**
	 * 安装应用
	 */
	public void update() {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(new File(Environment
				.getExternalStorageDirectory(), UPDATE_SERVERAPK)),
				"application/vnd.android.package-archive");
		this.activity.startActivity(intent);
	}

}
