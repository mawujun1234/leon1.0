package com.mawujun.controller.spring.mvc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hibernate.collection.spi.PersistentCollection;
import org.hibernate.proxy.HibernateProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.alibaba.fastjson.serializer.ValueFilter;
import com.jayway.jsonpath.JsonPath;
import com.mawujun.repository.hibernate.HibernateUtils;
import com.mawujun.repository.idEntity.IdEntity;
import com.mawujun.utils.page.QueryResult;

/**
 * 在接收的时候，重载了
 * 在返回响应内容的时候，如果返回的不是JSONObject货JSONArray就调用原来的方法
 * @author mawujun
 *
 */
public class HttpMessageConverter_FastJson extends AbstractHttpMessageConverter<Object> {
	
	public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
	final static Logger logger = LoggerFactory.getLogger(HttpMessageConverter_FastJson.class);   
	
	private String datePattern = "yyyy-MM-dd";//"yyyy-MM-dd HH:mm:ss"; 
	//private static final  String filterPropertys="filterPropertys";//以逗号分隔
	//private static final  String onlyIds="onlyIds";
	//是否过滤hibernate未初始化的数据
	private boolean enableHibernateLazyInitializerFilter=true;
	


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
			logger.debug(ex.getMessage());
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
		
		SerializeWriter out = new SerializeWriter();
		JSONSerializer serializer = new JSONSerializer(out);     
		serializer.setDateFormat(datePattern);
		//SerializerFeature[] features = {SerializerFeature.UseISO8601DateFormat, SerializerFeature.UseSingleQuotes }; 
		serializer.config(SerializerFeature.WriteDateUseDateFormat,true);//SerializerFeature.WriteDateUseDateFormat
		serializer.config(SerializerFeature.UseSingleQuotes,true);//SerializerFeature.
		serializer.config(SerializerFeature.SkipTransientField,true);
		serializer.config(SerializerFeature.WriteEnumUsingToString,true);
		serializer.config(SerializerFeature.SortField,true);

