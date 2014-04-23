package com.mawujun.repository1;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.binding.MapperProxy;
import org.apache.ibatis.session.SqlSession;
import org.hibernate.SessionFactory;

import com.mawujun.controller.spring.SpringContextHolder;
import com.mawujun.repository.hibernate.HibernateDao;
import com.mawujun.repository.hibernate.IHibernateDao;
import com.mawujun.repository.mybatis.MybatisRepository;
import com.mawujun.utils.AssertUtils;
import com.mawujun.utils.page.Page;

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

	
	private HibernateDao<Object,Serializable> hibernateDao=null;
	
	private MybatisRepository mybatisRepository;
	private String namespace;
	
	//Set<String> hibernateMethods=new HashSet<String>();

	public void setSessionFactory(SessionFactory sessionFactory) {
		//this.sessionFactory = sessionFactory;
		hibernateDao.setSessionFactory(sessionFactory);
	}
	public MyMapperProxy(SqlSession sqlSession, Class<T> mapperInterface,
			Map<Method, MapperMethod> methodCache) {
		super(sqlSession, mapperInterface, methodCache);

		mapperInterface.getGenericSuperclass();
		Type[] types=((ParameterizedType)mapperInterface.getGenericInterfaces()[0]).getActualTypeArguments();

		hibernateDao=new HibernateDao<Object,Serializable>((Class)types[0]);
		
		
		mybatisRepository=new MybatisRepository();
		mybatisRepository.setSqlSession(sqlSession);
		namespace=mapperInterface.getName();

	}

	/**
	 * 在这里进行拦截，如果是指定的方法就
	 * 额外的处理可以做成处理类，例如建一个InvokeHandler处理类，根据各种规则把处理内容发送到这个类里面进行处理，
	 * 这样的话MyMapperProxy就不需要依赖SessionFactory，MybatisRepository这些类了，直接在外面配置就可以了
	 */
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		//继承自IHibernateRepository的方法就在hibernate中执行，如果在子接口中重载了的话，就不会进入这里了
		if(method.getDeclaringClass()==IHibernateDao.class) {
			return invokeByHibernate(proxy, method, args);
		} else {
			Class[] paramTypes=method.getParameterTypes();
			//如果是分页的话
			if(paramTypes.length==1 && paramTypes[0]==Page.class && method.getReturnType()==Page.class){
				return mybatisRepository.selectPage(namespace+"."+method.getName(), (Page)args[0]);
			} else {
				 return super.invoke(proxy, method, args);
			}
			
		}
	   
	}
	
	
	private static final Map<String, Method> hibernateMethodCache=new ConcurrentHashMap<String, Method>();
	public Object invokeByHibernate(Object proxy, Method method, Object[] args) throws Throwable {
		//method.i
		//Method[] methods=HibernateInvoke.class.getMethods();
		
		
		Method hMethod=getMethod(method.getName(),args);
		return hMethod.invoke(hibernateDao, args);
		
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
		Class searchType = HibernateDao.class;
		//Class searchType = HibernateInvoke.class;
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
		
		
		List<Method> temps=new ArrayList<Method>();
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
							//temp=method;
							temps.add(method);
						} else if(isSame==2){
							hibernateMethodCache.put(key,method);
							return method;
						}
					}
				}
			}
			searchType = searchType.getSuperclass();
		}
		
		//当有多个方法都匹配的时候，获取继承结构中 最接近参数的那层的方法
		if(temps.size()==1){
			temp=temps.get(0);
		} else {
			Method standerMethod =temps.get(0);	
			for(int i=1;i<temps.size();i++){
				Class[] standerMethodParamTypes=standerMethod.getParameterTypes();
				Class[] targetMethodParamTypes=temps.get(i).getParameterTypes();
				
				//判断参数是否一致
				for(int j=0;j<standerMethodParamTypes.length;j++){	
					if(standerMethodParamTypes[j]==targetMethodParamTypes[j]){	
						continue;
					} else if(standerMethodParamTypes[j].isAssignableFrom(targetMethodParamTypes[j])){
						standerMethod=temps.get(i);
						break;
					} else if (standerMethodParamTypes[j].isArray() && targetMethodParamTypes[j].isArray()) {
						if (standerMethodParamTypes[j].getComponentType() ==targetMethodParamTypes[j].getComponentType()) {
							continue;
						} else if (standerMethodParamTypes[j].getComponentType().isAssignableFrom(targetMethodParamTypes[j].getComponentType())) {
							standerMethod=temps.get(i);
							break;
						} 
					}
				}
			}
			temp=standerMethod;
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
				} else {
					isSame=0;
					return isSame;
				}
			} else {
				return 0;
			}
		}
		return isSame;
	}

}
