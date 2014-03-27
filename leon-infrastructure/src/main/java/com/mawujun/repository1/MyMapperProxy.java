package com.mawujun.repository1;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.binding.MapperProxy;
import org.apache.ibatis.session.SqlSession;
import org.hibernate.SessionFactory;

import com.mawujun.controller.spring.SpringContextHolder;
import com.mawujun.repository.EntityTest;
import com.mawujun.utils.AssertUtils;
import com.mawujun.utils.ReflectionUtils;

/**
 * z这个类也可以自己扩展，例如要把某些逻辑写在持久层里的时候，可以为某些类专门指定使用哪个Proxy
 * @author mawujun 16064988@qq.com  
 *
 * @param <T>
 */
public class MyMapperProxy<T> extends MapperProxy<T> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//protected SessionFactory sessionFactory;
	
	HibernateInvoke hibernateProxy=null;
	
	
	//Set<String> hibernateMethods=new HashSet<String>();

	
	public MyMapperProxy(SqlSession sqlSession, Class<T> mapperInterface,
			Map<Method, MapperMethod> methodCache) {
		super(sqlSession, mapperInterface, methodCache);

		mapperInterface.getGenericSuperclass();
		Type[] types=((ParameterizedType)mapperInterface.getGenericInterfaces()[0]).getActualTypeArguments();
		SessionFactory sessionFactory=SpringContextHolder.getBean(SessionFactory.class);
		//hibernateProxy=new HibernateInvoke(sessionFactory);
		//hibernateProxy.setEntityClass((Class)types[0]);
		hibernateProxy=new HibernateInvoke((Class)types[0],sessionFactory);

	}



	/**
	 * 在这里进行拦截，如果是指定的方法就
	 */
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		//继承自IHibernateRepository的方法就在hibernate中执行，如果在子接口中重载了的话，就不会进入这里了
		if(method.getDeclaringClass()==IHibernateRepository.class) {
			return invokeByHibernate(proxy, method, args);
		} else {
			 return super.invoke(proxy, method, args);
		}
	   
	}
	private static final Map<String, Method> hibernateMethodCache=new ConcurrentHashMap<String, Method>();
	public Object invokeByHibernate(Object proxy, Method method, Object[] args) throws Throwable {
		//method.i
		//Method[] methods=HibernateInvoke.class.getMethods();
		
		
		Method hMethod=getMethod(method.getName(),args);
		return hMethod.invoke(hibernateProxy, args);
		 
//		String methodName=method.getName();
//		if(methodName.equals("create")){
//			return hibernateProxy.create(args[0]);
//		} else if(methodName.equals("update")){
//			hibernateProxy.update(args[0]);
//		} else if(methodName.equals("get")){
//			return hibernateProxy.get((Serializable)args[0]);
//		} else if(methodName.equals("delete")){
//			hibernateProxy.delete(args[0]);
//		} else if(methodName.equals("deleteById")){
//			hibernateProxy.deleteById((Serializable)args[0]);
//		} else if(methodName.equals("updateDynamic")){
//			//hibernateProxy.deleteById((Serializable)args[0]);
//			dsf
//			
//		} else {
//			throw new RuntimeException("没有定义对应的处理方法");
//		}
//		
//		return null;
	}
	
	private String getKey(String name, Class[] paramTypes){
		StringBuilder key=new StringBuilder(name);
		for(Class paramstype:paramTypes){
			key.append(paramstype.getName());
		}
		return key.toString();
	}
	/**
	 * 如果没有和参数什么完全匹配的方法，就返回最后一个名称和参数个数匹配的方法
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @param name
	 * @param paramTypes
	 * @return
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 */
	public  Method getMethod(String name, Object[] args) throws NoSuchMethodException, SecurityException {
		//AssertUtils.notNull(clazz, "Class must not be null");
		AssertUtils.notNull(name, "Method name must not be null");
		Class searchType = HibernateInvoke.class;
		//没有参数的方法
		if(args==null){
			Method temp=hibernateMethodCache.get(name);
			if(temp==null){
				temp=searchType.getMethod(name);
			}
			hibernateMethodCache.put(name,temp);
			return temp;
		}
		
		Class[] paramTypes=new Class[args.length];
		for(int i=0;i<args.length;i++){
			Object o=args[i];
			paramTypes[i]=o.getClass();
		}
		
		
		String key=getKey(name,paramTypes);
		Method temp=hibernateMethodCache.get(key);
		if(temp!=null){
			return temp;
		}
		
		
		
		while (!Object.class.equals(searchType) && searchType != null) {
			Method[] methods = (searchType.isInterface() ? searchType.getMethods() : searchType.getDeclaredMethods());
			for (int i = 0; i < methods.length; i++) {
				Method method = methods[i];
				if (name.equals(method.getName()) ) {
					if((paramTypes == null || paramTypes.length==0) && method.getParameterTypes().length==0){
						return method;
					} else {
						int isSame= isSameParames(paramTypes,method.getParameterTypes());
						if(isSame==1){
							temp=method;
						} else if(isSame==2){
							hibernateMethodCache.put(key,method);
							return method;
						}
					}
				}
			}
			searchType = searchType.getSuperclass();
		}
		
		hibernateMethodCache.put(key,temp);
		return temp;
	}
	
	/***
	 * 0表示不匹配
	 * 1：表示是Object的匹配，那要把这个方法先缓存
	 * 2：表示是完全匹配，直接可以返回该方法
	 * 
	 * 考虑多个参数
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @param paramTypes
	 * @param methodParamTypes
	 * @return
	 */
	public int isSameParames(Class[] paramTypes,Class[] methodParamTypes){
		if(paramTypes.length!=methodParamTypes.length){
			return 0;
		}
		int isSame=2;
		for(int i=0;i<paramTypes.length;i++){
			//methodParamTypes[i]==Serializable.class用于判断id的时候
			//if(methodParamTypes[i]==Object.class || methodParamTypes[i]==Serializable.class){
			if(methodParamTypes[i]==paramTypes[i]){	
				continue;
			} else if(methodParamTypes[i].isAssignableFrom(paramTypes[i])){
				isSame=1;
			} else if (methodParamTypes[i].isArray() && paramTypes[i].isArray()) {
				if (methodParamTypes[i].getComponentType() == paramTypes[i].getComponentType()) {
					continue;
				//} else if (paramTypes[i].getComponentType() == Object.class|| methodParamTypes[i].getComponentType() == Serializable.class) {
				} else if (methodParamTypes[i].getComponentType().isAssignableFrom(paramTypes[i].getComponentType())) {
					isSame = 1;
				}
			} else {
				return 0;
			}
		}
		return isSame;
	}

}
