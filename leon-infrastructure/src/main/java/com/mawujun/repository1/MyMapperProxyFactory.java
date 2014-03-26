package com.mawujun.repository1;

import org.apache.ibatis.binding.MapperProxyFactory;
import org.apache.ibatis.session.SqlSession;

public class MyMapperProxyFactory<T> extends MapperProxyFactory<T> {

	public MyMapperProxyFactory(Class<T> mapperInterface) {
		super(mapperInterface);
		// TODO Auto-generated constructor stub
	}
	@Override
	public T newInstance(SqlSession sqlSession) {
		    final MyMapperProxy<T> mapperProxy = new MyMapperProxy<T>(sqlSession, super.getMapperInterface(), super.getMethodCache());
		    return newInstance(mapperProxy);
	 }

}
