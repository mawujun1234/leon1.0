package com.mawujun.repository1;

import java.lang.reflect.Field;

import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.hibernate.SessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;

/**
 * 为了实现和hibernate整合，在调用增，删，改等方法的时候，调用hibernate来进行保存
 * 目的：通过反射修改Configuration的MapperRegistry类来达到修改扩展MapperProxy类的功能，
	修改SqlSessionFactoryBean的afterPropertiesSet方法，
	 this.sqlSessionFactory = buildSqlSessionFactory();之后通过反射修改Configuration的MapperRegistry
	再重载MapperRegistry的addMapper方法，继承MapperProxyFactory类重载newInstance方法
	替换MapperProxy类为自己的类
 * @author mawujun
 *
 */
public class MySqlSessionFactoryBean extends SqlSessionFactoryBean {
	//hibernate的SessionFactory，这样就支持多数据源了，只要指定这个sessionFactory就可以了
	//mybatis的多数据源是在MapperScannerConfigurer中指定的
	SessionFactory sessionFactory;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		SqlSessionFactory sqlSessionFactory=super.getObject();
		Configuration configuration=sqlSessionFactory.getConfiguration();
		
		MyMapperRegistry mapperRegistry=new MyMapperRegistry(configuration);
		mapperRegistry.setSessionFactory(sessionFactory);
		Field field = configuration.getClass().getDeclaredField("mapperRegistry");
		field.setAccessible(true);
		field.set(configuration, mapperRegistry);
		
		
	  }

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}
