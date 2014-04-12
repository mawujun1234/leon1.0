package com.mawujun.controller.spring.mvc;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hibernate.collection.spi.PersistentCollection;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.ui.ModelMap;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.PropertyPreFilter;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.jayway.jsonpath.JsonPath;
import com.mawujun.controller.spring.mvc.json.JsonConfigHolder;
import com.mawujun.repository.hibernate.HibernateUtils;
import com.mawujun.repository.idEntity.IdEntity;
import com.mawujun.utils.page.QueryResult;

public class CopyOfFastJsonToStringUtils {
	
	private static JSONSerializer getJSONSerializer(Object object){
		//JSONObject result=JSONObjectUtils.toJson(t);
		
		
		SerializeWriter out = new SerializeWriter();
		JSONSerializer serializer = new JSONSerializer(out);     
		serializer.setDateFormat(JsonConfigHolder.getDatePattern());
		//SerializerFeature[] features = {SerializerFeature.UseISO8601DateFormat, SerializerFeature.UseSingleQuotes }; 
		serializer.config(SerializerFeature.WriteDateUseDateFormat,true);//SerializerFeature.WriteDateUseDateFormat
		serializer.config(SerializerFeature.UseSingleQuotes,true);//SerializerFeature.
		serializer.config(SerializerFeature.SkipTransientField,true);
		serializer.config(SerializerFeature.WriteEnumUsingToString,true);
		serializer.config(SerializerFeature.WriteMapNullValue,JsonConfigHolder.getWriteMapNullValue());
		//serializer.config(SerializerFeature.SortField,true);
		 //关闭循环引用的配置
		serializer.config(SerializerFeature.DisableCircularReferenceDetect, JsonConfigHolder.getDisableCircularReferenceDetect());
		
		if(JsonConfigHolder.getEnableHibernateLazyInitializerFilter()){
			HibernateLazyInitializerFilter hibernateFilter=new HibernateLazyInitializerFilter();
			serializer.getValueFilters().add(hibernateFilter);
		}

		doFilterPropertys(object, serializer);
		return serializer;
	}

	public static String  getJsonString(Object object){
		JSONSerializer serializer=getJSONSerializer(object);

		if(!JsonConfigHolder.getAutoWrap()){
				if(object instanceof Map){
					doExtProperties((Map)object);
				}		
				serializer.write(object);
				String jsonString=serializer.toString();
				if(!(object instanceof Map)){
					jsonString=doExtProperties(jsonString);
				}
	
				jsonString=replaceJsonPath(jsonString);
				
				//FileCopyUtils.copy(jsonString, new OutputStreamWriter(outputMessage.getBody(), charset));
				return jsonString;
		} else {
			

		ModelMap map=new ModelMap();
		if(object instanceof QueryResult){
			QueryResult page=(QueryResult)object;
			//ModelMap map=new ModelMap();
			map.put(JsonConfigHolder.getRootName(), page.getResult());
			map.put(JsonConfigHolder.getTotalName(), page.getTotalItems());
			map.put(JsonConfigHolder.getStartName(), page.getStart());
			map.put(JsonConfigHolder.getLimitName(), page.getPageSize());
			map.put(JsonConfigHolder.getPageNoName(), page.getPageNo());
			//map.put(JsonConfigHolder.getSuccessName(), true);
			//map.put("wheres", page.getWheres());
			//map.put("sorts", page.getSorts());
			//object=map;

		} else {
			
			map.put(JsonConfigHolder.getRootName(), object);
			
			if(object instanceof Collection){
				map.put(JsonConfigHolder.getTotalName(), JsonConfigHolder.getTotal()!=null?JsonConfigHolder.getTotal():((Collection)object).size());
			} else {
				Class c=object.getClass();
				if(c.isArray()){
					map.put(JsonConfigHolder.getTotalName(), JsonConfigHolder.getTotal()!=null?JsonConfigHolder.getTotal():((Object[])object).length);
				} else {
					map.put(JsonConfigHolder.getTotalName(),JsonConfigHolder.getTotal()!=null?JsonConfigHolder.getTotal():1);
				}

			}
			//object=map;
		}
		if(JsonConfigHolder.getMsg()!=null){
			map.put(JsonConfigHolder.getMsgName(), JsonConfigHolder.getMsg());
		}
		
		map.put(JsonConfigHolder.getSuccessName(), JsonConfigHolder.getSuccessValue());
		
		serializer.write(map);
		String jsonString=serializer.toString();
		jsonString=replaceJsonPath(jsonString);
		jsonString=doExtProperties(jsonString);
		return jsonString;
		}
	}
	
