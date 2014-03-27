package com.mawujun.test;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.sql.DataSource;

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
	/**
	 * 根据hibernate的配置初始化数据库连接
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @param pfile
	 */
	public  static void initHibernate(String pfile) {
		try {
			PropertiesUtils aa=PropertiesUtils.load(pfile);//new PropertiesUtils();
			//aa.load(pfile);//加载配置文件中的数据源链接信息
			//;

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
	
	/**
	 * 根据传入的用户名密码等的配置初始化数据库连接
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @param pfile
	 */
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
	/**
	 * 根据配置好的DataSource的配置初始化数据库连接
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @param pfile
	 */
	public  static void initDataSource(DataSource dataSource){
		if(dbConn!=null){
			return;
		}
		try {
 
			Connection conn=dataSource.getConnection();
			dbConn =new DatabaseConnection(conn);
			} catch(Exception e){
				e.printStackTrace();
			}
	}
	
}
