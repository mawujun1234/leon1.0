package com.mawujun.repository.mybatisHibernate;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.binding.BindingException;
import org.apache.ibatis.binding.MapperProxyFactory;
import org.apache.ibatis.binding.MapperRegistry;
import org.apache.ibatis.builder.annotation.MapperAnnotationBuilder;
import org.apache.ibatis.session.Configuration;

public class MyMapperRegistry extends MapperRegistry {
	private final Map<Class<?>, MapperProxyFactory<?>> knownMappersNew;// = new HashMap<Class<?>, MapperProxyFactory<?>>();
	Configuration configNew=null;
	public MyMapperRegistry(Configuration config) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		super(config);
		configNew=config;
		
		Field field = MapperRegistry.class.getDeclaredField("knownMappers");
		field.setAccessible(true);
		knownMappersNew = (Map<Class<?>, MapperProxyFactory<?>>)field.get(this);
		
		
		// TODO Auto-generated constructor stub
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
				
				knownMappersNew.put(type, new MyMapperProxyFactory<T>(type));
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
}
