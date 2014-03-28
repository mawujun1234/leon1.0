package com.mawujun.repository1;

import org.apache.ibatis.binding.MapperProxyFactory;
import org.apache.ibatis.session.SqlSession;
import org.hibernate.SessionFactory;

public class MyMapperProxyFactory<T> extends MapperProxyFactory<T> {

	SessionFactory sessionFactory;
	
	public MyMapperProxyFactory(Class<T> mapperInterface) {
		super(mapperInterface);
		// TODO Auto-generated constructor stub
	}
	@Override
	public T newInstance(SqlSession sqlSession) {
		    final MyMapperProxy<T> mapperProxy = new MyMapperProxy<T>(sqlSession, super.getMapperInterface(), super.getMethodCache());
		    mapperProxy.setSessionFactory(sessionFactory);
		    return newInstance(mapperProxy);
	 }
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}
