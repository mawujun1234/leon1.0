package com.mawujun.repository;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;

import com.mawujun.repository1.HibernateInvoke;

public class MyClassTest {

	public static void main(String[] args) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		// TODO Auto-generated method stub
//		MyClass<String> myClass=new MyClass<String>(String.class);
//		Class searchType = myClass.getClass();//MyClass.class;
//		//MyClass<String> myClass1=(MyClass<String>)searchType.getConstructor(Class.class).newInstance(String.class);
//		
//		Method[] methods = (searchType.isInterface() ? searchType.getMethods() : searchType.getDeclaredMethods());
//		for (int i = 0; i < methods.length; i++) {
//			Method method = methods[i];
//			Type[] aa=method.getGenericParameterTypes();
//			System.out.println(aa[0].getClass().getGenericSuperclass().);
//			//.getParameterTypes();
//			Type[] pType = method.getParameterTypes();
//			System.out.println(pType[0].getClass().getGenericSuperclass());
//
//		}
		
		Method method=MyClass.class.getMethod("get");
		
		method.getParameterTypes();
	}

}
