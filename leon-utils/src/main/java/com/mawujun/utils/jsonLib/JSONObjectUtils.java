package com.mawujun.utils.jsonLib;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;
import net.sf.json.util.JSONUtils;
import net.sf.json.util.PropertySetStrategy;

import com.mawujun.utils.ReflectUtils;
import com.mawujun.utils.StringUtils;
import com.mawujun.utils.page.QueryResult;

/**
 * 用于返回extjs 需求格式的JSONObject的对象
 * @author mwj
 *
 */
public class JSONObjectUtils {
	private final static String datePattern = "yyyy-MM-dd";  
	
	private static Pattern pattern = Pattern.compile(datePattern);
	private static SimpleDateFormat sdf = new SimpleDateFormat(datePattern);


	/**
	 * 返回的数据格式主要用于grid
	 * @param root
	 * @param totalProperty
	 * @return
	 */
	public static JSONObject grid(JSONArray root,int totalProperty) {
		JSONObject result=new JSONObject();//grid要用到的时候
		result.put("root", root);
		result.put("totalProperty", totalProperty);
		return result;
	}
	
	public static JSONObject grid(QueryResult page) {
		JSONArray root=new JSONArray();
		for(Object map:page.getResult()){
			JSONObject json=JSONObject.fromObject(map);
			root.add(json);
		}
		
		JSONObject result=new JSONObject();//grid要用到的时候
		result.put("root", root);
		result.put("totalProperty", page.getTotalItems());
		return result;
	}
	
	/**
	 * 返回的数组
	 * @param root
	 * @param totalProperty
	 * @return
	 */
	public static JSONArray array(JSONArray root) {
		return root;
	}
	
	/**
	 * 返回ajax请求的数据格式
	 * @param data
	 * @param msg
	 * @return
	 */
	public static JSONObject ajaxSuccessId(Object id,String msg) {
		JSONObject result=new JSONObject();

		result.put("success", true);
		result.put("data", "{id:'"+id+"'}");
		result.put("msg", StringUtils.hasText(msg)?msg:"成功");
		return result;
	}
	
	/**
	 * 返回ajax请求的数据格式
	 * @param data
	 * @param msg
	 * @return
	 */
	public static JSONObject ajaxSuccess(JSONObject data,String msg) {
		JSONObject result=new JSONObject();

		result.put("success", true);
		result.put("data", data);
		result.put("msg", StringUtils.hasText(msg)?msg:"成功");
		return result;
	}
	
	/**
	 * 返回ajax请求的数据格式
	 * @param data 格式必须是json的格式 {id:'111'}这个样子
	 * @param msg
	 * @return
	 */
	public static JSONObject ajaxSuccess(String data,String msg) {
		JSONObject result=new JSONObject();

		result.put("success", true);
		result.put("data", data);
		result.put("msg", StringUtils.hasText(msg)?msg:"成功");
		return result;
	}
	
	public static JSONObject ajaxError(String data,String msg) {
		JSONObject result=new JSONObject();

		result.put("success", false);
		result.put("errors", "{}");
		result.put("data", data);
		result.put("msg", StringUtils.hasText(msg)?msg:"成功");
		return result;
	}
	public static JSONObject ajaxError(JSONObject data,String msg) {
		JSONObject result=new JSONObject();

		result.put("success", true);
		result.put("data", data);
		result.put("msg", StringUtils.hasText(msg)?msg:"失败");
		return result;
	}
	
	/**
	 * bean 忽略掉了集合
	 * @param bean
	 * @param msg
	 * @param excludes
	 * @return
	 */
	public static JSONObject success(Object bean,String msg, String... excludes){
//		if(bean.getClass()==String.class){
//			
//		}
		JSONObject root=JSONObjectUtils.toJsonIgnoreCollection(bean, true,excludes);
		JSONObject result=new JSONObject();
		result.put("success", true);
		result.put("errors", "{}");
		result.put("message", StringUtils.hasText(msg)?msg:"成功");
		
		JSONArray jsonArray=new JSONArray();
		jsonArray.add(root);
		result.put("root", root);
		return result;
	}
	
