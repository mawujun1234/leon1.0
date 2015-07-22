package com.mawujun.controller.spring.mvc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.List;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.FileCopyUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;
import com.mawujun.controller.spring.mvc.json.JsonConfigHolder;

/**
 * 在接收的时候，重载了
 * 在返回响应内容的时候，如果返回的不是JSONObject货JSONArray就调用原来的方法
 * @author mawujun
 *
 */
public class MappingFastJson2HttpMessageConverter extends AbstractHttpMessageConverter<Object> {
	
	public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
	final static Logger logger = LoggerFactory.getLogger(MappingFastJson2HttpMessageConverter.class);   
	
	private String datePattern = "yyyy-MM-dd";//"yyyy-MM-dd HH:mm:ss"; 
	//private static final  String filterPropertys="filterPropertys";//以逗号分隔
	//private static final  String onlyIds="onlyIds";
	//是否过滤hibernate未初始化的数据
	//private boolean enableHibernateLazyInitializerFilter=true;
	


	//List<String> datePatterns=new ArrayList<String>();
	 private static SerializeConfig serializeConfig = new SerializeConfig();     
	 static {         
		
		 serializeConfig.setAsmEnable(false);    
		 
	 } 
	
	public MappingFastJson2HttpMessageConverter() {
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
						return array;
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
			//logger.debug(ex.getMessage());
			logger.error("解析json字符串出错:", ex);
			throw new HttpMessageNotReadableException("解析json字符串出错: "+ ex.getMessage(), ex);
		}

	}

//	public JSONSerializer getJSONSerializer(Object object){
//		//JSONObject result=JSONObjectUtils.toJson(t);
//		
//		
//		SerializeWriter out = new SerializeWriter();
//		JSONSerializer serializer = new JSONSerializer(out);     
//		serializer.setDateFormat(JsonConfigHolder.getDatePattern());
//		//SerializerFeature[] features = {SerializerFeature.UseISO8601DateFormat, SerializerFeature.UseSingleQuotes }; 
//		serializer.config(SerializerFeature.WriteDateUseDateFormat,true);//SerializerFeature.WriteDateUseDateFormat
//		serializer.config(SerializerFeature.UseSingleQuotes,true);//SerializerFeature.
//		serializer.config(SerializerFeature.SkipTransientField,true);
//		serializer.config(SerializerFeature.WriteEnumUsingToString,true);
//		serializer.config(SerializerFeature.WriteMapNullValue,JsonConfigHolder.getWriteMapNullValue());
//		//serializer.config(SerializerFeature.SortField,true);
//		 //关闭循环引用的配置
//		serializer.config(SerializerFeature.DisableCircularReferenceDetect, JsonConfigHolder.getDisableCircularReferenceDetect());
//		
//		if(JsonConfigHolder.getEnableHibernateLazyInitializerFilter()){
//			HibernateLazyInitializerFilter hibernateFilter=new HibernateLazyInitializerFilter();
//			serializer.getValueFilters().add(hibernateFilter);
//		}
//
//		doFilterPropertys(object, serializer);
//		return serializer;
//	}

	 
	@Override
	protected void writeInternal(Object object, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
		if (object == null) {
			return;
		}
		MediaType contentType = outputMessage.getHeaders().getContentType();
		Charset charset = contentType.getCharSet() != null ? contentType.getCharSet() : DEFAULT_CHARSET;
//		JSONSerializer serializer=getJSONSerializer(object);
		
		try {
//			if(!JsonConfigHolder.getAutoWrap()){
//				if(object instanceof Map){
//					doExtProperties((Map)object);
//				}		
//				serializer.write(object);
//				String jsonString=serializer.toString();
//				if(!(object instanceof Map)){
//					jsonString=doExtProperties(jsonString);
//				}
//	
//				jsonString=replaceJsonPath(jsonString);
//				
//				FileCopyUtils.copy(jsonString, new OutputStreamWriter(outputMessage.getBody(), charset));
//				return;
//			}
//			
//
//		ModelMap map=new ModelMap();
//		if(object instanceof QueryResult){
//			QueryResult page=(QueryResult)object;
//			//ModelMap map=new ModelMap();
//			map.put(JsonConfigHolder.getRootName(), page.getResult());
//			map.put(JsonConfigHolder.getTotalName(), page.getTotalItems());
//			map.put(JsonConfigHolder.getStartName(), page.getStart());
//			map.put(JsonConfigHolder.getLimitName(), page.getPageSize());
//			map.put(JsonConfigHolder.getPageNoName(), page.getPageNo());
//			map.put(JsonConfigHolder.getSuccessName(), true);
//			map.put("wheres", page.getWheres());
//			map.put("sorts", page.getSorts());
//			//object=map;
//
//		} else {
//			
//			map.put(JsonConfigHolder.getRootName(), object);
//			map.put(JsonConfigHolder.getSuccessName(), true);
//			if(object instanceof Collection){
//				map.put(JsonConfigHolder.getTotalName(), ((Collection)object).size());
//			} else {
//				Class c=object.getClass();
//				if(c.isArray()){
//					map.put(JsonConfigHolder.getTotalName(), ((Object[])object).length);
//				} else {
//					map.put(JsonConfigHolder.getTotalName(),1);
//				}
//
//			}
//			//object=map;
//		}
//		
//		
//		serializer.write(map);
//		String jsonString=serializer.toString();
//		jsonString=replaceJsonPath(jsonString);
		
		FileCopyUtils.copy( FastJsonToStringUtils.getJsonString(object), new OutputStreamWriter(outputMessage.getBody(), charset));
		
		}catch(RuntimeException e){
			logger.error(e.getMessage(),e);
			e.printStackTrace();
			throw e;
			
		}  finally {
			JsonConfigHolder.remove();
		}
		

	}
	
