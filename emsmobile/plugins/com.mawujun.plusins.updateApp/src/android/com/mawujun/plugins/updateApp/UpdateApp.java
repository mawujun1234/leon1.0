package com.mawujun.plugins.updateApp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
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
 * ������µ�app���
 * 
 * @author mawujun
 * 
 */
public class UpdateApp extends CordovaPlugin {
	public CallbackContext callbackContext;
	int newVerCode = -1;// �°汾�ţ�����˵İ汾��
	String newVerName = "";//�°汾���ƣ�����˵İ汾����
	
	ProgressDialog pd = null;
	String UPDATE_SERVERAPK = "ApkUpdateAndroid.apk";
	String downloadFile = "http://192.168.0.100:88/phoneGap_jqm.apk";
	String serverVerUrl = "http://172.16.3.10:8080/version.js";// ���������汾��url
	Activity activity;

	@Override
	public boolean execute(String action, JSONArray args,
			CallbackContext callbackContext) throws JSONException {
		this.callbackContext = callbackContext;
		activity = this.cordova.getActivity();
		// ��鲢���³���app
		if ("checkOrUpdateApp".equals(action)) {
			this.updateVersion(args);

			callbackContext.success();
			return true;
		}
		return false;
	}

//	/**
//	 * ��鲢���³����app
//	 * 
//	 * @param args
//	 */
//	public void checkOrUpdateApp(JSONArray args) {
//
//	}

	public void updateVersion(JSONArray args) {
		// this.cordova.getActivity();
		if (getServerVer()) {
			int verCode = this.getVerCode();
			if (newVerCode > verCode) {
				doNewVersionUpdate();// ���°汾
			} else {
				// alert("")
				notNewVersionUpdate();// ��ʾ�������°汾
			}
		}
	}

	/**
	 * ��ð汾��
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
			Log.e("�汾�Ż�ȡ�쳣", e.getMessage());
		}
		return verCode;
	}

	/**
	 * ��ð汾����
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
			Log.e("�汾���ƻ�ȡ�쳣", e.getMessage());
		}
		return verName;
	}

	/**
	 * �ӷ������˻�ð汾����汾����
	 * 
	 * @return
	 */
	public boolean getServerVer() {
		try {
			URL url = new URL(serverVerUrl);
			HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
			httpConnection.setDoInput(true);
			httpConnection.setDoOutput(true);
			httpConnection.setRequestMethod("GET");
			httpConnection.connect();
			InputStreamReader reader = new InputStreamReader(
					httpConnection.getInputStream());
			BufferedReader bReader = new BufferedReader(reader);
			String json = bReader.readLine();
			JSONArray array = new JSONArray(json);
			JSONObject jsonObj = array.getJSONObject(0);
			newVerCode = Integer.parseInt(jsonObj.getString("verCode"));
			newVerName = jsonObj.getString("verName");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return true;// ��������Ϊfalse �򲻸��»��˳�����
		}
		return true;
	}

	/**
	 * �����°汾
	 */
	public void notNewVersionUpdate() {
		int verCode = this.getVerCode();
		String verName = this.getVerName();
		StringBuffer sb = new StringBuffer();
		sb.append("��ǰ�汾��");
		sb.append(verName);
		sb.append(" Code:");
		sb.append(verCode);
		sb.append("\n�������°汾���������");
		Dialog dialog = new AlertDialog.Builder(this.cordova.getActivity())
				.setTitle("�������").setMessage(sb.toString())
				.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						activity.finish();
					}
				}).create();
		dialog.show();
	}

	/**
	 * ���°汾
	 */
	public void doNewVersionUpdate() {
		int verCode = this.getVerCode();
		String verName = this.getVerName();
		StringBuffer sb = new StringBuffer();
		sb.append("��ǰ�汾��");
		sb.append(verName);
		sb.append(" Code:");
		sb.append(verCode);
		sb.append(",���ְ汾��");
		sb.append(newVerName);
		sb.append(" Code:");
		sb.append(newVerCode);
		sb.append(",�Ƿ����");
		Dialog dialog = new AlertDialog.Builder(this.cordova.getActivity())
				.setTitle("�������")
				.setMessage(sb.toString())
				.setPositiveButton("����", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						pd = new ProgressDialog(activity);
						pd.setTitle("��������");
						pd.setMessage("���Ժ󡣡���");
						pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
						downFile(downloadFile);
					}
				})
				.setNegativeButton("�ݲ�����",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								// finish();
							}
						}).create();
		// ��ʾ���¿�
		dialog.show();
	}

	/**
	 * ����apk
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
	 * ������ɣ�ͨ��handler�����ضԻ���ȡ��
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
	 * ��װӦ��
	 */
	public void update() {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(new File(Environment
				.getExternalStorageDirectory(), UPDATE_SERVERAPK)),
				"application/vnd.android.package-archive");
		this.activity.startActivity(intent);
	}

}
