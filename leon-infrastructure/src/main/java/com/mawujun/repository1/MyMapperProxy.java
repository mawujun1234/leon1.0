package com.mawujun.repository1;

import java.io.Serializable;
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
		hibernateProxy=new HibernateInvoke(sessionFactory);
		hibernateProxy.setEntityClass((Class)types[0]);
		//System.out.println(11);
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
		Class[] paramTypes=new Class[args.length];
		for(int i=0;i<args.length;i++){
			Object o=args[i];
			paramTypes[i]=o.getClass();
		}
		
		Method hMethod=getMethod(method.getName(),paramTypes);
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
	 */
	public  Method getMethod(String name, Class[] paramTypes) {
		//AssertUtils.notNull(clazz, "Class must not be null");
		AssertUtils.notNull(name, "Method name must not be null");
		
		Method temp=hibernateMethodCache.get(getKey(name,paramTypes));
		if(temp!=null){
			return temp;
		}
		
		Class searchType = HibernateInvoke.class;
		
		while (!Object.class.equals(searchType) && searchType != null) {
			Method[] methods = (searchType.isInterface() ? searchType.getMethods() : searchType.getDeclaredMethods());
			for (int i = 0; i < methods.length; i++) {
				Method method = methods[i];
				if (name.equals(method.getName()) ) {
					if(paramTypes == null || Arrays.equals(paramTypes, method.getParameterTypes())){
						return method;
					} else if(method.getParameterTypes().length==paramTypes.length){
						
						temp=method;//如果没有参数一致的方法的时候，就返回最后一个名词匹配的方法
					}
					
				}
			}
			searchType = searchType.getSuperclass();
		}
		
		hibernateMethodCache.put(getKey(name,paramTypes),temp);
		return temp;
	}

}