//	public String  getJsonString(Object object){
//		JSONSerializer serializer=getJSONSerializer(object);
//
//			if(!JsonConfigHolder.getAutoWrap()){
//				if(object instanceof Map){
//					doExtProperties((Map)object);
//				}		
//				serializer.write(object);
//				String jsonString=serializer.toString();
//				if(!(object instanceof Map)){
//					jsonString=doExtProperties(jsonString);
//				}
//	
//				jsonString=replaceJsonPath(jsonString);
//				
//				//FileCopyUtils.copy(jsonString, new OutputStreamWriter(outputMessage.getBody(), charset));
//				return jsonString;
//			}
//			
//
//		ModelMap map=new ModelMap();
//		if(object instanceof QueryResult){
//			QueryResult page=(QueryResult)object;
//			//ModelMap map=new ModelMap();
//			map.put(JsonConfigHolder.getRootName(), page.getResult());
//			map.put(JsonConfigHolder.getTotalName(), page.getTotalItems());
//			map.put(JsonConfigHolder.getStartName(), page.getStart());
//			map.put(JsonConfigHolder.getLimitName(), page.getPageSize());
//			map.put(JsonConfigHolder.getPageNoName(), page.getPageNo());
//			map.put(JsonConfigHolder.getSuccessName(), true);
//			map.put("wheres", page.getWheres());
//			map.put("sorts", page.getSorts());
//			//object=map;
//
//		} else {
//			
//			map.put(JsonConfigHolder.getRootName(), object);
//			map.put(JsonConfigHolder.getSuccessName(), true);
//			if(object instanceof Collection){
//				map.put(JsonConfigHolder.getTotalName(), ((Collection)object).size());
//			} else {
//				Class c=object.getClass();
//				if(c.isArray()){
//					map.put(JsonConfigHolder.getTotalName(), ((Object[])object).length);
//				} else {
//					map.put(JsonConfigHolder.getTotalName(),1);
//				}
//
//			}
//			//object=map;
//		}
//		
//		
//		serializer.write(map);
//		String jsonString=serializer.toString();
//		jsonString=replaceJsonPath(jsonString);
//		return jsonString;
//	}
//	
//	/**
//	 * 处理额外的属性
//	 */
//	public void doExtProperties(Map obj){
//		if(JsonConfigHolder.hasExtProperty()){
//			//if(obj instanceof Map){
//			obj.putAll(JsonConfigHolder.getExtProperties());
//			//}
//		}
//	}
//	/**
//	 * 处理额外的属性
//	 * @throws Exception 
//	 */
//	public String doExtProperties(String jsonString) throws RuntimeException{
//		
//		char lastChar=jsonString.charAt(jsonString.length()-1);
//		if(JsonConfigHolder.hasExtProperty()){
//			if(lastChar=='}'){
//				//serializer.c
//				Map object=JsonConfigHolder.getExtProperties();
//				JSONSerializer serializer=getJSONSerializer(object);
//				serializer.write(object);
//				String jsonStr=serializer.toString();
//				jsonStr=jsonStr.substring(1,jsonStr.length()-1);
//				return jsonString.substring(0, jsonString.length()-1)+","+jsonStr+"}";
//			} else {
//				throw new RuntimeException("只有转化为json对象的才能添加额外属性，数组类的对象不能添加额外属性!");
//			}
//		} else {
//			return jsonString;
//		}
//	}
//
//	
//	
//	public void doFilterPropertys(Object root ,JSONSerializer serializer) {
//		if(JsonConfigHolder.getFilterPropertys()==null || "".equals(JsonConfigHolder.getFilterPropertys())){
//			return;
//		}
//		
//		if((root instanceof HibernateInvoke) ){
//			return;
//		}
//		//
//		if(root  instanceof PersistentCollection){
//			PersistentCollection aa=(PersistentCollection)root;
//        	if(!aa.wasInitialized()){
//        		return;
//        	}
//		}
//		
//		String[] excludes=JsonConfigHolder.getFilterPropertys().split(",");//((String)map.get(ResultMap.filterPropertysName)).split(",");
//		//为定义了的类进行属性过滤
//		if(JsonConfigHolder.getFilterClass()!=null&& JsonConfigHolder.getFilterClass().length>0){
//			for(Class clazz:JsonConfigHolder.getFilterClass()){
//				SimplePropertyPreFilter filter = new SimplePropertyPreFilter(clazz); 
//				for(String str:excludes){
//					filter.getExcludes().add(str);
//				}
//				if(filter!=null){
//					serializer.getPropertyPreFilters().add((PropertyPreFilter) filter);
//				}
//			}
//		} else {
//			SimplePropertyPreFilter filter =null;
//			//自己进行判断
//			if(root instanceof Collection ){
//				
//				if(((Collection)root).size()>0){
//					Iterator iterator=((Collection)root).iterator();
//					Object obj=iterator.next();
//					filter = new SimplePropertyPreFilter(obj.getClass() ); 
//					for(String str:excludes){
//						filter.getExcludes().add(str);
//					}
//				}
//				
//			} else {
//				filter = new SimplePropertyPreFilter(root.getClass() ); 
//				for(String str:excludes){
//					filter.getExcludes().add(str);
//				}
//			}
//			if(filter!=null){
//				serializer.getPropertyPreFilters().add((PropertyPreFilter) filter);
//			}
//		}
//		
//	}
//	//Pattern pattern = Pattern.compile("\\{\"\\$ref\":.*\"\\}");  
//	//这里的？很重要，否则由于贪婪匹配的原因会造成问题
//	//http://www.imkevinyang.com/2010/07/javajs%E5%A6%82%E4%BD%95%E4%BD%BF%E7%94%A8%E6%AD%A3%E5%88%99%E8%A1%A8%E8%BE%BE%E5%BC%8F%E5%8C%B9%E9%85%8D%E5%B5%8C%E5%A5%97html%E6%A0%87%E7%AD%BE.html
//	String patStr="\\{\"\\$ref\":.*?\"\\}";
//	Pattern pattern = Pattern.compile(patStr);  
//	public String replaceJsonPath(final String jsonString){
//		if(JsonConfigHolder.getDisableCircularReferenceDetect()){
//			return jsonString;
//		}
//        Matcher matcher = pattern.matcher(jsonString);
//        String result=jsonString;
//        while (matcher.find())
//        {
//        	//当后台返回的时候有问题，测试 业务类型的时候有问题
//        	//http://su1216.iteye.com/blog/1571083
//        	//System.out.println("找到匹配的数据");
//        	//System.out.println(jsonString);
//        	String tmp = matcher.group(0);
//        	//System.out.println("=========="+tmp);
//        	String jsonPath=tmp.substring(9, tmp.length()-2);
//        	//System.out.println(jsonPath);
//        	
//        	//System.out.println(tmp); 	
//        	Object bb=JsonPath.read(jsonString, jsonPath);
//        	//bb里面包含有正则表达式里的符号，所以需要先取出这部分内容才可以替换
//        	bb=bb.toString().replaceAll(patStr, "{}");
//        	//System.out.println(bb);
//        	
//        	result=matcher.replaceAll(bb.toString());
//        	//System.out.println(result);
//        	
//        }
//        return result;
//	}


	public String getDatePattern() {
		return datePattern;
	}


	public void setDatePattern(String datePattern) {
		this.datePattern = datePattern;
	}



//	private class HibernateLazyInitializerFilter implements ValueFilter {
//		public Object process(Object source, String name, final Object value) {
//	        if ( value!=null && value instanceof HibernateInvoke) {
//	        	if(value instanceof IdEntity){
//	        		//现在只是把
//		        	return new IdEntity(){
//						@Override
//						public void setId(Object id) {
//							// TODO Auto-generated method stub
//							
//						}
//						@Override
//						public Object getId() {
//							return HibernateUtils.getIdDirect((IdEntity)value);
//						}
//		        		
//		        	};
//	        	} else {        		
//	        		return null;
//	        	}
//	        	
//	            //return ((IdEntity)value).getId();
//	        } else if(value instanceof PersistentCollection) {
//	        	PersistentCollection aa=(PersistentCollection)value;
//	        	if(aa.wasInitialized()){
//	        		return aa;
//	        	}
//	        	return null;
//	        }
//	        return value;
//	    }
//	}
	

}
