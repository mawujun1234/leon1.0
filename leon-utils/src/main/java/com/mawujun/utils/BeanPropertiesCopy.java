/**
 * Copyright (c) 2005-2011 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id: ObjectMapper.java 1627 2011-05-23 16:23:18Z calvinxiu $
 */
package com.mawujun.utils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.util.Assert;

import com.google.common.collect.Lists;

/**
 * 对象转换工具类.
 * 1.封装Dozer, 深度转换对象到对象
 * 2.封装Apache Commons BeanUtils, 将字符串转换为对象.
 * 
 * @author calvin
 */
public abstract class BeanPropertiesCopy {

	/**
	 * 持有Dozer单例, 避免重复创建DozerMapper消耗资源.
	 */
	private static DozerBeanMapper dozer = new DozerBeanMapper();

	/**
	 * 基于Dozer转换对象的类型.
	 * 主要用于对象之间进行拷贝
	 * Long[] aa=BeanPropertiesCopy.copy(value, long[].class)
	 */
	public static <T> T copy(Object source, Class<T> destinationClass) {
		return dozer.map(source, destinationClass);
	}

	/**
	 * 基于Dozer转换Collection中对象的类型.
	 */
	public static <T> List<T> copyList(Collection sourceList, Class<T> destinationClass) {
		List<T> destinationList = Lists.newArrayList();
		for (Object sourceObject : sourceList) {
			T destinationObject = dozer.map(sourceObject, destinationClass);
			destinationList.add(destinationObject);
		}
		return destinationList;
	}
	/**
	 * 基于Dozer将对象A的值拷贝到对象B中.
	 */
	public static void copy(Object source, Object destinationObject) {
		//dozer.
		dozer.map(source, destinationObject);
	}
	
	
	private static  HashMap<String,PropertyDescriptor[]> beanPropertyCache=new HashMap<String,PropertyDescriptor[]>();
	private static PropertyDescriptor[] getPropertyDescriptors(Class<?> clazz) throws BeansException, IntrospectionException {
		 PropertyDescriptor[] pds = beanPropertyCache.get(clazz.getName());
		if(pds!=null){
			//return pds;
		} else {
			BeanInfo beanInfo = Introspector.getBeanInfo(clazz);         
			pds = beanInfo.getPropertyDescriptors();
			beanPropertyCache.put(clazz.getName(), pds);
		}
		return pds;
	}
	private static PropertyDescriptor getPropertyDescriptor(Class<?> clazz, String propertyName)
			throws BeansException, IntrospectionException {         
		 PropertyDescriptor[] pds = getPropertyDescriptors(clazz);
		 for(PropertyDescriptor pd:pds){
			 if(pd.getName().equals(propertyName)){
				 return pd;
			 }
		 }
		 return null;
	}
	/**
	 * 最简单的属性拷贝，不考虑类型转换。
	 * 忽略为null的属性
	 * @param source
	 * @param target
	 * @throws BeansException
	 * @throws IntrospectionException
	 */
	public static void copyExcludeNull(Object source, Object target)
			throws BeansException, IntrospectionException {

		Assert.notNull(source, "Source must not be null");
		Assert.notNull(target, "Target must not be null");

		Class<?> actualEditable = target.getClass();

		PropertyDescriptor[] targetPds = getPropertyDescriptors(actualEditable);


		for (PropertyDescriptor targetPd : targetPds) {
			if (targetPd.getWriteMethod() != null) {
				PropertyDescriptor sourcePd = getPropertyDescriptor(source.getClass(), targetPd.getName());
				if (sourcePd != null && sourcePd.getReadMethod() != null) {
					try {
						Method readMethod = sourcePd.getReadMethod();
						if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
							readMethod.setAccessible(true);
						}
						Object value = readMethod.invoke(source);
						if(value==null){//字符串为空就不拷贝
							continue;
						}
						Method writeMethod = targetPd.getWriteMethod();
						if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
							writeMethod.setAccessible(true);
						}
						writeMethod.invoke(target, value);
					}
					catch (Throwable ex) {
						throw new FatalBeanException("Could not copy properties from source to target", ex);
					}
				}
			}
		}
	}


	static {
		//初始化日期格式为yyyy-MM-dd 或 yyyy-MM-dd HH:mm:ss
		registerDateConverter("yyyy-MM-dd,yyyy-MM-dd HH:mm:ss");
		
		//ConvertUtils.deregister(Integer.class);		
		//ConvertUtils.register(new IntegerConverter(null), Integer.class);

	}

	/**
	 * 定义Apache BeanUtils日期Converter的格式,可注册多个格式,以','分隔
	 */
	public static void registerDateConverter(String patterns) {
		DateConverter dc = new DateConverter();
		dc.setUseLocaleFormat(true);
		dc.setPatterns(StringUtils.split(patterns, ","));
		ConvertUtils.register(dc, Date.class);
	}

	/**
	 * 基于Apache BeanUtils转换字符串到相应类型.
	 * 可以将字符串转换成Integer等类型，转换为数字时，如果转换失败，会返回0，例如"10.123"转换为Intger的时候就会返回0
	 * @param value 待转换的字符串.
	 * @param toType 转换目标类型.
	 */
	public static Object convert(String value, Class<?> toType) {
		//如果类型相同，就不转换了
		if(toType==String.class){
			return value;
		}
		try {
			return ConvertUtils.convert(value, toType);
		} catch (Exception e) {
			throw ReflectionUtils.convertReflectionExceptionToUnchecked(e);
		}
	}
	
	public static Object convert(Object value, Class<?> toType) {
		//如果类型相同，就不转换了
		if(value.getClass()==toType){
			return value;
		}
		
		
		try {
			return ConvertUtils.convert(value, toType);
		} catch (Exception e) {
			throw ReflectionUtils.convertReflectionExceptionToUnchecked(e);
		}
	}
	/**
	 * 把字符串数组转换成其他类型的数组
	 * @param values
	 * @param toType
	 * @return
	 */
	public static Object convert(String[] values, Class<?> toType) {
		return ConvertUtils.convert(values, toType);
	}

	
//	public static boolean isWrapClass(Class clz) {
//		try {
//			return ((Class) clz.getField("TYPE").get(null)).isPrimitive();
//		} catch (Exception e) {
//			return false;
//		}
//	}

}