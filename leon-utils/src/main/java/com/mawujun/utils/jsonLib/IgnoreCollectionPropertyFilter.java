package com.mawujun.utils.jsonLib;

import java.lang.reflect.Field;

import net.sf.json.util.PropertyFilter;

import com.mawujun.utils.ReflectionUtils;

public class IgnoreCollectionPropertyFilter implements PropertyFilter {
	
	private boolean ignoreCollection=true;
	private boolean ignoreAllAssociate=false;
	public IgnoreCollectionPropertyFilter(){
		
	}
	
	public IgnoreCollectionPropertyFilter(boolean ignoreAllAssociate){
		//this.ignoreCollection=ignoreCollection;
		this.ignoreAllAssociate=ignoreAllAssociate;
	}


	public boolean apply(Object source, String name, Object value) {
		Field declaredField = null;
		// 如果return true,忽略值为null的属性
		if (value == null)
			return false;
		if(ReflectionUtils.isBaseType(value)){
			return false;
		}
		if(ignoreAllAssociate){
			return true;
		}
		if(ignoreCollection){
			if(ReflectionUtils.isCollectionMap(value)){
				return true;
			}
//			try {
//				declaredField=source.getClass().getDeclaredField(name);
//			} catch (SecurityException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//				return true;
//			} catch (NoSuchFieldException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//				return true;
//			}
//			// 忽略集合
//			if (declaredField != null) {
//				Class interfaces[]=declaredField.getType().getInterfaces();//.getClass().getInterfaces();
//				for(Class inter:interfaces){
//					if(inter==Collection.class || inter==Map.class){
//						return true;
//					}
//				}
////				if (get instanceof Collection || declaredField.getType() == Map.class) {
////					return true;
////				}
//			}

		}

		return false; 

	}

	public boolean isIgnoreCollection() {
		return ignoreCollection;
	}

	public void setIgnoreCollection(boolean ignoreCollection) {
		this.ignoreCollection = ignoreCollection;
	}

	public boolean isIgnoreAllAssociate() {
		return ignoreAllAssociate;
	}

	public void setIgnoreAllAssociate(boolean ignoreAllAssociate) {
		this.ignoreAllAssociate = ignoreAllAssociate;
	}

}
