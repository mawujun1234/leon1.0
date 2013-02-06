package com.mawujun.utils.spring.mvc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.JSONUtils;
import net.sf.json.util.PropertySetStrategy;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.FileCopyUtils;

import com.mawujun.utils.jsonLib.IgnorePropertyStrategyWrapper;
import com.mawujun.utils.jsonLib.JSONObjectUtils;
import com.mawujun.utils.page.PageRequest;

/**
 * 在接收的时候，重载了
 * 在返回响应内容的时候，如果返回的不是JSONObject货JSONArray就调用原来的方法
 * @author Administrator
 *
 */
public class HttpMessageConverter_JsonLib extends AbstractHttpMessageConverter<Object> {
	
	public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
	
	//private String datePattern = "yyyy-MM-dd HH:mm:ss"; 
	List<String> datePatterns=new ArrayList<String>();
	
	public HttpMessageConverter_JsonLib() {
		super(new MediaType("application", "json", DEFAULT_CHARSET));
		
		datePatterns.add("yyyy-MM-dd");
		datePatterns.add("yyyy-MM-dd HH:mm:ss");
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(this.getDatePatterns().toArray(new String[this.getDatePatterns().size()])));
	}


	@Override
	protected boolean supports(Class<?> clazz) { 
		if(clazz.isAssignableFrom(List.class)) {
			System.out.println("当批量操作的时候，方法参数只能是数组接收，不能使用List等集合");
			return false;
		}
		return true;
	}
	
	private Object stringToObject(String strFileContents,Class clazz){
		JSONObject jsonObject = JSONObject.fromObject(strFileContents);
		if (clazz.isAssignableFrom(PageRequest.class)) {
			return StringToPageRequest_JsonLib.toPageRequest(jsonObject);
		}

		JsonConfig cfg = new JsonConfig();
		cfg.setPropertySetStrategy(new IgnorePropertyStrategyWrapper(PropertySetStrategy.DEFAULT));
		cfg.setRootClass(clazz);
		Object o = JSONObject.toBean(jsonObject, cfg);// 日期报错,在前台要指定类型
		return o;
	}

	@Override
	protected Object readInternal(Class<? extends Object> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
		InputStream bin=inputMessage.getBody();

		BufferedReader in = new BufferedReader(new InputStreamReader(bin));
		String strFileContents = FileCopyUtils.copyToString(in);
		if (strFileContents.length() == 0) {
			throw new HttpMessageConversionException("not data in post/get");
		}

		try {
			if(clazz.isArray()){//如果接受参数是数据，就把结果转换成数组
				if (strFileContents.startsWith("{")) {
					Object o=stringToObject(strFileContents,clazz.getComponentType());
					Object array=Array.newInstance(clazz.getComponentType(), 1);
					Array.set(array, 0, o);
					return array;
					// return JSONObjectUtils.toBean(strFileContents, clazz);
				} else if (strFileContents.startsWith("[")) {
					Class returnTypeClass = clazz.getComponentType();
					JsonConfig cfg = new JsonConfig();
					cfg.setPropertySetStrategy(new IgnorePropertyStrategyWrapper(PropertySetStrategy.DEFAULT));
					cfg.setRootClass(returnTypeClass);
					
					JSONArray jsonArray = JSONArray.fromObject(strFileContents);				
					Object array=Array.newInstance(returnTypeClass, jsonArray.size());
					int i=0;
					for(Object jsonObject:jsonArray){
						Object o = JSONObject.toBean((JSONObject)jsonObject, cfg);;
						Array.set(array, i, o);
						i++;
					}
					
					return array;

				} 
			} else if(clazz.isAssignableFrom(List.class)){
				throw new HttpMessageNotReadableException("当批量操作的时候，方法参数只能是数组接收，不能使用List等集合");
			} else {//获取一个对象的时候
				if (strFileContents.startsWith("{")) {
					Object o=stringToObject(strFileContents,clazz);
					return o;
				} else if (strFileContents.startsWith("[")) {
					JSONArray jsonArray = JSONArray.fromObject(strFileContents);
					JsonConfig cfg = new JsonConfig();
					cfg.setPropertySetStrategy(new IgnorePropertyStrategyWrapper(PropertySetStrategy.DEFAULT));
					cfg.setRootClass(clazz);
					
					return JSONObject.toBean((JSONObject)jsonArray.get(jsonArray.size()-1), cfg);

				} 
			}
			return null;
			
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new HttpMessageNotReadableException("解析json字符串出错: "
					+ ex.getMessage(), ex);
		}

	}


	@Override
	protected void writeInternal(Object t, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
		if (t == null) {
			return;
		}
		JSONObject result=JSONObjectUtils.toJson(t);
		MediaType contentType = outputMessage.getHeaders().getContentType();
		Charset charset = contentType.getCharSet() != null ? contentType.getCharSet() : DEFAULT_CHARSET;
		FileCopyUtils.copy(result.toString(), new OutputStreamWriter(outputMessage.getBody(), charset));
		
		
		
//		//返回数据MappingJacksonHttpMessageConverter
//		//FileCopyUtils.copy(s, new OutputStreamWriter(outputMessage.getBody(), charset));
//		if(t==null){
//			return;
//		}
//		MediaType contentType = outputMessage.getHeaders().getContentType();
//		Charset charset = contentType.getCharSet() != null ? contentType.getCharSet() : DEFAULT_CHARSET;
//		if(t.getClass()==JSONObject.class || t.getClass()==JSONArray.class){
//			FileCopyUtils.copy(t.toString(), new OutputStreamWriter(outputMessage.getBody(), charset));
//		} else {
//			if(t.getClass().isArray()){
//				JSONArray aa=JSONArray.fromObject(t);
//				FileCopyUtils.copy(aa.toString(), new OutputStreamWriter(outputMessage.getBody(), charset));
//			} else if(t instanceof QueryResult){
//				JSONObject result=new JSONObject();
//				QueryResult qr=(QueryResult)t;
//				result.put("totalProperty", qr.getTotalCount());
//				JSONArray root=new JSONArray();
//				for(Object obj:qr.getResult()){
//					JSONObject json=JSONObjectUtils.toJson(obj);
//					root.add(json);
//				}
//				result.put("root", root);
//				FileCopyUtils.copy(result.toString(), new OutputStreamWriter(outputMessage.getBody(), charset));
//			} else {
//				JSONObject aa=JSONObjectUtils.toJsonIgnoreAllAssociate(t);
//				FileCopyUtils.copy(aa.toString(), new OutputStreamWriter(outputMessage.getBody(), charset));
//			}
//		}
	}

	public List<String> getDatePatterns() {
		return datePatterns;
	}

	public void setDatePatterns(List<String> datePatterns) {
		this.datePatterns = datePatterns;
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(this.getDatePatterns().toArray(new String[this.getDatePatterns().size()])));
	}


}
