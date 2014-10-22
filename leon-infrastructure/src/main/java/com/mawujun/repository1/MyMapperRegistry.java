package com.mawujun.repository1;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.apache.ibatis.binding.BindingException;
import org.apache.ibatis.binding.MapperProxyFactory;
import org.apache.ibatis.binding.MapperRegistry;
import org.apache.ibatis.builder.annotation.MapperAnnotationBuilder;
import org.apache.ibatis.session.Configuration;
import org.hibernate.SessionFactory;

public class MyMapperRegistry extends MapperRegistry {
	private final Map<Class<?>, MapperProxyFactory<?>> knownMappersNew;// = new HashMap<Class<?>, MapperProxyFactory<?>>();
	Configuration configNew=null;//访问不了父类的Configuration所以使用一个引用，引用到父类的Configuration
	//hibernate的SessionFactory
	SessionFactory sessionFactory;
	
	
	public MyMapperRegistry(Configuration config) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		super(config);
		configNew=config;
		
		Field field = MapperRegistry.class.getDeclaredField("knownMappers");
		field.setAccessible(true);
		knownMappersNew = (Map<Class<?>, MapperProxyFactory<?>>)field.get(this);
		
		
		// TODO Auto-generated constructor stub
	}

	public <T> boolean hasMapper(Class<T> type) {
		    return knownMappersNew.containsKey(type);
	}
	public Collection<Class<?>> getMappers() {
		    return Collections.unmodifiableCollection(knownMappersNew.keySet());
	}
	@Override
	public <T> void addMapper(Class<T> type) {
		if (type.isInterface()) {
			if (hasMapper(type)) {
				throw new BindingException("Type " + type
						+ " is already known to the MapperRegistry.");
			}
			boolean loadCompleted = false;
			try {
				//使用自己定义的MapperProxyFactory
				MyMapperProxyFactory myMapperProxyFactory=new MyMapperProxyFactory<T>(type);
				myMapperProxyFactory.setSessionFactory(sessionFactory);
				knownMappersNew.put(type, myMapperProxyFactory);
				// It's important that the type is added before the parser is
				// run
				// otherwise the binding may automatically be attempted by the
				// mapper parser. If the type is already known, it won't try.
				MapperAnnotationBuilder parser = new MapperAnnotationBuilder(configNew, type);
				parser.parse();
				loadCompleted = true;
			} finally {
				if (!loadCompleted) {
					knownMappersNew.remove(type);
				}
			}
		}
	}

	/**
	 * 获取hibernate的SessionFactory
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @return
	 */
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}
