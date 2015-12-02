package com.mawujun.mobile.geolocation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

public class Test {

	public static void main(String[] args) throws ClientProtocolException, IOException {
		// TODO Auto-generated method stub
		HttpPost httpPost = new HttpPost("http://localhost:8080/baseinfo/EquipmentTypeApp.jsp");
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
//		params.add(new BasicNameValuePair("sessionId", this.getSessionId()));
//		params.add(new BasicNameValuePair("loginName", this.getLoginName()));
//		params.add(new BasicNameValuePair("uuid", this.getUuid()));
		
//		Iterator<String> iterator=params.keys();
//		while(iterator.hasNext()){
//			String key=iterator.next();
//			if("uploadUrl".equals(key)){
//				continue;
//			}
//			nameValuePairs.add(new BasicNameValuePair(key, params.getString(key)));
//		}
//		nameValuePairs.add(new BasicNameValuePair("longitude", location.getLongitude()+""));
//		nameValuePairs.add(new BasicNameValuePair("latitude", location.getLatitude()+""));
//		nameValuePairs.add(new BasicNameValuePair("altitude", (location.getAltitude()==Double.MIN_VALUE?0:location.getAltitude())+""));//默认值Double.MIN_VALUE,但是数据库存储会出问题，所以默认值就改成0
//		nameValuePairs.add(new BasicNameValuePair("radius", location.getRadius()+""));
//		nameValuePairs.add(new BasicNameValuePair("direction", location.getDirection()+""));
//		nameValuePairs.add(new BasicNameValuePair("speed", location.getSpeed()+""));
//		//nameValuePairs.add(new BasicNameValuePair("loc_time",location.getTime()));//不使用这个，因为百度会缓存金维度
//		nameValuePairs.add(new BasicNameValuePair("loc_type",(location.getLocType()==BDLocation.TypeGpsLocation?"gps":"network")));
//		nameValuePairs.add(new BasicNameValuePair("loc_time",format.format(loc_time)));//String，时间，ex:2010-01-01 14:01:01
//		nameValuePairs.add(new BasicNameValuePair("loc_time_interval",loc_time_interval+""));
//		nameValuePairs.add(new BasicNameValuePair("distance", distance+""));
//		
//		httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8));
		
		String sessionId="9c5c49ab-d686-486a-ada4-3f9da3abc359";
		httpPost.setHeader("Cookie", "JSESSIONID=" + sessionId+"; sid="+sessionId);
		//httpPost.setHeader("Set-Cookie", "JSESSIONID=" + sessionId+"; sid="+sessionId);
		
		
		
//		HttpParams httpParameters = new BasicHttpParams();
//		HttpConnectionParams.setConnectionTimeout(httpParameters, 50000);
//		// Set the default socket timeout (SO_TIMEOUT) // in milliseconds which is the timeout for waiting for data.  
//	    HttpConnectionParams.setSoTimeout(httpParameters, 30000);  
	    HttpClient client = new DefaultHttpClient();
	    HttpResponse httpResponse = client.execute(httpPost);
		if (httpResponse.getStatusLine().getStatusCode() == 200) {			
			
			System.out.println("访问成功");
		} else {
			//这里响应代码不是200 页正茬插入了
			//Toast.makeText(activityContex, "发送经纬度到后台失败!"+httpResponse.getStatusLine().getStatusCode()+this.getUploadUrl(), Toast.LENGTH_LONG).show();
			//toast("发送经纬度到后台失败!",Toast.LENGTH_LONG);
			System.out.println("访问失败");
		}
	}

}
