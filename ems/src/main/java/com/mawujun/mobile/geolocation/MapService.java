package com.mawujun.mobile.geolocation;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mawujun.baseinfo.Pole;
import com.mawujun.baseinfo.PoleRepository;
import com.mawujun.utils.StringUtils;

@Service
public class MapService {
	private static Logger logger = LogManager.getLogger(MapService.class);

	@Autowired
	private PoleRepository poleRepository;

	private static String url = "http://api.map.baidu.com/geocoder/v2/?address=aaaaaaaaaaaaaaaaaaaaaa&output=json&ak=ED0fe5c7c869da5ee4260b4006e811b8&callback=showLocation";
	//http://api.map.baidu.com/geoconv/v1/?coords=116.3786889372559,39.90762965106183;116.38632786853032,39.90795884517671;116.39534009082035,39.907432133833574;116.40624058825688,39.90789300648029;116.41413701159672,39.90795884517671&from=1&to=5&ak=E4805d16520de693a3fe707cdc962045&callback=BMap._rd._cbk16761
	private static String transform_url = "http://api.map.baidu.com/geoconv/v1/?coords=aaaaaaaaaaaaaaaaaaaaaa&from=1&to=5&ak=ED0fe5c7c869da5ee4260b4006e811b8&callback=showLocation";
										 //http://api.map.baidu.com/geoconv/v1/?coords=116.32715863448607,39.990912172420714&from=1&to=5&ak=E4805d16520de693a3fe707cdc962045&callback=BMap._rd._cbk26948
	public void transform() {
		List<Pole> poles=poleRepository.queryNoTransformPoles();
		StringBuilder builder=new StringBuilder();
		int i=1;
		for(Pole pole:poles){
			builder.append(pole.getLongitude_orgin()+","+pole.getLatitude_orgin());

			transform1(pole.getCode(),builder.toString());
			builder=new StringBuilder();
			i++;
		}
	}
	/**
	 * 把标准的经纬度转换为百度的经纬度
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @param address
	 * @return
	 */
	private void transform1(String code,String coords_str) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httppost = new HttpGet(transform_url.replaceAll("aaaaaaaaaaaaaaaaaaaaaa",  org.springframework.util.StringUtils.trimAllWhitespace(coords_str)));
	
		CloseableHttpResponse response = null;
		String responseStr=null;
		try {
			response = httpclient.execute(httppost);
			// System.out.println(response.getStatusLine());
			HttpEntity entity = response.getEntity();
			responseStr=EntityUtils.toString(entity);
			//String[] address = responseStr.split("\\(");
           // String[] addrJson = address[1].split("\\)");
			if(responseStr.lastIndexOf(')')!=-1){
				responseStr=responseStr.substring(responseStr.indexOf("(")+1, responseStr.length()-1);
			} else {
				return;
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			logger.error(e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			logger.error(e);
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					// e.printStackTrace();
					logger.error(e);
				}
			}
		}
		logger.info(responseStr);
		JSONObject jsonObject = JSON.parseObject(responseStr);
		if (!jsonObject.containsKey("result")) {
			return ;
		}

		JSONArray coords = jsonObject.getJSONArray("result");
		for(int i=0;i<coords.size();i++){
			JSONObject data=coords.getJSONObject(i);
			poleRepository.updateLngLatByPoleCode(code, data.getString("x"), data.getString("y"));
			
		}

	}
	/**
	 * 获取某个地址的经纬度
	 * 
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @param address
	 * @return 如果发生异常，将会返回null,如果获取不到经纬度也会返回null
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public String[] getLngLat(String address) {
		if (address == null || "".equals(address)) {
			return null;
		}

		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httppost = new HttpGet(url.replaceAll("aaaaaaaaaaaaaaaaaaaaaa",  org.springframework.util.StringUtils.trimAllWhitespace(address)));
	
		CloseableHttpResponse response = null;
		String responseStr=null;
		try {
			response = httpclient.execute(httppost);
			// System.out.println(response.getStatusLine());
			HttpEntity entity = response.getEntity();
			responseStr=EntityUtils.toString(entity);
			//String[] address = responseStr.split("\\(");
           // String[] addrJson = address[1].split("\\)");
			if(responseStr.lastIndexOf(')')!=-1){
				responseStr=responseStr.substring(responseStr.indexOf("(")+1, responseStr.length()-1);
			} else {
				return null;
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			logger.error(e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			logger.error(e);
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					// e.printStackTrace();
					logger.error(e);
				}
			}
		}
		logger.info(responseStr);
		JSONObject jsonObject = JSON.parseObject(responseStr);
		if (!jsonObject.containsKey("result")) {
			return null;
		}

		if (!jsonObject.getJSONObject("result").containsKey("location")) {
			return null;
		}
		JSONObject coord = jsonObject.getJSONObject("result").getJSONObject("location");

		String[] result = new String[2];
		result[0] = coord.get("lng").toString();
		result[1] = coord.get("lat").toString();
		return result;
	}

	public String initAllPoleNoLngLat() {
		StringBuilder error=new StringBuilder("");
		// 查询所有还没有经纬度的点位
		List<Pole> poles = poleRepository.queryNoLngLatPole();
		for (Pole pole : poles) {
			String[] result = null;
			if(StringUtils.hasText(pole.getAddress())){
				result = getLngLat(pole.geetFullAddress());
			}
			if (result != null) {
				//pole.setLongitude(result[0]);// 经度
				//pole.setLatitude(result[1]);
				poleRepository.updateCoordes(result[0],result[1],pole.getId());
			} else {
				
				error.append(pole.getCode());
				error.append(",");
			}
		}
		
		return error.toString();
		
	}
}