		try {
			
		 if(object instanceof Map) {
			Map map=(Map)object;
			if((Boolean)map.get(ResultMap.enableHibernateLazyInitializerFilter)){
				serializer.getValueFilters().add(new HibernateLazyInitializerFilter());
			}
			if(!map.containsKey("success")){
				map.put("success", true);
			}
			if(map.containsKey(ResultMap.filterPropertysName)){
				doFilterPropertys(map, serializer);
//				String[] excludes=((String)map.get(ResultMap.filterPropertysName)).split(",");
//
//				SimplePropertyPreFilter filter =null;
//				if(map.get("root") instanceof List){
//					if(((List)map.get("root")).size()>0){
//						filter = new SimplePropertyPreFilter(((List)map.get("root")).get(0).getClass() ); 
//						for(String str:excludes){
//							filter.getExcludes().add(str);
//						}
//					}
//					
//				} else {
//					filter = new SimplePropertyPreFilter(map.get("root").getClass() ); 
//					for(String str:excludes){
//						filter.getExcludes().add(str);
//					}
//				}
//				if(filter!=null){
//					serializer.getPropertyPreFilters().add((PropertyPreFilter) filter);
//				}
//				map.remove(ResultMap.filterPropertysName);
			}
			
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
			
			if(page.getFilterPropertys()!=null && !"".equals(page.getFilterPropertys())){
				doFilterPropertys(map, serializer);
			}
			if(page.isEnableHibernateLazyInitializerFilter()){
				serializer.getValueFilters().add(new HibernateLazyInitializerFilter());
			}
		} else {
			ModelMap map=new ModelMap();
			map.put("root", object);
			map.put("success", true);
			object=map;
			serializer.getValueFilters().add(new HibernateLazyInitializerFilter());
		
		}
		
		doAllowSingle((ModelMap)object);
		//FileCopyUtils.copy(JSON.toJSONString(object,serializeConfig, SerializerFeature.PrettyFormat,SerializerFeature.UseSingleQuotes), new OutputStreamWriter(outputMessage.getBody(), charset));
		serializer.write(object);
		//System.out.println();
		//fastjson在处理循环依赖的时候出现的问题，出现了jsonpath的内容
		String jsonString=serializer.toString();
		jsonString=replaceJsonPath(jsonString);
		FileCopyUtils.copy(jsonString, new OutputStreamWriter(outputMessage.getBody(), charset));
		
		}catch(RuntimeException e){
			logger.error(e.getMessage(),e);
			
			throw e;
			//e.printStackTrace();
		}
		

	}
	/**
	 * 档root不是数组的时候就转换成数组
	 * @author mawujun email:mawujun1234@163.com qq:16064988
	 * @param map
	 */
	public void doAllowSingle(ModelMap map){
		if(map.get(ResultMap.allowSingle)!=null && !(Boolean)map.get(ResultMap.allowSingle)){
			Object root=map.get(ResultMap.root);
			if(!(root instanceof Collection) && !(root.getClass().isArray())){
				map.put(ResultMap.root, new Object[]{root});
			}
		}
	}
	
	public void doFilterPropertys(Map map,JSONSerializer serializer){
		String[] excludes=((String)map.get(ResultMap.filterPropertysName)).split(",");

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
		if(filter!=null){
			serializer.getPropertyPreFilters().add((PropertyPreFilter) filter);
		}
		map.remove(ResultMap.filterPropertysName);
	}
	//Pattern pattern = Pattern.compile("\\{\"\\$ref\":.*\"\\}");  
	//这里的？很重要，否则由于贪婪匹配的原因会造成问题
	//http://www.imkevinyang.com/2010/07/javajs%E5%A6%82%E4%BD%95%E4%BD%BF%E7%94%A8%E6%AD%A3%E5%88%99%E8%A1%A8%E8%BE%BE%E5%BC%8F%E5%8C%B9%E9%85%8D%E5%B5%8C%E5%A5%97html%E6%A0%87%E7%AD%BE.html
	String patStr="\\{\"\\$ref\":.*?\"\\}";
	Pattern pattern = Pattern.compile(patStr);  
	public String replaceJsonPath(final String jsonString){
        Matcher matcher = pattern.matcher(jsonString);
        String result=jsonString;
        while (matcher.find())
        {
        	//http://su1216.iteye.com/blog/1571083
        	//System.out.println("找到匹配的数据");
        	//System.out.println(jsonString);
        	String tmp = matcher.group(0);
        	//System.out.println("=========="+tmp);
        	String jsonPath=tmp.substring(9, tmp.length()-2);
        	//System.out.println(jsonPath);
        	
        	//System.out.println(tmp); 	
        	Object bb=JsonPath.read(jsonString, jsonPath);
        	//bb里面包含有正则表达式里的符号，所以需要先取出这部分内容才可以替换
        	bb=bb.toString().replaceAll(patStr, "{}");
        	//System.out.println(bb);
        	
        	result=matcher.replaceAll(bb.toString());
        	//System.out.println(result);
        	
        }
        return result;
	}


	public String getDatePattern() {
		return datePattern;
	}


	public void setDatePattern(String datePattern) {
		this.datePattern = datePattern;
	}



	private class HibernateLazyInitializerFilter implements ValueFilter {
		public Object process(Object source, String name, final Object value) {
	        if ( value!=null && value instanceof HibernateProxy) {
	        	if(value instanceof IdEntity){
	        		//现在只是把
		        	return new IdEntity(){
						@Override
						public void setId(Object id) {
							// TODO Auto-generated method stub
							
						}
						@Override
						public Object getId() {
							return HibernateUtils.getIdDirect((IdEntity)value);
						}
		        		
		        	};
	        	} else {        		
	        		return null;
	        	}
	        	
	            //return ((IdEntity)value).getId();
	        } else if(value instanceof PersistentCollection) {
	        	
	        	return null;
	        }
	        return value;
	    }
	}
	
	public boolean isEnableHibernateLazyInitializerFilter() {
		return enableHibernateLazyInitializerFilter;
	}


	public void setEnableHibernateLazyInitializerFilter(
			boolean enableHibernateLazyInitializerFilter) {
		this.enableHibernateLazyInitializerFilter = enableHibernateLazyInitializerFilter;
	}


}
