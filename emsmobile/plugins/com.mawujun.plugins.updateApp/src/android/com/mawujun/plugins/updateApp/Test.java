package com.mawujun.plugins.updateApp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

public class Test {

	public static void main(String[] args) {
		String serverVerUrl="http://172.16.3.10:8080/apkVersion.js";
		// TODO Auto-generated method stub
		HttpURLConnection httpConnection =null;
		InputStreamReader reader =null;
		BufferedReader bReader =null;
		try {
			URL url = new URL(serverVerUrl);
			
			httpConnection = (HttpURLConnection) url.openConnection();
			//httpConnection.setDoInput(true);
			//httpConnection.setDoOutput(true);
			//httpConnection.setRequestMethod("GET");
			//exceptionDialog("0");
			//httpConnection.connect();
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
			//exceptionDialog(json);
			//JSONArray array = new JSONArray(json);
			//JSONObject jsonObj = array.getJSONObject(0);
			JSONObject jsonObj = new JSONObject(json);
			int newVerCode = Integer.parseInt(jsonObj.getString("verCode"));
			String newVerName = jsonObj.getString("verName");
			//exceptionDialog(newVerCode+"=="+newVerName);
		} catch (Exception e) {
			//exceptionDialog(e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
			//return true;// 如果这里改为false 则不更新会退出程序
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
	}

}
