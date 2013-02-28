package com.mawujun.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mawujun.test.DbunitBaseRepositoryTest;
import com.mawujun.utils.PropertiesUtils;

/**
 * 测试测试环境
 * @author mawujun
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/com/mawujun/integration/applicationContext.xml"}) 
//@TransactionConfiguration(defaultRollback=false,transactionManager="transactionManager")
//@Transactional
@ActiveProfiles("test")
public class IntegrationTest  extends DbunitBaseRepositoryTest{
//	private String EntityTest_TableName="t_EntityTest";
//	private static Repository<EntityTest,Integer> repository;
//	private static SessionFactory sessionFactory;
//	private static SqlSessionFactory sqlSessionFactory;
	
	
	@Value("${jdbc.dbName}")
	private String dbName;
	@Value("${${jdbc.dbName}.mybatis.dialet}")
	private String mybatisDialet;
	@Value("${${jdbc.dbName}.hibernate.dialet}")
	private String hibernateDialet;
	
	{
		//@ActiveProfiles("test")设置后，在application.xml中并不能使用${spring.profiles.active}这个属性
		System.setProperty("spring.profiles.active", "test");
	}

	
//	private static class EntityTestRepository extends Repository<EntityTest,Integer> {
//		
//	}
//	
//	@BeforeClass
//	public static void setUpBeforeClass() throws Exception {	
//		//System.out.println(driverClassName);
//		//DbunitBaseRepositoryTest.init("")
////		repository=new  EntityTestRepository();
////		sessionFactory=HibernateUtil.getSessionFactory("com/mawujun/repository/hibernate.cfg.xml","com/mawujun/repository/hibernate.properties");
////		sqlSessionFactory=MybatisUtil.getSessionFactory("com/mawujun/repository/Configuration.xml");
////		repository.setSessionFactory(sessionFactory);
////		repository.setSqlSessionFactory(sqlSessionFactory);
//	}
//
//	@AfterClass
//	public static void tearDownAfterClass() throws Exception {
//	}
//	
//	// 讀取指定的資料集合
//	private static IDataSet getXMLDataSet(String file) throws Exception {
//		return new FlatXmlDataSetBuilder().build(new FileInputStream(file));
//	}
//
//	
//	@Before
//	public void before() throws Exception {
//		//DbunitBaseRepositoryTest.init(driver, url, usernam, password);
//		//DatabaseOperation.CLEAN_INSERT.execute(dbConn, getXMLDataSet(FileUtils.getCurrentClassPath(this)+"RepositoryTest.xml"));
//	}
//	@After
//	public void after() throws Exception {
//		//tx.commit();
//	}

	/**
	 * 测试mybatis和 <context:property-placeholder的冲突，不能使用${${jdbc.dbName}.mybatis.dialet}，嵌套的属性应用
	 * @throws IOException
	 */
	@Test
	public void test() throws IOException{
		PropertiesUtils aa=new PropertiesUtils();
		aa.load("com/mawujun/integration/dialet_config.propertites");
		assertNotNull(dbName);
		assertNotNull(mybatisDialet);
		assertNotNull(hibernateDialet);
		assertEquals(mybatisDialet, aa.get(dbName+".mybatis.dialet"));
		assertEquals(hibernateDialet, aa.get(dbName+".hibernate.dialet"));
	}
}
