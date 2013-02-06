package com.mawujun.test;

import java.sql.Connection;
import java.sql.DriverManager;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;

import com.mawujun.utils.PropertiesUtils;

/**
 * 使用dbunit进行数据的重置
 * @author mawujun
 *
 */
public class DbunitBaseRepositoryTest {
	protected static IDatabaseConnection dbConn;
	public  static void initHibernate(String pfile) {
		try {
			PropertiesUtils aa=new PropertiesUtils();
			aa.load(pfile);//加载配置文件中的数据源链接信息
			;

			//本例使用postgresql数据库 
			Class.forName(aa.getProperty("hibernate.connection.driver_class"));
			//连接DB 
			Connection conn=DriverManager.getConnection(aa.getProperty("hibernate.connection.url"),
					aa.getProperty("hibernate.connection.username"),
					aa.getProperty("hibernate.connection.password"));
			//获得DB连接
			dbConn =new DatabaseConnection(conn);
			} catch(Exception e){
				e.printStackTrace();
			}
	}
	
	public  static void init(String driver,String url,String usernam,String password){
		if(dbConn!=null){
			return;
		}
		try {
			//PropertiesUtils aa=new PropertiesUtils();
			//aa.load(pfile);//加载配置文件中的数据源链接信息
			//;

			//本例使用postgresql数据库 
			Class.forName(driver);
			//连接DB 
			Connection conn=DriverManager.getConnection(url,
					usernam,
					password);
			//获得DB连接
			dbConn =new DatabaseConnection(conn);
			} catch(Exception e){
				e.printStackTrace();
			}
	}
	
}
