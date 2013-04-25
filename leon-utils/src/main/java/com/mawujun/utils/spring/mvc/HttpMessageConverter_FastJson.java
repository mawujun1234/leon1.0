package com.mawujun.utils.spring.mvc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.PropertyPreFilter;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.mawujun.utils.page.QueryResult;

/**
 * 在接收的时候，重载了
 * 在返回响应内容的时候，如果返回的不是JSONObject货JSONArray就调用原来的方法
 * @author Administrator
 *
 */
public class HttpMessageConverter_FastJson extends AbstractHttpMessageConverter<Object> {
	
	public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
	
	private String datePattern = "yyyy-MM-dd";//"yyyy-MM-dd HH:mm:ss"; 
	//List<String> datePatterns=new ArrayList<String>();
	 private static SerializeConfig serializeConfig = new SerializeConfig();     
	 static {         
		 serializeConfig.setAsmEnable(false);    
		 
	 } 
	
	public HttpMessageConverter_FastJson() {
		super(new MediaType("application", "json", DEFAULT_CHARSET));

		serializeConfig.put(Date.class, new SimpleDateFormatSerializer(datePattern));  
	}


	@Override
	protected boolean supports(Class<?> clazz) { 
//		if(clazz.isAssignableFrom(List.class)) {
//			System.out.println("当批量操作的时候，方法参数只能是数组接收，不能使用List等集合");
//			return false;
//		}
		return true;
	}
	
//	private Object stringToObject(String strFileContents,Class clazz){
//		JSONObject jsonObject = JSONObject.fromObject(strFileContents);
//		if (clazz.isAssignableFrom(PageRequest.class)) {
//			return StringToPageRequest_JsonLib.toPageRequest(jsonObject);
//		}
//
//		JsonConfig cfg = new JsonConfig();
//		cfg.setPropertySetStrategy(new IgnorePropertyStrategyWrapper(PropertySetStrategy.DEFAULT));
//		cfg.setRootClass(clazz);
//		Object o = JSONObject.toBean(jsonObject, cfg);// 日期报错,在前台要指定类型
//		return o;
//	}

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
					//Object o=stringToObject(strFileContents,clazz.getComponentType());
					Object o=JSON.parseObject(strFileContents, clazz.getComponentType());
					Object array=Array.newInstance(clazz.getComponentType(), 1);
					Array.set(array, 0, o);
					return array;
					// return JSONObjectUtils.toBean(strFileContents, clazz);
				} else if (strFileContents.startsWith("[")) {
					Class returnTypeClass = clazz.getComponentType();

					List<Object> list=JSON.parseArray(strFileContents, returnTypeClass);
					if(list!=null){
						Object array=Array.newInstance(returnTypeClass, list.size());
						int i=0;
						for(Object o:list){
							Array.set(array, i, o);
							i++;
						}
					} else {
						return null;
					}
					

				} 
			} else if(clazz.isAssignableFrom(List.class)){
				throw new HttpMessageNotReadableException("当批量操作的时候，方法参数只能是数组接收，不能使用List等集合");
			} else {//获取一个对象的时候
				if (strFileContents.startsWith("{")) {
					Object o=JSON.parseObject(strFileContents,clazz);
					return o;
				} else if (strFileContents.startsWith("[")) {
					List<Object> list=(List<Object>) JSON.parseArray(strFileContents, clazz);
					return list.get(list.size()-1);

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
	protected void writeInternal(Object object, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
		if (object == null) {
			return;
		}
		//JSONObject result=JSONObjectUtils.toJson(t);
		MediaType contentType = outputMessage.getHeaders().getContentType();
		Charset charset = contentType.getCharSet() != null ? contentType.getCharSet() : DEFAULT_CHARSET;
		try {
		if(object instanceof Map){
			SerializeWriter out = new SerializeWriter();
			JSONSerializer serializer = new JSONSerializer(out);     
			serializer.setDateFormat(datePattern);
			//SerializerFeature[] features = {SerializerFeature.UseISO8601DateFormat, SerializerFeature.UseSingleQuotes }; 
			serializer.config(SerializerFeature.WriteDateUseDateFormat,true);//SerializerFeature.WriteDateUseDateFormat
			serializer.config(SerializerFeature.UseSingleQuotes,true);//SerializerFeature.
			serializer.config(SerializerFeature.SkipTransientField,true);
			serializer.config(SerializerFeature.WriteEnumUsingToString,true);
			serializer.config(SerializerFeature.SortField,true);
			
			Map map=(Map)object;
			if(!map.containsKey("success")){
				map.put("success", true);
			}
			if(map.containsKey("filterPropertys")){
				String[] excludes=((String)map.get("filterPropertys")).split(",");
//				应该是这里的问题，因为返回的是List
//				SimplePropertyPreFilter filter = new SimplePropertyPreFilter(map.get("root").getClass() ); 
//				for(String str:excludes){
//					filter.getExcludes().add(str);
//				}
				SimplePropertyPreFilter filter =null;
				if(map.get("root") instanceof List){
					if(((List)map.get("root")).size()>0){
						filter = new SimplePropertyPreFilter(((List)map.get("root")).get(0).getClass() ); 
						for(String str:excludes){
							filter.getExcludes().add(str);
						}
					}
					
				} else {
					filter = new SimplePropertyPreFilter(map.get("root").getClass() ); 
					for(String str:excludes){
						filter.getExcludes().add(str);
					}
				}
		
				serializer.getPropertyPreFilters().add((PropertyPreFilter) filter);
			}
			serializer.write(object);
			FileCopyUtils.copy(serializer.toString(), new OutputStreamWriter(outputMessage.getBody(), charset));
			return;
		} else if(object instanceof QueryResult){
			QueryResult page=(QueryResult)object;
			ModelMap map=new ModelMap();
			map.put("root", page.getResult());
			map.put("totalProperty", page.getTotalItems());
			map.put("start", page.getStart());
			map.put("limit", page.getPageSize());
			map.put("pageNo", page.getPageNo());
			map.put("success", true);
			map.put("wheres", page.getWheres());
			map.put("sorts", page.getSorts());
			object=map;
		} else {
			ModelMap map=new ModelMap();
			map.put("root", object);
			map.put("success", true);
			object=map;
		
		}
		
		FileCopyUtils.copy(JSON.toJSONString(object,serializeConfig, SerializerFeature.PrettyFormat,SerializerFeature.UseSingleQuotes), new OutputStreamWriter(outputMessage.getBody(), charset));
		
		}catch(Exception e){
			e.printStackTrace();
		}
		

	}


	public String getDatePattern() {
		return datePattern;
	}


	public void setDatePattern(String datePattern) {
		this.datePattern = datePattern;
	}




}
