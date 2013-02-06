package com.mawujun.mybatis;

import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MybatisUtil {
	//private static final SqlSessionFactory sqlSessionFactory = buildSqlSessionFactory();
	private static SqlSessionFactory sqlSessionFactory ;

	private static SqlSessionFactory buildSqlSessionFactory(String configFile) {
		try {
			//String resource = "Configuration.xml";
			InputStream inputStream = Resources.getResourceAsStream(configFile);
			SqlSessionFactory  sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			return sqlSessionFactory;
		} catch (Throwable ex) {
			// Make sure you log the exception, as it might be swallowed
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SqlSessionFactory getSessionFactory(String configFile) {
		if(sqlSessionFactory==null){
			sqlSessionFactory=buildSqlSessionFactory(configFile);
		}
		return sqlSessionFactory;
	}
}
