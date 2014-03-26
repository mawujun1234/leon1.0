package com.mawujun.repository1;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.binding.MapperProxy;
import org.apache.ibatis.session.SqlSession;

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
	Set<String> hibernateMethods=new HashSet<String>();

	public MyMapperProxy(SqlSession sqlSession, Class<T> mapperInterface,
			Map<Method, MapperMethod> methodCache) {
		super(sqlSession, mapperInterface, methodCache);
		// TODO Auto-generated constructor stub
		//aa="11";
		hibernateMethods.add("getByUsername");
	}



	/**
	 * 在这里进行拦截，如果是指定的方法就
	 */
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		if(hibernateMethods.contains(method.getName())) {
			if(method.getReturnType()==Void.class){
					return null;
			} else {
				在这里执行hibernate的相关操作
				//return new User();
				return null;
			}
		} else {
			 return super.invoke(proxy, method, args);
		}
	   
	}

}