	/**
	 * 处理额外的属性
	 */
	private static void doExtProperties(Map obj){
		if(JsonConfigHolder.hasExtProperty()){
			//if(obj instanceof Map){
			obj.putAll(JsonConfigHolder.getExtProperties());
			//}
		}
	}
	/**
	 * 处理额外的属性
	 * @throws Exception 
	 */
	private static String doExtProperties(String jsonString) throws RuntimeException{
		
		char lastChar=jsonString.charAt(jsonString.length()-1);
		if(JsonConfigHolder.hasExtProperty()){
			if(lastChar=='}'){
				//serializer.c
				Map object=JsonConfigHolder.getExtProperties();
				JSONSerializer serializer=getJSONSerializer(object);
				serializer.write(object);
				String jsonStr=serializer.toString();
				jsonStr=jsonStr.substring(1,jsonStr.length()-1);
				return jsonString.substring(0, jsonString.length()-1)+","+jsonStr+"}";
			} else {
				throw new RuntimeException("只有转化为json对象的才能添加额外属性，数组类的对象不能添加额外属性!");
			}
		} else {
			return jsonString;
		}
	}

	
	
	private static void doFilterPropertys(Object root ,JSONSerializer serializer) {
		//if(JsonConfigHolder.getFilterPropertys()==null || "".equals(JsonConfigHolder.getFilterPropertys())){
		if(JsonConfigHolder.getFilterPropertys()==null || JsonConfigHolder.getFilterPropertys().length==0){
			return;
		}
		
		if((root instanceof HibernateProxy) ){
			return;
		}
		//
		if(root  instanceof PersistentCollection){
			PersistentCollection aa=(PersistentCollection)root;
        	if(!aa.wasInitialized()){
        		return;
        	}
		}
		
		String[] excludes=JsonConfigHolder.getFilterPropertys();//.split(",");
		//为定义了的类进行属性过滤
		if(JsonConfigHolder.getFilterClass()!=null&& JsonConfigHolder.getFilterClass().length>0){
			for(Class clazz:JsonConfigHolder.getFilterClass()){
				SimplePropertyPreFilter filter = new SimplePropertyPreFilter(clazz); 
				for(String str:excludes){
					filter.getExcludes().add(str);
				}
				if(filter!=null){
					serializer.getPropertyPreFilters().add((PropertyPreFilter) filter);
				}
			}
		} else {
			SimplePropertyPreFilter filter =null;
			//自己进行判断
			if(root instanceof Collection ){
				
				if(((Collection)root).size()>0){
					Iterator iterator=((Collection)root).iterator();
					Object obj=iterator.next();
					filter = new SimplePropertyPreFilter(obj.getClass() ); 
					for(String str:excludes){
						filter.getExcludes().add(str);
					}
				}
				
			} else {
				filter = new SimplePropertyPreFilter(root.getClass() ); 
				for(String str:excludes){
					filter.getExcludes().add(str);
				}
			}
			if(filter!=null){
				serializer.getPropertyPreFilters().add((PropertyPreFilter) filter);
			}
		}
		
	}
	//Pattern pattern = Pattern.compile("\\{\"\\$ref\":.*\"\\}");  
	//这里的？很重要，否则由于贪婪匹配的原因会造成问题
	//http://www.imkevinyang.com/2010/07/javajs%E5%A6%82%E4%BD%95%E4%BD%BF%E7%94%A8%E6%AD%A3%E5%88%99%E8%A1%A8%E8%BE%BE%E5%BC%8F%E5%8C%B9%E9%85%8D%E5%B5%8C%E5%A5%97html%E6%A0%87%E7%AD%BE.html
	static String patStr="\\{\"\\$ref\":.*?\"\\}";
	static Pattern pattern = Pattern.compile(patStr);  
	public static String replaceJsonPath(final String jsonString){
		if(JsonConfigHolder.getDisableCircularReferenceDetect()){
			return jsonString;
		}
        Matcher matcher = pattern.matcher(jsonString);
        String result=jsonString;
        while (matcher.find())
        {
        	//当后台返回的时候有问题，测试 业务类型的时候有问题
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
	
	private static class HibernateLazyInitializerFilter implements ValueFilter {
		public Object process(Object source, String name, final Object value) {
	        if ( value!=null && value instanceof HibernateProxy) {
	        	if(value instanceof IdEntity){
	        		//现在只是把
		        	return new IdEntity<Object>(){
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
	        	PersistentCollection aa=(PersistentCollection)value;
	        	if(aa.wasInitialized()){
	        		return aa;
	        	}
	        	return null;
	        }
	        return value;
	    }
	}

}