	private static JsonConfig getJsonConfig(String[] excludes, String datePattern) {   
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(new String[] {"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss"}));
        JsonConfig jsonConfig = new JsonConfig();   
        jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
        if(excludes!=null && excludes.length>0){
        	jsonConfig.setExcludes(excludes);   
        }
        
        jsonConfig.setPropertySetStrategy(new IgnorePropertyStrategyWrapper(PropertySetStrategy.DEFAULT));
        
        jsonConfig.setIgnoreDefaultExcludes(false);   
        jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);   
        if(datePattern!=null && !"".equals(datePattern.trim())){
        	datePattern=JSONObjectUtils.datePattern;
        } 
        DateJsonValueProcessor djp=new DateJsonValueProcessor(datePattern);
    	jsonConfig.registerJsonValueProcessor(Date.class, djp);  
        jsonConfig.registerJsonValueProcessor(java.sql.Date.class,djp);
		jsonConfig.registerJsonValueProcessor(java.sql.Timestamp.class,djp);

        return jsonConfig;   
	}
	//缓存，某个实体类对应的关联类
	private static Map<Class,Set<Class>> typeMap=new HashMap<Class,Set<Class>>();
	private static JsonConfig getJsonConfigOnlyId(Object bean,JsonConfig jsonConfig,boolean associateOnlyId) {
		//自动获取这个类的关联的实体类，并且注册，和缓存这些对应关系
		Set<Class> clssSet=new HashSet<Class>();
		if(typeMap.containsKey(bean.getClass())){
			clssSet=typeMap.get(bean.getClass());
			
		} else {
			Field[] fields=ReflectUtils.getAllDeclaredFields(bean.getClass());
			try {
				
				for (Field field : fields) {
					//如果是基本类型，string,集合类型的话，跳过，否则就注册进去
					if(ReflectUtils.isBaseType(field.getType()) 
							|| ReflectUtils.containInterface(field.getType(), Collection.class)
							|| ReflectUtils.containInterface(field.getType(), Map.class)){
						continue;
					} else {
						//过滤掉自己定义的实体类
						//jsonConfig.registerJsonValueProcessor(field.getType(), new AssociateEntryGetIdJsonValueProcessor(associateOnlyId,true));
						clssSet.add(field.getType());
					}
				}
				typeMap.put(bean.getClass(), clssSet);
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 		
		}
		//过滤掉自己定义的实体类
		for(Class clz:clssSet){
			jsonConfig.registerJsonValueProcessor(clz, new AssociateEntryGetIdJsonValueProcessor(associateOnlyId,true));
		}
		return jsonConfig;
	}
	/**
	 * 都不会出现死循环了，对于关联集合和关联对象，只获取基本属性，不会获取集合内对象的关联对像和关联集合，避免了死循环
	 * associateOnlyId=true:如果是关联对象，并且含有 getId()/getID()的方法的话，true就只返回id，false：获取所有的属性,不包括关联对象和集合
	 * 不过滤关联的实体对象和集合，要排除属性的话 使用excludes参数
	 * @param bean 
	 * @param datePattern 指定日期格式。默认是yyy-MM-dd
	 * @param excludes 忽略的属性
	 * @param associateOnlyId 如果是关联对象，并且含有 getId()的方法的话，true就只返回id，false的话就不做判断，按默认操作
	 * @return
	 */
	public static JSONObject toJson(Object bean, String datePattern,boolean associateOnlyId,String... excludes){
		JsonConfig jsonConfig =getJsonConfig(excludes,datePattern);
		jsonConfig=getJsonConfigOnlyId(bean,jsonConfig,associateOnlyId);
		
		JSONObject jsonObject = JSONObject.fromObject(bean, jsonConfig);  
        return jsonObject;
	}
	public static JSONObject toJson(Object bean){
		JsonConfig jsonConfig =getJsonConfig(null,null);
		return JSONObject.fromObject(bean,jsonConfig);
	}
	/**
	 * 都不会出现死循环了，对于关联集合和关联对象，只获取基本属性，不会获取集合内对象的关联对像和关联集合，避免了死循环
	 * associateOnlyId=true:如果是关联对象，并且含有 getId()/getID()的方法的话，true就只返回id，false：获取所有的属性,不包括关联对象和集合
	 * 不过滤关联的实体对象和集合，要排除属性的话 使用excludes参数
	 * @param bean
	 * @param  associateOnlyId 如果是关联对象，并且含有 getId()的方法的话，true就只返回id，false的话就不做判断，按默认操作
	 * @return
	 */
	public static JSONObject toJson(Object bean,boolean associateOnlyId,String... excludes){  
		JsonConfig jsonConfig =getJsonConfig(excludes,null);
		jsonConfig=getJsonConfigOnlyId(bean,jsonConfig,associateOnlyId);
		JSONObject jsonObject = JSONObject.fromObject(bean, jsonConfig);  
        return jsonObject;
	}
	
	/**
	 * 不会出现死循环，对于关联集合和关联对象，只获取基本属性，不会获取集合内对象的关联对像和关联集合，避免了死循环
	 * 忽略所有的关联的集合数据，但不过滤一的实体对象
	 * associateOnlyId=true:如果是关联对象，并且含有 getId()/getID()的方法的话，true就只返回id，false：获取所有的属性,不包括关联对象和集合
	 * @param bean
	 * @param  associateOnlyId 如果是关联对象，并且含有 getId()的方法的话，true就只返回id，false的话就不做判断，按默认操作
	 * @param excludes 忽略的属性
	 * @return
	 */
	public static JSONObject toJsonIgnoreCollection(Object bean,boolean associateOnlyId,String... excludes){
		JsonConfig jsonConfig =getJsonConfig(excludes,null);
		jsonConfig=getJsonConfigOnlyId(bean,jsonConfig,associateOnlyId);
		jsonConfig.setJsonPropertyFilter(new IgnoreCollectionPropertyFilter()); 
		JSONObject jsonObject = JSONObject.fromObject(bean, jsonConfig);  
        return jsonObject;
	}
	
	/**
	 * 不会出现死循环，对于关联集合和关联对象，只获取基本属性，不会获取集合内对象的关联对像和关联集合，避免了死循环
	 * 过滤所有的关联的集合数据和实体对象，只返回基本类型的属性
	 * @param bean
	 * @param excludes 忽略的属性
	 * @return
	 */
	public static JSONObject toJsonIgnoreAllAssociate(Object bean,String... excludes){
		JsonConfig jsonConfig =getJsonConfig(excludes,null);
		jsonConfig.setJsonPropertyFilter(new IgnoreCollectionPropertyFilter(true)); //过滤所有关联数据
		JSONObject jsonObject = JSONObject.fromObject(bean, jsonConfig);  
        return jsonObject;
	}
	
	
	public static Object toBean(String json,Class clazz){
		
		//http://www.cnblogs.com/mailingfeng/archive/2012/01/18/2325707.html
		JsonConfig jsonConfig = getJsonConfig(null,datePattern);
		if(json==null || "".equals(json.trim())){
			return null;
		}
		if(json.startsWith("{")) {
			JSONObject jsonObject=JSONObject.fromObject(json, jsonConfig);
			return JSONObject.toBean(jsonObject, clazz);
		} 
//		else if(json.startsWith("[")){
//			JSONArray jsonObj=jsonObj = JSONArray.fromObject(json, jsonConfig);
//			return jsonObj.to
//		} 
		else {
			throw new JSONException("json字符串有问题!" ); 
		}
		
	}
	
	
	public static JSONObject fromJSON(String json) {
		JsonConfig jsonConfig = getJsonConfig(null,datePattern);
		JSONObject jsonObj;
		try {
			jsonObj = JSONObject.fromObject(json, jsonConfig);
		} catch (Exception e) {
			return null;
		}
		return jsonObj;
	}
	  
	
	
	
//	private static ArrayList<Object> JSON2JavaObj(JSONArray jsonArr) {
//		ArrayList<Object> arrL = new ArrayList<Object>();
//		for (int i = 0, l = jsonArr.size(); i < l; i++) {
//			Object objTemp = jsonArr.get(i);
//			if (objTemp instanceof JSONNull) {
//				arrL.add(null);
//			}else if (objTemp instanceof JSONObject) {
//				arrL.add(JSON2JavaObj((JSONObject) objTemp));
//			} else if (objTemp instanceof JSONArray) {
//				arrL.add(JSON2JavaObj((JSONArray) objTemp));
//			} else {
//				// date
//				if (objTemp != null && objTemp instanceof String) {
//					String str = objTemp.toString();
//					Matcher m = pattern.matcher(str);
//					if (m.matches()) {
//						try {
//							objTemp = sdf.parse(str);
//						} catch (Exception e) {
//						}
//					}
//				}
//				arrL.add(objTemp);
//			}
//		}
//		return arrL;
//	}
//
//	/**
//	 * JSONObject to Java Object
//	 * @param jsonObject
//	 * @return HashMap
//	 */
//	@SuppressWarnings(value = { "unchecked" })
//	private static Map<String, Object> JSON2JavaObj(JSONObject jsonObject) {
//		Map<String, Object> tempObjectMap = new HashMap<String, Object>();
//		Iterator ite = jsonObject.entrySet().iterator();
//		while (ite.hasNext()) {
//			Map.Entry paraMap = (Map.Entry) ite.next();
//			String strKey = (String) paraMap.getKey();
//			Object objTemp = paraMap.getValue();
//			if (objTemp instanceof JSONNull) {
//				tempObjectMap.put(strKey, null);
//			} else if (objTemp instanceof JSONObject) {
//				tempObjectMap.put(strKey, JSON2JavaObj((JSONObject) objTemp));
//			} else if (objTemp instanceof JSONArray) {
//				tempObjectMap.put(strKey, JSON2JavaObj((JSONArray) objTemp));
//			} else {
//				// date
//				if (objTemp != null && objTemp instanceof String) {
//					String str = objTemp.toString();
//					Matcher m = pattern.matcher(str);
//					if (m.matches()) {
//						try {
//							objTemp = sdf.parse(str);
//						} catch (Exception e) {
//						}
//					}
//				}
//				tempObjectMap.put(strKey, objTemp);
//			}
//		}
//		return tempObjectMap;
//	}
//
//	/**
//	 * JSON String to Java Map
//	 * @param json
//	 * @return
//	 */
//	public static Map<String, Object> fromJSON(String json) {
////		JsonConfig jsonConfig = new JsonConfig();
////		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
////		DateJsonValueProcessor p = new DateJsonValueProcessor(datePattern);
////		jsonConfig.registerJsonValueProcessor(java.util.Date.class,p);
////		jsonConfig.registerJsonValueProcessor(java.sql.Date.class,p);
////		jsonConfig.registerJsonValueProcessor(java.sql.Timestamp.class,p);
//		JsonConfig jsonConfig = getJsonConfig(null,datePattern);
//		JSONObject jsonObj;
//		try {
//			jsonObj = JSONObject.fromObject(json, jsonConfig);
//		} catch (Exception e) {
//			return null;
//		}
//		return JSON2JavaObj(jsonObj);
//	}
//
//
//	/**
//	 * JSON String to Java ArrayList
//	 * 
//	 * @param json
//	 * @return
//	 */
//	public static ArrayList<Object> fromArrayJSON(String json) {
////		JsonConfig jsonConfig = new JsonConfig();
////		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
////		DateJsonValueProcessor p = new DateJsonValueProcessor(datePattern);
////		jsonConfig.registerJsonValueProcessor(java.util.Date.class,p);
////		jsonConfig.registerJsonValueProcessor(java.sql.Date.class,p);
////		jsonConfig.registerJsonValueProcessor(java.sql.Timestamp.class,p);
//		
//		JsonConfig jsonConfig = getJsonConfig(null,datePattern);
//		JSONArray jsonObj;
//		try {
//			jsonObj = JSONArray.fromObject(json, jsonConfig);
//		} catch (Exception e) {
//			return null;
//		}
//		return JSON2JavaObj(jsonObj);
//	}



}
