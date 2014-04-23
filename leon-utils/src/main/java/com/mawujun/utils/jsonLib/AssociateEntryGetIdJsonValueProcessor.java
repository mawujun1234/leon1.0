package com.mawujun.utils.jsonLib;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

import com.mawujun.utils.ReflectUtils;

public class AssociateEntryGetIdJsonValueProcessor implements JsonValueProcessor {
	
	private boolean onlyBaseType=false;
	
	private boolean associateOnlyId=true;
	
	public AssociateEntryGetIdJsonValueProcessor(){
		
	}

	/**
	 * onlyBaseType=true,表示被关联的实体和集合都只返回基础属性
	 * @param onlyBaseType
	 */
	public AssociateEntryGetIdJsonValueProcessor(boolean associateOnlyId,boolean onlyBaseType){
		this.associateOnlyId=associateOnlyId;
		this.onlyBaseType=onlyBaseType;
	}
	public Object processArrayValue(Object value, JsonConfig arg1) {
		// TODO Auto-generated method stub
		//return process(value);这里也这么调用的话，在集合里面也会返回只有id的数组，将不会出现死循环
		//return value;
		if(onlyBaseType){
			return JSONObjectUtils.toJsonIgnoreAllAssociate(value);
		} else {
			return value;
		}
		
		
	}

	public Object processObjectValue(String key, Object value, JsonConfig arg2) {
		// TODO Auto-generated method stub
		return process(value,  arg2);
	}
	

	private Object process(Object value, JsonConfig arg2) {
		if (value == null) {
			return null;
		}
		if(associateOnlyId){
			if(ReflectUtils.getMethod(value.getClass(), "getId")!=null){
				JSONObject jsonObject =jsonObject= new JSONObject();
				jsonObject.put("id", ReflectUtils.invokeGetterMethod(value, "id"));
				return jsonObject;
			}else if(ReflectUtils.getMethod(value.getClass(), "getID")!=null	){
				JSONObject jsonObject =jsonObject= new JSONObject();
				jsonObject.put("id", ReflectUtils.invokeGetterMethod(value, "ID"));
				return jsonObject;
			}
		}
		if(onlyBaseType){
			return JSONObjectUtils.toJsonIgnoreAllAssociate(value);
		} else {
			return value;
		}
	}
	
//	private Object process(Object value) {
//		if (value == null) {
//			return null;
//		}
//		//如果是基本类型就返回value
//		if(ReflectionUtils.isBaseType(value)){
//			return value;
//		}
//
//		Method methods[] = value.getClass().getDeclaredMethods();
//		JSONObject jsonObject =null;
//		try {
//			for (Method method : methods) {
//				if (method.getName().equalsIgnoreCase("getId")) {
//					//result = method.invoke(value);
//					jsonObject= new JSONObject(  );
//					jsonObject.put("id", method.invoke(value));
//					break;
//				}
//			}
//		} catch (IllegalArgumentException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return value;
//		} catch (IllegalAccessException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return value;
//		} catch (InvocationTargetException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return value;
//		}
//		//
//		return jsonObject;
//	}

}
